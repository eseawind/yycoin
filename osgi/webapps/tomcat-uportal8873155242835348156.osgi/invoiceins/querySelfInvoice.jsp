<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="我的发票" />
<link href="../js/plugin/dialog/css/dialog.css" type="text/css" rel="stylesheet"/>
<script src="../js/title_div.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/math.js"></script>
<script src="../js/tableSort.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src="../js/adapter.js"></script>
<script language="javascript">
function query()
{
	adminForm.submit();
}

function load()
{
	loadForm();
}

// 查询 未关联发票的退货清单
function querForConfirmRetIns()
{
	var canUseMoney = getRadio("fullId").pmoney;

	if (parseFloat(canUseMoney) <= 0)
	{
		alert('可确认金额须为大于0才能操作');

		return false;
	} 
	
    if (getRadio('fullId'))
    {
        document.location.href = '../sail/out.do?method=queryForConfirmRetIns&invoiceId=' + getRadioValue("fullId") 
        + '&total=' + getRadio("fullId").pmoney  + '&invoiceName=' + getRadio("fullId").pinvoiceName
        + '&providerId=' + getRadio("fullId").pproviderId + '&providerName='+getRadio("fullId").pproviderName;
    }
    else
    {
        alert('不能操作!');
    }
}

// 未确认发票的相同发票类型的采购付款申请
function queryForConfirmStockPay()
{
	var canUseMoney = getRadio("fullId").pmoney;

	if (parseFloat(canUseMoney) <= 0)
	{
		alert('可确认金额须为大于0才能操作');

		return false;
	} 
	
    if (getRadio('fullId'))
    {
        document.location.href = '../sail/out.do?method=queryForConfirmOutIns&firstLoad=1&invoiceId=' + getRadioValue("fullId") 
        + '&total=' + getRadio("fullId").pmoney  + '&invoiceName=' + getRadio("fullId").pinvoiceName
        + '&providerId=' + getRadio("fullId").pproviderId + '&providerName='+getRadio("fullId").pproviderName;
    }
    else
    {
        alert('不能操作!');
    }
}

</script>

</head>
<body class="body_class" onload="load()">
<form action="../finance/invoiceins.do" name="adminForm"><input type="hidden"
	value="querySelfInvoice" name="method"> <input type="hidden" value="1"
	name="firstLoad">

<p:navigation
    height="22">
    <td width="550" class="navigation">发票确认&gt;&gt; 我的发票</td>
                <td width="85"></td>
</p:navigation> <br>

<table width="98%" border="0" cellpadding="0" cellspacing="0"
	align="center">
	
    <p:line flag="0" />

	<tr>
		<td align='center' colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id="mainTable">
					<tr align="center" class="content0">
						<td align="center" width="5%" align="center">选择</td>
						<td align="center" onclick="tableSort(this)" class="td_class">供应商</td>
						<td align="center" onclick="tableSort(this)" class="td_class">发票类型</td>
						<td align="center" onclick="tableSort(this)" class="td_class">数量</td>
						<td align="center" onclick="tableSort(this)" class="td_class">发票金额</td>
						<td align="center" onclick="tableSort(this)" class="td_class">可确认金额</td>
					</tr>

					<c:forEach items="${resultList}" var="item" varStatus="vs">
						<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'
						>
							<td align="center"><input type="radio" name="fullId" 
								value="${item.invoiceId}" pproviderId="${item.providerId}" pproviderName="${item.providerName}"
								pmoney="${item.mayConfirmMoneys}" pinvoiceName="${item.invoiceName}"
								${vs.index== 0 ? "checked" : ""}/></td>
							
							<td align="center" onclick="hrefAndSelect(this)">${item.providerName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.invoiceName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.invoiceAmount}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.moneys)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.mayConfirmMoneys)}</td>
						</tr>
					</c:forEach>
				</table>

				</td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>


	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<c:if test="${my:length(resultList) > 0}">
	<tr>
		<td width="100%">
		<div align="right">
		
        <input
            type="button" class="button_class"
            value="&nbsp;&nbsp;退货退票确认&nbsp;&nbsp;" onclick="querForConfirmRetIns()" />&nbsp;&nbsp; 
            <input
            type="button" class="button_class"
            value="&nbsp;&nbsp;采购进票确认&nbsp;&nbsp;" onclick="queryForConfirmStockPay()" />&nbsp;&nbsp;
        
		</div>
		
		</td>
		<td width="0%"></td>
	</tr>
	
	</c:if>

	<p:message2/>
</table>

</form>
</body>
</html>
