package com.china.center.oa.client.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.client.bean.AppUserApplyBean;

@SuppressWarnings("serial")
@Entity(inherit = true)
public class AppUserApplyVO extends AppUserApplyBean
{
	@Relationship(relationField = "applyId")
	private String applyName = "";

	/**
	 * @return the applyName
	 */
	public String getApplyName()
	{
		return applyName;
	}

	/**
	 * @param applyName the applyName to set
	 */
	public void setApplyName(String applyName)
	{
		this.applyName = applyName;
	}
	
	
}
