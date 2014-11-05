/**
 * File Name: CreditItemManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.manager.impl;


import java.io.Serializable;
import java.util.List;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.credit.bean.CreditItemBean;
import com.china.center.oa.credit.bean.CreditItemSecBean;
import com.china.center.oa.credit.bean.CreditItemThrBean;
import com.china.center.oa.credit.bean.CreditLevelBean;
import com.china.center.oa.credit.dao.CreditItemDAO;
import com.china.center.oa.credit.dao.CreditItemSecDAO;
import com.china.center.oa.credit.dao.CreditItemThrDAO;
import com.china.center.oa.credit.dao.CreditLevelDAO;
import com.china.center.oa.credit.dao.CustomerCreditDAO;
import com.china.center.oa.credit.manager.CreditItemManager;
import com.china.center.oa.customer.constant.CreditConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.tools.JudgeTools;


/**
 * CreditItemManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CreditItemManagerImpl
 * @since 1.0
 */
@Exceptional
public class CreditItemManagerImpl implements CreditItemManager
{
    private CreditItemDAO creditItemDAO = null;

    private CommonDAO commonDAO = null;

    private CreditItemSecDAO creditItemSecDAO = null;

    private CreditItemThrDAO creditItemThrDAO = null;

    private CustomerCreditDAO customerCreditDAO = null;

    private CreditLevelDAO creditLevelDAO = null;

    /**
     * default constructor
     */
    public CreditItemManagerImpl()
    {
    }

    /**
     * updateCreditItemSec
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateCreditItemSec(User user, CreditItemSecBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        CreditItemSecBean old = checkUpdate(bean);

        bean.setPid(old.getPid());

        creditItemSecDAO.updateEntityBean(bean);

        return true;
    }

    /**
     * updateCreditLevel
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateCreditLevel(User user, CreditLevelBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        creditLevelDAO.updateEntityBean(bean);

        return true;
    }

    /**
     * updateCreditItem
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateCreditItem(User user, CreditItemBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        CreditItemBean old = checkUpdateCreditItem(bean);

        bean.setName(old.getName());

        creditItemDAO.updateEntityBean(bean);

        return true;
    }

    /**
     * addCreditItemThr
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean addCreditItemThr(User user, CreditItemThrBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkAddThr(bean);

        bean.setId(commonDAO.getSquenceString20());

        bean.setName(bean.getName().trim());

        creditItemThrDAO.saveEntityBean(bean);

        return true;
    }

    /**
     * updateCreditItemThr
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateCreditItemThr(User user, CreditItemThrBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        CreditItemThrBean old = checkUpdateThr(bean);

        bean.setPid(old.getPid());

        bean.setUnit(old.getUnit());

        creditItemThrDAO.updateEntityBean(bean);

        return true;
    }

    /**
     * deleteCreditItemThr
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean deleteCreditItemThr(User user, Serializable id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        checkDelThr(id);

        creditItemThrDAO.deleteEntityBean(id);

        return true;
    }

    /**
     * checkDelThr
     * 
     * @param id
     * @throws MYException
     */
    private void checkDelThr(Serializable id)
        throws MYException
    {
        if (customerCreditDAO.countByValueId(id) > 0)
        {
            throw new MYException("此指标项已经被使用,不能删除");
        }
    }

    /**
     * checkUpdateThr
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    private CreditItemThrBean checkUpdateThr(CreditItemThrBean bean)
        throws MYException
    {
        CreditItemThrBean old = creditItemThrDAO.find(bean.getId());

        if ( !old.getName().equals(bean.getName()))
        {
            if (creditItemThrDAO.countByUnique(bean.getName(), bean.getPid()) > 0)
            {
                throw new MYException("%s已经存在", bean.getName());
            }
        }

        if (old.getIndexPos() != bean.getIndexPos())
        {
            if (creditItemThrDAO.countByPidAndIndexPos(bean.getPid(), bean.getIndexPos()) > 0)
            {
                throw new MYException("%s索引位置重复", bean.getName());
            }
        }

        // count per
        if (bean.getPer() > 100.0d)
        {
            throw new MYException("得分不能超过100");
        }

        return old;
    }

    /**
     * checkAddThr
     * 
     * @param bean
     * @throws MYException
     */
    private void checkAddThr(CreditItemThrBean bean)
        throws MYException
    {
        if (creditItemThrDAO.countByUnique(bean.getName(), bean.getPid()) > 0)
        {
            throw new MYException("%s已经存在", bean.getName());
        }

        if (creditItemThrDAO.countByPidAndIndexPos(bean.getPid(), bean.getIndexPos()) > 0)
        {
            throw new MYException("%s索引位置重复", bean.getName());
        }

        if (bean.getPer() > 100.0d)
        {
            throw new MYException("得分不能超过100");
        }

        // process unit
        CreditItemSecBean parent = creditItemSecDAO.find(bean.getPid());

        if (parent == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        bean.setUnit(parent.getUnit());
    }

    /**
     * checkUpdate
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    private CreditItemSecBean checkUpdate(CreditItemSecBean bean)
        throws MYException
    {
        CreditItemSecBean old = creditItemSecDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !old.getName().equals(bean.getName()))
        {
            if (creditItemSecDAO.countByUnique(bean.getName()) > 0)
            {
                throw new MYException("%s已经存在", bean.getName());
            }
        }

        // percent must check amount
        if (old.getType() == CreditConstant.CREDIT_ITEM_TYPE_PERCENT)
        {
            // count per
            List<CreditItemSecBean> list = creditItemSecDAO.queryEntityBeansByFK(bean.getPid());

            double count = 0.0d;

            for (CreditItemSecBean creditItemSecBean : list)
            {
                if (creditItemSecBean.getId().equals(bean.getId()))
                {
                    count += bean.getPer();
                }
                else
                {
                    count += creditItemSecBean.getPer();
                }
            }

            if (count > 100.0d)
            {
                throw new MYException("百分比之和超过100");
            }
        }

        return old;
    }

    /**
     * checkUpdateCreditItem
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    private CreditItemBean checkUpdateCreditItem(CreditItemBean bean)
        throws MYException
    {
        CreditItemBean old = creditItemDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        List<CreditItemBean> list = creditItemDAO.listEntityBeans();

        double count = 0.0d;

        for (CreditItemBean creditItemSecBean : list)
        {
            if (creditItemSecBean.getId().equals(bean.getId()))
            {
                count += bean.getPer();
            }
            else
            {
                count += creditItemSecBean.getPer();
            }
        }

        if (count > 100.0d)
        {
            throw new MYException("百分比之和超过100");
        }

        return old;
    }

    /**
     * @return the creditItemDAO
     */
    public CreditItemDAO getCreditItemDAO()
    {
        return creditItemDAO;
    }

    /**
     * @param creditItemDAO
     *            the creditItemDAO to set
     */
    public void setCreditItemDAO(CreditItemDAO creditItemDAO)
    {
        this.creditItemDAO = creditItemDAO;
    }

    /**
     * @return the creditItemSecDAO
     */
    public CreditItemSecDAO getCreditItemSecDAO()
    {
        return creditItemSecDAO;
    }

    /**
     * @param creditItemSecDAO
     *            the creditItemSecDAO to set
     */
    public void setCreditItemSecDAO(CreditItemSecDAO creditItemSecDAO)
    {
        this.creditItemSecDAO = creditItemSecDAO;
    }

    /**
     * @return the creditItemThrDAO
     */
    public CreditItemThrDAO getCreditItemThrDAO()
    {
        return creditItemThrDAO;
    }

    /**
     * @param creditItemThrDAO
     *            the creditItemThrDAO to set
     */
    public void setCreditItemThrDAO(CreditItemThrDAO creditItemThrDAO)
    {
        this.creditItemThrDAO = creditItemThrDAO;
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
     * @return the creditLevelDAO
     */
    public CreditLevelDAO getCreditLevelDAO()
    {
        return creditLevelDAO;
    }

    /**
     * @param creditLevelDAO
     *            the creditLevelDAO to set
     */
    public void setCreditLevelDAO(CreditLevelDAO creditLevelDAO)
    {
        this.creditLevelDAO = creditLevelDAO;
    }

    /**
     * @return the customerCreditDAO
     */
    public CustomerCreditDAO getCustomerCreditDAO()
    {
        return customerCreditDAO;
    }

    /**
     * @param customerCreditDAO
     *            the customerCreditDAO to set
     */
    public void setCustomerCreditDAO(CustomerCreditDAO customerCreditDAO)
    {
        this.customerCreditDAO = customerCreditDAO;
    }
}
