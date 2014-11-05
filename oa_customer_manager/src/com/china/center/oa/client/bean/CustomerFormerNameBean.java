package com.china.center.oa.client.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.JoinType;

@SuppressWarnings("serial")
@Entity(name = "客户曾用名")
@Table(name = "T_CENTER_VS_FORMERCUST")
public class CustomerFormerNameBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	/**
	 * 曾用名(停用客 户)， 当前客户可以对应多个曾用名,但曾用名只能出现一次
	 */
	@Unique
	@Join(tagClass = CustomerBean.class,type = JoinType.LEFT, alias = "C1")
	private String formerCustId = "";

	/**
	 * 关联的当前的客户
	 */
	@FK
	@Join(tagClass = CustomerBean.class,type = JoinType.LEFT, alias = "C2")
	private String currCustId = "";

	public CustomerFormerNameBean()
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

	public String getFormerCustId()
	{
		return formerCustId;
	}

	public void setFormerCustId(String formerCustId)
	{
		this.formerCustId = formerCustId;
	}

	public String getCurrCustId()
	{
		return currCustId;
	}

	public void setCurrCustId(String currCustId)
	{
		this.currCustId = currCustId;
	}
}
