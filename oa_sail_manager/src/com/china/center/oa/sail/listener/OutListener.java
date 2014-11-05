/**
 * File Name: OutListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.listener;


import java.util.List;

import com.center.china.osgi.publics.ParentListener;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.wrap.ResultBean;
import com.china.center.oa.sail.bean.OutBalanceBean;
import com.china.center.oa.sail.bean.OutBean;


/**
 * OutListener
 * 
 * @author ZHUZHU
 * @version 2011-1-9
 * @see OutListener
 * @since 3.0
 */
public interface OutListener extends ParentListener
{
    /**
     * 驳回监听
     * 
     * @param bean
     * @throws MYException
     */
    void onReject(User user, OutBean bean)
        throws MYException;

    /**
     * 删除监听
     * 
     * @param bean
     * @throws MYException
     */
    void onDelete(User user, OutBean bean)
        throws MYException;

    /**
     * 通过监听
     * 
     * @param bean
     * @throws MYException
     */
    void onPass(User user, OutBean bean)
        throws MYException;

    /**
     * 库单对产品库存异动了(销售/入库包括调拨都在里面)
     * 
     * @param bean
     * @throws MYException
     */
    void onConfirmOutOrBuy(User user, OutBean bean)
        throws MYException;

    /**
     * 结算单/结算退货单 通过
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    void onOutBalancePass(User user, OutBalanceBean bean)
        throws MYException;

    /**
     * 总部核对监听
     * 
     * @param bean
     * @throws MYException
     */
    void onCheck(User user, OutBean bean)
        throws MYException;

    /**
     * 取消坏账
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    void onCancleBadDebts(User user, OutBean bean)
        throws MYException;

    /**
     * 确认坏账
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    void onConfirmBadDebts(User user, OutBean bean)
        throws MYException;

    /**
     * 销售单移交
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    void onTranOutList(User user, List<OutBean> outList, StafferBean targerStaffer)
        throws MYException;

    /**
     * 确认收款
     * 
     * @param bean
     * @throws MYException
     */
    ResultBean onHadPay(User user, OutBean bean);

    /**
     * 销售单还剩余未付款的金额(这里坏账是在内的),仅仅是普通销售的
     * 
     * @param user
     * @param fullId
     * @return
     */
    double outNeedPayMoney(User user, String fullId);
    
    void onOutRepairePass(User user, OutBean oldOutBean, OutBean newOutBean) throws MYException;
    
    /**
     * 销售单移交(已付款)
     * @param user
     * @param out
     * @throws MYException
     */
    void onTranOut(User user, OutBean out, StafferBean targerStaffer) throws MYException;
}
