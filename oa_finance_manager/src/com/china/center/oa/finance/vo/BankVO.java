/**
 * File Name: BankVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.finance.bean.BankBean;


/**
 * BankVO
 * 
 * @author ZHUZHU
 * @version 2010-12-12
 * @see BankVO
 * @since 3.0
 */
@Entity(inherit = true)
public class BankVO extends BankBean
{
    @Relationship(relationField = "dutyId")
    private String dutyName = "";

    @Ignore
    private double lastMoney = 0.0d;

    /**
     * default constructor
     */
    public BankVO()
    {
    }

    /**
     * @return the dutyName
     */
    public String getDutyName()
    {
        return dutyName;
    }

    /**
     * @param dutyName
     *            the dutyName to set
     */
    public void setDutyName(String dutyName)
    {
        this.dutyName = dutyName;
    }

    /**
     * @return the lastMoney
     */
    public double getLastMoney()
    {
        return lastMoney;
    }

    /**
     * @param lastMoney
     *            the lastMoney to set
     */
    public void setLastMoney(double lastMoney)
    {
        this.lastMoney = lastMoney;
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
            .append("BankVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("dutyName = ")
            .append(this.dutyName)
            .append(TAB)
            .append("lastMoney = ")
            .append(this.lastMoney)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
