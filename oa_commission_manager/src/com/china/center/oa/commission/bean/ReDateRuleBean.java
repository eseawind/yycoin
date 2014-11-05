package com.china.center.oa.commission.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.publics.constant.StafferConstant;

@Entity
@Table(name = "T_CENTER_REDATE_RULE")
public class ReDateRuleBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";
    
    /**
     * 大于多少天
     */    
    private int leftDays = 0;
      
    /**
     * 小于多少天
     */
    private int rightDays = 0;
    
    /**
     * 金额
     */
    private double money = 0.0d;
    
    /**
     * 黑名单类型
     */
    private int blackType = StafferConstant.BLACK_YES;
    
    /**
     * 逻辑运算符, 0:||, 1:&&
     */
    private int logicOperation = 0;
    
    /**
     * 备注
     */
    private String description = "";
    
    public ReDateRuleBean()
    {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLeftDays() {
        return leftDays;
    }

    public void setLeftDays(int leftDays) {
        this.leftDays = leftDays;
    }

    public int getRightDays() {
        return rightDays;
    }

    public void setRightDays(int rightDays) {
        this.rightDays = rightDays;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getBlackType() {
        return blackType;
    }

    public void setBlackType(int blackType) {
        this.blackType = blackType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    };
    
    public int getLogicOperation() {
        return logicOperation;
    }

    public void setLogicOperation(int logicOperation) {
        this.logicOperation = logicOperation;
    }

    public String toString()
    {
        final String TAB = ",";
        
        StringBuilder retValue = new StringBuilder();
        
        retValue
        .append("ReDateRuleBean ( ")
        .append(super.toString())
        .append(TAB)
        .append("id = ")
        .append(this.id)
        .append(TAB)
        .append("leftDays = ")
        .append(this.leftDays)
        .append(TAB)
        .append("rightDays = ")
        .append(this.rightDays)
        .append(TAB)
        .append("money = ")
        .append(this.money)
        .append(TAB)
        .append("blackType = ")
        .append(this.blackType)
        .append(TAB)
        .append("description = ")
        .append(this.description)
        .append(" )");
        
        return retValue.toString();
    }
}
