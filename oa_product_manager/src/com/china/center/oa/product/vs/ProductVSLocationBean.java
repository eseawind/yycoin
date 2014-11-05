/**
 * File Name: ProductCombinationBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-15<br>
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
import com.china.center.jdbc.clone.DataClone;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.publics.bean.LocationBean;


/**
 * 产品可以在哪些区域出售
 * 
 * @author ZHUZHU
 * @version 2010-8-15
 * @see ProductVSLocationBean
 * @since 1.0
 */
@Entity(cache = true)
@Table(name = "T_CENTER_VS_PRODUCTLOCATION")
public class ProductVSLocationBean implements DataClone<ProductVSLocationBean>, Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @FK
    @Join(tagClass = ProductBean.class)
    private String productId = "";

    @FK(index = AnoConstant.FK_FIRST)
    @Join(tagClass = LocationBean.class)
    private String locationId = "";

    /**
     * Copy Constructor
     * 
     * @param productVSLocationBean
     *            a <code>ProductVSLocationBean</code> object
     */
    public ProductVSLocationBean(ProductVSLocationBean productVSLocationBean)
    {
        this.id = productVSLocationBean.id;
        this.productId = productVSLocationBean.productId;
        this.locationId = productVSLocationBean.locationId;
    }

    /**
     * default constructor
     */
    public ProductVSLocationBean()
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
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("ProductVSLocationBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("productId = ")
            .append(this.productId)
            .append(TAB)
            .append("locationId = ")
            .append(this.locationId)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

    public ProductVSLocationBean clones()
    {
        return new ProductVSLocationBean(this);
    }

}
