package com.china.center.oa.project.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.project.constant.ProjectConstant;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * 项目管理申请总表
 * 
 * @author ZHANGYANG
 * @version 2013-2-27
 * @see TcpApplyBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_PROJECTAPPLY")
public class ProjectApplyBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    private String id = "";

    private String name = "";

    /**
     * 申请ID
     */
    private String applyId = "";

    /**
     * 申请人
     */
    @Join(tagClass = StafferBean.class, alias = "APPLYER")
    private String applyerId = "";

    private int type = ProjectConstant.PROJECT_APPLY;

    private int status = ProjectConstant.TASK_INIT;

    private String logTime = "";

    private String description = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getApplyerId() {
		return applyerId;
	}

	public void setApplyerId(String applyerId) {
		this.applyerId = applyerId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLogTime() {
		return logTime;
	}

	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
            .append("ProjectApplyBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("name = ")
            .append(this.name)
            .append(TAB)
            .append("applyId = ")
            .append(this.applyId)
            .append(TAB)
            .append("applyerId = ")
            .append(this.applyerId)
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
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

    
}
