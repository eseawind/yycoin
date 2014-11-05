<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="补货查询" cal="true" guid="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/tableSort.js"></script>
<script language="javascript">
function exports()
{
	//document.location.href = '../finance/finance.do?method=exportFinanceItem';
}

function load()
{
	loadForm();
	
	bingTable("senfe");
}

function query()
{
	submit(null, null, null);
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../sail/outImport.do" method="post">
<input type="hidden" name="method" value="queryNegativeInventory"> 
<input type="hidden" value="2" name="firstLoad">
<p:navigation
	height="22">
	<td width="550" class="navigation">补货查询</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>补货查询：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0" id="senfe">
			<tr align=center class="content0">
				<td align="center" width="12%" class="td_class" onclick="tableSort(this)"><strong>产品</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>仓区</strong></td>				
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>负库存数量</strong></td>
			</tr>

			<c:forEach items="${list}" var="item"
				varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="left">${item.productName}</td>
					<td align="left">${item.depotpartName}</td>
					<td align="left">${item.amount}</td>
				</tr>
			</c:forEach>
		</table>
		
		<p:formTurning form="formEntry" method="queryNegativeInventory"></p:formTurning>

	</p:subBody>

	<p:line flag="1" />
	
	<p:button leftWidth="100%" rightWidth="0%">
        <div align="right"><input type="button" class="button_class"
            id="ok_export" style="cursor: pointer" value="&nbsp;&nbsp;导 出&nbsp;&nbsp;"
            onclick="export()">&nbsp;&nbsp;
            <input type="button" class="button_class"
            id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
            onclick="javaScript:window.history.go(-1);"></div>
    </p:button>
	
	<p:message2 />
	
</p:body>
</form>
</body>
</html>

