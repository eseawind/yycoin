/**
 * File Name: RoleAuthBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.vs;


import java.io.Serializable;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * 职员岗位对应关系
 * 
 * @author ZHUZHU
 * @version 2008-11-2
 * @see
 * @since
 */
@Entity
@Table(name = "T_CENTER_VS_STA_PRI")
public class StafferVSPriBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @FK
    @Join(tagClass = StafferBean.class)
    private String stafferId = "";

    @FK(index = AnoConstant.FK_FIRST)
    @Join(tagClass = PrincipalshipBean.class)
    private String principalshipId = "";

    /**
     * default constructor
     */
    public StafferVSPriBean()
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
     * @return the principalshipId
     */
    public String getPrincipalshipId()
    {
        return principalshipId;
    }

    /**
     * @param principalshipId
     *            the principalshipId to set
     */
    public void setPrincipalshipId(String principalshipId)
    {
        this.principalshipId = principalshipId;
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
            .append("SatfferVSPriBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("principalshipId = ")
            .append(this.principalshipId)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
