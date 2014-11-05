<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="流程定义" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script language="javascript">
function addBean()
{
     //自动校验
    if (formCheck(formEntry) && checked() && window.confirm('确认保存流程定义'))
    {
        submitC(formEntry);
    }
}

function checked()
{
    if ($$('mode') == 1 && $O('templateIds').value == '')
    {
        alert('请选择流程模板');
        return false;
    }
    
    return true;
}

function selectTemplate()
{
    window.common.modal('../flow/template.do?method=rptQueryTemplateFile&load=1');
}

function clears()
{
    $O('templateIds').value = '';
    $O('templateNames').value = '';
}
function getTemplateFile(oo)
{
    var currentObj;
    var currentDis;
    
    currentObj = $O('templateIds');
    currentDis = $O('templateNames');
   
    
    for (var i = 0; i < oo.length; i++)
    {
        var eachItem = oo[i];
        
        if (containOption(eachItem.value))
        {
            continue;
        }
        
        currentObj.value += eachItem.value + ';'
        currentDis.value += eachItem.pname + ';'
    }
}

function containOption(id)
{
    var currentObj;
    
    currentObj = $O('templateIds');
    
    var vsoptions = currentObj.value.split(';');
        
    for (var k = 0; k < vsoptions.length; k++)
    {
        if (vsoptions[k] == id)
        {
            return true;
        }
    }
    
    return false;
}

function change()
{
    if ($$('mode') == 1)
    {
        $v('tr_stemp', true);
    }
    else
    {
        clears();
        $v('tr_stemp', false);
    }
}

function load()
{
    $v('tr_stemp', false);
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../flow/flow.do" method="post"><input
	type="hidden" name="method" value="addFlowDefine"> <input
	type="hidden" name="templateIds" value=""> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">流程定义管理</span> &gt;&gt; 新建流程定义</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">
	<p:class value="com.china.center.oa.flow.bean.FlowDefineBean" opr="1" />

	<p:subBody width="98%">
		<p:table cells="1" id="tables">

			<p:pro field="name" innerString="size=50"/>

			<p:pro field="type">
			    <option value="">--</option>
				<p:option type="112" />
			</p:pro>

			<p:pro field="parentType">
                <option value="">--</option>
                <p:option type="parentType" />
            </p:pro>
            
			<p:pro field="mode" innerString="onchange=change()">
				<option value="">--</option>
				<p:option type="flowMode" />
			</p:pro>
			

			<p:cell title="选择模板" width="8" id="stemp">
				<input type="text" name="templateNames" size="60" readonly="readonly" style="cursor: pointer;" onclick="selectTemplate()">
				<span style="cursor: pointer;" onclick="selectTemplate()">选择流程模板</span>&nbsp;
				<span style="cursor: pointer;" onclick="clears()">清空</span>
			</p:cell>
			
			<p:pro field="description" innerString="rows=3 cols=55" />
			
		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;保 存&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message />
</p:body></form>
</body>
</html>

