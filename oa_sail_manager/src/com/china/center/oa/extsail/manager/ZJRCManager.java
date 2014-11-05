package com.china.center.oa.extsail.manager;

import java.util.Map;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.extsail.bean.ZJRCOutBean;
import com.china.center.oa.extsail.bean.ZJRCProductBean;

public interface ZJRCManager
{
	boolean addZJRCProduct(User user, ZJRCProductBean bean) throws MYException;
	
	boolean updateZJRCProduct(User user, ZJRCProductBean bean) throws MYException;
	
	boolean deleteZJRCProduct(User user, String id) throws MYException;
	
	String addZJRCOut(final ZJRCOutBean outBean, @SuppressWarnings("rawtypes") final Map dataMap, final User user)
    throws MYException;
	
	int submit(final String fullId, final User user)
    throws MYException;
	
	boolean delZJRCOut(User user, String fullId) throws MYException;
	
	// 紫金订单 -> OA 订单
	void createZJRC2OAOut();
	
	void modifyStatusWithoutTrans(String fullId, String refBaseId);
	
	boolean modifyStatus(String fullId, int status) throws MYException;
	
	boolean createOneZJRC(String fullId) throws MYException;
}
