package com.china.center.oa.tax.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_FINANCESHOW")
public class FinanceShowBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
    private String taxId = "";

    private String taxName = "";

    private String showBeginAllmoney = "";

    private long beginAllmoney = 0L;

    /**
     * 显示金额
     */
    private String showCurrInmoney = "";

    private long currInmoney = 0L;

    private String showAllInmoney = "";

    private long allInmoney = 0L;

    /**
     * 显示金额
     */

    private String showCurrOutmoney = "";

    private long currOutmoney = 0L;

    private String showAllOutmoney = "";

    private long allOutmoney = 0L;

    /**
     * 期末余额
     */
    private String showLastmoney = "";

    private long lastmoney = 0L;

    /**
     * 方向
     */
    private String forwardName = "";

    /**
     * 单位名称
     */
    private String unitName = "";

    /**
     * 职员
     */
    private String stafferName = "";

    /**
     * 0:科目 1:职员 2:单位
     */
    private int type = 0;

    /**
     * default constructor
     */
    public FinanceShowBean()
    {
    }

	public FinanceShowBean(int type)
    {
        this.type = type;
    }

	 /**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}
	
    /**
     * @return the taxId
     */
    public String getTaxId()
    {
        return taxId;
    }

    /**
     * @param taxId
     *            the taxId to set
     */
    public void setTaxId(String taxId)
    {
        this.taxId = taxId;
    }

    /**
     * @return the taxName
     */
    public String getTaxName()
    {
        return taxName;
    }

    /**
     * @param taxName
     *            the taxName to set
     */
    public void setTaxName(String taxName)
    {
        this.taxName = taxName;
    }

    /**
     * @return the showBeginAllmoney
     */
    public String getShowBeginAllmoney()
    {
        return showBeginAllmoney;
    }

    /**
     * @param showBeginAllmoney
     *            the showBeginAllmoney to set
     */
    public void setShowBeginAllmoney(String showBeginAllmoney)
    {
        this.showBeginAllmoney = showBeginAllmoney;
    }

    /**
     * @return the showCurrInmoney
     */
    public String getShowCurrInmoney()
    {
        return showCurrInmoney;
    }

    /**
     * @param showCurrInmoney
     *            the showCurrInmoney to set
     */
    public void setShowCurrInmoney(String showCurrInmoney)
    {
        this.showCurrInmoney = showCurrInmoney;
    }

    /**
     * @return the showAllInmoney
     */
    public String getShowAllInmoney()
    {
        return showAllInmoney;
    }

    /**
     * @param showAllInmoney
     *            the showAllInmoney to set
     */
    public void setShowAllInmoney(String showAllInmoney)
    {
        this.showAllInmoney = showAllInmoney;
    }

    /**
     * @return the showCurrOutmoney
     */
    public String getShowCurrOutmoney()
    {
        return showCurrOutmoney;
    }

    /**
     * @param showCurrOutmoney
     *            the showCurrOutmoney to set
     */
    public void setShowCurrOutmoney(String showCurrOutmoney)
    {
        this.showCurrOutmoney = showCurrOutmoney;
    }

    /**
     * @return the showAllOutmoney
     */
    public String getShowAllOutmoney()
    {
        return showAllOutmoney;
    }

    /**
     * @param showAllOutmoney
     *            the showAllOutmoney to set
     */
    public void setShowAllOutmoney(String showAllOutmoney)
    {
        this.showAllOutmoney = showAllOutmoney;
    }

    /**
     * @return the showLastmoney
     */
    public String getShowLastmoney()
    {
        return showLastmoney;
    }

    /**
     * @param showLastmoney
     *            the showLastmoney to set
     */
    public void setShowLastmoney(String showLastmoney)
    {
        this.showLastmoney = showLastmoney;
    }

    /**
     * @return the forwardName
     */
    public String getForwardName()
    {
        return forwardName;
    }

    /**
     * @param forwardName
     *            the forwardName to set
     */
    public void setForwardName(String forwardName)
    {
        this.forwardName = forwardName;
    }

    /**
     * @return the unitName
     */
    public String getUnitName()
    {
        return unitName;
    }

    /**
     * @param unitName
     *            the unitName to set
     */
    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
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
     * @return the beginAllmoney
     */
    public long getBeginAllmoney()
    {
        return beginAllmoney;
    }

    /**
     * @param beginAllmoney
     *            the beginAllmoney to set
     */
    public void setBeginAllmoney(long beginAllmoney)
    {
        this.beginAllmoney = beginAllmoney;
    }

    /**
     * @return the currInmoney
     */
    public long getCurrInmoney()
    {
        return currInmoney;
    }

    /**
     * @param currInmoney
     *            the currInmoney to set
     */
    public void setCurrInmoney(long currInmoney)
    {
        this.currInmoney = currInmoney;
    }

    /**
     * @return the allInmoney
     */
    public long getAllInmoney()
    {
        return allInmoney;
    }

    /**
     * @param allInmoney
     *            the allInmoney to set
     */
    public void setAllInmoney(long allInmoney)
    {
        this.allInmoney = allInmoney;
    }

    /**
     * @return the currOutmoney
     */
    public long getCurrOutmoney()
    {
        return currOutmoney;
    }

    /**
     * @param currOutmoney
     *            the currOutmoney to set
     */
    public void setCurrOutmoney(long currOutmoney)
    {
        this.currOutmoney = currOutmoney;
    }

    /**
     * @return the allOutmoney
     */
    public long getAllOutmoney()
    {
        return allOutmoney;
    }

    /**
     * @param allOutmoney
     *            the allOutmoney to set
     */
    public void setAllOutmoney(long allOutmoney)
    {
        this.allOutmoney = allOutmoney;
    }

    /**
     * @return the lastmoney
     */
    public long getLastmoney()
    {
        return lastmoney;
    }

    /**
     * @param lastmoney
     *            the lastmoney to set
     */
    public void setLastmoney(long lastmoney)
    {
        this.lastmoney = lastmoney;
    }

    /**
     * @return the stafferName
     */
    public String getStafferName()
    {
        return stafferName;
    }

    /**
     * @param stafferName
     *            the stafferName to set
     */
    public void setStafferName(String stafferName)
    {
        this.stafferName = stafferName;
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
            .append("FinanceShowBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("taxId = ")
            .append(this.taxId)
            .append(TAB)
            .append("taxName = ")
            .append(this.taxName)
            .append(TAB)
            .append("showBeginAllmoney = ")
            .append(this.showBeginAllmoney)
            .append(TAB)
            .append("beginAllmoney = ")
            .append(this.beginAllmoney)
            .append(TAB)
            .append("showCurrInmoney = ")
            .append(this.showCurrInmoney)
            .append(TAB)
            .append("currInmoney = ")
            .append(this.currInmoney)
            .append(TAB)
            .append("showAllInmoney = ")
            .append(this.showAllInmoney)
            .append(TAB)
            .append("allInmoney = ")
            .append(this.allInmoney)
            .append(TAB)
            .append("showCurrOutmoney = ")
            .append(this.showCurrOutmoney)
            .append(TAB)
            .append("currOutmoney = ")
            .append(this.currOutmoney)
            .append(TAB)
            .append("showAllOutmoney = ")
            .append(this.showAllOutmoney)
            .append(TAB)
            .append("allOutmoney = ")
            .append(this.allOutmoney)
            .append(TAB)
            .append("showLastmoney = ")
            .append(this.showLastmoney)
            .append(TAB)
            .append("lastmoney = ")
            .append(this.lastmoney)
            .append(TAB)
            .append("forwardName = ")
            .append(this.forwardName)
            .append(TAB)
            .append("unitName = ")
            .append(this.unitName)
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
