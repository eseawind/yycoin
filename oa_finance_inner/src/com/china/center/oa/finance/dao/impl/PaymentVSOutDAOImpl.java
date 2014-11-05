/**
 * File Name: PaymentVSOutDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.dao.impl;


import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.finance.dao.PaymentVSOutDAO;
import com.china.center.oa.finance.vo.PaymentVSOutVO;
import com.china.center.oa.finance.vs.PaymentVSOutBean;


/**
 * PaymentVSOutDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-12-26
 * @see PaymentVSOutDAOImpl
 * @since 3.0
 */
public class PaymentVSOutDAOImpl extends BaseDAO<PaymentVSOutBean, PaymentVSOutVO> implements PaymentVSOutDAO
{
    public int countByBill(String billId)
    {
        String sql = BeanTools.getCountHead(claz) + "where billId = ?";

        return this.jdbcOperation.queryForInt(sql, billId);
    }

}
