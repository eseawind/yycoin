/**
 * File Name: WorkFlowFacade.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.facade;


import com.china.center.common.MYException;
import com.china.center.oa.flow.bean.FlowDefineBean;
import com.china.center.oa.flow.bean.FlowViewerBean;
import com.china.center.oa.flow.bean.TemplateFileBean;


/**
 * WorkFlowFacade
 * 
 * @author ZHUZHU
 * @version 2010-7-17
 * @see WorkFlowFacade
 * @since 1.0
 */
public interface WorkFlowFacade
{
    boolean addTemplateFile(String userId, TemplateFileBean bean)
        throws MYException;

    boolean updateTemplateFile(String userId, TemplateFileBean bean)
        throws MYException;

    boolean deleteTemplateFile(String userId, String id)
        throws MYException;

    boolean addFlowDefine(String userId, FlowDefineBean bean)
        throws MYException;

    boolean updateFlowDefine(String userId, FlowDefineBean bean)
        throws MYException;

    boolean configFlowToken(String userId, FlowDefineBean bean)
        throws MYException;

    boolean configFlowView(String userId, FlowViewerBean bean)
        throws MYException;

    boolean deleteFlowDefine(String userId, String flowId)
        throws MYException;

    boolean dropFlowDefine(String userId, String flowId, boolean forceDrop)
        throws MYException;
}
