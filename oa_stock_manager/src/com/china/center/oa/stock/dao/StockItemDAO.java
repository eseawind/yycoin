/**
 * File Name: StockItemDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-20<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.stock.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.stock.bean.StockItemBean;
import com.china.center.oa.stock.vo.StockItemVO;


/**
 * StockItemDAO
 * 
 * @author ZHUZHU
 * @version 2010-9-20
 * @see StockItemDAO
 * @since 1.0
 */
public interface StockItemDAO extends DAO<StockItemBean, StockItemVO>
{
    boolean updateStatus(String id, int status);

    boolean updatePay(String id, int pay);

    List<StockItemVO> queryStatStockItemVO(String beginTime, String endTime, PageSeparate separate);

    int countStatStockItem(String beginTime, String endTime);

    int sumNetProductByPid(String pid);
    
    boolean updateExtraStatus(String id, int extraStatus);
}
