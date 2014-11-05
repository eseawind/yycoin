/**
 * File Name: CustomerAssignWrap.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-12-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.client.wrap;


import java.io.Serializable;


/**
 * CustomerAssignWrap
 * 
 * @author ZHUZHU
 * @version 2008-12-14
 * @see CustomerAssignWrap
 * @since 1.0
 */
public class CustomerAssignWrap implements Serializable
{
    private String stafferId = "";

    private String stafferName = "";

    private String customerId = "";

    private String customerName = "";

    private String customerCode = "";

    private String sellType = "";

    private String loginTime = "";

    public CustomerAssignWrap()
    {
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
     * @return the customerId
     */
    public String getCustomerId()
    {
        return customerId;
    }

    /**
     * @param customerId
     *            the customerId to set
     */
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
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
     * @return the sellType
     */
    public String getSellType()
    {
        return sellType;
    }

    /**
     * @param sellType
     *            the sellType to set
     */
    public void setSellType(String sellType)
    {
        this.sellType = sellType;
    }

    /**
     * @return the loginTime
     */
    public String getLoginTime()
    {
        return loginTime;
    }

    /**
     * @param loginTime
     *            the loginTime to set
     */
    public void setLoginTime(String loginTime)
    {
        this.loginTime = loginTime;
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
}
