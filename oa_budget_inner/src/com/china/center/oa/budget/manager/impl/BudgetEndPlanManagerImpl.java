/**
 * File Name: BudgetEndPlanManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-5-31<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.manager.impl;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.iaop.annotation.IntegrationAOP;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.budget.bean.BudgetBean;
import com.china.center.oa.budget.bean.BudgetItemBean;
import com.china.center.oa.budget.constant.BudgetConstant;
import com.china.center.oa.budget.dao.BudgetLogDAO;
import com.china.center.oa.budget.dao.impl.BudgetDAOImpl;
import com.china.center.oa.budget.dao.impl.BudgetItemDAOImpl;
import com.china.center.oa.budget.helper.BudgetHelper;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.LogBean;
import com.china.center.oa.publics.bean.PlanBean;
import com.china.center.oa.publics.constant.ModuleConstant;
import com.china.center.oa.publics.constant.OperationConstant;
import com.china.center.oa.publics.dao.LogDAO;
import com.china.center.oa.publics.manager.CarryPlan;
import com.china.center.tools.MathTools;
import com.china.center.tools.TimeTools;


/**
 * 预算自动结束
 * 
 * @author ZHUZHU
 * @version 2009-5-31
 * @see BudgetEndPlanManagerImpl
 * @since 1.0
 */
@IntegrationAOP
public class BudgetEndPlanManagerImpl implements CarryPlan
{
    private final Log _logger = LogFactory.getLog(getClass());

    private BudgetItemDAOImpl budgetItemDAO = null;

    private BudgetDAOImpl budgetDAO = null;

    private LogDAO logDAO = null;

    private BudgetLogDAO budgetLogDAO = null;

    /**
     * default constructor
     */
    public BudgetEndPlanManagerImpl()
    {
    }

    /**
     * end 是否最后一次执行
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean carryPlan(PlanBean plan, boolean end)
    {
        String budgetId = plan.getFkId();

        return corePorcess(end, budgetId);
    }

    /**
     * 单独执行计划(有事务的)
     * 
     * @param budgetId
     * @return
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean carry(String budgetId)
    {
        return corePorcess(false, budgetId);
    }

    /**
     * @param end
     * @param budgetId
     * @return
     */
    private boolean corePorcess(boolean end, String budgetId)
    {
        BudgetBean bean = budgetDAO.find(budgetId);

        if (bean == null)
        {
            _logger.error("预算不存在:" + budgetId);
            return true;
        }

        // 当前合计
        double total = 0.0d;

        // process subItem
        List<BudgetItemBean> items = budgetItemDAO.queryEntityBeansByFK(bean.getId());

        String logLevel = BudgetHelper.getLogLevel(bean);

        // 更新子项的预算使用
        for (BudgetItemBean budgetItemBean : items)
        {
            double itemSubTotal = budgetLogDAO.sumBudgetLogByLevel("budgetItemId" + logLevel,
                budgetItemBean.getId()) / 100.0d;

            total += itemSubTotal;

            budgetItemBean.setUseMonery(itemSubTotal);

            // update budgetItem
            budgetItemDAO.updateEntityBean(budgetItemBean);
        }

        // 结束处理
        if (end)
        {
            // 取消预占的(因为结束了)
            budgetLogDAO.updatePreToZeroByBudgetId(budgetId);

            bean.setStatus(BudgetConstant.BUDGET_STATUS_END);

            // 计算执行结果
            if (MathTools.compare(total, bean.getTotal()) == 0)
            {
                bean.setCarryStatus(BudgetConstant.BUDGET_CARRY_BALANCE);
            }

            if (MathTools.compare(total, bean.getTotal()) > 0)
            {
                bean.setCarryStatus(BudgetConstant.BUDGET_CARRY_OVER);
            }

            if (MathTools.compare(total, bean.getTotal()) < 0)
            {
                bean.setCarryStatus(BudgetConstant.BUDGET_CARRY_LESS);
            }

            // FIX BUG update UseMoney
            budgetItemDAO.updateUseMoneyEqualsRealMoney(budgetId);

            log(Helper.getSystemUser(), budgetId, OperationConstant.OPERATION_END,
                "到达预算截至时间,系统自动结束此预算");

            // get current real monery
            bean.setRealMonery(total);

            // update BudgetBean
            budgetDAO.updateEntityBean(bean);
        }

        return true;
    }

    /**
     * log in db
     * 
     * @param user
     * @param id
     */
    private void log(User user, String id, String operation, String reson)
    {
        // 记录审批日志
        LogBean log = new LogBean();

        log.setFkId(id);

        log.setLocationId(user.getLocationId());
        log.setStafferId(user.getStafferId());
        log.setLogTime(TimeTools.now());
        log.setModule(ModuleConstant.MODULE_BUDGET);
        log.setOperation(operation);
        log.setLog(reson);

        logDAO.saveEntityBean(log);
    }

    public int getPlanType()
    {
        return BudgetConstant.BUGFET_PLAN_TYPE;
    }

    public BudgetItemDAOImpl getBudgetItemDAO()
    {
        return budgetItemDAO;
    }

    public void setBudgetItemDAO(BudgetItemDAOImpl budgetItemDAO)
    {
        this.budgetItemDAO = budgetItemDAO;
    }

    public BudgetDAOImpl getBudgetDAO()
    {
        return budgetDAO;
    }

    public void setBudgetDAO(BudgetDAOImpl budgetDAO)
    {
        this.budgetDAO = budgetDAO;
    }

    public LogDAO getLogDAO()
    {
        return logDAO;
    }

    public void setLogDAO(LogDAO logDAO)
    {
        this.logDAO = logDAO;
    }

    /**
     * @return the budgetLogDAO
     */
    public BudgetLogDAO getBudgetLogDAO()
    {
        return budgetLogDAO;
    }

    /**
     * @param budgetLogDAO
     *            the budgetLogDAO to set
     */
    public void setBudgetLogDAO(BudgetLogDAO budgetLogDAO)
    {
        this.budgetLogDAO = budgetLogDAO;
    }
}
