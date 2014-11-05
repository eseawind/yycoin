package com.china.center.oa.extsail.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.extsail.bean.ZJRCOutBean;

@SuppressWarnings("serial")
@Entity(inherit = true)
public class ZJRCOutVO extends ZJRCOutBean
{
	/**
     * 源仓库
     */
    @Relationship(relationField = "location")
    private String depotName = "";
    
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
	
	/**
	 * 区
	 */
	@Relationship(relationField = "areaId")
	private String areaName = "";

	public ZJRCOutVO()
	{
	}

	/**
	 * @return the depotName
	 */
	public String getDepotName()
	{
		return depotName;
	}

	/**
	 * @param depotName the depotName to set
	 */
	public void setDepotName(String depotName)
	{
		this.depotName = depotName;
	}

	/**
	 * @return the provinceName
	 */
	public String getProvinceName()
	{
		return provinceName;
	}

	/**
	 * @param provinceName the provinceName to set
	 */
	public void setProvinceName(String provinceName)
	{
		this.provinceName = provinceName;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName()
	{
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName)
	{
		this.cityName = cityName;
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
}
