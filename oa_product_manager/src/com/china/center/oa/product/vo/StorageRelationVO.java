/**
 * File Name: StorageRelationVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-22<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.product.vs.StorageRelationBean;


/**
 * StorageRelationVO
 * 
 * @author ZHUZHU
 * @version 2010-8-22
 * @see StorageRelationVO
 * @since 1.0
 */
@Entity(inherit = true)
public class StorageRelationVO extends StorageRelationBean
{
    @Relationship(relationField = "productId")
    private String productName = "";

    @Relationship(relationField = "productId", tagField = "code")
    private String productCode = "";

    @Relationship(relationField = "productId", tagField = "reserve4")
    private String productMtype = "";

    @Relationship(relationField = "productId", tagField = "type")
    private int productType = 0;

    @Relationship(relationField = "productId", tagField = "sailType")
    private int productSailType = 0;
    
    @Relationship(relationField = "productId", tagField = "sailPrice")
    private double productSailPrice = 0.0d;

    @Relationship(relationField = "storageId")
    private String storageName = "";

    @Relationship(relationField = "depotpartId")
    private String depotpartName = "";

    @Relationship(relationField = "depotpartId", tagField = "type")
    private int depotpartType = 0;

    @Relationship(relationField = "locationId")
    private String locationName = "";

    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    /**
     * industryId
     */
    @Relationship(relationField = "locationId", tagField = "industryId")
    private String industryId = "";

    @Relationship(relationField = "productId", tagField = "consumeInDay")
    private int pconsumeInDay = 0;
    
    @Ignore
    private int mayAmount = 0;

    @Ignore
    private int inwayAmount = 0;

    /**
     * 合计量
     */
    @Ignore
    private int total = 0;

    /**
     * 预先分配的,即销售单未审批的
     */
    @Ignore
    private int preassignAmount = 0;

    /**
     * 差误的数量
     */
    @Ignore
    private int errorAmount = 0;

    /**
     * 产品批发价
     */
    @Ignore
    private double batchPrice = 0.0d;

    /**
     * 产品成本价
     */
    @Ignore
    private double costPrice = 0.0d;

    /**
     * 增量后的成本
     */
    @Ignore
    private double addPrice = 0.0d;

    @Ignore
    private String showJOSNStr = "";

    /**
     * 事业部名
     */
    @Ignore
    private String industryName = "";
    
    /**
     * 地点名（行政管理中心）
     */
    @Ignore
    private String industryName2 = "";
    
    /**
     * 业务录入价
     */
    @Ignore
    private double inputPrice = 0.0d;
    
    /**
     * default constructor
     */
    public StorageRelationVO()
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
     * @return the mayAmount
     */
    public int getMayAmount()
    {
        return mayAmount;
    }

    /**
     * @param mayAmount
     *            the mayAmount to set
     */
    public void setMayAmount(int mayAmount)
    {
        this.mayAmount = mayAmount;
    }

    /**
     * @return the preassignAmount
     */
    public int getPreassignAmount()
    {
        return preassignAmount;
    }

    /**
     * @param preassignAmount
     *            the preassignAmount to set
     */
    public void setPreassignAmount(int preassignAmount)
    {
        this.preassignAmount = preassignAmount;
    }

    /**
     * @return the errorAmount
     */
    public int getErrorAmount()
    {
        return errorAmount;
    }

    /**
     * @param errorAmount
     *            the errorAmount to set
     */
    public void setErrorAmount(int errorAmount)
    {
        this.errorAmount = errorAmount;
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
     * @return the batchPrice
     */
    public double getBatchPrice()
    {
        return batchPrice;
    }

    /**
     * @param batchPrice
     *            the batchPrice to set
     */
    public void setBatchPrice(double batchPrice)
    {
        this.batchPrice = batchPrice;
    }

    /**
     * @return the depotpartType
     */
    public int getDepotpartType()
    {
        return depotpartType;
    }

    /**
     * @param depotpartType
     *            the depotpartType to set
     */
    public void setDepotpartType(int depotpartType)
    {
        this.depotpartType = depotpartType;
    }

    /**
     * @return the inwayAmount
     */
    public int getInwayAmount()
    {
        return inwayAmount;
    }

    /**
     * @param inwayAmount
     *            the inwayAmount to set
     */
    public void setInwayAmount(int inwayAmount)
    {
        this.inwayAmount = inwayAmount;
    }

    /**
     * @return the total
     */
    public int getTotal()
    {
        return total;
    }

    /**
     * @param total
     *            the total to set
     */
    public void setTotal(int total)
    {
        this.total = total;
    }

    /**
     * @return the costPrice
     */
    public double getCostPrice()
    {
        return costPrice;
    }

    /**
     * @param costPrice
     *            the costPrice to set
     */
    public void setCostPrice(double costPrice)
    {
        this.costPrice = costPrice;
    }

    /**
     * @return the productMtype
     */
    public String getProductMtype()
    {
        return productMtype;
    }

    /**
     * @param productMtype
     *            the productMtype to set
     */
    public void setProductMtype(String productMtype)
    {
        this.productMtype = productMtype;
    }

    /**
     * @return the productType
     */
    public int getProductType()
    {
        return productType;
    }

    /**
     * @param productType
     *            the productType to set
     */
    public void setProductType(int productType)
    {
        this.productType = productType;
    }

    /**
     * @return the productSailType
     */
    public int getProductSailType()
    {
        return productSailType;
    }

    /**
     * @param productSailType
     *            the productSailType to set
     */
    public void setProductSailType(int productSailType)
    {
        this.productSailType = productSailType;
    }

    /**
     * @return the showJOSNStr
     */
    public String getShowJOSNStr()
    {
        return showJOSNStr;
    }

    /**
     * @param showJOSNStr
     *            the showJOSNStr to set
     */
    public void setShowJOSNStr(String showJOSNStr)
    {
        this.showJOSNStr = showJOSNStr;
    }

    /**
     * @return the addPrice
     */
    public double getAddPrice()
    {
        return addPrice;
    }

    /**
     * @param addPrice
     *            the addPrice to set
     */
    public void setAddPrice(double addPrice)
    {
        this.addPrice = addPrice;
    }

    /**
     * @return the industryId
     */
    public String getIndustryId()
    {
        return industryId;
    }

    /**
     * @param industryId
     *            the industryId to set
     */
    public void setIndustryId(String industryId)
    {
        this.industryId = industryId;
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

    public double getInputPrice()
	{
		return inputPrice;
	}

	public void setInputPrice(double inputPrice)
	{
		this.inputPrice = inputPrice;
	}
	
	

	public double getProductSailPrice() {
		return productSailPrice;
	}

	public void setProductSailPrice(double productSailPrice) {
		this.productSailPrice = productSailPrice;
	}

	public int getPconsumeInDay()
	{
		return pconsumeInDay;
	}

	public void setPconsumeInDay(int pconsumeInDay)
	{
		this.pconsumeInDay = pconsumeInDay;
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
            .append("StorageRelationVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("productName = ")
            .append(this.productName)
            .append(TAB)
            .append("productCode = ")
            .append(this.productCode)
            .append(TAB)
            .append("productMtype = ")
            .append(this.productMtype)
            .append(TAB)
            .append("productType = ")
            .append(this.productType)
            .append(TAB)
            .append("productSailType = ")
            .append(this.productSailType)
            .append(TAB)
            .append("storageName = ")
            .append(this.storageName)
            .append(TAB)
            .append("depotpartName = ")
            .append(this.depotpartName)
            .append(TAB)
            .append("depotpartType = ")
            .append(this.depotpartType)
            .append(TAB)
            .append("locationName = ")
            .append(this.locationName)
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("industryId = ")
            .append(this.industryId)
            .append(TAB)
            .append("mayAmount = ")
            .append(this.mayAmount)
            .append(TAB)
            .append("inwayAmount = ")
            .append(this.inwayAmount)
            .append(TAB)
            .append("total = ")
            .append(this.total)
            .append(TAB)
            .append("preassignAmount = ")
            .append(this.preassignAmount)
            .append(TAB)
            .append("errorAmount = ")
            .append(this.errorAmount)
            .append(TAB)
            .append("batchPrice = ")
            .append(this.batchPrice)
            .append(TAB)
            .append("costPrice = ")
            .append(this.costPrice)
            .append(TAB)
            .append("addPrice = ")
            .append(this.addPrice)
            .append(TAB)
            .append("showJOSNStr = ")
            .append(this.showJOSNStr)
            .append(TAB)
            .append("industryName = ")
            .append(this.industryName)
            .append(TAB)
            .append("industryName2 = ")
            .append(this.industryName2)
            .append(TAB) 
            .append("productSailPrice = ")
            .append(this.productSailPrice)
            .append(TAB)     
            .append(" )");

        return retValue.toString();
    }

}
