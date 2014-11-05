package com.china.center.oa.commission.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.StafferBean;

@Entity
@Table(name = "T_CENTER_BLACKRULE_STAFFER")
public class BlackRuleStafferBean implements Serializable
{
    @Id(autoIncrement=true)
    private String id = "";
    
    /**
     * 关联单号
     */
    @FK
    private String refId = "";
    
    /**
     * 职员
     */
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    private String stafferId = "";
    
    public BlackRuleStafferBean(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getStafferId() {
        return stafferId;
    }

    public void setStafferId(String stafferId) {
        this.stafferId = stafferId;
    };
    
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("BlackRuleStafferBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("refId = ")
            .append(this.refId)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)           
            .append(TAB)
            .append(" )");
            ;
        
        return retValue.toString();
    }
}
