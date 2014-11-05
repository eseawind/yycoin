package org.apache.jsp.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Random;

public final class shousuo_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(3);
    _jspx_dependants.add("/admin/../common/common.jsp");
    _jspx_dependants.add("/WEB-INF/tld/pageTld.tld");
    _jspx_dependants.add("/WEB-INF/tld/common.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fp_005flink_005ftitle_005fcal_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_005fvarStatus_005fvar_005fitems;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fp_005flink_005ftitle_005fcal_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fforEach_005fvarStatus_005fvar_005fitems = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fp_005flink_005ftitle_005fcal_005fnobody.release();
    _005fjspx_005ftagPool_005fc_005fforEach_005fvarStatus_005fvar_005fitems.release();
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

      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      if (_jspx_meth_p_005flink_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("<link href=\"../css/shousuo.css\" type=text/css rel=stylesheet>\r\n");
      out.write("<script type=text/javascript><!--\r\n");
      out.write("var LastLeftID = \"\";\r\n");
      out.write("function menuFix() {\r\n");
      out.write("var obj = document.getElementById(\"nav\").getElementsByTagName(\"li\");\r\n");
      out.write("\r\n");
      out.write("for (var i=0; i<obj.length; i++) {\r\n");
      out.write("   obj[i].onmouseover=function() {\r\n");
      out.write("    this.className+=(this.className.length>0? \" \": \"\") + \"sfhover\";\r\n");
      out.write("   }\r\n");
      out.write("   obj[i].onMouseDown=function() {\r\n");
      out.write("    this.className+=(this.className.length>0? \" \": \"\") + \"sfhover\";\r\n");
      out.write("   }\r\n");
      out.write("   obj[i].onMouseUp=function() {\r\n");
      out.write("    this.className+=(this.className.length>0? \" \": \"\") + \"sfhover\";\r\n");
      out.write("   }\r\n");
      out.write("   obj[i].onmouseout=function() {\r\n");
      out.write("    this.className=this.className.replace(new RegExp(\"( ?|^)sfhover\\\\b\"), \"\");\r\n");
      out.write("   }\r\n");
      out.write("}\r\n");
      out.write("}\r\n");
      out.write("function DoMenu(emid)\r\n");
      out.write("{\r\n");
      out.write("var obj = document.getElementById(emid);\r\n");
      out.write("obj.className = (obj.className.toLowerCase() == \"expanded\"?\"collapsed\":\"expanded\");\r\n");
      out.write("if((LastLeftID!=\"\")&&(emid!=LastLeftID)) //关闭上一个Menu\r\n");
      out.write("{\r\n");
      out.write("   document.getElementById(LastLeftID).className = \"collapsed\";\r\n");
      out.write("}\r\n");
      out.write("LastLeftID = emid;\r\n");
      out.write("}\r\n");
      out.write("function GetMenuID()\r\n");
      out.write("{\r\n");
      out.write("var MenuID=\"\";\r\n");
      out.write("var _paramStr = new String(window.location.href);\r\n");
      out.write("var _sharpPos = _paramStr.indexOf(\"#\");\r\n");
      out.write("\r\n");
      out.write("if (_sharpPos >= 0 && _sharpPos < _paramStr.length - 1)\r\n");
      out.write("{\r\n");
      out.write("   _paramStr = _paramStr.substring(_sharpPos + 1, _paramStr.length);\r\n");
      out.write("}\r\n");
      out.write("else\r\n");
      out.write("{\r\n");
      out.write("   _paramStr = \"\";\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("if (_paramStr.length > 0)\r\n");
      out.write("{\r\n");
      out.write("   var _paramArr = _paramStr.split(\"&\");\r\n");
      out.write("   if (_paramArr.length>0)\r\n");
      out.write("   {\r\n");
      out.write("    var _paramKeyVal = _paramArr[0].split(\"=\");\r\n");
      out.write("    if (_paramKeyVal.length>0)\r\n");
      out.write("    {\r\n");
      out.write("     MenuID = _paramKeyVal[1];\r\n");
      out.write("    }\r\n");
      out.write("   }\r\n");
      out.write("   /*\r\n");
      out.write("   if (_paramArr.length>0)\r\n");
      out.write("   {\r\n");
      out.write("    var _arr = new Array(_paramArr.length);\r\n");
      out.write("   }\r\n");
      out.write("\r\n");
      out.write("   //取所有#后面的，菜单只需用到Menu\r\n");
      out.write("   //for (var i = 0; i < _paramArr.length; i++)\r\n");
      out.write("   {\r\n");
      out.write("    var _paramKeyVal = _paramArr[i].split('=');\r\n");
      out.write("\r\n");
      out.write("    if (_paramKeyVal.length>0)\r\n");
      out.write("    {\r\n");
      out.write("     _arr[_paramKeyVal[0]] = _paramKeyVal[1];\r\n");
      out.write("    }\r\n");
      out.write("   }\r\n");
      out.write("   */\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("if(MenuID!=\"\")\r\n");
      out.write("{\r\n");
      out.write("   DoMenu(MenuID)\r\n");
      out.write("}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("function load()\r\n");
      out.write("{\r\n");
      out.write("\tGetMenuID(); //*这两个function的顺序要注意一下，不然在Firefox里GetMenuID()不起效果\r\n");
      out.write("\tmenuFix();\r\n");
      out.write("}\r\n");
      out.write("--></script>\r\n");
      out.write("</head>\r\n");
      out.write("<body class=\"tree_class\" onload=\"load()\">\r\n");
      out.write("<table>\r\n");
      out.write("\t<tr height=\"10\">\r\n");
      out.write("\t\t<td colspan=\"2\"></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t<tr height=\"10\">\r\n");
      out.write("\t\t<td width=\"15\"></td>\r\n");
      out.write("\t\t<td><font color=\"blue\"\r\n");
      out.write("\t\t\tstyle=\"font-family: arial, 宋体, serif; size: 12px\"><B>[");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${g_stafferBean.industryName}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("]\r\n");
      out.write("\t\t ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${g_stafferBean.name}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("</B></font></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t<tr height=\"10\">\r\n");
      out.write("\t\t<td colspan=\"2\"></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("<table width=\"100%\">\r\n");
      out.write("<div id=\"PARENT\">\r\n");
      out.write("<ul id=\"nav\">\r\n");
      out.write("\t");
      if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t<li><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${g_modifyPassword}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" target=\"main\">修改密码</a></li>\r\n");
      out.write("\t<li><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${g_logout}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\" target=\"_parent\">退出</a></li>\r\n");
      out.write("</ul>\r\n");
      out.write("</div>\r\n");
      out.write("</table>\r\n");
      out.write("<table>\r\n");
      out.write("\t<tr height=\"10\">\r\n");
      out.write("\t\t<td colspan=\"2\"></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t<tr height=\"10\">\r\n");
      out.write("\t\t<td width=\"15\"></td>\r\n");
      out.write("\t\t");

		Random random = new Random(System.currentTimeMillis());

		int ran = random.nextInt(1000);

		request.setAttribute("colock", (ran % 4) + 1);
		
      out.write("\r\n");
      out.write("\t\t<td></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t<tr height=\"10\">\r\n");
      out.write("\t\t<td colspan=\"2\"></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
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
    com.china.center.common.taglib.PageLink _jspx_th_p_005flink_005f0 = (com.china.center.common.taglib.PageLink) _005fjspx_005ftagPool_005fp_005flink_005ftitle_005fcal_005fnobody.get(com.china.center.common.taglib.PageLink.class);
    _jspx_th_p_005flink_005f0.setPageContext(_jspx_page_context);
    _jspx_th_p_005flink_005f0.setParent(null);
    _jspx_th_p_005flink_005f0.setTitle("显示菜单");
    _jspx_th_p_005flink_005f0.setCal(false);
    int _jspx_eval_p_005flink_005f0 = _jspx_th_p_005flink_005f0.doStartTag();
    if (_jspx_th_p_005flink_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fp_005flink_005ftitle_005fcal_005fnobody.reuse(_jspx_th_p_005flink_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fp_005flink_005ftitle_005fcal_005fnobody.reuse(_jspx_th_p_005flink_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f0 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_005fvarStatus_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f0.setParent(null);
    _jspx_th_c_005fforEach_005f0.setVar("item");
    _jspx_th_c_005fforEach_005f0.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${menuRootList}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    _jspx_th_c_005fforEach_005f0.setVarStatus("vs");
    int[] _jspx_push_body_count_c_005fforEach_005f0 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
      if (_jspx_eval_c_005fforEach_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("\t\t<li><a href=\"#Menu=ChildMenu");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${vs.index}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\"\r\n");
          out.write("\t\t\tonclick=\"DoMenu('ChildMenu");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${vs.index}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("')\">");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.menuItemName}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("</a>\r\n");
          out.write("\t\t<ul id=\"ChildMenu");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${vs.index}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\" class=\"collapsed\">\r\n");
          out.write("\t\t\t");
          if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
            return true;
          out.write("\r\n");
          out.write("\t\t</ul>\r\n");
          out.write("\t\t</li>\r\n");
          out.write("\t");
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
      _005fjspx_005ftagPool_005fc_005fforEach_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
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
    _jspx_th_c_005fforEach_005f1.setVar("item1");
    _jspx_th_c_005fforEach_005f1.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${menuItemMap[item.id]}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    _jspx_th_c_005fforEach_005f1.setVarStatus("vs1");
    int[] _jspx_push_body_count_c_005fforEach_005f1 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
      if (_jspx_eval_c_005fforEach_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("\t\t\t\t<li><a href=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item1.url}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\" target=\"main\" id=\"a_");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item1.id}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\" title=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item1.description}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write('"');
          out.write('>');
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item1.menuItemName}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("</a></li>\r\n");
          out.write("\t\t\t");
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
}
