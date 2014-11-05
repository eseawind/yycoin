/**
 * File Name: FlowAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-4-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.project.action;

import java.awt.Color;
import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;

import com.center.china.osgi.config.ConfigLoader;
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
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.dao.CustomerMainDAO;
import com.china.center.oa.product.bean.ProviderBean;
import com.china.center.oa.product.dao.ProviderDAO;
import com.china.center.oa.project.bean.AgreementBean;
import com.china.center.oa.project.bean.ProjectApproveBean;
import com.china.center.oa.project.bean.ProjectBean;
import com.china.center.oa.project.bean.TaskBean;
import com.china.center.oa.project.constant.ProjectConstant;
import com.china.center.oa.project.dao.AgreementDAO;
import com.china.center.oa.project.dao.InvoiceLineProjectDAO;
import com.china.center.oa.project.dao.PayLineProjectDAO;
import com.china.center.oa.project.dao.ProLineProjectDAO;
import com.china.center.oa.project.dao.ProjectApproveDAO;
import com.china.center.oa.project.dao.ProjectDAO;
import com.china.center.oa.project.dao.StafferProjectDAO;
import com.china.center.oa.project.dao.TaskDAO;
import com.china.center.oa.project.dao.TranLineProjectDAO;
import com.china.center.oa.project.helper.ProjectHelper;
import com.china.center.oa.project.manager.AgreementManager;
import com.china.center.oa.project.manager.InvoiceLineProjectManager;
import com.china.center.oa.project.manager.PayLineProjectManager;
import com.china.center.oa.project.manager.ProLineProjectManager;
import com.china.center.oa.project.manager.ProjectManager;
import com.china.center.oa.project.manager.StafferProjectManager;
import com.china.center.oa.project.manager.TaskManager;
import com.china.center.oa.project.manager.TranLineProjectManager;
import com.china.center.oa.project.vo.AgreementVO;
import com.china.center.oa.project.vo.InvoiceLineProjectVO;
import com.china.center.oa.project.vo.PayLineProjectVO;
import com.china.center.oa.project.vo.ProLineProjectVO;
import com.china.center.oa.project.vo.ProjectApproveVO;
import com.china.center.oa.project.vo.ProjectVO;
import com.china.center.oa.project.vo.StafferProjectVO;
import com.china.center.oa.project.vo.TaskVO;
import com.china.center.oa.project.vo.TransLineProjectVO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.AttachmentDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.vo.FlowLogVO;
import com.china.center.oa.publics.vo.StafferVO;
import com.china.center.oa.tcp.wrap.TcpParamWrap;
import com.china.center.osgi.jsp.ElTools;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.FileTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.SequenceTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.UtilStream;
import com.china.center.tools.WriteFileBuffer;

public class ProjectAction extends DispatchAction
{
	private final Log _logger = LogFactory.getLog(getClass());

	private ProjectDAO projectDAO = null;

	private ProjectApproveDAO projectApproveDAO = null;

	private AgreementDAO agreementDAO = null;

	private TaskManager taskManager = null;

	private TaskDAO taskDAO = null;

	private static String QUERYPROJECT = "queryProject";

	private static String QUERYAGREEMENT = "queryAgreement";

	private static String QUERYPROJECTTASK = "project.queryProjectTask";
	
	private static String QUERYPROJECTALLTASK = "project.queryProjectAllTask";

	private static String QUERYSELFAPPROVE = "project.queryProjectSelfApprove";

	private StafferDAO stafferDAO = null;

	private AgreementManager agreementManager = null;

	private ProjectManager projectManager = null;

	private ProLineProjectManager proLineProjectManager = null;

	private StafferProjectManager stafferProjectManager = null;

	private PayLineProjectManager payLineProjectManager = null;

	private TranLineProjectManager tranLineProjectManager = null;

	// private TcpFlowDAO tcpFlowDAO = null;

	private InvoiceLineProjectManager invoiceLineProjectManager = null;

	private ProviderDAO providerDAO = null;

	private CustomerMainDAO customerMainDAO = null;

	private AttachmentDAO attachmentDAO = null;

	private FlowLogDAO flowLogDAO = null;

	private StafferProjectDAO stafferProjectDAO = null;

	private ProLineProjectDAO proLineProjectDAO = null;

	private PayLineProjectDAO payLineProjectDAO = null;

	private TranLineProjectDAO tranLineProjectDAO = null;

	private InvoiceLineProjectDAO invoiceLineProjectDAO = null;

	private DutyDAO dutyDAO = null;

	/**
	 * default constructor
	 */
	public ProjectAction()
	{
	}

	/**
	 * uploadFileTab
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward uploadFileTab(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		ProjectBean bean = new ProjectBean();

		// 模板最多10M
		String flag = request.getParameter("flag");
		if (null != flag && flag.equals("1"))
		{
			return mapping.findForward("uploadFileTab");
		}
		RequestDataStream rds = new RequestDataStream(request,
				1024 * 1024 * 10L);
		try
		{
			rds.parser();
		}
		catch (Exception e)
		{
			_logger.error(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:附件超过10M");

			return mapping.findForward("error");
		}
		try
		{
			BeanUtil.getBean(bean, rds.getParmterMap());

			User user = Helper.getUser(request);

			String processId = rds.getParameter("processId");

			bean.setProcessid(processId);

			ActionForward afor = parserAttachment2(mapping, request, rds, bean);
			if (afor != null)
			{
				return afor;
			}

			rds.close();
		}
		catch (Exception e)
		{
			_logger.error(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败");

			return mapping.findForward("error");
		}
		request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加成功");
		return mapping.findForward("uploadFileTab");
	}

	/**
	 * addOrUpdateProject
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward addOrUpdateProject(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		ProjectBean bean = new ProjectBean();
		try
		{
			BeanUtil.getBean(bean, request.getParameterMap());

			User user = Helper.getUser(request);

			String processId = request.getParameter("processId");
			String projectName = request.getParameter("projectName");
			String projectType = request.getParameter("projectType");
			String customerId = request.getParameter("customerId");
			String predictSucRate = request.getParameter("predictSucRate");
			String desc = request.getParameter("description");

			// 人员行项目
			String[] s_prostafferId = request
					.getParameterValues("s_prostafferId");
			String[] role = request.getParameterValues("role");

			// 产品行项目
			String[] s_projectproId = request
					.getParameterValues("s_projectproId");
			String[] procount = request.getParameterValues("procount");
			String[] prounitprice = request.getParameterValues("prounitprice");

			// 配件行项目
			// String []s_projectproId =
			// request.getParameterValues("s_projectproId");
			// String []procount = request.getParameterValues("procount");
			// String []prounitprice =
			// request.getParameterValues("prounitprice");

			bean.setProcessid(processId);
			bean.setProjectName(projectName);
			bean.setProjectType(projectType);
			bean.setCustomerId(customerId);
			bean.setPredictSucRate(predictSucRate);
			bean.setDescription(desc);
			String addOrUpdate = request.getParameter("addOrUpdate");
			StafferBean stafferBean = stafferDAO.find(user.getStafferId());
			if ("0".equals(addOrUpdate))
			{
				bean.setCreater(user.getStafferId());
				projectManager.addProjectBean(user, bean);
			}
			else
			{
				bean.setCreater(user.getStafferId());
				projectManager.updateProjectBean(user, bean);
			}
			// 人员行项目保存
			if (null != s_prostafferId && s_prostafferId.length > 0)
			{
				stafferProjectManager.addStafferLineProject(user,
						s_prostafferId, role, bean.getId());
			}
			// 产品行项目保存
			if (null != s_projectproId && s_projectproId.length > 0)
			{
				this.proLineProjectManager.addProjectProLineProject(user,
						s_projectproId, procount, prounitprice, bean.getId());
			}
			// 提交
			projectManager.submitProject(user, bean.getId(), processId);

		}
		catch (Exception e)
		{
			_logger.error(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败");

			return mapping.findForward("error");
		}

		request.setAttribute(KeyConstant.MESSAGE, "成功提交申请");

		return mapping.findForward("queryProject");
	}

	/**
	 * addOrUpdateTask
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward addOrUpdateTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		TaskBean bean = new TaskBean();

		// 模板最多10M
		RequestDataStream rds = new RequestDataStream(request,
				1024 * 1024 * 10L);
		try
		{
			rds.parser();
		}
		catch (Exception e)
		{
			_logger.error(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:附件超过10M");

			return mapping.findForward("error");
		}
		try
		{
			BeanUtil.getBean(bean, rds.getParmterMap());

			User user = Helper.getUser(request);
			String dutyStafferID = rds.getParameter("dutyStafferID");
			String partakerids = rds.getParameter("partakerids");
			bean.setPartaker(partakerids);
			String tmpartakerids = partakerids + "," + dutyStafferID;
			String receiverid = rds.getParameter("receiverid");
			String taskCode = rds.getParameter("taskCode");
//			String applyer = user.getStafferId();
			String selType = rds.getParameter("selType");
			String oprType = rds.getParameter("oprType");

			String addOrUpdate = rds.getParameter("addOrUpdate");
			bean.setDutyStaffer(dutyStafferID);
			bean.setReceiver(receiverid);
			bean.setTaskCode(taskCode);
			bean.setCreator(user.getStafferId());
			bean.setCreatorName(user.getStafferName());

			ActionForward afor = parserAttachment(mapping, request, rds, bean);
			if (afor != null)
			{
				return afor;
			}

			rds.close();

			StafferBean stafferBean = stafferDAO.find(user.getStafferId());
			if ("0".equals(addOrUpdate))
			{
				taskManager.addTaskBean(user, bean);
			}
			else
			{
				taskManager.updateTaskBean(user, bean);
			}

			// 提交
			if (oprType.equals("1"))
			{
				taskManager.submitTask(user, bean.getId(), tmpartakerids);
			}
		}
		catch (MYException e)
		{
			_logger.error(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getErrorContent());

			return mapping.findForward("error");
		}

		request.setAttribute(KeyConstant.MESSAGE, "成功提交申请");

		return mapping.findForward("queryTask");
	}

	/**
	 * addOrUpdateAgreement
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward addOrUpdateAgreement(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		AgreementBean bean = new AgreementBean();

		// 模板最多10M
		RequestDataStream rds = new RequestDataStream(request,
				1024 * 1024 * 10L);
		try
		{
			rds.parser();
		}
		catch (Exception e)
		{
			_logger.error(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:附件超过10M");

			return mapping.findForward("error");
		}
		try
		{
			BeanUtil.getBean(bean, rds.getParmterMap());

			User user = Helper.getUser(request);

			String processId = rds.getParameter("processId");
			String partaid = rds.getParameter("partaid");
			String partbid = rds.getParameter("partbid");
			String beforeAgreeids = rds.getParameter("beforeAgreeids");
			String afterAgreeids = rds.getParameter("afterAgreeids");
			String pa_selType = rds.getParameter("pa_selType");
			String pb_selType = rds.getParameter("pb_selType");
			String refProject = rds.getParameter("s_projectId");

			// 产品行项目
			String proIds = rds.getParameter("proIds");
			String pCount = rds.getParameter("pCount");
			String proPrice = rds.getParameter("proPrice");
			String[] plineArr1 = proIds.split(",");
			String[] plineArr2 = pCount.split(",");
			String[] plineArr3 = proPrice.split(",");

			// 付款行项目
			List<String> TpayType = rds.getParameters("payType");
			List<String> Tmoney = rds.getParameters("payMoney");
			List<String> Ttime = rds.getParameters("payTime");
			List<String> Tdays = rds.getParameters("finishDays");
			List<String> TBefore = rds.getParameters("s_payBeforeids");
			List<String> Tafter = rds.getParameters("s_payAfterids");

			// 交付行项目
			List<String> TtransType = rds.getParameters("transType");
			List<String> Ttransobj = rds.getParameters("transobj");
			List<String> TtransobjCount = rds.getParameters("transobjCount");
			List<String> TtransTime = rds.getParameters("transTime");
			List<String> TtransDays = rds.getParameters("transDays");
			List<String> trancurrentTask = rds
					.getParameters("s_trancurrentTask1id");
			List<String> Treceiverid = rds.getParameters("receiverid");
			List<String> Ts_tranBeforeids = rds
					.getParameters("s_tranBeforeids");
			List<String> Ts_tranAfterids = rds.getParameters("s_tranAfterids");

			// 开票行项目
			List<String> TinvoiceType = rds.getParameters("invoiceType");
			List<String> TinvoiceMoney = rds.getParameters("invoiceMoney");
			List<String> TinvoiceTime = rds.getParameters("invoiceTime");
			List<String> TfinishiDays1 = rds.getParameters("finishiDays1");
			List<String> Ts_invoiceCurTask = rds
					.getParameters("s_invocurrentTask");
			List<String> Ts_invoiceBeforeids = rds
					.getParameters("s_invoiceBeforeids");
			List<String> Ts_invoiceAfterids = rds
					.getParameters("s_invoiceAfterids");

			bean.setProcessid(processId);
			bean.setParty_a(partaid);
			bean.setParty_b(partbid);
			bean.setBeforeAgreement(beforeAgreeids);
			bean.setAfterAgreement(afterAgreeids);
			bean.setParty_a(pa_selType + "," + partaid);
			bean.setParty_b(pb_selType + "," + partbid);
			bean.setRefProject(refProject);
			String addOrUpdate = rds.getParameter("addOrUpdate");
			ActionForward afor = parserAttachment1(mapping, request, rds, bean);
			if (afor != null)
			{
				return afor;
			}

			rds.close();
			StafferBean stafferBean = stafferDAO.find(user.getStafferId());
			if ("0".equals(addOrUpdate))
			{
				bean.setApplyer(user.getStafferId());
				agreementManager.addAgreementBean(user, bean);
			}
			else
			{
				bean.setApplyer(user.getStafferId());
				agreementManager.updateAgreementBean(user, bean);
			}
			// 产品行项目保存
			if (null != plineArr1 && plineArr1.length > 0)
			{
				proLineProjectManager.addProLineProject(user, plineArr1,
						plineArr2, plineArr3, bean.getId());
			}
			// 付款行项目保存
			if (null != TpayType && TpayType.size() > 0)
			{
				payLineProjectManager.addPayLineProject(user, TpayType, Tmoney,
						Ttime, Tdays, TBefore, Tafter, bean.getId());
			}
			// 交付行项目保存
			if (null != TtransType && TtransType.size() > 0)
			{
				this.tranLineProjectManager.addTranLineProject(user,
						TtransType, Ttransobj, TtransobjCount, TtransTime,
						TtransDays, trancurrentTask, Treceiverid,
						Ts_tranBeforeids, Ts_tranAfterids, bean.getId());
			}
			// 开票行项目保存
			if (null != TinvoiceType && TinvoiceType.size() > 0)
			{
				this.invoiceLineProjectManager.addInvoiceLineProject(user,
						TinvoiceType, TinvoiceMoney, TinvoiceTime,
						TfinishiDays1, Ts_invoiceCurTask, Ts_invoiceBeforeids,
						Ts_invoiceAfterids, bean.getId());
			}
			// 提交
			agreementManager.submitAgreement(user, bean.getId(), processId);

		}
		catch (Exception e)
		{
			_logger.error(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败");

			return mapping.findForward("error");
		}

		request.setAttribute(KeyConstant.MESSAGE, "成功提交申请");

		return mapping.findForward("queryAgreement");
	}

	/**
	 * findTask
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward findTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		User user = Helper.getUser(request);

		String id = request.getParameter("id");

		String appid = request.getParameter("appid");

		String update = request.getParameter("update");

		String ttflag = request.getParameter("ttflag");

		TaskVO bean = this.taskDAO.findVO(id);

		if (bean == null)
		{
			return ActionTools.toError("数据异常,请重新操作", mapping, request);
		}
		
		StafferVO staffervo = stafferDAO.findVO(bean.getApplyer());

		request.setAttribute("receiverid", bean.getReceiver());

		if ("1".equals(update)
				&& bean.getTaskStatus() != ProjectConstant.TASK_INIT)
		{
			return ActionTools.toError("不是保存状态的单据不能修改", mapping, request);
		}
		
		setHoureMin(bean);
		
		if (null != bean.getReceiver() && bean.getReceiver().length() > 0)
		{
			bean.setReceiver(bean.getReceiver().split(",")[0]);

			StafferVO receiver = stafferDAO.findVO(bean.getReceiver()
					.split(",")[0]);

			bean.setReceiverName(receiver.getName());
		}
		String parkers = bean.getPartaker();
		String partakerids = "";
		StringBuffer sbuffer = new StringBuffer();

		if (null != parkers && parkers.length() > 0)
		{
			String[] arr = parkers.split(",");
			for (int i = 0; i < arr.length; i++)
			{
				StafferBean sb = stafferDAO.find(arr[i]);
				if (i == arr.length - 1)
				{
					sbuffer.append(sb.getName());
					partakerids = partakerids + sb.getId();
				}
				else
				{
					sbuffer.append(sb.getName() + ",");
					partakerids = partakerids + sb.getId() + ",";
				}
			}
		}
		bean.setPartaker(sbuffer.toString());
		request.setAttribute("partakerids", partakerids);
		request.setAttribute("bean", bean);
		request.setAttribute("staffervo", staffervo);
		request.setAttribute("user", user);
		// 获取审批日志
		List<FlowLogBean> logs = flowLogDAO.queryEntityBeansByFK(id);
		List<FlowLogVO> logsVO = new ArrayList<FlowLogVO>();

		for (FlowLogBean flowLogBean : logs)
		{
			logsVO.add(ProjectHelper.getProjectFlowLogVO(flowLogBean));
		}
		request.setAttribute("logList", logsVO);
		// 获得当前的处理环节
		// TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(),
		// bean.getProjectStatus());

		// request.setAttribute("token", token);
		if (null != update && update.equals("1"))
		{
			return mapping.findForward("updateTask");
		}
		if ((null != ttflag && ttflag.equals("11")))
		{
			ProjectApproveBean pb = this.projectApproveDAO.find(appid);
			request.setAttribute("pb", pb);
			return mapping.findForward("processTask");
		}
		else
		{
			return mapping.findForward("detailTask");
		}
	}

	/**
	 * 〈功能详细描述〉
	 *
	 * @param bean
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private void setHoureMin(TaskVO bean) {
		String houre = StringTools.isNullOrNone(bean.getFinishTime()) ? "00" 
				: (bean.getFinishTime().trim().length() == 1 ? "0" + bean.getFinishTime().trim() : bean.getFinishTime().trim());
		
		String min = StringTools.isNullOrNone(bean.getFinishMin()) ? "00" 
				: (bean.getFinishMin().trim().length() == 1 ? "0" + bean.getFinishMin().trim() : bean.getFinishMin().trim());
		
		bean.setFinishFullTime(houre + ":" + min);
	}

	/**
	 * deleteTravelApply
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward deleteTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		AjaxResult ajax = new AjaxResult();

		try
		{
			String id = request.getParameter("id");

			User user = Helper.getUser(request);

			taskManager.deleteTaskBean(user, id);

			ajax.setSuccess("成功操作");
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			ajax.setError("操作失败:" + e.getMessage());
		}

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * findProject
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward findProject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		User user = Helper.getUser(request);

		String id = request.getParameter("id");
		String update = request.getParameter("update");
		String ttflag = request.getParameter("ttflag");

		ProjectVO bean = this.projectManager.findVO(id);
		StafferVO staffervo = stafferDAO.findVO(bean.getCreater());
		if (bean == null)
		{
			return ActionTools.toError("数据异常,请重新操作", mapping, request);
		}

		List<StafferProjectVO> spList = this.stafferProjectDAO
				.queryEntityVOsByFK(id);
		List<ProLineProjectVO> proLineProjectList = this.proLineProjectDAO
				.queryEntityVOsByFK(id);
		bean.setStafferProjectList(spList);
		bean.setProLineProjectList(proLineProjectList);
		request.setAttribute("update", update);
		request.setAttribute("bean", bean);
		request.setAttribute("staffervo", staffervo);
		// 获取审批日志
		List<FlowLogBean> logs = flowLogDAO.queryEntityBeansByFK(id);
		List<FlowLogVO> logsVO = new ArrayList<FlowLogVO>();

		for (FlowLogBean flowLogBean : logs)
		{
			// logsVO.add(TCPHelper.getTCPFlowLogVO(flowLogBean));
		}
		request.setAttribute("logList", logsVO);
		// 获得当前的处理环节
		// TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(),
		// bean.getProjectStatus());

		// request.setAttribute("token", token);
		if (null != update && update.equals("1"))
		{
			return mapping.findForward("updateProject");
		}
		else if ((null != ttflag && ttflag.equals("11"))
				|| (null != update && update.equals("1")))
		{
			return mapping.findForward("processProject");
		}
		else
		{
			return mapping.findForward("detailProject");
		}
	}

	/**
	 * findAgreement
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward findAgreement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		User user = Helper.getUser(request);

		String id = request.getParameter("id");
		String update = request.getParameter("update");
		AgreementVO bean = this.agreementManager.findVO(id);
		StafferVO staffervo = stafferDAO.findVO(bean.getApplyer());
		if (bean == null)
		{
			return ActionTools.toError("数据异常,请重新操作", mapping, request);
		}
		String parta = bean.getParty_a();
		String partb = bean.getParty_b();
		if (null != parta && parta.length() > 0)
		{
			String[] pa = parta.split(",");
			if (pa != null && pa.length > 1 && pa[0].equals("1"))
			{
				if (null != stafferDAO.find(pa[1]))
				{
					String sname = stafferDAO.find(pa[1]).getName();
					bean.setParty_a(sname);
				}
				else
				{
					bean.setParty_a("");
				}
			}
			else if (pa != null && pa.length > 1 && pa[0].equals("2"))
			{
				if (null != customerMainDAO.find(pa[1]))
				{
					String cname = customerMainDAO.find(pa[1]).getName();
					bean.setParty_a(cname);
				}
				else
				{
					bean.setParty_a("");
				}
			}
			else if (pa != null && pa.length > 1 && pa[0].equals("3"))
			{
				if (null != providerDAO.find(pa[1]))
				{
					String pname = providerDAO.find(pa[1]).getName();
					bean.setParty_a(pname);
				}
				else
				{
					bean.setParty_a("");
				}
			}
			else if (pa != null && pa.length > 1 && pa[0].equals("4"))
			{
				if (null != dutyDAO.find(pa[1]))
				{
					String dname = dutyDAO.find(pa[1]).getName();
					bean.setParty_a(dname);
				}
				else
				{
					bean.setParty_a("");
				}
			}
		}

		if (null != partb && partb.length() > 0)
		{
			String[] pb = partb.split(",");
			if (pb != null && pb.length > 1 && pb[0].equals("1"))
			{
				if (null != stafferDAO.find(pb[1]))
				{
					String sname = stafferDAO.find(pb[1]).getName();
					bean.setParty_b(sname);
				}
				else
				{
					bean.setParty_b("");
				}
			}
			else if (pb != null && pb.length > 1 && pb[0].equals("2"))
			{
				if (null != customerMainDAO.find(pb[1]))
				{
					String cname = customerMainDAO.find(pb[1]).getName();
					bean.setParty_b(cname);
				}
				else
				{
					bean.setParty_b("");
				}
			}
			else if (pb != null && pb.length > 1 && pb[0].equals("3"))
			{
				if (null != providerDAO.find(pb[1]))
				{
					String pname = providerDAO.find(pb[1]).getName();
					bean.setParty_b(pname);
				}
				else
				{
					bean.setParty_b("");
				}
			}
			else if (pb != null && pb.length > 1 && pb[0].equals("4"))
			{
				if (null != dutyDAO.find(pb[1]))
				{
					String dname = dutyDAO.find(pb[1]).getName();
					bean.setParty_b(dname);
				}
				else
				{
					bean.setParty_b("");
				}
			}
		}
		String beanBefore = bean.getBeforeAgreement();
		String beanAfter = bean.getAfterAgreement();
		if (null != beanBefore && beanBefore.length() > 0)
		{
			String[] arrBefore = beanBefore.split(",");
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < arrBefore.length; j++)
			{
				TaskBean tb = taskDAO.find(arrBefore[j]);
				sb.append(tb.getTaskName()).append(",");
			}
			bean.setBeforeAgreement(sb.toString());
		}
		if (null != beanAfter && beanAfter.length() > 0)
		{
			String[] arrBefore = beanAfter.split(",");
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < arrBefore.length; j++)
			{
				TaskBean tb = taskDAO.find(arrBefore[j]);
				sb.append(tb.getTaskName()).append(",");
			}
			bean.setAfterAgreement(sb.toString());
		}
		List<ProLineProjectVO> proLineList = proLineProjectDAO
				.queryEntityVOsByFK(id);
		List<PayLineProjectVO> tempPayLineProjectList = this.payLineProjectDAO
				.queryEntityVOsByFK(id);
		List<TransLineProjectVO> temptransLineProjectList = tranLineProjectDAO
				.queryEntityVOsByFK(id);
		List<InvoiceLineProjectVO> tempinvoiceLineProjectList = invoiceLineProjectDAO
				.queryEntityVOsByFK(id);
		bean.setProLineList(proLineList);

		// 付款行项目
		if (null != tempPayLineProjectList && tempPayLineProjectList.size() > 0)
		{
			for (int i = 0; i < tempPayLineProjectList.size(); i++)
			{
				String before = tempPayLineProjectList.get(i).getBeforeTask();
				String after = tempPayLineProjectList.get(i).getAfterTask();
				if (null != before && before.length() > 0)
				{
					String[] arrBefore = before.split(",");
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < arrBefore.length; j++)
					{
						TaskBean tb = taskDAO.find(arrBefore[j]);
						sb.append(tb.getTaskName()).append(",");
					}
					tempPayLineProjectList.get(i).setBeforeTask(sb.toString());
				}

				if (null != after && after.length() > 0)
				{
					String[] arrAfter = after.split(",");
					StringBuffer sa = new StringBuffer();
					for (int j = 0; j < arrAfter.length; j++)
					{
						TaskBean tb1 = taskDAO.find(arrAfter[j]);
						sa.append(tb1.getTaskName()).append(",");
					}
					tempPayLineProjectList.get(i).setAfterTask(sa.toString());
				}

			}
		}

		bean.setPayLineProjectList(tempPayLineProjectList);

		// 交付行项目
		if (null != temptransLineProjectList
				&& temptransLineProjectList.size() > 0)
		{
			for (int i = 0; i < temptransLineProjectList.size(); i++)
			{
				String before = temptransLineProjectList.get(i)
						.getBeforeTask1();
				String after = temptransLineProjectList.get(i).getAfterTask1();
				String receiver = temptransLineProjectList.get(i).getReceiver();
				if (null != receiver && receiver.length() > 0)
				{
					String[] rv = receiver.split(",");

					if (rv != null && rv.length > 1 && rv[1].equals("1"))
					{
						if (null != stafferDAO.find(rv[0]))
						{
							String sname = stafferDAO.find(rv[0]).getName();
							temptransLineProjectList.get(i).setReceiver(sname);
						}
						else
						{
							temptransLineProjectList.get(i).setReceiver("");
						}
					}
					else if (rv != null && rv.length > 1 && rv[1].equals("2"))
					{
						if (null != customerMainDAO.find(rv[0]))
						{
							String cname = customerMainDAO.find(rv[0]).getName();
							temptransLineProjectList.get(i).setReceiver(cname);
						}
						else
						{
							temptransLineProjectList.get(i).setReceiver("");
						}
					}
					else if (rv != null && rv.length > 1 && rv[1].equals("3"))
					{
						if (null != providerDAO.find(rv[0]))
						{
							String pname = providerDAO.find(rv[0]).getName();
							temptransLineProjectList.get(i).setReceiver(pname);
						}
						else
						{
							temptransLineProjectList.get(i).setReceiver("");
						}
					}
					else if (rv != null && rv.length > 1 && rv[1].equals("4"))
					{
						if (null != dutyDAO.find(rv[0]))
						{
							String dname = dutyDAO.find(rv[0]).getName();
							temptransLineProjectList.get(i).setReceiver(dname);
						}
						else
						{
							temptransLineProjectList.get(i).setReceiver("");
						}
					}
				}

				if (null != before && before.length() > 0)
				{
					String[] arrBefore = before.split(",");
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < arrBefore.length; j++)
					{
						TaskBean tb = taskDAO.find(arrBefore[j]);
						sb.append(tb.getTaskName()).append(",");
					}
					temptransLineProjectList.get(i).setBeforeTask1(
							sb.toString());
				}

				if (null != after && after.length() > 0)
				{
					String[] arrAfter = after.split(",");
					StringBuffer sa = new StringBuffer();
					for (int j = 0; j < arrAfter.length; j++)
					{
						TaskBean tb1 = taskDAO.find(arrAfter[j]);
						sa.append(tb1.getTaskName()).append(",");
					}
					temptransLineProjectList.get(i)
							.setAfterTask1(sa.toString());
				}
			}
		}

		bean.setTransLineProjectList(temptransLineProjectList);
		// //开票行项目
		if (null != tempinvoiceLineProjectList
				&& tempinvoiceLineProjectList.size() > 0)
		{
			for (int i = 0; i < tempinvoiceLineProjectList.size(); i++)
			{
				String before = tempinvoiceLineProjectList.get(i)
						.getBeforeTask2();
				String after = tempinvoiceLineProjectList.get(i)
						.getAfterTask2();
				if (null != before && before.length() > 0)
				{
					String[] arrBefore = before.split(",");
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < arrBefore.length; j++)
					{
						TaskBean tb = taskDAO.find(arrBefore[j]);
						sb.append(tb.getTaskName()).append(",");
					}
					tempinvoiceLineProjectList.get(i).setBeforeTask2(
							sb.toString());
				}

				if (null != after && after.length() > 0)
				{
					String[] arrAfter = after.split(",");
					StringBuffer sa = new StringBuffer();
					for (int j = 0; j < arrAfter.length; j++)
					{
						TaskBean tb1 = taskDAO.find(arrAfter[j]);
						sa.append(tb1.getTaskName()).append(",");
					}
					tempinvoiceLineProjectList.get(i).setAfterTask2(
							sa.toString());
				}

			}
		}

		bean.setInvoiceLineProjectList(tempinvoiceLineProjectList);
		request.setAttribute("update", update);
		// request.setAttribute("partakerids", partakerids);
		request.setAttribute("bean", bean);
		request.setAttribute("staffervo", staffervo);
		// 获取审批日志
		List<FlowLogBean> logs = flowLogDAO.queryEntityBeansByFK(id);
		List<FlowLogVO> logsVO = new ArrayList<FlowLogVO>();

		for (FlowLogBean flowLogBean : logs)
		{
			// logsVO.add(TCPHelper.getTCPFlowLogVO(flowLogBean));
		}
		request.setAttribute("logList", logsVO);
		if (null != update && update.equals("1"))
		{
			return mapping.findForward("updateAgreement");
		}
		else
		{
			return mapping.findForward("detailAgreement");
		}
	}

	/**
	 * parserAttachment
	 * 
	 * @param mapping
	 * @param request
	 * @param rds
	 * @param bean
	 * @return
	 */
	private ActionForward parserAttachment(ActionMapping mapping,
			HttpServletRequest request, RequestDataStream rds, TaskBean taskBean)
	{
		List<AttachmentBean> attachmentList = new ArrayList<AttachmentBean>();

		taskBean.setAttachmentList(attachmentList);

		String addOrUpdate = rds.getParameter("addOrUpdate");

		// 更新新加入之前
		if ("1".equals(addOrUpdate))
		{
			String attacmentIds = rds.getParameter("filePath");
			if (null != attacmentIds && attacmentIds.length() > 0)
			{
				String[] split = attacmentIds.split(";");

				for (String each : split)
				{
					if (StringTools.isNullOrNone(each))
					{
						continue;
					}

					AttachmentBean att = attachmentDAO.find(each);

					if (att != null)
					{
						attachmentList.add(att);
					}
				}
			}
		}

		// parser attachment
		if (!rds.haveStream())
		{
			return null;
		}

		Map<String, InputStream> streamMap = rds.getStreamMap();

		for (Map.Entry<String, InputStream> entry : streamMap.entrySet())
		{
			AttachmentBean bean = new AttachmentBean();

			FileOutputStream out = null;

			UtilStream ustream = null;

			try
			{
				String savePath = mkdir(this.getAttachmentPath());

				String fileAlais = SequenceTools.getSequence();

				String fileName = FileTools.getFileName(rds.getFileName(entry
						.getKey()));

				String rabsPath = '/' + savePath + '/' + fileAlais + "."
						+ FileTools.getFilePostfix(fileName).toLowerCase();

				String filePath = this.getAttachmentPath() + '/' + rabsPath;

				bean.setName(fileName);

				bean.setPath(rabsPath);

				bean.setLogTime(TimeTools.now());

				out = new FileOutputStream(filePath);

				ustream = new UtilStream(entry.getValue(), out);

				ustream.copyStream();

				attachmentList.add(bean);
			}
			catch (IOException e)
			{
				_logger.error(e, e);

				request.setAttribute(KeyConstant.ERROR_MESSAGE, "保存失败");

				return mapping.findForward("queryTask");
			}
			finally
			{
				if (ustream != null)
				{
					try
					{
						ustream.close();
					}
					catch (IOException e)
					{
						_logger.error(e, e);
					}
				}
			}
		}

		return null;
	}

	/**
	 * parserAttachment
	 * 
	 * @param mapping
	 * @param request
	 * @param rds
	 * @param bean
	 * @return
	 */
	private ActionForward parserAttachment1(ActionMapping mapping,
			HttpServletRequest request, RequestDataStream rds,
			AgreementBean agreementBean)
	{
		List<AttachmentBean> attachmentList = new ArrayList<AttachmentBean>();

		agreementBean.setAttachmentList(attachmentList);

		String addOrUpdate = rds.getParameter("addOrUpdate");

		// 更新新加入之前
		if ("1".equals(addOrUpdate))
		{
			String attacmentIds = rds.getParameter("filePath");
			if (null != attacmentIds && attacmentIds.length() > 0)
			{
				String[] split = attacmentIds.split(";");

				for (String each : split)
				{
					if (StringTools.isNullOrNone(each))
					{
						continue;
					}

					AttachmentBean att = attachmentDAO.find(each);

					if (att != null)
					{
						attachmentList.add(att);
					}
				}
			}
		}

		// parser attachment
		if (!rds.haveStream())
		{
			return null;
		}

		Map<String, InputStream> streamMap = rds.getStreamMap();

		for (Map.Entry<String, InputStream> entry : streamMap.entrySet())
		{
			AttachmentBean bean = new AttachmentBean();

			FileOutputStream out = null;

			UtilStream ustream = null;

			try
			{
				String savePath = mkdir(this.getAttachmentPath());

				String fileAlais = SequenceTools.getSequence();

				String fileName = FileTools.getFileName(rds.getFileName(entry
						.getKey()));

				String rabsPath = '/' + savePath + '/' + fileAlais + "."
						+ FileTools.getFilePostfix(fileName).toLowerCase();

				String filePath = this.getAttachmentPath() + '/' + rabsPath;

				bean.setName(fileName);

				bean.setPath(rabsPath);

				bean.setLogTime(TimeTools.now());

				out = new FileOutputStream(filePath);

				ustream = new UtilStream(entry.getValue(), out);

				ustream.copyStream();

				attachmentList.add(bean);
			}
			catch (IOException e)
			{
				_logger.error(e, e);

				request.setAttribute(KeyConstant.ERROR_MESSAGE, "保存失败");

				return mapping.findForward("queryTask");
			}
			finally
			{
				if (ustream != null)
				{
					try
					{
						ustream.close();
					}
					catch (IOException e)
					{
						_logger.error(e, e);
					}
				}
			}
		}

		return null;
	}

	/**
	 * parserAttachment
	 * 
	 * @param mapping
	 * @param request
	 * @param rds
	 * @param bean
	 * @return
	 */
	private ActionForward parserAttachment2(ActionMapping mapping,
			HttpServletRequest request, RequestDataStream rds,
			ProjectBean projectBean)
	{
		List<AttachmentBean> attachmentList = new ArrayList<AttachmentBean>();

		projectBean.setAttachmentList(attachmentList);

		String addOrUpdate = rds.getParameter("addOrUpdate");

		// 更新新加入之前
		if ("1".equals(addOrUpdate))
		{
			String attacmentIds = rds.getParameter("filePath");
			if (null != attacmentIds && attacmentIds.length() > 0)
			{
				String[] split = attacmentIds.split(";");

				for (String each : split)
				{
					if (StringTools.isNullOrNone(each))
					{
						continue;
					}

					AttachmentBean att = attachmentDAO.find(each);

					if (att != null)
					{
						attachmentList.add(att);
					}
				}
			}
		}

		// parser attachment
		if (!rds.haveStream())
		{
			return null;
		}

		Map<String, InputStream> streamMap = rds.getStreamMap();

		for (Map.Entry<String, InputStream> entry : streamMap.entrySet())
		{
			AttachmentBean bean = new AttachmentBean();

			FileOutputStream out = null;

			UtilStream ustream = null;

			try
			{
				String savePath = mkdir(this.getAttachmentPath());

				String fileAlais = SequenceTools.getSequence();

				String fileName = FileTools.getFileName(rds.getFileName(entry
						.getKey()));

				String rabsPath = '/' + savePath + '/' + fileAlais + "."
						+ FileTools.getFilePostfix(fileName).toLowerCase();

				String filePath = this.getAttachmentPath() + '/' + rabsPath;

				bean.setName(fileName);

				bean.setPath(rabsPath);

				bean.setLogTime(TimeTools.now());

				out = new FileOutputStream(filePath);

				ustream = new UtilStream(entry.getValue(), out);

				ustream.copyStream();

				attachmentList.add(bean);
			}
			catch (IOException e)
			{
				_logger.error(e, e);

				request.setAttribute(KeyConstant.ERROR_MESSAGE, "保存失败");

				return mapping.findForward("queryProject");
			}
			finally
			{
				if (ustream != null)
				{
					try
					{
						ustream.close();
					}
					catch (IOException e)
					{
						_logger.error(e, e);
					}
				}
			}
		}

		return null;
	}

	private String mkdir(String root)
	{
		String path = TimeTools.now("yyyy/MM/dd/HH")
				+ "/"
				+ SequenceTools.getSequence(String.valueOf(new Random()
						.nextInt(1000)));

		FileTools.mkdirs(root + '/' + path);

		return path;
	}

	/**
	 * @return the flowAtt
	 */
	public String getAttachmentPath()
	{
		return ConfigLoader.getProperty("projectAttachmentPath");
	}

	/**
	 * selectpartyA
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward selectpartyA(ActionMapping mapping, ActionForm form,
			final HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		CommonTools.saveParamers(request);

		User user = Helper.getUser(request);

		final String stafferId = user.getStafferId();

		String selType = request.getParameter("selType");

		request.setAttribute("selType", selType);

		String selName = request.getParameter("selName");

		if (null != selType && selType.equals("1"))
		{
			StafferBean stafferBean = stafferDAO.findyStafferByName(selName);
			List<StafferBean> list = new ArrayList<StafferBean>();
			if (null == stafferBean)
			{
				list = null;
			}
			else
			{
				list.add(stafferBean);
				request.setAttribute("beanList", list);
			}
		}
		else if (null != selType && selType.equals("2"))
		{
			List<CustomerBean> cuslist = null;
			cuslist = customerMainDAO.querySelfCusByStafferId(stafferId);
			request.setAttribute("beanList", cuslist);
		}
		else if (null != selType && selType.equals("3"))
		{
			List<ProviderBean> providerList = null;
			providerList = providerDAO.findProviderByName(selName);
			request.setAttribute("beanList", providerList);
		}
		else if (null != selType && selType.equals("4"))
		{
			List<DutyBean> dutyList = new ArrayList<DutyBean>();
			DutyBean duty = dutyDAO.findyDutyByName(selName);
			if (null != duty)
			{
				dutyList.add(duty);
			}
			else
			{
				dutyList = null;
			}
			request.setAttribute("beanList", dutyList);
		}

		return mapping.findForward("selectpartyA");
	}

	/**
	 * selectpartyB
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward selectpartyB(ActionMapping mapping, ActionForm form,
			final HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		CommonTools.saveParamers(request);

		User user = Helper.getUser(request);

		final String stafferId = user.getStafferId();

		String selType = request.getParameter("selType");

		request.setAttribute("selType", selType);

		String selName = request.getParameter("selName");

		if (null != selType && selType.equals("1"))
		{
			StafferBean stafferBean = stafferDAO.findyStafferByName(selName);
			List<StafferBean> list = new ArrayList<StafferBean>();
			if (null == stafferBean)
			{
				list = null;
			}
			else
			{
				list.add(stafferBean);
				request.setAttribute("beanList", list);
			}
		}
		else if (null != selType && selType.equals("2"))
		{
			List<CustomerBean> cuslist = null;
			cuslist = customerMainDAO.querySelfCusByStafferId(stafferId);
			request.setAttribute("beanList", cuslist);
		}
		else if (null != selType && selType.equals("3"))
		{
			List<ProviderBean> providerList = null;
			providerList = providerDAO.findProviderByName(selName);
			request.setAttribute("beanList", providerList);
		}
		else if (null != selType && selType.equals("4"))
		{
			List<DutyBean> dutyList = new ArrayList<DutyBean>();
			DutyBean duty = dutyDAO.findyDutyByName(selName);
			if (null != duty)
			{
				dutyList.add(duty);
			}
			else
			{
				dutyList = null;
			}
			request.setAttribute("beanList", dutyList);
		}
		return mapping.findForward("selectpartyB");
	}

	/**
	 * selectReceiver
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward selectReceiver(ActionMapping mapping, ActionForm form,
			final HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		CommonTools.saveParamers(request);

		User user = Helper.getUser(request);

		final String stafferId = user.getStafferId();

		String selType = request.getParameter("selType");

		request.setAttribute("selType", selType);

		String selName = request.getParameter("selName");

		if (null != selType && selType.equals("1"))
		{
			List<StafferBean> list = new ArrayList<StafferBean>();

			if (!StringTools.isNullOrNone(selName))
			{
				ConditionParse con = new ConditionParse();
				
				con.addWhereStr();
				
				con.addCondition("StafferBean.name", "like", selName);
				
				list = stafferDAO.queryEntityBeansByCondition(con);
			}
			
			request.setAttribute("beanList", list);
		}
		else if (null != selType && selType.equals("2"))
		{
			List<CustomerBean> cuslist = null;
			cuslist = customerMainDAO.querySelfCusByStafferId(stafferId);
			request.setAttribute("beanList", cuslist);
		}
		else if (null != selType && selType.equals("3"))
		{
			List<ProviderBean> providerList = null;
			providerList = providerDAO.findProviderByName(selName);
			request.setAttribute("beanList", providerList);
		}

		return mapping.findForward("selectReceiver");
	}

	/**
	 * selectProject
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward selectProject(ActionMapping mapping, ActionForm form,
			final HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		CommonTools.saveParamers(request);
		String name = request.getParameter("name");
		String code = request.getParameter("code");
		String flag = request.getParameter("flag");
		request.setAttribute("name", name);
		request.setAttribute("code", code);

		User user = Helper.getUser(request);

		List<ProjectVO> listvo = new ArrayList<ProjectVO>();

		if (null != flag && flag.equals("0"))
		{
			request.setAttribute("beanList", listvo);
			return mapping.findForward("selectProject");
		}

		List<ProjectVO> list = projectDAO.listEntityVOs();

		for (ProjectVO vo : list)
		{
			// if(vo.getId().equals(staffid))
			// {
			// continue;//处理不提交给自己
			// }

			if (!StringTools.isNullOrNone(name))
			{
				if (!vo.getProjectName().contains(name))
				{
					continue;
				}
			}
			if (!StringTools.isNullOrNone(code))
			{
				if (!vo.getProjectCode().equals(code.trim()))
				{
					continue;
				}
			}

			listvo.add(vo);
		}

		request.setAttribute("beanList", listvo);
		return mapping.findForward("selectProject");
	}

	/**
	 * selectBeforeAgre
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward selectBeforeAgre(ActionMapping mapping,
			ActionForm form, final HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		CommonTools.saveParamers(request);

		ConditionParse condtion = new ConditionParse();

		condtion.addCondition("TaskBean.taskStatus", "=",
				ProjectConstant.TASK_STAGE_ONE);

		condtion.addWhereStr();

		List<TaskBean> list = taskDAO.queryEntityBeansByCondition(condtion
				.toString());

		request.setAttribute("beanList", list);

		return mapping.findForward("selectBeforeAgre");
	}

	/**
	 * selectPaycurrentTask1
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward selectPaycurrentTask1(ActionMapping mapping,
			ActionForm form, final HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		CommonTools.saveParamers(request);

		ConditionParse condtion = new ConditionParse();

		condtion.addCondition("TaskBean.taskStatus", "=",
				ProjectConstant.TASK_STAGE_ONE);

		condtion.addWhereStr();

		List<TaskBean> list = taskDAO.queryEntityBeansByCondition(condtion
				.toString());

		request.setAttribute("beanList", list);

		return mapping.findForward("selectPaycurrentTask1");
	}

	/**
	 * selectTrancurrentTask1
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward selectTrancurrentTask1(ActionMapping mapping,
			ActionForm form, final HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		CommonTools.saveParamers(request);

		ConditionParse condtion = new ConditionParse();

		condtion.addCondition("TaskBean.taskStatus", "=",
				ProjectConstant.TASK_STAGE_ONE);

		condtion.addWhereStr();

		List<TaskBean> list = taskDAO.queryEntityBeansByCondition(condtion
				.toString());

		request.setAttribute("beanList", list);

		return mapping.findForward("selectTrancurrentTask1");
	}

	/**
	 * selectinvocurrentTask
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward selectinvocurrentTask(ActionMapping mapping,
			ActionForm form, final HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		CommonTools.saveParamers(request);

		ConditionParse condtion = new ConditionParse();

		condtion.addCondition("TaskBean.taskStatus", "=",
				ProjectConstant.TASK_STAGE_ONE);

		condtion.addWhereStr();

		List<TaskBean> list = taskDAO.queryEntityBeansByCondition(condtion
				.toString());

		request.setAttribute("beanList", list);

		return mapping.findForward("selectinvocurrentTask");
	}

	/**
	 * selectAfterAgre
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward selectAfterAgre(ActionMapping mapping,
			ActionForm form, final HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		CommonTools.saveParamers(request);

		String selType = request.getParameter("selType");

		String selName = request.getParameter("selName");

		ConditionParse condtion = new ConditionParse();

		condtion.addCondition("TaskBean.taskStatus", "=",
				ProjectConstant.TASK_STAGE_ONE);

		condtion.addWhereStr();

		List<TaskBean> list = taskDAO.queryEntityBeansByCondition(condtion
				.toString());

		request.setAttribute("beanList", list);

		return mapping.findForward("selectAfterAgre");
	}

	/**
	 * selectPayBeforeTask
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward selectPayBeforeTask(ActionMapping mapping,
			ActionForm form, final HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		CommonTools.saveParamers(request);

		String selType = request.getParameter("selType");

		String selName = request.getParameter("selName");

		ConditionParse condtion = new ConditionParse();

		condtion.addCondition("TaskBean.taskStatus", "=",
				ProjectConstant.TASK_STAGE_ONE);

		condtion.addWhereStr();

		List<TaskBean> list = taskDAO.queryEntityBeansByCondition(condtion
				.toString());

		request.setAttribute("beanList", list);

		return mapping.findForward("selectPayBeforeTask");
	}

	/**
	 * selectPayAfterTask
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward selectPayAfterTask(ActionMapping mapping,
			ActionForm form, final HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		CommonTools.saveParamers(request);

		String selType = request.getParameter("selType");

		String selName = request.getParameter("selName");

		ConditionParse condtion = new ConditionParse();

		condtion.addCondition("TaskBean.taskStatus", "=",
				ProjectConstant.TASK_STAGE_ONE);

		condtion.addWhereStr();

		List<TaskBean> list = taskDAO.queryEntityBeansByCondition(condtion
				.toString());

		request.setAttribute("beanList", list);

		return mapping.findForward("selectPayAfterTask");
	}

	/**
	 * selectTranBeforeTask
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward selectTranBeforeTask(ActionMapping mapping,
			ActionForm form, final HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		CommonTools.saveParamers(request);

		String selType = request.getParameter("selType");

		String selName = request.getParameter("selName");

		ConditionParse condtion = new ConditionParse();

		condtion.addCondition("TaskBean.taskStatus", "=",
				ProjectConstant.TASK_STAGE_ONE);

		condtion.addWhereStr();

		List<TaskBean> list = taskDAO.queryEntityBeansByCondition(condtion
				.toString());

		request.setAttribute("beanList", list);

		return mapping.findForward("selectTranBeforeTask");
	}

	/**
	 * selectPayAfterTask
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward selectTranAfterTask(ActionMapping mapping,
			ActionForm form, final HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		CommonTools.saveParamers(request);

		String selType = request.getParameter("selType");

		String selName = request.getParameter("selName");

		ConditionParse condtion = new ConditionParse();

		condtion.addCondition("TaskBean.taskStatus", "=",
				ProjectConstant.TASK_STAGE_ONE);

		condtion.addWhereStr();

		List<TaskBean> list = taskDAO.queryEntityBeansByCondition(condtion
				.toString());

		request.setAttribute("beanList", list);
		return mapping.findForward("selectTranAfterTask");
	}

	/**
	 * selectInvoiceBeforeTask
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward selectInvoiceBeforeTask(ActionMapping mapping,
			ActionForm form, final HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		CommonTools.saveParamers(request);

		String selType = request.getParameter("selType");

		String selName = request.getParameter("selName");

		ConditionParse condtion = new ConditionParse();

		condtion.addCondition("TaskBean.taskStatus", "=",
				ProjectConstant.TASK_STAGE_ONE);

		condtion.addWhereStr();

		List<TaskBean> list = taskDAO.queryEntityBeansByCondition(condtion
				.toString());

		request.setAttribute("beanList", list);
		return mapping.findForward("selectInvoiceBeforeTask");
	}

	/**
	 * selectInvoiceAfterTask
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward selectInvoiceAfterTask(ActionMapping mapping,
			ActionForm form, final HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		CommonTools.saveParamers(request);

		String selType = request.getParameter("selType");

		String selName = request.getParameter("selName");

		ConditionParse condtion = new ConditionParse();

		condtion.addCondition("TaskBean.taskStatus", "=",
				ProjectConstant.TASK_STAGE_ONE);

		condtion.addWhereStr();

		List<TaskBean> list = taskDAO.queryEntityBeansByCondition(condtion
				.toString());

		request.setAttribute("beanList", list);

		return mapping.findForward("selectInvoiceAfterTask");
	}

	/**
	 * selectInvoiceAfterTask
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward selectTaskBefore(ActionMapping mapping,
			ActionForm form, final HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		CommonTools.saveParamers(request);

		String selType = request.getParameter("selType");

		String selName = request.getParameter("selName");

		ConditionParse condtion = new ConditionParse();

		condtion.addCondition("TaskBean.taskStatus", "=",
				ProjectConstant.TASK_STAGE_ONE);

		condtion.addWhereStr();

		List<TaskBean> list = taskDAO.queryEntityBeansByCondition(condtion
				.toString());

		request.setAttribute("beanList", list);

		return mapping.findForward("selectTaskBefore");
	}

	/**
	 * selectTaskAfter
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward selectTaskAfter(ActionMapping mapping,
			ActionForm form, final HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		CommonTools.saveParamers(request);

		String selType = request.getParameter("selType");

		String selName = request.getParameter("selName");

		ConditionParse condtion = new ConditionParse();

		condtion.addCondition("TaskBean.taskStatus", "=",
				ProjectConstant.TASK_STAGE_TWO);

		condtion.addWhereStr();

		List<TaskBean> list = taskDAO.queryEntityBeansByCondition(condtion
				.toString());

		request.setAttribute("beanList", list);

		return mapping.findForward("selectTaskAfter");
	}

	/**
	 * queryProject
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryProject(ActionMapping mapping, ActionForm form,
			final HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		final ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		ActionTools.processJSONQueryCondition(QUERYPROJECT, request, condtion);

		condtion.addCondition("order by ProjectBean.applyTime desc");

		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYPROJECT,
				request, condtion, this.projectDAO);

		return JSONTools.writeResponse(response, jsonstr);
	}

	/**
	 * queryAgreement
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryAgreement(ActionMapping mapping, ActionForm form,
			final HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		final ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		ActionTools
				.processJSONQueryCondition(QUERYAGREEMENT, request, condtion);

		condtion.addCondition("order by AgreementBean.signDate desc");

		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYAGREEMENT,
				request, condtion, this.agreementDAO);

		return JSONTools.writeResponse(response, jsonstr);
	}

	/**
	 * 获取TAB页合同列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward getTabAgreement(ActionMapping mapping,
			ActionForm form, final HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		CommonTools.saveParamers(request);

		ConditionParse condtion = new ConditionParse();

		// condtion.addCondition("AgreementBean.taskStatus", "=",
		// ProjectConstant.TASK_STAGE_TWO);

		// condtion.addWhereStr();
		// List<AgreementBean> list =
		// this.agreementDAO.queryEntityBeansByCondition(condtion.toString());
		List<AgreementVO> list = this.agreementDAO.listEntityVOs();
		request.setAttribute("beanList", list);

		return mapping.findForward("getTabAgreement");
	}

	/**
	 * 获取TAB页任务列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryTabTask(ActionMapping mapping, ActionForm form,
			final HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		CommonTools.saveParamers(request);

		ConditionParse condtion = new ConditionParse();

		// condtion.addCondition("AgreementBean.taskStatus", "=",
		// ProjectConstant.TASK_STAGE_TWO);

		// condtion.addWhereStr();
		// List<AgreementBean> list =
		// this.agreementDAO.queryEntityBeansByCondition(condtion.toString());
		List<TaskVO> list = this.taskDAO.listEntityVOs();
		request.setAttribute("beanList", list);

		return mapping.findForward("queryTabTask");
	}

	/**
	 * queryTask
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryTask(ActionMapping mapping, ActionForm form,
			final HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{

		User user = Helper.getUser(request);

		final ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		ActionTools.processJSONQueryCondition(QUERYPROJECTTASK, request, condtion);

		condtion.addCondition("TaskBean.applyer", "=", user.getStafferId());

		condtion.addCondition("order by TaskBean.planFinishTime desc");

		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYPROJECTTASK,
				request, condtion, this.taskDAO, new HandleResult<TaskVO>()
				{
					public void handle(TaskVO vo)
					{
						if (null != vo.getReceiver()
								&& vo.getReceiver().length() > 0)
						{
							StafferBean sb = stafferDAO.find(vo.getReceiver()
									.split(",")[0]);
							vo.setReceiverName(sb.getName());
						}
					}
				});

		return JSONTools.writeResponse(response, jsonstr);
	}
	
	/**
	 * queryAllTask
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryAllTask(ActionMapping mapping, ActionForm form,
			final HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		User user = Helper.getUser(request);

		final ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		ActionTools.processJSONQueryCondition(QUERYPROJECTALLTASK, request, condtion);

		condtion.addCondition("order by TaskBean.planFinishTime desc");

		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYPROJECTALLTASK,
				request, condtion, this.taskDAO);
		/*, new HandleResult<TaskVO>()
				{
					public void handle(TaskVO vo)
					{
						if (null != vo.getReceiver()
								&& vo.getReceiver().length() > 0)
						{
							StafferBean sb = stafferDAO.find(vo.getReceiver()
									.split(",")[0]);
							vo.setReceiverName(sb.getName());
						}
					}
				});*/

		return JSONTools.writeResponse(response, jsonstr);
	}

	/**
	 * exportTask
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward exportTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		OutputStream out = null;

		String filenName = "exportTask_" + TimeTools.now("MMddHHmmss") + ".csv";

		response.setContentType("application/x-dbf");

		response.setHeader("Content-Disposition", "attachment; filename="
				+ filenName);

		WriteFile write = null;

		ConditionParse condtion = JSONPageSeparateTools.getCondition(request,
				QUERYPROJECTALLTASK);

		int count = taskDAO.countVOByCondition(condtion.toString());

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

			write.writeLine("id,任务名称,状态,类型,责任人,计划完成时间,交付类型,交付物,交付物数量,接收人,任务阶段");

			PageSeparate page = new PageSeparate();

			page.reset2(count, 2000);

			WriteFileBuffer line = new WriteFileBuffer(write);

			while (page.nextPage())
			{
				List<TaskVO> voFList = taskDAO.queryEntityVOsByCondition(
						condtion, page);

				for (TaskVO each : voFList)
				{
					line.reset();

					line.writeColumn(each.getId());
					line.writeColumn(each.getTaskName());
					line.writeColumn(ElTools.get("taskStatus",
							each.getTaskStatus()));
					line.writeColumn(ElTools.get("223", each.getTaskType()));
					
					line.writeColumn(each.getDutyName());
					line.writeColumn(each.getPlanFinishTime());
					
					line.writeColumn(ElTools.get2("224", each.getTransType()));
					line.writeColumn(ElTools.get2("226", each.getTransObj()));
					line.writeColumn(each.getTransObjCount());
					
					line.writeColumn(each.getReceiverName());
					line.writeColumn(ElTools.get2("taskStage",
							each.getTaskStage()));
					
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
	 * 处理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward processTaskBean(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{

		String id = request.getParameter("id");
		String oprType = request.getParameter("oprType");
		String reason = request.getParameter("description");
		String addInfo = request.getParameter("reason");
		try
		{
			User user = Helper.getUser(request);

			TcpParamWrap param = new TcpParamWrap();

			param.setId(id);
			param.setType(oprType);
			param.setReason(reason);

			// 提交
			if ("0".equals(oprType))
			{
				taskManager.passTaskBean(user, param);
			}
			else if ("1".equals(oprType))
			{
				taskManager.rejectTaskBean(user, param);
			}
			else if ("2".equals(oprType))
			{
				String proName = this.stafferDAO.find(user.getStafferId())
						.getName() + "," + TimeTools.now();
				param.setReason(addInfo + "," + proName);
				taskManager.passTaskBean(user, param);
			}
			else if ("3".equals(oprType))
			{
				taskManager.passTaskBean(user, param);
			}
			else if ("4".equals(oprType))
			{
				taskManager.passTaskBean(user, param);
			}
			else if ("5".equals(oprType))
			{
				taskManager.passTaskBean(user, param);
			}
			else if ("6".equals(oprType))
			{
				taskManager.passTaskBean(user, param);
			}
			else if ("7".equals(oprType))
			{
				taskManager.passTaskBean(user, param);
			}
			else if ("8".equals(oprType))
			{
				taskManager.passTaskBean(user, param);
			}
			else
			{
				taskManager.rejectTaskBean(user, param);
			}
			request.setAttribute(KeyConstant.MESSAGE, "成功处理任务申请");
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"处理任务申请失败:" + e.getMessage());
		}
		return mapping.findForward("querySelfApprove");
	}

	/**
	 * 查询待我处理的
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward querySelfApprove(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		User user = Helper.getUser(request);

		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		String cacheKey = QUERYSELFAPPROVE;

		ActionTools.processJSONQueryCondition(cacheKey, request, condtion);

		condtion.addCondition(" and (ProjectApproveBean.approverId = '" + user.getStafferId() 
				+ "' or ProjectApproveBean.receiver = '" + user.getStafferId() + "')");

		condtion.addCondition("order by ProjectApproveBean.logTime desc");
		String jsonstr = ActionTools.queryVOByJSONAndToString(cacheKey,
				request, condtion, this.projectApproveDAO,
				new HandleResult<ProjectApproveVO>()
				{
					public void handle(ProjectApproveVO vo)
					{
						ProjectHelper.getProjectApproveVO(vo);
					}
				});

		return JSONTools.writeResponse(response, jsonstr);
	}

	/**
	 * 获取TAB页合同列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward turnDrawGant(ActionMapping mapping, ActionForm form,
			final HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		return mapping.findForward("drawGant");

	}

	/**
	 * 获取TAB页合同列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward drawGant(ActionMapping mapping, ActionForm form,
			final HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		chart(response);
		return null;

	}

	/****************************************************/
	/********** 甘特图开始8 **********************************/
	public void chart(HttpServletResponse response)
	{
		// 甘特数据集
		IntervalCategoryDataset dataset = createSampleDataset();
		JFreeChart chart = ChartFactory.createGanttChart("", "", "", dataset,
				false, false, false);

		chart.setBackgroundPaint(new Color(242, 159, 125));
		chart.setBorderVisible(false);
		chart.setBorderPaint(ChartColor.WHITE);
		chart.setBorderStroke(null);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(new Color(128, 128, 128));
		plot.setOutlinePaint(ChartColor.WHITE);

		chart.getTitle().setFont(new Font("新宋体", Font.BOLD, 8));
		CategoryAxis domainAxis = plot.getDomainAxis();
		// 水平底部列表
		domainAxis.setLabelFont(new Font("新宋体", Font.BOLD, 8));

		// 水平底部标题
		domainAxis.setTickLabelFont(new Font("新宋体", Font.BOLD, 7));
		// 垂直标题
		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setLabelFont(new Font("新宋体", Font.BOLD, 5));
		// 用来控制时间轴的显示,防止乱码
		DateAxis da = (DateAxis) plot.getRangeAxis(0);
		da.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));
		// 把甘特图写入电脑中
		OutputStream fop = null;
		try
		{

			fop = response.getOutputStream();
			ChartUtilities.writeChartAsJPEG(fop, 1.0f, chart, 600, 100, null);
			fop.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				fop.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		// 传路径
		String filename = null;
		try
		{
			filename = ServletUtilities.saveChartAsPNG(chart, 300, 75, null,
					null);
		}
		catch (IOException e)
		{
			_logger.warn(e);
		}

	}

	/**
	 * 241. * 242. * @return The dataset. 243.
	 */
	private static IntervalCategoryDataset createSampleDataset()
	{

		final TaskSeries s1 = new TaskSeries("SCHEDULE");

		final Task t1 = new Task("任务1", date(1, Calendar.JANUARY, 2001), date(
				20, Calendar.JANUARY, 2001));
		t1.setPercentComplete(0.8);
		s1.add(t1);

		// 创建一个任务并插入两个子任务
		final Task t3 = new Task("任务2", date(10, Calendar.APRIL, 2001), date(5,
				Calendar.MAY, 2001));
		s1.add(t3);
		t3.setPercentComplete(0.4);

		final TaskSeriesCollection collection = new TaskSeriesCollection();
		collection.add(s1);

		return collection;
	}

	/**************************** 甘特图结束 ****************************/
	private static Date date(final int day, final int month, final int year)
	{

		final Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);

		final Date result = calendar.getTime();
		return result;

	}

	public ProjectDAO getProjectDAO()
	{
		return projectDAO;
	}

	public void setProjectDAO(ProjectDAO projectDAO)
	{
		this.projectDAO = projectDAO;
	}

	public AgreementDAO getAgreementDAO()
	{
		return agreementDAO;
	}

	public void setAgreementDAO(AgreementDAO agreementDAO)
	{
		this.agreementDAO = agreementDAO;
	}

	public TaskDAO getTaskDAO()
	{
		return taskDAO;
	}

	public void setTaskDAO(TaskDAO taskDAO)
	{
		this.taskDAO = taskDAO;
	}

	public StafferDAO getStafferDAO()
	{
		return stafferDAO;
	}

	public void setStafferDAO(StafferDAO stafferDAO)
	{
		this.stafferDAO = stafferDAO;
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

	public ProviderDAO getProviderDAO()
	{
		return providerDAO;
	}

	public void setProviderDAO(ProviderDAO providerDAO)
	{
		this.providerDAO = providerDAO;
	}

	public TaskManager getTaskManager()
	{
		return taskManager;
	}

	public void setTaskManager(TaskManager taskManager)
	{
		this.taskManager = taskManager;
	}

	public AttachmentDAO getAttachmentDAO()
	{
		return attachmentDAO;
	}

	public void setAttachmentDAO(AttachmentDAO attachmentDAO)
	{
		this.attachmentDAO = attachmentDAO;
	}

	public FlowLogDAO getFlowLogDAO()
	{
		return flowLogDAO;
	}

	public void setFlowLogDAO(FlowLogDAO flowLogDAO)
	{
		this.flowLogDAO = flowLogDAO;
	}

	public AgreementManager getAgreementManager()
	{
		return agreementManager;
	}

	public void setAgreementManager(AgreementManager agreementManager)
	{
		this.agreementManager = agreementManager;
	}

	public ProLineProjectManager getProLineProjectManager()
	{
		return proLineProjectManager;
	}

	public void setProLineProjectManager(
			ProLineProjectManager proLineProjectManager)
	{
		this.proLineProjectManager = proLineProjectManager;
	}

	public PayLineProjectManager getPayLineProjectManager()
	{
		return payLineProjectManager;
	}

	public void setPayLineProjectManager(
			PayLineProjectManager payLineProjectManager)
	{
		this.payLineProjectManager = payLineProjectManager;
	}

	public TranLineProjectManager getTranLineProjectManager()
	{
		return tranLineProjectManager;
	}

	public void setTranLineProjectManager(
			TranLineProjectManager tranLineProjectManager)
	{
		this.tranLineProjectManager = tranLineProjectManager;
	}

	public InvoiceLineProjectManager getInvoiceLineProjectManager()
	{
		return invoiceLineProjectManager;
	}

	public void setInvoiceLineProjectManager(
			InvoiceLineProjectManager invoiceLineProjectManager)
	{
		this.invoiceLineProjectManager = invoiceLineProjectManager;
	}

	public ProjectManager getProjectManager()
	{
		return projectManager;
	}

	public void setProjectManager(ProjectManager projectManager)
	{
		this.projectManager = projectManager;
	}

	public StafferProjectManager getStafferProjectManager()
	{
		return stafferProjectManager;
	}

	public void setStafferProjectManager(
			StafferProjectManager stafferProjectManager)
	{
		this.stafferProjectManager = stafferProjectManager;
	}

	public StafferProjectDAO getStafferProjectDAO()
	{
		return stafferProjectDAO;
	}

	public void setStafferProjectDAO(StafferProjectDAO stafferProjectDAO)
	{
		this.stafferProjectDAO = stafferProjectDAO;
	}

	public ProLineProjectDAO getProLineProjectDAO()
	{
		return proLineProjectDAO;
	}

	public void setProLineProjectDAO(ProLineProjectDAO proLineProjectDAO)
	{
		this.proLineProjectDAO = proLineProjectDAO;
	}

	public PayLineProjectDAO getPayLineProjectDAO()
	{
		return payLineProjectDAO;
	}

	public void setPayLineProjectDAO(PayLineProjectDAO payLineProjectDAO)
	{
		this.payLineProjectDAO = payLineProjectDAO;
	}

	public TranLineProjectDAO getTranLineProjectDAO()
	{
		return tranLineProjectDAO;
	}

	public void setTranLineProjectDAO(TranLineProjectDAO tranLineProjectDAO)
	{
		this.tranLineProjectDAO = tranLineProjectDAO;
	}

	public InvoiceLineProjectDAO getInvoiceLineProjectDAO()
	{
		return invoiceLineProjectDAO;
	}

	public void setInvoiceLineProjectDAO(
			InvoiceLineProjectDAO invoiceLineProjectDAO)
	{
		this.invoiceLineProjectDAO = invoiceLineProjectDAO;
	}

	public DutyDAO getDutyDAO()
	{
		return dutyDAO;
	}

	public void setDutyDAO(DutyDAO dutyDAO)
	{
		this.dutyDAO = dutyDAO;
	}

	public ProjectApproveDAO getProjectApproveDAO()
	{
		return projectApproveDAO;
	}

	public void setProjectApproveDAO(ProjectApproveDAO projectApproveDAO)
	{
		this.projectApproveDAO = projectApproveDAO;
	}

	// public TcpFlowDAO getTcpFlowDAO() {
	// return tcpFlowDAO;
	// }
	//
	//
	// public void setTcpFlowDAO(TcpFlowDAO tcpFlowDAO) {
	// this.tcpFlowDAO = tcpFlowDAO;
	// }

}
