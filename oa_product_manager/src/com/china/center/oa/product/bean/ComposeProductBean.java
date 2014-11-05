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
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.product.constant.ComposeConstant;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;


/**
 * CHECK ComposeProductBean(产品合成)
 * 
 * @author ZHUZHU
 * @version 2010-10-2
 * @see ComposeProductBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_COMPOSE")
public class ComposeProductBean implements Serializable
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

    private String relationId = "";

    /**
     * 分解的时候,保存合成的ID
     */
    @FK
    private String refId = "";

    private int type = ComposeConstant.COMPOSE_TYPE_COMPOSE;

    private int mtype = PublicConstant.MANAGER_TYPE_MANAGER;

    private int amount = 0;

    private int status = ComposeConstant.STATUS_SUBMIT;

    private double price = 0.0d;

    private String logTime = "";
    
    /**
     * 金价
     */
    private double goldPrice = 0.0d;
    
    /**
     * 银价
     */
    private double silverPrice = 0.0d;

    /**
     * 总部核对信息
     */
    private String checks = "";

    private int checkStatus = PublicConstant.CHECK_STATUS_INIT;
    
    /**
     * 拆分时，存原单id
     */
    private String parentId = "";

    /**
     * 是否混合  1：表示原申请为混合，该单要拆分为三单
     */
    private int hybrid = 0;
    
    private String tag = "";

    @Ignore
    private List<ComposeItemBean> itemList = null;

    @Ignore
    private List<ComposeFeeBean> feeList = null;

    /**
     * default constructor
     */
    public ComposeProductBean()
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
     * @return the relationId
     */
    public String getRelationId()
    {
        return relationId;
    }

    /**
     * @param relationId
     *            the relationId to set
     */
    public void setRelationId(String relationId)
    {
        this.relationId = relationId;
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
     * @return the logTime
     */
    public String getLogTime()
    {
        return logTime;
    }

    /**
     * @param logTime
     *            the logTime to set
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
     * @param type
     *            the type to set
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
     * @param itemList
     *            the itemList to set
     */
    public void setItemList(List<ComposeItemBean> itemList)
    {
        this.itemList = itemList;
    }

    /**
     * @return the feeList
     */
    public List<ComposeFeeBean> getFeeList()
    {
        return feeList;
    }

    /**
     * @param feeList
     *            the feeList to set
     */
    public void setFeeList(List<ComposeFeeBean> feeList)
    {
        this.feeList = feeList;
    }

    /**
     * @return the status
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status)
    {
        this.status = status;
    }

    /**
     * @return the refId
     */
    public String getRefId()
    {
        return refId;
    }

    /**
     * @param refId
     *            the refId to set
     */
    public void setRefId(String refId)
    {
        this.refId = refId;
    }

    /**
     * @return the checks
     */
    public String getChecks()
    {
        return checks;
    }

    /**
     * @param checks
     *            the checks to set
     */
    public void setChecks(String checks)
    {
        this.checks = checks;
    }

    /**
     * @return the checkStatus
     */
    public int getCheckStatus()
    {
        return checkStatus;
    }

    /**
     * @param checkStatus
     *            the checkStatus to set
     */
    public void setCheckStatus(int checkStatus)
    {
        this.checkStatus = checkStatus;
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

    /**
	 * @return the goldPrice
	 */
	public double getGoldPrice()
	{
		return goldPrice;
	}

	/**
	 * @param goldPrice the goldPrice to set
	 */
	public void setGoldPrice(double goldPrice)
	{
		this.goldPrice = goldPrice;
	}

	/**
	 * @return the silverPrice
	 */
	public double getSilverPrice()
	{
		return silverPrice;
	}

	/**
	 * @param silverPrice the silverPrice to set
	 */
	public void setSilverPrice(double silverPrice)
	{
		this.silverPrice = silverPrice;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId()
	{
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}

	/**
	 * @return the hybrid
	 */
	public int getHybrid()
	{
		return hybrid;
	}

	/**
	 * @param hybrid the hybrid to set
	 */
	public void setHybrid(int hybrid)
	{
		this.hybrid = hybrid;
	}

	/**
	 * @return the tag
	 */
	public String getTag()
	{
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag)
	{
		this.tag = tag;
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
            .append("ComposeProductBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("productId = ")
            .append(this.productId)
            .append(TAB)
            .append("deportId = ")
            .append(this.deportId)
            .append(TAB)
            .append("depotpartId = ")
            .append(this.depotpartId)
            .append(TAB)
            .append("storageId = ")
            .append(this.storageId)
            .append(TAB)
            .append("relationId = ")
            .append(this.relationId)
            .append(TAB)
            .append("refId = ")
            .append(this.refId)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("mtype = ")
            .append(this.mtype)
            .append(TAB)
            .append("amount = ")
            .append(this.amount)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("price = ")
            .append(this.price)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("checks = ")
            .append(this.checks)
            .append(TAB)
            .append("checkStatus = ")
            .append(this.checkStatus)
            .append(TAB)
            .append("itemList = ")
            .append(this.itemList)
            .append(TAB)
            .append("feeList = ")
            .append(this.feeList)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
