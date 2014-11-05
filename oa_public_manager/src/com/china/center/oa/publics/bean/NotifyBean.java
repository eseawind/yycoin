/**
 * File Name: NotifyBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.bean;


import java.io.Serializable;

import com.china.center.oa.publics.constant.StafferConstant;


/**
 * NotifyBean(全局的通知定义)
 * 
 * @author ZHUZHU
 * @version 2010-11-7
 * @see NotifyBean
 * @since 1.0
 */
public class NotifyBean implements Serializable
{
    /**
     * 通知类型 一般不区分的
     */
    private int type = 0;

    /**
     * 通知内容
     */
    private String message = "";

    /**
     * 谁通知的 默认是系统
     */
    private String notifyer = StafferConstant.SUPER_STAFFER;

    /**
     * 链接URL
     */
    private String url = "";

    /**
     * 其他信息
     */
    private String other = "";

    /**
     * default constructor
     */
    public NotifyBean()
    {
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
     * @return the message
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message)
    {
        this.message = message;
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
     * @return the other
     */
    public String getOther()
    {
        return other;
    }

    /**
     * @param other
     *            the other to set
     */
    public void setOther(String other)
    {
        this.other = other;
    }

    /**
     * @return the notifyer
     */
    public String getNotifyer()
    {
        return notifyer;
    }

    /**
     * @param notifyer
     *            the notifyer to set
     */
    public void setNotifyer(String notifyer)
    {
        this.notifyer = notifyer;
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
            .append("NotifyBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("message = ")
            .append(this.message)
            .append(TAB)
            .append("notifyer = ")
            .append(this.notifyer)
            .append(TAB)
            .append("url = ")
            .append(this.url)
            .append(TAB)
            .append("other = ")
            .append(this.other)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
