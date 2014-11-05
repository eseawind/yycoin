<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="采购项列表" cal="true"/>
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
		alert('请选择单据');
		return;
	}
	
	if (oo)
    opener.getStockItem(oo);
    
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
<form name="formEntry" action="../stock/stock.do" method="post"><input
	type="hidden" name="method" value="rptQueryStockItem">
<input type="hidden" value="1" name="load"> 
<input type="hidden" value="${providerId}" name="providerId"> 
<input type="hidden" value="${selectMode}" name="selectMode"> 

<p:navigation
	height="22">
	<td width="550" class="navigation">采购项管理</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">
	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr class="content1">
                        <td width="15%" align="center">开始时间</td>
                        <td align="center" width="35%"><p:plugin name="alogTime" size="20" value="${alogTime}" type="1"/></td>
                        <td width="15%" align="center">结束时间</td>
                        <td align="center"><p:plugin name="blogTime" size="20" value="${blogTime}" type="1"/>
                        </td>
            </tr>
            
			<tr class="content2">
				<td width="15%" align="center">采购单号</td>
				<td align="center" colspan="1"><input type="text" name="stockId" onkeypress="press()"
					value="${stockId}"></td>
				<td></td>
				<td></td>
			</tr>

			<tr class="content1">
				<td colspan="4" align="right"><input type="button"
					onclick="querys()" class="button_class"
					value="&nbsp;&nbsp;查 询&nbsp;&nbsp;"></td>
		</table>

	</p:subBody>

	<p:title>
		<td class="caption"><strong>采购项列表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center" width="5%">选择</td>
				<td align="center" width="15%"><strong>采购单号</strong></td>
				<td align="center" width="15%"><strong>采购项</strong></td>
				<td align="center" width="10%"><strong>供应商</strong></td>
				<td align="center" width="10%"><strong>产品</strong></td>
				<td align="center" width="10%"><strong>采购价</strong></td>
				<td align="center" width="10%"><strong>数量</strong></td>
				<td align="center" width="10%"><strong>总金额</strong></td>
				<td align="center" width="10%"><strong>时间</strong></td>
			</tr>

			<c:forEach items="${list}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type=${selectMode == 1 ? 'checkbox' : 'radio'} 
					name="beans" 
					value="${item.id}" 
					pid="${item.stockId}"
					ptotal="${my:formatNum(item.total)}"
					></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.stockId}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.id}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.providerName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.productName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.price)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.amount}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.total)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.stockTime}</td>
				</tr>
			</c:forEach>
		</table>
			
		<p:formTurning form="formEntry" method="rptQueryStockItem"></p:formTurning>

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

