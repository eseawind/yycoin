/**
 * File Name: StafferOrgWrap.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.wrap;


import java.io.Serializable;


/**
 * StafferOrgWrap
 * 
 * @author ZHUZHU
 * @version 2008-11-27
 * @see StafferOrgWrap
 * @since 1.0
 */
public class StafferOrgWrap implements Serializable
{
    private String id = "";

    private String stafferName = "";

    private String name = "";

    private String stafferId = "";

    private String principalshipId = "";

    private String principalshipName = "";

    private int personal = 0;

    public StafferOrgWrap()
    {}

    /**
     * @return the stafferName
     */
    public String getStafferName()
    {
        return stafferName;
    }

    public int hashCode()
    {
        return this.stafferId.hashCode();
    }

    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }

        if (obj == this)
        {
            return true;
        }

        if (obj instanceof StafferOrgWrap)
        {
            StafferOrgWrap tem = (StafferOrgWrap)obj;

            return this.getStafferId().equals(tem.getStafferId());
        }

        return false;
    }

    /**
     * @param stafferName
     *            the stafferName to set
     */
    public void setStafferName(String stafferName)
    {
        this.stafferName = stafferName;
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
     * @return the principalshipName
     */
    public String getPrincipalshipName()
    {
        return principalshipName;
    }

    /**
     * @param principalshipName
     *            the principalshipName to set
     */
    public void setPrincipalshipName(String principalshipName)
    {
        this.principalshipName = principalshipName;
    }

    /**
     * @return the personal
     */
    public int getPersonal()
    {
        return personal;
    }

    /**
     * @param personal
     *            the personal to set
     */
    public void setPersonal(int personal)
    {
        this.personal = personal;
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
}
