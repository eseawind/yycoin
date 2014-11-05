/**
 *
 */
package com.china.center.oa.product.vs;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;


/**
 * VS bean
 * 
 * @author Administrator
 */
@Entity
@Table(name = "T_CENTER_PRODUCTTYPEVSCUSTOMER")
public class ProductTypeVSCustomer implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    /**
     * 这里的type是固化的 $productType
     */
    @FK(index = 0)
    private String productTypeId = "";

    @FK(index = 1)
    private String customerId = "";

    /**
     *
     */
    public ProductTypeVSCustomer()
    {
    }

    /**
     * @return the productTypeId
     */
    public String getProductTypeId()
    {
        return productTypeId;
    }

    /**
     * @param productTypeId
     *            the productTypeId to set
     */
    public void setProductTypeId(String productTypeId)
    {
        this.productTypeId = productTypeId;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId()
    {
        return customerId;
    }

    /**
     * @param customerId
     *            the customerId to set
     */
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
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
}
