<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="修改加密锁" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="javascript">
function updateBean()
{
	submit('确定设置加密锁?');
}

</script>

</head>
<body class="body_class">
<form name="addApply" action="../admin/staffer.do"><input
	type="hidden" name="method" value="updatePwkey"> <input
	type="hidden" name="id" value="${bean.id}"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">人员管理</span> &gt;&gt; 设置加密锁</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>加密锁信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		<p:table cells="2">
			<p:cells title="加密锁设置" celspan="2">${hasSet ? "<font color=blue>已经设置加密锁</font>" : "<font color=red>没有设置加密锁</font>"}</p:cells>
			
			<p:cells title="请输入新锁" celspan="2"><input type="text" class="input_class" name="key" 
			title="请输入16位的数字或者字母"
			id="key" size="20" maxlength="16" oncheck="notNone;isNumberOrLetter;maxLength(16);minLength(16)"></p:cells>
		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="updateBean()"></div>
	</p:button>

	<p:message2 />
</p:body></form>
</body>
</html>

