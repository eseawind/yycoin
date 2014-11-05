/**
 * File Name: TaxManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-30<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.manager.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.china.center.spring.iaop.annotation.IntegrationAOP;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.expression.Expression;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.constanst.TaxConstanst;
import com.china.center.oa.tax.dao.FinanceItemDAO;
import com.china.center.oa.tax.dao.TaxDAO;
import com.china.center.oa.tax.manager.TaxManager;
import com.china.center.oa.tax.vo.TaxVO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.StringTools;


/**
 * TaxManagerImpl
 * 
 * @author ZHUZHU
 * @version 2011-1-30
 * @see TaxManagerImpl
 * @since 1.0
 */
@IntegrationAOP
public class TaxManagerImpl implements TaxManager
{
    private TaxDAO taxDAO = null;

    private CommonDAO commonDAO = null;

    private FinanceItemDAO financeItemDAO = null;

    /**
     * default constructor
     */
    public TaxManagerImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.tax.manager.TaxManager#addTaxBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.tax.bean.TaxBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addTaxBean(User user, TaxBean bean)
        throws MYException
    {
        return addTaxBeanWithoutTransactional(user, bean);
    }

    public boolean addTaxBeanWithoutTransactional(User user, TaxBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        // ID和code相同
        bean.setId(bean.getCode());

        // 设置默认值
        if (StringTools.isNullOrNone(bean.getRefId()))
        {
            bean.setRefType(TaxConstanst.TAX_REFTYPE_BANK);
        }
        else
        {
            if (bean.getBottomFlag() == TaxConstanst.TAX_BOTTOMFLAG_ROOT)
            {
                throw new MYException("关联银行的科目必须是最小科目,请确认操作");
            }
        }

        Expression exp = new Expression(bean, this);

        exp.check("#name || #code &unique @taxDAO", "名称或者编码已经存在");

        // 检查银行的关联属性
        if ( !StringTools.isNullOrNone(bean.getRefId()))
        {
            exp.check("#refId && #refType &unique @taxDAO", "银行关联已经存在");
        }

        // 检查父级点
        if (StringTools.isNullOrNone(bean.getParentId()))
        {
            bean.setParentId("0");
        }

        if ("0".equals(bean.getParentId()))
        {
            bean.setLevel(TaxConstanst.TAX_LEVEL_DEFAULT);
        }
        else
        {
            TaxBean parent = taxDAO.find(bean.getParentId());

            if (parent == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            if (parent.getBottomFlag() == TaxConstanst.TAX_BOTTOMFLAG_ITEM)
            {
                throw new MYException("父级科目不能是最小科目,请确认操作");
            }

            // 检查编码是否是父级的开始
            if ( !bean.getCode().startsWith(parent.getCode() + "-"))
            {
                throw new MYException("编码前缀必须是[%s],请确认操作", parent.getCode() + "-");
            }

            // 递归获取各级的ID
            bean.setLevel(parent.getLevel() + 1);

            if (bean.getLevel() > TaxConstanst.TAX_LEVEL_MAX)
            {
                throw new MYException("最多支持9级科目,请确认操作");
            }

            BeanUtil.copyPropertyWithoutException(bean, "parentId" + parent.getLevel(), parent
                .getId());

            int allLevel = parent.getLevel() - 1;

            for (int i = allLevel; i >= 0; i-- )
            {
                parent = taxDAO.find(parent.getParentId());

                if (parent == null)
                {
                    throw new MYException("数据错误,请确认操作");
                }

                BeanUtil.copyPropertyWithoutException(bean, "parentId" + parent.getLevel(), parent
                    .getId());

                if ("0".equals(parent.getParentId()))
                {
                    break;
                }
            }
        }

        int length = bean.getCode().split("-").length - 1;

        if (length != bean.getLevel())
        {
            throw new MYException("编码必须是-分隔,且级数和科目级别对应,请确认操作");
        }

        taxDAO.saveEntityBean(bean);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public void init2()
    {
        List<TaxBean> listEntityBeans = taxDAO.listEntityBeansByOrder("order by code");

        Map<String, TaxBean> map = new HashMap();

        for (TaxBean taxBean : listEntityBeans)
        {
            map.put(taxBean.getCode(), taxBean);
        }

        for (TaxBean taxBean : listEntityBeans)
        {
            String code = taxBean.getCode();

            String[] codes = code.split("-");

            String key = "";

            taxBean.setLevel(codes.length - 1);

            taxBean.setBottomFlag(1);

            if (codes.length == 1)
            {
                taxBean.setParentId("0");
            }
            else
            {
                for (int i = 0; i < codes.length; i++ )
                {
                    if (i < codes.length - 1)
                    {
                        String eachStr = codes[i];

                        key = key + eachStr + "-";

                        String field = "parentId" + i;

                        Map temp = new HashMap();

                        TaxBean tax = map.get(key.substring(0, key.length() - 1));

                        if (tax == null)
                        {
                            System.out.println(key + "/Error");

                            return;
                        }

                        tax.setBottomFlag(0);

                        temp.put(field, tax.getId());

                        BeanUtil.copyProperties(taxBean, temp);

                        // 最后一个
                        if (i == codes.length - 2)
                        {
                            taxBean.setParentId(tax.getId());
                        }
                    }
                }
            }
        }

        taxDAO.updateAllEntityBeans(listEntityBeans);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.tax.manager.TaxManager#deleteTaxBean(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean deleteTaxBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        TaxBean old = taxDAO.find(id);

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !StringTools.isNullOrNone(old.getRefId()))
        {
            throw new MYException("关联银行不能删除,请确认操作");
        }

        // 如果科目被使用是不能删除的
        int count = financeItemDAO.countByFK(id, AnoConstant.FK_FIRST);

        if (count > 0)
        {
            throw new MYException("科目已经被使用,请确认操作");
        }

        taxDAO.deleteEntityBean(id);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.tax.manager.TaxManager#updateTaxBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.tax.bean.TaxBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean updateTaxBean(User user, TaxBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        TaxBean old = taxDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 只能更新name和code
        old.setName(bean.getName());

        Expression exp = new Expression(old, this);

        exp.check("#name || #code &unique2 @taxDAO", "名称或者编码已经存在");

        taxDAO.updateEntityBean(old);

        return true;
    }

    public TaxVO findVO(TaxVO vo)
    {
        // 补全
        vo.setLevel(vo.getLevel() + 1);

        vo.getOther();

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i <= TaxConstanst.TAX_LEVEL_MAX; i++ )
        {
            String field = "parentId" + i;

            Object propertyValue = BeanUtil.getProperty(vo, field);

            if (propertyValue == null)
            {
                break;
            }

            TaxBean tax = taxDAO.find(propertyValue.toString());

            if (tax == null)
            {
                break;
            }

            sb.append(tax.getName()).append("->");
        }

        if (sb.length() > 0)
        {
            sb.delete(sb.length() - 2, sb.length());
        }

        vo.setParentAllShow(sb.toString());

        return vo;
    }

    /**
     * @return the taxDAO
     */
    public TaxDAO getTaxDAO()
    {
        return taxDAO;
    }

    /**
     * @param taxDAO
     *            the taxDAO to set
     */
    public void setTaxDAO(TaxDAO taxDAO)
    {
        this.taxDAO = taxDAO;
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
     * @return the financeItemDAO
     */
    public FinanceItemDAO getFinanceItemDAO()
    {
        return financeItemDAO;
    }

    /**
     * @param financeItemDAO
     *            the financeItemDAO to set
     */
    public void setFinanceItemDAO(FinanceItemDAO financeItemDAO)
    {
        this.financeItemDAO = financeItemDAO;
    }
}
