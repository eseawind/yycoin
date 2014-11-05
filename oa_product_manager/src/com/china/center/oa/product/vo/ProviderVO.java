/**
 * File Name: ProviderVO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-1-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.vo;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.oa.product.bean.ProviderBean;


/**
 * ProviderVO
 * 
 * @author ZHUZHU
 * @version 2010-1-3
 * @see ProviderVO
 * @since 1.0
 */
@Entity(inherit = true)
public class ProviderVO extends ProviderBean
{
    @Ignore
    private String loginName = "";

    @Ignore
    private String typeName = "";

    /**
     * default constructor
     */
    public ProviderVO()
    {
    }

    /**
     * @return the loginName
     */
    public String getLoginName()
    {
        return loginName;
    }

    /**
     * @param loginName
     *            the loginName to set
     */
    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }

    /**
     * @return the typeName
     */
    public String getTypeName()
    {
        return typeName;
    }

    /**
     * @param typeName
     *            the typeName to set
     */
    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }
}
