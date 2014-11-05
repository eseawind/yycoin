/**
 * File Name: CreditLevelVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-5-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.oa.credit.bean.CreditLevelBean;


/**
 * CreditLevelVO
 * 
 * @author ZHUZHU
 * @version 2010-5-8
 * @see CreditLevelVO
 * @since 1.0
 */
@Entity(inherit = true)
public class CreditLevelVO extends CreditLevelBean
{
    @Ignore
    private String customerAmount = "";

    /**
     * default constructor
     */
    public CreditLevelVO()
    {
    }

    /**
     * @return the customerAmount
     */
    public String getCustomerAmount()
    {
        return customerAmount;
    }

    /**
     * @param customerAmount
     *            the customerAmount to set
     */
    public void setCustomerAmount(String customerAmount)
    {
        this.customerAmount = customerAmount;
    }

}
