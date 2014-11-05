<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加采购预付款申请" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="javascript">

function addBean()
{
	
	submit('确定提交采购预付款申请?', null, null);
}

function load()
{
	loadForm();
}

function selectProvider()
{
	window.common.modal("../provider/provider.do?method=rptQueryProvider&load=1");
}

function getProvider(id, name)
{
	$O("providerName").value = name;
	$O("providerId").value = id;
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../finance/stock.do" method="post">
	<input type="hidden" name="method" value="addStockPrePayApply">
	<input type="hidden" name="providerId" value="">
	
<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">采购预付款管理</span> &gt;&gt; 增加采购预付款</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>采购预付款基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.finance.bean.StockPrePayApplyBean" />

		<p:table cells="1">
			
			<p:pro field="providerId" innerString="readonly=true size='60'">
				<input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectProvider()">&nbsp;&nbsp;
			</p:pro>
			
			<p:pro field="invoiceId" innerString="style='width: 400px'">
				<c:forEach items="${invoiceList}" var="item">
                    <option value="${item.id}">${item.fullName}</option>
                </c:forEach>
			</p:pro>

			<p:pro field="moneys" value="0.0" innerString="oncheck='isFloat'"/>
			
			<p:pro field="payDate" />
			
			<p:pro field="description" innerString="rows=2 cols=80" />			
			
		</p:table>
	</p:subBody>

	<p:line flag="0" />
	
	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button"
			class="button_class" id="ok_b" style="cursor: pointer"
			value="&nbsp;&nbsp;提  交&nbsp;&nbsp;" onclick="addBean()"></div>
	</p:button>

	<p:message />
	
</p:body>
</form>

</body>
</html>