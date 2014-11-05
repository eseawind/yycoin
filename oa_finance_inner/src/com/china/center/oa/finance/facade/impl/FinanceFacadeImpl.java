/**
 * File Name: FinanceFacadeImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.facade.impl;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.BackPayApplyBean;
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.bean.InvoiceBindOutBean;
import com.china.center.oa.finance.bean.InvoiceinsBean;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.bean.PaymentApplyBean;
import com.china.center.oa.finance.bean.PaymentBean;
import com.china.center.oa.finance.bean.StockPayApplyBean;
import com.china.center.oa.finance.bean.StockPrePayApplyBean;
import com.china.center.oa.finance.facade.FinanceFacade;
import com.china.center.oa.finance.manager.BackPayApplyManager;
import com.china.center.oa.finance.manager.BankManager;
import com.china.center.oa.finance.manager.BillManager;
import com.china.center.oa.finance.manager.InvoiceinsManager;
import com.china.center.oa.finance.manager.PaymentApplyManager;
import com.china.center.oa.finance.manager.PaymentManager;
import com.china.center.oa.finance.manager.StockPayApplyManager;
import com.china.center.oa.finance.vo.BatchSplitInBillWrap;
import com.china.center.oa.finance.vs.StockPayVSPreBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.facade.AbstarctFacade;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.tools.JudgeTools;


/**
 * FinanceFacadeImpl
 * 
 * @author ZHUZHU
 * @version 2010-12-12
 * @see FinanceFacadeImpl
 * @since 3.0
 */
public class FinanceFacadeImpl extends AbstarctFacade implements FinanceFacade
{
	protected final Log _logger = LogFactory.getLog(getClass());
	
    private BankManager bankManager = null;

    private PaymentManager paymentManager = null;

    private UserManager userManager = null;

    private BillManager billManager = null;

    private InvoiceinsManager invoiceinsManager = null;

    private PaymentApplyManager paymentApplyManager = null;

    private StockPayApplyManager stockPayApplyManager = null;

    private BackPayApplyManager backPayApplyManager = null;

    /**
     * default constructor
     */
    public FinanceFacadeImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.finance.facade.FinanceFacade#addBankBean(java.lang.String,
     *      com.china.center.oa.finance.bean.BankBean)
     */
    public boolean addBankBean(String userId, BankBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.BANK_OPR))
        {
            return bankManager.addBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.finance.facade.FinanceFacade#deleteBankBean(java.lang.String, java.lang.String)
     */
    public boolean deleteBankBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.BANK_OPR))
        {
            return bankManager.deleteBean(user, id);
        }
        else
        {
            throw noAuth();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.finance.facade.FinanceFacade#updateBankBean(java.lang.String,
     *      com.china.center.oa.finance.bean.BankBean)
     */
    public boolean updateBankBean(String userId, BankBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.BANK_OPR))
        {
            return bankManager.updateBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    public boolean addPaymentBean(String userId, PaymentBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.PAYMENT_OPR))
        {
            return paymentManager.addBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    public boolean addPaymentBeanList(String userId, List<PaymentBean> beanList)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, beanList);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.PAYMENT_OPR))
        {
            return paymentManager.addBeanList(user, beanList);
        }
        else
        {
            throw noAuth();
        }
    }

    public boolean deletePaymentBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.PAYMENT_OPR))
        {
            return paymentManager.deleteBean(user, id);
        }
        else
        {
            throw noAuth();
        }
    }

    public boolean batchDeletePaymentBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.PAYMENT_OPR))
        {
            return paymentManager.batchDeleteBean(user, id);
        }
        else
        {
            throw noAuth();
        }
    }

    public boolean drawPaymentBean(String userId, String id, String customerId,String description)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (PAYMENT_LOCK)
        {
            paymentManager.drawBean(user, id, customerId,description);
        }

        return true;
    }

    public boolean dropPaymentBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (PAYMENT_LOCK)
        {
            paymentManager.dropBean(user, id);
        }

        return true;
    }

    public boolean updatePaymentBean(String userId, PaymentBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.PAYMENT_OPR))
        {
            return paymentManager.updateBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    public boolean addInvoiceinsBean(String userId, InvoiceinsBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (INVOICEINS_LOCK)
        {
            if (containAuth(user, AuthConstant.SAIL_SUBMIT))
            {
                return invoiceinsManager.addInvoiceinsBean(user, bean);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean deleteInvoiceinsBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (INVOICEINS_LOCK)
        {
            if (containAuth(user, AuthConstant.INVOICEINS_DEL, AuthConstant.SAIL_SUBMIT))
            {
                return invoiceinsManager.deleteInvoiceinsBean(user, id);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean passInvoiceinsBean(String userId, InvoiceinsBean bean, String reason)
        throws MYException
    {
    	JudgeTools.judgeParameterIsNull(userId, bean, bean.getId());

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (INVOICEINS_LOCK)
        {
            if (containAuth(user, AuthConstant.INVOICEINS_OPR))
            {
                return invoiceinsManager.passInvoiceinsBean(user, bean, reason);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean checkInvoiceinsBean(String userId, String id, String reason)
        throws MYException
    {
    	JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (INVOICEINS_LOCK)
        {
            if (containAuth(user, AuthConstant.INVOICEINS_CHECK))
            {
                return invoiceinsManager.checkInvoiceinsBean(user, id, reason);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean rejectInvoiceinsBean(String userId, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (INVOICEINS_LOCK)
        {
            if (containAuth(user, AuthConstant.INVOICEINS_OPR, AuthConstant.INVOICEINS_CHECK))
            {
                return invoiceinsManager.rejectInvoiceinsBean(user, id, reason);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean addPaymentApply(String userId, PaymentApplyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (PAYMENT_APPLY_LOCK)
        {
            return paymentApplyManager.addPaymentApply(user, bean);
        }
    }
    
    /**
     * addPaymentApply5 预收单个拆分
     */
    public boolean addPaymentApply5(String userId, PaymentApplyBean bean)
    throws MYException
	{
	    JudgeTools.judgeParameterIsNull(userId, bean);
	
	    User user = userManager.findUser(userId);
	
	    checkUser(user);
	
	    synchronized (PAYMENT_APPLY_LOCK)
	    {
	        return paymentApplyManager.addPaymentApply5(user, bean);
	    }
	}
    
    /**
     * 批量勾款
     * 业务自己操作
     */
    public boolean addPaymentApply6(String userId, PaymentApplyBean bean)
    throws MYException
	{
	    JudgeTools.judgeParameterIsNull(userId, bean);
	
	    User user = userManager.findUser(userId);
	
	    checkUser(user);
	
	    synchronized (PAYMENT_APPLY_LOCK)
	    {
	        return paymentApplyManager.addPaymentApply6(user, bean);
	    }
	}

    public boolean deletePaymentApply(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (PAYMENT_APPLY_LOCK)
        {
            return paymentApplyManager.deletePaymentApply(user, id);
        }
    }

    public boolean passPaymentApply(String userId, String id, String reason,String description)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);
        checkUser(user);
        synchronized (PAYMENT_APPLY_LOCK)
        {
            if (containAuth(user, AuthConstant.INBILL_APPROVE))
            {
                return paymentApplyManager.passPaymentApply(user, id, reason,description);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean passCheck(String userId, String id, String reason,String description)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (PAYMENT_APPLY_LOCK)
        {
            if (containAuth(user, AuthConstant.INBILL_CHECK))
            {
                return paymentApplyManager.passCheck(user, id, reason);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean rejectPaymentApply(String userId, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);
        
        synchronized (PAYMENT_APPLY_LOCK)
        {
            if (containAuth(user, AuthConstant.INBILL_APPROVE, AuthConstant.INBILL_CHECK))
            {
            	
                return paymentApplyManager.rejectPaymentApply(user, id, reason);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean checkPaymentApply(String userId, String id, String checks, String refId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (PAYMENT_APPLY_LOCK)
        {
            if (containAuth(user, AuthConstant.BILL_QUERY_ALL))
            {
                return paymentApplyManager.checkPaymentApply(user, id, checks, refId);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean checkInvoiceinsBean2(String userId, String id, String checks, String refId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (INVOICEINS_LOCK)
        {
            if (containAuth(user, AuthConstant.BILL_QUERY_ALL))
            {
                return invoiceinsManager.checkInvoiceinsBean2(user, id, checks, refId);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean updatePaymentApply(String userId, PaymentApplyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (PAYMENT_APPLY_LOCK)
        {
            return paymentApplyManager.updatePaymentApply(user, bean);
        }
    }

    public String splitInBillBean(String userId, String id, double newMoney)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);
        
        synchronized (PAYMENT_APPLY_LOCK)
		{
        	return billManager.splitInBillBean(user, id, newMoney);
		}
    }
    
    public String freezeInBillBean(String userId, String id, double newMoney)
    throws MYException
	{
	    JudgeTools.judgeParameterIsNull(userId, id);
	
	    User user = userManager.findUser(userId);
	
	    checkUser(user);
	
	    synchronized (PAYMENT_APPLY_LOCK)
		{
	    	return billManager.freezeInBillBean(user, id, newMoney);	    	
		}
	}

    public boolean addInBillBean(String userId, InBillBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (INBILL_LOCK)
        {
            if (containAuth(user, AuthConstant.INBILL_OPR))
            {
                return billManager.addInBillBean(user, bean);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean deleteInBillBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (INBILL_LOCK)
        {
            if (containAuth(user, AuthConstant.INBILL_OPR))
            {
                return billManager.deleteInBillBean(user, id);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean addOutBillBean(String userId, OutBillBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (OUTBILL_LOCK)
        {
            if (containAuth(user, AuthConstant.OUTBILL_OPR))
            {
                return billManager.addOutBillBean(user, bean);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean deleteOutBillBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (OUTBILL_LOCK)
        {
            if (containAuth(user, AuthConstant.OUTBILL_OPR))
            {
                return billManager.deleteOutBillBean(user, id);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean rejectStockPayApply(String userId, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (STOCKPAYAPPLY_LOCK)
        {
            if (containAuth(user, AuthConstant.STOCK_PAY_CEO, AuthConstant.STOCK_PAY_SEC, AuthConstant.STOCK_PAY_SAIL))
            {
                return stockPayApplyManager.rejectStockPayApply(user, id, reason);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    /**
     * closeStockPayApply
     * 
     * @param userId
     * @param id
     * @param reason
     * @return
     * @throws MYException
     */
    public boolean closeStockPayApply(String userId, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (STOCKPAYAPPLY_LOCK)
        {
            if (containAuth(user, AuthConstant.STOCK_PAY_APPLY))
            {
                return stockPayApplyManager.closeStockPayApply(user, id, reason);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean submitStockPayApply(String userId, String id, double payMoney, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (STOCKPAYAPPLY_LOCK)
        {
            if (containAuth(user, AuthConstant.STOCK_PAY_APPLY))
            {
                return stockPayApplyManager.submitStockPayApply(user, id, payMoney, reason);
            }
            else
            {
                throw noAuth();
            }
        }
    }
    
    public boolean submitStockPayApply(String userId, String id, String reason, StockPayApplyBean bean)
    	throws MYException
	{
	    JudgeTools.judgeParameterIsNull(userId, id);
	
	    User user = userManager.findUser(userId);
	
	    checkUser(user);
	
	    synchronized (STOCKPAYAPPLY_LOCK)
	    {
	        if (containAuth(user, AuthConstant.STOCK_PAY_APPLY))
	        {
	            return stockPayApplyManager.submitStockPayApply(user, id, reason, bean);
	        }
	        else
	        {
	            throw noAuth();
	        }
	    }
	}

    public boolean passStockPayByCEO(String userId, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (STOCKPAYAPPLY_LOCK)
        {
            if (containAuth(user, AuthConstant.STOCK_PAY_CEO, AuthConstant.STOCK_PAY_SAIL,
                AuthConstant.STOCK_PAY_CHECK, AuthConstant.STOCK_PAY_CFO))
            {
                return stockPayApplyManager.passStockPayByCEO(user, id, reason);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean composeStockPayApply(String userId, List<String> idList)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, idList);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (STOCKPAYAPPLY_LOCK)
        {
            if (containAuth(user, AuthConstant.STOCK_PAY_APPLY))
            {
                return stockPayApplyManager.composeStockPayApply(user, idList);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean endStockPayBySEC(String userId, String id, String reason,
                                    List<OutBillBean> outBillList)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (STOCKPAYAPPLY_LOCK)
        {
            if (containAuth(user, AuthConstant.STOCK_PAY_SEC))
            {
                return stockPayApplyManager.endStockPayBySEC(user, id, reason, outBillList);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean addBackPayApplyBean(String userId, BackPayApplyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (PAYMENT_APPLY_LOCK)
        {
            if (containAuth(user, AuthConstant.SAIL_SUBMIT))
            {
                return backPayApplyManager.addBackPayApplyBean(user, bean);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean deleteBackPayApplyBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (BACKPAYAPPLY_LOCK)
        {
            if (containAuth(user, AuthConstant.SAIL_SUBMIT))
            {
                return backPayApplyManager.deleteBackPayApplyBean(user, id);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean passBackPayApplyBean(String userId, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (BACKPAYAPPLY_LOCK)
        {
            if (containAuth(user, AuthConstant.SAIL_BACKPAY_CENTER))
            {
                return backPayApplyManager.passBackPayApplyBean(user, id, reason);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean rejectBackPayApplyBean(String userId, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (BACKPAYAPPLY_LOCK)
        {
            if (containAuth(user, AuthConstant.SAIL_BACKPAY_CENTER, AuthConstant.SAIL_BACKPAY_SEC))
            {
                return backPayApplyManager.rejectBackPayApplyBean(user, id, reason);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean endBackPayApplyBean(String userId, String id, String reason,
                                       List<OutBillBean> outBillList)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (BACKPAYAPPLY_LOCK)
        {
            if (containAuth(user, AuthConstant.SAIL_BACKPAY_SEC))
            {
                return backPayApplyManager.endBackPayApplyBean(user, id, reason, outBillList);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean passTransferOutBillBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (BILLAPPLY_LOCK)
        {
            if (containAuth(user, "1611"))
            {
                return billManager.passTransferOutBillBean(user, id);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean rejectTransferOutBillBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (BILLAPPLY_LOCK)
        {
            if (containAuth(user, "1611"))
            {
                return billManager.rejectTransferOutBillBean(user, id);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean updateInBillBeanChecks(String userId, String id, String checks)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.INBILL_OPR))
        {
            return billManager.updateInBillBeanChecks(user, id, checks);
        }
        else
        {
            throw noAuth();
        }
    }

    public boolean updateOutBillBeanChecks(String userId, String id, String checks)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.OUTBILL_OPR))
        {
            return billManager.updateOutBillBeanChecks(user, id, checks);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    public boolean passTransferOutBillBean1(String userId, String id)
    throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);
    
        User user = userManager.findUser(userId);
    
        checkUser(user);
    
        synchronized (BILLAPPLY_LOCK)
        {
            if (containAuth(user, "1612"))
            {
                return billManager.passTransferOutBillBean1(user, id);
            }
            else
            {
                throw noAuth();
            }
        }
    }    
    
    /**
     * 
     * {@inheritDoc}
     */
    public boolean rejectTransferOutBillBean1(String userId, String id)
    throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);
    
        User user = userManager.findUser(userId);
    
        checkUser(user);
    
        synchronized (BILLAPPLY_LOCK)
        {
            if (containAuth(user, "1612"))
            {
                return billManager.rejectTransferOutBillBean1(user, id);
            }
            else
            {
                throw noAuth();
            }
        }
    }
    
    /**
     * 确认开票
     * {@inheritDoc}
     */
	public boolean confirmInvoice(String userId, String id) throws MYException
	{
		JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);
        
        return invoiceinsManager.confirmInvoice(user, id);
	}

	/**
	 * 确认付款
	 * {@inheritDoc}
	 */
	public boolean confirmPay(String userId, String id) throws MYException
	{
		JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);
        
        synchronized (INVOICEINS_CONFIRM_LOCK)
        {
        	return invoiceinsManager.confirmPay(user, id);
        }
	}
    
	/**
	 * 
	 * {@inheritDoc}
	 */
	public boolean backInvoiceins(String userId, String id) throws MYException
	{
		JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);
        
        return invoiceinsManager.backInvoiceins(user, id);
	}
	
	@Override
	public boolean batchSplitInBill(String userId, String billId, List<BatchSplitInBillWrap> list)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(list);
		
		User user = userManager.findUser(userId);

        checkUser(user);
        
		synchronized (PAYMENT_APPLY_LOCK)
		{
			paymentApplyManager.batchSplitInBill(user, billId, list);
		}
		
		return true;
	}
	
	@Override
	public boolean rejectStockPrePayApply(String userId, String id,
			String reason) throws MYException
	{
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (STOCKPREPAYAPPLY_LOCK)
        {
            if (containAuth(user, AuthConstant.STOCK_PAY_CEO, AuthConstant.STOCK_PAY_SEC, AuthConstant.STOCK_PAY_SAIL))
            {
                return stockPayApplyManager.rejectStockPrePayApply(user, id, reason);
            }
            else
            {
                throw noAuth();
            }
        }
    }

	@Override
	public boolean passStockPrePayByCEO(String userId, String id, String reason)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (STOCKPREPAYAPPLY_LOCK)
        {
            if (containAuth(user, AuthConstant.STOCK_PAY_CEO, AuthConstant.STOCK_PAY_SAIL,
                AuthConstant.STOCK_PAY_CHECK, AuthConstant.STOCK_PAY_CFO))
            {
                return stockPayApplyManager.passStockPrePayByCEO(user, id, reason);
            }
            else
            {
                throw noAuth();
            }
        }
    }

	@Override
	public boolean endStockPrePayBySEC(String userId, String id, String reason,
			List<OutBillBean> outBillList) throws MYException
	{
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (STOCKPREPAYAPPLY_LOCK)
        {
            if (containAuth(user, AuthConstant.STOCK_PAY_SEC))
            {
                return stockPayApplyManager.endStockPrePayBySEC(user, id, reason, outBillList);
            }
            else
            {
                throw noAuth();
            }
        }
    }

	@Override
	public boolean addStockPrePayApply(String userId, StockPrePayApplyBean bean)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (STOCKPREPAYAPPLY_LOCK)
        {
        	return stockPayApplyManager.addStockPrePayApply(user, bean);
        }
    }

	@Override
	public boolean updateStockPrePayApply(String userId,
			StockPrePayApplyBean bean) throws MYException
	{
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (STOCKPREPAYAPPLY_LOCK)
        {
        	return stockPayApplyManager.updateStockPrePayApply(user, bean);
        }
    }

	@Override
	public boolean deleteStockPrePayApply(String userId, String id)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (STOCKPREPAYAPPLY_LOCK)
        {
        	return stockPayApplyManager.deleteStockPrePayApply(user, id);
        }
    }
	
	@Override
	public String refStockPrePay(String userId, List<StockPayVSPreBean> vsList)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(userId, vsList);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (STOCKPAYAPPLY_LOCK)
        {
        	return stockPayApplyManager.refStockPrePay(user, vsList);
        }
    }
	
	@Override
	public boolean refConfirmInvoice(String userId,
			List<InvoiceBindOutBean> vsList) throws MYException
	{
        JudgeTools.judgeParameterIsNull(userId, vsList);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (INVOICE_BIND_LOCK)
        {
        	return invoiceinsManager.refConfirmInvoice(user, vsList);
        }
    }
	
	public boolean refInvoice(String userId,
			List<InvoiceBindOutBean> vsList) throws MYException
	{
        JudgeTools.judgeParameterIsNull(userId, vsList);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (INVOICE_BIND_LOCK)
        {
        	return invoiceinsManager.refInvoice(user, vsList);
        }
    }
	
	public boolean addTransferApply(String userId, PaymentApplyBean bean)
    throws MYException
	{
	    JudgeTools.judgeParameterIsNull(userId, bean);
	
	    User user = userManager.findUser(userId);
	
	    checkUser(user);
	
	    synchronized (PAYMENT_APPLY_LOCK1)
	    {
	        return paymentApplyManager.addTransferApply(user, bean);
	    }
	}
	
	public boolean addDrawProviderApply(String userId, PaymentApplyBean bean)
    throws MYException
	{
	    JudgeTools.judgeParameterIsNull(userId, bean);
	
	    User user = userManager.findUser(userId);
	
	    checkUser(user);
	
	    synchronized (PAYMENT_APPLY_LOCK)
	    {
	        return paymentApplyManager.addDrawProviderApply(user, bean);
	    }
	}
	
	public boolean refPurchaseBack(String userId, String customerId, String ids) throws MYException
	{
        JudgeTools.judgeParameterIsNull(userId, customerId, ids);

        User user = userManager.findUser(userId);

        checkUser(user);

        synchronized (PAYMENT_APPLY_LOCK2)
        {
        	return paymentApplyManager.refPurchaseBack(user, customerId, ids);
        }
    }
	
    /**
     * @return the bankManager
     */
    public BankManager getBankManager()
    {
        return bankManager;
    }

    /**
     * @param bankManager
     *            the bankManager to set
     */
    public void setBankManager(BankManager bankManager)
    {
        this.bankManager = bankManager;
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
     * @return the paymentManager
     */
    public PaymentManager getPaymentManager()
    {
        return paymentManager;
    }

    /**
     * @param paymentManager
     *            the paymentManager to set
     */
    public void setPaymentManager(PaymentManager paymentManager)
    {
        this.paymentManager = paymentManager;
    }

    /**
     * @return the invoiceinsManager
     */
    public InvoiceinsManager getInvoiceinsManager()
    {
        return invoiceinsManager;
    }

    /**
     * @param invoiceinsManager
     *            the invoiceinsManager to set
     */
    public void setInvoiceinsManager(InvoiceinsManager invoiceinsManager)
    {
        this.invoiceinsManager = invoiceinsManager;
    }

    /**
     * @return the paymentApplyManager
     */
    public PaymentApplyManager getPaymentApplyManager()
    {
        return paymentApplyManager;
    }

    /**
     * @param paymentApplyManager
     *            the paymentApplyManager to set
     */
    public void setPaymentApplyManager(PaymentApplyManager paymentApplyManager)
    {
        this.paymentApplyManager = paymentApplyManager;
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
     * @return the stockPayApplyManager
     */
    public StockPayApplyManager getStockPayApplyManager()
    {
        return stockPayApplyManager;
    }

    /**
     * @param stockPayApplyManager
     *            the stockPayApplyManager to set
     */
    public void setStockPayApplyManager(StockPayApplyManager stockPayApplyManager)
    {
        this.stockPayApplyManager = stockPayApplyManager;
    }

    /**
     * @return the backPayApplyManager
     */
    public BackPayApplyManager getBackPayApplyManager()
    {
        return backPayApplyManager;
    }

    /**
     * @param backPayApplyManager
     *            the backPayApplyManager to set
     */
    public void setBackPayApplyManager(BackPayApplyManager backPayApplyManager)
    {
        this.backPayApplyManager = backPayApplyManager;
    }
}
