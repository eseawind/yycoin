/**
 * File Name: TcpFlowDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.tcp.bean.TcpFlowBean;


/**
 * TcpFlowDAO
 * 
 * @author ZHUZHU
 * @version 2011-7-17
 * @see TcpFlowDAO
 * @since 3.0
 */
public interface TcpFlowDAO extends DAO<TcpFlowBean, TcpFlowBean>
{
    /**
     * findByFlowKeyAndNextStatus
     * 
     * @param flowKey
     * @param nextStatus
     * @return
     */
    TcpFlowBean findByFlowKeyAndNextStatus(String flowKey, int nextStatus);
}
