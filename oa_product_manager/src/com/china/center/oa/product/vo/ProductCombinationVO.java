/**
 * File Name: ProductCombinationVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.product.vs.ProductCombinationBean;


/**
 * ProductCombinationVO
 * 
 * @author ZHUZHU
 * @version 2010-8-15
 * @see ProductCombinationVO
 * @since 1.0
 */
@Entity(inherit = true)
public class ProductCombinationVO extends ProductCombinationBean
{
    @Relationship(relationField = "vproductId")
    private String vproductName = "";

    @Relationship(relationField = "sproductId")
    private String sproductName = "";

    @Relationship(relationField = "sproductId", tagField = "code")
    private String sproductCode = "";
    
    /**
     * 可卖数
     */
    @Ignore
    private int mayAmount = 0;
    
    /**
     * 子产品结算价
     */
    @Ignore
    private double addPrice = 0.0d;
    
    /**
     * default constructor
     */
    public ProductCombinationVO()
    {
    }

    /**
     * @return the vproductName
     */
    public String getVproductName()
    {
        return vproductName;
    }

    /**
     * @param vproductName
     *            the vproductName to set
     */
    public void setVproductName(String vproductName)
    {
        this.vproductName = vproductName;
    }

    /**
     * @return the sproductName
     */
    public String getSproductName()
    {
        return sproductName;
    }

    /**
     * @param sproductName
     *            the sproductName to set
     */
    public void setSproductName(String sproductName)
    {
        this.sproductName = sproductName;
    }

    /**
     * @return the sproductCode
     */
    public String getSproductCode()
    {
        return sproductCode;
    }

    /**
     * @param sproductCode
     *            the sproductCode to set
     */
    public void setSproductCode(String sproductCode)
    {
        this.sproductCode = sproductCode;
    }

	public int getMayAmount()
	{
		return mayAmount;
	}

	public void setMayAmount(int mayAmount)
	{
		this.mayAmount = mayAmount;
	}

	public double getAddPrice()
	{
		return addPrice;
	}

	public void setAddPrice(double addPrice)
	{
		this.addPrice = addPrice;
	}
}
