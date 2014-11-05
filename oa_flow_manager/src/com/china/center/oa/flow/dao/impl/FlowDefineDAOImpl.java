/**
 * File Name: FlowDefineDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.dao.impl;


import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.flow.bean.FlowDefineBean;
import com.china.center.oa.flow.dao.FlowDefineDAO;
import com.china.center.oa.flow.vo.FlowDefineVO;


/**
 * FlowDefineDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see FlowDefineDAOImpl
 * @since 1.0
 */
public class FlowDefineDAOImpl extends BaseDAO<FlowDefineBean, FlowDefineVO> implements FlowDefineDAO
{
    /**
     * updateStatus
     * 
     * @param id
     * @param status
     * @return
     */
    public boolean updateStatus(String id, int status)
    {
        this.jdbcOperation.updateField("status", status, id, claz);

        return true;
    }
}
