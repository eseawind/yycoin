/**
 * File Name: PaymentVSOutBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.vs;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * 销售单和回款的关系
 * 
 * @author ZHUZHU
 * @version 2010-12-26
 * @see PaymentVSOutBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_VS_OUTPAY")
public class PaymentVSOutBean implements Serializable
{
    @Id
    private String id = "";

    @FK
    private String parentId = "";

    /**
     * 销售单
     */
    private String outId = "";

    /**
     * 结算单
     */
    private String outBalanceId = "";

    private String paymentId = "";

    /**
     * 收款单
     */
    private String billId = "";

    /**
     * 所使用金额
     */
    private double moneys = 0.0d;

    @Join(tagClass = StafferBean.class)
    private String stafferId = "";

    private String locationId = "";

    private String logTime = "";

    /**
     * default constructor
     */
    public PaymentVSOutBean()
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
     * @return the paymentId
     */
    public String getPaymentId()
    {
        return paymentId;
    }

    /**
     * @param paymentId
     *            the paymentId to set
     */
    public void setPaymentId(String paymentId)
    {
        this.paymentId = paymentId;
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
     * @return the moneys
     */
    public double getMoneys()
    {
        return moneys;
    }

    /**
     * @param moneys
     *            the moneys to set
     */
    public void setMoneys(double moneys)
    {
        this.moneys = moneys;
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
     * @return the outBalanceId
     */
    public String getOutBalanceId()
    {
        return outBalanceId;
    }

    /**
     * @param outBalanceId
     *            the outBalanceId to set
     */
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
            .append("PaymentVSOutBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("parentId = ")
            .append(this.parentId)
            .append(TAB)
            .append("outId = ")
            .append(this.outId)
            .append(TAB)
            .append("outBalanceId = ")
            .append(this.outBalanceId)
            .append(TAB)
            .append("paymentId = ")
            .append(this.paymentId)
            .append(TAB)
            .append("billId = ")
            .append(this.billId)
            .append(TAB)
            .append("moneys = ")
            .append(this.moneys)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("locationId = ")
            .append(this.locationId)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
