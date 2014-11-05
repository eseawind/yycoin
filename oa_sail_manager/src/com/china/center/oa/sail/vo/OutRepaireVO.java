package com.china.center.oa.sail.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.sail.bean.OutRepaireBean;

@Entity(inherit = true)
public class OutRepaireVO extends OutRepaireBean
{
	@Relationship(relationField = "invoiceId")
    private String invoiceName = "";

    @Relationship(relationField = "dutyId")
    private String dutyName = "";

	public String getInvoiceName()
	{
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName)
	{
		this.invoiceName = invoiceName;
	}

	public String getDutyName()
	{
		return dutyName;
	}

	public void setDutyName(String dutyName)
	{
		this.dutyName = dutyName;
	}
    
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("OutRepaireVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("invoiceName = ")
            .append(this.invoiceName)
            .append(TAB)
            .append("dutyName = ")
            .append(this.dutyName)            
            .append(TAB)
            .append(" )");

        return retValue.toString();    
    }
}
