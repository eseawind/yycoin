package com.china.center.oa.project.manager;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;

public interface StafferProjectManager 
{

	 /**
     * 增加人员行项目
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
	boolean addStafferLineProject(User user, String s_prostafferId[],String role[],String refid)
	throws MYException;

    
    /**
     * 修改人员行项目
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean updateStafferLineProject(User user, String s_prostafferId[],String role[],String refid)
        throws MYException;
    
    public boolean submitStafferLineProject(User user, String id, String processId)
    throws MYException;
    
}
