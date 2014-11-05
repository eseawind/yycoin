/**
 * File Name: InBillVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.finance.bean.InBillBean;


/**
 * InBillVO
 * 
 * @author ZHUZHU
 * @version 2010-12-26
 * @see InBillVO
 * @since 3.0
 */
@Entity(inherit = true)
public class InBillVO extends InBillBean
{
    @Relationship(relationField = "bankId")
    private String bankName = "";

    @Relationship(relationField = "customerId")
    private String customerName = "";
    
    @Relationship(relationField = "customerId", tagField = "code")
    private String customerCode = "";

    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    @Relationship(relationField = "ownerId")
    private String ownerName = "";

    @Relationship(relationField = "paymentId", tagField = "description")
    private String paymentDes = "";

    @Relationship(relationField = "outId", tagField = "changeTime")
    private String changeTime = "";

    @Relationship(relationField = "outId", tagField = "payTime")
    private String payTime = "";

    @Relationship(relationField = "paymentId", tagField = "money")
    private double payMoney = 0.0d;

    @Relationship(relationField = "paymentId", tagField = "logTime")
    private String payLogTime = "";
    
    @Relationship(relationField = "dutyId")
    private String dutyName = "";
    
    @Relationship(relationField = "paymentId", tagField = "fromerNo")
    private String customerAccount = "";
    
    /**
     * 客户应收
     */
    @Ignore
    private double ownMoney = 0.0d;

    /**
     * default constructor
     */
    public InBillVO()
    {
    }

    /**
     * @return the bankName
     */
    public String getBankName()
    {
        return bankName;
    }

    /**
     * @param bankName
     *            the bankName to set
     */
    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName()
    {
        return customerName;
    }

    /**
     * @param customerName
     *            the customerName to set
     */
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
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
     * @return the ownerName
     */
    public String getOwnerName()
    {
        return ownerName;
    }

    /**
     * @param ownerName
     *            the ownerName to set
     */
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    /**
     * @return the paymentDes
     */
    public String getPaymentDes()
    {
        return paymentDes;
    }

    /**
     * @param paymentDes
     *            the paymentDes to set
     */
    public void setPaymentDes(String paymentDes)
    {
        this.paymentDes = paymentDes;
    }

    /**
	 * @return the dutyName
	 */
	public String getDutyName()
	{
		return dutyName;
	}

	/**
	 * @param dutyName the dutyName to set
	 */
	public void setDutyName(String dutyName)
	{
		this.dutyName = dutyName;
	}

	/**
     * @return the changeTime
     */
    public String getChangeTime()
    {
        return changeTime;
    }

    /**
     * @param changeTime
     *            the changeTime to set
     */
    public void setChangeTime(String changeTime)
    {
        this.changeTime = changeTime;
    }

    /**
     * @return the payMoney
     */
    public double getPayMoney()
    {
        return payMoney;
    }

    /**
     * @param payMoney
     *            the payMoney to set
     */
    public void setPayMoney(double payMoney)
    {
        this.payMoney = payMoney;
    }

    /**
     * @return the payLogTime
     */
    public String getPayLogTime()
    {
        return payLogTime;
    }

    /**
     * @param payLogTime
     *            the payLogTime to set
     */
    public void setPayLogTime(String payLogTime)
    {
        this.payLogTime = payLogTime;
    }

    /**
     * @return the payTime
     */
    public String getPayTime()
    {
        return payTime;
    }

    /**
     * @param payTime
     *            the payTime to set
     */
    public void setPayTime(String payTime)
    {
        this.payTime = payTime;
    }

    /**
	 * @return the customerCode
	 */
	public String getCustomerCode()
	{
		return customerCode;
	}

	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode)
	{
		this.customerCode = customerCode;
	}

	/**
	 * @return the customerAccount
	 */
	public String getCustomerAccount() {
		return customerAccount;
	}

	/**
	 * @param customerAccount the customerAccount to set
	 */
	public void setCustomerAccount(String customerAccount) {
		this.customerAccount = customerAccount;
	}

	/**
	 * @return the ownMoney
	 */
	public double getOwnMoney() {
		return ownMoney;
	}

	/**
	 * @param ownMoney the ownMoney to set
	 */
	public void setOwnMoney(double ownMoney) {
		this.ownMoney = ownMoney;
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
            .append("InBillVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("bankName = ")
            .append(this.bankName)
            .append(TAB)
            .append("customerName = ")
            .append(this.customerName)
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("ownerName = ")
            .append(this.ownerName)
            .append(TAB)
            .append("paymentDes = ")
            .append(this.paymentDes)
            .append(TAB)
            .append("dutyName = ")
            .append(this.dutyName)
            .append(TAB)
            .append("changeTime = ")
            .append(this.changeTime)
            .append(TAB)
            .append("payTime = ")
            .append(this.payTime)
            .append(TAB)
            .append("payMoney = ")
            .append(this.payMoney)
            .append(TAB)
            .append("payLogTime = ")
            .append(this.payLogTime)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
