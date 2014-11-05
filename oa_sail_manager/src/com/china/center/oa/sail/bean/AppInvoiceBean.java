package com.china.center.oa.sail.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_APPINVOICE")
public class AppInvoiceBean implements Serializable
{
	@Id
	private String id = "";
	
	@Unique
	private String orderNo = "";
	
	private String invoiceHead = "";
	
	private String invoiceName = "";
	
	private double invoiceMoney = 0.0d;

	/**
	 * 
	 */
	public AppInvoiceBean()
	{
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getInvoiceHead()
	{
		return invoiceHead;
	}

	public void setInvoiceHead(String invoiceHead)
	{
		this.invoiceHead = invoiceHead;
	}

	public String getInvoiceName()
	{
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName)
	{
		this.invoiceName = invoiceName;
	}

	public double getInvoiceMoney()
	{
		return invoiceMoney;
	}

	public void setInvoiceMoney(double invoiceMoney)
	{
		this.invoiceMoney = invoiceMoney;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}
}
