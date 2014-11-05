/**
 * File Name: BudgetFacadeImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-5-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.facade.impl;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.budget.bean.BudgetApplyBean;
import com.china.center.oa.budget.bean.BudgetBean;
import com.china.center.oa.budget.bean.BudgetItemBean;
import com.china.center.oa.budget.bean.FeeItemBean;
import com.china.center.oa.budget.constant.BudgetConstant;
import com.china.center.oa.budget.dao.BudgetApplyDAO;
import com.china.center.oa.budget.dao.BudgetDAO;
import com.china.center.oa.budget.facade.BudgetFacade;
import com.china.center.oa.budget.manager.BudgetApplyManager;
import com.china.center.oa.budget.manager.BudgetManager;
import com.china.center.oa.budget.manager.FeeItemManager;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.facade.AbstarctFacade;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.tools.JudgeTools;


/**
 * BudgetFacadeImpl
 * 
 * @author ZHUZHU
 * @version 2011-5-14
 * @see BudgetFacadeImpl
 * @since 3.0
 */
public class BudgetFacadeImpl extends AbstarctFacade implements BudgetFacade
{
    private BudgetManager budgetManager = null;

    private BudgetApplyManager budgetApplyManager = null;

    private UserManager userManager = null;

    private BudgetApplyDAO budgetApplyDAO = null;

    private BudgetDAO budgetDAO = null;

    private FeeItemManager feeItemManager = null;

    /**
     * default constructor
     */
    public BudgetFacadeImpl()
    {
    }

    /**
     * applyAddCustomer
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addBudget(String userId, BudgetBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        String auth = AuthConstant.BUDGET_LOCATION_OPR;

        if (bean.getType() == BudgetConstant.BUDGET_TYPE_COMPANY)
        {
            auth = AuthConstant.BUDGET_ROOT_OPR;
        }

        if (bean.getType() == BudgetConstant.BUDGET_TYPE_LOCATION)
        {
            auth = AuthConstant.BUDGET_LOCATION_OPR;
        }

        if (bean.getType() == BudgetConstant.BUDGET_TYPE_DEPARTMENT)
        {
            auth = AuthConstant.BUDGET_DEPARTMENT_OPR;
        }

        if (containAuth(user, auth))
        {
            return budgetManager.addBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * delApplyCustomer
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean delBudget(String userId, String cid)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, cid);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.BUDGET_FORCE_DELETE))
        {
            return budgetManager.delBean(user, cid, true);
        }
        else if (containAuth(user, AuthConstant.BUDGET_LOCATION_OPR)
                 || containAuth(user, AuthConstant.BUDGET_ROOT_OPR))
        {
            return budgetManager.delBean(user, cid, false);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * updateBudget
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateBudget(String userId, BudgetBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        String auth = AuthConstant.BUDGET_LOCATION_OPR;

        if (bean.getType() == BudgetConstant.BUDGET_TYPE_COMPANY)
        {
            auth = AuthConstant.BUDGET_ROOT_OPR;
        }

        if (bean.getType() == BudgetConstant.BUDGET_TYPE_LOCATION)
        {
            auth = AuthConstant.BUDGET_LOCATION_OPR;
        }

        if (bean.getType() == BudgetConstant.BUDGET_TYPE_DEPARTMENT)
        {
            auth = AuthConstant.BUDGET_DEPARTMENT_OPR;
        }

        if (containAuth(user, auth))
        {
            return budgetManager.updateBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * updateBudgetItem
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateBudgetItem(String userId, BudgetItemBean bean, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.BUDGET_LOCATION_OPR))
        {
            return budgetManager.updateItemBean(user, bean, reason);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * delBudgetItem
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean delBudgetItem(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.BUDGET_LOCATION_OPR))
        {
            return budgetManager.delItemBean(user, id);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * rejectApplyCustomer
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean rejectBudget(String userId, String cid, String reson)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, cid);

        User user = userManager.findUser(userId);

        checkUser(user);

        String auth = AuthConstant.BUDGET_LOCATION_CHECK;

        if (isRootBudget(cid))
        {
            auth = AuthConstant.BUDGET_ROOT_CHECK;
        }

        if (isLocationBudget(cid))
        {
            auth = AuthConstant.BUDGET_LOCATION_CHECK;
        }

        if (isDepartmentBudget(cid))
        {
            auth = AuthConstant.BUDGET_DEPARTMENT_CHECK;
        }

        if (containAuth(user, auth))
        {
            return budgetManager.rejectBean(user, cid, reson);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * passApplyCustomer
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean passBudget(String userId, String cid)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, cid);

        User user = userManager.findUser(userId);

        checkUser(user);

        String auth = AuthConstant.BUDGET_LOCATION_CHECK;

        if (isRootBudget(cid))
        {
            auth = AuthConstant.BUDGET_ROOT_CHECK;
        }

        if (isLocationBudget(cid))
        {
            auth = AuthConstant.BUDGET_LOCATION_CHECK;
        }

        if (isDepartmentBudget(cid))
        {
            auth = AuthConstant.BUDGET_DEPARTMENT_CHECK;
        }

        if (containAuth(user, auth))
        {
            return budgetManager.passBean(user, cid);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * passBudgetApply
     * 
     * @param userId
     * @param cid
     * @return
     * @throws MYException
     */
    public boolean passBudgetApply(String userId, String mode, String cid)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, cid);

        User user = userManager.findUser(userId);

        checkUser(user);

//        String auth = whetherAuth(user, mode, cid);

//        if (containAllAuth(user, auth, AuthConstant.BUDGET_CHANGE))
//        {
            return budgetApplyManager.passBean(user, cid);
//        }
//        else
//        {
//            throw noAuth();
//        }
    }

    /**
     * rejectBudgetApply
     * 
     * @param userId
     * @param cid
     * @param reson
     * @return
     * @throws MYException
     */
    public boolean rejectBudgetApply(String userId, String mode, String cid, String reson)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, cid);

        User user = userManager.findUser(userId);

        checkUser(user);

        String auth = whetherAuth(user, mode, cid);

        if (containAllAuth(user, auth, AuthConstant.BUDGET_CHANGE))
        {
            return budgetApplyManager.rejectBean(user, cid, reson);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * whetherAuth
     * 
     * @param user
     * @param mode
     * @return
     * @throws MYException
     */
    private String whetherAuth(User user, String mode, String cid)
        throws MYException
    {
        BudgetApplyBean apply = budgetApplyDAO.find(cid);

        if (apply == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        BudgetBean budget = budgetDAO.find(apply.getBudgetId());

        if (budget == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        String auth = AuthConstant.BUDGET_LOCATION_OPR;

        if (budget.getType() == BudgetConstant.BUDGET_TYPE_COMPANY)
        {
            auth = AuthConstant.BUDGET_ROOT_OPR;
        }

        if (budget.getType() == BudgetConstant.BUDGET_TYPE_LOCATION)
        {
            auth = AuthConstant.BUDGET_LOCATION_OPR;
        }

        if (budget.getType() == BudgetConstant.BUDGET_TYPE_DEPARTMENT)
        {
            auth = AuthConstant.BUDGET_DEPARTMENT_OPR;
        }

        return auth;
    }

    /**
     * addFeeItem
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addFeeItem(String userId, FeeItemBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.BUDGET_LOCATION_OPR))
        {
            return feeItemManager.addBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * updateFeeItem
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateFeeItem(String userId, FeeItemBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.BUDGET_LOCATION_OPR))
        {
            return feeItemManager.updateBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * deleteFeeItem
     * 
     * @param userId
     * @param id
     * @return
     * @throws MYException
     */
    public boolean deleteFeeItem(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.BUDGET_LOCATION_OPR))
        {
            return feeItemManager.deleteBean(user, id);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * addBudgetApply
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addBudgetApply(String userId, BudgetApplyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        BudgetBean budget = budgetDAO.find(bean.getBudgetId());

        if (budget == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        String auth = AuthConstant.BUDGET_LOCATION_OPR;

        if (budget.getType() == BudgetConstant.BUDGET_TYPE_COMPANY)
        {
            auth = AuthConstant.BUDGET_ROOT_OPR;
        }

        if (budget.getType() == BudgetConstant.BUDGET_TYPE_LOCATION)
        {
            auth = AuthConstant.BUDGET_LOCATION_OPR;
        }

        if (budget.getType() == BudgetConstant.BUDGET_TYPE_DEPARTMENT)
        {
            auth = AuthConstant.BUDGET_DEPARTMENT_OPR;
        }

        if (containAllAuth(user, auth, AuthConstant.BUDGET_CHANGE))
        {
            return budgetApplyManager.addBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    private boolean isRootBudget(String id)
        throws MYException
    {
        BudgetBean bean = this.budgetManager.findBudget(id);

        if (bean == null)
        {
            throw new MYException("预算不存在");
        }

        return bean.getType() == BudgetConstant.BUDGET_TYPE_COMPANY;
    }

    private boolean isLocationBudget(String id)
        throws MYException
    {
        BudgetBean bean = this.budgetManager.findBudget(id);

        if (bean == null)
        {
            throw new MYException("预算不存在");
        }

        return bean.getType() == BudgetConstant.BUDGET_TYPE_LOCATION;
    }

    private boolean isDepartmentBudget(String id)
        throws MYException
    {
        BudgetBean bean = this.budgetManager.findBudget(id);

        if (bean == null)
        {
            throw new MYException("预算不存在");
        }

        return bean.getType() == BudgetConstant.BUDGET_TYPE_DEPARTMENT;
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
     * @return the budgetManager
     */
    public BudgetManager getBudgetManager()
    {
        return budgetManager;
    }

    /**
     * @param budgetManager
     *            the budgetManager to set
     */
    public void setBudgetManager(BudgetManager budgetManager)
    {
        this.budgetManager = budgetManager;
    }

    /**
     * @return the feeItemManager
     */
    public FeeItemManager getFeeItemManager()
    {
        return feeItemManager;
    }

    /**
     * @param feeItemManager
     *            the feeItemManager to set
     */
    public void setFeeItemManager(FeeItemManager feeItemManager)
    {
        this.feeItemManager = feeItemManager;
    }

    /**
     * @return the budgetApplyManager
     */
    public BudgetApplyManager getBudgetApplyManager()
    {
        return budgetApplyManager;
    }

    /**
     * @param budgetApplyManager
     *            the budgetApplyManager to set
     */
    public void setBudgetApplyManager(BudgetApplyManager budgetApplyManager)
    {
        this.budgetApplyManager = budgetApplyManager;
    }

    /**
     * @return the budgetApplyDAO
     */
    public BudgetApplyDAO getBudgetApplyDAO()
    {
        return budgetApplyDAO;
    }

    /**
     * @param budgetApplyDAO
     *            the budgetApplyDAO to set
     */
    public void setBudgetApplyDAO(BudgetApplyDAO budgetApplyDAO)
    {
        this.budgetApplyDAO = budgetApplyDAO;
    }

    /**
     * @return the budgetDAO
     */
    public BudgetDAO getBudgetDAO()
    {
        return budgetDAO;
    }

    /**
     * @param budgetDAO
     *            the budgetDAO to set
     */
    public void setBudgetDAO(BudgetDAO budgetDAO)
    {
        this.budgetDAO = budgetDAO;
    }
}
