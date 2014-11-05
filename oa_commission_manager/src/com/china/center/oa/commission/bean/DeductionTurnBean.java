package com.china.center.oa.commission.bean;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@Entity(inherit=true)
@Table(name="T_CENTER_TURN_DEDUCTION")
public class DeductionTurnBean extends AbstractBean
{
    @Id(autoIncrement=true)
    private String id = "";
    
    @FK(index = AnoConstant.FK_FIRST)
    private String parentId = "";
    
    private double money = 0.0d;
    
    private String description = "";
    
    public DeductionTurnBean(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String toString()
    {
        final String TAB = ",";
        
        StringBuilder retValue = new StringBuilder();
        
        retValue
        .append("DeductionTurnBean ( ")
        .append(super.toString())
        .append(TAB)
        .append("id = ")
        .append(this.id)
        .append(TAB)
        .append("parentId = ")
        .append(this.parentId)        
        .append(TAB)
        .append("money = ")
        .append(this.money)
        .append(TAB)
        .append("description = ")
        .append(this.description)        
        .append(" )");
        
        return retValue.toString();
    }
}
