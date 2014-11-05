/**
 * File Name: AdminFilterListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.stock.portal.init;


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
 * @see NetAskFilterListener
 * @since 1.0
 */
public class NetAskFilterListener implements FilterListener
{
    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.webportal.filter.FilterListener#onDoFilterAfterCheckUser(javax.servlet.ServletRequest,
     *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public boolean onDoFilterAfterCheckUser(ServletRequest req, ServletResponse resp, FilterChain chain)
        throws ServletException, IOException
    {
        HttpServletRequest request = (HttpServletRequest)req;

        Object lock = request.getSession().getAttribute("GProvider");

        // 直接下一个
        if (lock == null)
        {
            return false;
        }

        final String servletPath = request.getServletPath();

        // 这个是外网询价的操作
        if (servletPath.startsWith("/netask"))
        {
            return false;
        }

        String net = request.getParameter("net");

        if (servletPath.startsWith("/stock/ask") && "1".equals(net))
        {
            return false;
        }

        RequestDispatcher lockDispatch = request.getRequestDispatcher("/netask/checkuser.do?method=logout");

        lockDispatch.forward(req, resp);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return getClass().getName();
    }

}
