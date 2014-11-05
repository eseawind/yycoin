/**
 * File Name: BackPayApplyManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-3-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.manager;


import java.util.List;

import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.BackPayApplyBean;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.listener.BackPayApplyListener;


/**
 * BackPayApplyManager
 * 
 * @author ZHUZHU
 * @version 2011-3-3
 * @see BackPayApplyManager
 * @since 3.0
 */
public interface BackPayApplyManager extends ListenerManager<BackPayApplyListener>
{
    boolean addBackPayApplyBean(User user, BackPayApplyBean bean)
        throws MYException;

    boolean passBackPayApplyBean(User user, String id, String reason)
        throws MYException;

    boolean rejectBackPayApplyBean(User user, String id, String reason)
        throws MYException;

    boolean deleteBackPayApplyBean(User user, String id)
        throws MYException;

    /**
     * 结束申请退款(需要同步)
     * 
     * @param stafferId
     * @param id
     * @return
     * @throws MYException
     */
    boolean endBackPayApplyBean(User user, String id, String reason, List<OutBillBean> outBillList)
        throws MYException;
    
    boolean backToPreWithOutTransaction(User user, BackPayApplyBean bean) 
        throws MYException;
}
