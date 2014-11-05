/**
 * File Name: GroupBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-4-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.group.bean;


import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.oa.gm.constant.GroupConstant;
import com.china.center.oa.group.vs.GroupVSStafferBean;


/**
 * GroupBean
 * 
 * @author zhuzhu
 * @version 2009-4-7
 * @see GroupBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_GROUP")
public class GroupBean implements Serializable
{
    @Id
    private String id = "";

    @Html(title = "名称", maxLength = 40, must = true)
    private String name = "";

    @Html(title = "类型", type = Element.SELECT)
    private int type = GroupConstant.GROUP_TYPE_PRIVATE;

    @FK
    private String stafferId = "";

    @Ignore
    private String stafferNames = "";

    @Ignore
    private List<GroupVSStafferBean> items = null;

    /**
     * default constructor
     */
    public GroupBean()
    {
    }

    /**
     * equals
     */
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }

        if (this == obj)
        {
            return true;
        }

        if (obj instanceof GroupBean)
        {
            GroupBean temp = (GroupBean)obj;

            return temp.getId().equals(this.id);
        }

        return false;
    }

    public int hashCode()
    {
        return this.id.hashCode();
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
     * @return the stafferId
     */
    public String getStafferId()
    {
        return stafferId;
    }

    /**
     * @param stafferId
     *            the stafferId to set
     */
    public void setStafferId(String stafferId)
    {
        this.stafferId = stafferId;
    }

    /**
     * @return the items
     */
    public List<GroupVSStafferBean> getItems()
    {
        return items;
    }

    /**
     * @param items
     *            the items to set
     */
    public void setItems(List<GroupVSStafferBean> items)
    {
        this.items = items;
    }

    /**
     * @return the stafferNames
     */
    public String getStafferNames()
    {
        return stafferNames;
    }

    /**
     * @param stafferNames
     *            the stafferNames to set
     */
    public void setStafferNames(String stafferNames)
    {
        this.stafferNames = stafferNames;
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
            .append("GroupBean ( ")
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
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("stafferNames = ")
            .append(this.stafferNames)
            .append(TAB)
            .append("items = ")
            .append(this.items)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
