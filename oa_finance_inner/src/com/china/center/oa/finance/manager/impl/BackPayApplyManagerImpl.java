/**
 * File Name: BackPayApplyManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-3-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.manager.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.dao.CustomerMainDAO;
import com.china.center.oa.client.dao.StafferVSCustomerDAO;
import com.china.center.oa.client.vs.StafferVSCustomerBean;
import com.china.center.oa.finance.bean.BackPayApplyBean;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.constant.BackPayApplyConstant;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.BackPayApplyDAO;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.finance.dao.OutBillDAO;
import com.china.center.oa.finance.listener.BackPayApplyListener;
import com.china.center.oa.finance.manager.BackPayApplyManager;
import com.china.center.oa.finance.manager.BillManager;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.dao.OutBalanceDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.manager.OutManager;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * BackPayApplyManagerImpl
 * 
 * @author ZHUZHU
 * @version 2011-3-3
 * @see BackPayApplyManagerImpl
 * @since 3.0
 */
@Exceptional
public class BackPayApplyManagerImpl extends AbstractListenerManager<BackPayApplyListener> implements BackPayApplyManager
{
    private final Log _logger = LogFactory.getLog(getClass());
    
    private BackPayApplyDAO backPayApplyDAO = null;

    private CommonDAO commonDAO = null;

    private OutDAO outDAO = null;

    private InBillDAO inBillDAO = null;

    private OutBillDAO outBillDAO = null;

    private FlowLogDAO flowLogDAO = null;

    private BillManager billManager = null;

    private OutManager outManager = null;

    private StafferVSCustomerDAO stafferVSCustomerDAO = null;
    
    private CustomerMainDAO customerMainDAO = null;
    
    private StafferDAO stafferDAO = null;
    
    private OutBalanceDAO outBalanceDAO = null;
    
    /**
     * default constructor
     */
    public BackPayApplyManagerImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.finance.manager.BackPayApplyManager#addBackPayApplyBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.finance.bean.BackPayApplyBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addBackPayApplyBean(User user, BackPayApplyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        if (bean.getType() == BackPayApplyConstant.TYPE_OUT)
        {
            checkPay(user, bean);
        }
        else
        {
            checkBillPay(bean);
        }

        bean.setId(commonDAO.getSquenceString20());

        bean.setLogTime(TimeTools.now());

        bean.setStafferId(user.getStafferId());

        bean.setLocationId(user.getLocationId());

        if (bean.getType() == BackPayApplyConstant.TYPE_OUT)
        {
            bean.setStatus(BackPayApplyConstant.STATUS_SUBMIT);
        }
        else
        {
            bean.setStatus(BackPayApplyConstant.STATUS_SEC);
        }

        // 预收变为关联状态
        if (bean.getType() == BackPayApplyConstant.TYPE_BILL)
        {
            InBillBean bill = inBillDAO.find(bean.getBillId());

            bill.setStatus(FinanceConstant.INBILL_STATUS_PREPAYMENTS);

            inBillDAO.updateEntityBean(bill);
        }

        saveFlowLog(user, BackPayApplyConstant.STATUS_INIT, bean, "提交", PublicConstant.OPRMODE_PASS);

        return backPayApplyDAO.saveEntityBean(bean);
    }

    /**
     * checkBillPay
     * 
     * @param bean
     * @throws MYException
     */
    private void checkBillPay(BackPayApplyBean bean)
        throws MYException
    {
        InBillBean inBill = inBillDAO.find(bean.getBillId());

        if (inBill == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (bean.getBackPay() <= 0.0d)
        {
            throw new MYException("退款金额错误,请确认操作");
        }

        if (inBill.getStatus() != FinanceConstant.INBILL_STATUS_NOREF)
        {
            throw new MYException("必须是预收单据,请确认操作");
        }

        if (inBill.getMoneys() < bean.getBackPay())
        {
            throw new MYException("退款金额溢出,请确认操作");
        }

        // 一个单子只能存在一个申请
        ConditionParse condition = new ConditionParse();

        condition.addWhereStr();

        condition.addCondition("BackPayApplyBean.billId", "=", bean.getBillId());

        condition.addIntCondition("BackPayApplyBean.status", "<",
            BackPayApplyConstant.STATUS_REJECT);

        int countByCondition = backPayApplyDAO.countByCondition(condition.toString());

        if (countByCondition > 0)
        {
            throw new MYException("此预收存在未结束的退款申请,请确认操作");
        }
    }

    /**
     * saveFlowLog
     * 
     * @param user
     * @param preStatus
     * @param apply
     * @param reason
     * @param oprMode
     */
    private void saveFlowLog(User user, int preStatus, BackPayApplyBean apply, String reason,
                             int oprMode)
    {
        FlowLogBean log = new FlowLogBean();

        log.setFullId(apply.getId());

        log.setActor(user.getStafferName());

        log.setOprMode(oprMode);

        log.setDescription(reason);

        log.setLogTime(TimeTools.now());

        log.setPreStatus(preStatus);

        log.setAfterStatus(apply.getStatus());

        flowLogDAO.saveEntityBean(log);
    }

    /**
     * checkAdd
     * 
     * @param bean
     * @throws MYException
     */
    private void checkPay(User user, BackPayApplyBean bean)
        throws MYException
    {
        double backTotal = outDAO.sumOutBackValue(bean.getOutId());

        OutBean out = outDAO.find(bean.getOutId());

        if (out == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 查询收款单
        double hasdIn = inBillDAO.sumByOutId(bean.getOutId());

        double hasdOut = outBillDAO.sumByRefId(bean.getOutId());

        double max = -outManager.outNeedPayMoney(user, out.getFullId());

        // 付款金额-退货金额-已经退款金额
        // double max = out.getTotal() - hasdIn + backTotal - hasdOut;

        if ( !MathTools.equal(bean.getBackPay() + bean.getChangePayment(), max))
        {
            throw new MYException(
                "销售单支付金额[%.2f],退货实物价值[%.2f],退货返还金额[%.2f],申请退货返还金额[%.2f],申请转预收金额[%.2f],申请金额必须等于[%.2f]",
                hasdIn, backTotal, hasdOut, bean.getBackPay(), bean.getChangePayment(), max);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.finance.manager.BackPayApplyManager#deleteBackPayApplyBean(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean deleteBackPayApplyBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        BackPayApplyBean old = backPayApplyDAO.find(id);

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (old.getStatus() != BackPayApplyConstant.STATUS_REJECT)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !user.getStafferId().equals(old.getStafferId()))
        {
            throw new MYException("数据错误,请确认操作");
        }

        backPayApplyDAO.deleteEntityBean(id);

        flowLogDAO.deleteEntityBeansByFK(id);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.finance.manager.BackPayApplyManager#passBackPayApplyBean(com.center.china.osgi.publics.User,
     *      java.lang.String, java.lang.String)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean rejectBackPayApplyBean(User user, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        BackPayApplyBean bean = backPayApplyDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        int preStatus = bean.getStatus();

        if (bean.getStatus() != BackPayApplyConstant.STATUS_SUBMIT
            && bean.getStatus() != BackPayApplyConstant.STATUS_SEC)
        {
            throw new MYException("数据错误,请确认操作");
        }

        bean.setStatus(BackPayApplyConstant.STATUS_REJECT);

        backPayApplyDAO.updateEntityBean(bean);

        // 预收回滚状态
        if (bean.getType() == BackPayApplyConstant.TYPE_BILL)
        {
            InBillBean bill = inBillDAO.find(bean.getBillId());

            if (bill != null && bill.getStatus() == FinanceConstant.INBILL_STATUS_PREPAYMENTS)
            {
                bill.setStatus(FinanceConstant.INBILL_STATUS_NOREF);

                bill.setDescription(bill.getDescription() + "<br>驳回[" + id + "]状态重置到预收");

                inBillDAO.updateEntityBean(bill);
            }
        }

        saveFlowLog(user, preStatus, bean, reason, PublicConstant.OPRMODE_REJECT);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.finance.manager.BackPayApplyManager#rejectBackPayApplyBean(com.center.china.osgi.publics.User,
     *      java.lang.String, java.lang.String)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean passBackPayApplyBean(User user, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        BackPayApplyBean bean = backPayApplyDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (bean.getType() == BackPayApplyConstant.TYPE_OUT)
        {
            checkPay(user, bean);
        }

        int next = 0;

        int preStatus = bean.getStatus();

        if (bean.getStatus() == BackPayApplyConstant.STATUS_SUBMIT)
        {
            next = BackPayApplyConstant.STATUS_SEC;
        }
        else if (bean.getStatus() == BackPayApplyConstant.STATUS_SEC)
        {
            bean.setDescription(bean.getDescription() + ",稽核意见："+ reason);
            next = BackPayApplyConstant.STATUS_END;
        }
        else
        {
            throw new MYException("数据错误,请确认操作");
        }

        bean.setStatus(next);

        backPayApplyDAO.updateEntityBean(bean);

        saveFlowLog(user, preStatus, bean, reason, PublicConstant.OPRMODE_PASS);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean endBackPayApplyBean(User user, String id, String reason,
                                       List<OutBillBean> outBillList)
        throws MYException
    {
        // 先通过
        passBackPayApplyBean(user, id, reason);

        BackPayApplyBean bean = backPayApplyDAO.find(id);

        String outBillId = "";

        // 付款(销售退款和预收退款)
        if ( !ListTools.isEmptyOrNull(outBillList))
        {
            // 事务里面先付款给供应商/客户
            outBillId = createOutBill(user, outBillList, bean);
        }

        if (bean.getType() == BackPayApplyConstant.TYPE_OUT)
        {
            // 处理销售退款
            handleOut(user, bean);
        }
        else
        {
            StringBuffer refBuffer = new StringBuffer();

            // 处理预收退款(把部分预收拆分出变成已经收款且锁定,正好和上面的付款抵消掉)
            InBillBean inBill = inBillDAO.find(bean.getBillId());

            if (inBill == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            String newId = "";

            // 为了兼容哦
            if (MathTools.equal2(bean.getBackPay(), inBill.getMoneys()))
            {
                if (inBill.getStatus() != FinanceConstant.INBILL_STATUS_PREPAYMENTS)
                {
                    throw new MYException("收款单已经不在关联申请状态,请确认操作");
                }

                newId = bean.getBillId();
            }
            else
            {
                if (inBill.getStatus() != FinanceConstant.INBILL_STATUS_NOREF)
                {
                    throw new MYException("收款单已经不在预收状态,请确认操作");
                }

                // 暂时不拆分了
                newId = billManager.splitInBillBeanWithoutTransactional(user, inBill.getId(), bean
                    .getBackPay());
            }

            // 预收
            InBillBean newInBill = inBillDAO.find(newId);

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

            refBuffer.append(newInBill.getId()).append(',');

            bean.setRefIds(bean.getRefIds() + refBuffer.toString());
        }

        // 更新关联
        backPayApplyDAO.updateRefIds(bean.getId(), bean.getRefIds());

        // TAX_ADD 销售退款/预收退款
        Collection<BackPayApplyListener> listenerMapValues = this.listenerMapValues();

        for (BackPayApplyListener listener : listenerMapValues)
        {
            listener.onEndBackPayApplyBean(user, bean, outBillList);
        }

        return true;
    }

    /**
     * 处理销售退款(只有转预收才处理,无预收不处理)
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    private void handleOut(User user, BackPayApplyBean bean)
        throws MYException
    {
        StringBuffer refBuffer = new StringBuffer();
        
        String refId = bean.getOutBalanceId();

        if (bean.getChangePayment() > 0)
        {
            // 找出已收,然后自动拆分
            List<InBillBean> inList = null;
            
        	if (StringTools.isNullOrNone(refId))
        	{
        		inList = inBillDAO.queryEntityBeansByFK(bean.getOutId());
        	}
        	else // 委托结算退货
        	{
        		inList = inBillDAO.queryEntityBeansByFK(refId, AnoConstant.FK_SECOND);
        	}

            // 从大到小
            Collections.sort(inList, new Comparator<InBillBean>()
            {
                public int compare(InBillBean o1, InBillBean o2)
                {
                    return (int) (o1.getMoneys() - o2.getMoneys());
                }
            });

            double hasOut = bean.getChangePayment();

            double badDebt = 0.0d;
            
            for (int i = 0; i < inList.size(); i++ )
            {
                InBillBean each = inList.get(i);

                // 最后的处理
                if (each.getMoneys() > hasOut)
                {
                    String newId = billManager.splitInBillBeanWithoutTransactional(user, each
                        .getId(), hasOut);

                    // 预收
                    InBillBean newInBill = inBillDAO.find(newId);

                    if (newInBill == null)
                    {
                        throw new MYException("数据错误,请确认操作");
                    }

                    // 取消关联了
                    newInBill.setOutId("");
                    newInBill.setOutBalanceId("");

                    // 未核对
                    newInBill.setCheckStatus(PublicConstant.CHECK_STATUS_INIT);
                    newInBill.setChecks("");

                    newInBill.setStatus(FinanceConstant.INBILL_STATUS_NOREF);
                    newInBill
                        .setDescription(newInBill.getDescription()
                                        + ";退货转预收,销售单:"
                                        + "<a href='../sail/out.do?method=findOut&radioIndex=0&fow=99&outId="
                                        + bean.getOutId() + "'>" + bean.getOutId() + "</a>");

                    inBillDAO.updateEntityBean(newInBill);

                    refBuffer.append(newInBill.getId()).append(',');

                    if (each.getType() == FinanceConstant.INBILL_TYPE_BADOUT
                    		&& each.getCreateType() == FinanceConstant.BILL_CREATETYPE_HAND)
                    {
                    	badDebt += hasOut;
                    }
                    
                    break;
                }
                else
                {
                    // 逐步的转预收
                    hasOut = hasOut - each.getMoneys();
                    each.setOutId("");
                    each.setOutBalanceId("");
                    each.setStatus(FinanceConstant.INBILL_STATUS_NOREF);

                    // 未核对
                    each.setCheckStatus(PublicConstant.CHECK_STATUS_INIT);
                    each.setChecks("");

                    each.setDescription("销售退款转预收:" + bean.getOutId());

                    inBillDAO.updateEntityBean(each);

                    refBuffer.append(each.getId()).append(',');

                    if (each.getType() == FinanceConstant.INBILL_TYPE_BADOUT
                    		&& each.getCreateType() == FinanceConstant.BILL_CREATETYPE_HAND)
                    {
                    	badDebt += each.getMoneys();
                    }
                    
                    if (hasOut == 0.0d)
                    {
                        break;
                    }
                }
            }

            // 移交客户预收
            transInBill(user, bean, refBuffer);
            
            double newPay = inBillDAO.sumByOutId(bean.getOutId());

            // 这里不更新单子的状态,只更新付款金额
            outDAO.updateHadPay(bean.getOutId(), newPay);

            OutBean out = outDAO.find(bean.getOutId());

            if (out != null)
            {
            	// 处理销售退库转预收时,坏账应收转预收时,订单中的坏账要进行调整
            	if (out.getBadDebts() > 0 && badDebt > 0)
            	{
            		if (badDebt <= out.getBadDebts())
            		{
            			outDAO.modifyBadDebts(bean.getOutId(), (out.getBadDebts() - badDebt));
            		}
            	}
            	
                outDAO.updateDescription(bean.getOutId(), StringTools.truncate2(out
                    .getDescription()
                                                                                + ";退货转预收:"
                                                                                + refBuffer, 500));
            }
        }

        if ( !StringTools.isNullOrNone(bean.getOutId()))
        {
            outManager.payOutWithoutTransactional2(user, bean.getOutId(), "销售退款后清算");
        }
        
        if ( !StringTools.isNullOrNone(refId))
        {
            // 修改结算单上的已付款金额
        	double newPayMoneys = inBillDAO.sumByOutBalanceId(refId);
        	
        	outBalanceDAO.updateHadPay(refId, newPayMoneys);
        }

        bean.setRefIds(bean.getRefIds() + refBuffer.toString());
    }

    /**
     * 销售退库时如果客户没有挂靠在经手人的名下，则自动移交
     * @param user
     * @param bean
     * @param refBuffer
     * @throws MYException
     */
    private void transInBill(User user, BackPayApplyBean bean, StringBuffer refBuffer)
            throws MYException {
        // add 针对退货转预收检查预收中的客户是否是挂靠在退货经手人的头上
        // 1)客户不存在，不处理，直接返回
        // 2)退库单经手人不是客户的当前挂靠人，也不处理，直接返回
        // 3)预收中的挂靠人是客户当前的挂靠人，不处理
        
        CustomerBean customerBean = customerMainDAO.find(bean.getCustomerId());

        if (customerBean == null)
        {            
            _logger.info("预收的客户" + bean.getCustomerId() +"不存在,请确认操作");
            
            return;
        }

        StafferVSCustomerBean vs = stafferVSCustomerDAO.findByUnique(bean.getCustomerId());

        if (vs == null)
        {           
            _logger.info("客户当前和职员"+bean.getCustomerId()+"无挂靠关系,请确认操作");
            
            return;
        }

        // 以客户当前挂靠人为主
        StafferBean destStaffer = stafferDAO.find(vs.getStafferId());
        
        if (null == destStaffer)
        {
            _logger.info("移交目标业务员不存在"+ vs.getStafferId());
            
            return;
        }
        
        String refIds = refBuffer.toString();
        
        String [] refIdss = refIds.split(",");
        
        StringBuffer sb = new StringBuffer();
        
        for (int i = 0; i < refIdss.length; i++)
        {
            if (i == refIdss.length - 1)
                sb.append("'").append(refIdss[i]).append("'");
            else
                sb.append("'").append(refIdss[i]).append("'").append(",");
        }
        
        ConditionParse condition = new ConditionParse();
        
        condition.addWhereStr();
        
//        condition.addIntCondition("status", "=", FinanceConstant.INBILL_STATUS_NOREF);
        
        condition.addCondition(" and id in ("+ sb.toString() + ")");
        
        List<InBillBean> inBillList = inBillDAO.queryEntityBeansByCondition(condition);

        // 剔除预收中的挂靠人与客户当前对应的挂靠人是一样(这些不用移交)
        for (Iterator<InBillBean> iterator = inBillList.iterator(); iterator.hasNext();)
        {
            InBillBean inBillBean = iterator.next();
            
            if (inBillBean.getOwnerId().equals(vs.getStafferId()))
            {
                iterator.remove();
            }
        }
        
        if (ListTools.isEmptyOrNull(inBillList))
            return;
        
        billManager.changeBillListToTranWithoutTransactional(user, inBillList, destStaffer);
    }

    private String createOutBill(User user, List<OutBillBean> outBillList, BackPayApplyBean apply)
        throws MYException
    {
        StringBuffer refBuffer = new StringBuffer();

        for (OutBillBean outBill : outBillList)
        {
            if (apply.getType() == BackPayApplyConstant.TYPE_OUT)
            {
                // 自动生成付款单
                outBill.setDescription("销售退货付款:" + apply.getOutId() + ".备注:"
                                       + apply.getDescription());

                outBill.setStockId(apply.getOutId());
            }
            else
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
            }

            outBill.setLocationId(user.getLocationId());

            outBill.setLogTime(TimeTools.now());

            outBill.setType(FinanceConstant.OUTBILL_TYPE_OUTBACK);

            outBill.setOwnerId(apply.getStafferId());

            outBill.setStafferId(user.getStafferId());

            outBill.setProvideId(apply.getCustomerId());

            outBill.setLock(FinanceConstant.BILL_LOCK_YES);

            billManager.addOutBillBeanWithoutTransaction(user, outBill);

            refBuffer.append(outBill.getId()).append(',');
        }

        apply.setRefIds(apply.getRefIds() + refBuffer.toString());

        return outBillList.get(0).getId();
    }
    
    /**
     * 直接转预收
     * {@inheritDoc}
     */
    @Override
    public boolean backToPreWithOutTransaction(User user, BackPayApplyBean bean) throws MYException {

        handleOut(user, bean);
        
        // TAX_ADD 销售退款/预收退款
        Collection<BackPayApplyListener> listenerMapValues = this.listenerMapValues();

        for (BackPayApplyListener listener : listenerMapValues)
        {
            listener.onEndBackPayApplyBean(user, bean, new ArrayList<OutBillBean>());
        }
        
        return true;
    }
    
    /**
     * @return the backPayApplyDAO
     */
    public BackPayApplyDAO getBackPayApplyDAO()
    {
        return backPayApplyDAO;
    }

    /**
     * @param backPayApplyDAO
     *            the backPayApplyDAO to set
     */
    public void setBackPayApplyDAO(BackPayApplyDAO backPayApplyDAO)
    {
        this.backPayApplyDAO = backPayApplyDAO;
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
     * @return the outDAO
     */
    public OutDAO getOutDAO()
    {
        return outDAO;
    }

    /**
     * @param outDAO
     *            the outDAO to set
     */
    public void setOutDAO(OutDAO outDAO)
    {
        this.outDAO = outDAO;
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
     * @return the inBillDAO
     */
    public InBillDAO getInBillDAO()
    {
        return inBillDAO;
    }

    /**
     * @param inBillDAO
     *            the inBillDAO to set
     */
    public void setInBillDAO(InBillDAO inBillDAO)
    {
        this.inBillDAO = inBillDAO;
    }

    /**
     * @return the outBillDAO
     */
    public OutBillDAO getOutBillDAO()
    {
        return outBillDAO;
    }

    /**
     * @param outBillDAO
     *            the outBillDAO to set
     */
    public void setOutBillDAO(OutBillDAO outBillDAO)
    {
        this.outBillDAO = outBillDAO;
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
     * @return the outManager
     */
    public OutManager getOutManager()
    {
        return outManager;
    }

    /**
     * @param outManager
     *            the outManager to set
     */
    public void setOutManager(OutManager outManager)
    {
        this.outManager = outManager;
    }

    public StafferVSCustomerDAO getStafferVSCustomerDAO() {
        return stafferVSCustomerDAO;
    }

    public void setStafferVSCustomerDAO(StafferVSCustomerDAO stafferVSCustomerDAO) {
        this.stafferVSCustomerDAO = stafferVSCustomerDAO;
    }

    /**
	 * @return the customerMainDAO
	 */
	public CustomerMainDAO getCustomerMainDAO()
	{
		return customerMainDAO;
	}

	/**
	 * @param customerMainDAO the customerMainDAO to set
	 */
	public void setCustomerMainDAO(CustomerMainDAO customerMainDAO)
	{
		this.customerMainDAO = customerMainDAO;
	}

	public StafferDAO getStafferDAO() {
        return stafferDAO;
    }

    public void setStafferDAO(StafferDAO stafferDAO) {
        this.stafferDAO = stafferDAO;
    }

	public OutBalanceDAO getOutBalanceDAO()
	{
		return outBalanceDAO;
	}

	public void setOutBalanceDAO(OutBalanceDAO outBalanceDAO)
	{
		this.outBalanceDAO = outBalanceDAO;
	}

}
