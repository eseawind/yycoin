package com.china.center.oa.tcp.manager.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.finance.bean.PreInvoiceApplyBean;
import com.china.center.oa.finance.bean.PreInvoiceVSOutBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.InsVSOutDAO;
import com.china.center.oa.finance.dao.InvoiceinsItemDAO;
import com.china.center.oa.finance.dao.PreInvoiceApplyDAO;
import com.china.center.oa.finance.dao.PreInvoiceVSOutDAO;
import com.china.center.oa.finance.vo.PreInvoiceApplyVO;
import com.china.center.oa.group.dao.GroupVSStafferDAO;
import com.china.center.oa.group.vs.GroupVSStafferBean;
import com.china.center.oa.mail.bean.MailBean;
import com.china.center.oa.mail.manager.MailMangaer;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.helper.UserHelper;
import com.china.center.oa.publics.manager.NotifyManager;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.sail.bean.OutBalanceBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.OutBalanceDAO;
import com.china.center.oa.sail.dao.OutDAO;
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
import com.china.center.oa.tcp.manager.PreInvoiceManager;
import com.china.center.oa.tcp.vo.TcpApproveVO;
import com.china.center.oa.tcp.wrap.TcpParamWrap;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

public class PreInvoiceManagerImpl implements PreInvoiceManager
{
    private final Log          operationLog       = LogFactory.getLog("opr");

    private CommonDAO commonDAO = null;
    
	private PreInvoiceApplyDAO preInvoiceApplyDAO = null;
	
	private PreInvoiceVSOutDAO preInvoiceVSOutDAO = null;
    
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
    
    private OutDAO outDAO = null;
    
    private OutBalanceDAO outBalanceDAO = null;
    
    private InvoiceinsItemDAO invoiceinsItemDAO = null;
    
    private InsVSOutDAO insVSOutDAO = null;
    
    /**
	 * 
	 */
	public PreInvoiceManagerImpl()
	{
		
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = MYException.class)
	public boolean addPreInvoiceBean(User user, PreInvoiceApplyBean bean)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, bean);

        bean.setId(commonDAO.getSquenceString20("FP"));

        bean.setStafferId(user.getStafferId());

        bean.setStatus(TcpConstanst.TCP_STATUS_INIT);

        // 获取flowKey
        bean.setFlowKey(TcpFlowConstant.PREINVOICE_APPLY);

        checkApply(user, bean);
        
        preInvoiceApplyDAO.saveEntityBean(bean);
        
        saveApply(user, bean);

        saveFlowLog(user, TcpConstanst.TCP_STATUS_INIT, bean, "自动提交保存", PublicConstant.OPRMODE_SAVE);

        return true;
    }

	/**
	 * 根据提供的销售单号检查：
	 * @param user
	 * @param bean
	 * @throws MYException
	 */
	private void checkApply(User user, PreInvoiceApplyBean bean) throws MYException 
	{
		// 1.检查是否有计划开单日期到期的,但还没有关联销售单的,如有,则不允许继续提交新的预开票申请
		int count = preInvoiceApplyDAO.countOverTimeBeans(user.getStafferId());
		
		if (count > 0){
			throw new MYException("存在超出开单计划日期的预开票申请，请先完成预开票与销售单的关联");
		}
	}
	
    public void saveApply(User user, PreInvoiceApplyBean bean) {
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
	private void saveFlowLog(User user, int preStatus, PreInvoiceApplyBean apply, String reason,
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
	public boolean updatePreInvoiceBean(User user, PreInvoiceApplyBean bean)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, bean);

        PreInvoiceApplyBean old = preInvoiceApplyDAO.find(bean.getId());

        if (old == null) {
            throw new MYException("数据错误,请确认操作");
        }

        if (!bean.getStafferId().equals(user.getStafferId())) {
            throw new MYException("只能修改自己的申请");
        }

        bean.setStatus(TcpConstanst.TCP_STATUS_INIT);

        checkApply(user, bean);
        
        // 获取flowKey
        bean.setFlowKey(TcpFlowConstant.PREINVOICE_APPLY);

        preInvoiceApplyDAO.updateEntityBean(bean);
        
        saveFlowLog(user, old.getStatus(), bean, "自动修改保存", PublicConstant.OPRMODE_SAVE);

        return true;
    }

	@Transactional(rollbackFor = MYException.class)
	public boolean submitPreInvoiceBean(User user, String id, String processId)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, id);

        PreInvoiceApplyVO bean = findVO(id);

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

        preInvoiceApplyDAO.updateStatus(bean.getId(), newStatus);

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
    private int saveApprove(User user, String processId, PreInvoiceApplyVO bean, int nextStatus,
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
    private int saveApprove(User user, List<String> processList, PreInvoiceApplyVO bean,
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
                approve.setCheckTotal(0);
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

                mail.setHref(TcpConstanst.PREINVOICE_PROCESS_URL + bean.getId());

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
	public boolean passPreInvoiceBean(User user, TcpParamWrap param)
			throws MYException
	{
        String id = param.getId();
        String processId = param.getProcessId();
        String reason = param.getReason();

        JudgeTools.judgeParameterIsNull(user, id);

        PreInvoiceApplyVO bean = findVO(id);

        if (bean == null) {
            throw new MYException("数据错误,请确认操作");
        }

        // 权限
        //checkAuth(user, id);

        int oldStatus = bean.getStatus();

        // 分支处理
        logicProcess(user, param, bean, oldStatus);
        
        if (oldStatus == TcpConstanst.TCP_STATUS_APPLY_RELATE){
        	
        	if (bean.getInvoiceMoney() < bean.getTotal())
        	{
        		// 记录操作日志
                saveFlowLog(user, oldStatus, bean, "提交", PublicConstant.OPRMODE_PASS);
                
        		return true;
        	}else{
        		// 当关联的金额满足时，判断关联的销售单的纳税实体是不是预开票申请中填写的
        		String applyDuty = bean.getDutyId();
        		
        		List<PreInvoiceVSOutBean> vsList = preInvoiceVSOutDAO.queryEntityBeansByFK(bean.getId());
        		
        		for (PreInvoiceVSOutBean vs : vsList)
        		{
        			OutBean outBean = outDAO.find(vs.getOutId());
        			
        			if (null == outBean){
        				throw new MYException("数据错误,请确认操作");
        			}else{
        				if (!outBean.getDutyId().equals(applyDuty))
        				{
        					//throw new MYException("关联的销售单[%s]的纳税实体与申请单上的纳税实体不一致", outBean.getFullId());
        					saveFlowLog(user, oldStatus, bean, "关联的销售单"+outBean.getFullId()+"的纳税实体与申请单上的纳税实体不一致", PublicConstant.OPRMODE_PASS);
        	                
        	        		return true;
        				}
        			}
        		}
        	}
        }

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

                preInvoiceApplyDAO.updateStatus(bean.getId(), newStatus);
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

                preInvoiceApplyDAO.updateStatus(bean.getId(), newStatus);
            }

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
        }
        // 插件模式
        else if (token.getNextPlugin().startsWith("plugin")) {
        	int newStatus = saveApprove(user, bean.getStafferId(), bean, token.getNextStatus(), 0);

            if (newStatus != oldStatus) {
                bean.setStatus(newStatus);

                preInvoiceApplyDAO.updateStatus(bean.getId(), newStatus);
            }

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
        }
        // 结束模式
        else if (token.getNextPlugin().startsWith("end")) {
            // 结束了需要清空
            tcpApproveDAO.deleteEntityBeansByFK(bean.getId());

            bean.setStatus(token.getNextStatus());

            preInvoiceApplyDAO.updateStatus(bean.getId(), bean.getStatus());

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
        }

        return true;
    }

    /**
     * CORE 预开票的分支处理
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
	private void logicProcess(User user, TcpParamWrap param, PreInvoiceApplyVO bean, int oldStatus)
            throws MYException {

        // 财务申请人关联销售单
        if (oldStatus == TcpConstanst.TCP_STATUS_APPLY_RELATE) {
        	double invoiceMoney = 0.0d;

        	double preInvoice = MathTools.parseDouble(TCPHelper.longToDouble2(bean.getTotal()));
        	double hasInvoice = MathTools.parseDouble(TCPHelper.longToDouble2(bean.getInvoiceMoney())); 
        	
        	// 本次新增的
        	List<PreInvoiceVSOutBean> vsList = (List<PreInvoiceVSOutBean>)param.getOther();
        	
        	List<PreInvoiceVSOutBean> lastVsList = new ArrayList<PreInvoiceVSOutBean>();
        	
        	for (PreInvoiceVSOutBean each : vsList)
        	{
        		each.setParentId(bean.getId());
        		
        		if ((hasInvoice + each.getInvoiceMoney())>= preInvoice){
        			
        			if (preInvoice - hasInvoice <= 0)
        				break;
        			
        			each.setInvoiceMoney(preInvoice - hasInvoice);
        			
        			invoiceMoney += each.getInvoiceMoney();
        			
        			hasInvoice += each.getInvoiceMoney();
        			
        			lastVsList.add(each);
        			
        			break;
        		}else{
        			invoiceMoney += each.getInvoiceMoney();
        			
        			hasInvoice += each.getInvoiceMoney();
        			
        			lastVsList.add(each);
        		}
        	}
        	
        	long invoiceMoneys = bean.getInvoiceMoney() + MathTools.doubleToLong2(invoiceMoney);
        	
        	preInvoiceVSOutDAO.saveAllEntityBeans(lastVsList);
        	
        	preInvoiceApplyDAO.updateInvoiceMoney(bean.getId(), invoiceMoneys);
        	
        	bean.setInvoiceMoney(invoiceMoneys);
        	
        	for (PreInvoiceVSOutBean each : lastVsList)
        	{
        		String outId = each.getOutId();
        		
        		OutBean outBean = outDAO.find(outId);
        		
        		if (null == outBean)
        		{
        			OutBalanceBean balanceBean = outBalanceDAO.find(outId);
        			
        			if (null == balanceBean)
        			{
        				throw new MYException("数据错误,[%s]不存在", outId);
        			}else{
        				each.setOutId(balanceBean.getOutId());
        				each.setOutBalanceId(outId);
        				
        				//  委托代销
        				handlerEachInAdd2(each, FinanceConstant.INSVSOUT_TYPE_BALANCE);
        			}
        		}else{
        			// 销售单
        			handlerEachInAdd2(each, FinanceConstant.INSVSOUT_TYPE_OUT);
        		}
        	}
        }
    }
    
	/**
	 * 
	 * @param itemBean
	 * @throws MYException
	 */
	private void handlerEachInAdd2(PreInvoiceVSOutBean itemBean, int type)
    throws MYException
	{
	    if (type == FinanceConstant.INSVSOUT_TYPE_OUT)
	    {
	        // 销售单
	        OutBean out = outDAO.find(itemBean.getOutId());
	
	        if (out == null)
	        {
	            throw new MYException("数据错误,请确认操作");
	        }
	
	        // 再次计算可开票金额 与 itemBean.getInvoiceMoney() 比较
	        double retTotal = outDAO.sumOutBackValueIgnoreStatus(out.getFullId());
        	
        	// 已开的发票 = 已开票 + 未审批结束的金额
	        double notEndInvoice = insVSOutDAO.sumOutHasInvoiceStatusNotEnd(out.getFullId());
        	
        	double hadInvoice = out.getInvoiceMoney() + notEndInvoice;
        	
        	double mayInvoiceMoneys = out.getTotal() - retTotal - hadInvoice ;

        	if (itemBean.getInvoiceMoney() > mayInvoiceMoneys){
        		throw new MYException("销售单[%s]销售金额[%.2f],已退货[%.2f],开票金额(含审批中)[%.2f],可开票金额为[%.2f]，本次申请预开票金额为[%.2f], 本次申请大于可开票金额",
        				out.getFullId(), out.getTotal(), retTotal, hadInvoice, mayInvoiceMoneys, itemBean.getInvoiceMoney());
        	}
        	
	        // 更新主单据
	        updateOut(out, itemBean.getInvoiceMoney());
	    }
	    else
	    {
	        // 结算清单
	        OutBalanceBean balance = outBalanceDAO.find(itemBean.getOutBalanceId());
	
	        if (balance == null)
	        {
	            throw new MYException("数据错误,请确认操作");
	        }
	        
	        // 再次计算可开票金额 与 itemBean.getInvoiceMoney() 比较
        	// 结算单退款金额
        	double refMoneys = outBalanceDAO.sumByOutBalanceId(balance.getId());
        	
        	// 已开的发票 = 已开票 + 未审批结束的金额
        	double notEndInvoice = insVSOutDAO.sumOutBlanceHasInvoiceStatusNotEnd(balance.getId());
        	
        	// 在途未审批结束的，未来可能要分开，以示说明
        	double hadInvoice = balance.getInvoiceMoney() + notEndInvoice;
        	
        	double mayInvoiceMoneys = balance.getTotal() - refMoneys - hadInvoice ;
        	
        	if (itemBean.getInvoiceMoney() > mayInvoiceMoneys){
        		throw new MYException("委托结算单[%s]结算金额[%.2f],已退货[%s],开票金额(含审批中)[%.2f],可开票金额为[%.2f]，本次申请预开票金额为[%.2f], 本次申请大于可开票金额",
        				balance.getId(), balance.getTotal(), refMoneys, hadInvoice, mayInvoiceMoneys, itemBean.getInvoiceMoney());
        	}
	
	        updateOutBalance(balance, itemBean.getInvoiceMoney());
	    }
	}
	
    /**
     * 更新销售单的开票状态
     * 
     * @param out
     */
    private void updateOut(OutBean out, double invoiceMoney)
    {
        double total = 0.0d;
        
        total = out.getInvoiceMoney() + invoiceMoney ;

        // 全部开票
        if (MathTools.compare(total, out.getTotal() - out.getPromValue())>=0)
        {
            // 更新开票状态-结束
            outDAO.updateInvoiceStatus(out.getFullId(), total,
                OutConstant.INVOICESTATUS_END);
        }
        else
        {
            // 更新开票状态-过程
            outDAO.updateInvoiceStatus(out.getFullId(), total, OutConstant.INVOICESTATUS_INIT);
        }
    }
    
    /**
     * updateOutBalance
     * 
     * @param balance
     */
    private void updateOutBalance(OutBalanceBean balance, double invoiceMoney)
    {
        double total = 0.0d;

        total = balance.getInvoiceMoney() + invoiceMoney;

        double refTotal = 0.0d;
        
        refTotal = outBalanceDAO.sumByOutBalanceId(balance.getId());
        
        // 全部开票
        if (MathTools.compare(total, (balance.getTotal() - refTotal)) >= 0)
        {
            // 更新开票状态-结束
            outBalanceDAO.updateInvoiceStatus(balance.getId(), total,
                OutConstant.INVOICESTATUS_END);
        }
        else
        {
            // 更新开票状态-过程
            outBalanceDAO.updateInvoiceStatus(balance.getId(), total,
                OutConstant.INVOICESTATUS_INIT);
        }
    }
	
    @Transactional(rollbackFor = MYException.class)
	public boolean rejectPreInvoiceBean(User user, TcpParamWrap param)
			throws MYException
	{
        String id = param.getId();
        String reason = param.getReason();

        JudgeTools.judgeParameterIsNull(user, id);

        PreInvoiceApplyVO bean = findVO(id);

        if (bean == null) {
            throw new MYException("数据错误,请确认操作");
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

        preInvoiceApplyDAO.updateStatus(bean.getId(), bean.getStatus());

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

        mail.setHref(TcpConstanst.TCP_EXPENSE_DETAIL_URL + bean.getId());

        // send mail
        mailMangaer.addMailWithoutTransactional(UserHelper.getSystemUser(), mail);

        return true;
    }

    /**
     * 
     * {@inheritDoc}
     */
	public PreInvoiceApplyVO findVO(String id)
	{
        PreInvoiceApplyVO bean = preInvoiceApplyDAO.findVO(id);

        if (bean == null) {
            return bean;
        }

        // 部门
        PrincipalshipBean depa = orgManager.findPrincipalshipById(bean.getDepartmentId());

        if (depa != null) {
            bean.setDepartmentName(depa.getFullName());
        }

        List<PreInvoiceVSOutBean> vsList = preInvoiceVSOutDAO.queryEntityVOsByFK(id);

        bean.setVsList(vsList);
        
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
	public boolean deletePreInvoiceBean(User user, String id) throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, id);

        PreInvoiceApplyBean bean = preInvoiceApplyDAO.find(id);

        if (bean == null) {
            throw new MYException("数据错误,请确认操作");
        }

        if (!canPreInvoiceApplyDelete(bean)) {
            throw new MYException("不是初始态和驳回态,不能删除");
        }

        // 删除
        preInvoiceVSOutDAO.deleteEntityBeansByFK(bean.getId());
        flowLogDAO.deleteEntityBeansByFK(bean.getId());

        preInvoiceApplyDAO.deleteEntityBean(id);

        tcpApplyDAO.deleteEntityBean(id);

        operationLog.info(user + " delete PreInvoiceApplyBean:" + bean);

        return true;
    }
	
    private boolean canPreInvoiceApplyDelete(PreInvoiceApplyBean bean)
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
	public CommonDAO getCommonDAO()
	{
		return commonDAO;
	}

	/**
	 * @param commonDAO the commonDAO to set
	 */
	public void setCommonDAO(CommonDAO commonDAO)
	{
		this.commonDAO = commonDAO;
	}

	/**
	 * @return the stafferDAO
	 */
	public StafferDAO getStafferDAO()
	{
		return stafferDAO;
	}

	/**
	 * @param stafferDAO the stafferDAO to set
	 */
	public void setStafferDAO(StafferDAO stafferDAO)
	{
		this.stafferDAO = stafferDAO;
	}

	public PreInvoiceApplyDAO getPreInvoiceApplyDAO()
	{
		return preInvoiceApplyDAO;
	}

	public void setPreInvoiceApplyDAO(PreInvoiceApplyDAO preInvoiceApplyDAO)
	{
		this.preInvoiceApplyDAO = preInvoiceApplyDAO;
	}

	public PreInvoiceVSOutDAO getPreInvoiceVSOutDAO()
	{
		return preInvoiceVSOutDAO;
	}

	public void setPreInvoiceVSOutDAO(PreInvoiceVSOutDAO preInvoiceVSOutDAO)
	{
		this.preInvoiceVSOutDAO = preInvoiceVSOutDAO;
	}

	public FlowLogDAO getFlowLogDAO()
	{
		return flowLogDAO;
	}

	public void setFlowLogDAO(FlowLogDAO flowLogDAO)
	{
		this.flowLogDAO = flowLogDAO;
	}

	public TcpHandleHisDAO getTcpHandleHisDAO()
	{
		return tcpHandleHisDAO;
	}

	public void setTcpHandleHisDAO(TcpHandleHisDAO tcpHandleHisDAO)
	{
		this.tcpHandleHisDAO = tcpHandleHisDAO;
	}

	public TcpApplyDAO getTcpApplyDAO()
	{
		return tcpApplyDAO;
	}

	public void setTcpApplyDAO(TcpApplyDAO tcpApplyDAO)
	{
		this.tcpApplyDAO = tcpApplyDAO;
	}

	public TcpFlowDAO getTcpFlowDAO()
	{
		return tcpFlowDAO;
	}

	public void setTcpFlowDAO(TcpFlowDAO tcpFlowDAO)
	{
		this.tcpFlowDAO = tcpFlowDAO;
	}

	public TcpApproveDAO getTcpApproveDAO()
	{
		return tcpApproveDAO;
	}

	public void setTcpApproveDAO(TcpApproveDAO tcpApproveDAO)
	{
		this.tcpApproveDAO = tcpApproveDAO;
	}

	public MailMangaer getMailMangaer()
	{
		return mailMangaer;
	}

	public void setMailMangaer(MailMangaer mailMangaer)
	{
		this.mailMangaer = mailMangaer;
	}

	public GroupVSStafferDAO getGroupVSStafferDAO()
	{
		return groupVSStafferDAO;
	}

	public void setGroupVSStafferDAO(GroupVSStafferDAO groupVSStafferDAO)
	{
		this.groupVSStafferDAO = groupVSStafferDAO;
	}

	public NotifyManager getNotifyManager()
	{
		return notifyManager;
	}

	public void setNotifyManager(NotifyManager notifyManager)
	{
		this.notifyManager = notifyManager;
	}

	public OrgManager getOrgManager()
	{
		return orgManager;
	}

	public void setOrgManager(OrgManager orgManager)
	{
		this.orgManager = orgManager;
	}

	public OutDAO getOutDAO()
	{
		return outDAO;
	}

	public void setOutDAO(OutDAO outDAO)
	{
		this.outDAO = outDAO;
	}

	public OutBalanceDAO getOutBalanceDAO()
	{
		return outBalanceDAO;
	}

	public void setOutBalanceDAO(OutBalanceDAO outBalanceDAO)
	{
		this.outBalanceDAO = outBalanceDAO;
	}

	public InvoiceinsItemDAO getInvoiceinsItemDAO()
	{
		return invoiceinsItemDAO;
	}

	public void setInvoiceinsItemDAO(InvoiceinsItemDAO invoiceinsItemDAO)
	{
		this.invoiceinsItemDAO = invoiceinsItemDAO;
	}

	/**
	 * @return the insVSOutDAO
	 */
	public InsVSOutDAO getInsVSOutDAO()
	{
		return insVSOutDAO;
	}

	/**
	 * @param insVSOutDAO the insVSOutDAO to set
	 */
	public void setInsVSOutDAO(InsVSOutDAO insVSOutDAO)
	{
		this.insVSOutDAO = insVSOutDAO;
	}
}
