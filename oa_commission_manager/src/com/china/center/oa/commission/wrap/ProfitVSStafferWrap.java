package com.china.center.oa.commission.wrap;

import java.io.Serializable;

public class ProfitVSStafferWrap implements Serializable 
{

    private String years = "";
    
    private String stafferName = "";
    
    private double curMoney = 0.0d;
    
    private double allMoneys = 0.0d;
    
    private String month = "";

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getStafferName() {
        return stafferName;
    }

    public void setStafferName(String stafferName) {
        this.stafferName = stafferName;
    }

    public double getCurMoney() {
        return curMoney;
    }

    public void setCurMoney(double curMoney) {
        this.curMoney = curMoney;
    }

    public double getAllMoneys() {
        return allMoneys;
    }

    public void setAllMoneys(double allMoneys) {
        this.allMoneys = allMoneys;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
    
    
}
