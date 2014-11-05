/**
 * File Name: PaymentListenerTaxGlueImpl.java<br>
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
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.finance.bean.PaymentBean;
import com.china.center.oa.finance.dao.BankDAO;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.finance.dao.OutBillDAO;
import com.china.center.oa.finance.listener.PaymentListener;
import com.china.center.oa.product.dao.ProviderDAO;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.DepartmentDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.bean.FinanceItemBean;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.constanst.TaxConstanst;
import com.china.center.oa.tax.dao.FinanceDAO;
import com.china.center.oa.tax.dao.FinanceItemDAO;
import com.china.center.oa.tax.dao.TaxDAO;
import com.china.center.oa.tax.helper.FinanceHelper;
import com.china.center.oa.tax.manager.FinanceManager;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.TimeTools;


/**
 * TODO_OSGI 回款认领/回款退领
 * 
 * @author ZHUZHU
 * @version 2011-6-8
 * @see PaymentListenerTaxGlueImpl
 * @since 3.0
 */
public class PaymentListenerTaxGlueImpl implements PaymentListener
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
    
    private FinanceItemDAO financeItemDAO = null;

    private InBillDAO inBillDAO = null;

    private OutBillDAO outBillDAO = null;

    /**
     * default constructor
     */
    public PaymentListenerTaxGlueImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.finance.listener.PaymentListener#onAddBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.finance.bean.PaymentBean)
     */
    public void onAddBean(User user, PaymentBean bean)
        throws MYException
    {
        if ( !TaxGlueHelper.bankGoon(bean.getBankId(), this.taxDAO))
        {
            return;
        }

        BankBean bank = bankDAO.find(bean.getBankId());

        if (bank == null)
        {
            throw new MYException("银行不存在,请确认操作");
        }

        FinanceBean financeBean = new FinanceBean();

        String name = user.getStafferName() + "导入回款:" + bean.getId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_PAYBACK_ADD);

        financeBean.setRefId(bean.getId());

        financeBean.setDutyId(bank.getDutyId());

        financeBean.setCreaterId(user.getStafferId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 借:银行科目 贷:银行对应的暂记户科目
        createAddItem1(user, bean, bank, financeBean, itemList);

        // 手续费
        if (bean.getHandling() > 0)
        {
            createAddItem2(user, bean, bank, financeBean, itemList);
        }

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }

    /**
     * createAddItem
     * 
     * @param user
     * @param bean
     * @param bank
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createAddItem1(User user, PaymentBean bean, BankBean bank,
                                FinanceBean financeBean, List<FinanceItemBean> itemList)
        throws MYException
    {
        String name = user.getStafferName() + "导入回款:" + bean.getId() + '.';

        // 借:库存商品 贷:应付账款-供应商
        FinanceItemBean itemIn = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn.setPareId(pareId);

        itemIn.setName("银行科目:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        TaxBean inTax = taxDAO.findByBankId(bank.getId());

        if (inTax == null)
        {
            throw new MYException("银行[%s]缺少对应科目,请确认操作", bank.getName());
        }

        // 科目拷贝
        FinanceHelper.copyTax(inTax, itemIn);

        double inMoney = bean.getMoney();

        itemIn.setInmoney(FinanceHelper.doubleToLong(inMoney));

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 NA
        itemList.add(itemIn);

        // 贷方
        FinanceItemBean itemOut = new FinanceItemBean();

        itemOut.setPareId(pareId);

        itemOut.setName("银行暂记户:" + name);

        itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut);

        TaxBean outTax = taxDAO.findTempByBankId(bank.getId());

        if (outTax == null)
        {
            throw new MYException("银行[%s]缺少暂记户科目,请确认操作", bank.getName());
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);

        double outMoney = bean.getMoney();

        itemOut.setInmoney(0);

        itemOut.setOutmoney(FinanceHelper.doubleToLong(outMoney));

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 NA
        itemList.add(itemOut);
    }

    /**
     * 手续费(银行科目/银行对应的暂记户科目)
     * 
     * @param user
     * @param bean
     * @param bank
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createAddItem2(User user, PaymentBean bean, BankBean bank,
                                FinanceBean financeBean, List<FinanceItemBean> itemList)
        throws MYException
    {
        String name = user.getStafferName() + "导入回款的手续费:" + bean.getId() + '.';

        // 借:库存商品 贷:应付账款-供应商
        FinanceItemBean itemIn = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn.setPareId(pareId);

        itemIn.setName("银行科目:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        TaxBean inTax = taxDAO.findByBankId(bank.getId());

        if (inTax == null)
        {
            throw new MYException("银行[%s]缺少对于科目,请确认操作", bank.getName());
        }

        // 科目拷贝
        FinanceHelper.copyTax(inTax, itemIn);

        double inMoney = bean.getHandling();

        // 导入回款手续费银行科目的金额减少
        itemIn.setInmoney( -FinanceHelper.doubleToLong(inMoney));

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 NA
        itemList.add(itemIn);

        // 贷方
        FinanceItemBean itemOut = new FinanceItemBean();

        itemOut.setPareId(pareId);

        itemOut.setName("银行暂记户:" + name);

        itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut);

        TaxBean outTax = taxDAO.findTempByBankId(bank.getId());

        if (outTax == null)
        {
            throw new MYException("银行[%s]缺少暂记户科目,请确认操作", bank.getName());
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);

        double outMoney = bean.getHandling();

        itemOut.setInmoney(0);

        itemOut.setOutmoney( -FinanceHelper.doubleToLong(outMoney));

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 NA
        itemList.add(itemOut);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.finance.listener.PaymentListener#onDeleteBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.finance.bean.PaymentBean)
     */
    public void onDeleteBean(User user, PaymentBean bean)
        throws MYException
    {
        // 删除相关的凭证
        List<FinanceBean> list = financeDAO.queryEntityBeansByFK(bean.getId());

        for (FinanceBean financeBean : list)
        {
            // 删除产生的凭证
            // financeManager.deleteFinanceBeanWithoutTransactional(user, financeBean.getId());
        	// 不删除，改为对冲
        	duichong(financeBean);
        }
    }

	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 *
	 * @param financeBean
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private void duichong(FinanceBean financeBean) {
		FinanceBean newFinanceBean = new FinanceBean();
		
		BeanUtil.copyProperties(newFinanceBean, financeBean);
		
		newFinanceBean.setId(commonDAO.getSquenceString20("PZ"));
		newFinanceBean.setName(newFinanceBean.getId());
		newFinanceBean.setInmoney(-newFinanceBean.getInmoney());
		newFinanceBean.setOutmoney(-newFinanceBean.getOutmoney());
		newFinanceBean.setFinanceDate(TimeTools.now_short());
		newFinanceBean.setLogTime(TimeTools.now());
		newFinanceBean.setDescription(newFinanceBean.getDescription() + ",退领凭证对冲");
		
		List<FinanceItemBean> itemList = financeItemDAO.queryEntityBeansByFK(financeBean.getId());
		
		List<FinanceItemBean> newItemList = new ArrayList<FinanceItemBean>();
		
		for (FinanceItemBean each : itemList) {
			FinanceItemBean newItem = new FinanceItemBean();
			
			BeanUtil.copyProperties(newItem, each);
			
			newItem.setId(commonDAO.getSquenceString20());
			newItem.setPid(newFinanceBean.getId());
			newItem.setName(newItem.getId());
			newItem.setPareId(commonDAO.getSquenceString());
			newItem.setInmoney(-newItem.getInmoney());
			newItem.setOutmoney(-newItem.getOutmoney());
			newItem.setFinanceDate(newFinanceBean.getFinanceDate());
			newItem.setLogTime(newFinanceBean.getLogTime());
			
			newItemList.add(newItem);
		}
		
		financeDAO.saveEntityBean(newFinanceBean);
		
		financeItemDAO.saveAllEntityBeans(newItemList);
	}

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "PaymentListener.TaxGlueImpl";
    }

    public void onDropBean(User user, PaymentBean bean)
        throws MYException
    {
        if ( !TaxGlueHelper.bankGoon(bean.getBankId(), this.taxDAO))
        {
            return;
        }

        // 回款退领 删除此回款所有的回款认领的凭证
        List<FinanceBean> list = financeDAO.queryEntityBeansByFK(bean.getId());

        for (FinanceBean financeBean : list)
        {
            // 删除回款认领的凭证,只保留一个回款增加的凭证
            if (financeBean.getCreateType() != TaxConstanst.FINANCE_CREATETYPE_PAYBACK_ADD)
            {
            	// 删除产生的凭证
                // financeManager.deleteFinanceBeanWithoutTransactional(user, financeBean.getId());
            	// 不删除，改为对冲
                duichong(financeBean);
            }
        }
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
	 * @return the financeItemDAO
	 */
	public FinanceItemDAO getFinanceItemDAO() {
		return financeItemDAO;
	}

	/**
	 * @param financeItemDAO the financeItemDAO to set
	 */
	public void setFinanceItemDAO(FinanceItemDAO financeItemDAO) {
		this.financeItemDAO = financeItemDAO;
	}
}
