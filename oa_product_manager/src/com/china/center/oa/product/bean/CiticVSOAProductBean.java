package com.china.center.oa.product.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;

@Entity
@Table(name = "t_center_vs_citic_product")
public class CiticVSOAProductBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
   
	private String productCode = "";
   
	private String productName = "";

	@Unique(dependFields = "firstName")
	private String citicProductCode = "";
   
	private String citicProductName = "";
	
	private String firstName = "";

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
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

	public String getCiticProductCode()
	{
		return citicProductCode;
	}

	public void setCiticProductCode(String citicProductCode)
	{
		this.citicProductCode = citicProductCode;
	}

	public String getCiticProductName()
	{
		return citicProductName;
	}

	public void setCiticProductName(String citicProductName)
	{
		this.citicProductName = citicProductName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
}
