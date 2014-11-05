/**
 * File Name: ComposeProductListenerTaxGlueImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-6-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.glue.listener.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.ComposeFeeBean;
import com.china.center.oa.product.bean.ComposeFeeDefinedBean;
import com.china.center.oa.product.bean.ComposeItemBean;
import com.china.center.oa.product.bean.ComposeProductBean;
import com.china.center.oa.product.bean.DecomposeProductBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.constant.ComposeConstant;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.dao.ComposeFeeDefinedDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.dao.ProviderDAO;
import com.china.center.oa.product.listener.ComposeProductListener;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.DepartmentDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.helper.OATools;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.bean.FinanceItemBean;
import com.china.center.oa.tax.bean.FinanceTagBean;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.constanst.TaxConstanst;
import com.china.center.oa.tax.constanst.TaxItemConstanst;
import com.china.center.oa.tax.dao.TaxDAO;
import com.china.center.oa.tax.helper.FinanceHelper;
import com.china.center.oa.tax.manager.FinanceManager;
import com.china.center.oa.tax.manager.FinanceTagManager;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * TODO_OSGI 产品合成-运营总监通过(合成和分解)
 * 
 * @author ZHUZHU
 * @version 2011-6-8
 * @see ComposeProductListenerTaxGlueImpl
 * @since 3.0
 */
public class ComposeProductListenerTaxGlueImpl implements ComposeProductListener
{
    private final Log _logger = LogFactory.getLog(getClass());

    private DutyDAO dutyDAO = null;

    private DepartmentDAO departmentDAO = null;

    private TaxDAO taxDAO = null;

    private CommonDAO commonDAO = null;

    private ProviderDAO providerDAO = null;

    private FinanceManager financeManager = null;

    private StafferDAO stafferDAO = null;

    private ProductDAO productDAO = null;

    private ComposeFeeDefinedDAO composeFeeDefinedDAO = null;
    
    private FinanceTagManager financeTagManager = null;

    /**
     * default constructor
     */
    public ComposeProductListenerTaxGlueImpl()
    {
    }

    /**
     * 借：库存商品（合成后的商品）/合成费用（负数）（科目根据合成费用对应科目）<br>
     * 贷：库存商品（合成前的商品）
     */
    public void onConfirmCompose(User user, ComposeProductBean bean)
        throws MYException
    {
        // 修改库存(合成)
        if (bean.getType() == ComposeConstant.COMPOSE_TYPE_COMPOSE)
        {
            FinanceBean financeBean = new FinanceBean();

            String name = "产品合成:" + bean.getId() + '.';

            financeBean.setName(name);

            // MANAGER 合成凭证的管理类型处理DE
            if (OATools.isManager(bean.getMtype()))
            {
                financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);
                financeBean.setDutyId(PublicConstant.MANAGER_DUTY_ID);
            }
            else
            {
                financeBean.setType(TaxConstanst.FINANCE_TYPE_DUTY);
                financeBean.setDutyId(PublicConstant.DEFAULR_DUTY_ID);
            }

            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_PRODUCT_COMPOSE);

            financeBean.setRefId(bean.getId());
            
            if (!StringTools.isNullOrNone(bean.getParentId()))
            {
            	financeBean.setRefId(bean.getParentId());
            }
            
            financeBean.setRefOut(bean.getId());

            TaxGlueHelper.setDutyId(financeBean, bean.getMtype());

            financeBean.setDescription(financeBean.getName());

            financeBean.setFinanceDate(TimeTools.now_short());

            financeBean.setLogTime(TimeTools.now());

            List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

            createItem(user, bean, financeBean, itemList);

            financeBean.setItemList(itemList);

            financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
            
            processComposeTag(user, bean);
        }
        else
        {
            // 分解
            FinanceBean financeBean = new FinanceBean();

            String name = "产品分解:" + bean.getId() + '.';

            financeBean.setName(name);

            // MANAGER 合成回滚凭证的管理类型处理
            if (OATools.isManager(bean.getMtype()))
            {
                financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);
                financeBean.setDutyId(PublicConstant.MANAGER_DUTY_ID);
            }
            else
            {
                financeBean.setType(TaxConstanst.FINANCE_TYPE_DUTY);
                financeBean.setDutyId(PublicConstant.DEFAULR_DUTY_ID);
            }

            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_PRODUCT_COMPOSE_BACK);

            financeBean.setRefId(bean.getId());
            
            if (!StringTools.isNullOrNone(bean.getParentId()))
            {
            	financeBean.setRefId(bean.getParentId());
            }
            
            financeBean.setRefOut(bean.getId());

            TaxGlueHelper.setDutyId(financeBean, bean.getMtype());

            financeBean.setDescription(financeBean.getName());

            financeBean.setFinanceDate(TimeTools.now_short());

            financeBean.setLogTime(TimeTools.now());

            List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

            createItemForRollBack(user, bean, financeBean, itemList);

            financeBean.setItemList(itemList);

            financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
            
            processComposeTag(user, bean);
        }
    }
    
    /**
     * 生 成财务 标记数据
     * @param user
     * @param bean
     * @throws MYException
     */
    private void processComposeTag(User user, ComposeProductBean bean)
    throws MYException
	{
		FinanceTagBean tag = new FinanceTagBean();
		
		tag.setType("PRODUCTCOMPOSE");
		tag.setTypeName("合成产品");
		tag.setFullId(bean.getId());
		tag.setStatsTime(TimeTools.now());
		tag.setTag(bean.getTag());
		tag.setMtype(bean.getMtype());
		
		financeTagManager.addFinanceTagBeanWithoutTransaction(user, tag);
	}

    /**
     * createItem
     * 
     * @param user
     * @param compose
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createItem(User user, ComposeProductBean compose, FinanceBean financeBean,
                            List<FinanceItemBean> itemList)
        throws MYException
    {
        String name = "产品合成:" + compose.getId() + '.';

        String pare1 = commonDAO.getSquenceString();

        // 借:库存商品 贷:应付账款-供应商
        FinanceItemBean itemIn1 = new FinanceItemBean();

        itemIn1.setPareId(pare1);

        itemIn1.setName("库存商品（合成后的商品）:" + name);

        itemIn1.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn1);

        // 库存商品
        String itemInTaxId = TaxItemConstanst.DEPOR_PRODUCT;

        TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

        if (itemInTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(itemInTax, itemIn1);

        double money = compose.getAmount() * MathTools.round2(compose.getPrice());

        itemIn1.setInmoney(FinanceHelper.doubleToLong(money));

        itemIn1.setOutmoney(0);

        itemIn1.setDescription(itemIn1.getName());

        // 辅助核算 产品/仓库
        itemIn1.setProductId(compose.getProductId());
        itemIn1.setProductAmountIn(compose.getAmount());
        itemIn1.setDepotId(compose.getDeportId());

        itemList.add(itemIn1);

        List<ComposeFeeBean> feeList = compose.getFeeList();

        long total = 0L;

        for (ComposeFeeBean each : feeList)
        {
            // 合成费用（负数）（科目根据合成费用对应科目）
            FinanceItemBean eachItemIn = new FinanceItemBean();

            eachItemIn.setPareId(pare1);

            eachItemIn.setName("合成费用:" + name);

            eachItemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, eachItemIn);

            ComposeFeeDefinedBean feeDefined = composeFeeDefinedDAO.find(each.getFeeItemId());

            if (feeDefined == null || StringTools.isNullOrNone(feeDefined.getTaxId()))
            {
                throw new MYException("合成费用缺少对应的科目,请确认操作");
            }

            // 合成费用
            String eachItemInTaxId = feeDefined.getTaxId();

            TaxBean eachItemInTax = taxDAO.find(eachItemInTaxId);

            if (eachItemInTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(eachItemInTax, eachItemIn);

            // 合成费用（负数）
            double eachMoney = -each.getPrice();

            eachItemIn.setInmoney(FinanceHelper.doubleToLong(eachMoney));

            total += FinanceHelper.doubleToLong( -eachMoney);

            eachItemIn.setOutmoney(0);

            eachItemIn.setDescription(eachItemIn.getName());

            // 辅助核算 动态的就是全部(合成的费用人员是申请人)
            eachItemIn.setStafferId(compose.getStafferId());

            StafferBean staffer = stafferDAO.find(compose.getStafferId());

            if (staffer == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            eachItemIn.setDepartmentId(staffer.getPrincipalshipId());

            itemList.add(eachItemIn);
        }

        List<ComposeItemBean> composeItemList = compose.getItemList();

        for (ComposeItemBean composeItemBean : composeItemList)
        {
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            ProductBean product = productDAO.find(composeItemBean.getProductId());

            if (product == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            itemOut1.setName("库存商品（合成前的商品）(" + product.getName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);

            double outMoney = composeItemBean.getAmount() * composeItemBean.getPrice();

            itemOut1.setInmoney(0);

            itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            total += FinanceHelper.doubleToLong(outMoney);

            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 产品/仓库
            itemOut1.setProductId(composeItemBean.getProductId());
            itemOut1.setProductAmountOut(composeItemBean.getAmount());
            itemOut1.setDepotId(composeItemBean.getDeportId());

            itemList.add(itemOut1);
        }

//        if (itemIn1.getInmoney() != total)
//        {
//            _logger.info("产品合成差价:" + itemIn1.getInmoney() + ".实际合计:" + total);
//
//            itemIn1.setInmoney(total);
//        }
        
        // 合成尾差
        // 借:产品合成损益(5505-33)
        FinanceItemBean itemIn2 = new FinanceItemBean();

        itemIn2.setPareId(pare1);

        itemIn2.setName("合成尾差:" + name);

        itemIn2.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn2);

        // 库存商品
        String itemInTaxId2 = TaxItemConstanst.COMPOSEDIFF;

        TaxBean itemInTax2 = taxDAO.findByUnique(itemInTaxId2);

        if (itemInTax2 == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(itemInTax2, itemIn2);

        long money1 = total - itemIn1.getInmoney();

        itemIn2.setInmoney(money1);

        itemIn2.setOutmoney(0);

        itemIn2.setDescription(itemIn2.getName());

        // 辅助核算 合成部门/人员
        itemIn2.setStafferId(compose.getStafferId());

        StafferBean staffer = stafferDAO.find(compose.getStafferId());

        if (staffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        itemIn2.setDepartmentId(staffer.getPrincipalshipId());

        itemList.add(itemIn2);
        
    }

    public void onConfirmDecompose(User user, DecomposeProductBean bean)
    throws MYException
	{
        // 拆分
        FinanceBean financeBean = new FinanceBean();

        String name = "产品拆分:" + bean.getId() + '.';

        financeBean.setName(name);

        // MANAGER 合成回滚凭证的管理类型处理
        if (OATools.isManager(bean.getMtype()))
        {
            financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);
            financeBean.setDutyId(PublicConstant.MANAGER_DUTY_ID);
        }
        else
        {
            financeBean.setType(TaxConstanst.FINANCE_TYPE_DUTY);
            financeBean.setDutyId(PublicConstant.DEFAULR_DUTY_ID);
        }

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_PRODUCT_DECOMPOSE);

        financeBean.setRefId(bean.getId());

        TaxGlueHelper.setDutyId(financeBean, bean.getMtype());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        createItemForDecompose(user, bean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    
        // 产品拆分 财务数据标记
        processDecomposeTag(user, bean);
	}
    
    /**
     * processDecomposeTag
     * @param user
     * @param bean
     * @throws MYException
     */
    private void processDecomposeTag(User user, DecomposeProductBean bean)
    throws MYException
	{
		FinanceTagBean tag = new FinanceTagBean();
		
		tag.setType("PRODUCTSPLIT");
		tag.setTypeName("产品拆分及金银料出库");
		tag.setFullId(bean.getId());
		tag.setStatsTime(TimeTools.now());
		
		if (bean.getMtype() == PublicConstant.MANAGER_TYPE_COMMON)
		{
			tag.setTag("CFP1");
			tag.setMtype(PublicConstant.MANAGER_TYPE_COMMON);
		}else{
			tag.setTag("CFG1");
			tag.setMtype(PublicConstant.MANAGER_TYPE_MANAGER);
		}
		
		financeTagManager.addFinanceTagBeanWithoutTransaction(user, tag);
	}
    
    /**
     * createItemForRollBack
     * 
     * @param user
     * @param compose
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createItemForRollBack(User user, ComposeProductBean compose,
                                       FinanceBean financeBean, List<FinanceItemBean> itemList)
        throws MYException
    {
        String name = "产品分解:" + compose.getId() + '.';

        String pare1 = commonDAO.getSquenceString();

        // 借:库存商品 贷:应付账款-供应商
        FinanceItemBean itemIn1 = new FinanceItemBean();

        itemIn1.setPareId(pare1);

        itemIn1.setName("库存商品（合成后的商品）:" + name);

        itemIn1.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn1);

        // 库存商品
        String itemInTaxId = TaxItemConstanst.DEPOR_PRODUCT;

        TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

        if (itemInTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(itemInTax, itemIn1);

        // 回滚都是负数
        double money = -compose.getAmount() * compose.getPrice();

        itemIn1.setInmoney(FinanceHelper.doubleToLong(money));

        itemIn1.setOutmoney(0);

        itemIn1.setDescription(itemIn1.getName());

        // 辅助核算 产品/仓库
        itemIn1.setProductId(compose.getProductId());
        itemIn1.setProductAmountIn( -compose.getAmount());

        itemIn1.setDepotId(compose.getDeportId());

        itemList.add(itemIn1);

        List<ComposeFeeBean> feeList = compose.getFeeList();

        long total = 0L;

        for (ComposeFeeBean each : feeList)
        {
            // 合成费用（负数）（科目根据合成费用对应科目）
            FinanceItemBean eachItemIn = new FinanceItemBean();

            eachItemIn.setPareId(pare1);

            eachItemIn.setName("合成费用回滚:" + name);

            eachItemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, eachItemIn);

            ComposeFeeDefinedBean feeDefined = composeFeeDefinedDAO.find(each.getFeeItemId());

            if (feeDefined == null || StringTools.isNullOrNone(feeDefined.getTaxId()))
            {
                throw new MYException("合成费用缺少对应的科目,请确认操作");
            }

            // 库存商品
            String eachItemInTaxId = feeDefined.getTaxId();

            TaxBean eachItemInTax = taxDAO.find(eachItemInTaxId);

            if (eachItemInTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(eachItemInTax, eachItemIn);

            // 合成费用（正数）
            double eachMoney = -each.getPrice();

            eachItemIn.setInmoney(FinanceHelper.doubleToLong(eachMoney));

            total += FinanceHelper.doubleToLong( -eachMoney);

            eachItemIn.setOutmoney(0);

            eachItemIn.setDescription(eachItemIn.getName());

            // 辅助核算 动态的就是全部
            eachItemIn.setStafferId(user.getStafferId());

            StafferBean staffer = stafferDAO.find(user.getStafferId());

            if (staffer == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            eachItemIn.setDepartmentId(staffer.getPrincipalshipId());

            // 这里不需要数量啊
            eachItemIn.setProductId(compose.getProductId());
            eachItemIn.setDepotId(compose.getDeportId());

            itemList.add(eachItemIn);
        }

        // 产品成本
        List<ComposeItemBean> composeItemList = compose.getItemList();

        for (ComposeItemBean composeItemBean : composeItemList)
        {
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            ProductBean product = productDAO.find(composeItemBean.getProductId());

            if (product == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            itemOut1.setName("库存商品（合成前的商品）(" + product.getName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 库存商品
            String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);

            double outMoney = -composeItemBean.getAmount() * composeItemBean.getPrice();

            itemOut1.setInmoney(0);

            itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            total += FinanceHelper.doubleToLong(outMoney);

            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 产品/仓库
            itemOut1.setProductId(composeItemBean.getProductId());
            itemOut1.setProductAmountOut( -composeItemBean.getAmount());
            itemOut1.setDepotId(composeItemBean.getDeportId());

            itemList.add(itemOut1);
        }

        if (itemIn1.getInmoney() != total)
        {
            _logger.info("产品分解差价:" + itemIn1.getInmoney() + ".实际合计:" + total);

            itemIn1.setInmoney(total);
        }
    }
    
    private void createItemForDecompose(User user, DecomposeProductBean compose,
            FinanceBean financeBean, List<FinanceItemBean> itemList)
	throws MYException
	{
        String name = "产品拆分:" + compose.getId() + '.';

        String pare1 = commonDAO.getSquenceString();

        // 借:库存商品 贷:应付账款-供应商
        FinanceItemBean itemIn1 = new FinanceItemBean();

        itemIn1.setPareId(pare1);

        itemIn1.setName("库存商品（成品）:" + name);

        itemIn1.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn1);

        // 库存商品
        String itemInTaxId = TaxItemConstanst.DEPOR_PRODUCT;

        TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

        if (itemInTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(itemInTax, itemIn1);

        // 回滚都是负数
        double money = -compose.getAmount() * compose.getPrice();

        itemIn1.setInmoney(FinanceHelper.doubleToLong(money));

        itemIn1.setOutmoney(0);

        itemIn1.setDescription(itemIn1.getName());

        // 辅助核算 产品/仓库
        itemIn1.setProductId(compose.getProductId());
        itemIn1.setProductAmountIn( -compose.getAmount());

        itemIn1.setDepotId(compose.getDeportId());

        itemList.add(itemIn1);

        long total = 0L;

        // 产品成本
        List<ComposeItemBean> composeItemList = compose.getItemList();

        for (ComposeItemBean composeItemBean : composeItemList)
        {
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            ProductBean product = productDAO.find(composeItemBean.getProductId());

            if (product == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            itemOut1.setName("库存商品（合成前的商品）(" + product.getName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            if (composeItemBean.getStype() == ProductConstant.DECOMPOSE_PRODUCT_STORAGE)
            {
            	 // 库存商品
                String itemTaxIdOut1 = TaxItemConstanst.DEPOR_PRODUCT;

                TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

                if (outTax == null)
                {
                    throw new MYException("数据错误,请确认操作");
                }

                // 科目拷贝
                FinanceHelper.copyTax(outTax, itemOut1);
            }else{
            	 // 库存商品
                String itemTaxIdOut1 = TaxItemConstanst.COMPOSEDIFF;

                TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

                if (outTax == null)
                {
                    throw new MYException("数据错误,请确认操作");
                }

                // 科目拷贝
                FinanceHelper.copyTax(outTax, itemOut1);
            }

            double outMoney = -composeItemBean.getAmount() * composeItemBean.getPrice();

            itemOut1.setInmoney(0);

            itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            total += FinanceHelper.doubleToLong(outMoney);

            itemOut1.setDescription(itemOut1.getName());

            if (composeItemBean.getStype() == ProductConstant.DECOMPOSE_PRODUCT_STORAGE)
            {
            	// 辅助核算 产品/仓库
                itemOut1.setProductId(composeItemBean.getProductId());
                itemOut1.setProductAmountOut( -composeItemBean.getAmount());
                itemOut1.setDepotId(composeItemBean.getDeportId());
            }else{
            	// 费用 挂靠 在 陈永董事长
                itemOut1.setStafferId("4643715");
                
                StafferBean sb = stafferDAO.find("4643715");
                
                if (sb == null)
                {
                	throw new MYException("拆分费用挂靠时,陈永董事长不存在");
                }
                
                itemOut1.setDepartmentId(sb.getPrincipalshipId());
            }

            itemList.add(itemOut1);
        }

        if (itemIn1.getInmoney() != total)
        {
            _logger.info("产品拆分差价:" + itemIn1.getInmoney() + ".实际合计:" + total);

            itemIn1.setInmoney(total);
        }
	}

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "ComposeProductListener.TaxGlueImpl";
    }

    /**
     * @return the dutyDAO
     */
    public DutyDAO getDutyDAO()
    {
        return dutyDAO;
    }

    /**
     * @param dutyDAO
     *            the dutyDAO to set
     */
    public void setDutyDAO(DutyDAO dutyDAO)
    {
        this.dutyDAO = dutyDAO;
    }

    /**
     * @return the departmentDAO
     */
    public DepartmentDAO getDepartmentDAO()
    {
        return departmentDAO;
    }

    /**
     * @param departmentDAO
     *            the departmentDAO to set
     */
    public void setDepartmentDAO(DepartmentDAO departmentDAO)
    {
        this.departmentDAO = departmentDAO;
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
     * @return the financeManager
     */
    public FinanceManager getFinanceManager()
    {
        return financeManager;
    }

    /**
     * @param financeManager
     *            the financeManager to set
     */
    public void setFinanceManager(FinanceManager financeManager)
    {
        this.financeManager = financeManager;
    }

    /**
     * @return the composeFeeDefinedDAO
     */
    public ComposeFeeDefinedDAO getComposeFeeDefinedDAO()
    {
        return composeFeeDefinedDAO;
    }

    /**
     * @param composeFeeDefinedDAO
     *            the composeFeeDefinedDAO to set
     */
    public void setComposeFeeDefinedDAO(ComposeFeeDefinedDAO composeFeeDefinedDAO)
    {
        this.composeFeeDefinedDAO = composeFeeDefinedDAO;
    }

    /**
     * @return the stafferDAO
     */
    public StafferDAO getStafferDAO()
    {
        return stafferDAO;
    }

    /**
     * @param stafferDAO
     *            the stafferDAO to set
     */
    public void setStafferDAO(StafferDAO stafferDAO)
    {
        this.stafferDAO = stafferDAO;
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

	/**
	 * @return the financeTagManager
	 */
	public FinanceTagManager getFinanceTagManager()
	{
		return financeTagManager;
	}

	/**
	 * @param financeTagManager the financeTagManager to set
	 */
	public void setFinanceTagManager(FinanceTagManager financeTagManager)
	{
		this.financeTagManager = financeTagManager;
	}
}
