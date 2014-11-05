package com.china.center.oa.client.manager;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.client.bean.AppUserBean;
import com.china.center.oa.client.bean.AppUserVSCustomerBean;
import com.china.center.oa.client.vo.AppUserVO;

public interface AppUserManager
{
	AppUserVO createOrModifyUser(AppUserBean bean) throws MYException;
	
	boolean modifyPassword(String userId, String orignalPassword, String newPassword) throws MYException;
	
	boolean passAppUserApply(User user, AppUserVSCustomerBean appUserVSCust) throws MYException;
	
	boolean rejectAppUserApply(User user, String appUserId, String reason) throws MYException;
	
	boolean addAppUserCust(User user, AppUserVSCustomerBean appUserVSCust) throws MYException;
}
