/**
 * File Name: CityDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-19<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.publics.bean.CityBean;
import com.china.center.oa.publics.dao.CityDAO;
import com.china.center.oa.publics.vo.CityVO;


/**
 * CityDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-6-19
 * @see CityDAOImpl
 * @since 1.0
 */
public class CityDAOImpl extends BaseDAO<CityBean, CityVO> implements CityDAO
{
    public List<CityBean> queryCanAssignCity(String provinceId, String locationId)
    {
        String sql = "where parentId = ? and id not in (select cityID from t_center_vs_locationcity where locationId <> ?)";

        return this.jdbcOperation.queryObjects(sql, this.claz, provinceId, locationId).list(
            this.claz);
    }

    /**
     * 根据名称模糊查询地市
     * 
     * @param name
     * @return
     */
    public CityBean findCityByName(String name)
    {
        List<CityBean> list = this.jdbcOperation.queryForList("where name like ?", claz, name);

        if (list.size() != 1)
        {
            return null;
        }

        return list.get(0);
    }
}
