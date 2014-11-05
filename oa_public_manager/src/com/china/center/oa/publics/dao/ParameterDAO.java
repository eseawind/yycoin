/**
 * File Name: ParameterDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao;

/**
 * ParameterDAO
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see ParameterDAO
 * @since 1.0
 */
public interface ParameterDAO
{
    void init();

    boolean getBoolean(String key);

    String getString(String key);

    int getInt(String key);
}
