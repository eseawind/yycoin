<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加分公司" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addApplys()
{
	submit('确定增加分公司?');
}

//选择职位
function selectPrin()
{
    window.common.modal('../admin/org.do?method=popOrg&addLocation=1');
}

function setOrgFromPop(id, name, level)
{
	if (level != 3)
	{
		alert('分公司只能挂靠二级组织');
		
		return false;
	}
    $O('parentId').value = id;
    
    $O('parentName').value = name;
}
</script>

</head>
<body class="body_class">
<form name="addApply" action="../admin/location.do"><input
	type="hidden" name="method" value="addLocation">
<input type="hidden" name="parentId" value=""> 	
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">分公司管理</span> &gt;&gt; 增加分公司</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>分公司基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.publics.bean.LocationBean" />

		<p:table cells="1">

			<p:pro field="name" />

			<p:pro field="code" innerString="style='ime-mode: disabled'" />
			
			<p:pro field="parentId" innerString="readonly=true">
			<input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectPrin()">&nbsp;&nbsp;
			</p:pro>

			<p:pro field="description" cell="0" innerString="rows=3 cols=55" />

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addApplys()"></div>
	</p:button>

	<p:message/>
</p:body></form>
</body>
</html>

