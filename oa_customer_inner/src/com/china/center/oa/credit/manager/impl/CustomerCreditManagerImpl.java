/**
 * File Name: CustomerCreditManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.manager.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.client.bean.CustomerApproveBean;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.dao.CustomerApproveDAO;
import com.china.center.oa.client.dao.CustomerMainDAO;
import com.china.center.oa.credit.bean.CreditLevelBean;
import com.china.center.oa.credit.bean.CreditlogBean;
import com.china.center.oa.credit.dao.CreditItemThrDAO;
import com.china.center.oa.credit.dao.CreditLevelDAO;
import com.china.center.oa.credit.dao.CreditlogDAO;
import com.china.center.oa.credit.dao.CustomerCreditApplyDAO;
import com.china.center.oa.credit.dao.CustomerCreditDAO;
import com.china.center.oa.credit.manager.CustomerCreditManager;
import com.china.center.oa.credit.vs.CustomerCreditApplyBean;
import com.china.center.oa.credit.vs.CustomerCreditBean;
import com.china.center.oa.customer.constant.CreditConstant;
import com.china.center.oa.customer.constant.CustomerConstant;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.TimeTools;


/**
 * CustomerCreditManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CustomerCreditManagerImpl
 * @since 1.0
 */
@Exceptional
public class CustomerCreditManagerImpl implements CustomerCreditManager
{
	private final Log _logger = LogFactory.getLog(getClass());
	
    private CustomerCreditDAO customerCreditDAO = null;

    private CustomerCreditApplyDAO customerCreditApplyDAO = null;

    private CreditLevelDAO creditLevelDAO = null;

    private CustomerMainDAO customerMainDAO = null;

    private CreditlogDAO creditlogDAO = null;

    private CreditItemThrDAO creditItemThrDAO = null;

    private CustomerApproveDAO customerApproveDAO = null;

    /**
     * default constructor
     */
    public CustomerCreditManagerImpl()
    {
    }

    /**
     * 设置客户等级
     * 
     * @param user
     * @param cid
     * @param creditList
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean configSpecialCredit(User user, String ccode, List<CustomerCreditBean> creditList)
        throws MYException
    {
        return false;
    }

    /**
     * configCustomerCredit
     * 
     * @param user
     * @param data
     * @return boolean
     * @throws MYException
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean configCustomerCredit(User user, String cid, List<CustomerCreditBean> creditList)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, creditList);

        for (CustomerCreditBean customerCreditBean : creditList)
        {
            customerCreditBean.setCid(cid);

            CustomerCreditBean old = customerCreditDAO.findByUnique(customerCreditBean.getCid(),
                customerCreditBean.getItemId());

            customerCreditBean.setLogTime(TimeTools.now());

            if (old == null)
            {
                customerCreditDAO.saveEntityBean(customerCreditBean);
            }
            else
            {
                customerCreditBean.setId(old.getId());

                // update
                customerCreditDAO.updateEntityBean(customerCreditBean);
            }

            // dynamic add log
            saveLog(user, customerCreditBean);
        }

        updateLevel(cid);

        return true;
    }

    /**
     * updateCustomerCredit
     * 
     * @param cid
     * @throws MYException
     */
    @Transactional(rollbackFor = MYException.class)
    public void updateCustomerCredit(String cid)
        throws MYException
    {
        updateLevel(cid);
    }

    /**
     * updateLevel
     * 
     * @param cid
     */
    private void updateLevel(String cid)
    {
        // sum value
        int sum = (int)Math.ceil(customerCreditDAO.sumValByFK(cid.contains("---")?cid.split("---")[0]:cid));

        // 不能是负数
        if (sum < 0)
        {
            sum = 0;
        }

        // 不能超过100
        if (sum >= 100)
        {
            sum = 100;
        }

        CreditLevelBean findByVal = creditLevelDAO.findByVal(sum);

        String level = CustomerConstant.CREDITLEVELID_DEFAULT;

        if (findByVal != null)
        {
            level = findByVal.getId();
        }
        
        // update customer
        
        if(cid.contains("---") && cid.split("---").length > 1)
        {
//        	double val = Double.parseDouble(cid.split("---")[1]);
        	customerMainDAO.updateCustomerCredit(cid.split("---")[0], level, (int)customerMainDAO.find(cid.split("---")[0]).getCreditVal());
        }
        else
        customerMainDAO.updateCustomerCredit(cid, level, sum);
    }

    @Transactional(rollbackFor = MYException.class)
    public void updateCustomerCredit2(String cid)
        throws MYException
    {
        updateLevel2(cid);
    }
    
    private void updateLevel2(String cid)
    {
    	int sum0 = 0;
    	
        // sum value
        int sum = (int)Math.ceil(customerCreditDAO.sumValByFK(cid.contains("---")?cid.split("---")[0]:cid));

        // 不能是负数
        if (sum < 0)
        {
        	sum0 = sum;
        	
            sum = 0;
        }

        // 不能超过100
        if (sum >= 100)
        {
            sum = 100;
        }

        CreditLevelBean findByVal = creditLevelDAO.findByVal(sum);

        String level = CustomerConstant.CREDITLEVELID_DEFAULT;

        if (findByVal != null)
        {
            level = findByVal.getId();
        }
        
        boolean outblack = updateLevelForOutBlack(cid, sum0, level);
        
        if (outblack)
        	return;
        
        // update customer
        
        if(cid.contains("---") && cid.split("---").length > 1)
        {
//        	double val = Double.parseDouble(cid.split("---")[1]);
        	customerMainDAO.updateCustomerCredit(cid.split("---")[0], level, (int)customerMainDAO.find(cid.split("---")[0]).getCreditVal());
        }
        else
        customerMainDAO.updateCustomerCredit(cid, level, sum);
    }
    
    /**
     * 客户如果是由黑名单转为非黑名单时，客户等级与信用分按 静态属性 计算的积分进行等级的计算<br>
     * @param cid
     * @param sum0 如果当前是负的分数， 要扣掉，是正数，要比较 扣掉之后数
     * @param level 新等级
     * @return
     */
	private boolean updateLevelForOutBlack(String cid, int sum0, String level)
	{
		CustomerBean cbean = null;
        
        if (cid.contains("---"))
        {
        	cbean = customerMainDAO.find(cid.split("---")[0]);
        }
        else
        {
        	cbean = customerMainDAO.find(cid);
        }
        
        if (cbean == null)
        	return true;
        
        // 黑名单客户 将要出黑
        if (cbean.getCreditLevelId().equals("90000000000000000000")
        		&& !level.equals("90000000000000000000"))
        {
        	// 出黑时 信用分 静态属性
        	int sum2 = (int)Math.ceil(customerCreditDAO.sumValByFK(cid.contains("---")?cid.split("---")[0]:cid, 0));
        	
        	sum2 = sum2 - Math.abs(sum0);
        	
        	// 不能是负数
            if (sum2 < 0)
            {
            	if (sum0 < 0)
            		sum2 = 0;
            	else
            		sum2 = sum0;
            }

            // 不能超过100
            if (sum2 >= 100)
            {
            	sum2 = 100;
            }

            CreditLevelBean findByVal1 = creditLevelDAO.findByVal(sum2);

            String level2 = CustomerConstant.CREDITLEVELID_DEFAULT;

            if (findByVal1 != null)
            {
                level2 = findByVal1.getId();
            }
            
            // 自动补的信用分，以便达到静态配置的信用等级
            if (sum2 > 0)
            {
                CustomerCreditBean drectBean = customerCreditDAO.findByUnique(cid, CreditConstant.SET_DRECT);
            	
            	CustomerCreditBean bean = new CustomerCreditBean();
                
                bean.setCid(cid);

                bean.setLogTime(TimeTools.now());

                bean.setItemId(CreditConstant.SET_DRECT);

                bean.setValueId("0");

                bean.setVal(sum2);
                
                bean.setLog("客户出黑名单时系统自动人工干预");

                bean.setPitemId(CreditConstant.SET_DRECT_PARENT);

                bean.setPtype(CreditConstant.CREDIT_TYPE_DYNAMIC);
                
                if (drectBean == null)
                {
                    customerCreditDAO.saveEntityBean(bean);
                }
                else
                {
                	bean.setId(drectBean.getId());

                	bean.setVal(bean.getVal() + drectBean.getVal());
                	
                    customerCreditDAO.updateEntityBean(bean);
                }
            }
            
            // 更新到客户时要求之和
            if (sum0 > 0)
            {
            	sum2 += sum0;
            }
            
            if(cid.contains("---") && cid.split("---").length > 1)
            {
//            	double val = Double.parseDouble(cid.split("---")[1]);
            	customerMainDAO.updateCustomerCredit(cid.split("---")[0], level, (int)customerMainDAO.find(cid.split("---")[0]).getCreditVal());
            }
            else
            customerMainDAO.updateCustomerCredit(cid, level2, sum2);
            
            return true;
        }
        
        return false;
	}

    /**
     * interposeCredit
     * 
     * @param user
     * @param cid
     * @param creditList
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean interposeCredit(User user, String cid, double newCreditVal)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, cid);
        // less than 100
        newCreditVal = Math.min(100.0d, newCreditVal);

        CustomerCreditBean customerCreditBean = new CustomerCreditBean();
        String tempid = cid;
        cid = cid.split("---")[0];
        // sum value
        double sum = Math.ceil(customerCreditDAO.sumValExceptPersonByFK(cid));
        customerCreditBean.setCid(cid);
        if(tempid.contains("---"))
        {
        	customerCreditBean.setVal(newCreditVal);
        }
//        customerCreditBean.setVal(newCreditVal + sum);
        else
        {
        	customerCreditBean.setVal(newCreditVal - sum);	
        }
        customerCreditBean.setLogTime(TimeTools.now());

        customerCreditBean.setPtype(CreditConstant.CREDIT_TYPE_DYNAMIC);

        customerCreditBean.setItemId(CreditConstant.SET_DRECT);
        if(tempid.contains("---"))
        	customerCreditBean.setLog(user.getStafferName() + "增加客户信用分,直接加分:"
                    + MathTools.formatNum(newCreditVal)+",销售单号："+tempid.split("---")[1]);	
        else
        	customerCreditBean.setLog(user.getStafferName() + "人为干预等级,直接加分到:"
                                  + MathTools.formatNum(newCreditVal));

        customerCreditBean.setPitemId(CreditConstant.SET_DRECT_PARENT);

        customerCreditBean.setValueId("0");
        if(tempid.contains("---"))
        cid = cid + "---" + newCreditVal;
        return interposeCredit(user, cid, customerCreditBean);
    }

    /**
     * interposeCredit
     * 
     * @param user
     * @param cid
     * @param customerCreditBean
     * @return boolean
     * @throws MYException
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean interposeCredit(User user, String cid, CustomerCreditBean customerCreditBean)
        throws MYException
    {
        return interposeCreditInner(user, cid, customerCreditBean);
    }

    /**
     * interposeCreditInner(None Transaction)(要求入库的信用值和不会小于0,也不会大于100)
     * 
     * @param user
     * @param cid
     * @param customerCreditBean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean interposeCreditInner(User user, String cid, CustomerCreditBean customerCreditBean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, cid, customerCreditBean);
        CustomerCreditBean drectBean = customerCreditDAO.findByUnique(cid.contains("---")?cid.split("---")[0]:cid, customerCreditBean
            .getItemId());

        // 当前不包括此单(99)
        double sumVal = customerCreditDAO.sumValExceptionByFK(cid.contains("---")?cid.split("---")[0]:cid, customerCreditBean.getItemId());
        // 预计合计(2)
        double total = sumVal + customerCreditBean.getVal();
        // 越界了(负数了,变为0)
        if (total < 0.0)
        {
            // 防止越界采用-sumVal和sumVal抵消
            customerCreditBean.setVal( -sumVal);
        }

        if (total > 100.0)
        {
            double minus = 100.0d - sumVal;

            // 防止越界
            customerCreditBean.setVal(minus);
        }
        if (drectBean == null)
        {
            customerCreditDAO.saveEntityBean(customerCreditBean);
        }
        else
        {
            customerCreditBean.setId(drectBean.getId());

            customerCreditDAO.updateEntityBean(customerCreditBean);
        }

        // dynamic add log
        saveLog(user, customerCreditBean);

        updateLevel(cid);

        return true;
    }

    /**
     * 事务由调用方保证
     * 
     * {@inheritDoc}
     */
    public boolean interposeCreditInnerWithoutUpdateLevel(User user, String cid, CustomerCreditBean customerCreditBean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, cid, customerCreditBean);
        CustomerCreditBean drectBean = customerCreditDAO.findByUnique(cid.contains("---")?cid.split("---")[0]:cid, customerCreditBean
            .getItemId());

        // 当前不包括此单(99)
        double sumVal = customerCreditDAO.sumValExceptionByFK(cid.contains("---")?cid.split("---")[0]:cid, customerCreditBean.getItemId());
        // 预计合计(2)
        double total = sumVal + customerCreditBean.getVal();
        // 越界了(负数了,变为0)
        if (total < 0.0)
        {
            // 防止越界采用-sumVal和sumVal抵消
            customerCreditBean.setVal( -sumVal);
        }

        if (total > 100.0)
        {
            double minus = 100.0d - sumVal;

            // 防止越界
            customerCreditBean.setVal(minus);
        }
        if (drectBean == null)
        {
            customerCreditDAO.saveEntityBean(customerCreditBean);
        }
        else
        {
            customerCreditBean.setId(drectBean.getId());

            customerCreditDAO.updateEntityBean(customerCreditBean);
        }

        // dynamic add log
        saveLog(user, customerCreditBean);

        //updateLevel(cid);

        return true;
    }
    
    /**
     * saveLog
     * 
     * @param user
     * @param customerCreditBean
     */
    private void saveLog(User user, CustomerCreditBean customerCreditBean)
    {
        if (customerCreditBean.getPtype() == CreditConstant.CREDIT_TYPE_DYNAMIC)
        {
            CreditlogBean log = new CreditlogBean();

            log.setStafferId(user.getStafferId());
            log.setLocationId(user.getLocationId());
            log.setLog(customerCreditBean.getLog());
            log.setCid(customerCreditBean.getCid());
            log.setLogTime(TimeTools.now());
            log.setVal(customerCreditBean.getVal());

            creditlogDAO.saveEntityBean(log);
        }
    }

    /**
     * applyConfigStaticCustomerCredit
     * 
     * @param user
     * @param cid
     * @param creditList
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean applyConfigStaticCustomerCredit(User user, String cid,
                                                   List<CustomerCreditApplyBean> creditList)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, creditList);

        CustomerBean bean = checkConfig(cid);

        for (CustomerCreditApplyBean customerCreditBean : creditList)
        {
            customerCreditBean.setCid(cid);

            customerCreditBean.setLogTime(TimeTools.now());
        }

        customerCreditApplyDAO.saveAllEntityBeans(creditList);

        saveApply(user, bean);
        
        return true;
    }

    /**
     * doPassApplyConfigStaticCustomerCredit
     * 
     * @param user
     * @param cid
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean doPassApplyConfigStaticCustomerCredit(User user, String cid)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, cid);

        // 更新信用次数
        CustomerBean old = customerMainDAO.find(cid);

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        List<CustomerCreditApplyBean> creditApplyList = customerCreditApplyDAO
            .queryEntityBeansByFK(cid);

        List<CustomerCreditBean> creditList = new ArrayList();

        for (CustomerCreditApplyBean customerCreditApplyBean : creditApplyList)
        {
            CustomerCreditBean bean = new CustomerCreditBean();

            BeanUtil.copyProperties(bean, customerCreditApplyBean);

            creditList.add(bean);
        }

        configCustomerCredit(user, cid, creditList);

        customerCreditApplyDAO.deleteEntityBeansByFK(cid);

        customerApproveDAO.deleteEntityBean(cid);

        old.setCreditUpdateTime(old.getCreditUpdateTime() + 1);

        customerMainDAO.updateCustomerCreditUpdateTime(cid, old.getCreditUpdateTime());

        return true;
    }

    /**
     * doRejectApplyConfigStaticCustomerCredit
     * 
     * @param user
     * @param cid
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean doRejectApplyConfigStaticCustomerCredit(User user, String cid)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, cid);

        customerCreditApplyDAO.deleteEntityBeansByFK(cid);

        customerApproveDAO.deleteEntityBean(cid);

        return true;
    }

    /**
     * saveApply
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    private void saveApply(User user, CustomerBean bean)
        throws MYException
    {
        CustomerApproveBean old = customerApproveDAO.find(bean.getId());

        if (old != null)
        {
            throw new MYException("客户修改申请已经存在(或者驳回了职员未处理),请先处理此申请");
        }

        CustomerApproveBean apply = new CustomerApproveBean();

        BeanUtil.copyProperties(apply, bean);

        apply.setStatus(CustomerConstant.STATUS_APPLY);

        apply.setOpr(CustomerConstant.OPR_UPATE_CREDIT);

        apply.setLogTime(TimeTools.now());

        apply.setApplyId(user.getStafferId());

        customerApproveDAO.saveEntityBean(apply);
    }

    /**
     * checkConfig
     * 
     * @param cid
     * @return
     * @throws MYException
     */
    private CustomerBean checkConfig(String cid)
        throws MYException
    {
        if (customerCreditApplyDAO.hasUpdate(cid))
        {
            throw new MYException("此客户的静态属性已经在申请中");
        }

        CustomerBean bean = customerMainDAO.find(cid);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }
        return bean;
    }

    /**
     * @return the customerCreditDAO
     */
    public CustomerCreditDAO getCustomerCreditDAO()
    {
        return customerCreditDAO;
    }

    /**
     * @param customerCreditDAO
     *            the customerCreditDAO to set
     */
    public void setCustomerCreditDAO(CustomerCreditDAO customerCreditDAO)
    {
        this.customerCreditDAO = customerCreditDAO;
    }

    /**
     * @return the creditLevelDAO
     */
    public CreditLevelDAO getCreditLevelDAO()
    {
        return creditLevelDAO;
    }

    /**
     * @param creditLevelDAO
     *            the creditLevelDAO to set
     */
    public void setCreditLevelDAO(CreditLevelDAO creditLevelDAO)
    {
        this.creditLevelDAO = creditLevelDAO;
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

	/**
     * @return the creditItemThrDAO
     */
    public CreditItemThrDAO getCreditItemThrDAO()
    {
        return creditItemThrDAO;
    }

    /**
     * @param creditItemThrDAO
     *            the creditItemThrDAO to set
     */
    public void setCreditItemThrDAO(CreditItemThrDAO creditItemThrDAO)
    {
        this.creditItemThrDAO = creditItemThrDAO;
    }

    /**
     * @return the customerCreditApplyDAO
     */
    public CustomerCreditApplyDAO getCustomerCreditApplyDAO()
    {
        return customerCreditApplyDAO;
    }

    /**
     * @param customerCreditApplyDAO
     *            the customerCreditApplyDAO to set
     */
    public void setCustomerCreditApplyDAO(CustomerCreditApplyDAO customerCreditApplyDAO)
    {
        this.customerCreditApplyDAO = customerCreditApplyDAO;
    }

    /**
	 * @return the customerApproveDAO
	 */
	public CustomerApproveDAO getCustomerApproveDAO()
	{
		return customerApproveDAO;
	}

	/**
	 * @param customerApproveDAO the customerApproveDAO to set
	 */
	public void setCustomerApproveDAO(CustomerApproveDAO customerApproveDAO)
	{
		this.customerApproveDAO = customerApproveDAO;
	}

	/**
     * @return the creditlogDAO
     */
    public CreditlogDAO getCreditlogDAO()
    {
        return creditlogDAO;
    }

    /**
     * @param creditlogDAO
     *            the creditlogDAO to set
     */
    public void setCreditlogDAO(CreditlogDAO creditlogDAO)
    {
        this.creditlogDAO = creditlogDAO;
    }
}
