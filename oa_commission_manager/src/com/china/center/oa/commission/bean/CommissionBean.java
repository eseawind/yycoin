package com.china.center.oa.commission.bean;

import java.util.List;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Table;

@Entity(name = "提成结果", inherit=true)
@Table(name = "T_CENTER_COMMISSION")
public class CommissionBean extends AbstractBean 
{

    @Id(autoIncrement=true)
    private String id = "";
    
    @FK(index = AnoConstant.FK_FIRST)
    private String parentId = "";
    
    /**
     * 应发提成（佣金）
     */
    private double shouldCommission = 0.0d;
    
    /**
     * 实发提成
     */
    private double realCommission = 0.0d;
    
    /**
     * 毛利
     */
    private double profit = 0.0d;
    
    /**
     * 费用
     */
    private double fee = 0.0d;
    
    /**
     * 上月未扣
     */
    private double lastDeduction = 0.0d;
    
    /**
     * 应收成本
     */
    private double receiveCost = 0.0d;
    
    /**
     * KPI扣款
     */
    private double kpiDeduction = 0.0d;
    
    /**
     * 转入下月扣款
     */
    private double turnNextMonthDeduction = 0.0d;
    
    /**
     * 年度KPI返款
     */
    private double yearKpiMoney = 0.0d;
    
    /**
     * 报废
     */
    private double broken = 0.0d;
    
    /**
     * 坏账
     */
    private double baddebt = 0.0d;
    
    /**
     * 资金占用费
     */
    private double financeFee = 0.0d;
    
    /**
     * 代还借款
     */
    private double insteadPay = 0.0d;
    
    /**
     * 手续费考核
     */
    private double handFee = 0.0d;
    
    /**
     * 以前冻结提成
     */
    private double befFreeze = 0.0d;
    
    /**
     * 本月冻结提成
     */
    private double curFreeze = 0.0d;
    
    /**
     * 冻结后应发提成
     */
    private double finalCommission = 0.0d;
    
    @Ignore
    private List<ExternalStafferYearBean> externalStafferList = null;
    
    @Ignore
    private List<ExternalAssessBean> externalAssessList = null;
    
    public CommissionBean()
    {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getShouldCommission() {
        return shouldCommission;
    }

    public void setShouldCommission(double shouldCommission) {
        this.shouldCommission = shouldCommission;
    }

    public double getRealCommission() {
        return realCommission;
    }

    public void setRealCommission(double realCommission) {
        this.realCommission = realCommission;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getLastDeduction() {
        return lastDeduction;
    }

    public void setLastDeduction(double lastDeduction) {
        this.lastDeduction = lastDeduction;
    }

    public double getReceiveCost() {
        return receiveCost;
    }

    public void setReceiveCost(double receiveCost) {
        this.receiveCost = receiveCost;
    }

    public double getKpiDeduction() {
        return kpiDeduction;
    }

    public void setKpiDeduction(double kpiDeduction) {
        this.kpiDeduction = kpiDeduction;
    }

    public double getTurnNextMonthDeduction() {
        return turnNextMonthDeduction;
    }

    public void setTurnNextMonthDeduction(double turnNextMonthDeduction) {
        this.turnNextMonthDeduction = turnNextMonthDeduction;
    }

    public double getYearKpiMoney() {
        return yearKpiMoney;
    }

    public void setYearKpiMoney(double yearKpiMoney) {
        this.yearKpiMoney = yearKpiMoney;
    }

    public double getBroken() {
        return broken;
    }

    public void setBroken(double broken) {
        this.broken = broken;
    }

    public double getBaddebt() {
        return baddebt;
    }

    public void setBaddebt(double baddebt) {
        this.baddebt = baddebt;
    }

    public double getFinanceFee() {
        return financeFee;
    }

    public void setFinanceFee(double financeFee) {
        this.financeFee = financeFee;
    }
    
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public double getInsteadPay() {
        return insteadPay;
    }

    public void setInsteadPay(double insteadPay) {
        this.insteadPay = insteadPay;
    }

    public double getHandFee() {
        return handFee;
    }

    public void setHandFee(double handFee) {
        this.handFee = handFee;
    }

    public double getBefFreeze() {
        return befFreeze;
    }

    public void setBefFreeze(double befFreeze) {
        this.befFreeze = befFreeze;
    }

    public double getCurFreeze() {
        return curFreeze;
    }

    public void setCurFreeze(double curFreeze) {
        this.curFreeze = curFreeze;
    }

    public double getFinalCommission() {
        return finalCommission;
    }

    public void setFinalCommission(double finalCommission) {
        this.finalCommission = finalCommission;
    }

    public List<ExternalStafferYearBean> getExternalStafferList() {
        return externalStafferList;
    }

    public void setExternalStafferList(List<ExternalStafferYearBean> externalStafferList) {
        this.externalStafferList = externalStafferList;
    }

    public List<ExternalAssessBean> getExternalAssessList() {
        return externalAssessList;
    }

    public void setExternalAssessList(List<ExternalAssessBean> externalAssessList) {
        this.externalAssessList = externalAssessList;
    }

    public String toString()
    {
        final String TAB = ",";
        
        StringBuilder retValue = new StringBuilder();
        
        retValue
        .append("CommissionBean ( ")
        .append(super.toString())
        .append(TAB)
        .append("id = ")
        .append(this.id)
        .append(TAB)
        .append("parentId = ")
        .append(this.parentId)        
        .append(TAB)
        .append("shouldCommission = ")
        .append(this.shouldCommission)
        .append(TAB)
        .append("realCommission = ")
        .append(this.realCommission)
        .append(TAB)
        .append("profit = ")
        .append(this.profit)
        .append(TAB)
        .append("fee = ")
        .append(this.fee)
        .append(TAB)
        .append("lastDeduction = ")
        .append(this.lastDeduction)
        .append(TAB)
        .append("receiveCost = ")
        .append(this.receiveCost)
        .append(TAB)
        .append("KPIDeduction = ")
        .append(this.kpiDeduction)
        .append(TAB)
        .append("turnNextMonthDeduction = ")
        .append(this.turnNextMonthDeduction)
        .append(TAB)
        .append("YearKPIMoney = ")
        .append(this.yearKpiMoney)
        .append(TAB)
        .append("broken = ")
        .append(this.broken)
        .append(TAB)
        .append("baddebt = ")
        .append(this.baddebt)
        .append(TAB)
        .append("financeFee = ")
        .append(this.financeFee)
        .append(TAB)
        .append("externalStafferList = ")
        .append(this.externalStafferList)
        .append(TAB)
        .append("externalAssessList = ")
        .append(this.externalAssessList)
        .append(" )");
        
        return retValue.toString();
    }
}
