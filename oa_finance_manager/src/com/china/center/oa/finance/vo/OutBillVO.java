/**
 * File Name: InBillVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.finance.bean.OutBillBean;


/**
 * InBillVO
 * 
 * @author ZHUZHU
 * @version 2010-12-26
 * @see OutBillVO
 * @since 3.0
 */
@Entity(inherit = true)
public class OutBillVO extends OutBillBean
{
    @Relationship(relationField = "bankId")
    private String bankName = "";

    @Relationship(relationField = "destBankId")
    private String destBankName = "";

    @Relationship(relationField = "provideId")
    private String provideName = "";

    @Relationship(relationField = "invoiceId")
    private String invoiceName = "";

    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    @Relationship(relationField = "ownerId")
    private String ownerName = "";

    @Relationship(relationField = "bankId", tagField = "dutyId")
    private String dutyId = "";

    /**
     * default constructor
     */
    public OutBillVO()
    {
    }

    /**
     * @return the bankName
     */
    public String getBankName()
    {
        return bankName;
    }

    /**
     * @param bankName
     *            the bankName to set
     */
    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    /**
     * @return the provideName
     */
    public String getProvideName()
    {
        return provideName;
    }

    /**
     * @param provideName
     *            the provideName to set
     */
    public void setProvideName(String provideName)
    {
        this.provideName = provideName;
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
     * @return the ownerName
     */
    public String getOwnerName()
    {
        return ownerName;
    }

    /**
     * @param ownerName
     *            the ownerName to set
     */
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    /**
     * @return the destBankName
     */
    public String getDestBankName()
    {
        return destBankName;
    }

    /**
     * @param destBankName
     *            the destBankName to set
     */
    public void setDestBankName(String destBankName)
    {
        this.destBankName = destBankName;
    }

    /**
     * @return the dutyId
     */
    public String getDutyId()
    {
        return dutyId;
    }

    /**
     * @param dutyId
     *            the dutyId to set
     */
    public void setDutyId(String dutyId)
    {
        this.dutyId = dutyId;
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
            .append("OutBillVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("bankName = ")
            .append(this.bankName)
            .append(TAB)
            .append("destBankName = ")
            .append(this.destBankName)
            .append(TAB)
            .append("provideName = ")
            .append(this.provideName)
            .append(TAB)
            .append("invoiceName = ")
            .append(this.invoiceName)
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("ownerName = ")
            .append(this.ownerName)
            .append(TAB)
            .append("dutyId = ")
            .append(this.dutyId)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
