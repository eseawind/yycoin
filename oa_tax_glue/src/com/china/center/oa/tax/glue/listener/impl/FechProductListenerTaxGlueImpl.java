/**
 * File Name: FechProductListenerTaxGlueImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-6-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.glue.listener.impl;


import java.util.ArrayList;
import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.ProviderBean;
import com.china.center.oa.product.dao.ProviderDAO;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.DutyConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.DepartmentDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.stock.bean.StockBean;
import com.china.center.oa.stock.bean.StockItemBean;
import com.china.center.oa.stockvssail.listener.FechProductListener;
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
import com.china.center.tools.TimeTools;


/**
 * 采购-拿货
 * 
 * @author ZHUZHU
 * @version 2011-6-12
 * @see FechProductListenerTaxGlueImpl
 * @since 3.0
 */
public class FechProductListenerTaxGlueImpl implements FechProductListener
{
    private DutyDAO dutyDAO = null;

    private DepartmentDAO departmentDAO = null;

    private TaxDAO taxDAO = null;

    private CommonDAO commonDAO = null;

    private ProviderDAO providerDAO = null;

    private StafferDAO stafferDAO = null;

    private FinanceManager financeManager = null;
    
    private FinanceTagManager financeTagManager = null;

    /**
     * default constructor
     */
    public FechProductListenerTaxGlueImpl()
    {
    }

    /**
     * 采购拿货的时候生成凭证
     */
    public void onFechProduct(User user, StockBean stock, StockItemBean each, OutBean out)
        throws MYException
    {
        // 借:1.库存商品(1243) 2.主营业务税金即附加(5402)（供应商税点*库存商品价值）（只有一般纳税人才会有主营业务税金，其他的没有此项）(5402)
        // 贷:1.应付账款-货款(2122-01) 2.应付账款-货款（税点应付）
        FinanceBean financeBean = new FinanceBean();

        String name = user.getStafferName() + ".拿货:" + stock.getId() + '/' + each.getId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_STOCK_IN);

        financeBean.setRefId(stock.getId());

        financeBean.setRefOut(out.getFullId());

        financeBean.setRefStock(stock.getId());

        financeBean.setDutyId(each.getDutyId());

        financeBean.setCreaterId(StafferConstant.SUPER_STAFFER);

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 借:库存商品 贷:应付账款-供应商
        createItem1(user, stock, each, out, financeBean, itemList);

        DutyBean duty = dutyDAO.find(each.getDutyId());

        if (duty == null)
        {
            throw new MYException("纳税实体不存在,请确认操作");
        }

        ProviderBean provider = providerDAO.find(each.getProviderId());

        if (provider == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 一般纳税人
        if (duty.getType() == DutyConstant.DUTY_TYPE_COMMON && provider.getDues() > 0)
        {
            // 借:主营业务税金即附加 贷:应付账款-供应商
            createItem2(user, stock, each, out, financeBean, itemList, provider);
        }

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
        
        // 采购入库 财务打标记
        processFetchProductTag(user, out);
    }

    /**
     * 借:库存商品 贷:应付账款-供应商
     * 
     * @param stock
     * @param each
     * @param out
     * @param financeBean
     * @param name
     * @param itemList
     * @throws MYException
     */
    private void createItem1(User user, StockBean stock, StockItemBean each, OutBean out,
                             FinanceBean financeBean, List<FinanceItemBean> itemList)
        throws MYException
    {
        String name = user.getStafferName() + ".拿货:" + stock.getId() + '/' + each.getId() + '.';

        // 借:库存商品 贷:应付账款-供应商
        FinanceItemBean itemIn1 = new FinanceItemBean();

        String pare1 = commonDAO.getSquenceString();

        itemIn1.setPareId(pare1);

        itemIn1.setName("库存商品:" + name);

        itemIn1.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn1);

        // 库存商品
        String itemTaxId1 = TaxItemConstanst.DEPOR_PRODUCT;

        TaxBean productTax = taxDAO.findByUnique(itemTaxId1);

        if (productTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(productTax, itemIn1);

        double money = each.getAmount() * each.getPrice();

        itemIn1.setInmoney(FinanceHelper.doubleToLong(money));

        itemIn1.setOutmoney(0);

        itemIn1.setDescription("库存商品:" + name + "入库单号:" + out.getFullId());

        // 辅助核算 仓库/产品
        itemIn1.setDepotId(out.getLocation());
        itemIn1.setProductId(each.getProductId());

        // 采购数量
        itemIn1.setProductAmountIn(each.getAmount());

        itemList.add(itemIn1);

        // 贷方
        FinanceItemBean itemOut1 = new FinanceItemBean();

        itemOut1.setPareId(pare1);

        itemOut1.setName("应付账款:" + name);

        itemOut1.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut1);

        // 库存商品
        String itemTaxIdOut1 = TaxItemConstanst.PAY_PRODUCT;

        TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

        if (outTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut1);

        double outMoney = each.getAmount() * each.getPrice();

        itemOut1.setInmoney(0);

        itemOut1.setOutmoney(FinanceHelper.doubleToLong(outMoney));

        itemOut1.setDescription("贷:" + name + "入库单号:" + out.getFullId());

        // 辅助核算 单位
        itemOut1.setUnitType(TaxConstanst.UNIT_TYPE_PROVIDE);
        itemOut1.setUnitId(each.getProviderId());

        itemList.add(itemOut1);
    }

    /**
     * 借:主营业务税金即附加 贷:应付账款-供应商
     * 
     * @param stock
     * @param each
     * @param out
     * @param financeBean
     * @param name
     * @param itemList
     * @throws MYException
     */
    private void createItem2(User user, StockBean stock, StockItemBean each, OutBean out,
                             FinanceBean financeBean, List<FinanceItemBean> itemList,
                             ProviderBean provider)
        throws MYException
    {
        String name = "主营业务税金及附加.拿货:" + stock.getId() + '/' + each.getId() + '.';

        // 借:库存商品 贷:应付账款-供应商
        FinanceItemBean itemIn = new FinanceItemBean();

        String pare = commonDAO.getSquenceString();

        itemIn.setPareId(pare);

        itemIn.setName("主营业务税金及附加:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        // 主营业务税金及附加
        String itemTaxId1 = TaxItemConstanst.MAIN_TAX;

        TaxBean productTax = taxDAO.findByUnique(itemTaxId1);

        if (productTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(productTax, itemIn);

        // 供应商税点*库存商品价值
        double money = each.getAmount() * each.getPrice() * provider.getDues() / 1000.0d;

        itemIn.setInmoney(FinanceHelper.doubleToLong(money));

        itemIn.setOutmoney(0);

        itemIn.setDescription("主营业务税金及附加");

        StafferBean staffer = stafferDAO.find(stock.getStafferId());

        if (staffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 辅助核算 部门(开单所在的部门)
        itemIn.setDepartmentId(staffer.getPrincipalshipId());

        itemList.add(itemIn);

        // 贷方 应付账款-供应商
        FinanceItemBean itemOut = new FinanceItemBean();

        itemOut.setPareId(pare);

        itemOut.setName("贷:" + name);

        itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut);

        // 库存商品
        String itemTaxIdOut1 = TaxItemConstanst.PAY_PRODUCT;

        TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut1);

        if (outTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);

        double outMoney = each.getAmount() * each.getPrice() * provider.getDues() / 1000.0d;

        itemOut.setInmoney(0);

        itemOut.setOutmoney(FinanceHelper.doubleToLong(outMoney));

        itemOut.setDescription("应付账款");

        // 辅助核算 单位
        itemOut.setUnitType(TaxConstanst.UNIT_TYPE_PROVIDE);
        itemOut.setUnitId(each.getProviderId());

        itemList.add(itemOut);
    }

    /**
	 * processFetchProductTag
	 * 采购入库 打标记
	 * @param user
	 * @param outBean
	 * @throws MYException
	 */
	private void processFetchProductTag(User user, OutBean outBean)
    throws MYException
	{
		FinanceTagBean tag = new FinanceTagBean();
		
		tag.setType("PURCHASEIN");
		tag.setTypeName("采购入库");
		tag.setFullId(outBean.getFullId());
		tag.setStatsTime(TimeTools.now());
		
		if (outBean.getMtype() == PublicConstant.MANAGER_TYPE_COMMON)
		{
			tag.setMtype(PublicConstant.MANAGER_TYPE_COMMON);
			
			// 17%
			if (outBean.getInvoiceId().equals("90000000000000000001"))
			{
				tag.setTag("CP1");
			}else if (outBean.getInvoiceId().equals("90000000000000000010"))
			{
				tag.setTag("CP2");
			}else if (outBean.getInvoiceId().equals("90000000000000000002"))
			{
				tag.setTag("CP3");
			}else if (outBean.getInvoiceId().equals("90000000000000000011"))
			{
				tag.setTag("CP4");
			}else{
				tag.setTag("CP5");
			}
		}else{
			tag.setTag("CG1");
			tag.setMtype(PublicConstant.MANAGER_TYPE_MANAGER);
		}
		
		financeTagManager.addFinanceTagBeanWithoutTransaction(user, tag);
	}
    
    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "FechProductListener.TaxGlueImpl";
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
