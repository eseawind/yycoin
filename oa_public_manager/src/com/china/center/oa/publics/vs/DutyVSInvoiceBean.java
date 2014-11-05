/**
 * File Name: DutyVSInvoiceBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.vs;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;


/**
 * DutyVSInvoiceBean
 * 
 * @author ZHUZHU
 * @version 2011-2-27
 * @see DutyVSInvoiceBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_VS_DUTYINV")
public class DutyVSInvoiceBean implements Serializable
{
    @Id
    private String id = "";

    private int dutyType = 0;

    private String invoiceId = "";

    /**
     * default constructor
     */
    public DutyVSInvoiceBean()
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
     * @return the dutyType
     */
    public int getDutyType()
    {
        return dutyType;
    }

    /**
     * @param dutyType
     *            the dutyType to set
     */
    public void setDutyType(int dutyType)
    {
        this.dutyType = dutyType;
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
            .append("DutyVSInvoiceBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("dutyType = ")
            .append(this.dutyType)
            .append(TAB)
            .append("invoiceId = ")
            .append(this.invoiceId)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
