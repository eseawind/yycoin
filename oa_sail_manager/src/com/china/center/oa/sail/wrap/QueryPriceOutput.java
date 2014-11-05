package com.china.center.oa.sail.wrap;


@SuppressWarnings("serial")
public class QueryPriceOutput extends Wrap
{
	private String  price = "0.00";
	
	public QueryPriceOutput()
	{
	}

	/**
	 * @return the price
	 */
	public String getPrice()
	{
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(String price)
	{
		this.price = price;
	}

}
