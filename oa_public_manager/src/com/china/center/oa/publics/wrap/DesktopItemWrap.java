/**
 * File Name: DeskItemWrap.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-12-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.wrap;


import java.io.Serializable;


/**
 * DeskItemWrap
 * 
 * @author ZHUZHU
 * @version 2011-12-3
 * @see DesktopItemWrap
 * @since 3.0
 */
public class DesktopItemWrap implements Serializable
{
    private String href = "";

    private String title = "";

    private String tips = "";

    /**
     * default constructor
     */
    public DesktopItemWrap()
    {
    }

    /**
     * @return the href
     */
    public String getHref()
    {
        return href;
    }

    /**
     * @param href
     *            the href to set
     */
    public void setHref(String href)
    {
        this.href = href;
    }

    /**
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @return the tips
     */
    public String getTips()
    {
        return tips;
    }

    /**
     * @param tips
     *            the tips to set
     */
    public void setTips(String tips)
    {
        this.tips = tips;
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
            .append("DeskItemWrap ( ")
            .append(super.toString())
            .append(TAB)
            .append("href = ")
            .append(this.href)
            .append(TAB)
            .append("title = ")
            .append(this.title)
            .append(TAB)
            .append("tips = ")
            .append(this.tips)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
