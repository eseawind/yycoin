<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="付款申请" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">
function pagePrint()
{
    $O('pr').style.display = 'none';
    $O('ba').style.display = 'none';
    $O('tables').style.display = 'none';
    window.print();

    $O('pr').style.display = 'inline';
    $O('ba').style.display = 'inline';
    $O('tables').style.display = 'block';
}


</script>

</head>
<body class="body_class">
<form name="formEntry" action="../finance/stock.do" method="post">
<input type="hidden" name="method" value=""> 
<input type="hidden" name="id" value="${bean.id}"> 
<p:navigation
	height="22">
	<td width="550" class="navigation"><span>付款申请明细</span></td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>付款基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		<p:class value="com.china.center.oa.finance.bean.StockPayApplyBean" />

		<p:table cells="2">

			<p:cell title="申请人">
               ${bean.stafferName}
            </p:cell>

            <p:cell title="应付金额">
               ${my:formatNum(bean.moneys)}
            </p:cell>
            
            <p:cell title="实付金额">
               ${my:formatNum(bean.realMoneys)}
            </p:cell>
            
            <p:cell title="类型">
               ${my:get('stockPayApplyStatus', bean.status)}
            </p:cell>

			<p:cell title="供应商">
               ${bean.provideName}
            </p:cell>
            
            <p:cell title="最早付款">
               ${bean.payDate}
            </p:cell>
            
             <p:cell title="采购信息">
               <a href="../stock/stock.do?method=findStock&id=${bean.stockId}">${bean.stockId}</a>/${bean.stockItemId}
            </p:cell>

			<p:cell title="时间" end="true">
               ${bean.logTime}
            </p:cell>
            
            <p:cell title="备注" end="true">
               ${bean.description}
            </p:cell>
         
		</p:table>

	</p:subBody>
	
	<c:if test="${my:length(outList) > 0}">
	
	<p:line flag="0" />

    <p:subBody width="100%">
        <table width="100%" border="0" cellspacing='1' id="tables1">
            <tr align="center" class="content0">
                <td align="center">付款单</td>
                <td align="center">金额</td>
                <td align="center">银行</td>
            </tr>

            <c:forEach items="${outList}" var="item" varStatus="vs">
                <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                    <td align="center"><a href="../finance/bill.do?method=findOutBill&id=${item.id}">${item.id}</a></td>

                    <td align="center">${my:formatNum(item.moneys)}</td>

                    <td align="center">${item.bankName}</td>

                </tr>
            </c:forEach>
        </table>
    </p:subBody>
    
	</c:if>
	
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

					<td align="center">${my:get('stockPayApplyStatus', item.preStatus)}</td>

					<td align="center">${my:get('stockPayApplyStatus', item.afterStatus)}</td>

					<td align="center">${item.description}</td>

					<td align="center">${item.logTime}</td>

				</tr>
			</c:forEach>
		</table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		<input type="button" name="pr"
            class="button_class" onclick="pagePrint()"
            value="&nbsp;&nbsp;打 印&nbsp;&nbsp;">&nbsp;&nbsp;
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

