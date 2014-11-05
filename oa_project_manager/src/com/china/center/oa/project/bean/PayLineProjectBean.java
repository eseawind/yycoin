package com.china.center.oa.project.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.oa.project.constant.ProjectConstant;

@Entity
@Table(name = "t_center_payLineProject")
public class PayLineProjectBean implements Serializable
{
	public PayLineProjectBean()
	{
		
	}
	
	@Id
    private String id = "";
	
	@FK
    protected String refId = "";

	 /**
	 * 
	 */
	private static final long serialVersionUID = -6218380754145248326L;

	/**
     * 付款类型
     */
    @Html(title = "付款类型", type = Element.SELECT,  must = true)
    private String payType = "";
    
    /**
     * 付款金额
     */
    @Html(title = "付款金额", maxLength = 200,  must = true)
    private String payMoney = "";
    
    /**
     * 付款时间
     */
    @Html(title = "付款时间",  must = true)
    private String payTime = "";

    /**
     * 完成天数
     */
    @Html(title = "完成天数",  must = true)
    private String finishDays = "";
    
    
    /**
     * 当前任务
     */
    @Html(title = "当前任务")
    private String paycurrentTask1 = "";

    /**
     * 前提任务
     */
    @Html(title = "前提任务")
    private String beforeTask = "";
    
    /**
     * 后续任务
     */
    @Html(title = "后续任务")
    private String afterTask = "";
    
    public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getFinishDays() {
		return finishDays;
	}

	public void setFinishDays(String finishDays) {
		this.finishDays = finishDays;
	}

	public String getBeforeTask() {
		return beforeTask;
	}

	public void setBeforeTask(String beforeTask) {
		this.beforeTask = beforeTask;
	}

	public String getAfterTask() {
		return afterTask;
	}

	public void setAfterTask(String afterTask) {
		this.afterTask = afterTask;
	}

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

	public String getPaycurrentTask1() {
		return paycurrentTask1;
	}

	public void setPaycurrentTask1(String paycurrentTask1) {
		this.paycurrentTask1 = paycurrentTask1;
	}

	@Override
	public String toString() {
		return "PayLineProjectBean [id=" + id + ", refId=" + refId
				+ ", payType=" + payType + ", payMoney=" + payMoney
				+ ", payTime=" + payTime + ", finishDays=" + finishDays
				+ ", paycurrentTask1=" + paycurrentTask1 + ", beforeTask="
				+ beforeTask + ", afterTask=" + afterTask + "]";
	}

	

}
