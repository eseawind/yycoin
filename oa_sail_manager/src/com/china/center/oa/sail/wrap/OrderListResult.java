package com.china.center.oa.sail.wrap;

@SuppressWarnings("serial")
public class OrderListResult extends SingleOrderResult
{
	private String productCode = "";
	
	private String productName = "";
	
	private String picPath = "";
	
	public OrderListResult()
	{
		
	}

	public String getProductCode()
	{
		return productCode;
	}

	public void setProductCode(String productCode)
	{
		this.productCode = productCode;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public String getPicPath()
	{
		return picPath;
	}

	public void setPicPath(String picPath)
	{
		this.picPath = picPath;
	}
}
