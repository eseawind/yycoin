package com.china.center.oa.sail.wrap;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class QueryPriceInput implements Serializable
{
	private String userId = "";
	
	private List<Wrap> products = null;
	
	public QueryPriceInput()
	{
		
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public List<Wrap> getProducts()
	{
		return products;
	}

	public void setProducts(List<Wrap> products)
	{
		this.products = products;
	}
	
	
}
