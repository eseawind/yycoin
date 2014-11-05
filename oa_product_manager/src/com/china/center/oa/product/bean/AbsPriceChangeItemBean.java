/**
 * File Name: PriceChangeSrcItemBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * PriceChangeSrcItemBean
 * 
 * @author ZHUZHU
 * @version 2010-10-4
 * @see AbsPriceChangeItemBean
 * @since 1.0
 */
@Entity
public abstract class AbsPriceChangeItemBean implements Serializable
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

    private String refId = "";

    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    private String stafferId = "";

    private int amount = 0;

    private double price = 0.0d;

    /**
     * default constructor
     */
    public AbsPriceChangeItemBean()
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
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuffer retValue = new StringBuffer();

        retValue.append("AbsPriceChangeItemBean ( ").append(super.toString()).append(TAB).append("id = ").append(
            this.id).append(TAB).append("parentId = ").append(this.parentId).append(TAB).append("productId = ").append(
            this.productId).append(TAB).append("deportId = ").append(this.deportId).append(TAB).append("depotpartId = ").append(
            this.depotpartId).append(TAB).append("storageId = ").append(this.storageId).append(TAB).append(
            "relationId = ").append(this.relationId).append(TAB).append("refId = ").append(this.refId).append(TAB).append(
            "stafferId = ").append(this.stafferId).append(TAB).append("amount = ").append(this.amount).append(TAB).append(
            "price = ").append(this.price).append(TAB).append(" )");

        return retValue.toString();
    }

}
