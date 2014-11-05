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
    opener.getCustomer1(oo);
    
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

function querySelfPublic()
{
    $O('method').value = 'rptQuerySelfPublicClient1';
    formEntry.submit();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../client/client.do" method="post"><input
	type="hidden" name="method" value="rptQuerySelfClient1">
<input type="hidden" value="1" name="load"> 
<input type="hidden" value="${stafferId}" name="stafferId"> 
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
				<td align="center" width="15%"><strong>名称</strong></td>
				<td align="center" width="15%"><strong>编码</strong></td>
				
				<td align="center" width="10%"><strong>信用分</strong></td>
				<td align="center" width="10%"><strong>信用等级</strong></td>
				<td align="center" width="10%"><strong>欠款金额</strong></td>
				<td align="center" width="10%"><strong>剩余信用</strong></td>
			</tr>

			<c:forEach items="${list}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type="radio" name="beans" pval="${my:formatNum(item.creditVal)}"
					    plever="1"
					    pstype="0"
					    pconnector="${item.connector}"
					    phandphone="${item.handphone}"
					    pcreditlevelid="${item.creditLevelId}"
						pname="${item.name}" value="${item.id}" ${vs.index== 0 ? "checked" : ""}/></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.name}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.code}</td>
					
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.creditVal)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.reserve1}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.reserve2}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.reserve3}</td>
				</tr>
			</c:forEach>
		</table>
			
		<p:formTurning form="formEntry" method="rptQuerySelfClient"></p:formTurning>

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

