package com.china.center.oa.finance.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BatchSplitInBillWrap implements Serializable
{
	/**
	 * 客户
	 */
	private String CustomerId = "";
	
	/**
	 * 0：是（勾款） 1：否（不勾款）
	 */
	private int type = 0;
	
	/**
	 * 销售单（销售出库、委托代销）
	 */
	private String outId = "";
	
	private String balanceId = "";
	
	private double money = 0.0d;

	public BatchSplitInBillWrap()
	{
	}

	/**
	 * @return the customerId
	 */
	public String getCustomerId()
	{
		return CustomerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId)
	{
		CustomerId = customerId;
	}

	/**
	 * @return the type
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type)
	{
		this.type = type;
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
	 * @return the money
	 */
	public double getMoney()
	{
		return money;
	}

	/**
	 * @param money the money to set
	 */
	public void setMoney(double money)
	{
		this.money = money;
	}

	/**
	 * @return the balanceId
	 */
	public String getBalanceId()
	{
		return balanceId;
	}

	/**
	 * @param balanceId the balanceId to set
	 */
	public void setBalanceId(String balanceId)
	{
		this.balanceId = balanceId;
	}
}
