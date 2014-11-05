/**
 * File Name: OutStatDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customervssail.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;

import com.china.center.jdbc.inter.IbatisDaoSupport;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.customer.constant.CreditConstant;
import com.china.center.oa.customervssail.dao.OutStatDAO;
import com.china.center.oa.publics.helper.OATools;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.tools.ListTools;
import com.china.center.tools.TimeTools;


/**
 * OutStatDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see OutStatDAOImpl
 * @since 1.0
 */
public class OutStatDAOImpl extends BaseDAO<OutBean, OutBean> implements OutStatDAO
{
    /**
     * 2010-03-01开始使用
     */
    private static final String BEGINDATE = "2010-03-01";

    private IbatisDaoSupport ibatisDaoSupport = null;

    /**
     * 查询没有进行统计的销售单(2009-12-01开始统计的)
     * 
     * @param cid
     * @param beginTime
     * @return
     */
    public List<OutBean> queryNoneStatByCid(String cid)
    {
        ConditionParse condtition = new ConditionParse();

        condtition.addWhereStr();

        String beginDate = BEGINDATE;

        // 只统计前6个月的数据(这里不能逾越财务年度的开始)
        String fbeginDate = TimeTools.getDateShortString( -6 * 30);

        if (beginDate.compareTo(fbeginDate) < 0)
        {
            beginDate = fbeginDate;
        }

        condtition.addCondition("outTime", ">=", beginDate);

        // 销售单
        condtition.addIntCondition("type", "=", 0);

        // 只能是销售出库
        condtition.addIntCondition("outType", "=", 0);

        // 未信用处理的单子
        condtition.addIntCondition("reserve1", "=", CreditConstant.CREDIT_OUT_INIT);

        condtition.addCondition("customerId", "=", cid);

        condtition.addCondition("and status in (3, 4)");

        condtition.addCondition("order by id asc");

        return this.queryEntityBeansByCondition(condtition);
    }

    /**
     * listCustomerIdList
     * 
     * @return
     */
    public List<String> listCustomerIdList()
    {
        ConditionParse condtition = new ConditionParse();

        condtition.addWhereStr();

        String beginDate = BEGINDATE;

        // 只统计前13个月的数据
        String fbeginDate = TimeTools.getDateShortString( -13 * 30);

        if (beginDate.compareTo(fbeginDate) < 0)
        {
            beginDate = fbeginDate;
        }

        condtition.addCondition("outTime", ">=", beginDate);

        // 销售单
        condtition.addIntCondition("type", "=", 0);

        // 只能是销售出库
        condtition.addIntCondition("outType", "=", 0);

        final List<String> list = new LinkedList<String>();

        this.jdbcOperation.query("select distinct(t.customerId) from t_center_out t "
                                 + condtition.toString(), new RowCallbackHandler()
        {
            public void processRow(ResultSet rs)
                throws SQLException
            {
                String ccid = rs.getString(1);

                if ( !list.contains(ccid))
                {
                    list.add(ccid);
                }
            }
        });

        this.jdbcOperation.query("select distinct(t.cid) from t_center_vs_curcre t ",
            new RowCallbackHandler()
            {
                public void processRow(ResultSet rs)
                    throws SQLException
                {
                    String ccid = rs.getString(1);

                    if ( !list.contains(ccid))
                    {
                        list.add(ccid);
                    }
                }
            });

        return list;
    }

    /**
     * findNearestById
     * 
     * @param fullId
     * @param cid
     * @return
     */
    public OutBean findNearestById(String id, String cid)
    {
        // 查询销售单且最近的一个
        List<OutBean> list = this.jdbcOperation
            .queryObjects(
                "where 1= 1 and type = ? and outType = ? and customerId = ? and id < ? order by id desc",
                claz, 0, 0, cid, id)
            .setMaxResults(1)
            .list(claz);

        if (ListTools.isEmptyOrNull(list))
        {
            return null;
        }

        return list.get(0);
    }

    /**
     * updateReserve1ByFullId
     * 
     * @param fullId
     * @param reserve1
     * @param reserve4
     * @return
     */
    public boolean updateReserve1ByFullId(String fullId, int reserve1, String reserve4)
    {
        this.jdbcOperation.update("set reserve1 = ? , reserve4 = ? where fullid = ?", claz,
            reserve1, reserve4, fullId);

        return true;
    }

    public boolean updateReserve5ByFullId(String fullId, String reserve5)
    {
        this.jdbcOperation.update("set reserve5 = ? where fullid = ?", claz, reserve5, fullId);

        return true;
    }

    /**
     * queryMaxBusiness
     * 
     * @param cid
     * @param beginDate
     * @param endDate
     * @return
     */
    public double queryMaxBusiness(String cid, String beginDate, String endDate)
    {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("customerId", cid);
        paramterMap.put("beginDate", beginDate);
        paramterMap.put("endDate", endDate);

        Object max = getIbatisDaoSupport().queryForObject("OutStatDAO.queryMaxBusiness",
            paramterMap);

        if (max == null)
        {
            return 0.0d;
        }

        return (Double)max;
    }

    /**
     * 查询财务年度以内的客户没有还款的金额
     * 
     * @param cid
     * @return
     */
    public double sumNotPayByCid(String cid)
    {
        String sql = "select sum(t1.total) from t_center_out t1 "
                     + "where t1.customerId = ? and t1.pay = 0 and t1.type = 0 and t1.outTime >= ?";

        return this.jdbcOperation.queryForDouble(sql, cid, OATools.getFinanceBeginDate());
    }

    /**
     * sumBusiness(统计一段时间内的销售额)
     * 
     * @param cid
     * @param beginDate
     * @param endDate
     * @return
     */
    public double sumBusiness(String cid, String beginDate, String endDate)
    {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("customerId", cid);
        paramterMap.put("beginDate", beginDate);
        paramterMap.put("endDate", endDate);

        Object max = getIbatisDaoSupport().queryForObject("OutStatDAO.sumBusiness", paramterMap);

        if (max == null)
        {
            return 0.0d;
        }

        return (Double)max;
    }

    /**
     * @return the ibatisDaoSupport
     */
    public IbatisDaoSupport getIbatisDaoSupport()
    {
        return ibatisDaoSupport;
    }

    /**
     * @param ibatisDaoSupport
     *            the ibatisDaoSupport to set
     */
    public void setIbatisDaoSupport(IbatisDaoSupport ibatisDaoSupport)
    {
        this.ibatisDaoSupport = ibatisDaoSupport;
    }

}
