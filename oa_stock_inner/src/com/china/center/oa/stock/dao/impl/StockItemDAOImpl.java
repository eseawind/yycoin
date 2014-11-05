/**
 * File Name: StockItemDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-20<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.stock.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.stock.bean.StockItemBean;
import com.china.center.oa.stock.constant.PriceConstant;
import com.china.center.oa.stock.dao.StockItemDAO;
import com.china.center.oa.stock.vo.StockItemVO;


/**
 * StockItemDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-9-20
 * @see StockItemDAOImpl
 * @since 1.0
 */
public class StockItemDAOImpl extends BaseDAO<StockItemBean, StockItemVO> implements StockItemDAO
{
    public boolean updateStatus(String id, int status)
    {
        this.jdbcOperation.updateField("status", status, id, this.claz);

        return true;
    }

    public boolean updatePay(String id, int pay)
    {
        this.jdbcOperation.updateField("pay", pay, id, this.claz);

        return true;
    }

    /**
     * 根据统计查询
     * 
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<StockItemVO> queryStatStockItemVO(String beginTime, String endTime, PageSeparate separate)
    {
        String sql = getSql();

        return this.jdbcOperation.queryObjectsBySqlAndPageSeparate(sql, separate, this.clazVO, beginTime, endTime);
    }

    public int countStatStockItem(String beginTime, String endTime)
    {
        String sql = getSql();

        return this.jdbcOperation.queryObjectsBySql(sql, beginTime, endTime).getCount();
    }

    /**
     * sumNetProductByPid
     * 
     * @param pid
     * @return
     */
    public int sumNetProductByPid(String pid)
    {
        String sql = "select sum(t1.amount) from T_CENTER_STOCKITEM t1, T_CENTER_STOCK t2 "
                     + "where t1.stockId = t2.id and t1.priceAskProviderId = ? and t2.type = ? and t2.status not in (0, 2)";

        return this.jdbcOperation.queryForInt(sql, pid, PriceConstant.PRICE_ASK_TYPE_NET);
    }

    /**
     * 获得统计的SQL
     * 
     * @return
     */
    private String getSql()
    {
        String sql = "select a.name as providerName, t.providerId, sum(total) as total, sum(amount) as amount "
                     + "from t_center_stockitem t, t_center_provide a ";

        sql += "where t.status = 1 and t.providerId = a.id and t.logTime >= ? and t.logTime <= ? "
               + "group by t.providerId order by sum(total) desc";

        return sql;
    }
    
    public boolean updateExtraStatus(String id, int extraStatus)
    {
        this.jdbcOperation.updateField("extraStatus", extraStatus, id, this.claz);

        return true;
    }
}
