/**
 * File Name: TravelApplyManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.manager;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;


/**
 * 包括TCP的公共处理
 * 
 * @author ZHUZHU
 * @version 2011-7-10
 * @see TcpFlowManager
 * @since 3.0
 */
public interface TcpFlowManager
{
    /**
     * 认领工单
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean drawApprove(User user, String id)
        throws MYException;

    /**
     * 退领工单
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean odrawApprove(User user, String id)
        throws MYException;
    
    boolean endApprove(User user, String id)
    throws MYException;
}
