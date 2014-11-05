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

import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Unique;


/**
 * @author ZHUZHU
 * @version 2008-11-2
 * @see
 * @since
 */
public abstract class BaseBean implements Serializable
{
    @Id(autoIncrement = true)
    protected String id = "";

    @Unique
    @Html(title = "名称", must = true, maxLength = 100)
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

        retValue.append("BaseBean ( ").append(super.toString()).append(TAB).append("id = ").append(this.id).append(TAB).append(
            "name = ").append(this.name).append(TAB).append(" )");

        return retValue.toString();
    }
}
