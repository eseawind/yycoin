package com.china.center.oa.project.vo;

import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.project.bean.AgreementBean;
import com.china.center.oa.project.bean.InvoiceLineProjectBean;
import com.china.center.oa.project.bean.PayLineProjectBean;
import com.china.center.oa.project.bean.ProLineProjectBean;
import com.china.center.oa.project.bean.TransLineProjectBean;
import com.china.center.oa.publics.bean.AttachmentBean;

@Entity(inherit = true)
public class AgreementVO extends AgreementBean
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8645158388502685848L;

	@Relationship(relationField = "processid")
    private String processer = "";
	
	@Relationship(relationField = "applyer")
    private String appName = "";
	
	public AgreementVO()
	{
		
	}
	
    
    /**
     * 产品行项目
     */
    @Ignore
    protected List<ProLineProjectVO> proLineList = null;
    
    /**
     * 付款行项目
     */
    @Ignore
    protected List<PayLineProjectVO> payLineProjectList = null;
    
    /**
     * 交付行项目
     */
    @Ignore
    protected List<TransLineProjectVO> transLineProjectList = null;
    
    /**
     * 开票行项目
     */
    @Ignore
    protected List<InvoiceLineProjectVO> invoiceLineProjectList = null;
    
    
	
	/**
     * 流程描述
     */
    @Ignore
    private String flowDescription = "";
	
	public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("AgreementVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("appName = ")
            .append(this.appName)
            .append(TAB)
            .append("processer = ")
            .append(this.processer)
            .append(TAB)
            .append("flowDescription = ")
            .append(this.flowDescription)
            .append(TAB)
             .append("proLineList = ")
            .append(this.proLineList)
            .append(TAB)
             .append("invoiceLineProjectList = ")
            .append(this.invoiceLineProjectList)
            .append(TAB)
             .append("transLineProjectList = ")
            .append(this.transLineProjectList)
            .append(TAB)
             .append("payLineProjectList = ")
            .append(this.payLineProjectList)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }


	public String getProcesser() {
		return processer;
	}

	public void setProcesser(String processer) {
		this.processer = processer;
	}

	public String getFlowDescription() {
		return flowDescription;
	}

	public void setFlowDescription(String flowDescription) {
		this.flowDescription = flowDescription;
	}


	public String getAppName() {
		return appName;
	}


	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	public List<ProLineProjectVO> getProLineList() {
		return proLineList;
	}

	public void setProLineList(List<ProLineProjectVO> proLineList) {
		this.proLineList = proLineList;
	}

	public List<PayLineProjectVO> getPayLineProjectList() {
		return payLineProjectList;
	}

	public void setPayLineProjectList(List<PayLineProjectVO> payLineProjectList) {
		this.payLineProjectList = payLineProjectList;
	}

	public List<TransLineProjectVO> getTransLineProjectList() {
		return transLineProjectList;
	}

	public void setTransLineProjectList(
			List<TransLineProjectVO> transLineProjectList) {
		this.transLineProjectList = transLineProjectList;
	}

	public List<InvoiceLineProjectVO> getInvoiceLineProjectList() {
		return invoiceLineProjectList;
	}

	public void setInvoiceLineProjectList(
			List<InvoiceLineProjectVO> invoiceLineProjectList) {
		this.invoiceLineProjectList = invoiceLineProjectList;
	}

}
