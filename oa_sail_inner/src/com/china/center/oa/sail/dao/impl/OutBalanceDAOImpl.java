/**
 * File Name: OutBalanceDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.dao.impl;


import java.util.List;

import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.sail.bean.OutBalanceBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.OutBalanceDAO;
import com.china.center.oa.sail.vo.OutBalanceVO;
import com.china.center.oa.sail.wrap.ConfirmInsWrap;


/**
 * OutBalanceDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-12-4
 * @see OutBalanceDAOImpl
 * @since 3.0
 */
public class OutBalanceDAOImpl extends BaseDAO<OutBalanceBean, OutBalanceVO> implements OutBalanceDAO
{
    public boolean updateInvoiceStatus(String id, double invoiceMoney, int invoiceStatus)
    {
        String sql = BeanTools.getUpdateHead(claz)
                     + "set invoiceMoney = ?, invoiceStatus = ? where id = ?";

        jdbcOperation.update(sql, invoiceMoney, invoiceStatus, id);

        return true;
    }

    public boolean updateHadPay(String id, double hadPay)
    {
        String sql = BeanTools.getUpdateHead(claz) + "set payMoney = ? where id = ?";

        jdbcOperation.update(sql, hadPay, id);

        return true;
    }

    public boolean updatePay(String id, int pay)
    {
        String sql = BeanTools.getUpdateHead(claz) + "set pay = ? where id = ?";

        jdbcOperation.update(sql, pay, id);

        return true;
    }

    public boolean updateCheck(String id, int checkStatus, String checks)
    {
        String sql = BeanTools.getUpdateHead(claz) + "set checkStatus = ?, checks = ? where id = ?";

        jdbcOperation.update(sql, checkStatus, checks, id);

        return true;
    }
    
    public double sumByOutBalanceId(String refOutBalanceId)
    {
        return this.jdbcOperation.queryForDouble(BeanTools.getSumHead(claz, "total")
                                                 + "where OutBalanceBean.refOutBalanceId = ? and status = 99", refOutBalanceId);
    }

    /**
     * 不包含 结算退货数据
     * {@inheritDoc}
     */
	public List<OutBalanceBean> queryExcludeSettleBack(String outId, int type)
	{
		ConditionParse con = new ConditionParse();
		
		con.addWhereStr();
		
		con.addCondition("OutBalanceBean.outId", "=", outId);
		
		con.addIntCondition("OutBalanceBean.type", "<>", type);
		
		if (type == 0){
			con.addIntCondition("OutBalanceBean.status", "=", OutConstant.OUTBALANCE_STATUS_END);	
		}else
			con.addCondition(" and OutBalanceBean.status in (1, 99)");
		
		return this.queryEntityBeansByCondition(con);
	}

	public List<OutBalanceBean> queryByOutIdAndType(String outId, int type)
	{
		return this.queryEntityBeansByCondition("where outId= ? and type = ?", outId, type);
	}

	/**
	 * 结算单且勾过款
	 * {@inheritDoc}
	 */
	public List<OutBalanceBean> queryHasPayByOutId(String outId)
	{
		return this.queryEntityBeansByCondition("where outId = ? and type = 0 and payMoney > 0 and status = 1", outId);
	}

	/**
	 * 结算单且未勾过款
	 * {@inheritDoc}
	 */
	public List<OutBalanceBean> queryNoPayByOutId(String outId)
	{
		return this.queryEntityBeansByCondition("where outId = ? and type = 0 and payMoney = 0 and status = 1", outId);
	}

	public boolean updateRebate(String id, int rebate)
	{
        String sql = "update t_center_outBalance set hasRebate = ? where id = ?";

        int i = jdbcOperation.update(sql, rebate, id);

        return i != 0;
	}
	
	@Override
	public boolean updatePayInvoiceData(String fullId, int piType, int piMtype,
			String piDutyId, int piStatus)
	{
        String sql = BeanTools.getUpdateHead(claz)
        				+ "set piType = ?, piMtype = ?, piDutyId = ?, piStatus = ? where id = ?";

		jdbcOperation.update(sql, piType, piMtype, piDutyId, piStatus, fullId);
		
		return true;
	}

	@Override
	public boolean initPayInvoiceData(String fullId)
	{
		 String sql = BeanTools.getUpdateHead(claz)
						+ "set piType = ?, piMtype = ?, piDutyId = ?, piStatus = ? where id = ?";

		 jdbcOperation.update(sql, -1, -1, "", -1, fullId);
		
		return true;
	}
	
	public boolean updatePayInvoiceStatus(String fullId, int piStatus)
	{
        String sql = BeanTools.getUpdateHead(claz)
        				+ "set piStatus = ? where id = ?";

		jdbcOperation.update(sql, piStatus, fullId);
		
		return true;
	}

	@Override
	public List<ConfirmInsWrap> queryCanConfirmBalance(ConditionParse con)
	{
		String sql = "select OutBalanceBean.id as fullId, 98 as outtype, OutBean.invoiceid as invoiceId, OutBalanceBean.outId as origId, OutBean.customerName, (OutBalanceBean.total - OutBalanceBean.hasConfirmInsMoney) as mayConfirmMoney " +
					" from t_center_outbalance OutBalanceBean, t_center_out OutBean " +
					" where OutBalanceBean.outid = OutBean.fullid and OutBean.outtime >= '2011-04-01'" +
					" and OutBalanceBean.type in (1,2) and OutBalanceBean.status = 99 and OutBalanceBean.hasConfirm = 0 " + con.toString();

		return this.jdbcOperation.queryObjectsBySql(sql).list(ConfirmInsWrap.class);
	}
	
	public boolean updateHasConfirm(String fullId, int hasConfirm)
	{
        String sql = BeanTools.getUpdateHead(claz)
						+ "set hasConfirm = ? where id = ?";

		jdbcOperation.update(sql, hasConfirm, fullId);
		
		return true;
	}


	@Override
	public boolean updateHasConfirmMoney(String fullId, double confirmMoney)
	{
        String sql = BeanTools.getUpdateHead(claz)
						+ "set hasConfirmInsMoney = ? where id = ?";

		jdbcOperation.update(sql, confirmMoney, fullId);
		
		return true;
	}
}
