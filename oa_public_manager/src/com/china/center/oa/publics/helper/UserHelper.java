/**
 * File Name: UserHelper.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2009-12-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.helper;


import com.center.china.osgi.publics.User;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.vo.UserVO;


/**
 * UserHelper
 * 
 * @author ZHUZHU
 * @version 2009-12-6
 * @see UserHelper
 * @since 1.0
 */
public abstract class UserHelper
{
    /**
     * getSystemUser
     * 
     * @return
     */
    public static User getSystemUser()
    {
        UserVO user = new UserVO();

        // CEO
        user.setId("1");

        user.setStafferId(StafferConstant.SUPER_STAFFER);

        user.setLocationId(PublicConstant.CENTER_LOCATION);

        user.setStafferName("SYSTEM");

        user.setLocationName("SYSTEM");

        user.setStatus(0);

        return user;
    }
}
