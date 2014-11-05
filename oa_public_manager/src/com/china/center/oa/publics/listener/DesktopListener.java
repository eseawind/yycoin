/**
 * File Name: DeskListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-12-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.listener;


import com.center.china.osgi.publics.User;
import com.china.center.oa.publics.wrap.DesktopWrap;


/**
 * DeskListener
 * 
 * @author ZHUZHU
 * @version 2011-12-3
 * @see DesktopListener
 * @since 3.0
 */
public interface DesktopListener
{
    String getKey();

    DesktopWrap getDeskWrap(User user);
}
