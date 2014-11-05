<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="采购付款" />
<script src="../js/title_div.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/tableSort.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script language="javascript">

function load()
{
	loadForm();
}

</script>

</head>
<body class="body_class" onkeypress="tooltip.bingEsc(event)" onload="load()">
<form action="" name="adminForm">

<table width="98%" border="0" cellpadding="0" cellspacing="0"
	align="center">

	<tr>
		<td align='center' colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id="mainTable">
					<tr align="center" class="content0">
						<td align="center" onclick="tableSort(this)" class="td_class">标识</td>
						<td align="center" onclick="tableSort(this)" class="td_class">采购人</td>
						<td align="center" onclick="tableSort(this)" class="td_class">状态</td>
						<td align="center" onclick="tableSort(this)" class="td_class">供应商</td>
						<td align="center" onclick="tableSort(this)" class="td_class">金额</td>
						<td align="center" onclick="tableSort(this)" class="td_class">最早付款</td>
						<td align="center" onclick="tableSort(this)" class="td_class">时间</td>
					</tr>

					<c:forEach items="${warnCEOList}" var="item" varStatus="vs">
						<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
							<td align="center">
							<a onclick="hrefAndSelect(this)" href="../finance/stock.do?method=findStockPayApply&id=${item.id}&update=1&mode=1">
							${item.id}</a></td>
							<td align="center">${item.stafferName}</td>
							<td align="center">${my:get('stockPayApplyStatus', item.status)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.provideName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.moneys)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.payDate}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.logTime}</td>
						</tr>
					</c:forEach>
					
					<c:forEach items="${warnCEOList2}" var="item" varStatus="vs">
						<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
							<td align="center">
							<a onclick="hrefAndSelect(this)" href="../finance/stock.do?method=findStockPrePayApply&id=${item.id}&update=1&mode=1">
							${item.id}</a></td>
							<td align="center">${item.stafferName}</td>
							<td align="center">${my:get('stockPayApplyStatus', item.status)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.providerName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.moneys)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.payDate}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.logTime}</td>
						</tr>
					</c:forEach>
				</table>

				</td>
			</tr>
		</table>
		</td>
	</tr>

</table>

</form>
</body>
</html>
