package com.china.center.oa.stock.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.oa.stock.constant.StockConstant;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_STOCK_WORK")
public class StockWorkBean implements Serializable
{
	@Id
	private String id = "";
	
	/**
	 * 跟单状态
	 * 0:待处理 1：处理结束
	 */
	private int status = 0;
	
	/**
	 * 跟单时间
	 */
	private String workDate = "";
	
	/**
	 * 操作时间
	 */
	private String logTime = "";
	
	/**
	 * 采购目标
	 */
	private String target = "";
	
	/**
	 * 采购申请人
	 */
	private String stafferId = "";
	
	/**
	 * 采购申请人
	 */
	private String stafferName = "";
	
	/**
	 * 采购单号
	 */
	@FK
	private String stockId = "";
	
	/**
	 * 采购 行项目 ID
	 */
	private String stockItemId = "";
	
	/**
	 * 采购商品
	 */
	private String productId = "";
	
	/**
	 * 采购商品
	 */
	private String productName = "";
	
	/**
	 * 供应商
	 */
	private String provideId = "";
	
	/**
	 * 供应商
	 */
	private String provideName = "";
	
	/**
	 * 到货日期
	 */
	private String needTime = "";
	
	/**
	 * 最新确认发货时间(取采购单关联的跟单任务中的最新交货时间)
	 * ref this.sendDate
	 */
	private String newSendDate = "";
	
	/**
	 * 供应商确认时间
	 */
	private String provideConfirmDate = "";
	
	/**
	 * 确认发货时间
	 */
	private String confirmSendDate = "";
	
	/**
	 * 供应商联系人
	 */
	@Html(title = "供应商联系人", must = true, maxLength = 40)
	private String connector = "";
	
	/**
	 * 跟单途径
	 * e.g QQ、固话、手机、邮件、传真
	 */
	@Html(title = "跟单途径", must = true, type = Element.SELECT)
	private int way = StockConstant.STOCKWORK_WAY_QQ;
	
	/**
	 * 是否按计划继续 check
	 */
	@Html(title = "是否按计划继续", must = true, type = Element.SELECT)
	private int followPlan = 0;
	
	/**
	 * 交期
	 */
	private int deliveryDate = 0;
	
	/**
	 * 工艺
	 */
	private int technology = 0;
	
	/**
	 * 付款方式
	 */
	private int pay = 0;
	
	/**
	 * 发货时间
	 */
	@Html(title = "发货时间", type = Element.DATE)
	private String sendDate = "";
	
	/**
	 * 任务单处理时，如果 选择了交期，则sendDate为新的发货日期
	 * 同时，再次生成任务单时，最新确认发货时间为 isNew = 1 对应的sendDate
	 */
	private int isNew = 0;
	
	/**
	 * 备注
	 */
	@Html(title = "备注", type = Element.TEXTAREA, maxLength = 200)
	private String description = "";

	/**
	 * 
	 */
	public StockWorkBean()
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
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
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
	 * @return the workDate
	 */
	public String getWorkDate()
	{
		return workDate;
	}

	/**
	 * @param workDate the workDate to set
	 */
	public void setWorkDate(String workDate)
	{
		this.workDate = workDate;
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
	 * @return the target
	 */
	public String getTarget()
	{
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target)
	{
		this.target = target;
	}

	/**
	 * @return the stafferId
	 */
	public String getStafferId()
	{
		return stafferId;
	}

	/**
	 * @param stafferId the stafferId to set
	 */
	public void setStafferId(String stafferId)
	{
		this.stafferId = stafferId;
	}

	/**
	 * @return the stafferName
	 */
	public String getStafferName()
	{
		return stafferName;
	}

	/**
	 * @param stafferName the stafferName to set
	 */
	public void setStafferName(String stafferName)
	{
		this.stafferName = stafferName;
	}

	/**
	 * @return the stockId
	 */
	public String getStockId()
	{
		return stockId;
	}

	/**
	 * @param stockId the stockId to set
	 */
	public void setStockId(String stockId)
	{
		this.stockId = stockId;
	}

	/**
	 * @return the stockItemId
	 */
	public String getStockItemId()
	{
		return stockItemId;
	}

	/**
	 * @param stockItemId the stockItemId to set
	 */
	public void setStockItemId(String stockItemId)
	{
		this.stockItemId = stockItemId;
	}

	/**
	 * @return the productId
	 */
	public String getProductId()
	{
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	/**
	 * @return the productName
	 */
	public String getProductName()
	{
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	/**
	 * @return the provideId
	 */
	public String getProvideId()
	{
		return provideId;
	}

	/**
	 * @param provideId the provideId to set
	 */
	public void setProvideId(String provideId)
	{
		this.provideId = provideId;
	}

	/**
	 * @return the provideName
	 */
	public String getProvideName()
	{
		return provideName;
	}

	/**
	 * @param provideName the provideName to set
	 */
	public void setProvideName(String provideName)
	{
		this.provideName = provideName;
	}

	/**
	 * @return the needTime
	 */
	public String getNeedTime()
	{
		return needTime;
	}

	/**
	 * @param needTime the needTime to set
	 */
	public void setNeedTime(String needTime)
	{
		this.needTime = needTime;
	}

	/**
	 * @return the newSendDate
	 */
	public String getNewSendDate()
	{
		return newSendDate;
	}

	/**
	 * @param newSendDate the newSendDate to set
	 */
	public void setNewSendDate(String newSendDate)
	{
		this.newSendDate = newSendDate;
	}

	/**
	 * @return the provideConfirmDate
	 */
	public String getProvideConfirmDate()
	{
		return provideConfirmDate;
	}

	/**
	 * @param provideConfirmDate the provideConfirmDate to set
	 */
	public void setProvideConfirmDate(String provideConfirmDate)
	{
		this.provideConfirmDate = provideConfirmDate;
	}

	/**
	 * @return the confirmSendDate
	 */
	public String getConfirmSendDate()
	{
		return confirmSendDate;
	}

	/**
	 * @param confirmSendDate the confirmSendDate to set
	 */
	public void setConfirmSendDate(String confirmSendDate)
	{
		this.confirmSendDate = confirmSendDate;
	}

	/**
	 * @return the connector
	 */
	public String getConnector()
	{
		return connector;
	}

	/**
	 * @param connector the connector to set
	 */
	public void setConnector(String connector)
	{
		this.connector = connector;
	}

	/**
	 * @return the way
	 */
	public int getWay()
	{
		return way;
	}

	/**
	 * @param way the way to set
	 */
	public void setWay(int way)
	{
		this.way = way;
	}

	/**
	 * @return the followPlan
	 */
	public int getFollowPlan()
	{
		return followPlan;
	}

	/**
	 * @param followPlan the followPlan to set
	 */
	public void setFollowPlan(int followPlan)
	{
		this.followPlan = followPlan;
	}

	/**
	 * @return the sendDate
	 */
	public String getSendDate()
	{
		return sendDate;
	}

	/**
	 * @param sendDate the sendDate to set
	 */
	public void setSendDate(String sendDate)
	{
		this.sendDate = sendDate;
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
	 * @return the deliveryDate
	 */
	public int getDeliveryDate()
	{
		return deliveryDate;
	}

	/**
	 * @param deliveryDate the deliveryDate to set
	 */
	public void setDeliveryDate(int deliveryDate)
	{
		this.deliveryDate = deliveryDate;
	}

	/**
	 * @return the technology
	 */
	public int getTechnology()
	{
		return technology;
	}

	/**
	 * @param technology the technology to set
	 */
	public void setTechnology(int technology)
	{
		this.technology = technology;
	}

	/**
	 * @return the pay
	 */
	public int getPay()
	{
		return pay;
	}

	/**
	 * @param pay the pay to set
	 */
	public void setPay(int pay)
	{
		this.pay = pay;
	}

	/**
	 * @return the isNew
	 */
	public int getIsNew()
	{
		return isNew;
	}

	/**
	 * @param isNew the isNew to set
	 */
	public void setIsNew(int isNew)
	{
		this.isNew = isNew;
	}
}
