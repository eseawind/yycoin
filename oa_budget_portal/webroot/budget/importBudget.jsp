<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="导入预算项" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定导入预算项?', null, checkValue);
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

var budgetObj;

function selectBudget(obj)
{
	
	budgetObj = obj;
	
    window.common.modal('../budget/budget.do?method=rptQueryDepartmentYearBudget&load=1&selectMode=1');
}

function getDepartmentYearBudgets(oos)
{
    var obj = oos[0];
    
    $O("parentName").value= obj.pname;
    
    $O("parentId").value = obj.value;
}



</script>

</head>
<body class="body_class"">
<form name="formEntry"
	action="../budget/budget.do?method=importBudget"
	enctype="multipart/form-data" method="post">
	<input type="hidden" name="nextLevel" value="2">
	<input type="hidden" name="type" value="2">
	<input type="hidden" name="parentId" value="">
	<p:navigation
	height="22">
	<td width="550" class="navigation"><span>导入预算项</span></td>
	<td width="85"></td>
</p:navigation> <br>
<p:body width="100%">

	<p:title>
		<td class="caption"><strong>选择文件：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="80%">

		<p:table cells="1">
		    <p:cell title="选择部门月度预算">
               <input type="text" style="width: 50%;cursor: pointer;"
                    name="parentName" value="" oncheck="notNone;" readonly="readonly" onclick="selectBudget(this)">
			   
            </p:cell>
			<p:cell title="导入文件">
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

