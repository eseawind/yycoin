package com.china.center.oa.finance.vs;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_VS_STOCKPAYCOMP")
public class StockPayVSComposeBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	@FK
	private String composeId = "";
	
	private String stockPayApplyId = "";

	/**
	 * 
	 */
	public StockPayVSComposeBean()
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
	 * @return the composeId
	 */
	public String getComposeId()
	{
		return composeId;
	}

	/**
	 * @param composeId the composeId to set
	 */
	public void setComposeId(String composeId)
	{
		this.composeId = composeId;
	}

	/**
	 * @return the stockPayApplyId
	 */
	public String getStockPayApplyId()
	{
		return stockPayApplyId;
	}

	/**
	 * @param stockPayApplyId the stockPayApplyId to set
	 */
	public void setStockPayApplyId(String stockPayApplyId)
	{
		this.stockPayApplyId = stockPayApplyId;
	}
}
