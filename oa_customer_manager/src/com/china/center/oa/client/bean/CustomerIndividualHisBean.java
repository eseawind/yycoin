package com.china.center.oa.client.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity(name = "个人客户", inherit = true)
@Table(name = "T_CENTER_CUSTOMER_INDIVIDUAL_HIS")
public class CustomerIndividualHisBean extends AbstractCustomerIndividualBean
{
	@Id(autoIncrement = true)
	private String id = "";
	
	/**
	 * 每一次修改记录一个批次
	 */
	private String batchId = "";
	
	/**
	 * 0:修改前；1：修改后
	 */
	private int forward = 0;

	private String customerId = "";
	
	private String name = "";
	
	private String updateId = "";
	
	private String updateTime = "";

	/**
	 * 
	 */
	public CustomerIndividualHisBean()
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
	 * @return the customerId
	 */
	public String getCustomerId()
	{
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId)
	{
		this.customerId = customerId;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the updateId
	 */
	public String getUpdateId()
	{
		return updateId;
	}

	/**
	 * @param updateId the updateId to set
	 */
	public void setUpdateId(String updateId)
	{
		this.updateId = updateId;
	}

	/**
	 * @return the updateTime
	 */
	public String getUpdateTime()
	{
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime)
	{
		this.updateTime = updateTime;
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
	 * @return the forward
	 */
	public int getForward()
	{
		return forward;
	}

	/**
	 * @param forward the forward to set
	 */
	public void setForward(int forward)
	{
		this.forward = forward;
	}
}
