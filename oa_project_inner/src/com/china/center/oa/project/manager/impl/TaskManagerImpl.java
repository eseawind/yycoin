package com.china.center.oa.project.manager.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.iaop.annotation.IntegrationAOP;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.center.china.osgi.config.ConfigLoader;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.group.dao.GroupVSStafferDAO;
import com.china.center.oa.group.vs.GroupVSStafferBean;
import com.china.center.oa.mail.manager.MailMangaer;
import com.china.center.oa.project.bean.ProjectApplyBean;
import com.china.center.oa.project.bean.ProjectApproveBean;
import com.china.center.oa.project.bean.ProjectFlowBean;
import com.china.center.oa.project.bean.ProjectHandleHisBean;
import com.china.center.oa.project.bean.TaskBean;
import com.china.center.oa.project.bean.TaskMailPlanBean;
import com.china.center.oa.project.constant.ProjectConstant;
import com.china.center.oa.project.dao.ProjectApplyDAO;
import com.china.center.oa.project.dao.ProjectApproveDAO;
import com.china.center.oa.project.dao.ProjectFlowDAO;
import com.china.center.oa.project.dao.ProjectHandleHisDAO;
import com.china.center.oa.project.dao.TaskDAO;
import com.china.center.oa.project.dao.TaskMailPlanDAO;
import com.china.center.oa.project.helper.ProjectHelper;
import com.china.center.oa.project.manager.TaskManager;
import com.china.center.oa.project.vo.ProjectApproveVO;
import com.china.center.oa.project.vo.TaskVO;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.IDPrefixConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.AttachmentDAO;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.manager.CommonMailManager;
import com.china.center.oa.publics.vo.StafferVO;
import com.china.center.oa.tcp.wrap.TcpParamWrap;
import com.china.center.tools.FileTools;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

@IntegrationAOP
public class TaskManagerImpl implements TaskManager
{
	private final Log triggerLog = LogFactory.getLog("trigger");
	
	private CommonDAO commonDAO = null;

	private TaskDAO taskDAO = null;

	private ProjectApplyDAO projectApplyDAO = null;

	private StafferDAO stafferDAO = null;

	private MailMangaer mailMangaer = null;

	private FlowLogDAO flowLogDAO = null;

	private ProjectHandleHisDAO projectHandleHisDAO = null;

	private final Log _logger = LogFactory.getLog(getClass());

	protected CommonMailManager commonMailManager = null;

	private AttachmentDAO attachmentDAO = null;

	private ProjectFlowDAO projectFlowDAO = null;

	private ProjectApproveDAO projectApproveDAO = null;

	private GroupVSStafferDAO groupVSStafferDAO = null;
	
	private TaskMailPlanDAO taskMailPlanDAO = null;
	
	private PlatformTransactionManager transactionManager = null;
	
	public TaskManagerImpl()
	{
		
	}

	@Transactional(rollbackFor = MYException.class)
	public boolean addTaskBean(User user, TaskBean bean) throws MYException
	{

		JudgeTools.judgeParameterIsNull(user, bean);

		bean.setId(commonDAO
				.getSquenceString20(IDPrefixConstant.ID_TASK_PREFIX));

		bean.setTaskCode(commonDAO
				.getSquenceString20(IDPrefixConstant.CODE_TASK_PREFIX));

//		bean.setApplyer(user.getStafferId());
		// 获取flowKey
		// TCPHelper.setFlowKey(bean);
		bean.setTaskStatus(ProjectConstant.TASK_INIT);
		bean.setTaskStage(ProjectConstant.TASK_STAGE_ONE);
		taskDAO.saveEntityBean(bean);
		saveApply(user, bean);
		saveFlowLog(user, ProjectConstant.TASK_INIT, bean, "自动提交保存",
				PublicConstant.OPRMODE_SAVE);
		return true;
	}

	@Transactional(rollbackFor = MYException.class)
	public boolean submitTask(User user, String id, String partakerids)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, id);
		TaskVO bean = findVO(id);
		if (bean == null)
		{
			throw new MYException("数据错误,请确认操作");
		}

		if (!bean.getCreator().equals(user.getStafferId()))
		{
			throw new MYException("只能操作自己的申请");
		}

		// 进入审批状态
		saveApprove(user, partakerids, bean, ProjectConstant.TASK_START, 0);

		int oldStatus = bean.getTaskStatus();

		bean.setTaskStatus(ProjectConstant.PROJECT_STATUS_CONFIRM);

		taskDAO.updateStatus(bean.getId(), ProjectConstant.TASK_START);

		// 记录操作日志
		saveFlowLog(user, oldStatus, bean, "提交申请",	PublicConstant.OPRMODE_SUBMIT);
		
		// email
		sendTaskMailWhenSubmit(bean);
		
		return true;
	}

	/**
	 * 〈功能详细描述〉
	 *
	 * @param bean
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private void sendTaskMailWhenSubmit(TaskVO bean) {
		StringBuilder buffer = new StringBuilder(60);
		
		List<String> tos = new ArrayList<String>();
		
		prepareTaskContent(bean, buffer, tos);
		
		for (String to : tos) {
			_logger.info("任务提交，邮件至：" + to);
			
			if (!StringTools.isNullOrNone(to) && to.indexOf("@") != -1) {
				commonMailManager.sendMail(to, "任务提交", buffer.toString());
			}
		}
	}

	/**
	 * 〈功能详细描述〉
	 *
	 * @param bean
	 * @param buffer
	 * @param tos
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private void prepareTaskContent(TaskVO bean, StringBuilder buffer,
			List<String> tos) {
		// 邮件, to 责任人，参与人， 结果审核人
		String receiver[] = bean.getReceiver().split(",");
		
		String receiverName = "";
		
		if (null != receiver && receiver.length > 0) {
			if (!StringTools.isNullOrNone(receiver[0])) {
				StafferBean sb = stafferDAO.find(receiver[0]);
				
				if (null != sb) {
					receiverName = sb.getName();
					tos.add(sb.getNation());
				}
			}
		}
		
		String dutyStafferName = "";
		
		StafferBean sb1 = stafferDAO.find(bean.getDutyStaffer());
		
		if (null != sb1) {
			dutyStafferName = sb1.getName();
			if (!tos.contains(sb1.getNation())) {
				tos.add(sb1.getNation());
			}
		}
		
		String partkerNames = getPartakerNames(bean.getPartaker(), tos);
		
		buffer.append("任务号：").append(bean.getId()).append("<br>");
		buffer.append("目的：").append(bean.getTaskName()).append("<br>");
		buffer.append("截止时间：").append(bean.getPlanFinishTime()).append("<br>");
		buffer.append("任务备注：").append(bean.getDescription()).append("<br>");
		buffer.append("责任人：").append(dutyStafferName).append("<br>");
		buffer.append("参与人：").append(partkerNames).append("<br>");
		buffer.append("结果审核人：").append(receiverName).append("<br>");
	}

	/**
	 * 〈功能详细描述〉
	 *
	 * @param parkers
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private String getPartakerNames(String parkers, List<String> mail) {
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
				}
				else
				{
					sbuffer.append(sb.getName() + ",");
				}
				
				if (!mail.contains(sb.getNation())) {
					mail.add(sb.getNation());
				}
				
			}
		}
		
		return sbuffer.toString();
	}

	public TaskVO findVO(String id)
	{
		TaskVO bean = this.taskDAO.findVO(id);

		if (bean == null)
		{
			return bean;
		}

		List<AttachmentBean> attachmentList = attachmentDAO
				.queryEntityVOsByFK(id);

		bean.setAttachmentList(attachmentList);

		// 当前处理人
		List<ProjectApproveVO> approveList = projectApproveDAO
				.queryEntityVOsByFK(bean.getId());

		for (ProjectApproveVO projectApproveVO : approveList)
		{
			bean.setProcesser(projectApproveVO.getApproverName() + ';');
		}

		// 流程描述
		List<ProjectFlowBean> flowList = projectFlowDAO
				.queryEntityBeansByFK(bean.getFlowKey());

		Collections.sort(flowList, new Comparator<ProjectFlowBean>()
		{
			public int compare(ProjectFlowBean o1, ProjectFlowBean o2)
			{
				return Integer.parseInt(o1.getId())
						- Integer.parseInt(o2.getId());
			}
		});

		StringBuffer sb = new StringBuffer();

		for (ProjectFlowBean projectFlowBean : flowList)
		{
			if (bean.getTaskStatus() == projectFlowBean.getCurrentStatus())
			{
				sb.append("<font color=red>")
						.append(DefinedCommon.getValue("tcpStatus",
								projectFlowBean.getCurrentStatus()))
						.append("</font>").append("->");
			}
			else
			{
				sb.append(
						DefinedCommon.getValue("taskStatus",
								projectFlowBean.getCurrentStatus())).append(
						"->");
			}
		}

		if (bean.getTaskStatus() == ProjectConstant.TASK_FINISH)
		{
			sb.append("<font color=red>")
					.append(DefinedCommon.getValue("taskStatus",
							ProjectConstant.TASK_FINISH)).append("</font>");
		}
		else
		{
			sb.append(DefinedCommon.getValue("taskStatus",
					ProjectConstant.TASK_FINISH));
		}

		bean.setFlowDescription(sb.toString());

		return bean;
	}

	/**
	 * 进入审批状态
	 * 
	 * @param processId
	 * @param bean
	 * @param pool
	 * @throws MYException
	 */
	private int saveApprove(User user, String partakerids, TaskVO bean,
			int nextStatus, int pool) throws MYException
	{
		List<String> processList = new ArrayList();

		processList.add(partakerids);

		return saveApprove(user, processList, bean, nextStatus, pool);
	}

	/**
	 * saveApprove
	 * 
	 * @param user
	 * @param processList
	 * @param bean
	 * @param nextStatus
	 * @param pool
	 * @return
	 * @throws MYException
	 */
	private int saveApprove(User user, List<String> processList, TaskVO bean,
			int nextStatus, int pool) throws MYException
	{
		List<ProjectApproveBean> appList = projectApproveDAO
				.queryEntityBeansByFK(bean.getId());
		if (appList.size() == 0)
		{
			List<ProjectApproveBean> approveList = new ArrayList<ProjectApproveBean>();
			
			boolean flag = false;
			
			for (String partakerids : processList)
			{
				// 进入审批状态
				String[] arr = partakerids.split(",");
				for (int i = 0; i < arr.length; i++)
				{
					if (StringTools.isNullOrNone(arr[i]))
						continue;
					
					ProjectApproveBean approve = new ProjectApproveBean();

					approve.setId(commonDAO.getSquenceString20());
					approve.setApplyerId(bean.getApplyer());
					approve.setApplyId(bean.getId());
					approve.setApproverId(arr[i]);
					approve.setFlowKey(bean.getFlowKey());
					approve.setLogTime(TimeTools.now());
					approve.setName(bean.getTaskName());
					approve.setStatus(nextStatus);
					approve.setType(ProjectConstant.TASK_APPLY);

					if (approve.getApproverId().equals(bean.getReceiver().split(",")[0]))
						flag = true;
					
					approveList.add(approve);
				}
			}

			if (!ListTools.isEmptyOrNull(approveList))
			{
				if (!flag)
					approveList.get(0).setReceiver(bean.getReceiver().split(",")[0]);
				
				projectApproveDAO.saveAllEntityBeans(approveList);
				
			}
		}
		else
		{
			nextStatus = bean.getTaskStatus();
		}

		return nextStatus;
	}

	private void saveFlowLog(User user, int preStatus, TaskBean apply,
			String reason, int oprMode)
	{
		FlowLogBean log = new FlowLogBean();

		log.setFullId(apply.getId());

		log.setActor(user.getStafferName());

		log.setActorId(user.getStafferId());

		log.setOprMode(oprMode);

		log.setDescription(reason);

		log.setLogTime(TimeTools.now());

		log.setPreStatus(preStatus);

		log.setAfterStatus(apply.getTaskStatus());

		flowLogDAO.saveEntityBean(log);

		// 先删除
		ConditionParse condition = new ConditionParse();
		condition.addWhereStr();
		condition.addCondition("stafferId", "=", user.getStafferId());
		condition.addCondition("refId", "=", apply.getId());
		projectHandleHisDAO.deleteEntityBeansByCondition(condition);

		// 记录处理历史
		ProjectHandleHisBean his = new ProjectHandleHisBean();
		his.setId(commonDAO.getSquenceString20());
		his.setLogTime(TimeTools.now());
		his.setRefId(apply.getId());
		his.setStafferId(user.getStafferId());
		his.setApplyId(apply.getApplyer());
		his.setType(apply.getTaskType());
		his.setName(apply.getTaskName());

		projectHandleHisDAO.saveEntityBean(his);
	}

	/**
	 * saveApply
	 * 
	 * @param user
	 * @param bean
	 */
	public void saveApply(User user, TaskBean bean)
	{
		ProjectApplyBean apply = new ProjectApplyBean();

		apply.setId(bean.getId());
		apply.setName(bean.getTaskName());
		apply.setApplyId(user.getStafferId());
		apply.setType(ProjectConstant.TASK_APPLY);
		apply.setStatus(ProjectConstant.TASK_INIT);
		apply.setLogTime(bean.getApplyTime());
		apply.setDescription(bean.getDescription());
		projectApplyDAO.saveEntityBean(apply);
	}

	@Transactional(rollbackFor = MYException.class)
	public boolean updateTaskBean(User user, TaskBean bean) throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, bean);

		TaskBean old = taskDAO.find(bean.getId());
		if (old == null)
		{
			throw new MYException("数据错误,请确认操作");
		}
		if (!user.getStafferId().equals(old.getApplyer()))
		{
			throw new MYException("只能修改自己的申请");
		}

		// bean.setStatus(TcpConstanst.TCP_STATUS_INIT);

		// 获取flowKey
		// TCPHelper.setFlowKey(bean);

		// 先清理
		attachmentDAO.deleteEntityBeansByFK(bean.getId());

		List<AttachmentBean> attachmentList = bean.getAttachmentList();
		if (null != attachmentList && attachmentList.size() > 0)
		{
			for (AttachmentBean attachmentBean : attachmentList)
			{
				attachmentBean.setId(commonDAO.getSquenceString20());
				attachmentBean.setRefId(bean.getId());
			}

			attachmentDAO.saveAllEntityBeans(attachmentList);
		}
		taskDAO.updateEntityBean(bean);

		saveFlowLog(user, old.getTaskStatus(), bean, "自动修改保存",
				PublicConstant.OPRMODE_SAVE);

		return true;
	}

	/**
	 * 权限
	 * 
	 * @param user
	 * @param id
	 * @throws MYException
	 */
	private void checkAuth(User user, String id) throws MYException
	{
		List<ProjectApproveBean> approveList = projectApproveDAO
				.queryEntityBeansByFK(id);

		boolean hasAuth = false;

		for (ProjectApproveBean projectApproveBean : approveList)
		{
			if (projectApproveBean.getApproverId().equals(user.getStafferId()))
			{
				hasAuth = true;

				break;
			}
		}

		if (!hasAuth)
		{
			throw new MYException("没有操作权限,请确认操作");
		}
	}

	/**
	 * 通过
	 * 
	 * @param user
	 * @param id
	 * @param processId
	 * @param reason
	 * @return
	 * @throws MYException
	 */
	@Transactional(rollbackFor = MYException.class)
	public boolean passTaskBean(User user, TcpParamWrap param)
			throws MYException
	{

		String id = param.getId();

		String reason = param.getReason();

		String optype = param.getType();

		TaskVO bean = findVO(id);

		if (bean == null)
		{
			throw new MYException("数据错误,请确认操作");
		}

		List<ProjectApproveBean> approveList = projectApproveDAO
				.queryEntityBeansByFK(bean.getId());

		if (optype.equals("2"))
		{
			bean.setAddinfo(bean.getAddinfo() + " \n" + reason);
			this.taskDAO.updateEntityBean(bean);
			return true;
		}

		if (optype.equals("3")
				&& user.getStafferId().equals(bean.getDutyStaffer()))
		{
			int t = bean.getTaskStatus();
			bean.setTaskStatus(PublicConstant.OPRMODE_APPLY_STOP);
			for (ProjectApproveBean pab : approveList)
			{
				pab.setMode(3);
				;
				this.projectApproveDAO.updateEntityBean(pab);
			}
			saveFlowLog(user, t, bean, "责任人申请任务停止",
					PublicConstant.OPRMODE_APPLY_STOP);
			return true;
		}
		if (optype.equals("4"))
		{
			int t = bean.getTaskStatus();
			bean.setTaskStatus(ProjectConstant.PROJECT_STATUS_AGREE);
			saveFlowLog(user, t, bean, "同意申请任务停止,",
					PublicConstant.OPRMODE_AGREE_APPLY_STOP);
			bean.setTaskStatus(ProjectConstant.TASK_CEASE);
			this.taskDAO.updateEntityBean(bean);
			projectApproveDAO.deleteEntityBeansByFK(bean.getId());
			
			// 向申请人发送邮件
			sendMail2Applyer(bean);
			
			return true;
		}
		if (optype.equals("5"))
		{
			int t = bean.getTaskStatus();
			bean.setTaskStatus(ProjectConstant.PROJECT_STATUS_NO_AGREE);
			saveFlowLog(user, t, bean, "不同意申请任务停止,",
					PublicConstant.OPRMODE_NOT_AGREE_APPLY_STOP);
			return true;
		}
		if (optype.equals("6")
				&& user.getStafferId().equals(bean.getDutyStaffer()))
		{
			int t = bean.getTaskStatus();
			for (ProjectApproveBean pab : approveList)
			{
				pab.setMode(6);
				;
				this.projectApproveDAO.updateEntityBean(pab);
			}
			saveFlowLog(user, t, bean, "责任人申请任务完成",
					PublicConstant.OPRMODE_APPLY_FINISH);
			return true;
		}
		if (optype.equals("7"))
		{
			int t = bean.getTaskStatus();
			bean.setTaskStatus(ProjectConstant.TASK_FINISH);
			bean.setRealFinishiTime(TimeTools.now_short());
			saveFlowLog(user, t, bean, "同意申请任务完成,",
					PublicConstant.OPRMODE_AGREE_APPLY_FINISH);
			this.taskDAO.updateEntityBean(bean);
			projectApproveDAO.deleteEntityBeansByFK(bean.getId());
			
			// 向申请者发送邮件
			sendMail2Applyer(bean);
			
			return true;
		}
		if (optype.equals("8"))
		{
			int t = bean.getTaskStatus();
			bean.setTaskStatus(ProjectConstant.PROJECT_STATUS_NO_FINISH);
			saveFlowLog(user, t, bean, "不同意申请任务完成,",
					PublicConstant.OPRMODE_NOT_AGREE_APPLY_FINISH);
			return true;
		}
		JudgeTools.judgeParameterIsNull(user, id);

		if (bean.getTaskStatus() == ProjectConstant.TASK_START)
		{
			throw new MYException("当前状态不可更改");
		}

		// 权限
		checkAuth(user, id);
		int oldStatus = bean.getTaskStatus();
		int b = 0;
		
		for (ProjectApproveBean projectApproveBean : approveList)
		{
			if (projectApproveBean.getFlag() != 1)
			{
				b = b + 1;
				if (projectApproveBean.getApproverId().equals(user.getStafferId()))
				{
					projectApproveBean.setFlag(1);
					saveFlowLog(user, projectApproveBean.getStatus(), bean,
							"接受任务,", PublicConstant.OPRMODE_RECEIVE);
					projectApproveDAO.updateEntityBean(projectApproveBean);
					//projectApproveDAO.deleteEntityBean(projectApproveBean.getId());
				}
				continue;
			}
		}
		
		if (b == 0 || b == 1)
		{
			// 结束了需要清空
			ProjectApproveBean pb = approveList.get(0);
			ProjectApproveBean approve = new ProjectApproveBean();

			approve.setId(commonDAO.getSquenceString20());
			approve.setApplyerId(pb.getApplyerId());
			approve.setApplyId(pb.getApplyId());
			if (null != bean.getReceiver() && bean.getReceiver().length() > 0)
			{
				approve.setApproverId(bean.getReceiver().split(",")[0]);
			}
			else
			{
				approve.setApproverId(null);
			}
			approve.setFlowKey(pb.getFlowKey());
			approve.setLogTime(TimeTools.now());
			approve.setName(pb.getName());
			approve.setStatus(ProjectConstant.TASK_START);
			approve.setType(pb.getType());
			approve.setFlag(0);
			projectApproveDAO.saveEntityBean(approve);
			
			bean.setTaskStatus(ProjectConstant.TASK_START);
			this.taskDAO.updateEntityBean(bean);
			
			projectApplyDAO.updateStatus(bean.getId(), bean.getTaskStatus());
			// 记录操作日志
			saveFlowLog(user, oldStatus, bean, reason,
					PublicConstant.OPRMODE_RECEIVE);
			for (ProjectApproveBean projectApproveBean : approveList)
			{
				projectApproveBean.setStatus(ProjectConstant.TASK_START);
				projectApproveDAO.updateEntityBean(projectApproveBean);
			}
			
			// 查找同一任务下，且receive=approve.getApproverId，并清除
			List<ProjectApproveBean> pbList = projectApproveDAO
											.queryEntityBeansByCondition(" where applyId=? and receiver = ?", approve.getApplyId(), approve.getApproverId());
			
			for (ProjectApproveBean each : pbList)
			{
				each.setReceiver("");
				
				projectApproveDAO.updateEntityBean(each);
			}
		}
		return true;
	}
	
	/**
	 * 
	 * 在任务确认为完成或停止时，触发邮件通知申请人，邮件标题为“XXX任务已为XXX状态”，邮件内容为任务基本信息加任务处理日志
	 *
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private void sendMail2Applyer(TaskVO bean) {
		String title = bean.getTaskName() + " 任务已为";
		
		if (bean.getTaskStatus() == ProjectConstant.TASK_CEASE) {
			title += "停止状态"; 
		} else {
			title += "完成状态";
		}
		
		// 任务基本信息
		StringBuilder buffer = new StringBuilder(60);
		
		List<String> tos = new ArrayList<String>();
		
		prepareTaskContent(bean, buffer, tos);
		
		StafferBean staffer = stafferDAO.find(bean.getApplyer());
		
		if (null != staffer) {
			if (!StringTools.isNullOrNone(staffer.getNation()) && staffer.getNation().indexOf("@") != -1) {
				// content
				List<FlowLogBean> list = flowLogDAO.queryEntityBeansByFK(bean.getId());
				
				StringBuilder sb = new StringBuilder();
				
				sb.append(buffer.toString()).append("<br>");
				
				for (FlowLogBean each : list) {
					sb.append("审批人:").append(each.getActor())
					.append("审批时间：").append(each.getLogTime())
					.append("意见：").append(each.getDescription()).append("<br>");
				}
				
				commonMailManager.sendMail(staffer.getNation(), title, sb.toString());
			}
		} else {
			_logger.info("触发邮件通知申请人时，申请人不存在，可能已删除，请检查.");
		}
	}

	@Transactional(rollbackFor = MYException.class)
	public boolean deleteTaskBean(User user, String id) throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, id);

		TaskBean bean = taskDAO.find(id);

		if (bean == null)
		{
			throw new MYException("数据错误,请确认操作");
		}

		if (bean.getTaskStatus() != ProjectConstant.TASK_INIT)
		{
			throw new MYException("非保存状态不能删除");
		}

		// 删除
		flowLogDAO.deleteEntityBeansByFK(bean.getId());

		String rootPath = ConfigLoader.getProperty("taskAttachmentPath");

		List<AttachmentBean> attachmenList = attachmentDAO
				.queryEntityBeansByFK(id);

		for (AttachmentBean attachmentBean : attachmenList)
		{
			FileTools.deleteFile(rootPath + attachmentBean.getPath());
		}

		attachmentDAO.deleteEntityBeansByFK(bean.getId());

		taskDAO.deleteEntityBean(id);

		projectApplyDAO.deleteEntityBean(id);

		return true;
	}

	/**
	 * rejectTaskBean
	 * 
	 * @param user
	 * @param id
	 * @param processId
	 * @param reason
	 * @return
	 * @throws MYException
	 */
	@Transactional(rollbackFor = MYException.class)
	public boolean rejectTaskBean(User user, TcpParamWrap param)
			throws MYException
	{
		String id = param.getId();
		String reason = param.getReason();
		String type = param.getType();

		JudgeTools.judgeParameterIsNull(user, id);

		TaskVO bean = findVO(id);

		if (bean == null)
		{
			throw new MYException("数据错误,请确认操作");
		}

		// 权限
		checkAuth(user, id);

		int oldStatus = bean.getTaskStatus();

		// 结束了需要清空
		projectApproveDAO.deleteEntityBeansByFK(bean.getId());

		bean.setTaskStatus(ProjectConstant.TASK_INIT);

		taskDAO.updateStatus(bean.getId(), bean.getTaskStatus());

		// 记录操作日志
		saveFlowLog(user, oldStatus, bean, reason,
				PublicConstant.OPRMODE_REJECT);

		return true;
	}

	public void sendEmail() throws MYException
	{
		List<TaskBean> taskList = this.taskDAO.queryTaskByConditions();
		try
		{
			long quot;
			if (null != taskList && taskList.size() > 0)
			{
				for (TaskBean tb : taskList)
				{
					String planFinishTime = tb.getPlanFinishTime();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String nowTime = sdf.format(new Date());
					Date date1;

					date1 = sdf.parse(planFinishTime);
					Date date2 = sdf.parse(nowTime);
					quot = date1.getTime() - date2.getTime();
					quot = quot / 1000 / 60 / 60 / 24 / 3;
					if (quot < 1)
					{
						String dutier = tb.getDutyStaffer();
						String parkers = dutier + "," + tb.getPartaker();
						String arr[] = parkers.split(",");
						for (int i = 0; i < arr.length; i++)
						{
							String proid1 = getSailOutDepart(arr[i]);
							String proid2 = getSailOutLargeArea(arr[i]);
							String proid3 = getSailOutCareer(arr[i]);
							if (null != proid1 && proid1.length() > 0)
							{
								sendMail(this.stafferDAO.find(tb.getApplyer()),
										proid1, tb);
							}
							else if (null != proid2 && proid2.length() > 0)
							{
								sendMail(this.stafferDAO.find(tb.getApplyer()),
										proid2, tb);
							}
							else if (null != proid3 && proid3.length() > 0)
							{
								sendMail(this.stafferDAO.find(tb.getApplyer()),
										proid3, tb);
							}
						}
					}
				}
			}
		}
		catch (ParseException e)
		{
			_logger.warn(e, e);
		}
	}
	
	/**
	 * JOB Run/10Minutes
	 */
	public void sendTaskWarningMail() {
		String nowTime = TimeTools.changeFormat(TimeTools.now(), TimeTools.LONG_FORMAT, "HH:mm");
		
		int hour = Integer.parseInt(nowTime.split(":")[0]);
		int min = Integer.parseInt(nowTime.split(":")[1]);
		
		// 进行中 的任务
		ConditionParse con = new ConditionParse();
		
		con.addWhereStr();
		
		con.addCondition("TaskBean.planFinishTime", "=", TimeTools.now_short());
		con.addIntCondition("TaskBean.taskStatus", "=", ProjectConstant.TASK_START);
		
		List<TaskBean> taskList = taskDAO.queryEntityBeansByCondition(con);
		
		List<TaskBean> mailList = new ArrayList<TaskBean>();
		
		for (TaskBean each : taskList) {
			if (StringTools.isNullOrNone(each.getFinishTime()) || StringTools.isNullOrNone(each.getFinishMin())) {
				continue;
			}
			
			int diff = (Integer.parseInt(each.getFinishTime()) * 60 + Integer.parseInt(each.getFinishMin()))
					- (hour * 60 + min);
			
			// 提前 30 分钟
			if (diff <= 30 && diff > 20) {
				mailList.add(each);
			}
		}
		
		// sendMail1
		sendWarningMail1(mailList);
		
		// 超过截止日期的任务
		con.clear();
		
		con.addWhereStr();
		
		con.addCondition("TaskBean.planFinishTime", "=", TimeTools.now_short());
		con.addCondition("TaskBean.taskStatus not in (3, 4)");
		
		List<TaskBean> task1List = taskDAO.queryEntityBeansByCondition(con);
		
		List<TaskBean> mail2List = new ArrayList<TaskBean>();
		
		for (TaskBean each : task1List) {
			if (StringTools.isNullOrNone(each.getFinishTime()) || StringTools.isNullOrNone(each.getFinishMin())) {
				continue;
			}
			
			int diff = (hour * 60 + min) - (Integer.parseInt(each.getFinishTime()) * 60 + Integer.parseInt(each.getFinishMin()));
			
			// 超过截止时间
			if (diff >= 0 && diff < 10) {
				mail2List.add(each);
			}
		}
		
		// sendMail
		sendWarningMail2(mail2List);
	}
	
	/**
	 * 
	 * 状态仍是“进行中”状态，则触发邮件至责任人与结果审核人，邮件标题为：XXX任务完成时间将至，请尽快处理。
	 *
	 * @param list
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private void sendWarningMail1(List<TaskBean> list) {
		for (TaskBean bean : list) {
			// 邮件, to 责任人，参与人， 结果审核人
			String receiver[] = bean.getReceiver().split(",");
			
			List<String> tos = new ArrayList<String>();

			if (null != receiver && receiver.length > 0) {
				if (!StringTools.isNullOrNone(receiver[0])) {
					StafferBean sb = stafferDAO.find(bean.getDutyStaffer());
					
					if (null != sb) {
						tos.add(sb.getNation());
					}
				}
			}
			
			StafferBean sb1 = stafferDAO.find(bean.getDutyStaffer());
			
			if (null != sb1) {
				if (!tos.contains(sb1.getNation())) {
					tos.add(sb1.getNation());
				}
			}
			
			StringBuilder sb = new StringBuilder(50);
			
			sb.append("任务：").append(bean.getTaskName()).append("<br>");
			sb.append("完成时间将至，请尽快处理。").append("<br>");
			
			for (String to : tos) {
				_logger.info("任务提醒，邮件至：" + to);
				
				if (!StringTools.isNullOrNone(to) && to.indexOf("@") != -1) {
					commonMailManager.sendMail(to, "任务提醒", sb.toString());
				} else {
					_logger.info("任务提醒，邮箱为空或是不规范的邮箱.");
				}
			}
		}
	}
	
	/**
	 * 
	 * 超出截止时间状态仍不为“完成或停止”，则发送邮件至责任人所属事业部负责人、部门负责人、结果审核人邮箱，邮件标题：XXX任务未按期完成，请督促责任人XXX尽快处理。
	 *
	 * @param list
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private void sendWarningMail2(List<TaskBean> list) {
		for (TaskBean bean : list) {
			// 邮件, to 责任人，参与人， 结果审核人
			String receiver[] = bean.getReceiver().split(",");
			
			List<String> tos = new ArrayList<String>();

			if (null != receiver && receiver.length > 0) {
				if (!StringTools.isNullOrNone(receiver[0])) {
					StafferBean sb = stafferDAO.find(bean.getDutyStaffer());
					
					if (null != sb) {
						tos.add(sb.getNation());
					}
				}
			}
			
			String dutyName = "";
			StafferBean sb1 = stafferDAO.find(bean.getDutyStaffer());
			
			if (null != sb1) {
				dutyName = sb1.getName();
				
				List<StafferBean> isbList = stafferDAO.
						queryEntityBeansByCondition("where StafferBean.industryId=? and postId = 16 and status = 0", sb1.getIndustryId());
				
				if (!ListTools.isEmptyOrNull(isbList)) {
					if (!tos.contains(isbList.get(0).getNation())) {
						tos.add(isbList.get(0).getNation());
					}
					
					if (isbList.size() > 1) {
						_logger.info(dutyName + " 所在的事业部负责人存在多人，系统默认取第一条.当前取的事业部负责人是:" + isbList.get(0).getName());
					}
				} else {
					_logger.info(dutyName + " 所在的事业部未设置负责人，请确认.");
				}
				
				List<StafferBean> psbList = stafferDAO.
						queryEntityBeansByCondition("where StafferBean.principalshipId=? and postId = 17 and status = 0", sb1.getPrincipalshipId());
				
				if (!ListTools.isEmptyOrNull(psbList)) {
					if (!tos.contains(psbList.get(0).getNation())) {
						tos.add(psbList.get(0).getNation());
					}
					
					if (psbList.size() > 1) {
						_logger.info(dutyName + " 所在的部门负责人存在多人，系统默认取第一条.当前取的部门负责人是:" + psbList.get(0).getName());
					}
				} else {
					_logger.info(dutyName + " 所在的部门未设置负责人，请确认.");
				}
				
			} else {
				_logger.info("责任人员信息不存在，可能已删除，请检查.");
			}
			
			StringBuilder sb = new StringBuilder(50);
			
			sb.append("任务：").append(bean.getTaskName()).append("<br>");
			sb.append("未按期完成，请督促责任人").append(dutyName).append("尽快处理。").append("<br>");
			
			for (String to : tos) {
				_logger.info("任务警告，邮件至：" + to);
				
				if (!StringTools.isNullOrNone(to) && to.indexOf("@") != -1) {
					commonMailManager.sendMail(to, "任务警告", sb.toString());
				} else {
					_logger.info("任务警告，邮箱为空或是不规范的邮箱.");
				}
			}
		}
	}
	
	public CommonDAO getCommonDAO()
	{
		return commonDAO;
	}

	public void setCommonDAO(CommonDAO commonDAO)
	{
		this.commonDAO = commonDAO;
	}

	public TaskDAO getTaskDAO()
	{
		return taskDAO;
	}

	public void setTaskDAO(TaskDAO taskDAO)
	{
		this.taskDAO = taskDAO;
	}

	public ProjectApplyDAO getProjectApplyDAO()
	{
		return projectApplyDAO;
	}

	public void setProjectApplyDAO(ProjectApplyDAO projectApplyDAO)
	{
		this.projectApplyDAO = projectApplyDAO;
	}

	public FlowLogDAO getFlowLogDAO()
	{
		return flowLogDAO;
	}

	public void setFlowLogDAO(FlowLogDAO flowLogDAO)
	{
		this.flowLogDAO = flowLogDAO;
	}

	public ProjectHandleHisDAO getProjectHandleHisDAO()
	{
		return projectHandleHisDAO;
	}

	public void setProjectHandleHisDAO(ProjectHandleHisDAO projectHandleHisDAO)
	{
		this.projectHandleHisDAO = projectHandleHisDAO;
	}

	public AttachmentDAO getAttachmentDAO()
	{
		return attachmentDAO;
	}

	public void setAttachmentDAO(AttachmentDAO attachmentDAO)
	{
		this.attachmentDAO = attachmentDAO;
	}

	public ProjectFlowDAO getProjectFlowDAO()
	{
		return projectFlowDAO;
	}

	public void setProjectFlowDAO(ProjectFlowDAO projectFlowDAO)
	{
		this.projectFlowDAO = projectFlowDAO;
	}

	public ProjectApproveDAO getProjectApproveDAO()
	{
		return projectApproveDAO;
	}

	public void setProjectApproveDAO(ProjectApproveDAO projectApproveDAO)
	{
		this.projectApproveDAO = projectApproveDAO;
	}

	/**
	 * 获取部门负责人
	 * 
	 * @param proid
	 * @return
	 */
	public String getSailOutDepart(String proid)
	{
		String tmp = proid;
		List<GroupVSStafferBean> vs = groupVSStafferDAO
				.queryEntityBeansByFK("A220110406000200001");

		for (GroupVSStafferBean groupVSStafferBean : vs)
		{
			StafferVO vo = stafferDAO.findVO(groupVSStafferBean.getStafferId());
			if (null != vo && vo.getId().equals(tmp))
			{
				proid = vo.getId();
				break;
			}
			else
			{
				proid = null;
				continue;
			}

		}
		return proid;
	}

	/**
	 * 获取大区负责人
	 * 
	 * @param proid
	 * @return
	 */
	public String getSailOutLargeArea(String proid)
	{
		List<GroupVSStafferBean> vs = groupVSStafferDAO
				.queryEntityBeansByFK("A220110406000200002");
		String tmp = proid;
		for (GroupVSStafferBean groupVSStafferBean : vs)
		{
			StafferVO vo = stafferDAO.findVO(groupVSStafferBean.getStafferId());
			if (null != vo && vo.getId().equals(tmp))
			{
				proid = vo.getId();
				break;
			}
			else
			{
				proid = null;
				continue;
			}

		}
		return proid;
	}

	/**
	 * 获取事业部负责人
	 * 
	 * @param proid
	 * @return
	 */
	public String getSailOutCareer(String proid)
	{

		List<GroupVSStafferBean> vs = groupVSStafferDAO
				.queryEntityBeansByFK("A220110406000200003");
		String tmp = proid;
		for (GroupVSStafferBean groupVSStafferBean : vs)
		{
			StafferVO vo = stafferDAO.findVO(groupVSStafferBean.getStafferId());
			if (null != vo && vo.getId().equals(tmp))
			{
				proid = vo.getId();
				break;
			}
			else
			{
				proid = null;
				continue;
			}

		}
		return proid;
	}

	/**
	 * 统计任务状态为进行中时需要发的邮件
	 * trigger every day
	 * {@inheritDoc}
	 */
	public void statsTaskForMail()
	{
		TransactionTemplate tran = new TransactionTemplate(transactionManager);
		
		triggerLog.info("statsTaskForMail 开始统计...");

		long statsStar = System.currentTimeMillis();
		
		tran.execute(new TransactionCallback()
		{
			public Object doInTransaction(TransactionStatus arg0)
			{
				ConditionParse con = new ConditionParse();
				
				con.addWhereStr();
				
				con.addIntCondition("TaskBean.taskStatus", "=", ProjectConstant.TASK_START);
				
				List<TaskVO> taskList = taskDAO.queryEntityVOsByCondition(con);
				
				for (TaskVO each : taskList)
				{
					String planFinishTime = each.getPlanFinishTime();
					
					int days = TimeTools.cdate(planFinishTime, TimeTools.now_short());
					
					// 处理days
					if (days > 30)
					{
						if (judgeIsFirstDayOfMonth())
						{
							String planTime = TimeTools.now_short(1) + " 9:00:00";
							
							saveTaskMailPlanInner(each, "的任务距计划完成时间大于 30 天", planTime, 1, false);
						}
					}
					else if (days > 7 && days <= 30)
					{
						if (judgeIsMonday(TimeTools.now_short(1)))
						{
							String planTime = TimeTools.now_short(1) + " 9:00:00";
							
							saveTaskMailPlanInner(each, "的任务距计划完成时间不足 30 天", planTime, 2, false);
						}
					}
					else if (days > 3 && days <= 7)
					{
						List<TaskMailPlanBean> list = taskMailPlanDAO.queryByIdAndTypeAndStatus(each.getId(), 3, 0);
						
						if (ListTools.isEmptyOrNull(list))
						{
							String planTime = TimeTools.now_short(2) + " 9:00:00";
							
							saveTaskMailPlanInner(each, "的任务距计划完成时间不足 7 天", planTime, 3, false);
						}
					}
					else if (days > 1 && days <= 3)
					{
						List<TaskMailPlanBean> list = taskMailPlanDAO.queryByIdAndTypeAndStatus(each.getId(), 4, 0);
						
						if (ListTools.isEmptyOrNull(list))
						{
							String planTime = TimeTools.now_short(1) + " 9:00:00";
							
							saveTaskMailPlanInner(each, "的任务距计划完成时间不足 3 天", planTime, 4, false);
						}
					}
					else if (days >= 0 && days <=1)
					{
						List<TaskMailPlanBean> list = taskMailPlanDAO.queryByIdAndTypeAndStatus(each.getId(), 5, 0);
						
						if (ListTools.isEmptyOrNull(list))
						{
							String planTime = TimeTools.now_short(1) + " 9:00:00";
							
							saveTaskMailPlanInner(each, "的任务距计划完成时间不足 1 天", planTime, 5, true);
							
							String planTime1 = TimeTools.now_short(1) + " 14:00:00";
							
							saveTaskMailPlanInner(each, "的任务距计划完成时间不足 1 天", planTime1, 5, true);
						}
					}
					else // 小于0 ，超时
					{
						List<TaskMailPlanBean> list = taskMailPlanDAO.queryByIdAndTypeAndStatus(each.getId(), 6, 0);
						
						if (ListTools.isEmptyOrNull(list))
						{
							String planTime = TimeTools.now_short(1) + " 9:00:00";
							
							saveTaskMailPlanInner(each, "的任务未按计划完成", planTime, 6, true);
						}
					}
				}
				
				return Boolean.TRUE;
			}
			
		});
		
		triggerLog.info("statsTaskForMail 统计结束... ,共耗时："
				+ (System.currentTimeMillis() - statsStar));
		
		return;
	}
	
	/**
	 * 获取上级领导
	 * @param to
	 */
	private String handleTo(String to)
	{
		String newTo = to;
		
		String [] arr = to.split(",");
		
		for (String each : arr)
		{
			StafferBean staff = stafferDAO.find(each);
			
			if (staff != null)
			{
				String postId = staff.getPostId();
				
				if (postId.equals(PublicConstant.POST_WORKER))
				{
					List<StafferBean> sbList = stafferDAO.queryByPricipalAndPostId(staff.getPrincipalshipId(), PublicConstant.POST_DEPART_MANAGER);
					
					for(StafferBean eachsb : sbList)
					{
						if (!newTo.contains(eachsb.getId()))
							newTo = eachsb.getId() + "," + newTo;
					}
				}
				else if (postId.equals(PublicConstant.POST_DEPART_MANAGER))
				{
					List<StafferBean> sbList = stafferDAO.queryByPricipalAndPostId(staff.getIndustryId(), PublicConstant.POST_SHI_MANAGER);
					
					for(StafferBean eachsb : sbList)
					{
						if (!newTo.contains(eachsb.getId()))
							newTo = eachsb.getId() + "," + newTo;
					}
				}
				else if (postId.equals(PublicConstant.POST_SHI_MANAGER))
				{
					List<StafferBean> sbList = stafferDAO.queryByPricipalAndPostId("", PublicConstant.POST_COMPANY_MANAGER);
					
					for(StafferBean eachsb : sbList)
					{
						if (!newTo.contains(eachsb.getId()))
							newTo = eachsb.getId() + "," + newTo;
					}
				}
				else
				{
					
				}
			}
		}
		
		return newTo;
	}
	
	private boolean judgeIsFirstDayOfMonth()
	{
		if (TimeTools.now_short().equals(TimeTools.getMonthEnd(TimeTools.getMonthBegin())))
			return true;
		
		return false;
	}
	
	private boolean judgeIsMonday(String date)
	{
		if ("星期一".equals(TimeTools.getWeekDay(date)))
			return true;

		return false;
	}
	
	/**
	 * 
	 * @param each
	 * 			
	 * @param title
	 * 			邮件标题
	 * @param parent
	 * 			是否包含上级领导
	 */
	private void saveTaskMailPlanInner(TaskVO each, String title, String planTime, int type, boolean parent)
	{
		StringBuilder sb = new StringBuilder();
		
		String partaker = "";
		
		String arr[] = each.getPartaker().split(",");
		
		for (int i = 0; i < arr.length; i++)
		{
			StafferBean staff = stafferDAO.find(arr[i]);
			
			if (staff != null)
			{
				partaker = staff.getName() + ",";
			}
		}
		
		String receivers = "";
		
		String receiver = each.getReceiver().split(",")[0];
		
		StafferBean staff = stafferDAO.find(receiver);
		
		if (staff != null)
		{
			receivers = staff.getName();
		}
		
		String to = each.getDutyStaffer() + "," + each.getPartaker() + "," + receiver;
		
		if (parent)
			to = handleTo(to);
		
		StringBuilder sb1 = new StringBuilder();
		
		List<FlowLogBean> logList = flowLogDAO.queryEntityBeansByFK(each.getId());
		
		for (FlowLogBean each1 : logList)
		{
			sb1.append(each1.getActor()).append("----").append(ProjectHelper.getOprMode(each1.getOprMode())).append("----").append(each1.getLogTime()).append("<br>");
		}
		
		String content = sb.append("任务名称：").append(each.getTaskName())
			.append("<br>计划完成时间：").append(each.getPlanFinishTime())
			.append("<br>当前状态:").append(ProjectHelper.getStatus(each.getTaskStatus()))
			.append("<br>责任人：").append(each.getDutyName())
			.append("<br>参与人：").append(partaker)
			.append("<br>接收人：").append(receivers)
			.append("<br>任务信息：").append(each.getAddinfo())
			.append("<br>任务审批日志：").append("<br>")
			.append(sb1.toString()).toString();
		
		TaskMailPlanBean tmpBean = new TaskMailPlanBean();
		
		tmpBean.setId(commonDAO.getSquenceString());
		
		tmpBean.setTaskId(each.getId());
		
		tmpBean.setTitle(each.getTaskName() + "," + title);

		tmpBean.setContent(content);

		tmpBean.setTos(to);
		
		tmpBean.setPlanTime(planTime);
		
		tmpBean.setStatus(0);
		
		tmpBean.setType(type);
		
		taskMailPlanDAO.saveEntityBean(tmpBean);
	}
	
	/**
	 * 发送计划中的邮件
	 *  状态为0 待发送且计划发送时间小于等于当前时间
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor=MYException.class)
	public void sendTaskMail()
	{
		List<TaskMailPlanBean> list = taskMailPlanDAO.queryByNeedMail();
		
//		for (TaskMailPlanBean each : list)
		for (Iterator<TaskMailPlanBean> iterator = list.iterator(); iterator.hasNext();)
		{
			TaskMailPlanBean each = iterator.next();
			
			if (!StringTools.isNullOrNone(each.getTaskId())) {
				TaskBean task = taskDAO.find(each.getTaskId());
				
				if (null != task) {
					if (task.getTaskStatus() != ProjectConstant.TASK_START) {
						taskMailPlanDAO.deleteEntityBean(each.getId());
						
						iterator.remove();
						
						continue;
					}
				}
			}
			
			String  to = each.getTos();
			
			String toAdd = "";
			
			String []tos = to.split(",");
			
			for (int i = 0; i < tos.length; i++)
			{
				StafferBean sb = stafferDAO.find(tos[i]);
				
				if (sb != null)
				{
					if (!StringTools.isNullOrNone(sb.getNation()))
						toAdd = sb.getNation() + "," + toAdd;
				}
			}
			
			if (!StringTools.isNullOrNone(toAdd))
			{
				String ss = toAdd.substring(toAdd.length() -1);
				
				String newTo = toAdd;
				
				if (ss.equals(","))
				{
					newTo = toAdd.substring(0, toAdd.length() - 1);
				}
				
				if (!StringTools.isNullOrNone(newTo))
				{
					String newTos[] = newTo.split(",");
					
					for (String s : newTos)
					{
						triggerLog.info("sendTaskMail 邮件发送至:" + s);
						
						commonMailManager.sendMail(s, each.getTitle(), each.getContent());
					}
					
					each.setStatus(1);
					
					taskMailPlanDAO.updateEntityBean(each);
				}
			}
		}
	}
	
	public StafferDAO getStafferDAO()
	{
		return stafferDAO;
	}

	public void setStafferDAO(StafferDAO stafferDAO)
	{
		this.stafferDAO = stafferDAO;
	}

	public GroupVSStafferDAO getGroupVSStafferDAO()
	{
		return groupVSStafferDAO;
	}

	public void setGroupVSStafferDAO(GroupVSStafferDAO groupVSStafferDAO)
	{
		this.groupVSStafferDAO = groupVSStafferDAO;
	}

	public void sendMail(StafferBean bean, String recids, TaskBean tb)
			throws MYException
	{
		StringBuffer sb = new StringBuffer();
		StafferBean proBean = this.stafferDAO.find("3328333");
		sb.append("系统发送>>>").append("\r\n").append("任务名称:" + tb.getTaskName())
				.append("\r\n").append("任务标识:" + tb.getId()).append("\r\n")
				.append("申请人:" + bean.getName()).append("\r\n")
				.append("申请时间:" + tb.getApplyTime()).append(",");

		String message = sb.toString();

		String to = proBean.getNation();

		_logger.info(message);

		commonMailManager.sendMail(to, "任务申请", message);
	}

	public MailMangaer getMailMangaer()
	{
		return mailMangaer;
	}

	public void setMailMangaer(MailMangaer mailMangaer)
	{
		this.mailMangaer = mailMangaer;
	}

	public CommonMailManager getCommonMailManager()
	{
		return commonMailManager;
	}

	public void setCommonMailManager(CommonMailManager commonMailManager)
	{
		this.commonMailManager = commonMailManager;
	}

	public PlatformTransactionManager getTransactionManager()
	{
		return transactionManager;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public TaskMailPlanDAO getTaskMailPlanDAO()
	{
		return taskMailPlanDAO;
	}

	public void setTaskMailPlanDAO(TaskMailPlanDAO taskMailPlanDAO)
	{
		this.taskMailPlanDAO = taskMailPlanDAO;
	}
}
