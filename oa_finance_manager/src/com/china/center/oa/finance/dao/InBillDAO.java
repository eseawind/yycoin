/**
 * File Name: InBillDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.vo.InBillVO;
import com.china.center.oa.finance.vo.PrePaymentWrap;


/**
 * InBillDAO
 * 
 * @author ZHUZHU
 * @version 2010-12-26
 * @see InBillDAO
 * @since 3.0
 */
public interface InBillDAO extends DAO<InBillBean, InBillVO>
{
    double sumByPaymentId(String paymentId);

    /**
     * sumByOutId(这里包括申请关联的)
     * 
     * @param outId
     * @return
     */
    double sumByOutId(String outId);

    /**
     * sumByOutId(这里包括申请关联的)
     * 
     * @param outId
     * @return
     */
    double sumByOutBalanceId(String outBalanceId);

    double sumByCondition(ConditionParse condition);

    int lockByCondition(ConditionParse condition);

    int countUnitInBill(String id);

    boolean updateSrcMoneys(String id, double srcMoneys);

    boolean updateUpdateId(String id, int updateId);

    boolean chageBillToTran(String srcId, String destId, String customerId);
    
    double sumBadDebtsByOutId(String outId);
    
    int countSelfCustomerInbillByCondtion(String stafferId, ConditionParse condition);
    
    List<PrePaymentWrap> querySelfCustomerInbillByCondtion(String stafferId,
            ConditionParse condition,
            PageSeparate page);
    
    List<PrePaymentWrap> querySelfCustomerInbill(String stafferId, String customerId);
    
}
