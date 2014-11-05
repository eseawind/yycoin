/**
 * File Name: BudgetHelper.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-5-30<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.helper;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.china.center.common.MYException;
import com.china.center.oa.budget.bean.BudgetApplyBean;
import com.china.center.oa.budget.bean.BudgetBean;
import com.china.center.oa.budget.bean.BudgetItemBean;
import com.china.center.oa.budget.constant.BudgetConstant;
import com.china.center.oa.budget.vo.BudgetItemVO;
import com.china.center.oa.budget.vo.BudgetLogVO;
import com.china.center.oa.budget.vo.BudgetVO;
import com.china.center.tools.MathTools;


/**
 * BudgetHelper
 * 
 * @author ZHUZHU
 * @version 2009-5-30
 * @see BudgetHelper
 * @since 1.0
 */
public abstract class BudgetHelper
{
    private static Map<Integer, Integer> budgetNextMap = new HashMap<Integer, Integer>();

    static
    {
        budgetNextMap.put(BudgetConstant.BUDGET_LEVEL_YEAR, BudgetConstant.BUDGET_LEVEL_MONTH);

        budgetNextMap.put(BudgetConstant.BUDGET_LEVEL_MONTH, 9999);

        // 月度预算变更只要财务总监核准 年度变更需要总经理 公司年度变更需要董事长
    }

    /**
     * 或者下一个apply的状态
     * 
     * @param budget
     * @param status
     * @return
     */
    public static int getNextApplyStatus(BudgetBean budget, BudgetApplyBean apply)
    {
        return BudgetConstant.BUDGET_APPLY_STATUS_END;
    }

    /**
     * getNextType
     * 
     * @param type
     * @return
     */
    public static int getNextType(int type)
    {
        return budgetNextMap.get(type);
    }

    public static void formatBudgetVO(BudgetVO vo)
    {
        vo.setStotal( (MathTools.formatNum2(vo.getTotal())));

        vo.setSrealMonery( (MathTools.formatNum2(vo.getRealMonery())));
    }

    /**
     * format
     * 
     * @param budgetItemVO
     */
    public static void formatBudgetItem(BudgetItemVO budgetItemVO)
    {
        budgetItemVO.setSbudget(MathTools.formatNum2(budgetItemVO.getBudget()));

        budgetItemVO.setBudgetStr(MathTools.formatNum(budgetItemVO.getBudget()));

        budgetItemVO.setSrealMonery(MathTools.formatNum2(budgetItemVO.getRealMonery()));

        budgetItemVO.setSuseMonery(MathTools.formatNum2(budgetItemVO.getUseMonery()));

        budgetItemVO.setSremainMonery(MathTools.formatNum2(budgetItemVO.getBudget()
                                                           - budgetItemVO.getRealMonery()));
    }

    /**
     * format log
     * 
     * @param log
     */
    public static void formatBudgetLog(BudgetLogVO log)
    {
        log.setSmonery(MathTools.formatNum(log.getMonery() / 100.0d));
    }

    /**
     * 检查使用状态
     * 
     * @param budget
     * @return
     * @throws MYException
     */
    public static boolean checkBudgetCanUse(BudgetBean budget)
        throws MYException
    {
        if (budget.getType() != BudgetConstant.BUDGET_TYPE_DEPARTMENT
            || budget.getLevel() != BudgetConstant.BUDGET_LEVEL_MONTH)
        {
            throw new MYException("不是部门月度预算,不能报销");
        }

        // 执行状态
        if (budget.getCarryStatus() != BudgetConstant.BUDGET_CARRY_DOING
            || budget.getStatus() != BudgetConstant.BUDGET_STATUS_PASS)
        {
            throw new MYException("预算[%s]不在执行状态", budget.getName());
        }

        return true;
    }

    /**
     * 是否是最小执行单位
     * 
     * @param budget
     * @return
     */
    public static boolean isUnitBudget(BudgetBean budget)
    {
        if (budget.getType() == BudgetConstant.BUDGET_TYPE_DEPARTMENT
            && budget.getLevel() == BudgetConstant.BUDGET_LEVEL_MONTH)
        {
            return true;
        }

        return false;
    }

    /**
     * getLogLevel
     * 
     * @param budget
     * @return
     */
    public static String getLogLevel(BudgetBean budget)
    {
        if (budget.getType() == BudgetConstant.BUDGET_TYPE_DEPARTMENT
            && budget.getLevel() == BudgetConstant.BUDGET_LEVEL_MONTH)
        {
            return "";
        }

        if (budget.getType() == BudgetConstant.BUDGET_TYPE_DEPARTMENT
            && budget.getLevel() == BudgetConstant.BUDGET_LEVEL_YEAR)
        {
            return "2";
        }

        if (budget.getType() == BudgetConstant.BUDGET_TYPE_LOCATION)
        {
            return "1";
        }

        if (budget.getType() == BudgetConstant.BUDGET_TYPE_COMPANY)
        {
            return "0";
        }

        return "";
    }

    /**
     * 判断使用状态
     * 
     * @param budget
     * @return
     */
    public static boolean budgetCanUse(BudgetBean budget)
    {
        if (budget.getType() != BudgetConstant.BUDGET_TYPE_DEPARTMENT
            || budget.getLevel() != BudgetConstant.BUDGET_LEVEL_MONTH)
        {
            return false;
        }

        // 执行状态
        if (budget.getCarryStatus() != BudgetConstant.BUDGET_CARRY_DOING
            || budget.getStatus() != BudgetConstant.BUDGET_STATUS_PASS)
        {
            return false;
        }

        return true;
    }

    /**
     * 获取实际的预算(执行中的采用执行预算,结束的使用真正预算,这样才能释放预算)
     * 
     * @param budgetBean
     * @param budgetItemBean
     * @return
     */
    @Deprecated
    public static double getBudgetItemRealBudget(BudgetBean budgetBean,
                                                 BudgetItemBean budgetItemBean)
    {
        if (budgetBean.getStatus() != BudgetConstant.BUDGET_STATUS_END)
        {
            return budgetItemBean.getBudget();
        }
        else
        {
            // 实际使用的啊
            return budgetItemBean.getRealMonery();
        }
    }

    /**
     * getLinkName
     * 
     * @param budgetBean
     * @return
     */
    public static String getLinkName(BudgetBean budgetBean)
    {
        // budget/budget.do?method=findBudget&update=1&id=
        return "<a href='../budget/budget.do?method=findBudget&id=" + budgetBean.getId() + "'>"
               + budgetBean.getName() + "</a> ";
    }

    /**
     * format list
     * 
     * @param items
     * @return
     */
    public static List<BudgetItemVO> formatBudgetItemList(List<BudgetItemVO> items)
    {
        for (BudgetItemVO budgetItemVO : items)
        {
            formatBudgetItem(budgetItemVO);
        }

        return items;
    }

    public static List<BudgetVO> formatBudgetList(List<BudgetVO> items)
    {
        for (BudgetVO budgetItemVO : items)
        {
            formatBudgetVO(budgetItemVO);
        }

        return items;
    }

    /**
     * formatBudgetLogList
     * 
     * @param items
     * @return List<BudgetLogVO>
     */
    public static List<BudgetLogVO> formatBudgetLogList(List<BudgetLogVO> items)
    {
        for (BudgetLogVO log : items)
        {
            formatBudgetLog(log);
        }

        return items;
    }
}
