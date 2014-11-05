/**
 * File Name: ComposeProductBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.bean;


import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.product.constant.ComposeConstant;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;


/**
 * CHECK ComposeProductBean(产品分拆)
 * 
 * @author ZHUZHU
 * @version 2010-10-2
 * @see DecomposeProductBean
 * @since 1.0
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_DECOMPOSE")
public class DecomposeProductBean implements Serializable
{
    @Id
    private String id = "";

    @Join(tagClass = StafferBean.class)
    private String stafferId = "";

    @Join(tagClass = ProductBean.class)
    private String productId = "";

    @Join(tagClass = DepotBean.class)
    private String deportId = "";

    @Join(tagClass = DepotpartBean.class)
    private String depotpartId = "";

    @Join(tagClass = StorageBean.class, type = JoinType.LEFT)
    private String storageId = "";

    private int type = ComposeConstant.COMPOSE_TYPE_COMPOSE;
    
    private int mtype = PublicConstant.MANAGER_TYPE_MANAGER;

    private int amount = 0;

    private int status = ComposeConstant.STATUS_SUBMIT;

    private double price = 0.0d;

    private String logTime = "";
    
    @Ignore
    private List<ComposeItemBean> itemList = null;

    /**
     * default constructor
     */
    public DecomposeProductBean()
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
     * @return the stafferId
     */
    public String getStafferId()
    {
        return stafferId;
    }

    /**
     * @param stafferId
     *            the stafferId to set
     */
    public void setStafferId(String stafferId)
    {
        this.stafferId = stafferId;
    }

    /**
     * @return the productId
     */
    public String getProductId()
    {
        return productId;
    }

    /**
     * @param productId
     *            the productId to set
     */
    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    /**
     * @return the deportId
     */
    public String getDeportId()
    {
        return deportId;
    }

    /**
     * @param deportId
     *            the deportId to set
     */
    public void setDeportId(String deportId)
    {
        this.deportId = deportId;
    }

    /**
     * @return the depotpartId
     */
    public String getDepotpartId()
    {
        return depotpartId;
    }

    /**
     * @param depotpartId
     *            the depotpartId to set
     */
    public void setDepotpartId(String depotpartId)
    {
        this.depotpartId = depotpartId;
    }

    /**
     * @return the storageId
     */
    public String getStorageId()
    {
        return storageId;
    }

    /**
     * @param storageId
     *            the storageId to set
     */
    public void setStorageId(String storageId)
    {
        this.storageId = storageId;
    }

	/**
	 * @return the mtype
	 */
	public int getMtype()
	{
		return mtype;
	}

	/**
	 * @param mtype the mtype to set
	 */
	public void setMtype(int mtype)
	{
		this.mtype = mtype;
	}

	/**
	 * @return the amount
	 */
	public int getAmount()
	{
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	/**
	 * @return the status
	 */
	public int getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status)
	{
		this.status = status;
	}

	/**
	 * @return the price
	 */
	public double getPrice()
	{
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price)
	{
		this.price = price;
	}

	/**
	 * @return the logTime
	 */
	public String getLogTime()
	{
		return logTime;
	}

	/**
	 * @param logTime the logTime to set
	 */
	public void setLogTime(String logTime)
	{
		this.logTime = logTime;
	}

	/**
	 * @return the type
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type)
	{
		this.type = type;
	}

	/**
	 * @return the itemList
	 */
	public List<ComposeItemBean> getItemList()
	{
		return itemList;
	}

	/**
	 * @param itemList the itemList to set
	 */
	public void setItemList(List<ComposeItemBean> itemList)
	{
		this.itemList = itemList;
	}
}
