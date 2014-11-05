package com.china.center.oa.commission.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@Entity(inherit=true)
@Table(name="T_CENTER_EXTERNAL_ASSESS")
public class ExternalAssessBean extends AbstractBean
{
    @Id(autoIncrement=true)
    private String id = "";
    
    private double kpi = 0.0d;
    
    private double ratio = 0.0d;
    
    private double cost = 0.0d;
    
    private double yearKpi = 0.0d;
    
    private double shouldCommission = 0.0d;

    public ExternalAssessBean() {};
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getKpi() {
        return kpi;
    }

    public void setKpi(double kpi) {
        this.kpi = kpi;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getYearKpi() {
        return yearKpi;
    }

    public void setYearKpi(double yearKpi) {
        this.yearKpi = yearKpi;
    }

    public double getShouldCommission() {
        return shouldCommission;
    }

    public void setShouldCommission(double shouldCommission) {
        this.shouldCommission = shouldCommission;
    }
    
    public String toString()
    {
        final String TAB = ",";
        
        StringBuilder retValue = new StringBuilder();
        
        retValue
        .append("ExternalAssessBean ( ")
        .append(super.toString())
        .append(TAB)
        .append("id = ")
        .append(this.id)
        .append(TAB)
        .append("KPI = ")
        .append(this.kpi)
        .append(TAB)
        .append("KPI = ")
        .append(this.kpi)
        .append(TAB)
        .append("ratio = ")
        .append(this.ratio)
        .append(TAB)
        .append("cost = ")
        .append(this.cost) 
        .append(TAB)
        .append("yearKPI = ")
        .append(this.yearKpi) 
        .append(TAB)
        .append("shouldCommission = ")
        .append(this.shouldCommission)         
        .append(" )");
        
        return retValue.toString();
    }
    
    
}
