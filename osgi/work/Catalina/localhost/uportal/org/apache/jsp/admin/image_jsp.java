package org.apache.jsp.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.io.OutputStream;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import javax.imageio.*;

public final class image_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

Color getRandColor(int fc, int bc)
    {
        Random random = new Random();
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
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
      response.setContentType("image/jpeg; charset=gb2312");
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
      out.write('\r');
      out.write('\n');

            //out.clear();//杩�����resin������濡����omacat��互涓��杩��  
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            int width = 60, height = 20;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            Graphics g = image.getGraphics();
            Random random = new Random();

            g.setColor(getRandColor(200, 250));
            g.fillRect(0, 0, width, height);

            g.setFont(new Font("Times   New   Roman", Font.PLAIN, 18));

            g.setColor(getRandColor(160, 200));
            for (int i = 0; i < 155; i++ )
            {
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                int xl = random.nextInt(10);
                int yl = random.nextInt(10);
                g.drawLine(x, y, x + xl, y + yl);
            }

            char c[] = new char[62];

            for (int i = 97, j = 0; i < 123; i++ , j++ )
            {
                c[j] = (char)i;
            }
            for (int o = 65, p = 26; o < 91; o++ , p++ )
            {
                c[p] = (char)o;
            }
            for (int m = 48, n = 52; m < 58; m++ , n++ )
            {
                c[n] = (char)m;
            }
            String sRand = "";
            for (int i = 0; i < 4; i++ )
            {
                int x = random.nextInt(62);
                String rand = String.valueOf(c[x]);
                sRand += rand;

                g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110),
                    20 + random.nextInt(110)));
                g.drawString(rand, 13 * i + 6, 16);
            }

            //   灏��璇��瀛��SESSION  
            session.setAttribute("rand", sRand);
            
            g.dispose();
            
            ImageIO.setUseCache(true);
            
            OutputStream os=response.getOutputStream();

            ImageIO.write(image, "JPEG", os);
            
            os.flush();
            os.close();
            os=null;
            response.flushBuffer();
            out.clear();
            out = pageContext.pushBody();


      out.write('\r');
      out.write('\n');
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
