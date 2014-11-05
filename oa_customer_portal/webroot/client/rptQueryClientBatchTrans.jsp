<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="客户批量转移列表" />
<base target="_self">
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function querys()
{	
	formEntry.submit();
}

var flag = true;
function sures()
{
	//add();
	$O('type').value = '0';
    
    $O('method').value = 'customerBatchTrans';

    $Dbuttons(true);
    
    formEntry.submit();

    //closes();
}

function reject()
{
	$O('type').value = '1';
    
    $O('method').value = 'customerBatchTrans';

    $Dbuttons(true);
    
    formEntry.submit();
    
}

function add()
{
    var opener = window.common.opener();
    
    var oo = getCheckBox("beans");
    
    if (oo && oo.length == 0)
    {
        alert('请选择促销规则');
        flag = false;
        return;
    }
    
    if (oo)
    opener.getProm(oo);
}

function closes()
{
	var opener = window.common.opener();
	
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

function closesd()
{
    //var opener = window.common.opener();

    //opener = null;
    //window.close();

	document.location.href = '../client/queryCanAssignClient.jsp';
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../client/client.do" method="post"><input
	type="hidden" name="method" value="rptQueryClientBatchTrans"><input
	type="hidden" value="1" name="load">
<input type="hidden" value="${selectMode}" name="selectMode">
<input type="hidden" value="" name="type">

<p:navigation
	height="22">
	<td width="550" class="navigation">批量接受客户管理</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>客户批量转移列表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center">客户编码</td>
				<td align="center">客户名称</td>
				<td align="center">回收业务员</td>
				<td align="center">接受业务员</td>				
			</tr>

			<c:forEach items="${beanList}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">					
					<td align="center" onclick="hrefAndSelect(this)">${item.customerCode}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.customerName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.stafferName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.stafferName1}</td>					
				</tr>
			</c:forEach>
		</table>
			
		<p:formTurning form="formEntry" method="rptQueryClientBatchTrans"></p:formTurning>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		<input type="button" class="button_class"
            value="&nbsp;&nbsp;驳 回&nbsp;&nbsp;" onClick="reject()" id="rej">&nbsp;&nbsp;
        <input type="button" class="button_class" id="sure1"
            value="&nbsp;&nbsp;确 定&nbsp;&nbsp;" onClick="sures()" id="sures">&nbsp;&nbsp;<input type="button" class="button_class"
            value="&nbsp;&nbsp;关 闭&nbsp;&nbsp;" onClick="closesd()" id="clo">&nbsp;&nbsp;
		</div>
	</p:button>

	<p:message2 />

</p:body></form>
</body>
</html>

