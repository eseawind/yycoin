/**
 * File Name: PriceChangeDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao.impl;


import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.product.bean.PriceChangeBean;
import com.china.center.oa.product.dao.PriceChangeDAO;
import com.china.center.oa.product.vo.PriceChangeVO;


/**
 * PriceChangeDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-4
 * @see PriceChangeDAOImpl
 * @since 1.0
 */
public class PriceChangeDAOImpl extends BaseDAO<PriceChangeBean, PriceChangeVO> implements PriceChangeDAO
{
    public boolean updateStatus(String id, int status)
    {
        return this.jdbcOperation.updateField("status", status, id, claz) > 0;
    }
}
