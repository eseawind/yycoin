package com.china.center.oa.finance.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PrePaymentWrap implements Serializable
{
	/**
	 * 客户
	 */
	private String customerId = "";
	
	private String customerName = "";
	
	/**
	 * 所有预收
	 */
	private double totalMoney = 0.0d;
	
	/**
	 * 普通类预收
	 */
	private double commonTotalMoney = 0.0d;
	
	/**
	 * 管理类预收
	 */
	private double manageTotalMoney = 0.0d;
	
	/**
	 * 冻结预收
	 */
	private double freezeTotalMoney = 0.0d;
	
	/**
	 * 最近收款单日期
	 */
	private String latestDate = "";
	
	/**
	 * 职员
	 */
	private String stafferName = "";

	public PrePaymentWrap()
	{
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

	/**
	 * @return the totalMoney
	 */
	public double getTotalMoney()
	{
		return totalMoney;
	}

	/**
	 * @param totalMoney the totalMoney to set
	 */
	public void setTotalMoney(double totalMoney)
	{
		this.totalMoney = totalMoney;
	}

	/**
	 * @return the commonTotalMoney
	 */
	public double getCommonTotalMoney()
	{
		return commonTotalMoney;
	}

	/**
	 * @param commonTotalMoney the commonTotalMoney to set
	 */
	public void setCommonTotalMoney(double commonTotalMoney)
	{
		this.commonTotalMoney = commonTotalMoney;
	}

	/**
	 * @return the manageTotalMoney
	 */
	public double getManageTotalMoney()
	{
		return manageTotalMoney;
	}

	/**
	 * @param manageTotalMoney the manageTotalMoney to set
	 */
	public void setManageTotalMoney(double manageTotalMoney)
	{
		this.manageTotalMoney = manageTotalMoney;
	}

	/**
	 * @return the freezeTotalMoney
	 */
	public double getFreezeTotalMoney()
	{
		return freezeTotalMoney;
	}

	/**
	 * @param freezeTotalMoney the freezeTotalMoney to set
	 */
	public void setFreezeTotalMoney(double freezeTotalMoney)
	{
		this.freezeTotalMoney = freezeTotalMoney;
	}

	/**
	 * @return the latestDate
	 */
	public String getLatestDate()
	{
		return latestDate;
	}

	/**
	 * @param latestDate the latestDate to set
	 */
	public void setLatestDate(String latestDate)
	{
		this.latestDate = latestDate;
	}

	/**
	 * @return the stafferName
	 */
	public String getStafferName()
	{
		return stafferName;
	}

	/**
	 * @param stafferName the stafferName to set
	 */
	public void setStafferName(String stafferName)
	{
		this.stafferName = stafferName;
	}
}
