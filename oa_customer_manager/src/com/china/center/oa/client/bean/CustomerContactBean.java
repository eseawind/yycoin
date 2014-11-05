package com.china.center.oa.client.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity(name = "客户联系信息", inherit = true)
@Table(name = "T_CENTER_VS_CUSTCONT")
public class CustomerContactBean extends AbstractCustomerContactBean
{
	@Id
	private String id = "";

	@FK
	private String customerId = "";
	
	/**
	 * 标注是新增还是删除的
	 */
	@Ignore
	private int addOrDel = 0;
	
	@Ignore
	private CustomerContactBean self = null;

	/**
	 * 
	 */
	public CustomerContactBean()
	{
	}
	
	public CustomerContactBean(boolean addOrDel)
	{
		this.addOrDel = 1;
		this.name = "无";
		this.birthday = "无";
		this.handphone = "无";
		this.tel = "无";
		this.email = "无";
		this.qq = "无";
		this.weibo = "无";
		this.weixin = "无";
		this.reportTo = "无";
		this.relationship = -1;
		this.interest = "无";
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
	public CustomerContactBean getSelf()
	{
		return self;
	}

	/**
	 * @param self the self to set
	 */
	public void setSelf(CustomerContactBean self)
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
