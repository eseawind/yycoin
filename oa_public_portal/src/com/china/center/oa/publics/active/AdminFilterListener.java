/**
 * File Name: AdminFilterListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.active;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.china.center.webportal.filter.FilterListener;


/**
 * AdminFilterListener
 * 
 * @author ZHUZHU
 * @version 2010-7-3
 * @see AdminFilterListener
 * @since 1.0
 */
public class AdminFilterListener implements FilterListener
{
    public boolean onDoFilterAfterCheckUser(ServletRequest req, ServletResponse resp,
                                            FilterChain chain)
        throws ServletException, IOException
    {
        HttpServletRequest request = (HttpServletRequest)req;

        // 处理menu的逻辑
        String menu = request.getParameter("menu");

        if ("1".equals(menu))
        {
            request.getSession().setAttribute("f_menu", menu);
        }

        Object lock = request.getSession().getAttribute("SLOCK");

        if (lock != null && lock.toString().equals("true"))
        {
            final String servletPath = request.getServletPath();

            if (servletPath.startsWith("/admin/checkuser.do?method=unlock"))
            {
                return false;
            }

            if (servletPath.startsWith("/admin/logout"))
            {
                return false;
            }

            RequestDispatcher lockDispatch = request.getRequestDispatcher("/admin/lock.jsp");

            lockDispatch.forward(req, resp);

            return true;
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "Publics.AdminFilterListener";
    }

}
