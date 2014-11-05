/**
 * File Name: StorageApplyBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-28<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.oa.product.constant.StorageConstant;
import com.china.center.oa.product.vs.StorageRelationBean;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * StorageApplyBean
 * 
 * @author ZHUZHU
 * @version 2010-10-28
 * @see StorageApplyBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_STORAGE_CAPPLY")
public class StorageApplyBean implements Serializable
{
    @Id
    private String id = "";

    @Unique
    @Join(tagClass = StorageRelationBean.class)
    private String storageRelationId = "";

    @Join(tagClass = StafferBean.class, alias = "s1")
    private String applyer = "";

    private String productName = "";

    @Join(tagClass = StafferBean.class, alias = "s2")
    private String reveiver = "";

    private String logTime = "";

    private int status = StorageConstant.STORAGEAPPLY_STATUS_SUBMIT;

    @Html(title = "转移数量", must = true, type = Element.NUMBER)
    private int amount = 0;

    @Html(title = "描述", type = Element.TEXTAREA, maxLength = 200)
    private String description = "";

    /**
     * default constructor
     */
    public StorageApplyBean()
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
     * @return the storageRelationId
     */
    public String getStorageRelationId()
    {
        return storageRelationId;
    }

    /**
     * @param storageRelationId
     *            the storageRelationId to set
     */
    public void setStorageRelationId(String storageRelationId)
    {
        this.storageRelationId = storageRelationId;
    }

    /**
     * @return the applyer
     */
    public String getApplyer()
    {
        return applyer;
    }

    /**
     * @param applyer
     *            the applyer to set
     */
    public void setApplyer(String applyer)
    {
        this.applyer = applyer;
    }

    /**
     * @return the productName
     */
    public String getProductName()
    {
        return productName;
    }

    /**
     * @param productName
     *            the productName to set
     */
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    /**
     * @return the reveiver
     */
    public String getReveiver()
    {
        return reveiver;
    }

    /**
     * @param reveiver
     *            the reveiver to set
     */
    public void setReveiver(String reveiver)
    {
        this.reveiver = reveiver;
    }

    /**
     * @return the logTime
     */
    public String getLogTime()
    {
        return logTime;
    }

    /**
     * @param logTime
     *            the logTime to set
     */
    public void setLogTime(String logTime)
    {
        this.logTime = logTime;
    }

    /**
     * @return the status
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status)
    {
        this.status = status;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (id == null) ? 0 : id.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if ( ! (obj instanceof StorageApplyBean)) return false;
        final StorageApplyBean other = (StorageApplyBean)obj;
        if (id == null)
        {
            if (other.id != null) return false;
        }
        else if ( !id.equals(other.id)) return false;
        return true;
    }

    /**
     * @return the amount
     */
    public int getAmount()
    {
        return amount;
    }

    /**
     * @param amount
     *            the amount to set
     */
    public void setAmount(int amount)
    {
        this.amount = amount;
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
            .append("StorageApplyBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("storageRelationId = ")
            .append(this.storageRelationId)
            .append(TAB)
            .append("applyer = ")
            .append(this.applyer)
            .append(TAB)
            .append("productName = ")
            .append(this.productName)
            .append(TAB)
            .append("reveiver = ")
            .append(this.reveiver)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("status = ")
            .append(this.status)
            .append(TAB)
            .append("amount = ")
            .append(this.amount)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

}
