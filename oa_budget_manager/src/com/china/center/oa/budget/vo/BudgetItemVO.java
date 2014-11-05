/**
 * File Name: BudgetItemVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-12-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.budget.bean.BudgetItemBean;


/**
 * BudgetItemVO
 * 
 * @author ZHUZHU
 * @version 2008-12-7
 * @see BudgetItemVO
 * @since 1.0
 */
@Entity(inherit = true)
public class BudgetItemVO extends BudgetItemBean
{
    @Relationship(relationField = "feeItemId")
    private String feeItemName = "";

    @Relationship(relationField = "budgetId")
    private String budgetName = "";

    /**
     * 
     */
    @Ignore
    private String budgetStr = "";

    /**
     * 未分配
     */
    @Ignore
    private String sbudget = "";

    /**
     * 未分配的
     */
    @Ignore
    private String snoAssignMonery = "";

    @Ignore
    private String srealMonery = "";

    @Ignore
    private String suseMonery = "";

    /**
     * 剩余预算
     */
    @Ignore
    private String sremainMonery = "";

    /**
     * 预占&&实际(正式的哦)
     */
    @Ignore
    private String spreAndUseMonery = "";

    @Ignore
    private String schangeMonery = "";

    @Ignore
    private String subDescription = "";

    public BudgetItemVO()
    {
    }

    /**
     * @return the feeItemName
     */
    public String getFeeItemName()
    {
        return feeItemName;
    }

    /**
     * @param feeItemName
     *            the feeItemName to set
     */
    public void setFeeItemName(String feeItemName)
    {
        this.feeItemName = feeItemName;
    }

    /**
     * @return the sbudget
     */
    public String getSbudget()
    {
        return sbudget;
    }

    /**
     * @param sbudget
     *            the sbudget to set
     */
    public void setSbudget(String sbudget)
    {
        this.sbudget = sbudget;
    }

    /**
     * @return the srealMonery
     */
    public String getSrealMonery()
    {
        return srealMonery;
    }

    /**
     * @param srealMonery
     *            the srealMonery to set
     */
    public void setSrealMonery(String srealMonery)
    {
        this.srealMonery = srealMonery;
    }

    public String getSuseMonery()
    {
        return suseMonery;
    }

    public void setSuseMonery(String suseMonery)
    {
        this.suseMonery = suseMonery;
    }

    /**
     * @return the sremainMonery
     */
    public String getSremainMonery()
    {
        return sremainMonery;
    }

    /**
     * @param sremainMonery
     *            the sremainMonery to set
     */
    public void setSremainMonery(String sremainMonery)
    {
        this.sremainMonery = sremainMonery;
    }

    /**
     * @return the schangeMonery
     */
    public String getSchangeMonery()
    {
        return schangeMonery;
    }

    /**
     * @param schangeMonery
     *            the schangeMonery to set
     */
    public void setSchangeMonery(String schangeMonery)
    {
        this.schangeMonery = schangeMonery;
    }

    /**
     * @return the budgetName
     */
    public String getBudgetName()
    {
        return budgetName;
    }

    /**
     * @param budgetName
     *            the budgetName to set
     */
    public void setBudgetName(String budgetName)
    {
        this.budgetName = budgetName;
    }

    /**
     * @return the budgetStr
     */
    public String getBudgetStr()
    {
        return budgetStr;
    }

    /**
     * @param budgetStr
     *            the budgetStr to set
     */
    public void setBudgetStr(String budgetStr)
    {
        this.budgetStr = budgetStr;
    }

    /**
     * @return the subDescription
     */
    public String getSubDescription()
    {
        return subDescription;
    }

    /**
     * @param subDescription
     *            the subDescription to set
     */
    public void setSubDescription(String subDescription)
    {
        this.subDescription = subDescription;
    }

    /**
     * @return the snoAssignMonery
     */
    public String getSnoAssignMonery()
    {
        return snoAssignMonery;
    }

    /**
     * @param snoAssignMonery
     *            the snoAssignMonery to set
     */
    public void setSnoAssignMonery(String snoAssignMonery)
    {
        this.snoAssignMonery = snoAssignMonery;
    }

    /**
     * @return the spreAndUseMonery
     */
    public String getSpreAndUseMonery()
    {
        return spreAndUseMonery;
    }

    /**
     * @param spreAndUseMonery
     *            the spreAndUseMonery to set
     */
    public void setSpreAndUseMonery(String spreAndUseMonery)
    {
        this.spreAndUseMonery = spreAndUseMonery;
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
            .append("BudgetItemVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("feeItemName = ")
            .append(this.feeItemName)
            .append(TAB)
            .append("budgetName = ")
            .append(this.budgetName)
            .append(TAB)
            .append("budgetStr = ")
            .append(this.budgetStr)
            .append(TAB)
            .append("sbudget = ")
            .append(this.sbudget)
            .append(TAB)
            .append("snoAssignMonery = ")
            .append(this.snoAssignMonery)
            .append(TAB)
            .append("srealMonery = ")
            .append(this.srealMonery)
            .append(TAB)
            .append("suseMonery = ")
            .append(this.suseMonery)
            .append(TAB)
            .append("sremainMonery = ")
            .append(this.sremainMonery)
            .append(TAB)
            .append("spreAndUseMonery = ")
            .append(this.spreAndUseMonery)
            .append(TAB)
            .append("schangeMonery = ")
            .append(this.schangeMonery)
            .append(TAB)
            .append("subDescription = ")
            .append(this.subDescription)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
