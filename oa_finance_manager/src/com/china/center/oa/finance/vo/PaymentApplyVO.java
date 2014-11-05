/**
 * File Name: PayApplyVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.finance.bean.PaymentApplyBean;


/**
 * PayApplyVO
 * 
 * @author ZHUZHU
 * @version 2011-1-8
 * @see PaymentApplyVO
 * @since 3.0
 */
@Entity(inherit = true)
public class PaymentApplyVO extends PaymentApplyBean
{
    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    @Relationship(relationField = "oriStafferId")
    private String oriStafferName = "";
    
    @Relationship(relationField = "customerId")
    private String customerName = "";
    
    @Relationship(relationField = "oriCustomerId")
    private String oriCustomerName = "";
    
    

    /**
     * default constructor
     */
    public PaymentApplyVO()
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

    public String getOriCustomerName()
	{
		return oriCustomerName;
	}

	public void setOriCustomerName(String oriCustomerName)
	{
		this.oriCustomerName = oriCustomerName;
	}

	public String getOriStafferName()
	{
		return oriStafferName;
	}

	public void setOriStafferName(String oriStafferName)
	{
		this.oriStafferName = oriStafferName;
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
            .append("PayApplyVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("customerName = ")
            .append(this.customerName)
            .append(TAB)
            .append("oricustomerName = ")
            .append(this.oriCustomerName)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
