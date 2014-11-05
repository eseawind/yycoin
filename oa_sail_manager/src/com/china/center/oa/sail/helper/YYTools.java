/**
 * File Name: YYTools.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-1-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.helper;


import java.util.Calendar;

import com.china.center.tools.TimeTools;


/**
 * YYTools
 * 
 * @author ZHUZHU
 * @version 2010-1-3
 * @see YYTools
 * @since 1.0
 */
public abstract class YYTools
{
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
     * 获得单据信用、付款统计的时间
     * 
     * @return
     */
    public static String getStatBeginDate()
    {
        return TimeTools.now_short( -15 * 30);
    }

    /**
     * 获得单据信用、付款统计的时间
     * 
     * @return
     */
    public static String getStatEndDate()
    {
        return TimeTools.now_short(1);
    }
}
