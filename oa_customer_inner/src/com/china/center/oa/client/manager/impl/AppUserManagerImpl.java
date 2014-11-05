package com.china.center.oa.client.manager.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.oa.client.bean.AppUserApplyBean;
import com.china.center.oa.client.bean.AppUserBean;
import com.china.center.oa.client.bean.AppUserVSCustomerBean;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.dao.AppUserApplyDAO;
import com.china.center.oa.client.dao.AppUserDAO;
import com.china.center.oa.client.dao.AppUserVSCustomerDAO;
import com.china.center.oa.client.dao.CustomerMainDAO;
import com.china.center.oa.client.manager.AppUserManager;
import com.china.center.oa.client.vo.AppUserVO;
import com.china.center.oa.customer.constant.CustomerConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

public class AppUserManagerImpl implements AppUserManager
{
	private final Log _logger = LogFactory.getLog(this.getClass());
	
	private AppUserDAO appUserDAO = null;
	
	private AppUserApplyDAO appUserApplyDAO = null;
	
	private CommonDAO commonDAO = null;
	
	private CustomerMainDAO customerMainDAO = null;
	
	private AppUserVSCustomerDAO appUserVSCustomerDAO = null;
	
	/**
	 * 
	 */
	public AppUserManagerImpl()
	{
	}

	@Transactional(rollbackFor = MYException.class)
	public AppUserVO createOrModifyUser(AppUserBean bean) throws MYException
	{
		JudgeTools.judgeParameterIsNull(bean);
		
		checkAppUser(bean);

		if (bean.getOprType() == CustomerConstant.OPRTYPE_NEW)
		{
			
			bean.setId(commonDAO.getSquenceString20());
			// 审核中
			bean.setStatus(1);
			
			bean.setLogTime(TimeTools.now());

			appUserDAO.saveEntityBean(bean);
			
			// apply
			AppUserApplyBean applyBean = new AppUserApplyBean();
			
			BeanUtil.copyProperties(applyBean, bean);
			
			applyBean.setApplyId("227");
			applyBean.setStatus(CustomerConstant.USER_APPLY_APPROVE);
			
			appUserApplyDAO.saveEntityBean(applyBean);
		}
		else
		{
			AppUserBean old = appUserDAO.find(bean.getId());
			
			if (null == old)
			{
				throw new MYException("修改资料时，原用户数据不存在。");
			}
			
			_logger.info("" + old);
			
			appUserDAO.updateEntityBean(bean);
			
			_logger.info("" + bean);
			
			// apply
			appUserApplyDAO.deleteEntityBean(old.getId());
			
			AppUserApplyBean applyBean = new AppUserApplyBean();
			
			BeanUtil.copyProperties(applyBean, bean);
			
			applyBean.setApplyId("227");
			applyBean.setStatus(CustomerConstant.USER_APPLY_APPROVE);
			
			appUserApplyDAO.saveEntityBean(applyBean);
		}

		AppUserVO vo = new AppUserVO();
		
		BeanUtil.copyProperties(vo, bean);
		
		vo.setStatusVal(DefinedCommon.getValue("userStatus", vo.getStatus()));
		vo.setCreditMoney(0);
		vo.setPreMoney(0);
		vo.setReceiveMoney(0);

		return vo;
	}

	private void checkAppUser(AppUserBean appUser) throws MYException
	{
		if (appUser.getOprType() != CustomerConstant.OPRTYPE_NEW && appUser.getOprType() != CustomerConstant.OPRTYPE_UPDATE)
    	{
    		throw new MYException("操作类型须指定是创建还是修改标识");
    	}
    	
		if (StringTools.isNullOrNone(appUser.getLoginName()))
		{
			throw new MYException("用户名不能为空");
		}
		
		if (appUser.getOprType() == CustomerConstant.OPRTYPE_NEW)
		{
			int count = appUserDAO.countByUnique(appUser.getLoginName());
			
			if (count > 0)
			{
				throw new MYException("用户名已存在");
			}
		}
		
		if (StringTools.isNullOrNone(appUser.getPassword()))
		{
			throw new MYException("密码不可为空");
		}
		
    	// 修改须提供用户ID
    	if (appUser.getOprType() == CustomerConstant.OPRTYPE_UPDATE)
    	{
    		if (StringTools.isNullOrNone(appUser.getId()))
    		{
    			throw new MYException("修改类型时，须提供用户ID");
    		}
    	}
	}
	
	@Transactional(rollbackFor = MYException.class)
	public boolean modifyPassword(String userId, String orignalPassword,
			String newPassword) throws MYException
	{
		JudgeTools.judgeParameterIsNull(userId, orignalPassword, newPassword);
		
		AppUserBean bean = appUserDAO.find(userId);
		
		if (null == bean)
		{
			throw new MYException("用户不存在");
		}
		
		// 有效性
		if (bean.getStatus() != CustomerConstant.USER_STATUS_AVAILABLE)
		{
			throw new MYException("不是有效的用户");
		}
		
		if (!bean.getPassword().equals(orignalPassword))
		{
			throw new MYException("原密码不对");
		}
		
		bean.setPassword(newPassword);
		
		appUserDAO.updateEntityBean(bean);
		
		return true;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = MYException.class)
	public boolean passAppUserApply(User user, AppUserVSCustomerBean appUserVSCust) throws MYException
	{
		JudgeTools.judgeParameterIsNull(user,appUserVSCust);
		
		AppUserApplyBean userApplyBean = appUserApplyDAO.find(appUserVSCust.getAppUserId());
		
		if (null == userApplyBean)
		{
			throw new MYException("数据错误");
		}
		
		addInnerAppVSCust(appUserVSCust);
		
		// 4.修改申请状态及APP用户状态
		userApplyBean.setStatus(CustomerConstant.USER_APPLY_PASS);
		userApplyBean.setApproveId(user.getStafferName());
		
		appUserApplyDAO.updateEntityBean(userApplyBean);
		
		return true;
	}

	private void addInnerAppVSCust(AppUserVSCustomerBean appUserVSCust) throws MYException
	{
		AppUserBean  appUser = appUserDAO.find(appUserVSCust.getAppUserId());
		
		if (null == appUser)
		{
			throw new MYException("数据错误");
		}
		
		// 1.检查客户是否为在用状态
		String customerId = appUserVSCust.getCustomerId();
		
		CustomerBean cust = customerMainDAO.find(customerId);
		
		if (null == cust)
		{
			throw new MYException("数据错误");
		}
		
		if (cust.getStatus() != CustomerConstant.STATUS_OK)
		{
			throw new MYException("客户不是正常状态");
		}
		
		// 2.检查客户是否已被绑定到其它的用户上
		int count = appUserVSCustomerDAO.queryEntityBeansByFK(customerId).size();
		
		if (count > 0)
		{
			throw new MYException("客户已被其它用户绑定");
		}
		
		// 3.绑定用户
		appUser.setStatus(CustomerConstant.USER_STATUS_AVAILABLE);
		
		appUserDAO.updateEntityBean(appUser);
		
		AppUserVSCustomerBean vsCust = appUserVSCustomerDAO.findByUnique(appUserVSCust.getAppUserId());
		
		if (null == vsCust)
		{
			appUserVSCustomerDAO.saveEntityBean(appUserVSCust);
		}else{
			appUserVSCustomerDAO.updateEntityBean(appUserVSCust);
		}
		
		return;
	}

	/***
	 * 
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = MYException.class)
	public boolean rejectAppUserApply(User user, String appUserId, String reason) throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, appUserId);
		
		// 1.直接更新申请状态为驳回态
		AppUserApplyBean applyBean = appUserApplyDAO.find(appUserId);
		
		if (null != applyBean)
		{
			applyBean.setStatus(CustomerConstant.USER_APPLY_REJECT);
			
			applyBean.setDescription(applyBean.getDescription() + " 驳回原因:" + reason);
			
			appUserApplyDAO.updateEntityBean(applyBean);
		}
		
		AppUserBean bean = appUserDAO.find(appUserId);
		
		if (null != bean)
		{
			bean.setStatus(CustomerConstant.USER_STATUS_REJECT);
			
			appUserDAO.updateEntityBean(bean);
		}
		
		return true;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = MYException.class)
	public boolean addAppUserCust(User user, AppUserVSCustomerBean appUserVSCust)
			throws MYException
	{
		addInnerAppVSCust(appUserVSCust);
		
		return true;
	}
	
	public AppUserDAO getAppUserDAO()
	{
		return appUserDAO;
	}

	public void setAppUserDAO(AppUserDAO appUserDAO)
	{
		this.appUserDAO = appUserDAO;
	}

	public AppUserApplyDAO getAppUserApplyDAO()
	{
		return appUserApplyDAO;
	}

	public void setAppUserApplyDAO(AppUserApplyDAO appUserApplyDAO)
	{
		this.appUserApplyDAO = appUserApplyDAO;
	}

	public CommonDAO getCommonDAO()
	{
		return commonDAO;
	}

	public void setCommonDAO(CommonDAO commonDAO)
	{
		this.commonDAO = commonDAO;
	}

	/**
	 * @return the appUserVSCustomerDAO
	 */
	public AppUserVSCustomerDAO getAppUserVSCustomerDAO()
	{
		return appUserVSCustomerDAO;
	}

	/**
	 * @param appUserVSCustomerDAO the appUserVSCustomerDAO to set
	 */
	public void setAppUserVSCustomerDAO(AppUserVSCustomerDAO appUserVSCustomerDAO)
	{
		this.appUserVSCustomerDAO = appUserVSCustomerDAO;
	}

	/**
	 * @return the customerMainDAO
	 */
	public CustomerMainDAO getCustomerMainDAO()
	{
		return customerMainDAO;
	}

	/**
	 * @param customerMainDAO the customerMainDAO to set
	 */
	public void setCustomerMainDAO(CustomerMainDAO customerMainDAO)
	{
		this.customerMainDAO = customerMainDAO;
	}
}
