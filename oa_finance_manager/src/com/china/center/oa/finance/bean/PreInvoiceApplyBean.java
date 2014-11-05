package com.china.center.oa.finance.bean;

import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;

@SuppressWarnings("serial")
@Entity(name = "预开票申请")
@Table(name = "T_CENTER_PREINVOICE")
public class PreInvoiceApplyBean implements Serializable
{
    @Id
    @Html(title = "标识", must = true, maxLength = 40)
    private String id = "";
    
    @Html(title = "目的", must = true, maxLength = 40)
    private String name = "";

    private String flowKey = "";

    @Html(title = "申请人", name = "stafferName", must = true, maxLength = 40, readonly = true)
    @Join(tagClass = StafferBean.class)
    private String stafferId = "";

    @Html(title = "部门", name = "departmentName", must = true, maxLength = 40, readonly = true)
    @Join(tagClass = PrincipalshipBean.class, type = JoinType.LEFT)
    private String departmentId = "";

    private String logTime = "";

    @Html(title = "备注", maxLength = 200, must = true, type = Element.TEXTAREA)
    private String description = "";

    @Html(title = "状态", must = true, type = Element.SELECT)
    private int status = 0;

    /**
     * TCP 类型
     */
    private int type = 22;

    /**
     * 0:销售 1:职能 2:管理
     */
    @Html(title = "申请系列", must = true, type = Element.SELECT)
    private int stype = 0;

    @Html(title = "开票类型", must = true, type = Element.SELECT)
    private int invoiceType = 0;
    
    private String customerId = "";
    
    @Html(title = "客户", must = true, maxLength = 200)
    private String customerName = "";
    
    /**
     * 申请返利金额
     */
    @Html(title = "开票金额", must = true, type = Element.DOUBLE)
    private long total = 0L;
    
    @Html(title = "已开金额", must = true, type = Element.DOUBLE)
    private long invoiceMoney = 0L;
    
    @Html(title = "开票品名", must = true, type = Element.INPUT)
    private String invoiceName = "";
    
    @Html(title = "纳税实体", must = true, type = Element.SELECT)
    private String dutyId = "";
    
    /**
     * 开票
     */
    private int otype = FinanceConstant.INVOICEINS_TYPE_OUT;
    
    /**
     * 计划开单日期
     */
    @Html(title = "计划开单日期", must = true, type = Element.DATE)
    private String planOutTime = "";
    
    @Ignore
    private List<PreInvoiceVSOutBean> vsList = null;

	/**
	 * 
	 */
	public PreInvoiceApplyBean()
	{
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getFlowKey()
	{
		return flowKey;
	}

	public void setFlowKey(String flowKey)
	{
		this.flowKey = flowKey;
	}

	public String getStafferId()
	{
		return stafferId;
	}

	public void setStafferId(String stafferId)
	{
		this.stafferId = stafferId;
	}

	public String getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(String departmentId)
	{
		this.departmentId = departmentId;
	}

	public String getLogTime()
	{
		return logTime;
	}

	public void setLogTime(String logTime)
	{
		this.logTime = logTime;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public int getStype()
	{
		return stype;
	}

	public void setStype(int stype)
	{
		this.stype = stype;
	}

	public int getInvoiceType()
	{
		return invoiceType;
	}

	public void setInvoiceType(int invoiceType)
	{
		this.invoiceType = invoiceType;
	}

	public String getCustomerId()
	{
		return customerId;
	}

	public void setCustomerId(String customerId)
	{
		this.customerId = customerId;
	}

	public String getCustomerName()
	{
		return customerName;
	}

	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	public long getTotal()
	{
		return total;
	}

	public void setTotal(long total)
	{
		this.total = total;
	}

	public String getInvoiceName()
	{
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName)
	{
		this.invoiceName = invoiceName;
	}

	public String getDutyId()
	{
		return dutyId;
	}

	public void setDutyId(String dutyId)
	{
		this.dutyId = dutyId;
	}

	public List<PreInvoiceVSOutBean> getVsList()
	{
		return vsList;
	}

	public void setVsList(List<PreInvoiceVSOutBean> vsList)
	{
		this.vsList = vsList;
	}

	public int getOtype()
	{
		return otype;
	}

	public void setOtype(int otype)
	{
		this.otype = otype;
	}

	public String getPlanOutTime()
	{
		return planOutTime;
	}

	public void setPlanOutTime(String planOutTime)
	{
		this.planOutTime = planOutTime;
	}

	public long getInvoiceMoney()
	{
		return invoiceMoney;
	}

	public void setInvoiceMoney(long invoiceMoney)
	{
		this.invoiceMoney = invoiceMoney;
	}
}
