/**
 * File Name: StorageRelationListenerSailImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.listener.impl;


import java.util.LinkedList;
import java.util.List;

import com.china.center.oa.product.listener.StorageRelationListener;
import com.china.center.oa.product.vo.StorageRelationVO;
import com.china.center.oa.product.vs.StorageRelationBean;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.tools.TimeTools;


/**
 * StorageRelationListenerSailImpl(库单未库管通过是预占库存的)
 * 
 * @author ZHUZHU
 * @version 2010-11-21
 * @see StorageRelationListenerSailImpl
 * @since 3.0
 */
public class StorageRelationListenerSailImpl implements StorageRelationListener
{
    private OutDAO outDAO = null;

    /**
     * default constructor
     */
    public StorageRelationListenerSailImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.listener.StorageRelationListener#onFindMaySailStorageRelation(com.china.center.oa.product.vs.StorageRelationBean)
     */
    public int onFindPreassignByStorageRelation(StorageRelationBean bean)
    {
        // 销售单库管未通过是预占库存的
        int sumInOut = outDAO.sumNotEndProductInOutByStorageRelation(bean.getProductId(), bean
            .getDepotpartId(), bean.getPriceKey(), bean.getStafferId());

        // 出库单提交后也是预占库存的(因为正数需要踢出,防止开单抵消单据)
        int sumInIn = outDAO.sumNotEndProductInInByStorageRelation(bean.getProductId(), bean
            .getDepotpartId(), bean.getPriceKey(), bean.getStafferId());

        // 返回一定大于0
        int result = sumInOut + sumInIn;

        if (result < 0)
        {
            return 0;
        }

        return result;
    }

    public int onFindPreassignByStorageRelation2(String depotpartId, String productId, String stafferId)
    {
        // 销售单库管未通过是预占库存的
        int sumInOut = outDAO.sumNotEndProductInOutByStorageRelation2(productId, depotpartId, stafferId);

        // 出库单提交后也是预占库存的(因为正数需要踢出,防止开单抵消单据)
        int sumInIn = outDAO.sumNotEndProductInInByStorageRelation2(productId, depotpartId, stafferId);

        // 返回一定大于0
        int result = sumInOut + sumInIn;

        if (result < 0)
        {
            return 0;
        }

        return result;
    }
    
    public int onFindInwayByStorageRelation(StorageRelationBean relation)
    {
        // 修改后在途的产品直接是异动库存,但是状态在3,4
        Integer sum = -outDAO.sumInwayProductInBuy(relation, TimeTools.getDateShortString( -365),
            TimeTools.now_short());

        return sum;
    }

    public List<StorageRelationVO> onExportOtherStorageRelation()
    {
        List<BaseBean> baseList = outDAO.queryInwayOut();

        List<StorageRelationVO> voList = new LinkedList<StorageRelationVO>();

        for (BaseBean each : baseList)
        {
            StorageRelationVO vo = new StorageRelationVO();

            vo.setDepotpartName(each.getDepotpartName());

            vo.setProductName(each.getProductName());

            vo.setAmount( -each.getAmount());

            vo.setPrice(each.getCostPrice());

            vo.setProductId(each.getProductId());

            vo.setLocationId(each.getLocationId());

            vo.setStorageName(each.getOutId());

            voList.add(vo);
        }

        return voList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "StorageRelationListener.SailImpl";
    }

    /**
     * @return the outDAO
     */
    public OutDAO getOutDAO()
    {
        return outDAO;
    }

    /**
     * @param outDAO
     *            the outDAO to set
     */
    public void setOutDAO(OutDAO outDAO)
    {
        this.outDAO = outDAO;
    }
}
