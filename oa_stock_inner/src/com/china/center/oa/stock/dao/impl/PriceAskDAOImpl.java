/**
 * File Name: PriceAskDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-11<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.stock.dao.impl;


import java.util.List;

import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.stock.bean.PriceAskBean;
import com.china.center.oa.stock.constant.PriceConstant;
import com.china.center.oa.stock.dao.PriceAskDAO;
import com.china.center.oa.stock.vo.PriceAskBeanVO;
import com.china.center.tools.TimeTools;


/**
 * PriceAskDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-9-11
 * @see PriceAskDAOImpl
 * @since 1.0
 */
public class PriceAskDAOImpl extends BaseDAO<PriceAskBean, PriceAskBeanVO> implements PriceAskDAO
{
    public boolean updateAmount(String id, int newAmount)
    {
        this.jdbcOperation.updateField("amount", newAmount, id, claz);

        return true;
    }

    public PriceAskBean findByDescription(String description)
    {
        List<PriceAskBean> list = this.jdbcOperation.queryForList("where description = ?", claz, description);

        if (list.size() > 0)
        {
            return list.get(0);
        }

        return null;
    }

    public boolean updateAmountStatus(String id, int newStatus)
    {
        this.jdbcOperation.updateField("amountStatus", newStatus, id, claz);

        return true;
    }

    /**
     * 定时更新询价超时
     * 
     * @return
     */
    public boolean checkAndUpdateOverTime()
    {
        // 超期询价的直接设置成超期且结束
        String sql = "update " + BeanTools.getTableName(this.claz)
                     + " set overtime = 1, status = ? where status = ? and processtime <= ?";

        this.jdbcOperation.update(sql, PriceConstant.PRICE_ASK_STATUS_END, PriceConstant.PRICE_ASK_STATUS_INIT,
            TimeTools.now());

        // 自动结束正常询价
        sql = "update " + BeanTools.getTableName(this.claz) + " set status = ? where status = ? and processtime <= ?";

        this.jdbcOperation.update(sql, PriceConstant.PRICE_ASK_STATUS_END, PriceConstant.PRICE_ASK_STATUS_PROCESSING,
            TimeTools.now());

        return true;
    }

    /**
     * findAbsByProductIdAndProcessTime
     * 
     * @param productId
     * @param processTime
     * @return
     */
    public PriceAskBean findAbsByProductIdAndProcessTime(String productId, String processTime)
    {
        return this.findUnique(
            "where PriceAskBean.productId = ? and PriceAskBean.processTime = ? and PriceAskBean.saveType = ?",
            productId, processTime, PriceConstant.PRICE_ASK_SAVE_TYPE_ABS);
    }

    /**
     * queryByParentId
     * 
     * @param parentId
     * @return
     */
    public List<PriceAskBean> queryByParentId(String parentId)
    {
        return this.queryEntityBeansByCondition("where parentAsk = ?", parentId);
    }
}
