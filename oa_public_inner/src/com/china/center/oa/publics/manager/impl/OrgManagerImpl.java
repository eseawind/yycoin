/**
 * File Name: OrgManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-23<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.OrgConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.InvoiceCreditDAO;
import com.china.center.oa.publics.dao.OrgDAO;
import com.china.center.oa.publics.dao.PrincipalshipDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.StafferVSPriDAO;
import com.china.center.oa.publics.listener.OrgListener;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.publics.vs.OrgBean;
import com.china.center.oa.publics.vs.StafferVSPriBean;
import com.china.center.oa.publics.wrap.StafferOrgWrap;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;


/**
 * OrgManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-6-23
 * @see OrgManagerImpl
 * @since 1.0
 */
@Exceptional
public class OrgManagerImpl extends AbstractListenerManager<OrgListener> implements OrgManager
{
    private OrgDAO orgDAO = null;

    private PrincipalshipDAO principalshipDAO = null;

    private StafferDAO stafferDAO = null;

    private CommonDAO commonDAO = null;

    private InvoiceCreditDAO invoiceCreditDAO = null;

    private StafferVSPriDAO stafferVSPriDAO = null;

    /**
     * default constructor
     */
    public OrgManagerImpl()
    {
    }

    /**
     * 查询下一级的岗位
     * 
     * @param id
     * @return
     * @throws MYException
     */
    public List<PrincipalshipBean> querySubPrincipalship(String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(id);

        PrincipalshipBean bean = principalshipDAO.find(id);

        if (bean == null)
        {
            throw new MYException("岗位不存在");
        }

        List<PrincipalshipBean> result = new ArrayList<PrincipalshipBean>();

        result.add(bean);

        diguiPrin(bean, result);

        return result;
    }

    private void diguiPrin(PrincipalshipBean parentBean, List<PrincipalshipBean> result)
    {
        List<PrincipalshipBean> list = principalshipDAO.querySubPrincipalship(parentBean.getId());

        if (list.isEmpty())
        {
            return;
        }

        result.addAll(list);

        for (PrincipalshipBean principalshipBean : list)
        {
            diguiPrin(principalshipBean, result);
        }
    }

    /**
     * addBeanWithoutTransactional
     */
    public boolean addBeanWithoutTransactional(User user, PrincipalshipBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkAddBean(bean);

        if (StringTools.isNullOrNone(bean.getId()))
        {
            bean.setId(commonDAO.getSquenceString());
        }

        principalshipDAO.saveEntityBean(bean);

        // 修改后永远只有一个了
        List<OrgBean> parents = bean.getParentOrgList();

        for (OrgBean orgBean : parents)
        {
            orgBean.setSubId(bean.getId());

            if (orgDAO.countByUnique(orgBean.getSubId(), orgBean.getParentId()) == 0)
            {
                orgDAO.saveEntityBean(orgBean);
            }
        }

        return true;
    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean addBean(User user, PrincipalshipBean bean)
        throws MYException
    {
        return addBeanWithoutTransactional(user, bean);
    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean updateBean(User user, PrincipalshipBean bean, boolean modfiyParent)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkUpdateBean(bean, modfiyParent);

        principalshipDAO.updateEntityBean(bean);
        
        processOrg(bean);
        
        //add 2012.7.4 -- status:1 停用，该层级下的组织也要停用 - 递归
        digui(bean);
        
        List<PrincipalshipBean> priList = principalshipDAO.queryEntityBeansByFK(bean.getId());
        
        for(PrincipalshipBean prinship : priList){
            
            prinship.setStatus(bean.getStatus());
            
            principalshipDAO.updateEntityBean(prinship);
            
            processOrg(prinship);
        }

        if (modfiyParent)
        {
            orgDAO.deleteEntityBeansByFK(bean.getId(), AnoConstant.FK_FIRST);

            List<OrgBean> parents = bean.getParentOrgList();

            for (OrgBean orgBean : parents)
            {
                orgBean.setSubId(bean.getId());

                if (orgDAO.countByUnique(orgBean.getSubId(), orgBean.getParentId()) == 0)
                {
                    orgDAO.saveEntityBean(orgBean);
                }
            }
        }

        return true;
    }

    /**
     * 递归
     * @param bean
     */
    private void digui(PrincipalshipBean bean) {
        
        List<PrincipalshipBean> priList = principalshipDAO.queryEntityBeansByFK(bean.getId());
        
        for(PrincipalshipBean prinship : priList){
            
            prinship.setStatus(bean.getStatus());
            
            principalshipDAO.updateEntityBean(prinship);
            
            processOrg(prinship);
            
            digui(prinship);
        }
    }

    /**
     * 当组织架构发生状态变化（启用或停用）时，要处理组织映射关系（有点多余）
     * 
     * @param bean
     */
    private void processOrg(PrincipalshipBean bean) {
        
        List<OrgBean> orgList = orgDAO.queryEntityBeansByFK(bean.getId(), AnoConstant.FK_FIRST);
        
        for (OrgBean orgBean : orgList){
            
            orgBean.setStatus(bean.getStatus());
            
            orgDAO.updateEntityBean(orgBean);
        }
               
    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean delBean(User user, String id)
        throws MYException
    {
        return delBeanWithoutTransactional(user, id);
    }

    /**
     * delBeanWithoutTransactional
     */
    public boolean delBeanWithoutTransactional(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        checkDelBean(user, id);

        principalshipDAO.deleteEntityBean(id);

        orgDAO.deleteEntityBeansByFK(id, AnoConstant.FK_FIRST);

        return true;
    }

    /**
     * 递归查询下面所有的职员
     * 
     * @param prinRootId
     * @return
     * @throws MYException
     */
    public List<StafferOrgWrap> queryAllSubStaffer(String prinRootId)
        throws MYException
    {
        List<PrincipalshipBean> plist = this.querySubPrincipalship(prinRootId);

        Set<StafferOrgWrap> wraps = new HashSet<StafferOrgWrap>();

        // 循环组织
        for (PrincipalshipBean principalshipBean : plist)
        {
            // 查询下一级岗位
            List<PrincipalshipBean> vos = principalshipDAO.querySubPrincipalship(principalshipBean
                .getId());

            for (PrincipalshipBean orgBean : vos)
            {
                // 子组织下的人员
                List<StafferBean> pplist = stafferDAO
                    .queryStafferByPrincipalshipId(orgBean.getId());

                if ( !pplist.isEmpty())
                {
                    for (StafferBean stafferBean2 : pplist)
                    {
                        StafferOrgWrap wrap = new StafferOrgWrap();

                        wrap.setStafferId(stafferBean2.getId());

                        wrap.setId(stafferBean2.getId());

                        wrap.setStafferName(stafferBean2.getName());

                        wrap.setName(stafferBean2.getName());

                        wrap.setPrincipalshipId(orgBean.getId());

                        wrap.setPrincipalshipName(orgBean.getName());

                        wraps.add(wrap);
                    }
                }
            }
        }

        List<StafferOrgWrap> list = new ArrayList<StafferOrgWrap>();

        for (StafferOrgWrap stafferOrgWrap : wraps)
        {
            list.add(stafferOrgWrap);
        }

        wraps.clear();

        // sort by name
        Collections.sort(list, new Comparator<StafferOrgWrap>()
        {

            public int compare(StafferOrgWrap o1, StafferOrgWrap o2)
            {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return list;
    }

    private void checkDelBean(User user, String id)
        throws MYException
    {
        PrincipalshipBean bean = principalshipDAO.find(id);

        if (bean == null)
        {
            throw new MYException("组织不存在");
        }

        if (orgDAO.countByFK(id) > 0)
        {
            throw new MYException("组织下存在下级组织结构,不能删除");
        }

        // 组织上没有人员
        if (stafferVSPriDAO.countByFK(id, AnoConstant.FK_FIRST) > 0)
        {
            throw new MYException("组织上有人员挂靠,不能删除");
        }

        if (invoiceCreditDAO.countByFK(id, AnoConstant.FK_FIRST) > 0)
        {
            throw new MYException("组织上有人员挂靠,不能删除");
        }

        if (MathTools.parseInt(id) <= MathTools.parseInt(OrgConstant.CENTER_LOCATION))
        {
            throw new MYException("初始化组织,不能删除");
        }

        Collection<OrgListener> listenerMapValues = this.listenerMapValues();

        for (OrgListener orgListener : listenerMapValues)
        {
            orgListener.onDeleteOrg(user, bean);
        }
    }

    /**
     * checkAddBean(一条线名称不能重复)
     * 
     * @param bean
     * @throws MYException
     */
    private void checkAddBean(PrincipalshipBean bean)
        throws MYException
    {
        List<OrgBean> parents = bean.getParentOrgList();

        if (ListTools.isEmptyOrNull(parents))
        {
            throw new MYException("组织[%s]没有上级主管", bean.getName());
        }

        // 只能是严格的上下级别
        OrgBean parent = parents.get(0);

        PrincipalshipBean parentNode = principalshipDAO.find(parent.getParentId());

        if ( (parentNode.getLevel() + 1) != bean.getLevel())
        {
            throw new MYException("组织[%s]和上级组织的级别只能相差一个级别", bean.getName());
        }

        for (OrgBean orgBean : parents)
        {
            PrincipalshipBean ship = principalshipDAO.find(orgBean.getParentId());

            if (ship == null)
            {
                throw new MYException("上级主管组织不存在");
            }

            if (bean.getLevel() <= ship.getLevel())
            {
                throw new MYException("组织级别不能低于上级");
            }

            if (bean.getName().equalsIgnoreCase(ship.getName()))
            {
                throw new MYException("一条线上的组织名称不能重复,请重新操作");
            }

            checkParentNameDuplicate(ship.getId(), bean.getName());
        }
    }

    /**
     * 递归检查父节点的名称是否重复
     */
    private void checkParentNameDuplicate(String itemId, String name)
        throws MYException
    {
        if (String.valueOf(PublicConstant.ORG_ROOT).equals(itemId))
        {
            return;
        }

        List<OrgBean> parentList = orgDAO.queryEntityBeansByFK(itemId, AnoConstant.FK_FIRST);

        for (OrgBean orgBean : parentList)
        {
            PrincipalshipBean pbean = principalshipDAO.find(orgBean.getParentId());

            if (pbean == null)
            {
                throw new MYException("数据错误,组织不存在,请重新操作");
            }

            if (pbean.getName().equalsIgnoreCase(name))
            {
                throw new MYException("一条线上的组织名称不能重复,请重新操作");
            }

            checkParentNameDuplicate(pbean.getId(), name);
        }
    }

    /**
     * 递归检查子节点的名称是否重复
     */
    private void checkSubNameDuplicate(String itemId, String name)
        throws MYException
    {
        List<OrgBean> subList = orgDAO.queryEntityBeansByFK(itemId);

        if (ListTools.isEmptyOrNull(subList))
        {
            return;
        }

        for (OrgBean orgBean : subList)
        {
            PrincipalshipBean pbean = principalshipDAO.find(orgBean.getSubId());

            if (pbean == null)
            {
                throw new MYException("数据错误,组织不存在,请重新操作");
            }

            if (pbean.getName().equalsIgnoreCase(name))
            {
                throw new MYException("一条线上的组织名称不能重复,请重新操作");
            }

            checkSubNameDuplicate(pbean.getId(), name);
        }
    }

    /**
     * checkUpdateBean
     * 
     * @param bean
     * @throws MYException
     */
    private void checkUpdateBean(PrincipalshipBean bean, boolean modfiyParent)
        throws MYException
    {
        PrincipalshipBean old = principalshipDAO.find(bean.getId());

        List<OrgBean> parents = null;

        if (modfiyParent)
        {
            parents = bean.getParentOrgList();
        }
        else
        {
            parents = orgDAO.queryEntityBeansByFK(bean.getId(), AnoConstant.FK_FIRST);
        }

        if (ListTools.isEmptyOrNull(parents))
        {
            throw new MYException("组织[%s]没有上级主管", bean.getName());
        }

        // 只能是严格的上下级别
        OrgBean parent = parents.get(0);

        PrincipalshipBean parentNode = principalshipDAO.find(parent.getParentId());

        if ( (parentNode.getLevel() + 1) != bean.getLevel())
        {
            throw new MYException("组织[%s]和上级组织的级别只能相差一个级别", bean.getName());
        }

        for (OrgBean orgBean : parents)
        {
            PrincipalshipBean ship = principalshipDAO.find(orgBean.getParentId());

            if (ship == null)
            {
                throw new MYException("上级主管组织不存在");
            }

            if (bean.getLevel() <= ship.getLevel())
            {
                throw new MYException("组织级别不能低于上级");
            }
        }

        if (modfiyParent)
        {
            for (OrgBean orgBean2 : parents)
            {
                PrincipalshipBean pp = principalshipDAO.find(orgBean2.getParentId());

                if (pp == null)
                {
                    throw new MYException("数据错误,组织不存在,请重新操作");
                }

                if (pp.getName().equalsIgnoreCase(bean.getName()))
                {
                    throw new MYException("一条线上的组织名称不能重复,请重新操作");
                }

                checkParentNameDuplicate(orgBean2.getParentId(), bean.getName());
            }
        }

        // 名称重复
        if ( !old.getName().equalsIgnoreCase(bean.getName()))
        {
            if (modfiyParent)
            {
                checkSubNameDuplicate(bean.getId(), bean.getName());
            }
            else
            {
                checkParentNameDuplicate(bean.getId(), bean.getName());

                checkSubNameDuplicate(bean.getId(), bean.getName());
            }
        }

    }

    public PrincipalshipBean findByIdAndSpecialLevel(String id, int level)
    {
        PrincipalshipBean principalship = principalshipDAO.find(id);

        if (principalship == null)
        {
            return null;
        }

        if (principalship.getLevel() == level)
        {
            return principalship;
        }

        if (principalship.getLevel() < level)
        {
            return null;
        }

        // 获取父级
        for (int i = 0; i < 20; i++ )
        {
            PrincipalshipBean parent = principalshipDAO.find(principalship.getParentId());

            if (parent == null)
            {
                return null;
            }

            if (parent.getLevel() == level)
            {
                return parent;
            }

            principalship = parent;
        }

        return null;
    }

    public boolean isSubPrincipalship(String parentId, String currentId)
    {
        if (currentId.equals(parentId))
        {
            return false;
        }

        PrincipalshipBean principalship = principalshipDAO.find(currentId);

        if (principalship == null)
        {
            return false;
        }

        // 获取父级
        for (int i = 0; i < 20; i++ )
        {
            PrincipalshipBean parent = principalshipDAO.find(principalship.getParentId());

            if (parent == null)
            {
                return false;
            }

            if (parent.getId().equals(parentId))
            {
                return true;
            }

            principalship = parent;
        }

        return false;
    }

    public boolean isStafferBelongOrg(String stafferId, String orgId)
    {
        List<StafferVSPriBean> vsList = stafferVSPriDAO.queryEntityBeansByFK(stafferId);

        for (StafferVSPriBean vs : vsList)
        {
            if (orgId.equals(vs.getPrincipalshipId()))
            {
                return true;
            }

            if (isSubPrincipalship(orgId, vs.getPrincipalshipId()))
            {
                return true;
            }
        }

        return false;
    }

    public boolean isOrgBelongStaffer(String stafferId, String orgId)
    {
        List<StafferVSPriBean> vsList = stafferVSPriDAO.queryEntityBeansByFK(stafferId);

        for (StafferVSPriBean vs : vsList)
        {
            if (orgId.equals(vs.getPrincipalshipId()))
            {
                return true;
            }

            if (isSubPrincipalship(vs.getPrincipalshipId(), orgId))
            {
                return true;
            }
        }

        return false;
    }

    public List<PrincipalshipBean> listAllIndustry()
    {
        List<PrincipalshipBean> list = new ArrayList<PrincipalshipBean>();

        // 三级的
        List<PrincipalshipBean> three = principalshipDAO
            .queryEntityBeansByFK(OrgConstant.ORG_BIG_DEPARTMENT);

        for (PrincipalshipBean principalshipBean : three)
        {
            // 四级的
            List<PrincipalshipBean> subList = principalshipDAO
                .queryEntityBeansByFK(principalshipBean.getId());

            for (PrincipalshipBean four : subList)
            {
                four.setParentId(principalshipBean.getName());
                four.setParentName(principalshipBean.getName());
            }

            list.addAll(subList);
        }

        return list;
    }

    public PrincipalshipBean findPrincipalshipById(String id)
    {
        PrincipalshipBean principalship = principalshipDAO.find(id);

        if (principalship == null)
        {
            return null;
        }

        String fullName = principalship.getName();

        if (OrgConstant.ORG_ROOT.equals(principalship.getId()))
        {
            principalship.setFullName(fullName);

            return principalship;
        }

        PrincipalshipBean parent = principalshipDAO.find(principalship.getParentId());

        if (parent != null)
        {
            principalship.setParentName(parent.getName());
        }

        while (parent != null)
        {
            fullName = parent.getName() + "->" + fullName;

            parent = principalshipDAO.find(parent.getParentId());
        }

        if (fullName.startsWith("董事会->"))
        {
            fullName = fullName.substring(5);
        }

        principalship.setFullName(fullName);

        return principalship;
    }

    /**
     * @return the orgDAO
     */
    public OrgDAO getOrgDAO()
    {
        return orgDAO;
    }

    /**
     * @param orgDAO
     *            the orgDAO to set
     */
    public void setOrgDAO(OrgDAO orgDAO)
    {
        this.orgDAO = orgDAO;
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

    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
    }

    /**
     * @return the stafferDAO
     */
    public StafferDAO getStafferDAO()
    {
        return stafferDAO;
    }

    /**
     * @param stafferDAO
     *            the stafferDAO to set
     */
    public void setStafferDAO(StafferDAO stafferDAO)
    {
        this.stafferDAO = stafferDAO;
    }

    /**
     * @return the stafferVSPriDAO
     */
    public StafferVSPriDAO getStafferVSPriDAO()
    {
        return stafferVSPriDAO;
    }

    /**
     * @param stafferVSPriDAO
     *            the stafferVSPriDAO to set
     */
    public void setStafferVSPriDAO(StafferVSPriDAO stafferVSPriDAO)
    {
        this.stafferVSPriDAO = stafferVSPriDAO;
    }

    /**
     * @return the invoiceCreditDAO
     */
    public InvoiceCreditDAO getInvoiceCreditDAO()
    {
        return invoiceCreditDAO;
    }

    /**
     * @param invoiceCreditDAO
     *            the invoiceCreditDAO to set
     */
    public void setInvoiceCreditDAO(InvoiceCreditDAO invoiceCreditDAO)
    {
        this.invoiceCreditDAO = invoiceCreditDAO;
    }
}
