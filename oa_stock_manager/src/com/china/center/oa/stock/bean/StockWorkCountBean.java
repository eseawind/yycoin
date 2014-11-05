package com.china.center.oa.stock.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_STOCK_WORKCOUNT")
public class StockWorkCountBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	@Unique(dependFields = "stockItemId")
	private String stockId = "";
	
	private String stockItemId = "";
	
	private int count = 0;

	/**
	 * 
	 */
	public StockWorkCountBean()
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
	 * @return the stockId
	 */
	public String getStockId()
	{
		return stockId;
	}

	/**
	 * @param stockId the stockId to set
	 */
	public void setStockId(String stockId)
	{
		this.stockId = stockId;
	}

	/**
	 * @return the stockItemId
	 */
	public String getStockItemId()
	{
		return stockItemId;
	}

	/**
	 * @param stockItemId the stockItemId to set
	 */
	public void setStockItemId(String stockItemId)
	{
		this.stockItemId = stockItemId;
	}

	/**
	 * @return the count
	 */
	public int getCount()
	{
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count)
	{
		this.count = count;
	}
}
