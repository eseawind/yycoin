<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="客户分布" />
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">

</script>

</head>
<body class="body_class"">
<form name="formEntry"><p:navigation height="22">
	<td width="550" class="navigation"><span>客户分布</span></td>
	<td width="85"></td>
</p:navigation> <br>
<p:body width="100%">

	<p:title>
		<td class="caption"><strong>客户分布：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="80%">
		<p:table cells="1">
			<p:cells celspan="0">
			    <c:forEach items="${urlList}" var="item">
				<img src="${item}">
				</c:forEach>
				<p>&nbsp;&nbsp;</p>
			</p:cells>
		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
			onclick="javascript:history.go(-1)"></div>
	</p:button>

	<p:message2 />
</p:body></form>
</body>
</html>

