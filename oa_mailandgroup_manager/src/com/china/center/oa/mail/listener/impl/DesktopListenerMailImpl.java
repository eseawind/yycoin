/**
 * File Name: DeskListenerMailImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-12-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.mail.listener.impl;


import java.util.ArrayList;
import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.gm.constant.MailConstant;
import com.china.center.oa.mail.bean.MailBean;
import com.china.center.oa.mail.dao.MailDAO;
import com.china.center.oa.publics.listener.DesktopListener;
import com.china.center.oa.publics.wrap.DesktopItemWrap;
import com.china.center.oa.publics.wrap.DesktopWrap;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * DeskListenerMailImpl
 * 
 * @author ZHUZHU
 * @version 2011-12-3
 * @see DesktopListenerMailImpl
 * @since 3.0
 */
public class DesktopListenerMailImpl implements DesktopListener
{
    private MailDAO mailDAO = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.DeskListener#getDeskWrap()
     */
    public DesktopWrap getDeskWrap(User user)
    {
        DesktopWrap wrap = new DesktopWrap();

        wrap.setName("我的邮件");

        final ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addCondition("MailBean.reveiveId", "=", user.getStafferId());

        condtion.addIntCondition("MailBean.status", "=", MailConstant.STATUS_INIT);

        // the nearest five days
        condtion.addCondition("MailBean.logTime", ">=", TimeTools.now( -5));

        condtion.addCondition("order by MailBean.logTime desc");

        List<MailBean> mailList = mailDAO.queryEntityBeansByLimit(condtion, 5);

        List<DesktopItemWrap> itemList = new ArrayList();

        for (MailBean mailBean : mailList)
        {
            DesktopItemWrap item = new DesktopItemWrap();

            item.setHref("../mail/mail.do?method=findMail&id=" + mailBean.getId());
            item.setTips(mailBean.getTitle());
            // [${item.logTime}] ${my:truncateString(item.title, 0, 45)}
            item.setTitle("[" + mailBean.getLogTime() + "] "
                          + StringTools.truncateString(mailBean.getTitle(), 0, 45));

            itemList.add(item);
        }

        wrap.setItemList(itemList);

        return wrap;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.DeskListener#getKey()
     */
    public String getKey()
    {
        return "mail";
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

}
