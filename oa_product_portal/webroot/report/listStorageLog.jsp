<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="盘点列表" />
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/tableSort.js"></script>
<script language="javascript">

</script>

</head>
<body class="body_class">
<form> <p:navigation
	height="22">
	<td width="550" class="navigation">盘点管理 &gt;&gt; 日志列表</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>日志列表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr align=center class="content0">
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>流水号</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>仓区</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>储位</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>产品</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>前数量</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>异动数量</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>后数量</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>操作类型</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>操作人员</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>单据</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>时间</strong></td>
			</tr>

			<c:forEach items="${listStorageLog}" var="item"
				varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center" onclick="hrefAndSelect(this)">${item.serializeId}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.depotpartName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.storageName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.productName}</td>
					
					<td align="center" onclick="hrefAndSelect(this)">${item.preAmount}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.changeAmount}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.afterAmount}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('storageType', item.type)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.user}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.description}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.logTime}</td>
				</tr>
			</c:forEach>
		</table>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="97%" rightWidth="3%">
		<div align="right"><input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;返 回&nbsp;&nbsp;">
		</div>
	</p:button>

	<p:message />

</p:body></form>
</body>
</html>

