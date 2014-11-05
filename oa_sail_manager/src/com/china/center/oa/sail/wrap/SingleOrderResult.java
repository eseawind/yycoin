package com.china.center.oa.sail.wrap;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SingleOrderResult implements Serializable
{
	private String orderNo = "";
	
	private String status = "";
	
	private String sale = "";
	
	private String pay = "";
	
	private String payAccount = "";
	
	private String payStatus = "";
	
	private String outTime = "";
	
	public SingleOrderResult()
	{
		
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getSale()
	{
		return sale;
	}

	public void setSale(String sale)
	{
		this.sale = sale;
	}

	public String getPay()
	{
		return pay;
	}

	public void setPay(String pay)
	{
		this.pay = pay;
	}

	public String getPayAccount()
	{
		return payAccount;
	}

	public void setPayAccount(String payAccount)
	{
		this.payAccount = payAccount;
	}

	public String getPayStatus()
	{
		return payStatus;
	}

	public void setPayStatus(String payStatus)
	{
		this.payStatus = payStatus;
	}

	public String getOutTime()
	{
		return outTime;
	}

	public void setOutTime(String outTime)
	{
		this.outTime = outTime;
	}
}
