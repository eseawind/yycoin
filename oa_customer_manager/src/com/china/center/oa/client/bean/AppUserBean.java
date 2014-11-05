package com.china.center.oa.client.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;

@SuppressWarnings("serial")
@Entity(inherit = true)
@Table(name = "T_CENTER_APPUSER")
public class AppUserBean extends AbstractUserBean
{
	@Id
	private String id = "";
	
	@Unique
	private String loginName = "";
	
	/**
	 * 1 审核中 2有效 3未通过 4 停用
	 */
	private int status = 0;
	
	private String logTime = "";
	
	@Ignore
	private int oprType = 0;
	
	/**
	 * 
	 */
	public AppUserBean()
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

	public int getOprType()
	{
		return oprType;
	}

	public void setOprType(int oprType)
	{
		this.oprType = oprType;
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
	
	public String toString()
	{
        final String TAB = ",";

        StringBuffer retValue = new StringBuffer();

        retValue
            .append("AppUserBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("loginName = ")
            .append(this.loginName)
            .append(TAB)
            .append("oprType = ")
            .append(this.oprType)
            .append(TAB)            
            .append("status = ")
            .append(this.status)
            .append(TAB)    
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)              
            .append(" )");

        return retValue.toString();
    }
}
