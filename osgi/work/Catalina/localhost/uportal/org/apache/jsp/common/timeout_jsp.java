package org.apache.jsp.common;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class timeout_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  public Object getDependants() {
    return _jspx_dependants;
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
      out.write("<html>\r\n");
      out.write("<HEAD>\r\n");
      out.write("<TITLE>-=重新登录提示=-</TITLE>\r\n");
      out.write("<META http-equiv=Content-Type content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<META content=\"MSHTML 6.00.2900.2668\" name=GENERATOR>\r\n");
      out.write("</HEAD>\r\n");
      out.write("<BODY bgColor=#ffffff leftMargin=0 topMargin=0 marginheight=\"0\"\r\n");
      out.write("\tmarginwidth=\"0\">\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<TABLE cellSpacing=0 cellPadding=0 width=\"100%\" height=\"96%\" border=0\r\n");
      out.write("\tbgColor=#f8f8f8>\r\n");
      out.write("\t<TBODY>\r\n");
      out.write("\t\t<TR>\r\n");
      out.write("\t\t\t<TD width=\"100%\" bgColor=#f8f8f8 height=120>\r\n");
      out.write("\t\t\t<center>\r\n");
      out.write("\t\t\t<p>对不起,因为太长时间未操作,</p>\r\n");
      out.write("\t\t\t<p>出于安全性考虑，您必须重新登录才能继续使用</p>\r\n");
      out.write("\t\t\t<p><a href=\"../index.jsp\" target=\"_parent\">登 录</a></p>\r\n");
      out.write("\t\t\t</center>\r\n");
      out.write("\t\t\t</TD>\r\n");
      out.write("\t\t\t<TD width=64>&nbsp;</TD>\r\n");
      out.write("\t\t</TR>\r\n");
      out.write("\t</TBODY>\r\n");
      out.write("</TABLE>\r\n");
      out.write("<TABLE cellSpacing=0 cellPadding=0 width=\"100%\" border=0 bgColor=#f8f8f8>\r\n");
      out.write("\t<TBODY>\r\n");
      out.write("\t\t<TR>\r\n");
      out.write("\t\t\t<TD width=280 bgColor=#f8f8f8>&nbsp;</TD>\r\n");
      out.write("\t\t\t<TD bgColor=#f8f8f8><IMG height=5 src=\"../images/Index_Txt2.gif\"\r\n");
      out.write("\t\t\t\twidth=453></TD>\r\n");
      out.write("\t\t</TR>\r\n");
      out.write("\t</TBODY>\r\n");
      out.write("</TABLE>\r\n");
      out.write("\r\n");
      out.write("</BODY>\r\n");
      out.write("</html:html>\r\n");
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
}
