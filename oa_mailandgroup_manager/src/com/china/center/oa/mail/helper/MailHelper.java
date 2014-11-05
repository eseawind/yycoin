/**
 * File Name: MaiHelper.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-4-19<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.mail.helper;


import java.util.HashSet;
import java.util.Set;

import com.china.center.tools.StringTools;


/**
 * MaiHelper
 * 
 * @author zhuzhu
 * @version 2009-4-19
 * @see MailHelper
 * @since 1.0
 */
public abstract class MailHelper
{
    /**
     * trimStaffers
     * 
     * @param ids
     * @return
     */
    public static String trimStaffers(String ids)
    {
        if (StringTools.isNullOrNone(ids))
        {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        Set<String> set = new HashSet<String>();

        String[] idArray = ids.split(";");

        for (String string : idArray)
        {
            set.add(string);
        }

        for (String string2 : set)
        {
            builder.append(string2).append(';');
        }

        return builder.toString();
    }

    /**
     * delete specify staffer
     * @param ids
     * @param stafferId
     * @return
     */
    public static String deleteStaffer(String ids, String stafferId)
    {
        if (StringTools.isNullOrNone(ids))
        {
            return "";
        }

        if (StringTools.isNullOrNone(stafferId))
        {
            return ids;
        }

        StringBuilder builder = new StringBuilder();

        Set<String> set = new HashSet<String>();

        String[] idArray = ids.split(";");

        for (String string : idArray)
        {
            set.add(string);
        }

        set.remove(stafferId);

        for (String string2 : set)
        {
            builder.append(string2).append(';');
        }

        return builder.toString();
    }
}
