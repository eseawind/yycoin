/**
 * File Name: ProviderBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-12-19<br>
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
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * AgreementBean
 * 
 * @author zhangyang
 * @version 2013-2-16
 * @see AgreementBean
 * @since 1.0
 */
@Entity
@Table(name = "t_center_agreement")
public class AgreementBean implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
    private String id = "";
	
	protected String flowKey = "";

    /**
     * 合同名称
     */
    @Html(title = "合同名称", must = true)
    private String agreementName = "";

    /**
     * 合同编码
     */
    @Unique
    @Html(title = "合同编码", must = true)
    private String agreementCode = "";
    
    /**
     * 线下编码
     */
    @Unique
    @Html(title = "线下编码", must = true)
    private String lineCode = "";
    
    /**
     * 签约日期
     */
    @Html(title = "签约日期",must = true, type = Element.DATE)
    private String signDate = "";
    
    /**
     * 合同金额
     */
    @Html(title = "合同金额")
    private float agreementMoney = 0.0f;

    /**
     * 合同类型 0:不是 1:是//临时数据,待定
     */
    @Html(title = "合同类型", type = Element.SELECT, must = true)
    private int agreementType;
    
    /**
     * 合同性质 0:不是 1:是//临时数据,待定
     */
    @Html(title = "合同性质", type = Element.SELECT, must = true)
    private String agreementProperty = "";

    /**
     * 甲方
     */
    @Html(title = "甲方",  must = true)
    private String party_a = "";
    
    /**
     * 乙方
     */
    @Html(title = "乙方",  must = true)
    private String party_b = "";
    
    /**
     * 甲方签约人
     */
    @Html(title = "甲方签约人",  must = true)
    private String firstparty = "";
    
    /**
     * 乙方签约人
     */
    @Html(title = "乙方签约人",  must = true)
    private String secondparty = "";
    


    
    
    /**
     * 合同状态
     */
    @Html(title = "合同状态",type = Element.SELECT, maxLength = 100)
    private int agreementStatus;
    
    /**
     *合同阶段
     */
    @Html(title = "合同阶段",type = Element.SELECT, maxLength = 100)
    private String agreementStage = "";

    /**
     * 合同文本
     */
    @Html(title = "合同文本")
    private String agreementText = "";
    
    /**
     * 关联项目
     */
    @Html(title = "关联项目")
    private String refProject = "";
    
    /**
     * 前提合同
     */
    @Html(title = "前提合同",type = Element.TEXTAREA)
    private String beforeAgreement = "";
    
    /**
     * 后续合同
     */
    @Html(title = "后续合同",type = Element.TEXTAREA)
    private String afterAgreement = "";
    
    /**
     * 完成进度
     */
    @Html(title = "完成进度")
    private String finishiProcess = "";
    
    /**
     * 合同备注
     */
    @Html(title = "合同备注",maxLength = 200, must = true, type = Element.TEXTAREA)
    private String agreementDesc = "";
    
    /**
     * 变更日志
     */
    @Html(title = "变更日志")
    private String changeLog = "";
    
    
    /**
     * 处理人
     */
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT,alias="s2")
    @Html(title = "处理人", must = true)
    private String processid = "";
    
    
    
    /**
     * 申请时间
     */
    @Html(title = "申请时间")
    private String applyTime = "";
    
    /**
     * 申请人
     */
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT,alias="s1")
    @Html(title = "申请人", must = true)
    private String applyer = "";
    

    /**
     * 附件列表
     */
    @Ignore
    protected List<AttachmentBean> attachmentList = null;
    
    public AgreementBean()
    {
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
            .append("ProjectBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("agreementName = ")
            .append(this.agreementName)
            .append(TAB)
            .append("applyTime = ")
            .append(this.applyTime)
            .append(TAB)
            .append("agreementCode = ")
            .append(this.agreementCode)
            .append(TAB)
             .append("applyer = ")
            .append(this.applyer)
            .append(TAB)
             .append("flowKey = ")
            .append(this.flowKey)
            .append(TAB)
            .append("lineCode = ")
            .append(this.lineCode)
            .append(TAB)
            .append("signDate = ")
            .append(this.signDate)
            .append(TAB)
            .append("agreementMoney = ")
            .append(this.agreementMoney)
            .append(TAB)
            .append("agreementType = ")
            .append(this.agreementType)
            .append(TAB)
            .append("agreementProperty = ")
            .append(this.agreementProperty)
            .append(TAB)
            .append("party_a = ")
            .append(this.party_a)
            .append(TAB)
            .append("party_b = ")
            .append(this.party_b)
            .append(TAB)
             .append("firstparty = ")
            .append(this.firstparty)
            .append(TAB)
            .append("secondparty = ")
            .append(this.secondparty)
            .append(TAB)
            .append("agreementStatus = ")
            .append(this.agreementStatus)
            .append(TAB)
            .append("agreementStage = ")
            .append(this.agreementStage)
            .append(TAB)
            .append("agreementText = ")
            .append(this.agreementText)
            .append(TAB)
            .append("refProject = ")
            .append(this.refProject)
            .append(TAB)
            .append("beforeAgreement = ")
            .append(this.beforeAgreement)
            .append(TAB)
            .append("afterAgreement = ")
            .append(this.afterAgreement)
            .append(TAB)
            .append("finishiProcess = ")
            .append(this.finishiProcess)
            .append(TAB)
            .append("agreementDesc = ")
            .append(this.agreementDesc)
            .append(TAB)
            .append("changeLog = ")
            .append(this.changeLog)
            .append(TAB)
             .append("attachmentList = ")
            .append(this.attachmentList)
            .append(TAB)
            .append("processid = ")
            .append(this.processid)
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

	public String getAgreementName() {
		return agreementName;
	}

	public void setAgreementName(String agreementName) {
		this.agreementName = agreementName;
	}
	
	public List<AttachmentBean> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<AttachmentBean> attachmentList) {
		this.attachmentList = attachmentList;
	}


	public String getAgreementCode() {
		return agreementCode;
	}

	public void setAgreementCode(String agreementCode) {
		this.agreementCode = agreementCode;
	}

	public String getLineCode() {
		return lineCode;
	}

	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public float getAgreementMoney() {
		return agreementMoney;
	}

	public void setAgreementMoney(float agreementMoney) {
		this.agreementMoney = agreementMoney;
	}

	
	public int getAgreementType() {
		return agreementType;
	}

	public void setAgreementType(int agreementType) {
		this.agreementType = agreementType;
	}

	public String getAgreementProperty() {
		return agreementProperty;
	}

	public void setAgreementProperty(String agreementProperty) {
		this.agreementProperty = agreementProperty;
	}

	public String getParty_a() {
		return party_a;
	}

	public void setParty_a(String party_a) {
		this.party_a = party_a;
	}

	public String getParty_b() {
		return party_b;
	}

	public void setParty_b(String party_b) {
		this.party_b = party_b;
	}




	public int getAgreementStatus() {
		return agreementStatus;
	}

	public void setAgreementStatus(int agreementStatus) {
		this.agreementStatus = agreementStatus;
	}

	public String getAgreementStage() {
		return agreementStage;
	}

	public void setAgreementStage(String agreementStage) {
		this.agreementStage = agreementStage;
	}

	public String getAgreementText() {
		return agreementText;
	}

	public void setAgreementText(String agreementText) {
		this.agreementText = agreementText;
	}

	public String getRefProject() {
		return refProject;
	}

	public void setRefProject(String refProject) {
		this.refProject = refProject;
	}

	public String getBeforeAgreement() {
		return beforeAgreement;
	}

	public void setBeforeAgreement(String beforeAgreement) {
		this.beforeAgreement = beforeAgreement;
	}

	public String getAfterAgreement() {
		return afterAgreement;
	}

	public void setAfterAgreement(String afterAgreement) {
		this.afterAgreement = afterAgreement;
	}

	public String getFinishiProcess() {
		return finishiProcess;
	}

	public void setFinishiProcess(String finishiProcess) {
		this.finishiProcess = finishiProcess;
	}

	public String getAgreementDesc() {
		return agreementDesc;
	}

	public void setAgreementDesc(String agreementDesc) {
		this.agreementDesc = agreementDesc;
	}

	public String getChangeLog() {
		return changeLog;
	}

	public void setChangeLog(String changeLog) {
		this.changeLog = changeLog;
	}

    
	public String getApplyTime() {
		return applyTime;
	}


	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getProcessid() {
		return processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getApplyer() {
		return applyer;
	}

	public void setApplyer(String applyer) {
		this.applyer = applyer;
	}

	public String getFlowKey() {
		return flowKey;
	}

	public void setFlowKey(String flowKey) {
		this.flowKey = flowKey;
	}

	public String getFirstparty() {
		return firstparty;
	}

	public void setFirstparty(String firstparty) {
		this.firstparty = firstparty;
	}

	public String getSecondparty() {
		return secondparty;
	}

	public void setSecondparty(String secondparty) {
		this.secondparty = secondparty;
	}


}
