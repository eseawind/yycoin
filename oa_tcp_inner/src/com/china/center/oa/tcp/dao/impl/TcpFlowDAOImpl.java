/**
 * File Name: TcpFlowDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.dao.impl;


import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.tcp.bean.TcpFlowBean;
import com.china.center.oa.tcp.dao.TcpFlowDAO;


/**
 * TcpFlowDAOImpl
 * 
 * @author ZHUZHU
 * @version 2011-7-17
 * @see TcpFlowDAOImpl
 * @since 3.0
 */
public class TcpFlowDAOImpl extends BaseDAO<TcpFlowBean, TcpFlowBean> implements TcpFlowDAO
{
    public TcpFlowBean findByFlowKeyAndNextStatus(String flowKey, int nextStatus)
    {
        return this.findUnique("where flowKey = ? and nextStatus = ?", flowKey, nextStatus);
    }
}
