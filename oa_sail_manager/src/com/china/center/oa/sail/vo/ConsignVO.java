package com.china.center.oa.sail.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.sail.bean.ConsignBean;

@SuppressWarnings("serial")
@Entity(inherit = true)
public class ConsignVO extends ConsignBean
{
	@Relationship(relationField = "fullId", tagField = "outTime")
	private String outTime = "";
	
	@Relationship(relationField = "fullId", tagField = "industryId")
	private String industryId = "";
	
	@Relationship(relationField = "fullId", tagField = "changeTime")
	private String changeTime = "";
	
	@Relationship(relationField = "distId", tagField = "outboundDate")
	private String outboundDate = "";
	
	public ConsignVO()
	{
	}

	/**
	 * @return the outTime
	 */
	public String getOutTime()
	{
		return outTime;
	}

	/**
	 * @param outTime the outTime to set
	 */
	public void setOutTime(String outTime)
	{
		this.outTime = outTime;
	}

	/**
	 * @return the industryId
	 */
	public String getIndustryId()
	{
		return industryId;
	}

	/**
	 * @param industryId the industryId to set
	 */
	public void setIndustryId(String industryId)
	{
		this.industryId = industryId;
	}

	/**
	 * @return the changeTime
	 */
	public String getChangeTime()
	{
		return changeTime;
	}

	/**
	 * @param changeTime the changeTime to set
	 */
	public void setChangeTime(String changeTime)
	{
		this.changeTime = changeTime;
	}

	/**
	 * @return the outboundDate
	 */
	public String getOutboundDate() {
		return outboundDate;
	}

	/**
	 * @param outboundDate the outboundDate to set
	 */
	public void setOutboundDate(String outboundDate) {
		this.outboundDate = outboundDate;
	}
}
