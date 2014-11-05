/**
 * File Name: FlowTokenDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.dao.impl;


import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.flow.bean.FlowTokenBean;
import com.china.center.oa.flow.dao.FlowTokenDAO;
import com.china.center.oa.flow.vo.FlowTokenVO;


/**
 * FlowTokenDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see FlowTokenDAOImpl
 * @since 1.0
 */
public class FlowTokenDAOImpl extends BaseDAO<FlowTokenBean, FlowTokenVO> implements FlowTokenDAO
{
    public FlowTokenBean findToken(String flowId, int orders)
    {
        return this.findUnique("where flowId = ? and orders = ?", flowId, orders);
    }

    public FlowTokenBean findBeginToken(String flowId)
    {
        return this.findUnique("where flowId = ? and begining = ?", flowId, true);
    }

    public FlowTokenBean findEndToken(String flowId)
    {
        return this.findUnique("where flowId = ? and ending = ?", flowId, true);
    }
}
