/**
 * File Name: StockDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-20<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.stock.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.stock.bean.StockBean;
import com.china.center.oa.stock.vo.StockVO;


/**
 * StockDAO
 * 
 * @author ZHUZHU
 * @version 2010-9-20
 * @see StockDAO
 * @since 1.0
 */
public interface StockDAO extends DAO<StockBean, StockVO>
{
    boolean updateStatus(String id, int status);

    boolean updatePayStatus(String id, int pay);

    boolean updateTotal(String id, double total);

    boolean updateExceptStatus(String id, int exceptStatus);

    boolean updateNeedTime(String id, String date);

    /**
     * updateConsign
     * 
     * @param id
     * @param consign
     * @return
     */
    boolean updateConsign(String id, String consign);
    
    boolean updateExtraStatus(String id, int extraStatus);
}
