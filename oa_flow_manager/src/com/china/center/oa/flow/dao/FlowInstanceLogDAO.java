/**
 * File Name: FlowInstanceLogDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.flow.bean.FlowInstanceLogBean;
import com.china.center.oa.flow.vo.FlowInstanceLogVO;


/**
 * FlowInstanceLogDAO
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see FlowInstanceLogDAO
 * @since 1.0
 */
public interface FlowInstanceLogDAO extends DAO<FlowInstanceLogBean, FlowInstanceLogVO>
{
    FlowInstanceLogBean findLastLog(String instanceId, String tokenId, String nextTokenId);

    FlowInstanceLogVO findLastLogVO(String instanceId, String tokenId);
}
