<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<title>永银文化-紫金农商销售数据接口平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript" type="text/JavaScript">
function Logout()
{
}
</script>
<frameset rows="63,*" cols="*" frameborder="NO" border="0"
	framespacing="0" onUnload="Logout()">
	<frame src="citicTop.jsp" name="topFrame" scrolling="NO" id="topFrame">
	<frameset cols="191,*" framespacing="0" frameborder="no" border="0">
	
		<frame src="citicShousuo.jsp" name="fun" noresize scrolling="auto">

		<frame src="citicWelcome.jsp" name="main">
	</frameset>
</frameset>
<noframes>
<body>
你使用的浏览器不支持框架。
</body>
</noframes>
</html>
