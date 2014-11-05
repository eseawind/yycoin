package com.china.center.oa.finance.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_AUTOPAYLOG")
public class AutoPayLogBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	private String outId = "";
	
	private String outBalanceId = "";
	
	private int outType = 0;
	
	private String billId = "";
	
	private double money = 0.0d;
	
	private String logTime = "";

	/**
	 * 
	 */
	public AutoPayLogBean()
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
	 * @return the outType
	 */
	public int getOutType()
	{
		return outType;
	}

	/**
	 * @param outType the outType to set
	 */
	public void setOutType(int outType)
	{
		this.outType = outType;
	}

	/**
	 * @return the billId
	 */
	public String getBillId()
	{
		return billId;
	}

	/**
	 * @param billId the billId to set
	 */
	public void setBillId(String billId)
	{
		this.billId = billId;
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
