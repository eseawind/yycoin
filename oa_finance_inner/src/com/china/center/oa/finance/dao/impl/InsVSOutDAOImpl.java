/**
 * File Name: InsVSOutDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-1<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.dao.impl;


import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.finance.dao.InsVSOutDAO;
import com.china.center.oa.finance.vs.InsVSOutBean;


/**
 * InsVSOutDAOImpl
 * 
 * @author ZHUZHU
 * @version 2011-1-1
 * @see InsVSOutDAOImpl
 * @since 3.0
 */
public class InsVSOutDAOImpl extends BaseDAO<InsVSOutBean, InsVSOutBean> implements InsVSOutDAO
{
    @Override
    public double getSumMoneysByBaseId(String baseId) {
        
        String sql = "select sum(t1.moneys) from T_CENTER_VS_INSOUT t1, T_CENTER_INVOICEINS t2 where t1.insid = t2.id" +
        		" and t2.status <> 98 and t1.baseid = ?";
        
        return this.jdbcOperation.queryForDouble(sql, baseId);
    }

	@Override
	public double sumOutHasInvoiceStatusNotEnd(String outId)
	{
		String sql = "select sum(t2.moneys) from T_CENTER_INVOICEINS t1, T_CENTER_VS_INSOUT t2 " +
						"where t1.id= t2.insid and t2.outid = ? and t1.status in (1, 2) and t1.otype = 0";

		return this.jdbcOperation.queryForDouble(sql, outId);
	}

	@Override
	public double sumOutBlanceHasInvoiceStatusNotEnd(String balanceId)
	{
		String sql = "select sum(t2.moneys) from T_CENTER_INVOICEINS t1, T_CENTER_VS_INSOUT t2 " +
						"where t1.id= t2.insid and t2.outBalanceId = ? and t1.status in (1, 2) and t1.otype = 0";

		return this.jdbcOperation.queryForDouble(sql, balanceId);
	}
}
