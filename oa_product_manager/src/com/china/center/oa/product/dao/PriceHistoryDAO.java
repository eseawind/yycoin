/**
 * File Name: PriceHistoryDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-5<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.product.bean.PriceHistoryBean;
import com.china.center.oa.product.vo.PriceHistoryVO;


/**
 * PriceHistoryDAO
 * 
 * @author ZHUZHU
 * @version 2010-10-5
 * @see PriceHistoryDAO
 * @since 1.0
 */
public interface PriceHistoryDAO extends DAO<PriceHistoryBean, PriceHistoryVO>
{
    PriceHistoryBean findLastByProductId(String productId);
}
