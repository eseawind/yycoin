<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="客户对比" guid="true"/>
<link href="../js/plugin/dialog/css/dialog.css" type="text/css"	rel="stylesheet" />
<LINK href="../css/tabs/jquery.tabs-ie.css" type=text/css rel=stylesheet>
<LINK href="../css/tabs/jquery.tabs.css" type=text/css rel=stylesheet>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/prototype.js"></script>
<script language="JavaScript" src="../js/buffalo.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<SCRIPT src="../js/jquery/jquery.tabs.js"></SCRIPT>
<script language="JavaScript" src="../client_js/client.js"></script>
<script language="javascript">

var cmap = window.top.topFrame.cmap;

var pList = window.top.topFrame.pList;

var updateCode = '${updateCode}';

function load()
{
    setOption($O('provinceId'), "", "--");
    for (var i = 0; i < pList.length; i++)
    {
        setOption($O('provinceId'), pList[i].id, pList[i].name);
    }

    $detail($O('viewTable'), ['ba','ba1','pa']);
    
    loadForm();
    
    //changes($O('cityId'));
    
    //loadForm();
    
    
    
    //testclip();
}

function passBean()
{
	$O("operation").value = "0";

	submit('确定通过客户变更信息?');
}

function rejectBean()
{
	$O("operation").value = "1";

	submit('确定驳回客户变更信息?');
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply" action="../client/client.do"><input
	type="hidden" name="method" value="processApply">
<input type="hidden" name="id" value="${bean.id}">
<input type="hidden" name="operation" value="0">
<input type="hidden" name="type" value="1">
	 <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">客户管理</span> &gt;&gt; 客户信息对比</td>
	<td width="85"></td>
</p:navigation> <br>

<table width="95%" border="0" cellpadding="0" cellspacing="0"
	align="center">
	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<c:if test="${bean.type==1}">
	<%@include file="compareIndividualClient.jsp"%>
	</c:if>
	<c:if test="${bean.type==2}">
	<%@include file="compareDepartClient.jsp"%>
	</c:if>
	<c:if test="${bean.type==3}">
	<%@include file="compareCorporationClient.jsp"%>
	</c:if>
	
	<%@include file="compareClientContact.jsp"%>
	
	<%@include file="compareClientBusiness.jsp"%>
	
	<%@include file="compareClientAddress.jsp"%>

	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td width="100%">
		<div align="right">
	        <input
	            type="button" name="ba" class="button_class"
	            onclick="passBean()"
	            value="&nbsp;&nbsp;通过&nbsp;&nbsp;">&nbsp;&nbsp;
	        
	        <input
	            type="button" name="ba1" class="button_class"
	            onclick="rejectBean()"
	            value="&nbsp;&nbsp;驳回&nbsp;&nbsp;">&nbsp;&nbsp;
	        
	        <input type="button" name="pa" class="button_class"
	            style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
	            onclick="javascript:history.go(-1)">
	     </div>
		</td>
		<td width="0%"></td>
	</tr>

</table>

</form>
</body>
</html>

