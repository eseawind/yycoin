package com.china.center.oa.sail.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.sail.bean.DistributionBean;

@Entity(inherit = true)
public class DistributionVO extends DistributionBean 
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
	
	/**
	 * 区
	 */
	@Relationship(relationField = "areaId")
	private String areaName = "";
	
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
	
	@Ignore
	private String shippingName = "";
	
	@Ignore
	private String transport = "";

	@Ignore
    private String transportNo = "";
	
	public DistributionVO(){}

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
	};
	
	public String getTransportName1()
	{
		return transportName1;
	}

	public void setTransportName1(String transportName1)
	{
		this.transportName1 = transportName1;
	}

	public String getTransportName2()
	{
		return transportName2;
	}

	public void setTransportName2(String transportName2)
	{
		this.transportName2 = transportName2;
	}

	public String getShippingName()
	{
		return shippingName;
	}

	public void setShippingName(String shippingName)
	{
		this.shippingName = shippingName;
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
	 * @return the transport
	 */
	public String getTransport() {
		return transport;
	}

	/**
	 * @param transport the transport to set
	 */
	public void setTransport(String transport) {
		this.transport = transport;
	}

	/**
	 * @return the transportNo
	 */
	public String getTransportNo() {
		return transportNo;
	}

	/**
	 * @param transportNo the transportNo to set
	 */
	public void setTransportNo(String transportNo) {
		this.transportNo = transportNo;
	}

	public String toString()
	{
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("DistributionVO ( ")
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
