/**
 * File Name: ProductTypeVSCustomerVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-1-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.oa.product.vs.ProductTypeVSCustomer;


/**
 * ProductTypeVSCustomerVO
 * 
 * @author ZHUZHU
 * @version 2010-1-3
 * @see ProductTypeVSCustomerVO
 * @since 1.0
 */
@Entity(inherit = true)
public class ProductTypeVSCustomerVO extends ProductTypeVSCustomer
{
    /**
     * productTypeName
     */
    @Ignore
    private String productTypeName = "";

    /**
     * default constructor
     */
    public ProductTypeVSCustomerVO()
    {
    }

    /**
     * @return the productTypeName
     */
    public String getProductTypeName()
    {
        return productTypeName;
    }

    /**
     * @param productTypeName
     *            the productTypeName to set
     */
    public void setProductTypeName(String productTypeName)
    {
        this.productTypeName = productTypeName;
    }
}
