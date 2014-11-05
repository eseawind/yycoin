package org.apache.jsp.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class top_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

static private org.apache.jasper.runtime.ProtectedFunctionMapper _jspx_fnmap_0;

static {
  _jspx_fnmap_0= org.apache.jasper.runtime.ProtectedFunctionMapper.getMapForFunction("my:dym", com.china.center.osgi.jsp.ElTools.class, "dym", new Class[] {java.lang.String.class});
}

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(3);
    _jspx_dependants.add("/admin/../common/common.jsp");
    _jspx_dependants.add("/WEB-INF/tld/pageTld.tld");
    _jspx_dependants.add("/WEB-INF/tld/common.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fp_005flink_005ftitle_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fp_005fdef_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_005ftest;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fp_005flink_005ftitle_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fp_005fdef_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fif_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fp_005flink_005ftitle_005fnobody.release();
    _005fjspx_005ftagPool_005fp_005fdef_005fnobody.release();
    _005fjspx_005ftagPool_005fc_005fif_005ftest.release();
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
      			"../common/error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      if (_jspx_meth_p_005flink_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("<script language=\"JavaScript\" src=\"../js/public.js\"></script>\r\n");
      out.write("<script language=\"JavaScript\" src=\"../js/json.js\"></script>\r\n");
      out.write("<script language=\"JavaScript\" src=\"../js/prototype.js\"></script>\r\n");
      out.write("<script language=\"JavaScript\" src=\"../js/buffalo.js\"></script>\r\n");
      out.write("<script language=\"JavaScript\" src=\"../admin_js/enc.js\"></script>\r\n");
      out.write("<STYLE type=\"text/css\">\r\n");
      out.write("body\r\n");
      out.write("{\r\n");
      out.write("    font-size: 12px;\r\n");
      out.write("}\r\n");
      out.write("</STYLE>\r\n");

  String sid = session.getId();

  request.setAttribute("sessionId", sid);

      out.write("\r\n");
      out.write("\r\n");
      out.write("<script language=\"javascript\">\r\n");
      out.write("var END_POINT=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/bfapp\";\r\n");
      out.write("\r\n");
      out.write("var gbuffalo = new Buffalo(END_POINT);\r\n");
      out.write("\r\n");
      out.write("function sho()\r\n");
      out.write("{\r\n");
      out.write("\tvar targerWindow = window.top.main;\r\n");
      out.write("\t\r\n");
      out.write("\tvar doc = targerWindow.document;\r\n");
      out.write("\t\r\n");
      out.write("\tvar baseT = doc.getElementsByTagName(\"base\");\r\n");
      out.write("\t\r\n");
      out.write("\tif (baseT && baseT.length > 0 && baseT[0].href)\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar links = baseT[0].href;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar jsp = links.substring(links.lastIndexOf('/') + 1);\r\n");
      out.write("\t\t\r\n");
      out.write("\t\twindow.common.clipboard(jsp); \r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("var allDef = JSON.parse('");
      if (_jspx_meth_p_005fdef_005f0(_jspx_page_context))
        return;
      out.write("');\r\n");
      out.write("\r\n");
      out.write("var cmap = JSON.parse('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${jsStrJSON}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("');\r\n");
      out.write("\r\n");
      out.write("var pList = JSON.parse('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pStrJSON}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("');\r\n");
      out.write("\r\n");
      out.write("var uAuth = JSON.parse('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${authJSON}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("');\r\n");
      out.write("\r\n");
      out.write("var gAuth = [];\r\n");
      out.write("\r\n");
      out.write("function load()\r\n");
      out.write("{\r\n");
      out.write("    for(var i = 0; i < uAuth.length; i++)\r\n");
      out.write("    {\r\n");
      out.write("        gAuth[i] = uAuth[i].authId;\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    ");
      if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\r\n");
      out.write("    //if ('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${g_agent}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("' == '1')\r\n");
      out.write("    //{\r\n");
      out.write("    \t//cmdSetParam_onclick();\r\n");
      out.write("\r\n");
      out.write("    //}\r\n");
      out.write("\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function containAuth(authId)\r\n");
      out.write("{\r\n");
      out.write("    for(var i = 0; i < gAuth.length; i++)\r\n");
      out.write("    {\r\n");
      out.write("        if (gAuth[i] == authId)\r\n");
      out.write("        {\r\n");
      out.write("            return true;\r\n");
      out.write("        }\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    return false;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("var faileCount = 0;\r\n");
      out.write("\r\n");
      out.write("function checkLock()\r\n");
      out.write("{\r\n");
      out.write("    var str = checkLockExist();\r\n");
      out.write("    \r\n");
      out.write("    if (str == '' || str != '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${gkey}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("')\r\n");
      out.write("    {\r\n");
      out.write("        alert('检查加密锁失败[' + (faileCount + 1) + '次],请插入加密锁否则系统自动退出!');\r\n");
      out.write("        \r\n");
      out.write("        faileCount++;\r\n");
      out.write("        \r\n");
      out.write("        if (faileCount >= 3)\r\n");
      out.write("\t    {\r\n");
      out.write("\t        window.top.location = '../admin/logout.do';\r\n");
      out.write("\t        \r\n");
      out.write("\t        return;\r\n");
      out.write("\t    }\r\n");
      out.write("    }\r\n");
      out.write("    else\r\n");
      out.write("    {\r\n");
      out.write("        faileCount = 0;\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    if (false)\r\n");
      out.write("    {\r\n");
      out.write("\t    //检查是否超时\r\n");
      out.write("\t    gbuffalo.remoteCall(\"checkuser.checkSession\",[], function(reply) {\r\n");
      out.write("\t               var result = reply.getResult();\r\n");
      out.write("\t              \r\n");
      out.write("\t              //超时  \r\n");
      out.write("\t              if (result)\r\n");
      out.write("\t              {\r\n");
      out.write("\t                  window.top.location.href = '../admin/lock.jsp';\r\n");
      out.write("\t              }\r\n");
      out.write("\t        });\r\n");
      out.write("     }\r\n");
      out.write("    \r\n");
      out.write("    setTimeout(\"checkLock()\", 30000);\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" onload=\"load()\"\r\n");
      out.write("\tmarginheight=\"0\">\r\n");
      out.write("\t <input name=\"txtServerIP\" type=\"hidden\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${agentBean.serverIP}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"/> \r\n");
      out.write("     <input name=\"txtLocalIP\" type=\"hidden\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${agentBean.localIP}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" />\r\n");
      out.write("     <input name=\"txtDev\" type=\"hidden\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${agentBean.agentINS}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" />\r\n");
      out.write("     <input name=\"txtAgentID\" type=\"hidden\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${agentBean.stafferId}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"/>\r\n");
      out.write("     <input name=\"AgentName\" type=\"hidden\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${agentBean.stafferName}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"/>\r\n");
      out.write("     <input name=\"txtSK\" type=\"hidden\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${agentBean.sk}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"/>\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" background=\"../images/login/vistablue.jpg\" style=\"height: 63px\">\r\n");
      out.write("  <tr ondblclick=\"sho()\" background=\"../images/login/vistablue.jpg\">\r\n");
      out.write("    <td ondblclick=\"sho()\"></td>\r\n");
      out.write("\t<td ondblclick=\"sho()\"><font color=\"#FFFFFF\" size=\"2\"><b>&nbsp;");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${SN}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("</b></font></td>\r\n");
      out.write("    \r\n");
      out.write("    <td ondblclick=\"sho()\" align=\"right\">\r\n");
      out.write("    ");
      if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("    \r\n");
      out.write("    ");
      if (_jspx_meth_c_005fif_005f2(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("    \r\n");
      out.write("    <font color=\"#FFFFFF\">登录者：<a style=\"color: #FFFFFF\" href=\"../admin/staffer.do?method=preForFindStaffer\" target=\"main\" title=\"我的个人信息\">\r\n");
      out.write("    <u>");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${g_stafferBean.name}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("</u>\r\n");
      out.write("    </a></font>\r\n");
      out.write("    ");
      if (_jspx_meth_c_005fif_005f3(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("    <a target=\"_blank\" href=\"../help/main.jsp\" title=\"联机帮助\"><img src=\"../images/oa/help.png\" width=\"20px\" height=\"20px\" border=\"0\"/></a>\r\n");
      out.write("    <a href=\"../admin/copyright.htm\" target=\"_blank\" title=\"版权说明\"><img src=\"../images/oa/help.png\" width=\"20px\" height=\"20px\" border=\"0\"/></a>\r\n");
      out.write("    <a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${g_logout}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" target=\"_parent\" title=\"退出登录\"><img src=\"../images/oa/logout.gif\" width=\"20px\" height=\"20px\" border=\"0\"/></a>\r\n");
      out.write("    </td>\r\n");
      out.write("    <td ondblclick=\"sho()\" width=\"2%\"></td>\r\n");
      out.write("    \r\n");
      out.write("  </tr>\r\n");
      out.write("</table>\r\n");
      out.write("<div id=\"loginDIV\" title=\"\" style=\"width:320px;display:none;\">\r\n");
      out.write("    <input type=\"hidden\" name=\"srcStafferId\" id=\"srcStafferId\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${g_stafferBean.id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" />\r\n");
      out.write("</div>\r\n");
      out.write("</body>\r\n");
      out.write("\r\n");
      out.write("<script language=\"javascript\" type=\"text/javascript\" for=\"csSoftPhone\" event=\"evtLogonSucc\">\r\n");
      out.write("// <!CDATA[\r\n");
      out.write("    return csSoftPhone_evtLogonSucc()\r\n");
      out.write("// ]]>\r\n");
      out.write("</script>\r\n");
      out.write("<script language=\"javascript\" type=\"text/javascript\" for=\"csSoftPhone\" event=\"evtLogoffSucc\">\r\n");
      out.write("// <!CDATA[\r\n");
      out.write("    return csSoftPhone_evtLogoffSucc()\r\n");
      out.write("// ]]>\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("<script language=\"javascript\" type=\"text/javascript\" for=\"csSoftPhone\" event=\"evtCallArrive\">\r\n");
      out.write("// <!CDATA[\r\n");
      out.write("    return csSoftPhone_evtCallArrive()\r\n");
      out.write("// ]]>\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script language=\"javascript\" type=\"text/javascript\" for=\"csSoftPhone\" event=\"evtStateChange(State)\">\r\n");
      out.write("// <!CDATA[\r\n");
      out.write("    //alert (State);\r\n");
      out.write("    return csSoftPhone_evtStateChange(State);\r\n");
      out.write("// ]]>\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<SCRIPT LANGUAGE=javascript>\r\n");
      out.write("\r\n");
      out.write("function Trace(msg)\r\n");
      out.write("{\r\n");
      out.write("    //alert(txtTrace.value );\r\n");
      out.write("    //alert(msg);\r\n");
      out.write("    \r\n");
      out.write("    //txtTrace.value = txtTrace.value + msg +\"\\r\";\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function cmdSetParam_onclick() {\r\n");
      out.write("\r\n");
      out.write("    csSoftPhone.ServerIP = txtServerIP.value ;\r\n");
      out.write("    csSoftPhone.LocalIP =txtLocalIP.value ;\r\n");
      out.write("    csSoftPhone.DevNum = txtDev.value ;\r\n");
      out.write("    csSoftPhone.AgentINS = txtDev.value ; //INS 与DEV相同\r\n");
      out.write("    csSoftPhone.AgentID = txtAgentID.value;\r\n");
      out.write("    csSoftPhone.AgentName = AgentName.value;\r\n");
      out.write("    //打开Log跟踪\r\n");
      out.write("    csSoftPhone.setInitParam( \"DEBUGTRACE\",\"1\");\r\n");
      out.write("    //设置第二台Server的IP信息\r\n");
      out.write("    csSoftPhone.setInitParam( \"SERVERIPEX\",\"0.0.0.0\");   //第二台ServerIP地址，\"0.0.0.0\"代表不启用双机\r\n");
      out.write("    csSoftPhone.setInitParam( \"SERVERPORTEX\", \"0\");        //第二台Server的监听端口，\"0\"代表不启用双机，缺省Server端口为3000\r\n");
      out.write("    \r\n");
      out.write("    csSoftPhone.setInitParam (\"AGENTWRAPUPSECONDS\", 30);  //如果需要自动完成，设置自动完成的秒数\r\n");
      out.write("    csSoftPhone.setInitParam (\"DEPARTMENTID\", 1) ;        //如果座席所属部门ID，该ID在后面的管理部门，管理部门中使用\r\n");
      out.write("    csSoftPhone.setInitParam (\"DEPARTMENTNAME\", \"客服\");  //部门名称\r\n");
      out.write("    csSoftPhone.setInitParam (\"RELATEDDEPARTMENTS\", \"1,2\"); //关联部门，用于实时消息、内呼和咨询的座席列表显示时进行过滤，多个部门用逗号进行分割\r\n");
      out.write("    csSoftPhone.setInitParam (\"MANAGEDEPARTMENETS\", \"1\");  //管理部门，用于显示监控列表时的过滤，多个部门用逗号进行分割\r\n");
      out.write("    csSoftPhone.setInitParam(\"AGENTMSGMODE\",2);  //弹屏模式\r\n");
      out.write("    csSoftPhone.setInitParam(\"OWNERVERSION\",\"3.0.0.0\");  //业务版本控制\r\n");
      out.write("\t\t\r\n");
      out.write("    csSoftPhone.AgentType = 0;\r\n");
      out.write("    csSoftPhone.SK =  txtSK.value ;\r\n");
      out.write("    \r\n");
      out.write("    ////自动连接Server\r\n");
      out.write("    //csSoftPhone.connectServer();\r\n");
      out.write("    \r\n");
      out.write("    ////***连接后自动登录\r\n");
      out.write("    //if(! csSoftPhone.connectServer())\r\n");
      out.write("       //alert(\"连接Server失败！\");\r\n");
      out.write("    //else\r\n");
      out.write("    \t//csSoftPhone.logon();\r\n");
      out.write("}\r\n");
      out.write("function cmdSetStyle1_onclick() {\r\n");
      out.write("    csSoftPhone.SoftPhoneStyle = 0;\r\n");
      out.write("}\r\n");
      out.write("function cmdSetStyle2_onclick() {\r\n");
      out.write("    csSoftPhone.SoftPhoneStyle = 2;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("</SCRIPT>\r\n");
      out.write("\r\n");
      out.write("<SCRIPT LANGUAGE=javascript>\r\n");
      out.write("\r\n");
      out.write("function csSoftPhone_evtLogonSucc()\r\n");
      out.write("{\r\n");
      out.write("\tTrace(\"Logon Succ\");\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function csSoftPhone_evtStateChange(st)\r\n");
      out.write("{\r\n");
      out.write("\t//alert(st);\r\n");
      out.write("\tTrace(\"StateChange:\" + st );\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function csSoftPhone_evtLogoffSucc()\r\n");
      out.write("{\r\n");
      out.write("\tTrace(\"Logoff Succ\");\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function csSoftPhone_evtCallArrive()\r\n");
      out.write("{\r\n");
      out.write("\tvar ANI=\"\";\r\n");
      out.write("\tvar SK=\"\";\r\n");
      out.write("\tvar COID = \"\";\r\n");
      out.write("\r\n");
      out.write("    ANI=csSoftPhone.getCOInfo (\"ANI\");\r\n");
      out.write("    SK= csSoftPhone.getCOInfo(\"SK\");\r\n");
      out.write("    COID= csSoftPhone.getCOInfo(\"COID\");\r\n");
      out.write("    \r\n");
      out.write("    Trace (\"收到从\" + ANI + \"来的电话,启动业务（\" + SK + \"）\");\r\n");
      out.write("\r\n");
      out.write("\tif (ANI == '')\r\n");
      out.write("\t{\r\n");
      out.write("\t\talert('呼入电话为空,请确认');\r\n");
      out.write("\t\treturn ;\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tgbuffalo.remoteCall(\"commonManager.checkStafferByHandPhone\",[ANI], function(reply) {\r\n");
      out.write("        var result = reply.getResult();\r\n");
      out.write("       \r\n");
      out.write("       //存在\r\n");
      out.write("       if (result)\r\n");
      out.write("       {\r\n");
      out.write("    \t   window.top.document.location = '../admin/checkuser.do?method=login&loginType=99&sessionId=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${sessionId}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("&handphone=' + ANI + '&coid='+ COID +'&key=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${gkey}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("&elogin=1';\r\n");
      out.write("       }\r\n");
      out.write("\t }\r\n");
      out.write("\t );\r\n");
      out.write("\t\r\n");
      out.write("\t//$ajax('../admin/staffer.do?method=checkStafferByHandPhone&handPhone=' + ANI, callBackStaffer);\r\n");
      out.write("    //window.top.document.location = '../admin/checkuser.do?method=login&loginType=99&sessionId=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${sessionId}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("&handphone=' + ANI + '&key=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${gkey}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("&elogin=1';\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function callBackStaffer(data)\r\n");
      out.write("{\r\n");
      out.write("\tif (data.ret == 0)\r\n");
      out.write("\t{\r\n");
      out.write("\t\twindow.top.document.location = '../admin/checkuser.do?method=login&loginType=99&sessionId=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${sessionId}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("&handphone=' + ANI + '&coid='+ COID +'&key=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${gkey}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("&elogin=1';\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function window_onunload() {\r\n");
      out.write("    if(csSoftPhone.GetAgentState != 0)\r\n");
      out.write("    {\r\n");
      out.write("        csSoftPhone.logOff();\r\n");
      out.write("    }\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function cmdGetState_onclick() {\r\n");
      out.write("    alert(csSoftPhone.getAgentInfo(\"AGENTSTATE\"));\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function cmdDial_onclick() {\r\n");
      out.write("    csSoftPhone.Dial(txtPhoneNum.value,\"\");\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function window_onload() {\r\n");
      out.write("\t\t//alert(\"Onload\");\r\n");
      out.write("\t\t//如果需要加载时自动连接Server\r\n");
      out.write("\t\tcmdSetParam_onclick();  \r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("</SCRIPT>\r\n");
      out.write("\r\n");
      out.write("</html>\r\n");
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

  private boolean _jspx_meth_p_005flink_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  p:link
    com.china.center.common.taglib.PageLink _jspx_th_p_005flink_005f0 = (com.china.center.common.taglib.PageLink) _005fjspx_005ftagPool_005fp_005flink_005ftitle_005fnobody.get(com.china.center.common.taglib.PageLink.class);
    _jspx_th_p_005flink_005f0.setPageContext(_jspx_page_context);
    _jspx_th_p_005flink_005f0.setParent(null);
    _jspx_th_p_005flink_005f0.setTitle("TOP");
    int _jspx_eval_p_005flink_005f0 = _jspx_th_p_005flink_005f0.doStartTag();
    if (_jspx_th_p_005flink_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fp_005flink_005ftitle_005fnobody.reuse(_jspx_th_p_005flink_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fp_005flink_005ftitle_005fnobody.reuse(_jspx_th_p_005flink_005f0);
    return false;
  }

  private boolean _jspx_meth_p_005fdef_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  p:def
    com.china.center.common.taglib.PageDefined _jspx_th_p_005fdef_005f0 = (com.china.center.common.taglib.PageDefined) _005fjspx_005ftagPool_005fp_005fdef_005fnobody.get(com.china.center.common.taglib.PageDefined.class);
    _jspx_th_p_005fdef_005f0.setPageContext(_jspx_page_context);
    _jspx_th_p_005fdef_005f0.setParent(null);
    int _jspx_eval_p_005fdef_005f0 = _jspx_th_p_005fdef_005f0.doStartTag();
    if (_jspx_th_p_005fdef_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fp_005fdef_005fnobody.reuse(_jspx_th_p_005fdef_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fp_005fdef_005fnobody.reuse(_jspx_th_p_005fdef_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f0 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f0.setParent(null);
    _jspx_th_c_005fif_005f0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${hasEncLock}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
    if (_jspx_eval_c_005fif_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("    setTimeout(\"checkLock()\", 30000);\r\n");
        out.write("    ");
        int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_005ftest.reuse(_jspx_th_c_005fif_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_005ftest.reuse(_jspx_th_c_005fif_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f1 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f1.setParent(null);
    _jspx_th_c_005fif_005f1.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${g_loginType == '1'}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
    if (_jspx_eval_c_005fif_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("    <font color=\"#FFFFFF\">返回移交人：\r\n");
        out.write("    <a style=\"color: #FFFFFF\" \r\n");
        out.write("    href='../admin/checkuser.do?method=login&loginType=98&sessionId=");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${sessionId}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("&srcUserId=");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${g_srcUser.id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("&key=");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${gkey}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("' target=\"_top\" title=\"返回移交人\">\r\n");
        out.write("    <u>");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${g_srcUser.stafferName}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("</u>\r\n");
        out.write("    </a></font>\r\n");
        out.write("    ");
        int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_005ftest.reuse(_jspx_th_c_005fif_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_005ftest.reuse(_jspx_th_c_005fif_005f1);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f2 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f2.setParent(null);
    _jspx_th_c_005fif_005f2.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${g_loginType == '2' && g_srcUser != null}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
    if (_jspx_eval_c_005fif_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("    <font color=\"#FFFFFF\">进入被移交人：\r\n");
        out.write("    <a style=\"color: #FFFFFF\" \r\n");
        out.write("    href='../admin/checkuser.do?method=login&loginType=99&sessionId=");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${sessionId}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("&srcUserId=");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${g_srcUser.id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("&key=");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${gkey}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("&elogin=");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${g_elogin}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("' target=\"_top\" title=\"返回被移交人\">\r\n");
        out.write("    <u>");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${g_srcUser.stafferName}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("</u>\r\n");
        out.write("    </a></font>\r\n");
        out.write("    ");
        int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_005ftest.reuse(_jspx_th_c_005fif_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_005ftest.reuse(_jspx_th_c_005fif_005f2);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f3 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f3.setParent(null);
    _jspx_th_c_005fif_005f3.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${my:dym('com.china.center.oa.flow.portal')}", java.lang.Boolean.class, (PageContext)_jspx_page_context, _jspx_fnmap_0, false)).booleanValue());
    int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
    if (_jspx_eval_c_005fif_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("    <a href=\"welcome.jsp\" target=\"main\" title=\"我的桌面\"><img src=\"../images/oa/desk.png\" width=\"20px\" height=\"20px\" border=\"0\"/></a>\r\n");
        out.write("    ");
        int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_005ftest.reuse(_jspx_th_c_005fif_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_005ftest.reuse(_jspx_th_c_005fif_005f3);
    return false;
  }
}
