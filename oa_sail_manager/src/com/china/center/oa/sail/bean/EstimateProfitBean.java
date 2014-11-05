package com.china.center.oa.sail.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_ESTIMATEPROFIT")
public class EstimateProfitBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	/**
	 * 银行产品（非OA产品）
	 */
	@Unique
	private String productName = "";
	
	/**
	 * 预估毛利
	 */
	private double profit = 0.0d;
	
	private String logTime = "";
	
	private String stafferName = "";

	/**
	 * 
	 */
	public EstimateProfitBean()
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
	 * @return the profit
	 */
	public double getProfit()
	{
		return profit;
	}

	/**
	 * @param profit the profit to set
	 */
	public void setProfit(double profit)
	{
		this.profit = profit;
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
