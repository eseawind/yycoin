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
		return;
	}
	
	if (oo)
    opener.setinvocurrentobjVal(oo);
    
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
	<p:title>
		<td class="caption"><strong>查询列表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center" width="11%"><strong>单据ID</strong></td>
				<td align="center" width="11%"><strong>任务名称</strong></td>
				<td align="center" width="9%"><strong>任务类型</strong></td>
				<td align="center" width="5%"><strong>责任人</strong></td>
				<td align="center" width="9%"><strong>计划完成时间</strong></td>
				<td align="center" width="9%"><strong>实际完成时间</strong></td>
				<td align="center" width="7%"><strong>交付类型</strong></td>
				<td align="center" width="7%"><strong>交付物</strong></td>
				<td align="center" width="8%"><strong>交付物数量</strong></td>
				<td align="center" width="5%"><strong>接收人</strong></td>
				<td align="center" width="8%"><strong>任务状态</strong></td>
				<td align="center" width="8%"><strong>任务阶段</strong></td>
			</tr>

			<c:forEach items="${beanList}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center" onclick="hrefAndSelect(this)"><a href="../project/project.do?method=findTask&id=${item.id}" >${item.id}</a></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.taskName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.taskType}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.dutyName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.planFinishTime}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.realFinishiTime}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('212', item.transType)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('214', item.transObj)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.transObjCount}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.receiverName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('taskStatus', item.taskStatus)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('taskStage', item.taskStage)}</td>

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

