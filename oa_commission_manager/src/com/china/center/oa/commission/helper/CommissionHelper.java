package com.china.center.oa.commission.helper;

import com.china.center.tools.TimeTools;

public abstract class CommissionHelper 
{

    public static double getFinanceFeeRatio(String date1, String date2)
    {
        // date1 - date2
        int day = TimeTools.cdate(date1, date2);
        
        if (day <= 60)
            return 0.015d;
        else
            return 0.02d;
    }    
    
}
