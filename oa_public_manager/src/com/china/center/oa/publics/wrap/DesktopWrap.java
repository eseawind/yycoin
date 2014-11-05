/**
 * File Name: DeskWrap.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-12-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.wrap;


import java.io.Serializable;
import java.util.List;


/**
 * DeskWrap
 * 
 * @author ZHUZHU
 * @version 2011-12-3
 * @see DesktopWrap
 * @since 3.0
 */
public class DesktopWrap implements Serializable
{
    private String name = "";

    private List<DesktopItemWrap> itemList = null;

    /**
     * default constructor
     */
    public DesktopWrap()
    {
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
     * @return the itemList
     */
    public List<DesktopItemWrap> getItemList()
    {
        return itemList;
    }

    /**
     * @param itemList
     *            the itemList to set
     */
    public void setItemList(List<DesktopItemWrap> itemList)
    {
        this.itemList = itemList;
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
            .append("DeskWrap ( ")
            .append(super.toString())
            .append(TAB)
            .append("name = ")
            .append(this.name)
            .append(TAB)
            .append("itemList = ")
            .append(this.itemList)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
