/**
 * File Name: ChangeLogBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-3-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.client.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.publics.constant.OperationConstant;


/**
 * ChangeLogBean
 * 
 * @author ZHUZHU
 * @version 2009-3-12
 * @see ChangeLogBean
 * @since 1.0
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_LOG_CCHANGE")
public class ChangeLogBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    private String stafferId = "";

    private String stafferName = "";

    private String customerId = "";

    private String customerName = "";

    private String customerCode = "";

    private int operation = OperationConstant.OPERATION_CHANGELOG_ADD;

    private String logTime = "";

    /**
     * default constructor
     */
    public ChangeLogBean()
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
     * @return the operation
     */
    public int getOperation()
    {
        return operation;
    }

    /**
     * @param operation
     *            the operation to set
     */
    public void setOperation(int operation)
    {
        this.operation = operation;
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
