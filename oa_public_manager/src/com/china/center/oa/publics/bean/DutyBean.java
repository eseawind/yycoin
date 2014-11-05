/**
 * File Name: DutyBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.jdbc.clone.DataClone;
import com.china.center.oa.publics.constant.DutyConstant;
import com.china.center.oa.publics.constant.PublicConstant;


/**
 * 纳税实体
 * 
 * @author ZHUZHU
 * @version 2010-7-9
 * @see DutyBean
 * @since 1.0
 */
@Entity(cache = false)
@Table(name = "T_CENTER_DUTYENTITY")
public class DutyBean implements DataClone<DutyBean>, Serializable
{
    @Id
    private String id = "";

    @Unique
    @Html(title = "名称", must = true, maxLength = 100)
    private String name = "";

    @Html(title = "纳税类型", must = true, type = Element.SELECT)
    private int type = DutyConstant.DUTY_TYPE_COMMON;

    @Html(title = "管理类型", must = true, type = Element.SELECT)
    private int mtype = PublicConstant.MANAGER_TYPE_COMMON;

    @Html(title = "是否显示", must = true, type = Element.SELECT)
    private int showType = DutyConstant.SHOWTYPE_YES;

    /**
     * 税点(内部的)
     */
    @Html(title = "税点(‰)", must = true, type = Element.NUMBER, maxLength = 3)
    private int dues = 0;

    @Html(title = "税务证号", must = true, maxLength = 100)
    private String icp = "";

    @Html(title = "其他", type = Element.TEXTAREA, maxLength = 200)
    private String description = "";

    /**
     * 开票人
     */
    @Html(title = "开票人", must = true, type = Element.SELECT)
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    private String invoicer = "";
    
    /**
     * Copy Constructor
     * 
     * @param dutyBean
     *            a <code>DutyBean</code> object
     */
    public DutyBean(DutyBean dutyBean)
    {
        this.id = dutyBean.id;
        this.name = dutyBean.name;
        this.type = dutyBean.type;
        this.mtype = dutyBean.mtype;
        this.dues = dutyBean.dues;
        this.icp = dutyBean.icp;
        this.showType = dutyBean.showType;
        this.description = dutyBean.description;
    }

    /**
     * default constructor
     */
    public DutyBean()
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
     * @return the icp
     */
    public String getIcp()
    {
        return icp;
    }

    /**
     * @param icp
     *            the icp to set
     */
    public void setIcp(String icp)
    {
        this.icp = icp;
    }

    public DutyBean clones()
    {
        return new DutyBean(this);
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
     * @return the dues
     */
    public int getDues()
    {
        return dues;
    }

    /**
     * @param dues
     *            the dues to set
     */
    public void setDues(int dues)
    {
        this.dues = dues;
    }

    /**
     * @return the mtype
     */
    public int getMtype()
    {
        return mtype;
    }

    /**
     * @param mtype
     *            the mtype to set
     */
    public void setMtype(int mtype)
    {
        this.mtype = mtype;
    }

    /**
     * @return the showType
     */
    public int getShowType()
    {
        return showType;
    }

    /**
     * @param showType
     *            the showType to set
     */
    public void setShowType(int showType)
    {
        this.showType = showType;
    }

    public String getInvoicer()
	{
		return invoicer;
	}

	public void setInvoicer(String invoicer)
	{
		this.invoicer = invoicer;
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
            .append("DutyBean ( ")
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
            .append("mtype = ")
            .append(this.mtype)
            .append(TAB)
            .append("showType = ")
            .append(this.showType)
            .append(TAB)
            .append("dues = ")
            .append(this.dues)
            .append(TAB)
            .append("icp = ")
            .append(this.icp)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
