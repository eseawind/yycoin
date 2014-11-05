/**
 * File Name: FlowInstanceLogDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.flow.bean.FlowInstanceLogBean;
import com.china.center.oa.flow.dao.FlowInstanceLogDAO;
import com.china.center.oa.flow.vo.FlowInstanceLogVO;


/**
 * FlowInstanceLogDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see FlowInstanceLogDAOImpl
 * @since 1.0
 */
public class FlowInstanceLogDAOImpl extends BaseDAO<FlowInstanceLogBean, FlowInstanceLogVO> implements FlowInstanceLogDAO
{
    /**
     * findLastLog
     * 
     * @param instanceId
     * @param tokenId
     * @param nextTokenId
     * @return
     */
    public FlowInstanceLogBean findLastLog(String instanceId, String tokenId, String nextTokenId)
    {
        List<FlowInstanceLogBean> list = this.queryEntityBeansByCondition(
            "where instanceId = ? and tokenId = ? and nextTokenId = ? order by logTime desc", instanceId, tokenId,
            nextTokenId);

        if (list.isEmpty())
        {
            return null;
        }

        return list.get(0);
    }

    public FlowInstanceLogVO findLastLogVO(String instanceId, String tokenId)
    {
        List<FlowInstanceLogVO> list = this.queryEntityVOsByCondition(
            "where FlowInstanceLogBean.instanceId = ? and FlowInstanceLogBean.tokenId = ? order by logTime desc",
            instanceId, tokenId);

        if (list.isEmpty())
        {
            return null;
        }

        return list.get(0);
    }
}
