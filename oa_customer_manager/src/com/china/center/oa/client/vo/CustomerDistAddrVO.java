package com.china.center.oa.client.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.client.bean.CustomerDistAddrBean;

@SuppressWarnings("serial")
@Entity(inherit = true)
public class CustomerDistAddrVO extends CustomerDistAddrBean
{
	@Relationship(tagField = "name", relationField = "provinceId")
    private String provinceName = "";

    @Relationship(tagField = "name", relationField = "cityId")
    private String cityName = "";

    @Relationship(tagField = "name", relationField = "areaId")
    private String areaName = "";

	public CustomerDistAddrVO()
	{
	}
	
	public CustomerDistAddrVO(boolean addOrDel)
	{
		this.addOrDel = 1;
		this.provinceName = "无";
		this.cityName = "无";
		this.areaName = "无";
		this.address = "无";
		this.fullAddress = "无";
		this.contact = "无";
		this.telephone = "无";
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
