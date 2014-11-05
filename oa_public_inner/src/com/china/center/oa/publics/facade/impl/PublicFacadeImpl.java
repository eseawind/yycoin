/**
 * File Name: PublicFacadeImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.facade.impl;


import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.DepartmentBean;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.EnumBean;
import com.china.center.oa.publics.bean.InvoiceBean;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.PostBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.RoleBean;
import com.china.center.oa.publics.bean.ShowBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.bean.UserBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.facade.AbstarctFacade;
import com.china.center.oa.publics.facade.PublicFacade;
import com.china.center.oa.publics.helper.RoleHelper;
import com.china.center.oa.publics.manager.DepartmentManager;
import com.china.center.oa.publics.manager.DutyManager;
import com.china.center.oa.publics.manager.EnumManager;
import com.china.center.oa.publics.manager.InvoiceManager;
import com.china.center.oa.publics.manager.LocationManager;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.publics.manager.PostManager;
import com.china.center.oa.publics.manager.RoleManager;
import com.china.center.oa.publics.manager.ShowManager;
import com.china.center.oa.publics.manager.StafferManager;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.oa.publics.vs.LocationVSCityBean;
import com.china.center.tools.JudgeTools;


/**
 * PublicFacadeImpl
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see PublicFacadeImpl
 * @since 1.0
 */
public class PublicFacadeImpl extends AbstarctFacade implements PublicFacade
{
    private LocationManager locationManager = null;

    private StafferManager stafferManager = null;

    private RoleManager roleManager = null;

    private ShowManager showManager = null;

    private UserManager userManager = null;

    private OrgManager orgManager = null;

    private DepartmentManager departmentManager = null;

    private PostManager postManager = null;

    private DutyManager dutyManager = null;

    private EnumManager enumManager = null;

    private InvoiceManager invoiceManager = null;

    /**
     * default constructor
     */
    public PublicFacadeImpl()
    {
    }

    /**
     * addLocationBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addLocationBean(User user, LocationBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        if (containAuth(user, AuthConstant.LOCATION_OPR))
        {
            return locationManager.addBean(user, bean);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * delLocationBean
     * 
     * @param user
     * @param locationId
     * @return
     * @throws MYException
     */
    public boolean delLocationBean(User user, String locationId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, locationId);

        if (containAuth(user, AuthConstant.LOCATION_OPR))
        {
            return locationManager.delBean(user, locationId);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * addStafferBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addStafferBean(User user, StafferBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        if (containAuth(user, AuthConstant.STAFFER_OPR))
        {
            return stafferManager.addBean(user, bean);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * updateStafferBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateStafferBean(User user, StafferBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        if (containAuth(user, AuthConstant.STAFFER_OPR))
        {
            return stafferManager.updateBean(user, bean);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    public boolean updateCredit(String userId, String id, double credit)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.STAFFER_OPR))
        {
            return stafferManager.updateCredit(user, id, credit);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * updateStafferBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateStafferPwkey(User user, StafferBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        if (RoleHelper.isSuperManager(user))
        {
            return stafferManager.updatePwkey(user, bean);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.publics.facade.PublicFacade#updateStafferLever(com.center.china.osgi.publics.User,
     *      com.china.center.oa.publics.bean.StafferBean)
     */
    public boolean updateStafferLever(User user, StafferBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        if (containAuth(user, AuthConstant.CUSTOMER_LEVER_OPR))
        {
            return stafferManager.updateLever(user, bean);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * delStafferBean
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    public boolean delStafferBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        if (containAuth(user, AuthConstant.STAFFER_OPR))
        {
            return stafferManager.delBean(user, id);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    public boolean dropStafferBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        if (containAuth(user, AuthConstant.STAFFER_OPR))
        {
            return stafferManager.dropBean(user, id);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * addRoleBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addRoleBean(String userId, RoleBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.ROLE_OPR))
        {
            return roleManager.addBean(user, bean);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * updateRoleBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateRoleBean(String userId, RoleBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.ROLE_OPR))
        {
            return roleManager.updateBean(user, bean);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * delRoleBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean delRoleBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.ROLE_OPR))
        {
            return roleManager.delBean(user, id);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * addUserBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addUserBean(String userId, UserBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.USER_OPR))
        {
            return userManager.addBean(user, bean);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * updateUserBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateUserBean(String userId, UserBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.USER_OPR))
        {
            return userManager.updateBean(user, bean);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * delRoleBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean delUserBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.USER_OPR))
        {
            return userManager.delBean(user, id);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * updateUserPassword
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateUserPassword(String userId, String id, String password)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, password);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.USER_OPR))
        {
            return userManager.updatePassword(id, password);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * updateUserStatus
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateUserStatus(String userId, String id, int status)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.USER_OPR))
        {
            return userManager.updateStatus(id, status);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * updateUserLocation
     * 
     * @param userId
     * @param id
     * @param locationId
     * @return
     * @throws MYException
     */
    public boolean updateUserLocation(String userId, String id, String locationId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.USER_OPR))
        {
            return userManager.updateLocation(id, locationId);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * addOrgBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addOrgBean(String userId, PrincipalshipBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.ORG_MANAGER))
        {
            return orgManager.addBean(user, bean);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * updateOrgBean
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateOrgBean(String userId, PrincipalshipBean bean, boolean modfiyParent)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.ORG_MANAGER))
        {
            return orgManager.updateBean(user, bean, modfiyParent);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    public boolean delOrgBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.ORG_MANAGER))
        {
            return orgManager.delBean(user, id);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * addDepartmentBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addDepartmentBean(String userId, DepartmentBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.DEPARTMENT_MANAGER))
        {
            return departmentManager.addBean(user, bean);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * updateDepartmentBean
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateDepartmentBean(String userId, DepartmentBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.DEPARTMENT_MANAGER))
        {
            return departmentManager.updateBean(user, bean);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * delDepartmentBean
     * 
     * @param userId
     * @param id
     * @return
     * @throws MYException
     */
    public boolean delDepartmentBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.DEPARTMENT_MANAGER))
        {
            return departmentManager.delBean(user, id);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * addPostBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addPostBean(String userId, PostBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.POST_MANAGER))
        {
            return postManager.addBean(user, bean);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * updatePostBean
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updatePostBean(String userId, PostBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.POST_MANAGER))
        {
            return postManager.updateBean(user, bean);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * delPostBean
     * 
     * @param userId
     * @param id
     * @return
     * @throws MYException
     */
    public boolean delPostBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.POST_MANAGER))
        {
            return postManager.delBean(user, id);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    public boolean addLocationVSCity(User user, String locationId, List<LocationVSCityBean> list)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, locationId);

        if (containAuth(user, AuthConstant.LOCATION_OPR))
        {
            return locationManager.addLocationVSCity(user, locationId, list);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    public boolean addDutyBean(User user, DutyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        if (containAuth(user, AuthConstant.DUTY_OPR))
        {
            return dutyManager.addBean(user, bean);
        }
        else
        {
            throw new MYException("没有权限");
        }

    }

    public boolean deleteDutyBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        if (containAuth(user, AuthConstant.DUTY_OPR))
        {
            return dutyManager.deleteBean(user, id);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    public boolean updateDutyBean(User user, DutyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        if (containAuth(user, AuthConstant.DUTY_OPR))
        {
            return dutyManager.updateBean(user, bean);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * @return the locationManager
     */
    public LocationManager getLocationManager()
    {
        return locationManager;
    }

    /**
     * @param locationManager
     *            the locationManager to set
     */
    public void setLocationManager(LocationManager locationManager)
    {
        this.locationManager = locationManager;
    }

    /**
     * @return the stafferManager
     */
    public StafferManager getStafferManager()
    {
        return stafferManager;
    }

    /**
     * @param stafferManager
     *            the stafferManager to set
     */
    public void setStafferManager(StafferManager stafferManager)
    {
        this.stafferManager = stafferManager;
    }

    /**
     * @return the roleManager
     */
    public RoleManager getRoleManager()
    {
        return roleManager;
    }

    /**
     * @param roleManager
     *            the roleManager to set
     */
    public void setRoleManager(RoleManager roleManager)
    {
        this.roleManager = roleManager;
    }

    /**
     * @return the userManager
     */
    public UserManager getUserManager()
    {
        return userManager;
    }

    /**
     * @param userManager
     *            the userManager to set
     */
    public void setUserManager(UserManager userManager)
    {
        this.userManager = userManager;
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

    /**
     * @return the departmentManager
     */
    public DepartmentManager getDepartmentManager()
    {
        return departmentManager;
    }

    /**
     * @param departmentManager
     *            the departmentManager to set
     */
    public void setDepartmentManager(DepartmentManager departmentManager)
    {
        this.departmentManager = departmentManager;
    }

    /**
     * @return the postManager
     */
    public PostManager getPostManager()
    {
        return postManager;
    }

    /**
     * @param postManager
     *            the postManager to set
     */
    public void setPostManager(PostManager postManager)
    {
        this.postManager = postManager;
    }

    /**
     * @return the dutyManager
     */
    public DutyManager getDutyManager()
    {
        return dutyManager;
    }

    /**
     * @param dutyManager
     *            the dutyManager to set
     */
    public void setDutyManager(DutyManager dutyManager)
    {
        this.dutyManager = dutyManager;
    }

    public boolean addEnumBean(String userId, EnumBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.ENUM_OPR))
        {
            return enumManager.addBean(user, bean);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    public boolean deleteEnumBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.ENUM_OPR))
        {
            return enumManager.deleteBean(user, id);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    public boolean updateEnumBean(String userId, EnumBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.ENUM_OPR))
        {
            return enumManager.updateBean(user, bean);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    public boolean updateInvoiceBean(String userId, InvoiceBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.INVOICE_OPR))
        {
            return invoiceManager.updateBean(user, bean);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    public boolean addShowBean(String userId, ShowBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.SHOW_OPR))
        {
            return showManager.addBean(user, bean);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    public boolean deleteShowBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.SHOW_OPR))
        {
            return showManager.deleteBean(user, id);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    public boolean updateShowBean(String userId, ShowBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.SHOW_OPR))
        {
            return showManager.updateBean(user, bean);
        }
        else
        {
            throw new MYException("没有权限");
        }
    }

    /**
     * @return the enumManager
     */
    public EnumManager getEnumManager()
    {
        return enumManager;
    }

    /**
     * @param enumManager
     *            the enumManager to set
     */
    public void setEnumManager(EnumManager enumManager)
    {
        this.enumManager = enumManager;
    }

    /**
     * @return the invoiceManager
     */
    public InvoiceManager getInvoiceManager()
    {
        return invoiceManager;
    }

    /**
     * @param invoiceManager
     *            the invoiceManager to set
     */
    public void setInvoiceManager(InvoiceManager invoiceManager)
    {
        this.invoiceManager = invoiceManager;
    }

    /**
     * @return the showManager
     */
    public ShowManager getShowManager()
    {
        return showManager;
    }

    /**
     * @param showManager
     *            the showManager to set
     */
    public void setShowManager(ShowManager showManager)
    {
        this.showManager = showManager;
    }
}
