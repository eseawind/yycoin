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
    opener.getOut(oo);
    
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
	type="hidden" name="method" value="rptQueryOutForInvoice">
<input type="hidden" value="1" name="load"> 
<input type="hidden" value="${bad}" name="bad"> 
<input type="hidden" value="${stafferId}" name="stafferId"> 
<input type="hidden" value="${dutyId}" name="dutyId">
<input type="hidden" value="${dutyId2}" name="dutyId2"> 
<input type="hidden" value="${invoiceId}" name="invoiceId"> 
<input type="hidden" value="${invoiceId2}" name="invoiceId2"> 
<input type="hidden" value="${customerId}" name="customerId"> 
<input type="hidden" value="${mode}" name="mode"> 
<input type="hidden" value="${selectMode}" name="selectMode"> 
<input type="hidden" value="${invoiceStatus}" name="invoiceStatus">
 <input type="hidden" value="${nopay}" name="nopay">

<p:navigation
	height="22">
	<td width="550" class="navigation">销售管理</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">
	<p:subBody width="100%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr class="content1">
                        <td width="15%" align="center">开始时间</td>
                        <td align="center" width="35%"><p:plugin name="outTime" size="20" value="${outTime}"/></td>
                        <td width="15%" align="center">结束时间</td>
                        <td align="center"><p:plugin name="outTime1" size="20" value="${outTime1}"/>
                        </td>
            </tr>
            
			<tr class="content2">
				<td width="15%" align="center">销售单号</td>
				<td align="center"><input type="text" name="fullId" onkeypress="press()"
					value="${fullId}"></td>
				<td width="15%" align="center">客户</td>
				<td align="center"><input type="text" name="customerName" onkeypress="press()"
					value="${customerName}"></td>
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

	<p:subBody width="100%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center" width="5%">选择</td>
				<td align="center" width="12%"><strong>单号</strong></td>
				<td align="center" width="9%"><strong>客户</strong></td>
				<td align="center" width="8%"><strong>总金额</strong></td>
				<td align="center" width="8%"><strong>已支付</strong></td>
				<td align="center" width="15%"><strong>回款来源</strong></td>					
				<td align="center" width="8%"><strong>折扣金额</strong></td>
				<td align="center" width="8%"><strong>退货金额</strong></td>
				<td align="center" width="5%"><strong>状态</strong></td>
				<td align="center" width="8%"><strong>已开票</strong></td>
				<td align="center" width="8%"><strong>可开票</strong></td>
				<td align="center" width="10%"><strong>管理类型</strong></td>
			</tr>

			<c:forEach items="${list}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type=${selectMode == 1 ? 'checkbox' : 'radio'} name="beans" value="${item.fullId}" 
					ptotal="${my:formatNum(item.total)}"
					pinvoicemoney="${my:formatNum(item.mayInvoiceMoneys)}"
					plast="${my:formatNum(item.total -item.hadPay - item.badDebts - item.promValue)}"
					pbad="${my:formatNum(item.badDebts)}"
					></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.fullId}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.customerName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.total)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.hadPay)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.description}</td>					
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.promValue)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.retTotal)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('outStatus', item.status)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.invoiceMoney)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.mayInvoiceMoneys)}</td>					
					<td align="center" onclick="hrefAndSelect(this)">${my:get('pubManagerType', item.mtype)}</td>
				</tr>
			</c:forEach>
		</table>
			
		<p:formTurning form="formEntry" method="rptQueryOutForInvoice"></p:formTurning>

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

