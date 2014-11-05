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
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.manager.BillManager;
import com.china.center.oa.group.dao.GroupVSStafferDAO;
import com.china.center.oa.group.vs.GroupVSStafferBean;
import com.china.center.oa.mail.bean.MailBean;
import com.china.center.oa.mail.manager.MailMangaer;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.IDPrefixConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.helper.UserHelper;
import com.china.center.oa.publics.manager.NotifyManager;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.sail.bean.BaseBalanceBean;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.OutBalanceBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.BaseBalanceDAO;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.OutBalanceDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.helper.OutHelper;
import com.china.center.oa.tcp.bean.OutBatchPriceBean;
import com.china.center.oa.tcp.bean.RebateApplyBean;
import com.china.center.oa.tcp.bean.TcpApplyBean;
import com.china.center.oa.tcp.bean.TcpApproveBean;
import com.china.center.oa.tcp.bean.TcpFlowBean;
import com.china.center.oa.tcp.bean.TcpHandleHisBean;
import com.china.center.oa.tcp.bean.TravelApplyPayBean;
import com.china.center.oa.tcp.constanst.TcpConstanst;
import com.china.center.oa.tcp.constanst.TcpFlowConstant;
import com.china.center.oa.tcp.dao.OutBatchPriceDAO;
import com.china.center.oa.tcp.dao.RebateApplyDAO;
import com.china.center.oa.tcp.dao.TcpApplyDAO;
import com.china.center.oa.tcp.dao.TcpApproveDAO;
import com.china.center.oa.tcp.dao.TcpFlowDAO;
import com.china.center.oa.tcp.dao.TcpHandleHisDAO;
import com.china.center.oa.tcp.dao.TravelApplyPayDAO;
import com.china.center.oa.tcp.helper.TCPHelper;
import com.china.center.oa.tcp.listener.TcpPayListener;
import com.china.center.oa.tcp.manager.RebateManager;
import com.china.center.oa.tcp.vo.OutBatchPriceVO;
import com.china.center.oa.tcp.vo.RebateApplyVO;
import com.china.center.oa.tcp.vo.TcpApproveVO;
import com.china.center.oa.tcp.wrap.TcpParamWrap;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

public class RebateManagerImpl extends AbstractListenerManager<TcpPayListener> implements RebateManager
{
    private final Log          operationLog       = LogFactory.getLog("opr");

    private CommonDAO commonDAO = null;
    
    private RebateApplyDAO rebateApplyDAO = null;
    
    private TravelApplyPayDAO travelApplyPayDAO = null;
    
    private FlowLogDAO flowLogDAO = null;
    
    private TcpHandleHisDAO tcpHandleHisDAO = null;
    
    private TcpApplyDAO tcpApplyDAO = null;
    
    private TcpFlowDAO tcpFlowDAO = null;
    
    private TcpApproveDAO tcpApproveDAO = null;
    
    private MailMangaer        mailMangaer        = null;
    
    private GroupVSStafferDAO groupVSStafferDAO = null;
    
    private NotifyManager      notifyManager      = null;
    
    private OrgManager         orgManager         = null;
    
    private OutDAO outDAO = null;
    
    private BaseDAO baseDAO = null;
    
    private OutBalanceDAO outBalanceDAO = null;
    
    private BaseBalanceDAO baseBalanceDAO = null;
    
    private OutBatchPriceDAO outBatchPriceDAO = null;
    
    private StafferDAO stafferDAO = null;
    
    private BillManager billManager = null;
    
    /**
	 * 
	 */
	public RebateManagerImpl()
	{
		
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = MYException.class)
	public boolean addRebateBean(User user, RebateApplyBean bean)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, bean);

        bean.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_REBATE_PRIFIX));

        bean.setStafferId(user.getStafferId());

        bean.setStatus(TcpConstanst.TCP_STATUS_INIT);

        // 获取flowKey
        bean.setFlowKey(TcpFlowConstant.REBATE_APPLY_0_50000);

        List<TravelApplyPayBean> payList = bean.getPayList();

        for (TravelApplyPayBean travelApplyPayBean : payList) {
            travelApplyPayBean.setId(commonDAO.getSquenceString20());

            travelApplyPayBean.setParentId(bean.getId());
        }

        travelApplyPayDAO.saveAllEntityBeans(payList);

        rebateApplyDAO.saveEntityBean(bean);

        saveApply(user, bean);

        saveFlowLog(user, TcpConstanst.TCP_STATUS_INIT, bean, "自动提交保存", PublicConstant.OPRMODE_SAVE);

        return true;
    }

	/**
	 * 根据提供的销售单号检查：
	 * 1.确保销售单没有被其它返利单使用；
	 * 2.获取销售单的明细，单号  产品   售价   数量   金额
	 * 3.判断是否满足返利规则 (自有返利与非自有返利)
	 * @param user
	 * @param bean
	 * @throws MYException
	 */
	private void checkApply(User user, RebateApplyBean bean) throws MYException {
		String outIds = bean.getOutIds();
		
		StafferBean staffer = stafferDAO.find(bean.getStafferId());
		
		if (StringTools.isNullOrNone(outIds))
		{
			throw new MYException("参与销售单为空");
		}

		String [] outIdarr = outIds.split(";");
		
		List<BaseBean> itemList = new ArrayList<BaseBean>();
        
        // CORE 组装assemble未返利 的商品明细
        for (int i = 0 ; i < outIdarr.length; i++)
        {
            int type = 0;

            OutBean outBean = outDAO.find(outIdarr[i]);

            OutBalanceBean outBalanceBean = null;

            if (outBean == null)
            {
                outBalanceBean = outBalanceDAO.find(outIdarr[i]);

                if (outBalanceBean == null)
                {
                	throw new MYException(outIdarr[i] + "的销售单不存在");
                }

                if (outBalanceBean.getHasRebate() == OutConstant.OUT_REBATE_YES)
                {
                	throw new MYException("委托单 [%s] 已经参与过返利，不能重 复返利.", outBalanceBean.getId());
                }
                
                // 原始的单据
                outBean = outDAO.find(outBalanceBean.getOutId());

                if (outBean == null)
                {
                	throw new MYException(outIdarr[i] + "的销售单不存在");
                }

                type = 1;
            }else{
                if (outBean.getHasRebate() == OutConstant.OUT_REBATE_YES)
                {
                	throw new MYException("委托单 [%s] 已经参与过返利，不能重 复返利.", outBean.getFullId());
                }
            }

            // 0:销售单 1:结算单
            if (type == 0)
            {
            	List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());
            	
            	List<BaseBean> mergeBaseList = OutHelper.trimBaseList4(baseList);

            	// 退货产品明细
            	ConditionParse con = new ConditionParse();

                con.addWhereStr();

                con.addCondition("OutBean.refOutFullId", "=", outBean.getFullId());

                con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

                con.addIntCondition("OutBean.outType", "=", OutConstant.OUTTYPE_IN_OUTBACK);

                List<OutBean> refOutList = outDAO.queryEntityBeansByCondition(con);
                
                for (OutBean each : refOutList)
                {
                	List<BaseBean> list = baseDAO.queryEntityBeansByFK(each.getFullId());
                	
                	each.setBaseList(list);
                }

                // 计算出已经退货的数量                
                for (BaseBean baseBean : mergeBaseList)
                {
                    int hasBack = 0;

                    // 退库
                    for (OutBean ref : refOutList)
                    {
                        List<BaseBean> refOutBaseList = OutHelper.trimBaseList2(ref.getBaseList());

                        for (BaseBean refBase : refOutBaseList)
                        {
                            if (refBase.equals2(baseBean))
                            {
                                hasBack += refBase.getAmount();

                                break;
                            }
                        }
                    }

                    baseBean.setDescription(outBean.getChangeTime());  // 发货时间
                    baseBean.setInway(hasBack);
                }
                                
            	itemList.addAll(mergeBaseList);
            }
            else
            {
            	// 结算单
            	List<BaseBalanceBean> baseBalanceList = baseBalanceDAO.queryEntityBeansByFK(outBalanceBean.getId());
            	
            	List<BaseBean> baseList = new ArrayList<BaseBean>();
            	
            	for (BaseBalanceBean each : baseBalanceList)
            	{
            		String baseId = each.getBaseId();
            		
            		BaseBean baseBean = baseDAO.find(baseId);
            		
            		if (null != baseBean)
            		{
            			baseBean.setId(each.getId());
            			baseBean.setOutId(each.getParentId()); // 委托代销号
            			baseBean.setAmount(each.getAmount());
            			baseBean.setPrice(each.getSailPrice());
            			baseBean.setLocationId(each.getBaseId());
            			baseBean.setDescription(outBean.getChangeTime()); // 用于存发货审批时间
            			
            			baseList.add(baseBean);
            		}
            	}
            	
            	// 结算单退货明细
            	List<BaseBalanceBean> refList = new ArrayList<BaseBalanceBean>();
            	
            	List<OutBalanceBean> balanceList = outBalanceDAO.queryEntityBeansByFK(outBalanceBean.getId(), AnoConstant.FK_FIRST);
            	
            	for (OutBalanceBean each : balanceList)
            	{
            		List<BaseBalanceBean> bbList = baseBalanceDAO.queryEntityBeansByFK(each.getId());
            		
            		refList.addAll(bbList);
            	}
            	
        		for (BaseBean baseBean : baseList)
                {
                    int hasBack = 0;

                    // 申请除外
                    for (BaseBalanceBean each : refList)
                    {
                        if (each.getBaseId().equals(baseBean.getLocationId()))
                        {
                            hasBack += each.getAmount();
                        }
                    }
                    
                    baseBean.setInway(baseBean.getInway() + hasBack);
                }
            	
            	itemList.addAll(baseList);
            }
        }

        double rebateMoney = 0.0d;
        
        double total = 0.0d;
        
        double applyRebate = bean.getTotal()/100.0d ;
        
        // itemList 根据统计的数据计算可返利的金额
        // 自有返利
        for (BaseBean each : itemList){
            if (bean.getAtype() == TcpConstanst.REBATETYPE_SELF){
            	// 根据产品及出库时间获取 出库日批价
            	OutBatchPriceBean obp = outBatchPriceDAO
            								.queryByProductIdAndChangeTime(each.getProductId(), staffer.getIndustryId(),
            										TimeTools.changeFormat(each.getDescription(), TimeTools.LONG_FORMAT, TimeTools.SHORT_FORMAT));
            	
            	if (obp == null){
            		throw new MYException("没有发货日 " + TimeTools.changeFormat(each.getDescription(), TimeTools.LONG_FORMAT, TimeTools.SHORT_FORMAT) + " 的出库批价，请维护先。");
            	}
            	
            	rebateMoney += (each.getPrice() - obp.getPrice()) * (each.getAmount() - each.getInway());
            	
            }else{
            	total += (each.getAmount() - each.getInway()) * each.getPrice(); 
            }
        }

        // judge
        if (bean.getAtype() == TcpConstanst.REBATETYPE_SELF){
        	
        	if (applyRebate > rebateMoney)
        	{
        		throw new MYException("自有返利时，申请返利金额[%s]，超过实际可返利金额[%s] ((开单价-出库日批价)*数量)", MathTools.formatNum2(applyRebate), MathTools.formatNum2(rebateMoney));
        	}
        	
        	operationLog.info("可返利：" + rebateMoney);
        	
        }else{
        	// 小于20%
        	if (applyRebate > (total - applyRebate) * 0.2){
        		throw new MYException("非自有返利时，申请返利金额[%s]，超过实际可返利金额[%s]", MathTools.formatNum2(applyRebate), MathTools.formatNum2((total - applyRebate) * 0.2));
        	}
        }
	}
	
    public void saveApply(User user, RebateApplyBean bean) {
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
	private void saveFlowLog(User user, int preStatus, RebateApplyBean apply, String reason,
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
	public boolean updateRebateBean(User user, RebateApplyBean bean)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, bean);

        RebateApplyBean old = rebateApplyDAO.find(bean.getId());

        if (old == null) {
            throw new MYException("数据错误,请确认操作");
        }

        if (!bean.getStafferId().equals(user.getStafferId())) {
            throw new MYException("只能修改自己的申请");
        }

        bean.setStatus(TcpConstanst.TCP_STATUS_INIT);

        // 获取flowKey
        bean.setFlowKey(TcpFlowConstant.REBATE_APPLY_0_50000);

        // 先清理
        travelApplyPayDAO.deleteEntityBeansByFK(bean.getId());

        List<TravelApplyPayBean> payList = bean.getPayList();

        for (TravelApplyPayBean travelApplyPayBean : payList) {
            travelApplyPayBean.setId(commonDAO.getSquenceString20());

            travelApplyPayBean.setParentId(bean.getId());
        }

        travelApplyPayDAO.saveAllEntityBeans(payList);

        rebateApplyDAO.updateEntityBean(bean);

        saveFlowLog(user, old.getStatus(), bean, "自动修改保存", PublicConstant.OPRMODE_SAVE);

        return true;
    }

	@Transactional(rollbackFor = MYException.class)
	public boolean submitRebateBean(User user, String id, String processId)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, id);

        RebateApplyVO bean = findVO(id);

        if (bean == null) {
            throw new MYException("数据错误,请确认操作");
        }

        if (!bean.getStafferId().equals(user.getStafferId())) {
            throw new MYException("只能操作自己的申请");
        }

        // 计算返利金额是否在规则范围内
        checkApply(user, bean);

        // 获得当前的处理环节
        TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(), bean.getStatus());

        // 进入审批状态
        int newStatus = saveApprove(user, processId, bean, token.getNextStatus(), 0);

        int oldStatus = bean.getStatus();

        bean.setStatus(newStatus);

        rebateApplyDAO.updateStatus(bean.getId(), newStatus);

        // 更新销售单标志
        updateOutHasRebate(bean, true);
        
        // 记录操作日志
        saveFlowLog(user, oldStatus, bean, "提交申请", PublicConstant.OPRMODE_SUBMIT);

        return true;
    }

	private void updateOutHasRebate(RebateApplyBean bean, boolean hasRebate)
	{
		String [] outIdarr = bean.getOutIds().split(";");
		
        for (int i = 0 ; i < outIdarr.length; i++)
        {
        	if (hasRebate){
        		outDAO.updateRebate(outIdarr[i], OutConstant.OUT_REBATE_YES);
        		outBalanceDAO.updateRebate(outIdarr[i], OutConstant.OUT_REBATE_YES);
        	}else{
        		outDAO.updateRebate(outIdarr[i], OutConstant.OUT_REBATE_NO);
        		outBalanceDAO.updateRebate(outIdarr[i], OutConstant.OUT_REBATE_NO);
        	}
        }
	}
	
    /**
     * 进入审批状态
     * 
     * @param processId
     * @param bean
     * @param pool
     * @throws MYException
     */
    private int saveApprove(User user, String processId, RebateApplyVO bean, int nextStatus,
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
    private int saveApprove(User user, List<String> processList, RebateApplyVO bean,
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

                mail.setHref(TcpConstanst.TCP_EXPENSE_PROCESS_URL + bean.getId());

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
	public boolean passRebateBean(User user, TcpParamWrap param)
			throws MYException
	{
        String id = param.getId();
        String processId = param.getProcessId();
        String reason = param.getReason();

        JudgeTools.judgeParameterIsNull(user, id);

        RebateApplyVO bean = findVO(id);

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

                rebateApplyDAO.updateStatus(bean.getId(), newStatus);
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

                rebateApplyDAO.updateStatus(bean.getId(), newStatus);
            }

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
        }
        // 插件模式
        else if (token.getNextPlugin().startsWith("plugin")) {
            // plugin:travelSingeAll(意思是权签人会签)
            if (token.getNextPlugin().equalsIgnoreCase("plugin:travelSingeAll")) {}
        }
        // 结束模式
        else if (token.getNextPlugin().startsWith("end")) {
            // 结束了需要清空
            tcpApproveDAO.deleteEntityBeansByFK(bean.getId());

            bean.setStatus(token.getNextStatus());

            rebateApplyDAO.updateStatus(bean.getId(), bean.getStatus());

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
        }

        return true;
    }

    /**
     * CORE 返利的分支处理
     * 
     * @param user
     * @param param
     * @param bean
     * @param oldStatus
     * @throws MYException
     */
    @SuppressWarnings("unchecked")
	private void logicProcess(User user, TcpParamWrap param, RebateApplyVO bean, int oldStatus)
            throws MYException {

        // 财务收款和支付
        if (oldStatus == TcpConstanst.TCP_STATUS_WAIT_PAY) {
            StringBuffer idBuffer = new StringBuffer();

            // 财务付款
            List<OutBillBean> outBillList = (List<OutBillBean>) param.getOther();

            if (outBillList != null) {
                double total = 0.0d;

                for (OutBillBean outBill : outBillList) {
                    // 生成付款单
                    createOutBill(user, outBill, bean);

                    total += outBill.getMoneys();

                    idBuffer.append(outBill.getId()).append(';');
                }

                if (MathTools.doubleToLong2(total) != bean.getTotal()) {
                    throw new MYException("付款金额[%.2f]不等于申请返利金额[%.2f]", total,
                            MathTools.longToDouble2(bean.getTotal()));
                }
            }

            // 财务收款和支付
            Collection<TcpPayListener> listenerMapValues = this.listenerMapValues();

            for (TcpPayListener tcpPayListener : listenerMapValues) {
                 // 财务入账生成的凭证
                tcpPayListener.onPayRebateApply(user, bean, outBillList);
            }
            
            // 更新纳税实体
            rebateApplyDAO.updateEntityBean(bean);
        }

        // 这里处理报销最终的凭证
        if (oldStatus == TcpConstanst.TCP_STATUS_LAST_CHECK) {
            // 校验入账金额 这里最终是财务入账

            Collection<TcpPayListener> listenerMapValues = this.listenerMapValues();

            for (TcpPayListener tcpPayListener : listenerMapValues) {
                 // 财务入账生成的凭证
                tcpPayListener.onEndRebateApply(user, bean);
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
    private void createOutBill(User user, OutBillBean outBill, RebateApplyVO apply)
            throws MYException {
        // 自动生成付款单
        outBill.setDescription(DefinedCommon.getValue("tcpType", apply.getType()) + "申请借款的付款:"
                + apply.getId());

        outBill.setLocationId(user.getLocationId());

        outBill.setLogTime(TimeTools.now());

        outBill.setType(FinanceConstant.OUTBILL_TYPE_SALECHARGE);

        outBill.setOwnerId(apply.getStafferId());

        outBill.setStafferId(user.getStafferId());

        outBill.setProvideId("");

        // 借款的单据
        outBill.setStockId(apply.getId());

        outBill.setStockItemId("");

        billManager.addOutBillBeanWithoutTransaction(user, outBill);
    }
    
    @Transactional(rollbackFor = MYException.class)
	public boolean rejectRebateBean(User user, TcpParamWrap param)
			throws MYException
	{
        String id = param.getId();
        String reason = param.getReason();

        JudgeTools.judgeParameterIsNull(user, id);

        RebateApplyVO bean = findVO(id);

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

        rebateApplyDAO.updateStatus(bean.getId(), bean.getStatus());

        // 更新销售单标志
        updateOutHasRebate(bean, false);
        
        // 记录操作日志
        saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_REJECT);

        // 通知提交人
        notifyManager.notifyMessage(bean.getStafferId(),
                "返利:" + bean.getName() + "被" + user.getStafferName() + "驳回,原因:" + reason);

        MailBean mail = new MailBean();

        mail.setTitle("返利:" + bean.getName() + "被" + user.getStafferName() + "驳回,原因:"
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
	public RebateApplyVO findVO(String id)
	{
        RebateApplyVO bean = rebateApplyDAO.findVO(id);

        if (bean == null) {
            return bean;
        }

        // 部门
        PrincipalshipBean depa = orgManager.findPrincipalshipById(bean.getDepartmentId());

        if (depa != null) {
            bean.setDepartmentName(depa.getFullName());
        }

        List<TravelApplyPayBean> payList = travelApplyPayDAO.queryEntityVOsByFK(id);

        bean.setPayList(payList);

        TCPHelper.chageVO(bean);

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
	public boolean deleteRebateBean(User user, String id) throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, id);

        RebateApplyBean bean = rebateApplyDAO.find(id);

        if (bean == null) {
            throw new MYException("数据错误,请确认操作");
        }

        if (!canRebateApplyDelete(bean)) {
            throw new MYException("不是初始态和驳回态,不能删除");
        }

        // 删除
        travelApplyPayDAO.deleteEntityBeansByFK(bean.getId());
        flowLogDAO.deleteEntityBeansByFK(bean.getId());

        rebateApplyDAO.deleteEntityBean(id);

        tcpApplyDAO.deleteEntityBean(id);

        operationLog.info(user + " delete RebateApplyBean:" + bean);

        return true;
    }
	
    private boolean canRebateApplyDelete(RebateApplyBean bean)
    {
        if (bean.getStatus() == TcpConstanst.TCP_STATUS_INIT
            || bean.getStatus() == TcpConstanst.TCP_STATUS_REJECT)
        {
            return true;
        }

        return false;
    }
	
    @Transactional(rollbackFor = MYException.class)
	public boolean importBatchPrice(User user, List<OutBatchPriceBean> bpList) throws MYException
	{
    	JudgeTools.judgeParameterIsNull(user, bpList);
    	
    	// 起始,终止,产品唯一,如有重复替换
    	for (OutBatchPriceBean each : bpList)
    	{
    		OutBatchPriceVO existsBean = outBatchPriceDAO.findVOByUnique(each.getProductId(), each.getIndustryId(), "2099-12-31");

    		if (null != existsBean)
    		{
    			String beginDate = each.getBeginDate();

    			if (beginDate.equals(existsBean.getBeginDate())){
    				outBatchPriceDAO.deleteEntityBean(existsBean.getId());
    			}else{
        			String lastEnd = TimeTools.getStringByOrgAndDaysAndFormat(beginDate + " 00:00:01", -1, TimeTools.SHORT_FORMAT);
        			
        			// lastEnd 不能比existsBean的beginDate 小
        			if (TimeTools.cdate(existsBean.getBeginDate(),lastEnd) > 0){
        				throw new MYException("事业部[%s],产品[%s] 导入的起始日期异常，出现起始日期大于结束日期情况，可能原因是日期没有正常顺延" ,existsBean.getIndustryName(),existsBean.getProductName());
        			}
        			existsBean.setEndDate(lastEnd);
        			
        			outBatchPriceDAO.updateEntityBean(existsBean);
    			}
    		}
    		
    		outBatchPriceDAO.saveEntityBean(each);
    	}
    	
    	return true;
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
	 * @return the rebateApplyDAO
	 */
	public RebateApplyDAO getRebateApplyDAO()
	{
		return rebateApplyDAO;
	}

	/**
	 * @param rebateApplyDAO the rebateApplyDAO to set
	 */
	public void setRebateApplyDAO(RebateApplyDAO rebateApplyDAO)
	{
		this.rebateApplyDAO = rebateApplyDAO;
	}

	/**
	 * @return the travelApplyPayDAO
	 */
	public TravelApplyPayDAO getTravelApplyPayDAO()
	{
		return travelApplyPayDAO;
	}

	/**
	 * @param travelApplyPayDAO the travelApplyPayDAO to set
	 */
	public void setTravelApplyPayDAO(TravelApplyPayDAO travelApplyPayDAO)
	{
		this.travelApplyPayDAO = travelApplyPayDAO;
	}

	/**
	 * @return the flowLogDAO
	 */
	public FlowLogDAO getFlowLogDAO()
	{
		return flowLogDAO;
	}

	/**
	 * @param flowLogDAO the flowLogDAO to set
	 */
	public void setFlowLogDAO(FlowLogDAO flowLogDAO)
	{
		this.flowLogDAO = flowLogDAO;
	}

	/**
	 * @return the tcpHandleHisDAO
	 */
	public TcpHandleHisDAO getTcpHandleHisDAO()
	{
		return tcpHandleHisDAO;
	}

	/**
	 * @param tcpHandleHisDAO the tcpHandleHisDAO to set
	 */
	public void setTcpHandleHisDAO(TcpHandleHisDAO tcpHandleHisDAO)
	{
		this.tcpHandleHisDAO = tcpHandleHisDAO;
	}

	/**
	 * @return the tcpApplyDAO
	 */
	public TcpApplyDAO getTcpApplyDAO()
	{
		return tcpApplyDAO;
	}

	/**
	 * @param tcpApplyDAO the tcpApplyDAO to set
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
	 * @param tcpFlowDAO the tcpFlowDAO to set
	 */
	public void setTcpFlowDAO(TcpFlowDAO tcpFlowDAO)
	{
		this.tcpFlowDAO = tcpFlowDAO;
	}

	/**
	 * @return the tcpApproveDAO
	 */
	public TcpApproveDAO getTcpApproveDAO()
	{
		return tcpApproveDAO;
	}

	/**
	 * @param tcpApproveDAO the tcpApproveDAO to set
	 */
	public void setTcpApproveDAO(TcpApproveDAO tcpApproveDAO)
	{
		this.tcpApproveDAO = tcpApproveDAO;
	}

	/**
	 * @return the mailMangaer
	 */
	public MailMangaer getMailMangaer()
	{
		return mailMangaer;
	}

	/**
	 * @param mailMangaer the mailMangaer to set
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
	 * @param groupVSStafferDAO the groupVSStafferDAO to set
	 */
	public void setGroupVSStafferDAO(GroupVSStafferDAO groupVSStafferDAO)
	{
		this.groupVSStafferDAO = groupVSStafferDAO;
	}

	/**
	 * @return the notifyManager
	 */
	public NotifyManager getNotifyManager()
	{
		return notifyManager;
	}

	/**
	 * @param notifyManager the notifyManager to set
	 */
	public void setNotifyManager(NotifyManager notifyManager)
	{
		this.notifyManager = notifyManager;
	}

	/**
	 * @return the orgManager
	 */
	public OrgManager getOrgManager()
	{
		return orgManager;
	}

	/**
	 * @param orgManager the orgManager to set
	 */
	public void setOrgManager(OrgManager orgManager)
	{
		this.orgManager = orgManager;
	}

	/**
	 * @return the outDAO
	 */
	public OutDAO getOutDAO()
	{
		return outDAO;
	}

	/**
	 * @param outDAO the outDAO to set
	 */
	public void setOutDAO(OutDAO outDAO)
	{
		this.outDAO = outDAO;
	}

	/**
	 * @return the baseDAO
	 */
	public BaseDAO getBaseDAO()
	{
		return baseDAO;
	}

	/**
	 * @param baseDAO the baseDAO to set
	 */
	public void setBaseDAO(BaseDAO baseDAO)
	{
		this.baseDAO = baseDAO;
	}

	/**
	 * @return the outBalanceDAO
	 */
	public OutBalanceDAO getOutBalanceDAO()
	{
		return outBalanceDAO;
	}

	/**
	 * @param outBalanceDAO the outBalanceDAO to set
	 */
	public void setOutBalanceDAO(OutBalanceDAO outBalanceDAO)
	{
		this.outBalanceDAO = outBalanceDAO;
	}

	/**
	 * @return the baseBalanceDAO
	 */
	public BaseBalanceDAO getBaseBalanceDAO()
	{
		return baseBalanceDAO;
	}

	/**
	 * @param baseBalanceDAO the baseBalanceDAO to set
	 */
	public void setBaseBalanceDAO(BaseBalanceDAO baseBalanceDAO)
	{
		this.baseBalanceDAO = baseBalanceDAO;
	}

	/**
	 * @return the outBatchPriceDAO
	 */
	public OutBatchPriceDAO getOutBatchPriceDAO()
	{
		return outBatchPriceDAO;
	}

	/**
	 * @param outBatchPriceDAO the outBatchPriceDAO to set
	 */
	public void setOutBatchPriceDAO(OutBatchPriceDAO outBatchPriceDAO)
	{
		this.outBatchPriceDAO = outBatchPriceDAO;
	}

	/**
	 * @return the billManager
	 */
	public BillManager getBillManager()
	{
		return billManager;
	}

	/**
	 * @param billManager the billManager to set
	 */
	public void setBillManager(BillManager billManager)
	{
		this.billManager = billManager;
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
}
