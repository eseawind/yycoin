/**
 * File Name: TaxFacadeImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-31<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.facade.impl;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.facade.AbstarctFacade;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.facade.TaxFacade;
import com.china.center.oa.tax.manager.FinanceManager;
import com.china.center.oa.tax.manager.TaxManager;
import com.china.center.tools.JudgeTools;


/**
 * TaxFacadeImpl
 * 
 * @author ZHUZHU
 * @version 2011-1-31
 * @see TaxFacadeImpl
 * @since 1.0
 */
public class TaxFacadeImpl extends AbstarctFacade implements TaxFacade
{
    private TaxManager taxManager = null;

    private FinanceManager financeManager = null;

    private UserManager userManager = null;

    /**
     * default constructor
     */
    public TaxFacadeImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.tax.facade.TaxFacade#addTaxBean(java.lang.String, com.china.center.oa.tax.bean.TaxBean)
     */
    public boolean addTaxBean(String userId, TaxBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.TAX_OPR))
        {
            return taxManager.addTaxBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.tax.facade.TaxFacade#deleteTaxBean(java.lang.String, java.lang.String)
     */
    public boolean deleteTaxBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.TAX_OPR))
        {
            return taxManager.deleteTaxBean(user, id);
        }
        else
        {
            throw noAuth();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.tax.facade.TaxFacade#updateTaxBean(java.lang.String,
     *      com.china.center.oa.tax.bean.TaxBean)
     */
    public boolean updateTaxBean(String userId, TaxBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.TAX_OPR))
        {
            return taxManager.updateTaxBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    public boolean addFinanceBean(String userId, FinanceBean bean)
        throws MYException
    {
        synchronized (FINANCE_LOCK)
        {
            JudgeTools.judgeParameterIsNull(userId, bean);

            User user = userManager.findUser(userId);

            checkUser(user);

            if (containAuth(user, AuthConstant.FINANCE_OPR))
            {
                return financeManager.addFinanceBean(user, bean);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean updateFinanceBean(String userId, FinanceBean bean)
        throws MYException
    {
        synchronized (FINANCE_LOCK)
        {
            JudgeTools.judgeParameterIsNull(userId, bean);

            User user = userManager.findUser(userId);

            checkUser(user);

            if (containAuth(user, AuthConstant.FINANCE_OPR))
            {
                return financeManager.updateFinanceBean(user, bean);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean deleteFinanceBean(String userId, String id)
        throws MYException
    {
        synchronized (FINANCE_LOCK)
        {
            JudgeTools.judgeParameterIsNull(userId, id);

            User user = userManager.findUser(userId);

            checkUser(user);

            if (containAuth(user, AuthConstant.FINANCE_DELETE))
            {
                return financeManager.deleteFinanceBean(user, id);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean checks(String userId, String id, String reason)
        throws MYException
    {
        synchronized (CHECK_LOCK)
        {
            JudgeTools.judgeParameterIsNull(userId, id);

            User user = userManager.findUser(userId);

            checkUser(user);

            if (containAuth(user, AuthConstant.FINANCE_CHECK))
            {
                return financeManager.checks(user, id, reason);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean checks2(String userId, String id, int type, String reason)
        throws MYException
    {
        synchronized (CHECK_LOCK)
        {
            JudgeTools.judgeParameterIsNull(userId, id);

            User user = userManager.findUser(userId);

            checkUser(user);

            if (containAuth(user, AuthConstant.FINANCE_CHECK))
            {
                return financeManager.checks2(user, id, type, reason);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean updateFinanceCheck(String userId, String id, String reason)
        throws MYException
    {
        synchronized (CHECK_LOCK)
        {
            JudgeTools.judgeParameterIsNull(userId, id);

            User user = userManager.findUser(userId);

            checkUser(user);

            if (containAuth(user, AuthConstant.FINANCE_CHECK))
            {
                return financeManager.updateFinanceCheck(user, id, reason);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    /**
     * @return the taxManager
     */
    public TaxManager getTaxManager()
    {
        return taxManager;
    }

    /**
     * @param taxManager
     *            the taxManager to set
     */
    public void setTaxManager(TaxManager taxManager)
    {
        this.taxManager = taxManager;
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
     * @return the financeManager
     */
    public FinanceManager getFinanceManager()
    {
        return financeManager;
    }

    /**
     * @param financeManager
     *            the financeManager to set
     */
    public void setFinanceManager(FinanceManager financeManager)
    {
        this.financeManager = financeManager;
    }

}
