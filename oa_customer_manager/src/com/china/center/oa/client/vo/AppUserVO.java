package com.china.center.oa.client.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.oa.client.bean.AppUserBean;

@SuppressWarnings("serial")
@Entity(inherit = true)
public class AppUserVO extends AppUserBean
{
	/**
	 * 状态中文
	 */
	@Ignore
	private String statusVal = "";
	
	@Ignore
	private double creditMoney = 0.0d;
	
	@Ignore
	private double preMoney = 0.0d;
	
	@Ignore
	private double receiveMoney = 0.0d;
	
	/**
	 * 绑定的客户
	 */
	@Ignore
	private String customerName = "";

	/**
	 * 
	 */
	public AppUserVO()
	{
	}

	public String getStatusVal()
	{
		return statusVal;
	}

	public void setStatusVal(String statusVal)
	{
		this.statusVal = statusVal;
	}

	public double getCreditMoney()
	{
		return creditMoney;
	}

	public void setCreditMoney(double creditMoney)
	{
		this.creditMoney = creditMoney;
	}

	public double getPreMoney()
	{
		return preMoney;
	}

	public void setPreMoney(double preMoney)
	{
		this.preMoney = preMoney;
	}

	public double getReceiveMoney()
	{
		return receiveMoney;
	}

	public void setReceiveMoney(double receiveMoney)
	{
		this.receiveMoney = receiveMoney;
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
}
