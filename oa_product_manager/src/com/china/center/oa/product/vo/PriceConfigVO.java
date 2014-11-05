/**
 * 
 */
package com.china.center.oa.product.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.product.bean.PriceConfigBean;

/**
 * @author smart
 *
 */
@Entity(inherit = true)
public class PriceConfigVO extends PriceConfigBean
{

	/**
	 * 产品名称
	 */
	@Relationship(relationField = "productId")
	private String productName = "";
	
	/**
	 * 事业部
	 */
	@Ignore
	private String industryName = "";
	
	/**
	 * 职员
	 */
	@Relationship(relationField = "stafferId")
	private String stafferName = "";

	/**
	 * 产品性质
	 */
	@Relationship(relationField = "productId", tagField = "ptype")
	private int nature = 0;
	
	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public String getIndustryName()
	{
		return industryName;
	}

	public void setIndustryName(String industryName)
	{
		this.industryName = industryName;
	}

	public String getStafferName()
	{
		return stafferName;
	}

	public void setStafferName(String stafferName)
	{
		this.stafferName = stafferName;
	}
	
	public int getNature()
	{
		return nature;
	}

	public void setNature(int nature)
	{
		this.nature = nature;
	}

	public String toString()
	{
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("ProductPriceConfigVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("productName = ")
            .append(this.productName)
            .append(TAB)
            .append("industryName = ")
            .append(this.industryName)
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("nature = ")
            .append(this.nature)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
