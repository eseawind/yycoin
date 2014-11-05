/**
 * File Name: CreditItemBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: ZHUACHEN<br>
 * CreateTime: 2009-10-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.clone.DataClone;
import com.china.center.oa.customer.constant.CreditConstant;


/**
 * CreditItemBean
 * 
 * @author ZHUZHU
 * @version 2009-10-27
 * @see CreditItemBean
 * @since 1.0
 */
@Entity(cache = true)
@Table(name = "T_CENTER_CREDIT_ITEM01")
public class CreditItemBean implements DataClone<CreditItemBean>, Serializable
{
    @Id
    private String id = "";

    private String name = "";

    private int type = CreditConstant.CREDIT_TYPE_STATIC;

    private double per = 0.0d;

    /**
     * Copy Constructor
     * 
     * @param creditItemBean
     *            a <code>CreditItemBean</code> object
     */
    public CreditItemBean(CreditItemBean creditItemBean)
    {
        this.id = creditItemBean.id;
        this.name = creditItemBean.name;
        this.type = creditItemBean.type;
        this.per = creditItemBean.per;
    }

    /**
     * default constructor
     */
    public CreditItemBean()
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
     * @return the per
     */
    public double getPer()
    {
        return per;
    }

    /**
     * @param per
     *            the per to set
     */
    public void setPer(double per)
    {
        this.per = per;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (id == null) ? 0 : id.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if ( ! (obj instanceof CreditItemBean)) return false;
        final CreditItemBean other = (CreditItemBean)obj;
        if (id == null)
        {
            if (other.id != null) return false;
        }
        else if ( !id.equals(other.id)) return false;
        return true;
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

    public CreditItemBean clones()
    {
        return new CreditItemBean(this);
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
            .append("CreditItemBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("name = ")
            .append(this.name)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("per = ")
            .append(this.per)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
