/**
 * File Name: FlowLogDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-20<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.publics.bean.FlowLogBean;


/**
 * FlowLogDAO
 * 
 * @author ZHUZHU
 * @version 2010-9-20
 * @see FlowLogDAO
 * @since 1.0
 */
public interface FlowLogDAO extends DAO<FlowLogBean, FlowLogBean>
{
    /**
     * 获取最近一个日志
     * 
     * @param refId
     * @return
     */
    FlowLogBean findLastLog(String refId);
}
