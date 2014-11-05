<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="修改运输" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>

<c:set var="dis" value="修改" />

<script language="javascript">
function addApplys()
{
	submit('确定${dis}运输[' + $$('name') + ']');
}

function load()
{
	loadForm();
	change();
}

function change()
{
	if ($$('type') == 0)
	{
		$d('parent', true);
	}
	else
	{
		$d('parent', false);
	}
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply" action="../sail/transport.do">
<input type="hidden" name="method" value="updateTransport">
<input type="hidden" name="id" value="${bean.id}">
 <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">运输管理</span> &gt;&gt; ${dis}运输</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>运输信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.sail.bean.TransportBean" opr="1"/>

		<p:table cells="1">
			<p:cell title="类型">
				<select name="type" onchange="change()" oncheck="notNone;">
					<option value="1">运输方式</option>
				</select>
				<font color="#FF0000">*</font>
			</p:cell>

			<p:pro field="name" innerString="readonly=readonly"/>

			<p:pro field="parent">
				<option value="">--</option>
				<c:forEach items="${transportList}" var="item">
					<option value="${item.id}">${item.name}</option>
				</c:forEach>
			</p:pro>
			
			<p:pro field="company" />
			<p:pro field="conphone" />
			<p:pro field="area" />
			<p:pro field="outTime" />

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addApplys()">&nbsp;&nbsp; <input type="button"
			class="button_class" onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
	</p:button>

	<p:message2/>
</p:body></form>
</body>
</html>

