/**
 * File Name: OrgManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-23<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;


import java.util.List;

import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.listener.OrgListener;
import com.china.center.oa.publics.wrap.StafferOrgWrap;


/**
 * OrgManager
 * 
 * @author ZHUZHU
 * @version 2010-6-23
 * @see OrgManager
 * @since 1.0
 */
public interface OrgManager extends ListenerManager<OrgListener>
{
    List<PrincipalshipBean> querySubPrincipalship(String id)
        throws MYException;

    /**
     * 列出所有事业部(行业事业部)
     * 
     * @return
     */
    List<PrincipalshipBean> listAllIndustry();

    boolean addBean(User user, PrincipalshipBean bean)
        throws MYException;

    boolean addBeanWithoutTransactional(User user, PrincipalshipBean bean)
        throws MYException;

    boolean updateBean(User user, PrincipalshipBean bean, boolean modfiyParent)
        throws MYException;

    boolean delBean(User user, String id)
        throws MYException;

    boolean delBeanWithoutTransactional(User user, String id)
        throws MYException;

    List<StafferOrgWrap> queryAllSubStaffer(String prinRootId)
        throws MYException;

    /**
     * 查询组织的指定级别的组织
     * 
     * @param id
     * @param level
     * @return
     */
    PrincipalshipBean findByIdAndSpecialLevel(String id, int level);

    /**
     * 获取完成的组织结构
     * 
     * @param id
     * @return
     */
    PrincipalshipBean findPrincipalshipById(String id);

    /**
     * 是否是子组织
     * 
     * @param parentId
     * @param currentId
     * @return
     */
    boolean isSubPrincipalship(String parentId, String currentId);

    /**
     * 人员是否属于组织下
     * 
     * @param stafferId
     * @param orgId
     * @return
     */
    boolean isStafferBelongOrg(String stafferId, String orgId);

    /**
     * 组织是否在人员所属的组织下
     * 
     * @param stafferId
     * @param orgId
     * @return
     */
    boolean isOrgBelongStaffer(String stafferId, String orgId);
}
