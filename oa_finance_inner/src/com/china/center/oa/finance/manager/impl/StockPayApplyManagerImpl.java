/**
 * File Name: StockPayApplyManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-18<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.manager.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.bean.StockPayApplyBean;
import com.china.center.oa.finance.bean.StockPrePayApplyBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.constant.StockPayApplyConstant;
import com.china.center.oa.finance.dao.StockPayApplyDAO;
import com.china.center.oa.finance.dao.StockPayVSComposeDAO;
import com.china.center.oa.finance.dao.StockPayVSPreDAO;
import com.china.center.oa.finance.dao.StockPrePayApplyDAO;
import com.china.center.oa.finance.listener.StockPayApplyListener;
import com.china.center.oa.finance.manager.BillManager;
import com.china.center.oa.finance.manager.StockPayApplyManager;
import com.china.center.oa.finance.vs.StockPayVSComposeBean;
import com.china.center.oa.finance.vs.StockPayVSPreBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.SysConfigConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.oa.stock.bean.StockBean;
import com.china.center.oa.stock.dao.StockDAO;
import com.china.center.oa.stock.dao.StockItemDAO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * StockPayApplyManagerImpl
 * 
 * @author ZHUZHU
 * @version 2011-2-18
 * @see StockPayApplyManagerImpl
 * @since 3.0
 */
@Exceptional
public class StockPayApplyManagerImpl extends AbstractListenerManager<StockPayApplyListener> implements StockPayApplyManager
{
    private StockPayApplyDAO stockPayApplyDAO = null;

    private FlowLogDAO flowLogDAO = null;

    private BillManager billManager = null;

    private CommonDAO commonDAO = null;

    private StockDAO stockDAO = null;
    
    private StockItemDAO stockItemDAO = null;

    private ParameterDAO parameterDAO = null;

    private UserManager userManager = null;
    
    private StockPrePayApplyDAO stockPrePayApplyDAO = null;
    
    private StockPayVSPreDAO stockPayVSPreDAO = null;
    
    private StockPayVSComposeDAO stockPayVSComposeDAO = null;

    /**
     * default constructor
     */
    public StockPayApplyManagerImpl()
    {
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean submitStockPayApply(User user, String id, double payMoney, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        StockPayApplyBean apply = checkSubmit(id, payMoney);

        int preStatus = apply.getStatus();

        // 是否未完全付款
        if (payMoney != apply.getMoneys())
        {
            double last = apply.getMoneys() - payMoney;

            StockPayApplyBean lastApply = new StockPayApplyBean();

            BeanUtil.copyProperties(lastApply, apply);

            lastApply.setMoneys(last);

            lastApply.setStatus(StockPayApplyConstant.APPLY_STATUS_INIT);

            lastApply.setDescription(apply.getDescription() + ".分拆");

            lastApply.setLogTime(TimeTools.now());

            lastApply.setId(commonDAO.getSquenceString20());

            stockPayApplyDAO.saveEntityBean(lastApply);
        }

        apply.setStatus(getNextStatus(user, apply));

        apply.setMoneys(payMoney);

        stockPayApplyDAO.updateEntityBean(apply);

        saveFlowLog(user, preStatus, apply, reason, PublicConstant.OPRMODE_PASS);

        return true;
    }
    
    @Transactional(rollbackFor = MYException.class)
    public boolean submitStockPayApply(User user, String id, String reason, StockPayApplyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);
        
        double payMoney = bean.getMoneys();

        StockPayApplyBean apply = checkSubmit(id, payMoney);
        
        int isFinal = bean.getIsFinal();

        int preStatus = apply.getStatus();

        // 结束付款,实付金额可以大于应付 (2014.5.19 要求不能大于应付）
        if (isFinal == StockPayApplyConstant.APPLY_ISFINAL_YES)
        {
        	if (MathTools.compare(bean.getMoneys(), bean.getRealMoneys()) < 0)
        	{
        		throw new MYException("[结束付款]时，实付金额不能大于应付金额");
        	}
        	
        	// 不允许存在未审批结束的付款单. 
        	boolean isNotFinish = stockPayApplyDAO.isExistsNotFinish(id, apply.getRefId());
        	
        	if (isNotFinish){
        		throw new MYException("存在未审批结束的付款申请,本单不能标识为[结束付款]");
        	}
        }else{
        	double newPyaMoney = bean.getMoneys() - bean.getRealMoneys();
        	
        	if (MathTools.compare(newPyaMoney, 0) <= 0)
        	{
        		throw new MYException("完全付款，请勾选[结束付款]");
        	}
        	
            StockPayApplyBean lastApply = new StockPayApplyBean();

            BeanUtil.copyProperties(lastApply, apply);

            lastApply.setMoneys(newPyaMoney);
            
            lastApply.setRealMoneys(0);
            
            lastApply.setGoldPrice(0);
            
            lastApply.setSilverPrice(0);

            lastApply.setIsFinal(StockPayApplyConstant.APPLY_ISFINAL_YES);
            
            lastApply.setStatus(StockPayApplyConstant.APPLY_STATUS_INIT);

            lastApply.setDescription(apply.getDescription() + ".未结束付款的分拆");

            lastApply.setLogTime(TimeTools.now());

            lastApply.setId(commonDAO.getSquenceString20());

            stockPayApplyDAO.saveEntityBean(lastApply);
        }
        
        apply.setStatus(getNextStatus(user, apply));

        apply.setGoldPrice(bean.getGoldPrice());
        
        apply.setSilverPrice(bean.getSilverPrice());
        
        apply.setIsFinal(bean.getIsFinal());
        
        apply.setRealMoneys(bean.getRealMoneys());

        stockPayApplyDAO.updateEntityBean(apply);

        saveFlowLog(user, preStatus, apply, reason, PublicConstant.OPRMODE_PASS);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean composeStockPayApply(User user, List<String> idList)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, idList);

        if (idList.size() <= 1)
        {
            throw new MYException("超过两个申请才可以合并,请确认操作");
        }

        List<StockPayApplyBean> beanList = new ArrayList<StockPayApplyBean>();

        double total = 0.0d;

        String pid = "";

        StringBuffer stockBuffer = new StringBuffer();

        StringBuffer stockItemBuffer = new StringBuffer();

        StringBuffer idBuffer = new StringBuffer();

        int mtype = -1;
        for (String id : idList)
        {
            StockPayApplyBean bean = stockPayApplyDAO.find(id);

            if (bean == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            if (bean.getStatus() != StockPayApplyConstant.APPLY_STATUS_INIT)
            {
                throw new MYException("付款申请不再初始态,不能合并,请确认操作");
            }

            if (StringTools.isNullOrNone(pid))
            {
                pid = bean.getProvideId();
            }
            else
            {
                if ( !pid.equals(bean.getProvideId()))
                {
                    throw new MYException("供应商一致才能合并,请确认操作");
                }
            }

            StockBean stock = stockDAO.find(bean.getStockId());

            if (stock == null)
            {
                throw new MYException("只有采购付款才可以合并,请确认操作");
            }

            // 管理属性必须一致
            if (mtype == -1)
            {
                mtype = stock.getMtype();
            }
            else
            {
                if (mtype != stock.getMtype())
                {
                    throw new MYException("不同属性的产品不能合并申请付款,请确认操作");
                }
            }

            idBuffer
                .append(id)
                .append("/金额:")
                .append(MathTools.formatNum(bean.getMoneys()))
                .append(';')
                .append("<br>");
            
            stockBuffer.append(setStockUrl(bean.getStockId()))
            		.append(";/采购项:")
            		.append(bean.getStockItemId())
            		.append("/金额:")
            		.append(MathTools.formatNum(bean.getMoneys()))
            		.append(';')
            		.append("<br>");
            //stockItemBuffer.append().append(';');

            total += bean.getMoneys();

            beanList.add(bean);
            
        }
        
        StockPayApplyBean apply = new StockPayApplyBean();

        apply.setId(commonDAO.getSquenceString20());

        apply.setDescription("合并后系统生成付款申请:<br>" + idBuffer.toString() + "采购单:<br>"
                             + stockBuffer.toString());

        apply.setDutyId(beanList.get(0).getDutyId());

        apply.setInvoiceId(beanList.get(0).getInvoiceId());

        apply.setLocationId(beanList.get(0).getLocationId());

        apply.setLogTime(TimeTools.now());

        apply.setMoneys(total);

        // 今天
        apply.setPayDate(TimeTools.now_short());

        apply.setProvideId(beanList.get(0).getProvideId());

        apply.setStatus(StockPayApplyConstant.APPLY_STATUS_INIT);

        apply.setStockId("");

        apply.setStockItemId("");

        apply.setStafferId(user.getStafferId());
        
        apply.setRefId(apply.getId());

        stockPayApplyDAO.saveEntityBean(apply);

        // 自动结束掉
        for (StockPayApplyBean each : beanList)
        {
            each.setStatus(StockPayApplyConstant.APPLY_STATUS_END);

            stockPayApplyDAO.updateEntityBean(each);

            saveFlowLog(user, StockPayApplyConstant.APPLY_STATUS_INIT, each, "自动合并付款单",
                PublicConstant.OPRMODE_EXCEPTION);
            
            StockPayVSComposeBean vsComp = new StockPayVSComposeBean();
            
            vsComp.setComposeId(apply.getId());
            vsComp.setStockPayApplyId(each.getId());
            
            stockPayVSComposeDAO.saveEntityBean(vsComp);
        }

        return true;
    }
    
    private static String setStockUrl(String stockId)
    {
    	return "<a href='../stock/stock.do?method=findStock&id=" + stockId + "'>" + stockId + "</a>";
    }
    
    private void saveFlowLog(User user, int preStatus, StockPayApplyBean apply, String reason,
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
    
    private void saveFlowLog(User user, int preStatus, StockPrePayApplyBean apply, String reason,
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
     * checkSubmit
     * 
     * @param id
     * @param payMoney
     * @return
     * @throws MYException
     */
    private StockPayApplyBean checkSubmit(String id, double payMoney)
        throws MYException
    {
        StockPayApplyBean apply = stockPayApplyDAO.find(id);

        if (apply == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( ! (apply.getStatus() == StockPayApplyConstant.APPLY_STATUS_INIT || apply.getStatus() == StockPayApplyConstant.APPLY_STATUS_REJECT))
        {
            throw new MYException("状态不能提交,请确认操作");
        }

        if (TimeTools.now_short().compareTo(apply.getPayDate()) < 0)
        {
            throw new MYException("付款的最早时间还没有到,请确认操作");
        }

//        if (MathTools.compare(apply.getMoneys(), payMoney) < 0)
//        {
//            throw new MYException("申请付款金额溢出,最大付款金额[%.2f],请确认操作", apply.getMoneys());
//        }

        return apply;
    }
    
    /**
     * 
     * @param id
     * @param payMoney
     * @return
     * @throws MYException
     */
    private StockPrePayApplyBean checkSubmit2(String id, double payMoney)
    throws MYException
	{
	    StockPrePayApplyBean apply = stockPrePayApplyDAO.find(id);
	
	    if (apply == null)
	    {
	        throw new MYException("数据错误,请确认操作");
	    }
	
	    if ( ! (apply.getStatus() == StockPayApplyConstant.APPLY_STATUS_INIT || apply.getStatus() == StockPayApplyConstant.APPLY_STATUS_REJECT))
	    {
	        throw new MYException("状态不能提交,请确认操作");
	    }
	
	    if (TimeTools.now_short().compareTo(apply.getPayDate()) < 0)
	    {
	        throw new MYException("付款的最早时间还没有到,请确认操作");
	    }
	
	//    if (MathTools.compare(apply.getMoneys(), payMoney) < 0)
	//    {
	//        throw new MYException("申请付款金额溢出,最大付款金额[%.2f],请确认操作", apply.getMoneys());
	//    }
	
	    return apply;
	}

    /**
     * checkPassByCEO
     * 
     * @param id
     * @param payMoney
     * @return
     * @throws MYException
     */
    private StockPayApplyBean checkPassByCEO(String id)
        throws MYException
    {
        StockPayApplyBean apply = stockPayApplyDAO.find(id);

        if (apply == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (TimeTools.now_short().compareTo(apply.getPayDate()) < 0)
        {
            throw new MYException("付款的最早时间还没有到,请确认操作");
        }

        return apply;
    }
    
    private StockPrePayApplyBean checkPassByCEO2(String id)
    throws MYException
	{
	    StockPrePayApplyBean apply = stockPrePayApplyDAO.find(id);
	
	    if (apply == null)
	    {
	        throw new MYException("数据错误,请确认操作");
	    }
	
	   /* if (TimeTools.now_short().compareTo(apply.getPayDate()) < 0)
	    {
	        throw new MYException("付款的最早时间还没有到,请确认操作");
	    }*/
	
	    return apply;
	}

    private StockPayApplyBean checkEndPass(String id)
        throws MYException
    {
        StockPayApplyBean apply = stockPayApplyDAO.find(id);

        if (apply == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (apply.getStatus() != StockPayApplyConstant.APPLY_STATUS_SEC)
        {
            throw new MYException("状态不能通过,请确认操作");
        }

        if (TimeTools.now_short().compareTo(apply.getPayDate()) < 0)
        {
            throw new MYException("付款的最早时间还没有到,请确认操作");
        }

        return apply;
    }
    
    private StockPrePayApplyBean checkEndPass2(String id)
    throws MYException
	{
	    StockPrePayApplyBean apply = stockPrePayApplyDAO.find(id);
	
	    if (apply == null)
	    {
	        throw new MYException("数据错误,请确认操作");
	    }
	
	    if (apply.getStatus() != StockPayApplyConstant.APPLY_STATUS_SEC)
	    {
	        throw new MYException("状态不能通过,请确认操作");
	    }
	
	   /* if (TimeTools.now_short().compareTo(apply.getPayDate()) < 0)
	    {
	        throw new MYException("付款的最早时间还没有到,请确认操作");
	    }*/
	
	    return apply;
	}

    private StockPayApplyBean checkReject(String id)
        throws MYException
    {
        StockPayApplyBean apply = stockPayApplyDAO.find(id);

        if (apply == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (TimeTools.now_short().compareTo(apply.getPayDate()) < 0)
        {
            throw new MYException("付款的最早时间还没有到,请确认操作");
        }

        return apply;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean passStockPayByCEO(User user, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        StockPayApplyBean apply = checkPassByCEO(id);

        int oldStatus = apply.getStatus();

        apply.setStatus(getNextStatus(user, apply));

        stockPayApplyDAO.updateEntityBean(apply);

        saveFlowLog(user, oldStatus, apply, reason, PublicConstant.OPRMODE_PASS);

        return true;
    }

    /**
     * getNextStatus
     * 
     * @param apply
     * @return
     * @throws MYException
     */
    private int getNextStatus(User user, StockPayApplyBean apply)
        throws MYException
    {
        int next = StockPayApplyConstant.APPLY_STATUS_SAIL;

        if (apply.getStatus() == StockPayApplyConstant.APPLY_STATUS_INIT)
        {
            if ( !userManager.containAuth(user.getId(), AuthConstant.STOCK_PAY_APPLY))
            {
                throw new MYException("没有操作权限");
            }

            next = StockPayApplyConstant.APPLY_STATUS_SAIL;
        }

//        if (apply.getStatus() == StockPayApplyConstant.APPLY_STATUS_SAIL)
//        {
//            if ( !userManager.containAuth(user.getId(), AuthConstant.STOCK_PAY_SAIL))
//            {
//                throw new MYException("没有操作权限");
//            }
//
//            next = StockPayApplyConstant.APPLY_STATUS_CHECK;
//        }

        if (apply.getStatus() == StockPayApplyConstant.APPLY_STATUS_SAIL)
        {
            if ( !userManager.containAuth(user.getId(), AuthConstant.STOCK_PAY_SAIL))
            {
                throw new MYException("没有操作权限");
            }

            next = StockPayApplyConstant.APPLY_STATUS_CFO;
        }

        if (apply.getStatus() == StockPayApplyConstant.APPLY_STATUS_CFO)
        {
            if ( !userManager.containAuth(user.getId(), AuthConstant.STOCK_PAY_CFO))
            {
                throw new MYException("没有操作权限");
            }

            int max = parameterDAO.getInt(SysConfigConstant.STOCK_MAX_SINGLE_MONEY);

            if (apply.getMoneys() >= max)
            {
                next = StockPayApplyConstant.APPLY_STATUS_CEO;
            }
            else
            {
                next = StockPayApplyConstant.APPLY_STATUS_SEC;
            }
        }

        if (apply.getStatus() == StockPayApplyConstant.APPLY_STATUS_CEO)
        {
            if ( !userManager.containAuth(user.getId(), AuthConstant.STOCK_PAY_CEO))
            {
                throw new MYException("没有操作权限");
            }

            next = StockPayApplyConstant.APPLY_STATUS_SEC;
        }

        return next;
    }
    
    private int getNextStatus2(User user, StockPrePayApplyBean apply)
    throws MYException
	{
	    int next = StockPayApplyConstant.APPLY_STATUS_SAIL;
	
	    if (apply.getStatus() == StockPayApplyConstant.APPLY_STATUS_INIT)
	    {
	        if ( !userManager.containAuth(user.getId(), AuthConstant.STOCK_PAY_APPLY))
	        {
	            throw new MYException("没有操作权限");
	        }
	
	        next = StockPayApplyConstant.APPLY_STATUS_SAIL;
	    }
	
	//    if (apply.getStatus() == StockPayApplyConstant.APPLY_STATUS_SAIL)
	//    {
	//        if ( !userManager.containAuth(user.getId(), AuthConstant.STOCK_PAY_SAIL))
	//        {
	//            throw new MYException("没有操作权限");
	//        }
	//
	//        next = StockPayApplyConstant.APPLY_STATUS_CHECK;
	//    }
	
	    if (apply.getStatus() == StockPayApplyConstant.APPLY_STATUS_SAIL)
	    {
	        if ( !userManager.containAuth(user.getId(), AuthConstant.STOCK_PAY_SAIL))
	        {
	            throw new MYException("没有操作权限");
	        }
	
	        next = StockPayApplyConstant.APPLY_STATUS_CFO;
	    }
	
	    if (apply.getStatus() == StockPayApplyConstant.APPLY_STATUS_CFO)
	    {
	        if ( !userManager.containAuth(user.getId(), AuthConstant.STOCK_PAY_CFO))
	        {
	            throw new MYException("没有操作权限");
	        }
	
	        int max = parameterDAO.getInt(SysConfigConstant.STOCK_MAX_SINGLE_MONEY);
	
	        if (apply.getMoneys() >= max)
	        {
	            next = StockPayApplyConstant.APPLY_STATUS_CEO;
	        }
	        else
	        {
	            next = StockPayApplyConstant.APPLY_STATUS_SEC;
	        }
	    }
	
	    if (apply.getStatus() == StockPayApplyConstant.APPLY_STATUS_CEO)
	    {
	        if ( !userManager.containAuth(user.getId(), AuthConstant.STOCK_PAY_CEO))
	        {
	            throw new MYException("没有操作权限");
	        }
	
	        next = StockPayApplyConstant.APPLY_STATUS_SEC;
	    }
	
	    return next;
	}

    @Transactional(rollbackFor = MYException.class)
    public boolean rejectStockPayApply(User user, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        StockPayApplyBean apply = checkReject(id);

        int preStatus = apply.getStatus();

        apply.setStatus(StockPayApplyConstant.APPLY_STATUS_REJECT);

        stockPayApplyDAO.updateEntityBean(apply);

        saveFlowLog(user, preStatus, apply, reason, PublicConstant.OPRMODE_REJECT);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean closeStockPayApply(User user, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        StockPayApplyBean apply = checkReject(id);

        int preStatus = apply.getStatus();

        apply.setStatus(StockPayApplyConstant.APPLY_STATUS_END);

        stockPayApplyDAO.updateEntityBean(apply);

        saveFlowLog(user, preStatus, apply, reason, PublicConstant.OPRMODE_EXCEPTION);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean endStockPayBySEC(User user, String id, String reason,
                                    List<OutBillBean> outBillList)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id, outBillList);

        StockPayApplyBean apply = checkEndPass(id);

        StringBuffer sb = new StringBuffer();

        double totla = 0.0d;
        // 可以生成多个
        for (OutBillBean outBill : outBillList)
        {
            // 生成付款
            createOutBill(user, outBill, apply);

            sb.append(outBill.getId()).append(';');

            totla += outBill.getMoneys();
        }

        if ( !MathTools.equal(apply.getRealMoneys(), totla))
        {
            throw new MYException("付款金额不正确,应该付款[%.2f],会计填写金额[[%.2f]]", apply.getRealMoneys(), totla);
        }

        apply.setStatus(StockPayApplyConstant.APPLY_STATUS_END);

        apply.setInBillId(sb.toString());

        // 结束申请流程
        stockPayApplyDAO.updateEntityBean(apply);

        // TAX_ADD 采购付款--会计付款
        Collection<StockPayApplyListener> listenerMapValues = this.listenerMapValues();

        for (StockPayApplyListener listener : listenerMapValues)
        {
            listener.onEndStockPayBySEC(user, apply, outBillList);
        }

        saveFlowLog(user, StockPayApplyConstant.APPLY_STATUS_SEC, apply, reason,
            PublicConstant.OPRMODE_PASS);

        return true;
    }

    /**
     * createOutBill
     * 
     * @param user
     * @param outBill
     * @param apply
     * @throws MYException
     */
    private void createOutBill(User user, OutBillBean outBill, StockPayApplyBean apply)
        throws MYException
    {
        // 自动生成付款单
        outBill.setDescription("采购付款申请:" + apply.getId());

        outBill.setInvoiceId(apply.getInvoiceId());

        outBill.setLocationId(apply.getLocationId());

        outBill.setLogTime(TimeTools.now());

        outBill.setType(FinanceConstant.OUTBILL_TYPE_STOCK);

        outBill.setOwnerId(apply.getStafferId());

        outBill.setStafferId(user.getStafferId());

        outBill.setProvideId(apply.getProvideId());

        outBill.setStockId(apply.getStockId());

        outBill.setStockItemId(apply.getStockItemId());

        billManager.addOutBillBeanWithoutTransaction(user, outBill);
    }

    private void createOutBill2(User user, OutBillBean outBill, StockPrePayApplyBean apply)
    throws MYException
	{
	    // 自动生成付款单
	    outBill.setDescription("采购付款申请:" + apply.getId());
	
	    outBill.setInvoiceId(apply.getInvoiceId());
	
	    outBill.setLocationId("");
	
	    outBill.setLogTime(TimeTools.now());
	
	    outBill.setType(FinanceConstant.OUTBILL_TYPE_STOCKPREPAY);
	
	    outBill.setOwnerId(apply.getStafferId());
	
	    outBill.setStafferId(user.getStafferId());
	
	    outBill.setProvideId(apply.getProviderId());
	
	    outBill.setStockId("");
	
	    outBill.setStockItemId("");
	
	    billManager.addOutBillBeanWithoutTransaction(user, outBill);
	}
    
    @Override
    @Transactional(rollbackFor = MYException.class)
	public boolean addStockPrePayApply(User user, StockPrePayApplyBean bean)
			throws MYException
	{
    	JudgeTools.judgeParameterIsNull(user, bean);
    	
    	bean.setId(commonDAO.getSquenceString20());
    	
    	bean.setStatus(StockPayApplyConstant.APPLY_STATUS_SAIL);
    	
    	stockPrePayApplyDAO.saveEntityBean(bean);
    	
    	saveFlowLog(user, StockPayApplyConstant.APPLY_STATUS_INIT, bean, "提交", PublicConstant.OPRMODE_PASS);
    	
		return true;
	}

	@Override
	@Transactional(rollbackFor = MYException.class)
	public boolean updateStockPrePayApply(User user, StockPrePayApplyBean bean)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, bean, bean.getId());
		
		StockPrePayApplyBean oldBean = stockPrePayApplyDAO.find(bean.getId());
		
		if (null == oldBean)
		{
			throw new MYException("数据错误");
		}
		
		if (oldBean.getStatus() != StockPayApplyConstant.APPLY_STATUS_INIT
				&& oldBean.getStatus() != StockPayApplyConstant.APPLY_STATUS_REJECT)
		{
			throw new MYException("只能修改初始与驳回态");
		}
		
		int preStatus =oldBean.getStatus();
		
		bean.setStatus(StockPayApplyConstant.APPLY_STATUS_SAIL);
		
		stockPrePayApplyDAO.updateEntityBean(bean);
		
		saveFlowLog(user, preStatus, bean, "提交", PublicConstant.OPRMODE_PASS);
		
		return true;
	}

	@Override
	@Transactional(rollbackFor = MYException.class)
	public boolean deleteStockPrePayApply(User user, String id)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, id);
		
		StockPrePayApplyBean oldBean = stockPrePayApplyDAO.find(id);
		
		if (null != oldBean)
		{
			if (oldBean.getStatus() != StockPayApplyConstant.APPLY_STATUS_INIT
					&& oldBean.getStatus() != StockPayApplyConstant.APPLY_STATUS_REJECT)
			{
				throw new MYException("只能删除初始与驳回态");
			}
		}
		
		stockPrePayApplyDAO.deleteEntityBean(id);
		
		return true;
	}

	@Override
	@Transactional(rollbackFor = MYException.class)
	public boolean passStockPrePayByCEO(User user, String id, String reason)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, id);

        StockPrePayApplyBean apply = checkPassByCEO2(id);

        int oldStatus = apply.getStatus();

        apply.setStatus(getNextStatus2(user, apply));

        stockPrePayApplyDAO.updateEntityBean(apply);

        saveFlowLog(user, oldStatus, apply, reason, PublicConstant.OPRMODE_PASS);

        return true;
    }

	@Override
	@Transactional(rollbackFor = MYException.class)
	public boolean endStockPrePayBySEC(User user, String id, String reason,
			List<OutBillBean> outBillList) throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, id, outBillList);

        StockPrePayApplyBean apply = checkEndPass2(id);

        StringBuffer sb = new StringBuffer();

        double totla = 0.0d;
        // 可以生成多个
        for (OutBillBean outBill : outBillList)
        {
            // 生成付款
            createOutBill2(user, outBill, apply);

            sb.append(outBill.getId()).append(';');

            totla += outBill.getMoneys();
        }

        if ( !MathTools.equal(apply.getMoneys(), totla))
        {
            throw new MYException("付款金额不正确,应该付款[%.2f],会计填写金额[[%.2f]]", apply.getMoneys(), totla);
        }

        apply.setStatus(StockPayApplyConstant.APPLY_STATUS_END);

        apply.setInBillId(sb.toString());

        // 结束申请流程
        stockPrePayApplyDAO.updateEntityBean(apply);

        // TAX_ADD 采购付款--会计付款
        Collection<StockPayApplyListener> listenerMapValues = this.listenerMapValues();

        for (StockPayApplyListener listener : listenerMapValues)
        {
            listener.onEndStockPrePayBySEC(user, apply, outBillList);
        }

        saveFlowLog(user, StockPayApplyConstant.APPLY_STATUS_SEC, apply, reason,
            PublicConstant.OPRMODE_PASS);

        return true;
    }

	@Override
	@Transactional(rollbackFor = MYException.class)
	public boolean rejectStockPrePayApply(User user, String id, String reason)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, id);

        StockPrePayApplyBean apply = stockPrePayApplyDAO.find(id);

        if (apply == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        int preStatus = apply.getStatus();

        apply.setStatus(StockPayApplyConstant.APPLY_STATUS_REJECT);

        stockPrePayApplyDAO.updateEntityBean(apply);

        saveFlowLog(user, preStatus, apply, reason, PublicConstant.OPRMODE_REJECT);

        return true;
    }
    
	@Override
	@Transactional(rollbackFor = MYException.class)
	public String refStockPrePay(User user, List<StockPayVSPreBean> vsList)
			throws MYException
	{
		// 只有一个付款申请单号
		String stockPayApplyId = vsList.get(0).getPayApplyId(); 
		
		if (StringTools.isNullOrNone(stockPayApplyId))
		{
			throw new MYException("未关联采购付款申请单");
		}
		
		StockPayApplyBean stockPayApplyBean = stockPayApplyDAO.find(stockPayApplyId);
		
		if (null == stockPayApplyBean)
		{
			throw new MYException("数据错误，请重新操作");
		}
		
		if (stockPayApplyBean.getStatus() != StockPayApplyConstant.APPLY_STATUS_SEC)
		{
			throw new MYException("状态错误，不是待财务支付");
		}
		
		double total = 0.0d;
		
		for (StockPayVSPreBean each : vsList)
		{
			StockPrePayApplyBean pre = stockPrePayApplyDAO.find(each.getPrePayId());
			
			each.setMoneys(pre.getMoneys() - pre.getRealMoneys());
			
			total += each.getMoneys();
		}
		
		double needMoneys = stockPayApplyBean.getRealMoneys();
		
		// 如果是合成付款单, 预付款金额要求足够大,因为如果合成付款只预付部分,则合成单要拆单, 但合成单是由多个采购付款申请单组合而成, 
		// 这样后面生成财务标记无法正确匹配到原始付款申请单.
		if (StringTools.isNullOrNone(stockPayApplyBean.getStockId()))
		{
			if (MathTools.compare(needMoneys, total) > 0)
			{
				throw new MYException("合成单预付款支付时，须全额支付.");
			}
		}
		
		// 预付不足够全部支付 采购付款申请
		if (MathTools.compare(needMoneys, total) > 0)
		{
			// 采购付款申请一分为二
			StockPayApplyBean newBean = new StockPayApplyBean();
			
			BeanUtil.copyProperties(newBean, stockPayApplyBean);
			
			newBean.setId(commonDAO.getSquenceString20());
			newBean.setMoneys(needMoneys - total);
			newBean.setRealMoneys(0);
			newBean.setDescription(newBean.getDescription() + 
					",预付款勾应付时,预付不足,采购付款申请单分拆, 原单：" + stockPayApplyBean.getId() + ",新单:" + newBean.getId() );
			
			stockPayApplyDAO.saveEntityBean(newBean);
			
			stockPayApplyId = newBean.getId();
			
			saveFlowLog(user, StockPayApplyConstant.APPLY_STATUS_SEC, newBean, "预付不足,采购付款申请单分拆, 原单：" + stockPayApplyBean.getId(),
		            PublicConstant.OPRMODE_PASS);
			
			// 原采购付款申请单置为结束
			stockPayApplyBean.setRealMoneys(total);
			
			stockPayApplyBean.setStatus(StockPayApplyConstant.APPLY_STATUS_END);
			
			stockPayApplyDAO.updateEntityBean(stockPayApplyBean);
			
			saveFlowLog(user, StockPayApplyConstant.APPLY_STATUS_SEC, stockPayApplyBean, "预付款支付",
		            PublicConstant.OPRMODE_PASS);
			
			// 
			for (StockPayVSPreBean each : vsList)
			{
				StockPrePayApplyBean preBean = stockPrePayApplyDAO.find(each.getPrePayId());
				
				preBean.setRealMoneys(preBean.getRealMoneys() + each.getMoneys());
				
				if (preBean.getRealMoneys() > preBean.getMoneys())
				{
					throw new MYException("数据溢出");
				}
				
				stockPrePayApplyDAO.updateEntityBean(preBean);
				
				each.setStatus(1);
			}
			
			stockPayVSPreDAO.saveAllEntityBeans(vsList);
			
		}else{
			
			// 原采购付款申请单置为结束
			//stockPayApplyBean.setRealMoneys(stockPayApplyBean.getMoneys());
			
			stockPayApplyBean.setStatus(StockPayApplyConstant.APPLY_STATUS_END);
			
			stockPayApplyDAO.updateEntityBean(stockPayApplyBean);
			
			saveFlowLog(user, StockPayApplyConstant.APPLY_STATUS_SEC, stockPayApplyBean, "预付款支付",
		            PublicConstant.OPRMODE_PASS);
			
			for (StockPayVSPreBean each : vsList)
			{
				StockPrePayApplyBean preBean = stockPrePayApplyDAO.find(each.getPrePayId());
				
				if (needMoneys <= each.getMoneys())
				{
					preBean.setRealMoneys(preBean.getRealMoneys() + needMoneys);
					
					if (preBean.getRealMoneys() > preBean.getMoneys())
					{
						throw new MYException("数据溢出");
					}
					
					each.setMoneys(needMoneys);
					
					stockPrePayApplyDAO.updateEntityBean(preBean);
					
					needMoneys = 0;
					
					each.setStatus(1);
					
					break;
					
				}else
				{
					preBean.setRealMoneys(preBean.getRealMoneys() + each.getMoneys());
					
					if (preBean.getRealMoneys() > preBean.getMoneys())
					{
						throw new MYException("数据溢出");
					}
					
					needMoneys -= each.getMoneys();
					
					each.setStatus(1);
					
					if (needMoneys == 0)
					{
						break;
					}
				}
			}
			
			stockPayVSPreDAO.saveAllEntityBeans(vsList);
		}
		
		// 结束的采购付款,生成财务标记数据
        Collection<StockPayApplyListener> listenerMapValues = this.listenerMapValues();

        for (StockPayApplyListener listener : listenerMapValues)
        {
            listener.onEndRefStockPayBySEC(user, stockPayApplyBean);
        }
		
		return stockPayApplyId;
	}
	
    /**
     * @return the stockPayApplyDAO
     */
    public StockPayApplyDAO getStockPayApplyDAO()
    {
        return stockPayApplyDAO;
    }

    /**
     * @param stockPayApplyDAO
     *            the stockPayApplyDAO to set
     */
    public void setStockPayApplyDAO(StockPayApplyDAO stockPayApplyDAO)
    {
        this.stockPayApplyDAO = stockPayApplyDAO;
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
     * @return the parameterDAO
     */
    public ParameterDAO getParameterDAO()
    {
        return parameterDAO;
    }

    /**
     * @param parameterDAO
     *            the parameterDAO to set
     */
    public void setParameterDAO(ParameterDAO parameterDAO)
    {
        this.parameterDAO = parameterDAO;
    }

    /**
     * @return the userManager
     */
    public UserManager getUserManager()
    {
        return userManager;
    }

    /**
     * @param userManager
     *            the userManager to set
     */
    public void setUserManager(UserManager userManager)
    {
        this.userManager = userManager;
    }

    /**
     * @return the stockDAO
     */
    public StockDAO getStockDAO()
    {
        return stockDAO;
    }

    /**
     * @param stockDAO
     *            the stockDAO to set
     */
    public void setStockDAO(StockDAO stockDAO)
    {
        this.stockDAO = stockDAO;
    }

	/**
	 * @return the stockItemDAO
	 */
	public StockItemDAO getStockItemDAO()
	{
		return stockItemDAO;
	}

	/**
	 * @param stockItemDAO the stockItemDAO to set
	 */
	public void setStockItemDAO(StockItemDAO stockItemDAO)
	{
		this.stockItemDAO = stockItemDAO;
	}

	/**
	 * @return the stockPrePayApplyDAO
	 */
	public StockPrePayApplyDAO getStockPrePayApplyDAO()
	{
		return stockPrePayApplyDAO;
	}

	/**
	 * @param stockPrePayApplyDAO the stockPrePayApplyDAO to set
	 */
	public void setStockPrePayApplyDAO(StockPrePayApplyDAO stockPrePayApplyDAO)
	{
		this.stockPrePayApplyDAO = stockPrePayApplyDAO;
	}

	/**
	 * @return the stockPayVSPreDAO
	 */
	public StockPayVSPreDAO getStockPayVSPreDAO()
	{
		return stockPayVSPreDAO;
	}

	/**
	 * @param stockPayVSPreDAO the stockPayVSPreDAO to set
	 */
	public void setStockPayVSPreDAO(StockPayVSPreDAO stockPayVSPreDAO)
	{
		this.stockPayVSPreDAO = stockPayVSPreDAO;
	}

	/**
	 * @return the stockPayVSComposeDAO
	 */
	public StockPayVSComposeDAO getStockPayVSComposeDAO()
	{
		return stockPayVSComposeDAO;
	}

	/**
	 * @param stockPayVSComposeDAO the stockPayVSComposeDAO to set
	 */
	public void setStockPayVSComposeDAO(StockPayVSComposeDAO stockPayVSComposeDAO)
	{
		this.stockPayVSComposeDAO = stockPayVSComposeDAO;
	}
}
