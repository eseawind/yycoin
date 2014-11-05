/**
 * File Name: TcpPrepaymentBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.budget.bean.FeeItemBean;


/**
 * TcpPrepaymentBean(no used)
 * 
 * @author ZHUZHU
 * @version 2011-7-10
 * @see TcpPrepaymentBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_TCPPREPAYMENT")
public class TcpPrepaymentBean implements Serializable
{
    @Id
    private String id = "";

    @FK
    private String refId = "";

    private String refItemId = "";

    private String budgetId = "";

    private String budgetItemId = "";

    @Join(tagClass = FeeItemBean.class)
    private String feeItem = "";

    private String departmentId = "";

    private long moneys = 0L;

    /**
     * default constructor
     */
    public TcpPrepaymentBean()
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
     * @return the refId
     */
    public String getRefId()
    {
        return refId;
    }

    /**
     * @param refId
     *            the refId to set
     */
    public void setRefId(String refId)
    {
        this.refId = refId;
    }

    /**
     * @return the refItemId
     */
    public String getRefItemId()
    {
        return refItemId;
    }

    /**
     * @param refItemId
     *            the refItemId to set
     */
    public void setRefItemId(String refItemId)
    {
        this.refItemId = refItemId;
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
     * @return the budgetItemId
     */
    public String getBudgetItemId()
    {
        return budgetItemId;
    }

    /**
     * @param budgetItemId
     *            the budgetItemId to set
     */
    public void setBudgetItemId(String budgetItemId)
    {
        this.budgetItemId = budgetItemId;
    }

    /**
     * @return the feeItem
     */
    public String getFeeItem()
    {
        return feeItem;
    }

    /**
     * @param feeItem
     *            the feeItem to set
     */
    public void setFeeItem(String feeItem)
    {
        this.feeItem = feeItem;
    }

    /**
     * @return the departmentId
     */
    public String getDepartmentId()
    {
        return departmentId;
    }

    /**
     * @param departmentId
     *            the departmentId to set
     */
    public void setDepartmentId(String departmentId)
    {
        this.departmentId = departmentId;
    }

    /**
     * @return the moneys
     */
    public long getMoneys()
    {
        return moneys;
    }

    /**
     * @param moneys
     *            the moneys to set
     */
    public void setMoneys(long moneys)
    {
        this.moneys = moneys;
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

        retValue.append("TcpPrepaymentBean ( ").append(super.toString()).append(TAB).append("id = ").append(this.id).append(
            TAB).append("refId = ").append(this.refId).append(TAB).append("refItemId = ").append(this.refItemId).append(
            TAB).append("budgetId = ").append(this.budgetId).append(TAB).append("budgetItemId = ").append(
            this.budgetItemId).append(TAB).append("feeItem = ").append(this.feeItem).append(TAB).append(
            "departmentId = ").append(this.departmentId).append(TAB).append("moneys = ").append(this.moneys).append(TAB).append(
            " )");

        return retValue.toString();
    }

}
