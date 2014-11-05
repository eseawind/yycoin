/**
 * File Name: PaymentDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-22<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.finance.bean.PaymentBean;
import com.china.center.oa.finance.vo.PaymentVO;


/**
 * PaymentDAO
 * 
 * @author ZHUZHU
 * @version 2010-12-22
 * @see PaymentDAO
 * @since 3.0
 */
public interface PaymentDAO extends DAO<PaymentBean, PaymentVO>
{
    /**
     * 没有认领的回款(useall为0的)
     * 
     * @param bankId
     * @return
     */
    double sumNotUserByBankId(String bankId);
}
