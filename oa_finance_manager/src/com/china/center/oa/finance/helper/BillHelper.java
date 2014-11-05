/**
 * File Name: BillHelper.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-4-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.helper;


import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.publics.constant.PublicConstant;


/**
 * BillHelper
 * 
 * @author ZHUZHU
 * @version 2011-4-10
 * @see BillHelper
 * @since 3.0
 */
public abstract class BillHelper
{
    /**
     * 是否是预收
     * 
     * @param inBill
     * @return
     */
    public static boolean isPreInBill(InBillBean inBill)
    {
        if (inBill.getType() == FinanceConstant.INBILL_TYPE_SAILOUT
            && inBill.getStatus() != FinanceConstant.INBILL_STATUS_PAYMENTS)
        {
            return true;
        }

        return false;
    }

    /**
     * 收款单的状态变成未核对
     * 
     * @param inBill
     */
    public static void initInBillCheckStatus(InBillBean inBill)
    {
        inBill.setCheckStatus(PublicConstant.CHECK_STATUS_INIT);
    }
}
