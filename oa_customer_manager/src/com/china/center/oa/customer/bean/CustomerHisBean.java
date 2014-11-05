/**
 * File Name: CustomerHisBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.bean;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * CustomerHisBean
 * 
 * @author ZHUZHU
 * @version 2008-11-9
 * @see CustomerHisBean
 * @since 1.0
 */
@Entity(inherit = true)
@Table(name = "T_CENTER_CUSTOMER_HIS")
public class CustomerHisBean extends AbstractBean
{
    @Id(autoIncrement = true)
    private String id = "";

    @FK
    private String customerId = "";

    @Html(title = "客户名称", must = true, maxLength = 80)
    private String name = "";

    @Join(tagClass = StafferBean.class, type = JoinType.LEFT)
    private String updaterId = "";

    /**
     * default constructor
     */
    public CustomerHisBean()
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
     * @return the updaterId
     */
    public String getUpdaterId()
    {
        return updaterId;
    }

    /**
     * @param updaterId
     *            the updaterId to set
     */
    public void setUpdaterId(String updaterId)
    {
        this.updaterId = updaterId;
    }
}
