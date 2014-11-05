package com.china.center.oa.sail.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.sail.bean.BaseBean;

@Entity(inherit = true)
public class BaseVO extends BaseBean
{
	@Relationship(relationField = "locationId")
	private String depotName = "";

	public String getDepotName()
	{
		return depotName;
	}

	public void setDepotName(String depotName)
	{
		this.depotName = depotName;
	}
	
	
}
