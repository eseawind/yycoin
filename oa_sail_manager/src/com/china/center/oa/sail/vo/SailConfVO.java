/**
 * File Name: SailConfVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2012-2-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.sail.bean.SailConfBean;


/**
 * SailConfVO
 * 
 * @author ZHUZHU
 * @version 2012-2-12
 * @see SailConfVO
 * @since 3.0
 */
@Entity(inherit = true)
public class SailConfVO extends SailConfBean
{
    @Relationship(relationField = "productId")
    private String productName = "公共";

    @Relationship(relationField = "productId", tagField = "sailPrice")
    private double sailPrice = 0.0d;

    @Relationship(relationField = "industryId")
    private String industryName = "";
    
    @Ignore
    private String naShuiShiTiName = "";
    
    @Ignore
    private String industryTmpName = "";

    /**
     * default constructor
     */
    public SailConfVO()
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
     * @return the industryName
     */
    public String getIndustryName()
    {
        return industryName;
    }

    /**
     * @param industryName
     *            the industryName to set
     */
    public void setIndustryName(String industryName)
    {
        this.industryName = industryName;
    }

    /**
     * @return the sailPrice
     */
    public double getSailPrice()
    {
        return sailPrice;
    }

    /**
     * @param sailPrice
     *            the sailPrice to set
     */
    public void setSailPrice(double sailPrice)
    {
        this.sailPrice = sailPrice;
    }
    
    public String getNaShuiShiTiName() {
		return naShuiShiTiName;
	}

	public void setNaShuiShiTiName(String naShuiShiTiName) {
		this.naShuiShiTiName = naShuiShiTiName;
	}
	
	

	public String getIndustryTmpName() {
		return industryTmpName;
	}

	public void setIndustryTmpName(String industryTmpName) {
		this.industryTmpName = industryTmpName;
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

        retValue.append("SailConfVO ( ").append(super.toString()).append(TAB).append(
            "productName = ").append(this.productName).append(TAB).append("sailPrice = ").append(
            this.sailPrice).append(TAB).append("industryName = ").append(this.industryName).append(
            		TAB).append("naShuiShiTiName = ").append(this.naShuiShiTiName).append(TAB).append("industryTmpName = ")
            .append(this.industryTmpName).append(TAB).append(" )");

        return retValue.toString();
    }

}
