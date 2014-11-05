package com.china.center.oa.sail.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

/**
 * 
 * 中信批量领样退货或转销售
 *
 * @author fangliwen 2013-10-14
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_BATCHSWATCH")
public class BatchSwatchBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	@FK
	private String batchId = "";
	
	private String outId = "";
	
	private String productName = "";
	
	private int amount = 0;
	
	/**
	 * 退货/销售
	 */
	private String action = "";
	
	/**
	 * 检查是否通过检查
	 * 0:通过；1：未通过
	 */
	private int ret = 1;
	
	/**
	 * 检查结果
	 */
	private String result = "产品不存在或数量不足";
	
	private String customerId = "";
	
	private String customerName = "";
	
	private String baseId = "";
	
	private String dirDeport = "";
	
	private String description = "";

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
	 * @return the productName
	 */
	public String getProductName()
	{
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	/**
	 * @return the amount
	 */
	public int getAmount()
	{
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	/**
	 * @return the action
	 */
	public String getAction()
	{
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action)
	{
		this.action = action;
	}

	/**
	 * @return the ret
	 */
	public int getRet()
	{
		return ret;
	}

	/**
	 * @param ret the ret to set
	 */
	public void setRet(int ret)
	{
		this.ret = ret;
	}

	/**
	 * @return the result
	 */
	public String getResult()
	{
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result)
	{
		this.result = result;
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
	 * @return the baseId
	 */
	public String getBaseId()
	{
		return baseId;
	}

	/**
	 * @param baseId the baseId to set
	 */
	public void setBaseId(String baseId)
	{
		this.baseId = baseId;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName()
	{
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	public String getDirDeport()
	{
		return dirDeport;
	}

	public void setDirDeport(String dirDeport)
	{
		this.dirDeport = dirDeport;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
