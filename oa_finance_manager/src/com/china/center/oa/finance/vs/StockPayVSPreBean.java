package com.china.center.oa.finance.vs;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

/**
 * 采购付款关联预付款
 * @author smart
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_VS_STOCKPREPAY")
public class StockPayVSPreBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	@FK
	private String payApplyId = "";
	
	private String prePayId = "";
	
	private double moneys = 0.0d;
	
	private String logTime = "";
	
	/**
	 * 1:使用过  0:未使用
	 */
	private int status = 0;

	public StockPayVSPreBean()
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
	 * @return the payApplyId
	 */
	public String getPayApplyId()
	{
		return payApplyId;
	}

	/**
	 * @param payApplyId the payApplyId to set
	 */
	public void setPayApplyId(String payApplyId)
	{
		this.payApplyId = payApplyId;
	}

	/**
	 * @return the prePayId
	 */
	public String getPrePayId()
	{
		return prePayId;
	}

	/**
	 * @param prePayId the prePayId to set
	 */
	public void setPrePayId(String prePayId)
	{
		this.prePayId = prePayId;
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
	 * @return the status
	 */
	public int getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status)
	{
		this.status = status;
	}
}
