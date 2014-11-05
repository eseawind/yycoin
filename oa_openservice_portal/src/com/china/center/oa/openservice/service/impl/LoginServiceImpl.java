package com.china.center.oa.openservice.service.impl;

import com.center.china.osgi.publics.User;
import com.china.center.oa.openservice.service.LoginService;
import com.china.center.oa.openservice.service.result.WsResult;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.PrincipalshipDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.UserDAO;
import com.china.center.oa.publics.helper.LoginHelper;
import com.china.center.tools.Security;

public class LoginServiceImpl implements LoginService 
{
    private UserDAO userDAO = null;

    private StafferDAO stafferDAO = null;   

    private PrincipalshipDAO    principalshipDAO    = null;
    
    public WsResult login(String name, String password, String rand, String key, String randKey)
    {
        User user = userDAO.findUserByName(name);

        String randVal = rand.toUpperCase();

        if (user == null)
        {
            return errorResult(0);                       
        }

        // 锁定处理
        if (user.getStatus() == PublicConstant.LOGIN_STATUS_LOCK)
        {
            return errorResult(4);
        }

        StafferBean stafferBean = stafferDAO.find(user.getStafferId());

        if (stafferBean == null)
        {
            return errorResult(0); 
        }

        // 验证密码
        boolean confirmPass = user.getPassword().equals(Security.getSecurity(password));
        
        boolean confirmUkey = handleEncLock(key, randKey, randVal, true, stafferBean.getPwkey());
        
        if (!confirmPass)
        {
            return errorResult(2);
        }
        
        if (!confirmUkey)
        {
            return errorResult(3);
        }
        
        String departmentId = stafferBean.getPrincipalshipId();
        
        PrincipalshipBean prin = principalshipDAO.find(departmentId);
        
        if (null == prin)
        {
            errorResult(5);
        }
        
        if (confirmPass && confirmUkey)
        {
            return successResult(departmentId, prin.getName());            
        }
        else
        {
            if ( (user.getFail() + 1) >= PublicConstant.LOGIN_FAIL_MAX)
            {
                userDAO.updateStatus(user.getName(), PublicConstant.LOGIN_STATUS_LOCK);

                userDAO.updateFail(user.getName(), 0);
            }
            else
            {
                userDAO.updateFail(user.getName(), user.getFail() + 1);
            }

            return errorResult(2);
        }
    }

    private boolean handleEncLock(String key, String randKey, String randVal, boolean hasEncLock,
                                  String pwkey)
    {
        return !hasEncLock || LoginHelper.encRadomStr(pwkey, key, randVal).equals(randKey);
    }

    private WsResult errorResult(int ret)
    {
        WsResult result = new WsResult();
                
        result.setRet(ret);
        
        return result;
    }
    
    private WsResult successResult(String departmentId, String departmentName)
    {
        WsResult result = new WsResult();
        
        result.setRet(0);
        
        result.setDepartmentId(departmentId);
        
        result.setDepartmentName(departmentName);
        
        return result;
    }
    
    /**
     * @return the userDAO
     */
    public UserDAO getUserDAO()
    {
        return userDAO;
    }

    /**
     * @param userDAO
     *            the userDAO to set
     */
    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    /**
     * @return the stafferDAO
     */
    public StafferDAO getStafferDAO()
    {
        return stafferDAO;
    }

    /**
     * @param stafferDAO
     *            the stafferDAO to set
     */
    public void setStafferDAO(StafferDAO stafferDAO)
    {
        this.stafferDAO = stafferDAO;
    }

    public PrincipalshipDAO getPrincipalshipDAO() {
        return principalshipDAO;
    }

    public void setPrincipalshipDAO(PrincipalshipDAO principalshipDAO) {
        this.principalshipDAO = principalshipDAO;
    }
    
    
}
