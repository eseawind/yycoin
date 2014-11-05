package com.china.center.oa.tax.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

/**
 * 
 * 财务-金蝶 OA 标记数据
 *
 * @author fangliwen 2014-5-7
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_FINANCE_TAG")
public class FinanceTagBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	/**
	 * 标记类型
	 */
	private String type = "";
	
	/**
	 * 标记
	 */
	private String typeName = "";
	
	/**
	 * 单号
	 */
	private String fullId = "";
	
	/**
	 * 销售出库时关联的发票单
	 */
	private String insId = "";
	
	/**
	 * 标记
	 */
	private String tag = "";
	
	/**
	 * 出数据时间
	 */
	private String statsTime = "";
	
	private int mtype = 0;

	/**
	 * 
	 */
	public FinanceTagBean()
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
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName()
	{
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName)
	{
		this.typeName = typeName;
	}

	/**
	 * @return the fullId
	 */
	public String getFullId()
	{
		return fullId;
	}

	/**
	 * @param fullId the fullId to set
	 */
	public void setFullId(String fullId)
	{
		this.fullId = fullId;
	}

	/**
	 * @return the tag
	 */
	public String getTag()
	{
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag)
	{
		this.tag = tag;
	}

	/**
	 * @return the statsTime
	 */
	public String getStatsTime()
	{
		return statsTime;
	}

	/**
	 * @param statsTime the statsTime to set
	 */
	public void setStatsTime(String statsTime)
	{
		this.statsTime = statsTime;
	}

	/**
	 * @return the mtype
	 */
	public int getMtype()
	{
		return mtype;
	}

	/**
	 * @param mtype the mtype to set
	 */
	public void setMtype(int mtype)
	{
		this.mtype = mtype;
	}

	/**
	 * @return the insId
	 */
	public String getInsId()
	{
		return insId;
	}

	/**
	 * @param insId the insId to set
	 */
	public void setInsId(String insId)
	{
		this.insId = insId;
	}
}
