/**
 * File Name: PriceChangeSrcItemVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.product.bean.PriceChangeSrcItemBean;


/**
 * PriceChangeSrcItemVO
 * 
 * @author ZHUZHU
 * @version 2010-10-4
 * @see PriceChangeSrcItemVO
 * @since 1.0
 */
@Entity(inherit = true)
public class PriceChangeSrcItemVO extends PriceChangeSrcItemBean
{
    @Relationship(relationField = "productId")
    private String productName = "";

    @Relationship(relationField = "productId", tagField = "code")
    private String productCode = "";

    @Relationship(relationField = "deportId")
    private String deportName = "";

    @Relationship(relationField = "depotpartId")
    private String depotpartName = "";

    @Relationship(relationField = "storageId")
    private String storageName = "";

    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    /**
     * default constructor
     */
    public PriceChangeSrcItemVO()
    {
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

    /**
     * @return the deportName
     */
    public String getDeportName()
    {
        return deportName;
    }

    /**
     * @param deportName
     *            the deportName to set
     */
    public void setDeportName(String deportName)
    {
        this.deportName = deportName;
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
     * @return the stafferName
     */
    public String getStafferName()
    {
        return stafferName;
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
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuffer retValue = new StringBuffer();

        retValue.append("PriceChangeSrcItemVO ( ").append(super.toString()).append(TAB).append("productName = ").append(
            this.productName).append(TAB).append("productCode = ").append(this.productCode).append(TAB).append(
            "deportName = ").append(this.deportName).append(TAB).append("depotpartName = ").append(this.depotpartName).append(
            TAB).append("storageName = ").append(this.storageName).append(TAB).append("stafferName = ").append(
            this.stafferName).append(TAB).append(" )");

        return retValue.toString();
    }

}
