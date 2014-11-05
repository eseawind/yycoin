/**
 * File Name: NotifyListenerMailImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.mail.listener.impl;


import com.china.center.common.MYException;
import com.china.center.oa.mail.bean.MailBean;
import com.china.center.oa.mail.manager.MailMangaer;
import com.china.center.oa.publics.bean.NotifyBean;
import com.china.center.oa.publics.helper.UserHelper;
import com.china.center.oa.publics.listener.NotifyListener;


/**
 * NotifyListenerMailImpl(邮件通知)
 * 
 * @author ZHUZHU
 * @version 2010-11-7
 * @see NotifyListenerMailImpl
 * @since 1.0
 */
public class NotifyListenerMailImpl implements NotifyListener
{
    private MailMangaer mailMangaer = null;

    /**
     * default constructor
     */
    public NotifyListenerMailImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.NotifyListener#notifyWithTransaction(java.lang.String,
     *      com.china.center.oa.publics.bean.NotifyBean)
     */
    public void notifyWithTransaction(String stafferId, NotifyBean bean)
        throws MYException
    {
        MailBean mail = createMailBean(stafferId, bean);

        mailMangaer.addBean(UserHelper.getSystemUser(), mail);
    }

    private MailBean createMailBean(String stafferId, NotifyBean bean)
    {
        MailBean mail = new MailBean();

        mail.setReveiveId(stafferId);

        mail.setReveiveIds(stafferId + ";");

        mail.setSenderId(bean.getNotifyer());

        mail.setTitle(bean.getMessage());

        mail.setContent(bean.getMessage());

        mail.setHref(bean.getUrl());

        return mail;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.NotifyListener#notifyWithoutTransaction(java.lang.String,
     *      com.china.center.oa.publics.bean.NotifyBean)
     */
    public void notifyWithoutTransaction(String stafferId, NotifyBean bean)
        throws MYException
    {
        MailBean mail = createMailBean(stafferId, bean);

        mailMangaer.addMailWithoutTransactional(UserHelper.getSystemUser(), mail);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "NotifyListenerMail.Impl";
    }

    /**
     * @return the mailMangaer
     */
    public MailMangaer getMailMangaer()
    {
        return mailMangaer;
    }

    /**
     * @param mailMangaer
     *            the mailMangaer to set
     */
    public void setMailMangaer(MailMangaer mailMangaer)
    {
        this.mailMangaer = mailMangaer;
    }

}
