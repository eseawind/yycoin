/**
 * File Name: LoginHelper.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: ZHUACHEN<br>
 * CreateTime: 2009-8-23<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.helper;


import com.china.center.tools.DecSecurity;
import com.china.center.tools.Security;
import com.china.center.tools.StringTools;


/**
 * LoginHelper
 * 
 * @author ZHUZHU
 * @version 2009-8-23
 * @see LoginHelper
 * @since 1.0
 */
public abstract class LoginHelper
{
    /**
     * encRadomStr
     * 
     * @param pwkey
     *            DB存储
     * @param key
     *            锁的ID
     * @param random
     *            加密的字符
     * @return
     */
    public static String encRadomStr(String pwkey, String key, String random)
    {
        String md5key = Security.getSecurity(key);

        String deskeygen = md5key.substring(0, 4)
                           + md5key.substring(md5key.length() - 4, md5key.length());

        String realKey = DecSecurity.decrypt(pwkey, deskeygen);

        if (StringTools.isNullOrNone(realKey))
        {
            return "";
        }

        SoftKey mysoftkey = new SoftKey();

        try
        {
            return mysoftkey.StrEnc(random, realKey);
        }
        catch (Exception e)
        {
            return "";
        }
    }
}
