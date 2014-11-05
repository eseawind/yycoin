/**
 * File Name: BudgetLogVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-6-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.budget.bean.BudgetLogBean;


/**
 * BudgetLogVO
 * 
 * @author ZHUZHU
 * @version 2009-6-26
 * @see BudgetLogVO
 * @since 1.0
 */
@Entity(inherit = true)
public class BudgetLogVO extends BudgetLogBean
{
    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    @Relationship(relationField = "feeItemId")
    private String feeItemName = "";

    @Relationship(relationField = "budgetId")
    private String budgetName = "";

    @Relationship(relationField = "departmentId")
    private String departmentName = "";

    @Relationship(relationField = "budgetId0")
    private String budgetName0 = "";

    @Relationship(relationField = "budgetId1")
    private String budgetName1 = "";

    @Relationship(relationField = "budgetId2")
    private String budgetName2 = "";

    @Ignore
    private String smonery = "";

    /**
     * default constructor
     */
    public BudgetLogVO()
    {
    }

    /**
     * @return the stafferName
     */
    public String getStafferName()
    {
        return stafferName;
    }

    /**
     * @param stafferName
     *            the stafferName to set
     */
    public void setStafferName(String stafferName)
    {
        this.stafferName = stafferName;
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
     * @return the smonery
     */
    public String getSmonery()
    {
        return smonery;
    }

    /**
     * @param smonery
     *            the smonery to set
     */
    public void setSmonery(String smonery)
    {
        this.smonery = smonery;
    }

    /**
     * @return the departmentName
     */
    public String getDepartmentName()
    {
        return departmentName;
    }

    /**
     * @param departmentName
     *            the departmentName to set
     */
    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

    /**
     * @return the budgetName0
     */
    public String getBudgetName0()
    {
        return budgetName0;
    }

    /**
     * @param budgetName0
     *            the budgetName0 to set
     */
    public void setBudgetName0(String budgetName0)
    {
        this.budgetName0 = budgetName0;
    }

    /**
     * @return the budgetName1
     */
    public String getBudgetName1()
    {
        return budgetName1;
    }

    /**
     * @param budgetName1
     *            the budgetName1 to set
     */
    public void setBudgetName1(String budgetName1)
    {
        this.budgetName1 = budgetName1;
    }

    /**
     * @return the budgetName2
     */
    public String getBudgetName2()
    {
        return budgetName2;
    }

    /**
     * @param budgetName2
     *            the budgetName2 to set
     */
    public void setBudgetName2(String budgetName2)
    {
        this.budgetName2 = budgetName2;
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

        retValue.append("BudgetLogVO ( ").append(super.toString()).append(TAB).append(
            "stafferName = ").append(this.stafferName).append(TAB).append("feeItemName = ").append(
            this.feeItemName).append(TAB).append("budgetName = ").append(this.budgetName).append(
            TAB).append("departmentName = ").append(this.departmentName).append(TAB).append(
            "budgetName0 = ").append(this.budgetName0).append(TAB).append("budgetName1 = ").append(
            this.budgetName1).append(TAB).append("budgetName2 = ").append(this.budgetName2).append(
            TAB).append("smonery = ").append(this.smonery).append(TAB).append(" )");

        return retValue.toString();
    }

}
