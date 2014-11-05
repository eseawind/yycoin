<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="流程模板" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定修改流程模板?');
}

function editTemplate() 
{
    if (window.ActiveXObject)
    {
        var openDocObj = new ActiveXObject("SharePoint.OpenDocuments.2");
    } 
    else
    {
        alert('请使用IE进行操作');
        return;
    }
    
    openDocObj.editDocument("${url}"); 
    
}

function viewTemplate() 
{
    if (window.ActiveXObject)
    {
        var openDocObj = new ActiveXObject("SharePoint.OpenDocuments.2");
    } 
    else
    {
        alert('请使用IE进行操作');
        return;
    }
    
    openDocObj.ViewDocument("${url}"); 
}

</script>

</head>
<body class="body_class">
<form name="addApply" action="../flow/template.do" method="post"><input type="hidden"
	name="method" value="updateTemplateFile"> <input type="hidden"
    name="id" value="${bean.id}"><p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">流程模板管理</span> &gt;&gt; 修改流程模板</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>流程模板基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.flow.bean.TemplateFileBean"
			opr="1" />

		<p:table cells="1">

			<p:pro field="name" innerString="size=50" />

			<p:cell title="模板">
				<a href="javaScript:void(0);">${bean.fileName}</a>&nbsp;&nbsp;<input class="button_class"
					type="button" name="view_b" onclick="viewTemplate()" value="在线预览">&nbsp;&nbsp;<input class="button_class"
                    type="button" name="edit_b" onclick="editTemplate()" value="在线修改">
			</p:cell>

			<p:pro field="description" cell="0" innerString="rows=4 cols=55" />

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message />
</p:body></form>
</body>
</html>

