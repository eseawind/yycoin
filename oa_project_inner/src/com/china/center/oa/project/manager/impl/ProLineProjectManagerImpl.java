package com.china.center.oa.project.manager.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.iaop.annotation.IntegrationAOP;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.project.bean.ProLineProjectBean;
import com.china.center.oa.project.constant.ProjectConstant;
import com.china.center.oa.project.dao.AgreementDAO;
import com.china.center.oa.project.dao.ProLineProjectDAO;
import com.china.center.oa.project.dao.ProjectApplyDAO;
import com.china.center.oa.project.dao.ProjectApproveDAO;
import com.china.center.oa.project.dao.ProjectFlowDAO;
import com.china.center.oa.project.dao.ProjectHandleHisDAO;
import com.china.center.oa.project.manager.ProLineProjectManager;
import com.china.center.oa.publics.constant.IDPrefixConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.AttachmentDAO;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.tools.JudgeTools;

@IntegrationAOP
public class ProLineProjectManagerImpl implements ProLineProjectManager
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
	 
	 
	
	@Transactional(rollbackFor = MYException.class)
	public boolean addProLineProject(User user, String[] ids, String[] counts,
			String[] prices,String refid) throws MYException {
		   JudgeTools.judgeParameterIsNull(user);
		   
		   for(int i = 0;i < ids.length;i++)
		   {
			   
		   ProLineProjectBean ppb = new ProLineProjectBean();
		   
		   ppb.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_AGREEMENT_PL_PREFIX));
		   
		   ppb.setRefId(refid);
		   
		   ppb.setProjectpro(ids[i]);
		   
		   ppb.setProcount(counts[i]);
		   
		   ppb.setProunitprice(prices[i]);
		   
		   proLineProjectDAO.saveEntityBean(ppb);
	        
		   }
	        return true;
	}
	
	@Transactional(rollbackFor = MYException.class)
	public boolean addProjectProLineProject(User user, String[] ids, String[] counts,
			String[] prices,String refid) throws MYException {
		   JudgeTools.judgeParameterIsNull(user);
		   
		   for(int i = 0;i < ids.length;i++)
		   {
			   
		   ProLineProjectBean ppb = new ProLineProjectBean();
		   
		   ppb.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_PROJECT_PL_PREFIX));
		   
		   ppb.setRefId(refid);
		   
		   ppb.setProjectpro(ids[i]);
		   
		   ppb.setProcount(counts[i]);
		   
		   ppb.setProunitprice(prices[i]);
		   
		   proLineProjectDAO.saveEntityBean(ppb);
	        
		   }
	        return true;
	}

	@Transactional(rollbackFor = MYException.class)
	public boolean updateProLineProject(User user, String[] arr1,
			String[] arr2, String[] arr3,String refid) throws MYException {
		return false;
	}

	@Transactional(rollbackFor = MYException.class)
	public boolean submitProLineProject(User user, String id, String processId)
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
	
	

}
