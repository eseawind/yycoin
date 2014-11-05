/**
 * File Name: PriceChangeListenerTaxGlueImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-6-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.glue.listener.impl;


import java.util.ArrayList;
import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.PriceChangeBean;
import com.china.center.oa.product.bean.PriceChangeNewItemBean;
import com.china.center.oa.product.bean.PriceChangeSrcItemBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.dao.ComposeFeeDefinedDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.dao.ProviderDAO;
import com.china.center.oa.product.listener.PriceChangeListener;
import com.china.center.oa.product.vs.StorageRelationBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.DepartmentDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.StafferVSPriDAO;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.bean.FinanceItemBean;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.constanst.TaxConstanst;
import com.china.center.oa.tax.constanst.TaxItemConstanst;
import com.china.center.oa.tax.dao.TaxDAO;
import com.china.center.oa.tax.helper.FinanceHelper;
import com.china.center.oa.tax.manager.FinanceManager;
import com.china.center.tools.TimeTools;


/**
 * TODO_OSGI 产品调价
 * 
 * @author ZHUZHU
 * @version 2011-6-8
 * @see PriceChangeListenerTaxGlueImpl
 * @since 3.0
 */
public class PriceChangeListenerTaxGlueImpl implements PriceChangeListener
{
    private DutyDAO dutyDAO = null;

    private DepartmentDAO departmentDAO = null;

    private TaxDAO taxDAO = null;

    private CommonDAO commonDAO = null;

    private ProviderDAO providerDAO = null;

    private FinanceManager financeManager = null;

    private StafferDAO stafferDAO = null;

    private StafferVSPriDAO stafferVSPriDAO = null;

    private ProductDAO productDAO = null;

    private ComposeFeeDefinedDAO composeFeeDefinedDAO = null;

    /**
     * default constructor
     */
    public PriceChangeListenerTaxGlueImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.listener.PriceChangeListener#onConfirmPriceChange(com.center.china.osgi.publics.User,
     *      com.china.center.oa.product.bean.PriceChangeBean)
     */
    public void onConfirmPriceChange(User user, PriceChangeBean bean)
        throws MYException
    {
        // 借方:库存商品（调价后的商品） 投资收益-调价收入（可能是正负）
        // 贷方：库存商品（调价前的商品）
        FinanceBean financeBean = new FinanceBean();

        String name = "产品调价:" + bean.getId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_PRODUCT_PRICE);

        financeBean.setRefId(bean.getId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        TaxGlueHelper.setDutyId(financeBean, bean.getMtype());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        createItem(user, bean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }

    /**
     * createItem
     * 
     * @param user
     * @param priceChange
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createItem(User user, PriceChangeBean priceChange, FinanceBean financeBean,
                            List<FinanceItemBean> itemList)
        throws MYException
    {

        String name = "产品调价:" + priceChange.getId() + '.';

        String pare1 = commonDAO.getSquenceString();

        // 借方:库存商品（调价后的商品） 投资收益-调价收入（可能是正负）
        FinanceItemBean itemIn1 = new FinanceItemBean();

        itemIn1.setPareId(pare1);

        itemIn1.setName("投资收益-调价收入:" + name);

        itemIn1.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn1);

        // 调价收入
        String itemInTaxId = TaxItemConstanst.PRICECHANGE_REVEIVE;

        TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

        if (itemInTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(itemInTax, itemIn1);

        // 原价
        List<PriceChangeSrcItemBean> srcList = priceChange.getSrcList();

        // 现价
        List<PriceChangeNewItemBean> newList = priceChange.getNewList();

        long inTotal = 0L;

        for (PriceChangeNewItemBean newEach : newList)
        {
            // 库存商品（调价后的商品）
            FinanceItemBean eachItemIn = new FinanceItemBean();

            eachItemIn.setPareId(pare1);

            ProductBean product = productDAO.find(newEach.getProductId());

            if (product == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            eachItemIn.setName("库存商品（调价后的商品）(" + product.getName() + "):" + name);

            eachItemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, eachItemIn);

            // 库存商品
            String eachItemInTaxId = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean eachItemInTax = taxDAO.findByUnique(eachItemInTaxId);

            if (eachItemInTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(eachItemInTax, eachItemIn);

            // 调价后的商品价格
            double eachMoney = newEach.getPrice() * newEach.getAmount();

            eachItemIn.setInmoney(FinanceHelper.doubleToLong(eachMoney));

            inTotal += eachItemIn.getInmoney();

            eachItemIn.setOutmoney(0);

            eachItemIn.setDescription(eachItemIn.getName());

            // 辅助核算 产品/仓库
            eachItemIn.setProductId(newEach.getProductId());
            eachItemIn.setProductAmountIn(newEach.getAmount());
            eachItemIn.setDepotId(newEach.getDeportId());

            itemList.add(eachItemIn);
        }

        long outnTotal = 0L;
        // 贷方:库存商品（调价前的商品）
        for (PriceChangeSrcItemBean srcEach : srcList)
        {
            FinanceItemBean itemOut1 = new FinanceItemBean();

            itemOut1.setPareId(pare1);

            ProductBean product = productDAO.find(srcEach.getProductId());

            if (product == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            itemOut1.setName("库存商品（调价前的商品）(" + product.getName() + "):" + name);

            itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOut1);

            // 库存商品
            String eachItemInTaxId = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(eachItemInTaxId);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOut1);

            double outMoney = srcEach.getAmount() * srcEach.getPrice();

            itemOut1.setInmoney(0);

            itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            outnTotal += itemOut1.getOutmoney();

            itemOut1.setDescription(itemOut1.getName());

            // 辅助核算 产品/仓库
            itemOut1.setProductId(srcEach.getProductId());
            itemOut1.setProductAmountOut(srcEach.getAmount());
            itemOut1.setDepotId(srcEach.getDeportId());

            itemList.add(itemOut1);
        }

        // 在借方是负数
        itemIn1.setInmoney(outnTotal - inTotal);

        itemIn1.setOutmoney(0);

        itemIn1.setDescription(itemIn1.getName());

        StafferBean staffer = stafferDAO.find(user.getStafferId());

        if (staffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 辅助核算 部门
        itemIn1.setDepartmentId(staffer.getPrincipalshipId());

        itemList.add(0, itemIn1);
    }

    /**
     * 回滚
     * 
     * @param user
     * @param priceChange
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createItem2(User user, PriceChangeBean priceChange, FinanceBean financeBean,
                             List<FinanceItemBean> itemList)
        throws MYException
    {
        String name = "产品调价回滚:" + priceChange.getId() + '.';

        String pare1 = commonDAO.getSquenceString();

        // 借方:库存商品（调价后的商品） 投资收益-调价收入（可能是正负）
        FinanceItemBean itemIn1 = new FinanceItemBean();

        itemIn1.setPareId(pare1);

        itemIn1.setName("投资收益-调价收入:" + name);

        itemIn1.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn1);

        // 调价收入
        String itemInTaxId = TaxItemConstanst.PRICECHANGE_REVEIVE;

        TaxBean itemInTax = taxDAO.findByUnique(itemInTaxId);

        if (itemInTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(itemInTax, itemIn1);

        // 原价
        List<PriceChangeSrcItemBean> srcList = priceChange.getSrcList();

        double oldTotal = 0.0d;

        for (PriceChangeSrcItemBean oldEach : srcList)
        {
            oldTotal += oldEach.getAmount() * oldEach.getPrice();
        }

        // 现价
        List<PriceChangeNewItemBean> newList = priceChange.getNewList();

        double newTotal = 0.0d;

        for (PriceChangeNewItemBean newEach : newList)
        {
            newTotal += newEach.getAmount() * newEach.getPrice();
        }

        // 在借方是负数(负数)
        double money = (newTotal - oldTotal);

        itemIn1.setInmoney(FinanceHelper.doubleToLong(money));

        itemIn1.setOutmoney(0);

        itemIn1.setDescription(itemIn1.getName());

        StafferBean staffer = stafferDAO.find(user.getStafferId());

        if (staffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 辅助核算 部门
        itemIn1.setDepartmentId(staffer.getPrincipalshipId());

        itemList.add(itemIn1);

        for (PriceChangeNewItemBean newEach : newList)
        {
            // 库存商品（调价后的商品）
            FinanceItemBean eachItemIn = new FinanceItemBean();

            eachItemIn.setPareId(pare1);

            ProductBean product = productDAO.find(newEach.getProductId());

            if (product == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            eachItemIn.setName("库存商品（调价后的商品）(" + product.getName() + "):" + name);

            eachItemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, eachItemIn);

            // 库存商品
            String eachItemInTaxId = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean eachItemInTax = taxDAO.findByUnique(eachItemInTaxId);

            if (eachItemInTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(eachItemInTax, eachItemIn);

            // 调价后的商品价格
            double eachMoney = -newEach.getPrice() * newEach.getAmount();

            eachItemIn.setInmoney(FinanceHelper.doubleToLong(eachMoney));

            eachItemIn.setOutmoney(0);

            eachItemIn.setDescription(eachItemIn.getName());

            // 辅助核算 产品/仓库
            eachItemIn.setProductId(newEach.getProductId());
            eachItemIn.setProductAmountIn( -newEach.getAmount());
            eachItemIn.setDepotId(newEach.getDeportId());

            itemList.add(eachItemIn);
        }

        // 贷方:库存商品（调价前的商品）
        for (PriceChangeSrcItemBean srcEach : srcList)
        {
            FinanceItemBean itemOutEach = new FinanceItemBean();

            itemOutEach.setPareId(pare1);

            ProductBean product = productDAO.find(srcEach.getProductId());

            if (product == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            itemOutEach.setName("库存商品（调价前的商品）(" + product.getName() + "):" + name);

            itemOutEach.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOutEach);

            // 库存商品
            String eachItemInTaxId = TaxItemConstanst.DEPOR_PRODUCT;

            TaxBean outTax = taxDAO.findByUnique(eachItemInTaxId);

            if (outTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(outTax, itemOutEach);

            double outMoney = -srcEach.getAmount() * srcEach.getPrice();

            itemOutEach.setInmoney(0);

            itemOutEach.setOutmoney(FinanceHelper.doubleToLong(outMoney));

            itemOutEach.setDescription(itemOutEach.getName());

            // 辅助核算 产品/仓库
            itemOutEach.setProductId(srcEach.getProductId());
            itemOutEach.setProductAmountOut( -srcEach.getAmount());
            itemOutEach.setDepotId(srcEach.getDeportId());

            itemList.add(itemOutEach);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.listener.PriceChangeListener#onPriceChange(com.center.china.osgi.publics.User,
     *      com.china.center.oa.product.bean.ProductBean)
     */
    public void onPriceChange(User user, ProductBean bean)
        throws MYException
    {
        // 

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.listener.PriceChangeListener#onPriceChange2(com.center.china.osgi.publics.User,
     *      com.china.center.oa.product.vs.StorageRelationBean)
     */
    public int onPriceChange2(User user, StorageRelationBean relation)
    {
        // 
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.listener.PriceChangeListener#onPriceChange3(com.center.china.osgi.publics.User,
     *      com.china.center.oa.product.vs.StorageRelationBean)
     */
    public int onPriceChange3(User user, StorageRelationBean relation)
    {
        // 
        return 0;
    }

    public void onRollbackPriceChange(User user, PriceChangeBean bean)
        throws MYException
    {
        // 借方(负):库存商品（调价后的商品） 投资收益-调价收入（可能是正负）
        // 贷方(负)：库存商品（调价前的商品）
        FinanceBean financeBean = new FinanceBean();

        String name = "产品调价:" + bean.getId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_PRODUCT_PRICE_BACK);

        financeBean.setRefId(bean.getId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        TaxGlueHelper.setDutyId(financeBean, bean.getMtype());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        createItem2(user, bean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "PriceChangeListener.TaxGlueImpl";
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
     * @return the stafferVSPriDAO
     */
    public StafferVSPriDAO getStafferVSPriDAO()
    {
        return stafferVSPriDAO;
    }

    /**
     * @param stafferVSPriDAO
     *            the stafferVSPriDAO to set
     */
    public void setStafferVSPriDAO(StafferVSPriDAO stafferVSPriDAO)
    {
        this.stafferVSPriDAO = stafferVSPriDAO;
    }
}
