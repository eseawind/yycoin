/**
 * File Name: FinanceRefer.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2012-4-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.tax.constanst.FinaConstant;


/**
 * 凭证关联的活跃资源
 * 
 * @author ZHUZHU
 * @version 2012-4-27
 * @see FinanceRefer
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_FINANCEREFER")
public class FinanceRefer implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    private String refId = "";

    private String other = "";

    /**
     * 0:单位 1:职员 2:部门
     */
    @FK
    private int type = FinaConstant.FINANCEREFER_UNIT;

    /**
     * default constructor
     */
    public FinanceRefer()
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
     * @return the refId
     */
    public String getRefId()
    {
        return refId;
    }

    /**
     * @param refId
     *            the refId to set
     */
    public void setRefId(String refId)
    {
        this.refId = refId;
    }

    /**
     * @return the other
     */
    public String getOther()
    {
        return other;
    }

    /**
     * @param other
     *            the other to set
     */
    public void setOther(String other)
    {
        this.other = other;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(int type)
    {
        this.type = type;
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
            .append("FinanceRefer ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("refId = ")
            .append(this.refId)
            .append(TAB)
            .append("other = ")
            .append(this.other)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
