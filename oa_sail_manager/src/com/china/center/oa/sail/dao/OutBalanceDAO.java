/**
 * File Name: OutBalanceDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.sail.bean.OutBalanceBean;
import com.china.center.oa.sail.vo.OutBalanceVO;
import com.china.center.oa.sail.wrap.ConfirmInsWrap;


/**
 * OutBalanceDAO
 * 
 * @author ZHUZHU
 * @version 2010-12-4
 * @see OutBalanceDAO
 * @since 3.0
 */
public interface OutBalanceDAO extends DAO<OutBalanceBean, OutBalanceVO>
{
    boolean updateInvoiceStatus(String id, double invoiceMoney, int invoiceStatus);

    boolean updateCheck(String id, int checkStatus, String check);

    /**
     * 更新已经付款金额
     * 
     * @param fullId
     * @param hadPay
     * @return
     */
    boolean updateHadPay(String id, double hadPay);

    boolean updatePay(String id, int pay);
    
    double sumByOutBalanceId(String refOutBalanceId);
    
    List<OutBalanceBean> queryExcludeSettleBack(String outId, int type);
    
    List<OutBalanceBean> queryByOutIdAndType(String outId, int type);
    
    List<OutBalanceBean> queryHasPayByOutId(String outId);
    
    List<OutBalanceBean> queryNoPayByOutId(String outId);
    
    boolean updateRebate(String id, int rebate);
    
    boolean updatePayInvoiceData(String fullId, int piType, int piMtype, String piDutyId, int piStatus);
    
    boolean initPayInvoiceData(String fullId);
    
    boolean updatePayInvoiceStatus(String fullId, int piStatus);
    
    List<ConfirmInsWrap> queryCanConfirmBalance(ConditionParse con);
    
    boolean updateHasConfirm(String fullId, int hasConfirm);
    
    boolean updateHasConfirmMoney(String fullId, double confirmMoney);
}
