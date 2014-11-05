/**
 * File Name: MailGroupFacade.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.mg.facade;


import com.china.center.common.MYException;
import com.china.center.oa.group.bean.GroupBean;
import com.china.center.oa.mail.bean.MailBean;


/**
 * MailGroupFacade
 * 
 * @author ZHUZHU
 * @version 2010-7-4
 * @see MailGroupFacade
 * @since 1.0
 */
public interface MailGroupFacade
{
    boolean addGroup(String userId, GroupBean bean)
        throws MYException;

    boolean updateGroup(String userId, GroupBean bean)
        throws MYException;

    boolean delGroup(String userId, String id)
        throws MYException;

    boolean addMail(String userId, MailBean bean)
        throws MYException;

    boolean deleteMail(String userId, String id)
        throws MYException;

    boolean deleteMail2(String userId, String id)
        throws MYException;

    boolean updateStatusToRead(String userId, String id)
        throws MYException;

    boolean updateFeebackToYes(String userId, String id)
        throws MYException;
}
