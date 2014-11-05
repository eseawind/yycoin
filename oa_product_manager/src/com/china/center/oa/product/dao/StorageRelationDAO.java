/**
 * File Name: StorageRelationDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-22<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.vo.StorageRelationVO;
import com.china.center.oa.product.vs.StorageRelationBean;


/**
 * StorageRelationDAO
 * 
 * @author ZHUZHU
 * @version 2010-8-22
 * @see StorageRelationDAO
 * @since 1.0
 */
public interface StorageRelationDAO extends DAO<StorageRelationBean, StorageRelationVO>
{
    int sumAllProductInStorage(String storageId);

    int sumProductInStorage(String productId, String storageId);

    int sumProductInDepotpartId(String productId, String depotpartId);

    int updateStorageRelationAmount(String id, int change);

    int sumProductInDepotpartIdAndPriceKey(String productId, String depotpartId, String priceKey);

    int sumProductInLocationId(String productId, String locationId);

    /**
     * 统计良品仓的库存
     * 
     * @param productId
     * @param locationId
     * @return
     */
    int sumProductInOKLocationId(String productId, String locationId);

    int sumProductInLocationIdAndPriceKey(String productId, String locationId, String priceKey);

    int sumAllProductByProductId(String productId);

    List<StorageRelationVO> queryStorageRelationByCondition(ConditionParse condition,
                                                            boolean isLimit);

    /**
     * 查询仓库下库存,和价格无关的o
     * 
     * @param depotId
     * @return
     */
    List<StorageRelationVO> queryStorageRelationWithoutPrice(String depotId);

    /**
     * findByDepotpartIdAndProductIdAndPriceKeyAndStafferId(查询)
     * 
     * @param depotpartId
     * @param productId
     * @param priceKey
     * @param stafferId
     * @return
     */
    StorageRelationBean findByDepotpartIdAndProductIdAndPriceKeyAndStafferId(String depotpartId,
                                                                             String productId,
                                                                             String priceKey,
                                                                             String stafferId);
    
    /**
     * 根据三要素查询库存
     * 
     * @param depotpartId
     * @param productId
     * @param stafferId
     * @return
     */
    List<StorageRelationBean> queryByDepotpartIdAndProductIdAndStafferId(String depotpartId,
            String productId,
            String stafferId);
    
    int sumByDepotpartIdAndProductIdAndStafferId(String depotpartId,
            String productId,
            String stafferId);
    
    int updateStorageRelationInputRate(String id, double inputRate);
}
