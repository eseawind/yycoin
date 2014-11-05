/**
 * File Name: FinanceRepBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-10-30<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.oa.tax.constanst.FinaConstant;


/**
 * FinanceRepBean
 * 
 * @author ZHUZHU
 * @version 2011-10-30
 * @see FinanceRepBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_FINANCEREP")
public class FinanceRepBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    /**
     * 0:资产负债 1:损益表
     */
    @FK
    private int type = 0;

    /**
     * 0:加法 1:减法 99合计
     */
    private int rmethod = FinaConstant.FINANCEREP_RMETHOD_ADD;

    @Unique(dependFields = "type")
    private int itemIndex = 0;

    private String itemName = "";

    private String itemPName = "";

    private String expr = "";

    /**
     * default constructor
     */
    public FinanceRepBean()
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
     * @return the itemIndex
     */
    public int getItemIndex()
    {
        return itemIndex;
    }

    /**
     * @param itemIndex
     *            the itemIndex to set
     */
    public void setItemIndex(int itemIndex)
    {
        this.itemIndex = itemIndex;
    }

    /**
     * @return the itemName
     */
    public String getItemName()
    {
        return itemName;
    }

    /**
     * @param itemName
     *            the itemName to set
     */
    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    /**
     * @return the itemPName
     */
    public String getItemPName()
    {
        return itemPName;
    }

    /**
     * @param itemPName
     *            the itemPName to set
     */
    public void setItemPName(String itemPName)
    {
        this.itemPName = itemPName;
    }

    /**
     * @return the expr
     */
    public String getExpr()
    {
        return expr;
    }

    /**
     * @param expr
     *            the expr to set
     */
    public void setExpr(String expr)
    {
        this.expr = expr;
    }

    /**
     * @return the rmethod
     */
    public int getRmethod()
    {
        return rmethod;
    }

    /**
     * @param rmethod
     *            the rmethod to set
     */
    public void setRmethod(int rmethod)
    {
        this.rmethod = rmethod;
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
            .append("FinanceRepBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("rmethod = ")
            .append(this.rmethod)
            .append(TAB)
            .append("itemIndex = ")
            .append(this.itemIndex)
            .append(TAB)
            .append("itemName = ")
            .append(this.itemName)
            .append(TAB)
            .append("itemPName = ")
            .append(this.itemPName)
            .append(TAB)
            .append("expr = ")
            .append(this.expr)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
