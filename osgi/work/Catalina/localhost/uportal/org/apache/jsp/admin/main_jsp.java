package org.apache.jsp.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class main_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(3);
    _jspx_dependants.add("/admin/../common/common.jsp");
    _jspx_dependants.add("/WEB-INF/tld/pageTld.tld");
    _jspx_dependants.add("/WEB-INF/tld/common.tld");
  }

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

      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>SKY-OA系统</title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<script language=\"JavaScript\" type=\"text/JavaScript\">\r\n");
      out.write("function Logout()\r\n");
      out.write("{\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("<frameset rows=\"63,*\" cols=\"*\" frameborder=\"NO\" border=\"0\"\r\n");
      out.write("\tframespacing=\"0\" onUnload=\"Logout()\">\r\n");
      out.write("\t<frame src=\"top.jsp\" name=\"topFrame\" scrolling=\"NO\" id=\"topFrame\">\r\n");
      out.write("\t<frameset cols=\"191,*\" framespacing=\"0\" frameborder=\"no\" border=\"0\">\r\n");
      out.write("\t\r\n");
      out.write("\t\t<frame src=\"shousuo.jsp\" name=\"fun\" noresize scrolling=\"auto\">\r\n");
      out.write("\r\n");
      out.write("\t\t<frame src=\"welcome.jsp\" name=\"main\">\r\n");
      out.write("\t</frameset>\r\n");
      out.write("</frameset>\r\n");
      out.write("<noframes>\r\n");
      out.write("<body>\r\n");
      out.write("你使用的浏览器不支持框架。\r\n");
      out.write("</body>\r\n");
      out.write("</noframes>\r\n");
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
}
