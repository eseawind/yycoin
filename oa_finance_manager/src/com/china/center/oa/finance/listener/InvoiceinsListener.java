package com.china.center.oa.finance.listener;

import com.center.china.osgi.publics.ParentListener;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.InvoiceinsBean;

public interface InvoiceinsListener extends ParentListener
{
	void onConfirmPay(User user, InvoiceinsBean bean)
	throws MYException;
	
}
