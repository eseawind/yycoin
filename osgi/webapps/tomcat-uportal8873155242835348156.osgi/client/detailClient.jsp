<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<HTML>
<head>
<p:link title="查看客户" guid="true"/>
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

function load()
{
    $('#container-1').tabs();
    
    //显示container-1
    $('#container-1').css({display:"block"});

    //initDialog();
	$detail($O('viewTable'), ['ba']);

    loadForm();
}

</SCRIPT>
</HEAD>
<BODY class="body_class" onload="load()">
<form name="addApply" action="../client/client.do" method="post">
	<input type="hidden" name="type" value="${bean.type}">
		
	<p:navigation height="22">
		<td width="550" class="navigation"><span
			style="cursor: pointer;" onclick="javascript:history.go(-1)">客户管理</span>	&gt;&gt; 客户明细</td>
		<td width="85"></td>
	</p:navigation>

	<p:body width="100%">

	<p:title>
		<c:if test="${bean.type==1}">
		<td class="caption"><strong>【个人】客户信息：</strong></td>
		</c:if>
		<c:if test="${bean.type==2}">
		<td class="caption"><strong>【部门】客户信息：</strong></td>
		</c:if>
		<c:if test="${bean.type==3}">
		<td class="caption"><strong>【企业】客户信息：</strong></td>
		</c:if>
	</p:title>

	<p:line flag="0" />
		
	<p:subBody width="98%">
			
	<div id="container-1" style="display: none;">
		<ul>
			<li><a href="#fragment-basic-info"><span>基本信息</span></a></li>
			<li><a href="#fragment-linkman-info"><span>联系人信息</span></a></li>
			<li><a href="#fragment-busi-info"><span>财务信息</span></a></li>
			<li><a href="#fragment-addr-info"><span>地址信息</span></a></li>
		</ul>
	
		<div id="fragment-basic-info">
			<c:if test="${bean.type==1}">
			<%@include file="detailIndividualClient.jsp"%>
			</c:if>
			<c:if test="${bean.type==2}">
			<%@include file="detailDepartClient.jsp"%>
			</c:if>
			<c:if test="${bean.type==3}">
			<%@include file="detailCorporationClient.jsp"%>
			</c:if>
		</div>
		
		<div id="fragment-linkman-info">
			<%@include file="detailClientContact.jsp"%>
		</div>
		
		<div id="fragment-busi-info">
			<%@include file="detailClientBusiness.jsp"%>
		</div>
		
		<div id="fragment-addr-info">
			<%@include file="detailClientAddress.jsp"%>
		</div>		
	
	</div>
	
	</p:subBody>
	
	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
			<input type="button" name="ba" class="button_class"
            style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
            onclick="javascript:history.go(-1)">
		</div>
	</p:button>

	<p:message />
	</p:body>
</form>
</BODY>
</HTML>
