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

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.product.bean.ProductBean;


/**
 * 虚拟产品和实际产品对应关系
 * 
 * @author ZHUZHU
 * @version 2010-8-15
 * @see ProductCombinationBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_VS_PRODUCT")
public class ProductCombinationBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @FK
    @Join(tagClass = ProductBean.class, alias = "p1")
    private String vproductId = "";

    @Join(tagClass = ProductBean.class, alias = "p2")
    private String sproductId = "";

    private String createrId = "";

    private int amount = 1;

    /**
     * default constructor
     */
    public ProductCombinationBean()
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
     * @return the vproductId
     */
    public String getVproductId()
    {
        return vproductId;
    }

    /**
     * @param vproductId
     *            the vproductId to set
     */
    public void setVproductId(String vproductId)
    {
        this.vproductId = vproductId;
    }

    /**
     * @return the sproductId
     */
    public String getSproductId()
    {
        return sproductId;
    }

    /**
     * @param sproductId
     *            the sproductId to set
     */
    public void setSproductId(String sproductId)
    {
        this.sproductId = sproductId;
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
     * @return the createrId
     */
    public String getCreaterId()
    {
        return createrId;
    }

    /**
     * @param createrId
     *            the createrId to set
     */
    public void setCreaterId(String createrId)
    {
        this.createrId = createrId;
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
            .append("ProductCombinationBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("vproductId = ")
            .append(this.vproductId)
            .append(TAB)
            .append("sproductId = ")
            .append(this.sproductId)
            .append(TAB)
            .append("createrId = ")
            .append(this.createrId)
            .append(TAB)
            .append("amount = ")
            .append(this.amount)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
