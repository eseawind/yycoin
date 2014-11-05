/**
 * File Name: ComposeItemBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;


/**
 * GSOutItemBean
 * 
 * @author ZHUZHU
 * @version 2010-10-2
 * @see GSOutItemBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_GSOUT_ITEM")
public class GSOutItemBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @FK
    private String parentId = "";

    @Join(tagClass = ProductBean.class)
    private String productId = "";

    @Join(tagClass = DepotBean.class, type = JoinType.LEFT)
    private String deportId = "";

    @Join(tagClass = DepotpartBean.class, type = JoinType.LEFT)
    private String depotpartId = "";

    @Join(tagClass = StorageBean.class, type = JoinType.LEFT)
    private String storageId = "";

    private int amount = 0;

    private int goldWeight = 0;
    
    private double goldPrice = 0.0d;
    
    private int silverWeight = 0;
    
    private double silverPrice = 0.0d;
    
    private double price = 0.0d;

    private String logTime = "";
    
    /**
     * default constructor
     */
    public GSOutItemBean()
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
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the parentId
     */
    public String getParentId()
    {
        return parentId;
    }

    /**
     * @param parentId
     *            the parentId to set
     */
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    /**
     * @return the productId
     */
    public String getProductId()
    {
        return productId;
    }

    /**
     * @param productId
     *            the productId to set
     */
    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    /**
     * @return the deportId
     */
    public String getDeportId()
    {
        return deportId;
    }

    /**
     * @param deportId
     *            the deportId to set
     */
    public void setDeportId(String deportId)
    {
        this.deportId = deportId;
    }

    /**
     * @return the depotpartId
     */
    public String getDepotpartId()
    {
        return depotpartId;
    }

    /**
     * @param depotpartId
     *            the depotpartId to set
     */
    public void setDepotpartId(String depotpartId)
    {
        this.depotpartId = depotpartId;
    }

    /**
     * @return the storageId
     */
    public String getStorageId()
    {
        return storageId;
    }

    /**
     * @param storageId
     *            the storageId to set
     */
    public void setStorageId(String storageId)
    {
        this.storageId = storageId;
    }

    /**
     * @return the amount
     */
    public int getAmount()
    {
        return amount;
    }

    /**
     * @param amount
     *            the amount to set
     */
    public void setAmount(int amount)
    {
        this.amount = amount;
    }

	/**
	 * @return the goldWeight
	 */
	public int getGoldWeight()
	{
		return goldWeight;
	}

	/**
	 * @param goldWeight the goldWeight to set
	 */
	public void setGoldWeight(int goldWeight)
	{
		this.goldWeight = goldWeight;
	}

	/**
	 * @return the silverWeight
	 */
	public int getSilverWeight()
	{
		return silverWeight;
	}

	/**
	 * @param silverWeight the silverWeight to set
	 */
	public void setSilverWeight(int silverWeight)
	{
		this.silverWeight = silverWeight;
	}

	/**
     * @return the logTime
     */
    public String getLogTime()
    {
        return logTime;
    }

    /**
     * @param logTime
     *            the logTime to set
     */
    public void setLogTime(String logTime)
    {
        this.logTime = logTime;
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

	/**
	 * @return the goldPrice
	 */
	public double getGoldPrice()
	{
		return goldPrice;
	}

	/**
	 * @param goldPrice the goldPrice to set
	 */
	public void setGoldPrice(double goldPrice)
	{
		this.goldPrice = goldPrice;
	}

	/**
	 * @return the silverPrice
	 */
	public double getSilverPrice()
	{
		return silverPrice;
	}

	/**
	 * @param silverPrice the silverPrice to set
	 */
	public void setSilverPrice(double silverPrice)
	{
		this.silverPrice = silverPrice;
	}

	/**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("GSOutItemBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("parentId = ")
            .append(this.parentId)
            .append(TAB)
            .append("productId = ")
            .append(this.productId)
            .append(TAB)
            .append("deportId = ")
            .append(this.deportId)
            .append(TAB)
            .append("depotpartId = ")
            .append(this.depotpartId)
            .append(TAB)
            .append("storageId = ")
            .append(this.storageId)
            .append(TAB)
            .append("amount = ")
            .append(this.amount)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
