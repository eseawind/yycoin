/*
 * File Name: Product.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-3-25
 * Grant: open source to everybody
 */
package com.china.center.oa.project.bean;


import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * 项目
 * 
 * @author Zhangyang
 * @version 2013-2-16
 * @see
 * @since
 */
@Entity
@Table(name = "t_center_project")
public class ProjectBean implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3119678524478950182L;
	
	public ProjectBean()
	  {
		
	  }

	@Id
    private String id = "";
	
	protected String flowKey = "";

    /**
     * 项目名称
     */
    @Html(title = "项目名称", must = true)
    private String projectName = "";

    /**
     * 项目编码
     */
    @Unique
    @Html(title = "项目编码", must = true)
    private String projectCode = "";

    /**
     * 项目类型 0:不是 1:是//临时数据,待定
     */
    @Html(title = "项目类型", type = Element.SELECT, must = true)
    private String projectType;

//    @Html(title = "计量单位", type = Element.SELECT, must = true)
//    private String amountUnit = "";

    /**
     * 客户名称
     */
    @Join(tagClass = CustomerBean.class, type = JoinType.LEFT)
    @Html(title = "客户名称", must = true)
    private String customerId = "";
    
    /**
     * 创建人
     */
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    @Html(title = "创建人", must = true)
    private String creater = "";
    
    /**
     * 项目状态 
     */
    @Html(title = "项目状态",type = Element.SELECT, maxLength = 100)
    private int projectStatus ;

    /**
     * 项目阶段 
     */
    @Html(title = "项目阶段",type = Element.SELECT, maxLength = 100)
    private String projectStage = "";
    


    /**
     * 预计成功率
     */
    @Html(title = "预计成功率", maxLength = 100)
    private String predictSucRate = "";

    /**
     * 项目备注
     */
    @Html(title = "项目备注",must = true, type = Element.TEXTAREA)
    private String description = "";

    /**
     * 变更日志
     */
    @Html(title = "变更日志")
    private String changelog = "";


    /**
     * 申请时间
     */
    @Html(title = "申请时间")
    private String applyTime = "";
    
    /**
     * 处理人
     */
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    @Html(title = "处理人", must = true)
    private String processid = "";
    
    /**
     * 附件列表
     */
    @Ignore
    protected List<AttachmentBean> attachmentList = null;
    

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
            .append("ProjectBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("projectName = ")
            .append(this.projectName)
            .append(TAB)
            .append("processid = ")
            .append(this.processid)
            .append(TAB)
            .append("projectCode = ")
            .append(this.projectCode)
            .append(TAB)
            .append("projectType = ")
            .append(this.projectType)
            .append(TAB)
            .append("customerId = ")
            .append(this.customerId)
            .append(TAB)
            .append("attachmentList = ")
            .append(this.attachmentList)
            .append(TAB)
            .append("creater = ")
            .append(this.creater)
            .append(TAB)
            .append("flowKey = ")
            .append(this.flowKey)
            .append(TAB)
            .append("projectStatus = ")
            .append(this.projectStatus)
            .append(TAB)
            .append("projectStage = ")
            .append(this.projectStage)
            .append(TAB)
            .append("predictSucRate = ")
            .append(this.predictSucRate)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("changelog = ")
            .append(this.changelog)
            .append(TAB)
            .append("applyTime = ")
            .append(this.applyTime)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public String getProjectCode() {
		return projectCode;
	}


	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}


	public String getProjectType() {
		return projectType;
	}


	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}




	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}



	public int getProjectStatus() {
		return projectStatus;
	}


	public void setProjectStatus(int projectStatus) {
		this.projectStatus = projectStatus;
	}


	public String getProjectStage() {
		return projectStage;
	}


	public void setProjectStage(String projectStage) {
		this.projectStage = projectStage;
	}


	public String getPredictSucRate() {
		return predictSucRate;
	}


	public void setPredictSucRate(String predictSucRate) {
		this.predictSucRate = predictSucRate;
	}




	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getChangelog() {
		return changelog;
	}


	public void setChangelog(String changelog) {
		this.changelog = changelog;
	}


	public String getApplyTime() {
		return applyTime;
	}


	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}


	public String getCreater() {
		return creater;
	}


	public void setCreater(String creater) {
		this.creater = creater;
	}


	public String getProcessid() {
		return processid;
	}


	public void setProcessid(String processid) {
		this.processid = processid;
	}


	public String getFlowKey() {
		return flowKey;
	}


	public void setFlowKey(String flowKey) {
		this.flowKey = flowKey;
	}


	public List<AttachmentBean> getAttachmentList() {
		return attachmentList;
	}


	public void setAttachmentList(List<AttachmentBean> attachmentList) {
		this.attachmentList = attachmentList;
	}

}
