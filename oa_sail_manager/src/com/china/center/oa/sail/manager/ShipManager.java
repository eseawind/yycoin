package com.china.center.oa.sail.manager;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.sail.bean.PreConsignBean;
import com.china.center.oa.sail.vo.OutVO;

public interface ShipManager
{
//	void createPackage();
	
	boolean addPickup(User user, String packageIds) throws MYException;
	
	boolean deletePackage(User user, String packageIds) throws MYException;
	
	boolean updateStatus(User user, String pickupId) throws MYException;
	
	boolean updatePrintStatus(String pickupId, int index_pos) throws MYException;
	
	void createPackage(PreConsignBean pre, OutVO out) throws MYException;
//	
//	void createInsPackage(PreConsignBean pre, String insId) throws MYException;
}
