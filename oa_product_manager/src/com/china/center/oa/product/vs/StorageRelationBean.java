/**
 * File Name: StorageRelationBean.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * CREATER: zhuAchen
 * CreateTime: 2008-1-19
 * Grant: open source to everybody
 */
package com.china.center.oa.product.vs;


import java.io.Serializable;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.bean.DepotpartBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.bean.StorageBean;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * 储位的关系(实际的产品库存表)(depotpartId, productId, priceKey, stafferId)
 * 
 * @author ZHUZHU
 * @version 2008-1-19
 * @see
 * @since
 */
@Entity(name = "储位的关系")
@Table(name = "T_CENTER_STORAGERALATION")
public class StorageRelationBean implements Serializable
{
    @Id
    private String id = "";

    @Join(tagClass = DepotpartBean.class)
    private String depotpartId = "";

    @FK
    @Join(tagClass = StorageBean.class, type = JoinType.LEFT)
    private String storageId = "";

    @FK(index = AnoConstant.FK_FIRST)
    @Join(tagClass = ProductBean.class)
    private String productId = "";

    @FK(index = AnoConstant.FK_SECOND)
    @Join(tagClass = DepotBean.class)
    private String locationId = "";

    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    private String stafferId = "";

    private String priceKey = "";

    private int amount = 0;

    private double price = 0.0d;

    private double lastPrice = 0.0d;
    
    /**
     * 进项税率
     */
    private double inputRate = 0.0d;

    /**
     * default constructor
     */
    public StorageRelationBean()
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
     * @return the locationId
     */
    public String getLocationId()
    {
        return locationId;
    }

    /**
     * @param locationId
     *            the locationId to set
     */
    public void setLocationId(String locationId)
    {
        this.locationId = locationId;
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
     * @return the priceKey
     */
    public String getPriceKey()
    {
        return priceKey;
    }

    /**
     * @param priceKey
     *            the priceKey to set
     */
    public void setPriceKey(String priceKey)
    {
        this.priceKey = priceKey;
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
     * @return the lastPrice
     */
    public double getLastPrice()
    {
        return lastPrice;
    }

    /**
     * @param lastPrice
     *            the lastPrice to set
     */
    public void setLastPrice(double lastPrice)
    {
        this.lastPrice = lastPrice;
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

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("StorageRelationBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("depotpartId = ")
            .append(this.depotpartId)
            .append(TAB)
            .append("storageId = ")
            .append(this.storageId)
            .append(TAB)
            .append("productId = ")
            .append(this.productId)
            .append(TAB)
            .append("locationId = ")
            .append(this.locationId)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("priceKey = ")
            .append(this.priceKey)
            .append(TAB)
            .append("amount = ")
            .append(this.amount)
            .append(TAB)
            .append("price = ")
            .append(this.price)
            .append(TAB)
            .append("lastPrice = ")
            .append(this.lastPrice)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
