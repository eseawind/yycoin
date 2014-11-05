/**
 * File Name: InBillDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.dao.impl;


import java.util.List;

import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.finance.vo.InBillVO;
import com.china.center.oa.finance.vo.PrePaymentWrap;
import com.china.center.tools.StringTools;


/**
 * InBillDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-12-26
 * @see InBillDAOImpl
 * @since 3.0
 */
public class InBillDAOImpl extends BaseDAO<InBillBean, InBillVO> implements InBillDAO
{
    public double sumByPaymentId(String paymentId)
    {
        return this.jdbcOperation.queryForDouble(BeanTools.getSumHead(claz, "moneys")
                                                 + "where InBillBean.paymentId = ?", paymentId);
    }

    public double sumByOutId(String outId)
    {
        return this.jdbcOperation.queryForDouble(
            BeanTools.getSumHead(claz, "moneys")
                + "where InBillBean.outId = ? and InBillBean.type = ?", outId,
            FinanceConstant.INBILL_TYPE_SAILOUT);
    }

    public double sumByCondition(ConditionParse condition)
    {
        return this.jdbcOperation.queryForDouble(BeanTools.getSumHead(claz, "moneys")
                                                 + condition.toString());
    }

    public int lockByCondition(ConditionParse condition)
    {
        return this.jdbcOperation.update("set InBillBean.ulock = ? " + condition.toString(), claz,
            FinanceConstant.BILL_LOCK_YES);
    }

    public double sumByOutBalanceId(String outBalanceId)
    {
        return this.jdbcOperation.queryForDouble(BeanTools.getSumHead(claz, "moneys")
                                                 + "where InBillBean.outBalanceId = ?",
            outBalanceId);
    }

    public int countUnitInBill(String id)
    {
        return this.jdbcOperation.queryForInt(
            BeanTools.getCountHead(claz) + "where customerId = ?", id);
    }

    public boolean updateSrcMoneys(String id, double srcMoneys)
    {
        return this.jdbcOperation.updateField("srcMoneys", srcMoneys, id, claz) > 0;
    }

    public boolean updateUpdateId(String id, int updateId)
    {
        return this.jdbcOperation.updateField("updateId", updateId, id, claz) > 0;
    }

    public boolean chageBillToTran(String srcId, String destId, String customerId)
    {
        this.jdbcOperation.update(
            "set ownerId = ? where ownerId = ? and status = ? and customerId = ?", claz, destId,
            srcId, FinanceConstant.INBILL_STATUS_NOREF, customerId);

        return true;
    }

    /**
     * 获取关联单子的坏账勾款金额，含关联单的坏账及历史坏账账户
     * {@inheritDoc}
     */
    @Override
    public double sumBadDebtsByOutId(String outId) {

        return this.jdbcOperation.queryForDouble(
            BeanTools.getSumHead(claz, "moneys")
                + "where InBillBean.outId = ? and InBillBean.type = ? and InBillBean.createType = ?", outId,
            FinanceConstant.INBILL_TYPE_BADOUT, FinanceConstant.BILL_CREATETYPE_HANDBADOUT);
    
    }
    
    public int countSelfCustomerInbillByCondtion(String stafferId, ConditionParse condition)
    {
        return jdbcOperation
            .queryObjectsBySql(getLastQuerySelfSql(stafferId, condition))
            .getCount();
    }
    
    public List<PrePaymentWrap> querySelfCustomerInbillByCondtion(String stafferId,
            ConditionParse condition,
            PageSeparate page)
	{
		return jdbcOperation.queryObjectsBySqlAndPageSeparate(getLastQuerySelfSql(stafferId,
				condition), page, PrePaymentWrap.class);
	}
    
    public List<PrePaymentWrap> querySelfCustomerInbill(String stafferId, String customerId)
	{
    	String sql = getQuerySelfSql(stafferId) + " and InbillBean.customerId = ? group by InbillBean.customerId, t2.name, t3.name";
    	
    	return this.jdbcOperation.queryObjectsBySql(sql, customerId).list(PrePaymentWrap.class);
	}
    
    private String getLastQuerySelfSql(String stafferId, ConditionParse condition)
    {
        ConditionParse newConditionParse = new ConditionParse();

        newConditionParse.setCondition(condition.toString());

        newConditionParse.removeWhereStr();

        if (StringTools.isNullOrNone(newConditionParse.toString()))
        {
            newConditionParse.addString("1 = 1");
        }

        return getQuerySelfSql(stafferId) + " and " + newConditionParse.toString();
    }
    
    private String getQuerySelfSql(String stafferId)
    {
    	return "select InbillBean.customerId customerId, t2.name customerName, sum(InbillBean.moneys) totalMoney,"
				+ " sum(case when InbillBean.mtype=1 and InbillBean.freeze = 0 then InbillBean.moneys else 0 end) commonTotalMoney,"
		    	+ " sum(case when InbillBean.mtype=0 and InbillBean.freeze = 0 then InbillBean.moneys else 0 end) manageTotalMoney,"	 
		    	+ " sum(case InbillBean.freeze when 1 then InbillBean.moneys else 0 end) freezeTotalMoney, t3.name stafferName"	
		    	+ " from t_center_inbill InbillBean"	
		    	+ " left outer join t_center_unit t2 on (InbillBean.customerId = t2.id)"
		    	+ " left outer join t_center_oastaffer t3 on (InbillBean.ownerId = t3.id)"
		    	+ " where InbillBean.ownerId = '" + stafferId + "'"
		    	+ " AND InbillBean.status = 2 AND InbillBean.moneys >= 0.01";
    }
}
