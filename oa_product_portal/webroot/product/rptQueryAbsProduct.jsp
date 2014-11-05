<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="虚拟产品列表" />
<base target="_self">
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="javascript">

var voMap = JSON.parse('${mapStr}');

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
    
    var subList = voMap[oo[0].value];
    
    opener.getProductAbs(subList);
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
	window.open(obj.href);
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../product/product.do" method="post"><input
	type="hidden" name="method" value="rptQueryAbsProduct"><input
	type="hidden" value="1" name="load"><input
    type="hidden" value="1" name="selectMode"> 

<p:navigation
	height="22">
	<td width="550" class="navigation">产品管理</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">
	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr class="content1">
				<td width="15%" align="center">产品名称</td>
				<td align="center"><input type="text" name="name" onkeypress="press()"
					value="${name}"></td>
				<td width="15%" align="center">产品编码</td>
				<td align="center"><input type="text" name="code" onkeypress="press()"
					value="${code}"></td>
			</tr>

			<tr class="content1">
				<td colspan="4" align="right"><input type="button"
					onclick="querys()" class="button_class"
					value="&nbsp;&nbsp;查 询&nbsp;&nbsp;"></td>
		</table>

	</p:subBody>

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
				<td align="center" class="td_class"><strong>组合产品</strong></td>
			</tr>

			<c:forEach items="${beanList}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type='${selectMode == 1 ? "radio" : "radio"}' name="beans"
						pname="${item.name}" pcode="${item.code}" value="${item.id}"/></td>
					<td align="center" onclick="hrefAndSelect(this)" ondblclick="sures()">${item.name}</td>
					<td align="center" onclick="hrefAndSelect(this)" ondblclick="sures()">${item.code}</td>
					<td align="center" onclick="hrefAndSelect(this)" ondblclick="sures()">
					<font color="${vs.index % 2 == 0 ? 'blue' : ''}">
					${item.reserve1}
					</font>
					</td>
				</tr>
			</c:forEach>
		</table>
			
		<p:formTurning form="formEntry" method="rptQueryAbsProduct"></p:formTurning>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
        <input type="button" class="button_class" id="sure1"
            value="&nbsp;&nbsp;确 定&nbsp;&nbsp;" onClick="sures()" id="sures">&nbsp;&nbsp;<input type="button" class="button_class"
            value="&nbsp;&nbsp;关 闭&nbsp;&nbsp;" onClick="closesd()" id="clo">&nbsp;&nbsp;
		</div>
	</p:button>

	<p:message2 />

</p:body></form>
</body>
</html>

