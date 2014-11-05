package com.china.center.oa.sail.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.sail.bean.AuditRuleBean;

@Entity(inherit = true)
public class AuditRuleVO extends AuditRuleBean
{

	/**
	 * 事业部
	 */
	@Relationship(relationField = "industryId")	
	private String industryName = "";

	public String getIndustryName()
	{
		return industryName;
	}

	public void setIndustryName(String industryName)
	{
		this.industryName = industryName;
	}
	
	public String toString()
	{
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("AuditRuleVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("industryName = ")
            .append(this.industryName)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
