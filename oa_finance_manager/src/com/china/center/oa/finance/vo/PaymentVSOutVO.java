/**
 * File Name: PaymentVSOutVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.finance.vs.PaymentVSOutBean;


/**
 * PaymentVSOutVO
 * 
 * @author ZHUZHU
 * @version 2010-12-26
 * @see PaymentVSOutVO
 * @since 3.0
 */
@Entity(inherit = true)
public class PaymentVSOutVO extends PaymentVSOutBean
{
    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    /**
     * default constructor
     */
    public PaymentVSOutVO()
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

}
