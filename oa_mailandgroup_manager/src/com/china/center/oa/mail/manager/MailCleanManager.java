/**
 * File Name: MailCleanManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-5<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.mail.manager;

/**
 * MailCleanManager
 * 
 * @author ZHUZHU
 * @version 2010-12-5
 * @see MailCleanManager
 * @since 3.0
 */
public interface MailCleanManager
{
    /**
     * 清理六个月前的邮件
     */
    void cleanMail();
}
