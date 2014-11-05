/**
 * File Name: StafferListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-23<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.listener;


import com.center.china.osgi.publics.ParentListener;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.DutyBean;


/**
 * DutyListener
 * 
 * @author ZHUZHU
 * @version 2010-6-23
 * @see DutyListener
 * @since 1.0
 */
public interface DutyListener extends ParentListener
{
    /**
     * 删除duty
     * 
     * @param stafferId
     */
    void onDeleteDuty(User user, DutyBean bean)
        throws MYException;
}
