/**
 * File Name: PayApplyDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.dao.impl;


import java.util.List;

import com.china.center.common.MYException;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.finance.bean.PaymentApplyBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.PaymentApplyDAO;
import com.china.center.oa.finance.vo.PaymentApplyVO;


/**
 * PayApplyDAOImpl
 * 
 * @author ZHUZHU
 * @version 2011-1-8
 * @see PaymentApplyDAOImpl
 * @since 3.0
 */
public class PaymentApplyDAOImpl extends BaseDAO<PaymentApplyBean, PaymentApplyVO> implements PaymentApplyDAO
{
    public int countApplyByOutId(String outId)
    {
        return this.jdbcOperation
            .queryForInt(
                "select count(t1.id) from T_CENTER_PAYAPPLY t1, T_CENTER_VS_OUTPAY t2 where t1.id = t2.parentId and t1.status = ? and t2.outId = ?",
                FinanceConstant.PAYAPPLY_STATUS_INIT, outId);
    }

    /**
     * 待审批的收款单
     * {@inheritDoc}
     */
    @Override
    public int countApplyByOutId2(String outId) 
    {        
        return this.jdbcOperation
        .queryForInt(
            "select count(t1.id) from T_CENTER_PAYAPPLY t1, T_CENTER_VS_OUTPAY t2 where t1.id = t2.parentId and t1.status in (0,3) and t2.outId = ?",
            outId);
    }
    
    /**
     * 查询跟销售单关联的收款单
     */
    public PaymentApplyBean queryPaymentApplyBeanByfullId(String fullId)throws MYException
    {
        String sql = "select t1.* from T_CENTER_PAYAPPLY t1, T_CENTER_VS_OUTPAY t2 where t1.id = t2.parentId and t1.status in (0,3) and t2.outId = ?";
        List<PaymentApplyBean> list = this.jdbcOperation.queryForListBySql(sql,PaymentApplyBean.class, fullId);
        if(list !=null && list.size() > 0 )
        {
        	return list.get(0);
        }
        return null;
    }
    
	public double sumMoneysStatusNotEnd(String billId)
	{
		String sql = "select sum(t2.moneys) from T_CENTER_PAYAPPLY t1, T_CENTER_VS_OUTPAY t2 " +
				"where t1.id = t2.parentId and t1.status <> 1 and t1.type in (1,3) and t2.billId = ?";
		
		return jdbcOperation.queryForDouble(sql, billId);
	}
}
