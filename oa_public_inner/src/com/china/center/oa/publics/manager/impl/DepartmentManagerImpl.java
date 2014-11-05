/**
 * File Name: DepartmentManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager.impl;


import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.DepartmentBean;
import com.china.center.oa.publics.dao.DepartmentDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.manager.DepartmentManager;
import com.china.center.tools.JudgeTools;


/**
 * DepartmentManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see DepartmentManagerImpl
 * @since 1.0
 */
@Exceptional
public class DepartmentManagerImpl implements DepartmentManager
{
    private DepartmentDAO departmentDAO = null;

    private StafferDAO stafferDAO = null;

    /**
     * default constructor
     */
    public DepartmentManagerImpl()
    {}

    /**
     * addBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */

    @Transactional(rollbackFor = {MYException.class})
    public boolean addBean(User user, DepartmentBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkAddBean(bean);

        departmentDAO.saveEntityBean(bean);

        return true;
    }

    /**
     * 更新部门
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */

    @Transactional(rollbackFor = {MYException.class})
    public boolean updateBean(User user, DepartmentBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkUpdateBean(bean);

        departmentDAO.updateEntityBean(bean);

        return true;
    }

    /**
     * delBean
     * 
     * @param user
     * @param stafferId
     * @return
     * @throws MYException
     */

    @Transactional(rollbackFor = {MYException.class})
    public boolean delBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        checkDelBean(id);

        departmentDAO.deleteEntityBean(id);

        return true;
    }

    /**
     * @param bean
     * @throws MYException
     */
    private void checkAddBean(DepartmentBean bean)
        throws MYException
    {
        if (departmentDAO.countByUnique(bean.getName()) > 0)
        {
            throw new MYException("部门名称[%s]已经存在", bean.getName());
        }
    }

    private void checkUpdateBean(DepartmentBean bean)
        throws MYException
    {
        DepartmentBean dep = departmentDAO.find(bean.getId());

        if (dep == null)
        {
            throw new MYException("部门[%s]不存在", bean.getName());
        }

        if ( !dep.getName().equals(bean.getName()))
        {
            if (departmentDAO.countByUnique(bean.getName().trim()) > 0)
            {
                throw new MYException("部门名称[%s]已经存在", bean.getName());
            }
        }
    }

    /**
     * @param bean
     * @throws MYException
     */
    private void checkDelBean(String id)
        throws MYException
    {
        // 存在人员或者user
        if (stafferDAO.countByDepartmentId(id) > 0)
        {
            throw new MYException("部门被人员绑定无法删除");
        }
    }

    /**
     * @return the departmentDAO
     */
    public DepartmentDAO getDepartmentDAO()
    {
        return departmentDAO;
    }

    /**
     * @param departmentDAO
     *            the departmentDAO to set
     */
    public void setDepartmentDAO(DepartmentDAO departmentDAO)
    {
        this.departmentDAO = departmentDAO;
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

}
