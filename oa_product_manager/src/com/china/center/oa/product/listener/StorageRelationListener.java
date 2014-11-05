/**
 * File Name: StorageRelationListener.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.listener;


import java.util.List;

import com.center.china.osgi.publics.ParentListener;
import com.china.center.common.MYException;
import com.china.center.oa.product.vo.StorageRelationVO;
import com.china.center.oa.product.vs.StorageRelationBean;


/**
 * StorageRelationListener
 * 
 * @author ZHUZHU
 * @version 2010-11-21
 * @see StorageRelationListener
 * @since 3.0
 */
public interface StorageRelationListener extends ParentListener
{
    /**
     * 查询预占的库存
     * 
     * @param bean
     *            当前实际的库存
     * @return
     */
    int onFindPreassignByStorageRelation(StorageRelationBean bean);

    int onFindPreassignByStorageRelation2(String depotpartId, String productId, String stafferId);
    
    /**
     * 在途的产品(库存已经减去)
     * 
     * @param user
     * @param relation
     * @throws MYException
     */
    int onFindInwayByStorageRelation(StorageRelationBean relation);

    /**
     * exportOtherStorageRelation(导出其他的库存)
     * 
     * @return
     */
    List<StorageRelationVO> onExportOtherStorageRelation();
}
