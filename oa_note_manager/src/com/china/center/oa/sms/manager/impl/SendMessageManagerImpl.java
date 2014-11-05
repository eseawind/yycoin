/**
 * File Name: SendMessageManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sms.manager.impl;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.center.china.osgi.config.ConfigLoader;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.note.bean.ShortMessageTaskBean;
import com.china.center.oa.note.constant.ShortMessageConstant;
import com.china.center.oa.note.dao.ShortMessageTaskDAO;
import com.china.center.oa.sms.helper.SMSHelper;
import com.china.center.oa.sms.manager.SendMessageManager;
import com.china.center.tools.TimeTools;


/**
 * SendMessageManagerImpl(by MAS)
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see SendMessageManagerImpl
 * @since 1.0
 */
public class SendMessageManagerImpl implements SendMessageManager
{
    private ShortMessageTaskDAO shortMessageTaskDAO = null;

    private final Log _logger = LogFactory.getLog(getClass());

    private Log SMSLOG = LogFactory.getLog("sms");

    private PlatformTransactionManager transactionManager = null;

    /**
     * default constructor
     */
    public SendMessageManagerImpl()
    {
    }

    /**
     * sendSMSByMAS
     */
    public void sendShortMessage()
    {
        ConditionParse condition = new ConditionParse();

        condition.addWhereStr();

        condition.addIntCondition("status", "=", ShortMessageConstant.STATUS_WAIT_SEND);

        condition.addCondition("order by logTime desc");

        List<ShortMessageTaskBean> smsList = shortMessageTaskDAO.queryEntityBeansByLimit(condition, 200);

        // 操作在数据库事务中完成
        TransactionTemplate tranTemplate = new TransactionTemplate(transactionManager);

        for (final ShortMessageTaskBean shortMessageTaskBean : smsList)
        {
            sendSMS(shortMessageTaskBean, tranTemplate);
        }
    }

    /**
     * sendSMS
     * 
     * @param shortMessageTaskBean
     * @return Boolean.TRUE
     */
    private Object sendSMS(final ShortMessageTaskBean shortMessageTaskBean, TransactionTemplate tranTemplate)
    {
        final boolean isSend = SMSHelper.sendSMSReal(getSendSMSURL(), shortMessageTaskBean.getReceiver().trim(),
            shortMessageTaskBean.getMessage().trim(), shortMessageTaskBean.getHandId(), getExNumber());

        try
        {
            // must send each item in Transaction(may be wait)
            tranTemplate.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    if (isSend)
                    {
                        shortMessageTaskBean.setStatus(ShortMessageConstant.STATUS_SEND_SUCCESS);

                        if (shortMessageTaskBean.getMtype() == ShortMessageConstant.MTYPE_ONLY_SEND)
                        {
                            shortMessageTaskDAO.deleteEntityBean(shortMessageTaskBean.getId());
                        }
                        else
                        {
                            shortMessageTaskDAO.updateEntityBean(shortMessageTaskBean);
                        }
                    }
                    else
                    {
                        int fail = shortMessageTaskBean.getFail() + 1;

                        if (fail >= 3)
                        {
                            shortMessageTaskDAO.deleteEntityBean(shortMessageTaskBean.getId());

                            SMSLOG.warn("send faid and delete " + shortMessageTaskBean);

                            return Boolean.TRUE;
                        }

                        shortMessageTaskBean.setStatus(ShortMessageConstant.STATUS_INIT);

                        shortMessageTaskBean.setFail(fail);

                        // 过20分钟
                        shortMessageTaskBean.setSendTime(TimeTools.getDateTimeString(1200000));

                        shortMessageTaskDAO.updateEntityBean(shortMessageTaskBean);
                    }

                    return Boolean.TRUE;
                }
            });
        }
        catch (Throwable e)
        {
            _logger.error(e, e);
        }

        return Boolean.TRUE;
    }

    /**
     * @return the shortMessageTaskDAO
     */
    public ShortMessageTaskDAO getShortMessageTaskDAO()
    {
        return shortMessageTaskDAO;
    }

    /**
     * @param shortMessageTaskDAO
     *            the shortMessageTaskDAO to set
     */
    public void setShortMessageTaskDAO(ShortMessageTaskDAO shortMessageTaskDAO)
    {
        this.shortMessageTaskDAO = shortMessageTaskDAO;
    }

    /**
     * @return the sendSMSURL
     */
    public String getSendSMSURL()
    {
        return ConfigLoader.getProperty("sendSMSURL");
    }

    /**
     * @return the exNumber
     */
    public String getExNumber()
    {
        return ConfigLoader.getProperty("exNumber");
    }

    /**
     * @return the transactionManager
     */
    public PlatformTransactionManager getTransactionManager()
    {
        return transactionManager;
    }

    /**
     * @param transactionManager
     *            the transactionManager to set
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager)
    {
        this.transactionManager = transactionManager;
    }
}
