/**
 * File Name: FlowViewerDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.flow.bean.FlowViewerBean;
import com.china.center.oa.flow.vo.FlowViewerVO;


/**
 * FlowViewerDAO
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see FlowViewerDAO
 * @since 1.0
 */
public interface FlowViewerDAO extends DAO<FlowViewerBean, FlowViewerVO>
{
    int countByProcesser(String processer);
}
