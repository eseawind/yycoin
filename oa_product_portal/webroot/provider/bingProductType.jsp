<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="绑定类型"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定增加绑定关系?');
}

function load()
{
	loadForm();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply" action="../provider/provider.do" method="post"><input
	type="hidden" name="method" value="bingProductTypeToProvider">
	<input
	type="hidden" name="pid" value="${bean.id}"><p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: hand"
		onclick="javascript:history.go(-1)">供应商管理</span> &gt;&gt; 绑定类型</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>绑定类型：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:table cells="1">

			<p:cell title="供应商">
			${bean.name}
			</p:cell>

			<p:cell title="产品类型">
				<c:forEach items="${list}" var="item">
					<input type="checkbox" name="productTypeId" value="${item.id}" ${my:length(mapVS[item.id]) > 0 ? "checked=true" : ""}>${item.name}
					<br>
				</c:forEach>
			</p:cell>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			name="adds" style="cursor: pointer"
			value="&nbsp;&nbsp;确 定&nbsp;&nbsp;" onclick="addBean()">&nbsp;&nbsp;
		<input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
	</p:button>

	<tr>
		<td colspan='2' align="center"><FONT color="blue">${MESSAGE_INFO}</FONT><FONT
			color="red">${errorInfo}</FONT></td>
	</tr>
</p:body></form>
</body>
</html>

