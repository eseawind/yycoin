<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="上传客户" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定上传客户资料?', null, checkValue);
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

function exportCur()
{
    document.location.target = '_blank';
    document.location.href = '../sail/out.do?method=exportNotPay';
}

</script>

</head>
<body class="body_class"">
<form name="addApply"
	action="../customer/customer.do?method=uploadCustomer"
	enctype="multipart/form-data" method="post"><p:navigation
	height="22">
	<td width="550" class="navigation"><span>导入客户</span></td>
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
					href="../admin/down.do?method=downTemplateFile">下载客户资料导入模板(2008-12-26更新模板增加了CODE，导入请确认模板是否一致)</a>
			</p:cell>
		</p:table>

		<p:table cells="1">
			<p:cell title="导入文件">
				<input type="file" name="myFile" class="button_class" />
			</p:cell>
			<p:cell title="导出应收客户">
                <input type="button" class="button_class"  value="导出应收客户" onclick="exportCur()"/>
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

