/**
 * File Name: FinanceDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.vo.FinanceVO;


/**
 * FinanceDAO
 * 
 * @author ZHUZHU
 * @version 2011-2-6
 * @see FinanceDAO
 * @since 1.0
 */
public interface FinanceDAO extends DAO<FinanceBean, FinanceVO>
{
    boolean updateCheck(String id, String reason);

    boolean updateMonthIndex(String id, int monthIndex);

    /**
     * updateLockToEnd
     * 
     * @param beginTime
     * @param endTime
     * @return
     */
    int updateLockToEnd(String beginTime, String endTime);

    int updateLockToBegin(String beginTime, String endTime);

    List<FinanceBean> queryRefFinanceItemByOutId(String outId);

    List<FinanceBean> queryRefFinanceItemByBillId(String billId);

    List<FinanceBean> queryRefFinanceItemByStockId(String stockId);

    List<FinanceBean> queryRefFinanceItemByRefId(String refId);

    int findMaxMonthIndexByOut(String beginDate, String endDate);

    int findMaxMonthIndexByInner(String beginDate, String endDate);

    List<String> queryDuplicateMonthIndex(String date);
}
