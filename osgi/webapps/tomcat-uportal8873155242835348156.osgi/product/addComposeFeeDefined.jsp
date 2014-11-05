<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加合成费用" guid="true"/>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定增加合成费用?');
}

function getTax(oos)
{
    var obj = oos[0];
    
    if (obj.pbottomflag == 0)
    {
        alert('只能选择最小科目');
        
        return false;
    }
    
    $("input[name='taxId']").val(obj.value);
    
    $("input[name='taxName']").val(obj.pname);
}

function selectTax()
{
    window.common.modal('../tax/tax.do?method=rptQueryTax&load=1&selectMode=1');
}

</script>

</head>
<body class="body_class">
<form name="formEntry" action="../product/product.do" method="post">
<input type="hidden" name="method" value="addComposeFeeDefined">
<input type="hidden" name="taxId" value="">
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">合成费用管理</span> &gt;&gt; 增加合成费用</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>合成费用基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.product.bean.ComposeFeeDefinedBean" />

		<p:table cells="1">

			<p:pro field="name" innerString="size=60"/>
			
			<p:pro field="taxId" innerString="readonly=true size=60">
                <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectTax()">&nbsp;&nbsp;
            </p:pro>
			
			<p:pro field="description" innerString="rows=3 cols=55" />

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

