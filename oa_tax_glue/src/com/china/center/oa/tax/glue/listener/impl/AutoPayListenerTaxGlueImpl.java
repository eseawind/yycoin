package com.china.center.oa.tax.glue.listener.impl;

import java.util.ArrayList;
import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.bean.PaymentBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.BankDAO;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.finance.dao.PaymentDAO;
import com.china.center.oa.finance.listener.AutoPayListener;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.vo.UserVO;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.bean.FinanceItemBean;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.constanst.TaxConstanst;
import com.china.center.oa.tax.constanst.TaxItemConstanst;
import com.china.center.oa.tax.dao.TaxDAO;
import com.china.center.oa.tax.helper.FinanceHelper;
import com.china.center.oa.tax.manager.FinanceManager;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

public class AutoPayListenerTaxGlueImpl implements AutoPayListener
{
	private TaxDAO taxDAO = null;
	
	private BankDAO bankDAO = null;
	
	private InBillDAO inBillDAO = null;
	
	private CommonDAO commonDAO = null;

	private StafferDAO stafferDAO = null;
	
	private PaymentDAO paymentDAO = null;
	
	private OutDAO outDAO = null;
	
	private FinanceManager financeManager = null;
	
	public String getListenerType()
	{
		return "AutoPayListener.TaxGlueImpl";
	}

	public void onCompletePaymentToPre(PaymentBean payment) throws MYException
	{
        if (payment == null) {
            throw new MYException("数据错误,请确认操作");
        }

        if (!TaxGlueHelper.bankGoon(payment.getBankId(), this.taxDAO)) {
            return;
        }

        BankBean bank = bankDAO.find(payment.getBankId());

        if (bank == null) {
            throw new MYException("银行不存在,请确认操作");
        }

        List<InBillBean> inList = inBillDAO.queryEntityBeansByFK(payment.getId(),
                AnoConstant.FK_FIRST);

        if (ListTools.isEmptyOrNull(inList)) {
            throw new MYException("缺少对应的收款单,请确认操作");
        }

        for (InBillBean inBillBean : inList) {
                        
            // 预收
            if (inBillBean.getStatus() == FinanceConstant.INBILL_STATUS_NOREF) {
                // 第一个全凭证
                mainFinance(payment, bank, inBillBean);

                return;
            }
        }
        
        return;
	}
	
    /**
     * 第一个全凭证
     * 
     * @param user
     * @param apply
     * @param item
     * @param payment
     * @param bank
     * @param inBillBean
     * @throws MYException
     */
    private void mainFinance(PaymentBean payment, BankBean bank, InBillBean inBillBean) throws MYException {
        FinanceBean financeBean = new FinanceBean();

        String name = "系统通过回款认领(转预收):" + payment.getId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_BILL_GETPAY);

        // 这里也是关联的回款单号
        financeBean.setRefId(payment.getId());

        financeBean.setRefBill(inBillBean.getId());

        financeBean.setDutyId(bank.getDutyId());

        financeBean.setCreaterId(StafferConstant.SUPER_STAFFER);

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 借:银行科目 贷:银行对应的暂记户科目
        createAddItem1(payment, bank, inBillBean, financeBean, itemList);

        financeBean.setItemList(itemList);

        UserVO user = new UserVO();
        
        user.setStafferId(StafferConstant.SUPER_STAFFER);
        
        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }
    
    /**
     * 银行对应的暂记户科目（没有手续费）/(1)应收账款(2)预收账款
     * 
     * @param user
     * @param bean
     * @param bank
     * @param apply
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createAddItem1(PaymentBean payment, BankBean bank, InBillBean inBillBean, FinanceBean financeBean,
            List<FinanceItemBean> itemList) throws MYException {
        String name = "系统通过回款认领:" + payment.getId() + '.';

        // 银行对应的暂记户科目（没有手续费）/(1)应收账款(2)预收账款
        FinanceItemBean itemIn = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn.setPareId(pareId);

        itemIn.setName("银行暂记户:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        TaxBean inTax = taxDAO.findTempByBankId(bank.getId());

        if (inTax == null) {
            throw new MYException("银行[%s]缺少对应的暂记户科目,请确认操作", bank.getName());
        }

        // 科目拷贝
        FinanceHelper.copyTax(inTax, itemIn);

        // 当前发生额
        // double inMoney = item.getMoneys();
        double inMoney = payment.getMoney();

        itemIn.setInmoney(FinanceHelper.doubleToLong(inMoney));

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 NA
        itemList.add(itemIn);

        // 贷方
        FinanceItemBean itemOut = new FinanceItemBean();

        itemOut.setPareId(pareId);

        itemOut.setName("预收账款:" + name);

        itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut);

        // 预收账款(客户/职员/部门)
        TaxBean outTax = taxDAO.findByUnique(TaxItemConstanst.PREREVEIVE_PRODUCT);

        if (outTax == null) {
            throw new MYException("数据错误,请确认操作");
        }

        // 申请人
        StafferBean staffer = stafferDAO.find(inBillBean.getOwnerId());

        if (staffer == null) {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);

        // double outMoney = item.getMoneys();
        double outMoney = payment.getMoney(); // 回款全部先转成预收

        itemOut.setInmoney(0);

        itemOut.setOutmoney(FinanceHelper.doubleToLong(outMoney));

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 客户/职员/部门
        itemOut.setDepartmentId(staffer.getPrincipalshipId());
        itemOut.setStafferId(inBillBean.getOwnerId());
        itemOut.setUnitId(inBillBean.getCustomerId());
        itemOut.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemOut);
    }
    
    /**
     * 自动勾款, 预收-应收凭证
     * {@inheritDoc}
     */
	public void onRefInbillToSail(List<InBillBean> billList) throws MYException
	{
        UserVO user = new UserVO();
        
        user.setStafferId(StafferConstant.SUPER_STAFFER);
        
		for(InBillBean each : billList)
		{
			preToPay(user, each);
		}
	}

	/**
     * 预收转应收（勾款/销售单绑定）-会计审核(预收账款/应收账款)
     * 
     * @param user
     * @param apply
     * @param item
     * @throws MYException
     */
    private void preToPay(User user, InBillBean bill)
            throws MYException {
    	
        boolean flag = false;
        
        String bankId = "";
        
        String inBillId = bill.getId();
        
        InBillBean inBill = inBillDAO.find(inBillId);
        
        if (null != inBill){
            
            if (inBill.getType() ==  FinanceConstant.INBILL_TYPE_BADOUT){
                
                flag = true;
            }
        }
        
        PaymentBean payment = null;
        
        if (!flag){
            payment = paymentDAO.find(bill.getPaymentId());
    
            if (payment == null) {
                throw new MYException("数据错误,请确认操作");
            }
    
            if (!TaxGlueHelper.bankGoon(payment.getBankId(), this.taxDAO)) {
                return;
            }

            bankId = payment.getBankId();
        }else
        {
            bankId = FinanceConstant.BANK_BADDEBT;
        }
        BankBean bank = bankDAO.find(bankId);

        if (bank == null) {
            throw new MYException("银行不存在,请确认操作");
        }

        mainFinanceInPreToPay(user, bill, payment, bank);
    }
    
    /**
     * 预收转应收（勾款/销售单绑定）-会计审核
     * 
     * @param user
     * @param apply
     * @param item
     * @param payment
     * @param bank
     * @throws MYException
     */
    private void mainFinanceInPreToPay(User user, InBillBean bill,
            PaymentBean payment, BankBean bank) throws MYException {
        FinanceBean financeBean = new FinanceBean();

        String name = user.getStafferName() + "通过预收转应收(销售单关联):" + payment.getId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_BILL_MAYTOREAL);

        // 这里也是关联的回款单号
        financeBean.setRefId(payment.getId());

        financeBean.setRefBill(bill.getId());

        financeBean.setRefOut(bill.getOutId());

        financeBean.setDutyId(bank.getDutyId());

        financeBean.setCreaterId(user.getStafferId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 预收账款/应收账款
        createAddItem4(user, payment, bank, bill, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }
    
    /**
     * 预收账款/应收账款
     * 
     * @param user
     * @param bean
     * @param bank
     * @param apply
     * @param item
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createAddItem4(User user, PaymentBean bean, BankBean bank,
            InBillBean bill, FinanceBean financeBean, List<FinanceItemBean> itemList)
            throws MYException {
        // 申请人
        StafferBean staffer = stafferDAO.find(bill.getOwnerId());

        if (staffer == null) {
            throw new MYException("数据错误,请确认操作");
        }

        OutBean outBean = outDAO.find(bill.getOutId());

        if (outBean == null) {
            throw new MYException("数据错误,请确认操作");
        }

        String name = user.getStafferName() + "通过预收转应收(销售单关联):" + bill.getPaymentId() + '.';

        // 银行对应的暂记户科目（没有手续费）/应收账款
        FinanceItemBean itemIn = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn.setPareId(pareId);

        itemIn.setName("预收账款:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        TaxBean inTax = taxDAO.findByUnique(TaxItemConstanst.PREREVEIVE_PRODUCT);

        if (inTax == null) {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(inTax, itemIn);

        // 当前发生额
        double inMoney = bill.getMoneys();

        itemIn.setInmoney(FinanceHelper.doubleToLong(inMoney));

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 客户/职员/部门
        itemIn.setDepartmentId(staffer.getPrincipalshipId());
        itemIn.setStafferId(staffer.getId());
        itemIn.setUnitId(outBean.getCustomerId());
        itemIn.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemIn);

        // 贷方
        FinanceItemBean itemOut = new FinanceItemBean();

        itemOut.setPareId(pareId);

        itemOut.setName("应收账款:" + name);

        itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut);

        // 应收账款(客户/职员/部门)
        TaxBean outTax = taxDAO.findByUnique(TaxItemConstanst.REVEIVE_PRODUCT);

        if (outTax == null) {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);

        double outMoney = bill.getMoneys();

        itemOut.setInmoney(0);

        itemOut.setOutmoney(FinanceHelper.doubleToLong(outMoney));

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 客户/职员/部门
        copyDepartment(outBean, itemOut);
        itemOut.setStafferId(outBean.getStafferId());
        itemOut.setUnitId(outBean.getCustomerId());
        itemOut.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemOut);
    }
    
    private void copyDepartment(OutBean outBean, FinanceItemBean item)
    {
    	StafferBean sb = stafferDAO.find(outBean.getStafferId());

        if ( !StringTools.isNullOrNone(sb.getIndustryId3()))
        {
            item.setDepartmentId(sb.getIndustryId3());

            return;
        }            
        
        if ( !StringTools.isNullOrNone(sb.getIndustryId2()))
        {
            item.setDepartmentId(sb.getIndustryId2());

            return;
        }

        if ( !StringTools.isNullOrNone(sb.getIndustryId()))
        {
            item.setDepartmentId(sb.getIndustryId());

            return;
        }
    }
    
	/**
	 * @return the taxDAO
	 */
	public TaxDAO getTaxDAO()
	{
		return taxDAO;
	}

	/**
	 * @param taxDAO the taxDAO to set
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
	 * @param bankDAO the bankDAO to set
	 */
	public void setBankDAO(BankDAO bankDAO)
	{
		this.bankDAO = bankDAO;
	}

	/**
	 * @return the inBillDAO
	 */
	public InBillDAO getInBillDAO()
	{
		return inBillDAO;
	}

	/**
	 * @param inBillDAO the inBillDAO to set
	 */
	public void setInBillDAO(InBillDAO inBillDAO)
	{
		this.inBillDAO = inBillDAO;
	}

	/**
	 * @return the commonDAO
	 */
	public CommonDAO getCommonDAO()
	{
		return commonDAO;
	}

	/**
	 * @param commonDAO the commonDAO to set
	 */
	public void setCommonDAO(CommonDAO commonDAO)
	{
		this.commonDAO = commonDAO;
	}

	/**
	 * @return the stafferDAO
	 */
	public StafferDAO getStafferDAO()
	{
		return stafferDAO;
	}

	/**
	 * @param stafferDAO the stafferDAO to set
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
	 * @param financeManager the financeManager to set
	 */
	public void setFinanceManager(FinanceManager financeManager)
	{
		this.financeManager = financeManager;
	}

	/**
	 * @return the paymentDAO
	 */
	public PaymentDAO getPaymentDAO()
	{
		return paymentDAO;
	}

	/**
	 * @param paymentDAO the paymentDAO to set
	 */
	public void setPaymentDAO(PaymentDAO paymentDAO)
	{
		this.paymentDAO = paymentDAO;
	}

	/**
	 * @return the outDAO
	 */
	public OutDAO getOutDAO()
	{
		return outDAO;
	}

	/**
	 * @param outDAO the outDAO to set
	 */
	public void setOutDAO(OutDAO outDAO)
	{
		this.outDAO = outDAO;
	}
}
