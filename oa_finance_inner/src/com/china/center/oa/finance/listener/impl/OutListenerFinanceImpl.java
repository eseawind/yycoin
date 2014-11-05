/**
 * File Name: OutListenerFinanceImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.listener.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.finance.bean.BackPayApplyBean;
import com.china.center.oa.finance.bean.FinanceTcpApproveBean;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.bean.InvoiceinsItemBean;
import com.china.center.oa.finance.bean.PaymentApplyBean;
import com.china.center.oa.finance.bean.PreInvoiceApplyBean;
import com.china.center.oa.finance.bean.PreInvoiceVSOutBean;
import com.china.center.oa.finance.constant.BackPayApplyConstant;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.FinanceTcpApproveDAO;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.finance.dao.InvoiceinsDAO;
import com.china.center.oa.finance.dao.InvoiceinsItemDAO;
import com.china.center.oa.finance.dao.OutBillDAO;
import com.china.center.oa.finance.dao.PaymentVSOutDAO;
import com.china.center.oa.finance.dao.PreInvoiceApplyDAO;
import com.china.center.oa.finance.dao.PreInvoiceVSOutDAO;
import com.china.center.oa.finance.helper.BillHelper;
import com.china.center.oa.finance.listener.BillListener;
import com.china.center.oa.finance.manager.BackPayApplyManager;
import com.china.center.oa.finance.manager.BillManager;
import com.china.center.oa.finance.manager.BillOutManager;
import com.china.center.oa.finance.manager.PaymentApplyManager;
import com.china.center.oa.finance.vo.InBillVO;
import com.china.center.oa.finance.vs.PaymentVSOutBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PluginNameConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.wrap.ResultBean;
import com.china.center.oa.sail.bean.BaseBalanceBean;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.OutBalanceBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.BaseBalanceDAO;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.OutBalanceDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.listener.OutListener;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * OutListenerFinanceImpl
 * 
 * @author ZHUZHU
 * @version 2011-1-9
 * @see OutListenerFinanceImpl
 * @since 3.0
 */
public class OutListenerFinanceImpl extends AbstractListenerManager<BillListener> implements OutListener,BillOutManager
{
	private final Log _logger = LogFactory.getLog(getClass());
	
    private InBillDAO inBillDAO = null;

    private OutBillDAO outBillDAO = null;

    private BillManager billManager = null;

    private CommonDAO commonDAO = null;

    private OutDAO outDAO = null;

    private BaseDAO baseDAO = null;

    private OutBalanceDAO outBalanceDAO = null;
    
    private BaseBalanceDAO baseBalanceDAO = null;

    private BackPayApplyManager backPayApplyManager = null;
    
    private PaymentApplyManager paymentApplyManager = null;
    
    private PaymentVSOutDAO paymentVSOutDAO = null; 
    
    private PreInvoiceApplyDAO preInvoiceApplyDAO = null;
    
    private PreInvoiceVSOutDAO preInvoiceVSOutDAO = null;
    
    private FinanceTcpApproveDAO financeTcpApproveDAO = null;
    
    private FlowLogDAO flowLogDAO = null;
    
    private InvoiceinsDAO invoiceinsDAO = null;

    private InvoiceinsItemDAO invoiceinsItemDAO = null;
    
    /**
     * default constructor
     */
    public OutListenerFinanceImpl()
    {
    }

    public void onPass(User user, OutBean bean)
        throws MYException
    {
        // 只监听销售行为哦
        if (bean.getType() != OutConstant.OUT_TYPE_OUTBILL)
        {
            return;
        }

        // 待回款状态 且是款到发货
        if (bean.getStatus() == OutConstant.STATUS_PASS
            && bean.getReserve3() == OutConstant.OUT_SAIL_TYPE_MONEY)
        {
            List<InBillBean> billList = inBillDAO.queryEntityBeansByFK(bean.getFullId());

            if (billList.size() > 0)
            {
                for (InBillBean inBillBean : billList)
                {
                    // 固化收款单和销售单的关联关系
                    inBillBean.setStatus(FinanceConstant.INBILL_STATUS_PAYMENTS);
                }

                inBillDAO.updateAllEntityBeans(billList);
            }

            // 已经支付的
            double hasPay = inBillDAO.sumByOutId(bean.getFullId());

            // 再次更新已经支付的金额
            outDAO.updateHadPay(bean.getFullId(), hasPay);
        }
        
        // 同步更新未拆分到具体批次前就开票的明细（规定：这样销售单须一次性开完发票），在此借用 forceBuyType 字段值 999 来表示发生过拆分
        if (bean.getForceBuyType() == 999) {
        	// 检查该销售单是否已开票
        	if (bean.getInvoiceStatus() == OutConstant.INVOICESTATUS_END) {
        		List<InvoiceinsItemBean> insItemList = invoiceinsItemDAO.queryEntityBeansByCondition("where InvoiceinsItemBean.outId = ?", bean.getFullId());
            	
            	if (!ListTools.isEmptyOrNull(insItemList)) {
            		
            		InvoiceinsItemBean template = insItemList.get(0);
            		
            		String insId = template.getParentId();
            		
            		for (InvoiceinsItemBean each : insItemList) {
            			invoiceinsItemDAO.deleteEntityBean(each.getId());
            		}
            		
            		// 生成新的
            		List<BaseBean> bList = baseDAO.queryEntityBeansByFK(bean.getFullId());
            		
            		List<InvoiceinsItemBean> newList = new ArrayList<InvoiceinsItemBean>();
            		
            		for (BaseBean each : bList) {
            			InvoiceinsItemBean newitem = new InvoiceinsItemBean();
            			
            			BeanUtil.copyProperties(newitem, template);
            			newitem.setId(commonDAO.getSquenceString20());
            			newitem.setParentId(insId);
            			newitem.setAmount(each.getAmount());
            			newitem.setPrice(each.getPrice());
            			newitem.setMoneys(each.getValue());
            			newitem.setOutId(each.getOutId());
            			newitem.setBaseId(each.getId());
            			newitem.setProductId(each.getProductId());
            			newitem.setCostPrice(each.getCostPrice());
            			
            			newList.add(newitem);
            		}
            		
            		invoiceinsItemDAO.saveAllEntityBeans(newList);
            	}
        	}
        }
    }

    /**
     * CORE 验证回款的核心逻辑
     */
    public ResultBean onHadPay(User user, OutBean bean)
    {
        ResultBean result = new ResultBean();

        result.setResult(0);
        
        // 非销售单没有回款
        if (bean.getType() != OutConstant.OUT_TYPE_OUTBILL)
        {
            return result;
        }

        // 赠送没有金额
        if (bean.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT)
        {
            return result;
        }

        // 个人领样 与  巡展 按个人领样处理
        if (bean.getOutType() == OutConstant.OUTTYPE_OUT_SWATCH
        		|| bean.getOutType() == OutConstant.OUTTYPE_OUT_SHOW
        		|| bean.getOutType() == OutConstant.OUTTYPE_OUT_SHOWSWATCH)
        {
            return processSwithPay(bean.getFullId());
        }

        // 已经收到客户的金额
        double hasPay = inBillDAO.sumByOutId(bean.getFullId());
        
        // 历史坏账勾款的金额  - add by f 2012.7.31
        double hasBadDebtsPay = inBillDAO.sumBadDebtsByOutId(bean.getFullId());
        
        // 本单的坏账与坏账勾款相加
        hasBadDebtsPay += bean.getBadDebts(); 

        List<InBillBean> inList = inBillDAO.queryEntityBeansByFK(bean.getFullId());

        StringBuffer inBuffer = new StringBuffer();

        for (InBillBean inBillBean : inList)
        {
            inBuffer.append(inBillBean.getId()).append(';');
        }

        String inString = inBuffer.toString();
        
        if (inList.size() > 50)
        {
        	_logger.info("销售单关联收款单:" + inBuffer.toString());
        	
        	inString = "关联收款单太多，在此省略";
        }
        
        // 已经退货付款的金额
        double hasdOut = outBillDAO.sumByRefId(bean.getFullId());

        // 委托的金额
        double balancePay = 0.0d;

        // 退货实物的价值
        double refInOutTotal = 0.0d;

        if (bean.getOutType() == OutConstant.OUTTYPE_OUT_CONSIGN)
        {
            ConditionParse condition = new ConditionParse();

            condition.addWhereStr();

            condition.addCondition("OutBalanceBean.outId", "=", bean.getFullId());

            //condition.addIntCondition("OutBalanceBean.type", "=", OutConstant.OUTBALANCE_TYPE_BACK);
            condition.addCondition(" and OutBalanceBean.type in (1,2)");
            
            condition.addIntCondition("OutBalanceBean.status", "=",
                OutConstant.OUTBALANCE_STATUS_END);

            List<OutBalanceBean> balanceList = outBalanceDAO.queryEntityBeansByCondition(condition);

            for (OutBalanceBean outBalanceBean : balanceList)
            {
                balancePay += outBalanceBean.getTotal();
            }
        }
        else
        {
            // 查询销售退货的价值
            refInOutTotal = outDAO.sumOutBackValue(bean.getFullId());
        }

        double left = bean.getTotal() + hasdOut - bean.getPromValue();

//        double right = hasPay + bean.getBadDebts() + balancePay + refInOutTotal;
        double right = hasPay + hasBadDebtsPay + balancePay + refInOutTotal;

        // 金额不足
        if ( !MathTools.equal(left, right) && left > right)
        {
            result.setResult(1);
        }

        // 正好
        if (MathTools.equal(left, right))
        {
            result.setResult(0);
        }

        // 溢出
        if ( !MathTools.equal(left, right) && left < right)
        {
            result.setResult( -1);
        }

        Formatter formatter = new Formatter();

        String message = "";

        if ( !MathTools.equal(bean.getTotal(), (hasPay + hasBadDebtsPay + balancePay
                                                + refInOutTotal - hasdOut + bean.getPromValue())))
        {
            if (bean.getOutType() == OutConstant.OUTTYPE_OUT_CONSIGN)
            {
            	// 1）委托代销已全部结算；2）结算单全部勾款
            	String outId = bean.getFullId();
            	
            	List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outId);
            	
            	// 取未结算部分
        		List<BaseBalanceBean> baseBalanceList = new ArrayList<BaseBalanceBean>();
        		
        		List<OutBalanceBean> settleList = getBaseBalances(outId,
						baseBalanceList);
        		
        		boolean hadpay = true;
        		
        		// 检查是否全部转结算了，未全部转结算，不是已付款
        		for (BaseBean each : baseList)
        		{
        			int amount = each.getAmount();
        			
        			int settleAmount = 0;
        			for (BaseBalanceBean eachb : baseBalanceList)
        			{
        				if (eachb.getBaseId().equals(each.getId()))
        				{
        					settleAmount += eachb.getAmount();
        				}
        			}
        			
        			if (amount != settleAmount)
        			{
        				hadpay = false;
        				break;
        			}
        		}
        		
        		// 如果全部已结算，检查结算单是否已全部勾款
        		if (hadpay){
        			for (OutBalanceBean each : settleList)
        			{
        				if (!MathTools.equal2(each.getTotal(), each.getPayMoney()))
        				{
        					hadpay = false;
        					break;
        				}
        			}
        		}
        		
        		if (hadpay){
        			result.setResult(0);
        			
        			message = formatter
                    .format(
                        "【销售单总金额[%.2f],退货返还金额[%.2f]】,当前已经收到的付款金额[%.2f](%s),坏账金额[%.2f],退货实物价值[%.2f],委托退货金额[%.2f],折扣金额[%.2f],全部结算",
                        bean.getTotal(), hasdOut, hasPay, inString, hasBadDebtsPay,
                        refInOutTotal, balancePay, bean.getPromValue())
                    .toString();
        		}
        		else{
        			result.setResult(1);
        			
        			message = formatter.format(
                            "销售单总金额[%.2f],当前已经收到的付款金额[%.2f](%s),委托退货金额[%.2f],坏账金额[%.2f],退货金额[%.2f],折扣金额[%.2f],没有完全结算",
                            bean.getTotal(), hasPay, inString, balancePay, hasBadDebtsPay,
                            refInOutTotal, bean.getPromValue()).toString();
        		}
            }
            else
            {
                message = formatter
                    .format(
                        "销售单总金额[%.2f],当前已经收到的付款金额[%.2f](%s),坏账金额[%.2f],退货实物价值[%.2f],退货返还金额[%.2f],折扣金额[%.2f],没有完全结算",
                        bean.getTotal(), hasPay, inString, hasBadDebtsPay,
                        refInOutTotal, hasdOut, bean.getPromValue())
                    .toString();
            }
        }
        else
        {
            message = formatter
                .format(
                    "【销售单总金额[%.2f],退货返还金额[%.2f]】,当前已经收到的付款金额[%.2f](%s),坏账金额[%.2f],退货实物价值[%.2f],委托退货金额[%.2f],折扣金额[%.2f],全部结算",
                    bean.getTotal(), hasdOut, hasPay, inString, hasBadDebtsPay,
                    refInOutTotal, balancePay, bean.getPromValue())
                .toString();
        }

        // 差距值
        result.setValue(bean.getTotal()
                        - ( (hasPay + hasBadDebtsPay + balancePay + refInOutTotal - hasdOut + bean.getPromValue())));

        result.setMessage(message);

        return result;
    }

	private List<OutBalanceBean> getBaseBalances(String outId,
			List<BaseBalanceBean> baseBalanceList)
	{
		// 1.委托结算单
		List<OutBalanceBean> settleList = outBalanceDAO.queryEntityBeansByCondition("where outid = ? and type = ? and status = 1", outId, 0);
		
		for (OutBalanceBean eachBase : settleList)
		{
			baseBalanceList.addAll(baseBalanceDAO.queryEntityBeansByFK(eachBase.getId()));
			
			// 结算退货单
			double ret = outBalanceDAO.sumByOutBalanceId(eachBase.getId());
			
			eachBase.setTotal(eachBase.getTotal() - ret);
		}
		
		// 2.委托退货单
		List<OutBalanceBean> retsettleList = outBalanceDAO.queryEntityBeansByCondition("where outid = ? and type = ? and status = 99", outId, 1);
		
		for (OutBalanceBean eachBase : retsettleList)
		{
			baseBalanceList.addAll(baseBalanceDAO.queryEntityBeansByFK(eachBase.getId()));
		}
		return settleList;
	}

    public void onReject(User user, OutBean bean)
        throws MYException
    {
        receiveToPre(user, bean);

        // 清空已经付款
        outDAO.updateHadPay(bean.getFullId(), 0.0d);
    }

    public double outNeedPayMoney(User user, String fullId)
    {
        OutBean out = outDAO.find(fullId);

        double backTotal = outDAO.sumOutBackValue(fullId);

        double hadOut = outBillDAO.sumByRefId(fullId);

        double hadPay = inBillDAO.sumByOutId(fullId);

        return out.getTotal() + hadOut - hadPay - out.getBadDebts() - backTotal - out.getPromValue();
    }

    /**
     * 个人领样全部是自己验证回款
     * 
     * @param fullId
     * @throws MYException
     */
    private ResultBean processSwithPay(String fullId)
    {
        ResultBean result = new ResultBean();

        result.setMessage("领样全部退库/转销售");

        List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(fullId);

        // 退货
        List<OutBean> refBuyList = queryRefOut(fullId);

        // 销售
        List<OutBean> refList = querySailRef(fullId);

        // 计算出已经退货的数量
        for (BaseBean baseBean : baseList)
        {
            int hasBack = 0;

            for (OutBean ref : refBuyList)
            {
                List<BaseBean> refBaseList = ref.getBaseList();

                for (BaseBean refBase : refBaseList)
                {
                    if (refBase.equals(baseBean))
                    {
                        hasBack += refBase.getAmount();

                        break;
                    }
                }
            }

            for (OutBean ref : refList)
            {
                List<BaseBean> refBaseList = baseDAO.queryEntityBeansByFK(ref.getFullId());

                for (BaseBean refBase : refBaseList)
                {
                    if (refBase.equals(baseBean))
                    {
                        hasBack += refBase.getAmount();

                        break;
                    }
                }
            }

            baseBean.setInway(hasBack);
        }

        for (BaseBean baseBean : baseList)
        {
            if (baseBean.getInway() > baseBean.getAmount())
            {
                result.setResult( -1);
            }

            if (baseBean.getInway() < baseBean.getAmount())
            {
                result.setResult(1);
            }

            if (baseBean.getInway() != baseBean.getAmount())
            {
                result.setMessage(baseBean.getProductName() + "没有全部退库");

                break;
            }
        }

        return result;
    }

    /**
     * 返回个人领样退货的入库单
     * 
     * @param outId
     * @return
     */
    private List<OutBean> queryRefOut(String outId)
    {
        // 查询当前已经有多少个人领样
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addCondition("OutBean.refOutFullId", "=", outId);

        con.addCondition(" and OutBean.status in (3, 4)");

        con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);
        // 排除其它入库中的领样转销售的对冲单
//        con.addIntCondition("OutBean.outType", "<>", OutConstant.OUTTYPE_IN_OTHER);
        con.addCondition("OutBean.reserve8", "<>", "1");

        List<OutBean> refBuyList = outDAO.queryEntityBeansByCondition(con);

        for (OutBean outBean : refBuyList)
        {
            List<BaseBean> list = baseDAO.queryEntityBeansByFK(outBean.getFullId());

            outBean.setBaseList(list);
        }

        return refBuyList;
    }

    /**
     * 返回个人领样转销售的单据
     * 
     * @param fullId
     * @return
     */
    private List<OutBean> querySailRef(String fullId)
    {
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addCondition("OutBean.refOutFullId", "=", fullId);

        con.addCondition(" and OutBean.status in (3, 4)");

        con.addCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);

        // 包括保存的,防止溢出
        List<OutBean> refList = outDAO.queryEntityBeansByCondition(con);

        return refList;
    }

    public void onDelete(User user, OutBean bean)
        throws MYException
    {
        receiveToPre(user, bean);
    }

    /**
     * 已收转预收
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    private void receiveToPre(User user, OutBean bean)
        throws MYException
    {
        List<InBillBean> list = inBillDAO.queryEntityBeansByFK(bean.getFullId());

        if (list.size() > 0)
        {
            // 已收转预算
            for (InBillBean inBillBean : list)
            {
                inBillBean.setOutId("");

                inBillBean.setStatus(FinanceConstant.INBILL_STATUS_NOREF);

                if (inBillBean.getCheckStatus() == PublicConstant.CHECK_STATUS_END)
                {
                    inBillBean.setDescription(inBillBean.getDescription()
                                              + "<br>销售单被驳回/删除,重置收款单的核对状态.");

                    BillHelper.initInBillCheckStatus(inBillBean);
                }
                else
                {
                    inBillBean.setDescription(inBillBean.getDescription()
                                              + "<br>驳回receiveToPre状态重置到预收");
                }
            }

            inBillDAO.updateAllEntityBeans(list);

            // TAX_ADD 销售单驳回/删除后,应收转预收
            Collection<BillListener> listenerMapValues = this.listenerMapValues();

            for (BillListener listener : listenerMapValues)
            {
                listener.onFeeInReceiveToPre(user, bean, list);
            }
        }
    }

    public void onCancleBadDebts(User user, OutBean bean)
        throws MYException
    {
        // 取消坏账是这样的查询坏账的冲单,然后生成对冲的单据
        ConditionParse condition = new ConditionParse();

        condition.addWhereStr();

        condition.addCondition("InBillBean.outId", "=", bean.getFullId());

        condition.addIntCondition("InBillBean.type", "=", FinanceConstant.INBILL_TYPE_BADOUT);

        List<InBillBean> inList = inBillDAO.queryEntityBeansByCondition(condition);

        for (InBillBean inBillBean : inList)
        {
            if (inBillBean.getCheckStatus() == PublicConstant.CHECK_STATUS_INIT
                && inBillBean.getLock() == FinanceConstant.BILL_LOCK_NO)
            {
                inBillDAO.deleteEntityBean(inBillBean.getId());
            }
            else
            {
                String id = inBillBean.getId();

                // 后生成对冲的单据(因为被锁定了)(且必须是)
                inBillBean.setId(commonDAO.getSquenceString20());
                inBillBean.setMoneys( -inBillBean.getMoneys());
                inBillBean.setSrcMoneys( -inBillBean.getMoneys());
                inBillBean.setCheckStatus(PublicConstant.CHECK_STATUS_INIT);
                inBillBean.setChecks("");
                inBillBean.setDescription("取消坏账生成的对冲单据");
                inBillBean.setRefBillId(id);
                inBillBean.setLock(FinanceConstant.BILL_LOCK_NO);
                inBillBean.setLogTime(TimeTools.now());

                billManager.saveInBillInner(inBillBean);
            }
        }
    }

    public void onConfirmBadDebts(User user, OutBean bean)
        throws MYException
    {
        // 在收款单的时候坏账是人工生成的,所以无实现
    }

    public void onCheck(User user, OutBean bean)
        throws MYException
    {
        // 更新未核对的收款单UPDATEID
        List<InBillBean> billList = inBillDAO.queryEntityBeansByFK(bean.getFullId());

        for (InBillBean inBillBean : billList)
        {
            if (inBillBean.getCheckStatus() == PublicConstant.CHECK_STATUS_INIT)
            {
                inBillDAO.updateUpdateId(inBillBean.getId(), commonDAO.getSquence());
            }
        }

    }

    public void onConfirmOutOrBuy(User user, OutBean bean)
        throws MYException
    {
    	// 存在预开票,要删除预开票明细,同时修改预开票状态
    	if (bean.getType() == OutConstant.OUT_TYPE_INBILL && bean.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK){
        	List<PreInvoiceVSOutBean> prevsList = preInvoiceVSOutDAO.queryByOutId(bean.getRefOutFullId());
        	
        	processPreInvoice(user, prevsList);
    	}
    	
    	
        //针对【销售退库】 类型的 【入库单】，直接转成预收 add 2012.8.27
        if (bean.getType() == OutConstant.OUT_TYPE_INBILL && bean.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK)
        {
            String refOutFullId = bean.getRefOutFullId();
            
            OutBean outBean = outDAO.find(refOutFullId);
            
            String refBindOutId = bean.getRefBindOutId();
            
            if (StringTools.isNullOrNone(refBindOutId))
            {
                refBindOutId = ""; 
            }
            
            String [] ref = refBindOutId.split("~");
            
            String execPromOutId = "";
            
            if (ref.length > 0)
                execPromOutId = ref[0];
            
            
            ResultBean result = onHadPay(user, outBean);

            // 原单须是收支平衡
            if (result.getResult() == 0)
            {
                //借用申请退款实体
                BackPayApplyBean backBean = new BackPayApplyBean();
                
                backBean.setOutId(bean.getRefOutFullId());                
                backBean.setType(BackPayApplyConstant.TYPE_OUT);
                backBean.setStafferId(bean.getStafferId());
                backBean.setCustomerId(bean.getCustomerId());
                
                boolean exec = false;
                // 执行的销售单退货
                if (bean.getRefOutFullId().equals(execPromOutId))
                {
                    backBean.setChangePayment(bean.getTotal() - bean.getPromValue());
                    
                    exec = true;
                }else
                {
                    backBean.setChangePayment(bean.getTotal());   
                }
                
                // 促销类型为折扣比例，退货时可能退部分折扣金额
                if (bean.getPromStatus() == OutConstant.OUT_PROMSTATUS_PARTBACKBIND)
                {
                    outDAO.updatePromValue(execPromOutId, bean.getPromValue());
                }

                //销售退库 - 转预收
                backPayApplyManager.backToPreWithOutTransaction(user, backBean); 
                
                //参与促销退货，要处理折扣金额，这里要自动生成一张用退回的预收勾销售折扣金额，绝!
                createPaymentApplyForPromBack(user, bean, exec);
                
            }else 
            {
                if (!bean.getRefOutFullId().equals(execPromOutId) && !StringTools.isNullOrNone(execPromOutId))
                {                    
                    //原单变为未付款
                    outDAO.updatePay(execPromOutId, OutConstant.PAY_NOT);
                    
                    // 促销类型为折扣比例，退货时可能退部分折扣金额
                    if (bean.getPromStatus() == OutConstant.OUT_PROMSTATUS_PARTBACKBIND)
                    {
                        outDAO.updatePromValue(execPromOutId, bean.getPromValue());
                    }
                    
                }
                
                if (bean.getRefOutFullId().equals(execPromOutId))
                {
                    double hasPay = inBillDAO.sumByOutId(execPromOutId);
                    
                    if (hasPay > 0)
                        throw new MYException(result.getMessage()+",所以无法退款(当前退货直接退预收)");
                    
                    // 促销类型为折扣比例，退货时可能退部分折扣金额
                    if (bean.getPromStatus() == OutConstant.OUT_PROMSTATUS_PARTBACKBIND)
                    {
                        outDAO.updatePromValue(execPromOutId, bean.getPromValue());
                    }
                }
                
                // 部分退货时,修改原单中的信用占用，优先级：事业部->客户->业务员.  bean & outBean
                double creditTotal = bean.getTotal();
                
                if (outBean.getManagercredit() > 0)
                {
                    // 事业部信用占用的够大
                    if (outBean.getManagercredit() >= creditTotal)
                    {
                        outDAO.updateManagercredit(outBean.getFullId(), outBean.getManagerId(), (outBean.getManagercredit() - creditTotal));
                        
                        creditTotal = 0.0d;
                    }else
                    {
                        outDAO.updateManagercredit(outBean.getFullId(), outBean.getManagerId(), 0.0d);
                        
                        creditTotal = creditTotal - outBean.getManagercredit();
                    }
                }
                
                // 客户
                if (outBean.getCurcredit() > 0 && creditTotal > 0)
                {
                    
                    if (outBean.getCurcredit() >= creditTotal)
                    {
                        outDAO.updateCurcredit(outBean.getFullId(), (outBean.getCurcredit() - creditTotal));
                        
                        creditTotal = 0.0d;
                    }else
                    {
                        outDAO.updateCurcredit(outBean.getFullId(), 0.0d);
                        
                        creditTotal = creditTotal - outBean.getCurcredit();
                    }
                    
                }
                
                // 业务员
                if (outBean.getStaffcredit() > 0 && creditTotal > 0)
                {
                    if (outBean.getStaffcredit() >= creditTotal)
                    {
                        outDAO.updateStaffcredit(outBean.getFullId(), (outBean.getStaffcredit() - creditTotal));
                        
                        creditTotal = 0.0d;
                    }else
                    {
                        outDAO.updateStaffcredit(outBean.getFullId(), 0.0d);
                        
                        creditTotal = creditTotal - outBean.getStaffcredit();
                    }
                }
                
                if (creditTotal > 0)
                {
                    //仍大于0，数据溢出
                }
                               
            }

        }
    }

    /**
     * 
     * @param user
     * @param bean
     * @param exec
     * @throws MYException
     */
    private void createPaymentApplyForPromBack(User user, OutBean bean, boolean exec) 
    throws MYException 
    {        
        //针对参加了促销活动销售退库 promStatus[0:执行，1：参与，2：退货（销售退库单上）]
        if ((bean.getPromStatus() == OutConstant.OUT_PROMSTATUS_BACKBIND 
                || bean.getPromStatus() == OutConstant.OUT_PROMSTATUS_PARTBACKBIND)
                && !exec)
        {
            // 执行促销的销售单要自动勾款：promValue
            double promValue = bean.getPromValue();
            
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();

            condtion.addCondition("InBillBean.ownerId", "=", bean.getStafferId());

            condtion.addCondition("InBillBean.customerId", "=", bean.getCustomerId());

            condtion.addIntCondition("InBillBean.status", "=", FinanceConstant.INBILL_STATUS_NOREF);

            condtion.addCondition("order by InBillBean.logTime desc");

            List<InBillVO> billList = inBillDAO.queryEntityVOsByCondition(condtion);
         
            PaymentApplyBean apply = new PaymentApplyBean();

            apply.setType(FinanceConstant.PAYAPPLY_TYPE_AUTO);
            apply.setCustomerId(bean.getCustomerId());
            apply.setLocationId(bean.getLocationId());
            apply.setLogTime(TimeTools.now());
            apply.setStafferId(bean.getStafferId());
            //借用坏账字段，用完要置0
            apply.setBadMoney(promValue);

            List<PaymentVSOutBean> vsList = new ArrayList<PaymentVSOutBean>();

            double total = 0.0d;

            //要勾的销售单:执行了促销活动的销售单                    
            String refBindOutId = bean.getRefBindOutId();
            if (StringTools.isNullOrNone(refBindOutId))
            {
                throw new MYException("数据错误,请确认"); 
            }
            
            String [] ref = refBindOutId.split("~");
            
            if (null == ref)
            {
                throw new MYException("数据错误,请确认"); 
            }
            
            String execPromOutId = ref[0];
            
            if (StringTools.isNullOrNone(execPromOutId))
            {
                throw new MYException("数据错误,请确认"); 
            }
            
            Formatter formatter = new Formatter();

            String message = "";
            
            message = formatter.format("销售退库单[%s],关联的销售单[%s]参与了促销活动,其中执行促销活动的销售单[%s]折扣金额[%.2f]，自动生成收款申请单."
                    ,bean.getFullId(), bean.getRefOutFullId(),execPromOutId, promValue).toString();
            
            apply.setDescription(message);

            double outTotal = promValue;

            for (InBillBean bill : billList)
            {                                
                PaymentVSOutBean vs = new PaymentVSOutBean();
      
                vs.setLocationId(bean.getLocationId());
      
                vs.setMoneys(bill.getMoneys());
      
                vs.setOutId(execPromOutId);
      
                vs.setBillId(bill.getId());
      
                vs.setPaymentId(bill.getPaymentId());
      
                vs.setStafferId(bean.getStafferId());
      
                vsList.add(vs);
      
                total += vs.getMoneys();
      
                if (total >= outTotal)
                {
                    break;
                }
            }

            apply.setVsList(vsList);

            // 没有申请付款
            if (vsList.size() == 0)
            {
                 throw new MYException("没有预收用于勾折扣部分金额");
            }

            apply.setMoneys(total);
            
            //执行勾款 - 由稽核审核
            paymentApplyManager.addPaymentApply(user, apply);
                                
            //原单变为未付款
            outDAO.updatePay(execPromOutId, OutConstant.PAY_NOT);
        }
    }
   
    public void onOutBalancePass(User user, OutBalanceBean bean)
        throws MYException
    {
    	// 存在预开票,要删除预开票明细,同时修改预开票状态
    	if (bean.getType() == OutConstant.OUTBALANCE_TYPE_SAILBACK){
        	List<PreInvoiceVSOutBean> prevsList = preInvoiceVSOutDAO.queryByOutBalanceId(bean.getRefOutBalanceId());
        	
        	processPreInvoice(user, prevsList);
    	}
    	
    	// 委托结算退货时,如果已勾款要转预收
        if (bean.getType() == OutConstant.OUTBALANCE_TYPE_SAILBACK)
        {
            String refOutBalanceId = bean.getRefOutBalanceId();
            
            double changePayment = 0.0d;
            
            OutBalanceBean balanceBean = outBalanceDAO.find(refOutBalanceId);
            
            if (null == balanceBean)
            {
            	throw new MYException("数据错误");
            }
        	
            if (balanceBean.getPayMoney() >= bean.getTotal())
            {
            	changePayment = bean.getTotal();
            }
            else
            {
            	changePayment = balanceBean.getPayMoney();
            }
            
            //借用申请退款实体
            BackPayApplyBean backBean = new BackPayApplyBean();
            
            backBean.setOutId(bean.getOutId());                
            backBean.setType(BackPayApplyConstant.TYPE_OUT);
            backBean.setStafferId(bean.getStafferId());
            backBean.setCustomerId(bean.getCustomerId());
            backBean.setChangePayment(changePayment);
            backBean.setOutBalanceId(refOutBalanceId);
            
            //销售退库 - 转预收
            backPayApplyManager.backToPreWithOutTransaction(user, backBean); 
        }
    }

	private void processPreInvoice(User user,
			List<PreInvoiceVSOutBean> prevsList)
	{
		if (!ListTools.isEmptyOrNull(prevsList)){
    		
    		for (PreInvoiceVSOutBean each : prevsList)
        	{
        		PreInvoiceApplyBean apply = preInvoiceApplyDAO.find(each.getParentId());
        		
        		preInvoiceApplyDAO.updateInvoiceMoney(apply.getId(), apply.getInvoiceMoney() - MathTools.doubleToLong2(each.getInvoiceMoney()));
        	
        		preInvoiceApplyDAO.updateStatus(apply.getId(), 27);
        		
        		preInvoiceVSOutDAO.deleteEntityBean(each.getId());
        		
        		double total = 0.0d;
        		
        		if (StringTools.isNullOrNone(each.getOutBalanceId())){
        			
        			OutBean out = outDAO.find(each.getOutId());
        			
        			total = out.getInvoiceMoney() - each.getInvoiceMoney();
        			
        			if (total < 0)
        				total = 0;
        			
        			outDAO.updateInvoiceStatus(out.getFullId(), total, OutConstant.INVOICESTATUS_INIT);
        		}else{
        			OutBalanceBean balance = outBalanceDAO.find(each.getOutBalanceId());
        			
        			total = balance.getInvoiceMoney() - each.getInvoiceMoney();
        			
        			if (total < 0)
        				total = 0;
        			
        			outBalanceDAO.updateInvoiceStatus(balance.getId(), total,
        	                OutConstant.INVOICESTATUS_INIT);
        		}
        		
        		List<FinanceTcpApproveBean> ftList =  financeTcpApproveDAO.queryEntityBeansByFK(apply.getId());
        		
        		if (ListTools.isEmptyOrNull(ftList)){
        			FinanceTcpApproveBean approve = new FinanceTcpApproveBean();

                    approve.setId(commonDAO.getSquenceString20());
                    approve.setApplyerId(apply.getStafferId());
                    approve.setApplyId(apply.getId());
                    approve.setApproverId(apply.getStafferId());
                    approve.setFlowKey(apply.getFlowKey());
                    approve.setLogTime(TimeTools.now());
                    approve.setDepartmentId(apply.getDepartmentId());
                    approve.setName(apply.getName());
                    approve.setStatus(27);
                    approve.setTotal(apply.getTotal());
                    approve.setCheckTotal(0);
                    approve.setType(apply.getType());
                    approve.setStype(apply.getStype());
                    approve.setPool(0);
                    approve.setPayType(-1);

                    financeTcpApproveDAO.saveEntityBean(approve);
                    
                    FlowLogBean log = new FlowLogBean();

                    log.setFullId(apply.getId());

                    log.setActor(user.getStafferName());

                    log.setActorId(user.getStafferId());

                    log.setOprMode(0);

                    log.setDescription("退货,重新关联销售单");

                    log.setLogTime(TimeTools.now());

                    log.setPreStatus(27);

                    log.setAfterStatus(apply.getStatus());

                    flowLogDAO.saveEntityBean(log);
        		}
        	}
    	}
	}

    public void onTranOutList(User user, List<OutBean> outList, StafferBean targerStaffer)
        throws MYException
    {
        // do noting
    }    
    
    /**
     * 空开空退，按原销售金额勾款
     * 
     * {@inheritDoc}
     */
	public void onOutRepairePass(User user, OutBean oldOutBean, OutBean newOutBean) throws MYException
	{
		// do nothing
	}
    
	@Override
	public void onTranOut(User user, OutBean out, StafferBean targerStaffer) throws MYException
	{
		
	}
	
    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return PluginNameConstant.OUTLISTENER_FINANCEIMPL;
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
     * @return the outBalanceDAO
     */
    public OutBalanceDAO getOutBalanceDAO()
    {
        return outBalanceDAO;
    }

    /**
     * @param outBalanceDAO
     *            the outBalanceDAO to set
     */
    public void setOutBalanceDAO(OutBalanceDAO outBalanceDAO)
    {
        this.outBalanceDAO = outBalanceDAO;
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
     * @return the baseDAO
     */
    public BaseDAO getBaseDAO()
    {
        return baseDAO;
    }

    /**
     * @param baseDAO
     *            the baseDAO to set
     */
    public void setBaseDAO(BaseDAO baseDAO)
    {
        this.baseDAO = baseDAO;
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

    public BackPayApplyManager getBackPayApplyManager() {
        return backPayApplyManager;
    }

    public void setBackPayApplyManager(BackPayApplyManager backPayApplyManager) {
        this.backPayApplyManager = backPayApplyManager;
    }

    public PaymentApplyManager getPaymentApplyManager() {
        return paymentApplyManager;
    }

    public void setPaymentApplyManager(PaymentApplyManager paymentApplyManager) {
        this.paymentApplyManager = paymentApplyManager;
    }

	public PaymentVSOutDAO getPaymentVSOutDAO()
	{
		return paymentVSOutDAO;
	}

	public void setPaymentVSOutDAO(PaymentVSOutDAO paymentVSOutDAO)
	{
		this.paymentVSOutDAO = paymentVSOutDAO;
	}

	public PreInvoiceVSOutDAO getPreInvoiceVSOutDAO()
	{
		return preInvoiceVSOutDAO;
	}

	public void setPreInvoiceVSOutDAO(PreInvoiceVSOutDAO preInvoiceVSOutDAO)
	{
		this.preInvoiceVSOutDAO = preInvoiceVSOutDAO;
	}

	public FinanceTcpApproveDAO getFinanceTcpApproveDAO()
	{
		return financeTcpApproveDAO;
	}

	public void setFinanceTcpApproveDAO(FinanceTcpApproveDAO financeTcpApproveDAO)
	{
		this.financeTcpApproveDAO = financeTcpApproveDAO;
	}

	public PreInvoiceApplyDAO getPreInvoiceApplyDAO()
	{
		return preInvoiceApplyDAO;
	}

	public void setPreInvoiceApplyDAO(PreInvoiceApplyDAO preInvoiceApplyDAO)
	{
		this.preInvoiceApplyDAO = preInvoiceApplyDAO;
	}

	public FlowLogDAO getFlowLogDAO()
	{
		return flowLogDAO;
	}

	public void setFlowLogDAO(FlowLogDAO flowLogDAO)
	{
		this.flowLogDAO = flowLogDAO;
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
	 * @return the invoiceinsDAO
	 */
	public InvoiceinsDAO getInvoiceinsDAO() {
		return invoiceinsDAO;
	}

	/**
	 * @param invoiceinsDAO the invoiceinsDAO to set
	 */
	public void setInvoiceinsDAO(InvoiceinsDAO invoiceinsDAO) {
		this.invoiceinsDAO = invoiceinsDAO;
	}

	/**
	 * @return the invoiceinsItemDAO
	 */
	public InvoiceinsItemDAO getInvoiceinsItemDAO() {
		return invoiceinsItemDAO;
	}

	/**
	 * @param invoiceinsItemDAO the invoiceinsItemDAO to set
	 */
	public void setInvoiceinsItemDAO(InvoiceinsItemDAO invoiceinsItemDAO) {
		this.invoiceinsItemDAO = invoiceinsItemDAO;
	}
}
