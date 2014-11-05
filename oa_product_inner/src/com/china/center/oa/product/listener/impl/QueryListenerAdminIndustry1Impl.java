package com.china.center.oa.product.listener.impl;

import java.util.List;

import com.china.center.oa.product.manager.OrgProductManager;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.listener.QueryListener;

public class QueryListenerAdminIndustry1Impl implements QueryListener  {
   
    private OrgProductManager orgProductManager = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.QueryListener#getBeanList()
     */
    public List<?> getBeanList()
    {
        List<PrincipalshipBean> allAdminIndustry = orgProductManager.listAllAdminIndustry();
       
        return allAdminIndustry;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.listener.QueryListener#getKey()
     */
    public String getKey()
    {
        return "$adminindustryList";
    }

    /**
     * @return the orgManager
     */
    public OrgProductManager getOrgProductManager()
    {
        return orgProductManager;
    }

    /**
     * @param orgManager
     *            the orgManager to set
     */
    public void setOrgProductManager(OrgProductManager orgProductManager)
    {
        this.orgProductManager = orgProductManager;
    }


}
