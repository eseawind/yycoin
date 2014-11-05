/**
 * File Name: StafferListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-23<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.listener;


import java.util.List;

import com.center.china.osgi.publics.ParentListener;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.vs.LocationVSCityBean;


/**
 * LocationListener
 * 
 * @author ZHUZHU
 * @version 2010-6-23
 * @see LocationListener
 * @since 1.0
 */
public interface LocationListener extends ParentListener
{
    /**
     * 增加之前的准备
     * 
     * @param bean
     */
    void onAddLocationVSCityBefore(User user, String locationId, List<LocationVSCityBean> list)
        throws MYException;

    /**
     * 处理每一个地市
     * 
     * @param bean
     */
    void onAddLocationVSCityEach(User user, String locationId, LocationVSCityBean each)
        throws MYException;

    /**
     * 删除地市关系
     * 
     * @param stafferId
     */
    void onDeleteLocationVSCity(User user, String locationId, List<LocationVSCityBean> deleteList)
        throws MYException;

    /**
     * 删除区域的时候
     * 
     * @param user
     * @param locationId
     * @throws MYException
     */
    void onDeleteLocation(User user, String locationId)
        throws MYException;
}
