/**
 * File Name: PublicQueryManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager.impl;


import java.util.List;

import com.china.center.oa.publics.manager.PublicQueryManager;
import com.china.center.oa.publics.statics.PublicStatic;


/**
 * PublicQueryManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see PublicQueryManagerImpl
 * @since 1.0
 */
public class PublicQueryManagerImpl implements PublicQueryManager
{
    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.manager.PublicQueryManager#getSelectList(java.lang.String)
     */
    public List<?> getSelectList(String key)
    {
        return PublicStatic.getSelectMap().get(key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.manager.PublicQueryManager#putSelectList(java.lang.String, java.util.List)
     */
    public void putSelectList(String key, List<?> beanList)
    {
        PublicStatic.getSelectMap().put(key, beanList);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.manager.PublicQueryManager#removeSelectList(java.lang.String)
     */
    public void removeSelectList(String key)
    {
        PublicStatic.getSelectMap().remove(key);
    }
}
