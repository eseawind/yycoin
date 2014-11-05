<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="供应商用户维护" cal="false" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="javascript">

function addBean()
{
	submit('确定维护供应商用户?');
}


</script>

</head>
<body class="body_class">
<form name="formEntry" action="../provider/provider.do" method="post"><input
	type="hidden" name="method" value="addOrUpdateUserBean">
<input type="hidden" name="id" value="${bean.id}"> 
<input type="hidden" name="provideId" value="${provideId}"> 
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">供应商管理</span> &gt;&gt; 供应商用户</td>
	<td width="85"></td>
</p:navigation> <br>
<p:body width="100%">

	<p:title>
		<td class="caption"><strong>供应商用户基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.product.bean.ProviderUserBean" opr="1"/>

		<p:table cells="1">
			<p:pro field="name" innerString="size=60"/>
			
			<p:pro field="pwkey" innerString="size=60"/>
		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
		 value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message />
</p:body></form>
</body>
</html>

