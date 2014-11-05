/**
 * File Name: PlanManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-5-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager.impl;


import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.publics.bean.PlanBean;
import com.china.center.oa.publics.constant.PlanConstant;
import com.china.center.oa.publics.dao.PlanDAO;
import com.china.center.oa.publics.manager.CarryPlan;
import com.china.center.oa.publics.manager.PlanManager;
import com.china.center.oa.publics.statics.PublicStatic;
import com.china.center.tools.TimeTools;


/**
 * PlanManagerImpl
 * 
 * @author ZHUZHU
 * @version 2011-5-14
 * @see PlanManagerImpl
 * @since 3.0
 */
public class PlanManagerImpl implements PlanManager
{
    private final Log _logger = LogFactory.getLog(getClass());

    /**
     * 触发器的日志
     */
    private final Log planLog = LogFactory.getLog("plan");

    private PlanDAO planDAO = null;

    private PlatformTransactionManager transactionManager = null;

    public PlanManagerImpl()
    {
    }

    /**
     * 计划的执行(每天03点30执行，一天执行一次)
     */
    public void carryPlan()
    {
        planLog.info("begin carryPlan...");

        ConditionParse condotion = new ConditionParse();

        setOnceCondition(condotion);

        List<PlanBean> plans = planDAO.queryEntityBeansByCondition(condotion);

        // 处理结束的计划
        processPlans(plans, true);

        everydayPlan(condotion);

        // 处理每天的计划
        plans = planDAO.queryEntityBeansByCondition(condotion);

        processPlans(plans, false);

        planLog.info("end carryPlan");
    }

    /**
     * @param condotion
     */
    private void setOnceCondition(ConditionParse condotion)
    {
        condotion.addIntCondition("status", "=", PlanConstant.PLAN_STATUS_INIT);

        // FIX bug 这里不能carryType=PLAN_CARRYTYPES_ONCE，因为是结束的，主要时间在carryTime之后就要结束
        // condotion.addIntCondition("carryType", "=", PlanConstant.PLAN_CARRYTYPES_ONCE);

        condotion.addCondition("carryTime", "<=", TimeTools.now());

        // 获得执行顺序
        condotion.addCondition("order by orderIndex");
    }

    /**
     * @param condotion
     */
    private void everydayPlan(ConditionParse condotion)
    {
        condotion.clear();

        condotion.addIntCondition("carryType", "=", PlanConstant.PLAN_CARRYTYPES_EVERYDAY);

        condotion.addIntCondition("status", "=", PlanConstant.PLAN_STATUS_INIT);

        condotion.addCondition("beginTime", "<=", TimeTools.now());

        condotion.addCondition("carryTime", ">", TimeTools.now());

        condotion.addCondition("order by orderIndex");
    }

    /**
     * @param plans
     *            计划list
     * @param end
     *            是否是最后一次执行
     */
    private void processPlans(List<PlanBean> plans, boolean end)
    {
        for (PlanBean planBean : plans)
        {
            for (CarryPlan item : PublicStatic.getCarryPlanList())
            {
                if (planBean.getType() == item.getPlanType())
                {
                    // 全面保护
                    try
                    {
                        // 如果计划执行成功保存计划且是最后一次执行，更改计划状态
                        if (item.carryPlan(planBean, end) && end)
                        {
                            savePlan(planBean);
                        }

                        planLog.info("processPlan success:" + planBean.getFkId() + ";end is " + end
                                     + ";beginTime:" + planBean.getBeginTime() + ";carryTime:"
                                     + planBean.getCarryTime() + ";" + item);
                    }
                    catch (Throwable e)
                    {
                        planLog.error("processPlan error:" + planBean.getFkId() + ";end is " + end
                                      + ";beginTime:" + planBean.getBeginTime() + ";carryTime:"
                                      + planBean.getCarryTime() + ";" + item);

                        _logger.error(e, e);
                    }

                    break;
                }
            }
        }
    }

    /**
     * 事务内保存计划
     * 
     * @param planBean
     */
    private void savePlan(final PlanBean planBean)
    {
        TransactionTemplate tran = new TransactionTemplate(transactionManager);

        planBean.setStatus(PlanConstant.PLAN_STATUS_END);

        planBean.setRealCarryTime(TimeTools.now());

        tran.execute(new TransactionCallback()
        {
            public Object doInTransaction(TransactionStatus arg0)
            {
                planDAO.updateEntityBean(planBean);

                return Boolean.TRUE;
            }
        });
    }

    /**
     * @return the planDAO
     */
    public PlanDAO getPlanDAO()
    {
        return planDAO;
    }

    /**
     * @param planDAO
     *            the planDAO to set
     */
    public void setPlanDAO(PlanDAO planDAO)
    {
        this.planDAO = planDAO;
    }

    /**
     * 增加执行策略
     * 
     * @param plan
     */
    public boolean addCarryPlan(CarryPlan plan)
    {
        synchronized (PlanManagerImpl.class)
        {
            // 保护
            for (CarryPlan item : PublicStatic.getCarryPlanList())
            {
                if (item.getPlanType() == plan.getPlanType())
                {
                    return false;
                }
            }

            PublicStatic.getCarryPlanList().add(plan);

            return true;
        }
    }

    public boolean removeCarryPlan(int type)
    {
        synchronized (PlanManagerImpl.class)
        {
            for (Iterator iterator = PublicStatic.getCarryPlanList().iterator(); iterator.hasNext();)
            {
                CarryPlan plan = (CarryPlan)iterator.next();

                if (plan.getPlanType() == type)
                {
                    iterator.remove();
                }
            }

            return true;
        }
    }

    /**
     * @return the transactionManager
     */
    public PlatformTransactionManager getTransactionManager()
    {
        return transactionManager;
    }

    /**
     * @param transactionManager
     *            the transactionManager to set
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager)
    {
        this.transactionManager = transactionManager;
    }

}
