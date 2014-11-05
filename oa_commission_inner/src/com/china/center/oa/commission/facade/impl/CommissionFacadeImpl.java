package com.china.center.oa.commission.facade.impl;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.commission.facade.CommissionFacade;
import com.china.center.oa.commission.manager.CommissionManager;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.facade.AbstarctFacade;
import com.china.center.tools.JudgeTools;

public class CommissionFacadeImpl extends AbstarctFacade implements CommissionFacade 
{

    private CommissionManager commissionManager = null;
    
    @Override
    public boolean addCommission(String userId) throws MYException 
    {
        
        JudgeTools.judgeParameterIsNull(userId);
        
        User user = userManager.findUser(userId);
        
        checkUser(user);
        
        String auth = AuthConstant.COMMISSION_OPR;
        
        if (containAuth(user,auth))
        {            
            return commissionManager.addBean(user);
        }
        else
        {
            throw noAuth();
        }
        
    }

    /**
     * 
     * {@inheritDoc}
     */
    public boolean deleteCommission(String userId, String id) throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId,id);
        
        User user = userManager.findUser(userId);
        
        checkUser(user);
        
        String auth = AuthConstant.COMMISSION_OPR;
        
        if (containAuth(user,auth))
        {
            return commissionManager.deleteBean(user,id);
        }else
        {
            throw noAuth();
        }
    }
    
    public CommissionManager getCommissionManager() {
        return commissionManager;
    }

    public void setCommissionManager(CommissionManager commissionManager) {
        this.commissionManager = commissionManager;
    }

}
