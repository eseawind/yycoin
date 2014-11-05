package com.china.center.oa.product.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.product.bean.ProductVSGiftBean;

@SuppressWarnings("serial")
@Entity(inherit = true)
public class ProductVSGiftVO extends ProductVSGiftBean
{
	@Relationship(relationField = "productId")
	private String productName = "";
	
	@Relationship(relationField = "giftProductId")
	private String giftProductName = "";

	public ProductVSGiftVO()
	{
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public String getGiftProductName()
	{
		return giftProductName;
	}

	public void setGiftProductName(String giftProductName)
	{
		this.giftProductName = giftProductName;
	}
}
