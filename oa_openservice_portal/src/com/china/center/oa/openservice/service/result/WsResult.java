package com.china.center.oa.openservice.service.result;

import java.io.Serializable;

public class WsResult implements Serializable 
{

    private int ret = 0;
    
    private String departmentId = "";
    
    private String departmentName = "";

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
        
}
