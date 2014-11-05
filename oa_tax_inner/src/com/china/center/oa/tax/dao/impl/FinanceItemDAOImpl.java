/**
 * File Name: FinanceItemDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.dao.impl;


import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;

import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.IbatisDaoSupport;
import com.china.center.jdbc.inter.Query;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.tax.bean.FinanceItemBean;
import com.china.center.oa.tax.dao.FinanceItemDAO;
import com.china.center.oa.tax.vo.FinanceItemVO;
import com.china.center.oa.tax.vo.StafferUnitVO;


/**
 * FinanceItemDAOImpl
 * 
 * @author ZHUZHU
 * @version 2011-2-6
 * @see FinanceItemDAOImpl
 * @since 1.0
 */
public class FinanceItemDAOImpl extends BaseDAO<FinanceItemBean, FinanceItemVO> implements FinanceItemDAO
{
    private IbatisDaoSupport ibatisDaoSupport = null;

    /**
     * default constructor
     */
    public FinanceItemDAOImpl()
    {
    }

    public long sumInByCondition(ConditionParse condition)
    {
        String sql = BeanTools.getSumHead(claz, "inmoney") + condition.toString();

        return this.jdbcOperation.queryForLong(sql);
    }

    public long sumOutByCondition(ConditionParse condition)
    {
        String sql = BeanTools.getSumHead(claz, "outmoney") + condition.toString();

        return this.jdbcOperation.queryForLong(sql);
    }

    public long[] sumMoneryByCondition(ConditionParse condition)
    {
        String sql = "select sum(inmoney) as inmoney, sum(outmoney) as outmoney  from "
                     + BeanTools.getTableName(claz) + " FinanceItemBean " + condition.toString();

        Map queryForMap = this.jdbcOperation.queryForMap(sql);

        long[] result = new long[2];

        if (queryForMap.get("inmoney") == null)
        {
            result[0] = 0;
        }
        else
        {
            result[0] = ((BigDecimal)queryForMap.get("inmoney")).longValue();
        }

        if (queryForMap.get("outmoney") == null)
        {
            result[1] = 0;
        }
        else
        {
            result[1] = ((BigDecimal)queryForMap.get("outmoney")).longValue();
        }

        return result;
    }

    public long[] sumVOMoneryByCondition(ConditionParse condition, PageSeparate newPage)
    {
        long[] result = new long[2];

        Query query = this.jdbcOperation.createQueryByCondtitionAndPageSeparate(condition
            .toString(), newPage, this.clazVO);

        String sql = query.getLastSqlByHead("sum(INMONEY) as INMONEY, sum(OUTMONEY) as OUTMONEY");

        Map queryForMap = this.jdbcOperation.queryForMap(sql);

        if (queryForMap.size() == 0)
        {
            return result;
        }

        Object object = queryForMap.get("INMONEY");

        if (object == null)
        {
            result[0] = 0;
        }
        else
        {
            result[0] = ((BigDecimal)object).longValue();
        }

        object = queryForMap.get("OUTMONEY");

        if (object == null)
        {
            result[1] = 0;
        }
        else
        {
            result[1] = ((BigDecimal)object).longValue();
        }

        return result;
    }

    public List<String> queryDistinctUnitByStafferId(String stafferId, String beginDate,
                                                     String endDate)
    {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("stafferId", stafferId);
        paramterMap.put("beginDate", beginDate);
        paramterMap.put("endDate", endDate);

        List<String> result = getIbatisDaoSupport().queryForList(
            "FinanceItemDAOImpl.queryDistinctUnitByStafferId", paramterMap);

        return result;
    }

    public List<String> queryDistinctUnit(String beginDate, String endDate, String taxColumn,
                                          String taxId)
    {
        String sql = "select distinct(t1.unitId) as unitId from T_CENTER_FINANCEITEM t1 "
                     + "where t1.financeDate >= ? and t1.financeDate <= ? and " + taxColumn
                     + " = ?";

        final List<String> result = new LinkedList<String>();

        this.jdbcOperation.query(sql, new Object[] {beginDate, endDate, taxId},
            new RowCallbackHandler()
            {

                public void processRow(ResultSet rst)
                    throws SQLException
                {
                    result.add(rst.getString("unitId"));
                }
            });

        return result;
    }

    public List<String> queryDistinctStafferId(String beginDate, String endDate, String taxColumn,
                                               String taxId)
    {
        String sql = "select distinct(t1.stafferId) as stafferId from T_CENTER_FINANCEITEM t1 "
                     + "where t1.financeDate >= ? and t1.financeDate <= ? and " + taxColumn
                     + " = ?";

        final List<String> result = new LinkedList<String>();

        this.jdbcOperation.query(sql, new Object[] {beginDate, endDate, taxId},
            new RowCallbackHandler()
            {

                public void processRow(ResultSet rst)
                    throws SQLException
                {
                    result.add(rst.getString("stafferId"));
                }
            });

        return result;
    }

    /**
     * queryDistinctStafferIdAndUnitId
     * 
     * @param beginDate
     * @param endDate
     * @param taxColumn
     * @param taxId
     * @return
     */
    public List<StafferUnitVO> queryDistinctStafferIdAndUnitId(String beginDate, String endDate, String taxColumn,
            String taxId)
    {

        String sql = "select distinct t1.stafferId as stafferId, t1.unitId as unitId from T_CENTER_FINANCEITEM t1 "
                     + "where t1.financeDate >= ? and t1.financeDate <= ? and " + taxColumn
                     + " = ? order by t1.stafferId";

        final List<StafferUnitVO> result = new LinkedList<StafferUnitVO>();

        this.jdbcOperation.query(sql, new Object[] {beginDate, endDate, taxId},
            new RowCallbackHandler()
            {

                public void processRow(ResultSet rst)
                    throws SQLException
                {
                    StafferUnitVO vo = new StafferUnitVO();
                    
                    vo.setStafferId(rst.getString("stafferId"));
                    vo.setUnitId(rst.getString("unitId"));
                    
                    result.add(vo);
                }
            });

        return result;
          
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
