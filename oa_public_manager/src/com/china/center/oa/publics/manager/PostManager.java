/**
 * File Name: PostManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-23<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.PostBean;


/**
 * PostManager
 * 
 * @author ZHUZHU
 * @version 2010-6-23
 * @see PostManager
 * @since 1.0
 */
public interface PostManager
{
    boolean addBean(User user, PostBean bean)
        throws MYException;

    boolean updateBean(User user, PostBean bean)
        throws MYException;

    boolean delBean(User user, String id)
        throws MYException;
}
