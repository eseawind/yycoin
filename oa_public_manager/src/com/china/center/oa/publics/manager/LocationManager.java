/**
 * File Name: LocationManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;


import java.util.List;

import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.listener.LocationListener;
import com.china.center.oa.publics.vo.LocationVO;
import com.china.center.oa.publics.vs.LocationVSCityBean;


/**
 * LocationManager
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see LocationManager
 * @since 1.0
 */
public interface LocationManager extends ListenerManager<LocationListener>
{
    boolean addBean(User user, LocationBean bean)
        throws MYException;

    boolean delBean(User user, String locationId)
        throws MYException;

    LocationVO findVO(String id)
        throws MYException;

    boolean addLocationVSCity(User user, String locationId, List<LocationVSCityBean> list)
        throws MYException;
}
