/**
 * File Name: NotifyManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;


import com.center.china.osgi.publics.ListenerManager;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.NotifyBean;
import com.china.center.oa.publics.listener.NotifyListener;


/**
 * NotifyManager(系统内通知,可以多实现哦)
 * 
 * @author ZHUZHU
 * @version 2010-11-7
 * @see NotifyManager
 * @since 1.0
 */
public interface NotifyManager extends ListenerManager<NotifyListener>
{
    /**
     * notifyWithoutTransaction(事务由调用者提供,通知失败了也不抛出异常,不管多少通知)
     * 
     * @param stafferId
     * @param bean
     */
    void notifyWithoutTransaction(String stafferId, NotifyBean bean);

    /**
     * notifyMessage(事务由调用者提供,通知失败了也不抛出异常,不管多少通知)
     * 
     * @param stafferId
     * @param msg
     */
    void notifyMessage(String stafferId, String msg);

    /**
     * notifyWithoutTransaction(通知失败了抛出异常,且只要一个通知失败就抛出异常,后面的不执行)
     * 
     * @param stafferId
     * @param bean
     */
    void notifyWithoutTransactionUseException(String stafferId, NotifyBean bean)
        throws MYException;

    /**
     * notifyWithTransaction(通知失败了也不抛出异常,不管多少通知)
     * 
     * @param stafferId
     * @param bean
     */
    void notifyWithTransaction(String stafferId, NotifyBean bean);

    /**
     * notifyWithTransaction(通知失败了抛出异常,提示通知者,且只要一个通知失败就抛出异常,后面的不执行)
     * 
     * @param stafferId
     * @param bean
     */
    void notifyWithTransactionUseException(String stafferId, NotifyBean bean)
        throws MYException;
}
