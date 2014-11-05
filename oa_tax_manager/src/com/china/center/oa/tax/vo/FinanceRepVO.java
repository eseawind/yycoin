/**
 * File Name: FinanceRepVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-10-30<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.oa.tax.bean.FinanceRepBean;


/**
 * FinanceRepVO
 * 
 * @author ZHUZHU
 * @version 2011-10-30
 * @see FinanceRepVO
 * @since 3.0
 */
@Entity(inherit = true)
public class FinanceRepVO extends FinanceRepBean
{
    /**
     * 本期/年初
     */
    @Ignore
    private long beginMoney = 0L;

    @Ignore
    private String beginMoneyStr = "";

    /**
     * 期末/累计
     */
    @Ignore
    private long endMoney = 0L;

    @Ignore
    private String endMoneyStr = "";

    @Ignore
    private String endMoneyChineseStr = "";

    /**
     * default constructor
     */
    public FinanceRepVO()
    {
    }

    /**
     * @return the beginMoney
     */
    public long getBeginMoney()
    {
        return beginMoney;
    }

    /**
     * @param beginMoney
     *            the beginMoney to set
     */
    public void setBeginMoney(long beginMoney)
    {
        this.beginMoney = beginMoney;
    }

    /**
     * @return the beginMoneyStr
     */
    public String getBeginMoneyStr()
    {
        return beginMoneyStr;
    }

    /**
     * @param beginMoneyStr
     *            the beginMoneyStr to set
     */
    public void setBeginMoneyStr(String beginMoneyStr)
    {
        this.beginMoneyStr = beginMoneyStr;
    }

    /**
     * @return the endMoney
     */
    public long getEndMoney()
    {
        return endMoney;
    }

    /**
     * @param endMoney
     *            the endMoney to set
     */
    public void setEndMoney(long endMoney)
    {
        this.endMoney = endMoney;
    }

    /**
     * @return the endMoneyStr
     */
    public String getEndMoneyStr()
    {
        return endMoneyStr;
    }

    /**
     * @param endMoneyStr
     *            the endMoneyStr to set
     */
    public void setEndMoneyStr(String endMoneyStr)
    {
        this.endMoneyStr = endMoneyStr;
    }

    /**
     * @return the endMoneyChineseStr
     */
    public String getEndMoneyChineseStr()
    {
        return endMoneyChineseStr;
    }

    /**
     * @param endMoneyChineseStr
     *            the endMoneyChineseStr to set
     */
    public void setEndMoneyChineseStr(String endMoneyChineseStr)
    {
        this.endMoneyChineseStr = endMoneyChineseStr;
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
            .append("FinanceRepVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("beginMoney = ")
            .append(this.beginMoney)
            .append(TAB)
            .append("beginMoneyStr = ")
            .append(this.beginMoneyStr)
            .append(TAB)
            .append("endMoney = ")
            .append(this.endMoney)
            .append(TAB)
            .append("endMoneyStr = ")
            .append(this.endMoneyStr)
            .append(TAB)
            .append("endMoneyChineseStr = ")
            .append(this.endMoneyChineseStr)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
