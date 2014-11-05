package com.china.center.oa.sail.manager.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.iaop.annotation.IntegrationAOP;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.expression.Expression;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.sail.bean.PromotionBean;
import com.china.center.oa.sail.bean.PromotionItemBean;
import com.china.center.oa.sail.constanst.PromotionConstant;
import com.china.center.oa.sail.constanst.SailConstant;
import com.china.center.oa.sail.dao.PromotionDAO;
import com.china.center.oa.sail.dao.PromotionItemDAO;
import com.china.center.oa.sail.manager.PromotionManager;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.StringTools;

@IntegrationAOP
public class PromotionManagerImpl implements PromotionManager 
{

    private final Log operationLog = LogFactory.getLog("opr");
    
    private PromotionDAO promotionDAO = null;

    private PromotionItemDAO promotionItemDAO = null;
    
    private CommonDAO commonDAO = null;
    
    
    /**
     * default constructor
     */
    public PromotionManagerImpl()
    {
        
    }
    
    @Override
    @Transactional(rollbackFor = MYException.class)      
    public boolean addBean(User user, PromotionBean bean) throws MYException 
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        bean.setId(commonDAO.getSquenceString20());
        
        List<PromotionItemBean> itemList = bean.getItemList();
        
        for (PromotionItemBean each : itemList)
        {
            if ( !StringTools.isNullOrNone(each.getProductId()) && !"0".equals(each.getProductId()))
            {
                each.setType(SailConstant.SAILCONFIG_ONLYPRODUCT);

                each.setSailType( -1);

                each.setProductType( -1);
            }
            else
            {
                if (each.getProductType() == -1 && each.getSailType() == -1)
                {
                    throw new MYException("数据错误");
                }

                each.setProductId("0");

                if (each.getProductType() != -1)
                {
                    each.setType(SailConstant.SAILCONFIG_PRODUCTTYPE);
                    
                    each.setSailType( -1);
                }

                if (each.getSailType() != -1)
                {
                    each.setType(SailConstant.SAILCONFIG_SAILTTYPE);
                    
                    each.setProductType( -1);
                }
            }
            
            each.setRefId(bean.getId());
            
            promotionItemDAO.saveEntityBean(each);
        }
       
        Expression exp = new Expression(bean, this);

        exp.check("#name &unique @promotionDAO",
            "活动名称已存在");

        promotionDAO.saveEntityBean(bean);
        
        return true;
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public boolean updateBean(User user, PromotionBean bean) throws MYException 
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        PromotionBean old = promotionDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        operationLog.info("update sail conf,old:" + old);

        bean.setName(old.getName());
        bean.setInValid(old.getInValid());

        promotionDAO.updateEntityBean(bean);
        
        //明细先删，再插 promotionItemDAO.
        promotionItemDAO.deleteEntityBeansByFK(bean.getId());
        
        List<PromotionItemBean> itemList = bean.getItemList();
        
        for(PromotionItemBean each : itemList)
        {
            if ( !StringTools.isNullOrNone(each.getProductId()) && !"0".equals(each.getProductId()))
            {
                each.setType(SailConstant.SAILCONFIG_ONLYPRODUCT);
                
                each.setSailType( -1);

                each.setProductType( -1);
            }
            else
            {
                if (each.getProductType() == -1 && each.getSailType() == -1)
                {
                    throw new MYException("数据错误");
                }

                each.setProductId("0");

                if (each.getProductType() != -1)
                {
                    each.setType(SailConstant.SAILCONFIG_PRODUCTTYPE);

                    each.setSailType( -1);
                }

                if (each.getSailType() != -1)
                {
                    each.setType(SailConstant.SAILCONFIG_SAILTTYPE);

                    each.setProductType( -1);
                }
            }
            
            each.setRefId(bean.getId());
            
            promotionItemDAO.saveEntityBean(each);
        }

        operationLog.info("update sail conf,new:" + bean);

        return true;
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public boolean deleteBean(User user, String id) throws MYException 
    {
        JudgeTools.judgeParameterIsNull(user, id);

        PromotionBean bean = promotionDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        operationLog.info("delete sail conf:" + bean);

        promotionDAO.deleteEntityBean(id);
        
        promotionItemDAO.deleteEntityBeansByFK(id);
        
        return true;
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public boolean logicDeleteBean(User user, String id) throws MYException 
    {
        JudgeTools.judgeParameterIsNull(user, id);

        PromotionBean bean = promotionDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        operationLog.info("logic delete promotion conf:" + bean);
        
        bean.setInValid(PromotionConstant.INVALID_NO);
        
        promotionDAO.updateEntityBean(bean);

        return true;
    }   

    @Override
    public PromotionBean findProductConf(StafferBean sb, ProductBean productBean) 
    {

        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addIntCondition("type", "=", SailConstant.SAILCONFIG_ONLYPRODUCT);

        con.addCondition("productId", "=", productBean.getId());

        con.addCondition("industryId", "=", sb.getIndustryId());

        List<PromotionBean> conf = promotionDAO.queryEntityBeansByCondition(con.toString());

        if (conf.size() > 0)
        {
            return conf.get(0);
        }

        // 然后就是产品类型
        con.clear();

        con.addWhereStr();

        con.addIntCondition("type", "=", SailConstant.SAILCONFIG_PRODUCTTYPE);

        con.addIntCondition("productType", "=", productBean.getType());

        con.addCondition("industryId", "=", sb.getIndustryId());

        conf = promotionDAO.queryEntityBeansByCondition(con.toString());

        if (conf.size() > 0)
        {
            return conf.get(0);
        }

        // 然后就是销售类型
        con.clear();

        con.addWhereStr();

        con.addIntCondition("type", "=", SailConstant.SAILCONFIG_SAILTTYPE);

        con.addIntCondition("sailType", "=", productBean.getSailType());

        con.addCondition("industryId", "=", sb.getIndustryId());

        conf = promotionDAO.queryEntityBeansByCondition(con.toString());

        if (conf.size() > 0)
        {
            return conf.get(0);
        }

        // 返回默认的
        PromotionBean result = new PromotionBean();

        result.setRebateRate(0);

        // 先查询产品
        return result;
    }
    
    public PromotionDAO getPromotionDAO() {
        return promotionDAO;
    }

    public void setPromotionDAO(PromotionDAO promotionDAO) {
        this.promotionDAO = promotionDAO;
    }

    public CommonDAO getCommonDAO() {
        return commonDAO;
    }

    public void setCommonDAO(CommonDAO commonDAO) {
        this.commonDAO = commonDAO;
    }

    public PromotionItemDAO getPromotionItemDAO() {
        return promotionItemDAO;
    }

    public void setPromotionItemDAO(PromotionItemDAO promotionItemDAO) {
        this.promotionItemDAO = promotionItemDAO;
    }



}
