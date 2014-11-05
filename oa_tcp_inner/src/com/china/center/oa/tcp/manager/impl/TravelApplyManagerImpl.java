/**
 * File Name: TravelApplyManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.manager.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.iaop.annotation.IntegrationAOP;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.config.ConfigLoader;
import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.budget.bean.BudgetItemBean;
import com.china.center.oa.budget.bean.BudgetLogBean;
import com.china.center.oa.budget.constant.BudgetConstant;
import com.china.center.oa.budget.dao.BudgetItemDAO;
import com.china.center.oa.budget.manager.BudgetManager;
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.BankDAO;
import com.china.center.oa.finance.manager.BillManager;
import com.china.center.oa.group.dao.GroupVSStafferDAO;
import com.china.center.oa.group.vs.GroupVSStafferBean;
import com.china.center.oa.mail.bean.MailBean;
import com.china.center.oa.mail.manager.MailMangaer;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.IDPrefixConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.dao.AttachmentDAO;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.helper.UserHelper;
import com.china.center.oa.publics.manager.NotifyManager;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.publics.vo.StafferVO;
import com.china.center.oa.tcp.bean.TcpApplyBean;
import com.china.center.oa.tcp.bean.TcpApproveBean;
import com.china.center.oa.tcp.bean.TcpFlowBean;
import com.china.center.oa.tcp.bean.TcpHandleHisBean;
import com.china.center.oa.tcp.bean.TcpShareBean;
import com.china.center.oa.tcp.bean.TravelApplyBean;
import com.china.center.oa.tcp.bean.TravelApplyItemBean;
import com.china.center.oa.tcp.bean.TravelApplyPayBean;
import com.china.center.oa.tcp.constanst.TcpConstanst;
import com.china.center.oa.tcp.constanst.TcpFlowConstant;
import com.china.center.oa.tcp.dao.TcpApplyDAO;
import com.china.center.oa.tcp.dao.TcpApproveDAO;
import com.china.center.oa.tcp.dao.TcpFlowDAO;
import com.china.center.oa.tcp.dao.TcpHandleHisDAO;
import com.china.center.oa.tcp.dao.TcpPrepaymentDAO;
import com.china.center.oa.tcp.dao.TcpShareDAO;
import com.china.center.oa.tcp.dao.TravelApplyDAO;
import com.china.center.oa.tcp.dao.TravelApplyItemDAO;
import com.china.center.oa.tcp.dao.TravelApplyPayDAO;
import com.china.center.oa.tcp.helper.TCPHelper;
import com.china.center.oa.tcp.listener.TcpPayListener;
import com.china.center.oa.tcp.manager.TravelApplyManager;
import com.china.center.oa.tcp.vo.TcpApproveVO;
import com.china.center.oa.tcp.vo.TcpShareVO;
import com.china.center.oa.tcp.vo.TravelApplyItemVO;
import com.china.center.oa.tcp.vo.TravelApplyVO;
import com.china.center.oa.tcp.wrap.TcpParamWrap;
import com.china.center.tools.FileTools;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * TravelApplyManagerImpl
 * 
 * @author ZHUZHU
 * @version 2011-7-17
 * @see TravelApplyManagerImpl
 * @since 3.0
 */
@IntegrationAOP
public class TravelApplyManagerImpl extends AbstractListenerManager<TcpPayListener> implements TravelApplyManager
{
    private final Log operationLog = LogFactory.getLog("opr");
    
    private final Log badLog = LogFactory.getLog("bad");
    
    private final Log _logger = LogFactory.getLog(getClass());

    private TcpApplyDAO tcpApplyDAO = null;

    private TcpFlowDAO tcpFlowDAO = null;
    
    private StafferDAO stafferDAO = null;

    private GroupVSStafferDAO groupVSStafferDAO = null;

    private TcpPrepaymentDAO tcpPrepaymentDAO = null;

    private TcpShareDAO tcpShareDAO = null;

    private TravelApplyDAO travelApplyDAO = null;

    private TravelApplyItemDAO travelApplyItemDAO = null;

    private TravelApplyPayDAO travelApplyPayDAO = null;

    private CommonDAO commonDAO = null;

    private BankDAO bankDAO = null;

    private TcpHandleHisDAO tcpHandleHisDAO = null;

    private MailMangaer mailMangaer = null;

    private NotifyManager notifyManager = null;

    private BudgetManager budgetManager = null;

    private OrgManager orgManager = null;

    private BillManager billManager = null;

    private BudgetItemDAO budgetItemDAO = null;

    private AttachmentDAO attachmentDAO = null;

    private FlowLogDAO flowLogDAO = null;

    private TcpApproveDAO tcpApproveDAO = null;

    /**
     * default constructor
     */
    public TravelApplyManagerImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.tcp.manager.TravelApplyManager#addTravelApplyBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.tcp.bean.TravelApplyBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addTravelApplyBean(User user, TravelApplyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);
        
        bean.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_TCP_PREFIX));

        bean.setStafferId(user.getStafferId());

        // 借款人就是自己
        if (StringTools.isNullOrNone(bean.getBorrowStafferId()))
        {
            bean.setBorrowStafferId(user.getStafferId());
        }

        bean.setStatus(TcpConstanst.TCP_STATUS_INIT);

        // 获取flowKey
        TCPHelper.setFlowKey(bean);
        // TravelApplyItemBean
        List<TravelApplyItemBean> itemList = bean.getItemList();
        
        if(null != itemList && itemList.size() > 0)
        {
        	for (TravelApplyItemBean travelApplyItemBean : itemList)
            {
                travelApplyItemBean.setId(commonDAO.getSquenceString20());
                travelApplyItemBean.setParentId(bean.getId());
            }

            travelApplyItemDAO.saveAllEntityBeans(itemList);
        }
        
        if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_YES)
        {
            long temp = 0L;

            List<TravelApplyPayBean> payList = bean.getPayList();
            
            if(null != payList && payList.size() > 0)
            {
	            for (TravelApplyPayBean travelApplyPayBean : payList)
	            {
	                travelApplyPayBean.setId(commonDAO.getSquenceString20());
	                travelApplyPayBean.setParentId(bean.getId());
	
	                temp += travelApplyPayBean.getMoneys();
	            }
	            travelApplyPayDAO.saveAllEntityBeans(payList);
            }

            bean.setBorrowTotal(temp);

        }
        else
        {
            bean.setBorrowTotal(0);
        }
        // 合法性校验
        checkApply(bean);

        List<TcpShareBean> shareList = bean.getShareList();
        
        if(null != shareList && shareList.size() > 0)
        {
	        for (TcpShareBean tcpShareBean : shareList)
	        {
	            tcpShareBean.setId(commonDAO.getSquenceString20());
	            tcpShareBean.setRefId(bean.getId());
	        }
	        tcpShareDAO.saveAllEntityBeans(shareList);
        }

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
        
        travelApplyDAO.saveEntityBean(bean);

        saveApply(user, bean);

        saveFlowLog(user, TcpConstanst.TCP_STATUS_INIT, bean, "自动提交保存", PublicConstant.OPRMODE_SAVE);

        return true;
    }
    
    
    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.tcp.manager.TravelApplyManager#addTravelApplyBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.tcp.bean.TravelApplyBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addVocAndWorkTravelApplyBean(User user, TravelApplyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);
        
        bean.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_TCP_PREFIX));

        bean.setStafferId(user.getStafferId());

//        // 借款人就是自己
        if (StringTools.isNullOrNone(bean.getBorrowStafferId()))
        {
            bean.setBorrowStafferId(user.getStafferId());
        }

        //bean.setStatus(TcpConstanst.TCP_STATUS_INIT);

        // 获取flowKey
        //TCPHelper.setFlowKey(bean);
        bean.setFlowKey(TcpFlowConstant.EXTRA_WORK_AND_LEAVE_CEO);
        
//         合法性校验
        checkApply(bean);

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
        
        travelApplyDAO.saveEntityBean(bean);

        saveApply(user, bean);

        saveFlowLog(user, bean.getStatus(), bean, "自动提交保存", PublicConstant.OPRMODE_SAVE);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean submitTravelApplyBean(User user, String id, String processId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        TravelApplyVO bean = findVO(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !bean.getStafferId().equals(user.getStafferId()))
        {
            throw new MYException("只能操作自己的申请");
        }

        // 预算占用
        if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_YES)
        {
            checkBudget(user, bean, 0);
        }

        // 获得当前的处理环节
        TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(), bean.getStatus());

        // 进入审批状态
        int newStatus = saveApprove(user, processId, bean, token.getNextStatus(), 0);

        int oldStatus = bean.getStatus();

        bean.setStatus(newStatus);

        travelApplyDAO.updateStatus(bean.getId(), newStatus);

        // 中收在此产生凭证借：营业费用-中收 (5504-47)贷：预提费用 (2191)
        if (bean.getType() == TcpConstanst.TCP_APPLYTYPE_MID) {
        	Collection<TcpPayListener> listenerMapValues = this.listenerMapValues();

            for (TcpPayListener tcpPayListener : listenerMapValues)
            {
                tcpPayListener.onSubmitMidTravelApply(user, bean);
            }
        }
        
        // 记录操作日志
        saveFlowLog(user, oldStatus, bean, "提交申请", PublicConstant.OPRMODE_SUBMIT);

        return true;
    }
    
    
    /**
     * 获取部门负责人
     * @param proid
     * @return
     */
    public String getSailOutDepart(String proid)
    {
    	String tmp = proid;
        List<GroupVSStafferBean> vs = groupVSStafferDAO.queryEntityBeansByFK("A220110406000200001");

        for (GroupVSStafferBean groupVSStafferBean : vs)
        {
        	StafferVO vo = stafferDAO.findVO(groupVSStafferBean.getStafferId());
            if(null != vo && vo.getId().equals(tmp))
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
     * @param proid
     * @return
     */
    public String getSailOutLargeArea(String proid)
    {
        List<GroupVSStafferBean> vs = groupVSStafferDAO.queryEntityBeansByFK("A220110406000200002");
        String tmp = proid;
        for (GroupVSStafferBean groupVSStafferBean : vs)
        {
        	StafferVO vo = stafferDAO.findVO(groupVSStafferBean.getStafferId());
            if(null != vo && vo.getId().equals(tmp))
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
     * @param proid
     * @return
     */
    public String getSailOutCareer(String proid)
    {

        List<GroupVSStafferBean> vs = groupVSStafferDAO.queryEntityBeansByFK("A220110406000200003");
        String tmp = proid;
        for (GroupVSStafferBean groupVSStafferBean : vs)
        {
        	StafferVO vo = stafferDAO.findVO(groupVSStafferBean.getStafferId());
            if(null != vo && vo.getId().equals(tmp))
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
     * 获取部门负责人
     * @param proid
     * @return
     */
    public String getDepart(String proid)
    {
    	String tmp = proid;
        List<GroupVSStafferBean> vs = groupVSStafferDAO.queryEntityBeansByFK("A220110406000200001");

        for (GroupVSStafferBean groupVSStafferBean : vs)
        {
        	StafferVO vo = stafferDAO.findVO(groupVSStafferBean.getStafferId());
            if(null != vo && vo.getId().equals(tmp))
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
     * 获取中心总监负责人
     * @param proid
     * @return
     */
    public String getCenterInspectorGeneral(String proid)
    {
        List<GroupVSStafferBean> vs = groupVSStafferDAO.queryEntityBeansByFK("A220110406000200011");
        String tmp = proid;
        for (GroupVSStafferBean groupVSStafferBean : vs)
        {
        	StafferVO vo = stafferDAO.findVO(groupVSStafferBean.getStafferId());
            if(null != vo && vo.getId().equals(tmp))
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
     * 获取财务总监负责人
     * @param proid
     * @return
     */
    public String getFinanceInspectorGeneral(String proid)
    {

        List<GroupVSStafferBean> vs = groupVSStafferDAO.queryEntityBeansByFK("A220110406000200004");
        String tmp = proid;
        for (GroupVSStafferBean groupVSStafferBean : vs)
        {
        	StafferVO vo = stafferDAO.findVO(groupVSStafferBean.getStafferId());
            if(null != vo && vo.getId().equals(tmp))
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
     * 获取总裁
     * @param proid
     * @return
     */
    public String getPresident(String proid)
    {

        List<GroupVSStafferBean> vs = groupVSStafferDAO.queryEntityBeansByFK("A220110406000200005");
        String tmp = proid;
        for (GroupVSStafferBean groupVSStafferBean : vs)
        {
        	StafferVO vo = stafferDAO.findVO(groupVSStafferBean.getStafferId());
            if(null != vo && vo.getId().equals(tmp))
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
    
    @Transactional(rollbackFor = MYException.class)
    public boolean submitVocationAndWork(User user, String id, String processId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        TravelApplyVO bean = findVO(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !bean.getStafferId().equals(user.getStafferId()))
        {
            throw new MYException("只能操作自己的申请");
        }

        // 进入审批状态
        saveApprove(user, processId, bean, bean.getStatus(), 0);

        int oldStatus = bean.getStatus();

        travelApplyDAO.updateStatus(bean.getId(), bean.getStatus());

        // 记录操作日志
        saveFlowLog(user, oldStatus, bean, "提交申请", PublicConstant.OPRMODE_SUBMIT);

        return true;
    }
    
    
	@Transactional(rollbackFor = MYException.class)
	public boolean passTravelApplyBean(User user, TcpParamWrap param)
	    throws MYException
	{
	    String id = param.getId();
	    String processId = param.getProcessId();
	    String reason = param.getReason();
	
	    JudgeTools.judgeParameterIsNull(user, id);
	
	    TravelApplyVO bean = findVO(id);
	
	    if (bean == null)
	    {
	        throw new MYException("数据错误,请确认操作");
	    }
	
	    // 权限
	    checkAuth(user, id);
	
	    int oldStatus = bean.getStatus();
	
	    // 分支处理
	    logicProcess(user, param, bean, oldStatus);
	
	    // 获得当前的处理环节
	    TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(), bean.getStatus());
	    if (token == null)
	    {
	        throw new MYException("数据错误,请确认操作");
	    }
	
	    // 群组模式
	    if (token.getNextPlugin().startsWith("group"))
	    {
	        int newStatus = saveApprove(user, processId, bean, token.getNextStatus(), 0);
	        if (newStatus != oldStatus)
	        {
	            bean.setStatus(newStatus);
	
	            travelApplyDAO.updateStatus(bean.getId(), newStatus);
	        }
	
	        // 记录操作日志
	        saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
	    }
	    // 共享池模式
	    if (token.getNextPlugin().startsWith("pool"))
	    {
	        String groupId = token.getNextPlugin().substring(5);
	
	        List<GroupVSStafferBean> vsList = groupVSStafferDAO.queryEntityBeansByFK(groupId);
	
	        if (ListTools.isEmptyOrNull(vsList))
	        {
	            throw new MYException("当前群组内没有人员,请确认操作");
	        }
	
	        List<String> processList = new ArrayList();
	
	        for (GroupVSStafferBean groupVSStafferBean : vsList)
	        {
	            processList.add(groupVSStafferBean.getStafferId());
	        }
	
	        int newStatus = saveApprove(user, processList, bean, token.getNextStatus(),
	            TcpConstanst.TCP_POOL_POOL);
	
	        if (newStatus != oldStatus)
	        {
	            bean.setStatus(newStatus);
	            
	            // 会签结束，且没有借款的情况，后续状态直接置为结束
	            if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_NO && newStatus == TcpConstanst.TCP_STATUS_WAIT_PAY )
	            {
	                newStatus = TcpConstanst.TCP_STATUS_END;
	                
	                // 结束了要清空
	                tcpApproveDAO.deleteEntityBeansByFK(bean.getId());
	                
	                bean.setStatus(newStatus);
	            }
	
	            travelApplyDAO.updateStatus(bean.getId(), newStatus);
	        }
	
	        // 记录操作日志
	        saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
	    }
	    // 插件模式
	    else if (token.getNextPlugin().startsWith("plugin"))
	    {
	        // plugin:travelSingeAll(意思是权签人会签)
	        if (token.getNextPlugin().equalsIgnoreCase("plugin:travelSingeAll"))
	        {
	            List<String> processList = new ArrayList();
	
	            // 先处理一个
	            List<TcpShareVO> shareVOList = bean.getShareVOList();
	
	            if (ListTools.isEmptyOrNull(shareVOList))
	            {
	                throw new MYException("下环节里面没有人员,请确认操作");
	            }
	
	            for (TcpShareVO tcpShareVO : shareVOList)
	            {
	                processList.add(tcpShareVO.getApproverId());
	            }
	
	            int newStatus = saveApprove(user, processList, bean, token.getNextStatus(),
	                TcpConstanst.TCP_POOL_COMMON);
	
	            bean.setStatus(newStatus);                
	            
	            travelApplyDAO.updateStatus(bean.getId(), newStatus);
	
	            // 记录操作日志
	            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
	        }
	    }
	    // 结束模式
	    else if (token.getNextPlugin().startsWith("end"))
	    {
	        // 结束了需要清空
	        tcpApproveDAO.deleteEntityBeansByFK(bean.getId());
	
	        bean.setStatus(token.getNextStatus());
	
	        travelApplyDAO.updateStatus(bean.getId(), bean.getStatus());
	
	        // 记录操作日志
	        saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
	    }
	    return true;
	}
    
    

    /**
     * 请假加班审批
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean passVocAndWorkTravelApplyBean(User user, TcpParamWrap param)
        throws MYException
    {
        String id = param.getId();
        String processId = param.getProcessId();
        String reason = param.getReason();
        
        JudgeTools.judgeParameterIsNull(user, id);
        
        TravelApplyVO bean = findVO(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }
        
        // 权限
        checkAuth(user, id);

        int oldStatus = bean.getStatus();
        
        // 获得当前的处理环节
        TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(), bean.getStatus());
        if (token == null)
        {
            throw new MYException("数据错误,请确认操作");
        }
        
        // 请假类型，请假时间在2天以内时，流程直接到人事审批
        if (bean.getPurposeType() == 31) {
        	
        	if (bean.getStatus() != TcpConstanst.TCP_STATUS_HR) {
        		
          		String begind = TimeTools.changeFormat(bean.getBeginDate(), "yyyy-MM-dd HH:mm", "yyyy-MM-dd");
          		String endd = TimeTools.changeFormat(bean.getEndDate(), "yyyy-MM-dd HH:mm", "yyyy-MM-dd");
          		
          		int days = TimeTools.cdate(endd, begind);
        		
          		if (days > 0 && days <= 2) {
          			token = tcpFlowDAO.findByUnique(bean.getFlowKey(), TcpConstanst.TCP_STATUS_WAIT_DEPUTYCEO);
                    if (token == null)
                    {
                        throw new MYException("数据错误,请确认操作");
                    }
                    
                    List<GroupVSStafferBean> vs = groupVSStafferDAO.queryEntityBeansByFK("A220140624000200001");
                    
                    if (ListTools.isEmptyOrNull(vs)) {
                    	throw new MYException("数据错误,系统群组-人力资源未配置人员");
                    } else {
                    	processId = vs.get(0).getStafferId();
                    	
                    	StafferBean staffer = stafferDAO.find(processId);
                    	
                    	if (null == staffer) {
                    		throw new MYException("数据错误,系统群组-人力资源配置人员不存在");
                    	}
                    }
          		}
        		
        	}
        }
        
        // 群组模式
        if (token.getNextPlugin().startsWith("group"))
        {
            int newStatus = saveApprove(user, processId, bean, token.getNextStatus(), 0);
            
            if (newStatus != oldStatus)
            {
                bean.setStatus(newStatus);

                travelApplyDAO.updateStatus(bean.getId(), newStatus);
            }

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
        }
        // 共享池模式
        if (token.getNextPlugin().startsWith("pool"))
        {
            String groupId = token.getNextPlugin().substring(5);

            List<GroupVSStafferBean> vsList = groupVSStafferDAO.queryEntityBeansByFK(groupId);

            if (ListTools.isEmptyOrNull(vsList))
            {
                throw new MYException("当前群组内没有人员,请确认操作");
            }

            List<String> processList = new ArrayList();

            for (GroupVSStafferBean groupVSStafferBean : vsList)
            {
                processList.add(groupVSStafferBean.getStafferId());
            }

            int newStatus = saveApprove(user, processList, bean, token.getNextStatus(),
                TcpConstanst.TCP_POOL_POOL);

            if (newStatus != oldStatus)
            {
                bean.setStatus(newStatus);
                
                // 会签结束，且没有借款的情况，后续状态直接置为结束
                if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_NO && newStatus == TcpConstanst.TCP_STATUS_WAIT_PAY )
                {
                    newStatus = TcpConstanst.TCP_STATUS_END;
                    
                    // 结束了要清空
                    tcpApproveDAO.deleteEntityBeansByFK(bean.getId());
                    
                    bean.setStatus(newStatus);
                }

                travelApplyDAO.updateStatus(bean.getId(), newStatus);
            }

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
        }
        // 插件模式
        else if (token.getNextPlugin().startsWith("plugin"))
        {
            // plugin:travelSingeAll(意思是权签人会签)
            if (token.getNextPlugin().equalsIgnoreCase("plugin:travelSingeAll"))
            {
                List<String> processList = new ArrayList();

                // 先处理一个
                List<TcpShareVO> shareVOList = bean.getShareVOList();

                if (ListTools.isEmptyOrNull(shareVOList))
                {
                    throw new MYException("下环节里面没有人员,请确认操作");
                }

                for (TcpShareVO tcpShareVO : shareVOList)
                {
                    processList.add(tcpShareVO.getApproverId());
                }

                int newStatus = saveApprove(user, processList, bean, token.getNextStatus(),
                    TcpConstanst.TCP_POOL_COMMON);

                bean.setStatus(newStatus);
                
                travelApplyDAO.updateStatus(bean.getId(), newStatus);

                // 记录操作日志
                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
            }
        }
        // 结束模式
        else if (token.getNextPlugin().startsWith("end"))
        {
            // 结束了需要清空
            tcpApproveDAO.deleteEntityBeansByFK(bean.getId());

            bean.setStatus(token.getNextStatus());

            travelApplyDAO.updateStatus(bean.getId(), bean.getStatus());

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
        }

        return true;
    }
    
    /**
     *请假申请 
     */
    
    @Transactional(rollbackFor = MYException.class)
    public boolean vocationAndWork(TravelApplyVO bean,StafferVO staffervo,User user,String processId,
    		String reason,TcpParamWrap param,int oldStatus)
        throws MYException
    {
    	try
        {
    		long days = 0;
        	String beginDate = bean.getBeginDate();
          	String endDate = bean.getEndDate();
          	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      		long to = sdf.parse(endDate).getTime();
      		long from = sdf.parse(beginDate).getTime();
      		days = (to - from) / (1000 * 60 * 60 * 24);//请假时间，以天为单位
      		String postid = staffervo.getPostId();
      		StafferBean processBean = stafferDAO.find(user.getStafferId());
      		
      		 if((bean.getStatus() == TcpConstanst.TCP_STATUS_HR) && user.getStafferId().trim().equals("103565"))
      		{
      			 // 结束了要清空
	      	       tcpApproveDAO.deleteEntityBeansByFK(bean.getId());
	      	                    
	      	       bean.setStatus(TcpConstanst.TCP_STATUS_END);
	
	      	       travelApplyDAO.updateStatus(bean.getId(), TcpConstanst.TCP_STATUS_END);
	      	            // 记录操作日志
	      	       saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
	      	       return true;
      		}
      		else if(null != processBean && processBean.getPostId().equals("20"))
      		{
      			int newStatus = saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);//直接提交到徐景(人事部)
                
                if (newStatus != oldStatus)
                {
                    bean.setStatus(newStatus);

                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
                }

                // 记录操作日志
                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
                
      	        return true;
      		}
      		else
      		{
      			
      		if(days >= 0 && days < 3)
	      		{
	      			int newStatus = saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);
	                
	                if (newStatus != oldStatus)
	                {
	                    bean.setStatus(newStatus);
	
	                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
	                }
	
	                // 记录操作日志
	                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
	                
	      	        return true;
	      		}
      		
      		
      		if((null != postid && postid.equals("4") && days > 2 && days < 6) 
      				|| (null != postid && postid.equals("4") && days > 5))
	      		{
      				if(bean.getStatus() == 4)
      				{
      					int newStatus = saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);
      	                
      	                if (newStatus != oldStatus)
      	                {
      	                    bean.setStatus(newStatus);

      	                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
      	                }

      	                // 记录操作日志
      	                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
      	                
      	      	        return true;
      				}
      				else
      				{
      					int newStatus = saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_LOCATION, 0);
		                
		                if (newStatus != oldStatus)
		                {
		                    bean.setStatus(newStatus);
	
		                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
		                }
	
		                // 记录操作日志
		                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
		                
		      	        return true;
      				}
	      			
	      		 }
      		
      		if((null != postid && postid.equals("17") && days > 2 && days < 6) 
      				|| (null != postid && postid.equals("17") && days > 5))
	      		{
	      			int newStatus = saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);
	                
	                if (newStatus != oldStatus)
	                {
	                    bean.setStatus(newStatus);
	
	                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
	                }
	
	                // 记录操作日志
	                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
	                
	      	        return true;
	      		}
      		
      		if(null != postid && postid.equals("18") && days > 2 && days < 6)
      		{
      			int newStatus = saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);
                
                if (newStatus != oldStatus)
                {
                    bean.setStatus(newStatus);

                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
                }

                // 记录操作日志
                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
                
      	        return true;
      		 }
      		
      		if(null != postid && postid.equals("18") && days > 5)
      		{
      			if(bean.getStatus() == 28)
  				{
      				int newStatus = saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);
                    
                    if (newStatus != oldStatus)
                    {
                        bean.setStatus(newStatus);

                        travelApplyDAO.updateStatus(bean.getId(), newStatus);
                    }

                    // 记录操作日志
                    saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
                    
          	        return true;
  				}
  				else
  				{
  					int newStatus = saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_DEPUTYCEO, 0);
	                
	                if (newStatus != oldStatus)
	                {
	                    bean.setStatus(newStatus);

	                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
	                }

	                // 记录操作日志
	                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
	                
	      	        return true;
  				}
      		 }
      		if((null != postid && postid.equals("16") && days > 2 && days < 6) || 
      				(null != postid && postid.equals("16") && days > 5))
      		{
      			int newStatus = saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);
                
                if (newStatus != oldStatus)
                {
                    bean.setStatus(newStatus);

                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
                }

                // 记录操作日志
                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
                
      	        return true;
      		 }
      		
      		}
        }
        catch(ParseException e)
        {
        	throw new MYException("请假日期计算错误");
        }
  
        return true;
    }
    
    
    /**
     *加班,撤销申请 
     *
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addWork(TravelApplyVO bean,StafferVO staffervo,User user,String processId,
    		String reason,TcpParamWrap param,int oldStatus)
        throws MYException
    {
	      		String postid = staffervo.getPostId();
		    	if((bean.getPurposeType() != -1 && bean.getPurposeType() == 21)
		    			||(bean.getPurposeType() != -1 && bean.getPurposeType() == 12)
		    			||(bean.getPurposeType() != -1 && bean.getPurposeType() == 22)
		    			||(bean.getPurposeType() != -1 && bean.getPurposeType() == 32))
		  		{
		    		StafferBean processBean = stafferDAO.find(processId);
		      		
		    		if((bean.getStatus() == TcpConstanst.TCP_STATUS_HR) && user.getStafferId().trim().equals("103565"))
		      		{
		      		// 结束了要清空
			      	       tcpApproveDAO.deleteEntityBeansByFK(bean.getId());
			      	                    
			      	       bean.setStatus(TcpConstanst.TCP_STATUS_END);
			
			      	       travelApplyDAO.updateStatus(bean.getId(), TcpConstanst.TCP_STATUS_END);
			      	            // 记录操作日志
			      	       saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
			      	       return true;
		      		}
		    		else if(null != processBean && processBean.getPostId().equals("20"))
		      		{
		    			int newStatus = saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_DEPUTYCEO, 0);
		                
		                if (newStatus != oldStatus)
		                {
		                    bean.setStatus(newStatus);
	
		                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
		                }
	
		                // 记录操作日志
		                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
		                
		      	        return true;
		      		}
		      		else
		      		{
			  			if(null != postid && postid.equals("4"))
				      		{
			      				if(bean.getStatus() == TcpConstanst.TCP_STATUS_WAIT_DEPUTYCEO)//待总裁审批
			      				{
			      					int newStatus = saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);
			      	                
			      	                if (newStatus != oldStatus)
			      	                {
			      	                    bean.setStatus(newStatus);

			      	                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
			      	                }

			      	                // 记录操作日志
			      	                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
			      	                
			      	      	        return true;
			      				}
			      				else if(processBean.getPostId().equals("16"))//待事业部经理审批
			      				{
			      					int newStatus = saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_LOCATION, 0);
					                if (newStatus != oldStatus)
					                {
					                    bean.setStatus(newStatus);
				
					                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
					                }
				
					                // 记录操作日志
					                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
					                
					      	        return true;
			      				}
			      				else if(processBean.getPostId().equals("20"))
			      				{
			      					int newStatus = saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_DEPUTYCEO, 0);
					                
					                if (newStatus != oldStatus)
					                {
					                    bean.setStatus(newStatus);
				
					                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
					                }
				
					                // 记录操作日志
					                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
					                
					      	        return true;
			      				}
				      			
				      		 }
			  			
			  			if(null != postid && postid.equals("17"))
			      		{
			  				if(bean.getStatus() == 28)
			  				{
			  					int newStatus = saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);
			  	                
			  	                if (newStatus != oldStatus)
			  	                {
			  	                    bean.setStatus(newStatus);

			  	                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
			  	                }

			  	                // 记录操作日志
			  	                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
			  	                
			  	      	        return true;
			  				}
			  				else if(processBean.getPostId().equals("16"))
			  				{
			  					int newStatus = saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_LOCATION, 0);
				                
				                if (newStatus != oldStatus)
				                {
				                    bean.setStatus(newStatus);
			
				                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
				                }
			
				                // 记录操作日志
				                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
				                
				      	        return true;
			  				}
			  				else if(processBean.getPostId().equals("20"))
			  				{
			  					int newStatus = saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_DEPUTYCEO, 0);
				                
				                if (newStatus != oldStatus)
				                {
				                    bean.setStatus(newStatus);
			
				                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
				                }
			
				                // 记录操作日志
				                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
				                
				      	        return true;
			  				}
			      			
			      		 }
			  			
			  			if(null != postid && postid.equals("16"))
			      		{
			  				if(bean.getStatus() == 28)
			  				{
			  					int newStatus = saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);
			  	                
			  	                if (newStatus != oldStatus)
			  	                {
			  	                    bean.setStatus(newStatus);

			  	                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
			  	                }

			  	                // 记录操作日志
			  	                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
			  	                
			  	      	        return true;
			  				}
			  				else if(processBean.getPostId().equals("16"))
			  				{
			  					int newStatus = saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_LOCATION, 0);
				                
				                if (newStatus != oldStatus)
				                {
				                    bean.setStatus(newStatus);
			
				                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
				                }
			
				                // 记录操作日志
				                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
				                
				      	        return true;
			  				}
			  				else if(processBean.getPostId().equals("20"))
			  				{
			  					int newStatus = saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_DEPUTYCEO, 0);
				                
				                if (newStatus != oldStatus)
				                {
				                    bean.setStatus(newStatus);
			
				                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
				                }
			
				                // 记录操作日志
				                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
				                
				      	        return true;
			  				}
			      			
			      		 }
			  			
			  			if(null != postid && postid.equals("18"))
			      		{
			  				if(bean.getStatus() == 28)
			  				{
			  					int newStatus = saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);
			  	                
			  	                if (newStatus != oldStatus)
			  	                {
			  	                    bean.setStatus(newStatus);

			  	                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
			  	                }

			  	                // 记录操作日志
			  	                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
			  	                
			  	      	        return true;
			  				}
			  				else if(processBean.getPostId().equals("16"))
			  				{
			  					int newStatus = saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_LOCATION, 0);
				                
				                if (newStatus != oldStatus)
				                {
				                    bean.setStatus(newStatus);
			
				                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
				                }
			
				                // 记录操作日志
				                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
				                
				      	        return true;
			  				}
			  				else if(processBean.getPostId().equals("20"))
			  				{
			  					int newStatus = saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_DEPUTYCEO, 0);
				                
				                if (newStatus != oldStatus)
				                {
				                    bean.setStatus(newStatus);
			
				                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
				                }
			
				                // 记录操作日志
				                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
				                
				      	        return true;
			  				}
			      			
			      		 }
			  			
			  			if(null != postid && postid.equals("20"))
			      		{
			  				if(bean.getStatus() == 28)
			  				{
			  					int newStatus = saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);
			  	                
			  	                if (newStatus != oldStatus)
			  	                {
			  	                    bean.setStatus(newStatus);

			  	                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
			  	                }

			  	                // 记录操作日志
			  	                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
			  	                
			  	      	        return true;
			  				}
			  				else if(processBean.getPostId().equals("16"))
			  				{
			  					int newStatus = saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_LOCATION, 0);
				                
				                if (newStatus != oldStatus)
				                {
				                    bean.setStatus(newStatus);
			
				                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
				                }
			
				                // 记录操作日志
				                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
				                
				      	        return true;
			  				}
			  				else if(processBean.getPostId().equals("20"))
			  				{
			  					int newStatus = saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_DEPUTYCEO, 0);
				                
				                if (newStatus != oldStatus)
				                {
				                    bean.setStatus(newStatus);
			
				                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
				                }
			
				                // 记录操作日志
				                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
				                
				      	        return true;
			  				}
			      			
			      		 }
		      		}
		  		}
    	return true;
    }

    /**
     * logicProcess
     * 
     * @param user
     * @param param
     * @param bean
     * @param oldStatus
     * @throws MYException
     */
    private void logicProcess(User user, TcpParamWrap param, TravelApplyVO bean, int oldStatus)
        throws MYException
    {
        // 这里需要特殊处理的(稽核修改金额)
        if (oldStatus == TcpConstanst.TCP_STATUS_WAIT_CHECK)
        {
            // 稽核需要重新整理pay和重新预算
            if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_YES
            		|| bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_NO_BUTHOLD)
            {
            	if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_YES)
            	{
            		List<TravelApplyPayBean> newPayList = (List<TravelApplyPayBean>)param.getOther();

                    long newBrrow = 0L;

                    for (TravelApplyPayBean travelApplyPayBean : newPayList)
                    {
                        newBrrow += travelApplyPayBean.getCmoneys();
                    }

                    bean.setBorrowTotal(newBrrow);

                    bean.setPayList(newPayList);
                    
                    // 成功后更新支付列表
                    travelApplyPayDAO.updateAllEntityBeans(newPayList);
            	}

                // 如果没有借款不需要重新预算
                checkBudget(user, bean, 1);

                // 不借款仅用于占检查预算且占预算，所以借款额还是0
                if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_NO_BUTHOLD)
                {
                	bean.setBorrowTotal(0);
                }

                travelApplyDAO.updateBorrowTotal(param.getId(), bean.getBorrowTotal());
                
                String scompliance = param.getCompliance();
                // 更新合规标识
                travelApplyDAO.updateCompliance(param.getId(), scompliance);
            }
        }

        if (oldStatus == TcpConstanst.TCP_STATUS_WAIT_PAY)
        {
            String dutyId = param.getDutyId();

            if (dutyId == null)
            {
                throw new MYException("缺少纳税实体,请确认操作");
            }

            travelApplyDAO.updateDutyId(bean.getId(), dutyId);
        }

        if (oldStatus == TcpConstanst.TCP_STATUS_WAIT_PAY
            && bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_YES)
        {
            // 财务付款
            List<OutBillBean> outBillList = (List<OutBillBean>)param.getOther();

            String dutyId = param.getDutyId();

            double total = 0.0d;
            StringBuffer idBuffer = new StringBuffer();
            for (OutBillBean outBill : outBillList)
            {
                BankBean bank = bankDAO.find(outBill.getBankId());

                if (bank == null)
                {
                    throw new MYException("数据错误,请确认操作");
                }

                if ( !bank.getDutyId().equals(dutyId))
                {
                    throw new MYException("银行和纳税实体不对应,请确认操作");
                }

                // 生成付款单
                createOutBill(user, outBill, bean);

                total += outBill.getMoneys();

                idBuffer.append(outBill.getId()).append(';');
            }

            if (MathTools.doubleToLong2(total) != bean.getBorrowTotal())
            {
                throw new MYException("付款金额[%.2f]不等于借款金额[%.2f]", total, MathTools
                    .longToDouble2(bean.getBorrowTotal()));
            }

            Collection<TcpPayListener> listenerMapValues = this.listenerMapValues();

            for (TcpPayListener tcpPayListener : listenerMapValues)
            {
            	// 中收，在支付确认后，红冲提交时产生的凭证
            	if (bean.getType() == TcpConstanst.TCP_APPLYTYPE_MID) {
            		tcpPayListener.onSubmitMidTravelApply(user, bean, -1);
            	}
            	
                // TODO_OSGI 这里是出差申请的借款生成凭证/中收
                tcpPayListener.onPayTravelApply(user, bean, outBillList);
            }

            // 更新预算使用状态
            budgetManager.updateBudgetLogUserTypeByRefIdWithoutTransactional(user, bean.getId(),
                BudgetConstant.BUDGETLOG_USERTYPE_REAL, idBuffer.toString());
        }

        // 采购货比三家
        if (oldStatus == TcpConstanst.TCP_STATUS_WAIT_BUY
            && bean.getType() == TcpConstanst.TCP_APPLYTYPE_STOCK)
        {
            List<TravelApplyItemBean> newItemList = (List<TravelApplyItemBean>)param.getOther2();
            List<TravelApplyItemVO> itemVOList = bean.getItemVOList();

            long total = 0L;
            
            if(null != itemVOList && itemVOList.size()>0)
            {
            	
	            for (TravelApplyItemVO travelApplyItemVO : itemVOList)
	            {
	            	if(null != newItemList && newItemList.size() > 0)
	            	{
		                for (TravelApplyItemBean travelApplyItemBean : newItemList)
		                {
		                    if (travelApplyItemVO.getId().equals(travelApplyItemBean.getId()))
		                    {
		                        travelApplyItemVO.setCheckPrices(travelApplyItemBean.getCheckPrices());
		                        travelApplyItemVO.setMoneys(travelApplyItemBean.getMoneys());
		                        travelApplyItemVO.setPurpose(travelApplyItemBean.getPurpose());
		                    }
		                }
		                // 更新采购项
		                travelApplyItemDAO.updateEntityBean(travelApplyItemVO);
		
		                total += travelApplyItemVO.getMoneys();
	            	}  
	            }
            }
            travelApplyDAO.updateTotal(param.getId(), total);
            if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_YES)
            {
                // 更新预算(重新加入预占)
                checkBudget(user, bean, 1);
            }
        }
    }

    /**
     * createOutBill
     * 
     * @param user
     * @param outBill
     * @param apply
     * @throws MYException
     */
    private void createOutBill(User user, OutBillBean outBill, TravelApplyVO apply)
        throws MYException
    {
        // 自动生成付款单
        outBill.setDescription(DefinedCommon.getValue("tcpType", apply.getType()) + "申请借款的付款:"
                               + apply.getId());

        outBill.setLocationId(user.getLocationId());

        outBill.setLogTime(TimeTools.now());

        outBill.setType(FinanceConstant.OUTBILL_TYPE_BORROW);

        outBill.setOwnerId(apply.getBorrowStafferId());

        outBill.setStafferId(user.getStafferId());

        outBill.setProvideId("");

        // 借款的单据
        outBill.setStockId(apply.getId());

        outBill.setStockItemId("");

        billManager.addOutBillBeanWithoutTransaction(user, outBill);
    }

    /**
     * 权限
     * 
     * @param user
     * @param id
     * @throws MYException
     */
    private void checkAuth(User user, String id)
        throws MYException
    {
        List<TcpApproveBean> approveList = tcpApproveDAO.queryEntityBeansByFK(id);

        boolean hasAuth = false;

        for (TcpApproveBean tcpApproveBean : approveList)
        {
            if (tcpApproveBean.getApproverId().equals(user.getStafferId()))
            {
                hasAuth = true;

                break;
            }
        }

        if ( !hasAuth)
        {
            throw new MYException("没有操作权限,请确认操作");
        }
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean rejectTravelApplyBean(User user, TcpParamWrap param)
        throws MYException
    {
        String id = param.getId();
        String reason = param.getReason();
        String type = param.getType();

        JudgeTools.judgeParameterIsNull(user, id);

        TravelApplyVO bean = findVO(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 权限
        checkAuth(user, id);
        
        // 中收申请，同时删除凭证。如果此时凭证所在的月份已经月结，要求反月结才能继续驳回操作
        if (bean.getType() == TcpConstanst.TCP_APPLYTYPE_MID) {
        	Collection<TcpPayListener> listenerMapValues = this.listenerMapValues();

            for (TcpPayListener tcpPayListener : listenerMapValues)
            {
                tcpPayListener.onRejectMidTravelApply(user, bean);
            }
        }
        
        // 获得当前的处理环节
        // TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(), bean.getStatus());

        // 获得上一步
        FlowLogBean lastLog = flowLogDAO.findLastLog(bean.getId());

        if (lastLog == null)
        {
            // 驳回到初始
            type = "1";
        }
        else
        {
            int nextStatus = lastLog.getAfterStatus();

            // 驳回上一步到处是也是type=1
            if (TCPHelper.isTravelApplyInit(nextStatus))
            {
                type = "1";
            }
        }

        // 驳回到初始
        if ("1".equals(type))
        {
            // 结束了需要清空
            tcpApproveDAO.deleteEntityBeansByFK(bean.getId());

            // 清空预占的金额
            budgetManager.deleteBudgetLogListWithoutTransactional(user, bean.getId());

            int oldStatus = bean.getStatus();

            bean.setStatus(TcpConstanst.TCP_STATUS_REJECT);

            travelApplyDAO.updateStatus(bean.getId(), bean.getStatus());

            // 这里驳回需要删除pay
            if (false && bean.getType() == TcpConstanst.TCP_APPLYTYPE_STOCK)
            {
                travelApplyPayDAO.deleteEntityBeansByFK(bean.getId());
            }

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_REJECT);
        }
        // 驳回到上一步
        else
        {
            // 获得上一步的人
            String actorId = lastLog.getActorId();

            int nextStatus = lastLog.getAfterStatus();

            int newStatus = saveApprove(user, actorId, bean, nextStatus,
                TcpConstanst.TCP_POOL_COMMON);

            int oldStatus = bean.getStatus();

            bean.setStatus(newStatus);

            travelApplyDAO.updateStatus(bean.getId(), newStatus);

            if (TCPHelper.isTravelApplyInit(newStatus))
            {
                // 清空预占的金额
                budgetManager.deleteBudgetLogListWithoutTransactional(user, bean.getId());
            }

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_REJECT_PRE);
        }
        
        return true;
    }
    
    @Transactional(rollbackFor = MYException.class)
    public boolean rejectVocationAndWork(User user, TcpParamWrap param)
        throws MYException
    {
        String id = param.getId();
        String reason = param.getReason();
        String type = param.getType();

        JudgeTools.judgeParameterIsNull(user, id);

        TravelApplyVO bean = findVO(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 权限
        checkAuth(user, id);

        // 获得当前的处理环节
        // TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(), bean.getStatus());

        // 获得上一步
        FlowLogBean lastLog = flowLogDAO.findLastLog(bean.getId());

        if (lastLog == null)
        {
            // 驳回到初始
            type = "1";
        }
        else
        {
            int nextStatus = lastLog.getAfterStatus();

            // 驳回上一步到处是也是type=1
            if (TCPHelper.isTravelApplyInit(nextStatus))
            {
                type = "1";
            }
        }

        // 驳回到初始
        if ("1".equals(type))
        {
            // 结束了需要清空
            tcpApproveDAO.deleteEntityBeansByFK(bean.getId());

            // 清空预占的金额
            budgetManager.deleteBudgetLogListWithoutTransactional(user, bean.getId());

            int oldStatus = bean.getStatus();

            bean.setStatus(TcpConstanst.TCP_STATUS_REJECT);

            travelApplyDAO.updateStatus(bean.getId(), bean.getStatus());

            // 这里驳回需要删除pay
            if (false && bean.getType() == TcpConstanst.TCP_APPLYTYPE_STOCK)
            {
                travelApplyPayDAO.deleteEntityBeansByFK(bean.getId());
            }

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_REJECT);
        }
        // 驳回到上一步
        else
        {
            // 获得上一步的人
            String actorId = lastLog.getActorId();

            int nextStatus = lastLog.getAfterStatus();

            saveApprove(user, actorId, bean, nextStatus,
                TcpConstanst.TCP_POOL_COMMON);

            int oldStatus = bean.getStatus();
            int newStatus = 2;//部门经理
            
            if(oldStatus==6)
            {
            	newStatus = 4;
            }
            else if(oldStatus==4)
            {
            	newStatus = 2;
            }
            bean.setStatus(newStatus);

            travelApplyDAO.updateStatus(bean.getId(), newStatus);

            if (TCPHelper.isTravelApplyInit(newStatus))
            {
                // 清空预占的金额
                budgetManager.deleteBudgetLogListWithoutTransactional(user, bean.getId());
            }

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_REJECT_PRE);
        }

        return true;
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
    private int saveApprove(User user, List<String> processList, TravelApplyVO bean,
                            int nextStatus, int pool)
        throws MYException
    {
        // 获得当前的处理环节
        TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(), bean.getStatus());
        if (token == null || token.getSingeAll() == 0 )
        {
            // 清除之前的处理人
            tcpApproveDAO.deleteEntityBeansByFK(bean.getId());
        }
        else
        {
            // 仅仅删除自己的
            List<TcpApproveBean> approveList = tcpApproveDAO.queryEntityBeansByFK(bean.getId());
            for (TcpApproveBean tcpApproveBean : approveList)
            {
                if (tcpApproveBean.getApproverId().equals(user.getStafferId()))
                {
                    tcpApproveDAO.deleteEntityBean(tcpApproveBean.getId());
                }
            }
        }

        List<TcpApproveBean> appList = tcpApproveDAO.queryEntityBeansByFK(bean.getId());
        if (token == null || appList.size() == 0 || token.getSingeAll() == 0)
        {
            for (String processId : processList)
            {
                // 进入审批状态
                TcpApproveBean approve = new TcpApproveBean();

                approve.setId(commonDAO.getSquenceString20());
                approve.setApplyerId(bean.getStafferId());
                approve.setApplyId(bean.getId());
                approve.setApproverId(processId);
                approve.setFlowKey(bean.getFlowKey());
                approve.setLogTime(TimeTools.now());
                approve.setDepartmentId(bean.getDepartmentId());
                approve.setName(bean.getName());
                approve.setStatus(nextStatus);
                approve.setTotal(bean.getTotal());
                approve.setCheckTotal(bean.getBorrowTotal());
                approve.setType(bean.getType());
                approve.setStype(bean.getStype());
                approve.setPool(pool);
                approve.setPayType(TcpConstanst.PAYTYPE_GPAY_BO);

                tcpApproveDAO.saveEntityBean(approve);
            }

            // 如果是共享的不发送邮件
            if (pool == TcpConstanst.TCP_POOL_COMMON)
            {
                MailBean mail = new MailBean();

                mail.setTitle(bean.getStafferName() + "的"
                              + DefinedCommon.getValue("tcpType", bean.getType()) + "申请["
                              + bean.getName() + "]等待您的处理.");

                mail.setContent(mail.getContent());

                mail.setSenderId(StafferConstant.SUPER_STAFFER);

                mail.setReveiveIds(listToString(processList));

                mail.setReveiveIds2(bean.getStafferId());
                
                if(bean.getType()== TcpConstanst.VOCATION_WORK)
                {
                	mail.setHref(TcpConstanst.TCP_COMMIS_PROCESS_URL + bean.getId());	
                }
                else
                {
                	mail.setHref(TcpConstanst.TCP_TRAVELAPPLY_PROCESS_URL + bean.getId());
                }
                // send mail
                mailMangaer.addMailWithoutTransactional(UserHelper.getSystemUser(), mail);
            }
        }
        else
        {
            // 会签
            nextStatus = bean.getStatus();
        }

        return nextStatus;
    }
    

    /**
     * @param processers
     * @return
     */
    private String listToString(List<String> processers)
    {
        StringBuilder builder = new StringBuilder();

        for (String string : processers)
        {
            builder.append(string).append(';');
        }

        return builder.toString();
    }

    /**
     * 进入审批状态
     * 
     * @param processId
     * @param bean
     * @param pool
     * @throws MYException
     */
    private int saveApprove(User user, String processId, TravelApplyVO bean, int nextStatus,
                            int pool)
        throws MYException
    {
        List<String> processList = new ArrayList();

        processList.add(processId);

        return saveApprove(user, processList, bean, nextStatus, pool);
    }
    

    /**
     * 校验预算(且占用预算)
     * 
     * @param user
     * @param bean
     * @param type
     *            0:new add 1:update
     * @throws MYException
     */
    private void checkBudget(User user, TravelApplyVO bean, int type)
        throws MYException
    {
        // 不借款的不占用预算
        if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_NO)
        {
            return;
        }

        // 不借款，但占预算
        if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_NO_BUTHOLD)
        {
        	if (bean.getBorrowTotal() == 0)
        	{
        		return;
        	}
        }
        
        if (type == 1)
        {
            // 先删除之前的
            budgetManager.deleteBudgetLogListWithoutTransactional(user, bean.getId());
        }

        // 借款比
        double borrowRadio = 1.0d;

        if (bean.getTotal() != 0)
        {
            borrowRadio = (double)bean.getBorrowTotal() / (double)bean.getTotal();
        }

        List<TravelApplyItemVO> itemVOList = bean.getItemVOList();

        List<TcpShareVO> shareVOList = bean.getShareVOList();

        List<BudgetLogBean> logList = new ArrayList();

        long hasUse = 0L;

        int shareType = 0;

        for (Iterator iterator = shareVOList.iterator(); iterator.hasNext();)
        {
            TcpShareVO tcpShareVO = (TcpShareVO)iterator.next();

            // 每个申请项扣除的费用
            for (Iterator ite = itemVOList.iterator(); ite.hasNext();)
            {
                TravelApplyItemVO travelApplyItemVO = (TravelApplyItemVO)ite.next();

                if (travelApplyItemVO.getMoneys() == 0)
                {
                    continue;
                }

                // 预算鉴权()
                BudgetLogBean log = new BudgetLogBean();

                logList.add(log);

                log.setBudgetId(tcpShareVO.getBudgetId());

                BudgetItemBean item = budgetItemDAO.findByBudgetIdAndFeeItemId(tcpShareVO
                    .getBudgetId(), travelApplyItemVO.getFeeItemId());

                if (item == null)
                {

                    throw new MYException("预算[%s]里面缺少预算项[%s],请确认操作", tcpShareVO.getBudgetName(),
                        travelApplyItemVO.getFeeItemName());
                }

                log.setBudgetItemId(item.getId());

                log.setDepartmentId(tcpShareVO.getDepartmentId());

                log.setFeeItemId(travelApplyItemVO.getFeeItemId());

                log.setFromType(BudgetConstant.BUDGETLOG_FROMTYPE_TCP);

                log.setLocationId(user.getLocationId());

                log.setLog(DefinedCommon.getValue("tcpType", bean.getType()) + "申请[" + bean.getId()
                           + "]占用预算");

                log.setLogTime(TimeTools.now());

                log.setRefId(bean.getId());

                log.setRefSubId(travelApplyItemVO.getId());

                // 使用人
                log.setStafferId(bean.getBorrowStafferId());

                // 预占
                log.setUserType(BudgetConstant.BUDGETLOG_USERTYPE_PRE);

                long useMoney = 0;

                if (tcpShareVO.getRatio() != 0)
                {
                    // 这里肯定有误差的
                    useMoney = Math.round( (tcpShareVO.getRatio() / 100.0) * borrowRadio
                                          * travelApplyItemVO.getMoneys());
                }
                else
                {
                    // 使用实际的金额
                    useMoney = Math.round( (getShareratio(shareVOList, tcpShareVO) / 100.0d)
                                          * borrowRadio * travelApplyItemVO.getMoneys());

                    shareType = 1;
                }

                log.setMonery(useMoney);

                hasUse += useMoney;
            }
        }

        // 实际的二次调整
        if (shareType == 1)
        {
            resetShareratio(shareVOList, bean.getBorrowTotal());

            // 这里的实际使用的误差比较复杂(每个分担必须等于)/存在稽核后预算变小,但是实际的分担较大
            for (TcpShareVO tcpShareVO : shareVOList)
            {
                long btotal = 0;

                for (BudgetLogBean budgetLogBean : logList)
                {
                    if (budgetLogBean.getBudgetId().equals(tcpShareVO.getBudgetId()))
                    {
                        btotal += budgetLogBean.getMonery();
                    }
                }

                // 金额有差异
                if (btotal != tcpShareVO.getRealMonery())
                {
                    // 多余的金额(可能是负数哦)
                    long cache = tcpShareVO.getRealMonery() - btotal;

                    for (BudgetLogBean budgetLogBean : logList)
                    {
                        if (budgetLogBean.getBudgetId().equals(tcpShareVO.getBudgetId()))
                        {
                            if (budgetLogBean.getMonery() + cache >= 0)
                            {
                                budgetLogBean.setMonery(budgetLogBean.getMonery() + cache);

                                cache = 0;

                                break;
                            }
                            else
                            {
                                // 强制为0
                                budgetLogBean.setMonery(0);

                                // 顺差减少
                                cache = budgetLogBean.getMonery() + cache;
                            }
                        }
                    }

                    if (cache > 0)
                    {
                        // 这里说明有问题啊
                        badLog.equals("申请借款[" + bean.getId() + "]在分担上存在越界:" + cache + ".预算为:"
                                      + tcpShareVO.getBudgetName());
                    }
                }
            }
        }

        // 消除最终的误差
        long lasttotla = 0;

        for (BudgetLogBean budgetLogBean : logList)
        {
            lasttotla += budgetLogBean.getMonery();
        }

        long chae = lasttotla - bean.getBorrowTotal();

        if (chae != 0)
        {
            for (BudgetLogBean budgetLogBean : logList)
            {
                if (budgetLogBean.getMonery() > chae)
                {
                    budgetLogBean.setMonery(budgetLogBean.getMonery() - chae);

                    break;
                }
            }
        }

        // 进入使用日志,如果超出预算会抛出异常的
        budgetManager.addBudgetLogListWithoutTransactional(user, bean.getId(), logList);
    }

    private int getShareratio(List<TcpShareVO> shareVOList, TcpShareVO vo)
    {
        double total = 0;

        for (TcpShareVO tcpShareVO : shareVOList)
        {
            total += tcpShareVO.getRealMonery();
        }

        int ratio = (int) (vo.getRealMonery() / total * 100);

        if (ratio == 0)
        {
            ratio = 1;
        }

        return ratio;
    }

    /**
     * 修改后金额自动重新设置分担
     * 
     * @param shareVOList
     * @param newBorrowal
     */
    private void resetShareratio(List<TcpShareVO> shareVOList, long newBorrowal)
    {
        double total = 0;

        for (TcpShareVO tcpShareVO : shareVOList)
        {
            total += tcpShareVO.getRealMonery();
        }

        if (total == newBorrowal)
        {
            return;
        }

        // 比例啊
        double ratio = newBorrowal / (total + 0.0d);

        long newtotal = 0;

        for (TcpShareVO tcpShareVO : shareVOList)
        {
            tcpShareVO.setRealMonery(Math.round(tcpShareVO.getRealMonery() * ratio));

            newtotal += tcpShareVO.getRealMonery();
        }

        // 修复分担
        if (newtotal != newBorrowal)
        {
            // 多余的需要加入
            long cache = newBorrowal - newtotal;

            for (TcpShareVO tcpShareVO : shareVOList)
            {
                if (tcpShareVO.getRealMonery() + cache >= 0)
                {
                    tcpShareVO.setRealMonery(tcpShareVO.getRealMonery() + cache);

                    break;
                }
            }
        }

    }

    /**
     * checkApply
     * 
     * @param bean
     * @throws MYException
     */
    private void checkApply(TravelApplyBean bean)
        throws MYException
    {
        // 验证
        if (bean.getBorrowTotal() > bean.getTotal())
        {
            throw new MYException("借款金额[%f]大于总费用[%f]", bean.getBorrowTotal() / 100.0d, bean
                .getTotal() / 100.0d);
        }

        int ratioTotal = 0;

        int shareTotal = 0;
        if(null != bean.getShareList() && bean.getShareList().size() > 0)
        {
        	
	        for (TcpShareBean tcpShareBean : bean.getShareList())
	        {
	            tcpShareBean.setId(commonDAO.getSquenceString20());
	            tcpShareBean.setRefId(bean.getId());
	
	            ratioTotal += tcpShareBean.getRatio();
	            shareTotal += tcpShareBean.getRealMonery();
	        }
        }
        // 要么全是0,就是根据金额去分担(支持非比例分担)
        if (ratioTotal != 100 && ratioTotal != 0)
        {
            throw new MYException("分担比例之和必须是100");
        }

        if (ratioTotal > 0)
        {
        	if(null != bean.getShareList() && bean.getShareList().size() > 0)
        	{
        		
	            for (TcpShareBean tcpShareBean : bean.getShareList())
	            {
	                if (tcpShareBean.getRatio() <= 0)
	                {
	                    throw new MYException("分担比例不能小于0,且必须是整数");
	                }
	            }
        	}
        }
        else
        {
        	if(null != bean.getShareList() && bean.getShareList().size() > 0)
        	{
	            for (TcpShareBean tcpShareBean : bean.getShareList())
	            {
	                if (tcpShareBean.getRealMonery() <= 0)
	                {
	                    throw new MYException("分担金额比例不能小于0");
	                }
	            }
        	}
        }

        // 下面的申请暂时不实现分担,无法实现
//        if (ratioTotal == 0 && bean.getBorrowTotal() != shareTotal)
//        {
//            throw new MYException("费用申请借款的总金额[%.2f]必须和分担的金额[%.2f]一致", MathTools.longToDouble2(bean
//                .getTotal()), MathTools.longToDouble2(shareTotal));
//        }
    }

    /**
     * saveApply
     * 
     * @param user
     * @param bean
     */
    public void saveApply(User user, TravelApplyBean bean)
    {
        TcpApplyBean apply = new TcpApplyBean();

        apply.setId(bean.getId());
        apply.setName(bean.getId());
        apply.setFlowKey(bean.getFlowKey());
        apply.setApplyId(bean.getId());
        apply.setApplyId(user.getStafferId());
        apply.setDepartmentId(bean.getDepartmentId());
        apply.setType(bean.getType());
        apply.setStype(bean.getStype());
        apply.setStatus(TcpConstanst.TCP_STATUS_INIT);
        apply.setTotal(bean.getTotal());
        apply.setLogTime(bean.getLogTime());
        apply.setDescription(bean.getDescription());
        apply.setPayType(TcpConstanst.PAYTYPE_GPAY_BO);

        tcpApplyDAO.saveEntityBean(apply);
    }
    

    /**
     * saveFlowLog/PublicConstant.OPRMODE_PASS
     * 
     * @param user
     * @param preStatus
     * @param apply
     * @param reason
     * @param oprMode
     */
    private void saveFlowLog(User user, int preStatus, TravelApplyBean apply, String reason,
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

        log.setAfterStatus(apply.getStatus());

        flowLogDAO.saveEntityBean(log);

        // 先删除
        ConditionParse condition = new ConditionParse();
        condition.addWhereStr();
        condition.addCondition("stafferId", "=", user.getStafferId());
        condition.addCondition("refId", "=", apply.getId());
        tcpHandleHisDAO.deleteEntityBeansByCondition(condition);

        // 记录处理历史
        TcpHandleHisBean his = new TcpHandleHisBean();
        his.setId(commonDAO.getSquenceString20());
        his.setLogTime(TimeTools.now());
        his.setRefId(apply.getId());
        his.setStafferId(user.getStafferId());
        his.setApplyId(apply.getStafferId());
        his.setType(apply.getType());
        his.setName(apply.getName());

        tcpHandleHisDAO.saveEntityBean(his);
    }
    

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.tcp.manager.TravelApplyManager#updateTravelApplyBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.tcp.bean.TravelApplyBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean updateTravelApplyBean(User user, TravelApplyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        TravelApplyBean old = travelApplyDAO.find(bean.getId());
        
        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !bean.getStafferId().equals(user.getStafferId()))
        {
            throw new MYException("只能修改自己的申请");
        }

//        bean.setStatus(TcpConstanst.TCP_STATUS_INIT);

        // 借款人就是自己
        if (StringTools.isNullOrNone(bean.getBorrowStafferId()))
        {
            bean.setBorrowStafferId(user.getStafferId());
        }

        // 获取flowKey
//        if (!bean.getFlowKey().equals(TcpFlowConstant.EXTRA_WORK_AND_LEAVE_CEO))
//        	TCPHelper.setFlowKey(bean);
        
        bean.setFlowKey(TcpFlowConstant.EXTRA_WORK_AND_LEAVE_CEO);

        // 先清理
        travelApplyItemDAO.deleteEntityBeansByFK(bean.getId());
        travelApplyPayDAO.deleteEntityBeansByFK(bean.getId());
        tcpShareDAO.deleteEntityBeansByFK(bean.getId());
        attachmentDAO.deleteEntityBeansByFK(bean.getId());

        // TravelApplyItemBean
        List<TravelApplyItemBean> itemList = bean.getItemList();
        if(null != itemList && itemList.size() > 0)
        {
        	
	        for (TravelApplyItemBean travelApplyItemBean : itemList)
	        {
	            travelApplyItemBean.setId(commonDAO.getSquenceString20());
	            travelApplyItemBean.setParentId(bean.getId());
	        }
	
	        travelApplyItemDAO.saveAllEntityBeans(itemList);
        }

        if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_YES)
        {
            long temp = 0L;

            List<TravelApplyPayBean> payList = bean.getPayList();
            if(null != payList && payList.size() > 0)
            {
	            for (TravelApplyPayBean travelApplyPayBean : payList)
	            {
	                travelApplyPayBean.setId(commonDAO.getSquenceString20());
	                travelApplyPayBean.setParentId(bean.getId());
	
	                temp += travelApplyPayBean.getMoneys();
	            }
	
	            travelApplyPayDAO.saveAllEntityBeans(payList);
            }
            bean.setBorrowTotal(temp);
        }
        else
        {
            bean.setBorrowTotal(0);
        }

        checkApply(bean);

        List<TcpShareBean> shareList = bean.getShareList();
        if(null != shareList && shareList.size() > 0)
        {
	        for (TcpShareBean tcpShareBean : shareList)
	        {
	            tcpShareBean.setId(commonDAO.getSquenceString20());
	            tcpShareBean.setRefId(bean.getId());
	        }
	
	        tcpShareDAO.saveAllEntityBeans(shareList);
        }
        
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
        
        travelApplyDAO.updateEntityBean(bean);

        saveFlowLog(user, old.getStatus(), bean, "自动修改保存", PublicConstant.OPRMODE_SAVE);

        return true;
    }
    
    
    /**
     * 增加附件
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean updateAttachmentList(User user, TravelApplyBean bean)
    {
    	
    	List<AttachmentBean> attachmentList = bean.getAttachmentList();
    	
    	attachmentDAO.deleteByEntityBeans(attachmentList);
    	
//    	String rootPath = ConfigLoader.getProperty("tcpAttachmentPath");
//    	
//    	for (AttachmentBean attachmentBean : attachmentList)
//        {
//            FileTools.deleteFile(rootPath + attachmentBean.getPath());
//        }

        for (AttachmentBean attachmentBean : attachmentList)
        {
            attachmentBean.setId(commonDAO.getSquenceString20());
            attachmentBean.setRefId(bean.getId());
        }
        attachmentDAO.saveAllEntityBeans(attachmentList);

        boolean b = travelApplyDAO.updateEntityBean(bean);
    	return b;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean deleteTravelApplyBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        TravelApplyBean bean = travelApplyDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !TCPHelper.canTravelApplyDelete(bean))
        {
            throw new MYException("不是初始态和驳回态,不能删除");
        }

        // 删除
        travelApplyItemDAO.deleteEntityBeansByFK(bean.getId());
        travelApplyPayDAO.deleteEntityBeansByFK(bean.getId());
        tcpShareDAO.deleteEntityBeansByFK(bean.getId());
        flowLogDAO.deleteEntityBeansByFK(bean.getId());
        // 删除预算使用,一般都是空
        budgetManager.deleteBudgetLogListWithoutTransactional(user, bean.getId());

        String rootPath = ConfigLoader.getProperty("tcpAttachmentPath");

        List<AttachmentBean> attachmenList = attachmentDAO.queryEntityBeansByFK(id);

        for (AttachmentBean attachmentBean : attachmenList)
        {
            FileTools.deleteFile(rootPath + attachmentBean.getPath());
        }

        attachmentDAO.deleteEntityBeansByFK(bean.getId());

        travelApplyDAO.deleteEntityBean(id);

        tcpApplyDAO.deleteEntityBean(id);

        operationLog.info(user + " delete TravelApplyBean:" + bean);

        return true;
    }
    

    public TravelApplyVO findVO(String id)
    {
        TravelApplyVO bean = travelApplyDAO.findVO(id);

        if (bean == null)
        {
            return bean;
        }

        // 部门
        PrincipalshipBean depa = orgManager.findPrincipalshipById(bean.getDepartmentId());

        if (depa != null)
        {
            bean.setDepartmentName(depa.getFullName());
        }

        List<TravelApplyItemVO> itemVOList = travelApplyItemDAO.queryEntityVOsByFK(id);

        bean.setItemVOList(itemVOList);

        for (TravelApplyItemVO travelApplyItemVO : itemVOList)
        {
            travelApplyItemVO.setShowMoneys(TCPHelper
                .formatNum2(travelApplyItemVO.getMoneys() / 100.0d));
        }

        List<AttachmentBean> attachmentList = attachmentDAO.queryEntityVOsByFK(id);

        bean.setAttachmentList(attachmentList);

        List<TravelApplyPayBean> payList = travelApplyPayDAO.queryEntityVOsByFK(id);

        bean.setPayList(payList);

        List<TcpShareVO> shareList = tcpShareDAO.queryEntityVOsByFK(id);

        for (TcpShareVO tcpShareVO : shareList)
        {
            PrincipalshipBean dep = orgManager.findPrincipalshipById(tcpShareVO.getDepartmentId());

            if (dep != null)
            {
                tcpShareVO.setDepartmentName(dep.getFullName());
            }

            if (tcpShareVO.getRatio() == 0)
            {
                tcpShareVO
                    .setShowRealMonery(MathTools.longToDoubleStr2(tcpShareVO.getRealMonery()));
            }
            else
            {
                tcpShareVO.setShowRealMonery(String.valueOf(tcpShareVO.getRatio()));
            }
        }

        bean.setShareVOList(shareList);

        TCPHelper.chageVO(bean);

        // 当前处理人
        List<TcpApproveVO> approveList = tcpApproveDAO.queryEntityVOsByFK(bean.getId());

        for (TcpApproveVO tcpApproveVO : approveList)
        {
            bean.setProcesser(bean.getProcesser() + tcpApproveVO.getApproverName() + ';');
        }

        // 流程描述
        List<TcpFlowBean> flowList = tcpFlowDAO.queryEntityBeansByFK(bean.getFlowKey());

        Collections.sort(flowList, new Comparator<TcpFlowBean>()
        {
            public int compare(TcpFlowBean o1, TcpFlowBean o2)
            {
                return Integer.parseInt(o1.getId()) - Integer.parseInt(o2.getId());
            }
        });

        StringBuffer sb = new StringBuffer();

        for (TcpFlowBean tcpFlowBean : flowList)
        {
            if (bean.getStatus() == tcpFlowBean.getCurrentStatus())
            {
                sb.append("<font color=red>").append(
                    DefinedCommon.getValue("tcpStatus", tcpFlowBean.getCurrentStatus())).append(
                    "</font>").append("->");
            }
            else
            {
                sb
                    .append(DefinedCommon.getValue("tcpStatus", tcpFlowBean.getCurrentStatus()))
                    .append("->");
            }
        }

        if (bean.getStatus() == TcpConstanst.TCP_STATUS_END)
        {
            sb.append("<font color=red>").append(
                DefinedCommon.getValue("tcpStatus", TcpConstanst.TCP_STATUS_END)).append("</font>");
        }
        else
        {
            sb.append(DefinedCommon.getValue("tcpStatus", TcpConstanst.TCP_STATUS_END));
        }

        bean.setFlowDescription(sb.toString());

        return bean;
    }
    

    /**
     * @return the tcpApplyDAO
     */
    public TcpApplyDAO getTcpApplyDAO()
    {
        return tcpApplyDAO;
    }

    /**
     * @param tcpApplyDAO
     *            the tcpApplyDAO to set
     */
    public void setTcpApplyDAO(TcpApplyDAO tcpApplyDAO)
    {
        this.tcpApplyDAO = tcpApplyDAO;
    }

    /**
     * @return the tcpFlowDAO
     */
    public TcpFlowDAO getTcpFlowDAO()
    {
        return tcpFlowDAO;
    }

    /**
     * @param tcpFlowDAO
     *            the tcpFlowDAO to set
     */
    public void setTcpFlowDAO(TcpFlowDAO tcpFlowDAO)
    {
        this.tcpFlowDAO = tcpFlowDAO;
    }

    /**
     * @return the tcpPrepaymentDAO
     */
    public TcpPrepaymentDAO getTcpPrepaymentDAO()
    {
        return tcpPrepaymentDAO;
    }

    /**
     * @param tcpPrepaymentDAO
     *            the tcpPrepaymentDAO to set
     */
    public void setTcpPrepaymentDAO(TcpPrepaymentDAO tcpPrepaymentDAO)
    {
        this.tcpPrepaymentDAO = tcpPrepaymentDAO;
    }

    /**
     * @return the tcpShareDAO
     */
    public TcpShareDAO getTcpShareDAO()
    {
        return tcpShareDAO;
    }

    /**
     * @param tcpShareDAO
     *            the tcpShareDAO to set
     */
    public void setTcpShareDAO(TcpShareDAO tcpShareDAO)
    {
        this.tcpShareDAO = tcpShareDAO;
    }

    /**
     * @return the travelApplyDAO
     */
    public TravelApplyDAO getTravelApplyDAO()
    {
        return travelApplyDAO;
    }

    /**
     * @param travelApplyDAO
     *            the travelApplyDAO to set
     */
    public void setTravelApplyDAO(TravelApplyDAO travelApplyDAO)
    {
        this.travelApplyDAO = travelApplyDAO;
    }

    /**
     * @return the travelApplyItemDAO
     */
    public TravelApplyItemDAO getTravelApplyItemDAO()
    {
        return travelApplyItemDAO;
    }

    /**
     * @param travelApplyItemDAO
     *            the travelApplyItemDAO to set
     */
    public void setTravelApplyItemDAO(TravelApplyItemDAO travelApplyItemDAO)
    {
        this.travelApplyItemDAO = travelApplyItemDAO;
    }

    /**
     * @return the travelApplyPayDAO
     */
    public TravelApplyPayDAO getTravelApplyPayDAO()
    {
        return travelApplyPayDAO;
    }

    /**
     * @param travelApplyPayDAO
     *            the travelApplyPayDAO to set
     */
    public void setTravelApplyPayDAO(TravelApplyPayDAO travelApplyPayDAO)
    {
        this.travelApplyPayDAO = travelApplyPayDAO;
    }

    /**
     * @return the commonDAO
     */
    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    /**
     * @param commonDAO
     *            the commonDAO to set
     */
    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
    }

    /**
     * @return the flowLogDAO
     */
    public FlowLogDAO getFlowLogDAO()
    {
        return flowLogDAO;
    }

    /**
     * @param flowLogDAO
     *            the flowLogDAO to set
     */
    public void setFlowLogDAO(FlowLogDAO flowLogDAO)
    {
        this.flowLogDAO = flowLogDAO;
    }

    /**
     * @return the tcpApproveDAO
     */
    public TcpApproveDAO getTcpApproveDAO()
    {
        return tcpApproveDAO;
    }

    /**
     * @param tcpApproveDAO
     *            the tcpApproveDAO to set
     */
    public void setTcpApproveDAO(TcpApproveDAO tcpApproveDAO)
    {
        this.tcpApproveDAO = tcpApproveDAO;
    }

    /**
     * @return the attachmentDAO
     */
    public AttachmentDAO getAttachmentDAO()
    {
        return attachmentDAO;
    }

    /**
     * @param attachmentDAO
     *            the attachmentDAO to set
     */
    public void setAttachmentDAO(AttachmentDAO attachmentDAO)
    {
        this.attachmentDAO = attachmentDAO;
    }

    /**
     * @return the orgManager
     */
    public OrgManager getOrgManager()
    {
        return orgManager;
    }

    /**
     * @param orgManager
     *            the orgManager to set
     */
    public void setOrgManager(OrgManager orgManager)
    {
        this.orgManager = orgManager;
    }

    /**
     * @return the budgetManager
     */
    public BudgetManager getBudgetManager()
    {
        return budgetManager;
    }

    /**
     * @param budgetManager
     *            the budgetManager to set
     */
    public void setBudgetManager(BudgetManager budgetManager)
    {
        this.budgetManager = budgetManager;
    }

    /**
     * @return the budgetItemDAO
     */
    public BudgetItemDAO getBudgetItemDAO()
    {
        return budgetItemDAO;
    }

    /**
     * @param budgetItemDAO
     *            the budgetItemDAO to set
     */
    public void setBudgetItemDAO(BudgetItemDAO budgetItemDAO)
    {
        this.budgetItemDAO = budgetItemDAO;
    }

    /**
     * @return the notifyManager
     */
    public NotifyManager getNotifyManager()
    {
        return notifyManager;
    }

    /**
     * @param notifyManager
     *            the notifyManager to set
     */
    public void setNotifyManager(NotifyManager notifyManager)
    {
        this.notifyManager = notifyManager;
    }

    /**
     * @return the mailMangaer
     */
    public MailMangaer getMailMangaer()
    {
        return mailMangaer;
    }

    /**
     * @param mailMangaer
     *            the mailMangaer to set
     */
    public void setMailMangaer(MailMangaer mailMangaer)
    {
        this.mailMangaer = mailMangaer;
    }

    /**
     * @return the groupVSStafferDAO
     */
    public GroupVSStafferDAO getGroupVSStafferDAO()
    {
        return groupVSStafferDAO;
    }

    /**
     * @param groupVSStafferDAO
     *            the groupVSStafferDAO to set
     */
    public void setGroupVSStafferDAO(GroupVSStafferDAO groupVSStafferDAO)
    {
        this.groupVSStafferDAO = groupVSStafferDAO;
    }

    /**
     * @return the billManager
     */
    public BillManager getBillManager()
    {
        return billManager;
    }

    /**
     * @param billManager
     *            the billManager to set
     */
    public void setBillManager(BillManager billManager)
    {
        this.billManager = billManager;
    }

    /**
     * @return the tcpHandleHisDAO
     */
    public TcpHandleHisDAO getTcpHandleHisDAO()
    {
        return tcpHandleHisDAO;
    }

    /**
     * @param tcpHandleHisDAO
     *            the tcpHandleHisDAO to set
     */
    public void setTcpHandleHisDAO(TcpHandleHisDAO tcpHandleHisDAO)
    {
        this.tcpHandleHisDAO = tcpHandleHisDAO;
    }

    /**
     * @return the bankDAO
     */
    public BankDAO getBankDAO()
    {
        return bankDAO;
    }

    /**
     * @param bankDAO
     *            the bankDAO to set
     */
    public void setBankDAO(BankDAO bankDAO)
    {
        this.bankDAO = bankDAO;
    }

	public StafferDAO getStafferDAO() {
		return stafferDAO;
	}

	public void setStafferDAO(StafferDAO stafferDAO) {
		this.stafferDAO = stafferDAO;
	}

    
}
