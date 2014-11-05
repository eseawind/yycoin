/**
 * File Name: TravelApplyManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.manager.impl;

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
import com.china.center.oa.budget.bean.BudgetBean;
import com.china.center.oa.budget.bean.BudgetItemBean;
import com.china.center.oa.budget.bean.BudgetLogBean;
import com.china.center.oa.budget.bean.BudgetLogTmpBean;
import com.china.center.oa.budget.constant.BudgetConstant;
import com.china.center.oa.budget.dao.BudgetDAO;
import com.china.center.oa.budget.dao.BudgetItemDAO;
import com.china.center.oa.budget.dao.BudgetLogDAO;
import com.china.center.oa.budget.dao.BudgetLogTmpDAO;
import com.china.center.oa.budget.manager.BudgetManager;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.constant.FinanceConstant;
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
import com.china.center.oa.tcp.bean.ExpenseApplyBean;
import com.china.center.oa.tcp.bean.TcpApplyBean;
import com.china.center.oa.tcp.bean.TcpApproveBean;
import com.china.center.oa.tcp.bean.TcpFlowBean;
import com.china.center.oa.tcp.bean.TcpHandleHisBean;
import com.china.center.oa.tcp.bean.TcpShareBean;
import com.china.center.oa.tcp.bean.TravelApplyBean;
import com.china.center.oa.tcp.bean.TravelApplyItemBean;
import com.china.center.oa.tcp.bean.TravelApplyPayBean;
import com.china.center.oa.tcp.constanst.TcpConstanst;
import com.china.center.oa.tcp.dao.ExpenseApplyDAO;
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
import com.china.center.oa.tcp.manager.ExpenseManager;
import com.china.center.oa.tcp.vo.ExpenseApplyVO;
import com.china.center.oa.tcp.vo.TcpApproveVO;
import com.china.center.oa.tcp.vo.TcpShareVO;
import com.china.center.oa.tcp.vo.TravelApplyItemVO;
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
 * @see ExpenseManagerImpl
 * @since 3.0
 */
@IntegrationAOP
public class ExpenseManagerImpl extends AbstractListenerManager<TcpPayListener> implements
        ExpenseManager {
    private final Log          operationLog       = LogFactory.getLog("opr");

    private final Log          badLog             = LogFactory.getLog("bad");

    private TcpApplyDAO        tcpApplyDAO        = null;

    private TcpFlowDAO         tcpFlowDAO         = null;

    private GroupVSStafferDAO  groupVSStafferDAO  = null;

    private TcpPrepaymentDAO   tcpPrepaymentDAO   = null;

    private TcpShareDAO        tcpShareDAO        = null;

    private TravelApplyDAO     travelApplyDAO     = null;

    private TravelApplyItemDAO travelApplyItemDAO = null;

    private TravelApplyPayDAO  travelApplyPayDAO  = null;

    private CommonDAO          commonDAO          = null;

    private ExpenseApplyDAO    expenseApplyDAO    = null;

    private TcpHandleHisDAO    tcpHandleHisDAO    = null;

    private MailMangaer        mailMangaer        = null;

    private NotifyManager      notifyManager      = null;

    private BudgetManager      budgetManager      = null;

    private OrgManager         orgManager         = null;

    private BillManager        billManager        = null;

    private BudgetItemDAO      budgetItemDAO      = null;

    private BudgetDAO          budgetDAO          = null;

    private AttachmentDAO      attachmentDAO      = null;

    private FlowLogDAO         flowLogDAO         = null;

    private TcpApproveDAO      tcpApproveDAO      = null;

    private BudgetLogDAO       budgetLogDAO       = null;

    private BudgetLogTmpDAO    budgetLogTmpDAO    = null;
    
    private StafferDAO         stafferDAO         = null;
    
    private final Log _logger = LogFactory.getLog(getClass());
    
    /**
     * default constructor
     */
    public ExpenseManagerImpl() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.china.center.oa.tcp.manager.TravelApplyManager#addExpenseApplyBean
     * (com.center.china.osgi.publics.User,
     * com.china.center.oa.tcp.bean.ExpenseApplyBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addExpenseBean(User user, ExpenseApplyBean bean) throws MYException {
        JudgeTools.judgeParameterIsNull(user, bean);

        bean.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_TCP_PREFIX));

        bean.setStafferId(user.getStafferId());

        if (StringTools.isNullOrNone(bean.getBorrowStafferId())) {
            bean.setBorrowStafferId(user.getStafferId());
        }

        bean.setStatus(TcpConstanst.TCP_STATUS_INIT);

        // 获取flowKey
        TCPHelper.setFlowKey(bean);

        // TravelApplyItemBean
        List<TravelApplyItemBean> itemList = bean.getItemList();

        for (TravelApplyItemBean travelApplyItemBean : itemList) {
            travelApplyItemBean.setId(commonDAO.getSquenceString20());
            travelApplyItemBean.setParentId(bean.getId());
        }

        travelApplyItemDAO.saveAllEntityBeans(itemList);

        if (bean.getPayType() == TcpConstanst.PAYTYPE_PAY_YES) {
            long temp = 0L;

            List<TravelApplyPayBean> payList = bean.getPayList();

            for (TravelApplyPayBean travelApplyPayBean : payList) {
                travelApplyPayBean.setId(commonDAO.getSquenceString20());

                travelApplyPayBean.setParentId(bean.getId());

                temp += travelApplyPayBean.getMoneys();
            }

            // 这里的公司支付金额
            bean.setBorrowTotal(temp);

            // 员工还款金额
            bean.setLastMoney(0);

            travelApplyPayDAO.saveAllEntityBeans(payList);
        } else if (bean.getPayType() == TcpConstanst.PAYTYPE_PAY_OK) {
            bean.setLastMoney(0);

            bean.setBorrowTotal(0);
        }

        List<TcpShareBean> shareList = null;

        // 默认从父级申请拷贝过来
        if (ListTools.isEmptyOrNull(bean.getShareList())) {
            // 从父申请里面拷贝分担比例(包括金额分担)
            shareList = tcpShareDAO.queryEntityBeansByFK(bean.getRefId());

            bean.setShareList(shareList);
        } else {
            // 通用的自己申请
            shareList = bean.getShareList();
        }

        // 校验
        checkApply(user, bean);

        if (shareList == null) {
            throw new MYException("数据错误,请确认操作");
        }

        for (TcpShareBean tcpShareBean : shareList) {
            tcpShareBean.setId(commonDAO.getSquenceString20());
            tcpShareBean.setRefId(bean.getId());
        }

        tcpShareDAO.saveAllEntityBeans(shareList);

        List<AttachmentBean> attachmentList = bean.getAttachmentList();

        for (AttachmentBean attachmentBean : attachmentList) {
            attachmentBean.setId(commonDAO.getSquenceString20());
            attachmentBean.setRefId(bean.getId());
        }

        attachmentDAO.saveAllEntityBeans(attachmentList);

        expenseApplyDAO.saveEntityBean(bean);

        saveApply(user, bean);

        saveFlowLog(user, TcpConstanst.TCP_STATUS_INIT, bean, "自动提交保存", PublicConstant.OPRMODE_SAVE);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean submitExpenseBean(User user, String id, String processId) throws MYException {
        JudgeTools.judgeParameterIsNull(user, id);

        ExpenseApplyVO bean = findVO(id);

        if (bean == null) {
            throw new MYException("数据错误,请确认操作");
        }

        if (!bean.getStafferId().equals(user.getStafferId())) {
            throw new MYException("只能操作自己的申请");
        }

        // 预算占用
        checkBudget(user, bean, 0);

        // 获得当前的处理环节
        TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(), bean.getStatus());

        // 进入审批状态
        int newStatus = saveApprove(user, processId, bean, token.getNextStatus(), 0);

        int oldStatus = bean.getStatus();

        bean.setStatus(newStatus);

        expenseApplyDAO.updateStatus(bean.getId(), newStatus);

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
        List<GroupVSStafferBean> vs = groupVSStafferDAO.queryEntityBeansByFK("A1201107140002732276");

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
        List<GroupVSStafferBean> vs = groupVSStafferDAO.queryEntityBeansByFK("A1201107140002732627");
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
    public boolean passExpenseBean(User user, TcpParamWrap param) throws MYException {
        String id = param.getId();
        String processId = param.getProcessId();
        String reason = param.getReason();

        JudgeTools.judgeParameterIsNull(user, id);

        ExpenseApplyVO bean = findVO(id);

        if (bean == null) {
            throw new MYException("数据错误,请确认操作");
        }

        // 权限
        checkAuth(user, id);

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

                expenseApplyDAO.updateStatus(bean.getId(), newStatus);
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

            List<String> processList = new ArrayList();

            for (GroupVSStafferBean groupVSStafferBean : vsList) {
                processList.add(groupVSStafferBean.getStafferId());
            }

            int newStatus = saveApprove(user, processList, bean, token.getNextStatus(), 1);

            if (newStatus != oldStatus) {
                bean.setStatus(newStatus);

                expenseApplyDAO.updateStatus(bean.getId(), newStatus);
            }

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
        }
        // 插件模式
        else if (token.getNextPlugin().startsWith("plugin")) {
            // plugin:travelSingeAll(意思是权签人会签)
            if (token.getNextPlugin().equalsIgnoreCase("plugin:travelSingeAll")) {
                List<String> processList = new ArrayList();

                // 先处理一个
                List<TcpShareVO> shareVOList = bean.getShareVOList();

                if (ListTools.isEmptyOrNull(shareVOList)) {
                    throw new MYException("下环节里面没有人员,请确认操作");
                }

                for (TcpShareVO tcpShareVO : shareVOList) {
                    // 去重
                    if (!processList.contains(tcpShareVO.getApproverId())) {
                        processList.add(tcpShareVO.getApproverId());
                    }
                }

                int newStatus = saveApprove(user, processList, bean, token.getNextStatus(), 0);

                bean.setStatus(newStatus);

                expenseApplyDAO.updateStatus(bean.getId(), newStatus);

                // 记录操作日志
                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
            }
        }
        // 结束模式
        else if (token.getNextPlugin().startsWith("end")) {
            // 结束了需要清空
            tcpApproveDAO.deleteEntityBeansByFK(bean.getId());

            bean.setStatus(token.getNextStatus());

            expenseApplyDAO.updateStatus(bean.getId(), bean.getStatus());

            // 中收报销不生成凭证
            if (bean.getType() == TcpConstanst.TCP_EXPENSETYPE_MID) {
            	checkBudget(user, bean, 1);
            } else {
            	Collection<TcpPayListener> listenerMapValues = this.listenerMapValues();

                for (TcpPayListener tcpPayListener : listenerMapValues) {
                    // TODO_OSGI 这里是报销最终结束生成的凭证
                    tcpPayListener.onLastEndExpenseApply(user, bean, reason);
                }
            }

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
        }

        return true;
    }

    /**
     * CORE 报销的分支处理
     * 
     * @param user
     * @param param
     * @param bean
     * @param oldStatus
     * @throws MYException
     */
    private void logicProcess(User user, TcpParamWrap param, ExpenseApplyVO bean, int oldStatus)
            throws MYException {
        // 这里需要特殊处理的(稽核修改金额)
        if (oldStatus == TcpConstanst.TCP_STATUS_WAIT_CHECK) {
            // 稽核需要重新整理pay和重新预算
            if (bean.getPayType() == TcpConstanst.PAYTYPE_PAY_YES) {
                List<TravelApplyPayBean> newPayList = (List<TravelApplyPayBean>) param.getOther();

                long newBrrow = 0L;

                if (newPayList != null) {
                    for (TravelApplyPayBean travelApplyPayBean : newPayList) {
                        newBrrow += travelApplyPayBean.getCmoneys();
                    }
                } else {
                    throw new MYException("公司付款给员工下缺少付款申请");
                }

                // 重新计算付款金额(这里可能存在金额不一致的情况)
                bean.setBorrowTotal(newBrrow);

                bean.setPayList(newPayList);

                // 成功后更新支付列表
                travelApplyPayDAO.updateAllEntityBeans(newPayList);

                // 允许检查实际报销
                expenseApplyDAO.updateBorrowTotal(param.getId(), bean.getBorrowTotal());
                
                String scompliance = param.getCompliance();
                // 更新合规标识
                expenseApplyDAO.updateCompliance(param.getId(), scompliance);
            }

            // 重新校验预算(公司付款重新计算,收支平衡重新生成日志,员工付款也需要重新计算预算使用)
            checkBudget(user, bean, 1);
        }
        
        // 财务收款和支付
        if (oldStatus == TcpConstanst.TCP_STATUS_WAIT_PAY) {
            StringBuffer idBuffer = new StringBuffer();

            if (bean.getPayType() != TcpConstanst.PAYTYPE_PAY_OK) {
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

                    if (MathTools.doubleToLong2(total) != bean.getBorrowTotal()) {
                        throw new MYException("付款金额[%.2f]不等于公司支付金额[%.2f]", total,
                                MathTools.longToDouble2(bean.getBorrowTotal()));
                    }
                }

                // 收款单
                List<InBillBean> inBillList = (List<InBillBean>) param.getOther2();

                if (inBillList != null) {
                    double total = 0.0d;

                    for (InBillBean inBill : inBillList) {
                        // 生成收款单
                        creatInBill(user, inBill, bean);

                        total += inBill.getMoneys();

                        idBuffer.append(inBill.getId()).append(';');
                    }

                    if (MathTools.doubleToLong2(total) != bean.getLastMoney()) {
                        throw new MYException("员工应偿还借款金额[%.2f]不等于公司财务实际的收款金额[%.2f]",
                                MathTools.longToDouble2(bean.getLastMoney()), total);
                    }
                }

                Collection<TcpPayListener> listenerMapValues = this.listenerMapValues();

                for (TcpPayListener tcpPayListener : listenerMapValues) {
                    // TODO_OSGI 这里是出差申请的借款生成凭证
                    tcpPayListener.onPayExpenseApply(user, bean, outBillList, inBillList);
                }
            }

            // 更新报销使用状态
            budgetManager.updateBudgetLogUserTypeByRefIdWithoutTransactional(user, bean.getId(),
                    BudgetConstant.BUDGETLOG_USERTYPE_REAL, idBuffer.toString());

            if (!StringTools.isNullOrNone(bean.getRefId())) {
                // 这里要删除申请的冻结的(这样借款单据就没有预算了)
                budgetManager.deleteBudgetLogListWithoutTransactional(user, bean.getRefId());
            }
        }

        // 这里处理报销最终的凭证
        if (oldStatus == TcpConstanst.TCP_STATUS_LAST_CHECK) {
            // 校验入账金额 这里最终是财务入账
            long total = fillTaxTotal(param);

            // 收支平衡
            if (bean.getPayType() == TcpConstanst.PAYTYPE_PAY_OK) {
                // 不是通用报销
                if (bean.getType() != TcpConstanst.TCP_EXPENSETYPE_COMMON) {
                    if (bean.getTotal() != bean.getRefMoney() || bean.getTotal() != total) {
                        throw new MYException("收支平衡下财务入账金额必须是[%.2f]，当前入账金额是[%.2f]",
                                MathTools.longToDouble2(bean.getRefMoney()),
                                MathTools.longToDouble2(total));
                    }
                }
            }

            // 公司付款给员工
            if (bean.getPayType() == TcpConstanst.PAYTYPE_PAY_YES) {
                if ((bean.getRefMoney() + bean.getBorrowTotal()) != total) {
                    throw new MYException("公司付款给员工下财务入账金额必须是[%.2f]，当前入账金额是[%.2f]",
                            MathTools.longToDouble2(bean.getRefMoney() + bean.getBorrowTotal()),
                            MathTools.longToDouble2(total));
                }
            }

            // 员工付款给公司
            if (bean.getPayType() == TcpConstanst.PAYTYPE_PAY_NO) {
                if ((bean.getRefMoney() - bean.getLastMoney()) != total) {
                    throw new MYException("员工付款给公司下财务入账金额必须是[%.2f]，当前入账金额是[%.2f]",
                            MathTools.longToDouble2(bean.getRefMoney() - bean.getLastMoney()),
                            MathTools.longToDouble2(total));
                }
            }

            if (param.getOther() != null) {
                Collection<TcpPayListener> listenerMapValues = this.listenerMapValues();

                for (TcpPayListener tcpPayListener : listenerMapValues) {
                    // TODO_OSGI 这里是报销待财务入账生成的凭证
                    tcpPayListener.onEndExpenseApply(user, bean, (List<String>) param.getOther(),
                            (List<Long>) param.getOther2(), (List<String>) param.getOther3());
                }
            }

            // 结束申请单
            if (!StringTools.isNullOrNone(bean.getRefId())) {
                travelApplyDAO.updateFeedback(bean.getRefId(), bean.getId(),
                        TcpConstanst.TCP_APPLY_FEEDBACK_YES);
            }
        }
    }

    /**
     * 合计金额
     * 
     * @param param
     * @return
     */
    private long fillTaxTotal(TcpParamWrap param) {
        List<Long> monetList = (List<Long>) param.getOther2();

        if (monetList == null) {
            return 0;
        }

        long total = 0L;

        for (Long each : monetList) {
            total += each;
        }

        return total / 100;
    }

    /**
     * createOutBill
     * 
     * @param user
     * @param outBill
     * @param apply
     * @throws MYException
     */
    private void createOutBill(User user, OutBillBean outBill, ExpenseApplyVO apply)
            throws MYException {
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
     * creatInBill
     * 
     * @param user
     * @param inBill
     * @param apply
     * @throws MYException
     */
    private void creatInBill(User user, InBillBean inBill, ExpenseApplyVO apply) throws MYException {
        // 自动生成付款单
        inBill.setDescription(DefinedCommon.getValue("tcpType", apply.getType()) + "申请报销还款金额:"
                + apply.getId());

        inBill.setLocationId(user.getLocationId());

        inBill.setLogTime(TimeTools.now());

        inBill.setType(FinanceConstant.INBILL_TYPE_UNBORROW);

        inBill.setOwnerId(apply.getBorrowStafferId());

        inBill.setStafferId(user.getStafferId());

        // 借款的单据
        inBill.setOutId(apply.getId());

        billManager.addInBillBeanWithoutTransaction(user, inBill);
    }

    /**
     * 权限
     * 
     * @param user
     * @param id
     * @throws MYException
     */
    private void checkAuth(User user, String id) throws MYException {
        List<TcpApproveBean> approveList = tcpApproveDAO.queryEntityBeansByFK(id);

        boolean hasAuth = false;

        for (TcpApproveBean tcpApproveBean : approveList) {
            if (tcpApproveBean.getApproverId().equals(user.getStafferId())) {
                hasAuth = true;

                break;
            }
        }

        if (!hasAuth) {
            throw new MYException("没有操作权限,请确认操作");
        }
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean rejectExpenseBean(User user, TcpParamWrap param) throws MYException {
        String id = param.getId();
        String reason = param.getReason();
        String type = param.getType();

        JudgeTools.judgeParameterIsNull(user, id);

        ExpenseApplyVO bean = findVO(id);

        if (bean == null) {
            throw new MYException("数据错误,请确认操作");
        }

        // 权限
        checkAuth(user, id);

        // 获得当前的处理环节
        // TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(),
        // bean.getStatus());

        // 获得上一步
        FlowLogBean lastLog = flowLogDAO.findLastLog(bean.getId());

        if (lastLog == null) {
            // 驳回到初始
            type = "1";
        } else {
            int nextStatus = lastLog.getAfterStatus();

            // 驳回上一步到处是也是type=1
            if (TCPHelper.isTravelApplyInit(nextStatus)) {
                type = "1";
            }
        }

        // 驳回到初始
        if ("1".equals(type)) {
            // 结束了需要清空
            tcpApproveDAO.deleteEntityBeansByFK(bean.getId());

            // 清空预占的预算
            budgetManager.deleteBudgetLogListWithoutTransactional(user, bean.getId());

            if (bean.getType() != TcpConstanst.TCP_EXPENSETYPE_COMMON) {
                // 解冻申请占用的预算(这样恢复预算)
                changeBudgetBrrowLockStatus(user, bean.getRefId(),
                        BudgetConstant.BUDGETLOG_STATUS_OK);
            }

            int oldStatus = bean.getStatus();

            bean.setStatus(TcpConstanst.TCP_STATUS_REJECT);

            expenseApplyDAO.updateStatus(bean.getId(), bean.getStatus());

            // 对于跨月报销，且预算结束的情况，在稽核后会发生预算的自动流转，所以，在这里要反向操作预转（也就是恢复原样），参考checkBudget方法
            budgetTurnRollback(bean);

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_REJECT);

            // 通知提交人
            notifyManager.notifyMessage(bean.getStafferId(),
                    "费用报销:" + bean.getName() + "被" + user.getStafferName() + "驳回,原因:" + reason);

            MailBean mail = new MailBean();

            mail.setTitle("费用报销:" + bean.getName() + "被" + user.getStafferName() + "驳回,原因:"
                    + reason);

            mail.setContent(mail.getContent());

            mail.setSenderId(StafferConstant.SUPER_STAFFER);

            mail.setReveiveIds(bean.getStafferId());

            mail.setHref(TcpConstanst.TCP_EXPENSE_DETAIL_URL + bean.getId());

            // send mail
            mailMangaer.addMailWithoutTransactional(UserHelper.getSystemUser(), mail);
        }
        // 驳回到上一步
        else {
            // 获得上一步的人
            String actorId = lastLog.getActorId();

            int nextStatus = lastLog.getAfterStatus();

            int newStatus = saveApprove(user, actorId, bean, nextStatus, 0);

            int oldStatus = bean.getStatus();

            bean.setStatus(newStatus);

            expenseApplyDAO.updateStatus(bean.getId(), newStatus);

            if (TCPHelper.isTravelApplyInit(newStatus)) {
                // 清空预占的金额
                budgetManager.deleteBudgetLogListWithoutTransactional(user, bean.getId());
            }

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_REJECT_PRE);
        }

        return true;
    }

    /**
     * budgetTurnRollback
     * 
     * 跨月报销，关联申请单的预算已经结束，报销时要流转<br>
     * 这里主要是驳回时，流转也要恢复<br>
     * 
     * 
     * @param bean
     */
    private void budgetTurnRollback(ExpenseApplyVO bean) {

        if (bean.getType() != TcpConstanst.TCP_EXPENSETYPE_COMMON) {
            // 删除冻结的(这样借款单据就没有预算了),这里有个问题要是借款的预算已经关闭,是不能删除预算使用的
            List<BudgetLogBean> logList = budgetLogDAO.queryEntityBeansByFK(bean.getRefId());

            // 考虑报销时关联的申请占用预算已关闭的场景:在提交时已将同一部门，同一费用，即可以流转到本月预算中的数据存在另一张表t_center_budgetlogtmp
            // 同时，将流转的预算使用状态改为流转，并扣减原预算总额
            List<BudgetLogTmpBean> logTmpList = budgetLogTmpDAO.queryEntityBeansByFK(bean
                    .getRefId());

            // 转入
            List<BudgetLogTmpBean> logTmpList1 = budgetLogTmpDAO.queryEntityBeansByFK(bean.getId());

            // add 2012.6.20
            for (BudgetLogTmpBean budgetLogTmpBean : logTmpList1) {
                // 表示是预算已关闭，且报销时是同一部门

                if (budgetLogTmpBean.getStatus() == 99) {
                    // 关闭预算流入本月
                    budgetItemDAO.updateBudgetById(budgetLogTmpBean.getBudgetItemId(),
                            -MathTools.longToDouble2(budgetLogTmpBean.getMonery()));

                    // 预算总额变化 budgetDAO
                    budgetDAO.updateTotalById(budgetLogTmpBean.getBudgetId(),
                            -MathTools.longToDouble2(budgetLogTmpBean.getMonery()));

                    budgetLogTmpBean.setStatus(0);

                    budgetLogTmpDAO.updateEntityBean(budgetLogTmpBean);
                }

            }

            for (BudgetLogTmpBean budgetLogTmpBean : logTmpList) {

                if (budgetLogTmpBean.getStatus() == 98) {
                    
                    budgetItemDAO.updateBudgetById(budgetLogTmpBean.getBudgetItemId(),
                            MathTools.longToDouble2(budgetLogTmpBean.getMonery()));
                    
                    budgetItemDAO.updateUseMoneyAndRealMoneyById(budgetLogTmpBean.getBudgetItemId(),
                            MathTools.longToDouble2(budgetLogTmpBean.getMonery()));

                    // 预算总额变化 budgetDAO
                    budgetDAO.updateTotalById(budgetLogTmpBean.getBudgetId(),
                            MathTools.longToDouble2(budgetLogTmpBean.getMonery()));

                    budgetDAO.updateRealMoneyById(budgetLogTmpBean.getBudgetId(),
                            MathTools.longToDouble2(budgetLogTmpBean.getMonery()));

                    budgetLogTmpBean.setStatus(1);

                    budgetLogTmpDAO.updateEntityBean(budgetLogTmpBean);

                    for (BudgetLogBean budgetLogBean : logList) {

                        if (budgetLogBean.getId().equals(budgetLogTmpBean.getBudgetLogId())) {

                            budgetLogBean.setUserType(BudgetConstant.BUDGETLOG_USERTYPE_REAL);
                            budgetLogBean.setLog(budgetLogBean.getLog() + "- 预算流转恢复");

                            budgetLogDAO.updateEntityBean(budgetLogBean);

                        }

                    }
                }
            }

        }

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
    private int saveApprove(User user, List<String> processList, ExpenseApplyVO bean,
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
                approve.setCheckTotal(bean.getBorrowTotal());
                approve.setType(bean.getType());
                approve.setStype(bean.getStype());
                approve.setPool(pool);
                approve.setPayType(bean.getPayType());

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

    /**
     * 进入审批状态
     * 
     * @param processId
     * @param bean
     * @param pool
     * @throws MYException
     */
    private int saveApprove(User user, String processId, ExpenseApplyVO bean, int nextStatus,
            int pool) throws MYException {
        List<String> processList = new ArrayList();

        if (StringTools.isNullOrNone(processId)) {
            throw new MYException("审批的人不能为空");
        }

        processList.add(processId);

        return saveApprove(user, processList, bean, nextStatus, pool);
    }

    /**
     * 校验预算(且占用预算)/报销的时候先冻结申请的预算,然后重新规划处当前的预算
     * 
     * @param user
     * @param bean
     * @param type 0:new add 1:稽核需要重新整理pay和重新预算
     * @throws MYException
     */
    private void checkBudget(User user, ExpenseApplyVO bean, int type) throws MYException {
        // 通用报销下员工还款是不需要预算的
        if (bean.getType() == TcpConstanst.TCP_EXPENSETYPE_COMMON
                && bean.getPayType() == TcpConstanst.PAYTYPE_PAY_NO) {
            return;
        }

        // 非通用报销检查是否关联
        if (bean.getType() != TcpConstanst.TCP_EXPENSETYPE_COMMON) {
            TravelApplyBean apply = travelApplyDAO.find(bean.getRefId());

            checkApplyFeedback(bean, apply);
        }

        boolean flag = false;

        // 删除借款的预算使用,删除报销提交的预占预算，（type = 1 表示稽核时再次检查预算）
        if (type == 1) {
            // 先删除之前的
            budgetManager.deleteBudgetLogListWithoutTransactional(user, bean.getId());

            if (bean.getType() != TcpConstanst.TCP_EXPENSETYPE_COMMON) {
                // 删除冻结的(这样借款单据就没有预算了),这里有个问题要是借款的预算已经关闭,是不能删除预算使用的
                List<BudgetLogBean> logList = budgetLogDAO.queryEntityBeansByFK(bean.getRefId());

                // 考虑报销时关联的申请占用预算已关闭的场景:在提交时已将同一部门，同一费用，即可以流转到本月预算中的数据存在另一张表t_center_budgetlogtmp
                // 同时，将流转的预算使用状态改为流转，并扣减原预算总额
                List<BudgetLogTmpBean> logTmpList = budgetLogTmpDAO.queryEntityBeansByFK(bean
                        .getRefId());

                // 转入
                List<BudgetLogTmpBean> logTmpList1 = budgetLogTmpDAO.queryEntityBeansByFK(bean
                        .getId());

                // add 2012.6.20
                for (BudgetLogTmpBean budgetLogTmpBean : logTmpList1) {
                    // 表示是预算已关闭，且报销时是同一部门

                    if (budgetLogTmpBean.getStatus() == 0) {
                        // 关闭预算流入本月
                        budgetItemDAO.updateBudgetById(budgetLogTmpBean.getBudgetItemId(),
                                MathTools.longToDouble2(budgetLogTmpBean.getMonery()));

                        // 预算总额变化 budgetDAO
                        budgetDAO.updateTotalById(budgetLogTmpBean.getBudgetId(),
                                MathTools.longToDouble2(budgetLogTmpBean.getMonery()));

                        budgetLogTmpBean.setStatus(99);

                        budgetLogTmpDAO.updateEntityBean(budgetLogTmpBean);
                    }

                }

                for (BudgetLogTmpBean budgetLogTmpBean : logTmpList) {

                    if (budgetLogTmpBean.getStatus() == 1) {
                        budgetItemDAO.updateBudgetById(budgetLogTmpBean.getBudgetItemId(),
                                -MathTools.longToDouble2(budgetLogTmpBean.getMonery()));
                        
                        budgetItemDAO.updateUseMoneyAndRealMoneyById(budgetLogTmpBean.getBudgetItemId(),
                                -MathTools.longToDouble2(budgetLogTmpBean.getMonery()));

                        // 预算总额变化 budgetDAO
                        budgetDAO.updateTotalById(budgetLogTmpBean.getBudgetId(),
                                -MathTools.longToDouble2(budgetLogTmpBean.getMonery()));

                        budgetDAO.updateRealMoneyById(budgetLogTmpBean.getBudgetId(),
                                -MathTools.longToDouble2(budgetLogTmpBean.getMonery()));

                        budgetLogTmpBean.setStatus(98);

                        budgetLogTmpDAO.updateEntityBean(budgetLogTmpBean);
                    }

                }

                for (BudgetLogBean budgetLogBean : logList) {
                    BudgetBean budget = budgetDAO.find(budgetLogBean.getBudgetId());

                    if (budget == null) {
                        throw new MYException("预算不存在,请确认操作");
                    }

                    if (budget.getStatus() != BudgetConstant.BUDGET_STATUS_END) {
                        // 释放预占使用日志(这样跨月的报销会多使用预算,当前认为这样是可以接受的)
                        budgetLogDAO.deleteEntityBean(budgetLogBean.getId());
                    }

                    // 已关闭就打上标志
                    if (budget.getStatus() == BudgetConstant.BUDGET_STATUS_END) {
                        flag = true;
                    }

                    for (BudgetLogTmpBean budgetLogTmpBean : logTmpList) {

                        if (budgetLogBean.getId().equals(budgetLogTmpBean.getBudgetLogId())) {

                            budgetLogBean.setUserType(BudgetConstant.BUDGETLOG_USERTYPE_TURN);
                            budgetLogBean.setLog(budgetLogBean.getLog() + "- 预算流转");

                            budgetLogDAO.updateEntityBean(budgetLogBean);

                        }
                    }

                }

            }

        } else {
            if (bean.getType() != TcpConstanst.TCP_EXPENSETYPE_COMMON) {
                // 冻结前面的(这样前面的不占用预算)(因为申请占用了部分预算,报销的时候先把申请的预算冻结,然后把报销的预算占用,这样避免重复占用预算)
                List<BudgetLogBean> logList = budgetLogDAO.queryEntityBeansByFK(bean.getRefId());

                // add 2012.6.20 先删掉，防止之前提交过，并将已关闭的预算且符合流转的数据写入临时使用日志中
                budgetLogTmpDAO.deleteEntityBeansByFK(bean.getRefId());
                budgetLogTmpDAO.deleteEntityBeansByFK(bean.getId());

                // 对于已经结束的预算则不能变更状态了
                for (BudgetLogBean budgetLogBean : logList) {
                    BudgetBean budget = budgetDAO.find(budgetLogBean.getBudgetId());

                    if (budget == null) {
                        throw new MYException("预算不存在,请确认操作");
                    }

                    if (budget.getStatus() != BudgetConstant.BUDGET_STATUS_END) {
                        budgetLogBean.setStatus(BudgetConstant.BUDGETLOG_STATUS_TEMP);

                        budgetLogDAO.updateEntityBean(budgetLogBean);
                    }

                    // 已关闭就打上标志
                    if (budget.getStatus() == BudgetConstant.BUDGET_STATUS_END) {
                        flag = true;
                    }

                }
            }
        }

        double borrowRadio = 1.0;

        long max = 0;

        // 增加的时候
        if (type == 0) {
        	max = Math.max(bean.getRefMoney(), bean.getTotal());
        	
            // 如果报销的金额没有实际申请的大,还是需要占用的(防止总预算不够)
            if (bean.getRefMoney() >= bean.getTotal() && bean.getTotal() != 0) {
                //borrowRadio = bean.getRefMoney() / (bean.getTotal() + 0.0d);
            	// 如果borrowRadio大于1，则要求预算足够多，否则提交不了。
            	borrowRadio = 1.0;
                max = bean.getTotal();
            }
        } else {
            // 公司付款给员工
            if (bean.getPayType() == TcpConstanst.PAYTYPE_PAY_YES) {
                // 借款金额+支付金额
                max = bean.getRefMoney() + bean.getBorrowTotal();

                // 实际最终报销的情况下
                if (bean.getTotal() != 0) {
                    // 因为稽核有的时候有变化啊
                    borrowRadio = (bean.getRefMoney() + bean.getBorrowTotal())
                            / (bean.getTotal() + 0.0d);
                }
            }

            // 收支平衡(借款)
            if (bean.getPayType() == TcpConstanst.PAYTYPE_PAY_OK) {
                max = bean.getRefMoney();

                borrowRadio = 1.0;
            }

            // 员工付款(借款 - 回款)
            if (bean.getPayType() == TcpConstanst.PAYTYPE_PAY_NO) {
                max = bean.getRefMoney() - bean.getLastMoney();

                borrowRadio = 1.0;
            }

            // 防止越界
            if (max < 0) {
                max = 0;
            }
        }

        List<TravelApplyItemVO> itemVOList = bean.getItemVOList();

        List<TcpShareVO> shareVOList = bean.getShareVOList();

        // 0:比例分担 1:实际分担 2:每个报销项自己绑定
        int shareType = 2;

        for (TcpShareVO tcpShareVO : shareVOList) {
            if (tcpShareVO.getRatio() > 0) {
                shareType = 0;
            }

            if (tcpShareVO.getRealMonery() > 0) {
                shareType = 1;
            }
        }

        List<BudgetLogBean> logList = new ArrayList();

        // 分担模式
        if (bean.getSpecialType() == TcpConstanst.SPECIALTYPE_TEMPLATE_NO) {
            long hasUse = 0L;

            for (Iterator iterator = shareVOList.iterator(); iterator.hasNext();) {
                TcpShareVO tcpShareVO = (TcpShareVO) iterator.next();

                for (Iterator ite = itemVOList.iterator(); ite.hasNext();) {
                    TravelApplyItemVO travelApplyItemVO = (TravelApplyItemVO) ite.next();

                    // 预算鉴权()
                    BudgetLogBean log = new BudgetLogBean();

                    logList.add(log);

                    log.setBudgetId(tcpShareVO.getBudgetId());

                    BudgetItemBean item = budgetItemDAO.findByBudgetIdAndFeeItemId(
                            tcpShareVO.getBudgetId(), travelApplyItemVO.getFeeItemId());

                    if (item == null) {

                        throw new MYException("预算[%s]里面缺少预算项[%s],请确认操作",
                                tcpShareVO.getBudgetName(), travelApplyItemVO.getFeeItemName());
                    }

                    log.setBudgetItemId(item.getId());

                    log.setDepartmentId(tcpShareVO.getDepartmentId());

                    log.setFeeItemId(travelApplyItemVO.getFeeItemId());

                    log.setFromType(BudgetConstant.BUDGETLOG_FROMTYPE_EXPENSE);

                    log.setLocationId(user.getLocationId());

                    log.setLog(DefinedCommon.getValue("tcpType", bean.getType()) + "申请["
                            + bean.getId() + "]占用预算");

                    log.setLogTime(TimeTools.now());

                    log.setRefId(bean.getId());

                    log.setRefSubId(travelApplyItemVO.getId());

                    // 使用人
                    if (!StringTools.isNullOrNone(travelApplyItemVO.getFeeStafferId())) {
                        log.setStafferId(travelApplyItemVO.getFeeStafferId());
                    } else {
                        log.setStafferId(bean.getBorrowStafferId());
                    }

                    // 预占
                    log.setUserType(BudgetConstant.BUDGETLOG_USERTYPE_PRE);

                    // 这里肯定有误差的
                    long useMoney = 0;

                    if (tcpShareVO.getRatio() != 0) {
                        useMoney = Math.round((tcpShareVO.getRatio() / 100.0) * borrowRadio
                                * travelApplyItemVO.getMoneys());

                    } else {
                        // 直接指定金额(也是百分比啊)
                        double shareRatio = TCPHelper.getShareRatio(shareVOList, tcpShareVO);

                        useMoney = Math.round((shareRatio / 100.0) * borrowRadio
                                * travelApplyItemVO.getMoneys());
                    }

                    log.setMonery(useMoney);

                    hasUse += useMoney;
                }
            }

            // 实际的二次调整
            if (shareType == 1 && false) {
                resetShareratio(shareVOList, max);

                // 这里的实际使用的误差比较复杂(每个分担必须等于)/存在稽核后预算变小,但是实际的分担较大
                for (TcpShareVO tcpShareVO : shareVOList) {
                    long btotal = 0;

                    for (BudgetLogBean budgetLogBean : logList) {
                        if (budgetLogBean.getBudgetId().equals(tcpShareVO.getBudgetId())) {
                            btotal += budgetLogBean.getMonery();
                        }
                    }

                    // 金额有差异
                    if (btotal != tcpShareVO.getRealMonery()) {
                        // 多余的金额(可能是负数哦)
                        long cache = tcpShareVO.getRealMonery() - btotal;

                        for (BudgetLogBean budgetLogBean : logList) {
                            if (budgetLogBean.getBudgetId().equals(tcpShareVO.getBudgetId())) {
                                if (budgetLogBean.getMonery() + cache >= 0) {
                                    budgetLogBean.setMonery(budgetLogBean.getMonery() + cache);

                                    cache = 0;

                                    break;
                                } else {
                                    // 强制为0
                                    budgetLogBean.setMonery(0);

                                    // 顺差减少
                                    cache = budgetLogBean.getMonery() + cache;
                                }
                            }
                        }

                        if (cache > 0) {
                            // 这里说明有问题啊
                            badLog.equals("报销申请[" + bean.getId() + "]在分担上存在越界:" + cache + ".预算为:"
                                    + tcpShareVO.getBudgetName());
                        }
                    }
                }
            }

            // 处理不能的情况
            long chae = hasUse - max;

            // 消除误差
            if (chae != 0) {
                for (BudgetLogBean budgetLogBean : logList) {
                    if (budgetLogBean.getMonery() > chae) {
                        budgetLogBean.setMonery(budgetLogBean.getMonery() - chae);

                        break;
                    }
                }
            }
        }
        // 直接处理绑定在TravelApplyItemVO上的预算,十分简单
        else {
            // 直接使用预算
            long hasUse = 0L;

            for (Iterator ite = itemVOList.iterator(); ite.hasNext();) {
                TravelApplyItemVO travelApplyItemVO = (TravelApplyItemVO) ite.next();

                if (StringTools.isNullOrNone(travelApplyItemVO.getBudgetId())) {
                    throw new MYException("不是模板报销,数据错误,请确认操作");
                }

                // 预算鉴权()
                BudgetLogBean log = new BudgetLogBean();

                logList.add(log);

                log.setBudgetId(travelApplyItemVO.getBudgetId());

                BudgetItemBean item = budgetItemDAO.findByBudgetIdAndFeeItemId(
                        travelApplyItemVO.getBudgetId(), travelApplyItemVO.getFeeItemId());

                if (item == null) {

                    throw new MYException("预算[%s]里面缺少预算项[%s],请确认操作",
                            travelApplyItemVO.getBudgetName(), travelApplyItemVO.getFeeItemName());
                }

                BudgetBean budget = budgetDAO.find(travelApplyItemVO.getBudgetId());

                if (budget == null) {
                    throw new MYException("预算数据错误,请确认操作");
                }

                log.setBudgetItemId(item.getId());

                log.setDepartmentId(budget.getBudgetDepartment());

                log.setFeeItemId(travelApplyItemVO.getFeeItemId());

                log.setFromType(BudgetConstant.BUDGETLOG_FROMTYPE_EXPENSE);

                log.setLocationId(user.getLocationId());

                log.setLog(DefinedCommon.getValue("tcpType", bean.getType()) + "申请[" + bean.getId()
                        + "]占用预算");

                log.setLogTime(TimeTools.now());

                log.setRefId(bean.getId());

                log.setRefSubId(travelApplyItemVO.getId());

                // 使用人
                if (!StringTools.isNullOrNone(travelApplyItemVO.getFeeStafferId())) {
                    log.setStafferId(travelApplyItemVO.getFeeStafferId());
                } else {
                    log.setStafferId(bean.getBorrowStafferId());
                }

                // 预占
                log.setUserType(BudgetConstant.BUDGETLOG_USERTYPE_PRE);

                // 这里肯定有误差的
                long useMoney = Math.round(borrowRadio * travelApplyItemVO.getMoneys());
                log.setMonery(useMoney);

                hasUse += useMoney;
            }

            // 处理不能的情况
            long chae = hasUse - max;

            // 消除误差
            if (chae != 0) {
                for (BudgetLogBean budgetLogBean : logList) {
                    if (budgetLogBean.getMonery() > chae) {
                        budgetLogBean.setMonery(budgetLogBean.getMonery() - chae);

                        break;
                    }
                }
            }

        }

        // 进入使用日志,如果超出预算会抛出异常的
        if (flag) 
        {
            operationLog.info("报销单:" + bean.getId() + ", 原申请单：" + bean.getRefId() + " 预算已关闭, logList:"+ logList);
            
            budgetManager.addBudgetLogListWithoutTransactional(user, bean.getId(), bean.getRefId(),
                    bean.getRefMoney(), logList, type, shareVOList.size());
        } 
        else
            budgetManager.addBudgetLogListWithoutTransactional(user, bean.getId(), logList);
    }

    /**
     * 修改后金额自动重新设置分担
     * 
     * @param shareVOList
     * @param newBorrowal
     */
    private void resetShareratio(List<TcpShareVO> shareVOList, long newBorrowal) {
        double total = 0;

        for (TcpShareVO tcpShareVO : shareVOList) {
            total += tcpShareVO.getRealMonery();
        }

        if (total == newBorrowal) {
            return;
        }

        // 比例啊
        double ratio = newBorrowal / (total + 0.0d);

        long newtotal = 0;

        for (TcpShareVO tcpShareVO : shareVOList) {
            tcpShareVO.setRealMonery(Math.round(tcpShareVO.getRealMonery() * ratio));

            newtotal += tcpShareVO.getRealMonery();
        }

        // 修复分担
        if (newtotal != newBorrowal) {
            // 多余的需要加入
            long cache = newBorrowal - newtotal;

            for (TcpShareVO tcpShareVO : shareVOList) {
                if (tcpShareVO.getRealMonery() + cache >= 0) {
                    tcpShareVO.setRealMonery(tcpShareVO.getRealMonery() + cache);

                    break;
                }
            }
        }

    }

    /**
     * 修改预算日志的状态(可能回滚的)
     * 
     * @param user
     * @param id
     * @param lock
     * @throws MYException
     */
    private void changeBudgetBrrowLockStatus(User user, String id, int lock) throws MYException {
        budgetManager.updateBudgetLogStatusWithoutTransactional(user, id, lock);
    }

    /**
     * checkApplyFeedback
     * 
     * @param bean
     * @param apply
     * @throws MYException
     */
    private void checkApplyFeedback(ExpenseApplyVO bean, TravelApplyBean apply) throws MYException {
        if (apply == null) {
            throw new MYException("申请不存在,请确认操作");
        }

        if (apply.getFeedback() != TcpConstanst.TCP_APPLY_FEEDBACK_NO) {
            throw new MYException("申请[%s]已经被报销过,请确认操作", bean.getRefId());
        }
    }

    /**
     * checkApply
     * 
     * @param bean
     * @throws MYException
     */
    private void checkApply(User user, ExpenseApplyBean bean) throws MYException {
        // 不是通用报销不校验关联
        if (bean.getType() != TcpConstanst.TCP_EXPENSETYPE_COMMON) {
            TravelApplyBean apply = travelApplyDAO.find(bean.getRefId());

            if (apply == null) {
                throw new MYException("申请不存在,请确认操作");
            }

            if (apply.getFeedback() != TcpConstanst.TCP_APPLY_FEEDBACK_NO) {
                throw new MYException("申请[%s]已经被报销过,请确认操作", bean.getRefId());
            }

            if (!apply.getBorrowStafferId().equals(user.getStafferId())) {
                throw new MYException("只能关联自己的申请,请确认操作");
            }

//            if (apply.getStype() != bean.getStype()) {
//                throw new MYException("申请系列错误,请确认操作");
//            }

            // 报销的纳税和申请的一致
            if (StringTools.isNullOrNone(apply.getDutyId()))
            {
                bean.setDutyId(PublicConstant.DEFAULR_DUTY_ID);
            }
            else
            {
                bean.setDutyId(apply.getDutyId());
            }

            // 检查当前是否有单据关联
            List<ExpenseApplyBean> refList = expenseApplyDAO.queryEntityBeansByFK(bean.getRefId());

            if (refList.size() > 1) {
                throw new MYException("借款申请已经被其他单据使用,请确认操作");
            }

            if (refList.size() == 1 && !refList.get(0).getId().equals(bean.getId())) {
                throw new MYException("借款申请已经被其他单据[%s]使用,请确认操作", refList.get(0).getId());
            }

            // 实现单单清的策略
            bean.setRefMoney(apply.getBorrowTotal());

            if (bean.getPayType() == TcpConstanst.PAYTYPE_PAY_YES) {
                // 公司付款金额+借款金额 == 报销金额
                if (bean.getRefMoney() + bean.getBorrowTotal() != bean.getTotal()) {
                    throw new MYException("公司付款给员工下报销总金额[%s]必须等于借款金额[%s]加公司的付款金额[%s]",
                            MathTools.longToDoubleStr2(bean.getTotal()),
                            MathTools.longToDoubleStr2(bean.getRefMoney()),
                            MathTools.longToDoubleStr2(bean.getBorrowTotal()));
                }
            }

            if (bean.getPayType() == TcpConstanst.PAYTYPE_PAY_OK) {
                bean.setBorrowTotal(0);

                // 借款金额 == 报销金额
                if (bean.getRefMoney() + bean.getBorrowTotal() != bean.getTotal()) {
                    throw new MYException("收支平衡下报销总金额[%s]必须等于借款金额[%s]",
                            MathTools.longToDoubleStr2(bean.getTotal()),
                            MathTools.longToDoubleStr2(bean.getRefMoney()));
                }
            }

            if (bean.getPayType() == TcpConstanst.PAYTYPE_PAY_NO) {
                bean.setBorrowTotal(0);

                // 借款金额 == 报销金额+员工支付金额
                if (bean.getRefMoney() != bean.getTotal() + bean.getLastMoney()) {
                    throw new MYException("员工付款给公司下报销总金额[%s]加员工付款金额[%s]必须等于借款金额[%s]",
                            MathTools.longToDoubleStr2(bean.getTotal()),
                            MathTools.longToDoubleStr2(bean.getLastMoney()),
                            MathTools.longToDoubleStr2(bean.getRefMoney()));
                }
            }
        } else {
            // 默认
            bean.setDutyId(PublicConstant.DEFAULR_DUTY_ID);
        }

        if (bean.getShareList() != null) {
            int ratioTotal = 0;
            int shareTotal = 0;

            for (TcpShareBean tcpShareBean : bean.getShareList()) 
            {
                StafferBean sbean = stafferDAO.find(tcpShareBean.getBearId());
                
                if (null == sbean)
                {
                    throw new MYException("承担人不存在，ID:[%s]",tcpShareBean.getBearId());
                }
                
                if (sbean.getStatus() == StafferConstant.STATUS_DROP)
                {
                    throw new MYException("承担人[%s]状态为废弃", sbean.getName());
                }
                
                PrincipalshipBean prinBean = orgManager.findPrincipalshipById(tcpShareBean.getDepartmentId());
                
                if (null == prinBean)
                {
                    throw new MYException("部门不存在,ID:[%s]",tcpShareBean.getDepartmentId());
                }
                
                List<PrincipalshipBean> prinList = orgManager.querySubPrincipalship(sbean.getPrincipalshipId());
                
                for(Iterator<PrincipalshipBean> iterator = prinList.iterator(); iterator.hasNext();)
                {
                    PrincipalshipBean pBean = iterator.next();
                    
                    if (pBean.getStatus() == 1)
                        iterator.remove();
                }
                
                if (prinList.size() > 1)
                {
                    throw new MYException("承担人[%s]的部门[%s]不是最末级部门",sbean.getName(),prinList.get(0).getName());
                }
                
                // add 校验承担人是否与权签人是一同一部门
                if (!orgManager.isStafferBelongOrg (tcpShareBean.getBearId(), tcpShareBean.getDepartmentId())) 
                {
                    throw new MYException("承担人[%s]不在权签人所属的部门[%s]下,请确认操作",sbean.getName(), prinBean.getName() );
                }

                tcpShareBean.setId(commonDAO.getSquenceString20());
                tcpShareBean.setRefId(bean.getId());

                ratioTotal += tcpShareBean.getRatio();
                shareTotal += tcpShareBean.getRealMonery();
            }

            if (ratioTotal > 0) {
                if (ratioTotal != 100 && ratioTotal != 0) {
                    throw new MYException("分担比例不能小于0,且必须是整数");
                }
            } else {
                for (TcpShareBean tcpShareBean : bean.getShareList()) {
                    if (tcpShareBean.getRealMonery() <= 0) {
                        throw new MYException("分担金额比例不能小于0");
                    }
                }
            }
        }

        // 检验申请的状态
        if (!StringTools.isNullOrNone(bean.getRefId())) {
            TravelApplyBean travelApply = travelApplyDAO.find(bean.getRefId());

            if (travelApply == null) {
                throw new MYException("申请单据不存在无法报销,请确认操作");
            }

            // 小于待财务入账
            if (travelApply.getStatus() < TcpConstanst.TCP_STATUS_LAST_CHECK) {
                throw new MYException("申请单据状态不正确(需要是待财务入账之后的),请确认操作");
            }
        }
    }

    /**
     * saveApply
     * 
     * @param user
     * @param bean
     */
    public void saveApply(User user, ExpenseApplyBean bean) {
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
        apply.setPayType(bean.getPayType());

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
    private void saveFlowLog(User user, int preStatus, ExpenseApplyBean apply, String reason,
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.china.center.oa.tcp.manager.TravelApplyManager#updateExpenseApplyBean
     * (com.center.china.osgi.publics.User,
     * com.china.center.oa.tcp.bean.ExpenseApplyBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean updateExpenseBean(User user, ExpenseApplyBean bean) throws MYException {
        JudgeTools.judgeParameterIsNull(user, bean);

        ExpenseApplyBean old = expenseApplyDAO.find(bean.getId());

        if (old == null) {
            throw new MYException("数据错误,请确认操作");
        }

        if (!bean.getStafferId().equals(user.getStafferId())) {
            throw new MYException("只能修改自己的申请");
        }

        bean.setStatus(TcpConstanst.TCP_STATUS_INIT);

        bean.setBorrowStafferId(user.getStafferId());

        // 获取flowKey
        TCPHelper.setFlowKey(bean);

        // 先清理
        travelApplyItemDAO.deleteEntityBeansByFK(bean.getId());
        travelApplyPayDAO.deleteEntityBeansByFK(bean.getId());
        tcpShareDAO.deleteEntityBeansByFK(bean.getId());
        attachmentDAO.deleteEntityBeansByFK(bean.getId());

        // TravelApplyItemBean
        List<TravelApplyItemBean> itemList = bean.getItemList();

        for (TravelApplyItemBean travelApplyItemBean : itemList) {
            travelApplyItemBean.setId(commonDAO.getSquenceString20());
            travelApplyItemBean.setParentId(bean.getId());
        }

        travelApplyItemDAO.saveAllEntityBeans(itemList);

        if (bean.getPayType() == TcpConstanst.PAYTYPE_PAY_YES) {
            long temp = 0L;

            List<TravelApplyPayBean> payList = bean.getPayList();

            for (TravelApplyPayBean travelApplyPayBean : payList) {
                travelApplyPayBean.setId(commonDAO.getSquenceString20());

                travelApplyPayBean.setParentId(bean.getId());

                temp += travelApplyPayBean.getMoneys();
            }

            // 这里的公司支付金额
            bean.setBorrowTotal(temp);

            // 员工还款金额
            bean.setLastMoney(0);

            travelApplyPayDAO.saveAllEntityBeans(payList);
        } else if (bean.getPayType() == TcpConstanst.PAYTYPE_PAY_OK) {
            bean.setLastMoney(0);

            bean.setBorrowTotal(0);
        }

        List<TcpShareBean> shareList = null;

        if (ListTools.isEmptyOrNull(bean.getShareList())) {
            // 从父申请里面拷贝分担比例
            shareList = tcpShareDAO.queryEntityBeansByFK(bean.getRefId());
        } else {
            // 通用的自己申请
            shareList = bean.getShareList();
        }

        checkApply(user, bean);

        for (TcpShareBean tcpShareBean : shareList) {
            tcpShareBean.setId(commonDAO.getSquenceString20());
            tcpShareBean.setRefId(bean.getId());
        }

        tcpShareDAO.saveAllEntityBeans(shareList);

        List<AttachmentBean> attachmentList = bean.getAttachmentList();

        for (AttachmentBean attachmentBean : attachmentList) {
            attachmentBean.setId(commonDAO.getSquenceString20());
            attachmentBean.setRefId(bean.getId());
        }

        attachmentDAO.saveAllEntityBeans(attachmentList);

        expenseApplyDAO.updateEntityBean(bean);

        saveFlowLog(user, old.getStatus(), bean, "自动修改保存", PublicConstant.OPRMODE_SAVE);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean deleteExpenseBean(User user, String id) throws MYException {
        JudgeTools.judgeParameterIsNull(user, id);

        ExpenseApplyBean bean = expenseApplyDAO.find(id);

        if (bean == null) {
            throw new MYException("数据错误,请确认操作");
        }

        if (!TCPHelper.canTravelApplyDelete(bean)) {
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

        for (AttachmentBean attachmentBean : attachmenList) {
            FileTools.deleteFile(rootPath + attachmentBean.getPath());
        }

        attachmentDAO.deleteEntityBeansByFK(bean.getId());

        expenseApplyDAO.deleteEntityBean(id);

        tcpApplyDAO.deleteEntityBean(id);

        operationLog.info(user + " delete ExpenseApplyBean:" + bean);

        return true;
    }

    public ExpenseApplyVO findVO(String id) {
        ExpenseApplyVO bean = expenseApplyDAO.findVO(id);

        if (bean == null) {
            return bean;
        }

        // 部门
        PrincipalshipBean depa = orgManager.findPrincipalshipById(bean.getDepartmentId());

        if (depa != null) {
            bean.setDepartmentName(depa.getFullName());
        }

        List<TravelApplyItemVO> itemVOList = travelApplyItemDAO.queryEntityVOsByFK(id);

        bean.setItemVOList(itemVOList);

        for (TravelApplyItemVO travelApplyItemVO : itemVOList) {
            travelApplyItemVO
                    .setShowMoneys(TCPHelper.formatNum2(travelApplyItemVO.getMoneys() / 100.0d));
        }

        List<AttachmentBean> attachmentList = attachmentDAO.queryEntityVOsByFK(id);

        bean.setAttachmentList(attachmentList);

        List<TravelApplyPayBean> payList = travelApplyPayDAO.queryEntityVOsByFK(id);

        bean.setPayList(payList);

        List<TcpShareVO> shareList = tcpShareDAO.queryEntityVOsByFK(id);

        for (TcpShareVO tcpShareVO : shareList) {
            PrincipalshipBean dep = orgManager.findPrincipalshipById(tcpShareVO.getDepartmentId());

            if (dep != null) {
                tcpShareVO.setDepartmentName(dep.getFullName());
            }

            if (tcpShareVO.getRatio() == 0) {
                tcpShareVO
                        .setShowRealMonery(MathTools.longToDoubleStr2(tcpShareVO.getRealMonery()));
            } else {
                tcpShareVO.setShowRealMonery(String.valueOf(tcpShareVO.getRatio()));
            }
        }

        bean.setShareVOList(shareList);

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
     * @return the tcpPrepaymentDAO
     */
    public TcpPrepaymentDAO getTcpPrepaymentDAO() {
        return tcpPrepaymentDAO;
    }

    /**
     * @param tcpPrepaymentDAO the tcpPrepaymentDAO to set
     */
    public void setTcpPrepaymentDAO(TcpPrepaymentDAO tcpPrepaymentDAO) {
        this.tcpPrepaymentDAO = tcpPrepaymentDAO;
    }

    /**
     * @return the tcpShareDAO
     */
    public TcpShareDAO getTcpShareDAO() {
        return tcpShareDAO;
    }

    /**
     * @param tcpShareDAO the tcpShareDAO to set
     */
    public void setTcpShareDAO(TcpShareDAO tcpShareDAO) {
        this.tcpShareDAO = tcpShareDAO;
    }

    /**
     * @return the travelApplyDAO
     */
    public TravelApplyDAO getTravelApplyDAO() {
        return travelApplyDAO;
    }

    /**
     * @param travelApplyDAO the travelApplyDAO to set
     */
    public void setTravelApplyDAO(TravelApplyDAO travelApplyDAO) {
        this.travelApplyDAO = travelApplyDAO;
    }

    /**
     * @return the travelApplyItemDAO
     */
    public TravelApplyItemDAO getTravelApplyItemDAO() {
        return travelApplyItemDAO;
    }

    /**
     * @param travelApplyItemDAO the travelApplyItemDAO to set
     */
    public void setTravelApplyItemDAO(TravelApplyItemDAO travelApplyItemDAO) {
        this.travelApplyItemDAO = travelApplyItemDAO;
    }

    /**
     * @return the travelApplyPayDAO
     */
    public TravelApplyPayDAO getTravelApplyPayDAO() {
        return travelApplyPayDAO;
    }

    /**
     * @param travelApplyPayDAO the travelApplyPayDAO to set
     */
    public void setTravelApplyPayDAO(TravelApplyPayDAO travelApplyPayDAO) {
        this.travelApplyPayDAO = travelApplyPayDAO;
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
     * @return the budgetManager
     */
    public BudgetManager getBudgetManager() {
        return budgetManager;
    }

    /**
     * @param budgetManager the budgetManager to set
     */
    public void setBudgetManager(BudgetManager budgetManager) {
        this.budgetManager = budgetManager;
    }

    /**
     * @return the budgetItemDAO
     */
    public BudgetItemDAO getBudgetItemDAO() {
        return budgetItemDAO;
    }

    /**
     * @param budgetItemDAO the budgetItemDAO to set
     */
    public void setBudgetItemDAO(BudgetItemDAO budgetItemDAO) {
        this.budgetItemDAO = budgetItemDAO;
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
     * @return the expenseApplyDAO
     */
    public ExpenseApplyDAO getExpenseApplyDAO() {
        return expenseApplyDAO;
    }

    /**
     * @param expenseApplyDAO the expenseApplyDAO to set
     */
    public void setExpenseApplyDAO(ExpenseApplyDAO expenseApplyDAO) {
        this.expenseApplyDAO = expenseApplyDAO;
    }

    /**
     * @return the budgetDAO
     */
    public BudgetDAO getBudgetDAO() {
        return budgetDAO;
    }

    /**
     * @param budgetDAO the budgetDAO to set
     */
    public void setBudgetDAO(BudgetDAO budgetDAO) {
        this.budgetDAO = budgetDAO;
    }

    /**
     * @return the budgetLogDAO
     */
    public BudgetLogDAO getBudgetLogDAO() {
        return budgetLogDAO;
    }

    /**
     * @param budgetLogDAO the budgetLogDAO to set
     */
    public void setBudgetLogDAO(BudgetLogDAO budgetLogDAO) {
        this.budgetLogDAO = budgetLogDAO;
    }

    public BudgetLogTmpDAO getBudgetLogTmpDAO() {
        return budgetLogTmpDAO;
    }

    public void setBudgetLogTmpDAO(BudgetLogTmpDAO budgetLogTmpDAO) {
        this.budgetLogTmpDAO = budgetLogTmpDAO;
    }

    public StafferDAO getStafferDAO() {
        return stafferDAO;
    }

    public void setStafferDAO(StafferDAO stafferDAO) {
        this.stafferDAO = stafferDAO;
    }

    
}
