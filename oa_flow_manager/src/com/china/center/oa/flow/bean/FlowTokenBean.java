/**
 *
 */
package com.china.center.oa.flow.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.flow.constant.FlowConstant;
import com.china.center.oa.flow.vs.TokenVSHanderBean;
import com.china.center.oa.flow.vs.TokenVSOperationBean;
import com.china.center.oa.flow.vs.TokenVSTemplateBean;


/**
 * @author Administrator
 */
@Entity(name = "流程环节")
@Table(name = "T_CENTER_OAFLOWDEFINETOKEN")
public class FlowTokenBean implements Serializable
{
    @Id
    private String id = "";

    private String name = "";

    @FK
    @Join(tagClass = FlowDefineBean.class)
    private String flowId = "";

    /**
     * 模式
     */
    private int mode = FlowConstant.TOKEN_MODE_SELF;

    /**
     * 环节类型
     */
    private int type = FlowConstant.TOKEN_TYPE_REALTOKEN;

    /**
     * 子流程
     */
    private String subFlowId = "";

    /**
     * 插件类型
     */
    private int pluginType = FlowConstant.FLOW_PLUGIN_STAFFER;

    private int preOrders = 0;

    private int orders = 0;

    private int nextOrders = 0;

    private boolean begining = false;

    private boolean ending = false;

    private String description = "";

    @Ignore
    private List<TokenVSHanderBean> handles = new ArrayList<TokenVSHanderBean>();

    @Ignore
    private TokenVSOperationBean operation = null;

    @Ignore
    private List<TokenVSTemplateBean> tempaltes = new ArrayList<TokenVSTemplateBean>();

    /**
     *
     */
    public FlowTokenBean()
    {
    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the flowId
     */
    public String getFlowId()
    {
        return flowId;
    }

    /**
     * @param flowId
     *            the flowId to set
     */
    public void setFlowId(String flowId)
    {
        this.flowId = flowId;
    }

    /**
     * @return the orders
     */
    public int getOrders()
    {
        return orders;
    }

    /**
     * @param orders
     *            the orders to set
     */
    public void setOrders(int orders)
    {
        this.orders = orders;
    }

    /**
     * @return the begining
     */
    public boolean isBegining()
    {
        return begining;
    }

    /**
     * @param begining
     *            the begining to set
     */
    public void setBegining(boolean begining)
    {
        this.begining = begining;
    }

    /**
     * @return the ending
     */
    public boolean isEnding()
    {
        return ending;
    }

    /**
     * @param ending
     *            the ending to set
     */
    public void setEnding(boolean ending)
    {
        this.ending = ending;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the preOrders
     */
    public int getPreOrders()
    {
        return preOrders;
    }

    /**
     * @param preOrders
     *            the preOrders to set
     */
    public void setPreOrders(int preOrders)
    {
        this.preOrders = preOrders;
    }

    /**
     * @return the nextOrders
     */
    public int getNextOrders()
    {
        return nextOrders;
    }

    /**
     * @param nextOrders
     *            the nextOrders to set
     */
    public void setNextOrders(int nextOrders)
    {
        this.nextOrders = nextOrders;
    }

    /**
     * @return the mode
     */
    public int getMode()
    {
        return mode;
    }

    /**
     * @param mode
     *            the mode to set
     */
    public void setMode(int mode)
    {
        this.mode = mode;
    }

    /**
     * @return the handles
     */
    public List<TokenVSHanderBean> getHandles()
    {
        return handles;
    }

    /**
     * @param handles
     *            the handles to set
     */
    public void setHandles(List<TokenVSHanderBean> handles)
    {
        this.handles = handles;
    }

    /**
     * @return the operation
     */
    public TokenVSOperationBean getOperation()
    {
        return operation;
    }

    /**
     * @param operation
     *            the operation to set
     */
    public void setOperation(TokenVSOperationBean operation)
    {
        this.operation = operation;
    }

    /**
     * @return the tempaltes
     */
    public List<TokenVSTemplateBean> getTempaltes()
    {
        return tempaltes;
    }

    /**
     * @param tempaltes
     *            the tempaltes to set
     */
    public void setTempaltes(List<TokenVSTemplateBean> tempaltes)
    {
        this.tempaltes = tempaltes;
    }

    /**
     * @return the pluginType
     */
    public int getPluginType()
    {
        return pluginType;
    }

    /**
     * @param pluginType
     *            the pluginType to set
     */
    public void setPluginType(int pluginType)
    {
        this.pluginType = pluginType;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return the subFlowId
     */
    public String getSubFlowId()
    {
        return subFlowId;
    }

    /**
     * @param subFlowId
     *            the subFlowId to set
     */
    public void setSubFlowId(String subFlowId)
    {
        this.subFlowId = subFlowId;
    }
}
