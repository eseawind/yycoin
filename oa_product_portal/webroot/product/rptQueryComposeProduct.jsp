<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="产品列表" />
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
        alert('请选择产品');
        return;
    }
    
    if (oo)
    opener.getComposeProduct(oo);
}

function closes()
{
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

function pop(obj)
{
	document.location.href = obj.href;
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../product/product.do" method="post"><input
	type="hidden" name="method" value="rptQueryComposeProduct"><input
	type="hidden" value="1" name="load"><input
    type="hidden" value="${selectMode}" name="selectMode"> 
<input type="hidden" value="${abstractType}" name="abstractType">
<input type="hidden" value="${ctype}" name="ctype">
<input type="hidden" value="${status}" name="status">
<input type="hidden" value="${mtype}" name="mtype">
<input type="hidden" value="${flag }" name = "flag" />
<input type="hidden" value="${stock}" name = "stock" />

<p:navigation
	height="22">
	<td width="550" class="navigation">产品管理</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>产品列表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center">选择</td>
				<td align="center" class="td_class"><strong>名称</strong></td>
				<td align="center" class="td_class"><strong>编码</strong></td>
				<td align="center" class="td_class"><strong>管理</strong></td>
				<td align="center" class="td_class"><strong>分类</strong></td>
			</tr>

			<c:forEach items="${beanList}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type='${selectMode == 1 ? "radio" : "checkbox"}' checked name="beans"
					psailprice="${my:formatNum(item.sailPrice)}" pnature="${item.ptype}" pname="${item.name}" 
					pmtype="${item.reserve4}" poldproduct="${item.consumeInDay}" 
					pcode="${item.code}" value="${item.id}"/>
					</td>
					<td align="center" onclick="hrefAndSelect(this)" ondblclick="sures()">${item.name}</td>
					<td align="center" onclick="hrefAndSelect(this)" ondblclick="sures()">${item.code}</td>
					<td align="center" onclick="hrefAndSelect(this)" ondblclick="sures()">${my:get2('pubManagerType', item.reserve4)}</td>
					<td align="center" onclick="hrefAndSelect(this)" ondblclick="sures()">${my:get2('productType', item.type)}</td>
				</tr>
			</c:forEach>
		</table>
			
		<!--<p:formTurning form="formEntry" method="rptQueryProduct"></p:formTurning>-->

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

