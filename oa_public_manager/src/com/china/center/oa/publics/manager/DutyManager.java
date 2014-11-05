/**
 * File Name: LocationManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;


import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.listener.DutyListener;


/**
 * LocationManager
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see DutyManager
 * @since 1.0
 */
public interface DutyManager extends ListenerManager<DutyListener>
{
    boolean addBean(User user, DutyBean bean)
        throws MYException;

    boolean updateBean(User user, DutyBean bean)
        throws MYException;

    boolean deleteBean(User user, String id)
        throws MYException;
}
