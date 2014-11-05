package com.china.center.oa.finance.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_VS_INSPRODUCT")
public class InvoiceinsDetailBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	@FK
	private String insId = "";
	
	/**
	 * 开票品名
	 */
	private String showName = "";
	
	private String productId = "";
	
	private String productName = "";
	
	private int amount = 0;
	
	private double moneys = 0.0d;

	public InvoiceinsDetailBean()
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
	 * @return the insId
	 */
	public String getInsId()
	{
		return insId;
	}

	/**
	 * @param insId the insId to set
	 */
	public void setInsId(String insId)
	{
		this.insId = insId;
	}

	/**
	 * @return the productId
	 */
	public String getProductId()
	{
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId)
	{
		this.productId = productId;
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
	 * @return the moneys
	 */
	public double getMoneys()
	{
		return moneys;
	}

	/**
	 * @param moneys the moneys to set
	 */
	public void setMoneys(double moneys)
	{
		this.moneys = moneys;
	}

	/**
	 * @return the showName
	 */
	public String getShowName()
	{
		return showName;
	}

	/**
	 * @param showName the showName to set
	 */
	public void setShowName(String showName)
	{
		this.showName = showName;
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
}
