package com.china.center.oa.client.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_CUSTOMER_BANK")
public class CustomerBankBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	@FK
	private String customerId = "";
	/**
	 * 账户名与账户号确定一条记录
	 */
	@Unique(dependFields="accountNO")
	private String accountName = "";
	
	/**
	 * 账户号
	 */
	private String accountNO = "";

	/**
	 * 
	 */
	public CustomerBankBean()
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
	 * @return the accountName
	 */
	public String getAccountName()
	{
		return accountName;
	}

	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName)
	{
		this.accountName = accountName;
	}

	/**
	 * @return the accountNO
	 */
	public String getAccountNO()
	{
		return accountNO;
	}

	/**
	 * @param accountNO the accountNO to set
	 */
	public void setAccountNO(String accountNO)
	{
		this.accountNO = accountNO;
	}

	/**
	 * @return the customerId
	 */
	public String getCustomerId()
	{
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId)
	{
		this.customerId = customerId;
	}
}
