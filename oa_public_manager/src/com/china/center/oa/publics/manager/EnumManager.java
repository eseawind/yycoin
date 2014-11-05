/**
 * File Name: EnumManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.EnumBean;


/**
 * EnumManager
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see EnumManager
 * @since 1.0
 */
public interface EnumManager
{
    void init();

    boolean addBean(User user, EnumBean bean)
        throws MYException;

    boolean updateBean(User user, EnumBean bean)
        throws MYException;

    boolean deleteBean(User user, String id)
        throws MYException;
}
