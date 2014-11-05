package com.china.center.oa.client.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.client.bean.CustomerFormerNameBean;

@SuppressWarnings("serial")
@Entity(inherit = true)
public class CustomerFormerNameVO extends CustomerFormerNameBean
{
	@Relationship(relationField = "formerCustId")
	private String formerCustName = "";
	
	@Relationship(relationField = "currCustId")
	private String currCustName = "";

	public CustomerFormerNameVO()
	{
	}

	public String getFormerCustName()
	{
		return formerCustName;
	}

	public void setFormerCustName(String formerCustName)
	{
		this.formerCustName = formerCustName;
	}

	public String getCurrCustName()
	{
		return currCustName;
	}

	public void setCurrCustName(String currCustName)
	{
		this.currCustName = currCustName;
	}
}
