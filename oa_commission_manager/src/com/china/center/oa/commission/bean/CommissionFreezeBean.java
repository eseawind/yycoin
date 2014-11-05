package com.china.center.oa.commission.bean;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@Entity(name = "提成冻结(缓发)", inherit = true)
@Table(name = "T_CENTER_COMMISSION_FREEZE")
public class CommissionFreezeBean extends AbstractBean 
{

    @Id(autoIncrement = true)
    private String id = "";
    
    /**
     * 关联ID
     */
    @FK(index = AnoConstant.FK_FIRST)
    private String parentid = "";
    
    /**
     * 冻结比例 0， 0.5， 1  三种
     */
    private double freezeRate = 0.0d;
    
    /**
     * 冻结金额 = （当月实发提成 + 上月冻结提成） * 冻结比例
     */
    private double freezeMoney = 0.0d;
    
    /**
     * 信用金额
     */
    private double creditMoney = 0.0d;
    
    /**
     * 信用额度
     */
    private double creditAmout = 0.0d;
    
    /**
     * 信用占比= 信用金额/信用额度
     */
    private double creditRate = 0.0d;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public double getFreezeRate() {
        return freezeRate;
    }

    public void setFreezeRate(double freezeRate) {
        this.freezeRate = freezeRate;
    }

    public double getFreezeMoney() {
        return freezeMoney;
    }

    public void setFreezeMoney(double freezeMoney) {
        this.freezeMoney = freezeMoney;
    }

    public double getCreditMoney() {
        return creditMoney;
    }

    public void setCreditMoney(double creditMoney) {
        this.creditMoney = creditMoney;
    }

    public double getCreditAmout() {
        return creditAmout;
    }

    public void setCreditAmout(double creditAmout) {
        this.creditAmout = creditAmout;
    }

    public double getCreditRate() {
        return creditRate;
    }

    public void setCreditRate(double creditRate) {
        this.creditRate = creditRate;
    }
    
    public String toString()
    {
        final String TAB = ",";
        
        StringBuilder retValue = new StringBuilder();
        
        retValue
        .append("CommissionFreezeBean ( ")
        .append(super.toString())
        .append(TAB)
        .append("id = ")
        .append(this.id)
        .append(TAB)
        .append("freezeRate = ")
        .append(this.freezeRate)
        .append(TAB)
        .append("freezeMoney = ")
        .append(this.freezeMoney)
        .append(TAB)
        .append("creditMoney = ")
        .append(this.creditMoney)
        .append(TAB)
        .append("creditAmout = ")
        .append(this.creditAmout)
        .append(TAB)
        .append("creditRate = ")
        .append(this.creditRate)
        .append(" )");
        
        return retValue.toString();
    }
}
