/**
 * File Name: DepotBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-22<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.product.constant.DepotConstant;
import com.china.center.oa.publics.bean.PrincipalshipBean;


/**
 * 仓库
 * 
 * @author ZHUZHU
 * @version 2010-8-22
 * @see DepotBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_DEPOT")
public class DepotBean implements Serializable
{
    @Id
    private String id = "";

    @Unique
    @Html(title = "名称", must = true)
    private String name = "";

    /**
     * 所属事业部
     */
    @Html(title = "事业部", type = Element.SELECT)
    @Join(tagClass = PrincipalshipBean.class, type = JoinType.LEFT, alias = "INDUSTRY")
    private String industryId = "";

    /**
     * 仓库类型
     */
    @Html(title = "仓库类型", type = Element.SELECT, must = true)
    private int type = DepotConstant.DEPOT_TYPE_LOCATION;

    @Html(title = "描述", type = Element.TEXTAREA, maxLength = 200)
    private String description = "";

    @Html(title = "地点",type = Element.SELECT)
    @Join(tagClass = PrincipalshipBean.class, type = JoinType.LEFT,alias = "INDUSTRY2")
    private String industryId2 = "";
    
    /**
     * 状态 0 正常 1 停用
     */
    @Html(title = "状态 ", type = Element.SELECT)
    private int status = 0;
    
    /**
     * default constructor
     */
    public DepotBean()
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
     * @return the industryId
     */
    public String getIndustryId()
    {
        return industryId;
    }

    /**
     * @param industryId
     *            the industryId to set
     */
    public void setIndustryId(String industryId)
    {
        this.industryId = industryId;
    }

    
    public String getIndustryId2() {
        return industryId2;
    }

    public void setIndustryId2(String industryId2) {
        this.industryId2 = industryId2;
    }

    public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
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
            .append("DepotBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("name = ")
            .append(this.name)
            .append(TAB)
            .append("industryId = ")
            .append(this.industryId)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append("industryId2 = ")
            .append(this.industryId2)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
