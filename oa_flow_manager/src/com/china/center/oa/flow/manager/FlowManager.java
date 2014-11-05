/**
 * File Name: FlowManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.manager;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.flow.bean.FlowDefineBean;
import com.china.center.oa.flow.bean.FlowViewerBean;
import com.china.center.oa.flow.vo.FlowDefineVO;
import com.china.center.oa.flow.vo.FlowViewerVO;


/**
 * FlowManager
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see FlowManager
 * @since 1.0
 */
public interface FlowManager
{
    boolean addFlowDefine(User user, FlowDefineBean bean)
        throws MYException;

    boolean configFlowToken(User user, FlowDefineBean bean)
        throws MYException;

    boolean configFlowView(User user, FlowViewerBean bean)
        throws MYException;

    boolean updateFlowDefine(User user, FlowDefineBean bean)
        throws MYException;

    boolean delFlowDefine(User user, String flowId)
        throws MYException;

    boolean dropFlowDefine(User user, String flowId, boolean forceDrop)
        throws MYException;

    FlowDefineVO findFlowDefine(String id)
        throws MYException;

    FlowViewerVO findFlowViewer(String id)
        throws MYException;
}
