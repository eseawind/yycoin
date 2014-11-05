package com.china.center.oa.commission.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.commission.bean.BlackOutDetailBean;

@SuppressWarnings("serial")
@Entity(inherit = true)
public class BlackOutDetailVO extends BlackOutDetailBean
{
	@Relationship(relationField = "productId")
	private String productName = "";

	/**
	 * 
	 */
	public BlackOutDetailVO()
	{
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
}
