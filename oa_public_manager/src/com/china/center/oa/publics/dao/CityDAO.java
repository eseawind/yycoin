/**
 * File Name: CityDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-19<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.publics.bean.CityBean;
import com.china.center.oa.publics.vo.CityVO;


/**
 * CityDAO
 * 
 * @author ZHUZHU
 * @version 2010-6-19
 * @see CityDAO
 * @since 1.0
 */
public interface CityDAO extends DAO<CityBean, CityVO>
{
    List<CityBean> queryCanAssignCity(String provinceId, String locationId);

    CityBean findCityByName(String name);
}
