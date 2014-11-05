/**
 * File Name: CheckViewDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-3-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.tax.bean.CheckViewBean;
import com.china.center.oa.tax.vo.CheckViewVO;


/**
 * CheckViewDAO
 * 
 * @author ZHUZHU
 * @version 2011-3-9
 * @see CheckViewDAO
 * @since 3.0
 */
public interface CheckViewDAO extends DAO<CheckViewBean, CheckViewVO>
{
    boolean updateCheck(String tableName, String id, String reason);

    void syn();
}
