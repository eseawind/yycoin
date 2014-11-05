package com.china.center.oa.sail.bean;

import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Table;

@Entity
@Table(name = "t_center_baserepaire")
public class BaseRepaireBean implements Serializable
{
	@Id
	private String id = "";
	
	/**
	 * 关联单据头
	 */
	@FK	
	private String refId = "";
	
	/**
	 * 销售单行项目
	 */
	private String baseId = "";
	
	/**
	 * 产品
	 */
	private String productId = "";
	
	/**
	 * 数量(不变)
	 */
	private int amount = 0 ;
	
	/**
	 * 新销售价
	 */
	private double price = 0.0d;
	
	/**
	 * 新销售金额
	 */
	private double value = 0.0d;
	
	private String description = "";
	
	private String productName = "";
	
	private String showName = "";
	
	/**
	 * 新开票品名
	 */
	private String showId = "";
	
	/**
	 * 新成本（业务员结算价）
	 */
	private double inputPrice = 0.0d;
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getRefId()
	{
		return refId;
	}

	public void setRefId(String refId)
	{
		this.refId = refId;
	}

	public String getBaseId()
	{
		return baseId;
	}

	public void setBaseId(String baseId)
	{
		this.baseId = baseId;
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public double getValue()
	{
		return value;
	}

	public void setValue(double value)
	{
		this.value = value;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public String getShowName()
	{
		return showName;
	}

	public void setShowName(String showName)
	{
		this.showName = showName;
	}

	public String getShowId()
	{
		return showId;
	}

	public void setShowId(String showId)
	{
		this.showId = showId;
	}

	public double getInputPrice()
	{
		return inputPrice;
	}

	public void setInputPrice(double inputPrice)
	{
		this.inputPrice = inputPrice;
	}

	public String toString()
	{

        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("BaseRepaireBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("refId = ")
            .append(this.refId)
            .append(TAB)
            .append("baseId = ")
            .append(this.baseId)
            .append(TAB)
            .append("productId = ")
            .append(this.productId)
            .append(TAB)
            .append("productName = ")
            .append(this.productName)
            .append(TAB)
            .append("showName = ")
            .append(this.showName)
            .append(TAB)
            .append("showId = ")
            .append(this.showId)            
            .append(TAB)
            .append("amount = ")
            .append(this.amount)            
            .append(TAB)
            .append("price = ")
            .append(this.price)            
            .append(TAB)
            .append("inputPrice = ")
            .append(this.inputPrice)            
            .append(TAB)
            .append("value = ")
            .append(this.value)            
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    
	}
}
