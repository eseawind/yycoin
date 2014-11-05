package com.china.center.oa.client.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity(name = "客户业务信息", inherit = true)
@Table(name = "T_CENTER_VS_CUSTBUSI")
public class CustomerBusinessBean extends AbstractCustomerBusinessBean
{
	@Id
	private String id = "";

	@FK
	private String customerId = "";

	/**
	 * 标注是新增还是删除的
	 */
	@Ignore
	protected int addOrDel = 0;
	
	@Ignore
	private CustomerBusinessBean self = null;
	
	/**
	 * 
	 */
	public CustomerBusinessBean()
	{
	}
	
	public CustomerBusinessBean(boolean addOrDel)
	{
		this.addOrDel = 1;
		this.custAccountType= -1;
		this.custAccountBank = "无";
		this.custAccountName = "无";
		this.custAccount = "无";
		this.myAccountType= -1;
		this.myAccountBank = "无";
		this.myAccountName = "无";
		this.myAccount = "无";
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

	/**
	 * @return the self
	 */
	public CustomerBusinessBean getSelf()
	{
		return self;
	}

	/**
	 * @param self the self to set
	 */
	public void setSelf(CustomerBusinessBean self)
	{
		this.self = self;
	}

	/**
	 * @return the addOrDel
	 */
	public int getAddOrDel()
	{
		return addOrDel;
	}

	/**
	 * @param addOrDel the addOrDel to set
	 */
	public void setAddOrDel(int addOrDel)
	{
		this.addOrDel = addOrDel;
	}
}
