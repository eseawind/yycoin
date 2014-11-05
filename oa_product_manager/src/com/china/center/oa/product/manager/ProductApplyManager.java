package com.china.center.oa.product.manager;

import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.ProductApplyBean;
import com.china.center.oa.product.listener.ProductApplyListener;

public interface ProductApplyManager extends ListenerManager<ProductApplyListener> {

    boolean addProductApply(User user, ProductApplyBean bean) throws MYException;

    boolean updateProductApply(User user, ProductApplyBean bean) throws MYException;

    boolean deleteProductApply(User user, String id) throws MYException;
    
    boolean passProductApply(User user, ProductApplyBean bean) throws MYException;
    
    boolean pass1ProductApply(User user, ProductApplyBean bean) throws MYException;

    boolean rejectProductApply(User user, ProductApplyBean bean) throws MYException;
}
