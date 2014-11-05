package com.china.center.oa.sail.bean;

import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.InvoiceBean;
import com.china.center.oa.sail.constanst.OutConstant;

@Entity
@Table(name = "t_center_outrepaire")
public class OutRepaireBean implements Serializable
{
	@Id
	private String id = "";
	
	/**
	 * 空开空退的原销售出库的单据
	 */
	private String outId = "";
	
	/**
	 * 类型，空开空退，将来可能还有换货（预留）
	 */
	private int type = 0;
	
    /**
     * 发票ID
     */
    @Join(tagClass = InvoiceBean.class, type = JoinType.LEFT)
    private String invoiceId = "";

    /**
     * 纳税实体
     */
    @Join(tagClass = DutyBean.class, type = JoinType.LEFT)
    private String dutyId = "";
	
    /**
     * 账期
     */
    private int reday = 0;

    /**
     * 回款日期
     */
    private String redate = "";
    
    /**
     * 状态
     */
    private int status = OutConstant.OUT_REPAIRE_SUBMIT;
    
    /**
     * 申请人
     */
    private String stafferName = "";

    /**
     * 申请人
     */
    private String stafferId = "";
    
    /**
     * 商务模式下，经办人
     */
    private String operator = "";
    
    /**
     * 商务模式下，经办人名称
     */
    private String operatorName = "";
    
    /**
     * 空开空退原因
     */
    private String reason = "";
    
    private String logTime = "";
    
    private String description = "";
    
	/**
	 * 新销售单
	 */
	private String newOutId = "";
	
	/**
	 * 产生的退货单
	 */
	private String retOutId = "";

	@Ignore
	private List<BaseRepaireBean> list = null;
    
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getOutId()
	{
		return outId;
	}

	public void setOutId(String outId)
	{
		this.outId = outId;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getInvoiceId()
	{
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId)
	{
		this.invoiceId = invoiceId;
	}

	public String getDutyId()
	{
		return dutyId;
	}

	public void setDutyId(String dutyId)
	{
		this.dutyId = dutyId;
	}

	public int getReday()
	{
		return reday;
	}

	public void setReday(int reday)
	{
		this.reday = reday;
	}

	public String getRedate()
	{
		return redate;
	}

	public void setRedate(String redate)
	{
		this.redate = redate;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getStafferName()
	{
		return stafferName;
	}

	public void setStafferName(String stafferName)
	{
		this.stafferName = stafferName;
	}

	public String getStafferId()
	{
		return stafferId;
	}

	public void setStafferId(String stafferId)
	{
		this.stafferId = stafferId;
	}

	public String getOperator()
	{
		return operator;
	}

	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	public String getOperatorName()
	{
		return operatorName;
	}

	public void setOperatorName(String operatorName)
	{
		this.operatorName = operatorName;
	}

	public String getReason()
	{
		return reason;
	}

	public void setReason(String reason)
	{
		this.reason = reason;
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
    
	public String getNewOutId()
	{
		return newOutId;
	}

	public void setNewOutId(String newOutId)
	{
		this.newOutId = newOutId;
	}

	public String getRetOutId()
	{
		return retOutId;
	}

	public void setRetOutId(String retOutId)
	{
		this.retOutId = retOutId;
	}

	public List<BaseRepaireBean> getList()
	{
		return list;
	}

	public void setList(List<BaseRepaireBean> list)
	{
		this.list = list;
	}

	public String toString()
	{

        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("OutRepaireBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("outId = ")
            .append(this.outId)
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("invoiceId = ")
            .append(this.invoiceId)
            .append(TAB)
            .append("dutyId = ")
            .append(this.dutyId)
            .append(TAB)
            .append("reday = ")
            .append(this.reday)
            .append(TAB)
            .append("redate = ")
            .append(this.redate)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("operator = ")
            .append(this.operator)
            .append(TAB)
            .append("operatorName = ")
            .append(this.operatorName)
            .append(TAB)
            .append("reason = ")
            .append(this.reason)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("newOutId = ")
            .append(this.newOutId)
            .append(TAB)
            .append("retOutId = ")
            .append(this.retOutId)
            .append(TAB)
            .append(" )");

        return retValue.toString();
	}
    
}
