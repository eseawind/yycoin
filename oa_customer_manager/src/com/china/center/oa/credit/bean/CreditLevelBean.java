/**
 * File Name: CreditLevelBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: ZHUACHEN<br>
 * CreateTime: 2009-11-1<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;


/**
 * CreditLevelBean
 * 
 * @author ZHUZHU
 * @version 2009-11-1
 * @see CreditLevelBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_CREDIT_LEVEL")
public class CreditLevelBean implements Serializable
{
    @Id
    private String id = "";

    private String name = "";

    private double min = 0.0d;

    private double max = 0.0d;

    private double money = 0.0d;

    /**
     * default constructor
     */
    public CreditLevelBean()
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
     * @return the min
     */
    public double getMin()
    {
        return min;
    }

    /**
     * @param min
     *            the min to set
     */
    public void setMin(double min)
    {
        this.min = min;
    }

    /**
     * @return the max
     */
    public double getMax()
    {
        return max;
    }

    /**
     * @param max
     *            the max to set
     */
    public void setMax(double max)
    {
        this.max = max;
    }

    /**
     * @return the money
     */
    public double getMoney()
    {
        return money;
    }

    /**
     * @param money
     *            the money to set
     */
    public void setMoney(double money)
    {
        this.money = money;
    }
}
