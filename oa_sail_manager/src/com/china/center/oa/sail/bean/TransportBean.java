/*
 * File Name: TransportBean.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2008-1-14
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.JCheck;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;


/**
 * 运输方式
 * 
 * @author ZHUZHU
 * @version 2008-1-14
 * @see
 * @since
 */
@Entity(name = "运输方式")
@Table(name = "T_CENTER_TRANSPORT")
public class TransportBean implements Serializable
{
    @Id
    private String id = "";

    @Html(title = "名称", must = true, maxLength = 10, oncheck = JCheck.NOT_NONE)
    private String name = "";

    private int type = 0;

    @Html(title = "所属分类", type = Element.SELECT, must = true, oncheck = JCheck.NOT_NONE)
    private String parent = "0";

    @Html(title = "物流公司", maxLength = 40)
    private String company = "";

    @Html(title = "联系电话 ", maxLength = 40)
    private String conphone = "";

    @Html(title = "发货区域 ", type = Element.TEXTAREA, maxLength = 400)
    private String area = "";

    @Html(title = "发车时间 ", maxLength = 40)
    private String outTime = "";

    /**
     * kuaidi 100 code
     */
    private String apiCode = "";
    
    /**
     * default constructor
     */
    public TransportBean()
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
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @return the parent
     */
    public String getParent()
    {
        return parent;
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
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
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
     * @param parent
     *            the parent to set
     */
    public void setParent(String parent)
    {
        if (parent != null)
        {
            this.parent = parent;
        }
    }

    /**
     * @return the company
     */
    public String getCompany()
    {
        return company;
    }

    /**
     * @param company
     *            the company to set
     */
    public void setCompany(String company)
    {
        this.company = company;
    }

    /**
     * @return the conphone
     */
    public String getConphone()
    {
        return conphone;
    }

    /**
     * @param conphone
     *            the conphone to set
     */
    public void setConphone(String conphone)
    {
        this.conphone = conphone;
    }

    /**
     * @return the area
     */
    public String getArea()
    {
        return area;
    }

    /**
     * @param area
     *            the area to set
     */
    public void setArea(String area)
    {
        this.area = area;
    }

    /**
     * @return the outTime
     */
    public String getOutTime()
    {
        return outTime;
    }

    /**
     * @param outTime
     *            the outTime to set
     */
    public void setOutTime(String outTime)
    {
        this.outTime = outTime;
    }

    /**
	 * @return the apiCode
	 */
	public String getApiCode()
	{
		return apiCode;
	}

	/**
	 * @param apiCode the apiCode to set
	 */
	public void setApiCode(String apiCode)
	{
		this.apiCode = apiCode;
	}

	/**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuffer retValue = new StringBuffer();

        retValue
            .append("TransportBean ( ")
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
            .append("parent = ")
            .append(this.parent)
            .append(TAB)
            .append("company = ")
            .append(this.company)
            .append(TAB)
            .append("conphone = ")
            .append(this.conphone)
            .append(TAB)
            .append("area = ")
            .append(this.area)
            .append(TAB)
            .append("outTime = ")
            .append(this.outTime)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
