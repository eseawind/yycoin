/**
 * File Name: LocationAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.portal.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.center.china.osgi.publics.User;
import com.center.china.osgi.publics.file.read.ReadeFileFactory;
import com.center.china.osgi.publics.file.read.ReaderFile;
import com.center.china.osgi.publics.file.writer.WriteFile;
import com.center.china.osgi.publics.file.writer.WriteFileFactory;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONPageSeparateTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.common.PageSeparateTools;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.dao.CustomerMainDAO;
import com.china.center.oa.client.dao.StafferVSCustomerDAO;
import com.china.center.oa.client.vs.StafferVSCustomerBean;
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.bean.PaymentApplyBean;
import com.china.center.oa.finance.bean.PaymentBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.BackPayApplyDAO;
import com.china.center.oa.finance.dao.BankDAO;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.finance.dao.OutBillDAO;
import com.china.center.oa.finance.dao.PaymentApplyDAO;
import com.china.center.oa.finance.dao.PaymentDAO;
import com.china.center.oa.finance.dao.PaymentVSOutDAO;
import com.china.center.oa.finance.dao.StatBankDAO;
import com.china.center.oa.finance.facade.FinanceFacade;
import com.china.center.oa.finance.manager.BankManager;
import com.china.center.oa.finance.manager.BillManager;
import com.china.center.oa.finance.manager.PaymentManager;
import com.china.center.oa.finance.manager.StatBankManager;
import com.china.center.oa.finance.vo.BankVO;
import com.china.center.oa.finance.vo.BatchSplitInBillWrap;
import com.china.center.oa.finance.vo.InBillVO;
import com.china.center.oa.finance.vo.OutBillVO;
import com.china.center.oa.finance.vo.PaymentApplyVO;
import com.china.center.oa.finance.vo.PaymentVO;
import com.china.center.oa.finance.vo.PrePaymentWrap;
import com.china.center.oa.finance.vs.PaymentVSOutBean;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.InvoiceDAO;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.dao.RoleAuthDAO;
import com.china.center.oa.publics.helper.OATools;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.oa.publics.vs.RoleAuthBean;
import com.china.center.oa.sail.bean.OutBalanceBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.OutBalanceDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.manager.OutManager;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.dao.FinanceDAO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.SequenceTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.UtilStream;

/**
 * BankAction
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see FinanceAction
 * @since 1.0
 */
public class FinanceAction extends DispatchAction {
	
	private final Log _logger = LogFactory.getLog(getClass());
	
	private FinanceFacade financeFacade = null;

	private BankDAO bankDAO = null;

	private DutyDAO dutyDAO = null;

	private InBillDAO inBillDAO = null;

	private OutDAO outDAO = null;

	private FinanceDAO financeDAO = null;

	private OutBalanceDAO outBalanceDAO = null;

	private UserManager userManager = null;

	private PaymentDAO paymentDAO = null;

	private BillManager billManager = null;

	private FlowLogDAO flowLogDAO = null;

	private PaymentVSOutDAO paymentVSOutDAO = null;

	private PaymentApplyDAO paymentApplyDAO = null;

	private StatBankManager statBankManager = null;

	private StatBankDAO statBankDAO = null;

	private OutBillDAO outBillDAO = null;

	private OutManager outManager = null;

	private ParameterDAO parameterDAO = null;

	private BankManager bankManager = null;

	private PaymentManager paymentManager = null;
	
	private BackPayApplyDAO backPayApplyDAO = null;
	
	private StafferVSCustomerDAO stafferVSCustomerDAO = null; 
	
	private CustomerMainDAO customerMainDAO = null;
	
	private InvoiceDAO invoiceDAO = null;
	
	private RoleAuthDAO roleAuthDAO = null;
	
	private static final String QUERYBANK = "queryBank";

	private static final String RPTQUERYBANK = "rptQueryBank";

	private static final String QUERYPAYMENT = "queryPayment";

	private static final String QUERYSELFPAYMENT = "querySelfPayment";

	private static final String QUERYPAYMENTAPPLY = "queryPaymentApply";

	private static final String QUERYPAYMENTAPPLYCHECK = "queryPaymentApplyCheck";

	private static final String QUERYSTAT = "queryStat";

	private static final String QUERYSELFPAYMENTAPPLY = "querySelfPaymentApply";

	/**
	 * default constructor
	 */
	public FinanceAction() {
	}

	/**
	 * preForAddBank
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward preForAddBank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		return mapping.findForward("addBank");
	}

	/**
	 * queryBank
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryBank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		ActionTools.processJSONQueryCondition(QUERYBANK, request, condtion);

		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYBANK,
				request, condtion, this.bankDAO, new HandleResult<BankVO>() {
					public void handle(BankVO obj) {
						double total = statBankManager.findTotalByBankId(obj
								.getId());

						obj.setTotal(total);
					}
				});

		return JSONTools.writeResponse(response, jsonstr);
	}

	/**
	 * queryStat
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryStat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		ActionTools.processJSONQueryCondition(QUERYSTAT, request, condtion);

		condtion.addCondition("order by StatBankBean.logTime desc");

		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSTAT,
				request, condtion, this.statBankDAO);

		return JSONTools.writeResponse(response, jsonstr);
	}

	/**
	 * queryPaymentApply
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryPaymentApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		User user = Helper.getUser(request);

		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		ActionTools.processJSONQueryCondition(QUERYPAYMENTAPPLY, request,
				condtion);

		if (PageSeparateTools.isMenuLoad(request)
				|| StringTools.isNullOrNone(request.getParameter("status"))) {
			condtion.addIntCondition("PaymentApplyBean.status", "=",
					FinanceConstant.PAYAPPLY_STATUS_INIT);
		}

		if (!userManager.containAuth(user.getId(), AuthConstant.BILL_QUERY_ALL)) {
			condtion.addCondition("PaymentApplyBean.locationId", "=",
					user.getLocationId());
		}

		condtion.addCondition("order by PaymentApplyBean.logTime desc");
		String jsonstr = ActionTools.queryVOByJSONAndToString(
				QUERYPAYMENTAPPLY, request, condtion, this.paymentApplyDAO);

		return JSONTools.writeResponse(response, jsonstr);
	}

	/**
	 * queryPaymentApplyCheck
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryPaymentApplyCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		ActionTools.processJSONQueryCondition(QUERYPAYMENTAPPLYCHECK, request,
				condtion);

		condtion.addIntCondition("PaymentApplyBean.status", "=",
				FinanceConstant.PAYAPPLY_STATUS_CHECK);

		condtion.addCondition("order by PaymentApplyBean.logTime desc");

		String jsonstr = ActionTools
				.queryVOByJSONAndToString(QUERYPAYMENTAPPLYCHECK, request,
						condtion, this.paymentApplyDAO);

		return JSONTools.writeResponse(response, jsonstr);
	}

	/**
	 * querySelfPaymentApply
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward querySelfPaymentApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		User user = Helper.getUser(request);

		ActionTools.processJSONQueryCondition(QUERYSELFPAYMENTAPPLY, request,
				condtion);

		condtion.addCondition("PaymentApplyBean.stafferId", "=",
				user.getStafferId());

		condtion.addCondition("order by PaymentApplyBean.logTime desc");

		String jsonstr = ActionTools.queryVOByJSONAndToString(
				QUERYSELFPAYMENTAPPLY, request, condtion, this.paymentApplyDAO);

		return JSONTools.writeResponse(response, jsonstr);
	}

	/**
	 * queryPayment
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryPayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		Map<String, String> changeMap = initLogTime(request, condtion, false);

		ActionTools.processJSONDataQueryCondition(QUERYPAYMENT, request,
				condtion, changeMap);

		condtion.addCondition("order by PaymentBean.id desc");

		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYPAYMENT,
				request, condtion, this.paymentDAO);

		return JSONTools.writeResponse(response, jsonstr);
	}

	public ActionForward querySelfPayment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		User user = Helper.getUser(request);
		
		String roleid = user.getRoleId();

    	List<RoleAuthBean> authList = roleAuthDAO.queryEntityBeansByFK(roleid);
        
        int ctype = FinanceConstant.PAYMENTCTYPE_EXTERNAL;
        
        if(authList != null && authList.size() > 0 )
        {
        	for(int i = 0 ; i < authList.size(); i++ )
        	{
        		RoleAuthBean rab = authList.get(i);
        		if(rab.getAuthId().trim().equals("1630")) // 认领（财务）
        		{
        			ctype = FinanceConstant.PAYMENTCTYPE_INTERNAL;
        		}
        		
        		// 认领（全部）
        		if(rab.getAuthId().trim().equals("1631"))
        		{
        			ctype = 99;
        		}
        	}
        }
		
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		// 根据权限确定查询范围 内部、外部
		if (ctype != 99)
		{
			condtion.addIntCondition("PaymentBean.ctype", "=", ctype);
		}
		
		// TEMPLATE 在action里面默认查询条件
		Map<String, String> initMap = initLogTime(request, condtion, true);

		condtion.addCondition("and PaymentBean.destStafferId in ('0', '"
				+ Helper.getUser(request).getStafferId() + "')");

		ActionTools.processJSONDataQueryCondition(QUERYSELFPAYMENT, request,
				condtion, initMap);

		condtion.addCondition("order by PaymentBean.id desc");

		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSELFPAYMENT,
				request, condtion, this.paymentDAO);

		return JSONTools.writeResponse(response, jsonstr);
	}

	/**
	 * initLogTime
	 * 
	 * @param request
	 * @param condtion
	 * @return
	 */
	private Map<String, String> initLogTime(HttpServletRequest request,
			ConditionParse condtion, boolean initStatus) {
		Map<String, String> changeMap = new HashMap<String, String>();

		String alogTime = request.getParameter("alogTime");

		String blogTime = request.getParameter("blogTime");

		if (StringTools.isNullOrNone(alogTime)
				&& StringTools.isNullOrNone(blogTime)) {
			changeMap.put("alogTime", TimeTools.now_short(-30));

			changeMap.put("blogTime", TimeTools.now_short());

			if (initStatus) {
				changeMap.put("status",
						String.valueOf(FinanceConstant.PAYMENT_STATUS_INIT));

				condtion.addIntCondition("PaymentBean.status", "=",
						FinanceConstant.PAYMENT_STATUS_INIT);
			}

			condtion.addCondition("PaymentBean.logTime", ">=",
					TimeTools.now_short(-30) + " 00:00:00");

			condtion.addCondition("PaymentBean.logTime", "<=",
					TimeTools.now_short() + " 23:59:59");
		}

		return changeMap;
	}

	/**
	 * 查询客户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward rptQueryBank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException {
		CommonTools.saveParamers(request);

		List<BankVO> list = null;

		if (PageSeparateTools.isFirstLoad(request)) {
			ConditionParse condtion = new ConditionParse();

			condtion.addWhereStr();

			setInnerCondition(request, condtion);

			int total = bankDAO.countByCondition(condtion.toString());

			PageSeparate page = new PageSeparate(total,
					PublicConstant.PAGE_COMMON_SIZE);

			PageSeparateTools.initPageSeparate(condtion, page, request,
					RPTQUERYBANK);

			list = bankDAO.queryEntityVOsByCondition(condtion, page);
		} else {
			PageSeparateTools.processSeparate(request, RPTQUERYBANK);

			list = bankDAO.queryEntityVOsByCondition(
					PageSeparateTools.getCondition(request, RPTQUERYBANK),
					PageSeparateTools.getPageSeparate(request, RPTQUERYBANK));
		}

		for (BankVO bankVO : list) {
			double total = statBankManager.findTotalByBankId(bankVO.getId());

			bankVO.setTotal(total);
		}

		List<DutyBean> dutyList = dutyDAO.listEntityBeans();

		request.setAttribute("dutyList", dutyList);

		request.setAttribute("list", list);

		return mapping.findForward("rptQueryBank");
	}

	/**
	 * @param request
	 * @param condtion
	 */
	private void setInnerCondition(HttpServletRequest request,
			ConditionParse condtion) {
		String name = request.getParameter("name");

		String dutyId = request.getParameter("dutyId");

		if (!StringTools.isNullOrNone(name)) {
			condtion.addCondition("BankBean.name", "like", name);
		}

		if (!StringTools.isNullOrNone(dutyId)) {
			condtion.addCondition("BankBean.dutyId", "=", dutyId);
		}
		
		String invoiceId = request.getParameter("invoiceId");
		
		if (!StringTools.isNullOrNone(invoiceId))
		{
			// 无票（管理）
			if ("90000000000000000013".equals(invoiceId))
			{
				condtion.addIntCondition("BankBean.mtype", "=", PublicConstant.MANAGER_TYPE_MANAGER);
			}else{
				condtion.addIntCondition("BankBean.mtype", "=", PublicConstant.MANAGER_TYPE_COMMON);
			}
		}

		condtion.addCondition("order by BankBean.id desc");
	}

	/**
	 * addBank
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward addBank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		BankBean bean = new BankBean();

		try {
			BeanUtil.getBean(bean, request);

			User user = Helper.getUser(request);

			financeFacade.addBankBean(user.getId(), bean);

			request.setAttribute(KeyConstant.MESSAGE,
					"成功增加帐户:" + bean.getName());
		} catch (MYException e) {
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"增加失败:" + e.getMessage());
		}

		CommonTools.removeParamers(request);

		return mapping.findForward("queryBank");
	}

	/**
	 * addPaymentk
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward addPayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		PaymentBean bean = new PaymentBean();

		try {
			BeanUtil.getBean(bean, request);

			bean.setRefId("99" + SequenceTools.getSequence());

			bean.setBatchId(SequenceTools.getSequence());

			bean.setReceiveTime(TimeTools.now_short());

			User user = Helper.getUser(request);

			financeFacade.addPaymentBean(user.getId(), bean);

			request.setAttribute(KeyConstant.MESSAGE,
					"成功增加帐户:" + bean.getName());
		} catch (MYException e) {
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"增加失败:" + e.getMessage());
		}

		CommonTools.removeParamers(request);

		return mapping.findForward("queryPayment");
	}

	/**
	 * updateBank
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward updateBank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		BankBean bean = new BankBean();

		try {
			BeanUtil.getBean(bean, request);

			User user = Helper.getUser(request);

			financeFacade.updateBankBean(user.getId(), bean);

			request.setAttribute(KeyConstant.MESSAGE,
					"成功操作帐户:" + bean.getName());
		} catch (MYException e) {
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"操作失败:" + e.getMessage());
		}

		CommonTools.removeParamers(request);

		return mapping.findForward("queryBank");
	}

	/**
	 * delLocation
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward deleteBank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		AjaxResult ajax = new AjaxResult();

		try {
			String id = request.getParameter("id");

			User user = Helper.getUser(request);

			financeFacade.deleteBankBean(user.getId(), id);

			ajax.setSuccess("成功删除帐户");
		} catch (MYException e) {
			_logger.warn(e, e);

			ajax.setError("删除失败:" + e.getMessage());
		}

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * deletePayment
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward deletePayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		AjaxResult ajax = new AjaxResult();

		try {
			String id = request.getParameter("id");

			User user = Helper.getUser(request);

			financeFacade.deletePaymentBean(user.getId(), id);

			ajax.setSuccess("成功删除");
		} catch (MYException e) {
			_logger.warn(e, e);

			ajax.setError("删除失败:" + e.getMessage());
		}

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * checpPayment1
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward checpPayment1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		AjaxResult ajax = new AjaxResult();

		try {
			String id = request.getParameter("id");

			String reason = request.getParameter("reason");

			User user = Helper.getUser(request);

			paymentManager.checkBean1(user, id, reason);

			ajax.setSuccess("成功操作");
		} catch (MYException e) {
			_logger.warn(e, e);

			ajax.setError("操作失败:" + e.getMessage());
		}

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * checpPayment2
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward checpPayment2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		AjaxResult ajax = new AjaxResult();

		try {
			String id = request.getParameter("id");

			String reason = request.getParameter("reason");

			User user = Helper.getUser(request);

			paymentManager.checkBean2(user, id, reason);

			ajax.setSuccess("成功操作");
		} catch (MYException e) {
			_logger.warn(e, e);

			ajax.setError("操作失败:" + e.getMessage());
		}

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * 删除核对
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward checpPayment3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		AjaxResult ajax = new AjaxResult();

		try {
			String id = request.getParameter("id");

			String reason = request.getParameter("reason");

			User user = Helper.getUser(request);

			paymentManager.checkBean3(user, id, reason);

			ajax.setSuccess("成功操作");
		} catch (MYException e) {
			_logger.warn(e, e);

			ajax.setError("操作失败:" + e.getMessage());
		}

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * 核对
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward checkPaymentApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		AjaxResult ajax = new AjaxResult();

		try {
			String id = request.getParameter("id");

			String checks = request.getParameter("checks");

			String checkrefId = request.getParameter("checkrefId");

			User user = Helper.getUser(request);

			financeFacade.checkPaymentApply(user.getId(), id, checks,
					checkrefId);

			ajax.setSuccess("成功核对");
		} catch (MYException e) {
			_logger.warn(e, e);

			ajax.setError("核对失败:" + e.getMessage());
		}

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * batchDeletePayment
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward batchDeletePayment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		AjaxResult ajax = new AjaxResult();

		try {
			String id = request.getParameter("id");

			User user = Helper.getUser(request);

			financeFacade.batchDeletePaymentBean(user.getId(), id);

			ajax.setSuccess("成功删除");
		} catch (MYException e) {
			_logger.warn(e, e);

			ajax.setError("删除失败:" + e.getMessage());
		}

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * deletePaymentApply
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward deletePaymentApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		AjaxResult ajax = new AjaxResult();

		try {
			String id = request.getParameter("id");

			User user = Helper.getUser(request);

			financeFacade.deletePaymentApply(user.getId(), id);

			ajax.setSuccess("成功删除");
		} catch (MYException e) {
			_logger.warn(e, e);

			ajax.setError("删除失败:" + e.getMessage());
		}

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * 领取回款(第一次领取回款,可以绑定委托清单)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward drawPayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		try {
			String id = request.getParameter("id");

			String customerId = request.getParameter("customerId");
			
			String description = request.getParameter("description");
			
			User user = Helper.getUser(request);

			//商务 
	        ActionForward error = checkAuthForEcommerce(request, user, mapping);
	    	
	    	if (null != error)
	    	{
	    		return error;
	    	}
			
			// 认领
			financeFacade.drawPaymentBean(user.getId(), id, customerId,description);

			// 绑定
			addApply(request, id, customerId, user,description);

			request.setAttribute(KeyConstant.MESSAGE, "成功操作");
		} catch (MYException e) {
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"操作操作：" + e.getErrorContent());
		}

		return mapping.findForward("querySelfPayment");
	}

	/**
	 * drawPayment2(认领到的回款绑定预收或者销售)方法废弃
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	@Deprecated
	public ActionForward drawPayment2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		try {
			String id = request.getParameter("id");

			String customerId = request.getParameter("customerId");
			
			String description = request.getParameter("description");

			User user = Helper.getUser(request);

			// 付款申请
			addApply(request, id, customerId, user,description);

			request.setAttribute(KeyConstant.MESSAGE, "成功操作");
		} catch (MYException e) {
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"操作操作：" + e.getErrorContent());
		}

		return mapping.findForward("querySelfPayment");
	}

	/**
	 * drawPayment3(销售单和付收款单的关联,业务员勾款)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward drawPayment3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		try {
			String customerId = request.getParameter("customerId");

			String description = request.getParameter("description");
			
			User user = Helper.getUser(request);

	        //商务 
	        ActionForward error = checkAuthForEcommerce(request, user, mapping);
	    	
	    	if (null != error)
	    	{
	    		return error;
	    	}
			
			addApply2(request, customerId, user,description);

			request.setAttribute(KeyConstant.MESSAGE, "成功操作");
		} catch (MYException e) {
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"操作操作：" + e.getErrorContent());
		}

		// 到自己的申请界面
		return mapping.findForward("querySelfPaymentApply");
	}

	/**
	 * 预收转费用
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward drawPayment4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		AjaxResult ajax = new AjaxResult();

		try {
			String billId = request.getParameter("billId");

			User user = Helper.getUser(request);

			addApply3(request, billId, user);

			ajax.setSuccess("成功操作");
		} catch (MYException e) {
			_logger.warn(e, e);

			ajax.setError("操作失败:" + e.getMessage());
		}

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryForDrawPayment5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException 
	{		
		String billId = request.getParameter("billId");
		
		InBillVO inBillBean = inBillDAO.findVO(billId);
		
		if (null == inBillBean)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误，请确认");

			return mapping.findForward("querySelfInBill");
		}
				
		request.setAttribute("bean", inBillBean);
		
		return mapping.findForward("drawPayment5");
		
	}
	
	/**
	 * 预收拆分 (单个拆分)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward drawPayment5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {		
		try
		{
			//
			addApply5(request);

			request.setAttribute(KeyConstant.MESSAGE, "操作成功");
		} 
		catch (MYException e) 
		{
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"操作失败" + e.getErrorContent());
		}
		
		return mapping.findForward("querySelfInBill");
	}
	
	/**
	 * 
	 * @param request
	 * @throws MYException
	 */
	private void addApply5(HttpServletRequest request) throws MYException 
	{
		// 
		String billId = request.getParameter("id");

		String paymentId = request.getParameter("paymentId");
		
		//
		String customerId = request.getParameter("customerId");
		
		//
		String oriCustomerId = request.getParameter("oriCustomerId");
		
		String oriStafferId = request.getParameter("oriStafferId");
		
		String description = request.getParameter("description");

		double transMoney = MathTools.parseDouble(request.getParameter("transMoney"));
		
		User user = Helper.getUser(request);		
		
		InBillBean bill = inBillDAO.find(billId);

		if (bill == null) {
			throw new MYException("数据错误，请确认");
		}

		if (bill.getStatus() != FinanceConstant.INBILL_STATUS_NOREF) {
			throw new MYException("不是预收的收款单,请确认操作");
		}

		if (MathTools
				.compare(bill.getMoneys(), transMoney) < 0)
		{
			throw new MYException("预收金额小于转移金额,请确认操作");
		}
		
		StafferVSCustomerBean stafferVSCustomer = stafferVSCustomerDAO.findByUnique(customerId);
		
		if (null == stafferVSCustomer)
		{
			throw new MYException("转入的客户没有挂靠关系");
		}
		
		PaymentApplyBean apply = new PaymentApplyBean();

		apply.setType(FinanceConstant.PAYAPPLY_TYPE_TRANSPAYMENT);
		apply.setCustomerId(customerId);
		apply.setLocationId(user.getLocationId());
		apply.setLogTime(TimeTools.now());
		apply.setPaymentId(paymentId);
		apply.setStafferId(stafferVSCustomer.getStafferId());
		apply.setDescription(description);
		apply.setOriCustomerId(oriCustomerId);
		apply.setOriStafferId(oriStafferId);
		apply.setOriBillId(billId);

		List<PaymentVSOutBean> vsList = new ArrayList<PaymentVSOutBean>();

		apply.setVsList(vsList);

		PaymentVSOutBean vs = new PaymentVSOutBean();

		vs.setLocationId(user.getLocationId());

		vs.setMoneys(transMoney);

		vs.setOutId("");

		vs.setPaymentId(paymentId);

		vs.setBillId("");
		
		vs.setStafferId(stafferVSCustomer.getStafferId());

		vsList.add(vs);
		// -- end --

		apply.setMoneys(transMoney);

		financeFacade.addPaymentApply5(user.getId(), apply);
	}
	
	/**
	 * queryForDrawPayment51
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryForDrawPayment51(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException 
	{
		User user = Helper.getUser(request);
		
		 //商务 - begin
        ActionForward error = checkAuthForEcommerce(request, user, mapping);
    	
    	if (null != error)
    	{
    		return error;
    	}
        // 商务 - end
		
		String billId = request.getParameter("billId");
		
		InBillVO inBillBean = inBillDAO.findVO(billId);
		
		if (null == inBillBean)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误，请确认");

			return mapping.findForward("querySelfInBill");
		}
				
		request.setAttribute("bean", inBillBean);
		
		return mapping.findForward("batchSplitInBill");
	}
	
	/**
	 * batchSplitInBill
	 *  批量拆分
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward batchSplitInBill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException 
	{
		User user = Helper.getUser(request);
		
        RequestDataStream rds = new RequestDataStream(request);
        
        boolean importError = false;
        
        List<BatchSplitInBillWrap> importItemList = new ArrayList<BatchSplitInBillWrap>(); 
        
        StringBuilder builder = new StringBuilder();
        
        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("batchSplitInBill");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("batchSplitInBill");
        }
        
        String billId = rds.getParameter("billId");

        InBillBean inbill = inBillDAO.find(billId);
        
        if (null == inbill)
        {
        	 request.setAttribute(KeyConstant.ERROR_MESSAGE, "收款单不存在");

             return mapping.findForward("batchSplitInBill");
        }
        
        if (inbill.getStatus() != FinanceConstant.INBILL_STATUS_NOREF)
        {
        	request.setAttribute(KeyConstant.ERROR_MESSAGE, "收款单不是预收");

            return mapping.findForward("batchSplitInBill");
        }
        
        int mtype = inbill.getMtype();
        
        ReaderFile reader = ReadeFileFactory.getXLSReader();
        
        double total = 0.0d;
        
        try
        {
            reader.readFile(rds.getUniqueInputStream());
            
            while (reader.hasNext())
            {
                String[] obj = fillObj((String[])reader.next());

                // 第一行忽略
                if (reader.getCurrentLineNumber() == 1)
                {
                    continue;
                }

                if (StringTools.isNullOrNone(obj[0]))
                {
                    continue;
                }
                
                int currentNumber = reader.getCurrentLineNumber();

                if (obj.length >= 2 )
                {
                	BatchSplitInBillWrap bean = new BatchSplitInBillWrap();
                    
                	// 客户
            		if ( !StringTools.isNullOrNone(obj[0]))
            		{
            			String customerName = obj[0].trim();
            			
            			CustomerBean customer = customerMainDAO.findByUnique(customerName);
            			
            			if (null == customer){
                			builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("客户不存在")
                            .append("<br>");
                        
                			importError = true;
            			}else{
            				if (customer.getId().equals(inbill.getCustomerId())){
                    			builder
                                .append("第[" + currentNumber + "]错误:")
                                .append("拆分与被拆分的客户不能是同一客户")
                                .append("<br>");
                            
                    			importError = true;
            				}else{
            					bean.setCustomerId(customer.getId());
            				}
            			}
            			
            			
            		}else{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("拆分客户不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}

            		// 是否勾款（是/否）
            		if ( !StringTools.isNullOrNone(obj[1]))
            		{
            			String name = obj[1].trim();
            			
            			// 勾款
            			if (name.equals("是")){
                			bean.setType(0);
            			}else if (name.equals("否")){
            				bean.setType(1);
            			}
            			else{
            				builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("是否勾款只能是[是或否]")
                            .append("<br>");
                        
                			importError = true;
            			}
            		}else{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("是否勾款为空")
                        .append("<br>");
                    
            			importError = true;
            		}

            		// 销售单号
            		if ( !StringTools.isNullOrNone(obj[2]))
            		{
            			String outId = obj[2].trim();
            			
            			// 确定是销售出库或结算单
            			if (bean.getType() == 0){
            				
            				OutBean out = outDAO.find(outId);
            				
            				if (null == out){
            					OutBalanceBean balance = outBalanceDAO.find(outId);
            					
            					if (null == balance)
            					{
            						builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("销售单不存在")
                                    .append("<br>");
                                
                        			importError = true;
            					}else
            					{
            						if (balance.getType() == OutConstant.OUTBALANCE_TYPE_SAIL)
            						{
            							if (balance.getCustomerId().equals(bean.getCustomerId())){
            								// 增加票款一致的检查  ---- begin-------
                        					if (balance.getPiStatus() == OutConstant.OUT_PAYINS_STATUS_APPROVE)
                        					{
                        						builder
                                                .append("第[" + currentNumber + "]错误:")
                                                .append("结算单开票审批中,不能勾款")
                                                .append("<br>");
                                            
                                    			importError = true;
                        					}else{
                        						if (!StringTools.isNullOrNone(balance.getPiDutyId()))
                            					{
                            						if (balance.getPiMtype() == PublicConstant.MANAGER_TYPE_MANAGER)
                            						{
                            							if (inbill.getMtype() != balance.getPiMtype())
                            							{
                            								builder
                                                            .append("第[" + currentNumber + "]错误:")
                                                            .append("此单已开票，且开票的纳税实体是管理，故只能使用管理账户款进行勾款")
                                                            .append("<br>");
                                                        
                                                			importError = true;
                            							}
                            						}else{
                            							if (!balance.getPiDutyId().equals(PublicConstant.DEFAULR_DUTY_ID))
                            							{
                            								if (!inbill.getDutyId().equals(balance.getPiDutyId()) && !inbill.getDutyId().equals(PublicConstant.DEFAULR_DUTY_ID))
                            								{
                            									builder
                                                                .append("第[" + currentNumber + "]错误:")
                                                                .append("开票为非永银文化纳税实体，故只能使用该纳税实体的款或永银文化账户款进行勾款")
                                                                .append("<br>");
                                                            
                                                    			importError = true;
                            								}
                            							}else{
                            								// do nothing
                            							}
                            						}
                            					}
                        					}
                        					// 增加票款一致的检查  ---- end-------
                        					
                        					bean.setOutId(balance.getOutId());
                    						bean.setBalanceId(outId);
                						}else{
                							builder
                                            .append("第[" + currentNumber + "]错误:")
                                            .append("结算单中的客户与拆分客户不是同一个客户")
                                            .append("<br>");
                                        
                                			importError = true;
                						}
            						}else{
            							builder
                                        .append("第[" + currentNumber + "]错误:")
                                        .append("委托代销时,不是结算单")
                                        .append("<br>");
                                    
                            			importError = true;
            						}
            					}
            				}else{
            					
            					if (out.getOutType() == OutConstant.OUTTYPE_OUT_COMMON && out.getPay() == OutConstant.PAY_NOT)
            					{
            						if (out.getCustomerId().equals(bean.getCustomerId()))
            						{
            							// 增加票款一致的检查  ---- begin-------
                    					if (out.getPiStatus() == OutConstant.OUT_PAYINS_STATUS_APPROVE)
                    					{
                    						builder
                                            .append("第[" + currentNumber + "]错误:")
                                            .append("销售单开票审批中,不能勾款")
                                            .append("<br>");
                                        
                                			importError = true;
                    					}else{
                    						if (!StringTools.isNullOrNone(out.getPiDutyId()))
                        					{
                        						if (out.getPiMtype() == PublicConstant.MANAGER_TYPE_MANAGER)
                        						{
                        							if (inbill.getMtype() != out.getPiMtype())
                        							{
                        								builder
                                                        .append("第[" + currentNumber + "]错误:")
                                                        .append("此单已开票，且开票的纳税实体是管理，故只能使用管理账户款进行勾款")
                                                        .append("<br>");
                                                    
                                            			importError = true;
                        							}
                        						}else{
                        							if (!out.getPiDutyId().equals(PublicConstant.DEFAULR_DUTY_ID))
                        							{
                        								if (!inbill.getDutyId().equals(out.getPiDutyId()) && !inbill.getDutyId().equals(PublicConstant.DEFAULR_DUTY_ID))
                        								{
                        									builder
                                                            .append("第[" + currentNumber + "]错误:")
                                                            .append("开票为非永银文化纳税实体，故只能使用该纳税实体的款或永银文化账户款进行勾款")
                                                            .append("<br>");
                                                        
                                                			importError = true;
                        								}
                        							}else{
                        								// do nothing
                        							}
                        						}
                        					}
                    					}
                    					// 增加票款一致的检查  ---- end-------
            							
            							bean.setOutId(outId);
            						}
            						else{
            							builder
                                        .append("第[" + currentNumber + "]错误:")
                                        .append("销售单中的客户与拆分客户不是同一个客户")
                                        .append("<br>");
                                    
                            			importError = true;
            						}
            					}
            					else{
            						builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("只能是销售出库且未付款的")
                                    .append("<br>");
                                
                        			importError = true;
            					}
            				}
            				
            			}else{
            				bean.setOutId("");
            			}
            		}else{
            			if (bean.getType() == 0){
            				builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("销售单不能为空")
                            .append("<br>");
                        
                			importError = true;
            			}
            		}
            		
            		// 金额 须大于0
            		if ( !StringTools.isNullOrNone(obj[3]))
            		{
            			double money = MathTools.parseDouble(obj[3].trim());
            			
            			if (money <= 0){
                			builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("金额须大于0")
                            .append("<br>");
                        
                			importError = true;
            			}else{
            				bean.setMoney(money);
            				
            				total += money;
            			}
            			
            		}else{
            			builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("金额不能为空")
                        .append("<br>");
                    
            			importError = true;
            		}
            		
                    importItemList.add(bean);
                }
                else
                {
                    builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("数据长度不足4格错误")
                        .append("<br>");
                    
                    importError = true;
                }
            }
        }catch (Exception e)
        {
            _logger.error(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("batchSplitInBill");
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (IOException e)
            {
                _logger.error(e, e);
            }
        }
        
        rds.close();

        if (inbill.getMoneys() < total)
        {
        	builder
            .append("错误:")
            .append("导入金额合计大于可拆分的预收")
            .append("<br>");
        
        	importError = true;
        }
        
        if (importError){
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ builder.toString());

            return mapping.findForward("batchSplitInBill");
        }
        
        try
        {
        	financeFacade.batchSplitInBill(user.getId(), billId, importItemList);
        	
        	request.setAttribute(KeyConstant.MESSAGE, "批量拆分成功");
        }
        catch(MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "批量拆分出错:"+ e.getErrorContent());
        }
        
        return mapping.findForward("querySelfCustomerInBill");
	}
	
	/**
	 * 付款申请(只有第一次认领才会调用)
	 * 
	 * @param request
	 * @param id
	 * @param customerId
	 * @param user
	 * @throws MYException
	 */
	private void addApply(HttpServletRequest request, String id,
			String customerId, User user,String description) throws MYException {
		
		PaymentApplyBean apply = new PaymentApplyBean();

		apply.setType(FinanceConstant.PAYAPPLY_TYPE_PAYMENT);
		apply.setCustomerId(customerId);
		apply.setLocationId(user.getLocationId());
		apply.setLogTime(TimeTools.now());
		apply.setPaymentId(id);
		apply.setStafferId(user.getStafferId());
		apply.setDescription(description);

		setCommerceOperator(request, user, apply);
		
		List<PaymentVSOutBean> vsList = new ArrayList<PaymentVSOutBean>();

		PaymentBean pay = paymentDAO.find(id);

		double total = 0.0d;

		String [] p_outIds = request.getParameterValues("p_outId");
		String [] p_outMoneys = request.getParameterValues("p_outMoney");
		
		// 生成付款单申请
		for (int i = 0; i < p_outIds.length; i++) {
			String outId = p_outIds[i]; //request.getParameter("outId" + i);

			String outMoney = p_outMoneys[i]; //request.getParameter("outMoney" + i);

			if (StringTools.isNullOrNone(outMoney)
					|| MathTools.parseDouble(outMoney.trim()) == 0.0d) {
				continue;
			}

			// add by fang 2012.6.13
			if ("客户预收".equals(outId)) {
				continue;
			}

			PaymentVSOutBean vs = new PaymentVSOutBean();

			vs.setLocationId(user.getLocationId());

			vs.setMoneys(MathTools.parseDouble(outMoney.trim()));

			// modify by fang 2012.6.13 注释掉
			// if ("客户预收".equals(outId))
			// {
			// vs.setOutId("");
			// }
			// else
			// {
			OutBean out = outDAO.find(outId);

			if (out != null) {
				
	        	// 正在对账
	        	if (out.getFeedBackCheck() == 1)
	        	{
	        		throw new MYException("此销售单[%s]正在对账，不允许勾款:", outId);
	        	}
				
				vs.setOutId(outId);
			} else {
				// 关联委托清单
				OutBalanceBean outBal = outBalanceDAO.find(outId);

				if (outBal == null) {
					throw new MYException("关联的单据不存在:" + outId);
				}

				OutBean out1 = outDAO.find(outBal.getOutId());
				
	        	// 正在对账
	        	if (out1.getFeedBackCheck() == 1)
	        	{
	        		throw new MYException("此销售单[%s]正在对账，不允许勾款:", out1.getFullId());
	        	}
				
				vs.setOutBalanceId(outId);

				vs.setOutId(outBal.getOutId());
			}
			// }

			vs.setPaymentId(id);

			vs.setStafferId(user.getStafferId());

			vsList.add(vs);

			total += vs.getMoneys();
		}

		apply.setVsList(vsList);

		// 没有全部使用,增加预收(这里保证全部使用) -- mofify by fang 2012.6.13 注释
		/*
		 * if (total < pay.getMoney()) { PaymentVSOutBean vs = new
		 * PaymentVSOutBean();
		 * 
		 * vs.setLocationId(user.getLocationId());
		 * 
		 * vs.setMoneys(pay.getMoney() - total);
		 * 
		 * vs.setOutId("");
		 * 
		 * vs.setPaymentId(id);
		 * 
		 * vs.setStafferId(user.getStafferId());
		 * 
		 * vsList.add(vs); }
		 */

		// 不管有没有关联销售单，这里都生成一条没有关联销售单的数据，即setOutId("")，且仅一条 -- begin -- add by
		// fang 2012.6.13
		PaymentVSOutBean vs = new PaymentVSOutBean();

		vs.setLocationId(user.getLocationId());

		vs.setMoneys(pay.getMoney() - total);

		vs.setOutId("");

		vs.setPaymentId(id);

		vs.setStafferId(user.getStafferId());

		vsList.add(vs);
		// -- end --

		apply.setMoneys(pay.getMoney());

		financeFacade.addPaymentApply(user.getId(), apply);
	}

	/**
	 * queryForDrawPayment6 
	 *  客户预收勾应收(多笔预收勾多笔应收，且区分管理与普通)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryForDrawPayment6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException 
	{		
		String customerId = request.getParameter("customerId");
		
		User user = Helper.getUser(request);
		
		 //商务 - begin
        ActionForward error = checkAuthForEcommerce(request, user, mapping);
    	
    	if (null != error)
    	{
    		return error;
    	}
        // 商务 - end
		
		// 查询客户的预收总额和及普通、管理总额 
		List<PrePaymentWrap> prePaymentList = inBillDAO.querySelfCustomerInbill(user.getStafferId(), customerId);
		
		if (ListTools.isEmptyOrNull(prePaymentList))
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有可勾款的预收");
			
			return mapping.findForward("querySelfCustomerInBill");
		}
		
		request.setAttribute("prePayment", prePaymentList.get(0));
		
		return mapping.findForward("drawPayment6");
		
	}
	
	/**
	 * drawPayment6
	 *  客户多个收款勾多个销售
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward drawPayment6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {		
		try
		{
			addApply6(request);

			request.setAttribute(KeyConstant.MESSAGE, "操作成功");
		} 
		catch (MYException e) 
		{
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"操作失败" + e.getErrorContent());
		}
		
		return mapping.findForward("querySelfCustomerInBill");
	}
	
	/**
	 * addApply6
	 * @param request
	 * @throws MYException
	 */
	private void addApply6(HttpServletRequest request) throws MYException 
	{
		User user = Helper.getUser(request);
		
		String customerId = request.getParameter("customerId");
		
		String description = request.getParameter("description");
		
		PaymentApplyBean apply = new PaymentApplyBean();

		// 类型 与销售单勾款 一样
		apply.setType(FinanceConstant.PAYAPPLY_TYPE_TEMP);
		apply.setCustomerId(customerId);
		apply.setLocationId(user.getLocationId());
		apply.setStafferId(user.getStafferId());
		apply.setDescription(description);

		setCommerceOperator(request, user, apply);
		
		List<PaymentVSOutBean> vsList = new ArrayList<PaymentVSOutBean>();

		String [] p_outIds = request.getParameterValues("p_outId");
		String [] p_outMoneys = request.getParameterValues("p_outMoney");
		
		// 生成付款单申请
		for (int i = 0; i < p_outIds.length; i++) {
			String outId = p_outIds[i]; //request.getParameter("outId" + i);

			String outMoney = p_outMoneys[i]; //request.getParameter("outMoney" + i);

			if (StringTools.isNullOrNone(outMoney)
					|| MathTools.parseDouble(outMoney.trim()) == 0.0d) {
				continue;
			}

			PaymentVSOutBean vs = new PaymentVSOutBean();

			vs.setLocationId(user.getLocationId());

			vs.setMoneys(MathTools.parseDouble(outMoney.trim()));

			OutBean out = outDAO.find(outId);

			if (out != null) {
				
	        	// 正在对账
	        	if (out.getFeedBackCheck() == 1)
	        	{
	        		throw new MYException("此销售单[%s]正在对账，不允许勾款:", outId);
	        	}
				
				vs.setOutId(outId);
			} else {
				// 关联委托清单
				OutBalanceBean outBal = outBalanceDAO.find(outId);

				if (outBal == null) {
					throw new MYException("关联的单据不存在:" + outId);
				}

				OutBean out1 = outDAO.find(outBal.getOutId());
				
	        	// 正在对账
	        	if (out1.getFeedBackCheck() == 1)
	        	{
	        		throw new MYException("此销售单[%s]正在对账，不允许勾款:", out1.getFullId());
	        	}
				
				vs.setOutBalanceId(outId);

				vs.setOutId(outBal.getOutId());
			}

			vs.setStafferId(user.getStafferId());

			vsList.add(vs);
		}

		apply.setVsList(vsList);

		financeFacade.addPaymentApply6(user.getId(), apply);
	}
	
	/**
	 * 设置商务模式下经办人
	 * @param request
	 * @param user
	 * @param apply
	 * @throws MYException
	 */
	private void setCommerceOperator(HttpServletRequest request, User user,
			PaymentApplyBean apply) throws MYException
	{
		//商务 - begin        
        User g_srcUser = (User)request.getSession().getAttribute("g_srcUser");
        
        String elogin = (String)request.getSession().getAttribute("g_elogin");
        
        String g_loginType = (String)request.getSession().getAttribute("g_loginType");
        
        if (!StringTools.isNullOrNone(elogin) && null == g_srcUser)
        {
            throw new MYException("登陆异常,请重新登陆");
        }
        
        // 当前切换用户登陆的且为商务登陆的，记录经办人
        if (!StringTools.isNullOrNone(elogin) && null != g_srcUser && g_loginType.equals("1"))
        {
        	apply.setOperator(g_srcUser.getStafferId());
        	apply.setOperatorName(g_srcUser.getStafferName());
        }
        else
        {
        	apply.setOperator(user.getStafferId());
        	apply.setOperatorName(user.getStafferName());	
        }
        // 商务 - end
	}

	/**
	 * 付款申请
	 * 
	 * @param request
	 * @param id
	 * @param customerId
	 * @param user
	 * @throws MYException
	 */
	private void addApply2(HttpServletRequest request, String customerId,
			User user,String description) throws MYException {
		
		PaymentApplyBean apply = new PaymentApplyBean();

		apply.setType(FinanceConstant.PAYAPPLY_TYPE_TEMP);
		apply.setCustomerId(customerId);
		apply.setLocationId(user.getLocationId());
		apply.setLogTime(TimeTools.now());
		apply.setStafferId(user.getStafferId());
		apply.setBadMoney(MathTools.parseDouble(request
				.getParameter("badMoney")));
		apply.setDescription(description);

        setCommerceOperator(request, user, apply);
		
		List<PaymentVSOutBean> vsList = new ArrayList<PaymentVSOutBean>();

		double total = 0.0d;

		String[] billIds = request.getParameterValues("billId");

		String outId = request.getParameter("outId");

		double outTotal = MathTools.parseDouble(request.getParameter("total"));

		for (String billId : billIds) {
			InBillBean bill = inBillDAO.find(billId);

			PaymentVSOutBean vs = new PaymentVSOutBean();

			vs.setLocationId(user.getLocationId());

			vs.setMoneys(bill.getMoneys());

			OutBean out = outDAO.find(outId);

			if (out != null) {
				
	        	// 正在对账
	        	if (out.getFeedBackCheck() == 1)
	        	{
	        		throw new MYException("此销售单[%s]正在对账，不允许勾款:", outId);
	        	}
	        	
				vs.setOutId(outId);
			} else {
				// 关联委托清单
				OutBalanceBean outBal = outBalanceDAO.find(outId);

				if (outBal == null) {
					throw new MYException("关联的单据不存在:" + outId);
				}

				OutBean out1 = outDAO.find(outBal.getOutId());
				
	        	// 正在对账
	        	if (out1.getFeedBackCheck() == 1)
	        	{
	        		throw new MYException("此销售单[%s]正在对账，不允许勾款:", out1.getFullId());
	        	}
				
				vs.setOutBalanceId(outId);

				vs.setOutId(outBal.getOutId());
			}

			vs.setBillId(billId);

			vs.setPaymentId(bill.getPaymentId());

			vs.setStafferId(user.getStafferId());

			vsList.add(vs);

			total += vs.getMoneys();

			if (total >= outTotal) {
				break;
			}
		}

		apply.setVsList(vsList);

		// 没有申请付款
		if (vsList.size() == 0) {
			return;
		}

		apply.setMoneys(total);

		financeFacade.addPaymentApply(user.getId(), apply);
	}

	/**
	 * 预收转费用
	 * 
	 * @param request
	 * @param id
	 * @param customerId
	 * @param user
	 * @throws MYException
	 */
	private void addApply3(HttpServletRequest request, String billId, User user)
			throws MYException {
		String reason = request.getParameter("reason");

		// 转移的金额
		String refMoney = request.getParameter("refMoney");

		InBillBean bill = inBillDAO.find(billId);

		if (bill == null) {
			throw new MYException("数据错误,请确认操作");
		}

		if (bill.getStatus() != FinanceConstant.INBILL_STATUS_NOREF) {
			throw new MYException("不是预收的收款单,请确认操作");
		}

		// 不是全部转费用的时候就先拆分
		if (MathTools
				.compare(bill.getMoneys(), MathTools.parseDouble(refMoney)) != 0) {
			// 拆分哦
			billId = billManager.splitInBillBean(user, billId,
					MathTools.parseDouble(refMoney));

			bill = inBillDAO.find(billId);
		}

		// 看看是否存在退款申请
		PaymentApplyBean apply = new PaymentApplyBean();

		apply.setType(FinanceConstant.PAYAPPLY_TYPE_CHANGEFEE);
		apply.setCustomerId(bill.getCustomerId());
		apply.setLocationId(user.getLocationId());
		apply.setLogTime(TimeTools.now());
		apply.setStafferId(user.getStafferId());
		apply.setDescription(reason);

		List<PaymentVSOutBean> vsList = new ArrayList<PaymentVSOutBean>();

		PaymentVSOutBean vs = new PaymentVSOutBean();

		vs.setBillId(billId);
		vs.setMoneys(bill.getMoneys());
		vs.setStafferId(user.getStafferId());
		vs.setLocationId(user.getLocationId());

		vsList.add(vs);

		apply.setVsList(vsList);

		apply.setMoneys(bill.getMoneys());

		financeFacade.addPaymentApply(user.getId(), apply);
	}

	/**
	 * 业务员退领回款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward dropPayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		AjaxResult ajax = new AjaxResult();

		try {
			String id = request.getParameter("id");

			User user = Helper.getUser(request);

			financeFacade.dropPaymentBean(user.getId(), id);

			ajax.setSuccess("成功操作");
		} catch (MYException e) {
			_logger.warn(e, e);

			ajax.setError("操作失败:" + e.getMessage());
		}

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * findBank
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward findBank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		String id = request.getParameter("id");

		BankBean bean = bankDAO.findVO(id);

		if (bean == null) {
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据异常,请重新操作");

			return mapping.findForward("queryBank");
		}

		request.setAttribute("bean", bean);

		String update = request.getParameter("update");

		if ("1".equals(update)) {
			List<DutyBean> dutyList = dutyDAO.listEntityBeans();

			request.setAttribute("dutyList", dutyList);

			return mapping.findForward("updateBank");
		}

		return mapping.findForward("detailBank");
	}

	/**
	 * preForRefBill
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward preForRefBill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		CommonTools.saveParamers(request);

		User user = Helper.getUser(request);

        //商务 - begin
        ActionForward error = checkAuthForEcommerce(request, user, mapping);
    	
    	if (null != error)
    	{
    		return error;
    	}
        // 商务 - end
		
		String outId = request.getParameter("outId");

		String customerId = request.getParameter("customerId");

		OutBean out = outDAO.find(outId);

		if (out == null) {
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据不完备");

			return mapping.findForward("error");
		}

    	// 正在对账
    	if (out.getFeedBackCheck() == 1)
    	{
    		request.setAttribute(KeyConstant.ERROR_MESSAGE, "此销售单正在对账，不允许勾款");

            return mapping.findForward("error");
    	}
		
		if (out.getStatus() == OutConstant.STATUS_SAVE
				|| out.getStatus() == OutConstant.STATUS_REJECT) {
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "保存和驳回的单据不能勾款");

			return mapping.findForward("error");
		}
		
		if (out.getPiStatus() == OutConstant.OUT_PAYINS_STATUS_APPROVE)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "此销售单开票审批中，不允许勾款");

            return mapping.findForward("error");
		}

		double lastMoney = outManager.outNeedPayMoney(user, outId);

		if (lastMoney == 0.0) {
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "已经全部关联预付");

			return mapping.findForward("error");
		}

		// 退货实物价值-返还金额
		request.setAttribute("lastMoney", lastMoney);

		request.setAttribute("out", out);

		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		condtion.addCondition("InBillBean.ownerId", "=", user.getStafferId());

		condtion.addCondition("InBillBean.customerId", "=", customerId);

		condtion.addIntCondition("InBillBean.status", "=", FinanceConstant.INBILL_STATUS_NOREF);
		
		condtion.addCondition(" and InBillBean.moneys >= 0.01");
		
		// 检查要勾款单子是否已开过票
		if (out.getPiMtype() == PublicConstant.MANAGER_TYPE_MANAGER)
			condtion.addIntCondition("InBillBean.mtype", "=", PublicConstant.MANAGER_TYPE_MANAGER);
		else if (!StringTools.isNullOrNone(out.getPiDutyId()))
		{
			if (!out.getPiDutyId().equals(PublicConstant.DEFAULR_DUTY_ID))
			{
				condtion.addCondition(" and InBillBean.dutyId in ('90201008080000000001','"+ out.getPiDutyId() + "')");	
			}else{
				// do nothing
			}
		}

		condtion.addCondition("order by InBillBean.logTime desc");

		List<InBillVO> billList = inBillDAO.queryEntityVOsByCondition(condtion);

		if (OATools.getManagerFlag()
				&& out.getOutTime().compareTo("2012-01-01") >= 0 && false) {
			for (Iterator iterator = billList.iterator(); iterator.hasNext();) {
				InBillVO inBillVO = (InBillVO) iterator.next();

				if (!inBillVO.getDutyId().equals(out.getDutyId())) {
					iterator.remove();
				}
			}
		}

		request.setAttribute("billList", billList);

		return mapping.findForward("refBill");
	}

	/**
	 * preForRefPayment
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward preForAddPayment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		List<BankBean> bankList = bankDAO.listEntityBeans();

		request.setAttribute("bankList", bankList);

		return mapping.findForward("addPayment");
	}

	/**
	 * 委托代销勾款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward preForRefBillForOutBalance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		CommonTools.saveParamers(request);

		User user = Helper.getUser(request);

		String outBalanceId = request.getParameter("outBalanceId");

		String customerId = request.getParameter("customerId");

		OutBalanceBean out = outBalanceDAO.find(outBalanceId);

		if (out == null) {
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据不完备");

			return mapping.findForward("error");
		}

		OutBean outBean  = outDAO.find(out.getOutId());
		
    	// 正在对账
    	if (outBean.getFeedBackCheck() == 1)
    	{
    		request.setAttribute(KeyConstant.ERROR_MESSAGE, "此销售单正在对账，不允许勾款");

            return mapping.findForward("error");
    	}
    	
    	if (out.getPiStatus() == OutConstant.OUT_PAYINS_STATUS_APPROVE)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "此结算单开票审批中，不允许勾款");

            return mapping.findForward("error");
		}
		
		if (out.getTotal() - out.getPayMoney() == 0.0) {
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "已经全部关联预付");

			return mapping.findForward("error");
		}

		// 减去结算单退货部分
    	double refTotal = outBalanceDAO.sumByOutBalanceId(out.getId());
    	
		request.setAttribute("out", out);

		request.setAttribute("outId", outBalanceId);

		request.setAttribute("lastMoney", out.getTotal() - out.getPayMoney() - refTotal);

		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		condtion.addCondition("InBillBean.ownerId", "=", user.getStafferId());

		condtion.addCondition("InBillBean.customerId", "=", customerId);

		condtion.addIntCondition("InBillBean.status", "=", FinanceConstant.INBILL_STATUS_NOREF);
		
		condtion.addCondition(" and InBillBean.moneys >= 0.01");
		
		// 检查要勾款单子是否已开过票
		if (out.getPiMtype() == PublicConstant.MANAGER_TYPE_MANAGER)
			condtion.addIntCondition("InBillBean.mtype", "=", PublicConstant.MANAGER_TYPE_MANAGER);
		else if (!StringTools.isNullOrNone(out.getPiDutyId()))
		{
			if (!out.getPiDutyId().equals(PublicConstant.DEFAULR_DUTY_ID))
			{
				condtion.addCondition(" and InBillBean.dutyId in ('90201008080000000001','"+ out.getPiDutyId() + "')");	
			}else{
				// do nothing
			}
		}

		condtion.addCondition("order by InBillBean.logTime desc");

		List<InBillVO> billList = inBillDAO.queryEntityVOsByCondition(condtion);

		request.setAttribute("billList", billList);

		return mapping.findForward("refBill");
	}

	/**
	 * 导入
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward uploadPayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		User user = Helper.getUser(request);

		if (!userManager.containAuth(user, AuthConstant.PAYMENT_OPR)) {
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有权限");

			return mapping.findForward("uploadPayment");
		}

		RequestDataStream rds = new RequestDataStream(request);

		try {
			rds.parser();
		} catch (Exception e1) {
			_logger.error(e1, e1);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

			return mapping.findForward("uploadPayment");
		}

		StringBuilder builder = new StringBuilder();

		String bankId = rds.getParameter("bankId");

		String batchId = SequenceTools.getSequence();

		List<PaymentBean> payList = new LinkedList<PaymentBean>();

		boolean allSuccess = true;

		if (rds.haveStream()) {
			try {
				ReaderFile reader = ReadeFileFactory.getXLSReader();

				reader.readFile(rds.getUniqueInputStream());

				while (reader.hasNext()) {
					String[] obj = (String[]) reader.next();

					// 第一行忽略
					if (reader.getCurrentLineNumber() == 1) {
						continue;
					}

					// 序号 回款来源 回款金额 回款日期 备注
					int currentNumber = reader.getCurrentLineNumber();

					if (obj.length >= 9) {
						try {
							createPayment(user, bankId, obj, batchId, payList);
						} catch (MYException e) {
							builder.append("第[" + currentNumber + "]错误:")
									.append(e.getErrorContent()).append("<br>");

							allSuccess = false;

							break;
						}
					} else {
						builder.append("第[" + currentNumber + "]错误:")
								.append("数据长度不足9格,备注可以为空").append("<br>");

						allSuccess = false;

						break;
					}
				}

				if (allSuccess && payList.size() > 0) {
					financeFacade.addPaymentBeanList(user.getId(), payList);
				}
			} catch (Exception e) {
				_logger.error(e, e);

				request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入失败");

				return mapping.findForward("uploadPayment");
			}
		}

		rds.close();

		StringBuilder result = new StringBuilder();

		if (allSuccess) {
			result.append("导入成功:").append(payList.size()).append("条<br>");
		} else {
			result.append(builder.toString());
		}

		request.setAttribute(KeyConstant.MESSAGE, result.toString());

		return mapping.findForward("uploadPayment");
	}

	/**
	 * createPayment
	 * 
	 * @param user
	 * @param bankId
	 * @param obj
	 * @param batchId
	 * @param payList
	 * @return
	 * @throws MYException
	 */
	private boolean createPayment(User user, String bankId, String[] obj,
			String batchId, List<PaymentBean> payList) throws MYException {
		PaymentBean bean = new PaymentBean();

		boolean allEmpty = true;

		for (int i = 0; i < obj.length; i++) {
			if (!StringTools.isNullOrNone(obj[i])) {
				allEmpty = false;
				break;
			}
		}

		if (allEmpty) {
			return true;
		}

		if (StringTools.isNullOrNone(obj[0])) {
			throw new MYException("缺少唯一标识");
		}

		if (StringTools.isNullOrNone(obj[1])) {
			throw new MYException("缺少类型");
		}

		if (StringTools.isNullOrNone(obj[2])) {
			throw new MYException("缺少回款来源");
		}
		
		if (StringTools.isNullOrNone(obj[3])) {
			throw new MYException("缺少回款金额");
		}

		if ("对私".equals(obj[1])) {
			bean.setType(FinanceConstant.PAYMENT_PAY_SELF);
		} else {
			bean.setType(FinanceConstant.PAYMENT_PAY_PUBLIC);
		}

		bean.setRefId(obj[0].trim());
		bean.setBankId(bankId);
		bean.setFromer(obj[2]);
		bean.setMoney(MathTools.parseDouble(obj[3]));
		bean.setHandling(MathTools.parseDouble(obj[4]));
		bean.setReceiveTime(obj[5]);
		bean.setBatchId(batchId);

		bean.setDescription(obj[6]);
		
		if (!StringTools.isNullOrNone(obj[7])) {
			bean.setFromerNo(obj[7]);
		}
		
		if (!StringTools.isNullOrNone(obj[8])) {
			
			if (obj[8].trim().equals("外部"))
			{
				bean.setCtype(0);	
			}else if (obj[8].trim().equals("内部"))
			{
				bean.setCtype(1);
			}else{
				throw new MYException("回款资金性质只能是[外部、内部]");
			}
			
		}else{
			throw new MYException("缺少回款资金性质(外部、内部)");
		}

		PaymentBean oldPay = paymentDAO.findByUnique(bean.getBankId(),
				bean.getRefId());

		if (oldPay != null && oldPay.getMoney() != bean.getMoney()) {
			throw new MYException("导入金额出现错误,标识[%s]已经存在,且金额不一致", bean.getRefId());
		}

		// 插入新的值
		if (oldPay == null) {
			payList.add(bean);
		}

		return true;
	}

	/**
	 * exportPayment
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward exportPayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException {
		OutputStream out = null;

		// String depotartId = request.getParameter("depotartId");

		String filenName = "Payment_" + TimeTools.now("MMddHHmmss") + ".csv";

		reponse.setContentType("application/x-dbf");

		reponse.setHeader("Content-Disposition", "attachment; filename="
				+ filenName);

		WriteFile write = null;

		try {
			out = reponse.getOutputStream();

			write = WriteFileFactory.getMyTXTWriter();

			write.openFile(out);

			write.writeLine("日期,系统标识,导入标识,导入批次,帐户,类型,状态,核对状态,回款凭证号,认款凭证号,删除凭证号,认领时间,认领人,回款来源,绑定客户,客户编码,回款金额,手续费,回款时间,备注");

			ConditionParse condtion = JSONPageSeparateTools.getCondition(
					request, QUERYPAYMENT);

			PageSeparate page = new PageSeparate(
					JSONPageSeparateTools
							.getPageSeparate(request, QUERYPAYMENT));

			page.reset2(page.getRowCount(), 1000);

			while (page.nextPage()) {
				List<PaymentVO> voList = this.paymentDAO
						.queryEntityVOsByCondition(condtion, page);

				for (PaymentVO each : voList) {
					String typeName = DefinedCommon.getValue("paymentType",
							each.getType());
					String statusName = DefinedCommon.getValue("paymentStatus",
							each.getStatus());
					String checkStatusName = DefinedCommon.getValue(
							"paymentChechStatus", each.getCheckStatus());

					write.writeLine("["
							+ each.getLogTime()
							+ "]"
							+ ','
							+ each.getId()
							+ ','
							+ "M"
							+ each.getRefId()
							+ ','
							+ "M"
							+ each.getBatchId()
							+ ','
							+ each.getBankName()
							+ ','
							+ typeName
							+ ','
							+ statusName
							+ ','
							+ checkStatusName
							+ ','
							+ StringTools.getExportString(each.getChecks1())
							+ ','
							+ StringTools.getExportString(each.getChecks2())
							+ ','
							+ StringTools.getExportString(each.getChecks3())
							+ ','
							+ StringTools.getExportString(each.getUpdateTime())
							+ ','
							+ StringTools.getExportString(each.getStafferName())
							+ ','
							+ StringTools.getExportString(each.getFromer())
							+ ','
							+ StringTools.getExportString(each
									.getCustomerName())
							+ ","
							+ StringTools.getExportString(each
									.getCustoemrCode())
							+ ","
							+ MathTools.formatNum(each.getBakmoney())
							+ ','
							+ MathTools.formatNum(each.getHandling())
							+ ','
							+ each.getReceiveTime()
							+ ','
							+ StringTools.getExportString(each.getDescription()));
				}
			}

			write.close();

		} catch (Throwable e) {
			_logger.error(e, e);

			return null;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e1) {
				}
			}

			if (write != null) {

				try {
					write.close();
				} catch (IOException e1) {
				}
			}
		}

		return null;
	}

	/**
	 * findPayment(领取回款和回款详细)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward findPayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		String id = request.getParameter("id");
		String mode = request.getParameter("mode");

		PaymentVO bean = paymentDAO.findVO(id);

		if (bean == null) {
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据异常,请重新操作");

			return mapping.findForward("error");
		}

		double hasUsed = inBillDAO.sumByPaymentId(id);

		request.setAttribute("bean", bean);

		request.setAttribute("hasUsed", hasUsed);

		if ("2".equals(mode)) {
			List<InBillVO> inBillList = inBillDAO.queryEntityVOsByFK(id,
					AnoConstant.FK_FIRST);

			List<OutBillVO> outBillList = outBillDAO.queryEntityVOsByFK(id);

			request.setAttribute("inBillList", inBillList);

			request.setAttribute("outBillList", outBillList);

			// 关联的
			List<FinanceBean> financeBeanList = financeDAO
					.queryRefFinanceItemByRefId(id);

			request.setAttribute("financeBeanList", financeBeanList);

			// 明细
			return mapping.findForward("detailPayment");
		}

		List<PaymentApplyBean> queryEntityBeansByFK = paymentApplyDAO
				.queryEntityBeansByFK(id);

		for (PaymentApplyBean paymentApplyBean : queryEntityBeansByFK) {
			if (paymentApplyBean.getStatus() == FinanceConstant.PAYAPPLY_STATUS_INIT) {
				request.setAttribute(KeyConstant.ERROR_MESSAGE,
						"有付款申请没有处理结束,请重新操作");

				return mapping.findForward("querySelfPayment");
			}
		}

		// 2012以后的单据认领
		if ("1".equals(mode)) {
			return mapping.findForward("drawPayment2");
		}
		
		// 财务认领
		if ("3".equals(mode)) {
			return mapping.findForward("drawTransfer");
		}
		
		// 供应商款认领
		if ("4".equals(mode)) {
			return mapping.findForward("drawProvider");
		}

		return mapping.findForward("drawPayment");
	}

	/**
	 * findPaymentApply
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward findPaymentApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		String id = request.getParameter("id");

		String update = request.getParameter("update");

		PaymentApplyVO bean = paymentApplyDAO.findVO(id);
		
		if(null != bean.getPaymentId() || bean.getPaymentId().trim().length() > 0)
		{
			PaymentBean pay = paymentDAO.find(bean.getPaymentId());
		
			if(null != pay)
			{
				bean.setDescription(bean.getDescription() + " " + pay.getDescription());
			}
		}

		if (bean == null) {
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据异常,请重新操作");

			return mapping.findForward("error");
		}

		request.setAttribute("bean", bean);

		List<FlowLogBean> loglist = flowLogDAO.queryEntityBeansByFK(id);

		request.setAttribute("loglist", loglist);

		List<PaymentVSOutBean> vsList = paymentVSOutDAO
				.queryEntityBeansByFK(id);

		List<PaymentVSOutBean> vsList1 = new ArrayList<PaymentVSOutBean>();

		for (PaymentVSOutBean paymentVSOutBean : vsList) {
			if (StringTools.isNullOrNone(paymentVSOutBean.getOutId())) {
				paymentVSOutBean.setOutId("客户预收");
			}

			// add by fang delete money=0的数据
			if (paymentVSOutBean.getMoneys() != 0.0d) {
				vsList1.add(paymentVSOutBean);
			}
		}

		request.setAttribute("vsList", vsList1);

		if (bean.getStatus() != FinanceConstant.PAYAPPLY_STATUS_INIT
				&& bean.getStatus() != FinanceConstant.PAYAPPLY_STATUS_CHECK) {
			return mapping.findForward("detailPaymentApply");
		}

		if ("1".equals(update)) {
			return mapping.findForward("handlePaymentApply");
		}

		return mapping.findForward("detailPaymentApply");
	}

	/**
	 * passPaymentApply
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward passPaymentApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		String id = request.getParameter("id");

		String reason = request.getParameter("reason");
		
		String description = request.getParameter("description");

		String returnUrl = "queryPaymentApply";

		try {
			User user = Helper.getUser(request);
			PaymentApplyBean paymentApplyBean = paymentApplyDAO.find(id);
//			if (paymentApplyBean.getStatus() == FinanceConstant.PAYAPPLY_STATUS_INIT) {
				financeFacade.passPaymentApply(user.getId(), id, reason,description);
//			} else {
//				financeFacade.passCheck(user.getId(), id, reason,description);
				if (paymentApplyBean.getStatus() != FinanceConstant.PAYAPPLY_STATUS_INIT)
				{
					returnUrl = "queryPaymentApplyCheck";
				}
//			}

			request.setAttribute(KeyConstant.MESSAGE, "成功操作");
		} catch (MYException e) {
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"操作失败:" + e.getMessage());
		}

		CommonTools.removeParamers(request);

		return mapping.findForward(returnUrl);
	}

	/**
	 * rejectPaymentApply
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward rejectPaymentApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		String id = request.getParameter("id");

		String reason = request.getParameter("reason");

		try {
			User user = Helper.getUser(request);

			financeFacade.rejectPaymentApply(user.getId(), id, reason);

			request.setAttribute(KeyConstant.MESSAGE, "成功操作");
		} catch (MYException e) {
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"操作失败:" + e.getMessage());
		}

		CommonTools.removeParamers(request);

		return mapping.findForward("queryPaymentApply");
	}

	/**
	 * exportCurrentBank 导出帐户当前异动明细(201103的结余+收款单+为认领的回款-付款单)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward exportCurrentBank(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse reponse) throws ServletException {
		OutputStream out = null;

		// 银行ID
		String bankId = request.getParameter("bankId");

		String filenName = "BANK_" + TimeTools.now("MMddHHmmss") + ".csv";

		reponse.setContentType("application/x-dbf");

		reponse.setHeader("Content-Disposition", "attachment; filename="
				+ filenName);

		WriteFile write = null;

		try {
			BankBean bank = bankDAO.find(bankId);

			if (bank == null) {
				return ActionTools.toError("银行不存在,请确认操作", mapping, request);
			}

			out = reponse.getOutputStream();

			write = WriteFileFactory.getMyTXTWriter();

			write.openFile(out);

			// 获取统计
			bankManager.wirteBankStat(write, bank);

			write.close();

		} catch (Throwable e) {
			_logger.error(e, e);

			return null;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e1) {
				}
			}

			if (write != null) {

				try {
					write.close();
				} catch (IOException e1) {
				}
			}
		}

		return null;
	}

	/**
	 * exportStatBank
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public ActionForward exportStatBank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bankId = request.getParameter("bankId");

		String timekey = request.getParameter("timekey");

		BankBean bank = bankDAO.find(bankId);

		if (bank == null) {
			return ActionTools.toError("银行不存在,请确认操作", mapping, request);
		}

		Date date = TimeTools.getDateByFormat(timekey, TimeTools.SHORT_FORMAT);

		String newKey = TimeTools.getStringByFormat(date, "yyyyMMdd");

		String path = Helper.getRootPath();

		String fileName = bank.getName() + "_统计_" + newKey + ".csv";

		path = path + "bank/" + newKey + "/" + fileName;

		File file = new File(path);

		OutputStream out = response.getOutputStream();

		response.setContentLength((int) file.length());

		response.setContentType("application/x-dbf");

		response.setHeader("Content-Disposition", "attachment; filename="
				+ StringTools.getStringBySet(fileName, "GBK", "ISO8859-1"));

		UtilStream us = new UtilStream(new FileInputStream(file), out);

		us.copyAndCloseStream();

		return null;
	}

    private ActionForward checkAuthForEcommerce(HttpServletRequest request, User user, ActionMapping mapping)
    {
        // 针对非商务模式下，业务员开单要有权限限制
        String elogin = (String)request.getSession().getAttribute("g_elogin");
        
        String g_loginType = (String)request.getSession().getAttribute("g_loginType");
        
        // elogin 为空，则表示非商务模式, elogin 1 表示是商务切换登陆
        if (StringTools.isNullOrNone(elogin) || (elogin.equals("1") && g_loginType.equals("2")))
        {
        	// 检查是否有权限
        	if (!containAuth(user, AuthConstant.DIRECT_SALE))
        	{
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "非商务模式下,没有权限操作");

                return mapping.findForward("error");
        	}
        }
		return null;
    }
	
    private boolean containAuth(User user, String authId)
    {
        if (StringTools.isNullOrNone(authId))
        {
            return true;
        }

        if (authId.equals(AuthConstant.PUNLIC_AUTH))
        {
            return true;
        }

        List<RoleAuthBean> authList = user.getAuth();

        for (RoleAuthBean roleAuthBean : authList)
        {
            if (roleAuthBean.getAuthId().equals(authId))
            {
                return true;
            }
        }

        return false;
    }
    
    private String[] fillObj(String[] obj)
    {
        String[] result = new String[10];

        for (int i = 0; i < result.length; i++ )
        {
            if (i < obj.length)
            {
                result[i] = obj[i];
            }
            else
            {
                result[i] = "";
            }
        }

        return result;
    }
    
    /**
     * drawTransfer
     * 财务认领,勾转账
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward drawTransfer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		try {
			String id = request.getParameter("id");

			String description = request.getParameter("description");
			
			User user = Helper.getUser(request);

			//商务 
	        ActionForward error = checkAuthForEcommerce(request, user, mapping);
	    	
	    	if (null != error)
	    	{
	    		return error;
	    	}
			
			// 绑定
	    	PaymentApplyBean apply = addTransferApply(request, id, user, description);
			
			financeFacade.addTransferApply(user.getId(), apply);

			request.setAttribute(KeyConstant.MESSAGE, "成功操作");
		} catch (MYException e) {
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"操作操作：" + e.getErrorContent());
		}

		return mapping.findForward("querySelfPayment");
	}
    
    private PaymentApplyBean addTransferApply(HttpServletRequest request, String id,
			User user,String description) throws MYException {
		
		PaymentApplyBean apply = new PaymentApplyBean();

		apply.setType(99);
		apply.setCustomerId("");
		apply.setLocationId(user.getLocationId());
		apply.setLogTime(TimeTools.now());
		apply.setPaymentId(id);
		apply.setStafferId(user.getStafferId());
		apply.setDescription(description);

		setCommerceOperator(request, user, apply);
		
		List<PaymentVSOutBean> vsList = new ArrayList<PaymentVSOutBean>();

		PaymentBean pay = paymentDAO.find(id);

		String billId = request.getParameter("billId");
		double outMoneys = MathTools.parseDouble(request.getParameter("outMoney"));
		
		// 生成付款单申请
		PaymentVSOutBean vs = new PaymentVSOutBean();

		vs.setLocationId(user.getLocationId());

		vs.setMoneys(outMoneys);

		// 转账付款单
		vs.setOutId(billId);

		vs.setPaymentId(id);

		vs.setStafferId(user.getStafferId());

		vsList.add(vs);

		apply.setMoneys(pay.getMoney());
		
		apply.setVsList(vsList);
		
		return apply;
	}
    
    /**
     * drawProvider
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward drawProvider(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		try {
			String id = request.getParameter("id");
			
			String customerId = request.getParameter("customerId");

			String description = request.getParameter("description");
			
			User user = Helper.getUser(request);

			PaymentApplyBean apply = addDrawProviderApply(request, id, customerId, user, description);
			
			financeFacade.addDrawProviderApply(user.getId(), apply);

			request.setAttribute(KeyConstant.MESSAGE, "成功操作");
		} catch (MYException e) {
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"操作操作：" + e.getErrorContent());
		}

		return mapping.findForward("querySelfPayment");
	}
    
    private PaymentApplyBean addDrawProviderApply(HttpServletRequest request, String id, String customerId,
			User user,String description) throws MYException {
		
		PaymentApplyBean apply = new PaymentApplyBean();

		apply.setType(99);
		apply.setCustomerId(customerId);
		apply.setLocationId(user.getLocationId());
		apply.setLogTime(TimeTools.now());
		apply.setPaymentId(id);
		apply.setStafferId(user.getStafferId());
		apply.setDescription(description);

		setCommerceOperator(request, user, apply);
		
		List<PaymentVSOutBean> vsList = new ArrayList<PaymentVSOutBean>();

		PaymentBean pay = paymentDAO.find(id);

		// 生成付款单申请
		PaymentVSOutBean vs = new PaymentVSOutBean();

		vs.setLocationId(user.getLocationId());

		vs.setMoneys(pay.getMoney());

		vs.setPaymentId(id);

		vs.setStafferId(user.getStafferId());

		vsList.add(vs);

		apply.setMoneys(pay.getMoney());
		
		apply.setVsList(vsList);
		
		return apply;
	}
    
    /**
     * refPurchaseBack
     * 供应商预收勾采购退货单
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward refPurchaseBack(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
    	User user = Helper.getUser(request);
    	
    	String customerId = request.getParameter("customerId");
    	
    	// checkBox selected = true
		String[] fullIds = request.getParameterValues("fullId");
		
		String ids = "";
		
		for (int i = 0; i < fullIds.length; i++) {
			
			String fullId  = fullIds[i];
			
			ids += fullId+"~";
		}

		try{
			financeFacade.refPurchaseBack(user.getId(), customerId, ids);
			
			request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        request.setAttribute("mode", 1);
        
        return mapping.findForward("querySelfCustomerInBill");
	}
    
	/**
	 * @return the financeFacade
	 */
	public FinanceFacade getFinanceFacade() {
		return financeFacade;
	}

	/**
	 * @param financeFacade
	 *            the financeFacade to set
	 */
	public void setFinanceFacade(FinanceFacade financeFacade) {
		this.financeFacade = financeFacade;
	}

	/**
	 * @return the bankDAO
	 */
	public BankDAO getBankDAO() {
		return bankDAO;
	}

	/**
	 * @param bankDAO
	 *            the bankDAO to set
	 */
	public void setBankDAO(BankDAO bankDAO) {
		this.bankDAO = bankDAO;
	}

	/**
	 * @return the dutyDAO
	 */
	public DutyDAO getDutyDAO() {
		return dutyDAO;
	}

	/**
	 * @param dutyDAO
	 *            the dutyDAO to set
	 */
	public void setDutyDAO(DutyDAO dutyDAO) {
		this.dutyDAO = dutyDAO;
	}

	/**
	 * @return the paymentDAO
	 */
	public PaymentDAO getPaymentDAO() {
		return paymentDAO;
	}

	/**
	 * @param paymentDAO
	 *            the paymentDAO to set
	 */
	public void setPaymentDAO(PaymentDAO paymentDAO) {
		this.paymentDAO = paymentDAO;
	}

	/**
	 * @return the userManager
	 */
	public UserManager getUserManager() {
		return userManager;
	}

	/**
	 * @param userManager
	 *            the userManager to set
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	/**
	 * @return the paymentApplyDAO
	 */
	public PaymentApplyDAO getPaymentApplyDAO() {
		return paymentApplyDAO;
	}

	/**
	 * @param paymentApplyDAO
	 *            the paymentApplyDAO to set
	 */
	public void setPaymentApplyDAO(PaymentApplyDAO paymentApplyDAO) {
		this.paymentApplyDAO = paymentApplyDAO;
	}

	/**
	 * @return the flowLogDAO
	 */
	public FlowLogDAO getFlowLogDAO() {
		return flowLogDAO;
	}

	/**
	 * @param flowLogDAO
	 *            the flowLogDAO to set
	 */
	public void setFlowLogDAO(FlowLogDAO flowLogDAO) {
		this.flowLogDAO = flowLogDAO;
	}

	/**
	 * @return the paymentVSOutDAO
	 */
	public PaymentVSOutDAO getPaymentVSOutDAO() {
		return paymentVSOutDAO;
	}

	/**
	 * @param paymentVSOutDAO
	 *            the paymentVSOutDAO to set
	 */
	public void setPaymentVSOutDAO(PaymentVSOutDAO paymentVSOutDAO) {
		this.paymentVSOutDAO = paymentVSOutDAO;
	}

	/**
	 * @return the inBillDAO
	 */
	public InBillDAO getInBillDAO() {
		return inBillDAO;
	}

	/**
	 * @param inBillDAO
	 *            the inBillDAO to set
	 */
	public void setInBillDAO(InBillDAO inBillDAO) {
		this.inBillDAO = inBillDAO;
	}

	/**
	 * @return the outDAO
	 */
	public OutDAO getOutDAO() {
		return outDAO;
	}

	/**
	 * @param outDAO
	 *            the outDAO to set
	 */
	public void setOutDAO(OutDAO outDAO) {
		this.outDAO = outDAO;
	}

	/**
	 * @return the statBankManager
	 */
	public StatBankManager getStatBankManager() {
		return statBankManager;
	}

	/**
	 * @param statBankManager
	 *            the statBankManager to set
	 */
	public void setStatBankManager(StatBankManager statBankManager) {
		this.statBankManager = statBankManager;
	}

	/**
	 * @return the statBankDAO
	 */
	public StatBankDAO getStatBankDAO() {
		return statBankDAO;
	}

	/**
	 * @param statBankDAO
	 *            the statBankDAO to set
	 */
	public void setStatBankDAO(StatBankDAO statBankDAO) {
		this.statBankDAO = statBankDAO;
	}

	/**
	 * @return the outBalanceDAO
	 */
	public OutBalanceDAO getOutBalanceDAO() {
		return outBalanceDAO;
	}

	/**
	 * @param outBalanceDAO
	 *            the outBalanceDAO to set
	 */
	public void setOutBalanceDAO(OutBalanceDAO outBalanceDAO) {
		this.outBalanceDAO = outBalanceDAO;
	}

	/**
	 * @return the outBillDAO
	 */
	public OutBillDAO getOutBillDAO() {
		return outBillDAO;
	}

	/**
	 * @param outBillDAO
	 *            the outBillDAO to set
	 */
	public void setOutBillDAO(OutBillDAO outBillDAO) {
		this.outBillDAO = outBillDAO;
	}

	/**
	 * @return the outManager
	 */
	public OutManager getOutManager() {
		return outManager;
	}

	/**
	 * @param outManager
	 *            the outManager to set
	 */
	public void setOutManager(OutManager outManager) {
		this.outManager = outManager;
	}

	/**
	 * @return the parameterDAO
	 */
	public ParameterDAO getParameterDAO() {
		return parameterDAO;
	}

	/**
	 * @param parameterDAO
	 *            the parameterDAO to set
	 */
	public void setParameterDAO(ParameterDAO parameterDAO) {
		this.parameterDAO = parameterDAO;
	}

	/**
	 * @return the bankManager
	 */
	public BankManager getBankManager() {
		return bankManager;
	}

	/**
	 * @param bankManager
	 *            the bankManager to set
	 */
	public void setBankManager(BankManager bankManager) {
		this.bankManager = bankManager;
	}

	/**
	 * @return the financeDAO
	 */
	public FinanceDAO getFinanceDAO() {
		return financeDAO;
	}

	/**
	 * @param financeDAO
	 *            the financeDAO to set
	 */
	public void setFinanceDAO(FinanceDAO financeDAO) {
		this.financeDAO = financeDAO;
	}

	/**
	 * @return the billManager
	 */
	public BillManager getBillManager() {
		return billManager;
	}

	/**
	 * @param billManager
	 *            the billManager to set
	 */
	public void setBillManager(BillManager billManager) {
		this.billManager = billManager;
	}

	/**
	 * @return the paymentManager
	 */
	public PaymentManager getPaymentManager() {
		return paymentManager;
	}

	/**
	 * @param paymentManager
	 *            the paymentManager to set
	 */
	public void setPaymentManager(PaymentManager paymentManager) {
		this.paymentManager = paymentManager;
	}
	public BackPayApplyDAO getBackPayApplyDAO()
	{
		return backPayApplyDAO;
	}

	public void setBackPayApplyDAO(BackPayApplyDAO backPayApplyDAO)
	{
		this.backPayApplyDAO = backPayApplyDAO;
	}

	public StafferVSCustomerDAO getStafferVSCustomerDAO()
	{
		return stafferVSCustomerDAO;
	}

	public void setStafferVSCustomerDAO(StafferVSCustomerDAO stafferVSCustomerDAO)
	{
		this.stafferVSCustomerDAO = stafferVSCustomerDAO;
	}

	public CustomerMainDAO getCustomerMainDAO() {
		return customerMainDAO;
	}

	public void setCustomerMainDAO(CustomerMainDAO customerMainDAO) {
		this.customerMainDAO = customerMainDAO;
	}

	/**
	 * @return the invoiceDAO
	 */
	public InvoiceDAO getInvoiceDAO()
	{
		return invoiceDAO;
	}

	/**
	 * @param invoiceDAO the invoiceDAO to set
	 */
	public void setInvoiceDAO(InvoiceDAO invoiceDAO)
	{
		this.invoiceDAO = invoiceDAO;
	}

	/**
	 * @return the roleAuthDAO
	 */
	public RoleAuthDAO getRoleAuthDAO()
	{
		return roleAuthDAO;
	}

	/**
	 * @param roleAuthDAO the roleAuthDAO to set
	 */
	public void setRoleAuthDAO(RoleAuthDAO roleAuthDAO)
	{
		this.roleAuthDAO = roleAuthDAO;
	}
}
