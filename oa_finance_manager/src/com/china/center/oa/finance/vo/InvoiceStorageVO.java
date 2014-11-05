package com.china.center.oa.finance.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.finance.bean.InvoiceStorageBean;

@SuppressWarnings("serial")
@Entity(inherit = true)
public class InvoiceStorageVO extends InvoiceStorageBean
{
	@Relationship(relationField = "invoiceId")
    private String invoiceName = "";
	
	@Ignore
	private int invoiceAmount = 0;
	
	@Ignore
	private double mayConfirmMoneys = 0.0d;

	public InvoiceStorageVO()
	{
	}

	/**
	 * @return the invoiceName
	 */
	public String getInvoiceName()
	{
		return invoiceName;
	}

	/**
	 * @param invoiceName the invoiceName to set
	 */
	public void setInvoiceName(String invoiceName)
	{
		this.invoiceName = invoiceName;
	}

	/**
	 * @return the invoiceAmount
	 */
	public int getInvoiceAmount()
	{
		return invoiceAmount;
	}

	/**
	 * @param invoiceAmount the invoiceAmount to set
	 */
	public void setInvoiceAmount(int invoiceAmount)
	{
		this.invoiceAmount = invoiceAmount;
	}

	/**
	 * @return the mayConfirmMoneys
	 */
	public double getMayConfirmMoneys()
	{
		return mayConfirmMoneys;
	}

	/**
	 * @param mayConfirmMoneys the mayConfirmMoneys to set
	 */
	public void setMayConfirmMoneys(double mayConfirmMoneys)
	{
		this.mayConfirmMoneys = mayConfirmMoneys;
	}
}
