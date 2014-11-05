/**
 * File Name: PriceChangeDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.product.bean.PriceChangeBean;
import com.china.center.oa.product.vo.PriceChangeVO;


/**
 * PriceChangeDAO
 * 
 * @author ZHUZHU
 * @version 2010-10-4
 * @see PriceChangeDAO
 * @since 1.0
 */
public interface PriceChangeDAO extends DAO<PriceChangeBean, PriceChangeVO>
{
    boolean updateStatus(String id, int status);
}
