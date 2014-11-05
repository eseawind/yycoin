/**
 * File Name: GroupVSStaffer.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-4-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.group.vs;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * GroupVSStaffer
 * 
 * @author ZHUZHU
 * @version 2009-4-7
 * @see GroupVSStafferBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_VS_GROUPSTA")
public class GroupVSStafferBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @FK
    private String groupId = "";

    @Join(tagClass = StafferBean.class)
    private String stafferId = "";

    /**
     * default constructor
     */
    public GroupVSStafferBean()
    {}

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
     * @return the groupId
     */
    public String getGroupId()
    {
        return groupId;
    }

    /**
     * @param groupId
     *            the groupId to set
     */
    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
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
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue.append("GroupVSStafferBean ( ").append(super.toString()).append(TAB).append("id = ").append(this.id).append(
            TAB).append("groupId = ").append(this.groupId).append(TAB).append("stafferId = ").append(this.stafferId).append(
            TAB).append(" )");

        return retValue.toString();
    }
}
