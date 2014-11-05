/**
 * File Name: BudgetVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-12-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.vo;


import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.budget.bean.BudgetBean;


/**
 * BudgetVO
 * 
 * @author ZHUZHU
 * @version 2008-12-2
 * @see BudgetVO
 * @since 1.0
 */
@Entity(inherit = true)
public class BudgetVO extends BudgetBean
{
    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    @Relationship(relationField = "signer")
    private String signerName = "";

    @Relationship(relationField = "locationId")
    private String locationName = "";

    @Relationship(relationField = "parentId")
    private String parentName = "";

    @Relationship(relationField = "budgetDepartment")
    private String budgetDepartmentName = "";

    @Ignore
    private String budgetFullDepartmentName = "";

    @Ignore
    private String stotal = "";

    @Ignore
    private String srealMonery = "";

    @Ignore
    private List<BudgetItemVO> itemVOs = null;

    public BudgetVO()
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
     * @return the locationName
     */
    public String getLocationName()
    {
        return locationName;
    }

    /**
     * @param locationName
     *            the locationName to set
     */
    public void setLocationName(String locationName)
    {
        this.locationName = locationName;
    }

    /**
     * @return the itemVOs
     */
    public List<BudgetItemVO> getItemVOs()
    {
        return itemVOs;
    }

    /**
     * @param itemVOs
     *            the itemVOs to set
     */
    public void setItemVOs(List<BudgetItemVO> itemVOs)
    {
        this.itemVOs = itemVOs;
    }

    /**
     * @return the parentName
     */
    public String getParentName()
    {
        return parentName;
    }

    /**
     * @param parentName
     *            the parentName to set
     */
    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    /**
     * @return the stotal
     */
    public String getStotal()
    {
        return stotal;
    }

    /**
     * @param stotal
     *            the stotal to set
     */
    public void setStotal(String stotal)
    {
        this.stotal = stotal;
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

    /**
     * @return the budgetDepartmentName
     */
    public String getBudgetDepartmentName()
    {
        return budgetDepartmentName;
    }

    /**
     * @param budgetDepartmentName
     *            the budgetDepartmentName to set
     */
    public void setBudgetDepartmentName(String budgetDepartmentName)
    {
        this.budgetDepartmentName = budgetDepartmentName;
    }

    /**
     * @return the signerName
     */
    public String getSignerName()
    {
        return signerName;
    }

    /**
     * @param signerName
     *            the signerName to set
     */
    public void setSignerName(String signerName)
    {
        this.signerName = signerName;
    }

    /**
     * @return the budgetFullDepartmentName
     */
    public String getBudgetFullDepartmentName()
    {
        return budgetFullDepartmentName;
    }

    /**
     * @param budgetFullDepartmentName
     *            the budgetFullDepartmentName to set
     */
    public void setBudgetFullDepartmentName(String budgetFullDepartmentName)
    {
        this.budgetFullDepartmentName = budgetFullDepartmentName;
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
            .append("BudgetVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("signerName = ")
            .append(this.signerName)
            .append(TAB)
            .append("locationName = ")
            .append(this.locationName)
            .append(TAB)
            .append("parentName = ")
            .append(this.parentName)
            .append(TAB)
            .append("budgetDepartmentName = ")
            .append(this.budgetDepartmentName)
            .append(TAB)
            .append("budgetFullDepartmentName = ")
            .append(this.budgetFullDepartmentName)
            .append(TAB)
            .append("stotal = ")
            .append(this.stotal)
            .append(TAB)
            .append("srealMonery = ")
            .append(this.srealMonery)
            .append(TAB)
            .append("itemVOs = ")
            .append(this.itemVOs)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
