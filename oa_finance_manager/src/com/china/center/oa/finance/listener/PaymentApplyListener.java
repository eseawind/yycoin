/**
 * File Name: PaymentListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-6-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.listener;


import java.util.List;

import com.center.china.osgi.publics.ParentListener;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.bean.PaymentApplyBean;
import com.china.center.oa.finance.bean.PaymentBean;
import com.china.center.oa.sail.bean.OutBean;


/**
 * PaymentListener
 * 
 * @author ZHUZHU
 * @version 2011-6-8
 * @see PaymentApplyListener
 * @since 3.0
 */
public interface PaymentApplyListener extends ParentListener
{
    /**
     * 回款转预收/销售单绑定(预收转应收)/坏账/预收转费用 通过(退领不再这个监听)
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    void onPassBean(User user, PaymentApplyBean bean)
        throws MYException;
    
    /**
     * 转账用内部款勾
     * @param user
     * @param bean
     */
    void onDrawTransfer(User user, PaymentBean bean, String outBillId)
    	throws MYException ;
    
    /**
     * 供应商预付 勾 采购退货
     * @param user
     * @param bill
     * @param outList
     * @throws MYException
     */
    void onStockBack(User user, List<PaymentApplyBean> applyList)
    throws MYException;
}
