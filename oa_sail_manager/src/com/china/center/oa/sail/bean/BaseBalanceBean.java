/**
 * File Name: OutBalanceBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.bean;


import java.io.Serializable;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.oa.publics.constant.PublicConstant;


/**
 * BaseBalanceBean(委托结算单项)
 * 
 * @author ZHUZHU
 * @version 2010-12-5
 * @see BaseBalanceBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_BASEBALANCE")
public class BaseBalanceBean implements Serializable
{
    @Id
    private String id = "";

    @FK
    @Join(tagClass = OutBalanceBean.class)
    private String parentId = "";

    @Unique(dependFields = "parentId")
    private String baseId = "";

    @FK(index = AnoConstant.FK_FIRST)
    private String outId = "";

    private double sailPrice = 0.0d;

    private int amount = 0;

    /**
     * 开发票的金额(已经开票的金额)
     */
    private double invoiceMoney = 0.0d;

    private int mtype = PublicConstant.MANAGER_TYPE_COMMON;
    
    /**
     * 
     */
    @Ignore
    private int inway = 0;

    @Ignore
    private String productName = "";
    
    /**
     * default constructor
     */
    public BaseBalanceBean()
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
     * @return the baseId
     */
    public String getBaseId()
    {
        return baseId;
    }

    /**
     * @param baseId
     *            the baseId to set
     */
    public void setBaseId(String baseId)
    {
        this.baseId = baseId;
    }

    /**
     * @return the outId
     */
    public String getOutId()
    {
        return outId;
    }

    /**
     * @param outId
     *            the outId to set
     */
    public void setOutId(String outId)
    {
        this.outId = outId;
    }

    /**
     * @return the sailPrice
     */
    public double getSailPrice()
    {
        return sailPrice;
    }

    /**
     * @param sailPrice
     *            the sailPrice to set
     */
    public void setSailPrice(double sailPrice)
    {
        this.sailPrice = sailPrice;
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
     * @return the invoiceMoney
     */
    public double getInvoiceMoney()
    {
        return invoiceMoney;
    }

    /**
     * @param invoiceMoney
     *            the invoiceMoney to set
     */
    public void setInvoiceMoney(double invoiceMoney)
    {
        this.invoiceMoney = invoiceMoney;
    }

    /**
     * @return the mtype
     */
    public int getMtype()
    {
        return mtype;
    }

    /**
     * @param mtype
     *            the mtype to set
     */
    public void setMtype(int mtype)
    {
        this.mtype = mtype;
    }

    public int getInway()
	{
		return inway;
	}

	public void setInway(int inway)
	{
		this.inway = inway;
	}

	public String getProductName()
	{
		return productName;
	}

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

        StringBuilder retValue = new StringBuilder();

        retValue.append("BaseBalanceBean ( ").append(super.toString()).append(TAB).append("id = ").append(this.id).append(
            TAB).append("parentId = ").append(this.parentId).append(TAB).append("baseId = ").append(this.baseId).append(
            TAB).append("outId = ").append(this.outId).append(TAB).append("sailPrice = ").append(this.sailPrice).append(
            TAB).append("amount = ").append(this.amount).append(TAB).append("invoiceMoney = ").append(this.invoiceMoney).append(
            TAB).append("mtype = ").append(this.mtype).append(TAB).append(" )");

        return retValue.toString();
    }

}
