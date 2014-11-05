package com.china.center.oa.commission.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.product.bean.ProductBean;

@SuppressWarnings("serial")
public abstract class AbstractBlackOutDetailBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	@FK
	private String outId = "";
	
	private String outBalanceId = "";
	
	@Join(tagClass = ProductBean.class, type = JoinType.LEFT)
	private String productId = "";
	
	private int amount = 0;
	
	private double price = 0.0d;
	
	private double costPrice = 0.0d;

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
	 * @return the price
	 */
	public double getPrice()
	{
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price)
	{
		this.price = price;
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
	 * @return the outBalanceId
	 */
	public String getOutBalanceId()
	{
		return outBalanceId;
	}

	/**
	 * @param outBalanceId the outBalanceId to set
	 */
	public void setOutBalanceId(String outBalanceId)
	{
		this.outBalanceId = outBalanceId;
	}

	/**
	 * @return the costPrice
	 */
	public double getCostPrice()
	{
		return costPrice;
	}

	/**
	 * @param costPrice the costPrice to set
	 */
	public void setCostPrice(double costPrice)
	{
		this.costPrice = costPrice;
	}
}
