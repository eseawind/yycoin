<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="产品类别列表" />
<base target="_self">
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function querys()
{
	formEntry.submit();
}

function sures()
{
	add();
    
    closes();
}

function add()
{
    var opener = window.common.opener();
    
    var oo = getCheckBox("beans");
    
    if (oo && oo.length == 0)
    {
        alert('请选择促销规则');
        return;
    }
    
    if (oo)
    opener.getProductType(oo);
}

function closes()
{
	var opener = window.common.opener();
	
	opener = null;
	window.close();
}

function load()
{
	loadForm();
}

function press()
{
    window.common.enter(querys);
} 

function closesd()
{
    var opener = window.common.opener();
    
    opener = null;
    window.close();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../commission/black.do" method="post"><input
	type="hidden" name="method" value="rptQueryProductType"><input
	type="hidden" value="1" name="load">
<input type="hidden" value="${selectMode}" name="selectMode"> 

<p:navigation
	height="22">
	<td width="550" class="navigation">产品类别管理</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>列表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center">选择</td>
				<td align="center">名称</td>
				<td align="center">编码</td>
			</tr>

			<tr class="content1">
				<td align="center"><input type='${selectMode == 1 ? "radio" : "checkbox"}' name="beans"
				pname="金银章" value="0"/></td>
				<td align="center" onclick="hrefAndSelect(this)">金银章</td>
				<td align="center" onclick="hrefAndSelect(this)">0</td>
			</tr>
			
			<tr class="content2">
				<td align="center"><input type='${selectMode == 1 ? "radio" : "checkbox"}' name="beans"
				pname="金银币" value="1"/></td>
				<td align="center" onclick="hrefAndSelect(this)">金银币</td>
				<td align="center" onclick="hrefAndSelect(this)">1</td>
			</tr>
			
			<tr class="content1">
				<td align="center"><input type='${selectMode == 1 ? "radio" : "checkbox"}' name="beans"
				pname="流通币" value="2"/></td>
				<td align="center" onclick="hrefAndSelect(this)">流通币</td>
				<td align="center" onclick="hrefAndSelect(this)">2</td>
			</tr>

			<tr class="content2">
				<td align="center"><input type='${selectMode == 1 ? "radio" : "checkbox"}' name="beans"
				pname="旧币" value="3"/></td>
				<td align="center" onclick="hrefAndSelect(this)">旧币</td>
				<td align="center" onclick="hrefAndSelect(this)">3</td>
			</tr>
			
			<tr class="content1">
				<td align="center"><input type='${selectMode == 1 ? "radio" : "checkbox"}' name="beans"
				pname="邮票" value="4"/></td>
				<td align="center" onclick="hrefAndSelect(this)">邮票</td>
				<td align="center" onclick="hrefAndSelect(this)">4</td>
			</tr>
			
			<tr class="content2">
				<td align="center"><input type='${selectMode == 1 ? "radio" : "checkbox"}' name="beans"
				pname="其他" value="5"/></td>
				<td align="center" onclick="hrefAndSelect(this)">其他</td>
				<td align="center" onclick="hrefAndSelect(this)">5</td>
			</tr>

		</table>
			
		<p:formTurning form="formEntry" method="rptQueryPromotion"></p:formTurning>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		<c:if test="${selectMode != 1}">
		<input type="button" class="button_class" id="adds"
            value="&nbsp;&nbsp;选 择&nbsp;&nbsp;" onClick="add()">&nbsp;&nbsp;
        </c:if>
        <input type="button" class="button_class" id="sure1"
            value="&nbsp;&nbsp;确 定&nbsp;&nbsp;" onClick="sures()" id="sures">&nbsp;&nbsp;<input type="button" class="button_class"
            value="&nbsp;&nbsp;关 闭&nbsp;&nbsp;" onClick="closesd()" id="clo">&nbsp;&nbsp;
		</div>
	</p:button>

	<p:message />

</p:body></form>
</body>
</html>

