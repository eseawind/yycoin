/**
 * File Name: ProductChangeWrap.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-25<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.wrap;


import java.io.Serializable;

import com.china.center.oa.product.constant.StorageConstant;


/**
 * ProductChangeWrap
 * 
 * @author ZHUZHU
 * @version 2010-8-25
 * @see ProductChangeWrap
 * @since 1.0
 */
public class ProductChangeWrap implements Serializable
{
    /**
     * 储位
     */
    private String storageId = "";

    /**
     * 仓区
     */
    private String depotpartId = "";

    private String productId = "";

    /**
     * 如果有relationId,优先级最高(有的话depotpartId,productId,price,stafferId)
     */
    private String relationId = "";

    private double price = 0.0d;

    /**
     * 一个操作的
     */
    private String serializeId = "";

    private String description = "";

    /**
     * 谁的产品 主要是部分产品库存只能是某个人的
     */
    private String stafferId = "0";

    /**
     * 单据ID(这里会作为联合主键哦)
     */
    private String refId = "";

    /**
     * 正数增加库存 负数减少库存
     */
    private int change = 0;

    private int type = StorageConstant.OPR_STORAGE_INIT;

    /**
     * 进项税率
     */
    private double inputRate = 0.0d;
    
    /**
     * default constructor
     */
    public ProductChangeWrap()
    {
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
     * @return the change
     */
    public int getChange()
    {
        return change;
    }

    /**
     * @param change
     *            the change to set
     */
    public void setChange(int change)
    {
        this.change = change;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
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
     * @return the serializeId
     */
    public String getSerializeId()
    {
        return serializeId;
    }

    /**
     * @param serializeId
     *            the serializeId to set
     */
    public void setSerializeId(String serializeId)
    {
        this.serializeId = serializeId;
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
     * @return the stafferId
     */
    public String getStafferId()
    {
        return stafferId;
    }

    /**
     * @param stafferId
     *            the stafferId to set
     */
    public void setStafferId(String stafferId)
    {
        this.stafferId = stafferId;
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
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuffer retValue = new StringBuffer();

        retValue
            .append("ProductChangeWrap ( ")
            .append(super.toString())
            .append(TAB)
            .append("storageId = ")
            .append(this.storageId)
            .append(TAB)
            .append("depotpartId = ")
            .append(this.depotpartId)
            .append(TAB)
            .append("productId = ")
            .append(this.productId)
            .append(TAB)
            .append("relationId = ")
            .append(this.relationId)
            .append(TAB)
            .append("price = ")
            .append(this.price)
            .append(TAB)
            .append("serializeId = ")
            .append(this.serializeId)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("change = ")
            .append(this.change)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

    /**
     * @return the refId
     */
    public String getRefId()
    {
        return refId;
    }

    /**
     * @param refId
     *            the refId to set
     */
    public void setRefId(String refId)
    {
        this.refId = refId;
    }
}
