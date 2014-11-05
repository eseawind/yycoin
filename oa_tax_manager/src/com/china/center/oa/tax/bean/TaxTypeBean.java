/**
 * File Name: TaxTypeBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-30<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;


/**
 * TaxTypeBean
 * 
 * @author ZHUZHU
 * @version 2011-1-30
 * @see TaxTypeBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_TAXTYPE")
public class TaxTypeBean implements Serializable
{
    @Id
    private String id = "";

    @Unique
    private String name = "";

    private int type = 0;

    /**
     * default constructor
     */
    public TaxTypeBean()
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

        StringBuffer retValue = new StringBuffer();

        retValue.append("TaxTypeBean ( ").append(super.toString()).append(TAB).append("id = ").append(this.id).append(
            TAB).append("name = ").append(this.name).append(TAB).append("type = ").append(this.type).append(TAB).append(
            " )");

        return retValue.toString();
    }

}
