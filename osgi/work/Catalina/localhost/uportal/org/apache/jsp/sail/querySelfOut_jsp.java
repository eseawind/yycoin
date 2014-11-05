package org.apache.jsp.sail;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class querySelfOut_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

static private org.apache.jasper.runtime.ProtectedFunctionMapper _jspx_fnmap_0;
static private org.apache.jasper.runtime.ProtectedFunctionMapper _jspx_fnmap_1;
static private org.apache.jasper.runtime.ProtectedFunctionMapper _jspx_fnmap_2;
static private org.apache.jasper.runtime.ProtectedFunctionMapper _jspx_fnmap_3;

static {
  _jspx_fnmap_0= org.apache.jasper.runtime.ProtectedFunctionMapper.getMapForFunction("my:length", com.china.center.osgi.jsp.ElTools.class, "length", new Class[] {java.lang.Object.class});
  _jspx_fnmap_1= org.apache.jasper.runtime.ProtectedFunctionMapper.getMapForFunction("my:get", com.china.center.osgi.jsp.ElTools.class, "get", new Class[] {java.lang.String.class, int.class});
  _jspx_fnmap_2= org.apache.jasper.runtime.ProtectedFunctionMapper.getMapForFunction("my:formatNum", com.china.center.osgi.jsp.ElTools.class, "formatNum", new Class[] {double.class});
  _jspx_fnmap_3= org.apache.jasper.runtime.ProtectedFunctionMapper.getMapForFunction("my:auth", com.china.center.osgi.jsp.ElTools.class, "auth", new Class[] {com.center.china.osgi.publics.User.class, java.lang.String.class});
}

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(3);
    _jspx_dependants.add("/sail/../common/common.jsp");
    _jspx_dependants.add("/WEB-INF/tld/pageTld.tld");
    _jspx_dependants.add("/WEB-INF/tld/common.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fp_005flink_005ftitle_005flink_005fguid_005fdialog_005fcal_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_005fvarStatus_005fvar_005fitems;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_005fvar_005fvalue_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fp_005fnavigation_005fheight;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fp_005fplugin_005fvalue_005fsize_005fname_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fp_005fplugin_005fvalue_005ftype_005fsize_005fname_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_005ftest;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fp_005foption_005ftype_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fp_005fformTurning_005fmethod_005fform_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fp_005fmessage2_005fnobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fp_005flink_005ftitle_005flink_005fguid_005fdialog_005fcal_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fforEach_005fvarStatus_005fvar_005fitems = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fset_005fvar_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fp_005fnavigation_005fheight = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fp_005fplugin_005fvalue_005fsize_005fname_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fp_005fplugin_005fvalue_005ftype_005fsize_005fname_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fif_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fp_005foption_005ftype_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fp_005fformTurning_005fmethod_005fform_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fp_005fmessage2_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fp_005flink_005ftitle_005flink_005fguid_005fdialog_005fcal_005fnobody.release();
    _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.release();
    _005fjspx_005ftagPool_005fc_005fforEach_005fvarStatus_005fvar_005fitems.release();
    _005fjspx_005ftagPool_005fc_005fset_005fvar_005fvalue_005fnobody.release();
    _005fjspx_005ftagPool_005fp_005fnavigation_005fheight.release();
    _005fjspx_005ftagPool_005fp_005fplugin_005fvalue_005fsize_005fname_005fnobody.release();
    _005fjspx_005ftagPool_005fp_005fplugin_005fvalue_005ftype_005fsize_005fname_005fnobody.release();
    _005fjspx_005ftagPool_005fc_005fif_005ftest.release();
    _005fjspx_005ftagPool_005fp_005foption_005ftype_005fnobody.release();
    _005fjspx_005ftagPool_005fp_005fformTurning_005fmethod_005fform_005fnobody.release();
    _005fjspx_005ftagPool_005fp_005fmessage2_005fnobody.release();
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
      out.write("<script src=\"../js/title_div.js\"></script>\r\n");
      out.write("<script src=\"../js/public.js\"></script>\r\n");
      out.write("<script src=\"../js/JCheck.js\"></script>\r\n");
      out.write("<script src=\"../js/common.js\"></script>\r\n");
      out.write("<script src=\"../js/tableSort.js\"></script>\r\n");
      out.write("<script src=\"../js/jquery/jquery.js\"></script>\r\n");
      out.write("<script src=\"../js/plugin/dialog/jquery.dialog.js\"></script>\r\n");
      out.write("<script src=\"../js/plugin/highlight/jquery.highlight.js\"></script>\r\n");
      out.write("<script src='../js/adapter.js'></script>\r\n");
      out.write("<script language=\"javascript\">\r\n");
      out.write("function detail()\r\n");
      out.write("{\r\n");
      out.write("\tdocument.location.href = '../sail/out.do?method=findOut&outId=' + getRadioValue(\"fullId\");\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function pagePrint()\r\n");
      out.write("{\r\n");
      out.write("\twindow.open('../sail/out.do?method=findOut&fow=4&outId=' + getRadioValue(\"fullId\"));\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function selectCustomer()\r\n");
      out.write("{\r\n");
      out.write("    window.common.modal(\"../client/client.do?method=rptQuerySelfClient&stafferId=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${user.stafferId}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("&load=1\");\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function exports()\r\n");
      out.write("{\r\n");
      out.write("\tif (window.confirm(\"确定导出当前的全部查询的库单?\"))\r\n");
      out.write("\tdocument.location.href = '../sail/out.do?method=export&flags=1';\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function mark()\r\n");
      out.write("{\r\n");
      out.write("\tif (window.confirm(\"确定标记当前选择的库单?\"))\r\n");
      out.write("\t{\r\n");
      out.write("\t\tadminForm.action = '../sail/out.do';\r\n");
      out.write("\t\tadminForm.method.value = 'mark';\r\n");
      out.write("\t\tadminForm.outId.value = getRadioValue(\"fullId\");\r\n");
      out.write("\r\n");
      out.write("\t\tadminForm.submit();\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function comp()\r\n");
      out.write("{\r\n");
      out.write("\tvar now = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${now}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("';\r\n");
      out.write("\r\n");
      out.write("\tvar str1 = $O('outTime').value;\r\n");
      out.write("\r\n");
      out.write("\tvar str2 = $O('outTime1').value;\r\n");
      out.write("\r\n");
      out.write("\t//必须要有开始和结束时间一个\r\n");
      out.write("\tif (str1 == '' && str2 == '')\r\n");
      out.write("\t{\r\n");
      out.write("\t\talert('必须要有开始和结束时间一个');\r\n");
      out.write("\t\treturn false;\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tif (str1 != '' && str2 == '')\r\n");
      out.write("\t{\r\n");
      out.write("\t\tif (!coo(str1, now))\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\talert('查询日期跨度不能大于3个月(90天)!');\r\n");
      out.write("\t\t\treturn false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t$O('outTime1').value = now;\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tif (str1 == '' && str2 != '')\r\n");
      out.write("\t{\r\n");
      out.write("\t\tif (!coo(now, str2))\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\talert('查询日期跨度不能大于3个月(90天)!');\r\n");
      out.write("\t\t\treturn false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t\t$O('outTime').value = now;\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tif (str1 != '' && str2 != '')\r\n");
      out.write("\t{\r\n");
      out.write("\t\tif (!coo(str1, str2))\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\talert('查询日期跨度不能大于3个月(90天)!');\r\n");
      out.write("\t\t\treturn false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\treturn true;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function comp3()\r\n");
      out.write("{\r\n");
      out.write("\tvar now = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${now}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("';\r\n");
      out.write("\r\n");
      out.write("\tvar str1 = $O('redateB').value;\r\n");
      out.write("\r\n");
      out.write("\tvar str2 = $O('redateE').value;\r\n");
      out.write("\t\r\n");
      out.write("\tif (str1 != '' && str2 == '')\r\n");
      out.write("    {\r\n");
      out.write("        if (!coo(str1, now))\r\n");
      out.write("        {\r\n");
      out.write("            alert('查询日期跨度不能大于3个月(90天)!');\r\n");
      out.write("            return false;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        $O('redateE').value = now;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    if (str1 == '' && str2 != '')\r\n");
      out.write("    {\r\n");
      out.write("        if (!coo(now, str2))\r\n");
      out.write("        {\r\n");
      out.write("            alert('查询日期跨度不能大于3个月(90天)!');\r\n");
      out.write("            return false;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        $O('redateB').value = now;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    if (str1 != '' && str2 != '')\r\n");
      out.write("    {\r\n");
      out.write("        if (!coo(str1, str2))\r\n");
      out.write("        {\r\n");
      out.write("            alert('查询日期跨度不能大于3个月(90天)!');\r\n");
      out.write("            return false;\r\n");
      out.write("        }\r\n");
      out.write("    }\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("\treturn true;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function coo(str1, str2)\r\n");
      out.write("{\r\n");
      out.write("\tvar s1 = str1.split('-');\r\n");
      out.write("\tvar s2 = str2.split('-');\r\n");
      out.write("\r\n");
      out.write("\tvar year1 = parseInt(s1[0], 10);\r\n");
      out.write("\r\n");
      out.write("\tvar year2 = parseInt(s2[0], 10);\r\n");
      out.write("\r\n");
      out.write("\tvar month1 = parseInt(s1[1], 10);\r\n");
      out.write("\r\n");
      out.write("\tvar month2 = parseInt(s2[1], 10);\r\n");
      out.write("\r\n");
      out.write("\tvar day1 = parseInt(s1[2], 10);\r\n");
      out.write("\r\n");
      out.write("\tvar day2 = parseInt(s2[2], 10);\r\n");
      out.write("\r\n");
      out.write("\treturn Math.abs((year2 - year1) * 365 + (month2 - month1) * 30 + (day2 - day1)) <= 90;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function modfiy()\r\n");
      out.write("{\r\n");
      out.write("\tvar ret = checkCurrentUser();\r\n");
      out.write("\r\n");
      out.write("\tif (!ret)\r\n");
      out.write("\t{\r\n");
      out.write("\t\twindow.parent.location.reload();\r\n");
      out.write("\r\n");
      out.write("\t\treturn;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tif (getRadio('fullId').statuss == '0' || getRadio('fullId').statuss == '2')\r\n");
      out.write("\t{\r\n");
      out.write("\t\tdocument.location.href = '../sail/out.do?method=findOut&outId=' + getRadioValue(\"fullId\") + \"&fow=1\";\r\n");
      out.write("\t}\r\n");
      out.write("\telse\r\n");
      out.write("\t{\r\n");
      out.write("\t\talert('只有保存态和驳回态的库单可以修改!');\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function updateInvoice()\r\n");
      out.write("{\r\n");
      out.write("    if ($$('invoices') != '')\r\n");
      out.write("    {\r\n");
      out.write("        document.location.href = '../sail/out.do?method=updateInvoice&outId=' + getRadioValue(\"fullId\") + \"&invoices=\" + $$('invoices');\r\n");
      out.write("    }\r\n");
      out.write("    else\r\n");
      out.write("    {\r\n");
      out.write("        alert('请选择发票类型');\r\n");
      out.write("    }\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("var dutyMap = {};\r\n");
      out.write("\r\n");
      if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\r\n");
      out.write("function showInvoice(dutyId)\r\n");
      out.write("{\r\n");
      out.write("    var list = document.getElementsByTagName('p');\r\n");
      out.write("    \r\n");
      out.write("    var iList = dutyMap[dutyId];\r\n");
      out.write("    \r\n");
      out.write("    for (var i = 0; i < list.length; i++)\r\n");
      out.write("    {\r\n");
      out.write("        var ele = list[i];\r\n");
      out.write("        \r\n");
      out.write("        if (isIn(iList, ele.id))\r\n");
      out.write("        {\r\n");
      out.write("            $v(ele, true);\r\n");
      out.write("        }\r\n");
      out.write("        else\r\n");
      out.write("        {\r\n");
      out.write("            $v(ele, false);\r\n");
      out.write("        }\r\n");
      out.write("    }\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function isIn(list, id)\r\n");
      out.write("{\r\n");
      out.write("    if (id.indexOf('s_') != 0)\r\n");
      out.write("    {\r\n");
      out.write("        return true;\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    for (var i = 0; i < list.length; i++)\r\n");
      out.write("    {\r\n");
      out.write("        if (('s_' + list[i]) == id)\r\n");
      out.write("        {\r\n");
      out.write("            return true;\r\n");
      out.write("        }\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    return false;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function preUpdateInvoice()\r\n");
      out.write("{\r\n");
      out.write("\tvar ret = checkCurrentUser();\r\n");
      out.write("\r\n");
      out.write("\tif (!ret)\r\n");
      out.write("\t{\r\n");
      out.write("\t\twindow.parent.location.reload();\r\n");
      out.write("\r\n");
      out.write("\t\treturn;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("    if (getRadio('fullId').pinvoiceid == '')\r\n");
      out.write("    {\r\n");
      out.write("        alert('销售单开单选择不开发票,不能修改开票类型!');\r\n");
      out.write("        return;\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    if (parseFloat(getRadio('fullId').pinvoicemoney) == 0)\r\n");
      out.write("    {\r\n");
      out.write("        showInvoice(getRadio('fullId').pdutyid);\r\n");
      out.write("        \r\n");
      out.write("        setRadioValue('invoices', getRadio('fullId').pinvoiceid);\r\n");
      out.write("        \r\n");
      out.write("        $('#dlg').dialog({closed:false});\r\n");
      out.write("    }\r\n");
      out.write("    else\r\n");
      out.write("    {\r\n");
      out.write("        alert('已经开票,不能修改!');\r\n");
      out.write("    }\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function del()\r\n");
      out.write("{\r\n");
      out.write("\tvar ret = checkCurrentUser();\r\n");
      out.write("\r\n");
      out.write("\tif (!ret)\r\n");
      out.write("\t{\r\n");
      out.write("\t\twindow.parent.location.reload();\r\n");
      out.write("\r\n");
      out.write("\t\treturn;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tif (getRadio('fullId').statuss == '0' || getRadio('fullId').statuss == '2' || getRadio('fullId').temptype == '1')\r\n");
      out.write("\t{\r\n");
      out.write("\t\t if (window.confirm(\"确定删除销售单?\"))\r\n");
      out.write("\t\tdocument.location.href = '../sail/out.do?method=delOut&outId=' + getRadioValue(\"fullId\");\r\n");
      out.write("\t}\r\n");
      out.write("\telse\r\n");
      out.write("\t{\r\n");
      out.write("\t\talert('只有保存态和驳回态的库单可以删除!');\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function balance()\r\n");
      out.write("{\r\n");
      out.write("\tvar ret = checkCurrentUser();\r\n");
      out.write("\r\n");
      out.write("\tif (!ret)\r\n");
      out.write("\t{\r\n");
      out.write("\t\twindow.parent.location.reload();\r\n");
      out.write("\r\n");
      out.write("\t\treturn;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("    if ((getRadio('fullId').statuss == '3' || getRadio('fullId').statuss == '4') && getRadio('fullId').outtype == '3')\r\n");
      out.write("    {\r\n");
      out.write("        document.location.href = '../sail/out.do?method=preForAddOutBalance&type=0&outId=' + getRadioValue(\"fullId\");\r\n");
      out.write("    }\r\n");
      out.write("    else\r\n");
      out.write("    {\r\n");
      out.write("        alert('不能操作!');\r\n");
      out.write("    }\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function tran()\r\n");
      out.write("{\r\n");
      out.write("\tvar ret = checkCurrentUser();\r\n");
      out.write("\r\n");
      out.write("\tif (!ret)\r\n");
      out.write("\t{\r\n");
      out.write("\t\twindow.parent.location.reload();\r\n");
      out.write("\r\n");
      out.write("\t\treturn;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("    if ((getRadio('fullId').statuss == '3' || getRadio('fullId').statuss == '4') && getRadio('fullId').ppay == '1')\r\n");
      out.write("    {\r\n");
      out.write("        if (window.confirm(\"确定申请移交此销售单到自己名下?\"))\r\n");
      out.write("        document.location.href = '../sail/out.do?method=tranOut&outId=' + getRadioValue(\"fullId\");\r\n");
      out.write("    }\r\n");
      out.write("    else\r\n");
      out.write("    {\r\n");
      out.write("        alert('必须选择已经完全付款的单据,不能操作!');\r\n");
      out.write("    }\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function refBill()\r\n");
      out.write("{\r\n");
      out.write("\tvar ret = checkCurrentUser();\r\n");
      out.write("\r\n");
      out.write("\tif (!ret)\r\n");
      out.write("\t{\r\n");
      out.write("\t\twindow.parent.location.reload();\r\n");
      out.write("\r\n");
      out.write("\t\treturn;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("    if (getRadio('fullId').statuss != '2' && getRadio('fullId').statuss != '0' \r\n");
      out.write("        && getRadio('fullId').outtype != 1 && getRadio('fullId').outtype != 3 && getRadio('fullId').outtype != 5)\r\n");
      out.write("    {\r\n");
      out.write("        document.location.href = '../finance/bank.do?method=preForRefBill&outId=' + getRadioValue(\"fullId\") + '&customerId=' + getRadio('fullId').pcustomerid;\r\n");
      out.write("    }\r\n");
      out.write("    else\r\n");
      out.write("    {\r\n");
      out.write("        if (getRadio('fullId').outtype == 1 || getRadio('fullId').outtype == 3)\r\n");
      out.write("        alert('个人领样和委托代销不走此勾款流程!\\r\\n委托代销通过委托代销结算/退货勾款,\\r\\n个人领样通过退库和转销售勾款.');\r\n");
      out.write("        else\r\n");
      out.write("        alert('不能操作!');\r\n");
      out.write("    }\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function balance2()\r\n");
      out.write("{\r\n");
      out.write("\tvar ret = checkCurrentUser();\r\n");
      out.write("\r\n");
      out.write("\tif (!ret)\r\n");
      out.write("\t{\r\n");
      out.write("\t\twindow.parent.location.reload();\r\n");
      out.write("\r\n");
      out.write("\t\treturn;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("    if ((getRadio('fullId').statuss == '3' || getRadio('fullId').statuss == '4')\r\n");
      out.write("         && getRadio('fullId').outtype == '3')\r\n");
      out.write("    {\r\n");
      out.write("        document.location.href = '../sail/out.do?method=preForAddOutBalance&type=1&outId=' + getRadioValue(\"fullId\");\r\n");
      out.write("    }\r\n");
      out.write("    else\r\n");
      out.write("    {\r\n");
      out.write("        alert('不能操作!');\r\n");
      out.write("    }\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function sub()\r\n");
      out.write("{\r\n");
      out.write("\tvar ret = checkCurrentUser();\r\n");
      out.write("\r\n");
      out.write("\tif (!ret)\r\n");
      out.write("\t{\r\n");
      out.write("\t\twindow.parent.location.reload();\r\n");
      out.write("\r\n");
      out.write("\t\treturn;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tif (getRadio('fullId').statuss == '0' || getRadio('fullId').statuss == '2')\r\n");
      out.write("\t{\r\n");
      out.write("\t\t if (window.confirm(\"确定提交销售单?\"))\r\n");
      out.write("\t\t {\r\n");
      out.write("\t\t \t$O('method').value = 'modifyOutStatus';\r\n");
      out.write("\t\t \t$O('oldStatus').value = getRadio('fullId').statuss;\r\n");
      out.write("\t\t \t$O('statuss').value = '1';\r\n");
      out.write("\t\t \t$O('outId').value = getRadioValue(\"fullId\");\r\n");
      out.write("\t\t \t\r\n");
      out.write("\t\t \tdisableAllButton();\r\n");
      out.write("\t\t \tadminForm.submit();\r\n");
      out.write("\t\t }\r\n");
      out.write("\t}\r\n");
      out.write("\telse\r\n");
      out.write("\t{\r\n");
      out.write("\t\talert('此状态不能提交!');\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function query()\r\n");
      out.write("{\r\n");
      out.write("\tif (comp() || comp3())\r\n");
      out.write("\t{\r\n");
      out.write("\t\tadminForm.submit();\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function getCustomer(oos)\r\n");
      out.write("{\r\n");
      out.write("    var obj = oos;\r\n");
      out.write("    \r\n");
      out.write("\t$O(\"customerName\").value = obj.pname;\r\n");
      out.write("\t\r\n");
      out.write("\t$O(\"customerId\").value = obj.value;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function res()\r\n");
      out.write("{\r\n");
      out.write("\t$O('customerName').value = '';\r\n");
      out.write("\t$O(\"customerId\").value = '';\r\n");
      out.write("\t$O(\"id\").value = '';\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("var jmap = new Object();\r\n");
      if (_jspx_meth_c_005fforEach_005f2(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\r\n");
      out.write("function showDiv(id)\r\n");
      out.write("{\r\n");
      out.write("\ttooltip.showTable(jmap[id]);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function dialog_open()\r\n");
      out.write("{\r\n");
      out.write("    $v('dia_inner', true);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function load()\r\n");
      out.write("{\r\n");
      out.write("\tloadForm();\r\n");
      out.write("\t\r\n");
      out.write("\ttooltip.init();\r\n");
      out.write("\t\r\n");
      out.write("\tbingTable(\"mainTable\");\r\n");
      out.write("\t\r\n");
      out.write("\thighlights($(\"#mainTable\").get(0), ['驳回'], 'red');\r\n");
      out.write("\t\r\n");
      out.write("\thighlights($(\"#mainTable\").get(0), ['结束'], 'blue');\r\n");
      out.write("\t\r\n");
      out.write("\t$('#dlg').dialog({\r\n");
      out.write("                //iconCls: 'icon-save',\r\n");
      out.write("                modal:true,\r\n");
      out.write("                closed:true,\r\n");
      out.write("                buttons:{\r\n");
      out.write("                    '确 定':function(){\r\n");
      out.write("                        updateInvoice();\r\n");
      out.write("                    },\r\n");
      out.write("                    '取 消':function(){\r\n");
      out.write("                        $('#dlg').dialog({closed:true});\r\n");
      out.write("                    }\r\n");
      out.write("                }\r\n");
      out.write("     });\r\n");
      out.write("     \r\n");
      out.write("     $ESC('dlg');\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function checkCurrentUser()\r\n");
      out.write("{\t\r\n");
      out.write("     // check\r\n");
      out.write("     var elogin = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${g_elogin}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\";\r\n");
      out.write("\r\n");
      out.write(" \t //  商务登陆\r\n");
      out.write("     //if (elogin == '1')\r\n");
      out.write("     //{\r\n");
      out.write("          var top = window.top.topFrame.document;\r\n");
      out.write("          //var allDef = window.top.topFrame.allDef;\r\n");
      out.write("          var srcStafferId = top.getElementById('srcStafferId').value;\r\n");
      out.write("         \r\n");
      out.write("          var currentStafferId = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${g_stafferBean.id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\";\r\n");
      out.write("\r\n");
      out.write("          var currentStafferName = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${g_stafferBean.name}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\";\r\n");
      out.write("         \r\n");
      out.write("          if (srcStafferId && srcStafferId != currentStafferId)\r\n");
      out.write("          {\r\n");
      out.write("\r\n");
      out.write("               alert('请不要同时打开多个OA连接，且当前登陆者不同，当前登陆者应为：' + currentStafferName);\r\n");
      out.write("               \r\n");
      out.write("               return false;\r\n");
      out.write("          }\r\n");
      out.write("     //}\r\n");
      out.write("\r\n");
      out.write("\treturn true;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body class=\"body_class\" onkeypress=\"tooltip.bingEsc(event)\" onload=\"load()\">\r\n");
      out.write("<form action=\"../sail/out.do\" name=\"adminForm\"><input type=\"hidden\"\r\n");
      out.write("\tvalue=\"querySelfOut\" name=\"method\"> <input type=\"hidden\" value=\"1\"\r\n");
      out.write("\tname=\"firstLoad\">\r\n");
      out.write("\t<input type=\"hidden\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${customerId}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"\r\n");
      out.write("\tname=\"customerId\">\r\n");
      out.write("\t<input type=\"hidden\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${queryType}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"\r\n");
      out.write("\tname=\"queryType\">\r\n");
      out.write("\t<input type=\"hidden\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${GFlag}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"\r\n");
      out.write("\tname=\"flagg\">\r\n");
      out.write("\t<input type=\"hidden\" value=\"\"\r\n");
      out.write("\tname=\"outId\">\r\n");
      out.write("\t<input type=\"hidden\" value=\"\"\r\n");
      out.write("    name=\"oldStatus\">\r\n");
      out.write("\t<input type=\"hidden\" value=\"\"\r\n");
      out.write("\tname=\"statuss\">\r\n");
      out.write("\t<input type=\"hidden\" value=\"1\"\r\n");
      out.write("    name=\"selfQuery\">\r\n");
      out.write("<input type=\"hidden\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${vtype}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" name=\"vtype\">\r\n");
      if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\r\n");
      if (_jspx_meth_p_005fnavigation_005f0(_jspx_page_context))
        return;
      out.write(" <br>\r\n");
      out.write("\r\n");
      out.write("<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\r\n");
      out.write("\talign=\"center\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td align='center' colspan='2'>\r\n");
      out.write("\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\r\n");
      out.write("\t\t\tclass=\"border\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t<table width=\"100%\" border=\"0\" cellspacing='1'>\r\n");
      out.write("\t\t\t\t\t<tr class=\"content1\">\r\n");
      out.write("\t\t\t\t\t\t<td width=\"15%\" align=\"center\">开始时间</td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\" width=\"35%\">");
      if (_jspx_meth_p_005fplugin_005f0(_jspx_page_context))
        return;
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"15%\" align=\"center\">结束时间</td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\">");
      if (_jspx_meth_p_005fplugin_005f1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\r\n");
      out.write("                    <tr class=\"content2\">\r\n");
      out.write("                        <td width=\"15%\" align=\"center\">回款时间从</td>\r\n");
      out.write("                        <td align=\"center\" width=\"35%\">");
      if (_jspx_meth_p_005fplugin_005f2(_jspx_page_context))
        return;
      out.write("</td>\r\n");
      out.write("                        <td width=\"15%\" align=\"center\">到</td>\r\n");
      out.write("                        <td align=\"center\">");
      if (_jspx_meth_p_005fplugin_005f3(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("                        </td>\r\n");
      out.write("                    </tr>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t<tr class=\"content1\">\r\n");
      out.write("\t\t\t\t\t\t<td width=\"15%\" align=\"center\">销售单状态</td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\">\r\n");
      out.write("\t\t\t\t\t\t");
      if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"15%\" align=\"center\">客户：</td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\"><input type=\"text\" name=\"customerName\" maxlength=\"14\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${customerName}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"\r\n");
      out.write("\t\t\t\t\t\t\tonclick=\"selectCustomer()\" style=\"cursor: pointer;\"\r\n");
      out.write("\t\t\t\t\t\t\treadonly=\"readonly\"></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t<tr class=\"content2\">\r\n");
      out.write("\t\t\t\t\t\t<td width=\"15%\" align=\"center\">销售类型</td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\">\r\n");
      out.write("\t\t\t\t\t\t<select name=\"outType\"\r\n");
      out.write("\t\t\t\t\t\t\tclass=\"select_class\" values=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${outType}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write(">\r\n");
      out.write("\t\t\t\t\t\t\t<option value=\"\">--</option>\r\n");
      out.write("\t\t\t\t\t\t\t");
      if (_jspx_meth_p_005foption_005f1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t\t\t</select>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t<td width=\"15%\" align=\"center\">销售单号</td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\"><input type=\"text\" name=\"id\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t<tr class=\"content1\">\r\n");
      out.write("\t\t\t\t\t\t<td width=\"15%\" align=\"center\">是否回款</td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\" colspan=\"1\"><select name=\"pay\" values=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pay}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"\r\n");
      out.write("\t\t\t\t\t\t\tclass=\"select_class\">\r\n");
      out.write("\t\t\t\t\t\t\t<option value=\"\">--</option>\r\n");
      out.write("\t\t\t\t\t\t\t<option value=\"1\">是</option>\r\n");
      out.write("\t\t\t\t\t\t\t<option value=\"0\">否</option>\r\n");
      out.write("\t\t\t\t\t\t\t<option value=\"2\">超期</option>\r\n");
      out.write("\t\t\t\t\t\t</select></td>\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<td width=\"15%\" align=\"center\">仓库</td>\r\n");
      out.write("                        <td align=\"center\">\r\n");
      out.write("                        <select name=\"location\"\r\n");
      out.write("                            class=\"select_class\" values=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${location}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write(">\r\n");
      out.write("                            <option value=\"\">--</option>\r\n");
      out.write("                            ");
      if (_jspx_meth_c_005fforEach_005f3(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("                        </select>\r\n");
      out.write("                        </td>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t<tr class=\"content2\">\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<td colspan=\"4\" align=\"right\"><input type=\"button\" id=\"query_b\"\r\n");
      out.write("\t\t\t\t\t\t\tonclick=\"query()\" class=\"button_class\"\r\n");
      out.write("\t\t\t\t\t\t\tvalue=\"&nbsp;&nbsp;查 询&nbsp;&nbsp;\">&nbsp;&nbsp;\r\n");
      out.write("\t\t\t\t\t\t\t<input type=\"button\" onclick=\"res()\" class=\"button_class\" value=\"&nbsp;&nbsp;重 置&nbsp;&nbsp;\"></td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td valign=\"top\" colspan='2'>\r\n");
      out.write("\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t\t<!--DWLayoutTable-->\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td width=\"784\" height=\"6\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td align=\"center\" valign=\"top\">\r\n");
      out.write("\t\t\t\t<div align=\"left\">\r\n");
      out.write("\t\t\t\t<table width=\"90%\" border=\"0\" cellspacing=\"2\">\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"10\">\r\n");
      out.write("\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td width=\"35\">&nbsp;</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td width=\"6\"><img src=\"../images/dot_r.gif\" width=\"6\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\theight=\"6\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t<td class=\"caption\"><strong>浏览");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${fg}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("单:</strong><font color=blue>[当前查询数量:");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${my:length(listOut1)}", java.lang.String.class, (PageContext)_jspx_page_context, _jspx_fnmap_0, false));
      out.write("]</font></td>\r\n");
      out.write("\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td background=\"../images/dot_line.gif\" colspan='2'></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td height=\"10\" colspan='2'></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td align='center' colspan='2'>\r\n");
      out.write("\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\r\n");
      out.write("\t\t\tclass=\"border\">\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t<table width=\"100%\" border=\"0\" cellspacing='1' id=\"mainTable\">\r\n");
      out.write("\t\t\t\t\t<tr align=\"center\" class=\"content0\">\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\" width=\"5%\" align=\"center\">选择</td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\" onclick=\"tableSort(this)\" class=\"td_class\">单据编号</td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\" onclick=\"tableSort(this)\" class=\"td_class\">客户</td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\" onclick=\"tableSort(this)\" class=\"td_class\">状态</td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\" onclick=\"tableSort(this)\" class=\"td_class\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${fg}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("类型</td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\" onclick=\"tableSort(this)\" class=\"td_class\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${fg}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("时间</td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\" onclick=\"tableSort(this)\" class=\"td_class\">回款日期</td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\" onclick=\"tableSort(this)\" class=\"td_class\">付款方式</td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\" onclick=\"tableSort(this)\" class=\"td_class\">金额</td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\" onclick=\"tableSort(this)\" class=\"td_class\">付款</td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\" onclick=\"tableSort(this)\" class=\"td_class\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${fg}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("人</td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\" onclick=\"tableSort(this)\" class=\"td_class\">仓库</td>\r\n");
      out.write("\t\t\t\t\t\t<td align=\"center\" onclick=\"tableSort(this)\" class=\"td_class\">发货单</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t");
      if (_jspx_meth_c_005fforEach_005f4(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_p_005fformTurning_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td height=\"10\" colspan='2'></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td background=\"../images/dot_line.gif\" colspan='2'></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td height=\"10\" colspan='2'></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t");
      if (_jspx_meth_c_005fif_005f6(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t<tr height=\"10\">\r\n");
      out.write("\t\t<td height=\"10\" colspan='2'></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t");
      if (_jspx_meth_p_005fmessage2_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("</table>\r\n");
      out.write("\r\n");
      out.write("</form>\r\n");
      out.write("\r\n");
      out.write("<div id=\"dlg\" title=\"选择发票类型\" style=\"width:400px;\">\r\n");
      out.write("    <div style=\"padding:20px;height:200px;display: none;\" id=\"dia_inner\" title=\"\">\r\n");
      out.write("    ");
      if (_jspx_meth_c_005fforEach_005f5(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("    </div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
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
    com.china.center.common.taglib.PageLink _jspx_th_p_005flink_005f0 = (com.china.center.common.taglib.PageLink) _005fjspx_005ftagPool_005fp_005flink_005ftitle_005flink_005fguid_005fdialog_005fcal_005fnobody.get(com.china.center.common.taglib.PageLink.class);
    _jspx_th_p_005flink_005f0.setPageContext(_jspx_page_context);
    _jspx_th_p_005flink_005f0.setParent(null);
    _jspx_th_p_005flink_005f0.setTitle("查询销售单");
    _jspx_th_p_005flink_005f0.setLink(true);
    _jspx_th_p_005flink_005f0.setGuid(true);
    _jspx_th_p_005flink_005f0.setCal(true);
    _jspx_th_p_005flink_005f0.setDialog(true);
    int _jspx_eval_p_005flink_005f0 = _jspx_th_p_005flink_005f0.doStartTag();
    if (_jspx_th_p_005flink_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fp_005flink_005ftitle_005flink_005fguid_005fdialog_005fcal_005fnobody.reuse(_jspx_th_p_005flink_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fp_005flink_005ftitle_005flink_005fguid_005fdialog_005fcal_005fnobody.reuse(_jspx_th_p_005flink_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f0 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f0.setParent(null);
    _jspx_th_c_005fforEach_005f0.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${dutyList}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    _jspx_th_c_005fforEach_005f0.setVar("item");
    int[] _jspx_push_body_count_c_005fforEach_005f0 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
      if (_jspx_eval_c_005fforEach_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("    dutyMap['");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("'] = [];\r\n");
          out.write("    ");
          if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
            return true;
          out.write('\r');
          out.write('\n');
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f0[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f0.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f1 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_005fvarStatus_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fforEach_005f0);
    _jspx_th_c_005fforEach_005f1.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.outInvoiceBeanList}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    _jspx_th_c_005fforEach_005f1.setVar("item2");
    _jspx_th_c_005fforEach_005f1.setVarStatus("vs");
    int[] _jspx_push_body_count_c_005fforEach_005f1 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
      if (_jspx_eval_c_005fforEach_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("        dutyMap['");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write('\'');
          out.write(']');
          out.write('[');
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${vs.index}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("] = '");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item2.id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("';\r\n");
          out.write("    ");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f1[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f1.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f2 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f2.setParent(null);
    _jspx_th_c_005fforEach_005f2.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${listOut1}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    _jspx_th_c_005fforEach_005f2.setVar("item");
    int[] _jspx_push_body_count_c_005fforEach_005f2 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
      if (_jspx_eval_c_005fforEach_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("\tjmap['");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.fullId}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("'] = \"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${divMap[item.fullId]}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\";\r\n");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f2[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f2.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fset_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_005fset_005f0 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _005fjspx_005ftagPool_005fc_005fset_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fset_005f0.setParent(null);
    _jspx_th_c_005fset_005f0.setVar("fg");
    _jspx_th_c_005fset_005f0.setValue(new String("销售"));
    int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
    if (_jspx_th_c_005fset_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fset_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fset_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
    return false;
  }

  private boolean _jspx_meth_p_005fnavigation_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  p:navigation
    com.china.center.common.taglib.PageStart _jspx_th_p_005fnavigation_005f0 = (com.china.center.common.taglib.PageStart) _005fjspx_005ftagPool_005fp_005fnavigation_005fheight.get(com.china.center.common.taglib.PageStart.class);
    _jspx_th_p_005fnavigation_005f0.setPageContext(_jspx_page_context);
    _jspx_th_p_005fnavigation_005f0.setParent(null);
    _jspx_th_p_005fnavigation_005f0.setHeight("22");
    int _jspx_eval_p_005fnavigation_005f0 = _jspx_th_p_005fnavigation_005f0.doStartTag();
    if (_jspx_eval_p_005fnavigation_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_p_005fnavigation_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_p_005fnavigation_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_p_005fnavigation_005f0.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("    <td width=\"550\" class=\"navigation\">库单管理 &gt;&gt; 我的销售单");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${vtype}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("</td>\r\n");
        out.write("                <td width=\"85\"></td>\r\n");
        int evalDoAfterBody = _jspx_th_p_005fnavigation_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_p_005fnavigation_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_p_005fnavigation_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fp_005fnavigation_005fheight.reuse(_jspx_th_p_005fnavigation_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fp_005fnavigation_005fheight.reuse(_jspx_th_p_005fnavigation_005f0);
    return false;
  }

  private boolean _jspx_meth_p_005fplugin_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  p:plugin
    com.china.center.common.taglib.PagePlugin _jspx_th_p_005fplugin_005f0 = (com.china.center.common.taglib.PagePlugin) _005fjspx_005ftagPool_005fp_005fplugin_005fvalue_005fsize_005fname_005fnobody.get(com.china.center.common.taglib.PagePlugin.class);
    _jspx_th_p_005fplugin_005f0.setPageContext(_jspx_page_context);
    _jspx_th_p_005fplugin_005f0.setParent(null);
    _jspx_th_p_005fplugin_005f0.setName("outTime");
    _jspx_th_p_005fplugin_005f0.setSize("20");
    _jspx_th_p_005fplugin_005f0.setValue((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${outTime}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
    int _jspx_eval_p_005fplugin_005f0 = _jspx_th_p_005fplugin_005f0.doStartTag();
    if (_jspx_th_p_005fplugin_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fp_005fplugin_005fvalue_005fsize_005fname_005fnobody.reuse(_jspx_th_p_005fplugin_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fp_005fplugin_005fvalue_005fsize_005fname_005fnobody.reuse(_jspx_th_p_005fplugin_005f0);
    return false;
  }

  private boolean _jspx_meth_p_005fplugin_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  p:plugin
    com.china.center.common.taglib.PagePlugin _jspx_th_p_005fplugin_005f1 = (com.china.center.common.taglib.PagePlugin) _005fjspx_005ftagPool_005fp_005fplugin_005fvalue_005fsize_005fname_005fnobody.get(com.china.center.common.taglib.PagePlugin.class);
    _jspx_th_p_005fplugin_005f1.setPageContext(_jspx_page_context);
    _jspx_th_p_005fplugin_005f1.setParent(null);
    _jspx_th_p_005fplugin_005f1.setName("outTime1");
    _jspx_th_p_005fplugin_005f1.setSize("20");
    _jspx_th_p_005fplugin_005f1.setValue((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${outTime1}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
    int _jspx_eval_p_005fplugin_005f1 = _jspx_th_p_005fplugin_005f1.doStartTag();
    if (_jspx_th_p_005fplugin_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fp_005fplugin_005fvalue_005fsize_005fname_005fnobody.reuse(_jspx_th_p_005fplugin_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fp_005fplugin_005fvalue_005fsize_005fname_005fnobody.reuse(_jspx_th_p_005fplugin_005f1);
    return false;
  }

  private boolean _jspx_meth_p_005fplugin_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  p:plugin
    com.china.center.common.taglib.PagePlugin _jspx_th_p_005fplugin_005f2 = (com.china.center.common.taglib.PagePlugin) _005fjspx_005ftagPool_005fp_005fplugin_005fvalue_005ftype_005fsize_005fname_005fnobody.get(com.china.center.common.taglib.PagePlugin.class);
    _jspx_th_p_005fplugin_005f2.setPageContext(_jspx_page_context);
    _jspx_th_p_005fplugin_005f2.setParent(null);
    _jspx_th_p_005fplugin_005f2.setName("redateB");
    _jspx_th_p_005fplugin_005f2.setType(0);
    _jspx_th_p_005fplugin_005f2.setSize("20");
    _jspx_th_p_005fplugin_005f2.setValue((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${redateB}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
    int _jspx_eval_p_005fplugin_005f2 = _jspx_th_p_005fplugin_005f2.doStartTag();
    if (_jspx_th_p_005fplugin_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fp_005fplugin_005fvalue_005ftype_005fsize_005fname_005fnobody.reuse(_jspx_th_p_005fplugin_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fp_005fplugin_005fvalue_005ftype_005fsize_005fname_005fnobody.reuse(_jspx_th_p_005fplugin_005f2);
    return false;
  }

  private boolean _jspx_meth_p_005fplugin_005f3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  p:plugin
    com.china.center.common.taglib.PagePlugin _jspx_th_p_005fplugin_005f3 = (com.china.center.common.taglib.PagePlugin) _005fjspx_005ftagPool_005fp_005fplugin_005fvalue_005ftype_005fsize_005fname_005fnobody.get(com.china.center.common.taglib.PagePlugin.class);
    _jspx_th_p_005fplugin_005f3.setPageContext(_jspx_page_context);
    _jspx_th_p_005fplugin_005f3.setParent(null);
    _jspx_th_p_005fplugin_005f3.setName("redateE");
    _jspx_th_p_005fplugin_005f3.setType(0);
    _jspx_th_p_005fplugin_005f3.setSize("20");
    _jspx_th_p_005fplugin_005f3.setValue((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${redateE}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
    int _jspx_eval_p_005fplugin_005f3 = _jspx_th_p_005fplugin_005f3.doStartTag();
    if (_jspx_th_p_005fplugin_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fp_005fplugin_005fvalue_005ftype_005fsize_005fname_005fnobody.reuse(_jspx_th_p_005fplugin_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fp_005fplugin_005fvalue_005ftype_005fsize_005fname_005fnobody.reuse(_jspx_th_p_005fplugin_005f3);
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
    _jspx_th_c_005fif_005f0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${!ff}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
    if (_jspx_eval_c_005fif_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t<select name=\"status\" class=\"select_class\" values=\"");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${status}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("\">\r\n");
        out.write("\t\t\t\t\t\t\t<option value=\"\">--</option>\r\n");
        out.write("\t\t\t\t\t\t\t");
        if (_jspx_meth_p_005foption_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t<option value=\"99\">发货态</option>\r\n");
        out.write("\t\t\t\t\t\t</select>\r\n");
        out.write("\t\t\t\t\t\t");
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

  private boolean _jspx_meth_p_005foption_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  p:option
    com.china.center.common.taglib.PageSelectOption _jspx_th_p_005foption_005f0 = (com.china.center.common.taglib.PageSelectOption) _005fjspx_005ftagPool_005fp_005foption_005ftype_005fnobody.get(com.china.center.common.taglib.PageSelectOption.class);
    _jspx_th_p_005foption_005f0.setPageContext(_jspx_page_context);
    _jspx_th_p_005foption_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fif_005f0);
    _jspx_th_p_005foption_005f0.setType("outStatus");
    int _jspx_eval_p_005foption_005f0 = _jspx_th_p_005foption_005f0.doStartTag();
    if (_jspx_th_p_005foption_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fp_005foption_005ftype_005fnobody.reuse(_jspx_th_p_005foption_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fp_005foption_005ftype_005fnobody.reuse(_jspx_th_p_005foption_005f0);
    return false;
  }

  private boolean _jspx_meth_p_005foption_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  p:option
    com.china.center.common.taglib.PageSelectOption _jspx_th_p_005foption_005f1 = (com.china.center.common.taglib.PageSelectOption) _005fjspx_005ftagPool_005fp_005foption_005ftype_005fnobody.get(com.china.center.common.taglib.PageSelectOption.class);
    _jspx_th_p_005foption_005f1.setPageContext(_jspx_page_context);
    _jspx_th_p_005foption_005f1.setParent(null);
    _jspx_th_p_005foption_005f1.setType("outType_out");
    int _jspx_eval_p_005foption_005f1 = _jspx_th_p_005foption_005f1.doStartTag();
    if (_jspx_th_p_005foption_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fp_005foption_005ftype_005fnobody.reuse(_jspx_th_p_005foption_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fp_005foption_005ftype_005fnobody.reuse(_jspx_th_p_005foption_005f1);
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f3 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f3.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f3.setParent(null);
    _jspx_th_c_005fforEach_005f3.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${depotList}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    _jspx_th_c_005fforEach_005f3.setVar("item");
    int[] _jspx_push_body_count_c_005fforEach_005f3 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f3 = _jspx_th_c_005fforEach_005f3.doStartTag();
      if (_jspx_eval_c_005fforEach_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("                             <option value=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write('"');
          out.write('>');
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.name}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("</option>\r\n");
          out.write("                            ");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f3.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f3[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f3.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f3.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f3);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f4 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_005fvarStatus_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f4.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f4.setParent(null);
    _jspx_th_c_005fforEach_005f4.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${listOut1}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    _jspx_th_c_005fforEach_005f4.setVar("item");
    _jspx_th_c_005fforEach_005f4.setVarStatus("vs");
    int[] _jspx_push_body_count_c_005fforEach_005f4 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f4 = _jspx_th_c_005fforEach_005f4.doStartTag();
      if (_jspx_eval_c_005fforEach_005f4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("\t\t\t\t\t\t<tr class='");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${vs.index % 2 == 0 ? \"content1\" : \"content2\"}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("'\r\n");
          out.write("\t\t\t\t\t\t>\r\n");
          out.write("\t\t\t\t\t\t\t<td align=\"center\"><input type=\"radio\" name=\"fullId\" \r\n");
          out.write("\t\t\t\t\t\t\t    temptype=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.tempType}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\"\r\n");
          out.write("\t\t\t\t\t\t\t    outtype=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.outType}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\"\r\n");
          out.write("\t\t\t\t\t\t\t\tpcustomerid='");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.customerId}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("' \r\n");
          out.write("\t\t\t\t\t\t\t\tstatuss='");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.status}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("' \r\n");
          out.write("\t\t\t\t\t\t\t\tpinvoicemoney='");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.invoiceMoney}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("' \r\n");
          out.write("\t\t\t\t\t\t\t\tpinvoiceid='");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.invoiceId}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("' \r\n");
          out.write("\t\t\t\t\t\t\t\tppay='");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.pay}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("' \r\n");
          out.write("\t\t\t\t\t\t\t\tpdutyid='");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.dutyId}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("' \r\n");
          out.write("\t\t\t\t\t\t\t\tvalue=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.fullId}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write('"');
          out.write(' ');
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${vs.index== 0 ? \"checked\" : \"\"}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("/></td>\r\n");
          out.write("\t\t\t\t\t\t\t<td align=\"center\"\r\n");
          out.write("\t\t\t\t\t\t\tonMouseOver=\"showDiv('");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.fullId}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("')\" onmousemove=\"tooltip.move()\" onmouseout=\"tooltip.hide()\"><a onclick=\"hrefAndSelect(this)\" href=\"../sail/out.do?method=findOut&fow=99&outId=");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.fullId}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write('"');
          out.write('>');
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.mark ? \"<font color=blue><B>\" : \"\"}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\r\n");
          out.write("\t\t\t\t\t\t\t");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.fullId}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write(' ');
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.mark ? \"</B></font>\" : \"\"}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("</a></td>\r\n");
          out.write("\t\t\t\t\t\t\t<td align=\"center\" onclick=\"hrefAndSelect(this)\">");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.customerName}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("</td>\r\n");
          out.write("\t\t\t\t\t\t\t<td align=\"center\" onclick=\"hrefAndSelect(this)\">");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${my:get('outStatus', item.status)}", java.lang.String.class, (PageContext)_jspx_page_context, _jspx_fnmap_1, false));
          out.write("</td>\r\n");
          out.write("\t\t\t\t\t\t\t<td align=\"center\" onclick=\"hrefAndSelect(this)\">");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${my:get('outType_out', item.outType)}", java.lang.String.class, (PageContext)_jspx_page_context, _jspx_fnmap_1, false));
          out.write("</td>\r\n");
          out.write("\t\t\t\t\t\t\t<td align=\"center\" onclick=\"hrefAndSelect(this)\">");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.outTime}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("</td>\r\n");
          out.write("\t\t\t\t\t\t\t");
          if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fforEach_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
            return true;
          out.write("\r\n");
          out.write("\t\t\t\t\t\t\t");
          if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fforEach_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
            return true;
          out.write("\r\n");
          out.write("\t\t\t\t\t\t\t\r\n");
          out.write("\t\t\t\t\t\t\t");
          if (_jspx_meth_c_005fif_005f3(_jspx_th_c_005fforEach_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
            return true;
          out.write("\r\n");
          out.write("\t\t\t\t\t\t\t");
          if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fforEach_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
            return true;
          out.write("\r\n");
          out.write("\t\t\t\t\t\t\t");
          if (_jspx_meth_c_005fif_005f5(_jspx_th_c_005fforEach_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
            return true;
          out.write("\r\n");
          out.write("\t\t\t\t\t\t\t\r\n");
          out.write("\t\t\t\t\t\t\t<td align=\"center\" onclick=\"hrefAndSelect(this)\">");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${my:formatNum(item.total)}", java.lang.String.class, (PageContext)_jspx_page_context, _jspx_fnmap_2, false));
          out.write("</td>\r\n");
          out.write("\t\t\t\t\t\t\t<td align=\"center\" onclick=\"hrefAndSelect(this)\">");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${my:formatNum(item.hadPay)}", java.lang.String.class, (PageContext)_jspx_page_context, _jspx_fnmap_2, false));
          out.write("</td>\r\n");
          out.write("\t\t\t\t\t\t\t<td align=\"center\" onclick=\"hrefAndSelect(this)\">");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.stafferName}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("</td>\r\n");
          out.write("\t\t\t\t\t\t\t<td align=\"center\" onclick=\"hrefAndSelect(this)\">");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.depotName}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("</td>\r\n");
          out.write("\t\t\t\t\t\t\t<td align=\"center\" onclick=\"hrefAndSelect(this)\">\r\n");
          out.write("                            <a\r\n");
          out.write("                            href=\"../sail/transport.do?method=findConsign&forward=3&fullId=");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.fullId}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\"\r\n");
          out.write("                            >\r\n");
          out.write("                            ");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${my:get('consignStatus', item.consign)}", java.lang.String.class, (PageContext)_jspx_page_context, _jspx_fnmap_1, false));
          out.write("\r\n");
          out.write("                            </a>\r\n");
          out.write("                            </td>\r\n");
          out.write("\t\t\t\t\t\t</tr>\r\n");
          out.write("\t\t\t\t\t");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f4.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f4[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f4.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f4.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f4);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fforEach_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f1 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fforEach_005f4);
    _jspx_th_c_005fif_005f1.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.pay == 0}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
    if (_jspx_eval_c_005fif_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t<td align=\"center\" onclick=\"hrefAndSelect(this)\"><font color=red>");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.redate}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("</font></td>\r\n");
        out.write("\t\t\t\t\t\t\t");
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

  private boolean _jspx_meth_c_005fif_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fforEach_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f2 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fforEach_005f4);
    _jspx_th_c_005fif_005f2.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.pay == 1}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
    if (_jspx_eval_c_005fif_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t<td align=\"center\" onclick=\"hrefAndSelect(this)\"><font color=blue>");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.redate}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("</font></td>\r\n");
        out.write("\t\t\t\t\t\t\t");
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

  private boolean _jspx_meth_c_005fif_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fforEach_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f3 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fforEach_005f4);
    _jspx_th_c_005fif_005f3.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.reserve3 == 1}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
    if (_jspx_eval_c_005fif_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t<td align=\"center\" onclick=\"hrefAndSelect(this)\">款到发货(黑名单客户/零售)</td>\r\n");
        out.write("\t\t\t\t\t\t\t");
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

  private boolean _jspx_meth_c_005fif_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fforEach_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f4 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fforEach_005f4);
    _jspx_th_c_005fif_005f4.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.reserve3 == 2}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
    if (_jspx_eval_c_005fif_005f4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t<td align=\"center\" onclick=\"hrefAndSelect(this)\">客户信用和业务员信用额度担保</td>\r\n");
        out.write("\t\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_005ftest.reuse(_jspx_th_c_005fif_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_005ftest.reuse(_jspx_th_c_005fif_005f4);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f5(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fforEach_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f5 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fforEach_005f4);
    _jspx_th_c_005fif_005f5.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.reserve3 == 3}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
    if (_jspx_eval_c_005fif_005f5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\t\t\t<td align=\"center\" onclick=\"hrefAndSelect(this)\">信用担保</td>\r\n");
        out.write("\t\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_005ftest.reuse(_jspx_th_c_005fif_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_005ftest.reuse(_jspx_th_c_005fif_005f5);
    return false;
  }

  private boolean _jspx_meth_p_005fformTurning_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  p:formTurning
    com.china.center.common.taglib.PageFormTurning _jspx_th_p_005fformTurning_005f0 = (com.china.center.common.taglib.PageFormTurning) _005fjspx_005ftagPool_005fp_005fformTurning_005fmethod_005fform_005fnobody.get(com.china.center.common.taglib.PageFormTurning.class);
    _jspx_th_p_005fformTurning_005f0.setPageContext(_jspx_page_context);
    _jspx_th_p_005fformTurning_005f0.setParent(null);
    _jspx_th_p_005fformTurning_005f0.setForm("adminForm");
    _jspx_th_p_005fformTurning_005f0.setMethod("querySelfOut");
    int _jspx_eval_p_005fformTurning_005f0 = _jspx_th_p_005fformTurning_005f0.doStartTag();
    if (_jspx_th_p_005fformTurning_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fp_005fformTurning_005fmethod_005fform_005fnobody.reuse(_jspx_th_p_005fformTurning_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fp_005fformTurning_005fmethod_005fform_005fnobody.reuse(_jspx_th_p_005fformTurning_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f6(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f6 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f6.setParent(null);
    _jspx_th_c_005fif_005f6.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${my:length(listOut1) > 0}", java.lang.Boolean.class, (PageContext)_jspx_page_context, _jspx_fnmap_0, false)).booleanValue());
    int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
    if (_jspx_eval_c_005fif_005f6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t<tr>\r\n");
        out.write("\t\t<td width=\"100%\">\r\n");
        out.write("\t\t<div align=\"right\">\r\n");
        out.write("\t\t<input type=\"button\" class=\"button_class\"\r\n");
        out.write("\t\t\tvalue=\"&nbsp;&nbsp;标 记&nbsp;&nbsp;\" onClick=\"mark()\">&nbsp;&nbsp;\r\n");
        out.write("\t\t<input type=\"button\" class=\"button_class\"\r\n");
        out.write("            value=\"&nbsp;修改发票&nbsp;\" onClick=\"preUpdateInvoice()\">&nbsp;&nbsp;\r\n");
        out.write("\t\t<input\r\n");
        out.write("\t\t\ttype=\"button\" class=\"button_class\"\r\n");
        out.write("\t\t\tvalue=\"&nbsp;&nbsp;修 改&nbsp;&nbsp;\" onclick=\"modfiy()\" />&nbsp;&nbsp;\r\n");
        out.write("\t\t\t");
        if (_jspx_meth_c_005fif_005f7(_jspx_th_c_005fif_005f6, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t\t<input\r\n");
        out.write("\t\t\ttype=\"button\" class=\"button_class\"\r\n");
        out.write("\t\t\tvalue=\"&nbsp;&nbsp;删 除&nbsp;&nbsp;\" onclick=\"del()\" />&nbsp;&nbsp;\r\n");
        out.write("\t\t<input\r\n");
        out.write("            type=\"button\" class=\"button_class\"\r\n");
        out.write("            value=\"&nbsp;&nbsp;勾 款&nbsp;&nbsp;\" onclick=\"refBill()\" />&nbsp;&nbsp;\r\n");
        out.write("\t\t<input\r\n");
        out.write("            type=\"button\" class=\"button_class\"\r\n");
        out.write("            value=\"&nbsp;委托代销结算&nbsp;\" onclick=\"balance()\" />&nbsp;&nbsp;\r\n");
        out.write("        <input\r\n");
        out.write("            type=\"button\" class=\"button_class\"\r\n");
        out.write("            value=\"&nbsp;委托代销退货&nbsp;\" onclick=\"balance2()\" />&nbsp;&nbsp;\r\n");
        out.write("        <input\r\n");
        out.write("            type=\"button\" class=\"button_class\"\r\n");
        out.write("            value=\"&nbsp;销售单移交&nbsp;\" onclick=\"tran()\" />&nbsp;&nbsp;\r\n");
        out.write("            \r\n");
        out.write("        ");
        if (_jspx_meth_c_005fif_005f8(_jspx_th_c_005fif_005f6, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\t\t<td width=\"0%\"></td>\r\n");
        out.write("\t</tr>\r\n");
        out.write("\t\r\n");
        out.write("\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_005ftest.reuse(_jspx_th_c_005fif_005f6);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_005ftest.reuse(_jspx_th_c_005fif_005f6);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f7(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f7 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fif_005f6);
    _jspx_th_c_005fif_005f7.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${my:auth(user, '999999')}", java.lang.Boolean.class, (PageContext)_jspx_page_context, _jspx_fnmap_3, false)).booleanValue());
    int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
    if (_jspx_eval_c_005fif_005f7 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t<input type=\"button\" class=\"button_class\"\r\n");
        out.write("\t\t\tvalue=\"&nbsp;&nbsp;提 交&nbsp;&nbsp;\" onclick=\"sub()\" />&nbsp;&nbsp; \r\n");
        out.write("\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_005ftest.reuse(_jspx_th_c_005fif_005f7);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_005ftest.reuse(_jspx_th_c_005fif_005f7);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f8(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f8 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fif_005f6);
    _jspx_th_c_005fif_005f8.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${my:auth(user, '1417')}", java.lang.Boolean.class, (PageContext)_jspx_page_context, _jspx_fnmap_3, false)).booleanValue());
    int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
    if (_jspx_eval_c_005fif_005f8 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t<input\r\n");
        out.write("\t\t\t\ttype=\"button\" class=\"button_class\"\r\n");
        out.write("\t\t\t\tvalue=\"&nbsp;导出查询结果&nbsp;\" onclick=\"exports()\" /></div>\r\n");
        out.write("\t\t\t</td>\r\n");
        out.write("\t\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_005ftest.reuse(_jspx_th_c_005fif_005f8);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_005ftest.reuse(_jspx_th_c_005fif_005f8);
    return false;
  }

  private boolean _jspx_meth_p_005fmessage2_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  p:message2
    com.china.center.common.taglib.PageMessage2 _jspx_th_p_005fmessage2_005f0 = (com.china.center.common.taglib.PageMessage2) _005fjspx_005ftagPool_005fp_005fmessage2_005fnobody.get(com.china.center.common.taglib.PageMessage2.class);
    _jspx_th_p_005fmessage2_005f0.setPageContext(_jspx_page_context);
    _jspx_th_p_005fmessage2_005f0.setParent(null);
    int _jspx_eval_p_005fmessage2_005f0 = _jspx_th_p_005fmessage2_005f0.doStartTag();
    if (_jspx_th_p_005fmessage2_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fp_005fmessage2_005fnobody.reuse(_jspx_th_p_005fmessage2_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fp_005fmessage2_005fnobody.reuse(_jspx_th_p_005fmessage2_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f5(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f5 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f5.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f5.setParent(null);
    _jspx_th_c_005fforEach_005f5.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${invoiceList}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    _jspx_th_c_005fforEach_005f5.setVar("item");
    int[] _jspx_push_body_count_c_005fforEach_005f5 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f5 = _jspx_th_c_005fforEach_005f5.doStartTag();
      if (_jspx_eval_c_005fforEach_005f5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("    <p id=\"s_");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\"><input type=\"radio\" name=\"invoices\" value=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write('"');
          out.write('>');
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.fullName}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("<br></p>\r\n");
          out.write("    ");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f5.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f5[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f5.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f5.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f5);
    }
    return false;
  }
}
