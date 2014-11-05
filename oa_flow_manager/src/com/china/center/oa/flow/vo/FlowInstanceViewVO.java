/**
 * File Name: FlowInstanceViewBeanVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-9-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.flow.bean.FlowInstanceViewBean;


/**
 * FlowInstanceViewVO
 * 
 * @author ZHUZHU
 * @version 2008-9-3
 * @see
 * @since
 */
@Entity(inherit = true)
public class FlowInstanceViewVO extends FlowInstanceViewBean
{
    @Relationship(relationField = "instanceId", tagField = "title")
    private String title = "";

    @Relationship(relationField = "flowId")
    private String flowName = "";

    @Ignore
    private String tokenName = "";

    @Relationship(relationField = "createId")
    private String createName = "";

    /**
     * default constructor
     */
    public FlowInstanceViewVO()
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
            .append("FlowInstanceViewVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("title = ")
            .append(this.title)
            .append(TAB)
            .append("flowName = ")
            .append(this.flowName)
            .append(TAB)
            .append("tokenName = ")
            .append(this.tokenName)
            .append(TAB)
            .append("createName = ")
            .append(this.createName)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
