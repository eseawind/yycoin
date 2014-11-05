/**
 * File Name: MailCleanManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-5<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.mail.manager.impl;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.config.ConfigLoader;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.mail.bean.AttachmentBean;
import com.china.center.oa.mail.dao.AttachmentDAO;
import com.china.center.oa.mail.dao.MailDAO;
import com.china.center.oa.mail.dao.MailDAO2;
import com.china.center.oa.mail.manager.MailCleanManager;
import com.china.center.tools.FileTools;
import com.china.center.tools.TimeTools;


/**
 * MailCleanManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-12-5
 * @see MailCleanManagerImpl
 * @since 3.0
 */
public class MailCleanManagerImpl implements MailCleanManager
{
    private final Log _logger = LogFactory.getLog("trigger");

    private MailDAO mailDAO = null;

    private MailDAO2 mailDAO2 = null;

    private AttachmentDAO attachmentDAO = null;

    private int days = -180;

    /**
     * default constructor
     */
    public MailCleanManagerImpl()
    {
    }

    @Transactional(rollbackFor = MYException.class)
    public void cleanMail()
    {
        _logger.info("cleanMail begin......");

        ConditionParse condition = new ConditionParse();

        condition.addWhereStr();

        condition.addCondition("logTime", "<=", TimeTools.now(this.days));

        mailDAO.deleteEntityBeansByCondition(condition);

        mailDAO2.deleteEntityBeansByCondition(condition);

        // 最多取出5000
        List<AttachmentBean> attachmentList = attachmentDAO
            .queryEntityBeansByLimit(condition, 5000);

        for (AttachmentBean attachmentBean : attachmentList)
        {
            String file = getMailAttchmentPath() + attachmentBean.getPath();

            FileTools.deleteFile(file);

            _logger.info("delete mail attachment:" + file);
        }

        attachmentDAO.deleteEntityBeansByCondition(condition);

        _logger.info("cleanMail end......");
    }

    /**
     * @return the mailAttchmentPath
     */
    public String getMailAttchmentPath()
    {
        return ConfigLoader.getProperty("mailAttchmentPath");
    }

    /**
     * @return the mailDAO
     */
    public MailDAO getMailDAO()
    {
        return mailDAO;
    }

    /**
     * @param mailDAO
     *            the mailDAO to set
     */
    public void setMailDAO(MailDAO mailDAO)
    {
        this.mailDAO = mailDAO;
    }

    /**
     * @return the mailDAO2
     */
    public MailDAO2 getMailDAO2()
    {
        return mailDAO2;
    }

    /**
     * @param mailDAO2
     *            the mailDAO2 to set
     */
    public void setMailDAO2(MailDAO2 mailDAO2)
    {
        this.mailDAO2 = mailDAO2;
    }

    /**
     * @return the attachmentDAO
     */
    public AttachmentDAO getAttachmentDAO()
    {
        return attachmentDAO;
    }

    /**
     * @param attachmentDAO
     *            the attachmentDAO to set
     */
    public void setAttachmentDAO(AttachmentDAO attachmentDAO)
    {
        this.attachmentDAO = attachmentDAO;
    }

    /**
     * @return the days
     */
    public int getDays()
    {
        return days;
    }

    /**
     * @param days
     *            the days to set
     */
    public void setDays(int days)
    {
        this.days = days;
    }
}
