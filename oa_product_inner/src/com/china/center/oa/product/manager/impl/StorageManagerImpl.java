/**
 * File Name: StorageManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-22<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.manager.impl;


import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.expression.Expression;
import com.china.center.oa.product.bean.StorageBean;
import com.china.center.oa.product.constant.DepotConstant;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.product.dao.ProductChangeRecordDAO;
import com.china.center.oa.product.dao.StorageDAO;
import com.china.center.oa.product.dao.StorageRelationDAO;
import com.china.center.oa.product.manager.StorageManager;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.tools.JudgeTools;


/**
 * StorageManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-22
 * @see StorageManagerImpl
 * @since 1.0
 */
@Exceptional
public class StorageManagerImpl implements StorageManager
{
    private StorageDAO storageDAO = null;

    private StorageRelationDAO storageRelationDAO = null;
    
    private ProductChangeRecordDAO productChangeRecordDAO = null;

    private CommonDAO commonDAO = null;

    private DepotDAO depotDAO = null;

    /**
     * default constructor
     */
    public StorageManagerImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.manager.StorageManager#addBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.product.bean.StorageBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addBean(User user, StorageBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        bean.setId(commonDAO.getSquenceString20());

        Expression exp = new Expression(bean, this);

        exp.check("#name && #depotpartId &unique @storageDAO", "储位名称已经存在");

        storageDAO.saveEntityBean(bean);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.manager.StorageManager#deleteBean(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean deleteBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        StorageBean old = storageDAO.find(id);

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (old.getId().equals(DepotConstant.STOCK_STORAGE_ID))
        {
            throw new MYException("初始化数据不能删除,请确认操作");
        }

        // 储位里面有产品是不能删除的(这里是统计的产品的合计数量)
        if (storageRelationDAO.sumAllProductInStorage(id) != 0)
        {
            throw new MYException("储位下仍有产品不能删除,请确认操作");
        }

        storageDAO.deleteEntityBean(id);

        storageRelationDAO.deleteEntityBeansByFK(id);

        return true;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.manager.StorageManager#deleteBean(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean deleteMoveProduct(String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(id);

        productChangeRecordDAO.deleteEntityBean(id);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.manager.StorageManager#updateBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.product.bean.StorageBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean updateBean(User user, StorageBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        StorageBean old = storageDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        Expression exp = new Expression(bean, this);

        exp.check("#name && #depotpartId &unique2 @storageDAO", "储位名称已经存在");

        bean.setDepotpartId(old.getDepotpartId());

        bean.setLocationId(old.getLocationId());

        storageDAO.updateEntityBean(bean);

        return true;
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

	public ProductChangeRecordDAO getProductChangeRecordDAO() {
		return productChangeRecordDAO;
	}

	public void setProductChangeRecordDAO(
			ProductChangeRecordDAO productChangeRecordDAO) {
		this.productChangeRecordDAO = productChangeRecordDAO;
	}
    
    

}
