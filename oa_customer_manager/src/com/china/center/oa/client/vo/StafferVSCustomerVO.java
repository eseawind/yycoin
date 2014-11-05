/**
 * File Name: StafferVSCustomerVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-3-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.client.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.client.vs.StafferVSCustomerBean;


/**
 * StafferVSCustomerVO
 * 
 * @author ZHUZHU
 * @version 2009-3-15
 * @see StafferVSCustomerVO
 * @since 1.0
 */
@Entity(inherit = true)
public class StafferVSCustomerVO extends StafferVSCustomerBean
{
    @Relationship(tagField = "name", relationField = "customerId")
    private String customerName = "";

    @Relationship(tagField = "name", relationField = "stafferId")
    private String stafferName = "";

    @Relationship(tagField = "code", relationField = "customerId")
    private String customerCode = "";

    /**
     * default constructor
     */
    public StafferVSCustomerVO()
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

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuffer retValue = new StringBuffer();

        retValue.append("StafferVSCustomerVO ( ").append(super.toString()).append(TAB).append("customerName = ").append(
            this.customerName).append(TAB).append("stafferName = ").append(this.stafferName).append(TAB).append(
            "customerCode = ").append(this.customerCode).append(TAB).append(" )");

        return retValue.toString();
    }
}
