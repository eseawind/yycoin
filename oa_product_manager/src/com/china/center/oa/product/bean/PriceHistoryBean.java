/**
 * File Name: PriceHistoryBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-5<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.product.constant.StorageConstant;


/**
 * PriceHistoryBean
 * 
 * @author ZHUZHU
 * @version 2010-10-5
 * @see PriceHistoryBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_PRICE_HIS")
public class PriceHistoryBean implements Serializable
{
    @Id
    private String id = "";

    @FK
    @Join(tagClass = ProductBean.class)
    private String productId = "";

    private double price = 0.0d;

    private String logTime = "";

    private int type = StorageConstant.OPR_STORAGE_INIT;

    /**
     * default constructor
     */
    public PriceHistoryBean()
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
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuffer retValue = new StringBuffer();

        retValue.append("PriceHistoryBean ( ").append(super.toString()).append(TAB).append("id = ").append(this.id).append(
            TAB).append("productId = ").append(this.productId).append(TAB).append("price = ").append(this.price).append(
            TAB).append("logTime = ").append(this.logTime).append(TAB).append("type = ").append(this.type).append(TAB).append(
            " )");

        return retValue.toString();
    }

}
