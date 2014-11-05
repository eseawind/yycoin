/**
 * File Name: EnumDefineDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-16<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao.impl;


import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.publics.bean.EnumDefineBean;
import com.china.center.oa.publics.dao.EnumDefineDAO;


/**
 * EnumDefineDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-16
 * @see EnumDefineDAOImpl
 * @since 1.0
 */
public class EnumDefineDAOImpl extends BaseDAO<EnumDefineBean, EnumDefineBean> implements EnumDefineDAO
{

    public int countRef(String tableName, String column, String value)
    {
        return this.jdbcOperation.queryForInt("select count(1) from " + tableName + " where " + column + " = ?", value);
    }
}
