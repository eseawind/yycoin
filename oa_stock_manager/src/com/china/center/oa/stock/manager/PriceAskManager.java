/**
 * File Name: PriceManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-11<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.stock.manager;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.stock.bean.PriceAskBean;
import com.china.center.oa.stock.vo.PriceAskBeanVO;


/**
 * PriceManager
 * 
 * @author ZHUZHU
 * @version 2010-9-11
 * @see PriceAskManager
 * @since 1.0
 */
public interface PriceAskManager
{
    boolean addPriceAskBean(User user, PriceAskBean bean)
        throws MYException;

    boolean addPriceAskBeanWithoutTransactional(User user, PriceAskBean bean)
        throws MYException;

    boolean updatePriceAskAmount(User user, final String id, int newAmount)
        throws MYException;

    boolean updatePriceAskAmountStatus(User user, final String id, int newStatus)
        throws MYException;

    boolean rejectPriceAskBean(User user, String id, String reason)
        throws MYException;

    boolean endPriceAskBean(final User user, String id)
        throws MYException;

    boolean processPriceAskBean(final User user, final PriceAskBean bean)
        throws MYException;

    PriceAskBeanVO findPriceAskBeanVO(final String id)
        throws MYException;

    boolean checkOverTime_Job();

    boolean delPriceAskBean(User user, final String id)
        throws MYException;
}
