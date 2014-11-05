package com.china.center.oa.commission.manager;

import java.util.Set;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.commission.bean.BlackBean;
import com.china.center.oa.commission.bean.BlackRuleBean;

public interface BlackManager 
{

    boolean addBean(BlackRuleBean bean, User user) throws MYException;
    
    boolean updateBean(BlackRuleBean bean, User user) throws MYException;
    
    boolean deleteBean(String id, User user) throws MYException;
    
    /**
     * 每日统计黑名单 job
     */
    void statsBlack();
    
    void  processOutPayChangedWithoutTransaction(String stafferId, BlackBean blackBean,
            double backValue, String refId, final Set<String> idSet);
    
    /**
     * 当销售单由未付款变为已付款对黑名单的影响重新统计. stats per 5 mins.
     */
    void statsBlackWhenOutPayChanged();
    
    /**
     * job
     * 每日黑名单对应的产品明细
     */
    void statsBlackOutDetail();
}
