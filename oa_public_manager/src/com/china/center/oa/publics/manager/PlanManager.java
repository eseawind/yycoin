/**
 * File Name: PlanManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-5-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;

/**
 * PlanManager
 * 
 * @author ZHUZHU
 * @version 2011-5-14
 * @see PlanManager
 * @since 3.0
 */
public interface PlanManager
{
    void carryPlan();

    /**
     * 增加执行计划
     * 
     * @param plan
     * @return
     */
    boolean addCarryPlan(CarryPlan plan);

    boolean removeCarryPlan(int type);
}
