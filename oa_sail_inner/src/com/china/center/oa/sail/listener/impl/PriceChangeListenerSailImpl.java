/**
 * File Name: PriceChangeListenerSailImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-13<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.listener.impl;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.PriceChangeBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.listener.PriceChangeListener;
import com.china.center.oa.product.vs.StorageRelationBean;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.helper.YYTools;


/**
 * PriceChangeListenerSailImpl
 * 
 * @author ZHUZHU
 * @version 2010-11-13
 * @see PriceChangeListenerSailImpl
 * @since 3.0
 */
public class PriceChangeListenerSailImpl implements PriceChangeListener
{
    private OutDAO outDAO = null;

    /**
     * default constructor
     */
    public PriceChangeListenerSailImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.listener.PriceChangeListener#onPriceChange(com.center.china.osgi.publics.User,
     *      com.china.center.oa.product.bean.ProductBean)
     */
    public void onPriceChange(User user, ProductBean bean)
        throws MYException
    {
        // 一个是销售产品是否存在(还没有结束的)
        Integer count = outDAO.countNotEndProductInOut(bean.getId(), YYTools.getFinanceBeginDate(),
            YYTools.getFinanceEndDate());

        if (count > 0)
        {
            throw new MYException(bean.getName() + "已经在销售途中,不能调价");
        }
    }

    public int onPriceChange2(User user, StorageRelationBean relation)
    {
        // 指定情况下的仓区位置(仓区、产品、价格、公共)
        // 一个是销售产品是否存在(还没有结束的)
        Integer count1 = outDAO.sumNotEndProductInOut2(relation, YYTools.getFinanceBeginDate(),
            YYTools.getFinanceEndDate());

        // 是否存在调拨途中的产品(修改后在途的产品直接是异动库存,但是状态在3)
        Integer count2 = outDAO.sumNotEndProductInIn2(relation, YYTools.getFinanceBeginDate(),
            YYTools.getFinanceEndDate());

        int result = count1 + count2;

        if (result < 0)
        {
            return 0;
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "PriceChangeListener.SailImpl";
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

    public int onPriceChange3(User user, StorageRelationBean relation)
    {
        // 是否存在调拨途中的产品(修改后在途的产品直接是异动库存,但是状态在3)
        Integer sum = -outDAO.sumInwayProductInBuy(relation, YYTools.getFinanceBeginDate(), YYTools
            .getFinanceEndDate());

        return sum;
    }

    public void onConfirmPriceChange(User user, PriceChangeBean bean)
        throws MYException
    {

    }

    public void onRollbackPriceChange(User user, PriceChangeBean bean)
        throws MYException
    {

    }
}
