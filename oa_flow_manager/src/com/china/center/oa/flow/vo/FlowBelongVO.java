/**
 *
 */
package com.china.center.oa.flow.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.flow.bean.FlowBelongBean;


/**
 * FlowBelongVO
 * 
 * @author zhuzhu
 * @version 2009-5-3
 * @see FlowBelongVO
 * @since 1.0
 */
@Entity(inherit = true)
public class FlowBelongVO extends FlowBelongBean
{
    @Relationship(relationField = "instanceId", tagField = "title")
    private String title = "";

    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    @Relationship(relationField = "createId")
    private String createName = "";

    @Relationship(relationField = "tokenId")
    private String tokenName = "";

    @Relationship(relationField = "flowId")
    private String flowName = "";

    /**
     *
     */
    public FlowBelongVO()
    {
    }

    /**
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
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
     * @param tokenName
     *            the tokenName to set
     */
    public void setTokenName(String tokenName)
    {
        this.tokenName = tokenName;
    }

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
     * @return the createName
     */
    public String getCreateName()
    {
        return createName;
    }

    /**
     * @param createName
     *            the createName to set
     */
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("FlowBelongVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("title = ")
            .append(this.title)
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("createName = ")
            .append(this.createName)
            .append(TAB)
            .append("tokenName = ")
            .append(this.tokenName)
            .append(TAB)
            .append("flowName = ")
            .append(this.flowName)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
