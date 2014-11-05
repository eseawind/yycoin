/**
 * File Name: BaseBalanceVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.sail.bean.BaseBalanceBean;


/**
 * BaseBalanceVO
 * 
 * @author ZHUZHU
 * @version 2010-12-4
 * @see BaseBalanceVO
 * @since 3.0
 */
@Entity(inherit = true)
public class BaseBalanceVO extends BaseBalanceBean
{
    @Relationship(relationField = "parentId", tagField = "status")
    private int status = 0;

    /**
     * default constructor
     */
    public BaseBalanceVO()
    {
    }

    /**
     * @return the status
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status)
    {
        this.status = status;
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
            .append("BaseBalanceVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
