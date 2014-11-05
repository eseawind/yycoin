/**
 * File Name: FeeItemBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-12-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.bean;


import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.budget.constant.BudgetConstant;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * FeeItemBean
 * 
 * @author ZHUZHU
 * @version 2008-12-7
 * @see BudgetApplyBean
 * @since 1.0
 */
@Entity(name = "预算申请")
@Table(name = "T_CENTER_BUDGET_APPLY")
public class BudgetApplyBean implements Serializable
{
    @Id
    private String id = "";

    @Unique
    @Join(tagClass = BudgetBean.class)
    private String budgetId = "";

    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    private String stafferId = "";

    private String logTime = "";

    private int type = BudgetConstant.BUDGET_APPLY_TYPE_MODIFY;

    private int status = BudgetConstant.BUDGET_APPLY_STATUS_INIT;

    private String description = "";

    @Ignore
    private List<BudgetItemBean> items = null;

    public BudgetApplyBean()
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

    public String getBudgetId()
    {
        return budgetId;
    }

    public void setBudgetId(String budgetId)
    {
        this.budgetId = budgetId;
    }

    public String getStafferId()
    {
        return stafferId;
    }

    public void setStafferId(String stafferId)
    {
        this.stafferId = stafferId;
    }

    public String getLogTime()
    {
        return logTime;
    }

    public void setLogTime(String logTime)
    {
        this.logTime = logTime;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the items
     */
    public List<BudgetItemBean> getItems()
    {
        return items;
    }

    /**
     * @param items
     *            the items to set
     */
    public void setItems(List<BudgetItemBean> items)
    {
        this.items = items;
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
            .append("BudgetApplyBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("budgetId = ")
            .append(this.budgetId)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("items = ")
            .append(this.items)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
