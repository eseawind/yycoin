/**
 * File Name: TcpPayListenerTaxGlueImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-9-13<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.glue.listener.impl;


import java.util.ArrayList;
import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.dao.BankDAO;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.finance.dao.OutBillDAO;
import com.china.center.oa.finance.dao.PaymentDAO;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.DepartmentDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.bean.FinanceItemBean;
import com.china.center.oa.tax.bean.FinanceMonthBean;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.constanst.TaxConstanst;
import com.china.center.oa.tax.constanst.TaxItemConstanst;
import com.china.center.oa.tax.dao.FinanceDAO;
import com.china.center.oa.tax.dao.FinanceItemDAO;
import com.china.center.oa.tax.dao.FinanceMonthDAO;
import com.china.center.oa.tax.dao.TaxDAO;
import com.china.center.oa.tax.helper.FinanceHelper;
import com.china.center.oa.tax.manager.FinanceManager;
import com.china.center.oa.tcp.bean.AbstractTcpBean;
import com.china.center.oa.tcp.bean.ExpenseApplyBean;
import com.china.center.oa.tcp.bean.RebateApplyBean;
import com.china.center.oa.tcp.bean.TravelApplyBean;
import com.china.center.oa.tcp.constanst.TcpConstanst;
import com.china.center.oa.tcp.listener.TcpPayListener;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * 报销生成凭证的地方
 * 
 * @author ZHUZHU
 * @version 2011-9-13
 * @see TcpPayListenerTaxGlueImpl
 * @since 1.0
 */
public class TcpPayListenerTaxGlueImpl implements TcpPayListener
{
    private DutyDAO dutyDAO = null;

    private DepartmentDAO departmentDAO = null;

    private TaxDAO taxDAO = null;

    private BankDAO bankDAO = null;

    private CommonDAO commonDAO = null;

    private StafferDAO stafferDAO = null;

    private FinanceManager financeManager = null;

    private FinanceDAO financeDAO = null;

    private PaymentDAO paymentDAO = null;

    private ParameterDAO parameterDAO = null;

    private InBillDAO inBillDAO = null;

    private OutBillDAO outBillDAO = null;
    
    private FinanceMonthDAO financeMonthDAO = null;
    
    private FinanceItemDAO financeItemDAO = null;

    /**
     * default constructor
     */
    public TcpPayListenerTaxGlueImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.tcp.listener.TcpPayListener#onPayTravelApply(com.center.china.osgi.publics.User,
     *      com.china.center.oa.tcp.bean.TravelApplyBean, java.util.List)
     */
    public void onPayTravelApply(User user, TravelApplyBean bean, List<OutBillBean> outBillList)
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

            String name = bean.getDescription()+","+DefinedCommon.getValue("tcpType", bean.getType()) + "申请通过:"
                          + bean.getId() + '.';

            financeBean.setName(name);

            fillType(bean, financeBean);

            // 付款单申请
            financeBean.setRefId(bean.getId());

            financeBean.setRefBill(outBillBean.getId());

            financeBean.setDutyId(bank.getDutyId());

            financeBean.setCreaterId(user.getStafferId());

            financeBean.setDescription(financeBean.getName());

            financeBean.setFinanceDate(TimeTools.now_short());

            financeBean.setLogTime(TimeTools.now());

            List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

            // 中收
            if (bean.getType() == TcpConstanst.TCP_APPLYTYPE_MID) {
            	// 营业费用-中收/银行科目
            	createAddItem11(user, bean, bank, outBillBean, financeBean, itemList);
            } else {
            	// 应付账款-供应商/银行科目
                createAddItem1(user, bean, bank, outBillBean, financeBean, itemList);
            }

            financeBean.setItemList(itemList);

            financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
        }

    }

    /**
     * 凭证类型
     * 
     * @param bean
     * @param financeBean
     */
    private void fillType(AbstractTcpBean bean, FinanceBean financeBean)
    {
        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        if (bean.getType() == TcpConstanst.TCP_APPLYTYPE_TRAVEL)
        {
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_TCP_BORROW);
        }
        else if (bean.getType() == TcpConstanst.TCP_APPLYTYPE_ENTERTAIN)
        {
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_TCP_ENTERTAIN);
        }
        else if (bean.getType() == TcpConstanst.TCP_APPLYTYPE_STOCK)
        {
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_TCP_STOCK);
        }
        else if (bean.getType() == TcpConstanst.TCP_APPLYTYPE_PUBLIC)
        {
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_TCP_PUBLIC);
        }
        else if (bean.getType() == TcpConstanst.TCP_EXPENSETYPE_TRAVEL)
        {
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_EXPENSE_BORROW);
        }
        else if (bean.getType() == TcpConstanst.TCP_EXPENSETYPE_ENTERTAIN)
        {
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_EXPENSE_ENTERTAIN);
        }
        else if (bean.getType() == TcpConstanst.TCP_EXPENSETYPE_PUBLIC)
        {
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_EXPENSE_PUBLIC);
        }
        else if (bean.getType() == TcpConstanst.TCP_EXPENSETYPE_COMMON)
        {
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_EXPENSE_COMMON);
        }
        else
        {
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_TCP_BORROW);
        }
    }

    /**
     * 费用合计是借款金额+支付金额,同时把之前的借款需要平账
     */
    public void onPayExpenseApply(User user, ExpenseApplyBean bean, List<OutBillBean> outBillList,
                                  List<InBillBean> inBillList)
        throws MYException
    {
        // 这里不可能既收款又付款
        if ( !ListTools.isEmptyOrNull(outBillList) && !ListTools.isEmptyOrNull(inBillList))
        {
            throw new MYException("报销不可能同时存在收款和付款,请确认操作");
        }

        // 公司支付
        if ( !ListTools.isEmptyOrNull(outBillList) && ListTools.isEmptyOrNull(inBillList))
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

                String name = bean.getDescription()+","+DefinedCommon.getValue("tcpType", bean.getType()) + "申请通过:"
                              + bean.getId() + '.';

                financeBean.setName(name);

                fillType(bean, financeBean);

                // 付款单申请
                financeBean.setRefId(bean.getId());

                financeBean.setRefBill(outBillBean.getId());

                financeBean.setDutyId(bank.getDutyId());

                financeBean.setCreaterId(user.getStafferId());

                financeBean.setDescription(financeBean.getName());

                financeBean.setFinanceDate(TimeTools.now_short());

                financeBean.setLogTime(TimeTools.now());

                List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

                // 借:其他应收款-借款/贷:现金或银行
                createAddItem2(user, bean, bank, outBillBean, financeBean, itemList);

                financeBean.setItemList(itemList);

                financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
            }
        }

        // 员工还款
        if (ListTools.isEmptyOrNull(outBillList) && !ListTools.isEmptyOrNull(inBillList))
        {
            for (InBillBean inBillBean : inBillList)
            {
                // 兼容性
                if ( !TaxGlueHelper.bankGoon(inBillBean.getBankId(), this.taxDAO))
                {
                    continue;
                }

                BankBean bank = bankDAO.find(inBillBean.getBankId());

                if (bank == null)
                {
                    throw new MYException("银行不存在,请确认操作");
                }

                FinanceBean financeBean = new FinanceBean();

                String name = bean.getDescription()+","+DefinedCommon.getValue("tcpType", bean.getType()) + "申请通过:"
                              + bean.getId() + '.';

                financeBean.setName(name);

                fillType(bean, financeBean);

                // 收款单申请
                financeBean.setRefId(bean.getId());

                financeBean.setRefBill(inBillBean.getId());

                financeBean.setDutyId(bank.getDutyId());

                financeBean.setCreaterId(user.getStafferId());

                financeBean.setDescription(financeBean.getName());

                financeBean.setFinanceDate(TimeTools.now_short());

                financeBean.setLogTime(TimeTools.now());

                List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

                // 借:其他应收款-借款/贷:现金或银行
                createAddItem3(user, bean, bank, inBillBean, financeBean, itemList);

                financeBean.setItemList(itemList);

                financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
            }
        }

    }

    /**
     * 财务入账 借:各个费用科目 贷:备用金
     */
    public void onEndExpenseApply(User user, ExpenseApplyBean bean, List<String> taxIdList,
                                  List<Long> moneyList, List<String> stafferIdList)
        throws MYException
    {
        FinanceBean financeBean = new FinanceBean();

        String name = bean.getDescription()+","+DefinedCommon.getValue("tcpType", bean.getType()) + "报销最终通过:" + bean.getId()
                      + '.';

        financeBean.setName(name);

        fillType(bean, financeBean);

        financeBean.setRefId(bean.getId());

        financeBean.setDutyId(bean.getDutyId());

        financeBean.setCreaterId(user.getStafferId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 各种费用/备用金
        createAddItem4(user, bean, taxIdList, moneyList, financeBean, itemList, stafferIdList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }

    /**
     * 其他应收款-借款/银行科目
     * 
     * @param user
     * @param bean
     * @param bank
     * @param outBillBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createAddItem1(User user, TravelApplyBean bean, BankBean bank,
                                OutBillBean outBillBean, FinanceBean financeBean,
                                List<FinanceItemBean> itemList)
        throws MYException
    {
        // 借款人
        StafferBean staffer = stafferDAO.find(bean.getBorrowStafferId());

        if (staffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        String name = "出差申请借款:" + bean.getId() + '.';

        FinanceItemBean itemIn = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn.setPareId(pareId);

        itemIn.setName("其他应收款_备用金:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        // 其他应收款_备用金(部门/职员)
        TaxBean inTax = taxDAO.findByUnique(TaxItemConstanst.OTHER_RECEIVE_BORROW);

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

        // 辅助核算 部门和职员
        itemIn.setDepartmentId(staffer.getPrincipalshipId());
        itemIn.setStafferId(staffer.getId());

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
    
    /**
     * 营业费用-中收/银行科目
     * 
     * @param user
     * @param bean
     * @param bank
     * @param outBillBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createAddItem11(User user, TravelApplyBean bean, BankBean bank,
                                OutBillBean outBillBean, FinanceBean financeBean,
                                List<FinanceItemBean> itemList)
        throws MYException
    {
        // 借款人
        StafferBean staffer = stafferDAO.find(bean.getBorrowStafferId());

        if (staffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        String name = "中收申请借款:" + bean.getId() + '.';

        FinanceItemBean itemIn = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn.setPareId(pareId);

        itemIn.setName("营业费用-中收:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        // 其他应收款_备用金(部门/职员)
        TaxBean inTax = taxDAO.findByUnique(TaxItemConstanst.SALE_FEE_MID);

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

        // 辅助核算 部门和职员
        itemIn.setDepartmentId(staffer.getPrincipalshipId());
        itemIn.setStafferId(staffer.getId());

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

    /**
     * 其他应收款-借款/银行科目
     * 
     * @param user
     * @param bean
     * @param bank
     * @param outBillBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createAddItem2(User user, ExpenseApplyBean bean, BankBean bank,
                                OutBillBean outBillBean, FinanceBean financeBean,
                                List<FinanceItemBean> itemList)
        throws MYException
    {
        // 收款人
        StafferBean staffer = stafferDAO.find(bean.getBorrowStafferId());

        if (staffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        String name = "出差报销公司支付剩余金额:" + bean.getId() + '.';

        FinanceItemBean itemIn = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn.setPareId(pareId);

        itemIn.setName("其他应收款_备用金:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        // 其他应收款_备用金(部门/职员)
        TaxBean inTax = taxDAO.findByUnique(TaxItemConstanst.OTHER_RECEIVE_BORROW);

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

        // 辅助核算 部门和职员
        itemIn.setDepartmentId(staffer.getPrincipalshipId());
        itemIn.setStafferId(staffer.getId());

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

    /**
     * 其他应收款-借款(负数)/银行科目(负数)
     * 
     * @param user
     * @param bean
     * @param bank
     * @param outBillBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createAddItem3(User user, ExpenseApplyBean bean, BankBean bank,
                                InBillBean outBillBean, FinanceBean financeBean,
                                List<FinanceItemBean> itemList)
        throws MYException
    {
        // 收款人
        StafferBean staffer = stafferDAO.find(bean.getBorrowStafferId());

        if (staffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        String name = "出差报销员工还款金额:" + bean.getId() + '.';

        FinanceItemBean itemIn = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn.setPareId(pareId);

        itemIn.setName("其他应收款_备用金:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        // 其他应收款_备用金(部门/职员)
        TaxBean inTax = taxDAO.findByUnique(TaxItemConstanst.OTHER_RECEIVE_BORROW);

        if (inTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(inTax, itemIn);

        // 当前发生额
        double inMoney = outBillBean.getMoneys();

        itemIn.setInmoney( -FinanceHelper.doubleToLong(inMoney));

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 部门和职员
        itemIn.setDepartmentId(staffer.getPrincipalshipId());
        itemIn.setStafferId(staffer.getId());

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

        itemOut.setOutmoney( -FinanceHelper.doubleToLong(outMoney));

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 NA
        itemList.add(itemOut);
    }

    /**
     * 各种费用/备用金
     * 
     * @param user
     * @param bean
     * @param bank
     * @param outBillBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createAddItem4(User user, ExpenseApplyBean bean, List<String> taxIds,
                                List<Long> moneyList, FinanceBean financeBean,
                                List<FinanceItemBean> itemList, List<String> stafferIdList)
        throws MYException
    {
        // 收款人
        StafferBean staffer = stafferDAO.find(bean.getBorrowStafferId());

        if (staffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        String name = "报销最终入账:" + bean.getId() + '.';

        String pareId = commonDAO.getSquenceString();

        long total = 0L;

        for (int i = 0; i < taxIds.size(); i++ )
        {
            String eachTaxId = taxIds.get(i);

            StafferBean inStaffer = null;

            // 通用报销可能费用的花销人是A，但是收款人是B
            if (StringTools.isNullOrNone(stafferIdList.get(i)))
            {
                inStaffer = staffer;
            }
            else
            {
                inStaffer = stafferDAO.find(stafferIdList.get(i));

                if (inStaffer == null)
                {
                    throw new MYException("数据错误,请确认操作");
                }
            }

            FinanceItemBean itemIn = new FinanceItemBean();

            itemIn.setPareId(pareId);

            itemIn.setName("报销费用:" + name);

            itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemIn);

            // 费用科目
            TaxBean inTax = taxDAO.findByUnique(eachTaxId);

            if (inTax == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 科目拷贝
            FinanceHelper.copyTax(inTax, itemIn);

            itemIn.setInmoney(moneyList.get(i));

            total += moneyList.get(i);

            itemIn.setOutmoney(0);

            itemIn.setDescription(itemIn.getName());

            // 辅助核算 部门和职员
            itemIn.setDepartmentId(inStaffer.getPrincipalshipId());
            itemIn.setStafferId(inStaffer.getId());

            itemList.add(itemIn);
        }

        // 贷方
        FinanceItemBean itemOut = new FinanceItemBean();

        itemOut.setPareId(pareId);

        itemOut.setName("其他应收款_备用金:" + name);

        itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut);

        // 其他应收款_备用金
        TaxBean outTax = taxDAO.findByUnique(TaxItemConstanst.OTHER_RECEIVE_BORROW);

        if (outTax == null)
        {
            throw new MYException("缺少其他应收款_备用金,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);

        itemOut.setInmoney(0);

        itemOut.setOutmoney(total);

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 部门和职员
        itemOut.setDepartmentId(staffer.getPrincipalshipId());
        itemOut.setStafferId(staffer.getId());

        // 辅助核算 NA
        itemList.add(itemOut);
    }

    public void onLastEndExpenseApply(User user, ExpenseApplyBean bean, String check)
        throws MYException
    {
        financeManager.updateRefCheckByRefIdWithoutTransactional(bean.getId(), check);
    }

    /**
     * 返利
     * {@inheritDoc}
     */
	public void onEndRebateApply(User user, RebateApplyBean bean)
			throws MYException
	{
        FinanceBean financeBean = new FinanceBean();

        String name = bean.getDescription()+","+DefinedCommon.getValue("tcpType", bean.getType()) + "返利最终通过:" + bean.getId()
                      + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_REBATE_COMMON);

        financeBean.setRefId(bean.getId());

        financeBean.setDutyId(bean.getDutyId());

        financeBean.setCreaterId(user.getStafferId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        createAddRebateItem(user, bean, financeBean, itemList);
        
        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
    }
    
	private void createAddRebateItem(User user, RebateApplyBean bean, FinanceBean financeBean, List<FinanceItemBean> itemList)
        throws MYException
        {

        // 申请人
        StafferBean staffer = stafferDAO.find(bean.getStafferId());

        if (staffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        double total = bean.getTotal()/100d;
        //返利金额*税费比率（纪念品21%，邮政、终端、定制6%）
        if (staffer.getIndustryId().equals("5111656"))
        {
        	total = total * 0.21;
        }else if (staffer.getIndustryId().equals("5111657") || staffer.getIndustryId().equals("5111658") || staffer.getIndustryId().equals("5111664"))
        {
        	total = total * 0.06;
        }else{
        	//
        }
        
        String name = "返利"+bean.getId();

        // 手续费
        FinanceItemBean itemIn = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn.setPareId(pareId);

        itemIn.setName("销售费用:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        TaxBean inTax = taxDAO.findByUnique(TaxItemConstanst.SAIL_CHARGE);

        if (inTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(inTax, itemIn);

        // 当前发生额
        itemIn.setInmoney(FinanceHelper.doubleToLong(total));

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算/职员/部门
        itemIn.setDepartmentId(staffer.getPrincipalshipId());
        itemIn.setStafferId(bean.getStafferId());

        itemList.add(itemIn);

        // 贷方
        FinanceItemBean itemOut = new FinanceItemBean();

        itemOut.setPareId(pareId);

        itemOut.setName("主营业务税金及附加:" + name);

        itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut);

        // 银行科目
        TaxBean outTax = taxDAO.findByUnique(TaxItemConstanst.MAIN_INVOICEINS);

        if (outTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);

        itemOut.setInmoney(0);

        itemOut.setOutmoney(FinanceHelper.doubleToLong(total));

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 N/A
        itemOut.setDepartmentId(staffer.getPrincipalshipId());
        
        itemList.add(itemOut);
    
    }
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	public void onPayRebateApply(User user, RebateApplyBean bean, List<OutBillBean> outBillList) throws MYException
	{
		if (ListTools.isEmptyOrNull(outBillList)){
			throw new MYException("没有付款明细");
		}
		
		// 限制只能从一个账户出
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

            String name = bean.getDescription()+","+DefinedCommon.getValue("tcpType", bean.getType()) + "申请通过:"
                          + bean.getId() + '.';

            financeBean.setName(name);

            financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_REBATE_COMMON);

            // 付款单申请
            financeBean.setRefId(bean.getId());

            financeBean.setRefBill(outBillBean.getId());

            financeBean.setDutyId(bank.getDutyId());

            financeBean.setCreaterId(user.getStafferId());

            financeBean.setDescription(financeBean.getName());

            financeBean.setFinanceDate(TimeTools.now_short());

            financeBean.setLogTime(TimeTools.now());

            List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

            // 借:销售费用-借款/贷:现金或银行
            createAddRebateItem2(user, bean, bank, outBillBean, financeBean, itemList);

            financeBean.setItemList(itemList);

            financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
            
            bean.setDutyId(bank.getDutyId());
        }
	}
	
	/**
     * 销售费用-借款/银行科目
     * 
     * @param user
     * @param bean
     * @param bank
     * @param outBillBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createAddRebateItem2(User user, RebateApplyBean bean, BankBean bank,
                                OutBillBean outBillBean, FinanceBean financeBean,
                                List<FinanceItemBean> itemList)
        throws MYException
    {
        // 收款人
        StafferBean staffer = stafferDAO.find(bean.getStafferId());

        if (staffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        String name = "返利公司支付金额:" + bean.getId() + '.';

        FinanceItemBean itemIn = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn.setPareId(pareId);

        itemIn.setName("销售费用:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        // 销售费用(部门/职员)
        TaxBean inTax = taxDAO.findByUnique(TaxItemConstanst.SAIL_CHARGE);

        if (inTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(inTax, itemIn);

        // 当前发生额
        itemIn.setInmoney(bean.getTotal() * 100);

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 部门和职员
        itemIn.setDepartmentId(staffer.getPrincipalshipId());
        itemIn.setStafferId(staffer.getId());

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

        itemOut.setInmoney(0);

        itemOut.setOutmoney(bean.getTotal() * 100);

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 NA
        itemList.add(itemOut);
    }
	
    public void onSubmitMidTravelApply(User user, TravelApplyBean bean)
			throws MYException {
    	onSubmitMidTravelApply(user, bean, 1);
    }
    
    /* (non-Javadoc)
	 * @see com.china.center.oa.tcp.listener.TcpPayListener#onSubmitMidTravelApply(com.center.china.osgi.publics.User, com.china.center.oa.tcp.bean.TravelApplyBean)
	 */
	@Override
	public void onSubmitMidTravelApply(User user, TravelApplyBean bean, int type)
			throws MYException {
		
        FinanceBean financeBean = new FinanceBean();

        String name = bean.getDescription()+","+DefinedCommon.getValue("tcpType", bean.getType()) + "申请提交:"
                      + bean.getId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_MID_APPLY);

        financeBean.setRefId(bean.getId());

        financeBean.setRefBill("");

        financeBean.setDutyId(bean.getDutyId());

        financeBean.setCreaterId(user.getStafferId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 借:销售费用-借款/贷:现金或银行
        createAddMidItem(user, bean, financeBean, itemList, type);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean);
	}
    
	/**
     * 营业费用-中收 (5504-47)/预提费用 (2191)
     * 
     * @param user
     * @param bean
     * @param bank
     * @param outBillBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createAddMidItem(User user, TravelApplyBean bean, FinanceBean financeBean,
                                List<FinanceItemBean> itemList, int type)
        throws MYException
    {
        // 收款人
        StafferBean staffer = stafferDAO.find(bean.getStafferId());

        if (staffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        String name = "中收申请金额:" + bean.getId() + '.';

        FinanceItemBean itemIn = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn.setPareId(pareId);

        itemIn.setName("营业费用-中收 :" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        // 营业费用-中收
        TaxBean inTax = taxDAO.findByUnique(TaxItemConstanst.SALE_FEE_MID);

        if (inTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(inTax, itemIn);

        // 当前发生额
        itemIn.setInmoney(type * bean.getTotal() * 100);

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 部门和职员
        itemIn.setDepartmentId(staffer.getPrincipalshipId());
        itemIn.setStafferId(staffer.getId());

        itemList.add(itemIn);

        // 贷方
        FinanceItemBean itemOut = new FinanceItemBean();

        itemOut.setPareId(pareId);

        itemOut.setName("预提费用:" + name);

        itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut);

        // 预提费用
        TaxBean outTax = taxDAO.findByUnique(TaxItemConstanst.PRE_FEE);

        if (outTax == null)
        {
        	throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);

        itemOut.setInmoney(0);

        itemOut.setOutmoney(type * bean.getTotal() * 100);

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 NA
        itemList.add(itemOut);
    }
	
    /**
     * 
     */
    public void onRejectMidTravelApply(User user, TravelApplyBean bean)
			throws MYException {
    	List<FinanceBean> financeList = financeDAO.queryEntityBeansByFK(bean.getId());
    	
    	if (!ListTools.isEmptyOrNull(financeList)) {
    		String financeDate = financeList.get(0).getFinanceDate();
    		
    		// 判断该凭证是否已月结
    		String monthKey = TimeTools.changeFormat(financeDate, TimeTools.SHORT_FORMAT, "yyyyMM");
    		
    		List<FinanceMonthBean> fmbList = financeMonthDAO.queryEntityBeansByFK(monthKey);
    		
    		if (!ListTools.isEmptyOrNull(fmbList)) {
    			throw new MYException("驳回中收申请时，删除财务凭证已月结，请反月结，再驳回.");
    		} else {
    			for (FinanceBean each : financeList) {
    				financeDAO.deleteEntityBean(each.getId());
        			
        			financeItemDAO.deleteEntityBeansByFK(each.getId());
    			}
    		}
    		
    	}
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "TcpPayListener.TaxGlueImpl";
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
	 * @return the financeMonthDAO
	 */
	public FinanceMonthDAO getFinanceMonthDAO() {
		return financeMonthDAO;
	}

	/**
	 * @param financeMonthDAO the financeMonthDAO to set
	 */
	public void setFinanceMonthDAO(FinanceMonthDAO financeMonthDAO) {
		this.financeMonthDAO = financeMonthDAO;
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
