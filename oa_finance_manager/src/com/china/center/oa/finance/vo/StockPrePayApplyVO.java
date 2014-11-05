package com.china.center.oa.finance.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.finance.bean.StockPrePayApplyBean;

@SuppressWarnings("serial")
@Entity(inherit = true)
public class StockPrePayApplyVO extends StockPrePayApplyBean
{
 	@Relationship(relationField = "invoiceId")
    private String invoiceName = "";

    @Relationship(relationField = "providerId")
    private String providerName = "";

    @Relationship(relationField = "providerId", tagField = "bank")
    private String provideBank = "";

    @Relationship(relationField = "stafferId")
    private String stafferName = "";

	public StockPrePayApplyVO()
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
	 * @return the provideName
	 */
	public String getProviderName()
	{
		return providerName;
	}

	/**
	 * @param provideName the provideName to set
	 */
	public void setProviderName(String providerName)
	{
		this.providerName = providerName;
	}

	/**
	 * @return the provideBank
	 */
	public String getProvideBank()
	{
		return provideBank;
	}

	/**
	 * @param provideBank the provideBank to set
	 */
	public void setProvideBank(String provideBank)
	{
		this.provideBank = provideBank;
	}

	/**
	 * @return the stafferName
	 */
	public String getStafferName()
	{
		return stafferName;
	}

	/**
	 * @param stafferName the stafferName to set
	 */
	public void setStafferName(String stafferName)
	{
		this.stafferName = stafferName;
	}
}
