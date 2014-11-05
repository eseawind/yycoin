package com.china.center.oa.finance.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.InvoiceBean;

@SuppressWarnings("serial")
@Entity(name = "发票库存，财务导入")
@Table(name = "T_CENTER_INVOICESTORAGE")
public class InvoiceStorageBean implements Serializable
{
	@Id
	private String id = "";
	
	/**
	 * 业务员
	 */
	private String stafferId = "";
	
	private String stafferName = "";
	
	private String invoiceHead = "";
	
	private String invoiceCompany = "";
	
	/**
	 * 发票金额
	 */
	private double moneys = 0.0d;
	
	/**
	 * 已确认的发票金额
	 */
	private double hasConfirmMoneys = 0.0d;
	
	@Join(tagClass = InvoiceBean.class, type = JoinType.LEFT)
	private String invoiceId = "";
	
	private String invoiceNumber = "";
	
	private String invoiceDate = "";
	
	/**
	 * 供应商
	 */
	private String providerId = "";
	
	private String providerName = "";
	
	/**
	 * 不含税金额
	 */
	private double noTaxMoney = 0.0d;
	
	/**
	 * 含税金额
	 */
	private double taxMoney = 0.0d;
	
	/**
	 * 认证日期
	 */
	private String oprDate = "";
	
	/**
	 * 导入人
	 */
	private String oprName = "";
	
	/**
	 * 导入时间
	 */
	private String logTime = "";

	public InvoiceStorageBean()
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
	 * @return the invoiceHead
	 */
	public String getInvoiceHead()
	{
		return invoiceHead;
	}

	/**
	 * @param invoiceHead the invoiceHead to set
	 */
	public void setInvoiceHead(String invoiceHead)
	{
		this.invoiceHead = invoiceHead;
	}

	/**
	 * @return the invoiceCompany
	 */
	public String getInvoiceCompany()
	{
		return invoiceCompany;
	}

	/**
	 * @param invoiceCompany the invoiceCompany to set
	 */
	public void setInvoiceCompany(String invoiceCompany)
	{
		this.invoiceCompany = invoiceCompany;
	}

	/**
	 * @return the moneys
	 */
	public double getMoneys()
	{
		return moneys;
	}

	/**
	 * @param moneys the moneys to set
	 */
	public void setMoneys(double moneys)
	{
		this.moneys = moneys;
	}

	/**
	 * @return the hasConfirmMoneys
	 */
	public double getHasConfirmMoneys()
	{
		return hasConfirmMoneys;
	}

	/**
	 * @param hasConfirmMoneys the hasConfirmMoneys to set
	 */
	public void setHasConfirmMoneys(double hasConfirmMoneys)
	{
		this.hasConfirmMoneys = hasConfirmMoneys;
	}

	/**
	 * @return the invoiceId
	 */
	public String getInvoiceId()
	{
		return invoiceId;
	}

	/**
	 * @param invoiceId the invoiceId to set
	 */
	public void setInvoiceId(String invoiceId)
	{
		this.invoiceId = invoiceId;
	}

	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber()
	{
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber)
	{
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return the invoiceDate
	 */
	public String getInvoiceDate()
	{
		return invoiceDate;
	}

	/**
	 * @param invoiceDate the invoiceDate to set
	 */
	public void setInvoiceDate(String invoiceDate)
	{
		this.invoiceDate = invoiceDate;
	}

	/**
	 * @return the oprName
	 */
	public String getOprName()
	{
		return oprName;
	}

	/**
	 * @param oprName the oprName to set
	 */
	public void setOprName(String oprName)
	{
		this.oprName = oprName;
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
	 * @return the noTaxMoney
	 */
	public double getNoTaxMoney()
	{
		return noTaxMoney;
	}

	/**
	 * @param noTaxMoney the noTaxMoney to set
	 */
	public void setNoTaxMoney(double noTaxMoney)
	{
		this.noTaxMoney = noTaxMoney;
	}

	/**
	 * @return the taxMoney
	 */
	public double getTaxMoney()
	{
		return taxMoney;
	}

	/**
	 * @param taxMoney the taxMoney to set
	 */
	public void setTaxMoney(double taxMoney)
	{
		this.taxMoney = taxMoney;
	}

	/**
	 * @return the oprDate
	 */
	public String getOprDate()
	{
		return oprDate;
	}

	/**
	 * @param oprDate the oprDate to set
	 */
	public void setOprDate(String oprDate)
	{
		this.oprDate = oprDate;
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
	 * @return the providerName
	 */
	public String getProviderName()
	{
		return providerName;
	}

	/**
	 * @param providerName the providerName to set
	 */
	public void setProviderName(String providerName)
	{
		this.providerName = providerName;
	}
}
