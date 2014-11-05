/**
 * File Name: MailConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-4-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.gm.constant;


import com.china.center.jdbc.annotation.Defined;


/**
 * MailConstant
 * 
 * @author zhuzhu
 * @version 2009-4-12
 * @see MailConstant
 * @since 1.0
 */
public interface MailConstant
{
    /**
     * 收件人
     */
    int MAX_LENGTH = 500;

    /**
     * 未阅读
     */
    @Defined(key = "mailStatus", value = "未阅读")
    int STATUS_INIT = 0;

    /**
     * 已阅读
     */
    @Defined(key = "mailStatus", value = "已阅读")
    int STATUS_READ = 1;

    /**
     * 未回复
     */
    @Defined(key = "mailFeeback", value = "未回复")
    int FEEBACK_NO = 0;

    /**
     * 已回复
     */
    @Defined(key = "mailFeeback", value = "已回复")
    int FEEBACK_YES = 1;

    /**
     * 无附件
     */
    @Defined(key = "mailAttachment", value = "无附件")
    int ATTACHMENT_NO = 0;

    /**
     * 有附件
     */
    @Defined(key = "mailAttachment", value = "有附件")
    int ATTACHMENT_YES = 1;
}
