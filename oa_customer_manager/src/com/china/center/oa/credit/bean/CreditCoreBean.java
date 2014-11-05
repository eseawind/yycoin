/**
 * File Name: CreditCoreBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2009-12-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;


/**
 * 客户信用参数核心表
 * 
 * @author ZHUZHU
 * @version 2009-12-7
 * @see CreditCoreBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_CREDIT_CURCORE")
public class CreditCoreBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @Unique
    private String cid = "";

    private String logTime = "";

    /**
     * 最大交易额
     */
    private double maxBusiness = 0.0d;

    /**
     * 上一年遗留的
     */
    private double oldMaxBusiness = 0.0d;

    /**
     * 交易额(从3.1到次年的2.28)
     */
    private double sumTotal = 0.0d;

    /**
     * 上一年遗留的
     */
    private double oldSumTotal = 0.0d;

    /**
     * 2009
     */
    private int year = 2009;

    /**
     * old year
     */
    private int oldYear = 2009;

    /**
     * default constructor
     */
    public CreditCoreBean()
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
     * @return the cid
     */
    public String getCid()
    {
        return cid;
    }

    /**
     * @param cid
     *            the cid to set
     */
    public void setCid(String cid)
    {
        this.cid = cid;
    }

    /**
     * @return the sumTotal
     */
    public double getSumTotal()
    {
        return sumTotal;
    }

    /**
     * @param sumTotal
     *            the sumTotal to set
     */
    public void setSumTotal(double sumTotal)
    {
        this.sumTotal = sumTotal;
    }

    /**
     * @return the year
     */
    public int getYear()
    {
        return year;
    }

    /**
     * @param year
     *            the year to set
     */
    public void setYear(int year)
    {
        this.year = year;
    }

    /**
     * @return the maxBusiness
     */
    public double getMaxBusiness()
    {
        return maxBusiness;
    }

    /**
     * @param maxBusiness
     *            the maxBusiness to set
     */
    public void setMaxBusiness(double maxBusiness)
    {
        this.maxBusiness = maxBusiness;
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

        retValue.append("CreditCoreBean ( ").append(super.toString()).append(tab).append("id = ").append(this.id).append(
            tab).append("cid = ").append(this.cid).append(tab).append("maxBusiness = ").append(this.maxBusiness).append(
            tab).append("sumTotal = ").append(this.sumTotal).append(tab).append("year = ").append(this.year).append(tab).append(
            " )");

        return retValue.toString();
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
     * @return the oldMaxBusiness
     */
    public double getOldMaxBusiness()
    {
        return oldMaxBusiness;
    }

    /**
     * @param oldMaxBusiness
     *            the oldMaxBusiness to set
     */
    public void setOldMaxBusiness(double oldMaxBusiness)
    {
        this.oldMaxBusiness = oldMaxBusiness;
    }

    /**
     * @return the oldSumTotal
     */
    public double getOldSumTotal()
    {
        return oldSumTotal;
    }

    /**
     * @param oldSumTotal
     *            the oldSumTotal to set
     */
    public void setOldSumTotal(double oldSumTotal)
    {
        this.oldSumTotal = oldSumTotal;
    }

    /**
     * @return the oldYear
     */
    public int getOldYear()
    {
        return oldYear;
    }

    /**
     * @param oldYear
     *            the oldYear to set
     */
    public void setOldYear(int oldYear)
    {
        this.oldYear = oldYear;
    }

}
