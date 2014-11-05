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
import com.china.center.oa.publics.bean.PrincipalshipBean;


/**
 * DutyListener
 * 
 * @author ZHUZHU
 * @version 2010-6-23
 * @see OrgListener
 * @since 1.0
 */
public interface OrgListener extends ParentListener
{
    /**
     * 删除ORG
     * 
     * @param stafferId
     */
    void onDeleteOrg(User user, PrincipalshipBean org)
        throws MYException;
}
