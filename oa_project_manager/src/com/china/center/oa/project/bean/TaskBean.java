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
import com.china.center.oa.project.constant.ProjectConstant;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * 任务管理
 * 
 * @author Zhangyang
 * @version 2013-2-16
 * @see
 * @since
 */
@Entity
@Table(name = "t_center_task")
public class TaskBean implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3119678524478950182L;
	
	@Id
    private String id = "";
	
	protected String flowKey = "";

    /**
     * 任务名称
     */
    @Html(title = "任务名称", must = true)
    private String taskName = "";

    /**
     * 任务编码
     */
    @Unique
    @Html(title = "任务编码", must = true)
    private String taskCode = "";

    /**
     * 任务类型0:不是 1:是//临时数据,待定
     */
    @Html(title = "任务类型", type = Element.SELECT, must = true)
    private int taskType = 0;

    /**
     * 责任人
     */
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT,alias="duty")
    @Html(title = "责任人", must = true)
    private String dutyStaffer = "";
    
    /**
     * 处理人
     */
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT,alias="pro")
    @Html(title = "处理人", must = true)
    private String processid = "";
    
    /**
     * 申请人
     */
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT,alias="aper")
    @Html(name = "applyerName", title = "申请人", readonly = true)
    private String applyer = "";
    
    /**
     * 参与人
     */
    @Html(title = "参与人",  type = Element.TEXTAREA)
    private String partaker = "";
    /**
    /**
     * 计划完成时间
     */
    @Html(title = "计划完成时间", must = true,readonly=true,type = Element.DATE)
    private String planFinishTime = "";

    /**
     * 实际完成时间
     */
    @Html(title = "实际完成时间", must = true,type = Element.DATE)
    private String realFinishiTime = "";
    
    /**
     * 交付类型0:不是 1:是//临时数据,待定
     */
    @Html(title = "交付类型", type = Element.SELECT, must = true)
    private String transType = "";
    
    /**
     * 交付物0:不是 1:是//临时数据,待定
     */
    @Html(title = "交付物", type = Element.SELECT, must = true)
    private String transObj = "";

    /**
     * 交付物数量
     */
    @Html(title = "交付物数量", maxLength = 200)
    private int transObjCount = 0;

    /**
     * 接收人
     */
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT,alias="recei")
    @Html(title = "任务结果审核人", must = true)
    private String receiver = "";

    /**
     * 前提任务
     */
    @Html(title = "前提任务",type = Element.TEXTAREA)
    private String beforeTask = "";

    /**
     * 后续任务
     */
    @Html(title = "后续任务",type = Element.TEXTAREA)
    private String afterTask = "";

    /**
     * 任务状态 
     */
    @Html(title = "任务状态",type = Element.SELECT, maxLength = 100)
    private int taskStatus = ProjectConstant.TASK_INIT;
    
    /**
     * 任务阶段 
     */
    @Html(title = "任务阶段",type = Element.SELECT, maxLength = 100)
    private String taskStage = ProjectConstant.TASK_STAGE_ONE;

    /**
     * 附件
     */
    @Html(title = "附件", maxLength = 100)
    private String filePath = "";

    /**
     * 关联单据
     */
    @Html(title = "关联单据")
    private String refid = "";
    
    /**
     * 任务备注
     */
    @Html(title = "任务备注",  must = true,type = Element.TEXTAREA)
    private String description = "";
    
    /**
     * 任务备注
     */
    @Html(title = "任务信息", type = Element.TEXTAREA)
    private String addinfo = "";
    
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
     * 紧急类型
     */
    @Html(title = "紧急类型",type = Element.SELECT, must = true)
    private int emergencyType = ProjectConstant.TASK_EMERGENCY_COMMON;
    
    /**
     * 任务完成时间  小时
     */
    private String finishTime = "";
    
    /**
     * 任务完成时间 分
     */
    private String finishMin = "";
    
    @Ignore
    private String finishFullTime = "";
    
    /**
     * 创建人
     */
    private String creator = "";
    
    private String creatorName = "";
    
    /**
     * 附件列表
     */
    @Ignore
    protected List<AttachmentBean> attachmentList = null;

    public TaskBean() {
		
	}
    
    /**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the flowKey
	 */
	public String getFlowKey() {
		return flowKey;
	}

	/**
	 * @param flowKey the flowKey to set
	 */
	public void setFlowKey(String flowKey) {
		this.flowKey = flowKey;
	}

	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * @return the taskCode
	 */
	public String getTaskCode() {
		return taskCode;
	}

	/**
	 * @param taskCode the taskCode to set
	 */
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	/**
	 * @return the taskType
	 */
	public int getTaskType() {
		return taskType;
	}

	/**
	 * @param taskType the taskType to set
	 */
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	/**
	 * @return the dutyStaffer
	 */
	public String getDutyStaffer() {
		return dutyStaffer;
	}

	/**
	 * @param dutyStaffer the dutyStaffer to set
	 */
	public void setDutyStaffer(String dutyStaffer) {
		this.dutyStaffer = dutyStaffer;
	}

	/**
	 * @return the processid
	 */
	public String getProcessid() {
		return processid;
	}

	/**
	 * @param processid the processid to set
	 */
	public void setProcessid(String processid) {
		this.processid = processid;
	}

	/**
	 * @return the applyer
	 */
	public String getApplyer() {
		return applyer;
	}

	/**
	 * @param applyer the applyer to set
	 */
	public void setApplyer(String applyer) {
		this.applyer = applyer;
	}

	/**
	 * @return the partaker
	 */
	public String getPartaker() {
		return partaker;
	}

	/**
	 * @param partaker the partaker to set
	 */
	public void setPartaker(String partaker) {
		this.partaker = partaker;
	}

	/**
	 * @return the planFinishTime
	 */
	public String getPlanFinishTime() {
		return planFinishTime;
	}

	/**
	 * @param planFinishTime the planFinishTime to set
	 */
	public void setPlanFinishTime(String planFinishTime) {
		this.planFinishTime = planFinishTime;
	}

	/**
	 * @return the realFinishiTime
	 */
	public String getRealFinishiTime() {
		return realFinishiTime;
	}

	/**
	 * @param realFinishiTime the realFinishiTime to set
	 */
	public void setRealFinishiTime(String realFinishiTime) {
		this.realFinishiTime = realFinishiTime;
	}

	/**
	 * @return the transType
	 */
	public String getTransType() {
		return transType;
	}

	/**
	 * @param transType the transType to set
	 */
	public void setTransType(String transType) {
		this.transType = transType;
	}

	/**
	 * @return the transObj
	 */
	public String getTransObj() {
		return transObj;
	}

	/**
	 * @param transObj the transObj to set
	 */
	public void setTransObj(String transObj) {
		this.transObj = transObj;
	}

	/**
	 * @return the transObjCount
	 */
	public int getTransObjCount() {
		return transObjCount;
	}

	/**
	 * @param transObjCount the transObjCount to set
	 */
	public void setTransObjCount(int transObjCount) {
		this.transObjCount = transObjCount;
	}

	/**
	 * @return the receiver
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return the beforeTask
	 */
	public String getBeforeTask() {
		return beforeTask;
	}

	/**
	 * @param beforeTask the beforeTask to set
	 */
	public void setBeforeTask(String beforeTask) {
		this.beforeTask = beforeTask;
	}

	/**
	 * @return the afterTask
	 */
	public String getAfterTask() {
		return afterTask;
	}

	/**
	 * @param afterTask the afterTask to set
	 */
	public void setAfterTask(String afterTask) {
		this.afterTask = afterTask;
	}

	/**
	 * @return the taskStatus
	 */
	public int getTaskStatus() {
		return taskStatus;
	}

	/**
	 * @param taskStatus the taskStatus to set
	 */
	public void setTaskStatus(int taskStatus) {
		this.taskStatus = taskStatus;
	}

	/**
	 * @return the taskStage
	 */
	public String getTaskStage() {
		return taskStage;
	}

	/**
	 * @param taskStage the taskStage to set
	 */
	public void setTaskStage(String taskStage) {
		this.taskStage = taskStage;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the refid
	 */
	public String getRefid() {
		return refid;
	}

	/**
	 * @param refid the refid to set
	 */
	public void setRefid(String refid) {
		this.refid = refid;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the addinfo
	 */
	public String getAddinfo() {
		return addinfo;
	}

	/**
	 * @param addinfo the addinfo to set
	 */
	public void setAddinfo(String addinfo) {
		this.addinfo = addinfo;
	}

	/**
	 * @return the changelog
	 */
	public String getChangelog() {
		return changelog;
	}

	/**
	 * @param changelog the changelog to set
	 */
	public void setChangelog(String changelog) {
		this.changelog = changelog;
	}

	/**
	 * @return the applyTime
	 */
	public String getApplyTime() {
		return applyTime;
	}

	/**
	 * @param applyTime the applyTime to set
	 */
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	/**
	 * @return the emergencyType
	 */
	public int getEmergencyType() {
		return emergencyType;
	}

	/**
	 * @param emergencyType the emergencyType to set
	 */
	public void setEmergencyType(int emergencyType) {
		this.emergencyType = emergencyType;
	}

	/**
	 * @return the finishTime
	 */
	public String getFinishTime() {
		return finishTime;
	}

	/**
	 * @param finishTime the finishTime to set
	 */
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * @return the finishMin
	 */
	public String getFinishMin() {
		return finishMin;
	}

	/**
	 * @param finishMin the finishMin to set
	 */
	public void setFinishMin(String finishMin) {
		this.finishMin = finishMin;
	}

	/**
	 * @return the finishFullTime
	 */
	public String getFinishFullTime() {
		return finishFullTime;
	}

	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * @return the creatorName
	 */
	public String getCreatorName() {
		return creatorName;
	}

	/**
	 * @param creatorName the creatorName to set
	 */
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	/**
	 * @param finishFullTime the finishFullTime to set
	 */
	public void setFinishFullTime(String finishFullTime) {
		this.finishFullTime = finishFullTime;
	}

	/**
	 * @return the attachmentList
	 */
	public List<AttachmentBean> getAttachmentList() {
		return attachmentList;
	}

	/**
	 * @param attachmentList the attachmentList to set
	 */
	public void setAttachmentList(List<AttachmentBean> attachmentList) {
		this.attachmentList = attachmentList;
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
            .append("TaskBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
             .append("flowKey = ")
            .append(this.flowKey)
            .append(TAB)
            .append("taskName = ")
            .append(this.taskName)
            .append(TAB)
            .append("taskCode = ")
            .append(this.taskCode)
            .append(TAB)
            .append("taskType = ")
            .append(this.taskType)
            .append(TAB)
             .append("applyer = ")
            .append(this.applyer)
            .append(TAB)
            .append("dutyStaffer = ")
            .append(this.dutyStaffer)
            .append(TAB)
            .append("partaker = ")
            .append(this.partaker)
            .append(TAB)
            .append("planFinishTime = ")
            .append(this.planFinishTime)
            .append(TAB)
            .append("realFinishiTime = ")
            .append(this.realFinishiTime)
            .append(TAB)
            .append("transType = ")
            .append(this.transType)
            .append(TAB)
            .append("payObj = ")
            .append(this.transObj)
            .append(TAB)
            .append("processid = ")
            .append(this.processid)
            .append(TAB)
            .append("payObjCount = ")
            .append(this.transObjCount)
            .append(TAB)
            .append("receiver = ")
            .append(this.receiver)
            .append(TAB)
            .append("beforeTask = ")
            .append(this.beforeTask)
            .append(TAB)
            .append("afterTask = ")
            .append(this.afterTask)
            .append(TAB)
            .append("taskStatus = ")
            .append(this.taskStatus)
            .append(TAB)
            .append("taskStage = ")
            .append(this.taskStage)
            .append(TAB)
            .append("filePath = ")
            .append(this.filePath)
            .append(TAB)
            .append("refid = ")
            .append(this.refid)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("addinfo = ")
            .append(this.addinfo)
            .append(TAB)
            .append("changelog = ")
            .append(this.changelog)
            .append(TAB)
            .append("applyTime = ")
            .append(this.applyTime)
            .append(TAB)
            .append("attachmentList = ")
            .append(this.attachmentList)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
