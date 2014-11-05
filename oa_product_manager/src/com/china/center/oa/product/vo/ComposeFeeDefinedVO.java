/**
 * File Name: ComposeFeeDefinedVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-5-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.oa.product.bean.ComposeFeeDefinedBean;


/**
 * ComposeFeeDefinedVO
 * 
 * @author ZHUZHU
 * @version 2011-5-8
 * @see ComposeFeeDefinedVO
 * @since 3.0
 */
@Entity(inherit = true)
public class ComposeFeeDefinedVO extends ComposeFeeDefinedBean
{
    @Ignore
    private String taxName = "";

    /**
     * default constructor
     */
    public ComposeFeeDefinedVO()
    {
    }

    /**
     * @return the taxName
     */
    public String getTaxName()
    {
        return taxName;
    }

    /**
     * @param taxName
     *            the taxName to set
     */
    public void setTaxName(String taxName)
    {
        this.taxName = taxName;
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

        retValue.append("ComposeFeeDefinedVO ( ").append(super.toString()).append(TAB).append(
            "taxName = ").append(this.taxName).append(TAB).append(" )");

        return retValue.toString();
    }

}
