/**
 * File Name: BudgetApplyVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-6-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.vo;


import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.budget.bean.BudgetApplyBean;
import com.china.center.oa.budget.constant.BudgetConstant;


/**
 * BudgetApplyVO
 * 
 * @author ZHUZHU
 * @version 2009-6-14
 * @see BudgetApplyVO
 * @since 1.0
 */
@Entity(inherit = true)
public class BudgetApplyVO extends BudgetApplyBean
{
    @Relationship(relationField = "budgetId")
    private String budgetName = "";

    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    /**
     * 预算分类
     */
    @Relationship(relationField = "budgetId", tagField = "type")
    private int budgetType = BudgetConstant.BUDGET_TYPE_COMPANY;

    @Ignore
    private List<BudgetItemVO> itemVOs = null;

    /**
     * default constructor
     */
    public BudgetApplyVO()
    {
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
     * @return the budgetType
     */
    public int getBudgetType()
    {
        return budgetType;
    }

    /**
     * @param budgetType
     *            the budgetType to set
     */
    public void setBudgetType(int budgetType)
    {
        this.budgetType = budgetType;
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

        retValue.append("BudgetApplyVO ( ").append(super.toString()).append(TAB).append(
            "budgetName = ").append(this.budgetName).append(TAB).append("stafferName = ").append(
            this.stafferName).append(TAB).append("budgetType = ").append(this.budgetType).append(
            TAB).append("itemVOs = ").append(this.itemVOs).append(TAB).append(" )");

        return retValue.toString();
    }
}
