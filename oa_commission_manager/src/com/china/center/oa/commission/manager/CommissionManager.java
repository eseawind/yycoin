package com.china.center.oa.commission.manager;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.commission.bean.CommissionBean;



public interface CommissionManager 
{

    void statsFinanceFee0() ;
    
    void statsFinanceFee1() ;
    
    void statsHadPayAndProfit() ;
    
    void statsDrop() ;
    
    /**
     * 要求先做完月结
     * @return
     * @
     */
    void statsFeeAndNetProfit() ;
    
    /**
     * 财务月结转撤销时,要清除费用,到款净利及累计净利
     * @return
     * @
     */
    void rollbackFeeAndNetProfit() ;
    
    /**
     * 增加提成计算
     * @return
     * @throws MYException
     */
    boolean addBean(User user) throws MYException;
    
    boolean deleteBean(User user, String id) throws MYException;
    
    boolean importExternel(User user, CommissionBean bean, String type)
    throws MYException;
    
}
