/**
 * File Name: BaseBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Id;


/**
 * 通用的基础集成类
 * 
 * @author ZHUZHU
 * @version 2008-11-2
 * @see
 * @since
 */
public abstract class BaseBean2 implements Serializable
{
    @Id
    protected String id = "";

    protected String name = "";

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
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuffer retValue = new StringBuffer();

        retValue.append("BaseBean2 ( ").append(super.toString()).append(TAB).append("id = ").append(this.id).append(TAB).append(
            "name = ").append(this.name).append(TAB).append(" )");

        return retValue.toString();
    }
}
