/**
 * File Name: ProductVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.vo;


import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.product.bean.ProductBean;


/**
 * ProductVO
 * 
 * @author ZHUZHU
 * @version 2010-8-15
 * @see ProductVO
 * @since 1.0
 */
@Entity(inherit = true)
public class ProductVO extends ProductBean
{
    @Relationship(relationField = "createrId")
    private String createrName = "";

    @Ignore
    private String mainProviderName = "";

    @Ignore
    private String assistantProviderName1 = "";

    @Ignore
    private String assistantProviderName2 = "";

    @Ignore
    private String assistantProviderName3 = "";

    @Ignore
    private String assistantProviderName4 = "";

    /**
     * 合成产品在销售时可卖数
     */
    @Ignore    
    private int mayAmount = 0;
    
    /**
     * 合成产品的结算价（子产品结算价之和）
     */
    @Ignore
    private double addPrice = 0.0d;
    
    /**
     * 合成产品的结算价
     */
    @Ignore
    private double inputPrice = 0.0d;
    
     /**
      * 合成产品的真实成本（子产品真实成本的之和）
      */
    @Ignore    
    private double price = 0.0d;        
    
    @Ignore
    private List<ProductCombinationVO> vsVOList = null;

    /**
     * default constructor
     */
    public ProductVO()
    {
    }

    /**
     * @return the createrName
     */
    public String getCreaterName()
    {
        return createrName;
    }

    /**
     * @param createrName
     *            the createrName to set
     */
    public void setCreaterName(String createrName)
    {
        this.createrName = createrName;
    }

    /**
     * @return the vsVOList
     */
    public List<ProductCombinationVO> getVsVOList()
    {
        return vsVOList;
    }

    /**
     * @param vsVOList
     *            the vsVOList to set
     */
    public void setVsVOList(List<ProductCombinationVO> vsVOList)
    {
        this.vsVOList = vsVOList;
    }

    /**
     * @return the mainProviderName
     */
    public String getMainProviderName()
    {
        return mainProviderName;
    }

    /**
     * @param mainProviderName
     *            the mainProviderName to set
     */
    public void setMainProviderName(String mainProviderName)
    {
        this.mainProviderName = mainProviderName;
    }

    /**
     * @return the assistantProviderName1
     */
    public String getAssistantProviderName1()
    {
        return assistantProviderName1;
    }

    /**
     * @param assistantProviderName1
     *            the assistantProviderName1 to set
     */
    public void setAssistantProviderName1(String assistantProviderName1)
    {
        this.assistantProviderName1 = assistantProviderName1;
    }

    /**
     * @return the assistantProviderName2
     */
    public String getAssistantProviderName2()
    {
        return assistantProviderName2;
    }

    /**
     * @param assistantProviderName2
     *            the assistantProviderName2 to set
     */
    public void setAssistantProviderName2(String assistantProviderName2)
    {
        this.assistantProviderName2 = assistantProviderName2;
    }

    /**
     * @return the assistantProviderName3
     */
    public String getAssistantProviderName3()
    {
        return assistantProviderName3;
    }

    /**
     * @param assistantProviderName3
     *            the assistantProviderName3 to set
     */
    public void setAssistantProviderName3(String assistantProviderName3)
    {
        this.assistantProviderName3 = assistantProviderName3;
    }

    /**
     * @return the assistantProviderName4
     */
    public String getAssistantProviderName4()
    {
        return assistantProviderName4;
    }

    /**
     * @param assistantProviderName4
     *            the assistantProviderName4 to set
     */
    public void setAssistantProviderName4(String assistantProviderName4)
    {
        this.assistantProviderName4 = assistantProviderName4;
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

	public double getInputPrice()
	{
		return inputPrice;
	}

	public void setInputPrice(double inputPrice)
	{
		this.inputPrice = inputPrice;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
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
            .append("ProductVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("createrName = ")
            .append(this.createrName)
            .append(TAB)
            .append("mainProviderName = ")
            .append(this.mainProviderName)
            .append(TAB)
            .append("assistantProviderName1 = ")
            .append(this.assistantProviderName1)
            .append(TAB)
            .append("assistantProviderName2 = ")
            .append(this.assistantProviderName2)
            .append(TAB)
            .append("assistantProviderName3 = ")
            .append(this.assistantProviderName3)
            .append(TAB)
            .append("assistantProviderName4 = ")
            .append(this.assistantProviderName4)
            .append(TAB)
            .append("vsVOList = ")
            .append(this.vsVOList)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
