<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="修改科目" guid="true"/>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定修改科目?');
}

function getTax(oos)
{
    var obj = oos[0];
    
    if (obj.pbottomflag == 1)
    {
        alert('只能选择父级科目');
        
        return false;
    }
    
    $("input[name='parentId']").val(obj.value);
    
    $("input[name='parentName']").val(obj.pname);
    
    $("input[name='code']").val(obj.pcode);
}

function selectTax()
{
    window.common.modal('../tax/tax.do?method=rptQueryTax&load=1&selectMode=1');
}

function load()
{
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../tax/tax.do" method="post"><input
	type="hidden" name="method" value="updateTax">
<input type="hidden" name="id" value="${bean.id}">
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">科目管理</span> &gt;&gt; 修改科目</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>科目基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.tax.bean.TaxBean" opr="1"/>

		<p:table cells="1">

			<p:pro field="name" innerString="size=60"/>
            
			<p:pro field="code" innerString="size=60 readonly='readonly'"/>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message2 />
</p:body></form>
</body>
</html>

