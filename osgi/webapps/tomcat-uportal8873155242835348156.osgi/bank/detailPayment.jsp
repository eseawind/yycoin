<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加回款" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定增加回款?');
}

function selectBank()
{
    //单选
    window.common.modal('../finance/bank.do?method=rptQueryBank&load=1');
}

function getBank(obj)
{
    $O('bankName').value = obj.pname;
    
    $O('bankId').value = obj.value;
}

function selectStaffer()
{
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

function getStaffers(oos)
{
    var oo = oos[0];
    
    $O('owerName').value = oo.pname;
    $O('destStafferId').value = oo.value;
}

function clearValues()
{
    $O('owerName').value = '';
    $O('destStafferId').value = '';
}

</script>

</head>
<body class="body_class">
<form name="formEntry" action="../finance/bank.do" method="post">
<p:navigation height="22">
	<td width="550" class="navigation">回款明细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>回款基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		<p:class value="com.china.center.oa.finance.vo.PaymentVO" opr="2" />

		<p:table cells="2">
			<p:cell title="系统标识">
                ${bean.id}
            </p:cell>

			<p:cell title="导入标识">
                ${bean.refId}
            </p:cell>

			<p:cell title="导入批次">
                ${bean.batchId}
            </p:cell>

			<p:cell title="回款时间">
                ${bean.receiveTime}
            </p:cell>

			<p:cell title="帐户">
                ${bean.bankName}
            </p:cell>
            
            <p:cell title="导入时间">
                ${bean.logTime}
            </p:cell>
            
            <p:cell title="状态">
                ${my:get('paymentStatus', bean.status)}
            </p:cell>
            
            <p:pro field="bakmoney" />
            
			<p:pro field="updateTime" />
			
			<p:pro field="checkStatus">
                <p:option type="paymentChechStatus" />
            </p:pro>
            
			<p:pro field="checks1" />
			<p:pro field="checks2" />
			
			<p:pro field="checks3" cell="0"/>
			
			<p:pro field="fromer" innerString="size=60" />

			<p:pro field="type">
				<p:option type="paymentType" />
			</p:pro>

			<p:pro field="money" />

			<p:pro field="handling" />

			<p:cell title="认领职员">
                ${bean.stafferName}
            </p:cell>

			<p:cell title="关联客户">
                ${bean.customerName}
            </p:cell>

			<p:pro field="description" cell="2" />
			
			<p:cells celspan="2" title="关联凭证">
	            <c:forEach items="${financeBeanList}" var="item">
	                <a href="../finance/finance.do?method=findFinance&id=${item.id}">${item.id}</a>
	                &nbsp;
	            </c:forEach>
            </p:cells>

		</p:table>
	</p:subBody>

	<p:line />

	<p:subBody width="100%">
		<table width="100%" border="0" cellspacing='1' id="tables">
			<tr align="center" class="content0">
				<td width="10%" align="center">时间</td>
				<td width="10%" align="center">收付款单</td>
				<td width="10%" align="center">类型</td>
				<td width="15%" align="center">帐户</td>
				<td width="10%" align="center">金额</td>
				<td width="15%" align="center">关联</td>
			</tr>

			<c:forEach items="${inBillList}" var="item" varStatus="vs">
				<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
					<td align="center">${item.logTime}</td>
					<td align="center"><a
						href="../finance/bill.do?method=findInBill&id=${item.id}">${item.id}</a></td>

					<td align="center">收款单</td>
					<td align="center">${item.bankName}</td>

					<td align="center">${my:formatNum(item.moneys)}</td>
					<td align="center"><a href="../sail/out.do?method=findOut&fow=99&outId=${item.outId}">${item.outId}</a></td>
				</tr>
			</c:forEach>

			<c:forEach items="${outBillList}" var="item" varStatus="vs">
				<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
					<td align="center">${item.logTime}</td>
					<td align="center"><a
						href="../finance/bill.do?method=findOutBill&id=${item.id}">${item.id}</a></td>
					<td align="center">付款单</td>
					<td align="center">${item.bankName}</td>
					<td align="center">${my:formatNum(item.moneys)}</td>
					<td align="center">${item.stockId}</td>
				</tr>
			</c:forEach>

		</table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" name="ba"
			class="button_class" onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
	</p:button>

	<p:message2 />
</p:body></form>
</body>
</html>

