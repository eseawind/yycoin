/**
 * File Name: BackPayApplyBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-3-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.finance.constant.BackPayApplyConstant;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.sail.bean.UnitViewBean;


/**
 * BackPayApplyBean(包含销售退款和预收退款)
 * 
 * @author ZHUZHU
 * @version 2011-3-3
 * @see BackPayApplyBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_BACKPAYAPPLY")
public class BackPayApplyBean implements Serializable
{
    @Id
    private String id = "";

    private String outId = "";

    /**
     * 收款单
     */
    private String billId = "";

    private String locationId = "";

    private int status = BackPayApplyConstant.STATUS_INIT;

    /**
     * 销售退款和预收退款
     */
    private int type = BackPayApplyConstant.TYPE_OUT;

    /**
     * 退款
     */
    private double backPay = 0.0d;

    /**
     * 转预收
     */
    private double changePayment = 0.0d;

    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    private String stafferId = "";

    @Join(tagClass = UnitViewBean.class, type = JoinType.LEFT)
    private String customerId = "";

    private String logTime = "";

    /**
     * 处理关联的收付款单,逗号分隔
     */
    private String refIds = "";

    private String description = "";

    private String operator = "";
    
    private String operatorName = "";
    
    @Ignore
    private String outBalanceId = "";
    
    /**
     * default constructor
     */
    public BackPayApplyBean()
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
     * @return the outId
     */
    public String getOutId()
    {
        return outId;
    }

    /**
     * @param outId
     *            the outId to set
     */
    public void setOutId(String outId)
    {
        this.outId = outId;
    }

    /**
     * @return the status
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status)
    {
        this.status = status;
    }

    /**
     * @return the backPay
     */
    public double getBackPay()
    {
        return backPay;
    }

    /**
     * @param backPay
     *            the backPay to set
     */
    public void setBackPay(double backPay)
    {
        this.backPay = backPay;
    }

    /**
     * @return the changePayment
     */
    public double getChangePayment()
    {
        return changePayment;
    }

    /**
     * @param changePayment
     *            the changePayment to set
     */
    public void setChangePayment(double changePayment)
    {
        this.changePayment = changePayment;
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
     * @return the customerId
     */
    public String getCustomerId()
    {
        return customerId;
    }

    /**
     * @param customerId
     *            the customerId to set
     */
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return the billId
     */
    public String getBillId()
    {
        return billId;
    }

    /**
     * @param billId
     *            the billId to set
     */
    public void setBillId(String billId)
    {
        this.billId = billId;
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
     * @return the refIds
     */
    public String getRefIds()
    {
        return refIds;
    }

    /**
     * @param refIds
     *            the refIds to set
     */
    public void setRefIds(String refIds)
    {
        this.refIds = refIds;
    }

    public String getOperator()
	{
		return operator;
	}

	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	public String getOperatorName()
	{
		return operatorName;
	}

	public void setOperatorName(String operatorName)
	{
		this.operatorName = operatorName;
	}

	public String getOutBalanceId()
	{
		return outBalanceId;
	}

	public void setOutBalanceId(String outBalanceId)
	{
		this.outBalanceId = outBalanceId;
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
            .append("BackPayApplyBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("outId = ")
            .append(this.outId)
            .append(TAB)
            .append("billId = ")
            .append(this.billId)
            .append(TAB)
            .append("locationId = ")
            .append(this.locationId)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("backPay = ")
            .append(this.backPay)
            .append(TAB)
            .append("changePayment = ")
            .append(this.changePayment)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("customerId = ")
            .append(this.customerId)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("refIds = ")
            .append(this.refIds)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("operator = ")
            .append(this.operator)
            .append(TAB)
            .append("operatorName = ")
            .append(this.operatorName)
            .append(TAB)
            .append("outBalanceId = ")
            .append(this.outBalanceId)            
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
