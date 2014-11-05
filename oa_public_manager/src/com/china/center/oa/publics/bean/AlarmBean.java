/**
 * File Name: AlarmBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2012-5-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.oa.publics.constant.AlarmConstant;


/**
 * AlarmBean
 * 
 * @author ZHUZHU
 * @version 2012-5-8
 * @see AlarmBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_ALARM")
public class AlarmBean implements Serializable
{
    @Id
    private String id = "";

    @Unique
    private String refId = "";

    private int refType = 0;

    private int status = AlarmConstant.ALARMBEAN_STATUS;

    private String logTime = "";

    private String alarmContent = "";

    private String description = "";

    /**
     * default constructor
     */
    public AlarmBean()
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
     * @return the refType
     */
    public int getRefType()
    {
        return refType;
    }

    /**
     * @param refType
     *            the refType to set
     */
    public void setRefType(int refType)
    {
        this.refType = refType;
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
     * @return the alarmContent
     */
    public String getAlarmContent()
    {
        return alarmContent;
    }

    /**
     * @param alarmContent
     *            the alarmContent to set
     */
    public void setAlarmContent(String alarmContent)
    {
        this.alarmContent = alarmContent;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
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
            .append("AlarmBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("refId = ")
            .append(this.refId)
            .append(TAB)
            .append("refType = ")
            .append(this.refType)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("alarmContent = ")
            .append(this.alarmContent)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
