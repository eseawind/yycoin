package com.china.center.oa.extsail.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

/**
 * 紫金农商物流信息
 * @author smart
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_ZJRPDIST")
public class ZJRCDistributionBean implements Serializable
{
	/**
	 * 
	 */
	@Id(autoIncrement = true)
	private String id = "";
	
	/**
	 * 紫金农商单号
	 */
	@FK
	private String zjrcOutId = "";
	
	/**
	 * OA 状态为已发货, 此状态置为 配送中,否则,置为 备货中
	 * 紫金人员确认”银行签收“，状态为银行已收
	 * 紫金人员确认”客户签收“，状态为客户已收
	 */
	private int status = 0;
	
	private String logTime = "";
	
	public ZJRCDistributionBean()
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
	 * @return the zjrcOutId
	 */
	public String getZjrcOutId()
	{
		return zjrcOutId;
	}

	/**
	 * @param zjrcOutId the zjrcOutId to set
	 */
	public void setZjrcOutId(String zjrcOutId)
	{
		this.zjrcOutId = zjrcOutId;
	}
}
