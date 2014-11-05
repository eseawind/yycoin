/**
 * File Name: DefaultLoadQueryListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-23<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;


import java.util.List;


/**
 * LoadCarryPlan
 * 
 * @author ZHUZHU
 * @version 2010-8-23
 * @see LoadCarryPlan
 * @since 1.0
 */
public class LoadCarryPlan
{
    private PlanManager planManager = null;

    private List<CarryPlan> planList = null;

    /**
     * default constructor
     */
    public LoadCarryPlan()
    {
    }

    public void init()
    {
        for (CarryPlan each : planList)
        {
            planManager.addCarryPlan(each);
        }
    }

    public void destroy()
    {
        for (CarryPlan each : planList)
        {
            planManager.removeCarryPlan(each.getPlanType());
        }
    }

    /**
     * @return the planManager
     */
    public PlanManager getPlanManager()
    {
        return planManager;
    }

    /**
     * @param planManager
     *            the planManager to set
     */
    public void setPlanManager(PlanManager planManager)
    {
        this.planManager = planManager;
    }

    /**
     * @return the planList
     */
    public List<CarryPlan> getPlanList()
    {
        return planList;
    }

    /**
     * @param planList
     *            the planList to set
     */
    public void setPlanList(List<CarryPlan> planList)
    {
        this.planList = planList;
    }
}
