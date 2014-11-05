/**
 * File Name: EnumBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.oa.publics.constant.PublicConstant;


/**
 * EnumBean
 * 
 * @author ZHUZHU
 * @version 2008-11-9
 * @see EnumDefineBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_ENUMDEFINE")
public class EnumDefineBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @Unique
    private String name = "";

    private String cnname = "";

    /**
     * 0:字符 1:数字
     */
    private int type = PublicConstant.ENUMDEFINE_TYPE_STRING;

    private String ref = "";

    /**
     * default constructor
     */
    public EnumDefineBean()
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
     * @return the cnname
     */
    public String getCnname()
    {
        return cnname;
    }

    /**
     * @param cnname
     *            the cnname to set
     */
    public void setCnname(String cnname)
    {
        this.cnname = cnname;
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
            .append("EnumDefineBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("name = ")
            .append(this.name)
            .append(TAB)
            .append("cnname = ")
            .append(this.cnname)
            .append(TAB)
            .append("ref = ")
            .append(this.ref)
            .append(TAB)
            .append(" )");

        return retValue.toString();
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

}
