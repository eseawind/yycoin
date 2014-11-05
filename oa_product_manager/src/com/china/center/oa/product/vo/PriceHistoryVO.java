/**
 * File Name: PriceHistoryVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-5<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.product.bean.PriceHistoryBean;


/**
 * PriceHistoryVO
 * 
 * @author ZHUZHU
 * @version 2010-10-5
 * @see PriceHistoryVO
 * @since 1.0
 */
@Entity(inherit = true)
public class PriceHistoryVO extends PriceHistoryBean
{
    @Relationship(relationField = "productId")
    private String productName = "";

    /**
     * default constructor
     */
    public PriceHistoryVO()
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
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuffer retValue = new StringBuffer();

        retValue.append("PriceHistoryVO ( ").append(super.toString()).append(TAB).append("productName = ").append(
            this.productName).append(TAB).append(" )");

        return retValue.toString();
    }

}
