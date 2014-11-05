/**
 * File Name: FlowViewerDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.dao.impl;


import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.flow.bean.FlowViewerBean;
import com.china.center.oa.flow.dao.FlowViewerDAO;
import com.china.center.oa.flow.vo.FlowViewerVO;


/**
 * FlowViewerDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see FlowViewerDAOImpl
 * @since 1.0
 */
public class FlowViewerDAOImpl extends BaseDAO<FlowViewerBean, FlowViewerVO> implements FlowViewerDAO
{
    /**
     * countByProcesser
     * 
     * @param processer
     * @return
     */
    public int countByProcesser(String processer)
    {
        return this.countByCondition("where processer = ?", processer);
    }
}
