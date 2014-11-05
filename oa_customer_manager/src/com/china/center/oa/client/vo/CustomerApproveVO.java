package com.china.center.oa.client.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.client.bean.CustomerApproveBean;

@SuppressWarnings("serial")
@Entity(inherit = true)
public class CustomerApproveVO extends CustomerApproveBean
{
	@Relationship(relationField = "applyId")
	private String stafferName = "";

	/**
	 * 
	 */
	public CustomerApproveVO()
	{
	}

	/**
	 * @return the stafferName
	 */
	public String getStafferName()
	{
		return stafferName;
	}

	/**
	 * @param stafferName the stafferName to set
	 */
	public void setStafferName(String stafferName)
	{
		this.stafferName = stafferName;
	}
}
