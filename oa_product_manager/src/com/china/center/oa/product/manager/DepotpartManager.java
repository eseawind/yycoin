/**
 * File Name: DepotpartManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-22<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.manager;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.DepotpartBean;


/**
 * DepotpartManager
 * 
 * @author ZHUZHU
 * @version 2010-8-22
 * @see DepotpartManager
 * @since 1.0
 */
public interface DepotpartManager
{
    boolean addBean(User user, DepotpartBean bean)
        throws MYException;

    boolean updateBean(User user, DepotpartBean bean)
        throws MYException;

    boolean deleteBean(User user, final String id)
        throws MYException;
}
