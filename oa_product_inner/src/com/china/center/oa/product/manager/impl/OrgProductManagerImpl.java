package com.china.center.oa.product.manager.impl;

import java.util.List;

import org.china.center.spring.ex.annotation.Exceptional;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.china.center.oa.product.manager.OrgProductManager;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.constant.OrgConstant;
import com.china.center.oa.publics.dao.PrincipalshipDAO;
import com.china.center.oa.publics.listener.OrgListener;

@Exceptional
public class OrgProductManagerImpl extends AbstractListenerManager<OrgListener> implements OrgProductManager {

    private PrincipalshipDAO principalshipDAO = null;
    
    /**
     * 查询行政管理中心下4级组织 20120503 （代码写死）
     * {@inheritDoc}
     */
    @Override
    public List<PrincipalshipBean> listAllAdminIndustry() {

        // 行政管理中心下4级组织
        List<PrincipalshipBean> list = principalshipDAO
            .queryEntityBeansByFK(12);
     

        return list;
    
    }
    
    /**
     * @return the principalshipDAO
     */
    public PrincipalshipDAO getPrincipalshipDAO()
    {
        return principalshipDAO;
    }

    /**
     * @param principalshipDAO
     *            the principalshipDAO to set
     */
    public void setPrincipalshipDAO(PrincipalshipDAO principalshipDAO)
    {
        this.principalshipDAO = principalshipDAO;
    }
}
