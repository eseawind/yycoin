package com.china.center.oa.client.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_VS_CITICSTAFF")
public class CiticVSStafferBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	@FK
	private String refId = "";
	
	@Join(tagClass = CustomerBean.class, type = JoinType.LEFT)
	private String customerId = "";

	public CiticVSStafferBean()
	{
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getCustomerId()
	{
		return customerId;
	}

	public void setCustomerId(String customerId)
	{
		this.customerId = customerId;
	}

	public String getRefId()
	{
		return refId;
	}

	public void setRefId(String refId)
	{
		this.refId = refId;
	}

}
