/**
 * File Name: ComposeFeeVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.product.bean.ComposeFeeBean;


/**
 * ComposeFeeVO
 * 
 * @author ZHUZHU
 * @version 2010-10-2
 * @see ComposeFeeVO
 * @since 1.0
 */
@Entity(inherit = true)
public class ComposeFeeVO extends ComposeFeeBean
{
    @Relationship(relationField = "feeItemId")
    private String feeItemName = "";

    /**
     * default constructor
     */
    public ComposeFeeVO()
    {
    }

    /**
     * @return the feeItemName
     */
    public String getFeeItemName()
    {
        return feeItemName;
    }

    /**
     * @param feeItemName
     *            the feeItemName to set
     */
    public void setFeeItemName(String feeItemName)
    {
        this.feeItemName = feeItemName;
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

        retValue.append("ComposeFeeVO ( ").append(super.toString()).append(TAB).append(
            "feeItemName = ").append(this.feeItemName).append(TAB).append(" )");

        return retValue.toString();
    }

}
