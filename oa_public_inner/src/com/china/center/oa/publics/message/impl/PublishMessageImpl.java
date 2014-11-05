/**
 * File Name: CommonMessageImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-6-11<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.message.impl;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.china.center.common.MYException;
import com.china.center.oa.publics.message.PublishMessage;
import com.china.center.oa.publics.message.RegisterCommon;
import com.china.center.oa.publics.message.RegisterP2P;
import com.china.center.oa.publics.statics.PublicStatic;


/**
 * CommonMessageImpl
 * 
 * @author ZHUZHU
 * @version 2011-6-11
 * @see PublishMessageImpl
 * @since 3.0
 */
public class PublishMessageImpl implements PublishMessage
{
    public Map<String, String> publicCommonMessage(String msgId, String conent)
        throws MYException
    {
        Map<String, String> result = new HashMap<String, String>();

        Collection<RegisterCommon> values = PublicStatic.getCommonMap().values();

        for (RegisterCommon each : values)
        {
            if (msgId.equals(each.getMessageType()))
            {
                Map<String, String> temp = each.publicCommonMessage(msgId, conent);

                if (temp != null && temp.size() > 0)
                {
                    result.putAll(temp);
                }
            }
        }

        return result;
    }

    public Map<String, String> publicP2PMessage(String msgId, String conent)
        throws MYException
    {
        Map<String, String> result = new HashMap<String, String>();

        Collection<RegisterP2P> values = PublicStatic.getP2pMap().values();

        for (RegisterP2P each : values)
        {
            if (msgId.equals(each.getMessageType()))
            {
                Map<String, String> temp = each.publicP2PMessage(msgId, conent);

                if (temp != null && temp.size() > 0)
                {
                    result.putAll(temp);

                    break;
                }
            }
        }

        return result;
    }

    public Map<String, String> publicP2PMustMessage(String msgId, String conent)
        throws MYException
    {
        Map<String, String> result = new HashMap<String, String>();

        Collection<RegisterP2P> values = PublicStatic.getP2pMap().values();

        boolean isCarry = false;

        for (RegisterP2P each : values)
        {
            if (msgId.equals(each.getMessageType()))
            {
                isCarry = true;

                Map<String, String> temp = each.publicP2PMessage(msgId, conent);

                if (temp != null && temp.size() > 0)
                {
                    result.putAll(temp);

                    break;
                }
            }
        }

        if ( !isCarry)
        {
            throw new MYException("必须出现一个点对点实现");
        }

        return result;
    }

    /**
     * @return the p2pMap
     */
    public Map<String, RegisterP2P> getP2pMap()
    {
        return PublicStatic.getP2pMap();
    }

    /**
     * @return the commonMap
     */
    public Map<String, RegisterCommon> getCommonMap()
    {
        return PublicStatic.getCommonMap();
    }
}
