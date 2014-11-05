<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="回款销售单" />
<script src="../js/title_div.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/tableSort.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script language="javascript">

var jmap = new Object();
<c:forEach items="${listOut1}" var="item">
	jmap['${item.fullId}'] = "${divMap[item.fullId]}";
</c:forEach>

function showDiv(id)
{
	tooltip.showTable(jmap[id]);
}

function load()
{
	loadForm();
	
	tooltip.init();
	
	highlights($("#mainTable").get(0), ['驳回'], 'red');
}

</script>

</head>
<body class="body_class" onkeypress="tooltip.bingEsc(event)" onload="load()">
<form action="../sail/out.do" name="adminForm">
<c:set var="fg" value='销售'/>

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
						<td align="center" width="5%" align="center">选择</td>
						<td align="center" onclick="tableSort(this)" class="td_class">单据编号</td>
						<td align="center" onclick="tableSort(this)" class="td_class">客户</td>
						<td align="center" onclick="tableSort(this)" class="td_class">状态</td>
						<td align="center" onclick="tableSort(this)" class="td_class">${fg}类型</td>
						<td align="center" onclick="tableSort(this)" class="td_class">${fg}时间</td>
						<td align="center" onclick="tableSort(this)" class="td_class">回款日期</td>
						<td align="center" onclick="tableSort(this)" class="td_class">金额</td>
						<td align="center" onclick="tableSort(this)" class="td_class">付款</td>
						<td align="center" onclick="tableSort(this)" class="td_class">${fg}人</td>
						<td align="center" onclick="tableSort(this)" class="td_class">仓库</td>
						<td align="center" onclick="tableSort(this)" class="td_class">超期(天)</td>
					</tr>

					<c:forEach items="${listOut1}" var="item" varStatus="vs">
						<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'
						>
							<td align="center"><input type="radio" name="fullId" 
							    temptype="${item.tempType}"
							    outtype="${item.outType}"
								statuss='${item.status}' 
								value="${item.fullId}" ${vs.index== 0 ? "checked" : ""}/></td>
							<td align="center"
							onMouseOver="showDiv('${item.fullId}')" onmousemove="tooltip.move()" onmouseout="tooltip.hide()"><a onclick="hrefAndSelect(this)" href="../sail/out.do?method=findOut&fow=99&outId=${item.fullId}">${item.mark ? "<font color=blue><B>" : ""}
							${item.fullId} ${item.mark ? "</B></font>" : ""}</a></td>
							<td align="center">${item.customerName}</td>
							<td align="center">${my:get('outStatus', item.status)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:get('outType_out', item.outType)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.outTime}</td>
							<c:if test="${item.pay == 0}">
							<td align="center" onclick="hrefAndSelect(this)"><font color=red>${item.redate}</font></td>
							</c:if>
							<c:if test="${item.pay == 1}">
							<td align="center" onclick="hrefAndSelect(this)"><font color=blue>${item.redate}</font></td>
							</c:if>
							<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.total)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.hadPay)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.stafferName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.depotName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${overDayMap[item.fullId]}</td>
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
