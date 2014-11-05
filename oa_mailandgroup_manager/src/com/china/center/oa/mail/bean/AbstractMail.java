/**
 * File Name: AbstractMail.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-4-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.mail.bean;


import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Ignore;
import com.china.center.oa.gm.constant.MailConstant;


/**
 * AbstractMail
 * 
 * @author ZHUZHU
 * @version 2009-4-15
 * @see AbstractMail
 * @since 1.0
 */
public class AbstractMail implements Serializable
{
    private String title = "";

    private String content = "";

    private String senderId = "";

    private String senderName = "";

    /**
     * 邮件所有者
     */
    private String reveiveId = "";

    /**
     * 主送
     */
    private String reveiveIds = "";

    private String reveiveNames = "";

    /**
     * 抄送
     */
    private String reveiveIds2 = "";

    private String reveiveNames2 = "";

    private String reveiveIds3 = "";

    private String reveiveNames3 = "";

    private String href = "";

    /**
     * ref mail.use in feeback
     */
    private String refId = "";

    private int status = MailConstant.STATUS_INIT;

    private int feeback = MailConstant.FEEBACK_NO;

    private int attachment = MailConstant.ATTACHMENT_NO;

    private String logTime = "";

    @Ignore
    private List<AttachmentBean> attachments = null;

    /**
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @return the content
     */
    public String getContent()
    {
        return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content)
    {
        this.content = content;
    }

    /**
     * @return the senderId
     */
    public String getSenderId()
    {
        return senderId;
    }

    /**
     * @param senderId
     *            the senderId to set
     */
    public void setSenderId(String senderId)
    {
        this.senderId = senderId;
    }

    /**
     * @return the senderName
     */
    public String getSenderName()
    {
        return senderName;
    }

    /**
     * @param senderName
     *            the senderName to set
     */
    public void setSenderName(String senderName)
    {
        this.senderName = senderName;
    }

    /**
     * @return the reveiveId
     */
    public String getReveiveId()
    {
        return reveiveId;
    }

    /**
     * @param reveiveId
     *            the reveiveId to set
     */
    public void setReveiveId(String reveiveId)
    {
        this.reveiveId = reveiveId;
    }

    /**
     * @return the reveiveIds
     */
    public String getReveiveIds()
    {
        return reveiveIds;
    }

    /**
     * @param reveiveIds
     *            the reveiveIds to set
     */
    public void setReveiveIds(String reveiveIds)
    {
        this.reveiveIds = reveiveIds;
    }

    /**
     * @return the reveiveNames
     */
    public String getReveiveNames()
    {
        return reveiveNames;
    }

    /**
     * @param reveiveNames
     *            the reveiveNames to set
     */
    public void setReveiveNames(String reveiveNames)
    {
        this.reveiveNames = reveiveNames;
    }

    /**
     * @return the reveiveIds2
     */
    public String getReveiveIds2()
    {
        return reveiveIds2;
    }

    /**
     * @param reveiveIds2
     *            the reveiveIds2 to set
     */
    public void setReveiveIds2(String reveiveIds2)
    {
        this.reveiveIds2 = reveiveIds2;
    }

    /**
     * @return the reveiveNames2
     */
    public String getReveiveNames2()
    {
        return reveiveNames2;
    }

    /**
     * @param reveiveNames2
     *            the reveiveNames2 to set
     */
    public void setReveiveNames2(String reveiveNames2)
    {
        this.reveiveNames2 = reveiveNames2;
    }

    /**
     * @return the reveiveIds3
     */
    public String getReveiveIds3()
    {
        return reveiveIds3;
    }

    /**
     * @param reveiveIds3
     *            the reveiveIds3 to set
     */
    public void setReveiveIds3(String reveiveIds3)
    {
        this.reveiveIds3 = reveiveIds3;
    }

    /**
     * @return the reveiveNames3
     */
    public String getReveiveNames3()
    {
        return reveiveNames3;
    }

    /**
     * @param reveiveNames3
     *            the reveiveNames3 to set
     */
    public void setReveiveNames3(String reveiveNames3)
    {
        this.reveiveNames3 = reveiveNames3;
    }

    /**
     * @return the href
     */
    public String getHref()
    {
        return href;
    }

    /**
     * @param href
     *            the href to set
     */
    public void setHref(String href)
    {
        this.href = href;
    }

    /**
     * @return the status
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status)
    {
        this.status = status;
    }

    /**
     * @return the feeback
     */
    public int getFeeback()
    {
        return feeback;
    }

    /**
     * @param feeback
     *            the feeback to set
     */
    public void setFeeback(int feeback)
    {
        this.feeback = feeback;
    }

    /**
     * @return the attachment
     */
    public int getAttachment()
    {
        return attachment;
    }

    /**
     * @param attachment
     *            the attachment to set
     */
    public void setAttachment(int attachment)
    {
        this.attachment = attachment;
    }

    /**
     * @return the logTime
     */
    public String getLogTime()
    {
        return logTime;
    }

    /**
     * @param logTime
     *            the logTime to set
     */
    public void setLogTime(String logTime)
    {
        this.logTime = logTime;
    }

    /**
     * @return the attachments
     */
    public List<AttachmentBean> getAttachments()
    {
        return attachments;
    }

    /**
     * @param attachments
     *            the attachments to set
     */
    public void setAttachments(List<AttachmentBean> attachments)
    {
        this.attachments = attachments;
    }

    /**
     * @return the refId
     */
    public String getRefId()
    {
        return refId;
    }

    /**
     * @param refId
     *            the refId to set
     */
    public void setRefId(String refId)
    {
        this.refId = refId;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("AbstractMail ( ")
            .append(super.toString())
            .append(TAB)
            .append("title = ")
            .append(this.title)
            .append(TAB)
            .append("content = ")
            .append(this.content)
            .append(TAB)
            .append("senderId = ")
            .append(this.senderId)
            .append(TAB)
            .append("senderName = ")
            .append(this.senderName)
            .append(TAB)
            .append("reveiveId = ")
            .append(this.reveiveId)
            .append(TAB)
            .append("reveiveIds = ")
            .append(this.reveiveIds)
            .append(TAB)
            .append("reveiveNames = ")
            .append(this.reveiveNames)
            .append(TAB)
            .append("reveiveIds2 = ")
            .append(this.reveiveIds2)
            .append(TAB)
            .append("reveiveNames2 = ")
            .append(this.reveiveNames2)
            .append(TAB)
            .append("reveiveIds3 = ")
            .append(this.reveiveIds3)
            .append(TAB)
            .append("reveiveNames3 = ")
            .append(this.reveiveNames3)
            .append(TAB)
            .append("href = ")
            .append(this.href)
            .append(TAB)
            .append("refId = ")
            .append(this.refId)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("feeback = ")
            .append(this.feeback)
            .append(TAB)
            .append("attachment = ")
            .append(this.attachment)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("attachments = ")
            .append(this.attachments)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
