/**
 * File Name: DepotManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.manager;


import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.listener.DepotListener;


/**
 * DepotManager
 * 
 * @author ZHUZHU
 * @version 2010-8-15
 * @see DepotManager
 * @since 1.0
 */
public interface DepotManager extends ListenerManager<DepotListener>
{
    boolean addDepot(User user, DepotBean bean)
        throws MYException;

    boolean updateDepot(User user, DepotBean bean)
        throws MYException;

    boolean deleteDepot(User user, String id)
        throws MYException;

}
