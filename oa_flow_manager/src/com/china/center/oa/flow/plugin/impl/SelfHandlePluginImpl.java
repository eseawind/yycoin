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

import com.china.center.oa.flow.constant.FlowConstant;
import com.china.center.oa.flow.dao.FlowInstanceDAO;
import com.china.center.oa.flow.dao.FlowInstanceViewDAO;
import com.china.center.oa.flow.dao.TokenVSHanderDAO;
import com.china.center.oa.flow.plugin.HandlePlugin;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.StafferDAO;


/**
 * SelfHandlerPluginImpl
 * 
 * @author zhuzhu
 * @version 2009-5-3
 * @see SelfHandlePluginImpl
 * @since 1.0
 */
@Exceptional
public class SelfHandlePluginImpl extends AbstractHandlePlugin implements HandlePlugin
{
    private StafferDAO stafferDAO = null;

    private TokenVSHanderDAO tokenVSHanderDAO = null;

    private FlowInstanceDAO flowInstanceDAO = null;

    private FlowInstanceViewDAO flowInstanceViewDAO = null;

    /**
     * default constructor
     */
    public SelfHandlePluginImpl()
    {
    }

    /**
     * hasAuth
     */
    public boolean hasAuth(String instanceId, List<String> processers)
    {
        return true;
    }

    /**
     * processFlowInstanceViewer
     */
    public List<StafferBean> listInstanceViewer(String instanceId)
    {
        List<StafferBean> list = new ArrayList<StafferBean>();

        return list;
    }

    /**
     * listNextHandler
     */
    public List<StafferBean> listNextHandler(String instanceId, String nextTokenId)
    {
        return stafferDAO.listCommonEntityBeans();
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
        return FlowConstant.FLOW_PLUGIN_SELF;
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
        return "不限";
    }

}
