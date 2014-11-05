/**
 * File Name: StockPayApplyListenerTaxGlueImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-6-19<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.glue.listener.impl;


import java.util.ArrayList;
import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.bean.StockPayApplyBean;
import com.china.center.oa.finance.bean.StockPrePayApplyBean;
import com.china.center.oa.finance.constant.StockPayApplyConstant;
import com.china.center.oa.finance.dao.BankDAO;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.finance.dao.OutBillDAO;
import com.china.center.oa.finance.dao.PaymentDAO;
import com.china.center.oa.finance.dao.StockPayVSComposeDAO;
import com.china.center.oa.finance.listener.StockPayApplyListener;
import com.china.center.oa.finance.vs.StockPayVSComposeBean;
import com.china.center.oa.product.dao.ProviderDAO;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.DepartmentDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.bean.FinanceItemBean;
import com.china.center.oa.tax.bean.FinanceTagBean;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.constanst.TaxConstanst;
import com.china.center.oa.tax.constanst.TaxItemConstanst;
import com.china.center.oa.tax.dao.FinanceDAO;
import com.china.center.oa.tax.dao.TaxDAO;
import com.china.center.oa.tax.helper.FinanceHelper;
import com.china.center.oa.tax.manager.FinanceManager;
import com.china.center.oa.tax.manager.FinanceTagManager;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * TODO_OSGI 采购付款--会计付款
 * 
 * @author ZHUZHU
 * @version 2011-6-19
 * @see StockPayApplyListenerTaxGlueImpl
 * @since 3.0
 */
public class StockPayApplyListenerTaxGlueImpl implements StockPayApplyListener
{
    private DutyDAO dutyDAO = null;

    private DepartmentDAO departmentDAO = null;

    private TaxDAO taxDAO = null;

    private BankDAO bankDAO = null;

    private CommonDAO commonDAO = null;

    private ProviderDAO providerDAO = null;

    private StafferDAO stafferDAO = null;

    private FinanceManager financeManager = null;

    private FinanceDAO financeDAO = null;

    private PaymentDAO paymentDAO = null;

    private ParameterDAO parameterDAO = null;

    private InBillDAO inBillDAO = null;

    private OutBillDAO outBillDAO = null;

    private OutDAO outDAO = null;
    
    private StockPayVSComposeDAO stockPayVSComposeDAO = null;
    
    private FinanceTagManager financeTagManager = null;

    /**
     * default constructor
     */
    public StockPayApplyListenerTaxGlueImpl()
    {
    }

    /**
     * 采购付款--会计付款
     */
    public void onEndStockPayBySEC(User user, StockPayApplyBean bean, List<OutBillBean> outBillList)
        throws MYException
    {
        for (OutBillBean outBillBean : outBillList)
        {
            // 兼容性
            if ( !TaxGlueHelper.bankGoon(outBillBean.getBankId(), this.taxDAO))
            {
                continue;
            }

            BankBean bank = bankDAO.find(outBillBean.getBankId());

            if (bank == null)
            {
                throw new MYException("银行不存在,请确认操作");
            }

            FinanceBean financeBean = new FinanceBean();

            String name = "采购付款申请通过:" + bean.getId() + '.';

            financeBean.setName(name);

            financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_STOCK_PAY);

            // 付款单申请
            financeBean.setRefId(bean.getId());

            financeBean.setRefBill(outBillBean.getId());

            financeBean.setDutyId(bank.getDutyId());

            financeBean.setCreaterId(user.getStafferId());

            financeBean.setDescription(financeBean.getName());

            financeBean.setFinanceDate(TimeTools.now_short());

            financeBean.setLogTime(TimeTools.now());

            List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

            // 应付账款-供应商/银行科目
            createAddItem1(user, bean, bank, outBillBean, financeBean, itemList);

            financeBean.setItemList(itemList);

            financeManager.addFinanceBeanWithoutTransactional(user, financeBean);

        }
        
        // 生成财务 采购付款 标记数据
        processStockPayTag(user, bean);
    }

    /**
     * 应付账款-供应商/银行科目
     * 
     * @param user
     * @param bean
     * @param bank
     * @param outBillBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createAddItem1(User user, StockPayApplyBean bean, BankBean bank,
                                OutBillBean outBillBean, FinanceBean financeBean,
                                List<FinanceItemBean> itemList)
        throws MYException
    {
        // 申请人
        StafferBean staffer = stafferDAO.find(bean.getStafferId());

        if (staffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        String name = "采购付款申请通过:" + bean.getId() + '.';

        // 应付账款-供应商/银行科目
        FinanceItemBean itemIn = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn.setPareId(pareId);

        itemIn.setName("应付账款-货款:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        // 应付账款-货款(单位)
        TaxBean inTax = taxDAO.findByUnique(TaxItemConstanst.PAY_PRODUCT);

        if (inTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(inTax, itemIn);

        // 当前发生额
        double inMoney = bean.getMoneys();

        if (bean.getIsFinal() == StockPayApplyConstant.APPLY_ISFINAL_NO)
        {
        	inMoney = outBillBean.getMoneys();
        }

        itemIn.setInmoney(FinanceHelper.doubleToLong(inMoney));

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 单位
        itemIn.setUnitId(bean.getProvideId());
        itemIn.setUnitType(TaxConstanst.UNIT_TYPE_PROVIDE);

        itemList.add(itemIn);

        // 贷方
        FinanceItemBean itemOut = new FinanceItemBean();

        itemOut.setPareId(pareId);

        itemOut.setName("银行科目:" + name);

        itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut);

        // 银行科目
        TaxBean outTax = taxDAO.findByBankId(outBillBean.getBankId());

        if (outTax == null)
        {
            throw new MYException("银行[%s]缺少对应的科目,请确认操作", bank.getName());
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);

        double outMoney = outBillBean.getMoneys();

        itemOut.setInmoney(0);

        itemOut.setOutmoney(FinanceHelper.doubleToLong(outMoney));

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 NA
        itemList.add(itemOut);
        
        if (bean.getIsFinal() == StockPayApplyConstant.APPLY_ISFINAL_YES)
        {
        	// 应付与实付不一样，要生成营业外支出/营业外收入
            int forward = 0;
            String taxId = "";
            double otherMoney = 0.0d;
            
            if (bean.getRealMoneys() > bean.getMoneys()){
            	forward = 0;
            	
            	taxId = TaxItemConstanst.OTHER_PAY;
            	
            	otherMoney = bean.getRealMoneys() - bean.getMoneys();
            }else if (bean.getRealMoneys() < bean.getMoneys())
            {
            	forward = 1;
            	
            	taxId = TaxItemConstanst.EXT_RECEIVE;
            	
            	otherMoney = bean.getMoneys() - bean.getRealMoneys();
            }else{
            	// do nothing
            }
            
            if (otherMoney > 0)
            {
            	FinanceItemBean itemOther = new FinanceItemBean();

            	itemOther.setPareId(pareId);

            	itemOther.setName("营业外科目:" + name);

            	itemOther.setForward(forward);

                FinanceHelper.copyFinanceItem(financeBean, itemOther);

                // 银行科目
                TaxBean otherTax = taxDAO.find(taxId);

                if (otherTax == null)
                {
                    throw new MYException("缺少对应的科目,请确认操作");
                }

                // 科目拷贝
                FinanceHelper.copyTax(otherTax, itemOther);

                if (forward == 0){
                	itemOther.setInmoney(FinanceHelper.doubleToLong(otherMoney));
                	itemOther.setOutmoney(0);
                }
                else{
                	itemOther.setInmoney(0);
                	itemOther.setOutmoney(FinanceHelper.doubleToLong(otherMoney));
                }

                itemOther.setDescription(itemOther.getName());

                // 辅助核算 NA
                itemList.add(itemOther);
            }
        }
    }

    @Override
	public void onEndStockPrePayBySEC(User user, StockPrePayApplyBean bean,
			List<OutBillBean> outBillList) throws MYException
	{
        for (OutBillBean outBillBean : outBillList)
        {
            // 兼容性
            if ( !TaxGlueHelper.bankGoon(outBillBean.getBankId(), this.taxDAO))
            {
                continue;
            }

            BankBean bank = bankDAO.find(outBillBean.getBankId());

            if (bank == null)
            {
                throw new MYException("银行不存在,请确认操作");
            }

            FinanceBean financeBean = new FinanceBean();

            String name = "采购预付款申请通过:" + bean.getId() + '.';

            financeBean.setName(name);

            financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_STOCK_PAY);

            // 付款单申请
            financeBean.setRefId(bean.getId());

            financeBean.setRefBill(outBillBean.getId());

            financeBean.setDutyId(bank.getDutyId());

            financeBean.setCreaterId(user.getStafferId());

            financeBean.setDescription(financeBean.getName());

            financeBean.setFinanceDate(TimeTools.now_short());

            financeBean.setLogTime(TimeTools.now());

            List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

            // 应付账款-供应商/银行科目
            createAddItem2(user, bean, bank, outBillBean, financeBean, itemList);

            financeBean.setItemList(itemList);

            financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
        }
    }
    
    /**
     * 应付账款-供应商/银行科目
     * 
     * @param user
     * @param bean
     * @param bank
     * @param outBillBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createAddItem2(User user, StockPrePayApplyBean bean, BankBean bank,
                                OutBillBean outBillBean, FinanceBean financeBean,
                                List<FinanceItemBean> itemList)
        throws MYException
    {
        // 申请人
        StafferBean staffer = stafferDAO.find(bean.getStafferId());

        if (staffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        String name = "采购预付款申请通过:" + bean.getId() + '.';

        // 应付账款-供应商/银行科目
        FinanceItemBean itemIn = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn.setPareId(pareId);

        itemIn.setName("应付账款-货款:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        // 应付账款-货款(单位)
        TaxBean inTax = taxDAO.findByUnique(TaxItemConstanst.PAY_PRODUCT);

        if (inTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(inTax, itemIn);

        // 当前发生额
        double inMoney = outBillBean.getMoneys();

        itemIn.setInmoney(FinanceHelper.doubleToLong(inMoney));

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 单位
        itemIn.setUnitId(bean.getProviderId());
        itemIn.setUnitType(TaxConstanst.UNIT_TYPE_PROVIDE);

        itemList.add(itemIn);

        // 贷方
        FinanceItemBean itemOut = new FinanceItemBean();

        itemOut.setPareId(pareId);

        itemOut.setName("银行科目:" + name);

        itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut);

        // 银行科目
        TaxBean outTax = taxDAO.findByBankId(outBillBean.getBankId());

        if (outTax == null)
        {
            throw new MYException("银行[%s]缺少对应的科目,请确认操作", bank.getName());
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);

        double outMoney = outBillBean.getMoneys();

        itemOut.setInmoney(0);

        itemOut.setOutmoney(FinanceHelper.doubleToLong(outMoney));

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 NA
        itemList.add(itemOut);
    }
    
    @Override
	public void onEndRefStockPayBySEC(User user, StockPayApplyBean bean)
			throws MYException
	{
    	// 付款结束
    	if (bean.getStatus() == StockPayApplyConstant.APPLY_STATUS_END)
    	{
    		processStockPayTag(user, bean);
    	}
	}
    
    /**
     * processStockPayTag
     * @param user
     * @param outBean
     * @throws MYException
     */
    private void processStockPayTag(User user, StockPayApplyBean bean)
    throws MYException
	{
    	DutyBean duty = dutyDAO.find(bean.getDutyId());
    	
    	if (duty == null)
    	{
    		throw new MYException("数据错误");
    	}
    	
    	List<FinanceTagBean> tagList = new ArrayList<FinanceTagBean>();
    	
    	// 合成
    	if (StringTools.isNullOrNone(bean.getStockId()))
    	{
    		List<StockPayVSComposeBean> vsList = stockPayVSComposeDAO.queryEntityBeansByFK(bean.getId());
    		
    		for (StockPayVSComposeBean each : vsList)
    		{
    			saveFinanceTag(each.getStockPayApplyId(), duty, tagList);
    		}
    		
    	}else{
    		saveFinanceTag(bean.getId(), duty, tagList);
    	}
    	
    	if (ListTools.isEmptyOrNull(tagList))
    		return;
		
		financeTagManager.addFinanceTagBeanWithoutTransaction(user, tagList);
	}

	private void saveFinanceTag(String id, DutyBean duty,
			List<FinanceTagBean> tagList)
	{
		FinanceTagBean tag = new FinanceTagBean();
		
		tag.setType("PURCHASEPAY");
		tag.setTypeName("采购付款");
		tag.setFullId(id);
		tag.setStatsTime(TimeTools.now());
		
		if (duty.getMtype() == PublicConstant.MANAGER_TYPE_COMMON)
		{
			tag.setTag("FP1");
			tag.setMtype(PublicConstant.MANAGER_TYPE_COMMON);
		}else{
			tag.setTag("FG1");
			tag.setMtype(PublicConstant.MANAGER_TYPE_MANAGER);
		}
		
		tagList.add(tag);
	}
    
    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "StockPayApplyListener.TaxGlueImpl";
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
     * @return the bankDAO
     */
    public BankDAO getBankDAO()
    {
        return bankDAO;
    }

    /**
     * @param bankDAO
     *            the bankDAO to set
     */
    public void setBankDAO(BankDAO bankDAO)
    {
        this.bankDAO = bankDAO;
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
     * @return the financeDAO
     */
    public FinanceDAO getFinanceDAO()
    {
        return financeDAO;
    }

    /**
     * @param financeDAO
     *            the financeDAO to set
     */
    public void setFinanceDAO(FinanceDAO financeDAO)
    {
        this.financeDAO = financeDAO;
    }

    /**
     * @return the paymentDAO
     */
    public PaymentDAO getPaymentDAO()
    {
        return paymentDAO;
    }

    /**
     * @param paymentDAO
     *            the paymentDAO to set
     */
    public void setPaymentDAO(PaymentDAO paymentDAO)
    {
        this.paymentDAO = paymentDAO;
    }

    /**
     * @return the parameterDAO
     */
    public ParameterDAO getParameterDAO()
    {
        return parameterDAO;
    }

    /**
     * @param parameterDAO
     *            the parameterDAO to set
     */
    public void setParameterDAO(ParameterDAO parameterDAO)
    {
        this.parameterDAO = parameterDAO;
    }

    /**
     * @return the inBillDAO
     */
    public InBillDAO getInBillDAO()
    {
        return inBillDAO;
    }

    /**
     * @param inBillDAO
     *            the inBillDAO to set
     */
    public void setInBillDAO(InBillDAO inBillDAO)
    {
        this.inBillDAO = inBillDAO;
    }

    /**
     * @return the outBillDAO
     */
    public OutBillDAO getOutBillDAO()
    {
        return outBillDAO;
    }

    /**
     * @param outBillDAO
     *            the outBillDAO to set
     */
    public void setOutBillDAO(OutBillDAO outBillDAO)
    {
        this.outBillDAO = outBillDAO;
    }

    /**
     * @return the outDAO
     */
    public OutDAO getOutDAO()
    {
        return outDAO;
    }

    /**
     * @param outDAO
     *            the outDAO to set
     */
    public void setOutDAO(OutDAO outDAO)
    {
        this.outDAO = outDAO;
    }

	/**
	 * @return the stockPayVSComposeDAO
	 */
	public StockPayVSComposeDAO getStockPayVSComposeDAO()
	{
		return stockPayVSComposeDAO;
	}

	/**
	 * @param stockPayVSComposeDAO the stockPayVSComposeDAO to set
	 */
	public void setStockPayVSComposeDAO(StockPayVSComposeDAO stockPayVSComposeDAO)
	{
		this.stockPayVSComposeDAO = stockPayVSComposeDAO;
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
