/**
 * File Name: SailTranApplyView.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2012-5-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.sail.bean.SailTranApplyBean;


/**
 * SailTranApplyView
 * 
 * @author ZHUZHU
 * @version 2012-5-6
 * @see SailTranApplyView
 * @since 3.0
 */
@Entity(inherit = true)
public class SailTranApplyView extends SailTranApplyBean
{
    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    @Relationship(relationField = "oldStafferId")
    private String oldStafferName = "";

    @Relationship(relationField = "customerId")
    private String customerName = "";

    /**
     * default constructor
     */
    public SailTranApplyView()
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
            .append("SailTranApplyView ( ")
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

    /**
     * @return the oldStafferName
     */
    public String getOldStafferName()
    {
        return oldStafferName;
    }

    /**
     * @param oldStafferName
     *            the oldStafferName to set
     */
    public void setOldStafferName(String oldStafferName)
    {
        this.oldStafferName = oldStafferName;
    }

}
