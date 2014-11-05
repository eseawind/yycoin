/**
 * File Name: NotifyManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager.impl;


import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.NotifyBean;
import com.china.center.oa.publics.listener.NotifyListener;
import com.china.center.oa.publics.manager.NotifyManager;


/**
 * NotifyManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-11-7
 * @see NotifyManagerImpl
 * @since 1.0
 */
public class NotifyManagerImpl extends AbstractListenerManager<NotifyListener> implements NotifyManager
{
    private final Log _logger = LogFactory.getLog(getClass());

    /**
     * default constructor
     */
    public NotifyManagerImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.manager.NotifyManager#notifyWithTransaction(java.lang.String,
     *      com.china.center.oa.publics.bean.NotifyBean)
     */
    public void notifyWithTransaction(final String stafferId, final NotifyBean bean)
    {
        Collection<NotifyListener> listenerMapValues = listenerMapValues();

        for (NotifyListener notifyListener : listenerMapValues)
        {
            try
            {
                notifyListener.notifyWithTransaction(stafferId, bean);
            }
            catch (Throwable e)
            {
                _logger.error(e, e);
            }
        }

        _logger.info(bean);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.manager.NotifyManager#notifyWithTransactionUseException(java.lang.String,
     *      com.china.center.oa.publics.bean.NotifyBean)
     */
    public void notifyWithTransactionUseException(final String stafferId, final NotifyBean bean)
        throws MYException
    {
        try
        {
            Collection<NotifyListener> listenerMapValues = listenerMapValues();

            for (NotifyListener notifyListener : listenerMapValues)
            {
                notifyListener.notifyWithTransaction(stafferId, bean);
            }

            _logger.info(bean);
        }
        catch (Throwable e)
        {
            _logger.error(e, e);

            throw new MYException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.manager.NotifyManager#notifyWithoutTransaction(java.lang.String,
     *      com.china.center.oa.publics.bean.NotifyBean)
     */
    public void notifyWithoutTransaction(String stafferId, NotifyBean bean)
    {

        Collection<NotifyListener> listenerMapValues = listenerMapValues();

        for (NotifyListener notifyListener : listenerMapValues)
        {
            try
            {
                notifyListener.notifyWithoutTransaction(stafferId, bean);
            }
            catch (Throwable e)
            {
                _logger.error(e, e);
            }
        }

        _logger.info(bean);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.manager.NotifyManager#notifyWithoutTransactionUseException(java.lang.String,
     *      com.china.center.oa.publics.bean.NotifyBean)
     */
    public void notifyWithoutTransactionUseException(String stafferId, NotifyBean bean)
        throws MYException
    {
        try
        {
            Collection<NotifyListener> listenerMapValues = listenerMapValues();

            for (NotifyListener notifyListener : listenerMapValues)
            {
                notifyListener.notifyWithoutTransaction(stafferId, bean);
            }

            _logger.info(bean);
        }
        catch (Throwable e)
        {
            _logger.error(e, e);

            throw new MYException(e);
        }
    }

    public void notifyMessage(String stafferId, String msg)
    {
        NotifyBean notify = new NotifyBean();

        notify.setMessage(msg);

        notifyWithoutTransaction(stafferId, notify);
    }
}
