package com.china.center.oa.client.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.StafferBean;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_ACCESSLOG")
public class AccessLogBean implements Serializable
{

	@Id(autoIncrement = true)
	private String id = "";
	
	@Join(tagClass = StafferBean.class, type = JoinType.LEFT)
	private String stafferId = "";
	
	@Join(tagClass = CustomerBean.class, type = JoinType.LEFT)
	private String customerId = "";
	
	private String logTime = "";
	
	/**
	 * 是否是自己的客户
	 */
	private String description = "";

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getStafferId()
	{
		return stafferId;
	}

	public void setStafferId(String stafferId)
	{
		this.stafferId = stafferId;
	}

	public String getCustomerId()
	{
		return customerId;
	}

	public void setCustomerId(String customerId)
	{
		this.customerId = customerId;
	}

	public String getLogTime()
	{
		return logTime;
	}

	public void setLogTime(String logTime)
	{
		this.logTime = logTime;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public String toString()
	{
		return this.toString();
	}
}
