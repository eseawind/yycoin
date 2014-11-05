<%@ page contentType="text/html;charset=UTF-8" language="java"
	isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page
	import="org.apache.commons.logging.Log,org.apache.commons.logging.LogFactory"%>
<%@include file="./common.jsp"%>

<html>
<head>
<p:link title="错误提示" cal="false" guid="false" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0"
	background="../images/special/bg.gif">
<p:navigation height="22">
	<td width="550" class="navigation"><span>SKY-OA系统</td>
	<td width="85"></td>
</p:navigation>
<br>
<table width="100%" height="508" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<td height="477" valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<!--DWLayoutTable-->
			<tr>
				<td width="784" height="159"></td>
			</tr>
			<tr>
				<td height="318" align="center" valign="top" class="background">
				<table border="0" cellspacing="8">
					<tr>
						<td>
						<div align="center"><img onclick="javascript:history.go(-1)" style="cursor: pointer;"
							src="../images/error.gif" width="84" height="35"></div>
							<% 
                        if (exception != null)
                        {
                            exception.printStackTrace(new java.io.PrintWriter(out));
                        }
                    %>
						</td>
					</tr>
					<tr>
						<td align="center"><span class="style1"><font size="3">
						<strong>${errorInfo}</strong> </font></span></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
