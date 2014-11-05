<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="预占库存检查列表" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="javascript">
function sures()
{	
	submit('确定预占库存吗?只能对库存足够的产品进行预占.', null, null);
}

function load()
{
	loadForm();
}


</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../sail/outImport.do" method="post"><input
	type="hidden" name="method" value="processSplitOut">
	<input type="hidden" value="${batchId}" name="batchId">

<p:navigation
	height="22">
	<td width="550" class="navigation">库存检查结果</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>库存检查结果：（注意，只针对招行导入的销售单）</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center">批次号</td>
				<td align="center">产品</td>
				<td align="center">所需数量</td>
				<td align="center">可用库存</td>	
				<td align="center">库存情况</td>
			</tr>

			<c:forEach items="${resultList}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">					
					<td align="center" onclick="hrefAndSelect(this)">${item.batchId}</td>
					<td align="left" onclick="hrefAndSelect(this)">${item.productName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.amount}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.mayAmount}</td>
					<td align="center" onclick="hrefAndSelect(this)">
					<c:if test="${item.mayAmount >= item.amount}"><font color="blue">库存足够</font></c:if>
					<c:if test="${item.mayAmount < item.amount}"><font color="red">库存不够</font></c:if>
					</td>
				</tr>
			</c:forEach>
		</table>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
        <input type="button" class="button_class" id="sure1"
            value="&nbsp;&nbsp;确定预占库存&nbsp;&nbsp;" onClick="sures()" id="sures">&nbsp;&nbsp;
		</div>
	</p:button>

	<p:message2 />

</p:body></form>
</body>
</html>

