package com.china.center.oa.project.manager;

import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;

public interface TranLineProjectManager 
{

	 /**
     * 增加交付行项目
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean addTranLineProject(User user, List<String> TtransType,List<String> Ttransobj,List<String> TtransobjCount,
    		List<String> TtransTime,List<String> TtransDays,List<String> trancurrentTask,List<String> Treceiverid,List<String> Ts_tranBeforeids,List<String> Ts_tranAfterids,String refid)
        throws MYException;

    
    /**
     * 修改交付行项目
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean updateTranLineProject(User user, List<String> TpayType,List<String> Tmoney,List<String> Ttime,
    		List<String> Tdays,List<String> TBefore,List<String> Tafter,String refid)
        throws MYException;
    
    public boolean submitTranLineProject(User user, String id, String processId)
    throws MYException;
}
