/**
 * File Name: CustomerCheckBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-3-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.CommonConstant;


/**
 * CustomerCheckBean
 * 
 * @author ZHUZHU
 * @version 2009-3-15
 * @see CustomerCheckItemBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_CUS_CHECKITEM")
public class CustomerCheckItemBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @FK
    private String parentId = "";

    private String customerId = "";

    private String customerName = "";

    private String customerCode = "";

    @Join(tagClass = StafferBean.class)
    private String stafferId = "";

    private int status = CommonConstant.STATUS_INIT;

    private int ret = CommonConstant.RESULT_INIT;

    private String logTime = "";

    private String description = "";

    /**
     * default constructor
     */
    public CustomerCheckItemBean()
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
     * @return the parentId
     */
    public String getParentId()
    {
        return parentId;
    }

    /**
     * @param parentId
     *            the parentId to set
     */
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
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
     * @return the status
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status)
    {
        this.status = status;
    }

    /**
     * @return the ret
     */
    public int getRet()
    {
        return ret;
    }

    /**
     * @param ret
     *            the ret to set
     */
    public void setRet(int ret)
    {
        this.ret = ret;
    }

    /**
     * @return the logTime
     */
    public String getLogTime()
    {
        return logTime;
    }

    /**
     * @param logTime
     *            the logTime to set
     */
    public void setLogTime(String logTime)
    {
        this.logTime = logTime;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
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
}
