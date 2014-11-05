package com.china.center.oa.client.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.client.bean.CiticBranchBean;

@Entity(inherit = true)
public class CiticBranchVO extends CiticBranchBean
{
    @Relationship(tagField = "name", relationField = "stafferId")
    private String stafferName = "";
    
    @Ignore
    private String customerNames = "";

	public String getStafferName()
	{
		return stafferName;
	}

	public void setStafferName(String stafferName)
	{
		this.stafferName = stafferName;
	}

	public String getCustomerNames()
	{
		return customerNames;
	}

	public void setCustomerNames(String customerNames)
	{
		this.customerNames = customerNames;
	}
}
