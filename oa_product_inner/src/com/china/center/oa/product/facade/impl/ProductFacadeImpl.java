/**
 * File Name: ProductFacadeImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.facade.impl;


import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.ComposeFeeDefinedBean;
import com.china.center.oa.product.bean.ComposeProductBean;
import com.china.center.oa.product.bean.DecomposeProductBean;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.bean.DepotpartBean;
import com.china.center.oa.product.bean.GSOutBean;
import com.china.center.oa.product.bean.PriceChangeBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.bean.ProviderBean;
import com.china.center.oa.product.bean.ProviderUserBean;
import com.china.center.oa.product.bean.StorageApplyBean;
import com.china.center.oa.product.bean.StorageBean;
import com.china.center.oa.product.facade.ProductFacade;
import com.china.center.oa.product.manager.ComposeProductManager;
import com.china.center.oa.product.manager.DepotManager;
import com.china.center.oa.product.manager.DepotpartManager;
import com.china.center.oa.product.manager.PriceChangeManager;
import com.china.center.oa.product.manager.ProductManager;
import com.china.center.oa.product.manager.ProviderManager;
import com.china.center.oa.product.manager.StorageApplyManager;
import com.china.center.oa.product.manager.StorageManager;
import com.china.center.oa.product.manager.StorageRelationManager;
import com.china.center.oa.product.vo.ComposeProductVO;
import com.china.center.oa.product.vo.PriceChangeVO;
import com.china.center.oa.product.vs.ProductVSLocationBean;
import com.china.center.oa.product.vs.StorageRelationBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.constant.PublicLock;
import com.china.center.oa.publics.facade.AbstarctFacade;
import com.china.center.tools.JudgeTools;


/**
 * ProductFacadeImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-15
 * @see ProductFacadeImpl
 * @since 1.0
 */
public class ProductFacadeImpl extends AbstarctFacade implements ProductFacade
{
    private ProductManager productManager = null;

    private ProviderManager providerManager = null;

    private DepotManager depotManager = null;

    private DepotpartManager depotpartManager = null;

    private StorageManager storageManager = null;

    private PriceChangeManager priceChangeManager = null;

    private ComposeProductManager composeProductManager = null;

    private StorageRelationManager storageRelationManager = null;

    private StorageApplyManager storageApplyManager = null;

    private static Object COMPOSE_LOCK = new Object();

    /**
     * default constructor
     */
    public ProductFacadeImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.facade.ProductFacade#addProductBean(java.lang.String,
     *      com.china.center.oa.product.bean.ProductBean)
     */
    public boolean addProductBean(String userId, ProductBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.PRODUCT_OPR))
        {
            return productManager.addProductBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.facade.ProductFacade#changeProductStatus(java.lang.String, java.lang.String,
     *      int, int)
     */
    public boolean changeProductStatus(String userId, String productId, int oldStatus, int newStatus)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, productId);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.PRODUCT_CHECK))
        {
            return productManager.changeProductStatus(user, productId, oldStatus, newStatus);
        }
        else
        {
            throw noAuth();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.facade.ProductFacade#configProductVSLocation(java.lang.String, java.lang.String,
     *      java.util.List)
     */
    public boolean configProductVSLocation(String userId, String productId,
                                           List<ProductVSLocationBean> vsList)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, productId);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.PRODUCT_OPR))
        {
            return productManager.configProductVSLocation(user, productId, vsList);
        }
        else
        {
            throw noAuth();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.facade.ProductFacade#deleteProductBean(java.lang.String, java.lang.String)
     */
    public boolean deleteProductBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.PRODUCT_OPR)
            || containAuth(user, AuthConstant.PRODUCT_CHECK))
        {
            return productManager.deleteProductBean(user, id);
        }
        else
        {
            throw noAuth();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.facade.ProductFacade#updateProductBean(java.lang.String,
     *      com.china.center.oa.product.bean.ProductBean)
     */
    public boolean updateProductBean(String userId, ProductBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.PRODUCT_OPR))
        {
            return productManager.updateProductBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * checkHisProvider
     * 
     * @param userId
     * @param cid
     * @return
     * @throws MYException
     */
    public boolean checkHisProvider(String userId, String cid)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, cid);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_HIS_CHECK))
        {
            return providerManager.checkHisProvider(user, cid);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * 增加供应商
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addProvider(String userId, ProviderBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_OPR_PROVIDER))
        {
            return providerManager.addBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * addOrUpdateUserBean
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean bingProductTypeToCustmer(String userId, String pid, String[] productTypeIds)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, pid, productTypeIds);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_OPR_PROVIDER))
        {
            return providerManager.bingProductTypeToCustmer(user, pid, productTypeIds);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * addOrUpdateUserBean
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addOrUpdateUserBean(String userId, ProviderUserBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_OPR_PROVIDER))
        {
            return providerManager.addOrUpdateUserBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * updateUserPassword
     * 
     * @param userId
     * @param id
     * @param newpwd
     * @return
     * @throws MYException
     */
    public boolean updateUserPassword(String userId, String id, String newpwd)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id, newpwd);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_OPR_PROVIDER))
        {
            return providerManager.updateUserPassword(user, id, newpwd);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * 修改供应商
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateProvider(String userId, ProviderBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_OPR_PROVIDER))
        {
            return providerManager.updateBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    /**
     * 删除供应商
     * 
     * @param userId
     * @param providerId
     * @return
     * @throws MYException
     */
    public boolean delProvider(String userId, String providerId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, providerId);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.CUSTOMER_OPR_PROVIDER))
        {
            return providerManager.delBean(user, providerId);
        }
        else
        {
            throw noAuth();
        }
    }

    public boolean addDepotBean(String userId, DepotBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.DEPOT_OPR))
        {
            return depotManager.addDepot(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    public boolean deleteDepotBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.DEPOT_OPR))
        {
            return depotManager.deleteDepot(user, id);
        }
        else
        {
            throw noAuth();
        }
    }

    public boolean updateDepotBean(String userId, DepotBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.DEPOT_OPR))
        {
            return depotManager.updateDepot(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    public boolean addDepotpartBean(String userId, DepotpartBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.DEPOTPART_OPR))
        {
            return depotpartManager.addBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    public boolean deleteDepotpartBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.DEPOTPART_OPR))
        {
            return depotpartManager.deleteBean(user, id);
        }
        else
        {
            throw noAuth();
        }
    }

    public boolean updateDepotpartBean(String userId, DepotpartBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.DEPOTPART_OPR))
        {
            return depotpartManager.updateBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    public boolean addStorageBean(String userId, StorageBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.STORAGE_OPR))
        {
            return storageManager.addBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    public boolean deleteStorageBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.STORAGE_OPR))
        {
            return storageManager.deleteBean(user, id);
        }
        else
        {
            throw noAuth();
        }
    }

    public boolean updateStorageBean(String userId, StorageBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.STORAGE_OPR))
        {
            return storageManager.updateBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    public boolean deleteStorageRelation(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.STORAGE_OPR))
        {
            return storageRelationManager.deleteStorageRelation(user, id);
        }
        else
        {
            throw noAuth();
        }
    }

    public boolean transferStorageRelation(String userId, String sourceStorageId,
                                           String dirStorageId, String[] relations)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, sourceStorageId, dirStorageId, relations);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.STORAGE_OPR))
        {
            return storageRelationManager.transferStorageRelation(user, sourceStorageId,
                dirStorageId, relations);
        }
        else
        {
            throw noAuth();
        }
    }

    public String transferStorageRelationInDepotpart(String userId, String sourceRelationId,
                                                     String dirDepotpartId, String amount, String apply)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, sourceRelationId, dirDepotpartId);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.DEPOTPART_OPR))
        {
            return storageRelationManager.transferStorageRelationInDepotpart(user,
                sourceRelationId, dirDepotpartId, amount, apply);
        }
        else
        {
            throw noAuth();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.facade.ProductFacade#addComposeProduct(java.lang.String,
     *      com.china.center.oa.product.bean.ComposeProductBean)
     */
    public boolean addComposeProduct(String userId, ComposeProductBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.PRODUCT_CD))
        {
            return composeProductManager.addComposeProduct(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    public boolean addComposeFeeDefinedBean(String userId, ComposeFeeDefinedBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.PRODUCT_CD))
        {
            return composeProductManager.addComposeFeeDefinedBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    public boolean deleteComposeFeeDefinedBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.PRODUCT_CD))
        {
            return composeProductManager.deleteComposeFeeDefinedBean(user, id);
        }
        else
        {
            throw noAuth();
        }
    }

    public boolean updateComposeFeeDefinedBean(String userId, ComposeFeeDefinedBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.PRODUCT_CD))
        {
            return composeProductManager.updateComposeFeeDefinedBean(user, bean);
        }
        else
        {
            throw noAuth();
        }
    }

    public ComposeProductVO findComposeById(String id)
    {
        return composeProductManager.findById(id);
    }

    public boolean addPriceChange(String userId, PriceChangeBean bean)
        throws MYException
    {
        // LOCK 产品调价
        synchronized (PublicLock.PRODUCT_CORE)
        {
            JudgeTools.judgeParameterIsNull(userId, bean);

            User user = userManager.findUser(userId);

            checkUser(user);

            if (containAuth(user, AuthConstant.PRODUCT_CHANGE_PRICE))
            {
                return priceChangeManager.addPriceChange(user, bean);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean rollbackPriceChange(String userId, String id)
        throws MYException
    {
        // LOCK 产品调价回滚
        synchronized (PublicLock.PRODUCT_CORE)
        {
            JudgeTools.judgeParameterIsNull(userId, id);

            User user = userManager.findUser(userId);

            checkUser(user);

            if (containAuth(user, AuthConstant.PRODUCT_CHANGE_PRICE))
            {
                return priceChangeManager.rollbackPriceChange(user, id);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean onPriceChange(String userId, ProductBean bean)
    {
        try
        {
            JudgeTools.judgeParameterIsNull(userId, bean);

            User user = userManager.findUser(userId);

            checkUser(user);

            return priceChangeManager.onPriceChange(user, bean);
        }
        catch (MYException e)
        {
            return false;
        }

    }

    public int onPriceChange2(String userId, StorageRelationBean bean)
    {
        try
        {
            JudgeTools.judgeParameterIsNull(userId, bean);

            User user = userManager.findUser(userId);

            checkUser(user);

            return priceChangeManager.onPriceChange2(user, bean);
        }
        catch (MYException e)
        {
            return 0;
        }
    }

    public boolean isStorageRelationLock()
    {
        return storageRelationManager.isStorageRelationLock();
    }

    public void lockStorageRelation(String userId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.PRODUCT_CHANGE_PRICE))
        {
            storageRelationManager.lockStorageRelation();
        }
        else
        {
            throw noAuth();
        }
    }

    public void unlockStorageRelation(String userId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.PRODUCT_CHANGE_PRICE))
        {
            storageRelationManager.unlockStorageRelation();
        }
        else
        {
            throw noAuth();
        }
    }

    public boolean lastPassComposeProduct(String userId, String id)
        throws MYException
    {
        // LOCK 合成产品(两个锁)
        synchronized (COMPOSE_LOCK)
        {
            synchronized (PublicLock.PRODUCT_CORE)
            {
                JudgeTools.judgeParameterIsNull(userId);

                User user = userManager.findUser(userId);

                checkUser(user);

                if (containAuth(user, AuthConstant.PRODUCT_CD_CRO))
                {
                    return composeProductManager.lastPassComposeProduct(user, id);
                }
                else
                {
                    throw noAuth();
                }
            }
        }
    }

    public boolean passComposeProduct(String userId, String id)
        throws MYException
    {
        synchronized (COMPOSE_LOCK)
        {
            JudgeTools.judgeParameterIsNull(userId);

            User user = userManager.findUser(userId);

            checkUser(user);

            if (containAuth(user, AuthConstant.PRODUCT_CD_MANGAER))
            {
                return composeProductManager.passComposeProduct(user, id);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean rollbackComposeProduct(String userId, String id)
        throws MYException
    {
        synchronized (COMPOSE_LOCK)
        {
            JudgeTools.judgeParameterIsNull(userId);

            User user = userManager.findUser(userId);

            checkUser(user);

            if (containAuth(user, AuthConstant.PRODUCT_CD))
            {
                return composeProductManager.rollbackComposeProduct(user, id);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean rejectComposeProduct(String userId, String id)
        throws MYException
    {
        synchronized (COMPOSE_LOCK)
        {
            JudgeTools.judgeParameterIsNull(userId);

            User user = userManager.findUser(userId);

            checkUser(user);

            if (containAuth(user, AuthConstant.PRODUCT_CD_MANGAER, AuthConstant.PRODUCT_CD_CRO))
            {
                return composeProductManager.rejectComposeProduct(user, id);
            }
            else
            {
                throw noAuth();
            }
        }
    }

    public boolean addStorageApply(String userId, StorageApplyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        return storageApplyManager.addBean(user, bean);
    }

    public boolean passStorageApply(String userId, String id)
        throws MYException
    {
        // LOCK 产品私买属性转移
        synchronized (PublicLock.PRODUCT_CORE)
        {
            JudgeTools.judgeParameterIsNull(userId, id);

            User user = userManager.findUser(userId);

            checkUser(user);

            return storageApplyManager.passBean(user, id);
        }
    }

    public boolean rejectStorageApply(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        return storageApplyManager.rejectBean(user, id);
    }
    
    /**
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addDecomposeProduct(String userId, DecomposeProductBean bean)
    throws MYException
	{
	    JudgeTools.judgeParameterIsNull(userId, bean);
	
	    User user = userManager.findUser(userId);
	
	    checkUser(user);
	
	    if (containAuth(user, "1024"))
	    {
	        return composeProductManager.addDecomposeProduct(user, bean);
	    }
	    else
	    {
	        throw noAuth();
	    }
	}
    
    public boolean passDecomposeProduct(String userId, String id, String reason)
    throws MYException
	{
        synchronized (PublicLock.PRODUCT_CORE)
        {
            JudgeTools.judgeParameterIsNull(userId);

            User user = userManager.findUser(userId);

            checkUser(user);

            if (containAuth(user, "1025"))
            {
                return composeProductManager.passDecomposeProduct(user, id, reason);
            }
            else
            {
                throw noAuth();
            }
        }
	}
    
    @Override
	public boolean rejectDecomposeProduct(String userId, String id,
			String reason) throws MYException
	{
        JudgeTools.judgeParameterIsNull(userId);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, "1025"))
        {
            return composeProductManager.rejectDecomposeProduct(user, id, reason);
        }
        else
        {
            throw noAuth();
        }
    }
    
    @Override
	public boolean addGSOut(String userId, GSOutBean bean) throws MYException
	{
	    JudgeTools.judgeParameterIsNull(userId, bean);
	
	    User user = userManager.findUser(userId);
	
	    checkUser(user);
	
	    return storageApplyManager.addGSOutBean(user, bean);
	}

	@Override
	public boolean updateGSOut(String userId, GSOutBean bean)
			throws MYException
	{
	    JudgeTools.judgeParameterIsNull(userId, bean);
	
	    User user = userManager.findUser(userId);
	
	    checkUser(user);
	
	    return storageApplyManager.updateGSOutBean(user, bean);
	}

	@Override
	public boolean deleteGSOut(String userId, String id) throws MYException
	{
	    JudgeTools.judgeParameterIsNull(userId, id);
	
	    User user = userManager.findUser(userId);
	
	    checkUser(user);
	
	    return storageApplyManager.deleteGSOutBean(user, id);
	}

	@Override
	public boolean passGSOut(String userId, String id, int nextStatus) throws MYException
	{
	    JudgeTools.judgeParameterIsNull(userId, id);
	
	    User user = userManager.findUser(userId);
	
	    checkUser(user);
	
	    return storageApplyManager.passGSOutBean(user, id, nextStatus);
	}

	@Override
	public boolean rejectGSOut(String userId, String id, String reason) throws MYException
	{
	    JudgeTools.judgeParameterIsNull(userId, id);
	
	    User user = userManager.findUser(userId);
	
	    checkUser(user);
	
	    return storageApplyManager.rejectGSOutBean(user, id, reason);
	}

    public PriceChangeVO findPriceChangeById(String id)
    {
    	return priceChangeManager.findById(id);
    }

    /**
     * @return the productManager
     */
    public ProductManager getProductManager()
    {
        return productManager;
    }

    /**
     * @param productManager
     *            the productManager to set
     */
    public void setProductManager(ProductManager productManager)
    {
        this.productManager = productManager;
    }

    /**
     * @return the providerManager
     */
    public ProviderManager getProviderManager()
    {
        return providerManager;
    }

    /**
     * @param providerManager
     *            the providerManager to set
     */
    public void setProviderManager(ProviderManager providerManager)
    {
        this.providerManager = providerManager;
    }

    /**
     * @return the depotManager
     */
    public DepotManager getDepotManager()
    {
        return depotManager;
    }

    /**
     * @param depotManager
     *            the depotManager to set
     */
    public void setDepotManager(DepotManager depotManager)
    {
        this.depotManager = depotManager;
    }

    /**
     * @return the depotpartManager
     */
    public DepotpartManager getDepotpartManager()
    {
        return depotpartManager;
    }

    /**
     * @param depotpartManager
     *            the depotpartManager to set
     */
    public void setDepotpartManager(DepotpartManager depotpartManager)
    {
        this.depotpartManager = depotpartManager;
    }

    /**
     * @return the storageManager
     */
    public StorageManager getStorageManager()
    {
        return storageManager;
    }

    /**
     * @param storageManager
     *            the storageManager to set
     */
    public void setStorageManager(StorageManager storageManager)
    {
        this.storageManager = storageManager;
    }

    /**
     * @return the storageRelationManager
     */
    public StorageRelationManager getStorageRelationManager()
    {
        return storageRelationManager;
    }

    /**
     * @param storageRelationManager
     *            the storageRelationManager to set
     */
    public void setStorageRelationManager(StorageRelationManager storageRelationManager)
    {
        this.storageRelationManager = storageRelationManager;
    }

    /**
     * @return the composeProductManager
     */
    public ComposeProductManager getComposeProductManager()
    {
        return composeProductManager;
    }

    /**
     * @param composeProductManager
     *            the composeProductManager to set
     */
    public void setComposeProductManager(ComposeProductManager composeProductManager)
    {
        this.composeProductManager = composeProductManager;
    }

    /**
     * @return the priceChangeManager
     */
    public PriceChangeManager getPriceChangeManager()
    {
        return priceChangeManager;
    }

    /**
     * @param priceChangeManager
     *            the priceChangeManager to set
     */
    public void setPriceChangeManager(PriceChangeManager priceChangeManager)
    {
        this.priceChangeManager = priceChangeManager;
    }

    /**
     * @return the storageApplyManager
     */
    public StorageApplyManager getStorageApplyManager()
    {
        return storageApplyManager;
    }

    /**
     * @param storageApplyManager
     *            the storageApplyManager to set
     */
    public void setStorageApplyManager(StorageApplyManager storageApplyManager)
    {
        this.storageApplyManager = storageApplyManager;
    }
}
