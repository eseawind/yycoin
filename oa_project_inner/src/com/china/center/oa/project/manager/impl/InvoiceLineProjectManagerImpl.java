package com.china.center.oa.project.manager.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.iaop.annotation.IntegrationAOP;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.project.bean.InvoiceLineProjectBean;
import com.china.center.oa.project.bean.TransLineProjectBean;
import com.china.center.oa.project.dao.AgreementDAO;
import com.china.center.oa.project.dao.InvoiceLineProjectDAO;
import com.china.center.oa.project.dao.PayLineProjectDAO;
import com.china.center.oa.project.dao.ProLineProjectDAO;
import com.china.center.oa.project.dao.ProjectApplyDAO;
import com.china.center.oa.project.dao.ProjectApproveDAO;
import com.china.center.oa.project.dao.ProjectFlowDAO;
import com.china.center.oa.project.dao.ProjectHandleHisDAO;
import com.china.center.oa.project.dao.TranLineProjectDAO;
import com.china.center.oa.project.manager.InvoiceLineProjectManager;
import com.china.center.oa.publics.constant.IDPrefixConstant;
import com.china.center.oa.publics.dao.AttachmentDAO;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.tools.JudgeTools;

@IntegrationAOP
public class InvoiceLineProjectManagerImpl implements InvoiceLineProjectManager
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
	 
	 private InvoiceLineProjectDAO invoiceLineProjectDAO = null;
	 
	 
	 /**
	     * 增加开票行项目
	     * 
	     * @param user
	     * @param bean
	     * @return
	     * @throws MYException
	     */
	 @Transactional(rollbackFor = MYException.class)
	 public  boolean addInvoiceLineProject(User user, List<String> TinvoiceType,List<String> TinvoiceMoney,List<String> TinvoiceTime,
	    		List<String> TfinishiDays1,List<String> Ts_invoiceCurTask,List<String> Ts_invoiceBeforeids,List<String> Ts_invoiceAfterids,String refid)
	        throws MYException
	        {
		 JudgeTools.judgeParameterIsNull(user);
		   for(int i = 0;i < TinvoiceType.size();i++)
		   {
			   
			   InvoiceLineProjectBean ppb = new InvoiceLineProjectBean();
		   
			   ppb.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_AGREEMENT_KP_PREFIX));
			   
			   ppb.setRefId(refid);
			   
			   ppb.setInvoiceType(TinvoiceType.get(i));
			   
			   ppb.setInvoiceMoney(TinvoiceMoney.get(i));
			   
			   ppb.setInvoiceTime(TinvoiceTime.get(i));
			   
			   ppb.setFinishiDays1(TfinishiDays1.get(i));
			   
			   ppb.setInvocurrentTask(Ts_invoiceCurTask.get(i));
			   
			   ppb.setBeforeTask2(Ts_invoiceBeforeids.get(i));
			   
			   ppb.setAfterTask2(Ts_invoiceAfterids.get(i));
			   
			   invoiceLineProjectDAO.saveEntityBean(ppb);
	        
		   }
	        return true;
	}

	@Transactional(rollbackFor = MYException.class)
	public boolean updateInvoiceLineProject(User user, List<String> TpayType,List<String> Tmoney,List<String> Ttime,
   		List<String> Tdays,List<String> TBefore,List<String> Tafter,String refid) throws MYException {
		return false;
	}

	@Transactional(rollbackFor = MYException.class)
	public boolean submitInvoiceLineProject(User user, String id, String processId)
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

	public InvoiceLineProjectDAO getInvoiceLineProjectDAO() {
		return invoiceLineProjectDAO;
	}

	public void setInvoiceLineProjectDAO(InvoiceLineProjectDAO invoiceLineProjectDAO) {
		this.invoiceLineProjectDAO = invoiceLineProjectDAO;
	}


}
