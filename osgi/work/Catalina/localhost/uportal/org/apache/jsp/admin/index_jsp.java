package org.apache.jsp.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fremove_005fvar_005fscope_005fnobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fremove_005fvar_005fscope_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fremove_005fvar_005fscope_005fnobody.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>-=SKY·OA系统=-</title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write("a:link {\r\n");
      out.write("\tcolor: #000000;\r\n");
      out.write("\tTEXT-DECORATION: none\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("a:visited {\r\n");
      out.write("\tcolor: #000000;\r\n");
      out.write("\tTEXT-DECORATION: none\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("a:active {\r\n");
      out.write("\tcolor: #000000;\r\n");
      out.write("\tTEXT-DECORATION: none\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("a:hover {\r\n");
      out.write("\tcolor: #FF6600\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("body {\r\n");
      out.write("\tbackground: #e0e0e0;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("Table {\r\n");
      out.write("\tborder: 0px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("Th {\r\n");
      out.write("\tFONT-WEIGHT: bold;\r\n");
      out.write("\tFONT-SIZE: 12px;\r\n");
      out.write("\tCOLOR: white;\r\n");
      out.write("\tBACKGROUND-COLOR: #00BFFF\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("TD {\r\n");
      out.write("\tfont-size: 9pt;\r\n");
      out.write("\tline-height: 140%\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("TD.ForumRow {\r\n");
      out.write("\tPADDING-RIGHT: 3px;\r\n");
      out.write("\tPADDING-LEFT: 3px;\r\n");
      out.write("\tBACKGROUND: #f1f3f5;\r\n");
      out.write("\tPADDING-BOTTOM: 3px;\r\n");
      out.write("\tPADDING-TOP: 3px\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("TD.forumRowHighlight {\r\n");
      out.write("\tPADDING-RIGHT: 3px;\r\n");
      out.write("\tPADDING-LEFT: 3px;\r\n");
      out.write("\tBACKGROUND: #E4EDF9;\r\n");
      out.write("\tPADDING-BOTTOM: 3px;\r\n");
      out.write("\tPADDING-TOP: 3px\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".button_class {\r\n");
      out.write("    font-family: Arial, Helvetica, sans-serif;\r\n");
      out.write("    font-size: 12px;\r\n");
      out.write("    color: #333333;\r\n");
      out.write("    background-color: #f5f5f5;\r\n");
      out.write("    border: 1px solid #666666;\r\n");
      out.write("    background-position: center center;\r\n");
      out.write("    height: 18px;\r\n");
      out.write("    CURSOR: pointer;\r\n");
      out.write("    vertical-align: middle;\r\n");
      out.write("    visibility: visible;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".FormInputEdit {\r\n");
      out.write("\tTEXT-DECORATION: none;\r\n");
      out.write("\tline-height: 14px;\r\n");
      out.write("\tfont-size: 9pt;\r\n");
      out.write("\tfont-family: \"宋体\";\r\n");
      out.write("\tborder: 1px #547cbb solid;\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("<link href=\"../css/login.css\" type=\"text/css\" rel=\"stylesheet\">\r\n");
      out.write("<link href=\"../css/ccc.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("<script language=\"JavaScript\" src=\"../js/common.js\"></script>\r\n");
      out.write("<script language=\"JavaScript\" src=\"../js/public.js\"></script>\r\n");
      out.write("<script language=\"JavaScript\" src=\"../admin_js/enc.js\"></script>\r\n");
      out.write("<script language=\"JavaScript\">\r\n");
      out.write("var isActive = false;\r\n");
      out.write("\r\n");
      out.write("function reset(){\r\n");
      out.write("    document.loginform.userName.value=\"\";\r\n");
      out.write("    document.loginform.password.value=\"\"\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("function CheckuserName(str)\r\n");
      out.write("{\r\n");
      out.write("    var re = /^\\w{1,16}$/;\r\n");
      out.write("    return re.test(str)||str==\"\";\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("var ie6 = is_ie && /MSIE 6.0/.test(navigator.userAgent) && !(/MSIE 8.0/.test(navigator.userAgent)) && !(/MSIE 7.0/.test(navigator.userAgent));\r\n");
      out.write("\r\n");
      out.write("function VerifyInput()\r\n");
      out.write("{\r\n");
      out.write("\tif (is_ie && ie6)\r\n");
      out.write("\t{\r\n");
      out.write("\t\talert('请使用IE7以上的版本,当前系统不支持IE6');\r\n");
      out.write("\t\t\r\n");
      out.write("        return;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t//暂时不校验了\r\n");
      out.write("    if (false && isUseActive && !isActive && is_ie)\r\n");
      out.write("    {\r\n");
      out.write("        alert('安全控件初始化失败,请下载安全控件或者请插入加密锁');\r\n");
      out.write("        return;\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    if(document.loginform.userName.value.length <2\r\n");
      out.write("    || document.loginform.userName.value.length>16)\r\n");
      out.write("    {\r\n");
      out.write("        alert(\"用户名格式不对，必须为2～16位数字或字母，请重新输入!\");\r\n");
      out.write("        document.loginform.userName.focus();\r\n");
      out.write("     }\r\n");
      out.write("    else\r\n");
      out.write("    {\r\n");
      out.write("        if(document.loginform.password.value.length < 1)\r\n");
      out.write("        {\r\n");
      out.write("            alert(\"请输入密码!\");\r\n");
      out.write("            document.loginform.password.focus();\r\n");
      out.write("            return;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        if(document.loginform.rand.value.length != 4)\r\n");
      out.write("        {\r\n");
      out.write("            alert(\"请输入四位验证码!\");\r\n");
      out.write("            document.loginform.rand.focus();\r\n");
      out.write("            return;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        if (document.loginform.spassword.value.length < 1)\r\n");
      out.write("        {\r\n");
      out.write("            alert(\"请输入二次密码!\");\r\n");
      out.write("            document.loginform.spassword.focus();\r\n");
      out.write("            return;\r\n");
      out.write("        }\r\n");
      out.write("        \r\n");
      out.write("        var keyRand = $$('rand').toUpperCase();\r\n");
      out.write("        \r\n");
      out.write("        if (is_ie && isActive && !checkEnc(keyRand))\r\n");
      out.write("        {\r\n");
      out.write("            return;\r\n");
      out.write("        }\r\n");
      out.write("        \r\n");
      out.write("        $O('loginType').value = '0';\r\n");
      out.write("        \r\n");
      out.write("        if (false)\r\n");
      out.write("        {\r\n");
      out.write("            var url = '../admin/checkuser.do?method=login&userName='\r\n");
      out.write("                             + $$('userName') + '&password=' + $$('password') + '&rand=' + $$('rand')\r\n");
      out.write("                             + '&spassword=' + $$('spassword');\r\n");
      out.write("            var par = 'height=100, width=400, top=0, left=0, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=yes,status=yes';\r\n");
      out.write("            \r\n");
      out.write("            window.open(url, \"mainOpen\", par);\r\n");
      out.write("            \r\n");
      out.write("            window.close();\r\n");
      out.write("        }\r\n");
      out.write("        else\r\n");
      out.write("        {\r\n");
      out.write("            loginform.submit();\r\n");
      out.write("        }\r\n");
      out.write("        \r\n");
      out.write("        return false;\r\n");
      out.write("    }\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function VerifyInput2()\r\n");
      out.write("{\r\n");
      out.write("    if (is_ie && ie6)\r\n");
      out.write("    {\r\n");
      out.write("        alert('请使用IE7以上的版本,当前系统不支持IE6');\r\n");
      out.write("        \r\n");
      out.write("        return;\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    //暂时不校验了\r\n");
      out.write("    if (false && isUseActive && !isActive && is_ie)\r\n");
      out.write("    {\r\n");
      out.write("        alert('安全控件初始化失败,请下载安全控件或者请插入加密锁');\r\n");
      out.write("        return;\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    var keyRand = $$('rand').toUpperCase();\r\n");
      out.write("    \r\n");
      out.write("    if (!checkEnc(keyRand))\r\n");
      out.write("    {\r\n");
      out.write("        return;\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    $O('loginType').value = '1';\r\n");
      out.write("    \r\n");
      out.write("    loginform.submit();\r\n");
      out.write("    \r\n");
      out.write("    return;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function KeyPress()\r\n");
      out.write("{\r\n");
      out.write("    var event = common.getEvent();\r\n");
      out.write("    \r\n");
      out.write("    if(event.keyCode == 13)\r\n");
      out.write("    {\r\n");
      out.write("        VerifyInput();\r\n");
      out.write("        \r\n");
      out.write("        event.cancelBubble = true;\r\n");
      out.write("        event.returnValue = false;\r\n");
      out.write("    }\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function again(obj)\r\n");
      out.write("{\r\n");
      out.write("    var ltime = new Date().getTime();\r\n");
      out.write("    \r\n");
      out.write("    obj.src = 'image.jsp?randomNum=' + ltime;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("var isUseActive = true;\r\n");
      out.write("\r\n");
      out.write("function load()\r\n");
      out.write("{\r\n");
      out.write("    loginform.userName.focus();\r\n");
      out.write("    \r\n");
      out.write("    loginform.userName.select();\r\n");
      out.write("    \r\n");
      out.write("    if (is_ie && ie6)\r\n");
      out.write("\t{\r\n");
      out.write("\t\talert('请使用IE7以上的版本,当前系统不支持IE6');\r\n");
      out.write("\t\t\r\n");
      out.write("        return;\r\n");
      out.write("\t}\r\n");
      out.write("    \r\n");
      out.write("    if (isUseActive)\r\n");
      out.write("    {\r\n");
      out.write("\t    try\r\n");
      out.write("\t    {\r\n");
      out.write("\t        \r\n");
      out.write("\t        var s_simnew31 = new ActiveXObject(\"Syunew6A.s_simnew6\");\r\n");
      out.write("\t        \r\n");
      out.write("\t        DevicePath = s_simnew31.FindPort(0);\r\n");
      out.write("\t        \r\n");
      out.write("\t        if( s_simnew31.LastError!= 0 )\r\n");
      out.write("\t        {\r\n");
      out.write("\t            window.alert(\"未发现加密锁，请插入加密锁\");\r\n");
      out.write("\t            \r\n");
      out.write("\t            isActive = false;\r\n");
      out.write("\t        }\r\n");
      out.write("\t        else\r\n");
      out.write("\t        {\r\n");
      out.write("\t            isActive = true;\r\n");
      out.write("\t        }\r\n");
      out.write("\t    }\r\n");
      out.write("\t    catch(e)\r\n");
      out.write("\t    {\r\n");
      out.write("\t        isActive = false;\r\n");
      out.write("\t        \r\n");
      out.write("\t        alert('安全控件初始化失败,请使用IE内核浏览器且下载安全控件');\r\n");
      out.write("\t    }\r\n");
      out.write("    }\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"load()\">\r\n");
      out.write("<p>&nbsp;</p>\r\n");
      out.write("<p>&nbsp;</p>\r\n");
      out.write("<p>&nbsp;</p>\r\n");
      out.write("<p>&nbsp;</p>\r\n");
      out.write("<form name=\"loginform\" onsubmit=VerifyInput() action=./checkuser.do method=post>\r\n");
      out.write("<input type=\"hidden\" name=\"method\" value=\"login\" />\r\n");
      out.write("<input type=\"hidden\" name=\"jiamiRand\" value=\"\" />\r\n");
      out.write("<input type=\"hidden\" name=\"key\" value=\"\" />\r\n");

String superRand = "";
for (int i = 0; i < 20; i++ )
{
    int x = new Random().nextInt(10);
    superRand += x;
}

// 将认证码存入SESSION  
session.setAttribute("superRand", superRand);

out.println("<input type='hidden' name='superRand' value='" + superRand + "' />");


      out.write("\r\n");
      out.write("<input type=\"hidden\" name=\"encSuperRand\" value=\"\" />\r\n");
      out.write("<input type=\"hidden\" name=\"loginType\" value=\"0\" />\r\n");
      out.write("<!-- 限制OA 入口 -->\r\n");
      out.write("<input type=hidden name=\"loginKey\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${loginKey}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\">\r\n");
      out.write("<table style=\"border: 3px outset; width: 0pt;\" align=\"center\" border=\"0\"\r\n");
      out.write("\tcellpadding=\"1\" cellspacing=\"0\">\r\n");
      out.write("\t<tbody>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td>\r\n");
      out.write("\t\t\t<table style=\"width: 500px;\" class=\"TableFoot\" align=\"center\"\r\n");
      out.write("\t\t\t\tborder=\"0\" cellpadding=\"3\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t<tbody>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<th colspan=\"2\" height=\"35\" align=\"left\"><font size=4 color=black>SKY·OA系统</font></th>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</tbody>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t\t<table style=\"width: 500px;\" class=\"TableFoot\" align=\"center\"\r\n");
      out.write("\t\t\t\tborder=\"0\" cellpadding=\"3\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t<tbody>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td colspan=\"2\" class=\"forumRowHighlight\"\r\n");
      out.write("\t\t\t\t\t\t\talign=\"center\" height=\"25\" valign=\"middle\">\r\n");
      out.write("\t\t\t\t\t\t<table border=\"0\" height=\"100%\" width=\"100%\">\r\n");
      out.write("\t\t\t\t\t\t\t<tbody>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td rowspan=\"3\" height=\"100%\" width=\"61%\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td height=\"0\" width=\"39%\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td class=\"TdFoot\" height=\"\" valign=\"top\"><br>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<td height=\"\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t</tbody>\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</tbody>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t\t<table style=\"width: 500px;\" align=\"center\" border=\"0\"\r\n");
      out.write("\t\t\t\tcellpadding=\"3\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t\t<tbody>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td colspan=\"2\" class=\"forumRowHighlight\" align=\"center\"\r\n");
      out.write("\t\t\t\t\t\t\theight=\"4\" valign=\"middle\"></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td class=\"forumRow\" align=\"right\" valign=\"middle\" width=\"39%\"><b>用户名：</b></td>\r\n");
      out.write("\t\t\t\t\t\t<td class=\"forumRow\" valign=\"middle\" width=\"61%\"><input onkeypress=KeyPress()\r\n");
      out.write("\t\t\t\t\t\t\tname=\"userName\" class=\"FormInputEdit\" type=\"text\" value=\"\"></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td class=\"forumRow\" align=\"right\" valign=\"middle\" width=\"39%\"><b>密\r\n");
      out.write("\t\t\t\t\t\t码：</b></td>\r\n");
      out.write("\t\t\t\t\t\t<td class=\"forumRow\" valign=\"middle\"><input name=\"password\" onkeypress=KeyPress() value=\"\"\r\n");
      out.write("\t\t\t\t\t\t\tclass=\"FormInputEdit\" type=\"password\"></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td class=\"forumRow\" align=\"right\" valign=\"middle\" width=\"39%\"><b>验证码：</b></td>\r\n");
      out.write("\t\t\t\t\t\t<td class=\"forumRow\"><input name=\"rand\" value=\"\" size=\"6\" maxlength=\"4\" onkeypress=KeyPress()\r\n");
      out.write("\t\t\t\t\t\t\ttitle=\"验证码不区分大小写\" style=\"ime-mode: disabled\"\r\n");
      out.write("\t\t\t\t\t\t\tonkeypress=KeyPress() class=\"FormInputEdit\" type=\"text\">&nbsp;<span\r\n");
      out.write("\t\t\t\t\t\t\tid=\"rang_span\"><img name=\"randImage\" onclick=\"again(this)\"\r\n");
      out.write("\t\t\t\t\t\t\tstyle=\"cursor: pointer;\" title=\"点击可以切换验证码\" id=\"randImage\"\r\n");
      out.write("\t\t\t\t\t\t\tsrc=\"image.jsp\" width=\"60\" height=\"20\" border=\"1\" align=\"middle\"></span></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td class=\"forumRow\" align=\"right\" valign=\"middle\" width=\"39%\"><b>二次密码：</b></td>\r\n");
      out.write("\t\t\t\t\t\t<td class=\"forumRow\"><input name=spassword value=\"\" onkeypress=KeyPress()\r\n");
      out.write("\t\t\t\t\t\t\tclass=\"FormInputEdit\" type=\"text\">\r\n");
      out.write("\t\t\t\t\t\t\t<a href=\"../down/down.do?method=downTemplateFileByName&fileName=enc.zip\" target=\"_blank\">下载安全控件</a>&nbsp;\r\n");
      out.write("\t\t\t\t\t\t\t<a href=\"../down/down.do?method=downTemplateFileByName&fileName=IE8.zip\" target=\"_blank\">下载IE8</a>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td colspan=\"2\" class=\"forumRowHighlight\" align=\"center\"\r\n");
      out.write("\t\t\t\t\t\t\tvalign=\"middle\"><input name=\"BtnLogin\" value=\"&nbsp;&nbsp;登 录&nbsp;&nbsp;\" class=\"button_class\" onclick=\"VerifyInput()\"\r\n");
      out.write("\t\t\t\t\t\t\t type=\"button\"> \r\n");
      out.write("\t\t\t\t\t\t\t &nbsp;<input name=\"BtnLogin\" value=\"&nbsp;&nbsp;密码狗一键登录&nbsp;&nbsp;\" class=\"button_class\" onclick=\"VerifyInput2()\"\r\n");
      out.write("                             type=\"button\"> &nbsp; \r\n");
      out.write("                             <input class=\"button_class\"\r\n");
      out.write("\t\t\t\t\t\t\tname=\"BtnReset\" value=\"&nbsp;&nbsp;重 置&nbsp;&nbsp;\" type=\"reset\"></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr height=\"15\">\r\n");
      out.write("                        <td colspan=\"1\" class=\"forumRowHighlight\" align=\"left\" width=\"39%\"\r\n");
      out.write("                            valign=\"middle\">\r\n");
      out.write("                            [");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ggMap.SA}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("]&nbsp;&nbsp;<font color=red>");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${errorInfo}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("</font></td>\r\n");
      out.write("                            ");
      if (_jspx_meth_c_005fremove_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("                        <td align=\"right\" class=\"forumRowHighlight\"><a target=\"_blank\" href=\"../admin/copyright.htm\">版权声明</a></td>\r\n");
      out.write("                    </tr>\r\n");
      out.write("\t\t\t\t</tbody>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t</tbody>\r\n");
      out.write("</table>\r\n");
      out.write("</form>\r\n");
      out.write("<br>\r\n");
      out.write("<br>\r\n");
      out.write("<br>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      if (_jspxFactory != null) _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_005fremove_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:remove
    org.apache.taglibs.standard.tag.common.core.RemoveTag _jspx_th_c_005fremove_005f0 = (org.apache.taglibs.standard.tag.common.core.RemoveTag) _005fjspx_005ftagPool_005fc_005fremove_005fvar_005fscope_005fnobody.get(org.apache.taglibs.standard.tag.common.core.RemoveTag.class);
    _jspx_th_c_005fremove_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fremove_005f0.setParent(null);
    _jspx_th_c_005fremove_005f0.setVar("errorInfo");
    _jspx_th_c_005fremove_005f0.setScope("session");
    int _jspx_eval_c_005fremove_005f0 = _jspx_th_c_005fremove_005f0.doStartTag();
    if (_jspx_th_c_005fremove_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fremove_005fvar_005fscope_005fnobody.reuse(_jspx_th_c_005fremove_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fremove_005fvar_005fscope_005fnobody.reuse(_jspx_th_c_005fremove_005f0);
    return false;
  }
}
