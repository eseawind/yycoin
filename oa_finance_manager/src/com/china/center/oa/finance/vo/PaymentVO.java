/**
 * File Name: PaymentVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-22<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.finance.bean.PaymentBean;


/**
 * PaymentVO
 * 
 * @author ZHUZHU
 * @version 2010-12-22
 * @see PaymentVO
 * @since 3.0
 */
@Entity(inherit = true)
public class PaymentVO extends PaymentBean
{
    @Relationship(relationField = "bankId")
    private String bankName = "";

    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    @Relationship(relationField = "customerId")
    private String customerName = "";
    
    @Relationship(relationField = "customerId", tagField = "code")
    private String custoemrCode = "";

    @Relationship(relationField = "bankId", tagField = "mtype")
    private int mtype = 0;

    @Relationship(relationField = "bankId", tagField = "dutyId")
    private String dutyId = "";

    /**
     * default constructor
     */
    public PaymentVO()
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
     * @return the mtype
     */
    public int getMtype()
    {
        return mtype;
    }

    /**
     * @param mtype
     *            the mtype to set
     */
    public void setMtype(int mtype)
    {
        this.mtype = mtype;
    }

    /**
     * @return the dutyId
     */
    public String getDutyId()
    {
        return dutyId;
    }

    /**
     * @param dutyId
     *            the dutyId to set
     */
    public void setDutyId(String dutyId)
    {
        this.dutyId = dutyId;
    }

    /**
	 * @return the custoemrCode
	 */
	public String getCustoemrCode()
	{
		return custoemrCode;
	}

	/**
	 * @param custoemrCode the custoemrCode to set
	 */
	public void setCustoemrCode(String custoemrCode)
	{
		this.custoemrCode = custoemrCode;
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
            .append("PaymentVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("bankName = ")
            .append(this.bankName)
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("customerName = ")
            .append(this.customerName)
            .append(TAB)
            .append("mtype = ")
            .append(this.mtype)
            .append(TAB)
            .append("dutyId = ")
            .append(this.dutyId)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
