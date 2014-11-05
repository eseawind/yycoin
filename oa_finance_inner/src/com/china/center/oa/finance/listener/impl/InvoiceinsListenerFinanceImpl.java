package com.china.center.oa.finance.listener.impl;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.client.bean.AssignApplyBean;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.listener.ClientListener;
import com.china.center.oa.finance.manager.InvoiceinsManager;

public class InvoiceinsListenerFinanceImpl implements ClientListener
{
	private InvoiceinsManager invoiceinsManager = null;
	
	public String getListenerType()
	{
		return "CustomerListener.FinanceImpl";
	}

	public double onNoPayBusiness(CustomerBean bean)
	{
		return 0;
	}

	public void onDelete(CustomerBean bean) throws MYException
	{
		
	}

	public void onChangeCustomerRelation(User user, AssignApplyBean apply,
			CustomerBean cus) throws MYException
	{
		/*
		String flag = "Customer";
		
		String customerId = apply.getCustomerId();
		
    	List<InvoiceinsBean> invList = invoiceinsManager.autoAddOut(user, "", flag, customerId);
    	
    	List<InvoiceinsBean> invList1 = invoiceinsManager.autoAddOutBalance(user, "", flag, customerId);
    	
    	// persist and trigger tax 
    	invoiceinsManager.batchAddInvoiceins(user, invList, invList1);
	*/
		}

	public InvoiceinsManager getInvoiceinsManager()
	{
		return invoiceinsManager;
	}

	public void setInvoiceinsManager(InvoiceinsManager invoiceinsManager)
	{
		this.invoiceinsManager = invoiceinsManager;
	}
	
	
}
