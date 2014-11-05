package com.china.center.oa.customerservice.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Table;

@Entity
@Table(name = "T_CENTER_VS_FBOUT")
public class FeedBackVSOutBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	@FK
	private String taskId = "";
	
	private String outId = "";
	
	private String changeTime = "";
	
	@Ignore
	private String receiver = "";
	
	@Ignore
	private String mobile = "";
	
	/**
	 * 
	 */
	public FeedBackVSOutBean()
	{
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTaskId()
	{
		return taskId;
	}

	public void setTaskId(String taskId)
	{
		this.taskId = taskId;
	}

	public String getOutId()
	{
		return outId;
	}

	public void setOutId(String outId)
	{
		this.outId = outId;
	}

	public String getChangeTime()
	{
		return changeTime;
	}

	public void setChangeTime(String changeTime)
	{
		this.changeTime = changeTime;
	}

	public String getReceiver()
	{
		return receiver;
	}

	public void setReceiver(String receiver)
	{
		this.receiver = receiver;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}
}
