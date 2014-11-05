/**
 * File Name: FlowInstanceLogVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-5-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.flow.bean.FlowInstanceLogBean;


/**
 * FlowInstanceLogVO
 * 
 * @author ZHUZHU
 * @version 2009-5-3
 * @see FlowInstanceLogVO
 * @since 1.0
 */
@Entity(inherit = true)
public class FlowInstanceLogVO extends FlowInstanceLogBean
{
    @Relationship(relationField = "flowId")
    private String flowName = "";

    @Relationship(relationField = "createId")
    private String createName = "";

    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    @Relationship(relationField = "nextStafferId")
    private String nextStafferName = "";

    @Relationship(relationField = "instanceId", tagField = "title")
    private String title = "";

    @Relationship(relationField = "tokenId")
    private String tokenName = "";

    @Relationship(relationField = "nextTokenId")
    private String nextTokenName = "";

    /**
     * default constructor
     */
    public FlowInstanceLogVO()
    {
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
     * @return the nextStafferName
     */
    public String getNextStafferName()
    {
        return nextStafferName;
    }

    /**
     * @param nextStafferName
     *            the nextStafferName to set
     */
    public void setNextStafferName(String nextStafferName)
    {
        this.nextStafferName = nextStafferName;
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
     * @return the nextTokenName
     */
    public String getNextTokenName()
    {
        return nextTokenName;
    }

    /**
     * @param nextTokenName
     *            the nextTokenName to set
     */
    public void setNextTokenName(String nextTokenName)
    {
        this.nextTokenName = nextTokenName;
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
            .append("FlowInstanceLogVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("flowName = ")
            .append(this.flowName)
            .append(TAB)
            .append("createName = ")
            .append(this.createName)
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("nextStafferName = ")
            .append(this.nextStafferName)
            .append(TAB)
            .append("title = ")
            .append(this.title)
            .append(TAB)
            .append("tokenName = ")
            .append(this.tokenName)
            .append(TAB)
            .append("nextTokenName = ")
            .append(this.nextTokenName)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
