<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加科目" guid="true"/>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定增加科目?');
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
    
    $("input[name='code']").val(obj.pcode + '-');
}

function selectTax()
{
    window.common.modal('../tax/tax.do?method=rptQueryTax&load=1&selectMode=1');
}

var gbref = false;
function selectBank()
{
    gbref = !gbref;
    $v('refId_TR', gbref);
    $v('refType_TR', gbref);
}

function load()
{
    $v('refId_TR', false);
    $v('refType_TR', false);
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../tax/tax.do" method="post"><input
	type="hidden" name="method" value="addTax">
<input type="hidden" name="parentId" value="">
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">科目管理</span> &gt;&gt; 增加科目</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>科目基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.tax.bean.TaxBean" />

		<p:table cells="1">

			<p:pro field="name" innerString="size=60"/>
			
			<p:pro field="parentId" innerString="readonly=true size=60">
                <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectTax()">&nbsp;
                <input type="button" value="&nbsp;高级配置&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectBank()">&nbsp;&nbsp;
            </p:pro>
            
			<p:pro field="code" innerString="size=60"/>
			
            <p:pro field="refId" innerString="style='width:400px'">
                <p:option type="bankList" empty="true"/>
            </p:pro>
            
            <p:pro field="refType">
                <p:option type="taxRefType" empty="true"/>
            </p:pro>
			
			<p:pro field="bottomFlag">
                <p:option type="taxBottomFlag"/>
            </p:pro>
			
			<p:pro field="ptype">
				<p:option type="taxTypeList"/>
			</p:pro>
			
			<p:pro field="type">
                <p:option type="taxType"/>
            </p:pro>
            
            <p:pro field="forward">
                <p:option type="taxForward"/>
            </p:pro>
            
            <p:pro field="checkStaffer">
                <p:option type="taxCheckStaffer"/>
            </p:pro>

			<p:cell title="辅助核算项">
			<input type="checkbox" name="unit" value="1">单位<br>
			<input type="checkbox" name="department" value="1">部门<br>
			<input type="checkbox" name="staffer" value="1">职员<br>
			<input type="checkbox" name="depot" value="1">仓库<br>
			<input type="checkbox" name="product" value="1">产品<br>
			<input type="checkbox" name="duty" value="1">纳税实体
			</p:cell>

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

