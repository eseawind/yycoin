package com.china.center.oa.client.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class AbstractUserBean implements Serializable
{
	private String password = "";
	
	private String mobile = "";
	
	private String verifyCode = "";
	
	private String email = "";
	
	private String addressId = "";
	
	private String province = "";
	
	private String city = "";
	
	private String fullAddress = "";
	
	private String description = "";
	
	public AbstractUserBean()
	{
		
	}
	
	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getVerifyCode()
	{
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode)
	{
		this.verifyCode = verifyCode;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getAddressId()
	{
		return addressId;
	}

	public void setAddressId(String addressId)
	{
		this.addressId = addressId;
	}

	public String getProvince()
	{
		return province;
	}

	public void setProvince(String province)
	{
		this.province = province;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getFullAddress()
	{
		return fullAddress;
	}

	public void setFullAddress(String fullAddress)
	{
		this.fullAddress = fullAddress;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public String toString()
	{
        final String TAB = ",";

        StringBuffer retValue = new StringBuffer();

        retValue
            .append("AbstractUserBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("password = ")
            .append(this.password)
            .append(TAB)
            .append("mobile = ")
            .append(this.mobile)
            .append(TAB)
            .append("verifyCode = ")
            .append(this.verifyCode)
            .append(TAB)
            .append("addressId = ")
            .append(this.addressId)
            .append(TAB)
            .append("province = ")
            .append(this.province)
            .append(TAB)
            .append("city = ")
            .append(this.city)
            .append(TAB)
            .append("fullAddress = ")
            .append(this.fullAddress)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)            
            .append(" )");

        return retValue.toString();
    }
}
