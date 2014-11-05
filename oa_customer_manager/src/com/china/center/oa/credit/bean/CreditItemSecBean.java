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
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.clone.DataClone;
import com.china.center.oa.customer.constant.CreditConstant;


/**
 * CreditItemBean
 * 
 * @author ZHUZHU
 * @version 2009-10-27
 * @see CreditItemSecBean
 * @since 1.0
 */
@Entity(cache = true)
@Table(name = "T_CENTER_CREDIT_ITEM02")
public class CreditItemSecBean implements DataClone<CreditItemSecBean>, Serializable
{
    @Id
    private String id = "";

    @FK
    @Join(tagClass = CreditItemBean.class)
    private String pid = "";

    private String name = "";

    private int type = CreditConstant.CREDIT_ITEM_TYPE_PERCENT;

    private int face = CreditConstant.CREDIT_ITEM_FACE_OBVERSE;

    private int sub = CreditConstant.CREDIT_ITEM_SUB_YES;

    private String unit = "";

    private double per = 0.0d;

    /**
     * Copy Constructor
     * 
     * @param creditItemSecBean
     *            a <code>CreditItemSecBean</code> object
     */
    public CreditItemSecBean(CreditItemSecBean creditItemSecBean)
    {
        this.id = creditItemSecBean.id;
        this.pid = creditItemSecBean.pid;
        this.name = creditItemSecBean.name;
        this.type = creditItemSecBean.type;
        this.face = creditItemSecBean.face;
        this.sub = creditItemSecBean.sub;
        this.unit = creditItemSecBean.unit;
        this.per = creditItemSecBean.per;
    }

    /**
     * default constructor
     */
    public CreditItemSecBean()
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
        if ( ! (obj instanceof CreditItemSecBean)) return false;
        final CreditItemSecBean other = (CreditItemSecBean)obj;
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

    /**
     * @return the face
     */
    public int getFace()
    {
        return face;
    }

    /**
     * @param face
     *            the face to set
     */
    public void setFace(int face)
    {
        this.face = face;
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
            .append("CreditItemSecBean ( ")
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
            .append("type = ")
            .append(this.type)
            .append(tab)
            .append("face = ")
            .append(this.face)
            .append(tab)
            .append("unit = ")
            .append(this.unit)
            .append(tab)
            .append("per = ")
            .append(this.per)
            .append(tab)
            .append(" )");

        return retValue.toString();
    }

    /**
     * @return the sub
     */
    public int getSub()
    {
        return sub;
    }

    /**
     * @param sub
     *            the sub to set
     */
    public void setSub(int sub)
    {
        this.sub = sub;
    }

    public CreditItemSecBean clones()
    {
        return new CreditItemSecBean(this);
    }
}
