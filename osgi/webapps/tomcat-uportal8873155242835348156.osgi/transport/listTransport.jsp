<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="运输列表" />
<script src="../js/common.js"></script>
<script src="../js/tableSort.js"></script>
<script language="javascript">
function addApplys()
{
	transportForm.submit();
}

function dels()
{
	if (getRadioValue('transports') == '')
	{
		alert('请选择运输');
		return;
	}
	
	if (window.confirm('确定删除此运输方式?'))
	$l('../sail/transport.do?method=delTransport&id=' + getRadioValue('transports'));
}

function updateApplys()
{
	if (getRadioValue('transports') == '')
	{
		alert('请选择运输');
		return;
	}
	
	$l('../sail/transport.do?method=findTransport&id=' + getRadioValue('transports'));
}

</script>

</head>
<body class="body_class">
<form name="transportForm" action="../sail/transport.do"><input
	type="hidden" name="method" value="preForAddTransport"> <p:navigation
	height="22">
	<td width="550" class="navigation">运输管理  &gt;&gt; 浏览运输</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>运输列表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr align=center class="content0">
				<td align="center" class="td_class">选择</td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="15%"><strong>运输名称</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>所属分类</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>公司</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>联系电话</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>发货区域</strong></td>
			</tr>

			<c:forEach items="${transportList}" var="item" varStatus="vs">
				<tr class="${s.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type="radio" name="transports"
						value="${item.id}" ${vs.index== 0 ? "checked" : ""}/></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.name}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.parent}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.company}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.conphone}</td>
					<td align="center" onclick="hrefAndSelect(this)" title="${item.area}">${my:truncateString(item.area, 0, 30)}</td>
				</tr>
			</c:forEach>
		</table>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			style="cursor: pointer" value="&nbsp;&nbsp;增加运输&nbsp;&nbsp;"
			onclick="addApplys()">&nbsp;&nbsp;
			<input type="button" class="button_class"
			style="cursor: pointer" value="&nbsp;&nbsp;修改运输&nbsp;&nbsp;"
			onclick="updateApplys()">&nbsp;&nbsp;
			<input type="button"
			class="button_class" value="&nbsp;&nbsp;删除运输&nbsp;&nbsp;"
			onclick="dels()"></div>
	</p:button>

	<p:message />
	
</p:body></form>
</body>
</html>

