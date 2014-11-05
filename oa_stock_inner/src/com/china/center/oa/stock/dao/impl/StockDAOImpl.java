/**
 * File Name: StockDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-20<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.stock.dao.impl;


import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.stock.bean.StockBean;
import com.china.center.oa.stock.dao.StockDAO;
import com.china.center.oa.stock.vo.StockVO;


/**
 * StockDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-9-20
 * @see StockDAOImpl
 * @since 1.0
 */
public class StockDAOImpl extends BaseDAO<StockBean, StockVO> implements StockDAO
{
    /**
     * 更新状态
     * 
     * @param id
     * @param status
     * @return
     */
    public boolean updateStatus(String id, int status)
    {
        this.jdbcOperation.updateField("status", status, id, this.claz);

        return true;
    }

    public boolean updatePayStatus(String id, int pay)
    {
        this.jdbcOperation.updateField("pay", pay, id, this.claz);

        return true;
    }

    /**
     * 更新状态
     * 
     * @param id
     * @param status
     * @return
     */
    public boolean updateTotal(String id, double total)
    {
        this.jdbcOperation.updateField("total", total, id, this.claz);

        return true;
    }

    /**
     * 更新状态
     * 
     * @param id
     * @param status
     * @return
     */
    public boolean updateExceptStatus(String id, int exceptStatus)
    {
        this.jdbcOperation.updateField("exceptStatus", exceptStatus, id, this.claz);

        return true;
    }

    /**
     * 更新状态
     * 
     * @param id
     * @param status
     * @return
     */
    public boolean updateNeedTime(String id, String date)
    {
        this.jdbcOperation.updateField("needTime", date, id, this.claz);

        return true;
    }

    public boolean updateConsign(String id, String consign)
    {
        this.jdbcOperation.updateField("consign", consign, id, this.claz);

        return true;
    }
    
    public boolean updateExtraStatus(String id, int extraStatus)
    {
        this.jdbcOperation.updateField("extraStatus", extraStatus, id, this.claz);

        return true;
    }
}
