/**
 * File Name: StorageApplyManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-28<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.manager;


import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.GSOutBean;
import com.china.center.oa.product.bean.StorageApplyBean;
import com.china.center.oa.product.listener.StorageApplyListener;


/**
 * StorageApplyManager
 * 
 * @author ZHUZHU
 * @version 2010-10-28
 * @see StorageApplyManager
 * @since 1.0
 */
public interface StorageApplyManager extends ListenerManager<StorageApplyListener>
{
    boolean addBean(User user, StorageApplyBean bean)
        throws MYException;

    boolean passBean(User user, String id)
        throws MYException;

    boolean rejectBean(User user, String id)
        throws MYException;
    
    boolean addGSOutBean(User user, GSOutBean bean)
    throws MYException;
    
    boolean updateGSOutBean(User user, GSOutBean bean)
    throws MYException;
    
    boolean deleteGSOutBean(User user, String id)
    throws MYException;
    
    boolean passGSOutBean(User user, String id, int nextStatus)
    throws MYException;
    
    boolean rejectGSOutBean(User user, String id, String reason)
    throws MYException;
    
}
