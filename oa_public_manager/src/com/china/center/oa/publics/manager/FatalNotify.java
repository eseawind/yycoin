/**
 * File Name: FatalNotify.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-28<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;

/**
 * 致命事件通知
 * 
 * @author ZHUZHU
 * @version 2010-11-28
 * @see FatalNotify
 * @since 3.0
 */
public interface FatalNotify
{
    /**
     * notify(异步的通知)
     * 
     * @param message
     */
    void notify(String message);
    
}
