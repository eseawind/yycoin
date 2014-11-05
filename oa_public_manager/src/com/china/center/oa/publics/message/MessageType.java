/**
 * File Name: MessageType.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-6-11<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.message;

/**
 * MessageType
 * 
 * @author ZHUZHU
 * @version 2011-6-11
 * @see MessageType
 * @since 3.0
 */
public interface MessageType
{
    /**
     * 消息类型
     * 
     * @return
     */
    String getMessageType();

    /**
     * 注册主键,主要是为了动态卸载使用
     * 
     * @return
     */
    String getKey();
}
