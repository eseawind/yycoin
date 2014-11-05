/**
 * File Name: DepartmentManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.DepartmentBean;


/**
 * DepartmentManager
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see DepartmentManager
 * @since 1.0
 */
public interface DepartmentManager
{
    boolean addBean(User user, DepartmentBean bean)
        throws MYException;

    boolean updateBean(User user, DepartmentBean bean)
        throws MYException;

    boolean delBean(User user, String id)
        throws MYException;

}
