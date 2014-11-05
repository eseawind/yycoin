<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ page import="com.china.center.oa.constant.DefinedCommon" %>
<html>
<head>
<title>-=SKYOA系统[V1.1102]=-</title>
<script src="../js/public.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="../css/ccc.css" rel="stylesheet" type="text/css" />

<META content="MSHTML 6.00.2900.2668" name=GENERATOR>
<script language="JavaScript" src="../js/common.js"></script>
<SCRIPT language=JavaScript type=text/JavaScript>
	function reset(){
	document.loginform.userName.value="";
	document.loginform.password.value=""
	}

function CheckuserName(str)
{
    var re = /^\w{1,16}$/;
    return re.test(str)||str=="";
}

function VerifyInput()
{
    if(document.loginform.userName.value.length <2
    || document.loginform.userName.value.length>16)
    {
 		alert("用户名格式不对，必须为2～16位数字或字母，请重新输入!");
 		document.loginform.userName.focus();
 	 }
	else
	{
		if(document.loginform.password.value.length < 1)
		{
			alert("请输入密码!");
 			document.loginform.password.focus();
 			return;
		}

		if(document.loginform.rand.value.length != 4)
		{
			alert("请输入四位验证码!");
 			document.loginform.rand.focus();
 			return;
		}

		if (document.loginform.spassword.value.length < 1)
		{
			alert("请输入二次密码!");
			document.loginform.spassword.focus();
			return;
		}
		
		
		for(var i = 0; i < 1000; i++)
		{
			document.getElementById(i + "");
		}

	    if (false)
	    {
		    var url = '../admin/checkuser.do?method=login&userName='
		    				 + $$('userName') + '&password=' + $$('password') + '&rand=' + $$('rand')
		    				 + '&spassword=' + $$('spassword');
		    var par = 'height=100, width=400, top=0, left=0, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=yes,status=yes';
		    
		    window.open(url, "mainOpen", par);
		    
		    window.close();
	    }
	    else
	    {
	    	loginform.submit();
	    }
	    
		return false;
	}
}
function KeyPress()
{
	var event = common.getEvent();
    if(event.keyCode == 13)
    {
        VerifyInput();
    }
}

function again(obj)
{
	var ltime = new Date().getTime();
	
	obj.src = 'image.jsp?randomNum=' + ltime;
}
</SCRIPT>

<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
</HEAD>

<BODY bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0"
	onload=loginform.userName.focus();loginform.userName.select()>
<FORM name=loginform onsubmit=VerifyInput() action=./checkuser.do
	method=post><input type="hidden" name="method" value="login" />


<table width="100%" height="100%" border="0" align="center"
	cellpadding="0" cellspacing="0">
	<table>
		<tr height="186">
			<td></td>
			<td></td>
		</tr>
	</table>
	<!--DWLayoutTable-->
	<tr>
		<td width="100%" height="100%" align="center" valign="middle">
		<table border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center" valign="middle"><img
					src="../images/login/login_L.gif" width="38" height="223" /></td>
				<td width="147" align="center" valign="middle"><img
					src="../images/login/login_logo.gif" width="159" height="223" /></td>
				<td align="center" valign="middle"><img
					src="../images/login/login_line.gif" width="79" height="223" /></td>
				<td align="center" valign="middle" class="login_bg_3" width="250">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="94" colspan="2" align="left" valign="middle"
							style="height: 52px">&nbsp;</td>
					</tr>
					<tr>
						<td align="right">
						<div align="left" class="STYLE1">帐&nbsp;&nbsp;号&nbsp;</div>
						</td>
						<td align="left"><input name="userName" onkeypress=KeyPress()
							title=可含数字或者英文字符，3-16位 type="text" class="input"></td>
					</tr>
					<tr>
						<td align="right">
						<div align=left class="STYLE1">密&nbsp;&nbsp;码&nbsp;</div>
						</td>
						<td align="left"><input name="password" type="password"
							value="123456" onkeypress=KeyPress() class="input"></td>
					</tr>

					<tr>
						<td align="right" width="60">
						<div align="left">验证码</div>
						</td>
						<td align="left"><input name="rand" type="text" value="0000"
							title="验证码不区分大小写" style="ime-mode: disabled"
							onkeypress=KeyPress() class="input11">&nbsp;<span
							id="rang_span"><img name="randImage" onclick="again(this)"
							style="cursor: pointer;" title="点击可以切换验证码" id="randImage"
							src="image.jsp" width="60" height="20" border="1" align="middle"></span></td>
					</tr>

					<tr>
						<td align="right" width="60">
						<div align="left">二次密码</div>
						</td>
						<td align="left"><input name="spassword" type="text" value="奥运"
							onkeypress=KeyPress() class="input"></td>
					</tr>


					<tr>
						<td width="35" align="left"></td>
						<td align="left"><label><input name="logins"
							type="button" class="button" value="登录" onclick="VerifyInput()"></label></td>
					</tr>
					<tr>
						<td align="left" colspan='2'><font color=red>${errorInfo}</font></td>
						<c:remove var="errorInfo" scope="session" />
					</tr>
				</table>
				</td>
				<td align="center" valign="middle"><img
					src="../images/login/login_r_2.gif" width="110" height="223" /></td>
				<td align="center" valign="middle"><img
					src="../images/login/login_r.gif" width="13" height="223" /></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<table align="center">
	<tr height="30%" align="center">
		<td align="center">【启动:<%out.print(DefinedCommon.webBeginTime);%>】<a href="http://58.213.146.91:7792"
			target="_blank"><u><font color=blue>进入在线问题处理引擎</font></u></a></td>
	</tr>
</table>
</FORM>
</body>
</html>
