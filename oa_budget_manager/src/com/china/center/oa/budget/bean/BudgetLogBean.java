/**
 * File Name: BudgetLogBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-6-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.bean;


import java.io.Serializable;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.budget.constant.BudgetConstant;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * BudgetLogBean
 * 
 * @author ZHUZHU
 * @version 2009-6-26
 * @see BudgetLogBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_BUDGETLOG")
public class BudgetLogBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    /**
     * 使用人
     */
    @Join(tagClass = StafferBean.class)
    private String stafferId = "";

    /**
     * 0:报销 1:收款单
     */
    private int fromType = BudgetConstant.BUDGETLOG_FROMTYPE_TCP;

    /**
     * 0:预占 1:实际已经使用
     */
    private int userType = BudgetConstant.BUDGETLOG_USERTYPE_PRE;

    /**
     * 0:正式 1:临时(只有正式的才能进入统计范围)
     */
    private int status = BudgetConstant.BUDGETLOG_STATUS_OK;

    /**
     * 关联bill
     */
    private String billId = "";

    /**
     * 多个关联的付款单
     */
    private String billIds = "";

    /**
     * 关联ID(一般是关联主单据ID)
     */
    @FK
    private String refId = "";

    /**
     * 关联具体的子项ID
     */
    @FK(index = AnoConstant.FK_FIRST)
    private String refSubId = "";

    /**
     * 准确到分
     */
    private long monery = 0L;

    /**
     * 备份一下
     */
    private long bakmonery = 0L;

    private String locationId = "";

    /**
     * 使用时间
     */
    private String logTime = "";

    /**
     * 预算
     */
    @Join(tagClass = BudgetBean.class)
    private String budgetId = "";

    /**
     * 预算子项ID
     */
    private String budgetItemId = "";

    /**
     * 公司预算
     */
    @Join(tagClass = BudgetBean.class, alias = "BU0")
    private String budgetId0 = "";

    /**
     * 事业部预算
     */
    @Join(tagClass = BudgetBean.class, alias = "BU1")
    private String budgetId1 = "";

    /**
     * 部门年度预算
     */
    @Join(tagClass = BudgetBean.class, alias = "BU2")
    private String budgetId2 = "";

    private String budgetItemId0 = "";

    private String budgetItemId1 = "";

    private String budgetItemId2 = "";

    /**
     * 部门ID
     */
    @Join(tagClass = PrincipalshipBean.class)
    private String departmentId = "";

    /**
     * 预算项ID(费用科目)
     */
    @Join(tagClass = FeeItemBean.class)
    private String feeItemId = "";

    private String log = "";

    /**
     * default constructor
     */
    public BudgetLogBean()
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
     * @return the fromType
     */
    public int getFromType()
    {
        return fromType;
    }

    /**
     * @param fromType
     *            the fromType to set
     */
    public void setFromType(int fromType)
    {
        this.fromType = fromType;
    }

    /**
     * @return the userType
     */
    public int getUserType()
    {
        return userType;
    }

    /**
     * @param userType
     *            the userType to set
     */
    public void setUserType(int userType)
    {
        this.userType = userType;
    }

    /**
     * @return the budgetId
     */
    public String getBudgetId()
    {
        return budgetId;
    }

    /**
     * @param budgetId
     *            the budgetId to set
     */
    public void setBudgetId(String budgetId)
    {
        this.budgetId = budgetId;
    }

    /**
     * @return the billId
     */
    public String getBillId()
    {
        return billId;
    }

    /**
     * @param billId
     *            the billId to set
     */
    public void setBillId(String billId)
    {
        this.billId = billId;
    }

    /**
     * @return the refId
     */
    public String getRefId()
    {
        return refId;
    }

    /**
     * @param refId
     *            the refId to set
     */
    public void setRefId(String refId)
    {
        this.refId = refId;
    }

    /**
     * @return the refSubId
     */
    public String getRefSubId()
    {
        return refSubId;
    }

    /**
     * @param refSubId
     *            the refSubId to set
     */
    public void setRefSubId(String refSubId)
    {
        this.refSubId = refSubId;
    }

    /**
     * @return the monery
     */
    public long getMonery()
    {
        return monery;
    }

    /**
     * @param monery
     *            the monery to set
     */
    public void setMonery(long monery)
    {
        this.monery = monery;
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
     * @return the budgetItemId
     */
    public String getBudgetItemId()
    {
        return budgetItemId;
    }

    /**
     * @param budgetItemId
     *            the budgetItemId to set
     */
    public void setBudgetItemId(String budgetItemId)
    {
        this.budgetItemId = budgetItemId;
    }

    /**
     * @return the feeItemId
     */
    public String getFeeItemId()
    {
        return feeItemId;
    }

    /**
     * @param feeItemId
     *            the feeItemId to set
     */
    public void setFeeItemId(String feeItemId)
    {
        this.feeItemId = feeItemId;
    }

    /**
     * @return the log
     */
    public String getLog()
    {
        return log;
    }

    /**
     * @param log
     *            the log to set
     */
    public void setLog(String log)
    {
        this.log = log;
    }

    /**
     * @return the departmentId
     */
    public String getDepartmentId()
    {
        return departmentId;
    }

    /**
     * @param departmentId
     *            the departmentId to set
     */
    public void setDepartmentId(String departmentId)
    {
        this.departmentId = departmentId;
    }

    /**
     * @return the billIds
     */
    public String getBillIds()
    {
        return billIds;
    }

    /**
     * @param billIds
     *            the billIds to set
     */
    public void setBillIds(String billIds)
    {
        this.billIds = billIds;
    }

    /**
     * @return the budgetId0
     */
    public String getBudgetId0()
    {
        return budgetId0;
    }

    /**
     * @param budgetId0
     *            the budgetId0 to set
     */
    public void setBudgetId0(String budgetId0)
    {
        this.budgetId0 = budgetId0;
    }

    /**
     * @return the budgetId1
     */
    public String getBudgetId1()
    {
        return budgetId1;
    }

    /**
     * @param budgetId1
     *            the budgetId1 to set
     */
    public void setBudgetId1(String budgetId1)
    {
        this.budgetId1 = budgetId1;
    }

    /**
     * @return the budgetId2
     */
    public String getBudgetId2()
    {
        return budgetId2;
    }

    /**
     * @param budgetId2
     *            the budgetId2 to set
     */
    public void setBudgetId2(String budgetId2)
    {
        this.budgetId2 = budgetId2;
    }

    /**
     * @return the budgetItemId0
     */
    public String getBudgetItemId0()
    {
        return budgetItemId0;
    }

    /**
     * @param budgetItemId0
     *            the budgetItemId0 to set
     */
    public void setBudgetItemId0(String budgetItemId0)
    {
        this.budgetItemId0 = budgetItemId0;
    }

    /**
     * @return the budgetItemId1
     */
    public String getBudgetItemId1()
    {
        return budgetItemId1;
    }

    /**
     * @param budgetItemId1
     *            the budgetItemId1 to set
     */
    public void setBudgetItemId1(String budgetItemId1)
    {
        this.budgetItemId1 = budgetItemId1;
    }

    /**
     * @return the budgetItemId2
     */
    public String getBudgetItemId2()
    {
        return budgetItemId2;
    }

    /**
     * @param budgetItemId2
     *            the budgetItemId2 to set
     */
    public void setBudgetItemId2(String budgetItemId2)
    {
        this.budgetItemId2 = budgetItemId2;
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
     * @return the bakmonery
     */
    public long getBakmonery()
    {
        return bakmonery;
    }

    /**
     * @param bakmonery
     *            the bakmonery to set
     */
    public void setBakmonery(long bakmonery)
    {
        this.bakmonery = bakmonery;
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
            .append("BudgetLogBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("fromType = ")
            .append(this.fromType)
            .append(TAB)
            .append("userType = ")
            .append(this.userType)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("billId = ")
            .append(this.billId)
            .append(TAB)
            .append("billIds = ")
            .append(this.billIds)
            .append(TAB)
            .append("refId = ")
            .append(this.refId)
            .append(TAB)
            .append("refSubId = ")
            .append(this.refSubId)
            .append(TAB)
            .append("monery = ")
            .append(this.monery)
            .append(TAB)
            .append("bakmonery = ")
            .append(this.bakmonery)
            .append(TAB)
            .append("locationId = ")
            .append(this.locationId)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("budgetId = ")
            .append(this.budgetId)
            .append(TAB)
            .append("budgetItemId = ")
            .append(this.budgetItemId)
            .append(TAB)
            .append("budgetId0 = ")
            .append(this.budgetId0)
            .append(TAB)
            .append("budgetId1 = ")
            .append(this.budgetId1)
            .append(TAB)
            .append("budgetId2 = ")
            .append(this.budgetId2)
            .append(TAB)
            .append("budgetItemId0 = ")
            .append(this.budgetItemId0)
            .append(TAB)
            .append("budgetItemId1 = ")
            .append(this.budgetItemId1)
            .append(TAB)
            .append("budgetItemId2 = ")
            .append(this.budgetItemId2)
            .append(TAB)
            .append("departmentId = ")
            .append(this.departmentId)
            .append(TAB)
            .append("feeItemId = ")
            .append(this.feeItemId)
            .append(TAB)
            .append("log = ")
            .append(this.log)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
