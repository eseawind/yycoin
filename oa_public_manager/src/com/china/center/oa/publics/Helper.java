/*
 * File Name: Helper.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-12-15
 * Grant: open source to everybody
 */
package com.china.center.oa.publics;


import javax.servlet.http.HttpServletRequest;

import com.center.china.osgi.config.ConfigLoader;
import com.center.china.osgi.publics.User;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.vo.UserVO;
import com.china.center.tools.FileTools;


/**
 * Helper
 * 
 * @author ZHUZHU
 * @version 2007-12-16
 * @see
 * @since
 */
public abstract class Helper
{
    public static String getCurrentLocationId(HttpServletRequest request)
    {
        User user = getUser(request);

        return user.getLocationId();
    }

    public static User getUser(HttpServletRequest request)
    {
        return (User)request.getSession().getAttribute("user");
    }

    public static StafferBean getStaffer(HttpServletRequest request)
    {
        return (StafferBean)request.getSession().getAttribute("g_stafferBean");
    }

    /**
     * getPageSize
     * 
     * @param request
     * @return
     */
    public static int getPageSize(HttpServletRequest request)
    {
        Object attribute = request.getSession().getAttribute("g_page");

        if (attribute == null)
        {
            return 10;
        }

        return (Integer)attribute;
    }

    /**
     * 获得工程模板文件存放路径(end with /)
     * 
     * @return
     */
    public static String getRootPath()
    {
        return FileTools.formatPath(ConfigLoader.getProperty("root_path"));
    }

    public static User getSystemUser()
    {
        UserVO system = new UserVO();

        system.setId("0");

        system.setName("system");

        system.setStafferName("system");

        system.setRoleName("system");
        
        system.setLocationName("system");

        system.setLocationId(PublicConstant.VIRTUAL_LOCATION);

        system.setStafferId(StafferConstant.SUPER_STAFFER);

        return system;
    }
}
