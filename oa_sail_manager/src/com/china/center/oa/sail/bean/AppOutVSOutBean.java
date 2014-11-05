package com.china.center.oa.sail.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_VS_APPOUT")
public class AppOutVSOutBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	@Unique
	private String outId = "";
	
	@FK
	private String appOutId = "";
	
	public AppOutVSOutBean()
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

	public String getOutId()
	{
		return outId;
	}

	public void setOutId(String outId)
	{
		this.outId = outId;
	}

	public String getAppOutId()
	{
		return appOutId;
	}

	public void setAppOutId(String appOutId)
	{
		this.appOutId = appOutId;
	}
}
