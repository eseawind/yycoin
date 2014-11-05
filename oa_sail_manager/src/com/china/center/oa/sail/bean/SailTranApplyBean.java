/**
 * File Name: SailTranApply.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2012-5-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.bean;


import java.io.Serializable;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.sail.constanst.SailConstant;


/**
 * SailTranApply
 * 
 * @author ZHUZHU
 * @version 2012-5-6
 * @see SailTranApplyBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_SAILTRANAPPLY")
public class SailTranApplyBean implements Serializable
{
    @Id
    private String id = "";

    private int status = SailConstant.SAILTRANAPPLY_SUBMIT;

    private String outId = "";

    @Unique(dependFields = "status")
    @FK(index = AnoConstant.FK_FIRST)
    @Join(tagClass = CustomerBean.class)
    private String customerId = "";

    @FK
    @Join(tagClass = StafferBean.class, alias = "BS1")
    private String stafferId = "";

    @Join(tagClass = StafferBean.class, alias = "SB2")
    private String oldStafferId = "";

    private String logTime = "";

    private String description = "";
    
    private String operator = "";
    
    private String operatorName = "";

    /**
     * default constructor
     */
    public SailTranApplyBean()
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
     * @return the outId
     */
    public String getOutId()
    {
        return outId;
    }

    /**
     * @param outId
     *            the outId to set
     */
    public void setOutId(String outId)
    {
        this.outId = outId;
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
     * @return the oldStafferId
     */
    public String getOldStafferId()
    {
        return oldStafferId;
    }

    /**
     * @param oldStafferId
     *            the oldStafferId to set
     */
    public void setOldStafferId(String oldStafferId)
    {
        this.oldStafferId = oldStafferId;
    }

    public String getOperator()
	{
		return operator;
	}

	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	public String getOperatorName()
	{
		return operatorName;
	}

	public void setOperatorName(String operatorName)
	{
		this.operatorName = operatorName;
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
            .append("SailTranApplyBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("outId = ")
            .append(this.outId)
            .append(TAB)
            .append("customerId = ")
            .append(this.customerId)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("oldStafferId = ")
            .append(this.oldStafferId)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("operator = ")
            .append(this.operator)
            .append(TAB)
            .append("operatorName = ")
            .append(this.operatorName)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
