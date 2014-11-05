/**
 * File Name: FinanceFacade.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.facade;


import java.util.List;

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
import com.china.center.oa.finance.vo.BatchSplitInBillWrap;
import com.china.center.oa.finance.vs.StockPayVSPreBean;


/**
 * FinanceFacade
 * 
 * @author ZHUZHU
 * @version 2010-12-12
 * @see FinanceFacade
 * @since 3.0
 */
public interface FinanceFacade
{
    /**
     * 回款操作锁
     */
    Object PAYMENT_LOCK = new Object();

    Object PAYMENT_APPLY_LOCK = new Object();

    Object INBILL_LOCK = new Object();

    Object OUTBILL_LOCK = new Object();

    Object INVOICEINS_LOCK = new Object();

    Object STOCKPAYAPPLY_LOCK = new Object();
    
    Object STOCKPREPAYAPPLY_LOCK = new Object();
    
    Object STOCKPREREFPAY_LOCK = new Object();

    Object BACKPAYAPPLY_LOCK = new Object();

    Object BILLAPPLY_LOCK = new Object();
    
    Object INVOICEINS_CONFIRM_LOCK = new Object();
    
    Object INVOICE_BIND_LOCK = new Object();
    
    Object PAYMENT_APPLY_LOCK1 = new Object();
    
    Object PAYMENT_APPLY_LOCK2 = new Object();

    boolean addBankBean(String userId, BankBean bean)
        throws MYException;

    boolean updateBankBean(String userId, BankBean bean)
        throws MYException;

    boolean deleteBankBean(String userId, String id)
        throws MYException;

    boolean addPaymentBean(String userId, PaymentBean bean)
        throws MYException;

    boolean addPaymentBeanList(String userId, List<PaymentBean> beanList)
        throws MYException;

    boolean updatePaymentBean(String userId, PaymentBean bean)
        throws MYException;

    boolean deletePaymentBean(String userId, String id)
        throws MYException;

    /**
     * batchDeletePaymentBean
     * 
     * @param userId
     * @param id
     * @return
     * @throws MYException
     */
    boolean batchDeletePaymentBean(String userId, String id)
        throws MYException;

    /**
     * 领取回款(需要同步)
     * 
     * @param stafferId
     * @param id
     * @return
     * @throws MYException
     */
    boolean drawPaymentBean(String stafferId, String id, String customerId,String description)
        throws MYException;

    /**
     * 退领(需要同步)
     * 
     * @param stafferId
     * @param id
     * @return
     * @throws MYException
     */
    boolean dropPaymentBean(String userId, String id)
        throws MYException;

    boolean addInvoiceinsBean(String userId, InvoiceinsBean bean)
        throws MYException;

    boolean deleteInvoiceinsBean(String userId, String id)
        throws MYException;

    boolean addPaymentApply(String userId, PaymentApplyBean bean)
        throws MYException;

    boolean updatePaymentApply(String userId, PaymentApplyBean bean)
        throws MYException;

    boolean deletePaymentApply(String userId, String id)
        throws MYException;

    /**
     * 通过(需要同步)
     * 
     * @param stafferId
     * @param id
     * @return
     * @throws MYException
     */
    boolean passPaymentApply(String userId, String id, String reason,String description)
        throws MYException;

    /**
     * 稽核通过
     * 
     * @param userId
     * @param id
     * @param stafferId
     * @param reason
     * @return
     * @throws MYException
     */
    boolean passCheck(String userId, String id, String reason,String description)
        throws MYException;

    /**
     * 驳回(需要同步)
     * 
     * @param stafferId
     * @param id
     * @return
     * @throws MYException
     */
    boolean rejectPaymentApply(String userId, String id, String reason)
        throws MYException;

    /**
     * 总部核对
     * 
     * @param userId
     * @param id
     * @param checks
     * @param refId
     * @return
     * @throws MYException
     */
    boolean checkPaymentApply(String userId, String id, String checks, String refId)
        throws MYException;

    /**
     * 总部核对
     * 
     * @param userId
     * @param id
     * @param checks
     * @param refId
     * @return
     * @throws MYException
     */
    boolean checkInvoiceinsBean2(String userId, String id, String checks, String refId)
        throws MYException;

    /**
     * 分拆
     * 
     * @param user
     * @param id
     * @param newMoney
     * @return
     * @throws MYException
     */
    String splitInBillBean(String userId, String id, double newMoney)
        throws MYException;

    boolean addInBillBean(String userId, InBillBean bean)
        throws MYException;

    boolean deleteInBillBean(String userId, String id)
        throws MYException;

    /**
     * addOutBillBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean addOutBillBean(String userId, OutBillBean bean)
        throws MYException;

    /**
     * deleteOutBillBean
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean deleteOutBillBean(String userId, String id)
        throws MYException;

    /**
     * 通过(需要同步)
     * 
     * @param stafferId
     * @param id
     * @return
     * @throws MYException
     */
    boolean submitStockPayApply(String userId, String id, double payMoney, String reason)
        throws MYException;
    
    boolean submitStockPayApply(String userId, String id, String reason, StockPayApplyBean bean)
    throws MYException;

    /**
     * 驳回(需要同步)
     * 
     * @param stafferId
     * @param id
     * @return
     * @throws MYException
     */
    boolean rejectStockPayApply(String userId, String id, String reason)
        throws MYException;

    /**
     * 强制关闭
     * 
     * @param userId
     * @param id
     * @param reason
     * @return
     * @throws MYException
     */
    boolean closeStockPayApply(String userId, String id, String reason)
        throws MYException;

    boolean passStockPayByCEO(String userId, String id, String reason)
        throws MYException;

    boolean endStockPayBySEC(String userId, String id, String reason, List<OutBillBean> outBillList)
        throws MYException;

    boolean addBackPayApplyBean(String userId, BackPayApplyBean bean)
        throws MYException;

    boolean passBackPayApplyBean(String userId, String id, String reason)
        throws MYException;

    boolean rejectBackPayApplyBean(String userId, String id, String reason)
        throws MYException;

    boolean deleteBackPayApplyBean(String userId, String id)
        throws MYException;

    boolean endBackPayApplyBean(String userId, String id, String reason,
                                List<OutBillBean> outBillList)
        throws MYException;

    /**
     * 通过转账
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean passTransferOutBillBean(String userId, String id)
        throws MYException;

    /**
     * 驳回转账
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean rejectTransferOutBillBean(String userId, String id)
        throws MYException;

    boolean passInvoiceinsBean(String userId, InvoiceinsBean bean, String reason)
        throws MYException;

    boolean checkInvoiceinsBean(String userId, String id, String reason)
        throws MYException;

    boolean rejectInvoiceinsBean(String userId, String id, String reason)
        throws MYException;

    boolean updateInBillBeanChecks(String userId, String id, String checks)
        throws MYException;

    boolean updateOutBillBeanChecks(String userId, String id, String checks)
        throws MYException;

    /**
     * 合并付款
     * 
     * @param stafferId
     * @param id
     * @return
     * @throws MYException
     */
    boolean composeStockPayApply(String userId, List<String> idList)
        throws MYException;
    
    /**
     * 通过转账
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean passTransferOutBillBean1(String userId, String id)
        throws MYException;
    
    boolean rejectTransferOutBillBean1(String userId, String id)
    throws MYException;
    
    boolean confirmInvoice(String userId, String id) 
    throws MYException;
    
    boolean confirmPay(String userId, String id)
    throws MYException;
    
    boolean backInvoiceins(String userId, String id)
    throws MYException;
    
    String freezeInBillBean(String userId, String id, double newMoney)
    throws MYException;
    
    boolean addPaymentApply5(String userId, PaymentApplyBean bean)
    throws MYException;
    
    boolean batchSplitInBill(String userId, String billId, List<BatchSplitInBillWrap> list)
    throws MYException;
    
    boolean addPaymentApply6(String userId, PaymentApplyBean bean)
    throws MYException;
    
    /**
     * 驳回(需要同步)
     * 
     * @param stafferId
     * @param id
     * @return
     * @throws MYException
     */
    boolean rejectStockPrePayApply(String userId, String id, String reason)
        throws MYException;

    boolean passStockPrePayByCEO(String userId, String id, String reason)
        throws MYException;

    boolean endStockPrePayBySEC(String userId, String id, String reason, List<OutBillBean> outBillList)
        throws MYException;
     
    boolean addStockPrePayApply(String userId, StockPrePayApplyBean bean)
    throws MYException;
    
    boolean updateStockPrePayApply(String userId, StockPrePayApplyBean bean)
    throws MYException;
    
    boolean deleteStockPrePayApply(String userId, String id)
    throws MYException;
    
    String refStockPrePay(String userId, List<StockPayVSPreBean> vsList)
    throws MYException;
    
    boolean refConfirmInvoice(String userId, List<InvoiceBindOutBean> vsList)
    throws MYException;
    
    boolean refInvoice(String userId, List<InvoiceBindOutBean> vsList)
    throws MYException;
    
    boolean addTransferApply(String userId, PaymentApplyBean bean)
    throws MYException;
    
    boolean addDrawProviderApply(String userId, PaymentApplyBean bean)
    throws MYException;
    
    boolean refPurchaseBack(String userId, String customerId, String ids)
    throws MYException;
}
