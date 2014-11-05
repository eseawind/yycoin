/**
 * File Name: BudgetBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-12-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.bean;


import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.JCheck;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.oa.budget.constant.BudgetConstant;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * BudgetBean
 * 
 * @author ZHUZHU
 * @version 2008-12-2
 * @see BudgetBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_BUDGET")
public class BudgetBean implements Serializable
{
    @Id
    private String id = "";

    @Unique
    @Html(title = "预算标题", must = true, maxLength = 80)
    private String name = "";

    @FK
    @Html(title = "父级预算", must = true, name = "parentName")
    @Join(tagClass = BudgetBean.class, alias = "BudgetBean2")
    private String parentId = "0";

    @Join(tagClass = StafferBean.class)
    private String stafferId = "";

    /**
     * 权签人
     */
    @Html(title = "权签人", name = "signerName", must = true, readonly = true)
    @Join(tagClass = StafferBean.class, alias = "SIGN")
    private String signer = "";

    @Join(tagClass = LocationBean.class)
    private String locationId = "";

    private String logTime = "";

    @Html(title = "预算部门", name = "budgetDepartmentName", must = true, readonly = true)
    @Join(tagClass = PrincipalshipBean.class)
    private String budgetDepartment = "";

    @Html(title = "预算年度", must = true, type = Element.SELECT)
    private String year = "";

    @Html(title = "开始日期", must = true, type = Element.DATE)
    private String beginDate = "";

    @Html(title = "结束日期", must = true, type = Element.DATE)
    private String endDate = "";

    @Html(title = "状态", must = true, type = Element.SELECT)
    private int status = BudgetConstant.BUDGET_STATUS_INIT;

    /**
     * 预算使用控制
     */
    @Html(title = "执行状态", must = true, type = Element.SELECT)
    private int carryStatus = BudgetConstant.BUDGET_CARRY_INIT;

    @Html(title = "预算类型", must = true, type = Element.SELECT)
    private int type = BudgetConstant.BUDGET_TYPE_COMPANY;

    @Html(title = "预算分类", must = true, type = Element.SELECT)
    private int level = BudgetConstant.BUDGET_LEVEL_YEAR;

    /**
     * 预算总金额
     */
    private double total = 0.0d;

    @Html(title = "销售总额", must = true, maxLength = 20, oncheck = JCheck.ONLY_FLOAT)
    private double sail = 0.0d;

    @Html(title = "毛利率", must = true, maxLength = 20, oncheck = JCheck.ONLY_FLOAT)
    private double orgProfit = 0.0d;

    @Html(title = "净利率", must = true, maxLength = 20, oncheck = JCheck.ONLY_FLOAT)
    private double realProfit = 0.0d;

    @Html(title = "库存限额", must = true, maxLength = 20, oncheck = JCheck.ONLY_FLOAT)
    private double outSave = 0.0d;

    @Html(title = "最大借款", must = true, maxLength = 20, oncheck = JCheck.ONLY_FLOAT)
    private double outMoney = 0.0d;

    @Html(title = "最小收款", must = true, maxLength = 20, oncheck = JCheck.ONLY_FLOAT)
    private double inMoney = 0.0d;

    /**
     * 实际使用金额（在结束的时候会统计）
     */
    private double realMonery = 0.0d;

    @Html(title = "描述", type = Element.TEXTAREA, maxLength = 200)
    private String description = "";

    @Ignore
    private List<BudgetItemBean> items = null;

    public BudgetBean()
    {
    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the stafferId
     */
    public String getStafferId()
    {
        return stafferId;
    }

    /**
     * @param stafferId
     *            the stafferId to set
     */
    public void setStafferId(String stafferId)
    {
        this.stafferId = stafferId;
    }

    /**
     * @return the locationId
     */
    public String getLocationId()
    {
        return locationId;
    }

    /**
     * @param locationId
     *            the locationId to set
     */
    public void setLocationId(String locationId)
    {
        this.locationId = locationId;
    }

    /**
     * @return the logTime
     */
    public String getLogTime()
    {
        return logTime;
    }

    /**
     * @param logTime
     *            the logTime to set
     */
    public void setLogTime(String logTime)
    {
        this.logTime = logTime;
    }

    /**
     * @return the beginDate
     */
    public String getBeginDate()
    {
        return beginDate;
    }

    /**
     * @param beginDate
     *            the beginDate to set
     */
    public void setBeginDate(String beginDate)
    {
        this.beginDate = beginDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate()
    {
        return endDate;
    }

    /**
     * @param endDate
     *            the endDate to set
     */
    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    /**
     * @return the status
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status)
    {
        this.status = status;
    }

    /**
     * @return the carryStatus
     */
    public int getCarryStatus()
    {
        return carryStatus;
    }

    /**
     * @param carryStatus
     *            the carryStatus to set
     */
    public void setCarryStatus(int carryStatus)
    {
        this.carryStatus = carryStatus;
    }

    /**
     * @return the total
     */
    public double getTotal()
    {
        return total;
    }

    /**
     * @param total
     *            the total to set
     */
    public void setTotal(double total)
    {
        this.total = total;
    }

    /**
     * @return the realMonery
     */
    public double getRealMonery()
    {
        return realMonery;
    }

    /**
     * @param realMonery
     *            the realMonery to set
     */
    public void setRealMonery(double realMonery)
    {
        this.realMonery = realMonery;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the items
     */
    public List<BudgetItemBean> getItems()
    {
        return items;
    }

    /**
     * @param items
     *            the items to set
     */
    public void setItems(List<BudgetItemBean> items)
    {
        this.items = items;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return the level
     */
    public int getLevel()
    {
        return level;
    }

    /**
     * @param level
     *            the level to set
     */
    public void setLevel(int level)
    {
        this.level = level;
    }

    /**
     * @return the sail
     */
    public double getSail()
    {
        return sail;
    }

    /**
     * @param sail
     *            the sail to set
     */
    public void setSail(double sail)
    {
        this.sail = sail;
    }

    /**
     * @return the orgProfit
     */
    public double getOrgProfit()
    {
        return orgProfit;
    }

    /**
     * @param orgProfit
     *            the orgProfit to set
     */
    public void setOrgProfit(double orgProfit)
    {
        this.orgProfit = orgProfit;
    }

    /**
     * @return the realProfit
     */
    public double getRealProfit()
    {
        return realProfit;
    }

    /**
     * @param realProfit
     *            the realProfit to set
     */
    public void setRealProfit(double realProfit)
    {
        this.realProfit = realProfit;
    }

    /**
     * @return the outSave
     */
    public double getOutSave()
    {
        return outSave;
    }

    /**
     * @param outSave
     *            the outSave to set
     */
    public void setOutSave(double outSave)
    {
        this.outSave = outSave;
    }

    /**
     * @return the outMoney
     */
    public double getOutMoney()
    {
        return outMoney;
    }

    /**
     * @param outMoney
     *            the outMoney to set
     */
    public void setOutMoney(double outMoney)
    {
        this.outMoney = outMoney;
    }

    /**
     * @return the inMoney
     */
    public double getInMoney()
    {
        return inMoney;
    }

    /**
     * @param inMoney
     *            the inMoney to set
     */
    public void setInMoney(double inMoney)
    {
        this.inMoney = inMoney;
    }

    /**
     * @return the parentId
     */
    public String getParentId()
    {
        return parentId;
    }

    /**
     * @param parentId
     *            the parentId to set
     */
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    /**
     * @return the year
     */
    public String getYear()
    {
        return year;
    }

    /**
     * @param year
     *            the year to set
     */
    public void setYear(String year)
    {
        this.year = year;
    }

    /**
     * @return the budgetDepartment
     */
    public String getBudgetDepartment()
    {
        return budgetDepartment;
    }

    /**
     * @param budgetDepartment
     *            the budgetDepartment to set
     */
    public void setBudgetDepartment(String budgetDepartment)
    {
        this.budgetDepartment = budgetDepartment;
    }

    /**
     * @return the signer
     */
    public String getSigner()
    {
        return signer;
    }

    /**
     * @param signer
     *            the signer to set
     */
    public void setSigner(String signer)
    {
        this.signer = signer;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("BudgetBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("name = ")
            .append(this.name)
            .append(TAB)
            .append("parentId = ")
            .append(this.parentId)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("signer = ")
            .append(this.signer)
            .append(TAB)
            .append("locationId = ")
            .append(this.locationId)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("budgetDepartment = ")
            .append(this.budgetDepartment)
            .append(TAB)
            .append("year = ")
            .append(this.year)
            .append(TAB)
            .append("beginDate = ")
            .append(this.beginDate)
            .append(TAB)
            .append("endDate = ")
            .append(this.endDate)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("carryStatus = ")
            .append(this.carryStatus)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("level = ")
            .append(this.level)
            .append(TAB)
            .append("total = ")
            .append(this.total)
            .append(TAB)
            .append("sail = ")
            .append(this.sail)
            .append(TAB)
            .append("orgProfit = ")
            .append(this.orgProfit)
            .append(TAB)
            .append("realProfit = ")
            .append(this.realProfit)
            .append(TAB)
            .append("outSave = ")
            .append(this.outSave)
            .append(TAB)
            .append("outMoney = ")
            .append(this.outMoney)
            .append(TAB)
            .append("inMoney = ")
            .append(this.inMoney)
            .append(TAB)
            .append("realMonery = ")
            .append(this.realMonery)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("items = ")
            .append(this.items)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
