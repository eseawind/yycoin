package com.china.center.oa.finance.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.finance.constant.StockPayApplyConstant;
import com.china.center.oa.product.bean.ProviderBean;
import com.china.center.oa.publics.bean.InvoiceBean;
import com.china.center.oa.publics.bean.StafferBean;

/**
 * 同采购付款申请单
 * @author smart
 *
 */
@SuppressWarnings("serial")
@Entity(name = "采购预付款申请")
@Table(name = "T_CENTER_STOCKPREAPPLY")
public class StockPrePayApplyBean implements Serializable
{
	@Id
	private String id = "";
	
	private int status = StockPayApplyConstant.APPLY_STATUS_INIT;

	/**
	 * 发票类型
	 */
    @Join(tagClass = InvoiceBean.class, type = JoinType.LEFT)
    @Html(title = "发票类型", must = true, type = Element.SELECT)
    private String invoiceId = "";

    @FK
    @Join(tagClass = ProviderBean.class)
    @Html(title = "供应商", name = "providerName", must = true)
    private String providerId = "";
    
    @Join(tagClass = StafferBean.class)
    private String stafferId = "";

    @Html(title = "付款金额",type = Element.DOUBLE)
    private double moneys = 0.0d;

    @Html(title = "最迟付款日期", must = true, type = Element.DATE)
    private String payDate = "";

    @Html(title = "申请时间")
    private String logTime = "";

    /**
     * 付款单ID
     */
    private String inBillId = "";
    
    /**
     * 对冲采购付款申请，用预付款对冲采购付款申请
     */
    private double realMoneys = 0.0d;
    
    /**
     * 
     */
    @Html(title = "预付原因",type = Element.TEXTAREA)
    private String description = "";

	public StockPrePayApplyBean()
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
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return the status
	 */
	public int getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status)
	{
		this.status = status;
	}

	/**
	 * @return the invoiceId
	 */
	public String getInvoiceId()
	{
		return invoiceId;
	}

	/**
	 * @param invoiceId the invoiceId to set
	 */
	public void setInvoiceId(String invoiceId)
	{
		this.invoiceId = invoiceId;
	}

	/**
	 * @return the provideId
	 */
	public String getProviderId()
	{
		return providerId;
	}

	/**
	 * @param provideId the provideId to set
	 */
	public void setProviderId(String providerId)
	{
		this.providerId = providerId;
	}

	/**
	 * @return the stafferId
	 */
	public String getStafferId()
	{
		return stafferId;
	}

	/**
	 * @param stafferId the stafferId to set
	 */
	public void setStafferId(String stafferId)
	{
		this.stafferId = stafferId;
	}

	/**
	 * @return the moneys
	 */
	public double getMoneys()
	{
		return moneys;
	}

	/**
	 * @param moneys the moneys to set
	 */
	public void setMoneys(double moneys)
	{
		this.moneys = moneys;
	}

	/**
	 * @return the payDate
	 */
	public String getPayDate()
	{
		return payDate;
	}

	/**
	 * @param payDate the payDate to set
	 */
	public void setPayDate(String payDate)
	{
		this.payDate = payDate;
	}

	/**
	 * @return the logTime
	 */
	public String getLogTime()
	{
		return logTime;
	}

	/**
	 * @param logTime the logTime to set
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
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return the inBillId
	 */
	public String getInBillId()
	{
		return inBillId;
	}

	/**
	 * @param inBillId the inBillId to set
	 */
	public void setInBillId(String inBillId)
	{
		this.inBillId = inBillId;
	}

	/**
	 * @return the realMoneys
	 */
	public double getRealMoneys()
	{
		return realMoneys;
	}

	/**
	 * @param realMoneys the realMoneys to set
	 */
	public void setRealMoneys(double realMoneys)
	{
		this.realMoneys = realMoneys;
	}
}
