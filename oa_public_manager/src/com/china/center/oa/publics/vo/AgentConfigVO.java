package com.china.center.oa.publics.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.publics.bean.AgentConfigBean;

@Entity(inherit = true)
public class AgentConfigVO extends AgentConfigBean
{

	@Relationship(relationField = "stafferId")
	private String stafferName = "";

	public String getStafferName()
	{
		return stafferName;
	}

	public void setStafferName(String stafferName)
	{
		this.stafferName = stafferName;
	}
	
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("AgentConfigVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
	
}
