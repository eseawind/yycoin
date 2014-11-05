package com.china.center.oa.commission.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@Entity
@Table(name = "T_CENTER_OUT_PAYCHANGED")
public class OutPayChangedBean implements Serializable 
{
    @Id
    private String id = "";
    
    /**
     * 已付款的销售单号
     */
    private String outId = "";
    
    public OutPayChangedBean()
    {
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }
    
    
}
