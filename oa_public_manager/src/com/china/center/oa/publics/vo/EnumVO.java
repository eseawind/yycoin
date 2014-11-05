/**
 * File Name: EnumVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-16<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.publics.bean.EnumBean;


/**
 * EnumVO
 * 
 * @author ZHUZHU
 * @version 2010-8-16
 * @see EnumVO
 * @since 1.0
 */
@Entity(inherit = true)
public class EnumVO extends EnumBean
{
    @Relationship(relationField = "type", tagField = "cnname")
    private String typeName = "";

    /**
     * default constructor
     */
    public EnumVO()
    {
    }

    /**
     * @return the typeName
     */
    public String getTypeName()
    {
        return typeName;
    }

    /**
     * @param typeName
     *            the typeName to set
     */
    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

}
