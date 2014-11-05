package com.china.center.oa.customerservice.portal.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
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
import com.center.china.osgi.publics.file.writer.WriteFile;
import com.center.china.osgi.publics.file.writer.WriteFileFactory;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONPageSeparateTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.bean.CustomerContactBean;
import com.china.center.oa.client.dao.CustomerContactDAO;
import com.china.center.oa.client.dao.CustomerMainDAO;
import com.china.center.oa.credit.bean.CreditLevelBean;
import com.china.center.oa.credit.dao.CreditLevelDAO;
import com.china.center.oa.customerservice.bean.FeedBackCheckBean;
import com.china.center.oa.customerservice.bean.FeedBackDetailBean;
import com.china.center.oa.customerservice.bean.FeedBackVSOutBean;
import com.china.center.oa.customerservice.bean.FeedBackVisitBean;
import com.china.center.oa.customerservice.bean.FeedBackVisitItemBean;
import com.china.center.oa.customerservice.constant.CustomerServiceConstant;
import com.china.center.oa.customerservice.dao.FeedBackCheckDAO;
import com.china.center.oa.customerservice.dao.FeedBackDAO;
import com.china.center.oa.customerservice.dao.FeedBackDetailDAO;
import com.china.center.oa.customerservice.dao.FeedBackVSOutDAO;
import com.china.center.oa.customerservice.dao.FeedBackVisitDAO;
import com.china.center.oa.customerservice.dao.FeedBackVisitItemDAO;
import com.china.center.oa.customerservice.helper.CustomerServiceHelper;
import com.china.center.oa.customerservice.manager.CustomerServiceManager;
import com.china.center.oa.customerservice.vo.FeedBackVO;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.manager.CommonMailManager;
import com.china.center.oa.sail.bean.DistributionBean;
import com.china.center.oa.sail.dao.DistributionDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.helper.YYTools;
import com.china.center.osgi.jsp.ElTools;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.RequestTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.UtilStream;
import com.china.center.tools.WriteFileBuffer;

import edu.emory.mathcs.backport.java.util.Collections;

public class CustomerServiceAction extends DispatchAction
{
	private final Log _logger = LogFactory.getLog(getClass());

	private FeedBackDAO feedBackDAO = null;

	private FeedBackCheckDAO feedBackCheckDAO = null;

	private FeedBackDetailDAO feedBackDetailDAO = null;

	private FeedBackVisitDAO feedBackVisitDAO = null;

	private FeedBackVisitItemDAO feedBackVisitItemDAO = null;

	private FeedBackVSOutDAO feedBackVSOutDAO = null;

	private CustomerServiceManager customerServiceManager = null;

	private CreditLevelDAO creditLevelDAO = null;

	private CustomerMainDAO customerMainDAO = null;

	private OutDAO outDAO = null;

	private StafferDAO stafferDAO = null;

	private InBillDAO inBillDAO = null;
	
	private DistributionDAO distributionDAO = null;
	
	private CommonMailManager commonMailManager = null;
	
	private CustomerContactDAO customerContactDAO = null;

	private static final String QUERYFEEDBACK = "queryFeedBack";
	
	private static final String QUERYFEEDBACKVISIT = "queryFeedBackVisit";

	private static final String QUERYFEEDBACKCHECK = "queryFeedBackCheck";
	
	public CustomerServiceAction()
	{
	}

	/**
	 * 回访、对账任务查询 所有与查询自己的
	 * 
	 * @param form
	 * @param mapping
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryFeedBack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		String mode = RequestTools.getValueFromRequest(request, "mode");
		
		User user = Helper.getUser(request);
		
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		if (mode.equals("0"))
		{
			//condtion.addIntCondition("FeedBackBean.status", "=", CustomerServiceConstant.FEEDBACK_STATUS_DISTRIBUTE);
		}
		else
		{
			//condtion.addIntCondition("FeedBackBean.status", "<>", CustomerServiceConstant.FEEDBACK_STATUS_DISTRIBUTE);
			condtion.addCondition(" and FeedBackBean.status not in (1, 99)");
			
			condtion.addCondition("FeedBackBean.bear", "=", user.getStafferId());
		}
		
		ActionTools.processJSONQueryCondition(QUERYFEEDBACK, request, condtion);

		condtion.addCondition(" order by FeedBackBean.status asc, FeedBackBean.pStatus desc");
		
		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYFEEDBACK,
				request, condtion, this.feedBackDAO,
				new HandleResult<FeedBackVO>()
				{
					public void handle(FeedBackVO obj)
					{
						// 动态获取信用及预付款
						double custCredit = 0.0d; //getCustCredit(obj.getCustomerId());

						double staffCredit = 0.0d; //getStaffCredit(obj.getStafferId());

						double preMoney = getPreMoney(obj.getCustomerId());

						obj.setCustCredit(custCredit);
						obj.setStaffCredit(staffCredit);
						obj.setPreMoney(preMoney);

						obj.setNow(TimeTools.now_short());
					}
				});

		return JSONTools.writeResponse(response, jsonstr);
	}

	@SuppressWarnings("unused")
	private double getCustCredit(String customerId)
	{
		CustomerBean cbean = customerMainDAO.find(customerId);

		if (cbean == null)
		{
			return 0;
		}

		// query customer credit
		CreditLevelBean clevel = creditLevelDAO.find(cbean.getCreditLevelId());

		if (clevel == null)
		{
			return 0;
		}

		double noPayBusinessInCur = outDAO.sumNoPayBusiness(customerId,
				YYTools.getFinanceBeginDate(), YYTools.getFinanceEndDate());

		double remainInCur = clevel.getMoney() - noPayBusinessInCur;

		return remainInCur < 0 ? 0 : remainInCur;
	}

	@SuppressWarnings("unused")
	private double getStaffCredit(String stafferId)
	{
		StafferBean stafferBean = stafferDAO.find(stafferId);

		if (null == stafferBean)
		{
			return 0;
		}

		// 职员杠杆后的信用
		double staffCredit = stafferBean.getCredit() * stafferBean.getLever();

		// 自己担保的+替人担保的(这里应该区分不同的事业部)
		double noPayBusiness = outDAO.sumAllNoPayAndAvouchBusinessByStafferId(
				stafferId, stafferBean.getIndustryId(),
				YYTools.getStatBeginDate(), YYTools.getStatEndDate());

		return staffCredit - noPayBusiness;
	}

	private double getPreMoney(String customerId)
	{
		ConditionParse con = new ConditionParse();

		con.addWhereStr();

		con.addCondition("InBillBean.customerId", "=", customerId);

		con.addIntCondition("InBillBean.status", "=",
				FinanceConstant.INBILL_STATUS_NOREF);

		return inBillDAO.sumByCondition(con);

	}

	/**
	 * 分配回访任务
	 * 
	 * @param form
	 * @param mapping
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward allocationFeedBack(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		AjaxResult ajax = new AjaxResult();

		String destStafferId = request.getParameter("destStafferId");

		String taskIds = request.getParameter("taskIds");

		try
		{
			User user = Helper.getUser(request);

			customerServiceManager
					.allocationTasks(user, taskIds, destStafferId);

			ajax.setSuccess("任务分配成功");
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			ajax.setError("任务分配失败，" + e.getErrorContent());
		}

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * 接收回访、对账任务
	 * 
	 * @param form
	 * @param mapping
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward acceptFeedBack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		AjaxResult ajax = new AjaxResult();

		String taskIds = request.getParameter("taskIds");

		try
		{
			User user = Helper.getUser(request);

			customerServiceManager.acceptTasks(user, taskIds);

			ajax.setSuccess("任务接收成功");
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			ajax.setError("任务接收失败，" + e.getErrorContent());
		}

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * 拒绝接收
	 * 
	 * @param form
	 * @param mapping
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward rejectFeedBack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		AjaxResult ajax = new AjaxResult();

		String taskIds = request.getParameter("taskIds");

		try
		{
			User user = Helper.getUser(request);

			customerServiceManager.rejectTasks(user, taskIds);

			ajax.setSuccess("任务拒绝接收成功");
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			ajax.setError("任务拒绝接收失败，" + e.getErrorContent());
		}

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * 查看明细
	 * 
	 * @param form
	 * @param mapping
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward findFeedBack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		return mapping.findForward("detailFeedBack");
	}

	/***
	 * 查询回访数据
	 * 
	 * @param form
	 * @param mapping
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryFeedBackVisit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		String mode = RequestTools.getValueFromRequest(request, "mode");
		
		User user = Helper.getUser(request);
		
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		if (mode.equals("1"))
		{
			condtion.addCondition("FeedBackVisitBean.exceptionProcesser", "=", user.getStafferId());
			
			condtion.addIntCondition("FeedBackVisitBean.exceptionStatus", "=", CustomerServiceConstant.FEEDBACK_EXCEPTION_STATUS_INWAY);
		}
//		else
//		{
//			condtion.addCondition("FeedBackVisitBean.caller", "=", user.getStafferName());
//		}
		
		ActionTools.processJSONQueryCondition(QUERYFEEDBACKVISIT, request, condtion);

		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYFEEDBACKVISIT,
				request, condtion, this.feedBackVisitDAO);

		return JSONTools.writeResponse(response, jsonstr);
	}

	/***
	 * 查询对账数据
	 * 
	 * @param form
	 * @param mapping
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryFeedBackCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		String mode = RequestTools.getValueFromRequest(request, "mode");
		
		User user = Helper.getUser(request);
		
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		if (mode.equals("1"))
		{
			condtion.addCondition("FeedBackCheckBean.exceptionProcesser", "=", user.getStafferId());
			
			condtion.addIntCondition("FeedBackCheckBean.exceptionStatus", "=", CustomerServiceConstant.FEEDBACK_EXCEPTION_STATUS_INWAY);
		}
		
		ActionTools.processJSONQueryCondition(QUERYFEEDBACKCHECK, request, condtion);

		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYFEEDBACKCHECK,
				request, condtion, this.feedBackCheckDAO);

		return JSONTools.writeResponse(response, jsonstr);
	}
	
	/**
	 * 
	 * @param form
	 * @param mapping
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward preForAddFeedBackVisit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		String taskId = request.getParameter("taskId");

		boolean update = true;
		
		FeedBackVO bean = feedBackDAO.findVO(taskId);

		if (null == bean)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "任务不存在");

			return mapping.findForward("error");
		}

		if (bean.getType() != CustomerServiceConstant.FEEDBACK_TYPE_VISIT
				&& bean.getType() != CustomerServiceConstant.FEEDBACK_TYPE_EXCEPTION_VISIT)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "不是回访任务");

			return mapping.findForward("error");
		}

		if (bean.getStatus() != CustomerServiceConstant.FEEDBACK_STATUS_PROCESSING)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "不是处理中的回访");

			return mapping.findForward("error");
		}

		request.setAttribute("feedBack", bean);
		
		// 任务下销售单最近的发货时间
		FeedBackVSOutBean vsout = feedBackVSOutDAO.findMaxchangeTimeByTaskId(taskId);

		if (null != vsout)
			bean.setChangeTime(vsout.getChangeTime());
		
		// 检查是否已保存过
		FeedBackVisitBean visitBean = feedBackVisitDAO.findMaxLogTimeByTaskId(taskId);
		
		if (null == visitBean)
		{
			update = false;
			
			visitBean = new FeedBackVisitBean();
			
			CustomerBean cbean = customerMainDAO.find(bean.getCustomerId());
			
			if (null != cbean)
			{
				CustomerContactBean custCont = customerContactDAO.findFirstValidBean(cbean.getId());
				
				if (null != custCont){
					visitBean.setContact(custCont.getName());
					visitBean.setContactPhone(custCont.getHandphone());
				}

			}
		}
		
		request.setAttribute("bean", visitBean);
		
		request.setAttribute("update", update);
		
		double total = 0.0d;
		
		if (bean.getType() == CustomerServiceConstant.FEEDBACK_TYPE_VISIT)
		{
			if (!update)
			{
				List<FeedBackDetailBean> detailList = feedBackDetailDAO
				.queryEntityBeansByFK(taskId);

				List<FeedBackDetailBean> dList = CustomerServiceHelper
						.trimFeedBackDetailList(detailList);
				
				for (FeedBackDetailBean each : dList)
				{
					total += each.getMoney();
				}
		
				request.setAttribute("detailList", dList);
			}
			else
			{
				List<FeedBackVisitItemBean> visitItemList = feedBackVisitItemDAO
				.queryEntityBeansByFK(visitBean.getId());
				
				for (FeedBackVisitItemBean each : visitItemList)
				{
					total += each.getMoney();
				}
				
				request.setAttribute("detailList", visitItemList);
			}

			request.setAttribute("total", total);
			
			return mapping.findForward("addFeedBackVisit");
		}

		List<FeedBackVisitItemBean> visitItemList = feedBackVisitItemDAO
				.queryEntityBeansByFK(bean.getRefVisitId());

		for (Iterator<FeedBackVisitItemBean> iterator = visitItemList
				.iterator(); iterator.hasNext();)
		{
			FeedBackVisitItemBean viBean = iterator.next();

			// 除去无异常的数据
			if (viBean.getIfHasException() == CustomerServiceConstant.FEEDBACK_RECEIVE_EXCEPTION_NO)
			{
				iterator.remove();
			}
		}

		for (FeedBackVisitItemBean each : visitItemList)
		{
			total += each.getMoney();
		}
		
		request.setAttribute("total", total);
		
		request.setAttribute("detailList", visitItemList);

		// 异常回访
		return mapping.findForward("updateFeedBackVisit");
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
	public ActionForward addSingleCustomerVisit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		CommonTools.saveParamers(request);

		User user = Helper.getUser(request);
		
		String customerId = request.getParameter("cid");
		
		try
		{
			customerServiceManager.addSingleCustomerVisit(user, customerId);

			request.setAttribute(KeyConstant.MESSAGE, "生成成功");
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"处理失败，" + e.getErrorContent());
		}
		
		request.setAttribute("mode", 1);
		
		return mapping.findForward("queryFeedBack");
	}
	
	/**
	 * 增加回访
	 * 
	 * @param form
	 * @param mapping
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward addFeedBackVisit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		CommonTools.saveParamers(request);

		User user = Helper.getUser(request);

		String taskId = request.getParameter("taskId");

		FeedBackVO feedBackBean = feedBackDAO.findVO(taskId);

		if (null == feedBackBean)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "任务不存在");

			return mapping.findForward("error");
		}

		FeedBackVisitBean bean = new FeedBackVisitBean();

		BeanUtil.getBean(bean, request);

		bean.setCaller(user.getStafferName());

		bean.setCallTime(TimeTools.now());

		bean.setTaskId(taskId);

		bean.setTaskType(feedBackBean.getType());

		bean.setCustomerName(feedBackBean.getCustomerName());
		
		bean.setStafferName(feedBackBean.getStafferName());

		bean.setLogTime(TimeTools.now());

		StringBuilder sb = new StringBuilder();
		
		fillItem(bean, request, sb);

		try
		{
			customerServiceManager.addFeedBackVisit(user, bean);

			if (bean.getStatus() == 1)
			{
				if(!StringTools.isNullOrNone(sb.toString()))
				{
					sendVisitMail(bean, sb);
				}
			}
			
			request.setAttribute(KeyConstant.MESSAGE, "回访成功");
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"处理失败，" + e.getErrorContent());
		}
		
		request.setAttribute("mode", 1);
		
		return mapping.findForward("queryFeedBack");
	}

	private void sendVisitMail(FeedBackVisitBean bean, StringBuilder sb)
	{
		String title = "异常处理——" + bean.getCustomerName();
		
		String content = bean.getDescription() + "<br>"
							+ "回访人：" + bean.getCaller() + "<br>"
							+ "回访时间：" + bean.getCallTime() + "<br>"
							+ sb.toString();
		
		String toName = bean.getExceptionProcesserName() + "," + bean.getExceptRef();

		String tos[] = toName.split(",");
		
		for (String each : tos)
		{
			if (!StringTools.isNullOrNone(each)){
				StafferBean staff = stafferDAO.findByUnique(each);
				
				if (null != staff && !StringTools.isNullOrNone(staff.getNation())){

					commonMailManager.sendMail(staff.getNation(), title, content);
				}
			}
		}
	}

	private void sendCheckMail(FeedBackCheckBean bean)
	{
		String title = "异常处理——" + bean.getCustomerName();
		
		String content = bean.getDescription() + "<br>"
							+ "对账人：" + bean.getCaller() + "<br>"
							+ "对账时间：" + bean.getLogTime() + "<br>"
							;
		
		String toName = bean.getExceptionProcesserName() + "," + bean.getExceptRef();

		String tos[] = toName.split(",");
		
		for (String each : tos)
		{
			if (!StringTools.isNullOrNone(each)){
				StafferBean staff = stafferDAO.findByUnique(each);
				
				if (null != staff && !StringTools.isNullOrNone(staff.getNation())){

					commonMailManager.sendMail(staff.getNation(), title, content);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param bean
	 * @param request
	 */
	private void fillItem(FeedBackVisitBean bean, HttpServletRequest request, StringBuilder sb)
	{
		List<FeedBackVisitItemBean> itemList = new ArrayList<FeedBackVisitItemBean>();

		bean.setItemList(itemList);

		String[] productIds = request.getParameterValues("p_productId");
		String[] productNames = request.getParameterValues("p_productName");
		String[] amounts = request.getParameterValues("p_amount");
		String[] moneys = request.getParameterValues("p_money");
		String[] receives = request.getParameterValues("p_receive");
		String[] receiptTimes = request.getParameterValues("p_receiptTime");
		String[] ifHasExceptions = request
				.getParameterValues("p_ifHasException");
		String[] exceptionAmounts = request
				.getParameterValues("p_exceptionAmount");
		String[] exceptionTypes = request.getParameterValues("p_exceptionType");
		String[] exceptionTexts = request.getParameterValues("p_exceptionText");

		for (int i = 0; i < productIds.length; i++)
		{
			FeedBackVisitItemBean itemBean = new FeedBackVisitItemBean();

			itemBean.setProductId(productIds[i]);
			itemBean.setProductName(productNames[i]);
			itemBean.setAmount(MathTools.parseInt(amounts[i]));
			itemBean.setMoney(MathTools.parseDouble(moneys[i]));
			itemBean.setReceive(MathTools.parseInt(receives[i]));
			itemBean.setReceiptTime(receiptTimes[i]);
			itemBean.setIfHasException(MathTools.parseInt(ifHasExceptions[i]));
			itemBean.setExceptionAmount(MathTools.parseInt(exceptionAmounts[i]));
			itemBean.setExceptionType(MathTools.parseInt(exceptionTypes[i]));
			itemBean.setExceptionText(exceptionTexts[i]);

			itemList.add(itemBean);
			
			if (itemBean.getIfHasException() == 1)
			{
				sb.append(itemBean.getProductName()).append("----").append(DefinedCommon.getValue("231", itemBean.getExceptionType())).append("<br>");
			}
		}
	}

	/**
	 * 
	 * @param form
	 * @param mapping
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward findFeedBackVisit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		String update = request.getParameter("update");

		String id = request.getParameter("id");

		FeedBackVisitBean visitBean = feedBackVisitDAO.find(id);

		if (null == visitBean)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

			return mapping.findForward("error");
		}

		FeedBackVO bean = feedBackDAO.findVO(visitBean.getTaskId());

		if (null == bean)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "任务不存在");

			return mapping.findForward("error");
		}

		if (update.equals("1"))
		{
			if (bean.getType() != CustomerServiceConstant.FEEDBACK_TYPE_EXCEPTION_VISIT)
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, "不是异常回访任务");

				return mapping.findForward("error");
			}

			if (bean.getStatus() != CustomerServiceConstant.FEEDBACK_STATUS_EXCEPTION_PROCESSING)
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, "不是处理中的回访");

				return mapping.findForward("error");
			}
		}

		request.setAttribute("feedBack", bean);

		request.setAttribute("bean", visitBean);

		List<FeedBackVisitItemBean> visitItemList = feedBackVisitItemDAO
				.queryEntityBeansByFK(id);

		request.setAttribute("detailList", visitItemList);

		// update=1 表示对处理异常的回访
		if (update.equals("1"))
		{
			// 任务下销售单最近的发货时间
			FeedBackVSOutBean vsout = feedBackVSOutDAO.findMaxchangeTimeByTaskId(bean.getId());

			if (null != vsout)
				bean.setChangeTime(vsout.getChangeTime());
			
			return mapping.findForward("processFeedBackVisit");
		}

		return mapping.findForward("detailFeedBackVisit");
	}

	/**
	 * 异常处理
	 * 
	 * @param form
	 * @param mapping
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward processFeedBackVisit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		User user = Helper.getUser(request);

		String id = request.getParameter("id");

		String actualExceptionReason = request
				.getParameter("actualExceptionReason");

		String resolve = request.getParameter("resolve");

		String resolveText = request.getParameter("resolveText");
		
		int status = MathTools.parseInt(request.getParameter("status"));

		FeedBackVisitBean bean = feedBackVisitDAO.find(id);

		if (null == bean)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

			return mapping.findForward("error");
		}

		bean.setActualExceptionReason(MathTools.parseInt(actualExceptionReason));
		bean.setResolve(MathTools.parseInt(resolve));
		bean.setResolveText(resolveText);
		bean.setStatus(status);

		try
		{
			customerServiceManager.updateFeedBackVisit(user, bean);

			request.setAttribute(KeyConstant.MESSAGE, "处理成功");
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"处理失败，" + e.getErrorContent());
		}

		return mapping.findForward("queryFeedBackVisit");
	}

	/**
	 * 异常处理 - 对账
	 * 
	 * @param form
	 * @param mapping
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward processFeedBackCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		User user = Helper.getUser(request);

		String id = request.getParameter("id");

		String actualExceptionReason = request.getParameter("actualExceptionReason");

		String resolve = request.getParameter("resolve");

		String resolveText = request.getParameter("resolveText");
		
		int status = MathTools.parseInt(request.getParameter("status"));

		FeedBackCheckBean bean = feedBackCheckDAO.find(id);

		if (null == bean)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

			return mapping.findForward("error");
		}

		bean.setActualExceptionReason(MathTools.parseInt(actualExceptionReason));
		bean.setResolve(MathTools.parseInt(resolve));
		bean.setResolveText(resolveText);
		bean.setStatus(status);

		try
		{
			customerServiceManager.updateFeedBackCheck(user, bean);

			request.setAttribute(KeyConstant.MESSAGE, "处理成功");
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"处理失败，" + e.getErrorContent());
		}

		return mapping.findForward("queryFeedBackCheck");
	}	
	
	/**
	 * 
	 * @param form
	 * @param mapping
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward preForAddFeedBackCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		String taskId = request.getParameter("taskId");

		FeedBackVO bean = feedBackDAO.findVO(taskId);

		if (null == bean)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "任务不存在");

			return mapping.findForward("error");
		}

		if (bean.getType() != CustomerServiceConstant.FEEDBACK_TYPE_CHECK && bean.getType() != CustomerServiceConstant.FEEDBACK_TYPE_EXCEPTION_CHECK)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "不是对账任务");

			return mapping.findForward("error");
		}

		if (bean.getStatus() != CustomerServiceConstant.FEEDBACK_STATUS_PROCESSING)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "不是处理中的对账任务");

			return mapping.findForward("error");
		}
		
		// 检查是否已保存过
		FeedBackCheckBean checkBean = feedBackCheckDAO.findMaxLogTimeByTaskId(taskId);
		
		if (null == checkBean)
		{
			checkBean = new FeedBackCheckBean();
			
			CustomerBean cbean = customerMainDAO.find(bean.getCustomerId());
			
			if (null != cbean)
			{
				CustomerContactBean custCont = customerContactDAO.findFirstValidBean(cbean.getId());
				
				if (null != custCont){
					checkBean.setContact(custCont.getName());
					checkBean.setContactPhone(custCont.getHandphone());
				}
			}
		}
		
		request.setAttribute("bean", checkBean);

		List<FeedBackDetailBean> detailList = feedBackDetailDAO
				.queryEntityBeansByFK(taskId);

		List<FeedBackDetailBean> dList = CustomerServiceHelper.trimFeedBackDetailList(detailList);
		
		double total = 0.0d;
		double nopayTotal = 0.0d;
		
		for (FeedBackDetailBean each : dList)
		{
			total += (each.getMoney() + each.getBackMoney());
			nopayTotal += each.getNoPayMoneys();
		}
		
		request.setAttribute("total", total);
		request.setAttribute("nopayTotal", nopayTotal);
		
		request.setAttribute("feedBack", bean);

		request.setAttribute("detailList", dList);

		return mapping.findForward("addFeedBackCheck");
	}

	/**
	 * 增加对账
	 * 
	 * @param form
	 * @param mapping
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward addFeedBackCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		CommonTools.saveParamers(request);

		User user = Helper.getUser(request);

		String taskId = request.getParameter("taskId");

		FeedBackVO feedBackBean = feedBackDAO.findVO(taskId);

		if (null == feedBackBean)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "任务不存在");

			return mapping.findForward("error");
		}

		FeedBackCheckBean bean = new FeedBackCheckBean();

		BeanUtil.getBean(bean, request);

		bean.setTaskId(taskId);

		bean.setCustomerName(feedBackBean.getCustomerName());

		bean.setStafferName(feedBackBean.getStafferName());
		
		bean.setStatTime(feedBackBean.getLogTime());

		bean.setCaller(user.getStafferName());
		
		bean.setLogTime(TimeTools.now());

		try
		{
			customerServiceManager.addFeedBackCheck(user, bean);

			if (bean.getStatus() == 1 && !StringTools.isNullOrNone(bean.getExceptionProcesser()))
			{
				sendCheckMail(bean);
			}
			
			request.setAttribute(KeyConstant.MESSAGE, "对账成功");
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"对账失败，" + e.getErrorContent());
		}

		request.setAttribute("mode", 1);
		
		return mapping.findForward("queryFeedBack");
	}

	/**
	 * 对账单邮件发送
	 * 
	 * @param form
	 * @param mapping
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward mailFeedBackCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		AjaxResult ajax = new AjaxResult();

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * 打印  OR email
	 * @param form
	 * @param mapping
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward previewFeedBackCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		AjaxResult ajax = new AjaxResult();
		
		User user = Helper.getUser(request);
		
		String type = request.getParameter("type");
		
		String taskId = request.getParameter("taskId");
		
		String mailAddress = request.getParameter("mailAddress");
		
		FeedBackVO bean = feedBackDAO.findVO(taskId);

		if (null == bean)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "任务不存在");

			return mapping.findForward("error");
		}
		
		if (bean.getType() != CustomerServiceConstant.FEEDBACK_TYPE_CHECK)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "不是对账任务");

			return mapping.findForward("error");
		}

		bean.setMailAddress(mailAddress);
		
		List<FeedBackDetailBean> detailList = feedBackDetailDAO
				.queryEntityBeansByFK(taskId);
		
		bean.setDetailList(detailList);
		
		// email
		if (type.equals("1"))
		{
			try
			{
				customerServiceManager.mailAttachment(user, bean);
				
				ajax.setMsg("邮件发送成功");
			}
			catch (MYException e)
			{
				_logger.warn(e, e);
				
				ajax.setError("邮件发送失败:" + e.getErrorContent());
			}
			
			return JSONTools.writeResponse(response, ajax);
		}
		else
		{
			try
			{
				String path = customerServiceManager.downAttachment(user, bean);
				
				String fileName = path.substring(path.lastIndexOf("/"));
				
		        File file = new File(path);

		        OutputStream out = response.getOutputStream();

		        response.setContentType("application/x-dbf");

		        response.setHeader("Content-Disposition", "attachment; filename="
		                                                  + StringTools.getStringBySet(fileName,
		                                                      "GBK", "ISO8859-1"));

		        UtilStream us = new UtilStream(new FileInputStream(file), out);

		        us.copyAndCloseStream();
				
			}
			catch (MYException e)
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, "对账函生成失败:" + e.getErrorContent());

				return mapping.findForward("error");
			}
		
			return null;
		}
	}
	
	/**
	 * 
	 * @param form
	 * @param mapping
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward findFeedBackCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		String update = request.getParameter("update");
		
		String id = request.getParameter("id");
		
		FeedBackCheckBean checkBean = feedBackCheckDAO.find(id);
		
		if (null == checkBean)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "未找到对账记录");

			return mapping.findForward("error");
		}

		FeedBackVO bean = feedBackDAO.findVO(checkBean.getTaskId());

		if (null == bean)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "任务不存在");

			return mapping.findForward("error");
		}
		
		if (bean.getType() != CustomerServiceConstant.FEEDBACK_TYPE_CHECK && bean.getType() != CustomerServiceConstant.FEEDBACK_TYPE_EXCEPTION_CHECK)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "不是对账任务");

			return mapping.findForward("error");
		}

		if (update.equals("1"))
		{
			if (bean.getStatus() != CustomerServiceConstant.FEEDBACK_STATUS_EXCEPTION_PROCESSING)
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, "不是处理中的对账");

				return mapping.findForward("error");
			}
		}
		
		List<FeedBackDetailBean> detailList = feedBackDetailDAO.queryEntityBeansByFK(checkBean.getTaskId());

		List<FeedBackDetailBean> dList = CustomerServiceHelper.trimFeedBackDetailList(detailList);
		
		request.setAttribute("feedBack", bean);

		request.setAttribute("detailList", dList);

		request.setAttribute("bean", checkBean);
		
		// update=1 表示对处理异常的回访
		if (update.equals("1"))
		{
			return mapping.findForward("processFeedBackCheck");
		}
		
		return mapping.findForward("detailFeedBackCheck");
	}

	public ActionForward rptQueryTaskDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		String taskId = request.getParameter("taskId");
		
		String productId = request.getParameter("productId");
		
		ConditionParse con = new ConditionParse();
		
		con.addWhereStr();
		
		con.addCondition("FeedBackDetailBean.taskId", "=", taskId);
		con.addCondition("FeedBackDetailBean.productId", "=", productId);
		
		List<FeedBackDetailBean> items = feedBackDetailDAO.queryEntityBeansByCondition(con);
		
		for (FeedBackDetailBean each : items)
		{
			List<DistributionBean> distBeanList = distributionDAO.queryEntityBeansByFK(each.getOutId());
			
			if (!ListTools.isEmptyOrNull(distBeanList))
			{
				DistributionBean distBean = distBeanList.get(0);
				
				each.setReceiver(distBean.getReceiver());
				each.setMobile(distBean.getMobile());
			}
		}
		
		request.setAttribute("items", items);
		
		return mapping.findForward("rptQueryTaskDetail");
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
	public ActionForward rptQueryOutReceiver(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		CommonTools.saveParamers(request);
		
		String taskId = request.getParameter("taskId");
		
		List<FeedBackVSOutBean> vsoutList = feedBackVSOutDAO.queryEntityBeansByFK(taskId);
		
		Map<String, FeedBackVSOutBean> map = new HashMap<String, FeedBackVSOutBean>();
		
		// 将同一收货人+收货人电话 ，合并
		for(FeedBackVSOutBean each : vsoutList)
		{
			DistributionBean distBean = null;
			
			List<DistributionBean> distBeanList = distributionDAO.queryEntityBeansByFK(each.getOutId());
			
			if (!ListTools.isEmptyOrNull(distBeanList))
			{
				distBean = distBeanList.get(0);
			}
			
			if (null != distBean && !StringTools.isNullOrNone(distBean.getReceiver()))
			{
				String key = distBean.getReceiver() + "-" + distBean.getMobile();
				
				if (!map.containsKey(key))
				{
					FeedBackVSOutBean vsout = new FeedBackVSOutBean();
					
					vsout.setReceiver(distBean.getReceiver());
					vsout.setMobile(distBean.getMobile());
					
					map.put(key, vsout);
				}
			}
		}
		
		Collection<FeedBackVSOutBean> lastList = map.values();

		request.setAttribute("item", lastList);
		
		return mapping.findForward("rptQueryOutReceiver");
	}
	
	/**
	 * exportFeedBackCheck
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward exportFeedBackCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		OutputStream out = null;

		String filenName = "FeedBackCheck_" + TimeTools.now("MMddHHmmss") + ".csv";

		response.setContentType("application/x-dbf");

		response.setHeader("Content-Disposition", "attachment; filename="
				+ filenName);

		WriteFile write = null;

		ConditionParse condtion = JSONPageSeparateTools.getCondition(request,
				QUERYFEEDBACK);

		int count = feedBackDAO.countVOByCondition(condtion.toString());

		if (count > 150000)
		{
			return ActionTools.toError("导出数量大于150000,请重新选择时间段导出", mapping, request);
		}

		try
		{
			out = response.getOutputStream();

			write = WriteFileFactory.getMyTXTWriter();

			write.openFile(out);

			write.writeLine("任务,客户,业务员,销售单数量,商品数量,销售金额,已付金额,未付款金额,事业部,客户预付,类型,状态,处理状态,负责人,对账进度,对账结果,异常情况,描述");
			
			PageSeparate page = new PageSeparate();

			page.reset2(count, 2000);

			WriteFileBuffer line = new WriteFileBuffer(write);

			while (page.nextPage())
			{
				List<FeedBackVO> voFList = feedBackDAO.queryEntityVOsByCondition(
						condtion, page);

				for (FeedBackVO each : voFList)
				{
					if (each.getType() != CustomerServiceConstant.FEEDBACK_TYPE_CHECK)
						continue;
					
					List<FeedBackCheckBean> dList = feedBackCheckDAO.queryEntityBeansByFK(each.getId());
					
					// 取出dList中最近的一条
					FeedBackCheckBean checkBean = null;
					
					if (!ListTools.isEmptyOrNull(dList))
					{
						// 由ID 倒序
						Collections.sort(dList, new Comparator<FeedBackCheckBean>()
						{
							@Override
							public int compare(FeedBackCheckBean o1, FeedBackCheckBean o2)
							{
								return Integer.parseInt(o2.getId().substring(12)) - Integer.parseInt(o1.getId().substring(12));
							}
						});
						
						checkBean = dList.get(0);
					}
					
					line.reset();

					line.writeColumn(each.getId());
					
					line.writeColumn(each.getCustomerName());
					line.writeColumn(each.getStafferName());
					
					line.writeColumn(each.getOutCount());
					line.writeColumn(each.getProductCount());
					line.writeColumn(MathTools.formatNum2(each.getMoneys()));
					line.writeColumn(MathTools.formatNum2(each.getHadpay()));
					line.writeColumn(MathTools.formatNum2(each.getNoPayMoneys()));
					
					line.writeColumn(each.getIndustryIdName());
					line.writeColumn(MathTools.formatNum2(each.getPreMoney()));
					line.writeColumn("对账");
					
					line.writeColumn(ElTools.get("feedbackStatus",each.getStatus()));
					line.writeColumn(ElTools.get("feedbackPStatus",each.getPstatus()));
					line.writeColumn(each.getBearName());

					if (checkBean != null)
					{
						line.writeColumn(ElTools.get("checkProcess",checkBean.getCheckProcess()));
						line.writeColumn(ElTools.get("checkResult",checkBean.getCheckResult()));
						line.writeColumn(checkBean.getResolveText());
						line.writeColumn(checkBean.getDescription());	
					}
					else
					{
						line.writeColumn("--");
						line.writeColumn("--");
						line.writeColumn("--");
						line.writeColumn("--");
					}
					
					line.writeLine();
				}
			}

			write.close();
		}
		catch (Throwable e)
		{
			_logger.error(e, e);

			return null;
		}
		finally
		{
			if (out != null)
			{
				try
				{
					out.close();
				}
				catch (IOException e1)
				{
				}
			}

			if (write != null)
			{
				try
				{
					write.close();
				}
				catch (IOException e1)
				{
				}
			}
		}

		return null;
	}
	
	/**
	 * exportFeedBackVisit
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward exportFeedBackVisit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		OutputStream out = null;

		String filenName = "FeedBackVisit_" + TimeTools.now("MMddHHmmss") + ".csv";

		response.setContentType("application/x-dbf");

		response.setHeader("Content-Disposition", "attachment; filename="
				+ filenName);

		WriteFile write = null;

		ConditionParse condtion = JSONPageSeparateTools.getCondition(request,
				QUERYFEEDBACK);

		int count = feedBackDAO.countVOByCondition(condtion.toString());

		if (count > 150000)
		{
			return ActionTools.toError("导出数量大于150000,请重新选择时间段导出", mapping,
					request);
		}

		try
		{
			out = response.getOutputStream();

			write = WriteFileFactory.getMyTXTWriter();

			write.openFile(out);

			write.writeLine("任务编号,任务类型,状态,回访者,回访时间,业务员,事业部,客户,是否联系成功,联系人,联系电话,计划回复时间,异常状态,异常处理人,实际异常原因,解决办法,处理描述,描述,产品,数量,金额,是否异常,异常数量,异常类型,异常描述,销售单号");

			PageSeparate page = new PageSeparate();

			page.reset2(count, 2000);

			WriteFileBuffer line = new WriteFileBuffer(write);

			while (page.nextPage())
			{
				List<FeedBackVO> voFList = feedBackDAO.queryEntityVOsByCondition(
						condtion, page);

				for (FeedBackVO each : voFList)
				{
					if (each.getType() == CustomerServiceConstant.FEEDBACK_TYPE_CHECK)
						continue;
					
					List<FeedBackVSOutBean> vsList = feedBackVSOutDAO.queryEntityBeansByFK(each.getId());
					
					String outIds = "";
					
					for(FeedBackVSOutBean eachVS : vsList)
					{
						outIds += eachVS.getOutId() + ";";
					}
					
					List<FeedBackVisitBean> dList = feedBackVisitDAO.queryEntityBeansByFK(each.getId());
					
/*					// 取出dList中最近的一条
					FeedBackVisitBean visitBean = null;
					
					if (!ListTools.isEmptyOrNull(dList))
					{
						// 由ID 倒序
						Collections.sort(dList, new Comparator<FeedBackVisitBean>()
						{
							@Override
							public int compare(FeedBackVisitBean o1, FeedBackVisitBean o2)
							{
								return Integer.parseInt(o2.getId().substring(12)) - Integer.parseInt(o1.getId().substring(12));
							}
						});
						
						visitBean = dList.get(0);
					}
					
					if (visitBean == null)
						continue;
						
						*/
					
					for (FeedBackVisitBean visitBean : dList)
					{
						List<FeedBackVisitItemBean> itemList = feedBackVisitItemDAO.queryEntityBeansByFK(visitBean.getId());

						for (FeedBackVisitItemBean eachItem : itemList)
						{
							line.reset();

							line.writeColumn(visitBean.getTaskId());
							line.writeColumn(ElTools.get("feedbackType",
									visitBean.getTaskType()));
							line.writeColumn(ElTools.get("feedbackPstatus",visitBean.getStatus()));
							line.writeColumn(visitBean.getCaller());
							line.writeColumn(visitBean.getCallTime());
							
							line.writeColumn(visitBean.getStafferName());
							line.writeColumn(each.getIndustryIdName());
							
							line.writeColumn(visitBean.getCustomerName());
							line.writeColumn(ElTools.get("hasConnect",
									visitBean.getIfHasContact()));
							
							line.writeColumn(visitBean.getContact());
							line.writeColumn(visitBean.getContactPhone());
							
							line.writeColumn(visitBean.getPlanReplyDate());
							line.writeColumn(ElTools.get("exceptionStatus",visitBean.getExceptionStatus()));
							line.writeColumn(visitBean.getExceptionProcesserName());

							line.writeColumn(ElTools.get("229",visitBean.getActualExceptionReason()));

							line.writeColumn(ElTools.get("230",visitBean.getResolve()));
							line.writeColumn(visitBean.getResolveText());
							
							line.writeColumn(visitBean.getDescription());
							
							line.writeColumn(eachItem.getProductName());
							line.writeColumn(eachItem.getAmount());
							line.writeColumn(eachItem.getMoney());
							line.writeColumn(ElTools.get("receiveException",eachItem.getIfHasException()));
							line.writeColumn(eachItem.getExceptionAmount());
							line.writeColumn(ElTools.get("231",eachItem.getExceptionType()));
							line.writeColumn(eachItem.getExceptionText());
							
							line.writeColumn(outIds);
							
							line.writeLine();
						}
					}
					
				}
			}
			
			write.close();
		}
		catch (Throwable e)
		{
			_logger.error(e, e);

			return null;
		}
		finally
		{
			if (out != null)
			{
				try
				{
					out.close();
				}
				catch (IOException e1)
				{
				}
			}

			if (write != null)
			{

				try
				{
					write.close();
				}
				catch (IOException e1)
				{
				}
			}
		}

		return null;
	}
	
	public FeedBackDAO getFeedBackDAO()
	{
		return feedBackDAO;
	}

	public void setFeedBackDAO(FeedBackDAO feedBackDAO)
	{
		this.feedBackDAO = feedBackDAO;
	}

	public FeedBackCheckDAO getFeedBackCheckDAO()
	{
		return feedBackCheckDAO;
	}

	public void setFeedBackCheckDAO(FeedBackCheckDAO feedBackCheckDAO)
	{
		this.feedBackCheckDAO = feedBackCheckDAO;
	}

	public FeedBackDetailDAO getFeedBackDetailDAO()
	{
		return feedBackDetailDAO;
	}

	public void setFeedBackDetailDAO(FeedBackDetailDAO feedBackDetailDAO)
	{
		this.feedBackDetailDAO = feedBackDetailDAO;
	}

	public FeedBackVisitDAO getFeedBackVisitDAO()
	{
		return feedBackVisitDAO;
	}

	public void setFeedBackVisitDAO(FeedBackVisitDAO feedBackVisitDAO)
	{
		this.feedBackVisitDAO = feedBackVisitDAO;
	}

	public FeedBackVSOutDAO getFeedBackVSOutDAO()
	{
		return feedBackVSOutDAO;
	}

	public void setFeedBackVSOutDAO(FeedBackVSOutDAO feedBackVSOutDAO)
	{
		this.feedBackVSOutDAO = feedBackVSOutDAO;
	}

	public CustomerServiceManager getCustomerServiceManager()
	{
		return customerServiceManager;
	}

	public void setCustomerServiceManager(
			CustomerServiceManager customerServiceManager)
	{
		this.customerServiceManager = customerServiceManager;
	}

	public CreditLevelDAO getCreditLevelDAO()
	{
		return creditLevelDAO;
	}

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

	public OutDAO getOutDAO()
	{
		return outDAO;
	}

	public void setOutDAO(OutDAO outDAO)
	{
		this.outDAO = outDAO;
	}

	public StafferDAO getStafferDAO()
	{
		return stafferDAO;
	}

	public void setStafferDAO(StafferDAO stafferDAO)
	{
		this.stafferDAO = stafferDAO;
	}

	public InBillDAO getInBillDAO()
	{
		return inBillDAO;
	}

	public void setInBillDAO(InBillDAO inBillDAO)
	{
		this.inBillDAO = inBillDAO;
	}

	public FeedBackVisitItemDAO getFeedBackVisitItemDAO()
	{
		return feedBackVisitItemDAO;
	}

	public void setFeedBackVisitItemDAO(
			FeedBackVisitItemDAO feedBackVisitItemDAO)
	{
		this.feedBackVisitItemDAO = feedBackVisitItemDAO;
	}

	public DistributionDAO getDistributionDAO()
	{
		return distributionDAO;
	}

	public void setDistributionDAO(DistributionDAO distributionDAO)
	{
		this.distributionDAO = distributionDAO;
	}

	public CommonMailManager getCommonMailManager()
	{
		return commonMailManager;
	}

	public void setCommonMailManager(CommonMailManager commonMailManager)
	{
		this.commonMailManager = commonMailManager;
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
}
