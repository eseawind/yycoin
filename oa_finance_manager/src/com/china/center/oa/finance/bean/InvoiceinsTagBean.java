package com.china.center.oa.finance.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

/**
 * 
 * 记录完成的发票<br>
 * 为生成销售出库的标记数据做准备
 *
 * @author fangliwen 2014-5-13
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_INVOICEINSTAG")
public class InvoiceinsTagBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	private String insId = "";

	/**
	 * 
	 */
	public InvoiceinsTagBean()
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
