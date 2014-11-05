<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加预算项" guid="true"/>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">

var g_title = '预算项';

function addBean()
{
	submit(getTip());
}

function getTip()
{
    if(isNone(document.getElementById('id').value))
    {
        return '确定增加' + g_title + '?';
    }
    else
    {
        return '确定修改' + g_title + '?';
    }
}

function load()
{
    if(isNone(document.getElementById('id').value))
    {
        document.getElementById('navigation').innerHTML = '增加' + g_title;
    }
    else
    {
        document.getElementById('navigation').innerHTML = '修改' + g_title;
    }
}

var g_tax_flag = 1;

function selectTax(flag)
{
    g_tax_flag = flag;
    
    window.common.modal('../tax/tax.do?method=rptQueryTax&load=1&selectMode=1');
}

function getTax(oos)
{
    var obj = oos[0];
    
    if (obj.pbottomflag == 0)
    {
        alert('只能选择子级科目');
        
        return false;
    }
    
    if (g_tax_flag == 1)
    {
	    $("input[name='taxId']").val(obj.value);
	    
	    $("input[name='taxName']").val(obj.pname);
    }
    else
    {
        $("input[name='taxId2']").val(obj.value);
        
        $("input[name='taxName2']").val(obj.pname);
    }
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../budget/budget.do"><input
	type="hidden" name="method" value="addOrUpdateFeeItem">
<input id="id" type="hidden" name="id" value="${bean.id}"> 
<input id="taxId" type="hidden" name="taxId" value="${bean.taxId}"> 
<input id="taxId" type="hidden" name="taxId2" value="${bean.taxId2}"> 
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">预算项管理</span> &gt;&gt; <font id="navigation">增加预算项</font></td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>预算项基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.budget.bean.FeeItemBean" opr="1"/>

		<p:table cells="1">

			<p:pro field="name" innerString="size=60"/>
			
			<p:pro field="type">
			    <p:option type="feeItemType"></p:option>
			</p:pro>
			
			<p:pro field="taxId" innerString="readonly=true size=60" value="${bean.taxName}">
			     <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectTax(1)">&nbsp;
			</p:pro>
			
			<p:pro field="taxId2" innerString="readonly=true size=60" value="${bean.taxName2}">
                 <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectTax(2)">&nbsp;
            </p:pro>

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

