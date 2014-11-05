package com.china.center.oa.sail.bean;

import java.io.Serializable;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_DISTBASE")
public class DistributionBaseBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	@FK
	private String refId = "";
	
	@FK(index = AnoConstant.FK_FIRST)
	private String outId = "";
	
	private String baseId = "";

	/**
	 * 
	 */
	public DistributionBaseBean()
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

	/**
	 * @return the baseId
	 */
	public String getBaseId()
	{
		return baseId;
	}

	/**
	 * @param baseId the baseId to set
	 */
	public void setBaseId(String baseId)
	{
		this.baseId = baseId;
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
}
