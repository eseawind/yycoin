/**
 * File Name: BudgetApplyManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-6-14<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.manager.impl;


import java.util.ArrayList;
import java.util.List;

import org.china.center.spring.iaop.annotation.IntegrationAOP;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.budget.bean.BudgetApplyBean;
import com.china.center.oa.budget.bean.BudgetBean;
import com.china.center.oa.budget.bean.BudgetItemBean;
import com.china.center.oa.budget.bean.FeeItemBean;
import com.china.center.oa.budget.constant.BudgetConstant;
import com.china.center.oa.budget.dao.BudgetApplyDAO;
import com.china.center.oa.budget.dao.BudgetDAO;
import com.china.center.oa.budget.dao.BudgetItemDAO;
import com.china.center.oa.budget.dao.BudgetLogDAO;
import com.china.center.oa.budget.dao.BudgetLogTmpDAO;
import com.china.center.oa.budget.dao.FeeItemDAO;
import com.china.center.oa.budget.helper.BudgetHelper;
import com.china.center.oa.budget.listener.BudgetListener;
import com.china.center.oa.budget.manager.BudgetApplyManager;
import com.china.center.oa.budget.manager.BudgetManager;
import com.china.center.oa.budget.vo.BudgetItemVO;
import com.china.center.oa.publics.bean.LogBean;
import com.china.center.oa.publics.bean.NotifyBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.ModuleConstant;
import com.china.center.oa.publics.constant.OperationConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.LogDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.manager.NotifyManager;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.TimeTools;


/**
 * BudgetApplyManager
 * 
 * @author ZHUZHU
 * @version 2009-6-14
 * @see BudgetApplyManagerImpl
 * @since 1.0
 */
@IntegrationAOP
public class BudgetApplyManagerImpl extends AbstractListenerManager<BudgetListener> implements BudgetApplyManager
{
    private BudgetDAO budgetDAO = null;

    private BudgetItemDAO budgetItemDAO = null;

    private CommonDAO commonDAO = null;

    private FeeItemDAO feeItemDAO = null;

    private NotifyManager notifyManager = null;

    private BudgetApplyDAO budgetApplyDAO = null;

    private LogDAO logDAO = null;

    private BudgetLogDAO budgetLogDAO = null;

    private BudgetManager budgetManager = null;

    private OrgManager orgManager = null;

    private BudgetLogTmpDAO budgetLogTmpDAO = null;

    private StafferDAO stafferDAO = null;
    /**
     * default constructor
     */
    public BudgetApplyManagerImpl()
    {
    }

    /**
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public synchronized boolean addBean(User user, BudgetApplyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkAddBean(bean);

        bean.setId(commonDAO.getSquenceString20());

        budgetApplyDAO.saveEntityBean(bean);

        saveItemList(bean);

        // save log
        logApply(bean, user, bean.getId(), OperationConstant.OPERATION_SUBMIT, bean
            .getDescription());

        logApply(bean, user, bean.getBudgetId(), OperationConstant.OPERATION_UPDATE, bean
            .getDescription());

        return true;
    }

    /**
     * pass
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean passBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        int nextStatus = checkPass(user, id);

        BudgetApplyBean apply = budgetApplyDAO.find(id);

        if (nextStatus != BudgetConstant.BUDGET_APPLY_STATUS_END)
        {
            budgetApplyDAO.updateStatus(id, nextStatus);

            logApply(apply, user, apply.getId(), OperationConstant.OPERATION_PASS, "通过预算变更");
        }
        else
        {
            List<BudgetItemBean> applyItemList = budgetItemDAO.queryEntityBeansByFK(id);

            List<BudgetItemBean> currentItemList = budgetItemDAO.queryEntityBeansByFK(apply
                .getBudgetId());

            BudgetBean budget = budgetDAO.find(apply.getBudgetId());

            if (budget == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 校验是否有权限审批部门预算
            if (budget.getType() == BudgetConstant.BUDGET_TYPE_DEPARTMENT)
            {
                StafferBean sbean = stafferDAO.find(user.getStafferId());
                
                if (null == sbean)
                {
                    throw new MYException("审批的人不存在");
                }
                
                if ( !orgManager.isStafferBelongOrg(user.getStafferId(), budget
                    .getBudgetDepartment()) || sbean.getPostId().equals(PublicConstant.POST_WORKER))
                {
                    throw new MYException("部门预算必须都是非职员审批");
                }
                
            }

            apply.setItems(applyItemList);

            budget.setItems(currentItemList);

            // NOTE if BUDGET_APPLY_STATUS_END,copy budget and delete apply
            List<BudgetItemBean> newList = compareItemListAndModifyCurrentItemListReturnNewItemList(
                apply.getBudgetId(), applyItemList, currentItemList);

            // 合法检查
            checkLegality(apply, budget);

            String logLevel = BudgetHelper.getLogLevel(budget);

            // CORE 预算变更的核心检查
            for (BudgetItemBean budgetItemBean : currentItemList)
            {
                double itemTotal = budgetLogDAO.sumUsedAndPreBudgetLogByLevel("budgetItemId"
                                                                              + logLevel,
                    budgetItemBean.getId()) / 100.0d;

                //add by flw 2012.7.20 计算流转的预算金额,且是没有经稽核通过的
                double turnTotal = budgetLogTmpDAO.sumTurnBudgetLogTmp("budgetItemId"
                                                                        + logLevel,
                        budgetItemBean.getId()) / 100.0d;
                
                if (MathTools.compare(itemTotal - turnTotal, budgetItemBean.getBudget()) > 0)
                {
                    throw new MYException("当前的预算项已经使用和预占的金额达到[%.2f],而更新后的预算只有[%.2f],请确认",
                        itemTotal, budgetItemBean.getBudget());
                }

                // 还需要检查子预算子和,防止预算超标
                List<BudgetBean> subBudget = budgetDAO.querySubmitBudgetByParentId(apply
                    .getBudgetId());

                double total = 0.0d;

                // 计算子项之和
                for (BudgetBean budgetBean : subBudget)
                {
                    BudgetItemBean subBudgetItemBean = budgetItemDAO.findByBudgetIdAndFeeItemId(
                        budgetBean.getId(), budgetItemBean.getFeeItemId());

                    if (subBudgetItemBean != null)
                    {
//                        total += BudgetHelper
//                            .getBudgetItemRealBudget(budgetBean, subBudgetItemBean);
                        
                        total += budgetManager
                        .getBudgetItemRealBudget(budgetBean, subBudgetItemBean);
                    }
                }

                if (MathTools.compare(total, budgetItemBean.getBudget()) > 0)
                {
                    FeeItemBean fee = feeItemDAO.find(budgetItemBean.getFeeItemId());
                    throw new MYException("当前的预算项的子项[%s]金额达到[%.2f],而更新后的预算只有[%.2f],请确认", fee
                        .getName(), total, budgetItemBean.getBudget());
                }
            }

            // copy apply to current
            budgetItemDAO.updateAllEntityBeans(currentItemList);

            // add new item to newList
            budgetItemDAO.saveAllEntityBeans(newList);

            double newTotal = 0L;

            for (BudgetItemBean each : currentItemList)
            {
                newTotal += each.getBudget();
            }

            for (BudgetItemBean each : newList)
            {
                newTotal += each.getBudget();
            }

            // update budget total
            budgetDAO.updateTotal(apply.getBudgetId(), newTotal);

            deleteApply(id);
        }

        logApply(apply, user, apply.getBudgetId(), OperationConstant.OPERATION_PASS, "通过预算变更");

        return true;
    }

    protected void searchAllUnitSub(String rootId, List<BudgetBean> endBudgetList)
    {
        // 需要递归寻找
        List<BudgetBean> subBudget = budgetDAO.queryEntityBeansByFK(rootId);

        for (BudgetBean budgetBean : subBudget)
        {
            if (BudgetHelper.isUnitBudget(budgetBean))
            {
                endBudgetList.add(budgetBean);
            }
            else
            {
                searchAllUnitSub(budgetBean.getId(), endBudgetList);
            }
        }
    }

    /**
     * rejectBean
     * 
     * @param user
     * @param id
     * @param reson
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean rejectBean(User user, String id, String reson)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        BudgetApplyBean apply = budgetApplyDAO.find(id);

        if (apply == null)
        {
            throw new MYException("申请不存在");
        }

        BudgetBean budget = budgetDAO.find(apply.getBudgetId());

        if (budget == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 校验是否有权限审批部门预算
        if (budget.getType() == BudgetConstant.BUDGET_TYPE_DEPARTMENT)
        {
            StafferBean sbean = stafferDAO.find(user.getStafferId());
            
            if (null == sbean)
            {
                throw new MYException("审批的人不存在");
            }
            
            if ( !orgManager.isStafferBelongOrg(user.getStafferId(), budget
                .getBudgetDepartment()) || sbean.getPostId().equals(PublicConstant.POST_WORKER))
                {
                    throw new MYException("部门预算必须都是非职员属性才可审批");
            }
            
        }

        deleteApply(id);

        logApply(apply, user, apply.getBudgetId(), OperationConstant.OPERATION_REJECT, reson);

        return true;
    }

    /**
     * deleteApply
     * 
     * @param id
     */
    private void deleteApply(String id)
    {
        budgetApplyDAO.deleteEntityBean(id);

        budgetItemDAO.deleteEntityBeansByFK(id);

        logDAO.deleteEntityBean(id);
    }

    /**
     * 比较返回新增的item
     * 
     * @param applyItemList
     * @param currentItemList
     * @throws MYException
     */
    private List<BudgetItemBean> compareItemListAndModifyCurrentItemListReturnNewItemList(
                                                                                          String budgetId,
                                                                                          List<BudgetItemBean> applyItemList,
                                                                                          List<BudgetItemBean> currentItemList)
        throws MYException
    {
        List<BudgetItemBean> newList = new ArrayList();

        // copy apply to current
        for (BudgetItemBean apply : applyItemList)
        {
            boolean isContain = false;

            for (BudgetItemBean current : currentItemList)
            {
                if (current.getFeeItemId().equals(apply.getFeeItemId()))
                {
                    current.setBudget(apply.getBudget());

                    current.setUseMonery(current.getBudget());

                    isContain = true;

                    break;
                }
            }

            if ( !isContain)
            {
                apply.setId(commonDAO.getSquenceString20());

                apply.setBudgetId(budgetId);

                // set appltItem to save
                apply.setUseMonery(apply.getBudget());

                apply.setCarryStatus(BudgetConstant.BUDGET_CARRY_DOING);

                newList.add(apply);
            }
        }

        return newList;
    }

    /**
     * checkPass
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    private int checkPass(User user, String id)
        throws MYException
    {
        BudgetApplyBean apply = budgetApplyDAO.find(id);

        if (apply == null)
        {
            throw new MYException("申请不存在");
        }

        apply.setItems(budgetItemDAO.queryEntityBeansByFK(id));

        BudgetBean budget = budgetDAO.find(apply.getBudgetId());

        if (budget == null)
        {
            throw new MYException("预算不存在");
        }

        if (budget.getStatus() == BudgetConstant.BUDGET_STATUS_END)
        {
            throw new MYException("预算已经结束,不能进行变更");
        }

        if (apply.getStatus() < BudgetConstant.BUDGET_APPLY_STATUS_WAIT_APPROVE)
        {
            throw new MYException("不能审批");
        }

        checkLegality(apply, budget);

        // get next status
        return BudgetHelper.getNextApplyStatus(budget, apply);
    }

    /**
     * @param budgetId
     * @return
     * @throws MYException
     */
    public boolean whetherApply(String budgetId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(budgetId);

        int count = budgetApplyDAO.countByUnique(budgetId);

        if (count > 0)
        {
            throw new MYException("已经存在变更或者追加");
        }

        return true;
    }

    /**
     * saveItemList
     * 
     * @param bean
     */
    private void saveItemList(BudgetApplyBean bean)
    {
        List<BudgetItemBean> itemList = bean.getItems();

        for (BudgetItemBean budgetItemBean : itemList)
        {
            budgetItemBean.setId(commonDAO.getSquenceString20());
            budgetItemBean.setBudgetId(bean.getId());
        }

        budgetItemDAO.saveAllEntityBeans(itemList);
    }

    /**
     * @param bean
     * @throws MYException
     */
    private void checkAddBean(BudgetApplyBean bean)
        throws MYException
    {
        if (budgetApplyDAO.countByUnique(bean.getBudgetId()) > 0)
        {
            throw new MYException("已经存在预算变更或者追加");
        }

        BudgetBean budget = budgetDAO.find(bean.getBudgetId());

        if (budget == null)
        {
            throw new MYException("预算不存在");
        }

        if (ListTools.isEmptyOrNull(bean.getItems()))
        {
            throw new MYException("没有预算项");
        }

        // NOTE core check
        checkLegality(bean, budget);
    }

    /**
     * 合法检查
     * 
     * @param bean
     * @param budget
     * @throws MYException
     */
    private void checkLegality(BudgetApplyBean bean, BudgetBean budget)
        throws MYException
    {
        List<BudgetItemVO> currentItemList = budgetItemDAO.queryEntityVOsByFK(bean.getBudgetId());

        // can not drop exist item
        checkDrop(currentItemList, bean.getItems());

        // check modify
        if (bean.getType() == BudgetConstant.BUDGET_APPLY_TYPE_MODIFY)
        {
            handleCheckModify(bean, budget);
        }

        // check add
        if (bean.getType() == BudgetConstant.BUDGET_APPLY_TYPE_ADD)
        {
            handleCheckAdd(bean, budget);
        }

        // if not BUDGET_TYPE_COMPANY,check contain
        if (budget.getType() != BudgetConstant.BUDGET_TYPE_COMPANY)
        {
            // check budget
            compareSubItem(budgetItemDAO.queryEntityVOsByFK(budget.getParentId()), bean.getItems(),
                budget.getId());
        }
    }

    /**
     * 变更
     * 
     * @param bean
     * @throws MYException
     */
    private void handleCheckModify(BudgetApplyBean bean, BudgetBean budget)
        throws MYException
    {
        // if modify,the total only equal or less than current budget, and the modify budge must bigger than
        // realMonery
        List<BudgetItemBean> items = bean.getItems();

        double total = 0.0d;

        for (BudgetItemBean budgetItemBean : items)
        {
            BudgetItemVO currentItem = budgetItemDAO.findVOByBudgetIdAndFeeItemId(bean
                .getBudgetId(), budgetItemBean.getFeeItemId());

            total += budgetItemBean.getBudget();

            // the modify budge must bigger than realMonery
            if (currentItem != null)
            {
                // 当前最大占用
                double itemSubTotal = budgetManager.sumMaxUseInEachBudgetItem(currentItem);

                if (MathTools.compare(itemSubTotal, budgetItemBean.getBudget()) > 0)
                {
                    throw new MYException("预算项[%s]已经使用了%s,所以更改后的预算不能小于%s,您当前修改后的是%s", currentItem
                        .getFeeItemName(), MathTools.formatNum(itemSubTotal), MathTools
                        .formatNum(itemSubTotal), MathTools.formatNum(budgetItemBean.getBudget()));
                }
            }
            else
            {
                // add item,need check item is exist in parent
                if (budget.getType() != BudgetConstant.BUDGET_TYPE_COMPANY)
                {
                    BudgetItemBean parentItem = budgetItemDAO.findByBudgetIdAndFeeItemId(budget
                        .getParentId(), budgetItemBean.getFeeItemId());

                    if (parentItem == null)
                    {
                        throw new MYException("预算项[%s]是新增的,但是父预算没有此预算项", currentItem
                            .getFeeItemName());
                    }
                }
            }
        }

        double currentBudget = budgetItemDAO.sumBudgetTotal(bean.getBudgetId());

        if (MathTools.compare(currentBudget, total) < 0)
        {
            throw new MYException("当前预算总额是%s,但是你变更后的是%s，不能大于当前总额,请重新变更", MathTools
                .formatNum(currentBudget), MathTools.formatNum(total));
        }
    }

    /**
     * handleCheckAdd(追加)
     * 
     * @param bean
     * @throws MYException
     */
    private void handleCheckAdd(BudgetApplyBean bean, BudgetBean budget)
        throws MYException
    {
        // if modify,the total only equal or less than current budget, and the modify budge must bigger than
        // realMonery
        List<BudgetItemBean> items = bean.getItems();

        double total = 0.0d;

        for (BudgetItemBean budgetItemBean : items)
        {
            BudgetItemVO currentItem = budgetItemDAO.findVOByBudgetIdAndFeeItemId(bean
                .getBudgetId(), budgetItemBean.getFeeItemId());

            total += budgetItemBean.getBudget();

            if (currentItem != null)
            {
                // 预算支持修改变小的
                if (MathTools.compare(budgetItemBean.getBudget(), currentItem.getBudget()) < 0)
                {
                    throw new MYException("预算项[%s]预算是%s,更改后的预算不能小于%s,您当前修改后的是%s", currentItem
                        .getFeeItemName(), MathTools.formatNum(currentItem.getBudget()), MathTools
                        .formatNum(currentItem.getBudget()), MathTools.formatNum(budgetItemBean
                        .getBudget()));
                }
            }
            else
            {
                // add item,need check item is exist in parent
                if (budget.getType() != BudgetConstant.BUDGET_TYPE_COMPANY)
                {
                    BudgetItemBean parentItem = budgetItemDAO.findByBudgetIdAndFeeItemId(budget
                        .getParentId(), budgetItemBean.getFeeItemId());

                    if (parentItem == null)
                    {
                        throw new MYException("预算项是新增的,但是父预算没有此预算项");
                    }
                }
            }
        }

        double currentBudget = budgetItemDAO.sumBudgetTotal(bean.getBudgetId());

        // 预算支持修改变小的
        if (MathTools.compare(total, currentBudget) < 0)
        {
            throw new MYException("当前预算总额是%s,但是你变更后的是%s，不能小于当前总额请重新追加", MathTools
                .formatNum(currentBudget), MathTools.formatNum(total));
        }
    }

    /**
     * checkDrop
     * 
     * @param currentList
     * @param modifyLsit
     * @throws MYException
     */
    private void checkDrop(List<BudgetItemVO> currentList, List<BudgetItemBean> modifyLsit)
        throws MYException
    {
        for (BudgetItemVO currentItem : currentList)
        {
            boolean has = false;

            for (BudgetItemBean modifyItem : modifyLsit)
            {
                if (modifyItem.getFeeItemId().equals(currentItem.getFeeItemId()))
                {
                    has = true;
                    break;
                }
            }

            if ( !has)
            {
                throw new MYException("丢失预算项[%s]", currentItem.getFeeItemName());
            }
        }
    }

    /**
     * compareSubItem
     * 
     * @param parentSubItem
     * @param subItem
     * @throws MYException
     */
    private void compareSubItem(List<BudgetItemVO> parentSubItem, List<BudgetItemBean> subItem,
                                String budgetId)
        throws MYException
    {
        for (BudgetItemBean item : subItem)
        {
            boolean isOK = false;

            for (BudgetItemVO pitem : parentSubItem)
            {
                if (pitem.getFeeItemId().equals(item.getFeeItemId()))
                {
                    // total money,less than current
                    if (compareMoney(pitem, item.getBudget(), budgetId))
                    {
                        isOK = true;
                    }
                    else
                    {
                        throw new MYException("[%s]的预算和超出父预算", pitem.getFeeItemName());
                    }
                }
            }

            if ( !isOK)
            {
                FeeItemBean feeItem = feeItemDAO.find(item.getFeeItemId());

                if (feeItem == null)
                {
                    throw new MYException("预算项不存在");
                }

                throw new MYException("父预算缺少提交的预算项[%s]", feeItem.getName());
            }
        }
    }

    /**
     * compareMoney
     * 
     * @param pitem
     * @param currentMoney
     * @return
     */
    private boolean compareMoney(BudgetItemBean pitem, double currentMoney, String budgetId)
    {
        // this list cotain the modfiy budget(so we must filter it)
        List<BudgetBean> allSubBudget = budgetDAO.querySubmitBudgetByParentId(pitem.getBudgetId());

        double total = currentMoney;

        for (BudgetBean budgetBean : allSubBudget)
        {
            // filter myself
            if (budgetBean.getId().equals(budgetId))
            {
                continue;
            }

            BudgetItemBean subFeeItem = budgetItemDAO.findByBudgetIdAndFeeItemId(
                budgetBean.getId(), pitem.getFeeItemId());

            if (subFeeItem != null)
            {
//                total += BudgetHelper.getBudgetItemRealBudget(budgetBean, subFeeItem);
                total += budgetManager.getBudgetItemRealBudget(budgetBean, subFeeItem);
            }
        }

        return MathTools.compare(pitem.getBudget(), total) >= 0;
    }

    /**
     * log in db
     * 
     * @param user
     * @param id
     */
    private void logApply(BudgetApplyBean bean, User user, String id, String operation, String reson)
    {
        // 记录审批日志
        LogBean log = new LogBean();

        log.setFkId(id);

        log.setLocationId(user.getLocationId());
        log.setStafferId(user.getStafferId());
        log.setLogTime(TimeTools.now());
        log.setModule(ModuleConstant.MODULE_BUDGET);
        log.setOperation(operation);
        log.setLog(reson);

        logDAO.saveEntityBean(log);

        NotifyBean notify = new NotifyBean();

        notify.setMessage("预算申请" + operation + ":" + reson + ".操作人:" + user.getStafferName());

        notify.setUrl(BudgetConstant.BUDGET_DETAIL_URL + bean.getBudgetId());

        notifyManager.notifyWithoutTransaction(bean.getStafferId(), notify);
    }

    /**
     * @return the budgetDAO
     */
    public BudgetDAO getBudgetDAO()
    {
        return budgetDAO;
    }

    /**
     * @param budgetDAO
     *            the budgetDAO to set
     */
    public void setBudgetDAO(BudgetDAO budgetDAO)
    {
        this.budgetDAO = budgetDAO;
    }

    /**
     * @return the budgetItemDAO
     */
    public BudgetItemDAO getBudgetItemDAO()
    {
        return budgetItemDAO;
    }

    /**
     * @param budgetItemDAO
     *            the budgetItemDAO to set
     */
    public void setBudgetItemDAO(BudgetItemDAO budgetItemDAO)
    {
        this.budgetItemDAO = budgetItemDAO;
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
     * @return the feeItemDAO
     */
    public FeeItemDAO getFeeItemDAO()
    {
        return feeItemDAO;
    }

    /**
     * @param feeItemDAO
     *            the feeItemDAO to set
     */
    public void setFeeItemDAO(FeeItemDAO feeItemDAO)
    {
        this.feeItemDAO = feeItemDAO;
    }

    /**
     * @return the budgetApplyDAO
     */
    public BudgetApplyDAO getBudgetApplyDAO()
    {
        return budgetApplyDAO;
    }

    /**
     * @param budgetApplyDAO
     *            the budgetApplyDAO to set
     */
    public void setBudgetApplyDAO(BudgetApplyDAO budgetApplyDAO)
    {
        this.budgetApplyDAO = budgetApplyDAO;
    }

    /**
     * @return the logDAO
     */
    public LogDAO getLogDAO()
    {
        return logDAO;
    }

    /**
     * @param logDAO
     *            the logDAO to set
     */
    public void setLogDAO(LogDAO logDAO)
    {
        this.logDAO = logDAO;
    }

    /**
     * @return the notifyManager
     */
    public NotifyManager getNotifyManager()
    {
        return notifyManager;
    }

    /**
     * @param notifyManager
     *            the notifyManager to set
     */
    public void setNotifyManager(NotifyManager notifyManager)
    {
        this.notifyManager = notifyManager;
    }

    /**
     * @return the orgManager
     */
    public OrgManager getOrgManager()
    {
        return orgManager;
    }

    /**
     * @param orgManager
     *            the orgManager to set
     */
    public void setOrgManager(OrgManager orgManager)
    {
        this.orgManager = orgManager;
    }

    /**
     * @return the budgetLogDAO
     */
    public BudgetLogDAO getBudgetLogDAO()
    {
        return budgetLogDAO;
    }

    /**
     * @param budgetLogDAO
     *            the budgetLogDAO to set
     */
    public void setBudgetLogDAO(BudgetLogDAO budgetLogDAO)
    {
        this.budgetLogDAO = budgetLogDAO;
    }

    /**
     * @return the budgetManager
     */
    public BudgetManager getBudgetManager()
    {
        return budgetManager;
    }

    /**
     * @param budgetManager
     *            the budgetManager to set
     */
    public void setBudgetManager(BudgetManager budgetManager)
    {
        this.budgetManager = budgetManager;
    }

    public BudgetLogTmpDAO getBudgetLogTmpDAO() {
        return budgetLogTmpDAO;
    }

    public void setBudgetLogTmpDAO(BudgetLogTmpDAO budgetLogTmpDAO) {
        this.budgetLogTmpDAO = budgetLogTmpDAO;
    }

    public StafferDAO getStafferDAO() {
        return stafferDAO;
    }

    public void setStafferDAO(StafferDAO stafferDAO) {
        this.stafferDAO = stafferDAO;
    }

}
