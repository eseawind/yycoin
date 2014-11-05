package com.china.center.oa.project.manager;

import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;

public interface PayLineProjectManager 
{
	 /**
     * 增加付款行项目
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean addPayLineProject(User user, List<String> TpayType,List<String> Tmoney,List<String> Ttime,
    		List<String> Tdays,List<String> TBefore,List<String> Tafter,String refid)
        throws MYException;

    
    /**
     * 修改付款行项目
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean updatePayLineProject(User user, List<String> TpayType,List<String> Tmoney,List<String> Ttime,
    		List<String> Tdays,List<String> TBefore,List<String> Tafter,String refid)
        throws MYException;
    
    public boolean submitPayLineProject(User user, String id, String processId)
    throws MYException;
}
