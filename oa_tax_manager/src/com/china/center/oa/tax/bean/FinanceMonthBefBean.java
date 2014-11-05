package com.china.center.oa.tax.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;

/**
 * 财务上线前 补之前的月结项
 * 
 * @author 
 * @version 2011-7-27
 * @see FinanceMonthBefBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_FINANCEMONTH_BEF")
public class FinanceMonthBefBean implements Serializable {
    
    @Id(autoIncrement = true)
    private String id        = "";

    private int    type      = 0;

    private String monthKey  = "";

    private String month     = "";

    private String year      = "";

    /**
     * 当月发生
     */
    private long   money     = 0L;

    @Unique(dependFields = "monthKey")
    private int    itemIndex = 0;

    private String itemName  = "";

    private String logTime   = "";

    private String lastOpr   = "";

    /**
     * default constructor
     */
    public FinanceMonthBefBean() {
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMonthKey() {
        return monthKey;
    }

    public void setMonthKey(String monthKey) {
        this.monthKey = monthKey;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public int getItemIndex() {
        return itemIndex;
    }

    public void setItemIndex(int itemIndex) {
        this.itemIndex = itemIndex;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getLastOpr() {
        return lastOpr;
    }

    public void setLastOpr(String lastOpr) {
        this.lastOpr = lastOpr;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value
     * format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString() {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue.append("FinanceMonthBean ( ").append(super.toString()).append(TAB).append("id = ")
                .append(this.id).append(TAB).append("type = ").append(this.type).append(TAB)
                .append("itemIndex = ").append(this.itemIndex).append(TAB).append("itemName = ")
                .append(this.itemName).append(TAB).append("money = ").append(this.money)
                .append(TAB).append(TAB).append("monthKey = ").append(this.monthKey).append(TAB)
                .append("logTime = ").append(this.logTime).append(TAB).append("lastOpr = ")
                .append(this.lastOpr).append(TAB).append(" )");

        return retValue.toString();
    }

}
