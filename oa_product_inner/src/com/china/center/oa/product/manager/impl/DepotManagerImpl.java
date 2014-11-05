/**
 * File Name: DepotManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-22<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.manager.impl;


import java.util.Collection;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.expression.Expression;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.bean.DepotpartBean;
import com.china.center.oa.product.bean.StorageBean;
import com.china.center.oa.product.constant.DepotConstant;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.product.dao.DepotpartDAO;
import com.china.center.oa.product.dao.StorageDAO;
import com.china.center.oa.product.listener.DepotListener;
import com.china.center.oa.product.manager.DepotManager;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.tools.JudgeTools;


/**
 * DepotManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-22
 * @see DepotManagerImpl
 * @since 1.0
 */
@Exceptional
public class DepotManagerImpl extends AbstractListenerManager<DepotListener> implements DepotManager
{
    private DepotDAO depotDAO = null;

    private CommonDAO commonDAO = null;

    private DepotpartDAO depotpartDAO = null;

    private StorageDAO storageDAO = null;

    /**
     * default constructor
     */
    public DepotManagerImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.manager.DepotManager#addDepotBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.product.bean.DepotBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addDepot(User user, DepotBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        bean.setId(commonDAO.getSquenceString20());

        Expression exp = new Expression(bean, this);

        exp.check("#name &unique @depotDAO", "仓库名称已经存在");

        depotDAO.saveEntityBean(bean);

        addDefaultBean(bean);

        return true;
    }

    /**
     * 这里自动生成默认仓区和默认储位
     * 
     * @param bean
     */
    private void addDefaultBean(DepotBean bean)
    {
        DepotpartBean dpart = new DepotpartBean();

        dpart.setId(commonDAO.getSquenceString20());
        dpart.setType(DepotConstant.DEPOTPART_TYPE_OK);
        dpart.setLocationId(bean.getId());
        dpart.setName(bean.getName() + "_默认仓区");
        dpart.setDescription(bean.getName() + "的默认仓区");

        depotpartDAO.saveEntityBean(dpart);

        // 默认储位
        StorageBean sbean = new StorageBean();
        sbean.setId(commonDAO.getSquenceString20());
        sbean.setDepotpartId(dpart.getId());
        sbean.setLocationId(bean.getId());
        sbean.setName(bean.getName() + "_默认储位");
        sbean.setDescription("默认储位");

        storageDAO.saveEntityBean(sbean);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.manager.DepotManager#deleteDepotBean(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean deleteDepot(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        DepotBean old = depotDAO.find(id);

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (old.getId().equals(DepotConstant.STOCK_DEPOT_ID))
        {
            throw new MYException("初始化数据不能删除,请确认操作");
        }

        // 是否存在仓区
        if (depotpartDAO.countByFK(id) > 0)
        {
            throw new MYException("仓库下存在仓区不能删除");
        }

        Collection<DepotListener> values = this.listenerMapValues();

        for (DepotListener depotListener : values)
        {
            // 主要是仓区
            depotListener.onDeleteDepot(user, old);
        }

        depotDAO.deleteEntityBean(id);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.manager.DepotManager#updateDepotBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.product.bean.DepotBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean updateDepot(User user, DepotBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        DepotBean old = depotDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        Expression exp = new Expression(bean, this);

        exp.check("#name &unique2 @depotDAO", "仓库名称已经存在");

        depotDAO.updateEntityBean(bean);

        return true;
    }

    /**
     * @return the depotDAO
     */
    public DepotDAO getDepotDAO()
    {
        return depotDAO;
    }

    /**
     * @param depotDAO
     *            the depotDAO to set
     */
    public void setDepotDAO(DepotDAO depotDAO)
    {
        this.depotDAO = depotDAO;
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
     * @return the depotpartDAO
     */
    public DepotpartDAO getDepotpartDAO()
    {
        return depotpartDAO;
    }

    /**
     * @param depotpartDAO
     *            the depotpartDAO to set
     */
    public void setDepotpartDAO(DepotpartDAO depotpartDAO)
    {
        this.depotpartDAO = depotpartDAO;
    }

    /**
     * @return the storageDAO
     */
    public StorageDAO getStorageDAO()
    {
        return storageDAO;
    }

    /**
     * @param storageDAO
     *            the storageDAO to set
     */
    public void setStorageDAO(StorageDAO storageDAO)
    {
        this.storageDAO = storageDAO;
    }

}
