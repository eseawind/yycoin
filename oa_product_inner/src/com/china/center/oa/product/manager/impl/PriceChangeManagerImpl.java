/**
 * File Name: PriceChangeManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-4<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.manager.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.PriceChangeBean;
import com.china.center.oa.product.bean.PriceChangeNewItemBean;
import com.china.center.oa.product.bean.PriceChangeSrcItemBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.constant.PriceChangeConstant;
import com.china.center.oa.product.constant.StorageConstant;
import com.china.center.oa.product.dao.PriceChangeDAO;
import com.china.center.oa.product.dao.PriceChangeNewItemDAO;
import com.china.center.oa.product.dao.PriceChangeSrcItemDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.dao.StorageRelationDAO;
import com.china.center.oa.product.listener.PriceChangeListener;
import com.china.center.oa.product.manager.PriceChangeManager;
import com.china.center.oa.product.manager.StorageRelationManager;
import com.china.center.oa.product.vo.PriceChangeVO;
import com.china.center.oa.product.vs.StorageRelationBean;
import com.china.center.oa.product.wrap.ProductChangeWrap;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.helper.OATools;
import com.china.center.tools.CommonTools;
import com.china.center.tools.JudgeTools;


/**
 * PriceChangeManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-4
 * @see PriceChangeManagerImpl
 * @since 1.0
 */
@Exceptional
public class PriceChangeManagerImpl extends AbstractListenerManager<PriceChangeListener> implements PriceChangeManager
{
    private StorageRelationManager storageRelationManager = null;

    private CommonDAO commonDAO = null;

    private ProductDAO productDAO = null;

    private PriceChangeDAO priceChangeDAO = null;

    private StorageRelationDAO storageRelationDAO = null;

    private PriceChangeNewItemDAO priceChangeNewItemDAO = null;

    private PriceChangeSrcItemDAO priceChangeSrcItemDAO = null;

    /**
     * default constructor
     */
    public PriceChangeManagerImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.manager.PriceChangeManager#addPriceChange(com.center.china.osgi.publics.User,
     *      com.china.center.oa.product.bean.PriceChangeBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addPriceChange(User user, PriceChangeBean bean)
        throws MYException
    {
        try
        {
            JudgeTools.judgeParameterIsNull(user, bean);

            bean.setId(commonDAO.getSquenceString20());

            bean.setStatus(PriceChangeConstant.STATUS_COMMON);

            storageRelationManager.unlockStorageRelation();

            processStorageRelation(user, bean);

            saveInner(bean);

            // TAX_ADD 生成调价凭证
            Collection<PriceChangeListener> listenerList = this.listenerMapValues();

            for (PriceChangeListener priceChangeListener : listenerList)
            {
                priceChangeListener.onConfirmPriceChange(user, bean);
            }
        }
        finally
        {
            // 最后需要重新锁定
            storageRelationManager.lockStorageRelation();
        }

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean rollbackPriceChange(User user, String id)
        throws MYException
    {
        try
        {
            JudgeTools.judgeParameterIsNull(user, id);

            PriceChangeBean bean = priceChangeDAO.find(id);

            if (bean == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            if (bean.getStatus() == PriceChangeConstant.STATUS_ROLLBACK)
            {
                throw new MYException("调价已经回滚,请确认操作");
            }

            bean.setSrcList(priceChangeSrcItemDAO.queryEntityBeansByFK(id));

            bean.setNewList(priceChangeNewItemDAO.queryEntityBeansByFK(id));

            List<PriceChangeNewItemBean> newList = bean.getNewList();

            for (PriceChangeNewItemBean each : newList)
            {
                StorageRelationBean srb = storageRelationDAO.find(each.getRelationId());

                if (srb == null || srb.getAmount() != each.getAmount())
                {
                    throw new MYException("库存已经被操作无法回滚,请确认");
                }
            }

            storageRelationManager.unlockStorageRelation();

            rollback(user, bean, newList);

            // 设置成回滚
            priceChangeDAO.updateStatus(id, PriceChangeConstant.STATUS_ROLLBACK);

            // TAX_ADD 生成调价回滚凭证
            Collection<PriceChangeListener> listenerList = this.listenerMapValues();

            for (PriceChangeListener priceChangeListener : listenerList)
            {
                priceChangeListener.onRollbackPriceChange(user, bean);
            }
        }
        finally
        {
            // 最后需要重新锁定
            storageRelationManager.lockStorageRelation();
        }

        return true;
    }

    /**
     * rollback
     * 
     * @param user
     * @param bean
     * @param newList
     * @throws MYException
     */
    private void rollback(User user, PriceChangeBean bean, List<PriceChangeNewItemBean> newList)
        throws MYException
    {
        String sid = commonDAO.getSquenceString();

        // 开始回滚 先删除
        for (PriceChangeNewItemBean each : newList)
        {
            ProductChangeWrap wrap = new ProductChangeWrap();

            // 指定storageRelation
            wrap.setRelationId(each.getRelationId());
            wrap.setStafferId(each.getStafferId());
            wrap.setChange( -each.getAmount());
            wrap.setDepotpartId(each.getDepotpartId());
            wrap.setDescription("产品调价回滚(先清空产品):" + bean.getId());
            wrap.setPrice(each.getPrice());
            wrap.setProductId(each.getProductId());
            wrap.setType(StorageConstant.OPR_DDEPOTPART_CHANGE_ROLLBACK);
            wrap.setSerializeId(sid);
            wrap.setRefId(sid);

            storageRelationManager.changeStorageRelationWithoutTransaction(user, wrap, true);
        }

        List<PriceChangeSrcItemBean> srcList = bean.getSrcList();

        String nsid = commonDAO.getSquenceString();

        // 后增加
        for (PriceChangeSrcItemBean each : srcList)
        {
            ProductChangeWrap eachWrap = new ProductChangeWrap();

            eachWrap.setStafferId(each.getStafferId());
            eachWrap.setDepotpartId(each.getDepotpartId());
            eachWrap.setPrice(each.getPrice());
            eachWrap.setProductId(each.getProductId());
            eachWrap.setChange(each.getAmount());
            eachWrap.setDescription("产品调价回滚(后增加产品):" + bean.getId());
            eachWrap.setType(StorageConstant.OPR_DDEPOTPART_CHANGE_ROLLBACK);
            eachWrap.setSerializeId(sid);
            eachWrap.setRefId(nsid);

            storageRelationManager.changeStorageRelationWithoutTransaction(user, eachWrap, false);
        }
    }

    public PriceChangeVO findById(String id)
    {
        PriceChangeVO vo = priceChangeDAO.findVO(id);

        vo.setSrcVOList(priceChangeSrcItemDAO.queryEntityVOsByFK(id));
        vo.setNewVOList(priceChangeNewItemDAO.queryEntityVOsByFK(id));

        return vo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.manager.PriceChangeManager#onPriceChange(com.center.china.osgi.publics.User,
     *      com.china.center.oa.product.bean.ProductBean)
     */
    public boolean onPriceChange(User user, ProductBean bean)
    {
        Collection<PriceChangeListener> listenerList = this.listenerMapValues();

        for (PriceChangeListener priceChangeListener : listenerList)
        {
            try
            {
                priceChangeListener.onPriceChange(user, bean);
            }
            catch (MYException e)
            {
                return false;
            }
        }

        return true;
    }

    public int onPriceChange2(User user, StorageRelationBean bean)
    {
        Collection<PriceChangeListener> listenerList = this.listenerMapValues();

        int total = 0;

        for (PriceChangeListener priceChangeListener : listenerList)
        {
            total += priceChangeListener.onPriceChange2(user, bean);
        }

        return total;
    }

    /**
     * processStorageRelation
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    private void processStorageRelation(User user, PriceChangeBean bean)
        throws MYException
    {
        String sid = commonDAO.getSquenceString();

        // 修改库存(先删除后增加)
        List<PriceChangeSrcItemBean> srcList = bean.getSrcList();

        List<PriceChangeNewItemBean> newList = bean.getNewList();

        int mtype = -1;

        for (PriceChangeSrcItemBean each : srcList)
        {
            ProductChangeWrap wrap = new ProductChangeWrap();

            // 指定storageRelation
            wrap.setRelationId(each.getRelationId());
            wrap.setStafferId(each.getStafferId());
            wrap.setChange( -each.getAmount());
            wrap.setDepotpartId(each.getDepotpartId());
            wrap.setDescription("产品调价(先清空产品):" + bean.getId());
            wrap.setPrice(each.getPrice());
            wrap.setProductId(each.getProductId());
            wrap.setType(StorageConstant.OPR_DDEPOTPART_CHANGE);
            wrap.setSerializeId(sid);
            wrap.setRefId(commonDAO.getSquenceString());

            if (OATools.getManagerFlag())
            {
                ProductBean product = productDAO.find(each.getProductId());

                if (mtype == -1)
                {
                    mtype = CommonTools.parseInt(product.getReserve4());
                }
                else
                {
                    if (mtype != CommonTools.parseInt(product.getReserve4()))
                    {
                        throw new MYException("产品[" + product.getCode() + "]管理属性不匹配");
                    }
                }
            }

            storageRelationManager.changeStorageRelationWithoutTransaction(user, wrap, true);

            // 再增加到库存内
            List<PriceChangeNewItemBean> ref = getRef(each.getRefId(), newList);

            int total = 0;

            for (PriceChangeNewItemBean newItemBean : ref)
            {
                ProductChangeWrap eachWrap = new ProductChangeWrap();

                eachWrap.setStafferId(newItemBean.getStafferId());
                eachWrap.setChange(newItemBean.getAmount());
                eachWrap.setDepotpartId(newItemBean.getDepotpartId());

                // 这里新的产品需要指定储位
                eachWrap.setStorageId(newItemBean.getStorageId());
                eachWrap.setDescription("产品调价(后增加产品):" + bean.getId());
                eachWrap.setPrice(newItemBean.getPrice());
                eachWrap.setProductId(newItemBean.getProductId());
                eachWrap.setType(StorageConstant.OPR_DDEPOTPART_CHANGE);
                eachWrap.setSerializeId(sid);
                eachWrap.setRefId(commonDAO.getSquenceString());

                total += newItemBean.getAmount();

                StorageRelationBean newSrb = storageRelationManager
                    .changeStorageRelationWithoutTransaction(user, eachWrap, false);

                // 把新的关系放到历史表里面,提供回滚依据
                newItemBean.setRelationId(newSrb.getId());
                newItemBean.setStorageId(newSrb.getStorageId());
            }

            if (each.getAmount() != total)
            {
                ProductBean product = productDAO.find(each.getProductId());

                if (product == null)
                {
                    throw new MYException("数据错误,请确认操作");
                }

                throw new MYException("调价后产品[%s]数量不匹配,请确认操作", product.getName());
            }
        }

        if (mtype != -1)
        {
            bean.setMtype(mtype);
        }
    }

    /**
     * getRef
     * 
     * @param refId
     * @param newList
     * @return
     */
    private List<PriceChangeNewItemBean> getRef(String refId, List<PriceChangeNewItemBean> newList)
    {
        List<PriceChangeNewItemBean> result = new ArrayList<PriceChangeNewItemBean>();

        for (PriceChangeNewItemBean each : newList)
        {
            if (each.getRefId().equals(refId))
            {
                result.add(each);
            }
        }

        return result;
    }

    /**
     * saveInner
     * 
     * @param bean
     */
    private void saveInner(PriceChangeBean bean)
    {
        priceChangeDAO.saveEntityBean(bean);

        List<PriceChangeSrcItemBean> srcList = bean.getSrcList();

        for (PriceChangeSrcItemBean priceChangeSrcItemBean : srcList)
        {
            priceChangeSrcItemBean.setParentId(bean.getId());
        }

        priceChangeSrcItemDAO.saveAllEntityBeans(srcList);

        List<PriceChangeNewItemBean> newList = bean.getNewList();

        for (PriceChangeNewItemBean priceChangeNewItemBean : newList)
        {
            priceChangeNewItemBean.setParentId(bean.getId());
        }

        priceChangeNewItemDAO.saveAllEntityBeans(newList);
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
     * @return the commonDAO
     */
    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    /**
     * @param commonDAO
     *            the commonDAO to set
     */
    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
    }

    /**
     * @return the priceChangeDAO
     */
    public PriceChangeDAO getPriceChangeDAO()
    {
        return priceChangeDAO;
    }

    /**
     * @param priceChangeDAO
     *            the priceChangeDAO to set
     */
    public void setPriceChangeDAO(PriceChangeDAO priceChangeDAO)
    {
        this.priceChangeDAO = priceChangeDAO;
    }

    /**
     * @return the priceChangeNewItemDAO
     */
    public PriceChangeNewItemDAO getPriceChangeNewItemDAO()
    {
        return priceChangeNewItemDAO;
    }

    /**
     * @param priceChangeNewItemDAO
     *            the priceChangeNewItemDAO to set
     */
    public void setPriceChangeNewItemDAO(PriceChangeNewItemDAO priceChangeNewItemDAO)
    {
        this.priceChangeNewItemDAO = priceChangeNewItemDAO;
    }

    /**
     * @return the priceChangeSrcItemDAO
     */
    public PriceChangeSrcItemDAO getPriceChangeSrcItemDAO()
    {
        return priceChangeSrcItemDAO;
    }

    /**
     * @param priceChangeSrcItemDAO
     *            the priceChangeSrcItemDAO to set
     */
    public void setPriceChangeSrcItemDAO(PriceChangeSrcItemDAO priceChangeSrcItemDAO)
    {
        this.priceChangeSrcItemDAO = priceChangeSrcItemDAO;
    }

    /**
     * @return the storageRelationDAO
     */
    public StorageRelationDAO getStorageRelationDAO()
    {
        return storageRelationDAO;
    }

    /**
     * @param storageRelationDAO
     *            the storageRelationDAO to set
     */
    public void setStorageRelationDAO(StorageRelationDAO storageRelationDAO)
    {
        this.storageRelationDAO = storageRelationDAO;
    }

    /**
     * @return the productDAO
     */
    public ProductDAO getProductDAO()
    {
        return productDAO;
    }

    /**
     * @param productDAO
     *            the productDAO to set
     */
    public void setProductDAO(ProductDAO productDAO)
    {
        this.productDAO = productDAO;
    }
}
