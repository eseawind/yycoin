<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ page import="java.util.*" %>
<html>
<head>
<title>永银文化-紫金农商销售数据接口平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
a:link {
	color: #000000;
	TEXT-DECORATION: none
}

a:visited {
	color: #000000;
	TEXT-DECORATION: none
}

a:active {
	color: #000000;
	TEXT-DECORATION: none
}

a:hover {
	color: #FF6600
}

body {
	background: #e0e0e0;
}

Table {
	border: 0px;
}

Th {
	FONT-WEIGHT: bold;
	FONT-SIZE: 12px;
	COLOR: white;
	BACKGROUND-COLOR: #00BFFF
}

TD {
	font-size: 9pt;
	line-height: 140%
}

TD.ForumRow {
	PADDING-RIGHT: 3px;
	PADDING-LEFT: 3px;
	BACKGROUND: #f1f3f5;
	PADDING-BOTTOM: 3px;
	PADDING-TOP: 3px
}

TD.forumRowHighlight {
	PADDING-RIGHT: 3px;
	PADDING-LEFT: 3px;
	BACKGROUND: #E4EDF9;
	PADDING-BOTTOM: 3px;
	PADDING-TOP: 3px
}

.button_class {
    font-family: Arial, Helvetica, sans-serif;
    font-size: 12px;
    color: #333333;
    background-color: #f5f5f5;
    border: 1px solid #666666;
    background-position: center center;
    height: 18px;
    CURSOR: pointer;
    vertical-align: middle;
    visibility: visible;
}

.FormInputEdit {
	TEXT-DECORATION: none;
	line-height: 14px;
	font-size: 9pt;
	font-family: "宋体";
	border: 1px #547cbb solid;
}
</style>
<link href="../css/login.css" type="text/css" rel="stylesheet">
<link href="../css/ccc.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../admin_js/enc.js"></script>
<script language="JavaScript">
var isActive = false;

function reset(){
    document.loginform.userName.value="";
    document.loginform.password.value=""
    }

function CheckuserName(str)
{
    var re = /^\w{1,16}$/;
    return re.test(str)||str=="";
}

var ie6 = is_ie && /MSIE 6.0/.test(navigator.userAgent) && !(/MSIE 8.0/.test(navigator.userAgent)) && !(/MSIE 7.0/.test(navigator.userAgent));

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

        var keyRand = $$('rand').toUpperCase();
        
        loginform.submit();
        
        return false;
    }
}

function KeyPress()
{
    var event = common.getEvent();
    
    if(event.keyCode == 13)
    {
        VerifyInput();
        
        event.cancelBubble = true;
        event.returnValue = false;
    }
}

function again(obj)
{
    var ltime = new Date().getTime();
    
    obj.src = '../admin/image.jsp?randomNum=' + ltime;
}

function load()
{
    loginform.userName.focus();
    
    loginform.userName.select();
    
}
</script>
</head>
<body onload="load()">
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<form name="loginform" onsubmit=VerifyInput() action=../admin/logon.do method=post>
<input type="hidden" name="method" value="login" />
<input type="hidden" name="jiamiRand" value="" />
<input type="hidden" name="key" value="" />
<%
String superRand = "";
for (int i = 0; i < 20; i++ )
{
    int x = new Random().nextInt(10);
    superRand += x;
}

// 将认证码存入SESSION  
session.setAttribute("superRand", superRand);

out.println("<input type='hidden' name='superRand' value='" + superRand + "' />");

%>
<input type="hidden" name="encSuperRand" value="" />
<input type="hidden" name="loginType" value="0" />
<table style="border: 3px outset; width: 0pt;" align="center" border="0"
	cellpadding="1" cellspacing="0">
	<tbody>
		<tr>
			<td>
			<table style="width: 500px;" class="TableFoot" align="center"
				border="0" cellpadding="3" cellspacing="0">
				<tbody>
					<tr>
						<th colspan="2" height="35" align="left"><font size=4 color=black>永银文化-紫金农商销售数据接口平台</font></th>
					</tr>
				</tbody>
			</table>
			<table style="width: 500px;" class="TableFoot" align="center"
				border="0" cellpadding="3" cellspacing="0">
				<tbody>
					<tr>
						<td colspan="2" class="forumRowHighlight"
							align="center" height="25" valign="middle">
						<table border="0" height="100%" width="100%">
							<tbody>
								<tr>
									<td rowspan="3" height="100%" width="61%"></td>
									<td height="0" width="39%"></td>
								</tr>
								<tr>
									<td class="TdFoot" height="" valign="top"><br>
									</td>
								</tr>
								<tr>
									<td height=""></td>
								</tr>
							</tbody>
						</table>
						</td>
					</tr>
				</tbody>
			</table>
			<table style="width: 500px;" align="center" border="0"
				cellpadding="3" cellspacing="0">
				<tbody>
					<tr>
						<td colspan="2" class="forumRowHighlight" align="center"
							height="4" valign="middle"></td>
					</tr>
					<tr>
						<td class="forumRow" align="right" valign="middle" width="39%"><b>用户名：</b></td>
						<td class="forumRow" valign="middle" width="61%"><input onkeypress=KeyPress()
							name="userName" class="FormInputEdit" type="text" value=""></td>
					</tr>
					<tr>
						<td class="forumRow" align="right" valign="middle" width="39%"><b>密
						码：</b></td>
						<td class="forumRow" valign="middle"><input name="password" onkeypress=KeyPress() value=""
							class="FormInputEdit" type="password"></td>
					</tr>
					<tr>
						<td class="forumRow" align="right" valign="middle" width="39%"><b>验证码：</b></td>
						<td class="forumRow"><input name="rand" value="" size="6" maxlength="4" onkeypress=KeyPress()
							title="验证码不区分大小写" style="ime-mode: disabled"
							onkeypress=KeyPress() class="FormInputEdit" type="text">&nbsp;<span
							id="rang_span"><img name="randImage" onclick="again(this)"
							style="cursor: pointer;" title="点击可以切换验证码" id="randImage"
							src="../admin/image.jsp" width="60" height="20" border="1" align="middle"></span></td>
					</tr>
					<tr>
						<td colspan="2" class="forumRowHighlight" align="center"
							valign="middle"><input name="BtnLogin" value="&nbsp;&nbsp;登 录&nbsp;&nbsp;" class="button_class" onclick="VerifyInput()"
							 type="button"> 
							 &nbsp; 
                             <input class="button_class"
							name="BtnReset" value="&nbsp;&nbsp;重 置&nbsp;&nbsp;" type="reset"></td>
					</tr>
					<tr height="15">
                        <td colspan="2" class="forumRowHighlight" align="left" width="39%"
                            valign="middle">
                            <font color=red>${errorInfo}</font></td>
                            <c:remove var="errorInfo" scope="session" />
                    </tr>
				</tbody>
			</table>
			</td>
		</tr>
	</tbody>
</table>
</form>
<br>
<br>
<br>
</body>
</html>