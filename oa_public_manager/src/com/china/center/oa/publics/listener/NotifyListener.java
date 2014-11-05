/**
 * File Name: NotifyManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.listener;


import com.center.china.osgi.publics.ParentListener;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.NotifyBean;


/**
 * NotifyListener(系统内通知,可以多实现哦)
 * 
 * @author ZHUZHU
 * @version 2010-11-7
 * @see NotifyListener
 * @since 1.0
 */
public interface NotifyListener extends ParentListener
{
    /**
     * notifyWithoutTransaction(通知失败了也不抛出异常)
     * 
     * @param stafferId
     * @param bean
     */
    void notifyWithoutTransaction(String stafferId, NotifyBean bean)
        throws MYException;

    /**
     * notifyWithTransaction(通知失败了也不抛出异常)
     * 
     * @param stafferId
     * @param bean
     */
    void notifyWithTransaction(String stafferId, NotifyBean bean)
        throws MYException;

}
