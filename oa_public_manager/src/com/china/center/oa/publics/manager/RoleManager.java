/**
 * File Name: RoleManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-23<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.RoleBean;
import com.china.center.oa.publics.vo.RoleVO;


/**
 * RoleManager
 * 
 * @author ZHUZHU
 * @version 2010-6-23
 * @see RoleManager
 * @since 1.0
 */
public interface RoleManager
{
    boolean addBean(User user, RoleBean bean)
        throws MYException;

    boolean updateBean(User user, RoleBean bean)
        throws MYException;

    boolean delBean(User user, String id)
        throws MYException;

    RoleVO findVO(String id)
        throws MYException;
}
