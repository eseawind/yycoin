package com.china.center.oa.client.facade;

import java.io.Serializable;

import com.china.center.common.MYException;
import com.china.center.oa.client.bean.AssignApplyBean;
import com.china.center.oa.client.bean.CustomerApproveBean;
import com.china.center.oa.client.bean.CustomerCorporationApplyBean;
import com.china.center.oa.client.bean.CustomerDepartApplyBean;
import com.china.center.oa.client.bean.CustomerIndividualApplyBean;
import com.china.center.oa.credit.bean.CreditItemBean;
import com.china.center.oa.credit.bean.CreditItemSecBean;
import com.china.center.oa.credit.bean.CreditItemThrBean;
import com.china.center.oa.credit.bean.CreditLevelBean;
import com.china.center.oa.customer.bean.CustomerCheckBean;
import com.china.center.oa.customer.bean.CustomerCheckItemBean;

public interface ClientFacade
{
	boolean applyAddOrUpdateClient(String userId, CustomerIndividualApplyBean bean, String addOrUpdate)
    throws MYException;
	
	boolean applyAddOrUpdateClient(String userId, CustomerDepartApplyBean bean, String addOrUpdate)
    throws MYException;
	
	boolean applyAddOrUpdateClient(String userId, CustomerCorporationApplyBean bea, String addOrUpdaten)
    throws MYException;
	
	boolean delApplyClient(String userId, String cid)
    throws MYException;
	
    boolean passApplyClient(String userId, String cid)
    throws MYException;
    
    boolean rejectApplyClient(String userId, String cid, String reson)
    throws MYException;
    
    boolean applyDelClient(String userId, CustomerApproveBean bean)
    throws MYException;
    
    boolean addAssignApply(String userId, AssignApplyBean bean)
    throws MYException;
    
    boolean passAssignApply(String userId, String cid)
    throws MYException;
    
    boolean rejectAssignApply(String userId, String cid)
    throws MYException;
    
    boolean reclaimAssignClient(String userId, String cid, String type, String destStafferId)
    throws MYException;

    boolean reclaimStafferAssignClient(String userId, String stafferId, String destStafferId, int flag)
    throws MYException;
    
    boolean interposeCredit(String userId, String cid, double newCreditVal)
    throws MYException;
    
    boolean addCreditItemThr(String userId, CreditItemThrBean bean)
    throws MYException;

	boolean updateCreditItemThr(String userId, CreditItemThrBean bean)
	    throws MYException;
	
	boolean updateCreditItem(String userId, CreditItemBean bean)
	    throws MYException;
	
	boolean updateCreditItemSec(String userId, CreditItemSecBean bean)
	    throws MYException;
	
	boolean deleteCreditItemThr(String userId, Serializable id)
	    throws MYException;
	
    boolean updateCreditLevel(String userId, CreditLevelBean bean)
    throws MYException;
    
    boolean doPassApplyConfigStaticCustomerCredit(String userId, String cid)
    throws MYException;

	boolean doRejectApplyConfigStaticCustomerCredit(String userId, String cid)
	    throws MYException;
	
	boolean addCheckBean(String userId, CustomerCheckBean bean)
    throws MYException;

	boolean goonBean(String userId, CustomerCheckBean bean)
	    throws MYException;
	
	boolean passCheckBean(String userId, String id)
	    throws MYException;
	
    boolean delCheckBean(String userId, String id)
    throws MYException;
    
    boolean updateCheckItem(String userId, CustomerCheckItemBean bean)
    throws MYException;
    
    boolean rejectCheckBean(String userId, String id)
    throws MYException;
    
    boolean batchTransCustomer(String userId, int type) throws MYException;
}
