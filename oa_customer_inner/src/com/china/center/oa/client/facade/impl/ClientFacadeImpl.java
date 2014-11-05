package com.china.center.oa.client.facade.impl;

import java.io.Serializable;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.client.bean.AssignApplyBean;
import com.china.center.oa.client.bean.CustomerApproveBean;
import com.china.center.oa.client.bean.CustomerCorporationApplyBean;
import com.china.center.oa.client.bean.CustomerDepartApplyBean;
import com.china.center.oa.client.bean.CustomerIndividualApplyBean;
import com.china.center.oa.client.facade.ClientFacade;
import com.china.center.oa.client.manager.ClientManager;
import com.china.center.oa.credit.bean.CreditItemBean;
import com.china.center.oa.credit.bean.CreditItemSecBean;
import com.china.center.oa.credit.bean.CreditItemThrBean;
import com.china.center.oa.credit.bean.CreditLevelBean;
import com.china.center.oa.credit.manager.CreditItemManager;
import com.china.center.oa.credit.manager.CustomerCreditManager;
import com.china.center.oa.customer.bean.CustomerCheckBean;
import com.china.center.oa.customer.bean.CustomerCheckItemBean;
import com.china.center.oa.customer.manager.CustomerCheckManager;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.facade.AbstarctFacade;
import com.china.center.tools.JudgeTools;

public class ClientFacadeImpl extends AbstarctFacade implements ClientFacade
{
	private ClientManager clientManager = null;
	
	private CustomerCreditManager customerCreditManager = null;
	
    private CustomerCheckManager customerCheckManager = null;

    private CreditItemManager creditItemManager = null;
	
	/**
	 * applyAddClient 个人客户
	 * {@inheritDoc}
	 */
	public boolean applyAddOrUpdateClient(String userId, CustomerIndividualApplyBean bean, String addOrUpdate)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(userId, bean);
        
        User user = userManager.findUser(userId);
        
        checkUser(user);
        
        if (containAuth(user, AuthConstant.CUSTOMER_OPR))
        {
            return clientManager.applyAddOrUpdateClient(user, bean, addOrUpdate);
        }
        else
        {
            throw noAuth();
        }
    }

	/**
	 * applyAddClient 部门客户
	 * {@inheritDoc}
	 */
	public boolean applyAddOrUpdateClient(String userId, CustomerDepartApplyBean bean, String addOrUpdate)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);
        
        checkUser(user);
        
        if (containAuth(user, AuthConstant.CUSTOMER_OPR))
        {
        	return clientManager.applyAddOrUpdateClient(user, bean, addOrUpdate);
        }
        else
        {
            throw noAuth();
        }
    }

	/**
	 * 企业客户
	 * {@inheritDoc}
	 */
	public boolean applyAddOrUpdateClient(String userId, CustomerCorporationApplyBean bean, String addOrUpdate)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_OPR))
        {
        	return clientManager.applyAddOrUpdateClient(user, bean, addOrUpdate);
        }
        else
        {
            throw noAuth();
        }
    }

	public boolean passApplyClient(String userId, String cid)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(userId, cid);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_INFO_CHECK))
        {
            return clientManager.passApplyClient(user, cid);
        }
        else
        {
            throw noAuth();
        }
    }

	public boolean rejectApplyClient(String userId, String cid, String reson)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(userId, cid);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_INFO_CHECK))
        {
            return clientManager.rejectApplyClient(user, cid, reson);
        }
        else
        {
            throw noAuth();
        }
    }

	public boolean delApplyClient(String userId, String cid)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(userId, cid);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_OPR))
        {
            return clientManager.delApply(user, cid);
        }
        else
        {
            throw noAuth();
        }
    }
	
	public boolean applyDelClient(String userId, CustomerApproveBean bean)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_OPR))
        {
            return clientManager.applyDelClient(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }
	
    /**
     * addAssignApply
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addAssignApply(String userId, AssignApplyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_APPLY_ASSIGN))
        {
            return clientManager.addAssignApply(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }
	
    /**
     * passAssignApply(申请客户和职员的对于关系)
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean passAssignApply(String userId, String cid)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, cid);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_CHECK))
        {
            return clientManager.passAssignApply(user, cid);
        }
        else
        {
            throw noAuth();
        }
    }
    
    /**
     * passAssignApply
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean rejectAssignApply(String userId, String cid)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, cid);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_CHECK))
        {
            return clientManager.rejectAssignApply(user, cid);
        }
        else
        {
            throw noAuth();
        }
    }
    
    /**
     * passAssignApply
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean reclaimAssignClient(String userId, String cid, String type, String destStafferId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, cid);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_RECLAIM))
        {
            return clientManager.reclaimAssignClient(user, cid, type, destStafferId);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * 回收职员下的客户
     * 
     * @param userId
     * @param stafferId
     * @param flag
     * @return
     * @throws MYException
     */
    public boolean reclaimStafferAssignClient(String userId, String stafferId, String destStafferId, int flag)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, stafferId);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_RECLAIM))
        {
            return clientManager.reclaimStafferAssignClient(user, stafferId, destStafferId, flag);
        }
        else
        {
            throw noAuth();
        }
    }
    
    /**
     * interposeCredit
     * 
     * @param userId
     * @param cid
     * @param newCreditVal
     * @return
     * @throws MYException
     */
    public boolean interposeCredit(String userId, String cid, double newCreditVal)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, cid);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CREDIT_FORCE_UPDATE))
        {
            return customerCreditManager.interposeCredit(user, cid, newCreditVal);
        }
        else
        {
            throw noAuth();
        }
    }
    
    /**
     * addCreditItemThr
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addCreditItemThr(String userId, CreditItemThrBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CREDIT_OPR))
        {
            return creditItemManager.addCreditItemThr(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * updateCreditItemThr
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateCreditItemThr(String userId, CreditItemThrBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CREDIT_OPR))
        {
            return creditItemManager.updateCreditItemThr(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * updateCreditItem
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateCreditItem(String userId, CreditItemBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CREDIT_OPR))
        {
            return creditItemManager.updateCreditItem(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * updateCreditItemSec
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateCreditItemSec(String userId, CreditItemSecBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CREDIT_OPR))
        {
            return creditItemManager.updateCreditItemSec(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * deleteCreditItemThr
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean deleteCreditItemThr(String userId, Serializable id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CREDIT_OPR))
        {
            return creditItemManager.deleteCreditItemThr(user, id);
        }
        else
        {
            throw noAuth();
        }
    }
    
    /**
     * updateCreditLevel
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateCreditLevel(String userId, CreditLevelBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CREDIT_LEVEL_OPR))
        {
            return creditItemManager.updateCreditLevel(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }
    
    /**
     * doPassApplyConfigStaticCustomerCredit
     * 
     * @param userId
     * @param cid
     * @return
     * @throws MYException
     */
    public boolean doPassApplyConfigStaticCustomerCredit(String userId, String cid)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, cid);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_CREDIT_CHECK))
        {
            return customerCreditManager.doPassApplyConfigStaticCustomerCredit(user, cid);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * doRejectApplyConfigStaticCustomerCredit
     * 
     * @param userId
     * @param cid
     * @return
     * @throws MYException
     */
    public boolean doRejectApplyConfigStaticCustomerCredit(String userId, String cid)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, cid);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_CREDIT_CHECK))
        {
            return customerCreditManager.doRejectApplyConfigStaticCustomerCredit(user, cid);
        }
        else
        {
            throw noAuth();
        }
    }
    
    /**
     * addCheckBean
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addCheckBean(String userId, CustomerCheckBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_CHECK_COMMON))
        {
            return customerCheckManager.addBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * goonBean
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean goonBean(String userId, CustomerCheckBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_CHECK_COMMON))
        {
            return customerCheckManager.goonBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * passCheckBean(客户核对)
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean passCheckBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_CHECK_CHECK))
        {
            return customerCheckManager.passBean(user, id);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * updateCheckItem
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateCheckItem(String userId, CustomerCheckItemBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_CHECK_COMMON))
        {
            return customerCheckManager.updateCheckItem(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * rejectCheckBean
     * 
     * @param userId
     * @param id
     * @return
     * @throws MYException
     */
    public boolean rejectCheckBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_CHECK_CHECK))
        {
            return customerCheckManager.rejectBean(user, id);
        }
        else
        {
            throw noAuth();
        }
    }
    
    /**
     * delCheckBean
     * 
     * @param userId
     * @param id
     * @return
     * @throws MYException
     */
    public boolean delCheckBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_CHECK_COMMON))
        {
            return customerCheckManager.delBean(user, id);
        }
        else
        {
            throw noAuth();
        }
    }
    
    public boolean batchTransCustomer(String userId, int type) throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, type);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_APPLY_ASSIGN))
        {
            return clientManager.batchTransCustomer(user, type);
        }
        else
        {
            throw noAuth();
        }
    }
    
	/**
	 * @return the clientManager
	 */
	public ClientManager getClientManager()
	{
		return clientManager;
	}

	/**
	 * @param clientManager the clientManager to set
	 */
	public void setClientManager(ClientManager clientManager)
	{
		this.clientManager = clientManager;
	}

	/**
	 * @return the customerCreditManager
	 */
	public CustomerCreditManager getCustomerCreditManager()
	{
		return customerCreditManager;
	}

	/**
	 * @param customerCreditManager the customerCreditManager to set
	 */
	public void setCustomerCreditManager(CustomerCreditManager customerCreditManager)
	{
		this.customerCreditManager = customerCreditManager;
	}

	/**
	 * @return the customerCheckManager
	 */
	public CustomerCheckManager getCustomerCheckManager()
	{
		return customerCheckManager;
	}

	/**
	 * @param customerCheckManager the customerCheckManager to set
	 */
	public void setCustomerCheckManager(CustomerCheckManager customerCheckManager)
	{
		this.customerCheckManager = customerCheckManager;
	}

	/**
	 * @return the creditItemManager
	 */
	public CreditItemManager getCreditItemManager()
	{
		return creditItemManager;
	}

	/**
	 * @param creditItemManager the creditItemManager to set
	 */
	public void setCreditItemManager(CreditItemManager creditItemManager)
	{
		this.creditItemManager = creditItemManager;
	}
}
