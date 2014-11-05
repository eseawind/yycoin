/**
 * File Name: PublicQueryManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;


import java.util.List;


/**
 * PublicQueryManager
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see PublicQueryManager
 * @since 1.0
 */
public interface PublicQueryManager
{
    /**
     * 动态增加
     * 
     * @param key
     * @param beanList
     */
    void putSelectList(String key, List<?> beanList);

    /**
     * 动态删除
     * 
     * @param key
     */
    void removeSelectList(String key);

    /**
     * 获取
     * 
     * @param key
     * @return
     */
    List<?> getSelectList(String key);
}
