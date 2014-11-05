/**
 * File Name: StockManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-20<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.stock.manager;


import java.util.List;

import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.stock.bean.StockBean;
import com.china.center.oa.stock.bean.StockItemBean;
import com.china.center.oa.stock.listener.StockListener;
import com.china.center.oa.stock.vo.StockVO;


/**
 * StockManager
 * 
 * @author ZHUZHU
 * @version 2010-9-20
 * @see StockManager
 * @since 1.0
 */
public interface StockManager extends ListenerManager<StockListener>
{
    boolean addStockBean(final User user, final StockBean bean)
        throws MYException;

    StockVO findStockVO(String id);

    boolean stockItemAskChange(String itemId, String providerId)
        throws MYException;

    boolean fechProduct(User user, String itemId, String depotpartId)
        throws MYException;

    boolean stockItemAsk(StockItemBean bean)
        throws MYException;

    boolean stockItemAskForNet(StockItemBean oldItem, List<StockItemBean> newItemList)
        throws MYException;

    boolean updateStockBean(final User user, final StockBean bean)
        throws MYException;

    boolean updateStockDutyConfig(final User user, final StockBean bean)
        throws MYException;

    boolean updateStockNearlyPayDate(final User user, final String id, String nearlyPayDate)
        throws MYException;

    String endStock(final User user, final String id, String reason)
        throws MYException;

    boolean delStockBean(final User user, final String id)
        throws MYException;

    boolean passStock(final User user, final String id)
        throws MYException;

    boolean rejectStock(final User user, final String id, String reason)
        throws MYException;

    boolean rejectStockToAsk(final User user, final String id, String reason)
        throws MYException;

    /**
     * 修改采购项付款状态(无事务)
     * 
     * @param user
     * @param stockItemId
     * @return
     * @throws MYException
     */
    boolean payStockItemWithoutTransaction(final User user, final String stockItemId)
        throws MYException;
    
    boolean confirmProduct(User user, String itemId)
    throws MYException;
    
    boolean endProduct(User user, String id, String reason)
    	throws MYException;
}
