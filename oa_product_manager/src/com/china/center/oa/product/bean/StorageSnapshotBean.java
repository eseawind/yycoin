package com.china.center.oa.product.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_STORAGESNAPSHOT")
public class StorageSnapshotBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	private String sdate = "";
	
	private String industryName = "";
	
	private String depotName = "";
	
	private String depotpartName = "";
	
	private String depotpartProp = "";
	
	private String storageName = "";
	
	private String productCode = "";
	
	private String productName = "";
	
	private int amount = 0;
	
	private double price = 0.0d;

	public StorageSnapshotBean()
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
	 * @return the sdate
	 */
	public String getSdate()
	{
		return sdate;
	}

	/**
	 * @param sdate the sdate to set
	 */
	public void setSdate(String sdate)
	{
		this.sdate = sdate;
	}

	/**
	 * @return the industryName
	 */
	public String getIndustryName()
	{
		return industryName;
	}

	/**
	 * @param industryName the industryName to set
	 */
	public void setIndustryName(String industryName)
	{
		this.industryName = industryName;
	}

	/**
	 * @return the depotName
	 */
	public String getDepotName()
	{
		return depotName;
	}

	/**
	 * @param depotName the depotName to set
	 */
	public void setDepotName(String depotName)
	{
		this.depotName = depotName;
	}

	/**
	 * @return the depotpartName
	 */
	public String getDepotpartName()
	{
		return depotpartName;
	}

	/**
	 * @param depotpartName the depotpartName to set
	 */
	public void setDepotpartName(String depotpartName)
	{
		this.depotpartName = depotpartName;
	}

	/**
	 * @return the depotpartProp
	 */
	public String getDepotpartProp()
	{
		return depotpartProp;
	}

	/**
	 * @param depotpartProp the depotpartProp to set
	 */
	public void setDepotpartProp(String depotpartProp)
	{
		this.depotpartProp = depotpartProp;
	}

	/**
	 * @return the storageName
	 */
	public String getStorageName()
	{
		return storageName;
	}

	/**
	 * @param storageName the storageName to set
	 */
	public void setStorageName(String storageName)
	{
		this.storageName = storageName;
	}

	/**
	 * @return the productCode
	 */
	public String getProductCode()
	{
		return productCode;
	}

	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode)
	{
		this.productCode = productCode;
	}

	/**
	 * @return the productName
	 */
	public String getProductName()
	{
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	/**
	 * @return the amount
	 */
	public int getAmount()
	{
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	/**
	 * @return the price
	 */
	public double getPrice()
	{
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price)
	{
		this.price = price;
	}
}
