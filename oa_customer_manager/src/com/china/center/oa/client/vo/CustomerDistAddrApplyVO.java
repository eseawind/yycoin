package com.china.center.oa.client.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.client.bean.CustomerDistAddrApplyBean;

@SuppressWarnings("serial")
@Entity(inherit = true)
public class CustomerDistAddrApplyVO extends CustomerDistAddrApplyBean
{
	@Relationship(tagField = "name", relationField = "provinceId")
    private String provinceName = "";

    @Relationship(tagField = "name", relationField = "cityId")
    private String cityName = "";

    @Relationship(tagField = "name", relationField = "areaId")
    private String areaName = "";

	public CustomerDistAddrApplyVO()
	{
	}

	public String getProvinceName()
	{
		return provinceName;
	}

	public void setProvinceName(String provinceName)
	{
		this.provinceName = provinceName;
	}

	public String getCityName()
	{
		return cityName;
	}

	public void setCityName(String cityName)
	{
		this.cityName = cityName;
	}

	public String getAreaName()
	{
		return areaName;
	}

	public void setAreaName(String areaName)
	{
		this.areaName = areaName;
	}
}
