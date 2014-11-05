package com.china.center.oa.client.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_VS_APPUSERCUST")
public class AppUserVSCustomerBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	@Unique
	private String appUserId = "";
	
	// 也是唯一
	@FK
	private String customerId = "";
	
	public AppUserVSCustomerBean()
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

	public String getAppUserId()
	{
		return appUserId;
	}

	public void setAppUserId(String appUserId)
	{
		this.appUserId = appUserId;
	}

	public String getCustomerId()
	{
		return customerId;
	}

	public void setCustomerId(String customerId)
	{
		this.customerId = customerId;
	}
}
