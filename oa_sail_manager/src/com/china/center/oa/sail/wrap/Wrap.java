package com.china.center.oa.sail.wrap;

import java.io.Serializable;

public class Wrap implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String productCode = "";
	
	private String productName = "";

	/**
	 * 
	 */
	public Wrap()
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
}
