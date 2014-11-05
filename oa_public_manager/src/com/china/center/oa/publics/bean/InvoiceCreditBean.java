/**
 * File Name: InvoiceCreditBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-3-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.bean;


import java.io.Serializable;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;


/**
 * 事业部和信用的对应关系
 * 
 * @author ZHUZHU
 * @version 2011-3-12
 * @see InvoiceCreditBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_POSTCREDIT")
public class InvoiceCreditBean implements Serializable
{
    @Id
    private String id = "";

    @FK
    @Join(tagClass = StafferBean.class)
    private String stafferId = "";

    /**
     * 行业ID(industryId)
     */
    @FK(index = AnoConstant.FK_FIRST)
    @Join(tagClass = PrincipalshipBean.class)
    private String invoiceId = "";

    private double credit = 0.0d;

    /**
     * default constructor
     */
    public InvoiceCreditBean()
    {
    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
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
     * @return the invoiceId
     */
    public String getInvoiceId()
    {
        return invoiceId;
    }

    /**
     * @param invoiceId
     *            the invoiceId to set
     */
    public void setInvoiceId(String invoiceId)
    {
        this.invoiceId = invoiceId;
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
            .append("InvoiceCreditBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("invoiceId = ")
            .append(this.invoiceId)
            .append(TAB)
            .append("credit = ")
            .append(this.credit)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
