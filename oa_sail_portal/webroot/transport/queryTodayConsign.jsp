<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="今天到货发货单列表" />
<script src="../js/common.js"></script>
<script src="../js/tableSort.js"></script>
<script language="javascript">

function load()
{
	loadForm();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../sail/transport.do"><input
	type="hidden" name="method" value="queryConsign">

<p:body width="100%">

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr align=center class="content0">
				<td align="center" class="td_class">选择</td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>单据时间</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>单据</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>货单状态</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>到货时间</strong></td>
			</tr>
			
			<c:forEach items="${consignList}" var="item"
				varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type="radio" name="consigns"
						statuss="${item.currentStatus}" value="${item.fullId}"
						${vs.index== 0 ? "checked" : ""}/></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.outTime}</td>
					<td align="center" onclick="hrefAndSelect(this)"><a
						href="../sail/transport.do?method=findConsign&fullId=${item.fullId}"
						>${item.fullId}</a></td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('consignStatus', item.currentStatus)}</td>
					
					<td align="center" onclick="hrefAndSelect(this)">${item.arriveDate}</td>
				</tr>
			</c:forEach>
		</table>

	</p:subBody>

</p:body></form>
</body>
</html>

