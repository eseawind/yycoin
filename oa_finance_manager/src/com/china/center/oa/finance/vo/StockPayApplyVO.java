/**
 * File Name: StockPayApplyVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.finance.bean.StockPayApplyBean;


/**
 * StockPayApplyVO
 * 
 * @author ZHUZHU
 * @version 2011-2-17
 * @see StockPayApplyVO
 * @since 3.0
 */
@Entity(inherit = true)
public class StockPayApplyVO extends StockPayApplyBean
{
    @Relationship(relationField = "invoiceId")
    private String invoiceName = "";

    @Relationship(relationField = "dutyId")
    private String dutyName = "";

    @Relationship(relationField = "provideId")
    private String provideName = "";

    @Relationship(relationField = "provideId", tagField = "bank")
    private String provideBank = "";

    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    /**
     * default constructor
     */
    public StockPayApplyVO()
    {
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
     * @return the dutyName
     */
    public String getDutyName()
    {
        return dutyName;
    }

    /**
     * @param dutyName
     *            the dutyName to set
     */
    public void setDutyName(String dutyName)
    {
        this.dutyName = dutyName;
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
     * @return the provideBank
     */
    public String getProvideBank()
    {
        return provideBank;
    }

    /**
     * @param provideBank
     *            the provideBank to set
     */
    public void setProvideBank(String provideBank)
    {
        this.provideBank = provideBank;
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
            .append("StockPayApplyVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("invoiceName = ")
            .append(this.invoiceName)
            .append(TAB)
            .append("dutyName = ")
            .append(this.dutyName)
            .append(TAB)
            .append("provideName = ")
            .append(this.provideName)
            .append(TAB)
            .append("provideBank = ")
            .append(this.provideBank)
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
