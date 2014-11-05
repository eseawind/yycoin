package com.china.center.oa.tax.vo;

import java.io.Serializable;

public class StafferUnitVO implements Serializable 
{

    private String stafferId = "";
    
    private String unitId = "";

    public String getStafferId() {
        return stafferId;
    }

    public void setStafferId(String stafferId) {
        this.stafferId = stafferId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
    
    
}
