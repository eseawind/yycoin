<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="开票异常明细" cal="true" guid="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/tableSort.js"></script>
<script language="javascript">
function exports()
{
	//document.location.href = '../finance/finance.do?method=exportFinanceItem';
}

function load()
{
	loadForm();
	
	bingTable("senfe");
}

function sub(type)
{
	if (type == '0')
	{
		$ajax('../finance/invoiceins.do?method=modifyStatus&id=' + "${id}" + '&status=0', callSucess);
		//$O('status').value = '0';
	}
	else
	{
		$O('status').value = '1';	
		
		submit(null, null, null);	
	}
	
}

function callSucess(data)
{	
	window.history.go(-1);
}


</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../finance/invoiceins.do" method="post">
<input type="hidden" name="method" value="modifyStatus">
<input type="hidden" name="status" value="">
<input type="hidden" name="id" value="${id}">
<input type="hidden" name="mode" value="${mode}">

<p:navigation
	height="22">
	<td width="550" class="navigation">开票异常明细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>开票申请中不合规信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0" id="senfe">
			<tr align=center class="content0">
				<td align="center" width="12%" class="td_class" onclick="tableSort(this)"><strong>开票单号</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>异常信息</strong></td>	
			</tr>

			<c:forEach items="${expList}" var="item"
				varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="left">${item.parentId}</td>
					<td align="left">${item.exception}</td>
				</tr>
			</c:forEach>
		</table>
		
		<p:formTurning form="formEntry" method="modifyStatus"></p:formTurning>

	</p:subBody>

	<p:line flag="1" />
	
	<p:button leftWidth="100%" rightWidth="0%">
        <div align="right"><input type="button" class="button_class"
            id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
            onclick="sub(0)">
            <input type="button" class="button_class" id="sub_b"
			value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onClick="sub(1)" />
            </div>
    </p:button>
	
	<p:message />
	
</p:body>
</form>
</body>
</html>

