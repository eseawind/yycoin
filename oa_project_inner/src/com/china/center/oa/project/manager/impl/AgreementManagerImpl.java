package com.china.center.oa.project.manager.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.iaop.annotation.IntegrationAOP;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.project.bean.AgreementBean;
import com.china.center.oa.project.bean.ProjectApplyBean;
import com.china.center.oa.project.bean.ProjectApproveBean;
import com.china.center.oa.project.bean.ProjectFlowBean;
import com.china.center.oa.project.bean.ProjectHandleHisBean;
import com.china.center.oa.project.bean.TaskBean;
import com.china.center.oa.project.constant.ProjectConstant;
import com.china.center.oa.project.dao.AgreementDAO;
import com.china.center.oa.project.dao.ProjectApplyDAO;
import com.china.center.oa.project.dao.ProjectApproveDAO;
import com.china.center.oa.project.dao.ProjectFlowDAO;
import com.china.center.oa.project.dao.ProjectHandleHisDAO;
import com.china.center.oa.project.dao.TaskDAO;
import com.china.center.oa.project.manager.AgreementManager;
import com.china.center.oa.project.vo.AgreementVO;
import com.china.center.oa.project.vo.ProjectApproveVO;
import com.china.center.oa.project.vo.TaskVO;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.constant.IDPrefixConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.AttachmentDAO;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.TimeTools;

@IntegrationAOP
public class AgreementManagerImpl implements AgreementManager
{

	private CommonDAO commonDAO = null;
	
	private AgreementDAO agreementDAO = null;
	
	private ProjectApplyDAO projectApplyDAO = null;
	
	private FlowLogDAO flowLogDAO = null;
	
	 private ProjectHandleHisDAO projectHandleHisDAO = null;
	 
	 private final Log _logger = LogFactory.getLog(getClass());
	 
	 private AttachmentDAO attachmentDAO = null;
	 
	 private ProjectFlowDAO projectFlowDAO = null;
	 
	 private ProjectApproveDAO projectApproveDAO = null;
	
	 @Transactional(rollbackFor = MYException.class)
	 public boolean addAgreementBean(User user, AgreementBean bean) throws MYException 
	 {
		   JudgeTools.judgeParameterIsNull(user, bean);
		   
		   bean.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_AGREEMENT_PREFIX));
		   
		   bean.setAgreementCode(commonDAO.getSquenceString20(IDPrefixConstant.CODE_AGREEMENT_PREFIX));

	        bean.setApplyer(user.getStafferId());
	     // 获取flowKey
//	        TCPHelper.setFlowKey(bean);
	        bean.setAgreementStatus(ProjectConstant.AGREEMENT_INIT);
	        bean.setAgreementStage(ProjectConstant.AGREEMENT_STAGE_ONE);
	        agreementDAO.saveEntityBean(bean);
	        
	        saveApply(user, bean);
	        saveFlowLog(user, ProjectConstant.AGREEMENT_INIT, bean, "自动提交保存", PublicConstant.OPRMODE_SAVE);
	        return true;
	 	}
	 
	    @Transactional(rollbackFor = MYException.class)
	    public boolean submitAgreement(User user, String id, String processId)
	        throws MYException
	    {
	        JudgeTools.judgeParameterIsNull(user, id);
	        AgreementVO bean = findVO(id);
	        if (bean == null)
	        {
	            throw new MYException("数据错误,请确认操作");
	        }

	        if ( !bean.getApplyer().equals(user.getStafferId()))
	        {
	            throw new MYException("只能操作自己的申请");
	        }
	        // 获得当前的处理环节
	        ProjectFlowBean token = projectFlowDAO.findByUnique(bean.getFlowKey(), bean.getAgreementStatus());
	        // 进入审批状态
	        saveApprove(user, processId, bean, bean.getAgreementStatus(), 0);
	        int oldStatus = bean.getAgreementStatus();
//	        bean.setStatus(bean.getStatus());
	        agreementDAO.updateStatus(bean.getId(), bean.getAgreementStatus());
	        // 记录操作日志
	        saveFlowLog(user, oldStatus, bean, "提交申请", PublicConstant.OPRMODE_SUBMIT);
	        return true;
	    }
	 
	 public AgreementVO findVO(String id)
	    {
		 AgreementVO bean = this.agreementDAO.findVO(id);

	        if (bean == null)
	        {
	            return bean;
	        }


	        List<AttachmentBean> attachmentList = attachmentDAO.queryEntityVOsByFK(id);

	        bean.setAttachmentList(attachmentList);

	        // 当前处理人
	        List<ProjectApproveVO> approveList = projectApproveDAO.queryEntityVOsByFK(bean.getId());

	        for (ProjectApproveVO projectApproveVO : approveList)
	        {
	            bean.setProcesser(projectApproveVO.getApproverName() + ';');
	        }

	        // 流程描述
	        List<ProjectFlowBean> flowList = projectFlowDAO.queryEntityBeansByFK(bean.getFlowKey());

	        Collections.sort(flowList, new Comparator<ProjectFlowBean>()
	        {
	            public int compare(ProjectFlowBean o1, ProjectFlowBean o2)
	            {
	                return Integer.parseInt(o1.getId()) - Integer.parseInt(o2.getId());
	            }
	        });

	        StringBuffer sb = new StringBuffer();

	        for (ProjectFlowBean projectFlowBean : flowList)
	        {
	            if (bean.getAgreementStatus() == projectFlowBean.getCurrentStatus())
	            {
	                sb.append("<font color=red>").append(
	                    DefinedCommon.getValue("tcpStatus", projectFlowBean.getCurrentStatus())).append(
	                    "</font>").append("->");
	            }
	            else
	            {
	                sb
	                    .append(DefinedCommon.getValue("taskStatus", projectFlowBean.getCurrentStatus()))
	                    .append("->");
	            }
	        }

	        if (bean.getAgreementStatus() == ProjectConstant.TASK_FINISH)
	        {
	            sb.append("<font color=red>").append(
	                DefinedCommon.getValue("taskStatus", ProjectConstant.TASK_FINISH)).append("</font>");
	        }
	        else
	        {
	            sb.append(DefinedCommon.getValue("taskStatus", ProjectConstant.TASK_FINISH));
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
	    private int saveApprove(User user, String processId, AgreementVO bean, int nextStatus,
	                            int pool)
	        throws MYException
	    {
	        List<String> processList = new ArrayList();

	        processList.add(processId);

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
	    private int saveApprove(User user, List<String> processList, AgreementVO bean,
	                            int nextStatus, int pool)
	        throws MYException
	    {
	        // 获得当前的处理环节
	        ProjectFlowBean token = projectFlowDAO.findByUnique(bean.getFlowKey(), bean.getAgreementStatus());
	        if (token == null)
	        {
	            // 清除之前的处理人
	            projectApproveDAO.deleteEntityBeansByFK(bean.getId());
	        }
	        else
	        {
	            // 仅仅删除自己的
	            List<ProjectApproveBean> approveList = projectApproveDAO.queryEntityBeansByFK(bean.getId());
	            for (ProjectApproveBean projectApproveBean : approveList)
	            {
	                if (projectApproveBean.getApproverId().equals(user.getStafferId()))
	                {
	                    projectApproveDAO.deleteEntityBean(projectApproveBean.getId());
	                }
	            }
	        }

	        List<ProjectApproveBean> appList = projectApproveDAO.queryEntityBeansByFK(bean.getId());
	        if (token == null || appList.size() == 0 )
	        {
	            for (String processId : processList)
	            {
	                // 进入审批状态
	            	ProjectApproveBean approve = new ProjectApproveBean();

	                approve.setId(commonDAO.getSquenceString20());
	                approve.setApplyerId(bean.getApplyer());
	                approve.setApplyId(bean.getId());
	                approve.setApproverId(processId);
	                approve.setFlowKey(bean.getFlowKey());
	                approve.setLogTime(TimeTools.now());
	                approve.setName(bean.getAgreementName());
	                approve.setStatus(nextStatus);
	                approve.setType(ProjectConstant.AGREEMENT_APPLY);

	                projectApproveDAO.saveEntityBean(approve);
	            }

	        }
	        else
	        {
	            nextStatus = bean.getAgreementStatus();
	        }

	        return nextStatus;
	    }
	
	private void saveFlowLog(User user, int preStatus, AgreementBean apply, String reason,
            int oprMode)
		{
			FlowLogBean log = new FlowLogBean();
			
			log.setFullId(apply.getId());
			
			log.setActor(user.getStafferName());
			
			log.setActorId(user.getStafferId());
			
			log.setOprMode(oprMode);
			
			log.setDescription(reason);
			
			log.setLogTime(TimeTools.now());
			
			log.setPreStatus(preStatus);
			
			log.setAfterStatus(apply.getAgreementStatus());
			
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
			his.setType(apply.getAgreementType());
			his.setName(apply.getAgreementName());
			
			projectHandleHisDAO.saveEntityBean(his);
		}
	
	 /**
     * saveApply
     * 
     * @param user
     * @param bean
     */
    public void saveApply(User user, AgreementBean bean)
    {
    	ProjectApplyBean apply = new ProjectApplyBean();

        apply.setId(bean.getId());
        apply.setName(bean.getAgreementName());
        apply.setApplyId(user.getStafferId());
        apply.setType(ProjectConstant.AGREEMENT_APPLY);
        apply.setStatus(ProjectConstant.AGREEMENT_INIT);
        apply.setLogTime(bean.getApplyTime());
        apply.setDescription(bean.getAgreementDesc());
        projectApplyDAO.saveEntityBean(apply);
    }

    
    @Transactional(rollbackFor = MYException.class)
	public boolean updateAgreementBean(User user, AgreementBean bean)
			throws MYException {
    	JudgeTools.judgeParameterIsNull(user, bean);

        AgreementBean old = agreementDAO.find(bean.getId());
        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }
        if ( !user.getStafferId().equals(old.getApplyer()))
        {
            throw new MYException("只能修改自己的申请");
        }

//        bean.setStatus(TcpConstanst.TCP_STATUS_INIT);

        // 获取flowKey
//        TCPHelper.setFlowKey(bean);

        // 先清理
        attachmentDAO.deleteEntityBeansByFK(bean.getId());

        List<AttachmentBean> attachmentList = bean.getAttachmentList();
        if(null != attachmentList && attachmentList.size() > 0)
        {
	        for (AttachmentBean attachmentBean : attachmentList)
	        {
	            attachmentBean.setId(commonDAO.getSquenceString20());
	            attachmentBean.setRefId(bean.getId());
	        }
	
	        attachmentDAO.saveAllEntityBeans(attachmentList);
        }
        agreementDAO.updateEntityBean(bean);

        saveFlowLog(user, old.getAgreementStatus(), bean, "自动修改保存", PublicConstant.OPRMODE_SAVE);

        return true;
	}

	public CommonDAO getCommonDAO() {
		return commonDAO;
	}

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}


	public ProjectApplyDAO getProjectApplyDAO() {
		return projectApplyDAO;
	}

	public void setProjectApplyDAO(ProjectApplyDAO projectApplyDAO) {
		this.projectApplyDAO = projectApplyDAO;
	}

	public FlowLogDAO getFlowLogDAO() {
		return flowLogDAO;
	}

	public void setFlowLogDAO(FlowLogDAO flowLogDAO) {
		this.flowLogDAO = flowLogDAO;
	}

	public ProjectHandleHisDAO getProjectHandleHisDAO() {
		return projectHandleHisDAO;
	}

	public void setProjectHandleHisDAO(ProjectHandleHisDAO projectHandleHisDAO) {
		this.projectHandleHisDAO = projectHandleHisDAO;
	}

	public AttachmentDAO getAttachmentDAO() {
		return attachmentDAO;
	}

	public void setAttachmentDAO(AttachmentDAO attachmentDAO) {
		this.attachmentDAO = attachmentDAO;
	}

	public ProjectFlowDAO getProjectFlowDAO() {
		return projectFlowDAO;
	}

	public void setProjectFlowDAO(ProjectFlowDAO projectFlowDAO) {
		this.projectFlowDAO = projectFlowDAO;
	}

	public ProjectApproveDAO getProjectApproveDAO() {
		return projectApproveDAO;
	}

	public void setProjectApproveDAO(ProjectApproveDAO projectApproveDAO) {
		this.projectApproveDAO = projectApproveDAO;
	}

	public AgreementDAO getAgreementDAO() {
		return agreementDAO;
	}

	public void setAgreementDAO(AgreementDAO agreementDAO) {
		this.agreementDAO = agreementDAO;
	}
	
	

}
