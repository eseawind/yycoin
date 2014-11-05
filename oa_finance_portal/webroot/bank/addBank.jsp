<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加帐户" guid="true"/>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定增加帐户?');
}

var s_index = 0;

function selectTax(index)
{
    s_index = index;
    
    window.common.modal('../tax/tax.do?method=rptQueryTax&load=1&selectMode=1');
}

function getTax(oos)
{
    var obj = oos[0];
    
    if (obj.pbottomflag == 1)
    {
        alert('只能选择父级科目');
        
        return false;
    }
    
    if (s_index == 0)
    {
	    $("input[name='parentTaxId']").val(obj.value);
	    $("input[name='parentTaxName']").val(obj.pname);
	    $("input[name='code']").val(obj.pcode + '-');
    }
    else
    {
        $("input[name='parentTaxId2']").val(obj.value);
        $("input[name='parentTaxName2']").val(obj.pname);
        $("input[name='code2']").val(obj.pcode + '-');
    }
}

</script>

</head>
<body class="body_class">
<form name="formEntry" action="../finance/bank.do" method="post">
<input type="hidden" name="method" value="addBank">
<input type="hidden" name="parentTaxId" value="">
<input type="hidden" name="parentTaxId2" value="">

<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">帐户管理</span> &gt;&gt; 增加帐户</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>帐户基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.finance.bean.BankBean" />

		<p:table cells="1">

			<p:pro field="name" innerString="size=60"/>
			<p:pro field="bankNo" innerString="size=60"/>
			
			<p:pro field="dutyId">
                <p:option type="$dutyList"/>
            </p:pro>
            
            <p:pro field="taxType">
                 <option value=0>现金</option>
                 <option value=1>银行</option>
            </p:pro>
            
            <p:cell title="银行父级科目">
                <input type="text" name="parentTaxName" value="" readonly="readonly" size="60">
                <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectTax(0)">&nbsp;&nbsp;
            </p:cell>
            
            <p:pro field="code" innerString="size=60"/>
            
            <p:cell title="暂记户父级科目">
                <input type="text" name="parentTaxName2" value="" readonly="readonly" size="60">
                <input type="button" value="&nbsp;...&nbsp;" name="qout2" id="qout2"
                    class="button_class" onclick="selectTax(1)">&nbsp;&nbsp;
            </p:cell>
            
            <p:pro field="code2" innerString="size=60"/>
            
            <p:cell title="辅助核算项">
	            <input type="checkbox" name="unit" value="1">单位<br>
	            <input type="checkbox" name="department" value="1">部门<br>
	            <input type="checkbox" name="staffer" value="1">职员<br>
	            <input type="checkbox" name="depot" value="1">仓库<br>
	            <input type="checkbox" name="product" value="1">产品<br>
	            <input type="checkbox" name="duty" value="1">纳税实体
            </p:cell>

			<p:pro field="description" cell="0" innerString="rows=3 cols=55" />

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

