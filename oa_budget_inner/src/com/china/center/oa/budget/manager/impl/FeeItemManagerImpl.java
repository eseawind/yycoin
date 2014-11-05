/**
 * File Name: FeeItemManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-5-24<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.manager.impl;


import java.io.Serializable;

import org.china.center.spring.iaop.annotation.IntegrationAOP;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.oa.budget.bean.FeeItemBean;
import com.china.center.oa.budget.constant.BudgetConstant;
import com.china.center.oa.budget.dao.BudgetItemDAO;
import com.china.center.oa.budget.dao.FeeItemDAO;
import com.china.center.oa.budget.manager.FeeItemManager;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.tools.JudgeTools;


/**
 * FeeItemManager
 * 
 * @author ZHUZHU
 * @version 2009-5-24
 * @see FeeItemManagerImpl
 * @since 1.0
 */
@IntegrationAOP
public class FeeItemManagerImpl implements FeeItemManager
{
    private FeeItemDAO feeItemDAO = null;

    private BudgetItemDAO budgetItemDAO = null;

    private CommonDAO commonDAO = null;

    /**
     * default constructor
     */
    public FeeItemManagerImpl()
    {
    }

    /**
     * add fee item
     * 
     * @param user
     * @param bean
     * @return true
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean addBean(User user, FeeItemBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkAddBean(bean);

        bean.setId(commonDAO.getSquenceString20());

        feeItemDAO.saveEntityBean(bean);

        return true;
    }

    /**
     * @param bean
     * @throws MYException
     */
    private void checkAddBean(FeeItemBean bean)
        throws MYException
    {
        if (feeItemDAO.countByUnique(bean.getName().trim()) > 0)
        {
            throw new MYException("预算项已经存在");
        }
    }

    /**
     * updateBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateBean(User user, FeeItemBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkUpdateBean(bean);

        feeItemDAO.updateEntityBean(bean);

        return true;
    }

    /**
     * checkUpdateBean
     * 
     * @param bean
     * @throws MYException
     */
    private void checkUpdateBean(FeeItemBean bean)
        throws MYException
    {
        FeeItemBean old = feeItemDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("预算项不存在");
        }

        if ( !old.getName().equalsIgnoreCase(bean.getName()))
        {
            if (feeItemDAO.countByUnique(bean.getName().trim()) > 0)
            {
                throw new MYException("预算项已经存在");
            }
        }
    }

    /**
     * deleteBean
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean deleteBean(User user, Serializable id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        checkDeleteBean(user, id);

        feeItemDAO.deleteEntityBean(id);

        return true;
    }

    /**
     * checkDeleteBean
     * 
     * @param id
     * @throws MYException
     */
    private void checkDeleteBean(User user, Serializable id)
        throws MYException
    {
        if (BudgetConstant.FEE_ITEM_TRAVELLING.equals(id))
        {
            throw new MYException("初始化的差旅费不能删除");
        }

        // ref other
        int countByFK = budgetItemDAO.countByFK(id, AnoConstant.FK_FIRST);

        if (countByFK > 0)
        {
            throw new MYException("预算项已经被使用");
        }
    }

    /**
     * @return the feeItemDAO
     */
    public FeeItemDAO getFeeItemDAO()
    {
        return feeItemDAO;
    }

    /**
     * @param feeItemDAO
     *            the feeItemDAO to set
     */
    public void setFeeItemDAO(FeeItemDAO feeItemDAO)
    {
        this.feeItemDAO = feeItemDAO;
    }

    /**
     * @return the budgetItemDAO
     */
    public BudgetItemDAO getBudgetItemDAO()
    {
        return budgetItemDAO;
    }

    /**
     * @param budgetItemDAO
     *            the budgetItemDAO to set
     */
    public void setBudgetItemDAO(BudgetItemDAO budgetItemDAO)
    {
        this.budgetItemDAO = budgetItemDAO;
    }

    /**
     * @return the commonDAO
     */
    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    /**
     * @param commonDAO
     *            the commonDAO to set
     */
    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
    }

}
