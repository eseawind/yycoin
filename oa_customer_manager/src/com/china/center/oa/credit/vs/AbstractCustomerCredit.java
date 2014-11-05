/**
 * File Name: AbstractCreditLevel.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2009-11-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.vs;


import java.io.Serializable;

import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.credit.bean.CreditItemBean;
import com.china.center.oa.credit.bean.CreditItemSecBean;
import com.china.center.oa.credit.bean.CreditItemThrBean;
import com.china.center.oa.customer.constant.CreditConstant;


/**
 * AbstractCreditLevel
 * 
 * @author ZHUZHU
 * @version 2009-11-14
 * @see AbstractCreditLevel
 * @since 1.0
 */
public abstract class AbstractCustomerCredit implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @FK
    @Unique(dependFields = "itemId")
    @Join(tagClass = CustomerBean.class)
    private String cid = "";

    @Join(tagClass = CreditItemSecBean.class)
    private String itemId = "";

    @Join(tagClass = CreditItemBean.class)
    private String pitemId = "";

    @Join(tagClass = CreditItemThrBean.class, type = JoinType.LEFT)
    private String valueId = "";

    /**
     * static or dynamic
     */
    private int ptype = CreditConstant.CREDIT_TYPE_STATIC;

    private double val = 0.0d;

    private String logTime = "";

    private String log = "";

    /**
     * default constructor
     */
    public AbstractCustomerCredit()
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
     * @return the cid
     */
    public String getCid()
    {
        return cid;
    }

    /**
     * @param cid
     *            the cid to set
     */
    public void setCid(String cid)
    {
        this.cid = cid;
    }

    /**
     * @return the itemId
     */
    public String getItemId()
    {
        return itemId;
    }

    /**
     * @param itemId
     *            the itemId to set
     */
    public void setItemId(String itemId)
    {
        this.itemId = itemId;
    }

    /**
     * @return the pitemId
     */
    public String getPitemId()
    {
        return pitemId;
    }

    /**
     * @param pitemId
     *            the pitemId to set
     */
    public void setPitemId(String pitemId)
    {
        this.pitemId = pitemId;
    }

    /**
     * @return the valueId
     */
    public String getValueId()
    {
        return valueId;
    }

    /**
     * @param valueId
     *            the valueId to set
     */
    public void setValueId(String valueId)
    {
        this.valueId = valueId;
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
     * @return the log
     */
    public String getLog()
    {
        return log;
    }

    /**
     * @param log
     *            the log to set
     */
    public void setLog(String log)
    {
        this.log = log;
    }

    /**
     * @return the val
     */
    public double getVal()
    {
        return val;
    }

    /**
     * @param val
     *            the val to set
     */
    public void setVal(double val)
    {
        this.val = val;
    }

    /**
     * @return the ptype
     */
    public int getPtype()
    {
        return ptype;
    }

    /**
     * @param ptype
     *            the ptype to set
     */
    public void setPtype(int ptype)
    {
        this.ptype = ptype;
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

        retValue.append("AbstractCustomerCredit ( ").append(super.toString()).append(tab).append("id = ").append(
            this.id).append(tab).append("cid = ").append(this.cid).append(tab).append("itemId = ").append(this.itemId).append(
            tab).append("pitemId = ").append(this.pitemId).append(tab).append("valueId = ").append(this.valueId).append(
            tab).append("ptype = ").append(this.ptype).append(tab).append("val = ").append(this.val).append(tab).append(
            "logTime = ").append(this.logTime).append(tab).append("log = ").append(this.log).append(tab).append(" )");

        return retValue.toString();
    }

}
