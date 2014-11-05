package com.china.center.oa.client.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity(name = "客户配送地址信息", inherit = true)
@Table(name = "T_CENTER_VS_CUSTADDR")
public class CustomerDistAddrBean extends AbstractCustomerDistAddrBean
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
	private CustomerDistAddrBean self = null;
	
	/**
	 * 
	 */
	public CustomerDistAddrBean()
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

	/**
	 * @return the self
	 */
	public CustomerDistAddrBean getSelf()
	{
		return self;
	}

	/**
	 * @param self the self to set
	 */
	public void setSelf(CustomerDistAddrBean self)
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
