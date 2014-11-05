package com.china.center.oa.finance.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_AUTOPAYMONI")
public class AutoPayMonitorBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	/**
	 * 自动勾款时存放 销售单与预收单,防止数据重复使用
	 */
	@Unique
	private String refId = "";

	/**
	 * 
	 */
	public AutoPayMonitorBean()
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
	 * @return the refId
	 */
	public String getRefId()
	{
		return refId;
	}

	/**
	 * @param refId the refId to set
	 */
	public void setRefId(String refId)
	{
		this.refId = refId;
	}
}
