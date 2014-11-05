/**
 * File Name: ComposeProductVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.vo;


import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.product.bean.ComposeProductBean;


/**
 * ComposeProductVO
 * 
 * @author ZHUZHU
 * @version 2010-10-2
 * @see ComposeProductVO
 * @since 1.0
 */
@Entity(inherit = true)
public class ComposeProductVO extends ComposeProductBean
{
    @Relationship(relationField = "stafferId")
    private String stafferName = "";

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

    @Ignore
    private String otherId = "";

    @Ignore
    private List<ComposeItemVO> itemVOList = null;

    @Ignore
    private List<ComposeFeeVO> feeVOList = null;

    /**
     * default constructor
     */
    public ComposeProductVO()
    {
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
     * @return the itemVOList
     */
    public List<ComposeItemVO> getItemVOList()
    {
        return itemVOList;
    }

    /**
     * @param itemVOList
     *            the itemVOList to set
     */
    public void setItemVOList(List<ComposeItemVO> itemVOList)
    {
        this.itemVOList = itemVOList;
    }

    /**
     * @return the feeVOList
     */
    public List<ComposeFeeVO> getFeeVOList()
    {
        return feeVOList;
    }

    /**
     * @param feeVOList
     *            the feeVOList to set
     */
    public void setFeeVOList(List<ComposeFeeVO> feeVOList)
    {
        this.feeVOList = feeVOList;
    }

    /**
     * @return the otherId
     */
    public String getOtherId()
    {
        return otherId;
    }

    /**
     * @param otherId
     *            the otherId to set
     */
    public void setOtherId(String otherId)
    {
        this.otherId = otherId;
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
            .append("ComposeProductVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("productName = ")
            .append(this.productName)
            .append(TAB)
            .append("productCode = ")
            .append(this.productCode)
            .append(TAB)
            .append("deportName = ")
            .append(this.deportName)
            .append(TAB)
            .append("depotpartName = ")
            .append(this.depotpartName)
            .append(TAB)
            .append("storageName = ")
            .append(this.storageName)
            .append(TAB)
            .append("otherId = ")
            .append(this.otherId)
            .append(TAB)
            .append("itemVOList = ")
            .append(this.itemVOList)
            .append(TAB)
            .append("feeVOList = ")
            .append(this.feeVOList)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
