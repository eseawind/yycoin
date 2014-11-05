/**
 * File Name: LockHelper.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-6-23<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.helper;


import java.util.ArrayList;
import java.util.List;

import com.china.center.tools.StringTools;


/**
 * LockHelper
 * 
 * @author ZHUZHU
 * @version 2011-6-23
 * @see LockHelper
 * @since 3.0
 */
public abstract class LockHelper
{
    private static final List<Object> LOCKLIST = new ArrayList<Object>();

    private static final Object LOCK = new Object();

    private static final int MAX_LOCK = 500;

    static
    {
        for (int i = 0; i < MAX_LOCK; i++ )
        {
            LOCKLIST.add(new Object());
        }
    }

    /**
     * getLock
     * 
     * @param id
     * @return
     */
    public static Object getLock(String id)
    {
        if (StringTools.isNullOrNone(id))
        {
            return LOCK;
        }

        int index = Math.abs(id.hashCode() % MAX_LOCK);

        return LOCKLIST.get(index);
    }
}
