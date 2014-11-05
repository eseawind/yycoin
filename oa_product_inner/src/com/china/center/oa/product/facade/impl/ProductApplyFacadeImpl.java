package com.china.center.oa.product.facade.impl;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.ProductApplyBean;
import com.china.center.oa.product.facade.ProductApplyFacade;
import com.china.center.oa.product.manager.ProductApplyManager;
import com.china.center.oa.publics.facade.AbstarctFacade;
import com.china.center.tools.JudgeTools;

public class ProductApplyFacadeImpl extends AbstarctFacade implements ProductApplyFacade {

    private ProductApplyManager productApplyManager = null;

    public ProductApplyFacadeImpl() {
    }

    @Override
    public boolean addProductApply(String userId, ProductApplyBean bean) throws MYException 
    {

        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);
        //
//        if (containAuth(user, AuthConstant.PRODUCT_OPR)) {
//
            return productApplyManager.addProductApply(user, bean);
//
//        } else {
//            throw noAuth();
//        }

    }

    @Override
    public boolean updateProductApply(String userId, ProductApplyBean bean ) throws MYException 
    {

        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

//        if (containAuth(user, AuthConstant.PRODUCT_OPR)) {
//            
            return productApplyManager.updateProductApply(user, bean);
//        } else {
//            throw noAuth();
//        }

    }

    @Override
    public boolean deleteProductApply(String userId, String id) throws MYException {

        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

//        if (containAuth(user, AuthConstant.PRODUCT_OPR)) {
            return productApplyManager.deleteProductApply(user, id);
//        } else {
//            throw noAuth();
//        }

    }

    @Override
    public boolean passProductApply(String userId, ProductApplyBean bean) throws MYException {
        
        JudgeTools.judgeParameterIsNull(userId, bean);
        
        User user = userManager.findUser(userId);
        
        checkUser(user);
        
        return productApplyManager.passProductApply(user, bean);
    }

    @Override
    public boolean pass1ProductApply(String userId, ProductApplyBean bean) throws MYException {
        
        JudgeTools.judgeParameterIsNull(userId, bean);
        
        User user = userManager.findUser(userId);
        
        checkUser(user);
        
        return productApplyManager.pass1ProductApply(user, bean);
    }

    @Override
    public boolean rejectProductApply(String userId, ProductApplyBean bean) throws MYException {
        
        JudgeTools.judgeParameterIsNull(userId, bean);
        
        User user = userManager.findUser(userId);
        
        checkUser(user);
        
        return productApplyManager.rejectProductApply(user, bean);
    }
    
    public ProductApplyManager getProductApplyManager() {
        return productApplyManager;
    }

    public void setProductApplyManager(ProductApplyManager productApplyManager) {
        this.productApplyManager = productApplyManager;
    }


}
