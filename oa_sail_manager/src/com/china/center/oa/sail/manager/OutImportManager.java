package com.china.center.oa.sail.manager;

import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.sail.bean.BankSailBean;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.BatchApproveBean;
import com.china.center.oa.sail.bean.BatchSwatchBean;
import com.china.center.oa.sail.bean.ConsignBean;
import com.china.center.oa.sail.bean.DistributionBean;
import com.china.center.oa.sail.bean.EstimateProfitBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.bean.OutImportBean;
import com.china.center.oa.sail.bean.ReplenishmentBean;

public interface OutImportManager
{

	String addBean(List<OutImportBean> list)
		throws MYException;
	
	String addPufaBean(List<OutImportBean> list)
	throws MYException;
	
	boolean process(List<OutImportBean> list)
		throws MYException;
	
	boolean processAsyn(List<OutImportBean> list);
	
	List<ReplenishmentBean> queryReplenishmentBean() throws MYException;
	
	String batchApproveImport(List<BatchApproveBean> list, int type)
		throws MYException;
	
	boolean batchApprove(User user,String batchId)
		throws MYException;
	
	String batchSwatchImport(List<BatchSwatchBean> list)
	throws MYException;

	boolean batchSwatch(User user,String batchId)
	throws MYException;
	
	boolean batchUpdateConsign(List<ConsignBean> list)
	throws MYException;
	
	boolean batchUpdateDistAddr(List<DistributionBean> list)
	throws MYException;
	
	boolean batchUpdateEmergency(List<ConsignBean> list)
			throws MYException;
	
	List<OutImportBean> preUseAmountCheck(String batchId);
	
	void processSplitOut(String batchId);
	
	boolean batchUpdateRedate(List<OutBean> list)
	throws MYException;
	
	String addBankSail(User user, List<BankSailBean> list)
	throws MYException;
	
	boolean deleteBankSail(User user,String batchId)
	throws MYException;
	
	boolean addEstimateProfit(User user, List<EstimateProfitBean> list)
	throws MYException;
	
	boolean deleteEstimateProfit(User user,String batchId)
	throws MYException;
	
	boolean batchUpdateDepot(List<BaseBean> list)
			throws MYException;
	
}
