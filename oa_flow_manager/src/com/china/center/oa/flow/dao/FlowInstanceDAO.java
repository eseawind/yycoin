/**
 * File Name: FlowInstanceDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.flow.bean.FlowInstanceBean;
import com.china.center.oa.flow.vo.FlowInstanceVO;


/**
 * FlowInstanceDAO
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see FlowInstanceDAO
 * @since 1.0
 */
public interface FlowInstanceDAO extends DAO<FlowInstanceBean, FlowInstanceVO>
{
    int countProcessInstanceByFlowId(String flowId);

    boolean updateStatus(String id, int status);

    boolean updateCurrentTokenId(String id, String currentTokenId);

    FlowInstanceBean findByParentIdAndTokenId(String parentId, String tokenId);
}
