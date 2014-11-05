/**
 * File Name: ComposeProductDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.product.bean.ComposeProductBean;
import com.china.center.oa.product.vo.ComposeProductVO;


/**
 * ComposeProductDAO
 * 
 * @author ZHUZHU
 * @version 2010-10-2
 * @see ComposeProductDAO
 * @since 1.0
 */
public interface ComposeProductDAO extends DAO<ComposeProductBean, ComposeProductVO>
{
    boolean updateStatus(String id, int status);

    boolean updateLogTime(String id, String logTime);
    
    ComposeProductBean queryLatestByProduct(String productId);
    
    boolean updateHybrid(String id, int hybrid);
}
