/**
 * File Name: StafferTransferBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-10-23<br>
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
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * StafferTransferBean
 * 
 * @author ZHUZHU
 * @version 2011-10-23
 * @see StafferTransferBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_VS_STA_TRA")
public class StafferTransferBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @FK
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT, alias = "DEST")
    private String destId = "";

    @Unique
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT, alias = "SRC")
    private String srcId = "";

    /**
     * default constructor
     */
    public StafferTransferBean()
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
     * @return the destId
     */
    public String getDestId()
    {
        return destId;
    }

    /**
     * @param destId
     *            the destId to set
     */
    public void setDestId(String destId)
    {
        this.destId = destId;
    }

    /**
     * @return the srcId
     */
    public String getSrcId()
    {
        return srcId;
    }

    /**
     * @param srcId
     *            the srcId to set
     */
    public void setSrcId(String srcId)
    {
        this.srcId = srcId;
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
            .append("StafferTransferBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("destId = ")
            .append(this.destId)
            .append(TAB)
            .append("srcId = ")
            .append(this.srcId)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
