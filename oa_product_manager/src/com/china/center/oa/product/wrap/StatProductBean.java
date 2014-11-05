package com.china.center.oa.product.wrap;


import com.china.center.oa.product.bean.StorageLogBean;


/**
 * 盘点报表
 * 
 * @author ZHUZHU
 * @version 2007-9-2
 * @see
 * @since
 */
public class StatProductBean extends StorageLogBean
{
    /**
     * 储位
     */
    private String storageName = "";

    private String depotpartName = "";

    /**
     * 产品名称
     */
    private String productName = "";

    /**
     * 产品名称
     */
    private String productCode = "";

    private String locationName = "";

    /**
     * 当前数量
     */
    private int currentAmount = 0;

    /**
     * 事业部名
     */
    private String industryName = "";
    
    /**
     * 地点名（行政管理中心）
     */
    private String industryName2 = "";
    
    /**
     * default constructor
     */
    public StatProductBean()
    {
    }

    /**
     * @return the storageName
     */
    public String getStorageName()
    {
        return storageName;
    }

    /**
     * @return the productName
     */
    public String getProductName()
    {
        return productName;
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
     * @param productName
     *            the productName to set
     */
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    /**
     * @return the currentAmount
     */
    public int getCurrentAmount()
    {
        return currentAmount;
    }

    /**
     * @param currentAmount
     *            the currentAmount to set
     */
    public void setCurrentAmount(int currentAmount)
    {
        this.currentAmount = currentAmount;
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
    
    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getIndustryName2() {
        return industryName2;
    }

    public void setIndustryName2(String industryName2) {
        this.industryName2 = industryName2;
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
            .append("StatProductBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("storageName = ")
            .append(this.storageName)
            .append(TAB)
            .append("depotpartName = ")
            .append(this.depotpartName)
            .append(TAB)
            .append("productName = ")
            .append(this.productName)
            .append(TAB)
            .append("productCode = ")
            .append(this.productCode)
            .append(TAB)
            .append("locationName = ")
            .append(this.locationName)
            .append(TAB)
            .append("currentAmount = ")
            .append(this.currentAmount)
            .append(TAB)
            .append("industryName = ")
            .append(this.industryName)
            .append(TAB)
            .append("industryName2 = ")
            .append(this.industryName2)
            .append(TAB)            
            .append(" )");

        return retValue.toString();
    }
}
