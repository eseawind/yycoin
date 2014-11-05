package com.china.center.oa.client.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity(name = "客户联系信息",inherit = true)
@Table(name = "T_CENTER_VS_CUSTCONT_APPLY")
public class CustomerContactApplyBean extends AbstractCustomerContactBean
{
	@Id
	private String id = "";

	@FK
	private String customerId = "";
	
	/**
	 * 
	 */
	public CustomerContactApplyBean()
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
