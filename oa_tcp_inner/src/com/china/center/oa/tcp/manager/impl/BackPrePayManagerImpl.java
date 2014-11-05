package com.china.center.oa.tcp.manager.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.finance.bean.BackPayApplyBean;
import com.china.center.oa.finance.bean.BackPrePayApplyBean;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.constant.BackPayApplyConstant;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.BackPrePayApplyDAO;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.finance.facade.FinanceFacade;
import com.china.center.oa.finance.listener.BackPayApplyListener;
import com.china.center.oa.finance.manager.BillManager;
import com.china.center.oa.finance.vo.BackPrePayApplyVO;
import com.china.center.oa.group.dao.GroupVSStafferDAO;
import com.china.center.oa.group.vs.GroupVSStafferBean;
import com.china.center.oa.mail.bean.MailBean;
import com.china.center.oa.mail.manager.MailMangaer;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.dao.AttachmentDAO;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.helper.UserHelper;
import com.china.center.oa.publics.manager.NotifyManager;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.tcp.bean.TcpApplyBean;
import com.china.center.oa.tcp.bean.TcpApproveBean;
import com.china.center.oa.tcp.bean.TcpFlowBean;
import com.china.center.oa.tcp.bean.TcpHandleHisBean;
import com.china.center.oa.tcp.constanst.TcpConstanst;
import com.china.center.oa.tcp.constanst.TcpFlowConstant;
import com.china.center.oa.tcp.dao.TcpApplyDAO;
import com.china.center.oa.tcp.dao.TcpApproveDAO;
import com.china.center.oa.tcp.dao.TcpFlowDAO;
import com.china.center.oa.tcp.dao.TcpHandleHisDAO;
import com.china.center.oa.tcp.helper.TCPHelper;
import com.china.center.oa.tcp.manager.BackPrePayManager;
import com.china.center.oa.tcp.vo.TcpApproveVO;
import com.china.center.oa.tcp.wrap.TcpParamWrap;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

public class BackPrePayManagerImpl extends AbstractListenerManager<BackPayApplyListener> implements BackPrePayManager
{
    private final Log          operationLog       = LogFactory.getLog("opr");

    private CommonDAO commonDAO = null;
    
	private BackPrePayApplyDAO backPrePayApplyDAO = null;
	
    private FlowLogDAO flowLogDAO = null;
    
    private TcpHandleHisDAO tcpHandleHisDAO = null;
    
    private TcpApplyDAO tcpApplyDAO = null;
    
    private TcpFlowDAO tcpFlowDAO = null;
    
    private TcpApproveDAO tcpApproveDAO = null;
    
    private MailMangaer        mailMangaer        = null;
    
    private GroupVSStafferDAO groupVSStafferDAO = null;
    
    private NotifyManager      notifyManager      = null;
    
    private StafferDAO stafferDAO = null;
    
    private OrgManager orgManager = null;
    
    private InBillDAO inBillDAO = null;
    
    private BillManager billManager = null;
    
    private AttachmentDAO attachmentDAO = null;
    
    /**
	 * 
	 */
	public BackPrePayManagerImpl()
	{
		
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = MYException.class)
	public boolean addBackPrePayBean(User user, BackPrePayApplyBean bean)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, bean);

        bean.setId(commonDAO.getSquenceString20("YT"));

        bean.setStafferId(user.getStafferId());

        bean.setStatus(TcpConstanst.TCP_STATUS_INIT);

        // 获取flowKey
        bean.setFlowKey(TcpFlowConstant.BACKPREPAY_APPLY);

        checkApply(user, bean);

        backPrePayApplyDAO.saveEntityBean(bean);
        
        List<AttachmentBean> attachmentList = bean.getAttachmentList();

        for (AttachmentBean attachmentBean : attachmentList) {
            attachmentBean.setId(commonDAO.getSquenceString20());
            attachmentBean.setRefId(bean.getId());
        }

        attachmentDAO.saveAllEntityBeans(attachmentList);
        
        saveApply(user, bean);

        saveFlowLog(user, TcpConstanst.TCP_STATUS_INIT, bean, "自动提交保存", PublicConstant.OPRMODE_SAVE);

        return true;
    }

	/**
	 * 检查 原预收是否发生变化（SF 单是否为预收，且原金额没变，退款金额要小于等于原预收金额）
	 * 
	 * @param user
	 * @param bean
	 * @throws MYException
	 */
	private void checkApply(User user, BackPrePayApplyBean bean) throws MYException 
	{
		JudgeTools.judgeParameterIsNull(bean.getBillId());
		
		InBillBean inBill = inBillDAO.find(bean.getBillId());
		
		if (null == inBill) {
			throw new MYException("收款单[%s]不存在，请确认.", bean.getBillId());
		}
		
		if (inBill.getStatus() != FinanceConstant.INBILL_STATUS_NOREF) {
			throw new MYException("收款单[%s]不是预收，请确认.", bean.getBillId());
		}
		
		if (MathTools.doubleToLong2(inBill.getMoneys()) != bean.getTotal()) {
			throw new MYException("收款单[%s]预收发生变化，可能被使用过，请重新选择收款单.", bean.getBillId());
		}
	}
	
    public void saveApply(User user, BackPrePayApplyBean bean) {
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
        apply.setPayType(0);

        tcpApplyDAO.saveEntityBean(apply);
    }
	
	/**
	 * 
	 * @param user
	 * @param preStatus
	 * @param apply
	 * @param reason
	 * @param oprMode
	 */
	private void saveFlowLog(User user, int preStatus, BackPrePayApplyBean apply, String reason,
            int oprMode) {
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

        // 先删除历史处理
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
	
	@Transactional(rollbackFor = MYException.class)
	public boolean updateBackPrePayBean(User user, BackPrePayApplyBean bean)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, bean);

        BackPrePayApplyBean old = backPrePayApplyDAO.find(bean.getId());

        if (old == null) {
            throw new MYException("数据错误,请确认操作");
        }

        if (!bean.getStafferId().equals(user.getStafferId())) {
            throw new MYException("只能修改自己的申请");
        }

        bean.setStatus(TcpConstanst.TCP_STATUS_INIT);

        checkApply(user, bean);
        
        // 获取flowKey
        bean.setFlowKey(TcpFlowConstant.BACKPREPAY_APPLY);

        backPrePayApplyDAO.updateEntityBean(bean);
        
        attachmentDAO.deleteEntityBeansByFK(bean.getId());
        
        List<AttachmentBean> attachmentList = bean.getAttachmentList();

        for (AttachmentBean attachmentBean : attachmentList) {
            attachmentBean.setId(commonDAO.getSquenceString20());
            attachmentBean.setRefId(bean.getId());
        }

        attachmentDAO.saveAllEntityBeans(attachmentList);
        
        saveFlowLog(user, old.getStatus(), bean, "自动修改保存", PublicConstant.OPRMODE_SAVE);

        return true;
    }

	@Transactional(rollbackFor = MYException.class)
	public boolean submitBackPrePayBean(User user, String id, String processId)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, id);

        BackPrePayApplyVO bean = findVO(id);

        if (bean == null) {
            throw new MYException("数据错误,请确认操作");
        }

        if (!bean.getStafferId().equals(user.getStafferId())) {
            throw new MYException("只能操作自己的申请");
        }

        // 获得当前的处理环节
        TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(), bean.getStatus());

        // 进入审批状态
        int newStatus = saveApprove(user, processId, bean, token.getNextStatus(), 0);

        int oldStatus = bean.getStatus();

        bean.setStatus(newStatus);

        String newId = "";
        
        // 拆分原收款单，将未退部分拆成另一个收款单
    	synchronized (FinanceFacade.PAYMENT_APPLY_LOCK)
        {
    		if (bean.getTotal() != bean.getBackMoney()) {
    			newId = billManager.splitInBillBeanWithoutTransactional(user, bean.getBillId(), bean.getBackMoney() / 100.0d);
            	
            	InBillBean bill = inBillDAO.find(newId);

                bill.setStatus(FinanceConstant.INBILL_STATUS_PREPAYMENTS);

                inBillDAO.updateEntityBean(bill);
    		} else {
    			newId = bean.getBillId();
    			
    			InBillBean oldBill = inBillDAO.find(bean.getBillId());
    			
    			oldBill.setStatus(FinanceConstant.INBILL_STATUS_PREPAYMENTS);

                inBillDAO.updateEntityBean(oldBill);
    		}
        }
    	
//    	bean.setNewBillId(newId);
    	
    	backPrePayApplyDAO.updateStatusAndNewBillId(bean.getId(), newStatus, newId);
    	
        // 记录操作日志
        saveFlowLog(user, oldStatus, bean, "提交申请", PublicConstant.OPRMODE_SUBMIT);

        return true;
    }

    /**
     * 进入审批状态
     * 
     * @param processId
     * @param bean
     * @param pool
     * @throws MYException
     */
    private int saveApprove(User user, String processId, BackPrePayApplyVO bean, int nextStatus,
            int pool) throws MYException {
        List<String> processList = new ArrayList<String>();

        if (StringTools.isNullOrNone(processId)) {
            throw new MYException("审批的人不能为空");
        }

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
    private int saveApprove(User user, List<String> processList, BackPrePayApplyVO bean,
            int nextStatus, int pool) throws MYException {
        // 获得当前的处理环节
        TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(), bean.getStatus());

        if (token.getSingeAll() == 0) {
            // 清除之前的处理人
            tcpApproveDAO.deleteEntityBeansByFK(bean.getId());
        } else {
            // 仅仅删除自己的
            List<TcpApproveBean> approveList = tcpApproveDAO.queryEntityBeansByFK(bean.getId());

            for (TcpApproveBean tcpApproveBean : approveList) {
                if (tcpApproveBean.getApproverId().equals(user.getStafferId())) {
                    tcpApproveDAO.deleteEntityBean(tcpApproveBean.getId());
                }
            }
        }

        List<TcpApproveBean> appList = tcpApproveDAO.queryEntityBeansByFK(bean.getId());

        if (appList.size() == 0 || token.getSingeAll() == 0) {
            for (String processId : processList) {
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
                approve.setCheckTotal(bean.getBackMoney());
                approve.setType(bean.getType());
                approve.setStype(bean.getStype());
                approve.setPool(pool);
                approve.setPayType(-1);

                tcpApproveDAO.saveEntityBean(approve);
            }

            // 如果是共享的不发送邮件
            if (pool == TcpConstanst.TCP_POOL_COMMON) {
                MailBean mail = new MailBean();

                mail.setTitle(bean.getStafferName() + "的"
                        + DefinedCommon.getValue("tcpType", bean.getType()) + "申请["
                        + bean.getName() + "]等待您的处理.");

                mail.setContent(mail.getContent());

                mail.setSenderId(StafferConstant.SUPER_STAFFER);

                mail.setReveiveIds(listToString(processList));

                mail.setReveiveIds2(bean.getStafferId());

                mail.setHref(TcpConstanst.BACKPREPAY_PROCESS_URL + bean.getId());

                // send mail
                mailMangaer.addMailWithoutTransactional(UserHelper.getSystemUser(), mail);
            }
        } else {
            // 会签
            nextStatus = bean.getStatus();
        }

        return nextStatus;
    } 
	
    /**
     * @param processers
     * @return
     */
    private String listToString(List<String> processers) {
        StringBuilder builder = new StringBuilder();

        for (String string : processers) {
            builder.append(string).append(';');
        }

        return builder.toString();
    }
    
    @Transactional(rollbackFor = MYException.class)
	public boolean passBackPrePayBean(User user, TcpParamWrap param)
			throws MYException
	{
        String id = param.getId();
        String processId = param.getProcessId();
        String reason = param.getReason();

        JudgeTools.judgeParameterIsNull(user, id);

        BackPrePayApplyVO bean = findVO(id);

        if (bean == null) {
            throw new MYException("数据错误,请确认操作");
        }

        // 权限
        //checkAuth(user, id);

        int oldStatus = bean.getStatus();

        // 分支处理
        logicProcess(user, param, bean, oldStatus);
        
        // 获得当前的处理环节
        TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(), bean.getStatus());

        if (token == null) {
            throw new MYException("数据错误,请确认操作");
        }

        // 群组模式
        if (token.getNextPlugin().startsWith("group")) {
            int newStatus = saveApprove(user, processId, bean, token.getNextStatus(), 0);

            if (newStatus != oldStatus) {
                bean.setStatus(newStatus);

                backPrePayApplyDAO.updateStatus(bean.getId(), newStatus);
            }

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
        }
        // 共享池模式
        if (token.getNextPlugin().startsWith("pool")) {
            String groupId = token.getNextPlugin().substring(5);

            List<GroupVSStafferBean> vsList = groupVSStafferDAO.queryEntityBeansByFK(groupId);

            if (ListTools.isEmptyOrNull(vsList)) {
                throw new MYException("当前群组内没有人员,请确认操作");
            }

            List<String> processList = new ArrayList<String>();

            for (GroupVSStafferBean groupVSStafferBean : vsList) {
                processList.add(groupVSStafferBean.getStafferId());
            }

            int newStatus = saveApprove(user, processList, bean, token.getNextStatus(), 1);

            if (newStatus != oldStatus) {
                bean.setStatus(newStatus);

                backPrePayApplyDAO.updateStatus(bean.getId(), newStatus);
            }

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
        }
        // 插件模式
        else if (token.getNextPlugin().startsWith("plugin")) {
        	int newStatus = saveApprove(user, bean.getStafferId(), bean, token.getNextStatus(), 0);

            if (newStatus != oldStatus) {
                bean.setStatus(newStatus);

                backPrePayApplyDAO.updateStatus(bean.getId(), newStatus);
            }

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
        }
        // 结束模式
        else if (token.getNextPlugin().startsWith("end")) {
            // 结束了需要清空
            tcpApproveDAO.deleteEntityBeansByFK(bean.getId());

            bean.setStatus(token.getNextStatus());

            backPrePayApplyDAO.updateStatus(bean.getId(), bean.getStatus());

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
        }

        return true;
    }

    /**
     * CORE 出纳审批通过后，生成凭证与付款单
     * 
     * 关联销售单允许多次操作
     * 
     * @param user
     * @param param
     * @param bean
     * @param oldStatus
     * @throws MYException
     */
	@SuppressWarnings("unchecked")
	private void logicProcess(User user, TcpParamWrap param, BackPrePayApplyVO bean, int oldStatus)
            throws MYException {
		if (oldStatus == TcpConstanst.TCP_STATUS_FINANCE) {
			// 1.生成付款单 2.生成凭证
			List<OutBillBean> outBillList = (List<OutBillBean>)param.getOther();
			
            String outBillId = createOutBill(user, outBillList, bean);
            
            // 预收
            InBillBean newInBill = inBillDAO.find(bean.getNewBillId());

            if (newInBill == null)
            {
                throw new MYException("数据错误,请确认操作");
            }
            
            // 取消关联了
            newInBill.setOutId("");
            newInBill.setOutBalanceId("");
            newInBill.setStatus(FinanceConstant.INBILL_STATUS_PAYMENTS);
            newInBill.setLock(FinanceConstant.BILL_LOCK_YES);

            // 未核对
            newInBill.setCheckStatus(PublicConstant.CHECK_STATUS_INIT);
            newInBill.setChecks("");

            newInBill.setRefBillId(outBillId);
            newInBill.setDescription(newInBill.getDescription() + ";预收退款自动关联退款的付款单:" + outBillId);

            inBillDAO.updateEntityBean(newInBill);
            
            // 生成凭证
            BackPayApplyBean apply = new BackPayApplyBean();
            
            apply.setBillId(bean.getBillId());
            apply.setId(bean.getId());
            apply.setCustomerId(bean.getCustomerId());
            apply.setStafferId(bean.getStafferId());
            apply.setType(BackPayApplyConstant.TYPE_BILL);
            
            // TAX_ADD 销售退款/预收退款
            Collection<BackPayApplyListener> listenerMapValues = this.listenerMapValues();

            for (BackPayApplyListener listener : listenerMapValues)
            {
                listener.onEndBackPayApplyBean(user, apply, outBillList);
            }
		}
	}
	
	private String createOutBill(User user, List<OutBillBean> outBillList, BackPrePayApplyVO apply)
	        throws MYException
	    {
	        for (OutBillBean outBill : outBillList)
	        {
                InBillBean inBill = inBillDAO.find(apply.getBillId());

                if (inBill == null)
                {
                    throw new MYException("数据错误,请确认操作");
                }

                outBill.setDescription("预收退款:" + apply.getBillId() + ".备注:"
                                       + apply.getDescription());

                outBill.setStockId(inBill.getPaymentId());

                outBill.setRefBillId(apply.getBillId());

	            outBill.setLocationId(user.getLocationId());

	            outBill.setLogTime(TimeTools.now());

	            outBill.setType(FinanceConstant.OUTBILL_TYPE_OUTBACK);

	            outBill.setOwnerId(apply.getStafferId());

	            outBill.setStafferId(user.getStafferId());

	            outBill.setProvideId(apply.getCustomerId());

	            outBill.setLock(FinanceConstant.BILL_LOCK_YES);

	            billManager.addOutBillBeanWithoutTransaction(user, outBill);
	        }

	        return outBillList.get(0).getId();
	    }
    
    @Transactional(rollbackFor = MYException.class)
	public boolean rejectBackPrePayBean(User user, TcpParamWrap param)
			throws MYException
	{
        String id = param.getId();
        String reason = param.getReason();

        JudgeTools.judgeParameterIsNull(user, id);

        BackPrePayApplyBean bean = backPrePayApplyDAO.find(id);

        if (bean == null) {
            throw new MYException("数据错误,请确认操作");
        }

        InBillBean inBill = inBillDAO.find(bean.getNewBillId());
        
        if (null != inBill) {
        	inBill.setStatus(FinanceConstant.INBILL_STATUS_NOREF);
        	
        	inBillDAO.updateEntityBean(inBill);
        }
        
        // 权限
        //checkAuth(user, id);

        // 获得当前的处理环节
        // TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(),
        // bean.getStatus());

        // 驳回到初始
     // 结束了需要清空
        tcpApproveDAO.deleteEntityBeansByFK(bean.getId());

        int oldStatus = bean.getStatus();

        bean.setStatus(TcpConstanst.TCP_STATUS_REJECT);

        bean.setBillId("");
        bean.setNewBillId("");
        bean.setPaymentId("");
        bean.setTotal(0);
        bean.setBackMoney(0);
        
        backPrePayApplyDAO.updateEntityBean(bean);
//        backPrePayApplyDAO.updateStatus(bean.getId(), bean.getStatus());

        // 记录操作日志
        saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_REJECT);

        // 通知提交人
        notifyManager.notifyMessage(bean.getStafferId(),
                "预开票:" + bean.getName() + "被" + user.getStafferName() + "驳回,原因:" + reason);

        MailBean mail = new MailBean();

        mail.setTitle("预开票:" + bean.getName() + "被" + user.getStafferName() + "驳回,原因:"
                + reason);

        mail.setContent(mail.getContent());

        mail.setSenderId(StafferConstant.SUPER_STAFFER);

        mail.setReveiveIds(bean.getStafferId());

        mail.setHref(TcpConstanst.BACKPREPAY_DETAIL_URL + bean.getId());

        // send mail
        mailMangaer.addMailWithoutTransactional(UserHelper.getSystemUser(), mail);

        return true;
    }

    /**
     * 
     * {@inheritDoc}
     */
	public BackPrePayApplyVO findVO(String id)
	{
        BackPrePayApplyVO bean = backPrePayApplyDAO.findVO(id);

        if (bean == null) {
            return bean;
        }

        // 部门
        PrincipalshipBean depa = orgManager.findPrincipalshipById(bean.getDepartmentId());

        if (depa != null) {
            bean.setDepartmentName(depa.getFullName());
        }

        bean.setShowTotal(TCPHelper.formatNum2(bean.getTotal() / 100.0d));

        // 当前处理人
        List<TcpApproveVO> approveList = tcpApproveDAO.queryEntityVOsByFK(bean.getId());

        for (TcpApproveVO tcpApproveVO : approveList) {
            bean.setProcesser(bean.getProcesser() + tcpApproveVO.getApproverName() + ';');
        }

        // 流程描述
        List<TcpFlowBean> flowList = tcpFlowDAO.queryEntityBeansByFK(bean.getFlowKey());

        Collections.sort(flowList, new Comparator<TcpFlowBean>() {
            public int compare(TcpFlowBean o1, TcpFlowBean o2) {
                return Integer.parseInt(o1.getId()) - Integer.parseInt(o2.getId());
            }
        });

        StringBuffer sb = new StringBuffer();

        for (TcpFlowBean tcpFlowBean : flowList) {
            if (bean.getStatus() == tcpFlowBean.getCurrentStatus()) {
                sb.append("<font color=red>")
                        .append(DefinedCommon.getValue("tcpStatus", tcpFlowBean.getCurrentStatus()))
                        .append("</font>").append("->");
            } else {
                sb.append(DefinedCommon.getValue("tcpStatus", tcpFlowBean.getCurrentStatus()))
                        .append("->");
            }
        }

        if (bean.getStatus() == TcpConstanst.TCP_STATUS_END) {
            sb.append("<font color=red>")
                    .append(DefinedCommon.getValue("tcpStatus", TcpConstanst.TCP_STATUS_END))
                    .append("</font>");
        } else {
            sb.append(DefinedCommon.getValue("tcpStatus", TcpConstanst.TCP_STATUS_END));
        }

        bean.setFlowDescription(sb.toString());

        return bean;
    }
	
	@Transactional(rollbackFor = MYException.class)
	public boolean deleteBackPrePayBean(User user, String id) throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, id);

        BackPrePayApplyBean bean = backPrePayApplyDAO.find(id);

        if (bean == null) {
            throw new MYException("数据错误,请确认操作");
        }

        if (!canBackPrePayApplyDelete(bean)) {
            throw new MYException("不是初始态和驳回态,不能删除");
        }

        // 删除
        flowLogDAO.deleteEntityBeansByFK(bean.getId());

        backPrePayApplyDAO.deleteEntityBean(id);

        tcpApplyDAO.deleteEntityBean(id);

        operationLog.info(user + " delete BackPrePayApplyBean:" + bean);

        return true;
    }
	
    private boolean canBackPrePayApplyDelete(BackPrePayApplyBean bean)
    {
        if (bean.getStatus() == TcpConstanst.TCP_STATUS_INIT
            || bean.getStatus() == TcpConstanst.TCP_STATUS_REJECT)
        {
            return true;
        }

        return false;
    }

	/**
	 * @return the commonDAO
	 */
	public CommonDAO getCommonDAO() {
		return commonDAO;
	}

	/**
	 * @param commonDAO the commonDAO to set
	 */
	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	/**
	 * @return the backPrePayApplyDAO
	 */
	public BackPrePayApplyDAO getBackPrePayApplyDAO() {
		return backPrePayApplyDAO;
	}

	/**
	 * @param backPrePayApplyDAO the backPrePayApplyDAO to set
	 */
	public void setBackPrePayApplyDAO(BackPrePayApplyDAO backPrePayApplyDAO) {
		this.backPrePayApplyDAO = backPrePayApplyDAO;
	}

	/**
	 * @return the flowLogDAO
	 */
	public FlowLogDAO getFlowLogDAO() {
		return flowLogDAO;
	}

	/**
	 * @param flowLogDAO the flowLogDAO to set
	 */
	public void setFlowLogDAO(FlowLogDAO flowLogDAO) {
		this.flowLogDAO = flowLogDAO;
	}

	/**
	 * @return the tcpHandleHisDAO
	 */
	public TcpHandleHisDAO getTcpHandleHisDAO() {
		return tcpHandleHisDAO;
	}

	/**
	 * @param tcpHandleHisDAO the tcpHandleHisDAO to set
	 */
	public void setTcpHandleHisDAO(TcpHandleHisDAO tcpHandleHisDAO) {
		this.tcpHandleHisDAO = tcpHandleHisDAO;
	}

	/**
	 * @return the tcpApplyDAO
	 */
	public TcpApplyDAO getTcpApplyDAO() {
		return tcpApplyDAO;
	}

	/**
	 * @param tcpApplyDAO the tcpApplyDAO to set
	 */
	public void setTcpApplyDAO(TcpApplyDAO tcpApplyDAO) {
		this.tcpApplyDAO = tcpApplyDAO;
	}

	/**
	 * @return the tcpFlowDAO
	 */
	public TcpFlowDAO getTcpFlowDAO() {
		return tcpFlowDAO;
	}

	/**
	 * @param tcpFlowDAO the tcpFlowDAO to set
	 */
	public void setTcpFlowDAO(TcpFlowDAO tcpFlowDAO) {
		this.tcpFlowDAO = tcpFlowDAO;
	}

	/**
	 * @return the tcpApproveDAO
	 */
	public TcpApproveDAO getTcpApproveDAO() {
		return tcpApproveDAO;
	}

	/**
	 * @param tcpApproveDAO the tcpApproveDAO to set
	 */
	public void setTcpApproveDAO(TcpApproveDAO tcpApproveDAO) {
		this.tcpApproveDAO = tcpApproveDAO;
	}

	/**
	 * @return the mailMangaer
	 */
	public MailMangaer getMailMangaer() {
		return mailMangaer;
	}

	/**
	 * @param mailMangaer the mailMangaer to set
	 */
	public void setMailMangaer(MailMangaer mailMangaer) {
		this.mailMangaer = mailMangaer;
	}

	/**
	 * @return the groupVSStafferDAO
	 */
	public GroupVSStafferDAO getGroupVSStafferDAO() {
		return groupVSStafferDAO;
	}

	/**
	 * @param groupVSStafferDAO the groupVSStafferDAO to set
	 */
	public void setGroupVSStafferDAO(GroupVSStafferDAO groupVSStafferDAO) {
		this.groupVSStafferDAO = groupVSStafferDAO;
	}

	/**
	 * @return the notifyManager
	 */
	public NotifyManager getNotifyManager() {
		return notifyManager;
	}

	/**
	 * @param notifyManager the notifyManager to set
	 */
	public void setNotifyManager(NotifyManager notifyManager) {
		this.notifyManager = notifyManager;
	}

	/**
	 * @return the stafferDAO
	 */
	public StafferDAO getStafferDAO() {
		return stafferDAO;
	}

	/**
	 * @param stafferDAO the stafferDAO to set
	 */
	public void setStafferDAO(StafferDAO stafferDAO) {
		this.stafferDAO = stafferDAO;
	}

	/**
	 * @return the orgManager
	 */
	public OrgManager getOrgManager() {
		return orgManager;
	}

	/**
	 * @param orgManager the orgManager to set
	 */
	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}

	/**
	 * @return the inBillDAO
	 */
	public InBillDAO getInBillDAO() {
		return inBillDAO;
	}

	/**
	 * @param inBillDAO the inBillDAO to set
	 */
	public void setInBillDAO(InBillDAO inBillDAO) {
		this.inBillDAO = inBillDAO;
	}

	/**
	 * @return the billManager
	 */
	public BillManager getBillManager() {
		return billManager;
	}

	/**
	 * @param billManager the billManager to set
	 */
	public void setBillManager(BillManager billManager) {
		this.billManager = billManager;
	}

	/**
	 * @return the attachmentDAO
	 */
	public AttachmentDAO getAttachmentDAO() {
		return attachmentDAO;
	}

	/**
	 * @param attachmentDAO the attachmentDAO to set
	 */
	public void setAttachmentDAO(AttachmentDAO attachmentDAO) {
		this.attachmentDAO = attachmentDAO;
	}
}
