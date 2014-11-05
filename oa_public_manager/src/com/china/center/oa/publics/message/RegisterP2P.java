/**
 * File Name: RegisterP2P.java<br>
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
 * 注册p2p的实现(解决方向依赖)
 * 
 * @author ZHUZHU
 * @version 2011-6-11
 * @see RegisterP2P
 * @since 3.0
 */
public interface RegisterP2P extends MessageType
{
    /**
     * 发布消息点对点消息
     * 
     * @param msgId
     * @param conent
     * @return
     */
    Map<String, String> publicP2PMessage(String msgId, String conent)
        throws MYException;
}
