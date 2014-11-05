package com.china.center.oa.client.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.client.bean.CustomerBean;

@SuppressWarnings("serial")
@Entity(inherit = true)
public class CustomerVO extends CustomerBean
{
	@Relationship(tagField = "name", relationField = "provinceId")
    private String provinceName = "";

    @Relationship(tagField = "name", relationField = "cityId")
    private String cityName = "";

    @Relationship(relationField = "creditLevelId")
    private String creditLevelName = "";

    @Relationship(tagField = "name", relationField = "areaId")
    private String areaName = "";

    @Ignore
    private String stafferName = "";
    
	@Relationship(relationField = "refCorpCustId")
	private String refCorpCustName = "";
	
	@Relationship(relationField = "refDepartCustId")
	private String refDepartCustName = "";
    
	public CustomerVO()
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

	public String getCreditLevelName()
	{
		return creditLevelName;
	}

	public void setCreditLevelName(String creditLevelName)
	{
		this.creditLevelName = creditLevelName;
	}

	public String getAreaName()
	{
		return areaName;
	}

	public void setAreaName(String areaName)
	{
		this.areaName = areaName;
	}

	public String getStafferName()
	{
		return stafferName;
	}

	public void setStafferName(String stafferName)
	{
		this.stafferName = stafferName;
	}

	/**
	 * @return the refCorpCustName
	 */
	public String getRefCorpCustName()
	{
		return refCorpCustName;
	}

	/**
	 * @param refCorpCustName the refCorpCustName to set
	 */
	public void setRefCorpCustName(String refCorpCustName)
	{
		this.refCorpCustName = refCorpCustName;
	}

	/**
	 * @return the refDepartCustName
	 */
	public String getRefDepartCustName()
	{
		return refDepartCustName;
	}

	/**
	 * @param refDepartCustName the refDepartCustName to set
	 */
	public void setRefDepartCustName(String refDepartCustName)
	{
		this.refDepartCustName = refDepartCustName;
	}
}
