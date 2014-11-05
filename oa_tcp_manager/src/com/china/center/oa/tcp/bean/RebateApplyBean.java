package com.china.center.oa.tcp.bean;

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
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.tcp.constanst.TcpConstanst;

@SuppressWarnings("serial")
@Entity(name = "返利")
@Table(name = "T_CENTER_REBATE")
public class RebateApplyBean implements Serializable
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

    @Html(title = "申请事由", maxLength = 200, must = true, type = Element.TEXTAREA)
    private String description = "";

    @Html(title = "状态", must = true, type = Element.SELECT)
    private int status = TcpConstanst.TCP_STATUS_INIT;

    /**
     * TCP 类型
     */
    private int type = TcpConstanst.TCP_REBATETYPE;

    /**
     * 0:销售 1:职能 2:管理
     */
    @Html(title = "申请系列", must = true, type = Element.SELECT)
    private int stype = TcpConstanst.TCP_STYPE_SAIL;

    @Html(title = "开始日期", must = true, type = Element.DATE)
    private String beginDate = "";

    @Html(title = "申请类型", must = true, type = Element.SELECT)
    private int atype = TcpConstanst.REBATETYPE_CUSTOMIZE;
    
    private String customerId = "";
    
    @Html(title = "客户", must = true, maxLength = 200)
    private String customerName = "";
    
    private String outIds = "";
    
    /**
     * 申请返利金额
     */
    @Html(title = "申请返利金额", must = true, type = Element.DOUBLE)
    private long total = 0L;
    
    /**
     * 最终返利金额
     */
    @Html(title = "最终返利金额", type = Element.DOUBLE)
    private long lastMoney = 0L;
    
    private String dutyId = "";
    
    @Ignore
    private List<TravelApplyPayBean> payList = null;

	/**
	 * 
	 */
	public RebateApplyBean()
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
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the flowKey
	 */
	public String getFlowKey()
	{
		return flowKey;
	}

	/**
	 * @param flowKey the flowKey to set
	 */
	public void setFlowKey(String flowKey)
	{
		this.flowKey = flowKey;
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
	 * @return the departmentId
	 */
	public String getDepartmentId()
	{
		return departmentId;
	}

	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(String departmentId)
	{
		this.departmentId = departmentId;
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
	 * @return the type
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type)
	{
		this.type = type;
	}

	/**
	 * @return the stype
	 */
	public int getStype()
	{
		return stype;
	}

	/**
	 * @param stype the stype to set
	 */
	public void setStype(int stype)
	{
		this.stype = stype;
	}

	/**
	 * @return the beginDate
	 */
	public String getBeginDate()
	{
		return beginDate;
	}

	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(String beginDate)
	{
		this.beginDate = beginDate;
	}

	/**
	 * @return the atype
	 */
	public int getAtype()
	{
		return atype;
	}

	/**
	 * @param atype the atype to set
	 */
	public void setAtype(int atype)
	{
		this.atype = atype;
	}

	/**
	 * @return the customerId
	 */
	public String getCustomerId()
	{
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId)
	{
		this.customerId = customerId;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName()
	{
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	/**
	 * @return the outIds
	 */
	public String getOutIds()
	{
		return outIds;
	}

	/**
	 * @param outIds the outIds to set
	 */
	public void setOutIds(String outIds)
	{
		this.outIds = outIds;
	}

	/**
	 * @return the total
	 */
	public long getTotal()
	{
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(long total)
	{
		this.total = total;
	}

	/**
	 * @return the lastMoney
	 */
	public long getLastMoney()
	{
		return lastMoney;
	}

	/**
	 * @param lastMoney the lastMoney to set
	 */
	public void setLastMoney(long lastMoney)
	{
		this.lastMoney = lastMoney;
	}

	/**
	 * @return the payList
	 */
	public List<TravelApplyPayBean> getPayList()
	{
		return payList;
	}

	/**
	 * @param payList the payList to set
	 */
	public void setPayList(List<TravelApplyPayBean> payList)
	{
		this.payList = payList;
	}

	/**
	 * @return the dutyId
	 */
	public String getDutyId()
	{
		return dutyId;
	}

	/**
	 * @param dutyId the dutyId to set
	 */
	public void setDutyId(String dutyId)
	{
		this.dutyId = dutyId;
	}
}
