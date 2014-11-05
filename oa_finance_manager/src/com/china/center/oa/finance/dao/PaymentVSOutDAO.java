/**
 * File Name: PaymentVSOutDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.finance.vo.PaymentVSOutVO;
import com.china.center.oa.finance.vs.PaymentVSOutBean;


/**
 * PaymentVSOutDAO
 * 
 * @author ZHUZHU
 * @version 2010-12-26
 * @see PaymentVSOutDAO
 * @since 3.0
 */
public interface PaymentVSOutDAO extends DAO<PaymentVSOutBean, PaymentVSOutVO>
{
    int countByBill(String billId);
}
