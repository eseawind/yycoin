
package com.china.center.oa.project.manager.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.iaop.annotation.IntegrationAOP;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.project.bean.TransLineProjectBean;
import com.china.center.oa.project.dao.AgreementDAO;
import com.china.center.oa.project.dao.PayLineProjectDAO;
import com.china.center.oa.project.dao.ProLineProjectDAO;
import com.china.center.oa.project.dao.ProjectApplyDAO;
import com.china.center.oa.project.dao.ProjectApproveDAO;
import com.china.center.oa.project.dao.ProjectFlowDAO;
import com.china.center.oa.project.dao.ProjectHandleHisDAO;
import com.china.center.oa.project.dao.TranLineProjectDAO;
import com.china.center.oa.project.manager.TranLineProjectManager;
import com.china.center.oa.publics.constant.IDPrefixConstant;
import com.china.center.oa.publics.dao.AttachmentDAO;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.tools.JudgeTools;

@IntegrationAOP
public class TranLineProjectManagerImpl implements TranLineProjectManager
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
	 
	 private TranLineProjectDAO tranLineProjectDAO = null;
	 
	 
	 /**
	     * 增加交付行项目
	     * 
	     * @param user
	     * @param bean
	     * @return
	     * @throws MYException
	     */
	 @Transactional(rollbackFor = MYException.class)
	 public  boolean addTranLineProject(User user, List<String> TtransType,List<String> Ttransobj,List<String> TtransobjCount,
	    		List<String> TtransTime,List<String> TtransDays,List<String> trancurrentTask,List<String> Treceiverid,List<String> Ts_tranBeforeids,List<String> Ts_tranAfterids,String refid)
	        throws MYException
	        {
		 JudgeTools.judgeParameterIsNull(user);
		   for(int i = 0;i < TtransType.size();i++)
		   {
			   
			   TransLineProjectBean ppb = new TransLineProjectBean();
		   
			   ppb.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_AGREEMENT_JF_PREFIX));
			   
			   ppb.setRefId(refid);
			   
			   ppb.setTransType(TtransType.get(i));
			   
			   ppb.setTransobj(Ttransobj.get(i));
			   
			   ppb.setTransobjCount(TtransobjCount.get(i));
			   
			   ppb.setTransTime(TtransTime.get(i));
			   
			   ppb.setTransDays(TtransDays.get(i));
			   
			   ppb.setCurrentTask(trancurrentTask.get(i));
			   
			   ppb.setReceiver(Treceiverid.get(i));
			   
			   ppb.setBeforeTask1(Ts_tranBeforeids.get(i));
			   
			   ppb.setAfterTask1(Ts_tranAfterids.get(i));
			   
			   tranLineProjectDAO.saveEntityBean(ppb);
	        
		   }
	        return true;
	}

	 @Transactional(rollbackFor = MYException.class)
	public boolean updateTranLineProject(User user, List<String> TpayType,List<String> Tmoney,List<String> Ttime,
    		List<String> Tdays,List<String> TBefore,List<String> Tafter,String refid) throws MYException {
		return false;
	}

	 @Transactional(rollbackFor = MYException.class)
	public boolean submitTranLineProject(User user, String id, String processId)
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

	public TranLineProjectDAO getTranLineProjectDAO() {
		return tranLineProjectDAO;
	}

	public void setTranLineProjectDAO(TranLineProjectDAO tranLineProjectDAO) {
		this.tranLineProjectDAO = tranLineProjectDAO;
	}
	
	
}
