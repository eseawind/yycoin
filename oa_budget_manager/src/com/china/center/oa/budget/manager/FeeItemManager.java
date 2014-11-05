/**
 * File Name: FeeItemManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-5-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.manager;


import java.io.Serializable;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.budget.bean.FeeItemBean;


/**
 * FeeItemManager
 * 
 * @author ZHUZHU
 * @version 2011-5-14
 * @see FeeItemManager
 * @since 3.0
 */
public interface FeeItemManager
{
    boolean addBean(User user, FeeItemBean bean)
        throws MYException;

    boolean updateBean(User user, FeeItemBean bean)
        throws MYException;

    boolean deleteBean(User user, Serializable id)
        throws MYException;
}
