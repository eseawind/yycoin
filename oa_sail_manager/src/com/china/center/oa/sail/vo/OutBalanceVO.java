/**
 * File Name: OutBalanceVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.sail.bean.OutBalanceBean;


/**
 * OutBalanceVO
 * 
 * @author ZHUZHU
 * @version 2010-12-4
 * @see OutBalanceVO
 * @since 3.0
 */
@Entity(inherit = true)
public class OutBalanceVO extends OutBalanceBean
{
    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    @Relationship(relationField = "customerId")
    private String customerName = "";

    @Relationship(relationField = "dirDepot")
    private String dirDepotName = "";

    @Relationship(relationField = "outId", tagField = "outTime")
    private String outTime = "";

    @Relationship(relationField = "outId", tagField = "changeTime")
    private String changeTime = "";

    /**
     * 可开票金额
     */
    @Ignore
    private double mayInvoiceMoneys = 0.0d;
    
    /**
     * 结算单退货金额
     */
    @Ignore
    private double refMoneys = 0.0d;
    
    /**
     * default constructor
     */
    public OutBalanceVO()
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
     * @return the dirDepotName
     */
    public String getDirDepotName()
    {
        return dirDepotName;
    }

    /**
     * @param dirDepotName
     *            the dirDepotName to set
     */
    public void setDirDepotName(String dirDepotName)
    {
        this.dirDepotName = dirDepotName;
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

    public double getMayInvoiceMoneys()
	{
		return mayInvoiceMoneys;
	}

	public void setMayInvoiceMoneys(double mayInvoiceMoneys)
	{
		this.mayInvoiceMoneys = mayInvoiceMoneys;
	}

	public double getRefMoneys()
	{
		return refMoneys;
	}

	public void setRefMoneys(double refMoneys)
	{
		this.refMoneys = refMoneys;
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
            .append("OutBalanceVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("customerName = ")
            .append(this.customerName)
            .append(TAB)
            .append("dirDepotName = ")
            .append(this.dirDepotName)
            .append(TAB)
            .append("outTime = ")
            .append(this.outTime)
            .append(TAB)
            .append("changeTime = ")
            .append(this.changeTime)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
