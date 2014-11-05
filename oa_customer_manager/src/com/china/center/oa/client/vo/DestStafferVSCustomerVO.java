package com.china.center.oa.client.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.client.vs.DestStafferVSCustomerBean;

@Entity(inherit = true)
public class DestStafferVSCustomerVO extends DestStafferVSCustomerBean
{

    @Relationship(tagField = "name", relationField = "customerId")
    private String customerName = "";

    @Relationship(tagField = "name", relationField = "stafferId")
    private String stafferName = "";

    @Relationship(tagField = "name", relationField = "destStafferId")
    private String stafferName1 = "";
    
    @Relationship(tagField = "code", relationField = "customerId")
    private String customerCode = "";

    /**
     * default constructor
     */
    public DestStafferVSCustomerVO()
    {
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
     * @return the customerCode
     */
    public String getCustomerCode()
    {
        return customerCode;
    }

    /**
     * @param customerCode
     *            the customerCode to set
     */
    public void setCustomerCode(String customerCode)
    {
        this.customerCode = customerCode;
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

    public String getStafferName1()
	{
		return stafferName1;
	}

	public void setStafferName1(String stafferName1)
	{
		this.stafferName1 = stafferName1;
	}

	/**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuffer retValue = new StringBuffer();

        retValue.append("DestStafferVSCustomerVO ( ").append(super.toString()).append(TAB).append("customerName = ").append(
            this.customerName).append(TAB).append("stafferName = ").append(this.stafferName).append(TAB).append(
            "customerCode = ").append(this.customerCode).append(TAB).append(" )");

        return retValue.toString();
    }

}
