/**
 * File Name: OrgBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.vs;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.constant.PublicConstant;


/**
 * OrgBean
 * 
 * @author ZHUZHU
 * @version 2008-11-2
 * @see
 * @since
 */
@Entity
@Table(name = "T_CENTER_ORG")
public class OrgBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @FK
    @Join(tagClass = PrincipalshipBean.class)
    private String parentId = "";

    @Unique(dependFields = "parentId")
    @FK(index = 1)
    @Join(tagClass = PrincipalshipBean.class, alias = "PrincipalshipBean1")
    private String subId = "";
    
    private int status = PublicConstant.ORG_STATUS_USED;
    
    /**
     * default constructor
     */
    public OrgBean()
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
     * @return the subId
     */
    public String getSubId()
    {
        return subId;
    }

    /**
     * @param subId
     *            the subId to set
     */
    public void setSubId(String subId)
    {
        this.subId = subId;
    }

    /**
     * @return the parentId
     */
    public String getParentId()
    {
        return parentId;
    }

    /**
     * @param parentId
     *            the parentId to set
     */
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
}
