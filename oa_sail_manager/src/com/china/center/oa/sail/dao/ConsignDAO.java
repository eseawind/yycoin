/**
 * File Name: ConsignDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.dao;


import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.sail.bean.ConsignBean;
import com.china.center.oa.sail.bean.TransportBean;
import com.china.center.oa.sail.vo.ConsignVO;


/**
 * ConsignDAO
 * 
 * @author ZHUZHU
 * @version 2010-11-7
 * @see ConsignDAO
 * @since 1.0
 */
public interface ConsignDAO
{
    boolean addTransport(TransportBean bean);

    boolean updateTransport(TransportBean bean);

    TransportBean findTransport(String id);

    boolean addConsign(ConsignBean bean);

    boolean updateConsign(ConsignBean bean);

    boolean delConsign(String id);

    boolean delConsignBean(Serializable id);
    
    boolean delTransport(String id);

    List<TransportBean> listTransport();

    List<ConsignBean> queryConsignByCondition(ConditionParse condition);

    List<ConsignVO> queryDistinctConsignByCondition(ConditionParse condition);
    
    int countTransport(String transportId);

    /**
     * 查询第一个发货单(默认的)
     * 
     * @param id
     * @return
     */
    ConsignBean findDefaultConsignByFullId(String fullId);

    List<ConsignBean> queryConsignByFullId(String fullId);
    
    List<ConsignBean> queryConsignByDistId(String distId);

    ConsignBean findById(String gid);

    List<TransportBean> queryTransportByType(int type);

    List<TransportBean> queryTransportByParentId(String parentId);

    TransportBean findTransportById(String id);

    TransportBean findTransportByName(String name);

    int countByName(String name, int type);
    
    ConsignBean findDefaultConsignByDistId(String distId);
}
