/**
 * File Name: PaymentManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-22<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.manager;


import java.util.List;

import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.PaymentApplyBean;
import com.china.center.oa.finance.listener.PaymentApplyListener;
import com.china.center.oa.finance.vo.BatchSplitInBillWrap;


/**
 * PaymentManager
 * 
 * @author ZHUZHU
 * @version 2010-12-22
 * @see PaymentApplyManager
 * @since 3.0
 */
public interface PaymentApplyManager extends ListenerManager<PaymentApplyListener>
{
    /**
     * 申请付款
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean addPaymentApply(User user, PaymentApplyBean bean)
        throws MYException;

    boolean updatePaymentApply(User user, PaymentApplyBean bean)
        throws MYException;

    boolean deletePaymentApply(User user, String id)
        throws MYException;

    /**
     * 通过(需要同步，其实是结束的方法)
     * 
     * @param stafferId
     * @param id
     * @return
     * @throws MYException
     */
    boolean passPaymentApply(User user, String id, String reason,String description)
        throws MYException;

    /**
     * 通过待稽核
     * 
     * @param user
     * @param id
     * @param reason
     * @return
     * @throws MYException
     */
    boolean passCheck(User user, String id, String reason)
        throws MYException;

    /**
     * 驳回(需要同步)
     * 
     * @param stafferId
     * @param id
     * @return
     * @throws MYException
     */
    boolean rejectPaymentApply(User user, String id, String reason)
        throws MYException;

    /**
     * 总部核对
     * 
     * @param stafferId
     * @param id
     * @return
     * @throws MYException
     */
    boolean checkPaymentApply(User user, String id, String checks, String refId)
        throws MYException;
    
    void processOut(User user, String outId, String outBalanceId)
    	throws MYException;
    
    boolean addPaymentApply5(User user, PaymentApplyBean bean)
    throws MYException;
    
    boolean batchSplitInBill(User user, String billId, List<BatchSplitInBillWrap> list)
	throws MYException;
    
    boolean addPaymentApply6(User user, PaymentApplyBean bean)
    throws MYException;
    
    /**
     * 
     * @param outId
     * @param outBalanceId
     * @param dutyList
     * @param status 1 表示状态直接为结束
     * @throws MYException
     */
    void processPayInvoice(String outId, String outBalanceId,
			List<String> dutyList, int status) throws MYException;
    
    /**
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean addTransferApply(User user, PaymentApplyBean bean)
    throws MYException;
    
    boolean addDrawProviderApply(User user, PaymentApplyBean bean)
    throws MYException;
    
    boolean refPurchaseBack(User user, String customerId, String ids)
    throws MYException;
}
