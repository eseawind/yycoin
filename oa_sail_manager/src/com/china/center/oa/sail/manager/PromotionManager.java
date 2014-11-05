package com.china.center.oa.sail.manager;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.sail.bean.PromotionBean;

public interface PromotionManager 
{
    
    boolean addBean(User user, PromotionBean bean)
    throws MYException;

    boolean updateBean(User user, PromotionBean bean)
        throws MYException;
    
    boolean deleteBean(User user, String id)
        throws MYException;    
    
    PromotionBean findProductConf(StafferBean sb, ProductBean productBean);
    
    boolean logicDeleteBean(User user, String id)
    throws MYException;
    

}
