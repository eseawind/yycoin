package org.apache.jsp.desktop;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class desktop_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(3);
    _jspx_dependants.add("/desktop/../common/common.jsp");
    _jspx_dependants.add("/WEB-INF/tld/pageTld.tld");
    _jspx_dependants.add("/WEB-INF/tld/common.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_005fvarStatus_005fvar_005fitems;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fforEach_005fvarStatus_005fvar_005fitems = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fforEach_005fvarStatus_005fvar_005fitems.release();
    _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.release();
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
      out.write("<HTML><HEAD>\r\n");
      out.write("<TITLE>我的桌面</TITLE>\n");
      out.write("<SCRIPT type=\"text/javascript\" src=\"../js/jquery/jquery.js\"></SCRIPT>\n");
      out.write("<SCRIPT type=\"text/javascript\" src=\"../js/plugin/interface/interface.js\"></SCRIPT>\r\n");
      out.write("<SCRIPT type=\"text/javascript\">\r\n");
      out.write("function load()\r\n");
      out.write("{\r\n");
      out.write("\tvar black = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${black}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\";\r\n");
      out.write("\r\n");
      out.write("\tif (black > '')\r\n");
      out.write("\t{\r\n");
      out.write("\t\talert(black);\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("</SCRIPT>\n");
      out.write("<STYLE type=\"text/css\" media=\"all\">\n");
      out.write("html\n");
      out.write("{\n");
      out.write("\theight: 95%;\n");
      out.write("}\n");
      out.write("img{\n");
      out.write("\tborder: none;\n");
      out.write("}\n");
      out.write("body\n");
      out.write("{\n");
      out.write("\tbackground: #fff;\n");
      out.write("\theight: 100%;\n");
      out.write("\tfont-family: Arial, Helvetica, sans-serif;\n");
      out.write("\tfont-size: 12px;\n");
      out.write("}\n");
      out.write(".groupWrapper\n");
      out.write("{\n");
      out.write("\twidth: 48%;\n");
      out.write("\tfloat: left;\n");
      out.write("\tmargin-right: 1%;\n");
      out.write("\theight: 250px;\n");
      out.write("}\n");
      out.write(".serializer\n");
      out.write("{\n");
      out.write("\tclear: both;\n");
      out.write("}\n");
      out.write(".groupItem\n");
      out.write("{\n");
      out.write("\tmargin-bottom: 20px;\n");
      out.write("}\n");
      out.write(".groupItem .itemHeader\n");
      out.write("{\n");
      out.write("\tline-height: 28px;\n");
      out.write("\tbackground-color: #DAFF9F;\n");
      out.write("\tborder-top: 2px solid #B5EF59;\n");
      out.write("\tcolor: #000;\n");
      out.write("\tpadding: 0 10px;\n");
      out.write("\tcursor: move;\n");
      out.write("\tfont-weight: bold;\n");
      out.write("\tfont-size: 16px;\n");
      out.write("\theight: 28px;\n");
      out.write("\tposition: relative;\n");
      out.write("}\n");
      out.write("\n");
      out.write(".groupItem .itemHeader a\n");
      out.write("{\n");
      out.write("\tposition: absolute;\n");
      out.write("\tright: 10px;\n");
      out.write("\ttop: 0px;\n");
      out.write("\tfont-weight: normal;\n");
      out.write("\tfont-size: 12px;\n");
      out.write("\ttext-decoration: none;\n");
      out.write("}\n");
      out.write(".sortHelper\n");
      out.write("{\n");
      out.write("\tborder: 3px dashed #666;\n");
      out.write("\twidth: auto !important;\n");
      out.write("}\n");
      out.write(".groupWrapper p\n");
      out.write("{\n");
      out.write("\theight: 1px;\n");
      out.write("\toverflow: hidden;\n");
      out.write("\tmargin: 0;\n");
      out.write("\tpadding: 0;\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("</STYLE>\n");
      out.write("</HEAD><BODY onload=\"load()\">\r\n");
      if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\n");
      out.write("<SCRIPT type=\"text/javascript\">\n");
      out.write("$(document).ready(\n");
      out.write("\tfunction () {\n");
      out.write("\t\t$('a.closeEl').bind('click', toggleContent);\n");
      out.write("\t\t$('div.groupWrapper').Sortable(\n");
      out.write("\t\t\t{\n");
      out.write("\t\t\t\taccept: 'groupItem',\n");
      out.write("\t\t\t\thelperclass: 'sortHelper',\n");
      out.write("\t\t\t\tactiveclass : \t'sortableactive',\n");
      out.write("\t\t\t\thoverclass : \t'sortablehover',\n");
      out.write("\t\t\t\thandle: 'div.itemHeader',\n");
      out.write("\t\t\t\ttolerance: 'pointer',\n");
      out.write("\t\t\t\tonChange : function(ser)\n");
      out.write("\t\t\t\t{\n");
      out.write("\t\t\t\t},\n");
      out.write("\t\t\t\tonStart : function()\n");
      out.write("\t\t\t\t{\n");
      out.write("\t\t\t\t\t$.iAutoscroller.start(this, document.getElementsByTagName('body'));\n");
      out.write("\t\t\t\t},\n");
      out.write("\t\t\t\tonStop : function()\n");
      out.write("\t\t\t\t{\n");
      out.write("\t\t\t\t\t$.iAutoscroller.stop();\n");
      out.write("\t\t\t\t}\n");
      out.write("\t\t\t}\n");
      out.write("\t\t);\n");
      out.write("\t}\n");
      out.write(");\n");
      out.write("var toggleContent = function(e)\n");
      out.write("{\n");
      out.write("\tvar targetContent = $('div.itemContent', this.parentNode.parentNode);\n");
      out.write("\tif (targetContent.css('display') == 'none') {\n");
      out.write("\t\ttargetContent.slideDown(10);\n");
      out.write("\t\t$(this).html('[-]');\n");
      out.write("\t} else {\n");
      out.write("\t\ttargetContent.slideUp(10);\n");
      out.write("\t\t$(this).html('[+]');\n");
      out.write("\t}\n");
      out.write("\treturn false;\n");
      out.write("};\n");
      out.write("\n");
      out.write("</SCRIPT>\n");
      out.write("</BODY></HTML>");
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

  private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f0 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_005fvarStatus_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f0.setParent(null);
    _jspx_th_c_005fforEach_005f0.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${wrapList}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    _jspx_th_c_005fforEach_005f0.setVar("item");
    _jspx_th_c_005fforEach_005f0.setVarStatus("vs");
    int[] _jspx_push_body_count_c_005fforEach_005f0 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
      if (_jspx_eval_c_005fforEach_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n");
          out.write("<DIV id=\"sort");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${vs.index + 1}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\" class=\"groupWrapper\">\n");
          out.write("\t<DIV id=\"newsFeeder\" class=\"groupItem\" style=\"left: -14px; top: -56px; display: block; position: static; \">\n");
          out.write("\t\t<DIV class=\"itemHeader\" style=\"-webkit-user-select: none; \">");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.name}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("<A href=\"javaScript:void(0)\" class=\"closeEl\">[-]</A></DIV>\n");
          out.write("\t\t<DIV class=\"itemContent\" style=\"display: block; overflow-x: visible; overflow-y: visible; \">\n");
          out.write("\t\t\t");
          if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
            return true;
          out.write("\n");
          out.write("\t\t</DIV>\n");
          out.write("\t</DIV>\n");
          out.write("\t\n");
          out.write("\t<P>&nbsp;</P>\n");
          out.write("</DIV>\r\n");
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
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f1 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fforEach_005f0);
    _jspx_th_c_005fforEach_005f1.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${item.itemList}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    _jspx_th_c_005fforEach_005f1.setVar("itemEach");
    int[] _jspx_push_body_count_c_005fforEach_005f1 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
      if (_jspx_eval_c_005fforEach_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("\t\t\t<UL>\r\n");
          out.write("\t\t\t\t<LI><a href=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${itemEach.href}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\" title=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${itemEach.tips}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("\">\r\n");
          out.write("\t\t\t\t");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${itemEach.title}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
          out.write("</a></LI>\r\n");
          out.write("\t\t\t</UL>\n");
          out.write("\t\t    ");
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
      _005fjspx_005ftagPool_005fc_005fforEach_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
    }
    return false;
  }
}
