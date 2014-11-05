/**
 * File Name: InvoiceBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-19<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.bean;


import java.io.Serializable;
import java.text.DecimalFormat;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.oa.publics.constant.InvoiceConstant;


/**
 * 发票
 * 
 * @author ZHUZHU
 * @version 2010-9-19
 * @see InvoiceBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_INVOICE")
public class InvoiceBean implements Serializable
{
    @Id
    private String id = "";

    @Unique
    @Html(title = "名称", must = true, maxLength = 100)
    private String name = "";

    @Html(title = "发票类型", must = true, type = Element.SELECT)
    private int type = InvoiceConstant.INVOICE_TYPE_SPECIAL;

    @Html(title = "进销属性", must = true, type = Element.SELECT)
    private int forward = InvoiceConstant.INVOICE_FORWARD_IN;

    @Html(title = "抵扣属性", must = true, type = Element.SELECT)
    private int counteract = InvoiceConstant.INVOICE_COUNTERACT_NO;

    @Html(title = "税点(%)", must = true, maxLength = 100, type = Element.DOUBLE)
    private double val = 0.0d;

    @Html(title = "其他", type = Element.TEXTAREA, maxLength = 200)
    private String description = "";

    /**
     * default constructor
     */
    public InvoiceBean()
    {
    }

    public String getFullName()
    {
        String forwardName = "";

        if (this.forward == InvoiceConstant.INVOICE_FORWARD_IN)
        {
            forwardName = "进货";
        }
        else
        {
            forwardName = "销货";
        }

        String counteractName = "";

        if (this.counteract == InvoiceConstant.INVOICE_COUNTERACT_NO)
        {
            counteractName = "不可抵扣";
        }
        else
        {
            counteractName = "可抵扣";
        }

        if (this.id.equals(InvoiceConstant.INVOICE_INSTACE_NDK_2))
        {
            return forwardName + "-->" + this.name ;
        }
        else
        {
            return forwardName + "-->" + this.name + "("
                   + formatNum(this.val) + "%)";
        }
    }

    /**
     * formatNum
     * 
     * @param d
     * @return
     */
    private String formatNum(double d)
    {
        DecimalFormat df = new DecimalFormat("####0.00");

        String result = df.format(d);

        if (".00".equals(result))
        {
            result = "0" + result;
        }

        return result;
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
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
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
        if ( ! (obj instanceof InvoiceBean)) return false;
        final InvoiceBean other = (InvoiceBean)obj;
        if (id == null)
        {
            if (other.id != null) return false;
        }
        else if ( !id.equals(other.id)) return false;
        return true;
    }

    /**
     * @return the forward
     */
    public int getForward()
    {
        return forward;
    }

    /**
     * @param forward
     *            the forward to set
     */
    public void setForward(int forward)
    {
        this.forward = forward;
    }

    /**
     * @return the counteract
     */
    public int getCounteract()
    {
        return counteract;
    }

    /**
     * @param counteract
     *            the counteract to set
     */
    public void setCounteract(int counteract)
    {
        this.counteract = counteract;
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
            .append("InvoiceBean ( ")
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
            .append("forward = ")
            .append(this.forward)
            .append(TAB)
            .append("counteract = ")
            .append(this.counteract)
            .append(TAB)
            .append("val = ")
            .append(this.val)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
