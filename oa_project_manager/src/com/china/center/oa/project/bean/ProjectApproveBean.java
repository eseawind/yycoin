package com.china.center.oa.project.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.project.constant.ProjectConstant;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;

/**
 * 项目处理总表
 * 
 */
@Entity
@Table(name = "T_CENTER_PROJECTAPPROVE")
public class ProjectApproveBean implements Serializable
{
	@Id
    private String id = "";

    private String name = "";

    private String flowKey = "";

    /**
     * 申请ID
     */
    @FK
    private String applyId = "";

    /**
     * 申请人
     */
    @Join(tagClass = StafferBean.class, alias = "APPLYER")
    private String applyerId = "";

    @Join(tagClass = PrincipalshipBean.class, type = JoinType.LEFT)
    private String departmentId = "";

    /**
     * 审批人
     */
    @Join(tagClass = StafferBean.class, alias = "APPROVER")
    private String approverId = "";


    private int status = ProjectConstant.TASK_START;
    
    private int type = ProjectConstant.TASK_APPLY;


    private String logTime = "";
    
    private int flag = 0;
    
    private int mode = 0;

    private String description = "";

    private String receiver = "";
    
    /**
     * default constructor
     */
    public ProjectApproveBean()
    {
    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the applyId
     */
    public String getApplyId()
    {
        return applyId;
    }

    /**
     * @param applyId
     *            the applyId to set
     */
    public void setApplyId(String applyId)
    {
        this.applyId = applyId;
    }

    /**
     * @return the applyerId
     */
    public String getApplyerId()
    {
        return applyerId;
    }

    /**
     * @param applyerId
     *            the applyerId to set
     */
    public void setApplyerId(String applyerId)
    {
        this.applyerId = applyerId;
    }

    /**
     * @return the approverId
     */
    public String getApproverId()
    {
        return approverId;
    }

    /**
     * @param approverId
     *            the approverId to set
     */
    public void setApproverId(String approverId)
    {
        this.approverId = approverId;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return the status
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status)
    {
        this.status = status;
    }


    /**
     * @return the logTime
     */
    public String getLogTime()
    {
        return logTime;
    }

    /**
     * @param logTime
     *            the logTime to set
     */
    public void setLogTime(String logTime)
    {
        this.logTime = logTime;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the flowKey
     */
    public String getFlowKey()
    {
        return flowKey;
    }

    /**
     * @param flowKey
     *            the flowKey to set
     */
    public void setFlowKey(String flowKey)
    {
        this.flowKey = flowKey;
    }
    

    public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	/**
     * @return the departmentId
     */
    public String getDepartmentId()
    {
        return departmentId;
    }

    /**
     * @param departmentId
     *            the departmentId to set
     */
    public void setDepartmentId(String departmentId)
    {
        this.departmentId = departmentId;
    }
    
    public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String getReceiver()
	{
		return receiver;
	}

	public void setReceiver(String receiver)
	{
		this.receiver = receiver;
	}

	/**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("TcpApproveBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("name = ")
            .append(this.name)
            .append(TAB)
            .append("flowKey = ")
            .append(this.flowKey)
            .append(TAB)
            .append("applyId = ")
            .append(this.applyId)
            .append(TAB)
            .append("applyerId = ")
            .append(this.applyerId)
            .append(TAB)
            .append("departmentId = ")
            .append(this.departmentId)
            .append(TAB)
            .append("approverId = ")
            .append(this.approverId)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("flag = ")
            .append(this.flag)
            .append(TAB)
               .append("mode = ")
            .append(this.mode)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
