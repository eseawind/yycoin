/**
 * File Name: LocationDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao.impl;


import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.dao.LocationDAO;
import com.china.center.oa.publics.vo.LocationVO;


/**
 * LocationDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see LocationDAOImpl
 * @since 1.0
 */
public class LocationDAOImpl extends BaseDAO<LocationBean, LocationVO> implements LocationDAO
{
    public int countCode(String code)
    {
        return this.jdbcOperation.queryForInt("where code = ?", this.claz, code);
    }
}
