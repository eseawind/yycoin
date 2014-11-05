/**
 * File Name: FlowTokenVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-5-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.vo;


import java.util.ArrayList;
import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.oa.flow.bean.FlowTokenBean;


/**
 * FlowTokenVO
 * 
 * @author zhuzhu
 * @version 2009-5-2
 * @see FlowTokenVO
 * @since 1.0
 */
@Entity(inherit = true)
public class FlowTokenVO extends FlowTokenBean
{
    @Ignore
    private List<TokenVSHanderVO> handleVOs = new ArrayList<TokenVSHanderVO>();

    @Ignore
    private List<TokenVSTemplateVO> tempalteVOs = new ArrayList<TokenVSTemplateVO>();

    @Ignore
    private FlowInstanceLogVO log = null;

    /**
     * default constructor
     */
    public FlowTokenVO()
    {}

    /**
     * @return the handleVOs
     */
    public List<TokenVSHanderVO> getHandleVOs()
    {
        return handleVOs;
    }

    /**
     * @param handleVOs
     *            the handleVOs to set
     */
    public void setHandleVOs(List<TokenVSHanderVO> handleVOs)
    {
        this.handleVOs = handleVOs;
    }

    /**
     * @return the tempalteVOs
     */
    public List<TokenVSTemplateVO> getTempalteVOs()
    {
        return tempalteVOs;
    }

    /**
     * @param tempalteVOs
     *            the tempalteVOs to set
     */
    public void setTempalteVOs(List<TokenVSTemplateVO> tempalteVOs)
    {
        this.tempalteVOs = tempalteVOs;
    }

    /**
     * @return the log
     */
    public FlowInstanceLogVO getLog()
    {
        return log;
    }

    /**
     * @param log
     *            the log to set
     */
    public void setLog(FlowInstanceLogVO log)
    {
        this.log = log;
    }
}
