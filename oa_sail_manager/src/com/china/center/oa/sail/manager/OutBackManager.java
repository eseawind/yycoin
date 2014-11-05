package com.china.center.oa.sail.manager;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.sail.bean.OutBackBean;

public interface OutBackManager
{
	boolean addOutBack(User user, OutBackBean bean) throws MYException;
	
	boolean updateOutBack(User user, OutBackBean bean) throws MYException;
	
	boolean deleteOutBack(User user, String id) throws MYException;
	
	boolean unclaimOutBack(User user, String id) throws MYException;
	
	boolean claimOutBack(User user, OutBackBean bean) throws MYException;
	
	boolean checkOutBack(User user, String id, String reason) throws MYException;
	
	boolean finishOutBack(User user, String id) throws MYException;
}
