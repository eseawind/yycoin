/**
 * File Name: PayApplyDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.dao;


import com.china.center.common.MYException;
import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.finance.bean.PaymentApplyBean;
import com.china.center.oa.finance.vo.PaymentApplyVO;


/**
 * PayApplyDAO
 * 
 * @author ZHUZHU
 * @version 2011-1-8
 * @see PaymentApplyDAO
 * @since 3.0
 */
public interface PaymentApplyDAO extends DAO<PaymentApplyBean, PaymentApplyVO>
{
    int countApplyByOutId(String outId);
    
    int countApplyByOutId2(String outId);
    
    PaymentApplyBean queryPaymentApplyBeanByfullId(String fullId)throws MYException;
    
    double sumMoneysStatusNotEnd(String billId);
}
