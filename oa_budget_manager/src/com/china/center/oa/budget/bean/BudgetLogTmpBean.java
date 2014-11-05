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

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.budget.constant.BudgetConstant;

/**
 * 
 * 过期预占的预算在报销时流转到当前月，在稽核前的过度表
 * 
 * @author fangliwen 2012-6-20
 */
@Entity
@Table(name = "T_CENTER_BUDGETLOGTMP")
public class BudgetLogTmpBean implements Serializable {
    
    @Id(autoIncrement = true)
    private String  id           = "";
    
    
    private String budgetLogId   = "";

    /**
     * 0:正式 1:临时(只有正式的才能进入统计范围)
     */
    private int    status       = BudgetConstant.BUDGETLOG_STATUS_OK;

    /**
     * 关联ID(一般是关联主单据ID)
     */
    @FK
    private String refId        = "";

    /**
     * 准确到分
     */
    private long   monery       = 0L;

    /**
     * 预算
     */
    @Join(tagClass = BudgetBean.class)
    private String budgetId     = "";

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
     * 预算项ID(费用科目)
     */
    @Join(tagClass = FeeItemBean.class)
    private String feeItemId = "";
    
    
    /**
     * default constructor
     */
    public BudgetLogTmpBean() {
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public int getStatus() {
        return status;
    }


    public void setStatus(int status) {
        this.status = status;
    }


    public String getRefId() {
        return refId;
    }


    public void setRefId(String refId) {
        this.refId = refId;
    }


    public long getMonery() {
        return monery;
    }


    public void setMonery(long monery) {
        this.monery = monery;
    }


    public String getBudgetId() {
        return budgetId;
    }


    public void setBudgetId(String budgetId) {
        this.budgetId = budgetId;
    }


    public String getBudgetItemId() {
        return budgetItemId;
    }


    public void setBudgetItemId(String budgetItemId) {
        this.budgetItemId = budgetItemId;
    }

    public String getBudgetLogId() {
        return budgetLogId;
    }


    public void setBudgetLogId(String budgetLogId) {
        this.budgetLogId = budgetLogId;
    }


    public String getBudgetId0() {
        return budgetId0;
    }


    public void setBudgetId0(String budgetId0) {
        this.budgetId0 = budgetId0;
    }


    public String getBudgetId1() {
        return budgetId1;
    }


    public void setBudgetId1(String budgetId1) {
        this.budgetId1 = budgetId1;
    }


    public String getBudgetId2() {
        return budgetId2;
    }


    public void setBudgetId2(String budgetId2) {
        this.budgetId2 = budgetId2;
    }


    public String getBudgetItemId0() {
        return budgetItemId0;
    }


    public void setBudgetItemId0(String budgetItemId0) {
        this.budgetItemId0 = budgetItemId0;
    }


    public String getBudgetItemId1() {
        return budgetItemId1;
    }


    public void setBudgetItemId1(String budgetItemId1) {
        this.budgetItemId1 = budgetItemId1;
    }


    public String getBudgetItemId2() {
        return budgetItemId2;
    }


    public void setBudgetItemId2(String budgetItemId2) {
        this.budgetItemId2 = budgetItemId2;
    }


    public String getFeeItemId() {
        return feeItemId;
    }


    public void setFeeItemId(String feeItemId) {
        this.feeItemId = feeItemId;
    }


    /**
     * Constructs a <code>String</code> with all attributes in name = value
     * format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString() {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
        .append("BudgetLogTmpBean ( ")
        .append(super.toString())
        .append(TAB)
        .append("id = ")
        .append(this.id)
        .append(TAB)
        .append("budgetLogId = ")
        .append(this.budgetLogId)
        .append(TAB)
        .append("status = ")
        .append(this.status)
        .append(TAB)
        .append("refId = ")
        .append(this.refId)
        .append(TAB)
        .append("monery = ")
        .append(this.monery)
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
        .append("feeItemId = ")
        .append(this.feeItemId)
        .append(TAB)
        .append(" )");

        return retValue.toString();
    }

}
