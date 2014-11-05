/**
 * File Name: CustomerApply.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.client.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * CustomerApply
 * 
 * @author ZHUZHU
 * @version 2008-11-12
 * @see AssignApplyBean
 * @since 1.0
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_CUSASSIGNALY")
public class AssignApplyBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    private String userId = "";

    @Join(tagClass = StafferBean.class)
    private String stafferId = "";

    @Unique
    @Join(tagClass = CustomerBean.class)
    private String customerId = "";

    private String locationid = "";

    /**
     * default constructor
     */
    public AssignApplyBean()
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
     * @return the userId
     */
    public String getUserId()
    {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(String userId)
    {
        this.userId = userId;
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
     * @return the customerId
     */
    public String getCustomerId()
    {
        return customerId;
    }

    /**
     * @param customerId
     *            the customerId to set
     */
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }

    /**
     * @return the locationid
     */
    public String getLocationid()
    {
        return locationid;
    }

    /**
     * @param locationid
     *            the locationid to set
     */
    public void setLocationid(String locationid)
    {
        this.locationid = locationid;
    }
}
