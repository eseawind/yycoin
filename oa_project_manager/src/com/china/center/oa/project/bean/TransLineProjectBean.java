package com.china.center.oa.project.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;

@Entity
@Table(name = "t_center_transLineProject")
public class TransLineProjectBean implements Serializable
{

	public TransLineProjectBean()
	{
		
	}
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -45796244511010208L;
	
	@Id
    private String id = "";
	
	@FK
    protected String refId = "";


	/**
     * 交付类型
     */
    @Html(title = "交付类型",type = Element.SELECT, maxLength = 100,  must = true)
    private String transType = "";
    

    /**
     * 交付物
     */
    @Html(title = "交付物",type = Element.SELECT, maxLength = 100,  must = true)
    private String transobj = "";

    /**
     * 交付物数量
     */
    @Html(title = "交付物数量",  must = true)
    private String transobjCount = "";
    
    /**
     * 交付时间
     */
    @Html(title = "交付时间",  must = true)
    private String transTime = "";
    
    /**
     * 完成天数
     */
    @Html(title = "完成天数",must = true)
    private String transDays = "";
    
    /**
     * 当前任务
     */
    @Html(title = "当前任务",must = true)
    private String currentTask = "";
    
    /**
     * 接收人
     */
    @Html(title = "接收人",must = true)
    private String receiver = "";

    /**
     * 前提任务
     */
    @Html(title = "前提任务")
    private String beforeTask1 = "";
    
    /**
     * 后续任务
     */
    @Html(title = "后续任务")
    private String afterTask1 = "";
    
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTransobj() {
		return transobj;
	}

	public void setTransobj(String transobj) {
		this.transobj = transobj;
	}

	public String getTransobjCount() {
		return transobjCount;
	}

	public void setTransobjCount(String transobjCount) {
		this.transobjCount = transobjCount;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getTransDays() {
		return transDays;
	}

	public void setTransDays(String transDays) {
		this.transDays = transDays;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getBeforeTask1() {
		return beforeTask1;
	}

	public void setBeforeTask1(String beforeTask1) {
		this.beforeTask1 = beforeTask1;
	}

	public String getAfterTask1() {
		return afterTask1;
	}

	public void setAfterTask1(String afterTask1) {
		this.afterTask1 = afterTask1;
	}

	
	
	public String getCurrentTask() {
		return currentTask;
	}

	public void setCurrentTask(String currentTask) {
		this.currentTask = currentTask;
	}

	@Override
	public String toString() {
		return "TransLineProjectBean [id=" + id + ", refId=" + refId
				+ ", transType=" + transType + ", transobj=" + transobj
				+ ", transobjCount=" + transobjCount + ", transTime="
				+ transTime + ", transDays=" + transDays + ", currentTask="
				+ currentTask + ", receiver=" + receiver + ", beforeTask1="
				+ beforeTask1 + ", afterTask1=" + afterTask1 + "]";
	}

	
}
