/**
 * File Name: TokenVSHanderVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-5-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.oa.flow.vs.TokenVSHanderBean;


/**
 * TokenVSHanderVO
 * 
 * @author zhuzhu
 * @version 2009-5-2
 * @see TokenVSHanderVO
 * @since 1.0
 */
@Entity(inherit = true)
public class TokenVSHanderVO extends TokenVSHanderBean
{
    @Ignore
    private String processName = "";

    @Ignore
    private String processType = "";

    /**
     * default constructor
     */
    public TokenVSHanderVO()
    {}

    /**
     * @return the processName
     */
    public String getProcessName()
    {
        return processName;
    }

    /**
     * @param processName
     *            the processName to set
     */
    public void setProcessName(String processName)
    {
        this.processName = processName;
    }

    /**
     * @return the processType
     */
    public String getProcessType()
    {
        return processType;
    }

    /**
     * @param processType
     *            the processType to set
     */
    public void setProcessType(String processType)
    {
        this.processType = processType;
    }
}
