/**
 * File Name: FlowViewerVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-5-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.oa.flow.bean.FlowViewerBean;


/**
 * FlowViewerVO
 * 
 * @author zhuzhu
 * @version 2009-5-10
 * @see FlowViewerVO
 * @since 1.0
 */
@Entity(inherit = true)
public class FlowViewerVO extends FlowViewerBean
{
    @Ignore
    private String processerName = "";
    
    @Ignore
    private String processerType = "";
    
    

    /**
     * default constructor
     */
    public FlowViewerVO()
    {}

    /**
     * @return the processerName
     */
    public String getProcesserName()
    {
        return processerName;
    }

    /**
     * @param processerName
     *            the processerName to set
     */
    public void setProcesserName(String processerName)
    {
        this.processerName = processerName;
    }

    /**
     * @return the processerType
     */
    public String getProcesserType()
    {
        return processerType;
    }

    /**
     * @param processerType the processerType to set
     */
    public void setProcesserType(String processerType)
    {
        this.processerType = processerType;
    }
}
