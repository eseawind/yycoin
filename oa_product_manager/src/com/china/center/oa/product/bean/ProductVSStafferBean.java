package com.china.center.oa.product.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.StafferBean;

@Entity(name = "产品人员关系")
@Table(name="t_product_vs_staffer")
public class ProductVSStafferBean implements Serializable{

    @Id(autoIncrement=true)
    private String id = "";
    
    @FK    
    private String refId = "";
    
    /**
     * 角色:0:项目经理、1:创意、2:设计、3:文案、4:质检、5:销售人员、6:管理人员
     */
    private int stafferRole = -1;

    /**
     * 提成比例 正整数  ‰(千分)
     */
    private int commissionRatio = 0;
    
    /**
     * 关联的人员
     */
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    private String stafferId = "";
    
    
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

    public int getStafferRole() {
        return stafferRole;
    }

    public void setStafferRole(int stafferRole) {
        this.stafferRole = stafferRole;
    }

    public int getCommissionRatio() {
        return commissionRatio;
    }

    public void setCommissionRatio(int commissionRatio) {
        this.commissionRatio = commissionRatio;
    }

    public String getStafferId() {
        return stafferId;
    }

    public void setStafferId(String stafferId) {
        this.stafferId = stafferId;
    }

    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("ProductVSStafferBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("refId = ")
            .append(this.refId)
            .append(TAB)
            .append("stafferRole = ")
            .append(this.stafferRole)
            .append(TAB)
            .append("commissionRatio = ")
            .append(this.commissionRatio)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
