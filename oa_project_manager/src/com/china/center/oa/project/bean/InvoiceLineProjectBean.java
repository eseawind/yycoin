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
@Table(name = "t_center_invoiceLineProject")
public class InvoiceLineProjectBean implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8555229703956169559L;

	public InvoiceLineProjectBean()
	{
		
	}
	
	@Id
    private String id = "";
	
	@FK
    protected String refId = "";

	 /**
     * 开票类型
     */
    @Html(title = "开票类型",type = Element.SELECT, maxLength = 100,  must = true)
    private String invoiceType = "";

    /**
     * 开票金额
     */
    @Html(title = "开票金额", maxLength = 200,  must = true)
    private String invoiceMoney = "";
    
    /**
     * 开票时间
     */
    @Html(title = "开票时间",  must = true)
    private String invoiceTime = "";
    
    /**
     * 完成天数
     */
    @Html(title = "完成天数",  must = true)
    private String finishiDays1 = "";
    
    
    /**
     * 当前任务
     */
    @Html(title = "当前任务")
    private String invocurrentTask = "";
    
    /**
     * 前提任务
     */
    @Html(title = "前提任务")
    private String beforeTask2 = "";
    
    
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

	/**
     * 后续任务
     */
    @Html(title = "后续任务")
    private String afterTask2 = "";
    
    public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceMoney() {
		return invoiceMoney;
	}

	public void setInvoiceMoney(String invoiceMoney) {
		this.invoiceMoney = invoiceMoney;
	}

	public String getInvoiceTime() {
		return invoiceTime;
	}

	public void setInvoiceTime(String invoiceTime) {
		this.invoiceTime = invoiceTime;
	}

	public String getFinishiDays1() {
		return finishiDays1;
	}

	public void setFinishiDays1(String finishiDays1) {
		this.finishiDays1 = finishiDays1;
	}

	public String getBeforeTask2() {
		return beforeTask2;
	}

	public void setBeforeTask2(String beforeTask2) {
		this.beforeTask2 = beforeTask2;
	}

	public String getAfterTask2() {
		return afterTask2;
	}

	public void setAfterTask2(String afterTask2) {
		this.afterTask2 = afterTask2;
	}

	public String getInvocurrentTask() {
		return invocurrentTask;
	}

	public void setInvocurrentTask(String invocurrentTask) {
		this.invocurrentTask = invocurrentTask;
	}

	@Override
	public String toString() {
		return "InvoiceLineProjectBean [id=" + id + ", refId=" + refId
				+ ", invoiceType=" + invoiceType + ", invoiceMoney="
				+ invoiceMoney + ", invoiceTime=" + invoiceTime
				+ ", finishiDays1=" + finishiDays1 + ", invocurrentTask="
				+ invocurrentTask + ", beforeTask2=" + beforeTask2
				+ ", afterTask2=" + afterTask2 + "]";
	}
	
	
}
