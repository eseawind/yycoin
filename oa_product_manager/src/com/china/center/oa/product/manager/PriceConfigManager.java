package com.china.center.oa.product.manager;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.PriceConfigBean;

public interface PriceConfigManager
{

	boolean addBean(PriceConfigBean bean, User user) throws MYException;
	
	boolean updateBean(PriceConfigBean bean, User user) throws MYException;
	
	boolean deleteBean(String id, User user) throws MYException;
	
	PriceConfigBean getPriceConfigBean(String productId, String industryId, String stafferId);
	
	PriceConfigBean calcSailPrice(PriceConfigBean bean);
}
