<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="委托代销清单列表" cal="true"/>
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
    opener.getOutBalance(oo);
    
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
	type="hidden" name="method" value="rptQueryOutBalance">
<input type="hidden" value="1" name="load"> 
<input type="hidden" value="${stafferId}" name="stafferId"> 
<input type="hidden" value="${dutyId}" name="dutyId"> 
<input type="hidden" value="${invoiceId}" name="invoiceId"> 
<input type="hidden" value="${invoiceId2}" name="invoiceId2"> 
<input type="hidden" value="${customerId}" name="customerId"> 
<input type="hidden" value="${selectMode}" name="selectMode"> 
<input type="hidden" value="${type}" name="type"> 
<input type="hidden" value="${invoiceStatus}" name="invoiceStatus"> 
<input type="hidden" value="${pay}" name="pay"> 
<input type="hidden" value="${nopay}" name="nopay">
<input type="hidden" value="${hasRebate}" name="hasRebate">
<input type="hidden" value="${mtype}" name="mtype">

<p:navigation
	height="22">
	<td width="550" class="navigation">销售管理</td>
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
				<td width="15%" align="center">销售单号</td>
				<td align="center" colspan="1"><input type="text" name="outId" onkeypress="press()"
					value="${outId}"></td>
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
		<td class="caption"><strong>委托代销列表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center" width="5%">选择</td>
				<td align="center" width="15%"><strong>销售单号</strong></td>
				<td align="center" width="10%"><strong>客户</strong></td>
				<td align="center" width="10%"><strong>结算金额</strong></td>
				<td align="center" width="10%"><strong>已付款</strong></td>
				<td align="center" width="10%"><strong>已退款</strong></td>
				<td align="center" width="10%"><strong>状态</strong></td>
				<td align="center" width="5%"><strong>发票</strong></td>
				<td align="center" width="5%"><strong>已开票</strong></td>
				<td align="center" width="10%"><strong>管理类型</strong></td>
			</tr>

			<c:forEach items="${list}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type=${selectMode == 1 ? 'checkbox' : 'radio'} name="beans" value="${item.id}" 
					ptotal="${my:formatNum(item.total)}"
					pmtype="${item.mtype}"
					plast="${my:formatNum(item.total - item.payMoney - item.refMoneys)}"
					pinvoicemoney="${my:formatNum(item.total - item.invoiceMoney - item.refMoneys)}"
					></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.outId}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.customerName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.total)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.payMoney)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.refMoneys)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('outBalanceStatus', item.status)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('invoiceStatus', item.invoiceStatus)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.invoiceMoney)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('pubManagerType', item.mtype)}</td>
				</tr>
			</c:forEach>
		</table>
			
		<p:formTurning form="formEntry" method="rptQueryOutBalance"></p:formTurning>

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

