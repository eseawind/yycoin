/**
 * File Name: TcpPayListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-9-13<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.listener;


import java.util.List;

import com.center.china.osgi.publics.ParentListener;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.tcp.bean.ExpenseApplyBean;
import com.china.center.oa.tcp.bean.RebateApplyBean;
import com.china.center.oa.tcp.bean.TravelApplyBean;


/**
 * TcpPayListener
 * 
 * @author ZHUZHU
 * @version 2011-9-13
 * @see TcpPayListener
 * @since 1.0
 */
public interface TcpPayListener extends ParentListener
{
    /**
     * 出差申请财务支付的监听
     * 
     * @param user
     * @param bean
     * @param outBillList
     * @throws MYException
     */
    void onPayTravelApply(User user, TravelApplyBean bean, List<OutBillBean> outBillList)
        throws MYException;

    /**
     * 报销在财务收付款的时候
     * 
     * @param user
     * @param bean
     * @param outBillList
     *            付款单
     * @param inBillList
     *            收款单
     * @throws MYException
     */
    void onPayExpenseApply(User user, ExpenseApplyBean bean, List<OutBillBean> outBillList,
                           List<InBillBean> inBillList)
        throws MYException;

    /**
     * 财务入账
     * 
     * @param user
     * @param bean
     * @param outBillList
     *            付款单
     * @param inBillList
     *            收款单
     * @param stafferIdList
     *            职员列表
     * @throws MYException
     */
    void onEndExpenseApply(User user, ExpenseApplyBean bean, List<String> taxIdList,
                           List<Long> moneyList, List<String> stafferIdList)
        throws MYException;

    /**
     * 报销结束
     * 
     * @param user
     * @param bean
     * @param check
     * @throws MYException
     */
    void onLastEndExpenseApply(User user, ExpenseApplyBean bean, String check)
        throws MYException;
    
    /**
     * 返利财务收付款的时候
     * 
     * @param user
     * @param bean
     * @param outBillList
     *            付款单
     * @throws MYException
     */
    void onPayRebateApply(User user, RebateApplyBean bean, List<OutBillBean> outBillList)
        throws MYException;
    
    /**
     * 财务入账
     * 
     * @param user
     * @param bean
     * @param outBillList
     *            付款单
     * @param inBillList
     *            收款单
     * @param stafferIdList
     *            职员列表
     * @throws MYException
     */
    void onEndRebateApply(User user, RebateApplyBean bean)
        throws MYException;
    
    /**
     * 中收申请提交（驳回要删除）
     * 
     * @param user
     * @param bean
     * @param outBillList
     * @throws MYException
     */
    void onSubmitMidTravelApply(User user, TravelApplyBean bean)
        throws MYException;
    
    /**
     * 中收申请提交（驳回要删除）
     * 
     * @param user
     * @param bean
     * @param type
     * @param outBillList
     * @throws MYException
     */
    void onSubmitMidTravelApply(User user, TravelApplyBean bean, int type)
        throws MYException;
    
    /**
     * 驳回要删除凭证
     * 
     * @param user
     * @param bean
     * @param outBillList
     * @throws MYException
     */
    void onRejectMidTravelApply(User user, TravelApplyBean bean)
        throws MYException;
}
