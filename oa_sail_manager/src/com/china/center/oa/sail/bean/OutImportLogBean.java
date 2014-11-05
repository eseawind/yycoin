package com.china.center.oa.sail.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.sail.constanst.OutImportConstant;

@Entity
@Table(name = "t_center_outimport_log")
public class OutImportLogBean implements Serializable
{
	@Id
	private String id = "";
	
	@FK
	private String batchId = "";
	
	private String message = "";
	
	private String logTime = "";
	
	/**
	 * 0:失败 1：处理中 2：成功 
	 */
	private int status = OutImportConstant.LOGSTATUS_FAIL;
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getBatchId()
	{
		return batchId;
	}

	public void setBatchId(String batchId)
	{
		this.batchId = batchId;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getLogTime()
	{
		return logTime;
	}

	public void setLogTime(String logTime)
	{
		this.logTime = logTime;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}
}
