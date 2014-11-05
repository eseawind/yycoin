/**
 * File Name: TokenVSTemplateVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-5-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.flow.vo;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.flow.vs.TokenVSTemplateBean;

/**
 * TokenVSTemplateVO
 * 
 * @author zhuzhu
 * @version 2009-5-2
 * @see TokenVSTemplateVO
 * @since 1.0
 */
@Entity(inherit = true)
public class TokenVSTemplateVO extends TokenVSTemplateBean
{
    @Relationship(relationField = "templateId")
    private String templateName = "";

    /**
     * default constructor
     */
    public TokenVSTemplateVO()
    {}

    /**
     * @return the templateName
     */
    public String getTemplateName()
    {
        return templateName;
    }

    /**
     * @param templateName the templateName to set
     */
    public void setTemplateName(String templateName)
    {
        this.templateName = templateName;
    }
}
