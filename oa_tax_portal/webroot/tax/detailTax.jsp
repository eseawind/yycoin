<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加科目" guid="true"/>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
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
    //$v('refId_TR', false);
    //$v('refType_TR', false);
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../tax/tax.do" method="post">
<input type="hidden" name="parentId" value="">
<p:navigation
	height="22">
	<td width="550" class="navigation">科目明细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>科目基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.tax.bean.TaxBean" opr="2"/>

		<p:table cells="1">

			<p:pro field="name" innerString="size=60"/>
			
			<p:pro field="parentId" value="${bean.parentName}">
            </p:pro>
            
			<p:pro field="code" innerString="size=60"/>
			
			<c:if test="${my:length(bean.refId) != 0}">
	            <p:pro field="refId" innerString="style='width:400px'">
	                <p:option type="bankList" empty="true"/>
	            </p:pro>
	            
	            <p:pro field="refType">
	                <p:option type="taxRefType" empty="true"/>
	            </p:pro>
            </c:if>
			
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
            
            <p:cell title="级别">
              第${bean.level}级
            </p:cell>

			<p:cell title="辅助核算项">
			${bean.other}
			</p:cell>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
        <div align="right"><input type="button" class="button_class"
            id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
            onclick="javaScript:window.history.go(-1);"></div>
    </p:button>

	<p:message2 />
</p:body></form>
</body>
</html>

