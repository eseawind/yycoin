package com.china.center.oa.product.manager;

import java.util.List;

import com.center.china.osgi.publics.ListenerManager;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.listener.OrgListener;

public interface OrgProductManager extends ListenerManager<OrgListener> {

    /**
     * 列出行政管理中心下4级组织
     * @return
     */
    List<PrincipalshipBean> listAllAdminIndustry();
}
