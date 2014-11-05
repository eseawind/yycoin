<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="黑名单明细" cal="true" guid="true"/>
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
<form name="formEntry" action="../commission/black.do" method="post">
<input type="hidden" name="method" value="findBlack"> 
<input type="hidden" value="2" name="firstLoad">
<input type="hidden" name="id" value="${id}">
<input type="hidden" name="type" value="${type}">
<p:navigation
	height="22">
	<td width="550" class="navigation">黑名单明细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>黑名单单据明细：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0" id="senfe">
			<tr align=center class="content0">				
				<td align="center" width="10%" class="td_class" onclick="tableSort(this)"><strong>类型</strong></td>
				<td align="center" width="20%" class="td_class" onclick="tableSort(this)"><strong>销售单</strong></td>
				<td align="center" width="40%" class="td_class" onclick="tableSort(this)"><strong>客户</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>应收金额（合计：${my:formatNum(total)}）</strong></td>
			</tr>

			<c:forEach items="${beanList}" var="item"
				varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="left">${my:get('blackDataType',item.type)}</td>
					<td align="left"><a href="../sail/out.do?method=findOut&fow=99&outId=${item.outId}">${item.outId}</a></td>
					<td align="left">${item.customerName}</td>
					<td align="right">${my:formatNum(item.money)}</td>
				</tr>
			</c:forEach>
		</table>
		
		<p:formTurning form="formEntry" method="findBlack"></p:formTurning>

	</p:subBody>

	<p:line flag="1" />
	
	<p:button leftWidth="100%" rightWidth="0%">
        <div align="right"><input type="button" class="button_class"
            id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
            onclick="javaScript:window.history.go(-1);"></div>
    </p:button>
	
	<p:message2 />
	
</p:body>
</form>
</body>
</html>

