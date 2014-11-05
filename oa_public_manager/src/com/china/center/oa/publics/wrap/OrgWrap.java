/**
 * File Name: OrgWrap.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.wrap;


import java.io.Serializable;
import java.util.List;


/**
 * OrgWrap
 * 
 * @author ZHUZHU
 * @version 2008-11-17
 * @see OrgWrap
 * @since 1.0
 */
public class OrgWrap implements Serializable
{
    private String id = "";

    private String name = "";

    private int level = 0;

    private List<OrgWrap> subItem = null;

    public OrgWrap()
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
     * @return the level
     */
    public int getLevel()
    {
        return level;
    }

    /**
     * @param level
     *            the level to set
     */
    public void setLevel(int level)
    {
        this.level = level;
    }

    /**
     * @return the subItem
     */
    public List<OrgWrap> getSubItem()
    {
        return subItem;
    }

    /**
     * @param subItem
     *            the subItem to set
     */
    public void setSubItem(List<OrgWrap> subItem)
    {
        this.subItem = subItem;
    }
}
