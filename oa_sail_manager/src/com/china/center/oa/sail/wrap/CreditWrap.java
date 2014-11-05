/**
 * File Name: CreditWrap.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-3-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.wrap;


import java.io.Serializable;


/**
 * CreditWrap
 * 
 * @author ZHUZHU
 * @version 2011-3-12
 * @see CreditWrap
 * @since 3.0
 */
public class CreditWrap implements Serializable
{
    private String stafferId = "";

    private String industryId = "";

    private String fullId = "";

    private double total = 0.0d;

    private double hadPay = 0.0d;

    private double credit = 0.0d;

    private String outTime = "";
    
    private String customerName = "";
    
    private double curcredit = 0.0d;
    
    private double staffcredit = 0.0d;
    
    private double managercredit = 0.0d;

    /**
     * default constructor
     */
    public CreditWrap()
    {
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
     * @return the industryId
     */
    public String getIndustryId()
    {
        return industryId;
    }

    /**
     * @param industryId
     *            the industryId to set
     */
    public void setIndustryId(String industryId)
    {
        this.industryId = industryId;
    }

    /**
     * @return the fullId
     */
    public String getFullId()
    {
        return fullId;
    }

    /**
     * @param fullId
     *            the fullId to set
     */
    public void setFullId(String fullId)
    {
        this.fullId = fullId;
    }

    /**
     * @return the total
     */
    public double getTotal()
    {
        return total;
    }

    /**
     * @param total
     *            the total to set
     */
    public void setTotal(double total)
    {
        this.total = total;
    }

    /**
     * @return the outTime
     */
    public String getOutTime()
    {
        return outTime;
    }

    /**
     * @param outTime
     *            the outTime to set
     */
    public void setOutTime(String outTime)
    {
        this.outTime = outTime;
    }

    /**
     * @return the hadPay
     */
    public double getHadPay()
    {
        return hadPay;
    }

    /**
     * @param hadPay
     *            the hadPay to set
     */
    public void setHadPay(double hadPay)
    {
        this.hadPay = hadPay;
    }

    /**
     * @return the credit
     */
    public double getCredit()
    {
        return credit;
    }

    /**
     * @param credit
     *            the credit to set
     */
    public void setCredit(double credit)
    {
        this.credit = credit;
    }

	public String getCustomerName()
	{
		return customerName;
	}

	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}
	
	

	public double getCurcredit() {
		return curcredit;
	}

	public void setCurcredit(double curcredit) {
		this.curcredit = curcredit;
	}

	public double getStaffcredit() {
		return staffcredit;
	}

	public void setStaffcredit(double staffcredit) {
		this.staffcredit = staffcredit;
	}

	public double getManagercredit() {
		return managercredit;
	}

	public void setManagercredit(double managercredit) {
		this.managercredit = managercredit;
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
            .append("CreditWrap ( ")
            .append(super.toString())
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("industryId = ")
            .append(this.industryId)
            .append(TAB)
            .append("fullId = ")
            .append(this.fullId)
            .append(TAB)
            .append("total = ")
            .append(this.total)
            .append(TAB)
            .append("hadPay = ")
            .append(this.hadPay)
            .append(TAB)
            .append("credit = ")
            .append(this.credit)
            .append(TAB)
            .append("outTime = ")
            .append(this.outTime)
            .append(TAB)
            .append("curcredit = ")
            .append(this.curcredit)
            .append(TAB)
            .append("staffcredit = ")
            .append(this.staffcredit)
            .append(TAB)
            .append("managercredit = ")
            .append(this.managercredit)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
