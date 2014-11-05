/**
 * File Name: OutBillDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-16<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.dao.impl;


import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.OutBillDAO;
import com.china.center.oa.finance.vo.OutBillVO;


/**
 * OutBillDAOImpl
 * 
 * @author ZHUZHU
 * @version 2011-1-16
 * @see OutBillDAOImpl
 * @since 3.0
 */
public class OutBillDAOImpl extends BaseDAO<OutBillBean, OutBillVO> implements OutBillDAO
{
    public double sumByCondition(ConditionParse condition)
    {
        return this.jdbcOperation.queryForDouble(BeanTools.getSumHead(claz, "moneys")
                                                 + condition.toString());
    }

    public int lockByCondition(ConditionParse condition)
    {
        return this.jdbcOperation.update("set OutBillBean.ulock = ? " + condition.toString(), claz,
            FinanceConstant.BILL_LOCK_YES);
    }

    public double sumByRefId(String refId)
    {
        return this.jdbcOperation.queryForDouble(
            BeanTools.getSumHead(claz, "moneys")
                + "where OutBillBean.stockId = ? and OutBillBean.type = ?", refId,
            FinanceConstant.OUTBILL_TYPE_OUTBACK);
    }

    public int countUnitInBill(String id)
    {
        return this.jdbcOperation.queryForInt(BeanTools.getCountHead(claz) + "where provideId = ?",
            id);
    }

    public boolean updateSrcMoneys(String id, double srcMoneys)
    {
        return this.jdbcOperation.updateField("srcMoneys", srcMoneys, id, claz) > 0;
    }
}
