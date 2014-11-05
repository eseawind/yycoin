/**
 * File Name: StafferHelper.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: ZHUACHEN<br>
 * CreateTime: 2009-8-23<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.helper;


import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.tools.StringTools;


/**
 * StafferHelper
 * 
 * @author ZHUZHU
 * @version 2009-8-23
 * @see StafferHelper
 * @since 1.0
 */
public abstract class StafferHelper
{
    public static boolean hasEnc(StafferBean bean)
    {
        return !StringTools.isNullOrNone(bean.getPwkey()) && bean.getPwkey().trim().length() == 80;
    }
    
    public static boolean isVirtualDepartment(String prin)
    {
    	for (String each : PublicConstant.VIRTUAL_DEPARTMENT)
    	{
    		if (each.equals(prin))
    			return true;
    	}
    	
    	return false;
    }
}
