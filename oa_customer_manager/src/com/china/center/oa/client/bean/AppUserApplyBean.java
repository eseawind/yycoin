package com.china.center.oa.client.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.StafferBean;

@SuppressWarnings("serial")
@Entity(inherit = true)
@Table(name = "T_CENTER_APPUSER_APPLY")
public class AppUserApplyBean extends AbstractUserBean
{
	@Id
	private String id = "";
	
	private String loginName = "";
	
	@Join(tagClass = StafferBean.class, type = JoinType.LEFT)
	private String applyId = "";
	
	// 审核人
	private String approveId = "";
	
	/**
	 * 0:待审核  1:审核通过  2:驳回
	 */
	private int status = 0;
	
	private String logTime = "";
	
	public AppUserApplyBean()
	{
		
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getLoginName()
	{
		return loginName;
	}

	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}

	public String getApplyId()
	{
		return applyId;
	}

	public void setApplyId(String applyId)
	{
		this.applyId = applyId;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getLogTime()
	{
		return logTime;
	}

	public void setLogTime(String logTime)
	{
		this.logTime = logTime;
	}

	/**
	 * @return the approveId
	 */
	public String getApproveId()
	{
		return approveId;
	}

	/**
	 * @param approveId the approveId to set
	 */
	public void setApproveId(String approveId)
	{
		this.approveId = approveId;
	}
}
