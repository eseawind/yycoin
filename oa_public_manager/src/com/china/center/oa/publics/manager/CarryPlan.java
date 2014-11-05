/**
 * File Name: CarryPlanManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-5-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;

import com.china.center.oa.publics.bean.PlanBean;

/**
 * CarryPlanManager
 * 
 * @author ZHUZHU
 * @version 2011-5-14
 * @see CarryPlan
 * @since 3.0
 */
public interface CarryPlan
{
    /**
     * 执行计划
     * 
     * @param plan
     *            计划
     * @param end
     *            是否是最后一次执行
     * @return
     */
    boolean carryPlan(PlanBean plan, boolean end);

    /**
     * 获得计划类型
     * 
     * @return
     */
    int getPlanType();
}
