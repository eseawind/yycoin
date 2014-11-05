package com.china.center.oa.product.facade;

import com.china.center.common.MYException;
import com.china.center.oa.product.bean.ProductApplyBean;

public interface ProductApplyFacade {

    boolean addProductApply(String userId, ProductApplyBean bean) throws MYException;

    boolean updateProductApply(String userId, ProductApplyBean bean) throws MYException;

    boolean deleteProductApply(String userId, String id) throws MYException;
    
    boolean passProductApply(String userId, ProductApplyBean bean) throws MYException;
    
    boolean pass1ProductApply(String userId, ProductApplyBean bean) throws MYException;
    
    boolean rejectProductApply(String userId, ProductApplyBean bean) throws MYException;
}
