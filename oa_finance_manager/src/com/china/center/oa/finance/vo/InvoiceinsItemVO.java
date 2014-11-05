package com.china.center.oa.finance.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.finance.bean.InvoiceinsItemBean;

@Entity(inherit = true)
public class InvoiceinsItemVO extends InvoiceinsItemBean
{

	/**
	 * 产品名称
	 */
	@Relationship(relationField = "productId")
	private String productName = "";

	@Ignore
    private double taxrate = 0.0d;
	
	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}
	
	/**
	 * @return the taxrate
	 */
	public double getTaxrate() {
		return taxrate;
	}

	/**
	 * @param taxrate the taxrate to set
	 */
	public void setTaxrate(double taxrate) {
		this.taxrate = taxrate;
	}

	public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("InvoiceinsItemVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("productName = ")
            .append(this.productName)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
	
	
}
