/**
 * File Name: EnumManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.ex.annotation.Exceptional;
import org.hibernate.transaction.TransactionManagerLookup;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.common.taglib.MapBean;
import com.china.center.common.taglib.PageSelectOption;
import com.china.center.jdbc.expression.Expression;
import com.china.center.oa.publics.bean.EnumBean;
import com.china.center.oa.publics.bean.EnumDefineBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.EnumDAO;
import com.china.center.oa.publics.dao.EnumDefineDAO;
import com.china.center.oa.publics.manager.EnumManager;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.StringTools;


/**
 * EnumManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see EnumManagerImpl
 * @since 1.0
 */
@Exceptional
public class EnumManagerImpl implements EnumManager
{
    private final Log _logger = LogFactory.getLog(getClass());

    private EnumDAO enumDAO = null;

    private EnumDefineDAO enumDefineDAO = null;

    private CommonDAO commonDAO = null;

    /**
     * 准备使用hibernate
     */
    private TransactionManagerLookup transactionManagerLookup = null;

    private PlatformTransactionManager transactionManager = null;

    /**
     * default constructor
     */
    public EnumManagerImpl()
    {
    }

    /**
     * 加载标签资源
     */
    public void init()
    {
        List<EnumBean> list = enumDAO.listEntityBeans();

        for (EnumBean enumBean : list)
        {
            List<MapBean> iList = PageSelectOption.optionMap
                .get(String.valueOf(enumBean.getType()));

            if (iList != null)
            {
                // 先清空
                iList.clear();
            }
        }

        for (EnumBean enumBean : list)
        {
            List<MapBean> iList = PageSelectOption.optionMap
                .get(String.valueOf(enumBean.getType()));

            if (iList == null)
            {
                iList = new ArrayList<MapBean>();

                PageSelectOption.optionMap.put(String.valueOf(enumBean.getType()), iList);
            }

            iList.add(new MapBean(enumBean.getKey(), enumBean.getValue()));
        }
    }

    public boolean addBean(User user, final EnumBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        Expression exp = new Expression(bean, this);

        exp.check("#type && #value &unique @enumDAO", "配置项已经存在");

        bean.setKey(commonDAO.getSquenceString());

        bean.setStatus(PublicConstant.ENUM_ADD);

        try
        {
            // TEMPLATE 事务处理
            TransactionTemplate tran = new TransactionTemplate(transactionManager);

            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    enumDAO.saveEntityBean(bean);

                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error(e, e);

            throw new MYException("数据库内部错误");
        }
        catch (DataAccessException e)
        {
            _logger.error(e, e);

            throw new MYException("数据库内部错误");
        }

        init();

        return true;
    }

    public boolean deleteBean(User user, final String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        checkDelete(id);

        try
        {
            // 增加管理员操作在数据库事务中完成
            TransactionTemplate tran = new TransactionTemplate(transactionManager);

            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    enumDAO.deleteEntityBean(id);

                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error(e, e);

            throw new MYException("数据库内部错误");
        }
        catch (DataAccessException e)
        {
            _logger.error(e, e);

            throw new MYException("数据库内部错误");
        }

        init();

        return false;
    }

    private void checkDelete(final String id)
        throws MYException
    {
        final EnumBean old = enumDAO.find(id);

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (old.getStatus() == PublicConstant.ENUM_INIT)
        {
            throw new MYException("初始化配置不能删除");
        }

        // 查看是否被REF
        EnumDefineBean define = enumDefineDAO.findByUnique(old.getType());

        if (define == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        String ref = define.getRef();

        if ( !StringTools.isNullOrNone(ref))
        {
            // Table.column
            String[] splits = ref.split(";");

            for (String each : splits)
            {
                if (StringTools.isNullOrNone(each))
                {
                    continue;
                }

                String[] subSplits = each.split("\\.");

                if (subSplits.length != 2)
                {
                    continue;
                }

                int count = enumDefineDAO.countRef(subSplits[0].trim(), subSplits[1].trim(), old
                    .getKey());

                if (count > 0)
                {
                    throw new MYException("配置被使用,无法删除");
                }

            }
        }
    }

    public boolean updateBean(User user, final EnumBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        final EnumBean old = enumDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        old.setValue(bean.getValue());

        Expression exp = new Expression(old, this);

        exp.check("#type && #value &unique2 @enumDAO", "配置项已经存在");

        try
        {
            // 增加管理员操作在数据库事务中完成
            TransactionTemplate tran = new TransactionTemplate(transactionManager);

            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    enumDAO.updateEntityBean(old);

                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error(e, e);

            throw new MYException("数据库内部错误");
        }
        catch (DataAccessException e)
        {
            _logger.error(e, e);

            throw new MYException("数据库内部错误");
        }

        init();

        return true;
    }

    /**
     * @return the enumDAO
     */
    public EnumDAO getEnumDAO()
    {
        return enumDAO;
    }

    /**
     * @param enumDAO
     *            the enumDAO to set
     */
    public void setEnumDAO(EnumDAO enumDAO)
    {
        this.enumDAO = enumDAO;
    }

    /**
     * @return the transactionManager
     */
    public PlatformTransactionManager getTransactionManager()
    {
        return transactionManager;
    }

    /**
     * @param transactionManager
     *            the transactionManager to set
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager)
    {
        this.transactionManager = transactionManager;
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
     * @return the enumDefineDAO
     */
    public EnumDefineDAO getEnumDefineDAO()
    {
        return enumDefineDAO;
    }

    /**
     * @param enumDefineDAO
     *            the enumDefineDAO to set
     */
    public void setEnumDefineDAO(EnumDefineDAO enumDefineDAO)
    {
        this.enumDefineDAO = enumDefineDAO;
    }

    /**
     * @return the transactionManagerLookup
     */
    public TransactionManagerLookup getTransactionManagerLookup()
    {
        return transactionManagerLookup;
    }

    /**
     * @param transactionManagerLookup
     *            the transactionManagerLookup to set
     */
    public void setTransactionManagerLookup(TransactionManagerLookup transactionManagerLookup)
    {
        this.transactionManagerLookup = transactionManagerLookup;
    }
}
