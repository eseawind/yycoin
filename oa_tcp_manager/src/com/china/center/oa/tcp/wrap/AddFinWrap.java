/**
 * File Name: AddFinWrap.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-11-11<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.wrap;


import java.io.Serializable;


/**
 * AddFinWrap
 * 
 * @author ZHUZHU
 * @version 2011-11-11
 * @see AddFinWrap
 * @since 3.0
 */
public class AddFinWrap implements Serializable
{
    private String taxId = "";

    private String taxName = "";

    private String showMoney = "";

    private String stafferId = "";

    private String stafferName = "";

    /**
     * default constructor
     */
    public AddFinWrap()
    {
    }

    /**
     * @return the taxId
     */
    public String getTaxId()
    {
        return taxId;
    }

    /**
     * @param taxId
     *            the taxId to set
     */
    public void setTaxId(String taxId)
    {
        this.taxId = taxId;
    }

    /**
     * @return the taxName
     */
    public String getTaxName()
    {
        return taxName;
    }

    /**
     * @param taxName
     *            the taxName to set
     */
    public void setTaxName(String taxName)
    {
        this.taxName = taxName;
    }

    /**
     * @return the showMoney
     */
    public String getShowMoney()
    {
        return showMoney;
    }

    /**
     * @param showMoney
     *            the showMoney to set
     */
    public void setShowMoney(String showMoney)
    {
        this.showMoney = showMoney;
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

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("AddFinWrap ( ")
            .append(super.toString())
            .append(TAB)
            .append("taxId = ")
            .append(this.taxId)
            .append(TAB)
            .append("taxName = ")
            .append(this.taxName)
            .append(TAB)
            .append("showMoney = ")
            .append(this.showMoney)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
