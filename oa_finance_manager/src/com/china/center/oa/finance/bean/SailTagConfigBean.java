package com.china.center.oa.finance.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_SAILTAG_CONFIG")
public class SailTagConfigBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	/**
	 * 0：本月， 1：本月之前
	 */
	private int changeTime = 0;
	
	/**
	 * 旧货
	 */
	private int oldGood = 0;
	
	/**
	 * 税率
	 */
	private double rate = 0.0d;
	
	private String dutyId = "";
	
	private String flag = "";

	/**
	 * 
	 */
	public SailTagConfigBean()
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
	 * @return the changeTime
	 */
	public int getChangeTime()
	{
		return changeTime;
	}

	/**
	 * @param changeTime the changeTime to set
	 */
	public void setChangeTime(int changeTime)
	{
		this.changeTime = changeTime;
	}

	/**
	 * @return the oldGood
	 */
	public int getOldGood()
	{
		return oldGood;
	}

	/**
	 * @param oldGood the oldGood to set
	 */
	public void setOldGood(int oldGood)
	{
		this.oldGood = oldGood;
	}

	/**
	 * @return the rate
	 */
	public double getRate()
	{
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(double rate)
	{
		this.rate = rate;
	}

	/**
	 * @return the dutyId
	 */
	public String getDutyId()
	{
		return dutyId;
	}

	/**
	 * @param dutyId the dutyId to set
	 */
	public void setDutyId(String dutyId)
	{
		this.dutyId = dutyId;
	}

	/**
	 * @return the flag
	 */
	public String getFlag()
	{
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag)
	{
		this.flag = flag;
	}
}
