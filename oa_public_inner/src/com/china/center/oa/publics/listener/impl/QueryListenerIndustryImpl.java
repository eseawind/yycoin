/**
 * File Name: QueryListenerOrgImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2012-2-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.listener.impl;


import java.util.List;

import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.listener.QueryListener;
import com.china.center.oa.publics.manager.OrgManager;


/**
 * QueryListenerOrgImpl
 * 
 * @author ZHUZHU
 * @version 2012-2-26
 * @see QueryListenerIndustryImpl
 * @since 1.0
 */
public class QueryListenerIndustryImpl implements QueryListener
{
    private OrgManager orgManager = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.QueryListener#getBeanList()
     */
    public List<?> getBeanList()
    {
        List<PrincipalshipBean> allIndustry = orgManager.listAllIndustry();

        for (PrincipalshipBean principalshipBean : allIndustry)
        {
            principalshipBean.setName(principalshipBean.getParentName() + "-->" + principalshipBean.getName());

            principalshipBean.setFullName(principalshipBean.getName());
        }

        return allIndustry;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.QueryListener#getKey()
     */
    public String getKey()
    {
        return "$industryList";
    }

    /**
     * @return the orgManager
     */
    public OrgManager getOrgManager()
    {
        return orgManager;
    }

    /**
     * @param orgManager
     *            the orgManager to set
     */
    public void setOrgManager(OrgManager orgManager)
    {
        this.orgManager = orgManager;
    }

}
