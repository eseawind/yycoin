/**
 * File Name: NotifyHelper.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.helper;


import java.util.Formatter;

import com.china.center.oa.publics.bean.NotifyBean;


/**
 * NotifyHelper
 * 
 * @author ZHUZHU
 * @version 2010-11-7
 * @see NotifyHelper
 * @since 1.0
 */
public abstract class NotifyHelper
{
    /**
     * create a simple NotifyBean
     * 
     * @param message
     * @return
     */
    public static NotifyBean simpleNotify(String message)
    {
        NotifyBean bean = new NotifyBean();

        bean.setMessage(message);

        return bean;
    }

    /**
     * create a simple NotifyBean
     * 
     * @param message
     * @return
     */
    public static NotifyBean simpleNotify(String message, Object... par)
    {
        NotifyBean bean = new NotifyBean();

        Formatter formatter = new Formatter();

        bean.setMessage(formatter.format(message, par).toString());

        return bean;
    }
}
