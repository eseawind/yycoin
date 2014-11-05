/**
 * File Name: CommonMessage.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-6-11<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.message;


import java.util.Map;

import com.china.center.common.MYException;


/**
 * 简单的底层通信机制(接受)
 * 
 * @author ZHUZHU
 * @version 2011-6-11
 * @see PublishMessage
 * @since 3.0
 */
public interface PublishMessage
{
    /**
     * 接受消息点对点消息
     * 
     * @param msgId
     * @param conent
     * @return
     */
    Map<String, String> publicP2PMessage(String msgId, String conent)
        throws MYException;

    /**
     * 接受必须出现的点对点答复(如果没有注册就抛出异常)
     * 
     * @param msgId
     * @param conent
     * @return
     * @throws MYException
     */
    Map<String, String> publicP2PMustMessage(String msgId, String conent)
        throws MYException;

    /**
     * 接受广播消息(如果是操作含事务,如果是查询不含事务)
     * 
     * @param msgId
     * @param conent
     * @return
     * @throws MYException
     */
    Map<String, String> publicCommonMessage(String msgId, String conent)
        throws MYException;

    Map<String, RegisterP2P> getP2pMap();

    Map<String, RegisterCommon> getCommonMap();
}
