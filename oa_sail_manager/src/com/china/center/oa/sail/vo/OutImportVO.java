package com.china.center.oa.sail.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.sail.bean.OutImportBean;

@Entity(inherit = true)
public class OutImportVO extends OutImportBean
{
	@Relationship(relationField = "OANo", tagField = "status")
	private int oaStatus = -1;
	
	@Relationship(relationField = "OANo", tagField = "pay")
	private int oaPay = -1;

	public OutImportVO()
	{
	}

	public int getOaStatus()
	{
		return oaStatus;
	}

	public void setOaStatus(int oaStatus)
	{
		this.oaStatus = oaStatus;
	}

	public int getOaPay()
	{
		return oaPay;
	}

	public void setOaPay(int oaPay)
	{
		this.oaPay = oaPay;
	}
}
