/**
 *
 */
package com.china.center.oa.flow.vo;


import java.util.ArrayList;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.flow.bean.FlowDefineBean;


/**
 * @author Administrator
 */
@Entity(inherit = true)
public class FlowDefineVO extends FlowDefineBean
{
    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    @Ignore
    private List<FlowTokenVO> tokenVOs = new ArrayList<FlowTokenVO>();

    /**
     * default constructor
     */
    public FlowDefineVO()
    {}

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
     * @return the tokenVOs
     */
    public List<FlowTokenVO> getTokenVOs()
    {
        return tokenVOs;
    }

    /**
     * @param tokenVOs
     *            the tokenVOs to set
     */
    public void setTokenVOs(List<FlowTokenVO> tokenVOs)
    {
        this.tokenVOs = tokenVOs;
    }
}
