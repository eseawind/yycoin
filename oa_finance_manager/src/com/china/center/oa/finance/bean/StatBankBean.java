/**
 * File Name: StatBankBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-16<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;


/**
 * StatBankBean
 * 
 * @author ZHUZHU
 * @version 2011-1-16
 * @see StatBankBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_STATBANK")
public class StatBankBean implements Serializable
{
    @Id
    private String id = "";

    @FK
    @Unique(dependFields = "timeKey")
    @Join(tagClass = BankBean.class)
    private String bankId = "";

    private String timeKey = "";

    private String logTime = "";

    /**
     * 余额
     */
    private double total = 0.0d;

    private String description = "";

    /**
     * default constructor
     */
    public StatBankBean()
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
     * @return the bankId
     */
    public String getBankId()
    {
        return bankId;
    }

    /**
     * @param bankId
     *            the bankId to set
     */
    public void setBankId(String bankId)
    {
        this.bankId = bankId;
    }

    /**
     * @return the timeKey
     */
    public String getTimeKey()
    {
        return timeKey;
    }

    /**
     * @param timeKey
     *            the timeKey to set
     */
    public void setTimeKey(String timeKey)
    {
        this.timeKey = timeKey;
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
     * @return the total
     */
    public double getTotal()
    {
        return total;
    }

    /**
     * @param total
     *            the total to set
     */
    public void setTotal(double total)
    {
        this.total = total;
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
            .append("StatBankBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("bankId = ")
            .append(this.bankId)
            .append(TAB)
            .append("timeKey = ")
            .append(this.timeKey)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("total = ")
            .append(this.total)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
