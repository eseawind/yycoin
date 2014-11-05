package com.china.center.oa.sail.vo;

import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.sail.bean.PackageBean;
import com.china.center.oa.sail.wrap.PackageWrap;

@SuppressWarnings("serial")
@Entity(inherit = true)
public class PackageVO extends PackageBean
{
	@Relationship(relationField = "customerId")
	private String customerName = "";
	
	/**
	 * 运输方式1
	 */
	@Relationship(relationField = "transport1")
	private String transportName1 = "";
	
	/**
	 * 运输方式2
	 */
	@Relationship(relationField = "transport2")
	private String transportName2 = "";		
	
	/**
	 * 
	 */
	@Relationship(relationField = "locationId")
	private String locationName = "";
	
	@Ignore
	private String pay = "";
	
	/**
	 * 报表时间
	 */
	@Ignore
	private String repTime = "";
	
	@Ignore
	private List<PackageWrap> wrapList = null;

	public PackageVO()
	{
	}

	public String getCustomerName()
	{
		return customerName;
	}

	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	public String getTransportName1()
	{
		return transportName1;
	}

	public void setTransportName1(String transportName1)
	{
		this.transportName1 = transportName1;
	}

	public String getRepTime()
	{
		return repTime;
	}

	public void setRepTime(String repTime)
	{
		this.repTime = repTime;
	}

	/**
	 * @return the wrapList
	 */
	public List<PackageWrap> getWrapList()
	{
		return wrapList;
	}

	/**
	 * @param wrapList the wrapList to set
	 */
	public void setWrapList(List<PackageWrap> wrapList)
	{
		this.wrapList = wrapList;
	}

	/**
	 * @return the transportName2
	 */
	public String getTransportName2()
	{
		return transportName2;
	}

	/**
	 * @param transportName2 the transportName2 to set
	 */
	public void setTransportName2(String transportName2)
	{
		this.transportName2 = transportName2;
	}

	/**
	 * @return the locationName
	 */
	public String getLocationName()
	{
		return locationName;
	}

	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String locationName)
	{
		this.locationName = locationName;
	}

	/**
	 * @return the pay
	 */
	public String getPay()
	{
		return pay;
	}

	/**
	 * @param pay the pay to set
	 */
	public void setPay(String pay)
	{
		this.pay = pay;
	}
}
