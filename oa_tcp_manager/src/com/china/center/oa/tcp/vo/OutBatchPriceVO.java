package com.china.center.oa.tcp.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.tcp.bean.OutBatchPriceBean;

@SuppressWarnings("serial")
@Entity(inherit = true)
public class OutBatchPriceVO extends OutBatchPriceBean
{
	@Relationship(relationField = "productId")
	private String productName = "";

	@Relationship(relationField = "industryId")
	private String industryName = "";
	
	/**
	 * 
	 */
	public OutBatchPriceVO()
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

	/**
	 * @return the industryName
	 */
	public String getIndustryName()
	{
		return industryName;
	}

	/**
	 * @param industryName the industryName to set
	 */
	public void setIndustryName(String industryName)
	{
		this.industryName = industryName;
	}
}
