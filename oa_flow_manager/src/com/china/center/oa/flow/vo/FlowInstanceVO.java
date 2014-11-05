/**
 *
 */
package com.china.center.oa.flow.vo;


import com.china.center.jdbc.annotation.Alias;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.flow.bean.FlowInstanceBean;


/**
 * @author Administrator
 */
@Entity(inherit = true)
public class FlowInstanceVO extends FlowInstanceBean
{
    @Relationship(relationField = "flowId")
    private String flowName = "";

    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    @Relationship(relationField = "currentTokenId")
    private String tokenName = "";
    
    @Alias(aliasField = "id")
    private String instanceId = "";

    /**
     * default constructor
     */
    public FlowInstanceVO()
    {}

    /**
     * @return the flowName
     */
    public String getFlowName()
    {
        return flowName;
    }

    /**
     * @param flowName
     *            the flowName to set
     */
    public void setFlowName(String flowName)
    {
        this.flowName = flowName;
    }

    /**
     * @return the stafferName
     */
    public String getStafferName()
    {
        return stafferName;
    }

    /**
     * @param stafferName
     *            the stafferName to set
     */
    public void setStafferName(String stafferName)
    {
        this.stafferName = stafferName;
    }

    /**
     * @return the tokenName
     */
    public String getTokenName()
    {
        return tokenName;
    }

    /**
     * @param tokenName the tokenName to set
     */
    public void setTokenName(String tokenName)
    {
        this.tokenName = tokenName;
    }

    /**
     * @return the instanceId
     */
    public String getInstanceId()
    {
        return instanceId;
    }

    /**
     * @param instanceId the instanceId to set
     */
    public void setInstanceId(String instanceId)
    {
        this.instanceId = instanceId;
    }
}
