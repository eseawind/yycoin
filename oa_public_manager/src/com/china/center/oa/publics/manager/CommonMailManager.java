package com.china.center.oa.publics.manager;

/**
 * 
 * 邮件管理
 *
 * @author fangliwen 2012-10-21
 */
public interface CommonMailManager 
{

    /**
     * 异步发送邮件
     * @param message
     */
    void sendMail(String to, String subject, String message);
    
    void sendMail(String to, String subject, String message, String file);
}
