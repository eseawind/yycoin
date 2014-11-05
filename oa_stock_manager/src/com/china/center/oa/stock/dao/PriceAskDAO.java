/**
 * File Name: PriceAskDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-11<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.stock.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.stock.bean.PriceAskBean;
import com.china.center.oa.stock.vo.PriceAskBeanVO;


/**
 * PriceAskDAO
 * 
 * @author ZHUZHU
 * @version 2010-9-11
 * @see PriceAskDAO
 * @since 1.0
 */
public interface PriceAskDAO extends DAO<PriceAskBean, PriceAskBeanVO>
{
    boolean updateAmount(String id, int newAmount);

    PriceAskBean findByDescription(String description);

    boolean updateAmountStatus(String id, int newStatus);

    boolean checkAndUpdateOverTime();

    PriceAskBean findAbsByProductIdAndProcessTime(String productId, String processTime);

    List<PriceAskBean> queryByParentId(String parentId);
}
