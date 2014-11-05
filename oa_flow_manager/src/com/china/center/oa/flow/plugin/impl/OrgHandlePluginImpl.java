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

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.oa.flow.bean.FlowInstanceBean;
import com.china.center.oa.flow.constant.FlowConstant;
import com.china.center.oa.flow.dao.FlowInstanceDAO;
import com.china.center.oa.flow.dao.FlowInstanceViewDAO;
import com.china.center.oa.flow.dao.FlowViewerDAO;
import com.china.center.oa.flow.dao.TokenVSHanderDAO;
import com.china.center.oa.flow.plugin.HandlePlugin;
import com.china.center.oa.group.dao.GroupDAO;
import com.china.center.oa.group.dao.GroupVSStafferDAO;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.PrincipalshipDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.StafferVSPriDAO;
import com.china.center.oa.publics.vs.StafferVSPriBean;
import com.china.center.tools.StringTools;


/**
 * OrgHandlePluginImpl(查询自己的上级主管)<br>
 * 由于人员在组织结构里面存在多个,所以上级会存在多个
 * 
 * @author ZHUZHU
 * @version 2009-5-3
 * @see OrgHandlePluginImpl
 * @since 1.0
 */
@Exceptional
public class OrgHandlePluginImpl extends AbstractHandlePlugin implements HandlePlugin
{
    private StafferDAO stafferDAO = null;

    private GroupDAO groupDAO = null;

    private StafferVSPriDAO stafferVSPriDAO = null;

    private PrincipalshipDAO principalshipDAO = null;

    private TokenVSHanderDAO tokenVSHanderDAO = null;

    private GroupVSStafferDAO groupVSStafferDAO = null;

    private FlowInstanceDAO flowInstanceDAO = null;

    private FlowViewerDAO flowViewerDAO = null;

    private FlowInstanceViewDAO flowInstanceViewDAO = null;

    /**
     * default constructor
     */
    public OrgHandlePluginImpl()
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

        String createrId = instance.getStafferId();

        List<String> couldProcess = new ArrayList<String>();

        // 获得人员的组织结构
        List<StafferVSPriBean> vsList = stafferVSPriDAO.queryEntityBeansByFK(createrId);

        // 循环获得所有可以操作的人员
        for (StafferVSPriBean stafferVSPriBean : vsList)
        {
            PrincipalshipBean pri = principalshipDAO.find(stafferVSPriBean.getPrincipalshipId());

            if (pri == null || StringTools.isNullOrNone(pri.getParentId()))
            {
                continue;
            }

            String parentId = pri.getParentId();

            List<StafferVSPriBean> svsp = stafferVSPriDAO.queryEntityBeansByFK(parentId, AnoConstant.FK_FIRST);

            for (StafferVSPriBean each : svsp)
            {
                couldProcess.add(each.getStafferId());
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

        String createrId = instance.getStafferId();

        // 获得人员的组织结构
        List<StafferVSPriBean> vsList = stafferVSPriDAO.queryEntityBeansByFK(createrId);

        // 循环获得所有可以操作的人员
        for (StafferVSPriBean stafferVSPriBean : vsList)
        {
            PrincipalshipBean pri = principalshipDAO.find(stafferVSPriBean.getPrincipalshipId());

            if (pri == null || StringTools.isNullOrNone(pri.getParentId()))
            {
                continue;
            }

            String parentId = pri.getParentId();

            List<StafferVSPriBean> svsp = stafferVSPriDAO.queryEntityBeansByFK(parentId, AnoConstant.FK_FIRST);

            for (StafferVSPriBean each : svsp)
            {
                set.add(stafferDAO.find(each.getStafferId()));
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

        FlowInstanceBean instance = flowInstanceDAO.find(instanceId);

        if (instance == null)
        {
            return list;
        }

        String createrId = instance.getStafferId();

        Set<StafferBean> set = new HashSet<StafferBean>();

        // 获得人员的组织结构
        List<StafferVSPriBean> vsList = stafferVSPriDAO.queryEntityBeansByFK(createrId);

        // 循环获得所有可以操作的人员
        for (StafferVSPriBean stafferVSPriBean : vsList)
        {
            PrincipalshipBean pri = principalshipDAO.find(stafferVSPriBean.getPrincipalshipId());

            if (pri == null || StringTools.isNullOrNone(pri.getParentId()))
            {
                continue;
            }

            String parentId = pri.getParentId();

            List<StafferVSPriBean> svsp = stafferVSPriDAO.queryEntityBeansByFK(parentId, AnoConstant.FK_FIRST);

            for (StafferVSPriBean each : svsp)
            {
                set.add(stafferDAO.find(each.getStafferId()));
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
        return FlowConstant.FLOW_PLUGIN_ORG;
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
        return "上级主管";
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

    /**
     * @return the stafferVSPriDAO
     */
    public StafferVSPriDAO getStafferVSPriDAO()
    {
        return stafferVSPriDAO;
    }

    /**
     * @param stafferVSPriDAO
     *            the stafferVSPriDAO to set
     */
    public void setStafferVSPriDAO(StafferVSPriDAO stafferVSPriDAO)
    {
        this.stafferVSPriDAO = stafferVSPriDAO;
    }

    /**
     * @return the principalshipDAO
     */
    public PrincipalshipDAO getPrincipalshipDAO()
    {
        return principalshipDAO;
    }

    /**
     * @param principalshipDAO
     *            the principalshipDAO to set
     */
    public void setPrincipalshipDAO(PrincipalshipDAO principalshipDAO)
    {
        this.principalshipDAO = principalshipDAO;
    }

}
