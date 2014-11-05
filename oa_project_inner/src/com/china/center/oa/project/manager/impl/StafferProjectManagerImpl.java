package com.china.center.oa.project.manager.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.iaop.annotation.IntegrationAOP;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.project.bean.ProLineProjectBean;
import com.china.center.oa.project.bean.StafferProjectBean;
import com.china.center.oa.project.dao.AgreementDAO;
import com.china.center.oa.project.dao.ProLineProjectDAO;
import com.china.center.oa.project.dao.ProjectApplyDAO;
import com.china.center.oa.project.dao.ProjectApproveDAO;
import com.china.center.oa.project.dao.ProjectFlowDAO;
import com.china.center.oa.project.dao.ProjectHandleHisDAO;
import com.china.center.oa.project.dao.StafferProjectDAO;
import com.china.center.oa.project.manager.StafferProjectManager;
import com.china.center.oa.publics.constant.IDPrefixConstant;
import com.china.center.oa.publics.dao.AttachmentDAO;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.tools.JudgeTools;

@IntegrationAOP
public class StafferProjectManagerImpl implements StafferProjectManager
{

	private CommonDAO commonDAO = null;
	
	private ProjectApplyDAO projectApplyDAO = null;
	
	private FlowLogDAO flowLogDAO = null;
	
	 private ProjectHandleHisDAO projectHandleHisDAO = null;
	 
	 private final Log _logger = LogFactory.getLog(getClass());
	 
	 private AttachmentDAO attachmentDAO = null;
	 
	 private ProjectFlowDAO projectFlowDAO = null;
	 
	 private ProjectApproveDAO projectApproveDAO = null;
	 
	 private StafferProjectDAO stafferProjectDAO = null;
	 
	 
	
	@Transactional(rollbackFor = MYException.class)
	public boolean addStafferLineProject(User user, String s_prostafferId[],String role[],String refid)
	throws MYException 
	{
		   JudgeTools.judgeParameterIsNull(user);
		   
		   for(int i = 0;i < s_prostafferId.length;i++)
		   {
			   
		   StafferProjectBean ppb = new StafferProjectBean();
		   
		   ppb.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_PROJECT_RY_PREFIX));
		   
		   ppb.setRefid(refid);
		   
		   ppb.setStaffer(s_prostafferId[i]);
		   
		   ppb.setRole(role[i]);
		   
		   stafferProjectDAO.saveEntityBean(ppb);
	        
		   }
	        return true;
	}
	
	@Override
	public boolean updateStafferLineProject(User user, String[] s_prostafferId,
			String[] role, String refid) throws MYException {
		return false;
	}

	@Override
	public boolean submitStafferLineProject(User user, String id,
			String processId) throws MYException {
		return false;
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

	public StafferProjectDAO getStafferProjectDAO() {
		return stafferProjectDAO;
	}

	public void setStafferProjectDAO(StafferProjectDAO stafferProjectDAO) {
		this.stafferProjectDAO = stafferProjectDAO;
	}

	
}
