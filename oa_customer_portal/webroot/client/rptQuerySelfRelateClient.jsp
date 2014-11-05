<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="客户列表" />
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
	
	var oo = getRadio("beans");
	
	if (oo && oo.length == 0)
	{
		alert('请选择客户');
		return;
	}
	
	if (oo)
    opener.getRelateCustomer(oo);
    
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
<form name="formEntry" action="../client/client.do" method="post"><input
	type="hidden" name="method" value="rptQuerySelfRelateClient">
<input type="hidden" value="1" name="load"> 
<input type="hidden" value="${stafferId}" name="stafferId"> 
<input type="hidden" value="${stype}" name="stype">
<p:navigation
	height="22">
	<td width="550" class="navigation">客户管理</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">
	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr class="content1">
				<td width="15%" align="center">客户名称</td>
				<td align="center"><input type="text" name="name" onkeypress="press()"
					value="${name}"></td>
				<td width="15%" align="center">客户编码</td>
				<td align="center"><input type="text" name="code" onkeypress="press()"
					value="${code}"></td>
			</tr>

			<tr class="content1">
				<td colspan="4" align="right">
				<!--  
				<input type="button"
                    onclick="querySelfPublic()" class="button_class"
                    value="&nbsp;&nbsp;公共客户&nbsp;&nbsp;">
                -->
				&nbsp;&nbsp;<input type="button"
					onclick="querys()" class="button_class"
					value="&nbsp;&nbsp;查 询&nbsp;&nbsp;"></td>
		</table>

	</p:subBody>

	<p:title>
		<td class="caption"><strong>客户列表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center" width="5%">选择</td>
				<td align="center" width="20%"><strong>名称</strong></td>
				<td align="center" width="15%"><strong>编码</strong></td>
				
				<td align="center" width="20%"><strong>部门客户</strong></td>
				<td align="center" width="20%"><strong>组织客户</strong></td>
			</tr>

			<c:forEach items="${list}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type="radio" name="beans" pstype="${item.type}"
					    prefDepartCustId="${item.refDepartCustId}"
					    prefDepartCustName="${item.refDepartCustName}"
					   	prefCorpCustId="${item.refCorpCustId}"
					    prefCorpCustName="${item.refCorpCustName}"
						pname="${item.name}" value="${item.id}" ${vs.index== 0 ? "checked" : ""}/></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.name}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.code}</td>
					
					<td align="center" onclick="hrefAndSelect(this)">${item.refDepartCustName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.refCorpCustName}</td>
				</tr>
			</c:forEach>
		</table>
			
		<p:formTurning form="formEntry" method="rptQuerySelfRelateClient"></p:formTurning>

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

