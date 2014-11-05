/**
 * File Name: CommonDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-19<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao;

/**
 * CommonDAO
 * 
 * @author ZHUZHU
 * @version 2010-6-19
 * @see CommonDAO
 * @since 1.0
 */
public interface CommonDAO
{
    int getSquence();

    String getSquenceString();

    String getSquenceString20();

    String getSquenceString20(String pfix);

    /**
     * 实现数据库更新锁(全局锁,事务锁,其他的事务不结束,这个语句就会卡住)
     */
    void updatePublicLock();
    
    void updatePublicLock1();
}
