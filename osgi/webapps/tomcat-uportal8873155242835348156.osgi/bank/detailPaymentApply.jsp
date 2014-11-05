<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="收款申请" link="true" guid="true" cal="true" dialog="true" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script src="../stockapply_js/scheck.js"></script>
<script language="javascript">
function checkSubmit(checks, checkrefId)
{
    if (checks == '' || checkrefId == '')
    {
        alert('意见和关联单据不能为空');
        
        return false;
    }
    
    closeCheckDiv();
    
    $ajax2('../finance/bank.do?method=checkPaymentApply&id=${bean.id}', {'checks' : checks, 'checkrefId' : checkrefId}, 
                        callBackFun1);
}

function callBackFun1(data)
{
    alert(data.msg);
    
    if (data.ret == 0)
    {
        $('#checkCell_SEC').html('已核对 / ' + $('#checks').val() + ' / ' + $('#checkrefId').val());
    }
}

function checkBean()
{
    openCheckDiv();
}

</script>

</head>
<body class="body_class">
<form name="formEntry" action="../finance/bank.do" method="post">
<input type="hidden" name="method" value=""> <input
	type="hidden" name="id" value="${bean.id}"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span>收款申请详细</span></td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>申请基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		<p:class value="com.china.center.oa.finance.bean.PaymentApplyBean" />

		<p:table cells="1">
		
		    <p:cell title="标识">
               ${bean.id}
            </p:cell>

			<p:cell title="申请人">
               ${bean.stafferName}
            </p:cell>
            
			<p:cell title="经办人">
               ${bean.operatorName}
            </p:cell>            

			<p:cell title="金额">
               ${my:formatNum(bean.moneys)}
            </p:cell>

			<p:cell title="客户">
               ${bean.customerName}
            </p:cell>
            
            <p:cell title="回款导入">
               <a href="../finance/bank.do?method=findPayment&id=${bean.paymentId}&mode=2" >${bean.paymentId}</a>
            </p:cell>
            
            <p:cell title="关注类型">
               <font color="red">${my:get('pubVtype', bean.vtype)}</font>
            </p:cell>
            
            <p:cell title="核对信息" id="checkCell">
               ${my:get('pubCheckStatus', bean.checkStatus)} / ${bean.checks} / ${bean.checkrefId}
            </p:cell>

			<p:cell title="时间">
               ${bean.logTime}
            </p:cell>

		</p:table>

	</p:subBody>
	
	<p:subBody width="100%">
        <table width="100%" border="0" cellspacing='1' id="tables">
            <tr align="center" class="content0">
                <td width="10%" align="center">销售单</td>
                <td width="10%" align="center">委托结算</td>
                <td width="10%" align="center">金额</td>
                <td width="10%" align="center">收款单</td>
            </tr>

            <c:forEach items="${vsList}" var="item" varStatus="vs">
                <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                    <td align="center">${item.outId}</td>
                    <td align="center">${item.outBalanceId}</td>

                    <td align="center">${my:formatNum(item.moneys)}</td>
                    
                    <td align="center">${item.billId}</td>

                </tr>
            </c:forEach>
        </table>
    </p:subBody>

	<p:line flag="0" />

	<p:subBody width="100%">
		<table width="100%" border="0" cellspacing='1' id="tables">
			<tr align="center" class="content0">
				<td width="10%" align="center">审批人</td>
				<td width="10%" align="center">审批动作</td>
				<td width="10%" align="center">前状态</td>
				<td width="10%" align="center">后状态</td>
				<td width="45%" align="center">意见</td>
				<td width="15%" align="center">时间</td>
			</tr>

			<c:forEach items="${loglist}" var="item" varStatus="vs">
				<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
					<td align="center">${item.actor}</td>

					<td align="center">${my:get('oprMode', item.oprMode)}</td>

					<td align="center">${my:get('payApplyStatus', item.preStatus)}</td>

					<td align="center">${my:get('payApplyStatus', item.afterStatus)}</td>

					<td align="center">${item.description}</td>

					<td align="center">${item.logTime}</td>

				</tr>
			</c:forEach>
		</table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		    <input
            type="button" name="ba" class="button_class"
            onclick="checkBean()"
            value="&nbsp;&nbsp;总部核对&nbsp;&nbsp;">&nbsp;&nbsp;
            
            <input
            type="button" name="ba" class="button_class"
            onclick="javascript:history.go(-1)"
            value="&nbsp;&nbsp;返 回&nbsp;&nbsp;">
			</div>
	</p:button>

	<p:message2 />
</p:body></form>
</body>
</html>

