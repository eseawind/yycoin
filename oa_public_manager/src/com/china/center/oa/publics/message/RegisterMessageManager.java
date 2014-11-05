/**
 * File Name: RegisterManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-6-11<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.message;


import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * RegisterMessageManager(注册消息)
 * 
 * @author ZHUZHU
 * @version 2011-6-11
 * @see RegisterMessageManager
 * @since 3.0
 */
public class RegisterMessageManager
{
    private final Log _logger = LogFactory.getLog(getClass());

    private PublishMessage publishMessage = null;

    private List<RegisterP2P> p2pList = null;

    private List<RegisterCommon> commonList = null;

    private static final Object LOCK_OBJECT = new Object();

    /**
     * default constructor
     */
    public RegisterMessageManager()
    {
    }

    public void init()
    {
        synchronized (LOCK_OBJECT)
        {
            if (p2pList != null)
            {
                for (RegisterP2P each : p2pList)
                {
                    boolean add = true;

                    Collection<RegisterP2P> values = publishMessage.getP2pMap().values();

                    for (RegisterP2P subEach : values)
                    {
                        if (subEach.getMessageType().equals(each.getMessageType()))
                        {
                            _logger.error("RegisterMessageManager RegisterP2P Duplicate key:"
                                          + each.getMessageType() + ".By " + subEach.getKey());

                            add = false;

                            break;
                        }
                    }

                    if (add)
                    {
                        publishMessage.getP2pMap().put(each.getKey(), each);
                    }
                }
            }

            if (commonList != null)
            {
                for (RegisterCommon each : commonList)
                {
                    publishMessage.getCommonMap().put(each.getKey(), each);
                }
            }
        }
    }

    public void destroy()
    {
        synchronized (LOCK_OBJECT)
        {
            if (p2pList != null)
            {
                for (RegisterP2P each : p2pList)
                {
                    publishMessage.getP2pMap().remove(each.getKey());
                }
            }

            if (commonList != null)
            {
                for (RegisterCommon each : commonList)
                {
                    publishMessage.getCommonMap().remove(each.getKey());
                }
            }
        }
    }

    /**
     * @return the publishMessage
     */
    public PublishMessage getPublishMessage()
    {
        return publishMessage;
    }

    /**
     * @param publishMessage
     *            the publishMessage to set
     */
    public void setPublishMessage(PublishMessage publishMessage)
    {
        this.publishMessage = publishMessage;
    }

    /**
     * @return the p2pList
     */
    public List<RegisterP2P> getP2pList()
    {
        return p2pList;
    }

    /**
     * @param list
     *            the p2pList to set
     */
    public void setP2pList(List<RegisterP2P> list)
    {
        p2pList = list;
    }

    /**
     * @return the commonList
     */
    public List<RegisterCommon> getCommonList()
    {
        return commonList;
    }

    /**
     * @param commonList
     *            the commonList to set
     */
    public void setCommonList(List<RegisterCommon> commonList)
    {
        this.commonList = commonList;
    }
}
