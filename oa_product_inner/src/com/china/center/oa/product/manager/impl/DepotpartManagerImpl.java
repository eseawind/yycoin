/**
 * File Name: DepotpartManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-22<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.manager.impl;


import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.expression.Expression;
import com.china.center.oa.product.bean.DepotpartBean;
import com.china.center.oa.product.constant.DepotConstant;
import com.china.center.oa.product.dao.DepotpartDAO;
import com.china.center.oa.product.dao.StorageDAO;
import com.china.center.oa.product.manager.DepotpartManager;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.tools.JudgeTools;


/**
 * DepotpartManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-22
 * @see DepotpartManagerImpl
 * @since 1.0
 */
public class DepotpartManagerImpl implements DepotpartManager
{
    private DepotpartDAO depotpartDAO = null;

    private StorageDAO storageDAO = null;

    private CommonDAO commonDAO = null;

    /**
     * default constructor
     */
    public DepotpartManagerImpl()
    {
    }

    /**
     * Description:
     * 
     * @param bean
     * @return
     * @throws MYException
     * @since <IVersion>
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addBean(User user, DepotpartBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        bean.setId(commonDAO.getSquenceString20());

        Expression exp = new Expression(bean, this);

        exp.check("#name && #locationId &unique @depotpartDAO", "仓区名称已经存在");

        depotpartDAO.saveEntityBean(bean);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.manager.DepotpartManager#updateBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.product.bean.DepotpartBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean updateBean(User user, DepotpartBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        DepotpartBean old = depotpartDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        bean.setType(old.getType());

        bean.setLocationId(old.getLocationId());

        Expression exp = new Expression(bean, this);

        exp.check("#name && #locationId &unique2 @depotpartDAO", "仓区名称已经存在");

        depotpartDAO.updateEntityBean(bean);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.manager.DepotpartManager#deleteBean(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean deleteBean(User user, final String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(id);

        DepotpartBean old = depotpartDAO.find(id);

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (old.getId().equals(DepotConstant.STOCK_DEPOTPART_ID))
        {
            throw new MYException("初始化数据不能删除,请确认操作");
        }

        if (storageDAO.countByFK(id) > 0)
        {
            throw new MYException(DepotpartBean.class, "仍有储位，不能删除!");
        }

        depotpartDAO.deleteEntityBean(id);

        return true;
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
     * @return 返回 storageDAO
     */
    public StorageDAO getStorageDAO()
    {
        return storageDAO;
    }

    /**
     * @param 对storageDAO进行赋值
     */
    public void setStorageDAO(StorageDAO storageDAO)
    {
        this.storageDAO = storageDAO;
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
}
