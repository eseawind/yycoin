/**
 * File Name: CreditItemThrDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.credit.bean.CreditItemThrBean;
import com.china.center.oa.credit.dao.CreditItemThrDAO;
import com.china.center.oa.credit.vo.CreditItemThrVO;
import com.china.center.oa.customer.constant.CreditConstant;
import com.china.center.tools.ListTools;


/**
 * CreditItemThrDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CreditItemThrDAOImpl
 * @since 1.0
 */
public class CreditItemThrDAOImpl extends BaseDAO<CreditItemThrBean, CreditItemThrVO> implements CreditItemThrDAO
{
    /**
     * countByPidAndIndexPos
     * 
     * @param pid
     * @param indexPos
     * @return
     */
    public int countByPidAndIndexPos(String pid, int indexPos)
    {
        return this.countByCondition("where pid = ? and indexPos = ?", pid, indexPos);
    }

    /**
     * findMaxDelayItem
     * 
     * @return
     */
    public CreditItemThrBean findMaxDelayItem()
    {
        List<CreditItemThrBean> list = this.queryEntityBeansByCondition("where pid = ? order by indexPos desc",
            CreditConstant.OUT_DELAY_ITEM);

        if (ListTools.isEmptyOrNull(list))
        {
            return null;
        }

        return list.get(0);
    }

    /**
     * findDelayItemByDays
     * 
     * @param days
     * @return
     */
    public CreditItemThrBean findDelayItemByDays(int days)
    {
        List<CreditItemThrBean> list = this.queryEntityBeansByCondition(
            "where pid = ? and indexPos >= ? order by indexPos asc", CreditConstant.OUT_DELAY_ITEM, days);

        if (ListTools.isEmptyOrNull(list))
        {
            return findMaxDelayItem();
        }

        return list.get(0);
    }

    /**
     * findMaxDelayItem
     * 
     * @return
     */
    public CreditItemThrBean findMaxBusiness()
    {
        List<CreditItemThrBean> list = this.queryEntityBeansByCondition("where pid = ? order by indexPos desc",
            CreditConstant.OUT_MAX_BUSINESS);

        if (ListTools.isEmptyOrNull(list))
        {
            return null;
        }

        return list.get(0);
    }

    /**
     * findSingleMaxBusinessByValue
     * 
     * @param sigleMax
     * @return
     */
    public CreditItemThrBean findSingleMaxBusinessByValue(double sigleMax)
    {
        // 单位是万元
        int level = (int) (sigleMax / 10000.0d);

        List<CreditItemThrBean> list = this.queryEntityBeansByCondition(
            "where pid = ? and indexPos >= ? order by indexPos asc", CreditConstant.OUT_MAX_BUSINESS, level);

        if (ListTools.isEmptyOrNull(list))
        {
            return findMaxBusiness();
        }

        return list.get(0);
    }

    /**
     * findTotalBusiness
     * 
     * @return
     */
    public CreditItemThrBean findTotalBusiness()
    {
        List<CreditItemThrBean> list = this.queryEntityBeansByCondition("where pid = ? order by indexPos desc",
            CreditConstant.OUT_TOTAL_BUSINESS);

        if (ListTools.isEmptyOrNull(list))
        {
            return null;
        }

        return list.get(0);
    }

    public CreditItemThrBean findTotalBusinessByValue(double sumBusiness)
    {
        // 单位是万元
        int level = (int) (sumBusiness / 10000.0d);

        List<CreditItemThrBean> list = this.queryEntityBeansByCondition(
            "where pid = ? and indexPos >= ? order by indexPos asc", CreditConstant.OUT_TOTAL_BUSINESS, level);

        if (ListTools.isEmptyOrNull(list))
        {
            return findTotalBusiness();
        }

        return list.get(0);
    }
}
