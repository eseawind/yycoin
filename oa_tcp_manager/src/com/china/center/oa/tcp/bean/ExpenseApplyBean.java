/**
 * File Name: TravelApplyBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.oa.tcp.constanst.TcpConstanst;


/**
 * 报销申请
 * 
 * @author ZHUZHU
 * @version 2011-7-10
 * @see ExpenseApplyBean
 * @since 3.0
 */
@Entity(inherit = true)
@Table(name = "T_CENTER_TCPEXPENSE")
public class ExpenseApplyBean extends AbstractTcpBean implements Serializable
{
    @Id
    @Html(title = "标识", must = true, maxLength = 40)
    private String id = "";

    @Html(title = "付款类型", must = true, type = Element.SELECT)
    private int payType = TcpConstanst.PAYTYPE_PAY_YES;

    @Html(title = "报销方式", must = true, type = Element.SELECT)
    private int specialType = TcpConstanst.SPECIALTYPE_TEMPLATE_NO;

    /**
     * 
     */
    @Html(title = "票据张数", must = true, type = Element.NUMBER)
    private int ticikCount = 0;

    /**
     * 员工还款金额
     */
    @Html(title = "员工还款金额", must = true, type = Element.DOUBLE)
    private long lastMoney = 0L;

    @Html(title = "原申请借款金额", must = true, type = Element.DOUBLE)
    private long refMoney = 0L;

    /**
     * default constructor
     */
    public ExpenseApplyBean()
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
     * @return the lastMoney
     */
    public long getLastMoney()
    {
        return lastMoney;
    }

    /**
     * @param lastMoney
     *            the lastMoney to set
     */
    public void setLastMoney(long lastMoney)
    {
        this.lastMoney = lastMoney;
    }

    /**
     * @return the payType
     */
    public int getPayType()
    {
        return payType;
    }

    /**
     * @param payType
     *            the payType to set
     */
    public void setPayType(int payType)
    {
        this.payType = payType;
    }

    /**
     * @return the refMoney
     */
    public long getRefMoney()
    {
        return refMoney;
    }

    /**
     * @param refMoney
     *            the refMoney to set
     */
    public void setRefMoney(long refMoney)
    {
        this.refMoney = refMoney;
    }

    /**
     * @return the ticikCount
     */
    public int getTicikCount()
    {
        return ticikCount;
    }

    /**
     * @param ticikCount
     *            the ticikCount to set
     */
    public void setTicikCount(int ticikCount)
    {
        this.ticikCount = ticikCount;
    }

    /**
     * @return the specialType
     */
    public int getSpecialType()
    {
        return specialType;
    }

    /**
     * @param specialType
     *            the specialType to set
     */
    public void setSpecialType(int specialType)
    {
        this.specialType = specialType;
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
            .append("ExpenseApplyBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("payType = ")
            .append(this.payType)
            .append(TAB)
            .append("specialType = ")
            .append(this.specialType)
            .append(TAB)
            .append("ticikCount = ")
            .append(this.ticikCount)
            .append(TAB)
            .append("lastMoney = ")
            .append(this.lastMoney)
            .append(TAB)
            .append("refMoney = ")
            .append(this.refMoney)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
