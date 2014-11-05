package com.china.center.oa.client.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.client.bean.CiticVSStafferBean;

@Entity(inherit = true)
public class CiticVSStafferVO extends CiticVSStafferBean
{
    @Relationship(tagField = "name", relationField = "customerId")
    private String customerName = "";

	public String getCustomerName()
	{
		return customerName;
	}

	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}
}
