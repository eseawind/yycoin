<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="销售数据单导入" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定导入销售数据信息?', null, checkValue);
}

function checkValue()
{   
    var fileName = $O('myFile').value;
        
    if ("" == fileName)
    {
        alert("请输入要导入的文件名");
        return false;
    }
    
    if (fileName.indexOf('xls') == -1)
    {
        alert("只支持XLS文件格式!");
        return false;
    }
    
    return true;
}


function load()
{
	loadForm();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry"
	action="../sail/outImport.do?method=importOutDepot"
	enctype="multipart/form-data" method="post"><p:navigation
	height="22">
	<td width="550" class="navigation"><span>导入销售单</span></td>
	<td width="85"></td>
</p:navigation> <br>
<p:body width="100%">

	<p:title>
		<td class="caption"><strong>选择文件：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="80%">

		<p:table cells="1">
			<p:cell title="导入模板">
				<a target="_blank"
					href="../admin/down.do?method=downTemplateFileByName&fileName=OutDepotTemplate.xls">下载销售单号导入模板</a>
			</p:cell>
		</p:table>

		<p:table cells="1">
			<p:cell title="导入文件" end="true">
				<input type="file" name="myFile" style="width: 70%;" class="button_class" />
			</p:cell>
		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message2 />
</p:body></form>
</body>
</html>

