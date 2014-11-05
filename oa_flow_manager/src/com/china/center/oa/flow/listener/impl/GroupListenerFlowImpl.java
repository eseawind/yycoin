/**
 * File Name: FlowGroupListenerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.listener.impl;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.flow.constant.FlowConstant;
import com.china.center.oa.flow.dao.FlowViewerDAO;
import com.china.center.oa.flow.dao.TokenVSHanderDAO;
import com.china.center.oa.group.listener.GroupListener;


/**
 * FlowGroupListenerImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-15
 * @see GroupListenerFlowImpl
 * @since 1.0
 */
public class GroupListenerFlowImpl implements GroupListener
{
    private TokenVSHanderDAO tokenVSHanderDAO = null;

    private FlowViewerDAO flowViewerDAO = null;

    /**
     * default constructor
     */
    public GroupListenerFlowImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.group.listener.GroupListener#onDeleteGroup(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    public void onDeleteGroup(User user, String id)
        throws MYException
    {
        ConditionParse condition = new ConditionParse();

        condition.addWhereStr();

        condition.addCondition("processer", "=", id);

        condition.addIntCondition("type", "=", FlowConstant.FLOW_PLUGIN_GROUP);

        int count = tokenVSHanderDAO.countByCondition(condition.toString());

        if (count > 0)
        {
            throw new MYException("群组在流程环节中使用");
        }

        count = flowViewerDAO.countByCondition(condition.toString());

        if (count > 0)
        {
            throw new MYException("群组在流程查看中使用");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "Flow.Impl";
    }

    /**
     * @return the tokenVSHanderDAO
     */
    public TokenVSHanderDAO getTokenVSHanderDAO()
    {
        return tokenVSHanderDAO;
    }

    /**
     * @param tokenVSHanderDAO
     *            the tokenVSHanderDAO to set
     */
    public void setTokenVSHanderDAO(TokenVSHanderDAO tokenVSHanderDAO)
    {
        this.tokenVSHanderDAO = tokenVSHanderDAO;
    }

    /**
     * @return the flowViewerDAO
     */
    public FlowViewerDAO getFlowViewerDAO()
    {
        return flowViewerDAO;
    }

    /**
     * @param flowViewerDAO
     *            the flowViewerDAO to set
     */
    public void setFlowViewerDAO(FlowViewerDAO flowViewerDAO)
    {
        this.flowViewerDAO = flowViewerDAO;
    }

}
