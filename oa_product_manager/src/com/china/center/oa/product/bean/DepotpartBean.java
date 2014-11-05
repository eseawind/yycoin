package com.china.center.oa.product.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.oa.product.constant.DepotConstant;


/**
 * 仓区bean
 * 
 * @author ZHUZHU
 * @version [版本号, 2008-1-5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Entity(name = "仓区")
@Table(name = "T_CENTER_DEPOTPART")
public class DepotpartBean implements Serializable
{
    @Id
    private String id = "";

    @Unique
    @Html(title = "仓区名称", must = true, maxLength = 40)
    private String name = "";

    /**
     * 仓库
     */
    @FK
    @Join(tagClass = DepotBean.class)
    @Html(title = "所属仓库", type = Element.SELECT, must = true)
    private String locationId = "";

    /**
     * 1可发(良品仓) 0(次品仓) and 2(报废仓)不可以($depotpartType)
     */
    @Html(title = "仓区类型", type = Element.SELECT, must = true)
    private int type = DepotConstant.DEPOTPART_TYPE_OK;

    @Html(title = "描述", type = Element.TEXTAREA)
    private String description = "";

    public DepotpartBean()
    {
    }

    /**
     * @return 返回 id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param 对id进行赋值
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return 返回 name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param 对name进行赋值
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return 返回 locationId
     */
    public String getLocationId()
    {
        return locationId;
    }

    /**
     * @param 对locationId进行赋值
     */
    public void setLocationId(String locationId)
    {
        this.locationId = locationId;
    }

    /**
     * @return 返回 description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param 对description进行赋值
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return 返回 type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param 对type进行赋值
     */
    public void setType(int type)
    {
        this.type = type;
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
            .append("DepotpartBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("name = ")
            .append(this.name)
            .append(TAB)
            .append("locationId = ")
            .append(this.locationId)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
