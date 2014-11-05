/**
 * File Name: StafferHandlerPluginImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-5-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.plugin.impl;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.china.center.spring.ex.annotation.Exceptional;

import com.china.center.oa.flow.bean.FlowInstanceBean;
import com.china.center.oa.flow.bean.FlowViewerBean;
import com.china.center.oa.flow.constant.FlowConstant;
import com.china.center.oa.flow.dao.FlowInstanceDAO;
import com.china.center.oa.flow.dao.FlowInstanceViewDAO;
import com.china.center.oa.flow.dao.FlowViewerDAO;
import com.china.center.oa.flow.dao.TokenVSHanderDAO;
import com.china.center.oa.flow.plugin.HandlePlugin;
import com.china.center.oa.flow.vs.TokenVSHanderBean;
import com.china.center.oa.group.bean.GroupBean;
import com.china.center.oa.group.dao.GroupDAO;
import com.china.center.oa.group.dao.GroupVSStafferDAO;
import com.china.center.oa.group.vs.GroupVSStafferBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.StafferDAO;


/**
 * StafferHandlerPluginImpl
 * 
 * @author zhuzhu
 * @version 2009-5-3
 * @see GroupHandlePluginImpl
 * @since 1.0
 */
@Exceptional
public class GroupHandlePluginImpl extends AbstractHandlePlugin implements HandlePlugin
{
    private StafferDAO stafferDAO = null;

    private GroupDAO groupDAO = null;

    private TokenVSHanderDAO tokenVSHanderDAO = null;

    private GroupVSStafferDAO groupVSStafferDAO = null;

    private FlowInstanceDAO flowInstanceDAO = null;

    private FlowViewerDAO flowViewerDAO = null;

    private FlowInstanceViewDAO flowInstanceViewDAO = null;

    /**
     * default constructor
     */
    public GroupHandlePluginImpl()
    {
    }

    /**
     * hasAuth
     */
    public boolean hasAuth(String instanceId, List<String> processers)
    {
        FlowInstanceBean instance = flowInstanceDAO.find(instanceId);

        if (instance == null)
        {
            return false;
        }

        List<TokenVSHanderBean> list = tokenVSHanderDAO.queryTokenVSHanderByTokenIdAndType(
            instance.getCurrentTokenId(), getType());

        List<String> couldProcess = new ArrayList<String>();

        for (TokenVSHanderBean tokenVSHanderBean : list)
        {
            List<GroupVSStafferBean> vs = groupVSStafferDAO.queryEntityBeansByFK(tokenVSHanderBean.getProcesser());

            for (GroupVSStafferBean groupVSStafferBean : vs)
            {
                couldProcess.add(groupVSStafferBean.getStafferId());
            }
        }

        return couldProcess.containsAll(processers);
    }

    /**
     * processFlowInstanceViewer
     */
    public List<StafferBean> listInstanceViewer(String instanceId)
    {
        List<StafferBean> list = new ArrayList<StafferBean>();

        Set<StafferBean> set = new HashSet<StafferBean>();

        FlowInstanceBean instance = flowInstanceDAO.find(instanceId);

        if (instance == null)
        {
            return list;
        }

        List<FlowViewerBean> views = flowViewerDAO.queryEntityBeansByFK(instance.getFlowId());

        for (FlowViewerBean flowViewerBean : views)
        {
            List<GroupVSStafferBean> vs = groupVSStafferDAO.queryEntityBeansByFK(flowViewerBean.getProcesser());

            for (GroupVSStafferBean groupVSStafferBean : vs)
            {
                StafferBean sb = stafferDAO.find(groupVSStafferBean.getStafferId());

                if (sb != null)
                {
                    set.add(sb);
                }
            }
        }

        for (StafferBean stafferBean : set)
        {
            list.add(stafferBean);
        }

        return list;
    }

    /**
     * listNextHandler
     */
    public List<StafferBean> listNextHandler(String instanceId, String nextTokenId)
    {
        List<StafferBean> list = new ArrayList<StafferBean>();

        Set<StafferBean> set = new HashSet<StafferBean>();

        // 获得当期的vs
        List<TokenVSHanderBean> listVS = tokenVSHanderDAO.queryTokenVSHanderByTokenIdAndType(nextTokenId, getType());

        for (TokenVSHanderBean tokenVSHanderBean : listVS)
        {
            List<GroupVSStafferBean> vs = groupVSStafferDAO.queryEntityBeansByFK(tokenVSHanderBean.getProcesser());

            for (GroupVSStafferBean groupVSStafferBean : vs)
            {
                StafferBean sb = stafferDAO.find(groupVSStafferBean.getStafferId());

                if (sb != null)
                {
                    set.add(sb);
                }
            }
        }

        for (StafferBean stafferBean : set)
        {
            list.add(stafferBean);
        }

        return list;
    }

    /**
     * @return the stafferDAO
     */
    public StafferDAO getStafferDAO()
    {
        return stafferDAO;
    }

    /**
     * @param stafferDAO
     *            the stafferDAO to set
     */
    public void setStafferDAO(StafferDAO stafferDAO)
    {
        this.stafferDAO = stafferDAO;
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
     * @return the flowInstanceDAO
     */
    public FlowInstanceDAO getFlowInstanceDAO()
    {
        return flowInstanceDAO;
    }

    /**
     * @param flowInstanceDAO
     *            the flowInstanceDAO to set
     */
    public void setFlowInstanceDAO(FlowInstanceDAO flowInstanceDAO)
    {
        this.flowInstanceDAO = flowInstanceDAO;
    }

    /**
     * getType
     */
    public int getType()
    {
        return FlowConstant.FLOW_PLUGIN_GROUP;
    }

    /**
     * @return the flowInstanceViewDAO
     */
    public FlowInstanceViewDAO getFlowInstanceViewDAO()
    {
        return flowInstanceViewDAO;
    }

    /**
     * @param flowInstanceViewDAO
     *            the flowInstanceViewDAO to set
     */
    public void setFlowInstanceViewDAO(FlowInstanceViewDAO flowInstanceViewDAO)
    {
        this.flowInstanceViewDAO = flowInstanceViewDAO;
    }

    /**
     * @return the groupVSStafferDAO
     */
    public GroupVSStafferDAO getGroupVSStafferDAO()
    {
        return groupVSStafferDAO;
    }

    /**
     * @param groupVSStafferDAO
     *            the groupVSStafferDAO to set
     */
    public void setGroupVSStafferDAO(GroupVSStafferDAO groupVSStafferDAO)
    {
        this.groupVSStafferDAO = groupVSStafferDAO;
    }

    public String getHandleName(String handleId)
    {
        GroupBean group = groupDAO.find(handleId);

        if (group == null)
        {
            return "";
        }

        return group.getName();
    }

    /**
     * @return the groupDAO
     */
    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    /**
     * @param groupDAO
     *            the groupDAO to set
     */
    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
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
