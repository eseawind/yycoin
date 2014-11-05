/**
 * File Name: FlowInstanceDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.flow.bean.FlowInstanceBean;
import com.china.center.oa.flow.constant.FlowConstant;
import com.china.center.oa.flow.dao.FlowInstanceDAO;
import com.china.center.oa.flow.vo.FlowInstanceVO;


/**
 * FlowInstanceDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see FlowInstanceDAOImpl
 * @since 1.0
 */
public class FlowInstanceDAOImpl extends BaseDAO<FlowInstanceBean, FlowInstanceVO> implements FlowInstanceDAO
{
    /**
     * count process Instance bt flow id
     * 
     * @param flowId
     * @return
     */
    public int countProcessInstanceByFlowId(String flowId)
    {
        return this.countByCondition("where flowId = ? and status != ?", flowId, FlowConstant.FLOW_INSTANCE_END);
    }

    /**
     * updateStatus
     * 
     * @param id
     * @param status
     * @return
     */
    public boolean updateStatus(String id, int status)
    {
        this.jdbcOperation.updateField("status", status, id, this.claz);

        return true;
    }

    /**
     * updateCurrentTokenId
     * 
     * @param id
     * @param currentTokenId
     * @return
     */
    public boolean updateCurrentTokenId(String id, String currentTokenId)
    {
        this.jdbcOperation.updateField("currentTokenId", currentTokenId, id, this.claz);

        return true;
    }

    /**
     * findByParentIdAndTokenId
     * 
     * @param parentId
     * @param tokenId
     * @return
     */
    public FlowInstanceBean findByParentIdAndTokenId(String parentId, String tokenId)
    {
        List<FlowInstanceBean> list = this.queryEntityBeansByCondition("where parentId = ? and parentTokenId = ?",
            parentId, tokenId);

        if (list.isEmpty())
        {
            return null;
        }

        return list.get(0);
    }

}
