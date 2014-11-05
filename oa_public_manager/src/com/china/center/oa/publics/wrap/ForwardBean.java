/**
 * File Name: ForwardBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2012-4-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.wrap;


import java.io.Serializable;


/**
 * ForwardBean
 * 
 * @author ZHUZHU
 * @version 2012-4-7
 * @see ForwardBean
 * @since 3.0
 */
public class ForwardBean implements Serializable
{
    private String url = "";

    private String type = "";

    private String id = "";

    private Object other = "";

    /**
     * default constructor
     */
    public ForwardBean()
    {
    }

    /**
     * @return the url
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url)
    {
        this.url = url;
    }

    /**
     * @return the type
     */
    public String getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type)
    {
        this.type = type;
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
     * @return the other
     */
    public Object getOther()
    {
        return other;
    }

    /**
     * @param other
     *            the other to set
     */
    public void setOther(Object other)
    {
        this.other = other;
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
            .append("ForwardBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("url = ")
            .append(this.url)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("other = ")
            .append(this.other)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
