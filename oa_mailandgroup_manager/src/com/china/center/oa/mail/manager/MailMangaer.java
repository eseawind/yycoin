/**
 * File Name: MailMangaer.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.mail.manager;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.mail.bean.MailBean;


/**
 * MailMangaer
 * 
 * @author ZHUZHU
 * @version 2010-7-4
 * @see MailMangaer
 * @since 1.0
 */
public interface MailMangaer
{
    boolean addBean(User user, MailBean bean)
        throws MYException;

    boolean addMailWithoutTransactional(User user, MailBean bean)
        throws MYException;

    boolean deleteBean(User user, String id)
        throws MYException;

    boolean deleteBean2(User user, String id)
        throws MYException;

    boolean updateBean(User user, MailBean bean)
        throws MYException;

    boolean updateStatusToRead(User user, String id)
        throws MYException;

    boolean updateFeebackToYes(User user, String id)
        throws MYException;
}
