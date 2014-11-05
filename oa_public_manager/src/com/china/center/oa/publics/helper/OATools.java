/**
 * File Name: OATools.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2009-12-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.helper;


import java.util.Calendar;

import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.tools.TimeTools;


/**
 * OATools
 * 
 * @author ZHUZHU
 * @version 2009-12-8
 * @see OATools
 * @since 1.0
 */
public abstract class OATools
{
    /**
     * 管理行为
     */
    private static boolean managerFlag = true;

    private static boolean changeToV5 = true;

    /**
     * 获得财务年度的开始日期
     * 
     * @return
     */
    public static String getFinanceBeginDate()
    {
        Calendar cal = Calendar.getInstance();

        int month = cal.get(Calendar.MONTH);

        int year = cal.get(Calendar.YEAR);

        // 上一年的
        if (month >= 0 && month <= 1)
        {
            return (year - 1) + "-03-01";
        }

        return year + "-03-01";
    }

    /**
     * 获得财务年度的开始日期
     * 
     * @return
     */
    public static int getFinanceYear()
    {
        Calendar cal = Calendar.getInstance();

        int month = cal.get(Calendar.MONTH);

        int year = cal.get(Calendar.YEAR);

        // 上一年的
        if (month >= 0 && month <= 1)
        {
            return year - 1;
        }

        return year;
    }

    /**
     * 获得财务年度的结束日期
     * 
     * @return
     */
    public static String getFinanceEndDate()
    {
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);

        int month = cal.get(Calendar.MONTH);

        // 0-2月份
        if (month >= 0 && month <= 1)
        {
            int realYear = year;

            int daysOfMonth = TimeTools.getDaysOfMonth(realYear, 2);

            return realYear + "-02-" + daysOfMonth;
        }

        int realYear = year + 1;

        int daysOfMonth = TimeTools.getDaysOfMonth(realYear, 2);

        return realYear + "-02-" + daysOfMonth;
    }

    /**
     * getManagerType
     * 
     * @param type
     * @return
     */
    public static int getManagerType(String type)
    {
        if ("1".equals(type))
        {
            return PublicConstant.MANAGER_TYPE_COMMON;
        }

        return PublicConstant.MANAGER_TYPE_MANAGER;
    }

    /**
     * 是否是管理类型的
     * 
     * @param type
     * @return
     */
    public static boolean isManager(String type)
    {
        if (getManagerFlag())
        {
            return getManagerType(type) == PublicConstant.MANAGER_TYPE_MANAGER;
        }

        return true;
    }

    public static boolean isManager(int type)
    {
        if (getManagerFlag())
        {
            return getManagerType(type) == PublicConstant.MANAGER_TYPE_MANAGER;
        }

        return true;
    }

    /**
     * 是否是普通类型的
     * 
     * @param type
     * @return
     */
    public static boolean isCommon(String type)
    {
        return !isManager(type);
    }

    public static boolean isCommon(int type)
    {
        return !isManager(type);
    }

    /**
     * getManagerType
     * 
     * @param type
     * @return
     */
    public static int getManagerType(int type)
    {
        return getManagerType(String.valueOf(type));
    }

    public static boolean getManagerFlag()
    {
        return OATools.managerFlag;
    }

    /**
     * @return the changeToV5
     */
    public static boolean isChangeToV5()
    {
        return changeToV5;
    }

    /**
     * @param changeToV5
     *            the changeToV5 to set
     */
    public static void setChangeToV5(boolean changeToV5)
    {
        OATools.changeToV5 = changeToV5;
    }
}
