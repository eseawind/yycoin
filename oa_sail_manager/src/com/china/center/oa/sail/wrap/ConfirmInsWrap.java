package com.china.center.oa.sail.wrap;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ConfirmInsWrap implements Serializable
{
	/**
	 * 退货单号
	 * 采购付款申请单号
	 */
	private String fullId = "";
	
	private int outType = 0; 
	
	/**
	 * 发票类型
	 */
	private String invoiceId = "";
	
	private String invoiceName = "";
	
	/**
	 * 委托退货的原委托单
	 */
	private String origId = "";
	
	/**
	 * 可以核销的金额
	 */
	private double mayConfirmMoney = 0.0d;
	
	private String customerName = "";

	public ConfirmInsWrap()
	{
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
	 * @return the invoiceName
	 */
	public String getInvoiceName()
	{
		return invoiceName;
	}

	/**
	 * @param invoiceName the invoiceName to set
	 */
	public void setInvoiceName(String invoiceName)
	{
		this.invoiceName = invoiceName;
	}

	/**
	 * @return the origId
	 */
	public String getOrigId()
	{
		return origId;
	}

	/**
	 * @param origId the origId to set
	 */
	public void setOrigId(String origId)
	{
		this.origId = origId;
	}

	/**
	 * @return the mayConfirmMoney
	 */
	public double getMayConfirmMoney()
	{
		return mayConfirmMoney;
	}

	/**
	 * @param mayConfirmMoney the mayConfirmMoney to set
	 */
	public void setMayConfirmMoney(double mayConfirmMoney)
	{
		this.mayConfirmMoney = mayConfirmMoney;
	}

	/**
	 * @return the outType
	 */
	public int getOutType()
	{
		return outType;
	}

	/**
	 * @param outType the outType to set
	 */
	public void setOutType(int outType)
	{
		this.outType = outType;
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
}
