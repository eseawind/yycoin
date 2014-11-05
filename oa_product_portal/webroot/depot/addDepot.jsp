<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加仓库" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定增加仓库?');
}
</script>

</head>
<body class="body_class">
<form name="addApply" action="../depot/depot.do" method="post"><input
	type="hidden" name="method" value="addDepot">
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">仓库管理</span> &gt;&gt; 增加仓库</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>仓库基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.product.bean.DepotBean" />

		<p:table cells="1">

			<p:pro field="name" innerString="size=60"/>
			
			<p:pro field="type">
				<p:option type="depotType"/>
			</p:pro>
			
			<p:pro field="industryId" innerString="style='width:240px'">
				<p:option type="$industryList" empty="true"/>
			</p:pro>

			<p:pro field="industryId2" innerString="style='width:240px'">
				<p:option type="$adminindustryList" empty="true"/>
			</p:pro>

			<p:pro field="description" cell="0" innerString="rows=3 cols=55" />

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message/>
</p:body></form>
</body>
</html>

