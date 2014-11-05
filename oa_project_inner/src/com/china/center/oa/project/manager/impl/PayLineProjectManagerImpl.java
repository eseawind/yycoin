package com.china.center.oa.project.manager.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.iaop.annotation.IntegrationAOP;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.project.bean.PayLineProjectBean;
import com.china.center.oa.project.bean.ProLineProjectBean;
import com.china.center.oa.project.dao.AgreementDAO;
import com.china.center.oa.project.dao.PayLineProjectDAO;
import com.china.center.oa.project.dao.ProLineProjectDAO;
import com.china.center.oa.project.dao.ProjectApplyDAO;
import com.china.center.oa.project.dao.ProjectApproveDAO;
import com.china.center.oa.project.dao.ProjectFlowDAO;
import com.china.center.oa.project.dao.ProjectHandleHisDAO;
import com.china.center.oa.project.manager.PayLineProjectManager;
import com.china.center.oa.publics.constant.IDPrefixConstant;
import com.china.center.oa.publics.dao.AttachmentDAO;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.tools.JudgeTools;

@IntegrationAOP
public class PayLineProjectManagerImpl implements PayLineProjectManager
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
	 
	 private ProLineProjectDAO proLineProjectDAO = null;
	 
	 private PayLineProjectDAO payLineProjectDAO = null;
	 
	 
	 @Transactional(rollbackFor = MYException.class)
	public boolean addPayLineProject(User user, List<String> TpayType,List<String> Tmoney,List<String> Ttime,
    		List<String> Tdays,List<String> TBefore,List<String> Tafter,String refid) throws MYException {
		 JudgeTools.judgeParameterIsNull(user);
		   for(int i = 0;i < TpayType.size();i++)
		   {
			   
			   PayLineProjectBean ppb = new PayLineProjectBean();
		   
			   ppb.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_AGREEMENT_FK_PREFIX));
			   
			   ppb.setRefId(refid);
			   
			   ppb.setPayType(TpayType.get(i));
			   
			   ppb.setPayMoney(Tmoney.get(i));
			   
			   ppb.setPayTime(Ttime.get(i));
			   
			   ppb.setFinishDays(Tdays.get(i));
			   
			   ppb.setBeforeTask(TBefore.get(i));
			   
			   ppb.setAfterTask(Tafter.get(i));
			   
			   payLineProjectDAO.saveEntityBean(ppb);
	        
		   }
	        return true;
	}

	 @Transactional(rollbackFor = MYException.class)
	public boolean updatePayLineProject(User user, List<String> TpayType,List<String> Tmoney,List<String> Ttime,
    		List<String> Tdays,List<String> TBefore,List<String> Tafter,String refid) throws MYException {
		return false;
	}

	 @Transactional(rollbackFor = MYException.class)
	public boolean submitPayLineProject(User user, String id, String processId)
			throws MYException {
		return false;
	}

	public CommonDAO getCommonDAO() {
		return commonDAO;
	}

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public AgreementDAO getAgreementDAO() {
		return agreementDAO;
	}

	public void setAgreementDAO(AgreementDAO agreementDAO) {
		this.agreementDAO = agreementDAO;
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

	public ProLineProjectDAO getProLineProjectDAO() {
		return proLineProjectDAO;
	}

	public void setProLineProjectDAO(ProLineProjectDAO proLineProjectDAO) {
		this.proLineProjectDAO = proLineProjectDAO;
	}

	public PayLineProjectDAO getPayLineProjectDAO() {
		return payLineProjectDAO;
	}

	public void setPayLineProjectDAO(PayLineProjectDAO payLineProjectDAO) {
		this.payLineProjectDAO = payLineProjectDAO;
	}
	 
	 

}
