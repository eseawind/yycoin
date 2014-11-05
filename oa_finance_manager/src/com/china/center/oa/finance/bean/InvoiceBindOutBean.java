package com.china.center.oa.finance.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity(name = "发票绑定单据")
@Table(name = "T_CENTER_INSBINDOUT")
public class InvoiceBindOutBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	private String invoiceStorageId = "";
	
	private String providerId = "";
	
	/**
	 * 销售、结算退货单
	 * 采购付款申请单
	 */
	@FK
	private String fullId = "";
	
	private int outtype = 0;
	
	private double confirmMoney = 0.0d;
	
	private String logTime = "";
	
	@Ignore
	private double tempMoney = 0.0d;
	
	@Ignore
	private int temp = 0;

	public InvoiceBindOutBean()
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
	 * @return the invoiceStorageId
	 */
	public String getInvoiceStorageId()
	{
		return invoiceStorageId;
	}

	/**
	 * @param invoiceStorageId the invoiceStorageId to set
	 */
	public void setInvoiceStorageId(String invoiceStorageId)
	{
		this.invoiceStorageId = invoiceStorageId;
	}

	/**
	 * @return the fullId
	 */
	public String getFullId()
	{
		return fullId;
	}

	/**
	 * @param fullId the fullId to set
	 */
	public void setFullId(String fullId)
	{
		this.fullId = fullId;
	}

	/**
	 * @return the outtype
	 */
	public int getOuttype()
	{
		return outtype;
	}

	/**
	 * @param outtype the outtype to set
	 */
	public void setOuttype(int outtype)
	{
		this.outtype = outtype;
	}

	/**
	 * @return the confirmMoney
	 */
	public double getConfirmMoney()
	{
		return confirmMoney;
	}

	/**
	 * @param confirmMoney the confirmMoney to set
	 */
	public void setConfirmMoney(double confirmMoney)
	{
		this.confirmMoney = confirmMoney;
	}

	/**
	 * @return the temp
	 */
	public int getTemp()
	{
		return temp;
	}

	/**
	 * @param temp the temp to set
	 */
	public void setTemp(int temp)
	{
		this.temp = temp;
	}

	/**
	 * @return the tempMoney
	 */
	public double getTempMoney()
	{
		return tempMoney;
	}

	/**
	 * @param tempMoney the tempMoney to set
	 */
	public void setTempMoney(double tempMoney)
	{
		this.tempMoney = tempMoney;
	}

	/**
	 * @return the providerId
	 */
	public String getProviderId()
	{
		return providerId;
	}

	/**
	 * @param providerId the providerId to set
	 */
	public void setProviderId(String providerId)
	{
		this.providerId = providerId;
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
}
