/**
 * File Name: FinanceTurnVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.tax.bean.FinanceMonthBean;
import com.china.center.oa.tax.helper.FinanceHelper;


/**
 * FinanceTurnVO
 * 
 * @author ZHUZHU
 * @version 2011-7-27
 * @see FinanceMonthVO
 * @since 3.0
 */
@Entity(inherit = true)
public class FinanceMonthVO extends FinanceMonthBean
{
    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    @Relationship(relationField = "taxId")
    private String taxName = "";

    @Relationship(relationField = "taxId", tagField = "forward")
    private int forward = 0;

    @Ignore
    private String forwardName = "";

    /**
     * 显示金额
     */
    @Ignore
    private String showInmoneyTotal = "";

    /**
     * 显示金额
     */
    @Ignore
    private String showOutmoneyTotal = "";

    @Ignore
    private String showLastTotal = "";

    @Ignore
    private String showInmoneyAllTotal = "";

    @Ignore
    private String showOutmoneyAllTotal = "";

    @Ignore
    private String showLastAllTotal = "";

    @Ignore
    private String showMonthTurnTotal = "";

    /**
     * default constructor
     */
    public FinanceMonthVO()
    {
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
     * @return the showInmoneyTotal
     */
    public String getShowInmoneyTotal()
    {
        this.showInmoneyTotal = FinanceHelper.longToString(super.getInmoneyTotal());

        return showInmoneyTotal;
    }

    /**
     * @param showInmoneyTotal
     *            the showInmoneyTotal to set
     */
    public void setShowInmoneyTotal(String showInmoneyTotal)
    {
        this.showInmoneyTotal = showInmoneyTotal;
    }

    /**
     * @return the showOutmoneyTotal
     */
    public String getShowOutmoneyTotal()
    {
        this.showOutmoneyTotal = FinanceHelper.longToString(super.getOutmoneyTotal());

        return showOutmoneyTotal;
    }

    /**
     * @param showOutmoneyTotal
     *            the showOutmoneyTotal to set
     */
    public void setShowOutmoneyTotal(String showOutmoneyTotal)
    {
        this.showOutmoneyTotal = showOutmoneyTotal;
    }

    /**
     * @return the showLastTotal
     */
    public String getShowLastTotal()
    {
        this.showLastTotal = FinanceHelper.longToString(super.getLastTotal());

        return showLastTotal;
    }

    /**
     * @param showLastTotal
     *            the showLastTotal to set
     */
    public void setShowLastTotal(String showLastTotal)
    {
        this.showLastTotal = showLastTotal;
    }

    /**
     * @return the showInmoneyAllTotal
     */
    public String getShowInmoneyAllTotal()
    {
        this.showInmoneyAllTotal = FinanceHelper.longToString(super.getInmoneyAllTotal());

        return showInmoneyAllTotal;
    }

    /**
     * @param showInmoneyAllTotal
     *            the showInmoneyAllTotal to set
     */
    public void setShowInmoneyAllTotal(String showInmoneyAllTotal)
    {
        this.showInmoneyAllTotal = showInmoneyAllTotal;
    }

    /**
     * @return the showOutmoneyAllTotal
     */
    public String getShowOutmoneyAllTotal()
    {
        this.showOutmoneyAllTotal = FinanceHelper.longToString(super.getOutmoneyAllTotal());

        return showOutmoneyAllTotal;
    }

    /**
     * @param showOutmoneyAllTotal
     *            the showOutmoneyAllTotal to set
     */
    public void setShowOutmoneyAllTotal(String showOutmoneyAllTotal)
    {
        this.showOutmoneyAllTotal = showOutmoneyAllTotal;
    }

    /**
     * @return the showLastAllTotal
     */
    public String getShowLastAllTotal()
    {
        this.showLastAllTotal = FinanceHelper.longToString(super.getLastAllTotal());

        return showLastAllTotal;
    }

    /**
     * @param showLastAllTotal
     *            the showLastAllTotal to set
     */
    public void setShowLastAllTotal(String showLastAllTotal)
    {
        this.showLastAllTotal = showLastAllTotal;
    }

    /**
     * @return the forward
     */
    public int getForward()
    {
        return forward;
    }

    /**
     * @param forward
     *            the forward to set
     */
    public void setForward(int forward)
    {
        this.forward = forward;
    }

    /**
     * @return the showMonthTurnTotal
     */
    public String getShowMonthTurnTotal()
    {
        this.showMonthTurnTotal = FinanceHelper.longToString(super.getMonthTurnTotal());

        return showMonthTurnTotal;
    }

    /**
     * @param showMonthTurnTotal
     *            the showMonthTurnTotal to set
     */
    public void setShowMonthTurnTotal(String showMonthTurnTotal)
    {
        this.showMonthTurnTotal = showMonthTurnTotal;
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
            .append("FinanceMonthVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("taxName = ")
            .append(this.taxName)
            .append(TAB)
            .append("forward = ")
            .append(this.forward)
            .append(TAB)
            .append("forwardName = ")
            .append(this.forwardName)
            .append(TAB)
            .append("showInmoneyTotal = ")
            .append(this.showInmoneyTotal)
            .append(TAB)
            .append("showOutmoneyTotal = ")
            .append(this.showOutmoneyTotal)
            .append(TAB)
            .append("showLastTotal = ")
            .append(this.showLastTotal)
            .append(TAB)
            .append("showInmoneyAllTotal = ")
            .append(this.showInmoneyAllTotal)
            .append(TAB)
            .append("showOutmoneyAllTotal = ")
            .append(this.showOutmoneyAllTotal)
            .append(TAB)
            .append("showLastAllTotal = ")
            .append(this.showLastAllTotal)
            .append(TAB)
            .append("showMonthTurnTotal = ")
            .append(this.showMonthTurnTotal)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
