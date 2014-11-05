/**
 * File Name: InvoiceCreditVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-3-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.publics.bean.InvoiceCreditBean;


/**
 * InvoiceCreditVO
 * 
 * @author ZHUZHU
 * @version 2011-3-12
 * @see InvoiceCreditVO
 * @since 3.0
 */
@Entity(inherit = true)
public class InvoiceCreditVO extends InvoiceCreditBean
{
    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    @Relationship(relationField = "invoiceId")
    private String invoiceName = "";

    /**
     * default constructor
     */
    public InvoiceCreditVO()
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
     * @return the invoiceName
     */
    public String getInvoiceName()
    {
        return invoiceName;
    }

    /**
     * @param invoiceName
     *            the invoiceName to set
     */
    public void setInvoiceName(String invoiceName)
    {
        this.invoiceName = invoiceName;
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

        retValue.append("InvoiceCreditVO ( ").append(super.toString()).append(TAB).append(
            "stafferName = ").append(this.stafferName).append(TAB).append("invoiceName = ").append(
            this.invoiceName).append(TAB).append(" )");

        return retValue.toString();
    }

}
