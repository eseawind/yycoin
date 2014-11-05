/**
 * File Name: NetLoginHelper.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.stock.action.helper;


import javax.servlet.http.HttpServletRequest;


/**
 * NetLoginHelper
 * 
 * @author ZHUZHU
 * @version 2010-9-12
 * @see NetLoginHelper
 * @since 1.0
 */
public abstract class NetLoginHelper
{
    /**
     * isNetLogin
     * 
     * @param request
     * @return
     */
    public static boolean isNetLogin(HttpServletRequest request)
    {
        Object lock = request.getSession().getAttribute("GProvider");

        if (lock == null)
        {
            return false;
        }

        return true;
    }
}
