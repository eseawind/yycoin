/**
 * File Name: BillBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-25<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.bean;


import java.io.Serializable;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.Column;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.bean.UnitViewBean;


/**
 * CHECK InBillBean
 * 
 * @author ZHUZHU
 * @version 2010-12-25
 * @see InBillBean
 * @since 3.0
 */
@Entity(name = "收款单")
@Table(name = "T_CENTER_INBILL")
public class InBillBean implements Serializable
{
    @Id
    private String id = "";

    @Html(title = "类型", type = Element.SELECT)
    private int type = FinanceConstant.INBILL_TYPE_SAILOUT;

    @Html(title = "管理类型", must = true, type = Element.SELECT)
    private int mtype = PublicConstant.MANAGER_TYPE_COMMON;

    /**
     * 账户纳税实体
     */
    @Join(tagClass = DutyBean.class)
    private String dutyId = "";
    
    /**
     * 已经收取 预收(关联的销售单还没有正式生效) 未关联(还没有和销售单关联)(这个只有在销售收入下才有意义哦)
     */
    @Html(title = "状态", type = Element.SELECT)
    private int status = FinanceConstant.INBILL_STATUS_PAYMENTS;

    /**
     * 0:自动 1:手工
     */
    private int createType = FinanceConstant.BILL_CREATETYPE_AUTO;

    /**
     * 是否被统计锁定
     */
    @Column(name = "ulock")
    @Html(title = "锁定", type = Element.SELECT)
    private int lock = FinanceConstant.BILL_LOCK_NO;

    @Html(title = "帐户", type = Element.SELECT, must = true)
    @Join(tagClass = BankBean.class)
    private String bankId = "";

    /**
     * outId
     */
    @FK
    @Join(tagClass = OutBean.class, type = JoinType.LEFT)
    private String outId = "";

    /**
     * 结算单
     */
    @FK(index = AnoConstant.FK_SECOND)
    private String outBalanceId = "";

    @Html(title = "金额", type = Element.DOUBLE, must = true)
    private double moneys = 0.0d;

    @Html(title = "原始金额", type = Element.DOUBLE, must = true)
    private double srcMoneys = 0.0d;

    /**
     * 单位
     */
    @Html(title = "单位", name = "customerName", readonly = true, must = true)
    @Join(tagClass = UnitViewBean.class, type = JoinType.LEFT)
    private String customerId = "";

    /**
     * 单据生成人
     */
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT, alias = "SB1")
    private String stafferId = "";

    /**
     * 属于哪个职员的
     */
    @Html(title = "所属职员", name = "ownerName", readonly = true)
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT, alias = "SB2")
    private String ownerId = "";

    private String locationId = "";

    private String destBankId = "";

    /**
     * 转账关联
     */
    private String refBillId = "";

    @FK(index = AnoConstant.FK_FIRST)
    @Join(tagClass = PaymentBean.class, type = JoinType.LEFT)
    private String paymentId = "";

    private String logTime = "";

    @Html(title = "备注", maxLength = 200, type = Element.TEXTAREA)
    private String description = "";

    /**
     * 总部核对信息
     */
    private String checks = "";

    private int checkStatus = PublicConstant.CHECK_STATUS_END;

    /**
     * 销售单核对更新ID
     */
    private int updateId = 0;
    
    /**
     * 预收冻结: 冻结的预收不可以参加自动勾款
     * 0:未冻结 1:冻结
     * default 0
     */
    private int freeze = 0;
    

    /**
     * default constructor
     */
    public InBillBean()
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
     * @return the bankId
     */
    public String getBankId()
    {
        return bankId;
    }

    /**
     * @param bankId
     *            the bankId to set
     */
    public void setBankId(String bankId)
    {
        this.bankId = bankId;
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
     * @return the moneys
     */
    public double getMoneys()
    {
        return moneys;
    }

    /**
     * @param moneys
     *            the moneys to set
     */
    public void setMoneys(double moneys)
    {
        this.moneys = moneys;
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
     * @return the locationId
     */
    public String getLocationId()
    {
        return locationId;
    }

    /**
     * @param locationId
     *            the locationId to set
     */
    public void setLocationId(String locationId)
    {
        this.locationId = locationId;
    }

    /**
     * @return the destBankId
     */
    public String getDestBankId()
    {
        return destBankId;
    }

    /**
     * @param destBankId
     *            the destBankId to set
     */
    public void setDestBankId(String destBankId)
    {
        this.destBankId = destBankId;
    }

    /**
     * @return the refBillId
     */
    public String getRefBillId()
    {
        return refBillId;
    }

    /**
     * @param refBillId
     *            the refBillId to set
     */
    public void setRefBillId(String refBillId)
    {
        this.refBillId = refBillId;
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
     * @return the paymentId
     */
    public String getPaymentId()
    {
        return paymentId;
    }

    /**
     * @param paymentId
     *            the paymentId to set
     */
    public void setPaymentId(String paymentId)
    {
        this.paymentId = paymentId;
    }

    /**
     * @return the ownerId
     */
    public String getOwnerId()
    {
        return ownerId;
    }

    /**
     * @param ownerId
     *            the ownerId to set
     */
    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
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
     * @return the lock
     */
    public int getLock()
    {
        return lock;
    }

    /**
     * @param lock
     *            the lock to set
     */
    public void setLock(int lock)
    {
        this.lock = lock;
    }

    /**
     * @return the outBalanceId
     */
    public String getOutBalanceId()
    {
        return outBalanceId;
    }

    /**
     * @param outBalanceId
     *            the outBalanceId to set
     */
    public void setOutBalanceId(String outBalanceId)
    {
        this.outBalanceId = outBalanceId;
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
     * @return the srcMoneys
     */
    public double getSrcMoneys()
    {
        return srcMoneys;
    }

    /**
     * @param srcMoneys
     *            the srcMoneys to set
     */
    public void setSrcMoneys(double srcMoneys)
    {
        this.srcMoneys = srcMoneys;
    }

    /**
     * @return the updateId
     */
    public int getUpdateId()
    {
        return updateId;
    }

    /**
     * @param updateId
     *            the updateId to set
     */
    public void setUpdateId(int updateId)
    {
        this.updateId = updateId;
    }

    /**
     * @return the createType
     */
    public int getCreateType()
    {
        return createType;
    }

    /**
     * @param createType
     *            the createType to set
     */
    public void setCreateType(int createType)
    {
        this.createType = createType;
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

	/**
	 * @return the freeze
	 */
	public int getFreeze()
	{
		return freeze;
	}

	/**
	 * @param freeze the freeze to set
	 */
	public void setFreeze(int freeze)
	{
		this.freeze = freeze;
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
            .append("InBillBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("mtype = ")
            .append(this.mtype)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("createType = ")
            .append(this.createType)
            .append(TAB)
            .append("lock = ")
            .append(this.lock)
            .append(TAB)
            .append("bankId = ")
            .append(this.bankId)
            .append(TAB)
            .append("outId = ")
            .append(this.outId)
            .append(TAB)
            .append("outBalanceId = ")
            .append(this.outBalanceId)
            .append(TAB)
            .append("moneys = ")
            .append(this.moneys)
            .append(TAB)
            .append("srcMoneys = ")
            .append(this.srcMoneys)
            .append(TAB)
            .append("customerId = ")
            .append(this.customerId)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("ownerId = ")
            .append(this.ownerId)
            .append(TAB)
            .append("locationId = ")
            .append(this.locationId)
            .append(TAB)
            .append("destBankId = ")
            .append(this.destBankId)
            .append(TAB)
            .append("refBillId = ")
            .append(this.refBillId)
            .append(TAB)
            .append("paymentId = ")
            .append(this.paymentId)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("checks = ")
            .append(this.checks)
            .append(TAB)
            .append("checkStatus = ")
            .append(this.checkStatus)
            .append(TAB)
            .append("updateId = ")
            .append(this.updateId)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
