package com.china.center.oa.client.bean;

import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.StafferBean;

@SuppressWarnings("serial")
@Entity
@Table(name = "t_center_citic_branch")
public class CiticBranchBean implements Serializable
{
	@Id()
	private String id = "";
	
	@Unique
	@Join(tagClass = StafferBean.class, type = JoinType.LEFT)
	@Html(title = "中信操作员", name = "stafferName")
	private String stafferId = "";

	@Ignore
	private List<CiticVSStafferBean> vsList = null;
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getStafferId()
	{
		return stafferId;
	}

	public void setStafferId(String stafferId)
	{
		this.stafferId = stafferId;
	}

	public List<CiticVSStafferBean> getVsList()
	{
		return vsList;
	}

	public void setVsList(List<CiticVSStafferBean> vsList)
	{
		this.vsList = vsList;
	}
}
