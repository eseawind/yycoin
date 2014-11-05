package com.china.center.oa.finance.listener;

import java.util.List;

import com.center.china.osgi.publics.ParentListener;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.bean.PaymentBean;

public interface AutoPayListener extends ParentListener
{
	void onCompletePaymentToPre(PaymentBean payment) throws MYException;
	
	void onRefInbillToSail(List<InBillBean> billList) throws MYException;
}
