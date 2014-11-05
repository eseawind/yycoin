<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="销售列表" cal="true"/>
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
	var opener = window.common.opener();
	
	var oo = getCheckBox("beans");
	
	if (oo && oo.length == 0)
	{
		alert('请选择销售单');
		return;
	}
	
	if (oo)
    opener.getCanSwatch(oo);
    
    closes();
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

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../sail/out.do" method="post"><input
	type="hidden" name="method" value="rptCanSwatch">
<input type="hidden" value="1" name="load"> 
<input type="hidden" value="${stafferId}" name="stafferId"> 
<input type="hidden" value="${customerId}" name="customerId"> 
<input type="hidden" value="${mode}" name="mode"> 
<input type="hidden" value="${selectMode}" name="selectMode"> 

<p:navigation
	height="22">
	<td width="550" class="navigation">销售管理</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">
	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr class="content2">
				<td width="15%" align="center">产品名称</td>
				<td align="center"><input type="text" name="name"></td>
				<td width="15%" align="center">销售单号</td>
				<td align="center"><input type="text" name="fullId"></td>
			</tr>

			<tr class="content1">
				<td colspan="4" align="right"><input type="button"
					onclick="querys()" class="button_class"
					value="&nbsp;&nbsp;查 询&nbsp;&nbsp;"></td>
		</table>

	</p:subBody>

	<p:title>
		<td class="caption"><strong>销售列表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center" width="5%">选择</td>
				<td align="center" width="15%"><strong>单号</strong></td>
				<td align="center" width="20%"><strong>产品</strong></td>
				<td align="center" width="10%"><strong>数量</strong></td>
				<td align="center" width="10%"><strong>可转数量</strong></td>
				<td align="center" width="10%"><strong>已转/退数量</strong></td>
				<td align="center" width="10%"><strong>销售价</strong></td>
			</tr>

			<c:forEach items="${baseList}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type=${selectMode == 1 ? 'checkbox' : 'radio'} name="beans" pvalue="${item.id}" 
					poutid="${item.outId}"
					pproductName="${item.productName}"
					pamount="${item.amount}"
					pinway="${item.inway}"
					pprice="${my:formatNum(item.price)}"
					plast="${item.amount -item.inway}"
					></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.outId}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.productName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.amount}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.amount -item.inway}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.inway}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.price)}</td>
				</tr>
			</c:forEach>
		</table>
			
		<p:formTurning form="formEntry" method="rptCanSwatch"></p:formTurning>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="95%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			id="adds" style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="sures()"></div>
	</p:button>

	<p:message />

</p:body></form>
</body>
</html>

