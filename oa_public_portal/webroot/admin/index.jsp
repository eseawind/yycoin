<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ page import="java.util.*" %>
<html>
<head>
<title>-=SKY·OA系统=-</title>
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
	if (is_ie && ie6)
	{
		alert('请使用IE7以上的版本,当前系统不支持IE6');
		
        return;
	}
	
	//暂时不校验了
    if (false && isUseActive && !isActive && is_ie)
    {
        alert('安全控件初始化失败,请下载安全控件或者请插入加密锁');
        return;
    }
    
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
        
        var keyRand = $$('rand').toUpperCase();
        
        if (is_ie && isActive && !checkEnc(keyRand))
        {
            return;
        }
        
        $O('loginType').value = '0';
        
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

function VerifyInput2()
{
    if (is_ie && ie6)
    {
        alert('请使用IE7以上的版本,当前系统不支持IE6');
        
        return;
    }
    
    //暂时不校验了
    if (false && isUseActive && !isActive && is_ie)
    {
        alert('安全控件初始化失败,请下载安全控件或者请插入加密锁');
        return;
    }
    
    var keyRand = $$('rand').toUpperCase();
    
    if (!checkEnc(keyRand))
    {
        return;
    }
    
    $O('loginType').value = '1';
    
    loginform.submit();
    
    return;
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
    
    obj.src = 'image.jsp?randomNum=' + ltime;
}

var isUseActive = true;

function load()
{
    loginform.userName.focus();
    
    loginform.userName.select();
    
    if (is_ie && ie6)
	{
		alert('请使用IE7以上的版本,当前系统不支持IE6');
		
        return;
	}
    
    if (isUseActive)
    {
	    try
	    {
	        
	        var s_simnew31 = new ActiveXObject("Syunew6A.s_simnew6");
	        
	        DevicePath = s_simnew31.FindPort(0);
	        
	        if( s_simnew31.LastError!= 0 )
	        {
	            window.alert("未发现加密锁，请插入加密锁");
	            
	            isActive = false;
	        }
	        else
	        {
	            isActive = true;
	        }
	    }
	    catch(e)
	    {
	        isActive = false;
	        
	        alert('安全控件初始化失败,请使用IE内核浏览器且下载安全控件');
	    }
    }
}
</script>
</head>
<body onload="load()">
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<form name="loginform" onsubmit=VerifyInput() action=./checkuser.do method=post>
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
<!-- 限制OA 入口 -->
<input type=hidden name="loginKey" value="${loginKey}">
<table style="border: 3px outset; width: 0pt;" align="center" border="0"
	cellpadding="1" cellspacing="0">
	<tbody>
		<tr>
			<td>
			<table style="width: 500px;" class="TableFoot" align="center"
				border="0" cellpadding="3" cellspacing="0">
				<tbody>
					<tr>
						<th colspan="2" height="35" align="left"><font size=4 color=black>SKY·OA系统</font></th>
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
							src="image.jsp" width="60" height="20" border="1" align="middle"></span></td>
					</tr>
					<tr>
						<td class="forumRow" align="right" valign="middle" width="39%"><b>二次密码：</b></td>
						<td class="forumRow"><input name=spassword value="" onkeypress=KeyPress()
							class="FormInputEdit" type="text">
							<a href="../down/down.do?method=downTemplateFileByName&fileName=enc.zip" target="_blank">下载安全控件</a>&nbsp;
							<a href="../down/down.do?method=downTemplateFileByName&fileName=IE8.zip" target="_blank">下载IE8</a>
					</tr>
					<tr>
						<td colspan="2" class="forumRowHighlight" align="center"
							valign="middle"><input name="BtnLogin" value="&nbsp;&nbsp;登 录&nbsp;&nbsp;" class="button_class" onclick="VerifyInput()"
							 type="button"> 
							 &nbsp;<input name="BtnLogin" value="&nbsp;&nbsp;密码狗一键登录&nbsp;&nbsp;" class="button_class" onclick="VerifyInput2()"
                             type="button"> &nbsp; 
                             <input class="button_class"
							name="BtnReset" value="&nbsp;&nbsp;重 置&nbsp;&nbsp;" type="reset"></td>
					</tr>
					<tr height="15">
                        <td colspan="1" class="forumRowHighlight" align="left" width="39%"
                            valign="middle">
                            [${ggMap.SA}]&nbsp;&nbsp;<font color=red>${errorInfo}</font></td>
                            <c:remove var="errorInfo" scope="session" />
                        <td align="right" class="forumRowHighlight"><a target="_blank" href="../admin/copyright.htm">版权声明</a></td>
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