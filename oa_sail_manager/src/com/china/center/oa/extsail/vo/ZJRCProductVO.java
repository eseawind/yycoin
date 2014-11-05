package com.china.center.oa.extsail.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.extsail.bean.ZJRCProductBean;

@SuppressWarnings("serial")
@Entity(inherit = true)
public class ZJRCProductVO extends ZJRCProductBean
{
	@Relationship(relationField = "productId")
	private String productName = "";

	public ZJRCProductVO()
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
