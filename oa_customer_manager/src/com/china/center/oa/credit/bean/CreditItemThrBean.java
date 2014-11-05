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
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.JCheck;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.clone.DataClone;


/**
 * CreditItemBean
 * 
 * @author ZHUZHU
 * @version 2009-10-27
 * @see CreditItemThrBean
 * @since 1.0
 */
@Entity(cache = true)
@Table(name = "T_CENTER_CREDIT_ITEM03")
public class CreditItemThrBean implements DataClone<CreditItemThrBean>, Serializable
{
    @Id
    private String id = "";

    @FK
    @Join(tagClass = CreditItemSecBean.class)
    private String pid = "";

    @Html(title = "评价级别", must = true)
    @Unique(dependFields = "pid")
    private String name = "";

    private String unit = "";

    @Html(title = "档次", must = true, oncheck = JCheck.ONLY_NUMBER)
    private int indexPos = 0;

    @Html(title = "得分(百分制/实际值)", must = true, oncheck = JCheck.ONLY_FLOAT)
    private double per = 0.0d;

    /**
     * Copy Constructor
     * 
     * @param creditItemThrBean
     *            a <code>CreditItemThrBean</code> object
     */
    public CreditItemThrBean(CreditItemThrBean creditItemThrBean)
    {
        this.id = creditItemThrBean.id;
        this.pid = creditItemThrBean.pid;
        this.name = creditItemThrBean.name;
        this.unit = creditItemThrBean.unit;
        this.indexPos = creditItemThrBean.indexPos;
        this.per = creditItemThrBean.per;
    }

    /**
     * default constructor
     */
    public CreditItemThrBean()
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

    /**
     * @return the pid
     */
    public String getPid()
    {
        return pid;
    }

    /**
     * @param pid
     *            the pid to set
     */
    public void setPid(String pid)
    {
        this.pid = pid;
    }

    /**
     * @return the unit
     */
    public String getUnit()
    {
        return unit;
    }

    /**
     * @param unit
     *            the unit to set
     */
    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    /**
     * @return the indexPos
     */
    public int getIndexPos()
    {
        return indexPos;
    }

    /**
     * @param indexPos
     *            the indexPos to set
     */
    public void setIndexPos(int indexPos)
    {
        this.indexPos = indexPos;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String tab = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("CreditItemThrBean ( ")
            .append(super.toString())
            .append(tab)
            .append("id = ")
            .append(this.id)
            .append(tab)
            .append("pid = ")
            .append(this.pid)
            .append(tab)
            .append("name = ")
            .append(this.name)
            .append(tab)
            .append("unit = ")
            .append(this.unit)
            .append(tab)
            .append("indexPos = ")
            .append(this.indexPos)
            .append(tab)
            .append("per = ")
            .append(this.per)
            .append(tab)
            .append(" )");

        return retValue.toString();
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
        if ( ! (obj instanceof CreditItemThrBean)) return false;
        final CreditItemThrBean other = (CreditItemThrBean)obj;
        if (id == null)
        {
            if (other.id != null) return false;
        }
        else if ( !id.equals(other.id)) return false;
        return true;
    }

    public CreditItemThrBean clones()
    {
        return new CreditItemThrBean(this);
    }
}
