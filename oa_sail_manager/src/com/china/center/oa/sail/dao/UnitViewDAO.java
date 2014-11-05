/**
 * File Name: UnitViewDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.dao;


import java.io.Serializable;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.sail.bean.UnitViewBean;


/**
 * UnitViewDAO
 * 
 * @author ZHUZHU
 * @version 2011-2-27
 * @see UnitViewDAO
 * @since 3.0
 */
public interface UnitViewDAO extends DAO<UnitViewBean, UnitViewBean>
{
    UnitViewBean find(Serializable id);

    void syn();
    
    void synNew();
}
