/**
 * File Name: ProviderManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.manager.impl;


import java.util.Collection;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.ProviderBean;
import com.china.center.oa.product.bean.ProviderHisBean;
import com.china.center.oa.product.bean.ProviderUserBean;
import com.china.center.oa.product.constant.ProviderConstant;
import com.china.center.oa.product.dao.ProductTypeVSCustomerDAO;
import com.china.center.oa.product.dao.ProviderDAO;
import com.china.center.oa.product.dao.ProviderHisDAO;
import com.china.center.oa.product.dao.ProviderUserDAO;
import com.china.center.oa.product.listener.ProviderListener;
import com.china.center.oa.product.manager.ProviderManager;
import com.china.center.oa.product.vs.ProductTypeVSCustomer;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.Security;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * ProviderManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-21
 * @see ProviderManagerImpl
 * @since 1.0
 */
@Exceptional
public class ProviderManagerImpl extends AbstractListenerManager<ProviderListener> implements ProviderManager
{
    private ProviderDAO providerDAO = null;

    private ProviderHisDAO providerHisDAO = null;

    private ProviderUserDAO providerUserDAO = null;

    private ProductTypeVSCustomerDAO productTypeVSCustomerDAO = null;

    private CommonDAO commonDAO = null;

    public ProviderManagerImpl()
    {
    }

    /**
     * addBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean addBean(User user, ProviderBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean, bean.getName(), bean.getCode());

        checkAddBean(bean);

        bean.setLogTime(TimeTools.now());

        bean.setId(commonDAO.getSquenceString());

        providerDAO.saveEntityBean(bean);

        addHis(bean);

        return true;
    }

    /**
     * bingProductTypeToCustmer
     * 
     * @param pid
     * @param productTypeIds
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean bingProductTypeToCustmer(User user, String pid, String[] productTypeIds)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, pid, productTypeIds);

        productTypeVSCustomerDAO.delVSByCustomerId(pid);

        for (String item : productTypeIds)
        {
            ProductTypeVSCustomer bean = new ProductTypeVSCustomer();

            bean.setCustomerId(pid);

            bean.setProductTypeId(item);

            productTypeVSCustomerDAO.saveEntityBean(bean);
        }

        return true;
    }

    /**
     * addUserBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean addOrUpdateUserBean(User user, ProviderUserBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean, bean.getName());

        boolean isAdd = StringTools.isNullOrNone(bean.getId());

        checkUser(bean, isAdd);

        if (isAdd)
        {
            providerUserDAO.saveEntityBean(bean);
        }
        else
        {
            providerUserDAO.updateEntityBean(bean);
        }

        return true;
    }

    /**
     * updateUserPassword
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateUserPassword(User user, String id, String newpwd)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id, newpwd);

        String md5 = Security.getSecurity(newpwd);

        providerUserDAO.updatePassword(id, md5);

        return true;
    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean updateUserPwkey(User user, String id, String newpwkey)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id, newpwkey);

        providerUserDAO.updatePwkey(id, newpwkey);

        return true;
    }

    /**
     * checkUser
     * 
     * @param bean
     * @param isAdd
     * @throws MYException
     */
    private void checkUser(ProviderUserBean bean, boolean isAdd)
        throws MYException
    {
        if (isAdd)
        {
            if (providerUserDAO.countByUnique(bean.getName()) > 0)
            {
                throw new MYException("名称重复,请确认操作");
            }

            String md5 = Security.getSecurity(bean.getPassword());

            bean.setPassword(md5);

            bean.setLoginTime(TimeTools.now());
        }
        else
        {
            ProviderUserBean old = providerUserDAO.find(bean.getId());

            if (old == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            if ( !old.getName().equals(bean.getName()))
            {
                if (providerUserDAO.countByUnique(bean.getName()) > 0)
                {
                    throw new MYException("名称重复,请确认操作");
                }
            }

            bean.setPassword(old.getPassword());
        }
    }

    /**
     * updateBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateBean(User user, ProviderBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean, bean.getName());

        checkUpdateBean(bean);

        providerDAO.updateEntityBean(bean);

        addHis(bean);

        return true;
    }

    /**
     * delBean
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean delBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        ProviderBean bean = checkDelBean(id);

        // 这里供应商的删除应该是全局监听
        Collection<ProviderListener> listenerMapValues = this.listenerMapValues();

        for (ProviderListener providerListener : listenerMapValues)
        {
            providerListener.onDelete(bean);
        }

        providerDAO.deleteEntityBean(id);

        return true;
    }

    /**
     * checkHisCustomer
     * 
     * @param user
     * @param cid
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean checkHisProvider(User user, String cid)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, cid);

        providerHisDAO.updateCheckStatus(cid, ProviderConstant.HIS_CHECK_YES);

        return true;
    }

    /**
     * 检查add是否符合逻辑
     * 
     * @param bean
     * @throws MYException
     */
    private void checkAddBean(ProviderBean bean)
        throws MYException
    {
        if (providerDAO.countByUnique(bean.getCode()) > 0)
        {
            throw new MYException("供应商CODE重复");
        }

        if (providerDAO.countProviderByName(bean.getName()) > 0)
        {
            throw new MYException("供应商名称重复");
        }
    }

    /**
     * 修改检查 code不能改变的
     * 
     * @param bean
     * @throws MYException
     */
    private void checkUpdateBean(ProviderBean bean)
        throws MYException
    {
        ProviderBean old = providerDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("供应商不存在");
        }

        bean.setCode(old.getCode());

        bean.setLogTime(old.getLogTime());

        if ( !bean.getName().equals(old.getName()))
        {
            if (providerDAO.countProviderByName(bean.getName()) > 0)
            {
                throw new MYException("供应商名称重复");
            }
        }
    }

    private ProviderBean checkDelBean(String id)
        throws MYException
    {
        ProviderBean bean = providerDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        return bean;
    }

    /**
     * 增加历史记录
     * 
     * @param bean
     */
    private void addHis(ProviderBean bean)
    {
        ProviderHisBean his = new ProviderHisBean();

        BeanUtil.copyProperties(his, bean);

        his.setProviderId(bean.getId());

        his.setCheckStatus(ProviderConstant.HIS_CHECK_NO);

        providerHisDAO.saveEntityBean(his);
    }

    /**
     * @return the providerDAO
     */
    public ProviderDAO getProviderDAO()
    {
        return providerDAO;
    }

    /**
     * @param providerDAO
     *            the providerDAO to set
     */
    public void setProviderDAO(ProviderDAO providerDAO)
    {
        this.providerDAO = providerDAO;
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
     * @return the providerHisDAO
     */
    public ProviderHisDAO getProviderHisDAO()
    {
        return providerHisDAO;
    }

    /**
     * @param providerHisDAO
     *            the providerHisDAO to set
     */
    public void setProviderHisDAO(ProviderHisDAO providerHisDAO)
    {
        this.providerHisDAO = providerHisDAO;
    }

    /**
     * @return the providerUserDAO
     */
    public ProviderUserDAO getProviderUserDAO()
    {
        return providerUserDAO;
    }

    /**
     * @param providerUserDAO
     *            the providerUserDAO to set
     */
    public void setProviderUserDAO(ProviderUserDAO providerUserDAO)
    {
        this.providerUserDAO = providerUserDAO;
    }

    /**
     * @return the productTypeVSCustomerDAO
     */
    public ProductTypeVSCustomerDAO getProductTypeVSCustomerDAO()
    {
        return productTypeVSCustomerDAO;
    }

    /**
     * @param productTypeVSCustomerDAO
     *            the productTypeVSCustomerDAO to set
     */
    public void setProductTypeVSCustomerDAO(ProductTypeVSCustomerDAO productTypeVSCustomerDAO)
    {
        this.productTypeVSCustomerDAO = productTypeVSCustomerDAO;
    }
}
