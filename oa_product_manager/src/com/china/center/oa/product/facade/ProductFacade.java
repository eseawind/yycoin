/**
 * File Name: ProductFacade.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.facade;


import java.util.List;

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
import com.china.center.oa.product.vo.ComposeProductVO;
import com.china.center.oa.product.vo.PriceChangeVO;
import com.china.center.oa.product.vs.ProductVSLocationBean;
import com.china.center.oa.product.vs.StorageRelationBean;


/**
 * ProductFacade
 * 
 * @author ZHUZHU
 * @version 2010-8-15
 * @see ProductFacade
 * @since 1.0
 */
public interface ProductFacade
{
    boolean addProductBean(String userId, ProductBean bean)
        throws MYException;

    boolean updateProductBean(String userId, ProductBean bean)
        throws MYException;

    boolean deleteProductBean(String userId, String id)
        throws MYException;

    boolean configProductVSLocation(String userId, String productId,
                                    List<ProductVSLocationBean> vsList)
        throws MYException;

    boolean changeProductStatus(String userId, String productId, int oldStatus, int newStatus)
        throws MYException;

    boolean checkHisProvider(String userId, String cid)
        throws MYException;

    boolean addProvider(String userId, ProviderBean bean)
        throws MYException;

    boolean bingProductTypeToCustmer(String userId, String pid, String[] productTypeIds)
        throws MYException;

    boolean addOrUpdateUserBean(String userId, ProviderUserBean bean)
        throws MYException;

    boolean updateUserPassword(String userId, String id, String newpwd)
        throws MYException;

    boolean updateProvider(String userId, ProviderBean bean)
        throws MYException;

    boolean delProvider(String userId, String providerId)
        throws MYException;

    boolean addDepotBean(String userId, DepotBean bean)
        throws MYException;

    boolean updateDepotBean(String userId, DepotBean bean)
        throws MYException;

    boolean deleteDepotBean(String userId, String id)
        throws MYException;

    boolean addDepotpartBean(String userId, DepotpartBean bean)
        throws MYException;

    boolean updateDepotpartBean(String userId, DepotpartBean bean)
        throws MYException;

    boolean deleteDepotpartBean(String userId, final String id)
        throws MYException;

    boolean addStorageBean(String userId, StorageBean bean)
        throws MYException;

    boolean updateStorageBean(String userId, StorageBean bean)
        throws MYException;

    boolean deleteStorageBean(String userId, final String id)
        throws MYException;

    boolean deleteStorageRelation(String userId, String id)
        throws MYException;

    boolean transferStorageRelation(String userId, String sourceStorageId, String dirStorageId,
                                    String[] relations)
        throws MYException;

    String transferStorageRelationInDepotpart(String userId, String sourceRelationId,
                                              String dirDepotpartId, String amount, String apply)
        throws MYException;

    /**
     * addComposeProduct
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    boolean addComposeProduct(String userId, ComposeProductBean bean)
        throws MYException;

    /**
     * findComposeById
     * 
     * @param id
     * @return
     */
    ComposeProductVO findComposeById(String id);

    /**
     * 产品调价(CORE)(这个事务比较大)
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    boolean addPriceChange(String userId, PriceChangeBean bean)
        throws MYException;

    boolean rollbackPriceChange(String userId, String id)
        throws MYException;

    /**
     * findPriceChangeById
     * 
     * @param id
     * @return
     */
    PriceChangeVO findPriceChangeById(String id);

    /**
     * onPriceChange(是否可以change)
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    boolean onPriceChange(String userId, ProductBean bean);

    int onPriceChange2(String userId, StorageRelationBean bean);

    void lockStorageRelation(String userId)
        throws MYException;;

    void unlockStorageRelation(String userId)
        throws MYException;;

    boolean isStorageRelationLock();

    /**
     * 生产部经理审批
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean passComposeProduct(String userId, String id)
        throws MYException;

    /**
     * rollbackComposeProduct
     * 
     * @param userId
     * @param id
     * @return
     * @throws MYException
     */
    boolean rollbackComposeProduct(String userId, String id)
        throws MYException;

    /**
     * 运营总监审批
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean lastPassComposeProduct(String userId, String id)
        throws MYException;

    /**
     * 驳回合成
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean rejectComposeProduct(String userId, String id)
        throws MYException;

    boolean addStorageApply(String userId, StorageApplyBean bean)
        throws MYException;

    boolean passStorageApply(String userId, String id)
        throws MYException;

    boolean rejectStorageApply(String userId, String id)
        throws MYException;

    boolean addComposeFeeDefinedBean(String userId, ComposeFeeDefinedBean bean)
        throws MYException;

    boolean updateComposeFeeDefinedBean(String userId, ComposeFeeDefinedBean bean)
        throws MYException;

    boolean deleteComposeFeeDefinedBean(String userId, String id)
        throws MYException;
    
    boolean addDecomposeProduct(String userId, DecomposeProductBean bean)
    throws MYException;
    
    boolean passDecomposeProduct(String userId, String id, String reason)
    throws MYException;

    boolean rejectDecomposeProduct(String userId, String id, String reason)
    throws MYException;
    
    boolean addGSOut(String userId, GSOutBean bean)
    throws MYException;
    
    boolean updateGSOut(String userId, GSOutBean bean)
    throws MYException;
    
    boolean deleteGSOut(String userId, String id)
    throws MYException;
    
    boolean passGSOut(String userId, String id, int nextStatus)
    throws MYException;
    
    boolean rejectGSOut(String userId, String id, String reason)
    throws MYException;
}
