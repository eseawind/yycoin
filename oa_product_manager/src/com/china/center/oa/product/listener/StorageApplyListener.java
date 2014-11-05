package com.china.center.oa.product.listener;

import com.center.china.osgi.publics.ParentListener;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.GSOutBean;

public interface StorageApplyListener extends ParentListener
{
	void onConfirmGSOut(User user, GSOutBean bean)
    throws MYException;
}
