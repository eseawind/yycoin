package com.china.center.oa.finance.manager;

import com.china.center.common.MYException;
import com.china.center.oa.sail.bean.PreConsignBean;
import com.china.center.oa.sail.vo.OutVO;

public interface PackageManager {
	void createPackage();
	
	void createPackage(PreConsignBean pre, OutVO out) throws MYException;
	
	void createInsPackage(PreConsignBean pre, String insId) throws MYException;
}
