/**
 * File Name: StorageApplyVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-28<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.product.bean.StorageApplyBean;


/**
 * StorageApplyVO
 * 
 * @author ZHUZHU
 * @version 2010-10-28
 * @see StorageApplyVO
 * @since 1.0
 */
@Entity(inherit = true)
public class StorageApplyVO extends StorageApplyBean
{
    @Relationship(relationField = "applyer")
    private String applyName = "";

    @Relationship(relationField = "reveiver")
    private String reveiveName = "";

    @Relationship(relationField = "storageRelationId", tagField = "price")
    private double price = 0.0d;

    /**
     * default constructor
     */
    public StorageApplyVO()
    {
    }

    /**
     * @return the applyName
     */
    public String getApplyName()
    {
        return applyName;
    }

    /**
     * @param applyName
     *            the applyName to set
     */
    public void setApplyName(String applyName)
    {
        this.applyName = applyName;
    }

    /**
     * @return the reveiveName
     */
    public String getReveiveName()
    {
        return reveiveName;
    }

    /**
     * @param reveiveName
     *            the reveiveName to set
     */
    public void setReveiveName(String reveiveName)
    {
        this.reveiveName = reveiveName;
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
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("StorageApplyVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("applyName = ")
            .append(this.applyName)
            .append(TAB)
            .append("reveiveName = ")
            .append(this.reveiveName)
            .append(TAB)
            .append("price = ")
            .append(this.price)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
