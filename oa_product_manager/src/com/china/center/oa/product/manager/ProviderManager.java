/**
 * File Name: ProviderManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.manager;


import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.ProviderBean;
import com.china.center.oa.product.bean.ProviderUserBean;
import com.china.center.oa.product.listener.ProviderListener;


/**
 * ProviderManager
 * 
 * @author ZHUZHU
 * @version 2010-8-21
 * @see ProviderManager
 * @since 1.0
 */
public interface ProviderManager extends ListenerManager<ProviderListener>
{
    boolean addBean(User user, ProviderBean bean)
        throws MYException;

    boolean bingProductTypeToCustmer(User user, String pid, String[] productTypeIds)
        throws MYException;

    boolean addOrUpdateUserBean(User user, ProviderUserBean bean)
        throws MYException;

    boolean updateUserPassword(User user, String id, String newpwd)
        throws MYException;

    boolean updateUserPwkey(User user, String id, String newpwkey)
        throws MYException;

    boolean updateBean(User user, ProviderBean bean)
        throws MYException;

    boolean delBean(User user, String id)
        throws MYException;

    boolean checkHisProvider(User user, String cid)
        throws MYException;
}
