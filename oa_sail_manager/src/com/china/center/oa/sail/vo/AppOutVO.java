package com.china.center.oa.sail.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.sail.bean.AppOutBean;

@SuppressWarnings("serial")
@Entity(inherit = true)
public class AppOutVO extends AppOutBean
{
	@Relationship(relationField="userId", tagField = "loginName")
	private String loginName = "";
	
	@Ignore
	private String statusName = "";
	
	@Ignore
	private String payStatusName = "";
	
	@Ignore
	private String refOutId = "";
	
	public AppOutVO()
	{
		
	}

	/**
	 * @return the statusName
	 */
	public String getStatusName()
	{
		return statusName;
	}

	/**
	 * @param statusName the statusName to set
	 */
	public void setStatusName(String statusName)
	{
		this.statusName = statusName;
	}

	/**
	 * @return the payStatusName
	 */
	public String getPayStatusName()
	{
		return payStatusName;
	}

	/**
	 * @param payStatusName the payStatusName to set
	 */
	public void setPayStatusName(String payStatusName)
	{
		this.payStatusName = payStatusName;
	}

	/**
	 * @return the refOutId
	 */
	public String getRefOutId()
	{
		return refOutId;
	}

	/**
	 * @param refOutId the refOutId to set
	 */
	public void setRefOutId(String refOutId)
	{
		this.refOutId = refOutId;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName()
	{
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}
}
