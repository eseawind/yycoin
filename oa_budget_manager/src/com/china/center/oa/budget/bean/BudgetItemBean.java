/**
 * File Name: BudgetBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-12-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.bean;


import java.io.Serializable;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.budget.constant.BudgetConstant;


/**
 * BudgetBean
 * 
 * @author ZHUZHU
 * @version 2008-12-2
 * @see BudgetItemBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_BUDGETITEM")
public class BudgetItemBean implements Serializable
{
    @Id
    private String id = "";

    @FK(index = AnoConstant.FK_FIRST)
    @Join(tagClass = FeeItemBean.class)
    private String feeItemId = "";

    @FK
    @Join(tagClass = BudgetBean.class)
    private String budgetId = "";

    private String locationId = "";

    private int carryStatus = BudgetConstant.BUDGET_CARRY_INIT;

    /**
     * 预算金额
     */
    private double budget = 0.0d;

    /**
     * 实际使用金额(预算结束的时候产生)
     */
    private double realMonery = 0.0d;

    /**
     * 当前使用金额<br>
     * define the use money.befor the BudgetItem is end,the useMonery equals budget<br>
     * but after the BudgetItem ending useMonery will equals realMonery
     */
    private double useMonery = 0.0d;

    private String description = "";

    public BudgetItemBean()
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
     * @return the feeItemId
     */
    public String getFeeItemId()
    {
        return feeItemId;
    }

    /**
     * @param feeItemId
     *            the feeItemId to set
     */
    public void setFeeItemId(String feeItemId)
    {
        this.feeItemId = feeItemId;
    }

    /**
     * @return the budgetId
     */
    public String getBudgetId()
    {
        return budgetId;
    }

    /**
     * @param budgetId
     *            the budgetId to set
     */
    public void setBudgetId(String budgetId)
    {
        this.budgetId = budgetId;
    }

    /**
     * @return the locationId
     */
    public String getLocationId()
    {
        return locationId;
    }

    /**
     * @param locationId
     *            the locationId to set
     */
    public void setLocationId(String locationId)
    {
        this.locationId = locationId;
    }

    /**
     * @return the carryStatus
     */
    public int getCarryStatus()
    {
        return carryStatus;
    }

    /**
     * @param carryStatus
     *            the carryStatus to set
     */
    public void setCarryStatus(int carryStatus)
    {
        this.carryStatus = carryStatus;
    }

    /**
     * @return the budget
     */
    public double getBudget()
    {
        return budget;
    }

    /**
     * @param budget
     *            the budget to set
     */
    public void setBudget(double budget)
    {
        this.budget = budget;
    }

    /**
     * @return the realMonery
     */
    public double getRealMonery()
    {
        return realMonery;
    }

    /**
     * @param realMonery
     *            the realMonery to set
     */
    public void setRealMonery(double realMonery)
    {
        this.realMonery = realMonery;
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

    public double getUseMonery()
    {
        return useMonery;
    }

    public void setUseMonery(double useMonery)
    {
        this.useMonery = useMonery;
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
            .append("BudgetItemBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("feeItemId = ")
            .append(this.feeItemId)
            .append(TAB)
            .append("budgetId = ")
            .append(this.budgetId)
            .append(TAB)
            .append("locationId = ")
            .append(this.locationId)
            .append(TAB)
            .append("carryStatus = ")
            .append(this.carryStatus)
            .append(TAB)
            .append("budget = ")
            .append(this.budget)
            .append(TAB)
            .append("realMonery = ")
            .append(this.realMonery)
            .append(TAB)
            .append("useMonery = ")
            .append(this.useMonery)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
