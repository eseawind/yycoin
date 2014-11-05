/**
 * File Name: CustomerCheckManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.manager;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.customer.bean.CustomerCheckBean;
import com.china.center.oa.customer.bean.CustomerCheckItemBean;


/**
 * CustomerCheckManager
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CustomerCheckManager
 * @since 1.0
 */
public interface CustomerCheckManager
{
    boolean addBean(User user, CustomerCheckBean bean)
        throws MYException;

    boolean passBean(User user, String id)
        throws MYException;

    boolean goonBean(User user, CustomerCheckBean bean)
        throws MYException;

    boolean updateCheckItem(User user, CustomerCheckItemBean bean)
        throws MYException;

    int autoChangeStatus()
        throws MYException;

    boolean delBean(User user, String id)
        throws MYException;

    boolean rejectBean(User user, String id)
        throws MYException;
}
