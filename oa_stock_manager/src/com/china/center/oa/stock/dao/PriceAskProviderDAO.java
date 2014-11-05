/**
 * File Name: PriceAskProviderDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.stock.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.stock.bean.PriceAskProviderBean;
import com.china.center.oa.stock.vo.PriceAskProviderBeanVO;


/**
 * PriceAskProviderDAO
 * 
 * @author ZHUZHU
 * @version 2010-9-10
 * @see PriceAskProviderDAO
 * @since 1.0
 */
public interface PriceAskProviderDAO extends DAO<PriceAskProviderBean, PriceAskProviderBeanVO>
{
    PriceAskProviderBean findBeanByAskIdAndProviderId(String askId, String providerId, int type);

    PriceAskProviderBeanVO findVOByAskIdAndProviderId(String askId, String providerId, int type);

    List<PriceAskProviderBeanVO> queryByCondition(String askDate, String productId, String stockId);

    boolean deleteByProviderId(String askId, String providerId, int type);
}
