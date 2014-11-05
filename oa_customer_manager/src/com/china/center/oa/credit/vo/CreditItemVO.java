/**
 * File Name: CreditItemVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: ZHUACHEN<br>
 * CreateTime: 2009-11-1<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.oa.credit.bean.CreditItemBean;


/**
 * CreditItemVO
 * 
 * @author ZHUZHU
 * @version 2009-11-1
 * @see CreditItemVO
 * @since 1.0
 */
@Entity(inherit = true)
public class CreditItemVO extends CreditItemBean
{
    @Ignore
    private String perAmount = "";

    @Ignore
    private String point = "0.0";

    /**
     * default constructor
     */
    public CreditItemVO()
    {
    }

    /**
     * @return the perAmount
     */
    public String getPerAmount()
    {
        return perAmount;
    }

    /**
     * @param perAmount
     *            the perAmount to set
     */
    public void setPerAmount(String perAmount)
    {
        this.perAmount = perAmount;
    }

    /**
     * @return the point
     */
    public String getPoint()
    {
        return point;
    }

    /**
     * @param point
     *            the point to set
     */
    public void setPoint(String point)
    {
        this.point = point;
    }
}
