package com.china.center.oa.product.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_PRODUCTBOM")
public class ProductBOMBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	@FK
	@Join(tagClass = ProductBean.class, alias = "ProductBean1")
	private String productId = "";
	
	@Join(tagClass = ProductBean.class, alias = "ProductBean2")
	private String subProductId = "";

	public ProductBOMBean()
	{
	}

	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return the productId
	 */
	public String getProductId()
	{
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	/**
	 * @return the subProductId
	 */
	public String getSubProductId()
	{
		return subProductId;
	}

	/**
	 * @param subProductId the subProductId to set
	 */
	public void setSubProductId(String subProductId)
	{
		this.subProductId = subProductId;
	}
}
