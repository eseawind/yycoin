/**
 * File Name: TokenHanderBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-4-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.vs;

import java.io.Serializable;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.flow.constant.FlowConstant;

/**
 * TokenHanderBean
 * 
 * @author zhuzhu
 * @version 2009-4-26
 * @see TokenVSHanderBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_VS_TOKEN_PROCESSER")
public class TokenVSHanderBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    private String flowId = "";
    
    @FK
    private String tokenId = "";

    @FK(index = AnoConstant.FK_FIRST)
    private String processer = "";
    
    private int type = FlowConstant.FLOW_PLUGIN_STAFFER;

    /**
     * default constructor
     */
    public TokenVSHanderBean()
    {}

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the flowId
     */
    public String getFlowId()
    {
        return flowId;
    }

    /**
     * @param flowId the flowId to set
     */
    public void setFlowId(String flowId)
    {
        this.flowId = flowId;
    }

    /**
     * @return the processer
     */
    public String getProcesser()
    {
        return processer;
    }

    /**
     * @param processer the processer to set
     */
    public void setProcesser(String processer)
    {
        this.processer = processer;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return the tokenId
     */
    public String getTokenId()
    {
        return tokenId;
    }

    /**
     * @param tokenId the tokenId to set
     */
    public void setTokenId(String tokenId)
    {
        this.tokenId = tokenId;
    }
}
