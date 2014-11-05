package com.china.center.oa.sail.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_BATCHRET_LOG")
public class BatchReturnLog implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	private String batchId = "";
	
	@Unique
	private String outId = "";
	
    /**
     * 商务模式下，经办人
     */
    private String operator = "";
    
    /**
     * 商务模式下，经办人名称
     */
    private String operatorName = "";
    
    private String logTime = "";

	/**
	 * 
	 */
	public BatchReturnLog()
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
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return the batchId
	 */
	public String getBatchId()
	{
		return batchId;
	}

	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(String batchId)
	{
		this.batchId = batchId;
	}

	/**
	 * @return the outId
	 */
	public String getOutId()
	{
		return outId;
	}

	/**
	 * @param outId the outId to set
	 */
	public void setOutId(String outId)
	{
		this.outId = outId;
	}

	/**
	 * @return the operator
	 */
	public String getOperator()
	{
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	/**
	 * @return the operatorName
	 */
	public String getOperatorName()
	{
		return operatorName;
	}

	/**
	 * @param operatorName the operatorName to set
	 */
	public void setOperatorName(String operatorName)
	{
		this.operatorName = operatorName;
	}

	/**
	 * @return the logTime
	 */
	public String getLogTime()
	{
		return logTime;
	}

	/**
	 * @param logTime the logTime to set
	 */
	public void setLogTime(String logTime)
	{
		this.logTime = logTime;
	}
}
