package com.china.center.oa.client.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.client.bean.AddressBean;

@Entity(inherit = true)
public class AddressVO extends AddressBean 
{
	/**
	 * 省
	 */
	@Relationship(relationField = "provinceId")
	private String provinceName = "";
	
	/**
	 * 市
	 */
	@Relationship(relationField = "cityId")	
	private String cityName = "";
	
	/***
	 * 区
	 */
	@Relationship(relationField = "areaId")
	private String areaName = "";
	
	/**
	 * 完整的地址
	 */
	@Ignore
	private String fullAddress = "";
	
	public AddressVO(){}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getFullAddress()
	{
		return fullAddress;
	}

	public void setFullAddress(String fullAddress)
	{
		this.fullAddress = this.getProvinceName() + " " + this.getCityName() + " " + this.getAreaName() + " " + this.getAddress();
	}
	
	/**
	 * @return the areaName
	 */
	public String getAreaName()
	{
		return areaName;
	}

	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName)
	{
		this.areaName = areaName;
	}

	public String toString()
	{
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("AddressVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("provinceName = ")
            .append(this.provinceName)
            .append(TAB)
            .append("cityName = ")
            .append(this.cityName)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
