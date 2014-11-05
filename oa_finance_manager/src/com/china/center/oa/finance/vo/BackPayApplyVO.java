/**
 * File Name: BackPayApplyVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-3-5<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.finance.bean.BackPayApplyBean;


/**
 * BackPayApplyVO
 * 
 * @author ZHUZHU
 * @version 2011-3-5
 * @see BackPayApplyVO
 * @since 3.0
 */
@Entity(inherit = true)
public class BackPayApplyVO extends BackPayApplyBean
{
    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    @Relationship(relationField = "customerId")
    private String customerName = "";

    /**
     * default constructor
     */
    public BackPayApplyVO()
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
            .append("BackPayApplyVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("customerName = ")
            .append(this.customerName)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
