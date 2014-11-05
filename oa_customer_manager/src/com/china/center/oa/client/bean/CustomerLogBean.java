package com.china.center.oa.client.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "t_center_customer_log")
public class CustomerLogBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	private String oldName = "";
	
	private String newName = "";
	
	private String opr = "";
	
	private String logTime = "";

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getOldName()
	{
		return oldName;
	}

	public void setOldName(String oldName)
	{
		this.oldName = oldName;
	}

	public String getNewName()
	{
		return newName;
	}

	public void setNewName(String newName)
	{
		this.newName = newName;
	}

	public String getOpr()
	{
		return opr;
	}

	public void setOpr(String opr)
	{
		this.opr = opr;
	}

	public String getLogTime()
	{
		return logTime;
	}

	public void setLogTime(String logTime)
	{
		this.logTime = logTime;
	}
	
}
