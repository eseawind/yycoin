/**
 * File Name: StorageManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-22<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.manager;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.StorageBean;


/**
 * StorageManager
 * 
 * @author ZHUZHU
 * @version 2010-8-22
 * @see StorageManager
 * @since 1.0
 */
public interface StorageManager
{
    boolean addBean(User user, StorageBean bean)
        throws MYException;

    boolean updateBean(User user, StorageBean bean)
        throws MYException;

    boolean deleteBean(User user, final String id)
        throws MYException;
    
}
