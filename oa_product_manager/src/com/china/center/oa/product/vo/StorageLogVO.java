/**
 * File Name: StorageLogVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-25<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.product.bean.StorageLogBean;


/**
 * StorageLogVO
 * 
 * @author ZHUZHU
 * @version 2010-8-25
 * @see StorageLogVO
 * @since 1.0
 */
@Entity(inherit = true)
public class StorageLogVO extends StorageLogBean
{
    @Relationship(relationField = "depotpartId")
    private String depotpartName = "";

    @Relationship(relationField = "storageId")
    private String storageName = "";

    @Relationship(relationField = "productId")
    private String productName = "";

    @Relationship(relationField = "productId", tagField = "code")
    private String productCode = "";

    @Relationship(relationField = "locationId")
    private String locationName = "";

    @Ignore
    private String typeName = "";

    /**
     * default constructor
     */
    public StorageLogVO()
    {
    }

    /**
     * @return the depotpartName
     */
    public String getDepotpartName()
    {
        return depotpartName;
    }

    /**
     * @param depotpartName
     *            the depotpartName to set
     */
    public void setDepotpartName(String depotpartName)
    {
        this.depotpartName = depotpartName;
    }

    /**
     * @return the storageName
     */
    public String getStorageName()
    {
        return storageName;
    }

    /**
     * @param storageName
     *            the storageName to set
     */
    public void setStorageName(String storageName)
    {
        this.storageName = storageName;
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
     * @return the locationName
     */
    public String getLocationName()
    {
        return locationName;
    }

    /**
     * @param locationName
     *            the locationName to set
     */
    public void setLocationName(String locationName)
    {
        this.locationName = locationName;
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
            .append("StorageLogVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("depotpartName = ")
            .append(this.depotpartName)
            .append(TAB)
            .append("storageName = ")
            .append(this.storageName)
            .append(TAB)
            .append("productName = ")
            .append(this.productName)
            .append(TAB)
            .append("locationName = ")
            .append(this.locationName)
            .append(TAB)
            .append("typeName = ")
            .append(this.typeName)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

    /**
     * @return the productCode
     */
    public String getProductCode()
    {
        return productCode;
    }

    /**
     * @param productCode
     *            the productCode to set
     */
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
}
