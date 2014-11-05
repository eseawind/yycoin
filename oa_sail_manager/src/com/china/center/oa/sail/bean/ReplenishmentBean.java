package com.china.center.oa.sail.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Table;

@Entity
@Table(name = "t_center_Replenishment")
public class ReplenishmentBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	private String productId = "";
	   
	private String productName = "";
	
	private String depotpartId = "";
	
	private String depotpartName = "";
	
	private double costPrice = 0.0d;
	
	private String costPriceKey = "";
	
	private String owner = "0";
	
	@Ignore
	private int amount = 0;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public String getDepotpartId()
	{
		return depotpartId;
	}

	public void setDepotpartId(String depotpartId)
	{
		this.depotpartId = depotpartId;
	}

	public double getCostPrice()
	{
		return costPrice;
	}

	public void setCostPrice(double costPrice)
	{
		this.costPrice = costPrice;
	}

	public String getCostPriceKey()
	{
		return costPriceKey;
	}

	public void setCostPriceKey(String costPriceKey)
	{
		this.costPriceKey = costPriceKey;
	}

	public String getOwner()
	{
		return owner;
	}

	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	public String getDepotpartName()
	{
		return depotpartName;
	}

	public void setDepotpartName(String depotpartName)
	{
		this.depotpartName = depotpartName;
	}
}
