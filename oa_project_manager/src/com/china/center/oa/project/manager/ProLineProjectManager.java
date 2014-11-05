package com.china.center.oa.project.manager;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;

public interface ProLineProjectManager 
{

	 /**
     * 增加产品行项目
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean addProLineProject(User user, String arr1[],String arr2[],String arr3[],String refid)
        throws MYException;
    
    /**
     * 项目产品行项目
     * @param user
     * @param ids
     * @param counts
     * @param prices
     * @param refid
     * @return
     * @throws MYException
     */
    public boolean addProjectProLineProject(User user, String[] ids, String[] counts,
			String[] prices,String refid) throws MYException ;

    
    /**
     * 修改产品行项目
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean updateProLineProject(User user, String arr1[],String arr2[],String arr3[],String refid)
        throws MYException;
    
    public boolean submitProLineProject(User user, String id, String processId)
    throws MYException;
}
