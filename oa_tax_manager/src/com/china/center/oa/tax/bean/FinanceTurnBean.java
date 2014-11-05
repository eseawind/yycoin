/**
 * File Name: FinanceTurnBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * FinanceTurnBean
 * 
 * @author ZHUZHU
 * @version 2011-7-27
 * @see FinanceTurnBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_FINANCETURN")
public class FinanceTurnBean implements Serializable
{
    @Id
    private String id = "";

    private int status = 0;

    private int amount = 0;

    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    private String stafferId = "";

    private String startTime = "";

    private String endTime = "";

    private String monthKey = "";

    private String logTime = "";

    private String description = "";

    /**
     * default constructor
     */
    public FinanceTurnBean()
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
     * @return the amount
     */
    public int getAmount()
    {
        return amount;
    }

    /**
     * @param amount
     *            the amount to set
     */
    public void setAmount(int amount)
    {
        this.amount = amount;
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
     * @return the startTime
     */
    public String getStartTime()
    {
        return startTime;
    }

    /**
     * @param startTime
     *            the startTime to set
     */
    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime()
    {
        return endTime;
    }

    /**
     * @param endTime
     *            the endTime to set
     */
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    /**
     * @return the monthKey
     */
    public String getMonthKey()
    {
        return monthKey;
    }

    /**
     * @param monthKey
     *            the monthKey to set
     */
    public void setMonthKey(String monthKey)
    {
        this.monthKey = monthKey;
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
            .append("FinanceTurnBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("amount = ")
            .append(this.amount)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("startTime = ")
            .append(this.startTime)
            .append(TAB)
            .append("endTime = ")
            .append(this.endTime)
            .append(TAB)
            .append("monthKey = ")
            .append(this.monthKey)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
