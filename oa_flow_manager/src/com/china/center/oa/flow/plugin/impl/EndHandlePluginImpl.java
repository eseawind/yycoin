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
import com.china.center.oa.flow.plugin.HandlePlugin;
import com.china.center.oa.publics.bean.StafferBean;


/**
 * SelfHandlerPluginImpl
 * 
 * @author zhuzhu
 * @version 2009-5-3
 * @see EndHandlePluginImpl
 * @since 1.0
 */
@Exceptional
public class EndHandlePluginImpl extends AbstractHandlePlugin implements HandlePlugin
{
    /**
     * default constructor
     */
    public EndHandlePluginImpl()
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
        return new ArrayList<StafferBean>();
    }

    /**
     * listNextHandler
     */
    public List<StafferBean> listNextHandler(String instanceId, String nextTokenId)
    {
        return new ArrayList<StafferBean>();
    }

    /**
     * getType
     */
    public int getType()
    {
        return FlowConstant.FLOW_PLUGIN_END;
    }

    public String getHandleName(String handleId)
    {
        return "NA";
    }
}
