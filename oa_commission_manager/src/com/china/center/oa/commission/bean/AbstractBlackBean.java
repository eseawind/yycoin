package com.china.center.oa.commission.bean;

import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.StafferConstant;

@SuppressWarnings("serial")
public abstract class AbstractBlackBean implements Serializable
{
    @Id
    private String id = "";
    
    /**
     * 日期
     */
    private String logDate = "";
    
    /**
     * 职员ID
     */
    @Join(tagClass = StafferBean.class, type = JoinType.EQUAL)
    @Unique
    private String stafferId = "";
    
    /**
     * 职员事业部ID
     */
    @Join(tagClass = PrincipalshipBean.class, type = JoinType.EQUAL)
    private String industryId = "";
    
    /**
     * 超期应收金额
     */
    private double money = 0.0d;
    
    /**
     * 超期最长天数
     */
    private int days = 0;
    
    /**
     * 全部应收金额
     */
    private double allMoneys = 0.0d;
    
    /**
     * 信用,黑名单类型
     */
    private int credit = StafferConstant.BLACK_YES;
    
    /**
     * 进入黑名单日期
     */
    private String entryDate = "";
    
    /**
     * 解除黑名单日期
     */
    private String removeDate = "";
    
    @Ignore    
    private List<BlackOutBean> outList = null;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    public String getStafferId() {
        return stafferId;
    }

    public void setStafferId(String stafferId) {
        this.stafferId = stafferId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public double getAllMoneys() {
        return allMoneys;
    }

    public void setAllMoneys(double allMoneys) {
        this.allMoneys = allMoneys;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getRemoveDate() {
        return removeDate;
    }

    public void setRemoveDate(String removeDate) {
        this.removeDate = removeDate;
    }
    
    
    
    public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	public List<BlackOutBean> getOutList() {
        return outList;
    }

    public void setOutList(List<BlackOutBean> outList) {
        this.outList = outList;
    }

    public String toString()
    {
        final String TAB = ",";
        
        StringBuilder retValue = new StringBuilder();
        
        retValue
        .append("ReDateRuleBean ( ")
        .append(super.toString())
        .append(TAB)
        .append("id = ")
        .append(this.id)
        .append(TAB)
        .append("logDate = ")
        .append(this.logDate)
        .append(TAB)
        .append("stafferId = ")
        .append(this.stafferId)
        .append(TAB)
        .append("money = ")
        .append(this.money)
        .append(TAB)
        .append("days = ")
        .append(this.days)
        .append(TAB)
        .append("allMoneys = ")
        .append(this.allMoneys)
        .append(TAB)
        .append("credit = ")
        .append(this.credit)
        .append(TAB)
        .append("entryDate = ")
        .append(this.entryDate)
        .append(TAB)
        .append("industryId = ")
        .append(this.industryId)
        .append(TAB)
        .append("removeDate = ")
        .append(this.removeDate)
        .append(" )");
        
        return retValue.toString();
    }
}
