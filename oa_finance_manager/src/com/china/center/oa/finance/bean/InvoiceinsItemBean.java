/**
 * File Name: InvoiceinsItemBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.product.bean.ProductBean;


/**
 * InvoiceinsItemBean
 * 
 * @author ZHUZHU
 * @version 2010-12-26
 * @see InvoiceinsItemBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_INVOICEINS_ITEM")
public class InvoiceinsItemBean implements Serializable
{
    @Id
    private String id = "";

    @FK
    private String parentId = "";

    private String showId = "";

    private String showName = "";

    /**
     * 规格
     */
    private String special = "";

    /**
     * 单位
     */
    private String unit = "";

    /**
     * 产品数量
     */
    private int amount = 0;

    /**
     * 单价
     */
    private double price = 0.0d;

    /**
     * 总价
     */
    private double moneys = 0.0d;
    
    /**
     * 销售单号
     */
    private String outId = "";
    
    /**
     * 销售项目ID
     */
    private String baseId = "";
    
    /**
     * 产品
     */
    @Join(tagClass = ProductBean.class, type = JoinType.LEFT)
    private String productId = "";
    
    /**
     * 类型  0：销售单 1：结算单
     */
    private int type = 0;
    
    /**
     * 原始成本
     */
    private double costPrice = 0.0d;
    
    /**
     * default constructor
     */
    public InvoiceinsItemBean()
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
     * @return the showId
     */
    public String getShowId()
    {
        return showId;
    }

    /**
     * @param showId
     *            the showId to set
     */
    public void setShowId(String showId)
    {
        this.showId = showId;
    }

    /**
     * @return the showName
     */
    public String getShowName()
    {
        return showName;
    }

    /**
     * @param showName
     *            the showName to set
     */
    public void setShowName(String showName)
    {
        this.showName = showName;
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
     * @return the moneys
     */
    public double getMoneys()
    {
        return moneys;
    }

    /**
     * @param moneys
     *            the moneys to set
     */
    public void setMoneys(double moneys)
    {
        this.moneys = moneys;
    }

    /**
     * @return the special
     */
    public String getSpecial()
    {
        return special;
    }

    /**
     * @param special
     *            the special to set
     */
    public void setSpecial(String special)
    {
        this.special = special;
    }

    /**
     * @return the unit
     */
    public String getUnit()
    {
        return unit;
    }

    /**
     * @param unit
     *            the unit to set
     */
    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getOutId()
	{
		return outId;
	}

	public void setOutId(String outId)
	{
		this.outId = outId;
	}

	public String getBaseId()
	{
		return baseId;
	}

	public void setBaseId(String baseId)
	{
		this.baseId = baseId;
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}
	
	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public double getCostPrice()
	{
		return costPrice;
	}

	public void setCostPrice(double costPrice)
	{
		this.costPrice = costPrice;
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
            .append("InvoiceinsItemBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("parentId = ")
            .append(this.parentId)
            .append(TAB)
            .append("showId = ")
            .append(this.showId)
            .append(TAB)
            .append("showName = ")
            .append(this.showName)
            .append(TAB)
            .append("special = ")
            .append(this.special)
            .append(TAB)
            .append("unit = ")
            .append(this.unit)
            .append(TAB)
            .append("amount = ")
            .append(this.amount)
            .append(TAB)
            .append("price = ")
            .append(this.price)
            .append(TAB)
            .append("moneys = ")
            .append(this.moneys)
            .append(TAB)
            .append("outId = ")
            .append(this.outId)
            .append(TAB)
            .append("baseId = ")
            .append(this.baseId)
            .append(TAB)
            .append("productId = ")
            .append(this.productId)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
