/**
 * File Name: CurOutManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customervssail.manager.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.client.dao.CustomerMainDAO;
import com.china.center.oa.credit.bean.CreditCoreBean;
import com.china.center.oa.credit.bean.CreditItemBean;
import com.china.center.oa.credit.bean.CreditItemSecBean;
import com.china.center.oa.credit.bean.CreditItemThrBean;
import com.china.center.oa.credit.bean.CurOutBean;
import com.china.center.oa.credit.dao.CreditCoreDAO;
import com.china.center.oa.credit.dao.CreditItemDAO;
import com.china.center.oa.credit.dao.CreditItemSecDAO;
import com.china.center.oa.credit.dao.CreditItemThrDAO;
import com.china.center.oa.credit.dao.CreditlogDAO;
import com.china.center.oa.credit.dao.CurOutDAO;
import com.china.center.oa.credit.dao.CustomerCreditDAO;
import com.china.center.oa.credit.manager.CustomerCreditManager;
import com.china.center.oa.credit.vs.CustomerCreditBean;
import com.china.center.oa.customer.constant.CreditConstant;
import com.china.center.oa.customervssail.dao.OutStatDAO;
import com.china.center.oa.customervssail.manager.CurOutManager;
import com.china.center.oa.publics.constant.SysConfigConstant;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.helper.OATools;
import com.china.center.oa.publics.helper.UserHelper;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.bean.PromotionBean;
import com.china.center.oa.sail.dao.PromotionDAO;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * CORE 客户信用自动统计
 * 
 * @author ZHUZHU
 * @version 2010-10-6
 * @see CurOutManagerImpl
 * @since 1.0
 */
public class CurOutManagerImpl implements CurOutManager
{
    private final Log _logger = LogFactory.getLog(getClass());

    private final Log _coreLog = LogFactory.getLog("core");
    
    private PromotionDAO promotionDAO = null;

    private final Log triggerLog = LogFactory.getLog("trigger");

    private CurOutDAO curOutDAO = null;

    private CreditCoreDAO creditCoreDAO = null;

    private OutStatDAO outStatDAO = null;

    private ParameterDAO parameterDAO = null;

    private CreditlogDAO creditlogDAO = null;

    private CustomerMainDAO customerMainDAO = null;

    private CreditItemDAO creditItemDAO = null;

    private CustomerCreditDAO customerCreditDAO = null;

    private CreditItemSecDAO creditItemSecDAO = null;

    private CreditItemThrDAO creditItemThrDAO = null;

    private CustomerCreditManager customerCreditManager = null;

    private PlatformTransactionManager transactionManager = null;

    /**
     * default constructor
     */
    public CurOutManagerImpl()
    {
    }

    /**
     * 定期清理客户信用日志,防止日志过多
     */
    public void deleteHis()
    {
        triggerLog.info("CurOutManagerImpl deleteHis begin.....");
        
        final ConditionParse condition = new ConditionParse();

        condition.addWhereStr();

        condition.addCondition("logTime", "<=", TimeTools.getDateFullString( -7));

        condition.addIntCondition("val", "<=", 0);

        // 操作在数据库事务中完成
        TransactionTemplate tranTemplate = new TransactionTemplate(transactionManager);

        try
        {
            tranTemplate.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    creditlogDAO.deleteEntityBeansByCondition(condition);

                    return Boolean.TRUE;
                }
            });
        }
        catch (Throwable e)
        {
            _logger.error(e, e);
        }

        triggerLog.info("CurOutManagerImpl deleteHis end.....");
    }

    /**
     * CORE 统计客户信用开始
     */
    public void statOut()
    {
        triggerLog.info("统计客户信用开始.....");

        boolean outCredit = parameterDAO.getBoolean(SysConfigConstant.OUT_CREDIT);

        if ( !outCredit)
        {
            triggerLog.info("统计客户信用结束(开关没有开启).....");

            return;
        }

        List<String> customerIdList = outStatDAO.listCustomerIdList();

        User user = UserHelper.getSystemUser();

        CreditItemSecBean outItem = creditItemSecDAO.find(CreditConstant.OUT_COMMON_ITEM);

        CreditItemSecBean outDelayItem = creditItemSecDAO.find(CreditConstant.OUT_DELAY_ITEM);

        CreditItemThrBean maxDelayThrItem = creditItemThrDAO.findMaxDelayItem();

        if (outItem == null || outDelayItem == null || maxDelayThrItem == null)
        {
            _coreLog.error("miss CreditItemSecBean:" + CreditConstant.OUT_COMMON_ITEM + ".or:"
                           + CreditConstant.OUT_DELAY_ITEM + ".or:maxDelayThrItem");

            triggerLog.info("统计客户信用结束.....");

            return;
        }

        CreditItemThrBean maxBusiness = creditItemThrDAO.findMaxBusiness();

        CreditItemThrBean totalBusiness = creditItemThrDAO.findTotalBusiness();

        final int staticAmount = parameterDAO.getInt(SysConfigConstant.CREDIT_STATIC);

        // iterator to handle
        for (String cid : customerIdList)
        {
            try
            {
                if ( !StringTools.isNullOrNone(cid))
                {
                    handleEachCustomer(user, outItem, maxDelayThrItem, cid, maxBusiness,
                        totalBusiness, staticAmount);
                }
            }
            catch (Throwable e)
            {
                _logger.error(e, e);
            }
        }

        triggerLog.info("统计客户信用结束.....");
    }

    /**
     * 处理每个客户
     * 
     * @param user
     * @param outItem
     * @param maxDelayThrItem
     * @param customerBean
     */
    private void handleEachCustomer(User user, CreditItemSecBean outItem,
                                    CreditItemThrBean maxDelayThrItem, String cid,
                                    CreditItemThrBean maxBusiness, CreditItemThrBean totalBusiness,
                                    int staticAmount)
    {
        // 从2009-12-01开始分析客户的行为
        List<OutBean> outList = outStatDAO.queryNoneStatByCid(cid);

        // 开始分析
        for (OutBean outBean : outList)
        {
        	String eventid = outBean.getEventId();
            //如果为增加信用分则不减分
            if(null != eventid && eventid.length()>0)
            {
         	   PromotionBean promBean = promotionDAO.find(eventid);
                if(null != promBean && promBean.getRebateType()==2)
                {
             	   continue;
                }
            }
            // 每次小于1000于的勾款不触发增加信用分
            if (outBean.getTotal() < 1000)
            {
                continue;
            }

            // 付款且没有延期
            if (outBean.getPay() == CreditConstant.PAY_YES && outBean.getTempType() == 0)
            {
                handleCommon(user, cid, outItem, outBean);
            }

            // 延期的
            if (outBean.getPay() == CreditConstant.PAY_YES && outBean.getTempType() > 0)
            {
                // 减分 记入日志(变成已经处理)
                handleDelay(user, cid, outBean, maxDelayThrItem, true);
            }

            // 还未付款的，看看是否已经超期
            if (outBean.getPay() == CreditConstant.PAY_NOT
                && !StringTools.isNullOrNone(outBean.getRedate()))
            {
                int delay = TimeTools.cdate(TimeTools.now_short(), outBean.getRedate());

                // 等待延期
                if (delay <= 0)
                {
                    continue;
                }

                // 处理延期
                handleDelay(user, cid, outBean, maxDelayThrItem, false);
            }

        }

        // 单比最大交易额
        if (maxBusiness != null)
        {
            double maxBusinessAmount = outStatDAO.queryMaxBusiness(cid, OATools
                .getFinanceBeginDate(), OATools.getFinanceEndDate());

            CreditCoreBean old = creditCoreDAO.findByUnique(cid);

            // 这里如果历史过户,以最大的为基准
            if (old != null)
            {
                maxBusinessAmount = Math.max(old.getOldMaxBusiness(), maxBusinessAmount);
            }

            CreditItemThrBean sigleItem = creditItemThrDAO
                .findSingleMaxBusinessByValue(maxBusinessAmount);

            handleSingle(user, cid, sigleItem, maxBusinessAmount);
        }

        // 总交易额
        if (totalBusiness != null)
        {
            // 这里到了新的财务年度就会重新计算了
            double sumBusiness = outStatDAO.sumBusiness(cid, OATools.getFinanceBeginDate(), OATools
                .getFinanceEndDate());

            CreditItemThrBean sumItem = creditItemThrDAO.findTotalBusinessByValue(sumBusiness);

            handleTotal(user, cid, sumItem, sumBusiness);
        }

        // 在此统一更新客户信用等级,且根据客户是否由黑名单变为非黑名单而改变客户的等级
        try
        {
            customerCreditManager.updateCustomerCredit2(cid);
        }
        catch (MYException e)
        {
            _logger.error(e, e);
        }
        
        // 这里重新估计静态交易总额
        handleStaticCredit(user, cid, staticAmount);
    }

    /**
     * handleStaticCredit
     * 
     * @param user
     * @param cid
     * @param staticAmount
     */
    private void handleStaticCredit(User user, String cid, int staticAmount)
    {
        ConditionParse condition = new ConditionParse();
        condition.addWhereStr();

        condition.addCondition("cid", "=", cid);

        condition.addIntCondition("ptype", "=", CreditConstant.CREDIT_TYPE_STATIC);

        List<CustomerCreditBean> ccList = customerCreditDAO.queryEntityBeansByCondition(condition);

        List<CustomerCreditBean> modfiyList = new ArrayList();

        for (CustomerCreditBean customerCreditBean : ccList)
        {
            CreditItemThrBean creditItemThr = creditItemThrDAO
                .find(customerCreditBean.getValueId());

            if (creditItemThr == null)
            {
                continue;
            }

            CreditItemBean creditItem = creditItemDAO.find(customerCreditBean.getPitemId());

            if (creditItem == null)
            {
                continue;
            }

            CreditItemSecBean creditItemSec = creditItemSecDAO.find(customerCreditBean.getItemId());

            if (creditItemSec == null)
            {
                continue;
            }

            double itemValue = (staticAmount * creditItem.getPer() * creditItemSec.getPer() * creditItemThr
                .getPer()) / 1000000.0d;

            if (customerCreditBean.getVal() == itemValue)
            {
                continue;
            }

            // 更新静态指标
            CustomerCreditBean each = new CustomerCreditBean();

            each.setCid(cid);

            each.setLogTime(TimeTools.now());

            each.setItemId(creditItemSec.getId());

            each.setValueId(creditItemThr.getId());

//            each.setVal(itemValue);不对信用分做变更

            each.setLog("系统自动修正:" + each.getVal());

            each.setPitemId(creditItem.getId());

            each.setPtype(CreditConstant.CREDIT_TYPE_STATIC);

            modfiyList.add(each);
        }

        if (modfiyList.size() > 0)
        {
            try
            {
                customerCreditManager.configCustomerCredit(user, cid, ccList);
            }
            catch (MYException e)
            {
                _logger.error(e, e);
            }
        }
        else
        {
            try
            {
                customerCreditManager.updateCustomerCredit(cid);
            }
            catch (MYException e)
            {
                _logger.error(e, e);
            }
        }
    }

    /**
     * handleCommon
     * 
     * @param user
     * @param customerBean
     * @param cid
     * @param outItem
     */
    private void handleCommon(final User user, final String cid, final CreditItemSecBean outItem,
                              final OutBean outBean)
    {
        CustomerCreditBean customerCreditBean = customerCreditDAO.findByUnique(cid,
            CreditConstant.OUT_COMMON_ITEM);

        // 加分 记入日志(变成已经处理 reserve1=1)
        if (customerCreditBean == null)
        {
            customerCreditBean = new CustomerCreditBean();

            customerCreditBean.setVal(outItem.getPer());

            customerCreditBean.setLog("销售单[" + outBean.getFullId() + "]正常付款,加分:"
                                      + MathTools.formatNum(outItem.getPer()) + ".加分后:"
                                      + MathTools.formatNum(outItem.getPer()));
        }
        else
        {
            customerCreditBean.setLog("销售单["
                                      + outBean.getFullId()
                                      + "]正常付款,加分:"
                                      + MathTools.formatNum(outItem.getPer())
                                      + ".加分后:"
                                      + MathTools.formatNum(customerCreditBean.getVal()
                                                            + outItem.getPer()));

            customerCreditBean.setVal(outItem.getPer() + customerCreditBean.getVal());
        }

        customerCreditBean.setCid(cid);

        customerCreditBean.setLogTime(TimeTools.now());

        customerCreditBean.setPtype(CreditConstant.CREDIT_TYPE_DYNAMIC);

        customerCreditBean.setItemId(CreditConstant.OUT_COMMON_ITEM);

        customerCreditBean.setPitemId(CreditConstant.OUT_COMMON_ITEM_PARENT);

        customerCreditBean.setValueId("0");

        final CustomerCreditBean fcustomerCreditBean = customerCreditBean;

        // 操作在数据库事务中完成
        TransactionTemplate tranTemplate = new TransactionTemplate(transactionManager);

        try
        {
            // must send each item in Transaction(may be wait)
            tranTemplate.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    try
                    {
                        customerCreditManager.interposeCreditInnerWithoutUpdateLevel(user, cid, fcustomerCreditBean);
                    }
                    catch (MYException e)
                    {
                        _logger.warn(e, e);
                        throw new RuntimeException(e.getErrorContent());
                    }

                    // 更新out里面的状态
                    outStatDAO.updateReserve1ByFullId(outBean.getFullId(),
                        CreditConstant.CREDIT_OUT_END, MathTools.formatNum(fcustomerCreditBean
                            .getVal()));

                    double minus = fcustomerCreditBean.getVal();

                    saveCurLog(cid, outBean, minus);

                    triggerLog.info("handle out stat[" + cid + "]:" + outBean.getFullId());

                    return Boolean.TRUE;
                }
            });
        }
        catch (Throwable e)
        {
            _logger.error(e, e);
        }
    }

    /**
     * handleSingle
     * 
     * @param user
     * @param cid
     * @param sigleItem
     * @param maxBusinessAmount
     */
    private void handleSingle(final User user, final String cid, final CreditItemThrBean sigleItem,
                              final double maxBusinessAmount)
    {
        CustomerCreditBean customerCreditBean = customerCreditDAO.findByUnique(cid,
            CreditConstant.OUT_MAX_BUSINESS);

        // 单比最大交易额
        if (customerCreditBean == null)
        {
            customerCreditBean = new CustomerCreditBean();

            customerCreditBean.setVal(sigleItem.getPer());
        }

        customerCreditBean.setLog("单比最大交易额[" + MathTools.formatNum(maxBusinessAmount) + "],加分:"
                                  + MathTools.formatNum(sigleItem.getPer()));

        customerCreditBean.setCid(cid);

        customerCreditBean.setLogTime(TimeTools.now());

        customerCreditBean.setPtype(CreditConstant.CREDIT_TYPE_DYNAMIC);

        customerCreditBean.setItemId(CreditConstant.OUT_MAX_BUSINESS);

        customerCreditBean.setPitemId(CreditConstant.OUT_MAX_BUSINESS_PARENT);

        customerCreditBean.setValueId(sigleItem.getId());

        final CustomerCreditBean fcustomerCreditBean = customerCreditBean;

        // 操作在数据库事务中完成
        TransactionTemplate tranTemplate = new TransactionTemplate(transactionManager);

        try
        {
            // must send each item in Transaction(may be wait)
            tranTemplate.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    try
                    {
                        customerCreditManager.interposeCreditInnerWithoutUpdateLevel(user, cid, fcustomerCreditBean);
                    }
                    catch (MYException e)
                    {
                        _logger.warn(e, e);
                        throw new RuntimeException(e.getErrorContent());
                    }

                    // 记入日志
                    saveCore(cid, maxBusinessAmount, true);

                    return Boolean.TRUE;
                }
            });
        }
        catch (Throwable e)
        {
            _logger.error(e, e);
        }
    }

    /**
     * handleSingle
     * 
     * @param user
     * @param cid
     * @param sumItem
     * @param amount
     */
    private void handleTotal(final User user, final String cid, final CreditItemThrBean sumItem,
                             final double amount)
    {
        CustomerCreditBean customerCreditBean = customerCreditDAO.findByUnique(cid,
            CreditConstant.OUT_TOTAL_BUSINESS);

        // 单比最大交易额
        if (customerCreditBean == null)
        {
            customerCreditBean = new CustomerCreditBean();

            customerCreditBean.setVal(sumItem.getPer());
        }

        customerCreditBean.setLog("财务年度总交易额[" + MathTools.formatNum(amount) + "],加分:"
                                  + MathTools.formatNum(sumItem.getPer()));

        customerCreditBean.setCid(cid);

        customerCreditBean.setLogTime(TimeTools.now());

        customerCreditBean.setPtype(CreditConstant.CREDIT_TYPE_DYNAMIC);

        customerCreditBean.setItemId(CreditConstant.OUT_TOTAL_BUSINESS);

        customerCreditBean.setPitemId(CreditConstant.OUT_TOTAL_BUSINESS_PARENT);

        customerCreditBean.setValueId(sumItem.getId());

        final CustomerCreditBean fcustomerCreditBean = customerCreditBean;

        // 操作在数据库事务中完成
        TransactionTemplate tranTemplate = new TransactionTemplate(transactionManager);

        try
        {
            // must send each item in Transaction(may be wait)
            tranTemplate.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    try
                    {
                        customerCreditManager.interposeCreditInnerWithoutUpdateLevel(user, cid, fcustomerCreditBean);
                    }
                    catch (MYException e)
                    {
                        _logger.warn(e, e);
                        throw new RuntimeException(e.getErrorContent());
                    }

                    // 记入日志
                    saveCore(cid, amount, false);

                    return Boolean.TRUE;
                }
            });
        }
        catch (Throwable e)
        {
            _logger.error(e, e);
        }
    }

    /**
     * 处理延期的销售单
     * 
     * @param user
     * @param customerBean
     * @param cid
     * @param outItem
     * @param outBean
     */
    private void handleDelay(final User user, final String cid, final OutBean outBean,
                             final CreditItemThrBean maxDelayThrItem, boolean isEnd)
    {
        CustomerCreditBean customerCreditBean = customerCreditDAO.findByUnique(cid,
            CreditConstant.OUT_DELAY_ITEM);

        if ( !isEnd)
        {
            int delay = TimeTools.cdate(TimeTools.now_short(), outBean.getRedate());

            outBean.setTempType(delay);
        }

        final CreditItemThrBean delayItemThrBean = creditItemThrDAO.findDelayItemByDays(outBean
            .getTempType());

        // 设置延期时间
        if ( !isEnd)
        {
            // 如果还是上次的更新就返回(防止重复扣除)
            if (outBean.getReserve5().equals(delayItemThrBean.getId()))
            {
                return;
            }
        }

        // 如果最大的天数已经超过定义的延期天数据全部结束
        if (outBean.getTempType() >= maxDelayThrItem.getIndexPos())
        {
            isEnd = true;
        }

        // 减分 记入日志
        final double currentMinus = handleCurrentMinus(outBean, delayItemThrBean);

        if (customerCreditBean == null)
        {
            customerCreditBean = new CustomerCreditBean();

            // 负向指标
           String eventid = outBean.getEventId();
           //如果为增加信用分则不减分
           if(null != eventid && eventid.length()>0)
           {
        	   PromotionBean promBean = promotionDAO.find(eventid);
               if(null != promBean && promBean.getRebateType()==2)
               {
            	   
               }
               else
               {
            	   customerCreditBean.setVal( -currentMinus); 
               }
           }
           
            customerCreditBean.setLog("销售单[" + outBean.getFullId() + "]延期[" + outBean.getTempType()
                                      + "天]付款,减分:" + MathTools.formatNum(currentMinus) + ".减分后:"
                                      + MathTools.formatNum( -currentMinus));
        }
        else
        {
        	String eventid = outBean.getEventId();
            //如果为增加信用分则不减分
            if(null != eventid && eventid.length()>0)
            {
         	   PromotionBean promBean = promotionDAO.find(eventid);
                if(null != promBean && promBean.getRebateType()==2)
                {
             	   
                }
                else
                {
                	customerCreditBean
                    .setLog("销售单[" + outBean.getFullId() + "]延期[" + outBean.getTempType() + "天]付款,减分:"
                            + MathTools.formatNum(currentMinus) + ".减分后:"
                            + MathTools.formatNum( (customerCreditBean.getVal() - currentMinus)));

                	customerCreditBean.setVal(customerCreditBean.getVal() - currentMinus);
                }
            }
        }

        customerCreditBean.setCid(cid);

        customerCreditBean.setLogTime(TimeTools.now());

        customerCreditBean.setPtype(CreditConstant.CREDIT_TYPE_DYNAMIC);

        customerCreditBean.setItemId(CreditConstant.OUT_DELAY_ITEM);

        customerCreditBean.setPitemId(CreditConstant.OUT_DELAY_ITEM_PARENT);

        customerCreditBean.setValueId(delayItemThrBean.getId());

        final CustomerCreditBean fcustomerCreditBean = customerCreditBean;

        final boolean fisEnd = isEnd;

        // 操作在数据库事务中完成
        TransactionTemplate tranTemplate = new TransactionTemplate(transactionManager);

        try
        {
            // must send each item in Transaction(may be wait)
            tranTemplate.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    try
                    {
                        customerCreditManager.interposeCreditInnerWithoutUpdateLevel(user, cid, fcustomerCreditBean);
                    }
                    catch (MYException e)
                    {
                        _logger.warn(e, e);

                        throw new RuntimeException(e.getErrorContent());
                    }

                    // 更新out里面的状态
                    if (fisEnd)
                    {
                        outStatDAO.updateReserve1ByFullId(outBean.getFullId(),
                            CreditConstant.CREDIT_OUT_END, MathTools.formatNum(fcustomerCreditBean
                                .getVal()));
                    }
                    else
                    {
                        // 更新当前已经延期级别
                        outStatDAO.updateReserve5ByFullId(outBean.getFullId(), delayItemThrBean
                            .getId());
                    }

                    saveCurLog(cid, outBean, fcustomerCreditBean.getVal());

                    triggerLog.info("handle out stat[" + cid + "]:" + outBean.getFullId());

                    return Boolean.TRUE;
                }
            });
        }
        catch (Throwable e)
        {
            _logger.error(e, e);
        }
    }

    /**
     * handleCurrentMinus
     * 
     * @param outBean
     * @param delayItemThrBean
     * @return
     */
    private double handleCurrentMinus(final OutBean outBean,
                                      final CreditItemThrBean delayItemThrBean)
    {
        boolean isNearestDelay = false;

        OutBean nearestById = outStatDAO.findNearestById(outBean.getId(), outBean.getCustomerId());

        if (nearestById != null && nearestById.getTempType() > 0)
        {
            isNearestDelay = true;
        }

        double currentMinus = delayItemThrBean.getPer();

        // 上次已经扣除了一部分了
        if ( !StringTools.isNullOrNone(outBean.getReserve4()))
        {
            // 这里是正的
            double hasMinus = Math.abs(MathTools.parseDouble(outBean.getReserve4()));

            currentMinus = currentMinus - hasMinus;
        }

        if (isNearestDelay)
        {
            // 扣除双倍的
            return currentMinus * 2;
        }

        return currentMinus;
    }

    /**
     * @return the curOutDAO
     */
    public CurOutDAO getCurOutDAO()
    {
        return curOutDAO;
    }

    /**
     * @param curOutDAO
     *            the curOutDAO to set
     */
    public void setCurOutDAO(CurOutDAO curOutDAO)
    {
        this.curOutDAO = curOutDAO;
    }

    /**
	 * @return the customerMainDAO
	 */
	public CustomerMainDAO getCustomerMainDAO()
	{
		return customerMainDAO;
	}

	/**
	 * @param customerMainDAO the customerMainDAO to set
	 */
	public void setCustomerMainDAO(CustomerMainDAO customerMainDAO)
	{
		this.customerMainDAO = customerMainDAO;
	}

	/**
     * @return the outStatDAO
     */
    public OutStatDAO getOutStatDAO()
    {
        return outStatDAO;
    }

    /**
     * @param outStatDAO
     *            the outStatDAO to set
     */
    public void setOutStatDAO(OutStatDAO outStatDAO)
    {
        this.outStatDAO = outStatDAO;
    }

    /**
     * @return the transactionManager
     */
    public PlatformTransactionManager getTransactionManager()
    {
        return transactionManager;
    }

    /**
     * @param transactionManager
     *            the transactionManager to set
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager)
    {
        this.transactionManager = transactionManager;
    }

    /**
     * @return the creditlogDAO
     */
    public CreditlogDAO getCreditlogDAO()
    {
        return creditlogDAO;
    }

    /**
     * @param creditlogDAO
     *            the creditlogDAO to set
     */
    public void setCreditlogDAO(CreditlogDAO creditlogDAO)
    {
        this.creditlogDAO = creditlogDAO;
    }

    /**
     * @return the creditItemSecDAO
     */
    public CreditItemSecDAO getCreditItemSecDAO()
    {
        return creditItemSecDAO;
    }

    /**
     * @param creditItemSecDAO
     *            the creditItemSecDAO to set
     */
    public void setCreditItemSecDAO(CreditItemSecDAO creditItemSecDAO)
    {
        this.creditItemSecDAO = creditItemSecDAO;
    }

    /**
     * @return the customerCreditManager
     */
    public CustomerCreditManager getCustomerCreditManager()
    {
        return customerCreditManager;
    }

    /**
     * @param customerCreditManager
     *            the customerCreditManager to set
     */
    public void setCustomerCreditManager(CustomerCreditManager customerCreditManager)
    {
        this.customerCreditManager = customerCreditManager;
    }

    /**
     * @return the customerCreditDAO
     */
    public CustomerCreditDAO getCustomerCreditDAO()
    {
        return customerCreditDAO;
    }

    /**
     * @param customerCreditDAO
     *            the customerCreditDAO to set
     */
    public void setCustomerCreditDAO(CustomerCreditDAO customerCreditDAO)
    {
        this.customerCreditDAO = customerCreditDAO;
    }

    /**
     * @return the creditItemThrDAO
     */
    public CreditItemThrDAO getCreditItemThrDAO()
    {
        return creditItemThrDAO;
    }

    /**
     * @param creditItemThrDAO
     *            the creditItemThrDAO to set
     */
    public void setCreditItemThrDAO(CreditItemThrDAO creditItemThrDAO)
    {
        this.creditItemThrDAO = creditItemThrDAO;
    }

    /**
     * saveCurLog
     * 
     * @param customerBean
     * @param outBean
     * @param minus
     */
    private void saveCurLog(final String cid, final OutBean outBean, double minus)
    {
        if (minus != 0.0)
        {
            // add log CurOutBean
            CurOutBean log = new CurOutBean();

            log.setCid(cid);

            log.setDelay(outBean.getTempType());

            log.setLogTime(TimeTools.now());

            log.setOutId(outBean.getFullId());

            log.setVal(minus);

            curOutDAO.saveEntityBean(log);
        }
    }

    /**
     * @return the creditCoreDAO
     */
    public CreditCoreDAO getCreditCoreDAO()
    {
        return creditCoreDAO;
    }

    /**
     * @param creditCoreDAO
     *            the creditCoreDAO to set
     */
    public void setCreditCoreDAO(CreditCoreDAO creditCoreDAO)
    {
        this.creditCoreDAO = creditCoreDAO;
    }

    /**
     * saveCore
     * 
     * @param cid
     * @param amount
     * @param isMax
     */
    private void saveCore(final String cid, final double amount, boolean isMax)
    {
        CreditCoreBean core = new CreditCoreBean();

        core.setCid(cid);

        if (isMax)
        {
            core.setMaxBusiness(amount);
        }
        else
        {
            core.setSumTotal(amount);
        }

        core.setYear(TimeTools.getYeay());

        core.setLogTime(TimeTools.now());

        CreditCoreBean old = creditCoreDAO.findByUnique(cid);

        if (old == null)
        {
            creditCoreDAO.saveEntityBean(core);
        }
        else
        {
            core.setId(old.getId());

            if (isMax)
            {
                core.setSumTotal(old.getSumTotal());
            }
            else
            {
                core.setMaxBusiness(old.getMaxBusiness());
            }

            creditCoreDAO.updateEntityBean(core);
        }
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
     * @return the creditItemDAO
     */
    public CreditItemDAO getCreditItemDAO()
    {
        return creditItemDAO;
    }

    /**
     * @param creditItemDAO
     *            the creditItemDAO to set
     */
    public void setCreditItemDAO(CreditItemDAO creditItemDAO)
    {
        this.creditItemDAO = creditItemDAO;
    }

	public PromotionDAO getPromotionDAO() {
		return promotionDAO;
	}

	public void setPromotionDAO(PromotionDAO promotionDAO) {
		this.promotionDAO = promotionDAO;
	}
}