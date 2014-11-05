/**
 * File Name: FlowInstanceManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.manager;


import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.flow.bean.FlowInstanceBean;
import com.china.center.oa.flow.bean.InstanceTemplateBean;


/**
 * FlowInstanceManager
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see FlowInstanceManager
 * @since 1.0
 */
public interface FlowInstanceManager
{
    boolean addFlowInstance(User user, FlowInstanceBean bean, int operation, List<String> processers)
        throws MYException;

    boolean addFlowInstanceTemplate(User user, List<InstanceTemplateBean> files)
        throws MYException;

    boolean updateFlowInstance(User user, FlowInstanceBean bean, int operation, List<String> processers)
        throws MYException;

    boolean deleteFlowInstance(User user, String id)
        throws MYException;

    boolean passFlowInstance(User user, String instanceId, String opinion, List<String> processers)
        throws MYException;

    boolean rejectFlowInstance(User user, String instanceId, String opinion)
        throws MYException;

    boolean rejectToRootFlowInstance(User user, String instanceId, String opinion)
        throws MYException;

    boolean rejectAllFlowInstance(User user, String instanceId, String opinion)
        throws MYException;

    boolean exceptionEndFlowInstance(User user, String instanceId, String opinion)
        throws MYException;

    void deleteTempTemplateFile();

    boolean hasOwenInstance(User user, String instanceId);

}
