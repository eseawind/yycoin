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
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * 月结项
 * 
 * @author ZHUZHU
 * @version 2011-7-27
 * @see FinanceMonthBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_FINANCEMONTH")
public class FinanceMonthBean implements Serializable
{
    @Id
    private String id = "";

    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    private String stafferId = "";

    @FK
    private String monthKey = "";

    /**
     * 当月发生
     */
    private long inmoneyTotal = 0L;

    private long outmoneyTotal = 0L;

    /**
     * 当月累计
     */
    private long lastTotal = 0L;

    /**
     * 历届总和
     */
    private long inmoneyAllTotal = 0L;

    private long outmoneyAllTotal = 0L;

    /**
     * 期末余额
     */
    private long lastAllTotal = 0L;

    /**
     * 结转的金额
     */
    private long monthTurnTotal = 0L;

    @Unique(dependFields = "monthKey")
    @Join(tagClass = TaxBean.class, type = JoinType.LEFT)
    private String taxId = "";

    private String taxId0 = "";

    private String taxId1 = "";

    private String taxId2 = "";

    private String taxId3 = "";

    private String taxId4 = "";

    private String taxId5 = "";

    private String taxId6 = "";

    private String taxId7 = "";

    private String taxId8 = "";

    private String logTime = "";

    private String description = "";

    /**
     * default constructor
     */
    public FinanceMonthBean()
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
     * @return the inmoneyTotal
     */
    public long getInmoneyTotal()
    {
        return inmoneyTotal;
    }

    /**
     * @param inmoneyTotal
     *            the inmoneyTotal to set
     */
    public void setInmoneyTotal(long inmoneyTotal)
    {
        this.inmoneyTotal = inmoneyTotal;
    }

    /**
     * @return the outmoneyTotal
     */
    public long getOutmoneyTotal()
    {
        return outmoneyTotal;
    }

    /**
     * @param outmoneyTotal
     *            the outmoneyTotal to set
     */
    public void setOutmoneyTotal(long outmoneyTotal)
    {
        this.outmoneyTotal = outmoneyTotal;
    }

    /**
     * @return the taxId
     */
    public String getTaxId()
    {
        return taxId;
    }

    /**
     * @param taxId
     *            the taxId to set
     */
    public void setTaxId(String taxId)
    {
        this.taxId = taxId;
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
     * @return the lastTotal
     */
    public long getLastTotal()
    {
        return lastTotal;
    }

    /**
     * @param lastTotal
     *            the lastTotal to set
     */
    public void setLastTotal(long lastTotal)
    {
        this.lastTotal = lastTotal;
    }

    /**
     * @return the taxId0
     */
    public String getTaxId0()
    {
        return taxId0;
    }

    /**
     * @param taxId0
     *            the taxId0 to set
     */
    public void setTaxId0(String taxId0)
    {
        this.taxId0 = taxId0;
    }

    /**
     * @return the taxId1
     */
    public String getTaxId1()
    {
        return taxId1;
    }

    /**
     * @param taxId1
     *            the taxId1 to set
     */
    public void setTaxId1(String taxId1)
    {
        this.taxId1 = taxId1;
    }

    /**
     * @return the taxId2
     */
    public String getTaxId2()
    {
        return taxId2;
    }

    /**
     * @param taxId2
     *            the taxId2 to set
     */
    public void setTaxId2(String taxId2)
    {
        this.taxId2 = taxId2;
    }

    /**
     * @return the taxId3
     */
    public String getTaxId3()
    {
        return taxId3;
    }

    /**
     * @param taxId3
     *            the taxId3 to set
     */
    public void setTaxId3(String taxId3)
    {
        this.taxId3 = taxId3;
    }

    /**
     * @return the taxId4
     */
    public String getTaxId4()
    {
        return taxId4;
    }

    /**
     * @param taxId4
     *            the taxId4 to set
     */
    public void setTaxId4(String taxId4)
    {
        this.taxId4 = taxId4;
    }

    /**
     * @return the taxId5
     */
    public String getTaxId5()
    {
        return taxId5;
    }

    /**
     * @param taxId5
     *            the taxId5 to set
     */
    public void setTaxId5(String taxId5)
    {
        this.taxId5 = taxId5;
    }

    /**
     * @return the taxId6
     */
    public String getTaxId6()
    {
        return taxId6;
    }

    /**
     * @param taxId6
     *            the taxId6 to set
     */
    public void setTaxId6(String taxId6)
    {
        this.taxId6 = taxId6;
    }

    /**
     * @return the taxId7
     */
    public String getTaxId7()
    {
        return taxId7;
    }

    /**
     * @param taxId7
     *            the taxId7 to set
     */
    public void setTaxId7(String taxId7)
    {
        this.taxId7 = taxId7;
    }

    /**
     * @return the taxId8
     */
    public String getTaxId8()
    {
        return taxId8;
    }

    /**
     * @param taxId8
     *            the taxId8 to set
     */
    public void setTaxId8(String taxId8)
    {
        this.taxId8 = taxId8;
    }

    /**
     * @return the inmoneyAllTotal
     */
    public long getInmoneyAllTotal()
    {
        return inmoneyAllTotal;
    }

    /**
     * @param inmoneyAllTotal
     *            the inmoneyAllTotal to set
     */
    public void setInmoneyAllTotal(long inmoneyAllTotal)
    {
        this.inmoneyAllTotal = inmoneyAllTotal;
    }

    /**
     * @return the outmoneyAllTotal
     */
    public long getOutmoneyAllTotal()
    {
        return outmoneyAllTotal;
    }

    /**
     * @param outmoneyAllTotal
     *            the outmoneyAllTotal to set
     */
    public void setOutmoneyAllTotal(long outmoneyAllTotal)
    {
        this.outmoneyAllTotal = outmoneyAllTotal;
    }

    /**
     * @return the lastAllTotal
     */
    public long getLastAllTotal()
    {
        return lastAllTotal;
    }

    /**
     * @param lastAllTotal
     *            the lastAllTotal to set
     */
    public void setLastAllTotal(long lastAllTotal)
    {
        this.lastAllTotal = lastAllTotal;
    }

    /**
     * @return the monthTurnTotal
     */
    public long getMonthTurnTotal()
    {
        return monthTurnTotal;
    }

    /**
     * @param monthTurnTotal
     *            the monthTurnTotal to set
     */
    public void setMonthTurnTotal(long monthTurnTotal)
    {
        this.monthTurnTotal = monthTurnTotal;
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
            .append("FinanceMonthBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("inmoneyTotal = ")
            .append(this.inmoneyTotal)
            .append(TAB)
            .append("outmoneyTotal = ")
            .append(this.outmoneyTotal)
            .append(TAB)
            .append("lastTotal = ")
            .append(this.lastTotal)
            .append(TAB)
            .append("inmoneyAllTotal = ")
            .append(this.inmoneyAllTotal)
            .append(TAB)
            .append("outmoneyAllTotal = ")
            .append(this.outmoneyAllTotal)
            .append(TAB)
            .append("lastAllTotal = ")
            .append(this.lastAllTotal)
            .append(TAB)
            .append("monthTurnTotal = ")
            .append(this.monthTurnTotal)
            .append(TAB)
            .append("taxId = ")
            .append(this.taxId)
            .append(TAB)
            .append("taxId0 = ")
            .append(this.taxId0)
            .append(TAB)
            .append("taxId1 = ")
            .append(this.taxId1)
            .append(TAB)
            .append("taxId2 = ")
            .append(this.taxId2)
            .append(TAB)
            .append("taxId3 = ")
            .append(this.taxId3)
            .append(TAB)
            .append("taxId4 = ")
            .append(this.taxId4)
            .append(TAB)
            .append("taxId5 = ")
            .append(this.taxId5)
            .append(TAB)
            .append("taxId6 = ")
            .append(this.taxId6)
            .append(TAB)
            .append("taxId7 = ")
            .append(this.taxId7)
            .append(TAB)
            .append("taxId8 = ")
            .append(this.taxId8)
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
