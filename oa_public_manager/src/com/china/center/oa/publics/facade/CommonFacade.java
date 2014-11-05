/**
 * File Name: CommonFacade.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-18<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.facade;


import com.center.china.osgi.publics.User;


/**
 * CommonFacade
 * 
 * @author ZHUZHU
 * @version 2010-7-18
 * @see CommonFacade
 * @since 1.0
 */
public interface CommonFacade
{
    /**
     * auth
     * 
     * @param key
     * @param value
     * @return
     */
    boolean auth(String key, String value);

    /**
     * 登录接口
     * 
     * @param name
     * @param password
     * @return
     */
    User login(String name, String password);

    /**
     * login2
     * 
     * @param name
     * @param password
     * @param key
     * @return
     */
    User login2(String name, String password, String rand, String key, String randKey);
}
