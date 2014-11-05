<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="选择仓区" />
<base target="_self">
<script src="../js/public.js"></script>
<script src="../js/common.js"></script>
<script language="javascript">

function add()
{
	var opener = window.common.opener();
	
	var oo = getRadio("bean_inp");
	
	if (oo == null)
	{
		alert('请选择仓区');
		return;
	}
	
	opener.getDepotpart([oo]);
    
    opener = null;
    window.close();
}

function query()
{
	adminForm.submit();
}
</script>
</head>
<body class="body_class">
<form action="../depot/depotpart.do" name="adminForm"><input type="hidden"
	value="rptQueryDepotpart" name="method"> <input type="hidden"
	value="${depotId}" name="${depotId}">
<p:navigation
	height="22">
	<td width="550" class="navigation">仓区列表</td>
	<td width="85"></td>
</p:navigation>

<br>
<table width="85%" border="0" cellpadding="0" cellspacing="0"
	align="center">
	<tr>
		<td valign="top" colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<!--DWLayoutTable-->
			<tr>
				<td width="784" height="6"></td>
			</tr>
			<tr>
				<td align="center" valign="top">
				<div align="left">
				<table width="90%" border="0" cellspacing="2">
					<tr>
						<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="10">
							<tr>
								<td width="35">&nbsp;</td>
								<td width="6"><img src="../images/dot_r.gif" width="6"
									height="6"></td>
								<td class="caption"><strong>浏览仓区:<font color=blue>[当前查询数量:${my:length(beanList)}]</font> </strong></td>
								<td align="right"><input name="sure1" id="sure1"
									type="button" class="button_class" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
									onClick="add()"></td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>


	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td align='center' colspan='2'>
		<table width="85%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1'>
					<tr align="center" class="content0">
						<td align="center" width="8%" align="center">选择</td>
						<td align="center" onclick="tableSort(this)" class="td_class">仓区名称</td>
						<td align="center" onclick="tableSort(this)" class="td_class">仓区类型</td>
					</tr>

					<c:forEach items="${beanList}" var="item" varStatus="vs">
						<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
							<td align="center"><input type="radio" name="bean_inp" lname="${item.name}"
								 value="${item.id}" ${vs.index== 0 ? "checked" : ""}/></td>
							<td align="center" onclick="hrefAndSelect(this)">${item.name}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:get('depotpartType', item.type)}</td>
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
	
	<c:if test="${fn:length(beanList) > 0}">
	<tr>
		<td width="94%">
		<div align="right"><input type="button" class="button_class" name="sure2"
			value="&nbsp;&nbsp;确 定&nbsp;&nbsp;" onClick="add()">&nbsp;&nbsp;</div>
		</td>
		<td width="6%"></td>
	</tr>
	</c:if>
</table>

</form>
</body>
</html>
