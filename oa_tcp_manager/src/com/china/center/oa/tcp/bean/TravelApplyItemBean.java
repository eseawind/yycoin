/**
 * File Name: TravelApplyBean.java<br>
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
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.budget.bean.BudgetBean;
import com.china.center.oa.budget.bean.FeeItemBean;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * 费用申请项
 * 
 * @author ZHUZHU
 * @version 2011-7-10
 * @see TravelApplyItemBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_TRAVELAPPLYITEM")
public class TravelApplyItemBean implements Serializable
{
    @Id
    private String id = "";

    @FK
    private String parentId = "";

    private String beginDate = "";

    private String endDate = "";

    @Join(tagClass = FeeItemBean.class)
    private String feeItemId = "";

    /**
     * 如果没有使用share的均摊,有使用这个的比例
     */
    @Join(tagClass = BudgetBean.class, type = JoinType.LEFT)
    private String budgetId = "";

    /**
     * 金额
     */
    private long moneys = 0L;

    /**
     * 核对说明
     */
    private String purpose = "";

    /**
     * 采购的产品名称
     */
    private String productName = "";

    /**
     * 费用承担人
     */
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    private String feeStafferId = "";

    /**
     * 采购产品数量
     */
    private int amount = 0;

    /**
     * 采购产品金额
     */
    private long prices = 0L;

    private long checkPrices = 0L;

    private String description = "";

    /**
     * default constructor
     */
    public TravelApplyItemBean()
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
     * @return the parentId
     */
    public String getParentId()
    {
        return parentId;
    }

    /**
     * @param parentId
     *            the parentId to set
     */
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    /**
     * @return the beginDate
     */
    public String getBeginDate()
    {
        return beginDate;
    }

    /**
     * @param beginDate
     *            the beginDate to set
     */
    public void setBeginDate(String beginDate)
    {
        this.beginDate = beginDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate()
    {
        return endDate;
    }

    /**
     * @param endDate
     *            the endDate to set
     */
    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
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
     * @return the productName
     */
    public String getProductName()
    {
        return productName;
    }

    /**
     * @param productName
     *            the productName to set
     */
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    /**
     * @return the purpose
     */
    public String getPurpose()
    {
        return purpose;
    }

    /**
     * @param purpose
     *            the purpose to set
     */
    public void setPurpose(String purpose)
    {
        this.purpose = purpose;
    }

    /**
     * @return the amount
     */
    public int getAmount()
    {
        return amount;
    }

    /**
     * @param amount
     *            the amount to set
     */
    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    /**
     * @return the prices
     */
    public long getPrices()
    {
        return prices;
    }

    /**
     * @param prices
     *            the prices to set
     */
    public void setPrices(long prices)
    {
        this.prices = prices;
    }

    /**
     * @return the checkPrices
     */
    public long getCheckPrices()
    {
        return checkPrices;
    }

    /**
     * @param checkPrices
     *            the checkPrices to set
     */
    public void setCheckPrices(long checkPrices)
    {
        this.checkPrices = checkPrices;
    }

    /**
     * @return the feeStafferId
     */
    public String getFeeStafferId()
    {
        return feeStafferId;
    }

    /**
     * @param feeStafferId
     *            the feeStafferId to set
     */
    public void setFeeStafferId(String feeStafferId)
    {
        this.feeStafferId = feeStafferId;
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
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("TravelApplyItemBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("parentId = ")
            .append(this.parentId)
            .append(TAB)
            .append("beginDate = ")
            .append(this.beginDate)
            .append(TAB)
            .append("endDate = ")
            .append(this.endDate)
            .append(TAB)
            .append("feeItemId = ")
            .append(this.feeItemId)
            .append(TAB)
            .append("budgetId = ")
            .append(this.budgetId)
            .append(TAB)
            .append("moneys = ")
            .append(this.moneys)
            .append(TAB)
            .append("purpose = ")
            .append(this.purpose)
            .append(TAB)
            .append("productName = ")
            .append(this.productName)
            .append(TAB)
            .append("feeStafferId = ")
            .append(this.feeStafferId)
            .append(TAB)
            .append("amount = ")
            .append(this.amount)
            .append(TAB)
            .append("prices = ")
            .append(this.prices)
            .append(TAB)
            .append("checkPrices = ")
            .append(this.checkPrices)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
