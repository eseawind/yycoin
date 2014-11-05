<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="提成外部数据导入及净利导出" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	
	submit('确定导入数据?', null, checkValue);
}

function checkValue()
{   

	if (getRadio('importType') && getRadioValue('importType'))
	{
		$O('type').value = getRadioValue('importType');
	}
	else
	{
		alert("请选择要导入的数据类型");
        return false;
	}
	
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

function exportProfit()
{
	var month = $$('month');
	
	document.location.href = '../commission/commission.do?method=exportProfit&month='+month;
}

</script>

</head>
<body class="body_class"">
<form name="formEntry"
	action="../commission/commission.do?method=importExternel"
	enctype="multipart/form-data" method="post">
	<input type="hidden" name="type" value="">
	<p:navigation
	height="22">
	<td width="550" class="navigation"><span>导入/导出</span></td>
	<td width="85"></td>
</p:navigation> <br>
<p:body width="100%">

	<p:title>
		<td class="caption"><strong>选择文件：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="80%">

		<p:table cells="1">
		    <p:cell title="选择导入类型">
               <input type="radio" name="importType" value="0" checked="checked" />职员年限&nbsp;&nbsp;
			   <input type="radio" name="importType" value="1" />KPI及应发提成&nbsp;&nbsp;
            </p:cell>
			<p:cell title="导入文件">
				<input type="file" name="myFile" style="width: 70%;" class="button_class" />
			</p:cell>
		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
			<input type="button" class="button_class"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()">
		</div>
	</p:button>

	<p:title>
		<td class="caption"><strong>导出净利：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="80%">

		<p:table cells="1">
		    <p:cell title="选择月份">
               <input type="text" name="month" value="" title="如：201211"/>
            </p:cell>
		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			style="cursor: pointer" value="&nbsp;&nbsp;导出净利&nbsp;&nbsp;"
			onclick="exportProfit()">			
		</div>
	</p:button>

	<p:message2 />
</p:body></form>
</body>
</html>

