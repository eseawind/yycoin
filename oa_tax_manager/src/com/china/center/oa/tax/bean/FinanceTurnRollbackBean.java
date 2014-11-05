package com.china.center.oa.tax.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;

@Entity
@Table(name="T_CENTER_FINANCETURN_ROLLBACK")
public class FinanceTurnRollbackBean implements Serializable 
{
    @Id(autoIncrement=true)
    private String id = "";
    
    @Unique
    private String monthKey = "";
    
    private String logTime = "";
    
    public FinanceTurnRollbackBean(){};

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonthKey() {
        return monthKey;
    }

    public void setMonthKey(String monthKey) {
        this.monthKey = monthKey;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }
    
    
}
