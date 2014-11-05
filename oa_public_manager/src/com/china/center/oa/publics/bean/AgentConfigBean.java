package com.china.center.oa.publics.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.JoinType;

@Entity()
@Table(name = "t_center_agentconfig")
public class AgentConfigBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	/**
	 * 座席
	 */
	@Unique
	@Join(tagClass = StafferBean.class, type = JoinType.LEFT)
	private String stafferId = "";
	
	/**
	 * 座席实例
	 */
	private String agentINS = "";
	
	/**
	 * 服务IP
	 */
	private String serverIP = "";
	
	/**
	 * 本地IP
	 */
	private String localIP = "";
	
	/**
	 * 业务
	 */
	private String sk = "";
	
	private String description = "";

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

	public String getAgentINS()
	{
		return agentINS;
	}

	public void setAgentINS(String agentINS)
	{
		this.agentINS = agentINS;
	}

	public String getServerIP()
	{
		return serverIP;
	}

	public void setServerIP(String serverIP)
	{
		this.serverIP = serverIP;
	}

	public String getLocalIP()
	{
		return localIP;
	}

	public void setLocalIP(String localIP)
	{
		this.localIP = localIP;
	}

	public String getSk()
	{
		return sk;
	}

	public void setSk(String sk)
	{
		this.sk = sk;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
	
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("AgentConfigBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("agentINS = ")
            .append(this.agentINS)
            .append(TAB)
            .append("serverIP = ")
            .append(this.serverIP)
            .append(TAB)
            .append("localIP = ")
            .append(this.localIP)
            .append(TAB)
            .append("sk = ")
            .append(this.sk)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
	
}
