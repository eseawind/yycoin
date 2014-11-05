package com.china.center.oa.client.manager.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.oa.client.bean.AccessLogBean;
import com.china.center.oa.client.bean.AddressBean;
import com.china.center.oa.client.bean.AddressHisBean;
import com.china.center.oa.client.bean.AssignApplyBean;
import com.china.center.oa.client.bean.ChangeLogBean;
import com.china.center.oa.client.bean.CiticBranchBean;
import com.china.center.oa.client.bean.CiticVSStafferBean;
import com.china.center.oa.client.bean.CustomerApproveBean;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.bean.CustomerBusinessApplyBean;
import com.china.center.oa.client.bean.CustomerBusinessBean;
import com.china.center.oa.client.bean.CustomerBusinessHisBean;
import com.china.center.oa.client.bean.CustomerContactApplyBean;
import com.china.center.oa.client.bean.CustomerContactBean;
import com.china.center.oa.client.bean.CustomerContactHisBean;
import com.china.center.oa.client.bean.CustomerCorporationApplyBean;
import com.china.center.oa.client.bean.CustomerCorporationBean;
import com.china.center.oa.client.bean.CustomerCorporationHisBean;
import com.china.center.oa.client.bean.CustomerDepartApplyBean;
import com.china.center.oa.client.bean.CustomerDepartBean;
import com.china.center.oa.client.bean.CustomerDepartHisBean;
import com.china.center.oa.client.bean.CustomerDistAddrApplyBean;
import com.china.center.oa.client.bean.CustomerDistAddrBean;
import com.china.center.oa.client.bean.CustomerDistAddrHisBean;
import com.china.center.oa.client.bean.CustomerFormerNameBean;
import com.china.center.oa.client.bean.CustomerIndividualApplyBean;
import com.china.center.oa.client.bean.CustomerIndividualBean;
import com.china.center.oa.client.bean.CustomerIndividualHisBean;
import com.china.center.oa.client.dao.AccessLogDAO;
import com.china.center.oa.client.dao.AddressDAO;
import com.china.center.oa.client.dao.AddressHisDAO;
import com.china.center.oa.client.dao.AssignApplyDAO;
import com.china.center.oa.client.dao.ChangeLogDAO;
import com.china.center.oa.client.dao.CiticBranchDAO;
import com.china.center.oa.client.dao.CiticVSStafferDAO;
import com.china.center.oa.client.dao.CustomerApproveDAO;
import com.china.center.oa.client.dao.CustomerBusinessApplyDAO;
import com.china.center.oa.client.dao.CustomerBusinessDAO;
import com.china.center.oa.client.dao.CustomerBusinessHisDAO;
import com.china.center.oa.client.dao.CustomerContactApplyDAO;
import com.china.center.oa.client.dao.CustomerContactDAO;
import com.china.center.oa.client.dao.CustomerContactHisDAO;
import com.china.center.oa.client.dao.CustomerCorporationApplyDAO;
import com.china.center.oa.client.dao.CustomerCorporationDAO;
import com.china.center.oa.client.dao.CustomerCorporationHisDAO;
import com.china.center.oa.client.dao.CustomerDepartApplyDAO;
import com.china.center.oa.client.dao.CustomerDepartDAO;
import com.china.center.oa.client.dao.CustomerDepartHisDAO;
import com.china.center.oa.client.dao.CustomerDistAddrApplyDAO;
import com.china.center.oa.client.dao.CustomerDistAddrDAO;
import com.china.center.oa.client.dao.CustomerDistAddrHisDAO;
import com.china.center.oa.client.dao.CustomerFormerNameDAO;
import com.china.center.oa.client.dao.CustomerIndividualApplyDAO;
import com.china.center.oa.client.dao.CustomerIndividualDAO;
import com.china.center.oa.client.dao.CustomerIndividualHisDAO;
import com.china.center.oa.client.dao.CustomerMainDAO;
import com.china.center.oa.client.dao.DestStafferVSCustomerDAO;
import com.china.center.oa.client.dao.StafferVSCustomerDAO;
import com.china.center.oa.client.listener.ClientListener;
import com.china.center.oa.client.manager.ClientManager;
import com.china.center.oa.client.vo.CiticBranchVO;
import com.china.center.oa.client.vo.CustomerCorporationApplyVO;
import com.china.center.oa.client.vo.CustomerCorporationVO;
import com.china.center.oa.client.vo.CustomerDepartApplyVO;
import com.china.center.oa.client.vo.CustomerDepartVO;
import com.china.center.oa.client.vo.CustomerDistAddrApplyVO;
import com.china.center.oa.client.vo.CustomerDistAddrVO;
import com.china.center.oa.client.vo.CustomerIndividualApplyVO;
import com.china.center.oa.client.vo.CustomerIndividualVO;
import com.china.center.oa.client.vs.DestStafferVSCustomerBean;
import com.china.center.oa.client.vs.StafferVSCustomerBean;
import com.china.center.oa.customer.constant.CustomerConstant;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.OperationConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.LocationVSCityDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.vs.LocationVSCityBean;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

public class ClientManagerImpl extends AbstractListenerManager<ClientListener> implements ClientManager
{
	private final Log triggerLog = LogFactory.getLog("trigger");
	
	private LocationVSCityDAO locationVSCityDAO = null;
	
	private CommonDAO commonDAO = null;
	
	private CustomerIndividualDAO customerIndividualDAO = null;
	
	private CustomerDepartDAO customerDepartDAO = null;
	
	private CustomerCorporationDAO customerCorporationDAO = null;
	
	private CustomerContactDAO customerContactDAO = null;
	
	private CustomerBusinessDAO customerBusinessDAO = null;
	
	private CustomerApproveDAO customerApproveDAO = null;
	
	private CustomerMainDAO customerMainDAO = null;
	
	private StafferDAO stafferDAO = null;
	
	private StafferVSCustomerDAO stafferVSCustomerDAO = null;
	
	private ChangeLogDAO changeLogDAO = null;
	
	private AccessLogDAO accessLogDAO = null;
	
	private CustomerIndividualApplyDAO customerIndividualApplyDAO = null;
	
	private CustomerDepartApplyDAO customerDepartApplyDAO = null;
	
	private CustomerCorporationApplyDAO customerCorporationApplyDAO = null;
	
	private CustomerContactApplyDAO customerContactApplyDAO = null;
	
	private CustomerBusinessApplyDAO customerBusinessApplyDAO = null;
	
	private CustomerDistAddrApplyDAO customerDistAddrApplyDAO = null;
	
	private CustomerDistAddrDAO customerDistAddrDAO = null;
	
	private AssignApplyDAO assignApplyDAO = null;
	
	private DestStafferVSCustomerDAO destStafferVSCustomerDAO = null;
	
    private AddressDAO addressDAO = null;
    
    private AddressHisDAO addressHisDAO = null;
    
    private CiticBranchDAO citicBranchDAO = null;
    
    private CiticVSStafferDAO citicVSStafferDAO = null;
    
    private CustomerFormerNameDAO customerFormerNameDAO = null;
    
    private CustomerIndividualHisDAO customerIndividualHisDAO = null;
    
    private CustomerDepartHisDAO customerDepartHisDAO = null;
    
    private CustomerCorporationHisDAO customerCorporationHisDAO = null;
    
    private CustomerContactHisDAO customerContactHisDAO = null;
    
    private CustomerBusinessHisDAO customerBusinessHisDAO = null;
    
    private CustomerDistAddrHisDAO customerDistAddrHisDAO = null;
	
	/**
	 * 
	 */
	public ClientManagerImpl()
	{
		
	}

	@Transactional(rollbackFor = MYException.class)
	public boolean applyAddOrUpdateClient(User user, CustomerIndividualApplyBean bean, String addOrUpdate)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, bean, bean.getName());
		
		bean.setName(StringTools.getLineString(bean.getName().trim()));
		
		List<CustomerContactApplyBean> custContList = bean.getCustContList();
		
		List<CustomerBusinessApplyBean> custBusiList = bean.getCustBusiList();
		List<CustomerDistAddrApplyBean> custAddrList = bean.getCustAddrList();
		
		CustomerApproveBean approveBean = new CustomerApproveBean();
		
		BeanUtil.copyProperties(approveBean, bean);
		
		String id = "";
		String code = "";
		
		if (addOrUpdate.equals("0"))
		{
			id = commonDAO.getSquenceString();
			// 自动生成code
	        code = commonDAO.getSquenceString20();
	        
	        approveBean.setOpr(CustomerConstant.OPR_ADD);
	        
	        bean.setLogTime(TimeTools.now());
		}else{
			id = bean.getId();
			code = bean.getCode();
			
			approveBean.setOpr(CustomerConstant.OPR_UPDATE);
		}

        // 新增时,如果联系人中没有姓名和手机与个人信息相同的,系统自动增加一条联系信
		if (addOrUpdate.equals("0"))
		{
			boolean hadConnecter = false;
			for (CustomerContactApplyBean each : custContList)
			{
				if (each.getName().equals(bean.getName())
						&&each.getHandphone().equals(bean.getHandphone()))
				{
					hadConnecter = true;
					
					break;
				}
			}
			
			if (!hadConnecter){
				
				CustomerContactApplyBean custContBean = new CustomerContactApplyBean();
				
				BeanUtil.copyProperties(custContBean, bean);
				
				custContList.add(custContBean);
			}
		}
		
//        ClientHelper.encryptIndividualClient(bean);
        
        approveBean.setCode(code);

        approveBean.setStatus(CustomerConstant.STATUS_APPLY);

        approveBean.setApplyId(user.getStafferId());
        
        approveBean.setApplyType(0);

        approveBean.setId(id);

        approveBean.setLogTime(TimeTools.now());
        
        checkBean(approveBean, addOrUpdate);
		
		bean.setId(id);
		
        bean.setCode(code);
        
        CustomerIndividualApplyBean oldBean = customerIndividualApplyDAO.find(id);
        
        if (null != oldBean)
        {
        	throw new MYException("存在未审批的申请，请确认");
        }
        
		customerIndividualApplyDAO.saveEntityBean(bean);
		
		if (!StringTools.isNullOrNone(bean.getFormerCust()))
			updFormerCust(bean.getId(),bean.getFormerCust());
		
		updContAndBusi(id, custContList, custBusiList, custAddrList);
		
		// 生成待审批信息
		customerApproveDAO.saveEntityBean(approveBean);
		
		return true;
	}
	
	private void checkBean(CustomerApproveBean bean, String addOrUpdate)
    throws MYException
    {
		if (addOrUpdate.equals("0"))
		{
			checkAddBean(bean);
		}else{
			checkUpdateBean(bean);
		}
    }
	
    private void checkAddBean(CustomerApproveBean bean)
    throws MYException
	{
	    if (customerApproveDAO.countByUnique(bean.getName().trim()) > 0)
	    {
	        throw new MYException("客户[%s]已经存在申请", bean.getName());
	    }
	
	    if (customerMainDAO.countByUnique(bean.getName().trim()) > 0)
	    {
	        throw new MYException("客户名称[%s]已经存在", bean.getName());
	    }
	
	    // 验证申请中是否重复
	    if (customerApproveDAO.countByCode(bean.getCode().trim()) > 0)
	    {
	        throw new MYException("客户编码[%s]已经存在申请", bean.getName());
	    }
	
	    // 验证已经ok的客户中是否code重复
	    if (customerMainDAO.countByCode(bean.getCode().trim()) > 0)
	    {
	        throw new MYException("客户编码[%s]已经存在", bean.getName());
	    }
	}

    /**
     * 检查update的是否符合条件
     * 
     * @param bean
     * @throws MYException
     */
    private CustomerBean checkUpdateBean(CustomerApproveBean bean)
        throws MYException
    {
        CustomerBean old = customerMainDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("客户不存在,请重新操作");
        }

        if ( !old.getName().trim().equals(bean.getName().trim()))
        {
            if (customerApproveDAO.countByUnique(bean.getName().trim()) > 0)
            {
                throw new MYException("客户名称[%s]已经存在", bean.getName());
            }

            if (customerMainDAO.countByUnique(bean.getName().trim()) > 0)
            {
                throw new MYException("客户名称[%s]已经存在", bean.getName());
            }
        }

        if ( !old.getCode().trim().equals(bean.getCode().trim()))
        {
            // 验证申请中是否重复
            if (customerApproveDAO.countByCode(bean.getCode().trim()) > 0)
            {
                throw new MYException("客户编码[%s]已经存在", bean.getCode());
            }

            // 验证已经ok的客户中是否code重复
            if (customerMainDAO.countByCode(bean.getCode().trim()) > 0)
            {
                throw new MYException("客户编码[%s]已经存在", bean.getCode());
            }
        }

        CustomerApproveBean cbean = customerApproveDAO.find(bean.getId());

        if (cbean != null)
        {
            if (cbean.getOpr() == CustomerConstant.OPR_UPATE_CREDIT
                || cbean.getOpr() == CustomerConstant.OPR_UPATE_ASSIGNPER)
            {
                throw new MYException("客户[%s]存在信用或者利润分配修改,请先结束此审批", bean.getName());
            }
        }
        
        return old;
    }
    
    /**
     * 
     * @param currCust
     * @param notUses : 以","间隔
     */
    private void updFormerCust(String currCust, String notUses)
    {
    	String [] notUseCusts = notUses.split(",");
    	
    	for (String each : notUseCusts)
    	{
    		if (StringTools.isNullOrNone(each))
    			continue;
    			
    		CustomerFormerNameBean former = customerFormerNameDAO.findByUnique(each);
    		
    		if (null != former){
    			customerFormerNameDAO.deleteEntityBean(former.getId());
    		}
    		
    		CustomerFormerNameBean formerName = new CustomerFormerNameBean();
    		
    		formerName.setCurrCustId(currCust);
    		formerName.setFormerCustId(each);
    		
    		customerFormerNameDAO.saveEntityBean(formerName);
    	}
    }
    
	@Transactional(rollbackFor = MYException.class)
	public boolean applyAddOrUpdateClient(User user, CustomerDepartApplyBean bean, String addOrUpdate)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, bean, bean.getName());
		
		bean.setName(StringTools.getLineString(bean.getName().trim()));
		
		CustomerApproveBean approveBean = new CustomerApproveBean();
		
		BeanUtil.copyProperties(approveBean, bean);
		
		String id = "";
		String code = "";
		
		if (addOrUpdate.equals("0"))
		{
			id = commonDAO.getSquenceString();
			// 自动生成code
	        code = commonDAO.getSquenceString20();
	        
	        approveBean.setOpr(CustomerConstant.OPR_ADD);
	        
	        bean.setLogTime(TimeTools.now());
		}else{
			id = bean.getId();
			code = bean.getCode();
			
			approveBean.setOpr(CustomerConstant.OPR_UPDATE);
		}

        approveBean.setCode(code);

        approveBean.setStatus(CustomerConstant.STATUS_APPLY);

        approveBean.setApplyId(user.getStafferId());
        
        approveBean.setApplyType(0);

        approveBean.setId(id);

        approveBean.setLogTime(TimeTools.now());
        
        checkBean(approveBean, addOrUpdate);
		
		List<CustomerContactApplyBean> custContList = bean.getCustContList();
		List<CustomerBusinessApplyBean> custBusiList = bean.getCustBusiList();
		List<CustomerDistAddrApplyBean> custAddrList = bean.getCustAddrList();
		
		bean.setId(id);
		
        bean.setCode(code);
        
        bean.setRefDepartCustId(id);

        CustomerDepartApplyBean oldBean = customerDepartApplyDAO.find(id);
        
        if (null != oldBean)
        {
        	throw new MYException("存在未审批的申请，请确认");
        }
        
        customerDepartApplyDAO.saveEntityBean(bean);
        
		if (!StringTools.isNullOrNone(bean.getFormerCust()))
			updFormerCust(bean.getId(),bean.getFormerCust());
		
		updContAndBusi(id, custContList, custBusiList, custAddrList);
		
		// 生成待审批信息
		customerApproveDAO.saveEntityBean(approveBean);
		
		return true;
	}

	@Transactional(rollbackFor = MYException.class)
	public boolean applyAddOrUpdateClient(User user, CustomerCorporationApplyBean bean, String addOrUpdate)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, bean, bean.getName());
		
		bean.setName(StringTools.getLineString(bean.getName().trim()));
		
		CustomerApproveBean approveBean = new CustomerApproveBean();
		
		BeanUtil.copyProperties(approveBean, bean);
		
		String id = "";
		String code = "";
		
		if (addOrUpdate.equals("0"))
		{
			id = commonDAO.getSquenceString();
			// 自动生成code
	        code = commonDAO.getSquenceString20();
	        
	        approveBean.setOpr(CustomerConstant.OPR_ADD);
	        
	        bean.setLogTime(TimeTools.now());
		}else{
			id = bean.getId();
			code = bean.getCode();
			
			approveBean.setOpr(CustomerConstant.OPR_UPDATE);
		}

        approveBean.setCode(code);

        approveBean.setStatus(CustomerConstant.STATUS_APPLY);

        approveBean.setApplyId(user.getStafferId());
        
        approveBean.setApplyType(0);

        approveBean.setId(id);

        approveBean.setLogTime(TimeTools.now());
        
        checkBean(approveBean, addOrUpdate);
		
		List<CustomerContactApplyBean> custContList = bean.getCustContList();
		List<CustomerBusinessApplyBean> custBusiList = bean.getCustBusiList();
		List<CustomerDistAddrApplyBean> custAddrList = bean.getCustAddrList();
		
		bean.setId(id);
		
        bean.setCode(code);
        
        bean.setRefCorpCustId(id);
        
        CustomerCorporationApplyBean oldBean = customerCorporationApplyDAO.find(id);
        
        if (null != oldBean)
        {
        	throw new MYException("存在未审批的申请，请确认");
        }
        
		customerCorporationApplyDAO.saveEntityBean(bean);
        
		if (!StringTools.isNullOrNone(bean.getFormerCust()))
			updFormerCust(bean.getId(),bean.getFormerCust());
		
		updContAndBusi(id, custContList, custBusiList, custAddrList);

		// 生成待审批信息
		customerApproveDAO.saveEntityBean(approveBean);
		
		return true;
	}

    @Transactional(rollbackFor = {MYException.class})
    public boolean delApply(User user, String cid)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, cid);

        CustomerApproveBean bean = customerApproveDAO.find(cid);

        if (bean == null)
        {
            throw new MYException("申请的客户不存在");
        }

        if ( !user.getStafferId().equals(bean.getApplyId()))
        {
            throw new MYException("只有申请人才可以删除");
        }

        // 从apply里面删除
        customerApproveDAO.deleteEntityBean(cid);
        
    	customerIndividualApplyDAO.deleteEntityBean(cid);
    	customerDepartApplyDAO.deleteEntityBean(cid);
    	customerCorporationApplyDAO.deleteEntityBean(cid);
    	customerContactApplyDAO.deleteEntityBeansByFK(cid);
    	customerBusinessApplyDAO.deleteEntityBeansByFK(cid);
    	customerDistAddrApplyDAO.deleteEntityBeansByFK(cid);
    	
    	customerFormerNameDAO.deleteEntityBeansByFK(cid);

        return true;
    }
	
    public boolean hasCustomerAuth(String stafferId, String customerId)
    throws MYException
	{
	    JudgeTools.judgeParameterIsNull(stafferId, customerId);
	
	    return stafferVSCustomerDAO.countByStafferIdAndCustomerId(stafferId, customerId) > 0;
	}
    
	public CustomerIndividualVO findIndividualVO(String id, String update) throws MYException
	{
		CustomerIndividualVO vo = customerIndividualDAO.findVO(id);
		
		if (null == vo)
		{
			throw new MYException("数据错误,单据不存在");
		}
		
		String formers = vo.getFormerCust();
		
		String formerName = "";
		
		formerName = processFormerCust(formers);
		
		vo.setFormerCustName(formerName);
		
		List<CustomerContactBean> custContList = null;
		List<CustomerBusinessBean> custBusiVOList = null;
		List<CustomerDistAddrVO> custAddrVOList = null;
		
		if (!StringTools.isNullOrNone(update) && (update.equals("1")||update.equals("99")))
		{
			custContList = customerContactDAO.queryEntityBeansByCidAndValid(id, 0);
			
			custBusiVOList = customerBusinessDAO.queryEntityBeansByCidAndValid(id, 0);
			
			custAddrVOList = customerDistAddrDAO.queryEntityBeansByCidAndValid(id, 0);
		}else{
			custContList = customerContactDAO.queryEntityBeansByCondition("where customerId=? order by valid", id);
			
			custBusiVOList = customerBusinessDAO.queryEntityVOsByCondition("where customerId=? order by valid", id);
			
			custAddrVOList = customerDistAddrDAO.queryEntityVOsByCondition("where customerId=? order by valid", id);
		}
		
		vo.setCustContList(custContList);
		vo.setCustBusiList(custBusiVOList);
		vo.setCustAddrVOList(custAddrVOList);
		
		return vo;
	}

	public CustomerDepartVO findDepartVO(String id, String update) throws MYException
	{
		CustomerDepartVO vo = customerDepartDAO.findVO(id);
		
		if (null == vo)
		{
			throw new MYException("数据错误,单据不存在");
		}
		
		String formers = vo.getFormerCust();
		
		String formerName = "";
		
		formerName = processFormerCust(formers);
		
		vo.setFormerCustName(formerName);
		
		List<CustomerContactBean> custContList = null;
		List<CustomerBusinessBean> custBusiVOList = null;
		List<CustomerDistAddrVO> custAddrVOList = null;
		
		if (!StringTools.isNullOrNone(update) && (update.equals("1")||update.equals("99")))
		{
			custContList = customerContactDAO.queryEntityBeansByCidAndValid(id, 0);
			
			custBusiVOList = customerBusinessDAO.queryEntityBeansByCidAndValid(id, 0);
			
			custAddrVOList = customerDistAddrDAO.queryEntityBeansByCidAndValid(id, 0);
		}else{
			custContList = customerContactDAO.queryEntityBeansByCondition("where customerId=? order by valid", id);
			
			custBusiVOList = customerBusinessDAO.queryEntityVOsByCondition("where customerId=? order by valid", id);
			
			custAddrVOList = customerDistAddrDAO.queryEntityVOsByCondition("where customerId=? order by valid", id);
		}
		
		vo.setCustContList(custContList);
		vo.setCustBusiList(custBusiVOList);
		vo.setCustAddrVOList(custAddrVOList);
		
		return vo;
	}

	public CustomerCorporationVO findCorporationVO(String id, String update) throws MYException
	{
		CustomerCorporationVO vo = customerCorporationDAO.findVO(id);
		
		if (null == vo)
		{
			throw new MYException("数据错误,单据不存在");
		}
		
		String formers = vo.getFormerCust();
		
		String formerName = "";
		
		formerName = processFormerCust(formers);
		
		vo.setFormerCustName(formerName);
		
		List<CustomerContactBean> custContList = null;
		List<CustomerBusinessBean> custBusiVOList = null;
		List<CustomerDistAddrVO> custAddrVOList = null;
		
		if (!StringTools.isNullOrNone(update) && (update.equals("1")||update.equals("99")))
		{
			custContList = customerContactDAO.queryEntityBeansByCidAndValid(id, 0);
			
			custBusiVOList = customerBusinessDAO.queryEntityBeansByCidAndValid(id, 0);
			
			custAddrVOList = customerDistAddrDAO.queryEntityBeansByCidAndValid(id, 0);
		}else{
			custContList = customerContactDAO.queryEntityBeansByCondition("where customerId=? order by valid", id);
			
			custBusiVOList = customerBusinessDAO.queryEntityVOsByCondition("where customerId=? order by valid", id);
			
			custAddrVOList = customerDistAddrDAO.queryEntityVOsByCondition("where customerId=? order by valid", id);
		}
		
		vo.setCustContList(custContList);
		vo.setCustBusiList(custBusiVOList);
		vo.setCustAddrVOList(custAddrVOList);
		
		return vo;
	}
	
	public CustomerIndividualApplyVO findIndividualApplyVO(String id) throws MYException
	{
		CustomerIndividualApplyVO vo = customerIndividualApplyDAO.findVO(id);
		
		if (null == vo)
		{
			throw new MYException("数据错误,单据不存在");
		}
		
		String formers = vo.getFormerCust();
		
		String formerName = "";
		
		formerName = processFormerCust(formers);
		
		vo.setFormerCustName(formerName);
		
		List<CustomerContactApplyBean> custContList = customerContactApplyDAO.queryEntityBeansByFK(id);
		
		List<CustomerBusinessApplyBean> custBusiVOList = customerBusinessApplyDAO.queryEntityVOsByFK(id);
		
		List<CustomerDistAddrApplyVO> custAddrVOList = customerDistAddrApplyDAO.queryEntityVOsByFK(id);
		
		vo.setCustContList(custContList);
		vo.setCustBusiList(custBusiVOList);
		vo.setCustAddrVOList(custAddrVOList);
		
		return vo;
	}

	private String processFormerCust(String formers)
	{
		String formerName = "";
		
		if (!StringTools.isNullOrNone(formers))
		{
			String[] arr = formers.split(",");
			
			for (int i = 0; i < arr.length; i++)
			{
				CustomerBean cust = customerMainDAO.find(arr[i]);
				
				if (i == arr.length - 1)
				{
					formerName = formerName + cust.getName();
				}
				else
				{
					formerName = formerName + cust.getName() + ",";
				}
			}
		}
		
		return formerName;
	}

	public CustomerDepartApplyVO findDepartApplyVO(String id) throws MYException
	{
		CustomerDepartApplyVO vo = customerDepartApplyDAO.findVO(id);
		
		if (null == vo)
		{
			throw new MYException("数据错误,单据不存在");
		}
		
		String formers = vo.getFormerCust();
		
		String formerName = "";
		
		formerName = processFormerCust(formers);
		
		vo.setFormerCustName(formerName);
		
		List<CustomerContactApplyBean> custContList = customerContactApplyDAO.queryEntityBeansByFK(id);
		
		List<CustomerBusinessApplyBean> custBusiVOList = customerBusinessApplyDAO.queryEntityVOsByFK(id);
		
		List<CustomerDistAddrApplyVO> custAddrVOList = customerDistAddrApplyDAO.queryEntityVOsByFK(id);
		
		vo.setCustContList(custContList);
		vo.setCustBusiList(custBusiVOList);
		vo.setCustAddrVOList(custAddrVOList);
		
		return vo;
	}

	public CustomerCorporationApplyVO findCorporationApplyVO(String id) throws MYException
	{
		CustomerCorporationApplyVO vo = customerCorporationApplyDAO.findVO(id);
		
		if (null == vo)
		{
			throw new MYException("数据错误,单据不存在");
		}
		
		String formers = vo.getFormerCust();
		
		String formerName = "";
		
		formerName = processFormerCust(formers);
		
		vo.setFormerCustName(formerName);
		
		List<CustomerContactApplyBean> custContList = customerContactApplyDAO.queryEntityBeansByFK(id);
		
		List<CustomerBusinessApplyBean> custBusiVOList = customerBusinessApplyDAO.queryEntityVOsByFK(id);
		
		List<CustomerDistAddrApplyVO> custAddrVOList = customerDistAddrApplyDAO.queryEntityVOsByFK(id);
		
		vo.setCustContList(custContList);
		vo.setCustBusiList(custBusiVOList);
		vo.setCustAddrVOList(custAddrVOList);
		
		return vo;
	}
    
	@Transactional(rollbackFor = {MYException.class})
	public synchronized boolean passApplyClient(User user, String cid) throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, cid);

        CustomerApproveBean bean = customerApproveDAO.find(cid);

        checkPass(bean);

        if (bean.getOpr() == CustomerConstant.OPR_ADD)
        {
            // 先审批改变状态
            // customerApplyDAO.updateStatus(cid, CustomerConstant.STATUS_WAIT_CODE);
            handleAdd(user, bean);
        }

        if (bean.getOpr() == CustomerConstant.OPR_UPDATE)
        {
            handleUpdate(user, bean);
        }

        if (bean.getOpr() == CustomerConstant.OPR_DEL)
        {
            checkDelCustomer(bean);

            handleDelete(bean);
        }

        return true;
    }

	@Transactional(rollbackFor = {MYException.class})
	public boolean rejectApplyClient(User user, String cid, String reson)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, cid);

        CustomerApproveBean bean = customerApproveDAO.find(cid);

        checkPass(bean);

        bean.setStatus(CustomerConstant.STATUS_REJECT);

        bean.setReson(reson);

        customerApproveDAO.updateEntityBean(bean);

        return true;
    }
	
    /**
     * @param bean
     * @throws MYException
     */
    private void checkPass(CustomerApproveBean bean)
        throws MYException
    {
        if (bean == null)
        {
            throw new MYException("审批的客户不存在");
        }

        if (bean.getStatus() != CustomerConstant.STATUS_APPLY)
        {
            throw new MYException("审批的客户不在申请态");
        }
    }
    
    /**
     * 处理增加从apply到正式表
     * 
     * @param user
     * @param cid
     * @param bean
     * @throws MYException
     */
    private void handleAdd(User user, CustomerApproveBean bean)
        throws MYException
    {
    	String createrId = bean.getApplyId();
    	
    	CustomerBean cbean = new CustomerBean();
    	
    	if (bean.getType() == CustomerConstant.NATURE_INDIVIDUAL)
    	{
    		CustomerIndividualApplyBean applyBean = customerIndividualApplyDAO.find(bean.getId());
    		
    		if (null == applyBean)
    		{
    			throw new MYException("数据错误");
    		}
    		
    		BeanUtil.copyProperties(cbean, applyBean);
    		
    		CustomerIndividualBean indiBean = new CustomerIndividualBean();
    		
    		BeanUtil.copyProperties(indiBean, applyBean);
    		
    		customerIndividualDAO.saveEntityBean(indiBean);
    		
        	customerIndividualApplyDAO.deleteEntityBean(bean.getId());
    		
    	}else if (bean.getType() == CustomerConstant.NATURE_DEPART)
    	{
    		CustomerDepartApplyBean applyBean = customerDepartApplyDAO.find(bean.getId());
    		
    		if (null == applyBean)
    		{
    			throw new MYException("数据错误");
    		}
    		
    		BeanUtil.copyProperties(cbean, applyBean);
    		
    		CustomerDepartBean departBean = new CustomerDepartBean();
    		
    		BeanUtil.copyProperties(departBean, applyBean);
    		
    		customerDepartDAO.saveEntityBean(departBean);
    		
        	customerDepartApplyDAO.deleteEntityBean(bean.getId());
    		
    	}else{
    		CustomerCorporationApplyBean applyBean = customerCorporationApplyDAO.find(bean.getId());
    		
    		if (null == applyBean)
    		{
    			throw new MYException("数据错误");
    		}
    		
    		BeanUtil.copyProperties(cbean, applyBean);
    		
    		CustomerCorporationBean corpBean = new CustomerCorporationBean();
    		
    		BeanUtil.copyProperties(corpBean, applyBean);
    		
    		customerCorporationDAO.saveEntityBean(corpBean);
    		
        	customerCorporationApplyDAO.deleteEntityBean(bean.getId());
    	}

    	List<CustomerContactApplyBean> custContList = customerContactApplyDAO.queryEntityBeansByFK(bean.getId());
    	
    	for (CustomerContactApplyBean each : custContList)
    	{
    		CustomerContactBean custContBean = new CustomerContactBean();
    		
    		BeanUtil.copyProperties(custContBean, each);
    		
    		customerContactDAO.saveEntityBean(custContBean);
    		
    		customerContactApplyDAO.deleteEntityBean(each.getId());
    	}
    	
    	List<CustomerBusinessApplyBean> custBusiList = customerBusinessApplyDAO.queryEntityBeansByFK(bean.getId());
    	
    	for (CustomerBusinessApplyBean each : custBusiList)
    	{
    		CustomerBusinessBean custBusiBean = new CustomerBusinessBean();
    		
    		BeanUtil.copyProperties(custBusiBean, each);
    		
    		customerBusinessDAO.saveEntityBean(custBusiBean);
    		
    		customerBusinessApplyDAO.deleteEntityBean(each.getId());
    	}
    	
    	List<CustomerDistAddrApplyBean> custAddrList = customerDistAddrApplyDAO.queryEntityBeansByFK(bean.getId());
    	
    	for (CustomerDistAddrApplyBean each : custAddrList)
    	{
    		CustomerDistAddrBean custAddrBean = new CustomerDistAddrBean();
    		
    		BeanUtil.copyProperties(custAddrBean, each);
    		
    		customerDistAddrDAO.saveEntityBean(custAddrBean);
    		
    		customerDistAddrApplyDAO.deleteEntityBean(each.getId());
    	}
    	
        //CustomerHisBean hisbean = new CustomerHisBean();

        //BeanUtil.copyProperties(hisbean, bean);

        checkRealAdd(cbean);

        cbean.setStatus(CustomerConstant.REAL_STATUS_IDLE);
        
        cbean.setCreateTime(TimeTools.now());
        cbean.setLogTime(TimeTools.now());

        StafferBean creater = stafferDAO.find(createrId);

        // 如果客户所在的分公司和用户所在分公司相同,则默认给这个申请人
        // && creater.getLocationId().equals(bean.getLocationId())
        if (creater != null && stafferVSCustomerDAO.countByUnique(bean.getId()) == 0)
        {
            StafferVSCustomerBean vs = new StafferVSCustomerBean();

            vs.setStafferId(createrId);

            vs.setCustomerId(bean.getId());

            addStafferVSCustomer(vs);

            cbean.setStatus(CustomerConstant.REAL_STATUS_USED);
        }

        // 加入到正表里面
        customerMainDAO.saveEntityBean(cbean);

/*        hisbean.setCustomerId(cid);

        hisbean.setUpdaterId(user.getStafferId());

        hisbean.setCheckStatus(CustomerConstant.HIS_CHECK_YES);

        // 记录到his
        customerHisDAO.saveEntityBean(hisbean);*/
        
        // 从apply里面删除
        customerApproveDAO.deleteEntityBean(bean.getId());
    }
    
    /**
     * @param user
     * @param cid
     * @param bean
     * @throws MYException
     */
    private void handleUpdate(User user, CustomerApproveBean bean)
        throws MYException
    {
    	CustomerBean oldBean = customerMainDAO.find(bean.getId());
    	
    	if (null == oldBean)
    	{
    		throw new MYException("数据错误");
    	}
    	
    	String batchId = commonDAO.getSquenceString20();
    	
    	CustomerBean cbean = new CustomerBean();
    	
    	if (bean.getType() == CustomerConstant.NATURE_INDIVIDUAL)
    	{
    		CustomerIndividualApplyBean applyBean = customerIndividualApplyDAO.find(bean.getId());
    		
    		if (null == applyBean)
    		{
    			throw new MYException("数据错误");
    		}
    		
    		BeanUtil.copyProperties(cbean, applyBean);
    		
    		CustomerIndividualBean indiBean = new CustomerIndividualBean();
    		
    		BeanUtil.copyProperties(indiBean, applyBean);
    		
    		// 记录修改日志  begin
    		CustomerIndividualHisBean his0 = new CustomerIndividualHisBean();
    		
    		CustomerIndividualBean oldIndi = customerIndividualDAO.find(bean.getId());
    		
    		if (null != oldIndi)
    		{
    			BeanUtil.copyProperties(his0, oldIndi);
    			
    			his0.setCustomerId(bean.getId());
    			his0.setBatchId(batchId);
    			his0.setForward(0);
    			
    			customerIndividualHisDAO.saveEntityBean(his0);
    		}
    		
    		CustomerIndividualHisBean his1 = new CustomerIndividualHisBean();
    		
			BeanUtil.copyProperties(his1, applyBean);
			
			his1.setCustomerId(bean.getId());
			his1.setBatchId(batchId);
			his1.setForward(1);
			
			customerIndividualHisDAO.saveEntityBean(his1);
    		
			// 记录修改日志  end
			
    		customerIndividualDAO.deleteEntityBean(bean.getId());
    		customerIndividualDAO.saveEntityBean(indiBean);
    		
        	customerIndividualApplyDAO.deleteEntityBean(bean.getId());
    		
    	}else if (bean.getType() == CustomerConstant.NATURE_DEPART)
    	{
    		CustomerDepartApplyBean applyBean = customerDepartApplyDAO.find(bean.getId());
    		
    		if (null == applyBean)
    		{
    			throw new MYException("数据错误");
    		}
    		
    		BeanUtil.copyProperties(cbean, applyBean);
    		
    		CustomerDepartBean departBean = new CustomerDepartBean();
    		
    		BeanUtil.copyProperties(departBean, applyBean);
    		
    		// 记录修改日志  begin
    		CustomerDepartHisBean his0 = new CustomerDepartHisBean();
    		
    		CustomerDepartBean old = customerDepartDAO.find(bean.getId());
    		
    		if (null != old)
    		{
    			BeanUtil.copyProperties(his0, old);
    			
    			his0.setCustomerId(bean.getId());
    			his0.setBatchId(batchId);
    			his0.setForward(0);
    			
    			customerDepartHisDAO.saveEntityBean(his0);
    		}
    		
    		CustomerDepartHisBean his1 = new CustomerDepartHisBean();
    		
			BeanUtil.copyProperties(his1, applyBean);
			
			his1.setCustomerId(bean.getId());
			his1.setBatchId(batchId);
			his1.setForward(1);
			
			customerDepartHisDAO.saveEntityBean(his1);
    		
			// 记录修改日志  end
    		
    		customerDepartDAO.deleteEntityBean(bean.getId());
    		customerDepartDAO.saveEntityBean(departBean);
    		
        	customerDepartApplyDAO.deleteEntityBean(bean.getId());
    		
    	}else{
    		CustomerCorporationApplyBean applyBean = customerCorporationApplyDAO.find(bean.getId());
    		
    		if (null == applyBean)
    		{
    			throw new MYException("数据错误");
    		}
    		
    		BeanUtil.copyProperties(cbean, applyBean);
    		
    		CustomerCorporationBean corpBean = new CustomerCorporationBean();
    		
    		BeanUtil.copyProperties(corpBean, applyBean);
    		
    		// 记录修改日志  begin
    		CustomerCorporationHisBean his0 = new CustomerCorporationHisBean();
    		
    		CustomerCorporationBean old = customerCorporationDAO.find(bean.getId());
    		
    		if (null != old)
    		{
    			BeanUtil.copyProperties(his0, old);
    			
    			his0.setCustomerId(bean.getId());
    			his0.setBatchId(batchId);
    			his0.setForward(0);
    			
    			customerCorporationHisDAO.saveEntityBean(his0);
    		}
    		
    		CustomerCorporationHisBean his1 = new CustomerCorporationHisBean();
    		
			BeanUtil.copyProperties(his1, applyBean);
			
			his1.setCustomerId(bean.getId());
			his1.setBatchId(batchId);
			his1.setForward(1);
			
			customerCorporationHisDAO.saveEntityBean(his1);
    		
			// 记录修改日志  end
    		
    		customerCorporationDAO.deleteEntityBean(bean.getId());
    		customerCorporationDAO.saveEntityBean(corpBean);
    		
        	customerCorporationApplyDAO.deleteEntityBean(bean.getId());
    	}

    	// 记录客户附属信息
    	saveOthersHis(bean.getId(), batchId);
    	
    	// 处理客户联系信息,先处理删除部分
    	List<CustomerContactBean> ocustContList = customerContactDAO.queryEntityBeansByFK(bean.getId());
    	
    	for (CustomerContactBean oeach : ocustContList)
    	{
    		CustomerContactApplyBean apply = customerContactApplyDAO.find(oeach.getId());
    		
    		// 表示已删除的，将数据置为无效
    		if (null == apply)
    		{
    			customerContactDAO.updateValid(oeach.getId(), 1);
    		}
    	}
    	
    	List<CustomerContactApplyBean> custContList = customerContactApplyDAO.queryEntityBeansByFK(bean.getId());
    	
    	for (CustomerContactApplyBean each : custContList)
    	{
    		CustomerContactBean custContBean = new CustomerContactBean();
    		
    		BeanUtil.copyProperties(custContBean, each);
    		
    		customerContactDAO.deleteEntityBean(each.getId());
    		customerContactDAO.saveEntityBean(custContBean);
    		
    		customerContactApplyDAO.deleteEntityBean(each.getId());
    	}
    	
    	List<CustomerBusinessBean> ocustBusiList = customerBusinessDAO.queryEntityBeansByFK(bean.getId());
    	
    	for (CustomerBusinessBean oeach : ocustBusiList)
    	{
    		CustomerBusinessApplyBean apply = customerBusinessApplyDAO.find(oeach.getId());
    		
    		if (null == apply)
    		{
    			customerBusinessDAO.updateValid(oeach.getId(), 1);
    		}
    	}
    	
    	List<CustomerBusinessApplyBean> custBusiList = customerBusinessApplyDAO.queryEntityBeansByFK(bean.getId());
    	
    	for (CustomerBusinessApplyBean each : custBusiList)
    	{
    		CustomerBusinessBean custBusiBean = new CustomerBusinessBean();
    		
    		BeanUtil.copyProperties(custBusiBean, each);
    		
    		customerBusinessDAO.deleteEntityBean(each.getId());
    		customerBusinessDAO.saveEntityBean(custBusiBean);
    		
    		customerBusinessApplyDAO.deleteEntityBean(each.getId());
    	}
    	
    	List<CustomerDistAddrBean> ocustAddrList = customerDistAddrDAO.queryEntityBeansByFK(bean.getId());
    	
    	for (CustomerDistAddrBean oeach : ocustAddrList)
    	{
    		CustomerDistAddrApplyBean apply = customerDistAddrApplyDAO.find(oeach.getId());
    		
    		if (null == apply)
    		{
    			customerDistAddrDAO.updateValid(oeach.getId(), 1);
    		}
    	}
    	
    	List<CustomerDistAddrApplyBean> custAddrList = customerDistAddrApplyDAO.queryEntityBeansByFK(bean.getId());
    	
    	for (CustomerDistAddrApplyBean each : custAddrList)
    	{
    		CustomerDistAddrBean custAddrBean = new CustomerDistAddrBean();
    		
    		BeanUtil.copyProperties(custAddrBean, each);
    		
    		customerDistAddrDAO.deleteEntityBean(each.getId());
    		customerDistAddrDAO.saveEntityBean(custAddrBean);
    		
    		customerDistAddrApplyDAO.deleteEntityBean(each.getId());
    	}
    	
        checkRealAdd(cbean);

        cbean.setHistorySales(oldBean.getHistorySales());
        cbean.setSalesAmount(oldBean.getSalesAmount());
        cbean.setCreditLevelId(oldBean.getCreditLevelId());
        cbean.setCreditUpdateTime(oldBean.getCreditUpdateTime());
        cbean.setCreditVal(oldBean.getCreditVal());
        cbean.setContactTimes(oldBean.getContactTimes());
        cbean.setLastContactTime(oldBean.getLastContactTime());
        cbean.setStatus(oldBean.getStatus());
        cbean.setOstatus(oldBean.getOstatus());
        cbean.setCreaterId(oldBean.getCreaterId());
        cbean.setCreateTime(oldBean.getCreateTime());
        cbean.setLogTime(TimeTools.now());
        
        // 加入到正表里面
        customerMainDAO.updateEntityBean(cbean);

        // 从apply里面删除
        customerApproveDAO.deleteEntityBean(bean.getId());
    }
    
    /**
     * 
     * @param cid
     * @param batchId
     */
    private void saveOthersHis(String cid, String batchId)
    {
    	List<CustomerContactBean> oldcustContList = customerContactDAO.queryEntityBeansByFK(cid);
    	
    	for(CustomerContactBean each : oldcustContList)
    	{
    		CustomerContactHisBean his = new CustomerContactHisBean();
    		
    		BeanUtil.copyProperties(his, each);
    		
    		his.setParentId(each.getId());
    		his.setBatchId(batchId);
    		his.setForward(0);
    		
    		customerContactHisDAO.saveEntityBean(his);
    	}
    	
    	List<CustomerContactApplyBean> custContList = customerContactApplyDAO.queryEntityBeansByFK(cid);
    	
    	for(CustomerContactApplyBean each : custContList)
    	{
    		CustomerContactHisBean his = new CustomerContactHisBean();
    		
    		BeanUtil.copyProperties(his, each);
    		
    		his.setParentId(each.getId());
    		his.setBatchId(batchId);
    		his.setForward(1);
    		
    		customerContactHisDAO.saveEntityBean(his);
    	}
    	
    	List<CustomerBusinessBean> oldcustBusiList = customerBusinessDAO.queryEntityBeansByFK(cid);
    	
    	for(CustomerBusinessBean each : oldcustBusiList)
    	{
    		CustomerBusinessHisBean his = new CustomerBusinessHisBean();
    		
    		BeanUtil.copyProperties(his, each);
    		
    		his.setParentId(each.getId());
    		his.setBatchId(batchId);
    		his.setForward(0);
    		
    		customerBusinessHisDAO.saveEntityBean(his);
    	}
    	
    	List<CustomerBusinessApplyBean> custBusiList = customerBusinessApplyDAO.queryEntityBeansByFK(cid);
    	
    	for(CustomerBusinessApplyBean each : custBusiList)
    	{
    		CustomerBusinessHisBean his = new CustomerBusinessHisBean();
    		
    		BeanUtil.copyProperties(his, each);
    		
    		his.setParentId(each.getId());
    		his.setBatchId(batchId);
    		his.setForward(1);
    		
    		customerBusinessHisDAO.saveEntityBean(his);
    	}
    	
    	List<CustomerDistAddrBean> oldcustAddrList = customerDistAddrDAO.queryEntityBeansByFK(cid);
    	
    	for(CustomerDistAddrBean each : oldcustAddrList)
    	{
    		CustomerDistAddrHisBean his = new CustomerDistAddrHisBean();
    		
    		BeanUtil.copyProperties(his, each);
    		
    		his.setParentId(each.getId());
    		his.setBatchId(batchId);
    		his.setForward(0);
    		
    		customerDistAddrHisDAO.saveEntityBean(his);
    	}

    	List<CustomerDistAddrApplyBean> custAddrList = customerDistAddrApplyDAO.queryEntityBeansByFK(cid);
    	
    	for(CustomerDistAddrApplyBean each : custAddrList)
    	{
    		CustomerDistAddrHisBean his = new CustomerDistAddrHisBean();
    		
    		BeanUtil.copyProperties(his, each);
    		
    		his.setParentId(each.getId());
    		his.setBatchId(batchId);
    		his.setForward(1);
    		
    		customerDistAddrHisDAO.saveEntityBean(his);
    	}
    }
    
    /**
     * @param cid
     * @param bean
     */
    private void handleDelete(CustomerApproveBean bean)
    {
        // 从apply里面删除
        customerApproveDAO.deleteEntityBean(bean.getId());

        // 操作正表里面 - 要求不真正删除，只是更改为闲置状态
        // customerMainDAO.deleteEntityBean(cid);
        customerMainDAO.updateCustomerstatus(bean.getId(), CustomerConstant.REAL_STATUS_IDLE);

        // 需要删除职员和客户的对应关系
        stafferVSCustomerDAO.deleteEntityBeansByFK(bean.getId(), AnoConstant.FK_FIRST);
    }
    
    /**
     * 增加到正式表的检验
     * 
     * @param cbean
     * @throws MYException
     */
    private void checkRealAdd(CustomerBean cbean)
        throws MYException
    {
        // 修改区域属性
        String cityId = cbean.getCityId();

        if (StringTools.isNullOrNone(cityId))
        {
            throw new MYException("地市属性不存在,请确认数据的准确性");
        }
    }
    
    /**
     * 检查删除
     * 
     * @param bean
     * @throws MYException
     */
    private void checkDelCustomer(CustomerApproveBean bean)
        throws MYException
    {
        if (bean.getId().equals(CustomerConstant.PUBLIC_CUSTOMER_ID))
        {
            throw new MYException("公共客户,不能删除");
        }
    }
    
    /**
     * 保存关系,顺便记录日志
     * 
     * @param vs
     * @throws MYException
     */
    private void addStafferVSCustomer(StafferVSCustomerBean vs)
    {
        // 这里不检查主键重复了
        stafferVSCustomerDAO.saveEntityBean(vs);
        
        CustomerBean cb = customerMainDAO.find(vs.getCustomerId());

        if (cb == null)
        {
            return;
        }

        StafferBean sb = stafferDAO.find(vs.getStafferId());

        if (sb == null)
        {
            return;
        }

        ChangeLogBean log = new ChangeLogBean();

        log.setCustomerCode(cb.getId());
        log.setCustomerName(cb.getName());
        log.setCustomerCode(cb.getCode());

        log.setStafferId(sb.getId());

        log.setStafferName(sb.getName());

        log.setLogTime(TimeTools.now());

        log.setOperation(OperationConstant.OPERATION_CHANGELOG_ADD);

        changeLogDAO.saveEntityBean(log);
    }
    
    @Transactional(rollbackFor = MYException.class)
	public void addAccessLog(User user, String stafferId, String customerId)
			throws MYException
	{
        String selfCust = "属于自己的客户";
    	
    	boolean hasAuth = hasCustomerAuth(user.getStafferId(), customerId);
    	
    	if (!hasAuth)
    	{
    		selfCust = "不属于自己的客户";
    	}
    	
    	AccessLogBean log = new AccessLogBean();
    	
    	log.setStafferId(stafferId);
    	log.setCustomerId(customerId);
    	log.setLogTime(TimeTools.now());
    	log.setDescription(selfCust);
    	
    	accessLogDAO.saveEntityBean(log);
	}
    
	private void updContAndBusi(String cid,
			List<CustomerContactApplyBean> custContList,
			List<CustomerBusinessApplyBean> custBusiList,
			List<CustomerDistAddrApplyBean> custAddrList) throws MYException
	{
		for(CustomerContactApplyBean each : custContList)
		{
			 checkHandphoneUnique(each);
			
			// 新的
			if (StringTools.isNullOrNone(each.getId()))
			{
				each.setId(commonDAO.getSquenceString20());
			}
			
			each.setCustomerId(cid);
			
//			ClientHelper.encryptClientContact(each);
			
			customerContactApplyDAO.saveEntityBean(each);
		}
		
		for(CustomerBusinessApplyBean each : custBusiList)
		{
			if (StringTools.isNullOrNone(each.getId()))
			{
				each.setId(commonDAO.getSquenceString20());	
			}

			each.setCustomerId(cid);
			
			customerBusinessApplyDAO.saveEntityBean(each);
		}
		
		for(CustomerDistAddrApplyBean each : custAddrList)
		{
			if (StringTools.isNullOrNone(each.getId()))
			{
				each.setId(commonDAO.getSquenceString20());	
			}

			each.setCustomerId(cid);
			
			customerDistAddrApplyDAO.saveEntityBean(each);
		}
	}

	private void checkHandphoneUnique(CustomerContactApplyBean each) throws MYException
	{
		// 如果 修改前后不一样，则要判断手机唯一性
		if (!StringTools.isNullOrNone(each.getHandphone()))
		{
			if (StringTools.isNullOrNone(each.getId()))
			{
				boolean isUnique = customerContactDAO.uniqueHandphone(each.getHandphone());
				
				if (!isUnique)
				{
					throw new MYException("手机号已存在");
				}
			}else
			{
				CustomerContactBean ccb = customerContactDAO.find(each.getId());
				
				if (null != ccb)
				{
					if(!StringTools.isNullOrNone(ccb.getHandphone()) && !ccb.getHandphone().equals(each.getHandphone()))
					{
						boolean isUnique = customerContactDAO.uniqueHandphone(each.getHandphone());
						
						if (!isUnique)
						{
							throw new MYException("手机号已存在");
						}
					}
				}
			}
		}
	}
    
	/**
	 * applyDelClient
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = MYException.class)
	public boolean applyDelClient(User user, CustomerApproveBean bean)
	throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, bean, bean.getId());

        checkDelCustomer(bean);

        // 删除存在的(直接覆盖)
        customerApproveDAO.deleteEntityBean(bean.getId());

        bean.setApplyId(user.getStafferId());

        bean.setStatus(CustomerConstant.STATUS_APPLY);

        bean.setOpr(CustomerConstant.OPR_DEL);

        bean.setLogTime(TimeTools.now());

        customerApproveDAO.saveEntityBean(bean);

        return true;
    }
	
    /**
     * addAssignApply(增加客户和职员的对应关系)
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */

    @Transactional(rollbackFor = {MYException.class})
    public synchronized boolean addAssignApply(User user, AssignApplyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        CustomerBean cus = checkAssign(bean);

        cus.setStatus(CustomerConstant.REAL_STATUS_APPLY);

        assignApplyDAO.saveEntityBean(bean);

        customerMainDAO.updateEntityBean(cus);

        return true;
    }
	
    /**
     * @param bean
     * @throws MYException
     */
    private CustomerBean checkAssign(AssignApplyBean bean)
        throws MYException
    {
        if (assignApplyDAO.countByUnique(bean.getCustomerId()) > 0)
        {
            throw new MYException("客户已经被申请");
        }

        CustomerBean cus = customerMainDAO.find(bean.getCustomerId());

        if (cus == null)
        {
            throw new MYException("申请分配的客户不存在");
        }

        if (cus.getStatus() != CustomerConstant.REAL_STATUS_IDLE)
        {
            throw new MYException("申请分配的客户[%s]不在空闲态,请核实", cus.getName());
        }

        checkAtt(bean, cus);

        return cus;
    }
    
    /**
     * 检查属性
     * 
     * @param apply
     * @param cus
     * @throws MYException
     */
    private void checkAtt(AssignApplyBean apply, CustomerBean cus)
        throws MYException
    {
        StafferBean sb = stafferDAO.find(apply.getStafferId());

        if (sb == null)
        {
            throw new MYException("申请的职员不存在");
        }

        // NOTE zhuzhu 判断客户的属性和业务的是否吻合 --  modify 2012.7.2 去掉以下判断
/*        if (cus.getSelltype() != sb.getExamType())
        {
            throw new MYException("职员[%s]的终端拓展属性和客户[%s]的终端拓展属性不一致", sb.getName(), cus.getName());
        }*/
    }
    
    /**
     * 通过分配申请
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */

    @Transactional(rollbackFor = {MYException.class})
    public synchronized boolean passAssignApply(User user, String cid)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, cid);

        AssignApplyBean apply = assignApplyDAO.find(cid);

        CustomerBean cus = checkOprAssign(apply, true);

        checkAtt(apply, cus);

        // 删除申请
        assignApplyDAO.deleteEntityBean(cid);

        StafferVSCustomerBean vs = new StafferVSCustomerBean();

        vs.setStafferId(apply.getStafferId());

        vs.setCustomerId(apply.getCustomerId());

        // 保存对应关系
        addStafferVSCustomer(vs);

        // 修改客户状态
        cus.setStatus(CustomerConstant.REAL_STATUS_USED);

        customerMainDAO.updateEntityBean(cus);

        Collection<ClientListener> listenerMapValues = this.listenerMapValues();

        for (ClientListener customerListener : listenerMapValues)
        {
            customerListener.onChangeCustomerRelation(user, apply, cus);
        }

        return true;
    }
    
    /**
     * 检查分配客户操作的逻辑校验
     * 
     * @param apply
     * @return
     * @throws MYException
     */
    private CustomerBean checkOprAssign(AssignApplyBean apply, boolean pass)
        throws MYException
    {
        if (apply == null)
        {
            throw new MYException("申请不存在,请刷新数据");
        }

        CustomerBean cus = customerMainDAO.find(apply.getCustomerId());

        if (cus == null)
        {
            throw new MYException("申请分配的客户不存在");
        }

        if (pass && cus.getStatus() == CustomerConstant.REAL_STATUS_USED)
        {
            throw new MYException("申请分配的客户[%s]已经被使用,请核实", cus.getName());
        }

        return cus;
    }
    
    /**
     * 驳回分配申请(就是删除)
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */

    @Transactional(rollbackFor = {MYException.class})
    public synchronized boolean rejectAssignApply(User user, String cid)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, cid);

        AssignApplyBean apply = assignApplyDAO.find(cid);

        CustomerBean cus = checkOprAssign(apply, false);

        // 删除申请
        assignApplyDAO.deleteEntityBean(cid);

        // 只有申请态中才更新
        if (cus.getStatus() == CustomerConstant.REAL_STATUS_APPLY)
        {
            // 修改客户状态
            cus.setStatus(CustomerConstant.REAL_STATUS_IDLE);

            customerMainDAO.updateEntityBean(cus);
        }

        return true;
    }
    
    /**
     * 回收分配客户
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */

    @Transactional(rollbackFor = {MYException.class})
    public synchronized boolean reclaimAssignClient(User user, String cid, String type, String destStafferId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, cid);

        if (type.equals("1"))
        {
        	if (StringTools.isNullOrNone(destStafferId))
        	{
        		throw new MYException("批量回收客户时，没有指定接收客户的职员");
        	}
        	else
        	{
        		StafferVSCustomerBean vsBean = stafferVSCustomerDAO.findByUnique(cid);
        		
        		if (null != vsBean)
        		{
        			DestStafferVSCustomerBean destVSBean = new DestStafferVSCustomerBean();
    				
    				destVSBean.setStafferId(vsBean.getStafferId());
    				destVSBean.setDestStafferId(destStafferId);
    				destVSBean.setCustomerId(vsBean.getCustomerId());
    				
    				destStafferVSCustomerDAO.saveEntityBean(destVSBean);
        		}
        	}        	        	
        }
        
        // 删除对应关系
        stafferVSCustomerDAO.deleteEntityBeansByFK(cid, AnoConstant.FK_FIRST);

        if (type.equals("1"))
        {
        	// 恢复批量移交状态
            customerMainDAO.updateCustomerstatus(cid, CustomerConstant.REAL_STATUS_BATCHTRANS);
        }
        else
        {
        	// 恢复空闲状态
            customerMainDAO.updateCustomerstatus(cid, CustomerConstant.REAL_STATUS_IDLE);
        }

        return true;
    }

    @Transactional(rollbackFor = {MYException.class})
    public synchronized boolean reclaimStafferAssignClient(User user, String stafferId, String destStafferId, int flag)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, stafferId);

        if (flag == 3)
        {
        	if (StringTools.isNullOrNone(destStafferId))
        	{
        		throw new MYException("批量移交客户时，没有接收客户的职员，请确认");
        	}
        	else
        	{
        		List<StafferVSCustomerBean> vsList = stafferVSCustomerDAO.queryEntityBeansByFK(stafferId);
        		
        		if (!ListTools.isEmptyOrNull(vsList))
        		{
        			Map<String, String> tmpMap = new HashMap<String, String>();
        			
        			for(Iterator<StafferVSCustomerBean> iterator = vsList.iterator(); iterator.hasNext();)
        			{
        				StafferVSCustomerBean vsBean = iterator.next();
        				
        				String key = vsBean.getStafferId()+vsBean.getCustomerId();
        				
        				if (tmpMap.containsKey(key))
        				{
        					continue;
        				}
        				else
        				{
        					tmpMap.put(key, key);
        				}
        				
        				DestStafferVSCustomerBean destVSBean = new DestStafferVSCustomerBean();
        				
        				destVSBean.setStafferId(stafferId);
        				destVSBean.setDestStafferId(destStafferId);
        				destVSBean.setCustomerId(vsBean.getCustomerId());
        				
        				destStafferVSCustomerDAO.saveEntityBean(destVSBean);
        			}
        		}
        	}
        	
        	flag = 0;
        	
        	customerMainDAO.updateCustomerByStafferId(stafferId, CustomerConstant.REAL_STATUS_BATCHTRANS, flag);
        }
        else
        {
        	// 先更新状态
            customerMainDAO.updateCustomerByStafferId(stafferId, CustomerConstant.REAL_STATUS_IDLE, flag);
        }

        // 再删除对应关系(不能颠倒，否则无法更新客户状态)
        customerMainDAO.delCustomerByStafferId(stafferId, flag);

        return true;
    }
    
    public double sumNoPayBusiness(CustomerBean bean)
    {
        Collection<ClientListener> listenerMapValues = this.listenerMapValues();

        double result = 0.0d;

        for (ClientListener customerCreditListener : listenerMapValues)
        {
            result += customerCreditListener.onNoPayBusiness(bean);
        }

        return result;
    }
    
    /**
     * 增加地址
     * 
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = MYException.class)
	public boolean addAddressBean(User user, AddressBean bean)
			throws MYException
	{
    	JudgeTools.judgeParameterIsNull(user, bean);
    	
    	String id = commonDAO.getSquenceString();
    	
    	bean.setId(id);
    	
    	addressDAO.saveEntityBean(bean);
    	
		return true;
	}

    /**
     * 更新地址
     * 
     * {@inheritDoc}
     */
	@Transactional(rollbackFor = MYException.class)
	public boolean updateAddressBean(User user, AddressBean bean)
			throws MYException
	{
    	JudgeTools.judgeParameterIsNull(user, bean);
    	
    	AddressBean oldBean = addressDAO.find(bean.getId());
    	
    	if (null == oldBean)
    	{
    		throw new MYException("数据错误，原单不存在");
    	}
    	
    	// 记录修改记录
    	AddressHisBean hisBean = new AddressHisBean();
    	
    	AddressHisBean newBean = new AddressHisBean();
    	
    	String batch = commonDAO.getSquenceString20();
    	
    	BeanUtil.copyProperties(hisBean, oldBean);
    	
    	hisBean.setBatch(batch);
    	
    	addressHisDAO.saveEntityBean(hisBean);
    	
    	BeanUtil.copyProperties(newBean, bean);
    	
    	newBean.setUpdator(user.getStafferId());
    	newBean.setUpdateTime(TimeTools.now());
    	newBean.setBatch(batch);
    	
    	addressHisDAO.saveEntityBean(newBean);
    	
    	addressDAO.updateEntityBean(bean);
    	
		return true;
	}

	/**
	 * 删除地址
	 * 
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = MYException.class)
	public boolean delAddressBean(User user, String id) throws MYException
	{
    	JudgeTools.judgeParameterIsNull(user, id);
    	
    	AddressBean oldBean = addressDAO.find(id);
    	
    	if (null == oldBean)
    	{
    		throw new MYException("数据错误，原单不存在");
    	}
    	
    	addressDAO.deleteEntityBean(id);
    	
		return true;
	}
    
	/**
     * addBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean addCiticBean(User user, CiticBranchBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkAdd(user, bean);

        bean.setId(commonDAO.getSquenceString20());

        citicBranchDAO.saveEntityBean(bean);

        List<CiticVSStafferBean> items = bean.getVsList();

        // save items
        if ( !ListTools.isEmptyOrNull(items))
        {
            for (CiticVSStafferBean groupVSStafferBean : items)
            {
                groupVSStafferBean.setRefId(bean.getId());

                citicVSStafferDAO.saveEntityBean(groupVSStafferBean);
            }
        }

        return true;
    }	
	
    private void checkAdd(User user, CiticBranchBean bean)
    throws MYException
	{
	    // handle duplicate name
    	CiticBranchVO vo = citicBranchDAO.findVOByUnique(bean.getStafferId());
    	
	    if (vo != null)
	    {
	        throw new MYException("操作员[%s]重复", vo.getStafferName());
	    }
	}
    
    /**
     * updateBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateCiticBean(User user, CiticBranchBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

//        checkAdd(user, bean);

        citicBranchDAO.updateEntityBean(bean);

        List<CiticVSStafferBean> items = bean.getVsList();

        // save items
        if ( !ListTools.isEmptyOrNull(items))
        {
            // delete old items
        	citicVSStafferDAO.deleteEntityBeansByFK(bean.getId());

            // save new items
            for (CiticVSStafferBean groupVSStafferBean : items)
            {
                groupVSStafferBean.setRefId(bean.getId());

                citicVSStafferDAO.saveEntityBean(groupVSStafferBean);
            }
        }

        return true;
    }    
    
    @Transactional(rollbackFor = {MYException.class})
    public boolean delCiticBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        citicBranchDAO.deleteEntityBean(id);

        citicVSStafferDAO.deleteEntityBeansByFK(id);

        return true;
    }
    
    /**
     * 批量接收移交的客户
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = MYException.class)
    public synchronized boolean batchTransCustomer(User user, int type) throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, type);

        List<DestStafferVSCustomerBean> destVSList = destStafferVSCustomerDAO.queryEntityBeansByFK(user.getStafferId(), AnoConstant.FK_SECOND);
        
        if (!ListTools.isEmptyOrNull(destVSList))
        {
        	for (DestStafferVSCustomerBean each : destVSList)
        	{
        		// 确认接收
        		if (type == 0)
        		{
        			AssignApplyBean apply = new AssignApplyBean();
                	
                	apply.setStafferId(each.getDestStafferId());
                	apply.setCustomerId(each.getCustomerId());

                    CustomerBean cus = checkOprAssign(apply, false);

                    if (cus.getStatus() == CustomerConstant.REAL_STATUS_USED)
                    {
                    	destStafferVSCustomerDAO.deleteEntityBean(each.getId());
                    	
                    	continue;
                    }
                    
                    checkAtt(apply, cus);

                    StafferVSCustomerBean vs = new StafferVSCustomerBean();

                    vs.setStafferId(apply.getStafferId());

                    vs.setCustomerId(apply.getCustomerId());

                    // 保存对应关系
                    addStafferVSCustomer(vs);

                    // 修改客户状态
                    cus.setStatus(CustomerConstant.REAL_STATUS_USED);

                    customerMainDAO.updateEntityBean(cus);

                    Collection<ClientListener> listenerMapValues = this.listenerMapValues();

                    for (ClientListener customerListener : listenerMapValues)
                    {
                        customerListener.onChangeCustomerRelation(user, apply, cus);
                    }
                    
                    destStafferVSCustomerDAO.deleteEntityBean(each.getId());
        		}
        		// 驳回，不接收
        		else
        		{
        			AssignApplyBean apply = new AssignApplyBean();
                	
                	apply.setStafferId(each.getStafferId());
                	apply.setCustomerId(each.getCustomerId());

                    CustomerBean cus = checkOprAssign(apply, false);

                    if (cus.getStatus() == CustomerConstant.REAL_STATUS_USED)
                    {
                    	destStafferVSCustomerDAO.deleteEntityBean(each.getId());
                    	
                    	continue;
                    }
                    
                    checkAtt(apply, cus);

                    StafferVSCustomerBean vs = new StafferVSCustomerBean();

                    vs.setStafferId(apply.getStafferId());

                    vs.setCustomerId(apply.getCustomerId());

                    // 保存对应关系
                    addStafferVSCustomer(vs);

                    // 修改客户状态
                    cus.setStatus(CustomerConstant.REAL_STATUS_USED);

                    customerMainDAO.updateEntityBean(cus);
                    
                    destStafferVSCustomerDAO.deleteEntityBean(each.getId());
        		
        		}
        		
        	}
        }

        return true;
    }
    
    @Deprecated
    @Transactional(rollbackFor = MYException.class)
    public void synchronizationAllCustomerLocation()
    {
        triggerLog.info("synchronizationAllCustomerLocation begin...");

        try
        {
            List<LocationVSCityBean> list = locationVSCityDAO.listEntityBeans();

            customerMainDAO.initCustomerLocation();

            for (LocationVSCityBean locationVSCityBean : list)
            {
                customerMainDAO.updateCustomerLocationByCity(locationVSCityBean.getCityId(),
                    locationVSCityBean.getLocationId());
            }
        }
        catch (Exception e)
        {
            triggerLog.error(e, e);
        }

        triggerLog.info("synchronizationAllCustomerLocation end...");
    }
    
    public boolean hasCustomerAuth2(String stafferId, String customerId)
    {
        return stafferVSCustomerDAO.countByStafferIdAndCustomerId(stafferId, customerId) > 0;
    }
    
	/**
	 * @return the commonDAO
	 */
	public CommonDAO getCommonDAO()
	{
		return commonDAO;
	}

	/**
	 * @param commonDAO the commonDAO to set
	 */
	public void setCommonDAO(CommonDAO commonDAO)
	{
		this.commonDAO = commonDAO;
	}

	/**
	 * @return the customerIndividualDAO
	 */
	public CustomerIndividualDAO getCustomerIndividualDAO()
	{
		return customerIndividualDAO;
	}

	/**
	 * @param customerIndividualDAO the customerIndividualDAO to set
	 */
	public void setCustomerIndividualDAO(CustomerIndividualDAO customerIndividualDAO)
	{
		this.customerIndividualDAO = customerIndividualDAO;
	}

	/**
	 * @return the customerDepartDAO
	 */
	public CustomerDepartDAO getCustomerDepartDAO()
	{
		return customerDepartDAO;
	}

	/**
	 * @param customerDepartDAO the customerDepartDAO to set
	 */
	public void setCustomerDepartDAO(CustomerDepartDAO customerDepartDAO)
	{
		this.customerDepartDAO = customerDepartDAO;
	}

	/**
	 * @return the customerCorporationDAO
	 */
	public CustomerCorporationDAO getCustomerCorporationDAO()
	{
		return customerCorporationDAO;
	}

	/**
	 * @param customerCorporationDAO the customerCorporationDAO to set
	 */
	public void setCustomerCorporationDAO(
			CustomerCorporationDAO customerCorporationDAO)
	{
		this.customerCorporationDAO = customerCorporationDAO;
	}

	/**
	 * @return the customerContactDAO
	 */
	public CustomerContactDAO getCustomerContactDAO()
	{
		return customerContactDAO;
	}

	/**
	 * @param customerContactDAO the customerContactDAO to set
	 */
	public void setCustomerContactDAO(CustomerContactDAO customerContactDAO)
	{
		this.customerContactDAO = customerContactDAO;
	}

	/**
	 * @return the customerBusinessDAO
	 */
	public CustomerBusinessDAO getCustomerBusinessDAO()
	{
		return customerBusinessDAO;
	}

	/**
	 * @param customerBusinessDAO the customerBusinessDAO to set
	 */
	public void setCustomerBusinessDAO(CustomerBusinessDAO customerBusinessDAO)
	{
		this.customerBusinessDAO = customerBusinessDAO;
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
	 * @return the stafferVSCustomerDAO
	 */
	public StafferVSCustomerDAO getStafferVSCustomerDAO()
	{
		return stafferVSCustomerDAO;
	}

	/**
	 * @param stafferVSCustomerDAO the stafferVSCustomerDAO to set
	 */
	public void setStafferVSCustomerDAO(StafferVSCustomerDAO stafferVSCustomerDAO)
	{
		this.stafferVSCustomerDAO = stafferVSCustomerDAO;
	}

	/**
	 * @return the stafferDAO
	 */
	public StafferDAO getStafferDAO()
	{
		return stafferDAO;
	}

	/**
	 * @param stafferDAO the stafferDAO to set
	 */
	public void setStafferDAO(StafferDAO stafferDAO)
	{
		this.stafferDAO = stafferDAO;
	}

	/**
	 * @return the changeLogDAO
	 */
	public ChangeLogDAO getChangeLogDAO()
	{
		return changeLogDAO;
	}

	/**
	 * @param changeLogDAO the changeLogDAO to set
	 */
	public void setChangeLogDAO(ChangeLogDAO changeLogDAO)
	{
		this.changeLogDAO = changeLogDAO;
	}

	/**
	 * @return the accessLogDAO
	 */
	public AccessLogDAO getAccessLogDAO()
	{
		return accessLogDAO;
	}

	/**
	 * @param accessLogDAO the accessLogDAO to set
	 */
	public void setAccessLogDAO(AccessLogDAO accessLogDAO)
	{
		this.accessLogDAO = accessLogDAO;
	}

	/**
	 * @return the customerIndividualApplyDAO
	 */
	public CustomerIndividualApplyDAO getCustomerIndividualApplyDAO()
	{
		return customerIndividualApplyDAO;
	}

	/**
	 * @param customerIndividualApplyDAO the customerIndividualApplyDAO to set
	 */
	public void setCustomerIndividualApplyDAO(
			CustomerIndividualApplyDAO customerIndividualApplyDAO)
	{
		this.customerIndividualApplyDAO = customerIndividualApplyDAO;
	}

	/**
	 * @return the customerDepartApplyDAO
	 */
	public CustomerDepartApplyDAO getCustomerDepartApplyDAO()
	{
		return customerDepartApplyDAO;
	}

	/**
	 * @param customerDepartApplyDAO the customerDepartApplyDAO to set
	 */
	public void setCustomerDepartApplyDAO(
			CustomerDepartApplyDAO customerDepartApplyDAO)
	{
		this.customerDepartApplyDAO = customerDepartApplyDAO;
	}

	/**
	 * @return the customerCorporationApplyDAO
	 */
	public CustomerCorporationApplyDAO getCustomerCorporationApplyDAO()
	{
		return customerCorporationApplyDAO;
	}

	/**
	 * @param customerCorporationApplyDAO the customerCorporationApplyDAO to set
	 */
	public void setCustomerCorporationApplyDAO(
			CustomerCorporationApplyDAO customerCorporationApplyDAO)
	{
		this.customerCorporationApplyDAO = customerCorporationApplyDAO;
	}

	/**
	 * @return the customerContactApplyDAO
	 */
	public CustomerContactApplyDAO getCustomerContactApplyDAO()
	{
		return customerContactApplyDAO;
	}

	/**
	 * @param customerContactApplyDAO the customerContactApplyDAO to set
	 */
	public void setCustomerContactApplyDAO(
			CustomerContactApplyDAO customerContactApplyDAO)
	{
		this.customerContactApplyDAO = customerContactApplyDAO;
	}

	/**
	 * @return the customerBusinessApplyDAO
	 */
	public CustomerBusinessApplyDAO getCustomerBusinessApplyDAO()
	{
		return customerBusinessApplyDAO;
	}

	/**
	 * @param customerBusinessApplyDAO the customerBusinessApplyDAO to set
	 */
	public void setCustomerBusinessApplyDAO(
			CustomerBusinessApplyDAO customerBusinessApplyDAO)
	{
		this.customerBusinessApplyDAO = customerBusinessApplyDAO;
	}

	/**
	 * @return the assignApplyDAO
	 */
	public AssignApplyDAO getAssignApplyDAO()
	{
		return assignApplyDAO;
	}

	/**
	 * @param assignApplyDAO the assignApplyDAO to set
	 */
	public void setAssignApplyDAO(AssignApplyDAO assignApplyDAO)
	{
		this.assignApplyDAO = assignApplyDAO;
	}

	/**
	 * @return the destStafferVSCustomerDAO
	 */
	public DestStafferVSCustomerDAO getDestStafferVSCustomerDAO()
	{
		return destStafferVSCustomerDAO;
	}

	/**
	 * @param destStafferVSCustomerDAO the destStafferVSCustomerDAO to set
	 */
	public void setDestStafferVSCustomerDAO(
			DestStafferVSCustomerDAO destStafferVSCustomerDAO)
	{
		this.destStafferVSCustomerDAO = destStafferVSCustomerDAO;
	}

	/**
	 * @return the addressDAO
	 */
	public AddressDAO getAddressDAO()
	{
		return addressDAO;
	}

	/**
	 * @param addressDAO the addressDAO to set
	 */
	public void setAddressDAO(AddressDAO addressDAO)
	{
		this.addressDAO = addressDAO;
	}

	/**
	 * @return the addressHisDAO
	 */
	public AddressHisDAO getAddressHisDAO()
	{
		return addressHisDAO;
	}

	/**
	 * @param addressHisDAO the addressHisDAO to set
	 */
	public void setAddressHisDAO(AddressHisDAO addressHisDAO)
	{
		this.addressHisDAO = addressHisDAO;
	}

	/**
	 * @return the citicBranchDAO
	 */
	public CiticBranchDAO getCiticBranchDAO()
	{
		return citicBranchDAO;
	}

	/**
	 * @param citicBranchDAO the citicBranchDAO to set
	 */
	public void setCiticBranchDAO(CiticBranchDAO citicBranchDAO)
	{
		this.citicBranchDAO = citicBranchDAO;
	}

	/**
	 * @return the citicVSStafferDAO
	 */
	public CiticVSStafferDAO getCiticVSStafferDAO()
	{
		return citicVSStafferDAO;
	}

	/**
	 * @param citicVSStafferDAO the citicVSStafferDAO to set
	 */
	public void setCiticVSStafferDAO(CiticVSStafferDAO citicVSStafferDAO)
	{
		this.citicVSStafferDAO = citicVSStafferDAO;
	}

	/**
	 * @return the locationVSCityDAO
	 */
	public LocationVSCityDAO getLocationVSCityDAO()
	{
		return locationVSCityDAO;
	}

	/**
	 * @param locationVSCityDAO the locationVSCityDAO to set
	 */
	public void setLocationVSCityDAO(LocationVSCityDAO locationVSCityDAO)
	{
		this.locationVSCityDAO = locationVSCityDAO;
	}

	/**
	 * @return the customerDistAddrDAO
	 */
	public CustomerDistAddrDAO getCustomerDistAddrDAO()
	{
		return customerDistAddrDAO;
	}

	/**
	 * @param customerDistAddrDAO the customerDistAddrDAO to set
	 */
	public void setCustomerDistAddrDAO(CustomerDistAddrDAO customerDistAddrDAO)
	{
		this.customerDistAddrDAO = customerDistAddrDAO;
	}

	/**
	 * @return the customerDistAddrApplyDAO
	 */
	public CustomerDistAddrApplyDAO getCustomerDistAddrApplyDAO()
	{
		return customerDistAddrApplyDAO;
	}

	/**
	 * @param customerDistAddrApplyDAO the customerDistAddrApplyDAO to set
	 */
	public void setCustomerDistAddrApplyDAO(
			CustomerDistAddrApplyDAO customerDistAddrApplyDAO)
	{
		this.customerDistAddrApplyDAO = customerDistAddrApplyDAO;
	}

	/**
	 * @return the customerFormerNameDAO
	 */
	public CustomerFormerNameDAO getCustomerFormerNameDAO()
	{
		return customerFormerNameDAO;
	}

	/**
	 * @param customerFormerNameDAO the customerFormerNameDAO to set
	 */
	public void setCustomerFormerNameDAO(CustomerFormerNameDAO customerFormerNameDAO)
	{
		this.customerFormerNameDAO = customerFormerNameDAO;
	}

	/**
	 * @return the customerIndividualHisDAO
	 */
	public CustomerIndividualHisDAO getCustomerIndividualHisDAO()
	{
		return customerIndividualHisDAO;
	}

	/**
	 * @param customerIndividualHisDAO the customerIndividualHisDAO to set
	 */
	public void setCustomerIndividualHisDAO(
			CustomerIndividualHisDAO customerIndividualHisDAO)
	{
		this.customerIndividualHisDAO = customerIndividualHisDAO;
	}

	/**
	 * @return the customerDepartHisDAO
	 */
	public CustomerDepartHisDAO getCustomerDepartHisDAO()
	{
		return customerDepartHisDAO;
	}

	/**
	 * @param customerDepartHisDAO the customerDepartHisDAO to set
	 */
	public void setCustomerDepartHisDAO(CustomerDepartHisDAO customerDepartHisDAO)
	{
		this.customerDepartHisDAO = customerDepartHisDAO;
	}

	/**
	 * @return the customerCorporationHisDAO
	 */
	public CustomerCorporationHisDAO getCustomerCorporationHisDAO()
	{
		return customerCorporationHisDAO;
	}

	/**
	 * @param customerCorporationHisDAO the customerCorporationHisDAO to set
	 */
	public void setCustomerCorporationHisDAO(
			CustomerCorporationHisDAO customerCorporationHisDAO)
	{
		this.customerCorporationHisDAO = customerCorporationHisDAO;
	}

	/**
	 * @return the customerContactHisDAO
	 */
	public CustomerContactHisDAO getCustomerContactHisDAO()
	{
		return customerContactHisDAO;
	}

	/**
	 * @param customerContactHisDAO the customerContactHisDAO to set
	 */
	public void setCustomerContactHisDAO(CustomerContactHisDAO customerContactHisDAO)
	{
		this.customerContactHisDAO = customerContactHisDAO;
	}

	/**
	 * @return the customerBusinessHisDAO
	 */
	public CustomerBusinessHisDAO getCustomerBusinessHisDAO()
	{
		return customerBusinessHisDAO;
	}

	/**
	 * @param customerBusinessHisDAO the customerBusinessHisDAO to set
	 */
	public void setCustomerBusinessHisDAO(
			CustomerBusinessHisDAO customerBusinessHisDAO)
	{
		this.customerBusinessHisDAO = customerBusinessHisDAO;
	}

	/**
	 * @return the customerDistAddrHisDAO
	 */
	public CustomerDistAddrHisDAO getCustomerDistAddrHisDAO()
	{
		return customerDistAddrHisDAO;
	}

	/**
	 * @param customerDistAddrHisDAO the customerDistAddrHisDAO to set
	 */
	public void setCustomerDistAddrHisDAO(
			CustomerDistAddrHisDAO customerDistAddrHisDAO)
	{
		this.customerDistAddrHisDAO = customerDistAddrHisDAO;
	}
}
