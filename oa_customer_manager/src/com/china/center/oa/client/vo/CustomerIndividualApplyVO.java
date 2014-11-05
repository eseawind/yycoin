package com.china.center.oa.client.vo;

import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.client.bean.CustomerIndividualApplyBean;

@SuppressWarnings("serial")
@Entity(inherit = true)
public class CustomerIndividualApplyVO extends CustomerIndividualApplyBean
{
	@Relationship(tagField = "name", relationField = "provinceId")
    private String provinceName = "";

    @Relationship(tagField = "name", relationField = "cityId")
    private String cityName = "";

    @Relationship(tagField = "name", relationField = "areaId")
    private String areaName = "";
    
	@Relationship(relationField = "refCorpCustId")
	private String refCorpCustName = "";
	
	@Relationship(relationField = "refDepartCustId")
	private String refDepartCustName = "";
	
	@Ignore
	private List<CustomerDistAddrApplyVO> custAddrVOList = null;

	public CustomerIndividualApplyVO()
	{
	}

	public String getRefCorpCustName()
	{
		return refCorpCustName;
	}

	public void setRefCorpCustName(String refCorpCustName)
	{
		this.refCorpCustName = refCorpCustName;
	}

	public String getRefDepartCustName()
	{
		return refDepartCustName;
	}

	public void setRefDepartCustName(String refDepartCustName)
	{
		this.refDepartCustName = refDepartCustName;
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

	/**
	 * @return the custAddrVOList
	 */
	public List<CustomerDistAddrApplyVO> getCustAddrVOList()
	{
		return custAddrVOList;
	}

	/**
	 * @param custAddrVOList the custAddrVOList to set
	 */
	public void setCustAddrVOList(List<CustomerDistAddrApplyVO> custAddrVOList)
	{
		this.custAddrVOList = custAddrVOList;
	}
}
