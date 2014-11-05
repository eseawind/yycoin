package com.china.center.oa.finance.manager;

import com.center.china.osgi.publics.ListenerManager;
import com.china.center.oa.finance.listener.AutoPayListener;

public interface AutoPayManager extends ListenerManager<AutoPayListener>
{
	/**
	 * 自动处理回款到预收（全部为预收）
	 */
	void autoProcessPaymentToPre();
	
	/**
	 * 自动勾销售,预收勾应收
	 */
	void autoRefInbillToSail();
}
