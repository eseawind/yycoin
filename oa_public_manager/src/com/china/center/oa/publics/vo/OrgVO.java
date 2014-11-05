/**
 * File Name: OrgVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.publics.vs.OrgBean;


/**
 * OrgVO
 * 
 * @author zhuzhu
 * @version 2008-11-17
 * @see OrgVO
 * @since 1.0
 */
@Entity(inherit = true)
public class OrgVO extends OrgBean
{
    @Relationship(relationField = "parentId")
    private String parentName = "";

    @Relationship(relationField = "subId")
    private String subName = "";

    @Relationship(relationField = "subId", tagField = "level")
    private int subLevel = 0;

    @Relationship(relationField = "parentId", tagField = "level")
    private int parentLevel = 0;

    @Ignore
    private int nextCount = 0;

    public OrgVO()
    {
    }

    /**
     * @return the parentName
     */
    public String getParentName()
    {
        return parentName;
    }

    /**
     * @param parentName
     *            the parentName to set
     */
    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    /**
     * @return the subName
     */
    public String getSubName()
    {
        return subName;
    }

    /**
     * @param subName
     *            the subName to set
     */
    public void setSubName(String subName)
    {
        this.subName = subName;
    }

    /**
     * @return the nextCount
     */
    public int getNextCount()
    {
        return nextCount;
    }

    /**
     * @param nextCount
     *            the nextCount to set
     */
    public void setNextCount(int nextCount)
    {
        this.nextCount = nextCount;
    }

    /**
     * @return the subLevel
     */
    public int getSubLevel()
    {
        return subLevel;
    }

    /**
     * @param subLevel
     *            the subLevel to set
     */
    public void setSubLevel(int subLevel)
    {
        this.subLevel = subLevel;
    }

    /**
     * @return the parentLevel
     */
    public int getParentLevel()
    {
        return parentLevel;
    }

    /**
     * @param parentLevel
     *            the parentLevel to set
     */
    public void setParentLevel(int parentLevel)
    {
        this.parentLevel = parentLevel;
    }
}
