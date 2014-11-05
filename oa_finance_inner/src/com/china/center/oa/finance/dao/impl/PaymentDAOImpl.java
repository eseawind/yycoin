/**
 * File Name: PaymentDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-22<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.dao.impl;


import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.finance.bean.PaymentBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.PaymentDAO;
import com.china.center.oa.finance.vo.PaymentVO;


/**
 * PaymentDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-12-22
 * @see PaymentDAOImpl
 * @since 3.0
 */
public class PaymentDAOImpl extends BaseDAO<PaymentBean, PaymentVO> implements PaymentDAO
{
    public double sumNotUserByBankId(String bankId)
    {
        return this.jdbcOperation.queryForDouble(BeanTools.getSumHead(this.claz, "money")
                                                 + "where bankId= ? and useall = ?", bankId,
            FinanceConstant.PAYMENT_USEALL_INIT);
    }
}
