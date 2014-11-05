/**
 * File Name: MailBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-4-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.mail.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;


/**
 * MailBean
 * 
 * @author ZHUZHU
 * @version 2009-4-12
 * @see AttachmentBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_ATTACHMENT")
public class AttachmentBean implements Serializable
{
    @Id
    private String id = "";

    @FK
    private String mailId = "";

    private String name = "";

    private String stafferId = "";

    private String path = "";

    private String logTime = "";

    /**
     * default constructor
     */
    public AttachmentBean()
    {
    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the mailId
     */
    public String getMailId()
    {
        return mailId;
    }

    /**
     * @param mailId
     *            the mailId to set
     */
    public void setMailId(String mailId)
    {
        this.mailId = mailId;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the path
     */
    public String getPath()
    {
        return path;
    }

    /**
     * @param path
     *            the path to set
     */
    public void setPath(String path)
    {
        this.path = path;
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
     * @return the stafferId
     */
    public String getStafferId()
    {
        return stafferId;
    }

    /**
     * @param stafferId
     *            the stafferId to set
     */
    public void setStafferId(String stafferId)
    {
        this.stafferId = stafferId;
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
            .append("AttachmentBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("mailId = ")
            .append(this.mailId)
            .append(TAB)
            .append("name = ")
            .append(this.name)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("path = ")
            .append(this.path)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
