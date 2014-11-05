package com.china.center.oa.sail.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.sail.bean.AuditRuleItemBean;

@Entity(inherit = true)
public class AuditRuleItemVO extends AuditRuleItemBean
{

	/**
	 * 产品
	 */
	@Relationship(relationField = "productId")
	private String productName = "";

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}
	
	public String toString()
	{
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("AuditRuleItemVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("productName = ")
            .append(this.productName)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
	
}
