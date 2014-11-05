package com.china.center.oa.sail.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_APPDIST")
public class AppDistributionBean implements Serializable
{
	@Id
	private String id = "";
	
	@Unique
	private String orderNo = "";
	
	private String province = "";
	
	private String city = "";
	
	private String fullAddress = "";
	
	private String receiver = "";
	
	private String receiverMobile = "";
	
	private String carryType = "";

	/**
	 * 
	 */
	public AppDistributionBean()
	{
	}

	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return the province
	 */
	public String getProvince()
	{
		return province;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(String province)
	{
		this.province = province;
	}

	/**
	 * @return the city
	 */
	public String getCity()
	{
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city)
	{
		this.city = city;
	}

	/**
	 * @return the fullAddress
	 */
	public String getFullAddress()
	{
		return fullAddress;
	}

	/**
	 * @param fullAddress the fullAddress to set
	 */
	public void setFullAddress(String fullAddress)
	{
		this.fullAddress = fullAddress;
	}

	/**
	 * @return the receiver
	 */
	public String getReceiver()
	{
		return receiver;
	}

	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(String receiver)
	{
		this.receiver = receiver;
	}

	/**
	 * @return the receiverMobile
	 */
	public String getReceiverMobile()
	{
		return receiverMobile;
	}

	/**
	 * @param receiverMobile the receiverMobile to set
	 */
	public void setReceiverMobile(String receiverMobile)
	{
		this.receiverMobile = receiverMobile;
	}

	/**
	 * @return the carryType
	 */
	public String getCarryType()
	{
		return carryType;
	}

	/**
	 * @param carryType the carryType to set
	 */
	public void setCarryType(String carryType)
	{
		this.carryType = carryType;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}
}
