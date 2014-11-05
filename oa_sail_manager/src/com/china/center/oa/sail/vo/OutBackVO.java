package com.china.center.oa.sail.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.sail.bean.OutBackBean;

@SuppressWarnings("serial")
@Entity(inherit = true)
public class OutBackVO extends OutBackBean
{
	@Relationship(relationField = "fromProvince")
	private String fromProvinceName = "";
	
	@Relationship(relationField = "fromCity")
	private String fromCityName = "";
	
	@Relationship(relationField = "toProvince")
	private String toProvinceName = "";
	
	@Relationship(relationField = "toCity")
	private String toCityName = "";
	
	@Relationship(relationField = "customerId")
	private String customerName = "";
	
	@Relationship(relationField = "expressCompany")
	private String expressCompanyName = "";

	@Ignore
	private String fromFullAddress = "";
	
	@Ignore
	private String toFullAddress = "";
	
	public OutBackVO()
	{
	}

	/**
	 * @return the fromProvinceName
	 */
	public String getFromProvinceName()
	{
		return fromProvinceName;
	}

	/**
	 * @param fromProvinceName the fromProvinceName to set
	 */
	public void setFromProvinceName(String fromProvinceName)
	{
		this.fromProvinceName = fromProvinceName;
	}

	/**
	 * @return the fromCityName
	 */
	public String getFromCityName()
	{
		return fromCityName;
	}

	/**
	 * @param fromCityName the fromCityName to set
	 */
	public void setFromCityName(String fromCityName)
	{
		this.fromCityName = fromCityName;
	}

	/**
	 * @return the toProvinceName
	 */
	public String getToProvinceName()
	{
		return toProvinceName;
	}

	/**
	 * @param toProvinceName the toProvinceName to set
	 */
	public void setToProvinceName(String toProvinceName)
	{
		this.toProvinceName = toProvinceName;
	}

	/**
	 * @return the toCityName
	 */
	public String getToCityName()
	{
		return toCityName;
	}

	/**
	 * @param toCityName the toCityName to set
	 */
	public void setToCityName(String toCityName)
	{
		this.toCityName = toCityName;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName()
	{
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	/**
	 * @return the expressCompanyName
	 */
	public String getExpressCompanyName()
	{
		return expressCompanyName;
	}

	/**
	 * @param expressCompanyName the expressCompanyName to set
	 */
	public void setExpressCompanyName(String expressCompanyName)
	{
		this.expressCompanyName = expressCompanyName;
	}

	/**
	 * @return the fromFullAddress
	 */
	public String getFromFullAddress()
	{
		return fromFullAddress;
	}

	/**
	 * @param fromFullAddress the fromFullAddress to set
	 */
	public void setFromFullAddress(String fromFullAddress)
	{
		this.fromFullAddress = fromFullAddress;
	}

	/**
	 * @return the toFullAddress
	 */
	public String getToFullAddress()
	{
		return toFullAddress;
	}

	/**
	 * @param toFullAddress the toFullAddress to set
	 */
	public void setToFullAddress(String toFullAddress)
	{
		this.toFullAddress = toFullAddress;
	}
}
