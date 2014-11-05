package com.china.center.oa.sail.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_PAYTAG")
public class OutPayTagBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	@Unique
	private String outId = "";
	
	/**
	 * 0:初始  1:处理结束 2：处理过一次（针对未开票情况，再次处理时，若还是未开票，则不做操作）
	 */
	private int status = 0;

	/**
	 * 
	 */
	public OutPayTagBean()
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
	 * @return the outId
	 */
	public String getOutId()
	{
		return outId;
	}

	/**
	 * @param outId the outId to set
	 */
	public void setOutId(String outId)
	{
		this.outId = outId;
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
