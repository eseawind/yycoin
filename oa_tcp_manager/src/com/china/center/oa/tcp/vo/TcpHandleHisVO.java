/**
 * File Name: TcpHandleHisVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-9-18<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.tcp.bean.TcpHandleHisBean;


/**
 * TcpHandleHisVO
 * 
 * @author ZHUZHU
 * @version 2011-9-18
 * @see TcpHandleHisVO
 * @since 3.0
 */
@Entity(inherit = true)
public class TcpHandleHisVO extends TcpHandleHisBean
{
    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    @Relationship(relationField = "applyId")
    private String applyName = "";

    @Ignore
    private String url = "";

    /**
     * 总金额
     */
    @Ignore
    private String moneyStr1 = "";

    /**
     * 借款金额
     */
    @Ignore
    private String moneyStr2 = "";

    /**
     * default constructor
     */
    public TcpHandleHisVO()
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
     * @return the applyName
     */
    public String getApplyName()
    {
        return applyName;
    }

    /**
     * @param applyName
     *            the applyName to set
     */
    public void setApplyName(String applyName)
    {
        this.applyName = applyName;
    }

    /**
     * @return the url
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url)
    {
        this.url = url;
    }

    /**
     * @return the moneyStr1
     */
    public String getMoneyStr1()
    {
        return moneyStr1;
    }

    /**
     * @param moneyStr1
     *            the moneyStr1 to set
     */
    public void setMoneyStr1(String moneyStr1)
    {
        this.moneyStr1 = moneyStr1;
    }

    /**
     * @return the moneyStr2
     */
    public String getMoneyStr2()
    {
        return moneyStr2;
    }

    /**
     * @param moneyStr2
     *            the moneyStr2 to set
     */
    public void setMoneyStr2(String moneyStr2)
    {
        this.moneyStr2 = moneyStr2;
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

        retValue.append("TcpHandleHisVO ( ").append(super.toString()).append(TAB).append(
            "stafferName = ").append(this.stafferName).append(TAB).append("applyName = ").append(
            this.applyName).append(TAB).append("url = ").append(this.url).append(TAB).append(
            "moneyStr1 = ").append(this.moneyStr1).append(TAB).append("moneyStr2 = ").append(
            this.moneyStr2).append(TAB).append(" )");

        return retValue.toString();
    }

}
