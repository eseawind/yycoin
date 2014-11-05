/**
 * File Name: FlowDefineDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.flow.bean.FlowDefineBean;
import com.china.center.oa.flow.vo.FlowDefineVO;


/**
 * FlowDefineDAO
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see FlowDefineDAO
 * @since 1.0
 */
public interface FlowDefineDAO extends DAO<FlowDefineBean, FlowDefineVO>
{
    boolean updateStatus(String id, int status);
}
