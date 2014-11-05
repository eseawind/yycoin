package com.china.center.oa.sail.bean;

import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_STATSRANK")
public class StatsDeliveryRankBean implements Serializable
{
	@Id(autoIncrement = false)
	private String id = "";
	
	/**
	 * 批次，查询一次产生一个新的批次
	 */
	@FK
	private String batchId = "";
	
	/**
	 * 排名
	 */
	private int rank = 0;
	
	/**
	 * 客户
	 */
	private String customerId = "";
	
	private String customerName = "";
	
	private String outTime = "";
	
	private String productId = "";
	
	private String productName = "";
	
	/**
	 * 同一客户 同一日期 同一产品的数量之和
	 */
	private int amount = 0;
	
	/**
	 * 人工排名，排在前名
	 * 
	 * 0:no  1:yes
	 */
	private int humanSort = 0;
	
	/**
	 * 是否 按销售日期配送
	 * 0:no  1:yes
	 */
	private int baseOutTime = 0;
	
	/**
	 * 库存是否足够（多单明细计算之和）
	 * 1:足够
	 */
	private int enoughStock = 0;
	
	/**
	 * 是否处理完
	 * 1:完成 
	 * 
	 * 每天将未完成的数据清除
	 */
	private int hasFinish = 0;
	
	@Ignore
	private List<DeliveryRankVSOutBean> itemList = null;

	/**
	 * 
	 */
	public StatsDeliveryRankBean()
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
	 * @return the rank
	 */
	public int getRank()
	{
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(int rank)
	{
		this.rank = rank;
	}

	/**
	 * @return the customerId
	 */
	public String getCustomerId()
	{
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId)
	{
		this.customerId = customerId;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName()
	{
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
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
	 * @return the humanSort
	 */
	public int getHumanSort()
	{
		return humanSort;
	}

	/**
	 * @param humanSort the humanSort to set
	 */
	public void setHumanSort(int humanSort)
	{
		this.humanSort = humanSort;
	}

	/**
	 * @return the baseOutTime
	 */
	public int getBaseOutTime()
	{
		return baseOutTime;
	}

	/**
	 * @param baseOutTime the baseOutTime to set
	 */
	public void setBaseOutTime(int baseOutTime)
	{
		this.baseOutTime = baseOutTime;
	}

	/**
	 * @return the enoughStock
	 */
	public int getEnoughStock()
	{
		return enoughStock;
	}

	/**
	 * @param enoughStock the enoughStock to set
	 */
	public void setEnoughStock(int enoughStock)
	{
		this.enoughStock = enoughStock;
	}

	/**
	 * @return the batchId
	 */
	public String getBatchId()
	{
		return batchId;
	}

	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(String batchId)
	{
		this.batchId = batchId;
	}

	/**
	 * @return the hasFinish
	 */
	public int getHasFinish()
	{
		return hasFinish;
	}

	/**
	 * @param hasFinish the hasFinish to set
	 */
	public void setHasFinish(int hasFinish)
	{
		this.hasFinish = hasFinish;
	}

	/**
	 * @return the itemList
	 */
	public List<DeliveryRankVSOutBean> getItemList()
	{
		return itemList;
	}

	/**
	 * @param itemList the itemList to set
	 */
	public void setItemList(List<DeliveryRankVSOutBean> itemList)
	{
		this.itemList = itemList;
	}
}
