<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="任务列表" />
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
		alert('请选择任务');
		return false;
	}
	
	if (oo)
    opener.setCurTaskobjVal(oo);
    
    closes();
}

function getCheckBoxs(name)
{
    var arr = new Array();
    var k =0;
    var obj = document.getElementsByName(name);
    for (var i = 0; i < obj.length; i++)
    {
        if (obj[i].checked)
        {
            arr[k++] = obj[i];
        }
    }

    return arr;
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
    $O('method').value = 'rptQuerySelfPublicCustomer';
    formEntry.submit();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../project/project.do" method="post"><input
	type="hidden" name="method" value="selectpartyA">
<input type="hidden" value="1" name="load"> 
<input type="hidden" value="${stafferId}" name="stafferId"> 
<p:navigation
	height="22">
	<td width="550" class="navigation">任务列表</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">
	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr class="content1">
				<td width="15%" align="center">名称</td>
				<td align="center" width="20%">
					<input type="text" name="selName" onkeypress="press()"
					value="">
					</td>
				<td width="15%" align="center">编码</td>
				<td align="center"><input type="text" name="taskCode" onkeypress="press()"
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
		<td class="caption"><strong>查询列表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center" width="5%">选择</td>
				<td align="center" width="15%"><strong>名称</strong></td>
				<td align="center" width="15%"><strong>编码</strong></td>
				
			</tr>

			<c:forEach items="${beanList}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type='radio' name="beans"					
					pname="${item.taskName}"  value="${item.id}"/></td>		
					<td align="left" onclick="hrefAndSelect(this)">${item.taskName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.id}</td>

				</tr>
				</c:forEach>
		</table>
			
		<p:formTurning form="formEntry" method="rptQuerySelfCustomer"></p:formTurning>

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

