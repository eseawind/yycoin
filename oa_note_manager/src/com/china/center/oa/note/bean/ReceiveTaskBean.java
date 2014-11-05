/**
 * File Name: ReceiveTaskBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-7-28<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.note.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;


/**
 * ReceiveTaskBean
 * 
 * @author zhuzhu
 * @version 2009-7-28
 * @see ReceiveTaskBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_RECEIVETASK")
public class ReceiveTaskBean implements Serializable
{
    @Id
    private String id = "";

    private String message = "";

    private String sender = "";

    private String exNumber = "";

    private String logTime = "";

    /**
     * default constructor
     */
    public ReceiveTaskBean()
    {}

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
     * @return the message
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * @return the sender
     */
    public String getSender()
    {
        return sender;
    }

    /**
     * @param sender
     *            the sender to set
     */
    public void setSender(String sender)
    {
        this.sender = sender;
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
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String tab = ",";

        StringBuilder retValue = new StringBuilder();

        retValue.append("ReceiveTaskBean ( ").append(super.toString()).append(tab).append("id = ").append(
            this.id).append(tab).append("message = ").append(this.message).append(tab).append(
            "sender = ").append(this.sender).append(tab).append("logTime = ").append(this.logTime).append(
            tab).append(" )");

        return retValue.toString();
    }

    /**
     * @return the exNumber
     */
    public String getExNumber()
    {
        return exNumber;
    }

    /**
     * @param exNumber
     *            the exNumber to set
     */
    public void setExNumber(String exNumber)
    {
        this.exNumber = exNumber;
    }

}
