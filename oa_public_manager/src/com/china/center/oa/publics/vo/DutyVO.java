/**
 * File Name: DutyVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-28<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.vo;


import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.InvoiceBean;


/**
 * DutyVO
 * 
 * @author ZHUZHU
 * @version 2011-2-28
 * @see DutyVO
 * @since 3.0
 */
@Entity(inherit = true)
public class DutyVO extends DutyBean
{
	@Relationship(relationField = "invoicer")
	private String invoicerName = "";
	
    @Ignore
    private List<InvoiceBean> inInvoiceBeanList = null;

    @Ignore
    private List<InvoiceBean> outInvoiceBeanList = null;

    /**
     * default constructor
     */
    public DutyVO()
    {
    }

    /**
     * default constructor
     */
    public DutyVO(DutyBean dutyBean)
    {
    }

    /**
     * @return the inInvoiceBeanList
     */
    public List<InvoiceBean> getInInvoiceBeanList()
    {
        return inInvoiceBeanList;
    }

    /**
     * @param inInvoiceBeanList
     *            the inInvoiceBeanList to set
     */
    public void setInInvoiceBeanList(List<InvoiceBean> inInvoiceBeanList)
    {
        this.inInvoiceBeanList = inInvoiceBeanList;
    }

    /**
     * @return the outInvoiceBeanList
     */
    public List<InvoiceBean> getOutInvoiceBeanList()
    {
        return outInvoiceBeanList;
    }

    /**
     * @param outInvoiceBeanList
     *            the outInvoiceBeanList to set
     */
    public void setOutInvoiceBeanList(List<InvoiceBean> outInvoiceBeanList)
    {
        this.outInvoiceBeanList = outInvoiceBeanList;
    }

    public String getInvoicerName()
	{
		return invoicerName;
	}

	public void setInvoicerName(String invoicerName)
	{
		this.invoicerName = invoicerName;
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

        retValue.append("DutyVO ( ").append(super.toString()).append(TAB).append(
            "inInvoiceBeanList = ").append(this.inInvoiceBeanList).append(TAB).append(
            "outInvoiceBeanList = ").append(this.outInvoiceBeanList).append(TAB).append(" )");

        return retValue.toString();
    }

}
