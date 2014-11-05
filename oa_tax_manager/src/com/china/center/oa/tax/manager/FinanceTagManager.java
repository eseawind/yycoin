package com.china.center.oa.tax.manager;

import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.tax.bean.FinanceTagBean;

public interface FinanceTagManager
{
	boolean addFinanceTagBeanWithoutTransaction(User user, FinanceTagBean bean)
		throws MYException;
	
	boolean addFinanceTagBeanWithoutTransaction(User user, List<FinanceTagBean> list)
	throws MYException;
	
	boolean addFinanceTagBeanWithTransaction(User user, FinanceTagBean bean)
	throws MYException;
	
	/**
	 * 定时处理已开票的数据，为财务销售出库标记数据提供支持
	 */
	void processInvoiceinsTagData();
	
	/**
	 * 定时处理销售单回款标记
	 */
	void processOutPayTagData();
}
