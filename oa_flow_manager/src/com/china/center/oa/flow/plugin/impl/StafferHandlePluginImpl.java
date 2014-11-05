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
import java.util.List;

import org.china.center.spring.ex.annotation.Exceptional;

import com.china.center.oa.flow.bean.FlowInstanceBean;
import com.china.center.oa.flow.bean.FlowInstanceViewBean;
import com.china.center.oa.flow.constant.FlowConstant;
import com.china.center.oa.flow.dao.FlowInstanceDAO;
import com.china.center.oa.flow.dao.FlowInstanceViewDAO;
import com.china.center.oa.flow.dao.TokenVSHanderDAO;
import com.china.center.oa.flow.plugin.HandlePlugin;
import com.china.center.oa.flow.vs.TokenVSHanderBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.StafferDAO;


/**
 * StafferHandlerPluginImpl
 * 
 * @author zhuzhu
 * @version 2009-5-3
 * @see StafferHandlePluginImpl
 * @since 1.0
 */
@Exceptional
public class StafferHandlePluginImpl extends AbstractHandlePlugin implements HandlePlugin
{
    private StafferDAO stafferDAO = null;

    private TokenVSHanderDAO tokenVSHanderDAO = null;

    private FlowInstanceDAO flowInstanceDAO = null;

    private FlowInstanceViewDAO flowInstanceViewDAO = null;

    /**
     * default constructor
     */
    public StafferHandlePluginImpl()
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

        for (String string : processers)
        {
            boolean auth = false;

            for (TokenVSHanderBean tokenVSHanderBean : list)
            {
                if (tokenVSHanderBean.getProcesser().equals(string))
                {
                    auth = true;
                    break;
                }
            }

            if ( !auth)
            {
                return false;
            }
        }

        return true;
    }

    /**
     * processFlowInstanceViewer
     */
    public List<StafferBean> listInstanceViewer(String instanceId)
    {
        List<StafferBean> list = new ArrayList<StafferBean>();

        FlowInstanceBean instance = flowInstanceDAO.find(instanceId);

        if (instance == null)
        {
            return list;
        }

        List<FlowInstanceViewBean> views = flowInstanceViewDAO.queryEntityBeansByFK(instance.getFlowId());

        for (FlowInstanceViewBean flowInstanceViewBean : views)
        {
            StafferBean sb = stafferDAO.find(flowInstanceViewBean.getViewer());

            if (sb != null)
            {
                list.add(sb);
            }
        }

        return list;
    }

    /**
     * listNextHandler
     */
    public List<StafferBean> listNextHandler(String instanceId, String nextTokenId)
    {
        List<StafferBean> list = new ArrayList<StafferBean>();

        List<TokenVSHanderBean> listVS = tokenVSHanderDAO.queryTokenVSHanderByTokenIdAndType(nextTokenId, getType());

        for (TokenVSHanderBean tokenVSHanderBean : listVS)
        {
            StafferBean sb = stafferDAO.find(tokenVSHanderBean.getProcesser());

            if (sb != null)
            {
                list.add(sb);
            }
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
        return FlowConstant.FLOW_PLUGIN_STAFFER;
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

    public String getHandleName(String handleId)
    {
        StafferBean bean = stafferDAO.find(handleId);

        if (bean == null)
        {
            return "";
        }

        return bean.getName();
    }

}
