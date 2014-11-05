/**
 * File Name: StatBankVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-16<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.finance.bean.StatBankBean;


/**
 * StatBankVO
 * 
 * @author ZHUZHU
 * @version 2011-1-16
 * @see StatBankVO
 * @since 3.0
 */
@Entity(inherit = true)
public class StatBankVO extends StatBankBean
{
    @Relationship(relationField = "bankId")
    private String bankName = "";

    /**
     * default constructor
     */
    public StatBankVO()
    {
    }

    /**
     * @return the bankName
     */
    public String getBankName()
    {
        return bankName;
    }

    /**
     * @param bankName
     *            the bankName to set
     */
    public void setBankName(String bankName)
    {
        this.bankName = bankName;
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
            .append("StatBankVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("bankName = ")
            .append(this.bankName)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
