/**
 * File Name: ProductVSLocationVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.product.vs.ProductVSLocationBean;


/**
 * ProductVSLocationVO
 * 
 * @author ZHUZHU
 * @version 2010-8-21
 * @see ProductVSLocationVO
 * @since 1.0
 */
@Entity(inherit = true)
public class ProductVSLocationVO extends ProductVSLocationBean
{
    @Relationship(relationField = "productId")
    private String productName = "";

    @Relationship(relationField = "locationId")
    private String locationName = "";

    /**
     * default constructor
     */
    public ProductVSLocationVO()
    {
    }

    /**
     * @return the productName
     */
    public String getProductName()
    {
        return productName;
    }

    /**
     * @param productName
     *            the productName to set
     */
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    /**
     * @return the locationName
     */
    public String getLocationName()
    {
        return locationName;
    }

    /**
     * @param locationName
     *            the locationName to set
     */
    public void setLocationName(String locationName)
    {
        this.locationName = locationName;
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

        retValue.append("ProductVSLocationVO ( ").append(super.toString()).append(TAB).append("productName = ").append(
            this.productName).append(TAB).append("locationName = ").append(this.locationName).append(TAB).append(" )");

        return retValue.toString();
    }

}
