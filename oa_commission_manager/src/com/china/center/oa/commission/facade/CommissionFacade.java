package com.china.center.oa.commission.facade;

import com.china.center.common.MYException;

public interface CommissionFacade 
{

    boolean addCommission(String userId) throws MYException;
    
    boolean deleteCommission(String userId, String id) throws MYException;
}
