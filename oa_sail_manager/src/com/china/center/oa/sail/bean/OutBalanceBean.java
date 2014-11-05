/**
 * File Name: OutBalanceBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.bean;


import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.sail.constanst.OutConstant;


/**
 * OutBalanceBean(委托结算单)
 * 
 * @author ZHUZHU
 * @version 2010-12-4
 * @see OutBalanceBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_OUTBALANCE")
public class OutBalanceBean implements Serializable
{
    @Id
    private String id = "";

    @FK
    @Join(tagClass = OutBean.class)
    private String outId = "";

    private double total = 0.0d;

    private int status = OutConstant.OUTBALANCE_STATUS_SUBMIT;

    private int type = OutConstant.OUTBALANCE_TYPE_SAIL;

    private int mtype = PublicConstant.MANAGER_TYPE_COMMON;

    /**
     * 0:没有付款 1:付款
     */
    private int pay = OutConstant.PAY_NOT;

    /**
     * 已经支付的金额
     */
    private double payMoney = 0.0d;

    private String logTime = "";

    @Join(tagClass = CustomerBean.class)
    private String customerId = "";

    @Join(tagClass = StafferBean.class)
    private String stafferId = "";

    private String description = "";

    private String reason = "";

    /**
     * 开发票状态(0:没有开票 1:全部开票)
     */
    private int invoiceStatus = OutConstant.INVOICESTATUS_INIT;

    /**
     * 开发票的金额(已经开票的金额)
     */
    private double invoiceMoney = 0.0d;

    /**
     * 退货库
     */
    @Join(tagClass = DepotBean.class, type = JoinType.LEFT)
    private String dirDepot = "";

    /**
     * 总部核对信息
     */
    private String checks = "";

    private int checkStatus = PublicConstant.CHECK_STATUS_INIT;
    
    /**
     * 经办人
     */
    private String operator = "";
    
    /**
     * 经办人
     */
    private String operatorName = "";
    
    /**
     * 结算单退货时记录原结算单号
     */
    @FK(index = AnoConstant.FK_FIRST)
    private String refOutBalanceId = "";
    
    private String industryId = "";
    
    /**
     * 是否参与返利
     */
    private int hasRebate = OutConstant.OUT_REBATE_NO;
    
    /**
     * 勾款与开标纳税实体与管理属性,票款一致的保证
     */
    /**
     * pi: Pay & invoice 勾款或开票最终确定的纳税实体
     */
    private String piDutyId = "";
    
    /**
     * 勾款或开票的最终管理属性 PublicConstant.MANAGER_TYPE_COMMON
     */
    private int piMtype = -1;
    
    /**
     * 勾款或开票 OutConstant.OUT_PAYINS_TYPE_PAY
     */
    private int piType = -1;
    
    /**
     * 勾款或开票是否结束
     */
    private int piStatus = -1;
    
    /**
     * 核销发票金额
     */
    private double hasConfirmInsMoney = 0.0d;
    
    /**
     * 是否已核销完全
     */
    private int hasConfirm = 0;
    
    @Ignore
    private List<BaseBalanceBean> baseBalanceList = null;

    /**
     * default constructor
     */
    public OutBalanceBean()
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
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the outId
     */
    public String getOutId()
    {
        return outId;
    }

    /**
     * @param outId
     *            the outId to set
     */
    public void setOutId(String outId)
    {
        this.outId = outId;
    }

    /**
     * @return the total
     */
    public double getTotal()
    {
        return total;
    }

    /**
     * @param total
     *            the total to set
     */
    public void setTotal(double total)
    {
        this.total = total;
    }

    /**
     * @return the status
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status)
    {
        this.status = status;
    }

    /**
     * @return the logTime
     */
    public String getLogTime()
    {
        return logTime;
    }

    /**
     * @param logTime
     *            the logTime to set
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
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the baseBalanceList
     */
    public List<BaseBalanceBean> getBaseBalanceList()
    {
        return baseBalanceList;
    }

    /**
     * @param baseBalanceList
     *            the baseBalanceList to set
     */
    public void setBaseBalanceList(List<BaseBalanceBean> baseBalanceList)
    {
        this.baseBalanceList = baseBalanceList;
    }

    /**
     * @return the stafferId
     */
    public String getStafferId()
    {
        return stafferId;
    }

    /**
     * @param stafferId
     *            the stafferId to set
     */
    public void setStafferId(String stafferId)
    {
        this.stafferId = stafferId;
    }

    /**
     * @return the reason
     */
    public String getReason()
    {
        return reason;
    }

    /**
     * @param reason
     *            the reason to set
     */
    public void setReason(String reason)
    {
        this.reason = reason;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return the dirDepot
     */
    public String getDirDepot()
    {
        return dirDepot;
    }

    /**
     * @param dirDepot
     *            the dirDepot to set
     */
    public void setDirDepot(String dirDepot)
    {
        this.dirDepot = dirDepot;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId()
    {
        return customerId;
    }

    /**
     * @param customerId
     *            the customerId to set
     */
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }

    /**
     * @return the invoiceStatus
     */
    public int getInvoiceStatus()
    {
        return invoiceStatus;
    }

    /**
     * @param invoiceStatus
     *            the invoiceStatus to set
     */
    public void setInvoiceStatus(int invoiceStatus)
    {
        this.invoiceStatus = invoiceStatus;
    }

    /**
     * @return the invoiceMoney
     */
    public double getInvoiceMoney()
    {
        return invoiceMoney;
    }

    /**
     * @param invoiceMoney
     *            the invoiceMoney to set
     */
    public void setInvoiceMoney(double invoiceMoney)
    {
        this.invoiceMoney = invoiceMoney;
    }

    /**
     * @return the pay
     */
    public int getPay()
    {
        return pay;
    }

    /**
     * @param pay
     *            the pay to set
     */
    public void setPay(int pay)
    {
        this.pay = pay;
    }

    /**
     * @return the payMoney
     */
    public double getPayMoney()
    {
        return payMoney;
    }

    /**
     * @param payMoney
     *            the payMoney to set
     */
    public void setPayMoney(double payMoney)
    {
        this.payMoney = payMoney;
    }

    /**
     * @return the checks
     */
    public String getChecks()
    {
        return checks;
    }

    /**
     * @param checks
     *            the checks to set
     */
    public void setChecks(String checks)
    {
        this.checks = checks;
    }

    /**
     * @return the checkStatus
     */
    public int getCheckStatus()
    {
        return checkStatus;
    }

    /**
     * @param checkStatus
     *            the checkStatus to set
     */
    public void setCheckStatus(int checkStatus)
    {
        this.checkStatus = checkStatus;
    }

    /**
     * @return the mtype
     */
    public int getMtype()
    {
        return mtype;
    }

    /**
     * @param mtype
     *            the mtype to set
     */
    public void setMtype(int mtype)
    {
        this.mtype = mtype;
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

	public String getRefOutBalanceId()
	{
		return refOutBalanceId;
	}

	public void setRefOutBalanceId(String refOutBalanceId)
	{
		this.refOutBalanceId = refOutBalanceId;
	}

	public String getIndustryId()
	{
		return industryId;
	}

	public void setIndustryId(String industryId)
	{
		this.industryId = industryId;
	}

	/**
	 * @return the hasRebate
	 */
	public int getHasRebate()
	{
		return hasRebate;
	}

	/**
	 * @param hasRebate the hasRebate to set
	 */
	public void setHasRebate(int hasRebate)
	{
		this.hasRebate = hasRebate;
	}

	/**
	 * @return the piDutyId
	 */
	public String getPiDutyId()
	{
		return piDutyId;
	}

	/**
	 * @param piDutyId the piDutyId to set
	 */
	public void setPiDutyId(String piDutyId)
	{
		this.piDutyId = piDutyId;
	}

	/**
	 * @return the piMtype
	 */
	public int getPiMtype()
	{
		return piMtype;
	}

	/**
	 * @param piMtype the piMtype to set
	 */
	public void setPiMtype(int piMtype)
	{
		this.piMtype = piMtype;
	}

	/**
	 * @return the piType
	 */
	public int getPiType()
	{
		return piType;
	}

	/**
	 * @param piType the piType to set
	 */
	public void setPiType(int piType)
	{
		this.piType = piType;
	}

	/**
	 * @return the piStatus
	 */
	public int getPiStatus()
	{
		return piStatus;
	}

	/**
	 * @param piStatus the piStatus to set
	 */
	public void setPiStatus(int piStatus)
	{
		this.piStatus = piStatus;
	}

	/**
	 * @return the hasConfirmInsMoney
	 */
	public double getHasConfirmInsMoney()
	{
		return hasConfirmInsMoney;
	}

	/**
	 * @param hasConfirmInsMoney the hasConfirmInsMoney to set
	 */
	public void setHasConfirmInsMoney(double hasConfirmInsMoney)
	{
		this.hasConfirmInsMoney = hasConfirmInsMoney;
	}

	/**
	 * @return the hasConfirm
	 */
	public int getHasConfirm()
	{
		return hasConfirm;
	}

	/**
	 * @param hasConfirm the hasConfirm to set
	 */
	public void setHasConfirm(int hasConfirm)
	{
		this.hasConfirm = hasConfirm;
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
            .append("OutBalanceBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("outId = ")
            .append(this.outId)
            .append(TAB)
            .append("total = ")
            .append(this.total)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("mtype = ")
            .append(this.mtype)
            .append(TAB)
            .append("pay = ")
            .append(this.pay)
            .append(TAB)
            .append("payMoney = ")
            .append(this.payMoney)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("customerId = ")
            .append(this.customerId)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("reason = ")
            .append(this.reason)
            .append(TAB)
            .append("invoiceStatus = ")
            .append(this.invoiceStatus)
            .append(TAB)
            .append("invoiceMoney = ")
            .append(this.invoiceMoney)
            .append(TAB)
            .append("dirDepot = ")
            .append(this.dirDepot)
            .append(TAB)
            .append("checks = ")
            .append(this.checks)
            .append(TAB)
            .append("checkStatus = ")
            .append(this.checkStatus)
            .append(TAB)
            .append("operator = ")
            .append(this.operator)
            .append(TAB)
            .append("operatorName = ")
            .append(this.operatorName)
            .append(TAB)
            .append("refOutBalanceId = ")
            .append(this.refOutBalanceId)            
            .append(TAB)
            .append("baseBalanceList = ")
            .append(this.baseBalanceList)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
