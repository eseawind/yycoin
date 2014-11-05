package com.china.center.oa.customerservice.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@Entity
@Table(name = "T_CENTER_FEEDBACK_VISITITEM")
public class FeedBackVisitItemBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	@FK
	private String refId = "";
	
	/** 产品 */
	private String productId = "";
	
	private String productName = "";
	
	private int amount = 0;
	
	private double money = 0.0d;
	
	/** 是否收款 */
	private int receive = 0;
	
	/** 收货时间 */
	private String receiptTime = "";
	
	/** 是否有异常 */
	private int ifHasException = 0;
	
	/** 异常数量 */
	private int exceptionAmount = 0;
	
	/** 异常类型  231*/
	private int exceptionType = 0;
	
	/** 异常描述 */
	private String exceptionText = "";

	/**
	 * 
	 */
	public FeedBackVisitItemBean()
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

	public String getRefId()
	{
		return refId;
	}

	public void setRefId(String refId)
	{
		this.refId = refId;
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	public double getMoney()
	{
		return money;
	}

	public void setMoney(double money)
	{
		this.money = money;
	}

	public int getReceive()
	{
		return receive;
	}

	public void setReceive(int receive)
	{
		this.receive = receive;
	}

	public String getReceiptTime()
	{
		return receiptTime;
	}

	public void setReceiptTime(String receiptTime)
	{
		this.receiptTime = receiptTime;
	}

	public int getIfHasException()
	{
		return ifHasException;
	}

	public void setIfHasException(int ifHasException)
	{
		this.ifHasException = ifHasException;
	}

	public int getExceptionAmount()
	{
		return exceptionAmount;
	}

	public void setExceptionAmount(int exceptionAmount)
	{
		this.exceptionAmount = exceptionAmount;
	}

	public int getExceptionType()
	{
		return exceptionType;
	}

	public void setExceptionType(int exceptionType)
	{
		this.exceptionType = exceptionType;
	}

	public String getExceptionText()
	{
		return exceptionText;
	}

	public void setExceptionText(String exceptionText)
	{
		this.exceptionText = exceptionText;
	}
}
