/**
 * File Name: FlowTokenDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.flow.bean.FlowTokenBean;
import com.china.center.oa.flow.vo.FlowTokenVO;


/**
 * FlowTokenDAO
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see FlowTokenDAO
 * @since 1.0
 */
public interface FlowTokenDAO extends DAO<FlowTokenBean, FlowTokenVO>
{
    FlowTokenBean findToken(String flowId, int orders);

    FlowTokenBean findBeginToken(String flowId);

    FlowTokenBean findEndToken(String flowId);
}
