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
import com.china.center.jdbc.annotation.Unique;
import com.china.center.oa.product.constant.StorageConstant;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * Gold & Silver(金银料出入库)
 * 
 * @author ZHUZHU
 * @version 2010-10-2
 * @see GSOutBean
 * @since 1.0
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_GSOUT")
public class GSOutBean implements Serializable
{
    @Id
    private String id = "";

    @Join(tagClass = StafferBean.class)
    private String stafferId = "";
    
    /**
     * 入库时,要关联原出库单(出库单须是审批结束,且只能被关联一次)
     */
    @Unique
    private String refId = "";

    /**
     * 0:出库 1: 入库
     */
    private int type = StorageConstant.STORAGEAPPLY_GS_OUT;
    
    private int status = StorageConstant.GSOUT_STATUS_SAVE;

    /**
     * 行项目金料克数*数量 之和
     */
    private int gtotal = 0;
    
    /**
     * 行项目银料克数*数量 之和
     */
    private int stotal = 0;

    private String outTime = "";
    
    private String logTime = "";
    
    private String description = "";
    
    @Ignore
    private List<GSOutItemBean> itemList = null;

    /**
     * default constructor
     */
    public GSOutBean()
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
	 * @return the refId
	 */
	public String getRefId()
	{
		return refId;
	}

	/**
	 * @param refId the refId to set
	 */
	public void setRefId(String refId)
	{
		this.refId = refId;
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
	 * @return the gtotal
	 */
	public int getGtotal()
	{
		return gtotal;
	}

	/**
	 * @param gtotal the gtotal to set
	 */
	public void setGtotal(int gtotal)
	{
		this.gtotal = gtotal;
	}

	/**
	 * @return the stotal
	 */
	public int getStotal()
	{
		return stotal;
	}

	/**
	 * @param stotal the stotal to set
	 */
	public void setStotal(int stotal)
	{
		this.stotal = stotal;
	}

	/**
	 * @return the outTime
	 */
	public String getOutTime()
	{
		return outTime;
	}

	/**
	 * @param outTime the outTime to set
	 */
	public void setOutTime(String outTime)
	{
		this.outTime = outTime;
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
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return the itemList
	 */
	public List<GSOutItemBean> getItemList()
	{
		return itemList;
	}

	/**
	 * @param itemList the itemList to set
	 */
	public void setItemList(List<GSOutItemBean> itemList)
	{
		this.itemList = itemList;
	}
}
