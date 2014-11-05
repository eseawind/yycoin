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
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.publics.constant.PublicConstant;


/**
 * ComposeItemBean
 * 
 * @author ZHUZHU
 * @version 2010-10-2
 * @see ComposeItemBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_COMPOSE_ITEM")
public class ComposeItemBean implements Serializable
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

    private String relationId = "";

    private int mtype = PublicConstant.MANAGER_TYPE_MANAGER;

    private int amount = 0;

    private double price = 0.0d;

    private String logTime = "";
    
    /**
     * 进项税率
     */
    private double inputRate = 0.0d;

    /**
     * 产品分拆时标明是库存还是费用
     */
    private int stype = ProductConstant.DECOMPOSE_PRODUCT_STORAGE;
    
    /**
     * default constructor
     */
    public ComposeItemBean()
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
     * @return the relationId
     */
    public String getRelationId()
    {
        return relationId;
    }

    /**
     * @param relationId
     *            the relationId to set
     */
    public void setRelationId(String relationId)
    {
        this.relationId = relationId;
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
     * @return the price
     */
    public double getPrice()
    {
        return price;
    }

    /**
     * @param price
     *            the price to set
     */
    public void setPrice(double price)
    {
        this.price = price;
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
     * @return the mtype
     */
    public int getMtype()
    {
        return mtype;
    }

    /**
     * @param mtype
     *            the mtype to set
     */
    public void setMtype(int mtype)
    {
        this.mtype = mtype;
    }

    /**
	 * @return the inputRate
	 */
	public double getInputRate()
	{
		return inputRate;
	}

	/**
	 * @param inputRate the inputRate to set
	 */
	public void setInputRate(double inputRate)
	{
		this.inputRate = inputRate;
	}

	/**
	 * @return the stype
	 */
	public int getStype()
	{
		return stype;
	}

	/**
	 * @param stype the stype to set
	 */
	public void setStype(int stype)
	{
		this.stype = stype;
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
            .append("ComposeItemBean ( ")
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
            .append("relationId = ")
            .append(this.relationId)
            .append(TAB)
            .append("mtype = ")
            .append(this.mtype)
            .append(TAB)
            .append("amount = ")
            .append(this.amount)
            .append(TAB)
            .append("price = ")
            .append(this.price)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
