/**
 * File Name: OutUniqueBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;


/**
 * OutUniqueBean(当库存变动库存的时候插入数据库,从根本上防止重复库存的问题)
 * 
 * @author ZHUZHU
 * @version 2010-11-7
 * @see OutUniqueBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_OUTUNIQUE")
public class OutUniqueBean implements Serializable
{
    @Id
    private String id = "";

    private String ref = "";

    private String logTime = "";

    /**
     * default constructor
     */
    public OutUniqueBean()
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
     * @return the logTime
     */
    public String getLogTime()
    {
        return logTime;
    }

    /**
     * @param logTime
     *            the logTime to set
     */
    public void setLogTime(String logTime)
    {
        this.logTime = logTime;
    }

    /**
     * @return the ref
     */
    public String getRef()
    {
        return ref;
    }

    /**
     * @param ref
     *            the ref to set
     */
    public void setRef(String ref)
    {
        this.ref = ref;
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
            .append("OutUniqueBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("ref = ")
            .append(this.ref)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
