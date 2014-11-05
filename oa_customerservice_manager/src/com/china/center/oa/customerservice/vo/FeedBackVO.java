package com.china.center.oa.customerservice.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.customerservice.bean.FeedBackBean;

@Entity(inherit = true)
public class FeedBackVO extends FeedBackBean
{
	@Relationship(relationField = "customerId")
	private String customerName = "";
	
	@Relationship(relationField = "stafferId")
	private String stafferName = "";
	
	@Ignore
	private String now = "";
	
	@Ignore
	private String changeTime = "";

	public FeedBackVO()
	{
	}

	public String getCustomerName()
	{
		return customerName;
	}

	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	public String getStafferName()
	{
		return stafferName;
	}

	public void setStafferName(String stafferName)
	{
		this.stafferName = stafferName;
	}

	public String getNow()
	{
		return now;
	}

	public void setNow(String now)
	{
		this.now = now;
	}

	public String getChangeTime()
	{
		return changeTime;
	}

	public void setChangeTime(String changeTime)
	{
		this.changeTime = changeTime;
	}
}
